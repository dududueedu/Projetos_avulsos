package br.ufc.si.analex;

import br.ufc.si.view.lexView;
import java.util.Scanner;

public class Lexico_Principal {
    static Scanner scan = new Scanner (System.in);
	public static void main(String[] args) {
		//String exp = "x = ((soma2 + 34)) * (fator1 / 2);";
		String exp = "x = 2.1 ^ 1;";
                String exp3  = "x = (2*6.73.2) + (1.1^2);";
                String exp1 = "y = 7 + 4 - (2^2) * 5";    
                String exp2 = "z = 6/3 * 4 - (7^2.8) - 1;";
		analisadorLexico analexSimples = new analisadorLexico(exp2);
		analexSimples.analex();
		System.out.println(analexSimples);
                //lexView lV= new lexView();
                //lV.setVisible(true);
	}
}