package code;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.Scanner;
import java.util.Arrays;

public class TheCode {

    public static String[] alphabets = {"pamqlsoziekdjfurythgnwbxvc", "wqapmloikxszedcjuyhnvfrtgb", "qaszdefrgthyjukilompcvbnxw", "nhybgtvfrcdexszwqajuiklopm", "mlkjhgfdsqwxcvbnpoiuytreza", "ijnbhuygvcftrdxwsezqakolpm", "poiuytrezamlkjhgfdsqnbvcxw", "wxcvbnqsdfghjklmazertyuiop", "unybtvrcexzwaqikolpmjhgfds", "oplmkjuiytghbnvcfdrezasqxw", "sezqadrftwxcgyvhubjinkolpm", "jfkdlsmqhgpaozieurytvcbxnw", "gftrhdyejsuzkqailompnwbxvc", "frgthyjukilompnbvcxwedzsaq", "gftryehdjsuziakqlopmnwbxvc", "mlkjhgfdsqxecrvtbynuiopwza", "pamqlsoziekdjfurythgnwbvcx", "jklmuiopdfghertyqsazwxcvbn", "vgfcbhdxnjwskiqazolmpertyu", "onbivucyxtwrezapgjhklmfdsq", "portezcviuxwhskyajgblndqfm", "qposidufygthjreklzmawnxbvc", "pwoxicuvbtynrmelzakjhgfdsq", "hajzkelrmtgyfudisoqpnbvxcw", "wqxscdvfbgnhjukilompytreza", "thequickbrownfxjmpsvlazydg", "abcdefghijklmnopqrstuvwxyz"};
   
    public static void main(String[] args) {

	String codage;
	String site;
	String clef;
	int A;
	int B;
	Boolean[] carac;
	int longueur;
	
	if (args.length != 10){
	    // main : ce qui s execute
	    codage = "vigenere";
	    site = "site";
	    clef = "clef";
	    A = 7;
	    B = 3;
	    carac = new Boolean[]{true, true, true, false};
	    longueur = 20;
	    
	}
	else {

	    codage = args[0];
	    site = args[1];
	    clef = args[2];
	    A = Integer.parseInt(args[3]);
	    B = Integer.parseInt(args[4]);
	    carac = new Boolean[]{Boolean.parseBoolean(args[5]), Boolean.parseBoolean(args[6]), Boolean.parseBoolean(args[7]), Boolean.parseBoolean(args[8])};
	    longueur = Integer.parseInt(args[9]);;	    
	    
	}

	codage(codage, site, clef, A, B, carac[0], carac[1], carac[2], carac[3], longueur);
	
    }
    public static void codage(String codage, String site, String clef, int A, int B, Boolean min, Boolean maj, Boolean sym, Boolean chi, int longueur){
	String[] code;
	if (codage.equals("vigenere")) {
	    code = complexification(codeLettre(longueur(site), clef), longueur, min, maj,sym, chi);
	    copy(code[0]);
	    System.out.println("Le mot de passe vigenere est : " + code[0] + " de securite : " + code[1]);
	    
	}
	else {
	    code = complexification(codeChiffre(longueur(site), A,B), longueur, min, maj,sym, chi);
	    copy(code[0]);
	    System.out.println("Le mot de passe affine est : " + code[0] + " de securite : " + code[1]);
	}
    }

	
    public static String codeLettre(String mot, final String clef) {
	// Code le mot avec la clef
	StringBuilder code = new StringBuilder();
	for (int i = 0; i < mot.length(); i++) {
	    String alpha1 = alphabet(mot.charAt(1));
	    String alpha2 = alphabet(mot.charAt(0));
	    String alpha3 = alphabet(clef.charAt(0));
	    code.append(alpha1.charAt((alpha2.indexOf(mot.charAt(i)) + alpha3.indexOf(clef.charAt(i % clef.length()))) % 26));
	}
	return code.toString();
    }

