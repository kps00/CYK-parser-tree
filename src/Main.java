import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import logic.DeepParser;
/**
 * Implementacja algorytmu CYK analizuj�cego sk�adni� zadanego zdania.
 * regu�y umieszczone s� w pliku 'rules.csv'
 * ka�da regu�a ma posta� A->B;C
 * zapisan� w notacji chomskiego
 * z kolei ka�dy wyraz w pliku 'words.csv'
 * w analogicznej postaci B->s�owo
 * jako znaki terminalne tej�e notacji.
 * 
 * Parsowanie zdania wej�ciowego wymaga stworzenia instancji klasy logic.DeepParser, 
 * nast�pnie wczytania s�ownika (wczytuje zar�wno regu�y jak i s�owa) metod�
 * logic.DeepParser.loadDictionary()
 * oraz ostatecznie wywo�anei metody logic.DeepParser.parse(String zdanie). 
 * metoda ta wy�wietli w konsoli drzewo b�d�ce wynikiem analizy sk�adniowej,
 * a dok�adniej wywo�a metod� logic.TreeNode.print() dla roota. 
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
