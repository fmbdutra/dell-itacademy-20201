import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Principal {

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

	private static final String[] LISTA_NUMEROS_ROMANDOS = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V",
			"IV", "I" };

	public static boolean EhNumeroRomano(String palavra) {
		boolean ret = false;
		for (String numero : LISTA_NUMEROS_ROMANDOS) {
			if (numero.equalsIgnoreCase(palavra))
				ret = true;
		}

		return ret;

	}

	public static void leitorDeTexto(String caminhoTexto) throws IOException {
		Path path = Paths.get(caminhoTexto);
		List<String> linhasArquivo = Files.readAllLines(path);

		// MAP PARA SALVAR CHAVE DAS REPRESENTAÇÕES ROMANDAS
		Map<String, String> numeros = new HashMap<String, String>();

		// MAP PARA SALVAR CHAVE E VALOR DOS METAIS
		Map<String, Double> metais = new HashMap<String, Double>();

		for (String linha : linhasArquivo) {
			// PARA SEPARAR AS PALAVRAS NA LINHAS E PODER IDENTIFICAR
			// E FAZER AS CONDIÇÕES PARA SABER O QUE ESTÁ SENDO PEDIDO
			// SE É O VALOR DOS ROMANOS, METAIS OU PERGUNTAS

			String[] s = linha.split(" ");

			// METODO PARA LER E CONVERTER OS NUMEROS GALATICOS PARA ROMANOS
			if ("representa".equalsIgnoreCase(s[1]) && EhNumeroRomano(s[2])) {
				numeros.put(s[0], s[s.length - 1]);
			} 

			// METODO PARA LER METAIS E VALORES
			if ("valem".equalsIgnoreCase(s[s.length - 3]) && "créditos".equalsIgnoreCase(s[s.length - 1])) {
				String romanoDaFraseDecifrado = "";
				Double creditosPorcao = Double.parseDouble(s[s.length - 2]);
				String nomeDoMetal = s[s.length - 4];
				for (int i = 0; i < s.length - 4; i++) {
					String romanoDoMap = numeros.get(s[i]);
					romanoDaFraseDecifrado = romanoDaFraseDecifrado.concat(romanoDoMap);
				}

				int porcaoDecifrada = converteRomanoParaArabico(romanoDaFraseDecifrado);
				double precoDoMetalUnidade = creditosPorcao / porcaoDecifrada;

				metais.put(nomeDoMetal, precoDoMetalUnidade);
			}
			// --------------------
			// ------------------------
			// -----------------------------
			// PARA LER "QUANTO VALE"
			if ("quanto".equalsIgnoreCase(s[0]) && "vale".equalsIgnoreCase(s[1])
					&& "?".equalsIgnoreCase(s[s.length - 1])) {

				String romanoDaFraseDecifrado = "";
				String romanoDoMap = "";
				String siglas = "";

				try {
					for (int i = 2; i < s.length - 1; i++) {
						romanoDoMap = numeros.get(s[i]);
						romanoDaFraseDecifrado = romanoDaFraseDecifrado.concat(romanoDoMap);
						siglas = siglas.concat(s[i] + " ");
					}
					int siglaDecifrada = converteRomanoParaArabico(romanoDaFraseDecifrado);

					System.out.println(siglas + "é " + siglaDecifrada);

				} catch (Exception e) {
					System.out.println("Eu não sei do que voce está falando!");
				}
			}

			// PARA LER "QUANTOS CRÉDITOS"
			if ("quantos".equalsIgnoreCase(s[0]) && "créditos".equalsIgnoreCase(s[1]) 
					&& "são".equalsIgnoreCase(s[2]) 
					&& "?".equalsIgnoreCase(s[s.length - 1])) {

				String romanoDaFraseDecifrado = "";
				String romanoDoMap = "";
				String siglas = "";
				String pedra = s[s.length - 2];
				double creditosValem = 0;
				
				try {
					for (int i = 3; i < s.length - 2; i++) {
						romanoDoMap = numeros.get(s[i]);
						romanoDaFraseDecifrado = romanoDaFraseDecifrado.concat(romanoDoMap);
						siglas = siglas.concat(s[i] + " ");
					}
					int siglaDecifrada = converteRomanoParaArabico(romanoDaFraseDecifrado);
					double valorDaPedra = metais.get(pedra);

					creditosValem = siglaDecifrada * valorDaPedra;

					System.out.println(siglas + pedra + " é " + creditosValem + " créditos");

				} catch (Exception e) {
					System.out.println("Eu não sei do que voce está falando!");
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {

		leitorDeTexto("/ITAcademy20201/resources/teste.txt");
	}

}