    public static String codeChiffre(String mot, int A, int B) {
	StringBuilder code = new StringBuilder();
	for(int i = 0; i < mot.length(); i++) {
	    String alpha0 = alphabet(mot.charAt(0));
	    String alpha1 = alphabet(alpha0.charAt(A % alpha0.length()));
	    String alpha2 = alphabet(alpha0.charAt(B % alpha0.length()));
	    code.append(alpha1.charAt((A*alpha2.indexOf(mot.charAt(i))+B)%26));
	}
	return code.toString();
    }

    private static String alphabet(char lettre) {
       
	return alphabets[alphabets[26].indexOf(lettre)];
	
    }
   
    public static String[] complexification(String code, int len2, Boolean minState, Boolean majState, Boolean symState, Boolean chiState){
	// complexifie le mot de passe
	String alpha1 = alphabet(code.charAt(0)).toUpperCase();
	String alpha2 = alphabet(code.charAt(1));
	String symboles = "@#&!)-_%;:*$+=/?<>&-?($*@#";
	
	StringBuilder code2 = new StringBuilder();
	int len = code.length();
	int nb_carac;
	char lettrei;
	
	if (minState && !majState && !symState && !chiState) {
	    nb_carac = 26;
	    code2 = new StringBuilder(code);
	}
	else if (!minState && majState && !symState && !chiState) {
	    nb_carac = 26;
	    code2 = new StringBuilder(code.toUpperCase());
	}
	else if (!minState && !majState && symState && !chiState){
	    nb_carac = 26;
	    for(int i = 0; i < len; i++) {
		lettrei = code.charAt(i);
		code2.append(symboles.charAt(alpha2.indexOf(lettrei)));
	    }
	}
	else if (!minState && !majState && !symState && chiState) {
	    nb_carac = 10;
	    for(int i = 0; i < len; i++) {
		lettrei = code.charAt(i);
		code2.append(alpha2.indexOf(lettrei));
	    }
	}
	else if (minState && majState && !symState && !chiState) {
	    nb_carac = 52;
	    for(int i = 0; i < len; i++) {
		if (i%2 == 0){
		    code = code.toLowerCase();
		    lettrei = code.charAt(i);
		    code2.append(lettrei);
		}
		else {
		    code = code.toUpperCase();
		    lettrei = code.charAt(i);
		    code2.append(lettrei);
		}
	    }
	}
	else if (minState && !majState && symState && !chiState) {
	    nb_carac = 52;
	    for(int i = 0; i < len; i++) {
		lettrei = code.charAt(i);
		if (i%2 == 0){
		    code2.append(symboles.charAt(alpha2.indexOf(lettrei)));
		}
		else { code2.append(lettrei);
		}
	    }
	}
	else if (minState && !majState && !symState) {
	    nb_carac = 36;
	    for(int i = 0; i < len; i++) {
		lettrei = code.charAt(i);
		if (i%2 == 0){
		    code2.append(lettrei);
		}
		else {
		    code2.append(alpha2.indexOf(lettrei));
		}
	    }
	}
	else if (!minState && majState && symState && !chiState) {
	    nb_carac = 52;
	    code = code.toUpperCase();
	    for(int i = 0; i < len; i++) {
		lettrei = code.charAt(i);
		if (i%2 == 0){
		    code2.append(symboles.charAt(alpha1.indexOf(lettrei)));
		}
		else {
		    code2.append(lettrei);
		}
	    }
	}
	else if (!minState && majState && !symState) {
	    nb_carac = 36;
	    code = code.toUpperCase();
	    for(int i = 0; i < len; i++) {
		lettrei = code.charAt(i);
		if (i%2 == 0){
		    code2.append(lettrei);
		}
		else {
		    code2.append(alpha1.indexOf(lettrei));
		}
	    }
	}
	else if (!minState && !majState && symState) {
	    nb_carac = 36;
	    for(int i = 0; i < len; i++) {
		lettrei = code.charAt(i);
		if (i%2 == 0){
		    code2.append(symboles.charAt(alpha2.indexOf(lettrei)));
		}
		else {
		    code2.append(alpha2.indexOf(lettrei));
		}
	    }
	}
	else if (minState && majState && symState && !chiState) {
	    nb_carac = 78;
	    for(int i = 0; i < len; i++) {
		if (i % 3 == 0) {
		    code = code.toLowerCase();
		    lettrei = code.charAt(i);
		    code2.append(symboles.charAt(alpha2.indexOf(lettrei)));
		}
		else if (i % 3 == 1) {
		    code = code.toLowerCase();
		    lettrei = code.charAt(i);
		    code2.append(lettrei);
		}
		else {
		    code = code.toUpperCase();
		    lettrei = code.charAt(i);
		    code2.append(lettrei);
		}
	    }
	}
	else if (!minState && majState) {
	    nb_carac = 62;
	    for(int i = 0; i < len; i++) {
		if (i % 3 == 0) {
		    code = code.toLowerCase();
		    lettrei = code.charAt(i);
		    code2.append(alpha2.indexOf(lettrei));
		}
		else if (i % 3 == 1) {
		    code = code.toLowerCase();
		    lettrei = code.charAt(i);
		    code2.append(symboles.charAt(alpha2.indexOf(lettrei)));
		}
		else {
		    code = code.toUpperCase();
		    lettrei = code.charAt(i);
		    code2.append(lettrei);
		}
	    }
	}
	else if (minState && !majState) {
	    nb_carac = 62;
	    for(int i = 0; i < len; i++) {
		lettrei = code.charAt(i);
		if (i % 3 == 0) {
		    code2.append(lettrei);
		}
		else if (i % 3 == 1) {
		    code2.append(symboles.charAt(alpha2.indexOf(lettrei)));
		}
		else {
		    code2.append(alpha2.indexOf(lettrei));
		}
	    }
	}
	else if (minState && !symState) {
	    nb_carac = 62;
	    for(int i = 0; i < len; i++) {
		if (i % 3 == 0) {
		    code = code.toUpperCase();
		    lettrei = code.charAt(i);
		    code2.append(lettrei);
		}
		else if (i % 3 == 1) {
		    code = code.toLowerCase();
		    lettrei = code.charAt(i);
		    code2.append(lettrei);
		}
		else {
		    code = code.toLowerCase();
		    lettrei = code.charAt(i);
		    code2.append(alpha2.indexOf(lettrei));
		}
	    }
	}
	else if (minState){
	    nb_carac = 88;
	    for(int i = 0; i < len; i++) {
		if (i % 4 == 0) {
		    code = code.toLowerCase();
		    lettrei = code.charAt(i);
		    code2.append(symboles.charAt(alpha2.indexOf(lettrei)));
		}
		else if (i % 4 == 1) {
		    code = code.toLowerCase();
		    lettrei = code.charAt(i);
		    code2.append(lettrei);
		}
		else if (i% 4 == 2){
		    code = code.toLowerCase();
		    lettrei = code.charAt(i);
		    code2.append(alpha2.indexOf(lettrei));
		}
		else {
		    code = code.toUpperCase();
		    lettrei = code.charAt(i);
		    code2.append(lettrei);
		}
	    }
	}
	else {
	    nb_carac = 0;
	    code2 = new StringBuilder(code);
	}
	String code3 = code2.toString().substring(0,len2);
	int safe = (int) ((Math.round(Math.log(Math.pow(nb_carac,code3.length()))/Math.log(2))));
	return new String[] {code3, safe + " bits"};
    }
   
    public static void copy(String mess) {
	// copie dans le presse papier
	StringSelection ss = new StringSelection(mess);
	Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
    }
   
   
   
    public static String longueur(String site) {
	// Corrige la longueur du mot pour faire 20
	site = site.toLowerCase();
	String L = alphabet(site.charAt(0));
	StringBuilder site2 = new StringBuilder();

	for (int i = 0; i<(site.length() * 2) ; i++) {
	    if (i%2 == 0) {
		site2.append(site.charAt(i/2));
	    }
	    else {
		site2.append(L.charAt(i/2));
	    }
	}
	site = site2.toString();
	String T = alphabet(site.charAt(1));
	if (site.length()<=20) {
	    for (int i = 0; i < (20 - site.length()) ; i++) {
		site2.append(T.charAt(i));
	    }
	    site = site2.toString();
	}
	else {
	    site = site.substring(0, 20);
	}
	return site;
    }
}
