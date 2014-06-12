import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import logic.DeepParser;
/**
 * Implementacja algorytmu CYK analizuj¹cego sk³adniê zadanego zdania.
 * regu³y umieszczone s¹ w pliku 'rules.csv'
 * ka¿da regu³a ma postaæ A->B;C
 * zapisan¹ w notacji chomskiego
 * z kolei ka¿dy wyraz w pliku 'words.csv'
 * w analogicznej postaci B->s³owo
 * jako znaki terminalne tej¿e notacji.
 * 
 * Parsowanie zdania wejœciowego wymaga stworzenia instancji klasy logic.DeepParser, 
 * nastêpnie wczytania s³ownika (wczytuje zarówno regu³y jak i s³owa) metod¹
 * logic.DeepParser.loadDictionary()
 * oraz ostatecznie wywo³anei metody logic.DeepParser.parse(String zdanie). 
 * metoda ta wyœwietli w konsoli drzewo bêd¹ce wynikiem analizy sk³adniowej,
 * a dok³adniej wywo³a metodê logic.TreeNode.print() dla roota. 
 * @author kps
 *
 */

public class Main {

	public static void main(String[] args) {
		String sentence = "Marek ma kota.";
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		
		DeepParser deepParser = new DeepParser();
		deepParser.loadDictionary();
		
		
		while(true){
			try {
				sentence = bufferedReader.readLine();
				deepParser.parse(sentence);	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
