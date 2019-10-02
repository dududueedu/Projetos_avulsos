package anaSintatico;

import anaSintatico.AnasinSimp;

public class AnasinPrincipal {
	public static void main(String[] args) {
		String exp = "x1=2*3 + (1^2)";
		AnasinSimp sintatico = new AnasinSimp(exp);
		sintatico.anasint();
	}
}
