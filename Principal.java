package repasses.exec;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import conteudo4_xml.ParserDOM;
import repasses.model.DelimitadorUf;
import repasses.model.Escola;
import repasses.model.ParserSAX;

public class Principal {

	public static final String ARQUIVO1 = "arquivos/pda-repasses-mais-educacao-adesao-2016.csv";
	public static final String ARQUIVO2 = "arquivos/pda-repasses-mais-educacao-adesao-2017.csv";
	public static final String ARQUIVO3 = "arquivos/repasses_mais_educacao_adesao_2014.csv";
	public static final String ARQUIVO_TESTE = "arquivos/teste.csv";
	public static final String ARQUIVO_XML = "arquivos/repasses.xml";
	
	public static void main(String[] args) {
		try {
			//parseWithDOM();
			//parseWithSAX();
			String[][] mat = LerCsv();
			List<DelimitadorUf> delimitadorUf = calcularUfs(mat);
			criarXML(mat,delimitadorUf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<DelimitadorUf> calcularUfs(String[][] mat) {
		int id_uf = 1;
		int escolas = 0;
		int comeco_uf = 1;
		ArrayList<DelimitadorUf> delimitadoresUf = new ArrayList<DelimitadorUf>();
		String uf_atual = mat[1][1];
		String uf_aux = "";
		
		for(int i=1;i<mat.length;i++) {
			uf_aux = mat[i][1];
			if(uf_aux.equals(uf_atual)) {
				escolas++;
			}
			else {
				DelimitadorUf delimitadorUf = new DelimitadorUf(id_uf, comeco_uf, i-1, escolas);
				comeco_uf = i;
				delimitadoresUf.add(delimitadorUf);;
				uf_atual = uf_aux;
				escolas = 1;
				id_uf++;
			}
		}
		DelimitadorUf delimitadorUf = new DelimitadorUf(id_uf, comeco_uf, mat.length-1, escolas);
		delimitadoresUf.add(delimitadorUf);
		return delimitadoresUf;
	}
	
	public static String[][] LerCsv() throws IOException {
		InputStream is = new FileInputStream(ARQUIVO1);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		
		List<String[]> l = new ArrayList<String[]>();
		
		while(br.ready()) {
			String linha = br.readLine();
			String[] celulas = linha.split(";");
			l.add(celulas);
			
		}
		String[][] mat = l.toArray(new String[][] {});
		br.close();
		
		//System.out.println(mat.length);
		/*for(int i=0;i<mat.length;i++) {
			for(int j=0;j<mat[0].length;j++) {
				System.out.println(mat[i][j]);
			}
		}*/
		return mat;
	}
	
	public static void criarXML(String[][] mat, List<DelimitadorUf> delimitadorUf) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element raiz = doc.createElement("repasses");
			doc.appendChild(raiz);
			
			for(int i=0;i<delimitadorUf.size();i++){ 
				int linhas = delimitadorUf.get(i).getComeco();
				String sigla_uf = mat[linhas][1];
				List<Escola> escolas = new ArrayList<Escola>();
				for(int k=0;k<delimitadorUf.get(i).getEscolas() && k<5 && linhas<=delimitadorUf.get(i).getFim();k++) {
					String municipio = mat[linhas][2];
					String esfera = mat[linhas][3];
					String localizacao = mat[linhas][4];
					String inep = mat[linhas][5];
					String nome_escola = mat[linhas][6];
					String alunado = mat[linhas][7];
					String total_recebido = mat[linhas][8];
					String ano = mat[linhas][0];
					Escola escola = new Escola(nome_escola, esfera, localizacao, inep, alunado, total_recebido,ano,municipio);
					escolas.add(escola);
					linhas ++;
				}
		
				Element uf = criarUf(mat,sigla_uf, Integer.toString(escolas.size()), escolas, doc);
				raiz.appendChild(uf);
				escolas.clear();
			}
			
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer optimusPrime = tFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(ARQUIVO_XML);
			optimusPrime.setOutputProperty(OutputKeys.INDENT, "yes");
			optimusPrime.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",	
			"4");	
			optimusPrime.transform(source,	result);
			System.out.println("Xml criado!");
		}catch(ParserConfigurationException|TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public static Element criarUf(String[][] mat, String sigla_uf, String municipios, 
			List <Escola> escolas, Document doc) {
	
		Element uf = doc.createElement("uf");
		
		Element tagSiglauf = doc.createElement("sigla");
		tagSiglauf.appendChild(doc.createTextNode(sigla_uf));
		uf.appendChild(tagSiglauf);
		
		Element tagMunicipiosuf = doc.createElement("qtd_municipios");
		tagMunicipiosuf.appendChild(doc.createTextNode(municipios));
		uf.appendChild(tagMunicipiosuf);
		
			
		for(int j=0;j<escolas.size();j++) {
			Element tagEscola = doc.createElement("escola");
			Attr attrAno = doc.createAttribute("ano");
			attrAno.setValue(String.valueOf(escolas.get(j).getAno()));
			tagEscola.setAttributeNode(attrAno);
			
			Element tagNomeescola = doc.createElement("nome");
			tagNomeescola.appendChild(doc.createTextNode(escolas.get(j).getNome()));
			tagEscola.appendChild(tagNomeescola);
			
			Element tagMunicipio = doc.createElement("municipio");
			tagMunicipio.appendChild(doc.createTextNode(escolas.get(j).getMunicipio()));
			tagEscola.appendChild(tagMunicipio);
				
			Element tagEsferaescola = doc.createElement("esfera");
			tagEsferaescola.appendChild(doc.createTextNode(escolas.get(j).getEsfera()));
			tagEscola.appendChild(tagEsferaescola);
				
			Element tagLocalizacaoescola = doc.createElement("localizacao");
			tagLocalizacaoescola.appendChild(doc.createTextNode(escolas.get(j).getLocalizacao()));
			tagEscola.appendChild(tagLocalizacaoescola);
				
			Element tagInepescola = doc.createElement("inep");
			tagInepescola.appendChild(doc.createTextNode(escolas.get(j).getInep()));
			tagEscola.appendChild(tagInepescola);
				
			Element tagAlunadoescola = doc.createElement("alunado");
			tagAlunadoescola.appendChild(doc.createTextNode(escolas.get(j).getAlunado()));
			tagEscola.appendChild(tagAlunadoescola);
				
			Element tagTotal_recebidoescola = doc.createElement("total_recebido");
			tagTotal_recebidoescola.appendChild(doc.createTextNode(escolas.get(j).getTotal_recebido()));
			tagEscola.appendChild(tagTotal_recebidoescola);
				
				
			uf.appendChild(tagEscola);
		}
		
		return uf;
	}
	
	public static void parseWithDOM() {
		ParserDOM parser = new ParserDOM();
		parser.parse(ARQUIVO_XML);
		parser.printRaiz();
	}
	
	public static void parseWithSAX() {
		ParserSAX parser = new ParserSAX();
		parser.parse(ARQUIVO_XML);
	}
}
