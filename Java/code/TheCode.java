package code;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.Scanner;
import java.util.Arrays;
import java.security.MessageDigest;
import java.math.BigInteger;

public class TheCode {
    
    public static void main(String[] args) {
	// G?n?re le mot de passe

	Boolean[] carac;
	    String site;
	    String clef;
	    int longueur;
	
	if (args.length != 7){
	    // main : ce qui s execute
	    site = "site";
	    clef = "clef";
	    carac = new Boolean[]{true, true, true, true};
	    longueur = 20;
	    
	}
	else {
	    site = args[0];
	    clef = args[1];
	    carac =  new Boolean[]{Boolean.parseBoolean(args[2]), Boolean.parseBoolean(args[3]), Boolean.parseBoolean(args[4]), Boolean.parseBoolean(args[5])};
	    longueur = Integer.parseInt(args[6]);
	}
	String base = modifBase(carac[0], carac[1], carac[2], carac[3]);
	modification(site + clef, longueur, base);
	
    }

    private static String dec2Base(BigInteger x, String base) {
	// Convertit un BigInteger dans une base ayant base comme support

	BigInteger b = new BigInteger(String.valueOf(base.length()));
	StringBuilder result = new StringBuilder();
	BigInteger zero = new BigInteger("0");
	if (x.equals(zero)) {
	    result = new StringBuilder(base.charAt(0));
	} else {
	    while (!x.equals(zero)) {
		int inter = x.mod(b).intValue();
		result.insert(0, base.charAt(inter));
		x = x.divide(b);
	    }
	}
	return result.toString();
    }

    private static String sha256(String mot) {
	// Modifie mot en un chiffre en hexad?cimal suivant la fonction de hachage sha256
	
	try {
	    MessageDigest digest = MessageDigest.getInstance("SHA-256");
	    byte[] hash = digest.digest(mot.getBytes("UTF-8"));
	    StringBuilder hexString = new StringBuilder();
	    
	    for (byte b : hash) {
		String hex = Integer.toHexString(0xff & b);
		if (hex.length() == 1) hexString.append('0');
		hexString.append(hex);
	    }
	    return hexString.toString();
	} catch (Exception ex) {
	    throw new RuntimeException(ex);
	}
    }
	
    private static String modifBase(Boolean min, Boolean maj, Boolean sym, Boolean chi) {
	// Modifie la base suivant les caract?res coch?s
	    
	String base = "";
	if (min) {
	    base += "portezcviuxwhskyajgblndqfm";
	}
	if (maj) {
	    base += "THEQUICKBROWNFXJMPSVLAZYDG";
	}
	if (sym) {
	    base += "@#&!)-%;<:*$+=/?>(";
	}
	if (chi) {
	    base += "567438921";
	}
	return base;
    }

    private static void modification(String mot, int len, String base) {
	// Modifie le site et la clef en un mot de passe (mot = site + clef)
	    
	BigInteger code = new BigInteger(sha256(mot), 16);
	    
	int nb_carac = base.length();
	    
	String code2 = dec2Base(code, base).substring(0, len);
	    
	int bits = (int) ((Math.round(Math.log(Math.pow(nb_carac, code2.length())) / Math.log(2))));
	if (bits == 0) {
	    code2 = "";
	}
	    
	copy(code2);
	System.out.println("Le mot de passe est : " + code2 + " de securite : " + bits);
    }
    public static void copy(String mess) {
	// copie dans le presse papier
	StringSelection ss = new StringSelection(mess);
	Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
    }
    
}
