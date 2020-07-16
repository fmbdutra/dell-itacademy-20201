import static java.lang.String.join;
import static java.util.Collections.nCopies;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class main {

	public boolean validaNumeroRomano(String numeroRomano) {
		int repete = 0;
		boolean ret = true;

		for (int i = 0; i < numeroRomano.length(); i++) {
			char rom = numeroRomano.charAt(i);
			boolean naoPodeRepetir = rom == 'I' || rom == 'X' || rom == 'C' || rom == 'M';

			char numDepois = i < numeroRomano.length() - 1 ? numeroRomano.charAt(i + 1) : ' ';
			if (naoPodeRepetir) {
				if (numDepois == numeroRomano.charAt(i)) {
					repete++;
				} else {
					repete = 0;
				}
			}
			if (repete == 3) {
				return false;
			}
		}

		return ret;
	}

	public static int converteRomanoParaArabico(String numeroRomano) {
		int numeroEmArabico = 0;

		for (int i = 0; i < numeroRomano.length(); i++) {
			char numDepois = i < numeroRomano.length() - 1 ? numeroRomano.charAt(i + 1) : ' ';

			switch (numeroRomano.charAt(i)) {
			case 'I':
				if (numDepois == 'V' || numDepois == 'X') {
					numeroEmArabico -= 1;
				} else {
					numeroEmArabico += 1;
				}
				break;
			case 'V':
				numeroEmArabico += 5;
				break;
			case 'X':
				if (numDepois == 'L' || numDepois == 'C') {
					numeroEmArabico -= 10;
				} else {
					numeroEmArabico += 10;
				}
				break;
			case 'L':
				numeroEmArabico += 50;
				break;
			case 'C':
				if (numDepois == 'D' || numDepois == 'M') {
					numeroEmArabico -= 100;
				} else {
					numeroEmArabico += 100;
				}
				break;
			case 'D':
				numeroEmArabico += 500;
				break;
			case 'M':
				numeroEmArabico += 1000;
				break;
			default:
				System.out.println("Nao existe esse numero em romano!");
			}
		}
		return numeroEmArabico;

	}
	
	private static final String[] LISTA_NUMEROS_ROMANDOS = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
	
	public static boolean EhNumeroRomano(String palavra) {
		boolean ret = false;
		for(String numero : LISTA_NUMEROS_ROMANDOS) {
			if (numero.equalsIgnoreCase(palavra)) ret = true;
		}
		
		return ret;
		
	}
	
	public static void leitorDeTexto(String caminhoTexto) throws IOException {
		int numeroEmArabico = 0;
		ArrayList<String> metais = new ArrayList();
		Path path = Paths.get(caminhoTexto);
        List<String> linhasArquivo = Files.readAllLines(path);
        
        //PARA SEPARAR AS PALAVRAS NA LINHAS E PODER IDENTIFICAR
        //E FAZER AS CONDIÇÕES PARA SABER O QUE ESTÁ SENDO PEDIDO
        //SE É O VALOR DOS ROMANOS, METAIS OU PERGUNTAS
        int linhaTeste = 5;
        System.out.println(linhasArquivo.get(linhaTeste));
        String[] s = linhasArquivo.get(linhaTeste).split(" ");
        
        //METODO PARA LER E CONVERTER OS NUMEROS GALATICOS PARA ROMANOS
        if (s[1].equalsIgnoreCase("is") && EhNumeroRomano(s[2])) {
        	numeroEmArabico = converteRomanoParaArabico(s[2]);  
        	System.out.println(numeroEmArabico);
        }

        //METODO PARA LER METAIS E VALORES
        if(s[s.length-1].equalsIgnoreCase("credits")) {
        	metais.add(s[s.length-4]);
        	System.out.println(metais.get(0));
        }
	}

	public static void main(String[] args) throws Exception {

		int emArabico = converteRomanoParaArabico("XIX");
		
		leitorDeTexto("CAMINHO-DO-TEXTO");
	}

}
