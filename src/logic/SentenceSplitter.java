package logic;

public class SentenceSplitter {
	/**
	 * podziel zdanie na wyrazy
	 * @param sentence zdanie
	 * @return tablica wyrazów
	 */
	public static String[] split(String sentence){
		String temp = sentence.replaceAll("[.]", "");
		//temp = temp.replaceAll("?", "");
		temp = temp.replaceAll("!", "");
		//temp = temp.replaceAll("...", "");
		return temp.split(" ");
	}
}
