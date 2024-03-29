package br.ufc.si.anasin;
import java.util.List;
import br.ufc.si.analex.*;

public class AnasinSimp {

	private final List<TokenLexema> tokenLexemaList;
	private int contador = 0;

	public AnasinSimp(String cadeia) {
		analisadorLexico analex = new analisadorLexico(cadeia);
		this.tokenLexemaList = analex.analex();
	}
	
	public void anasint() {
		if(this.tokenLexemaList!=null) {
			this.program();
			if(this.contador!=tokenLexemaList.size()) {
				//ERRO NO ANALISADOR SINT�TICO
				this.msg("ERRO NO ANALISADOR SINTÁTICO");
			}
		}
		else {
			//ERRO NO ANALISADOR L�XICO
			this.msg("ERRO NO ANALISADOR LÉXICO");
		}
		
	}
	
        private void  program() {
		this.msg("Entrou program()");
		if(this.nextToken().getToken() == Token.BEGIN) {
			this.msg("token: " + this.nextToken());
			this.lex();
			this.stmt_list();
		}else
			this.msg("ERRO: Token: BEGIN esperado!");
		if(this.nextToken().getToken() != Token.END) {
			this.msg("ERRO: Token: END esperado!");
		}else {
			this.lex();
			this.msg("Saiu program() teste id, token: " + this.nextToken());
		}
	}
	
	private void stmt_list() {
		this.msg("Entrou em stmt_list");
		
		this.stmt();
		while(this.nextToken().getToken() == Token.PONTO_VIRGULA) {
			this.lex();
			this.stmt_list();
                        
                        //if(this.nextToken().getToken != Token)
		}
		this.msg("Saiu do stmt_list, token: "+this.nextToken());
	}
	
	private void stmt() {
		this.msg("Entrou em stmt, token: " + this.nextToken());
		if(this.nextToken().getToken() == Token.IDENTIFICADOR) {
			this.msg("ID: " + this.nextToken());
			this.lex();
			if(this.nextToken().getToken() == Token.OPERADOR_ATRIB) {
				this.msg("token: " + this.nextToken());
				this.lex();
				this.expression();
			}
		}else this.msg("ERRO ID");
		this.msg("Saiu do stmt, teste ID, token: " + this.nextToken());
	}
	
	private void expression() {
		this.msg("Entrou em expression(), token: " + this.nextToken());
		this.term();
		while(this.nextToken().getToken() == Token.OPERADOR_SOMA ||
			  this.nextToken().getToken() == Token.OPERADOR_SUBT) {
			this.lex();
			this.term();
		}
		this.msg("Saiu de expression(), token: " + this.nextToken());
	}
	
	private void term() {
		this.msg("Entrou em term(), token: " + this.nextToken());
		this.factor();
		while(this.nextToken().getToken() == Token.OPERADOR_MULT ||
			  this.nextToken().getToken() == Token.OPERADOR_DIVI) {
			this.lex();
			this.factor();
		}
		this.msg("Saiu de term(), token: " + this.nextToken());
	}
	
	private void factor() {
		this.msg("Entrou em factor(), token: " + this.nextToken());
		this.expr();
		if(this.nextToken().getToken() == Token.OPERADOR_EXP) {
			this.lex();
			this.factor();
		}
		this.msg("Saiu de factor(), token: " + this.nextToken());
	}
	
	/*private void expr() {
		this.msg("Entrou em expr(), token: " + this.nextToken());
		if(this.nextToken().getToken() == Token.IDENTIFICADOR) {
			this.msg("ID: " + this.nextToken());
			this.lex();
		}
		
		if(this.nextToken().getToken() == Token.LITERAL_INTEIRO) {
			this.lex();
			if(this.nextToken().getToken() == Token.LITERAL_FLUTANTE) {
				this.lex();
				/*if(this.nextToken().getToken() == Token.LITERAL_INTEIRO) {
					this.msg("Token: CONSTANTE_FLOAT ");
				}
			}else
				this.msg("Token: LITERAL_INTEIRO");
		}
		
		if(this.nextToken().getToken() == Token.PARENTESIS_ESQ) {
			this.msg(""+this.nextToken());
			this.lex();
			this.expression();
			if(this.nextToken().getToken() == Token.PARENTESIS_DIR) {
				this.msg(""+this.nextToken());
			}
		}
		this.msg("Saiu de expr(), token: " + this.nextToken());
	}
	*/
	/*private void expr() {
		this.msg("entrou em exp(), token: " + this.nextToken());
		if(null != this.nextToken().getToken()) switch (this.nextToken().getToken()) {
                case IDENTIFICADOR:
                    this.lex();
                    break;
                case LITERAL_INTEIRO:
                    this.lex();
                    break;
                case LITERAL_FLUTANTE:
                    this.lex();
                    break;
                case PARENTESIS_ESQ:
                    this.lex();
                    this.expression();
                    break;
                default:
                    break;
            }
            if(this.nextToken().getToken() == Token.PARENTESIS_DIR){
			this.lex();
		}else {
			this.msg("ERRO");
		}
		this.msg("Saiu do factr(), Token " + this.nextToken());
		
		
		
	}
	*/	
        //mudei essa
        private void expr() {
		this.msg("entrou em exp(), token: " + this.nextToken());
		if(null != this.nextToken().getToken()) switch (this.nextToken().getToken()) {
                case IDENTIFICADOR:
                    this.lex();
                    break;
                case LITERAL_INTEIRO:
                    this.lex();
                    break;
                case LITERAL_FLUTANTE:
                    this.lex();
                    break;
                case PARENTESIS_ESQ:
                    this.lex();
                    this.expression();
                    if(this.nextToken().getToken() == Token.PARENTESIS_DIR){
                        this.lex();
                    }
                    else this.msg("****ALERTA! ERRO, parentesis direito esperado****");
                    break;
                default:
                    this.msg("ERRO");
                    break;
            }
		this.msg("Saiu do exp(), Token " + this.nextToken());
	
	}
		
	private void msg(String cadeia) {
		System.out.println(cadeia);
	}
	
	private void lex() {
		if(this.contador==this.tokenLexemaList.size()) return;
		this.contador++;
	}
	
	private TokenLexema nextToken() {
		if(this.contador == this.tokenLexemaList.size()) return new TokenLexema(Token.FIM, Token.FIM.getValor()+"");
		return this.tokenLexemaList.get(this.contador);
	}

}
