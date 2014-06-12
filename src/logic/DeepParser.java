package logic;

import java.util.List;

import parser.Loader;

public class DeepParser {
	private Dictionary dictionary;
	
	/**
	 * Wczytaj wszystkie regu�y oraz s�owa z plik�w:
	 * rules.csv, words.csv.
	 */
	public void loadDictionary(){
		dictionary = new Dictionary();
		try{
			dictionary.rules = Loader.loadRulesProb();
		}
		catch(Exception e){
			System.out.println("[FAIL] Loading dictionary has failed. ");
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Przeanalizuj zdanie pod k�tem danej gramatyki bezkontekstowej.
	 * Wy�wietla drzewo reprezentuj�ce sk�adnie zdania w przypadku je�li zdanie
	 * jest poprawne. Je�li jest b��dne wy�wietla stosowny komunikat.
	 * @param sentence - zdanie do analizy
	 */
	@SuppressWarnings("unchecked")
	public void parse(String sentence){
		System.out.println("[OK][Parsing]'" + sentence + "'");
		String[] _sentence = SentenceSplitter.split(sentence);
		
		/*
		 * T - struktura algorytmu CYK
		 * pierszy indeks oznacza numer wyrazu z parsowanego zdania
		 * drugi indeks oznacza g��boko�� konstruowanych drzew
		 * trzeci indeks przebiega wszystkie regu�y gramatyki
		 * 
		 * podstawowy typ Object jest w rzeczywisto�ci list� w�z�w TreeNode
		 * dla ka�dej regu�y na danej g��boko�ci drzewa
		 */
		Object[][][] T = new Object[_sentence.length][_sentence.length][dictionary.rules.size()];
		
		for(int i=0; i < _sentence.length;i++){
			for(int j=0; j < _sentence.length;j++){
				for(int k=0; k < dictionary.rules.size();k++){
					T[i][j][k] = null;
				}
			}
		}

		for(int i=0; i < _sentence.length;i++){
			dictionary.checkForWord(i, 0, T, _sentence[i]);
		}
		
		/*
		 * iteracja po d�ugo�ciach ci�g�w wyraz�w np dla 'Ala ma kota.'
		 * d� = 2 { (Ala ma) , (ma kota) } 
		 * d� = 3 { (Ala ma kota) }
		 */
		for(int i=2;i<_sentence.length+1;i++){
			/*
			 * iteracja po mo�liwych pocz�tkach ci�gu wyraz�w np. dla 'Ala ma kota.'
			 * b�d� to 1 i 2gi wyraz dla ci�gu d�2 
			 * oraz 1 dla ci�gu d�3
			 */
			for(int j=1;j<(_sentence.length-i+2);j++){
				/*
				 * iteracja po mo�liwych kombinacjach podzia�u ci�gu na pary
				 * np dla 'Ala ma kota' dla d�=2 i pocz�tku w 1 b�d� to
				 * T[0][0] i T[1][1] 
				 * czyli 
				 * \__T[0][2]
				 *    |___[0][0]
				 *    |   \___ala
				 *    \___T[1][1]
				 *        |___ma
				 *        \___kota
				 * oraz T[0][1] i T[2][0]
				 * \__T[0][2]
				 *    |___T[0][1]
				 *    |   |__ala
				 *    |   \__ma
				 *    \___T[2][0]
				 *        \__kota
				 */
				for(int k=1;k<i;k++){
					/*
					 * iteracja po zbiorze regu� lewego argumentu - A
					 */
					for(int A = 0; A < dictionary.rules.size(); A++){
						/*
						 * interacja po zbiorze regu� prawego elementu - B
						 */
						for(int B = 0;B <dictionary.rules.size(); B++){
							/*
							 * sprawdzenie istnienia reguly C -> A , B
							 */
							if((T[j-1][k-1][A] != null) && (T[j+k-1][i-k-1][B] != null )){
								dictionary.checkForRule(j-1, i-1, T,T[j-1][k-1][A], T[j+k-1][i-k-1][B]);
							}
						}
					}
				}
			}
		}
		/*
		 * wy�wietl drzewa mo�liwych rozk��d�w frazy
		 * lub napisz �e zdanie jest niepoprawne wg
		 * zadanej gramatyki
		 */
		boolean isAword = false;
		for(int i=0;i<dictionary.rules.size();i++){
			if(T[0][_sentence.length-1][i] != null){
				for(int j=0;j<((List<TreeNode>)T[0][_sentence.length-1][i]).size();j++){
					((List<TreeNode>)T[0][_sentence.length-1][i]).get(j).print();
				}
				isAword = true;
			}
		}
		if(!isAword){
			System.out.println("[OK]zdanie niepoprawne.");
		}
	}
}
