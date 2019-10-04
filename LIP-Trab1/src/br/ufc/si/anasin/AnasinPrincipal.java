package br.ufc.si.anasin;

//import br.ufc.si.anasin.AnasinSimp;

public class AnasinPrincipal {
	public static void main(String[] args) {
		String exp  = "$ x = 2*6.73.2) + (1.1^2) #";
                String exp1 = "y = 7 + 4 - (2^2) * 5";    
                String exp2 = "$ z = 6/3 * 4 - (7^2) - 1; #";
    		AnasinSimp sintatico = new AnasinSimp(exp);
		sintatico.anasint();
	}
}
