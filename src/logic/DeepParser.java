package logic;

import java.util.List;

import parser.Loader;

public class DeepParser {
	private Dictionary dictionary;
	
	/**
	 * Wczytaj wszystkie regu³y oraz s³owa z plików:
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
	 * Przeanalizuj zdanie pod k¹tem danej gramatyki bezkontekstowej.
	 * Wyœwietla drzewo reprezentuj¹ce sk³adnie zdania w przypadku jeœli zdanie
	 * jest poprawne. Jeœli jest b³êdne wyœwietla stosowny komunikat.
	 * @param sentence - zdanie do analizy
	 */
	@SuppressWarnings("unchecked")
	public void parse(String sentence){
		System.out.println("[OK][Parsing]'" + sentence + "'");
		String[] _sentence = SentenceSplitter.split(sentence);
		
		/*
		 * T - struktura algorytmu CYK
		 * pierszy indeks oznacza numer wyrazu z parsowanego zdania
		 * drugi indeks oznacza g³êbokoœæ konstruowanych drzew
		 * trzeci indeks przebiega wszystkie regu³y gramatyki
		 * 
		 * podstawowy typ Object jest w rzeczywistoœci list¹ wêzów TreeNode
		 * dla ka¿dej regu³y na danej g³êbokoœci drzewa
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
		 * iteracja po d³ugoœciach ci¹gów wyrazów np dla 'Ala ma kota.'
		 * d³ = 2 { (Ala ma) , (ma kota) } 
		 * d³ = 3 { (Ala ma kota) }
		 */
		for(int i=2;i<_sentence.length+1;i++){
			/*
			 * iteracja po mo¿liwych pocz¹tkach ci¹gu wyrazów np. dla 'Ala ma kota.'
			 * bêd¹ to 1 i 2gi wyraz dla ci¹gu d³2 
			 * oraz 1 dla ci¹gu d³3
			 */
			for(int j=1;j<(_sentence.length-i+2);j++){
				/*
				 * iteracja po mo¿liwych kombinacjach podzia³u ci¹gu na pary
				 * np dla 'Ala ma kota' dla d³=2 i pocz¹tku w 1 bêd¹ to
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
					 * iteracja po zbiorze regu³ lewego argumentu - A
					 */
					for(int A = 0; A < dictionary.rules.size(); A++){
						/*
						 * interacja po zbiorze regu³ prawego elementu - B
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
		 * wyœwietl drzewa mo¿liwych rozk³¹dów frazy
		 * lub napisz ¿e zdanie jest niepoprawne wg
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
