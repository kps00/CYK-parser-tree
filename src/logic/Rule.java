package logic;

/**
 * regu³a postaci C -> A, B
 * o prawdopodobieñstwie p
 * suma prawdopodobieñstw szszystkich regu³ C = 1.0
 * @author kps
 *
 */
public class Rule {
	
	public Rule(String a1, String[] a2) {
		name   = a1;
		parts = a2;
		p = 0.0;
	}
	
	public Rule(String a1, String[] a2, double p) {
		name   = a1;
		parts  = a2;
		this.p = p;
	}

	public String name;
	public String[] parts;
	public double p;
}
