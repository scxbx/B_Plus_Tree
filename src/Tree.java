import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tree {
	private Node root;
	
	
	
	public Node searchNode(int value) {
		Node cNode = root;
		if (cNode.getClass().equals((new LeafNode()).getClass())) {
			return cNode;
		} else {
			cNode = ((IndexNode)cNode).getChild(value);
			
		}
		return cNode;
		
	}
	
	public List<Integer> search(int low, int high) {
		Node lowNode = (LeafNode)searchNode(low);
		Node highNode = (LeafNode)searchNode(high);
		Node cNode = lowNode;
		List<Integer> list = new ArrayList<Integer>();
		
		list.addAll(cNode.getLarger(low));
		if (cNode.equals(highNode)) {
			return list;
		}
		
		while (!cNode.equals(highNode)) {
			cNode = ((LeafNode)cNode).getrSibling();
			list.addAll(cNode.getValues());
		}
		
		list.addAll(cNode.getSmaller(high));
			
		return null;
		
	}
	
	public void insert(int value) {
		
	}
	
//	public static void main(String[] args) {
//		Tree t = new Tree();
//		t.root = new LeafNode();
//		System.out.println(t.root.getClass().equals((new LeafNode()).getClass()));
//		
//		List<Integer> list = new ArrayList<Integer>();
//		list.add(10);
//		list.add(20);
//		list.add(30);
//		list.add(40);
//		list.add(50);
//		
//		int loc = Collections.binarySearch(list, 0);
//		System.out.println(loc);
//	}
}
