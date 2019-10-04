package br.ufc.si.analex;

public enum Token {

	PARENTESIS_ESQ('('),
	PARENTESIS_DIR(')'),
	OPERADOR_SOMA('+'),
	OPERADOR_MULT('*'),
	OPERADOR_DIVI('/'),
	OPERADOR_SUBT('-'),
	OPERADOR_ATRIB('='),
	PONTO_VIRGULA(';'),
	FIM('F'),
	
        //begin e end TOKEN
        BEGIN('$'),
        END('#'),
        
	IDENTIFICADOR('I'),
	LITERAL_INTEIRO('L'),

	OPERADOR_EXP('^'),
	LITERAL_FLUTANTE('.');
	
	
	private final char valor;
	
	Token(char valor) {
		this.valor = valor;
	}
	
	public char getValor() {
		return this.valor;
	}
}

