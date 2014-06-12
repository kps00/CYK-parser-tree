package logic;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
	public List<Rule> rules;
	public List<Word> words;
	
	/**
	 * sprawdz w s³owniku czy istnieje s³owo word
	 * jeœli jest, stwórz wêze³ drzewa w strkturze w miejcu T[i][j]
	 * @param i indeks pierwszego wyrazu
	 * @param j glebokosc drzewa
	 * @param t struktura danych
	 * @param word s³owo
	 */
	@SuppressWarnings("unchecked")
	public void checkForWord(int i, int j, Object[][][] t, String word){
		/*
		 * po wszystkich regu³ach (A -> s³owo)
		 */
		for(int k=0; k<rules.size();k++){
			if(rules.get(k).parts[0].equalsIgnoreCase(word)){
				t[i][j][k] = new ArrayList<TreeNode>();
				((List<TreeNode>)t[i][j][k]).add(new TreeNode(rules.get(k).name,new ArrayList<TreeNode>(),rules.get(k).p));
				((List<TreeNode>)t[i][j][k]).get(0).children.add(new TreeNode(rules.get(k).parts[0],new ArrayList<TreeNode>(),1.0));
			}
		}
	}
	
	/**
	 * sprawdz czy istniej¹ regu³a dla lewego argumentu z poddrzew t2
	 * oraz prawego argumentu z poddrzew t3
	 * jeœli jest stwórz las w strukturze w miejscu T[i][j] i po³¹cz
	 * z odpowiednimi poddrzewami
	 * @param i indeks pierwszego wyrazu
	 * @param j glebokosc drzewa
	 * @param t struktura danych
	 * @param t2 las lewych argumentów
	 * @param t3 las prawyc argumentów
	 */
	@SuppressWarnings("unchecked")
	public void checkForRule(int i, int j, Object[][][] t, Object t2,
			Object t3) {
		/*
		 * iteracja po wszstkich regu³ach
		 */
		for(int k=0; k<rules.size();k++){
			/*
			 * ale tylko 2 argumentowych C -> A, B 
			 */
			if(rules.get(k).parts.length == 2){
				/*
				 * po wszystkich A
				 */
				for(int a=0; a<((List<TreeNode>)t2).size();a++){
					/*
					 * po wszystkich B
					 */
					for(int b=0; b<((List<TreeNode>)t3).size();b++){
						if(rules.get(k).parts[0].equalsIgnoreCase(((List<TreeNode>)t2).get(a).name) &&
								rules.get(k).parts[1].equalsIgnoreCase(((List<TreeNode>)t3).get(b).name)){
							/*
							 * konstruuj odnogê drzewa
							 */
							if(t[i][j][k] == null)	t[i][j][k] = new ArrayList<TreeNode>();
							((List<TreeNode>)t[i][j][k]).add(new TreeNode(rules.get(k).name,new ArrayList<TreeNode>(),rules.get(k).p));
							((List<TreeNode>)t[i][j][k]).get(((List<TreeNode>)t[i][j][k]).size()-1).children.add(
									((List<TreeNode>)t2).get(a));
							((List<TreeNode>)t[i][j][k]).get(((List<TreeNode>)t[i][j][k]).size()-1).children.add(
									((List<TreeNode>)t3).get(b));					
						}
					}
				}
			}
		}
	}
}
