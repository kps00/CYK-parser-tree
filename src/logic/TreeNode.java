package logic;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    final String name;
    final double p;
    final List<TreeNode> children;

    public TreeNode(String name, List<TreeNode> children) {
        this.name = name;
        this.children = children;
        this.p = 0.0;
    }

    public TreeNode(String name, List<TreeNode> children, double p) {
        this.name = name;
        this.children = children;
        this.p = p;
    }
    
    public void print() {
    	System.out.println("Prawdopodobieństwo drzewa: " + String.format("%1$,.6f",value()));
        print("", true);
    }
    
    public static List<TreeNode> forrest(String name, List<TreeNode> leftChildren, List<TreeNode> rightChildren, double p){
    	List<TreeNode> result = new ArrayList<TreeNode>();
    	for(int i=0;i<leftChildren.size();i++){
        	for(int j=0;j<leftChildren.size();j++){
        		List<TreeNode> temp = new ArrayList<TreeNode>();
        		temp.add(leftChildren.get(i));
        		temp.add(rightChildren.get(j));
        		result.add(new TreeNode(name, temp ,p ));
        	}
    	}
    	return result;
    }

    private void print(String prefix, boolean isTail) {
        System.out.print(prefix + (isTail ? "\\__ " : "|__ ") + name );
        if(children.size()>0){
        	System.out.println(" [" + p +"]");
        }
        else {System.out.println();}
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).print(prefix + (isTail ? "    " : "|   "), false);
        }
        if (children.size() >= 1) {
            children.get(children.size() - 1).print(prefix + (isTail ?"    " : "|   "), true);
        }
    }
    
    private double value(){
    	double result = p;
        for (int i = 0; i < children.size(); i++) {
            result *= children.get(i).value();
        }
        return result;
    }
}
