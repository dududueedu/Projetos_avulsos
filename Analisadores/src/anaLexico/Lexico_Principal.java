package anaLexico;

import anaLexico.analisadorLexico;

public class Lexico_Principal {
	public static void main(String[] args) {
		//String exp = "x = ((soma2 + 34)) * (fator1 / 2);";
		String exp = "x = 2.1 ^ 1;";
		analisadorLexico analexSimples = new analisadorLexico(exp);
		analexSimples.analex();
		System.out.println(analexSimples);
	}
}