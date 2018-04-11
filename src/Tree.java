import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tree {
	private Node root;
	static final int fan_out = 5;
	private int nodeNum;
	
	public Tree() {
		nodeNum = 0;
	}
	
	public Node searchNode(int value) {                           // seek leafNode from root
		Node cNode = root;
		if (cNode.getClass().equals((new LeafNode()).getClass())) {
			return cNode;
		} 
		while(!cNode.getClass().equals((new LeafNode()).getClass())) {
			cNode = ((IndexNode)cNode).getChild(value);
		}
		return cNode;
		
	}
	
	public Node searchNodeParent(int value) {                           // seek leafNode from root
		Node cNode = root;
		Node parent = null;
		if (cNode.getClass().equals((new LeafNode()).getClass())) {
			return cNode;
		} 
		while(!cNode.getClass().equals((new LeafNode()).getClass())) {
			parent = cNode;
			cNode = ((IndexNode)cNode).getChild(value);
		}
		return parent;
		
	}
	
//	public List<Integer> search(int low, int high) {               // 
//		Node lowNode = (LeafNode)searchNode(low);
//		Node highNode = (LeafNode)searchNode(high);
//		Node cNode = lowNode;
//		List<Integer> list = new ArrayList<Integer>();
//		
//		//list.addAll(cNode.getLarger(low));
//		if (cNode.equals(highNode)) {
//			for (int i = 0; i < list.size(); i++) {
//				if (c)
//			}
//			return list;
//		}
//		
//		while (!cNode.equals(highNode)) {
//			cNode = ((LeafNode)cNode).getrSibling();
//			list.addAll(cNode.getValues());
//		}
//		
//		list.addAll(cNode.getSmaller(high));
//			
//		return list;
//		
//	}
	
	public void insert(int value) {
		Node cNode = root;
		if (cNode.getClass().equals((new LeafNode()).getClass())) {  // check whether root is leafNode
			
			if(!isFull(cNode))                                      // when root is not full
				cNode.getValues().add(((LeafNode)cNode).searchPosition(value), value);
			else {                                                  // when root is full
				Node parent, leftChild, rightChild;
				parent = new IndexNode();
				leftChild = new LeafNode();
				rightChild = new LeafNode();
				parent.getValues().add(cNode.getValues().get(fan_out/2));   // copy up
				((IndexNode)parent).getChildren().add(leftChild);
				((IndexNode)parent).getChildren().add(rightChild);
				
				ArrayList<Integer> newList = new ArrayList<Integer>();
				newList.addAll(cNode.getValues());
				int loc = Collections.binarySearch(newList, value);
				if (loc < 0) {
					loc = -(loc + 1);
				}
				newList.add(loc, value);
				
				leftChild.setParent(parent);
				rightChild.setParent(parent);
				for(int i=0; i<fan_out/2; i++) {
					leftChild.getValues().add(newList.get(i));
				}
				
				for(int i=fan_out/2; i<fan_out; i++) {
					rightChild.getValues().add(newList.get(i));
				}
				
				root = parent;
				
			}
			
		}else {                                                  // when root is indexNode
			Node leaf = this.searchNode(value);
			if(!isFull(leaf))                                   // leaf is not full
				leaf.getValues().add(((LeafNode)leaf).searchPosition(value), value);
			else {                                              // leaf is full
				if(leaf.getParent().getValues().size()<=fan_out-2) {   //no need push up
					Node parent, leftChild, rightChild;
					parent = leaf.getParent();
					
					leftChild = new LeafNode();
					rightChild = new LeafNode();
					
					leaf.getValues().add(((LeafNode)leaf).searchPosition(value), value);     //add new value to corresponding leaf
					
					int loc = Collections.binarySearch(parent.getValues(), leaf.getValues().get(fan_out/2));  //important
					if (loc < 0) {
						loc = -(loc + 1);
					}
					parent.getValues().add(loc, leaf.getValues().get(fan_out/2));   // copy up
					int posi = ((IndexNode) parent).getChildren().indexOf(leaf);
					
//					p("leaf" + leaf);
//					p("parent" + leaf.getParent());
//					p("posi" + posi);
					
					((IndexNode)parent).getChildren().add(posi, leftChild);   // add it at leaf's position
					((IndexNode)parent).getChildren().add(posi+1, rightChild);  // add it at leaf's position+1
					
//					p("leftChild: " + leftChild);
//					p("rightChild: " + rightChild);
//					p("parent" + parent);
					
					for(int i=0; i< fan_out/2; i++) {
						leftChild.getValues().add(leaf.getValues().get(i));
					}
					
					for(int i=fan_out/2; i< fan_out; i++) {
						rightChild.getValues().add(leaf.getValues().get(i));
					}
					//p("1.parent" + parent);
					((IndexNode)parent).getChildren().remove(posi+2);
					//p("2.parent" + parent);
					
					
					
					leftChild.setParent(parent);
					rightChild.setParent(parent);
					
					//p("143" + parent);
					//p("144" + parent.getParent());
					
					//p("146 " + ((IndexNode) parent.getParent()));
					//p(((IndexNode) parent.getParent()).getChildren());
					
//					if (parent.getParent() != null) {
//						posi = ((IndexNode) parent.getParent()).getChildren().indexOf(parent);
//						((IndexNode)parent.getParent()).getChildren().add(posi, parent);
//						((IndexNode)parent.getParent()).getChildren().remove(posi + 1);
//						p("153 " + posi);
//						p("154 " + parent);
//						//p(this.toString());
//					}
//					posi = ((IndexNode) parent.getParent()).getChildren().indexOf(parent);
//					((IndexNode)parent.getParent()).getChildren().add(posi, parent);
//					((IndexNode)parent.getParent()).getChildren().remove(posi + 1);
					
//					p("leftChild: " + leftChild);
//					p("rightChild: " + rightChild);
//					p("parent" + parent);
//					p(this.root);
					
				}else {                                             // need push up -- leaf's parent is full
					Node parent, leftChild, rightChild;
					parent = leaf.getParent();
					
					leftChild = new LeafNode();
					rightChild = new LeafNode();
					
					leaf.getValues().add(((LeafNode)leaf).searchPosition(value), value);     //add new value to corresponding leaf
					
					int loc = Collections.binarySearch(parent.getValues(), leaf.getValues().get(fan_out/2));  // find location of copied up value important
					if (loc < 0) {
						loc = -(loc + 1);
					}
					parent.getValues().add(loc, leaf.getValues().get(fan_out/2));   // copy up -- overflow
					int posi = ((IndexNode) parent).getChildren().indexOf(leaf);
					((IndexNode)parent).getChildren().add(posi, leftChild);   // add it at leaf's position
					((IndexNode)parent).getChildren().add(posi+1, rightChild);  // add it at leaf's position+1
					
					for(int i=0; i< fan_out/2; i++) {
						leftChild.getValues().add(leaf.getValues().get(i));
					}
					
					for(int i=fan_out/2; i< fan_out; i++) {
						rightChild.getValues().add(leaf.getValues().get(i));
					}
					
					((IndexNode)parent).getChildren().remove(posi+2);
					
					leftChild.setParent(parent);
					rightChild.setParent(parent);
					
					Node flag = leaf.getParent();
					
					do {
						if (flag == root) {
							/*------------------------------------------------------------------------------------*/
							/*	push up 	no parent	*/
							/*------------------------------------------------------------------------------------*/
							leftChild = new IndexNode();
							rightChild = new IndexNode();
							
							
							//for left and right child, set children
							for (int i = 0; i < fan_out / 2 + 1; i++) {
								((IndexNode)leftChild).getChildren().add(((IndexNode)root).getChildren().get(i));
								((IndexNode)root).getChildren().get(i).setParent(leftChild);
							}
							for (int i = fan_out / 2 + 1; i < fan_out + 1; i++) {
								((IndexNode)rightChild).getChildren().add(((IndexNode)root).getChildren().get(i));
								((IndexNode)root).getChildren().get(i).setParent(rightChild);
							}
							
							
							//for left and right child, set values
							for(int i=0; i< fan_out/2; i++) {
								leftChild.getValues().add(root.getValues().get(i));
							}
							
							for(int i=fan_out/2 + 1; i< fan_out; i++) {
								rightChild.getValues().add(root.getValues().get(i));
							}
							
							
							//for left and right child, set parent
							leftChild.setParent(root);
							rightChild.setParent(root);
							
							//for parent(root), add children
							List<Node> clist = new ArrayList<Node>();
							clist.add(leftChild);
							clist.add(rightChild);
							
							((IndexNode)root).setChildren(clist);
							
							
							//for parent(root), set values
							ArrayList<Integer> rList = new ArrayList<Integer>();
							rList.add(root.getValues().get(fan_out / 2));
							
							
							root.setValues(rList);
							/*------------------------------------------------------------------------------------*/
						} else {
							parent = flag.getParent();
							rightChild = new IndexNode();
							leftChild = new IndexNode();
							
							//for rightChild and leftChild, set children 	for children of left and right child, set parent(left and right child)
							for (int i = 0; i < fan_out / 2 + 1; i++) {
								
//								p(((IndexNode)leftChild).getChildren().size());
//								p(((IndexNode)parent).getChildren().size());
//								p("flag: " + ((IndexNode)flag).getChildren().size());
								((IndexNode)leftChild).getChildren().add(((IndexNode)flag).getChildren().get(i));
								((IndexNode)flag).getChildren().get(i).setParent(leftChild);
							}
							for (int i = fan_out / 2 + 1; i < fan_out + 1; i++) {
								((IndexNode)rightChild).getChildren().add(((IndexNode)flag).getChildren().get(i));
								((IndexNode)flag).getChildren().get(i).setParent(rightChild);
							}
							
							
							//for parent, set values
							int p_loc = Collections.binarySearch(parent.getValues(), flag.getValues().get(fan_out/2));  // find location of pushed up value
							if (p_loc < 0) {
								p_loc = -(p_loc + 1);
							}
							parent.getValues().add(p_loc, flag.getValues().get(fan_out/2));  // push up
							
							
							//for parent, set children
							posi = ((IndexNode) parent).getChildren().indexOf(flag);
							((IndexNode)parent).getChildren().add(posi, leftChild);   // add it at leaf's position
							((IndexNode)parent).getChildren().add(posi + 1, rightChild);  // add it at leaf's position+1
							
							
							//for rightChild and leftChild, set parent
							rightChild.setParent(parent);
							leftChild.setParent(parent);
							
							
							//for left and right child, set values
							for(int i=0; i< fan_out/2; i++) {
								leftChild.getValues().add(flag.getValues().get(i));
							}
							
							for(int i=fan_out/2 + 1; i< fan_out; i++) {
								rightChild.getValues().add(flag.getValues().get(i));
							}
						}
						
						
						
						
						flag = flag.getParent();
					} while(isFull(flag));
					
					
					
					
					
					
					
					//=================================================================================
//					Node flag2 = leaf.getParent();
//					Node record = new IndexNode();
//					Node parent2, leftChild2, rightChild2;
//					int count = 3;
//					do {
//						if(flag2 == null)
//							flag2 = root;
//						parent2 = flag2.getParent();                  // may be null
//						leftChild2 = new IndexNode();
//						rightChild2 = new IndexNode();
//						if(parent2==null){
//							parent2 = new IndexNode();
//							
//						}
//						int p_loc = Collections.binarySearch(parent2.getValues(), flag2.getValues().get(fan_out/2));  // find location of pushed up value
//						if (p_loc < 0) {
//							p_loc = -(p_loc + 1);
//						}
//						parent2.getValues().add(p_loc, flag2.getValues().get(fan_out/2));  // push up
//						int posi2;
//						
//						if(((IndexNode)parent2).getChildren().size()==0) { 
//							posi2 = 0;
//						}
//						else {
//							posi2 = ((IndexNode) parent2).getChildren().indexOf(flag2);
//							System.out.println(posi2);
//						}
//						((IndexNode)parent2).getChildren().add(posi2, leftChild2);   // add it at leaf's position
//						((IndexNode)parent2).getChildren().add(posi2+1, rightChild2);  // add it at leaf's position+1
//						
//						for(int i=0; i< fan_out/2; i++) {
//							leftChild2.getValues().add(flag2.getValues().get(i));
//						}
//						
//						for(int i=fan_out/2+1; i< fan_out; i++) {
//							rightChild2.getValues().add(flag2.getValues().get(i));
//						}
//						//((IndexNode)parent2).getChildren().remove(posi2+2);
//						
//						leftChild2.setParent(parent2);
//						rightChild2.setParent(parent2);
//						
//						record = flag2;
//						flag2 = flag2.getParent();  // final step, flag2.getParent() is null
//						
//					
//					}while(isFull(flag2));
					
//					p(record.toString());
//					Node parent3 = new IndexNode();
//					Node leftChild3 = new IndexNode();
//					Node rightChild3 = new IndexNode();
//					 
//					
//					int p_loc = Collections.binarySearch(parent3.getValues(), record.getValues().get(fan_out/2));  // find location of pushed up value
//					
//					if (p_loc < 0) {
//						p_loc = -(p_loc + 1);
//					}
//					
//					parent3.getValues().add(p_loc, record.getValues().get(fan_out/2));  // push up
//					int posi2;
//					
//					if(((IndexNode)parent3).getChildren().size()==0) { 
//						posi2 = 0;
//					}
//					else {
//						posi2 = ((IndexNode) parent3).getChildren().indexOf(record);
//						System.out.println(posi2);
//					}
//					((IndexNode)parent3).getChildren().add(posi2, leftChild3);   // add it at leaf's position
//					((IndexNode)parent3).getChildren().add(posi2+1, rightChild3);  // add it at leaf's position+1
//					
//					for(int i=0; i< fan_out/2; i++) {
//						leftChild3.getValues().add(record.getValues().get(i));
//					}
//					
//					for(int i=fan_out/2+1; i< fan_out; i++) {
//						rightChild3.getValues().add(record.getValues().get(i));
//					}
//					//((IndexNode)parent2).getChildren().remove(posi2+2);
//					
//					leftChild3.setParent(parent3);
//					rightChild3.setParent(parent3);
//					root = parent3;
//					List<Node> li = new ArrayList<Node>();
//					li.add(leftChild3);
//					li.add(rightChild3);
//					((IndexNode)root).setChildren(li);
					
					
				}
				
				
			}
			
		}
		
		
		
		
		
	}
	
	public static <T> void p(T a) {
		System.out.println(a);
	}
	private boolean isFull(Node aNode) {
		if(aNode == null)
			return false;
		if(aNode.getValues() == null)
			return false;
		return aNode.getValues().size() >= fan_out-1;
	}
	
	
	public void print() {
		System.out.println(this.toString());
	}
	
	public String toString() {
		return "tree [root=" + root +"]";
	}
	
	public static void main(String[] args) {
		Tree tree = new Tree();
		tree.root = new LeafNode();
		
		tree.insert(4);
		 
		tree.insert(12);
		 
		tree.insert(16);
		 
		tree.insert(6);
		 
		tree.insert(18);
		 
		tree.insert(8);
		 
		tree.insert(9);
		 
		tree.insert(5);
		 
		tree.insert(10);
		 
		tree.insert(11);
		 
		tree.insert(19);
		 
		tree.insert(20);
		 
		tree.insert(21);
		 
		tree.insert(22);
		 
		tree.insert(2);
		tree.insert(23);
		tree.insert(24);
		tree.insert(25);
		tree.insert(26);
		tree.insert(27);
		//tree.insert(28);
		//tree.insert(29);
		//tree.insert(30);
		//tree.insert(31);
		tree.print();
		
		Node lowNode = tree.searchNode(10);
		p(lowNode);
		Node highNode = tree.searchNode(15);
		p(highNode);
		
		tree.printInorder(tree.root);
		p(tree.nodeNum(tree.root));
	}

	
	public void printInorder(Node cNode) {
		if (cNode == null) {
			return;
		}
		System.out.println(cNode.getValues());
		if (cNode.getClass().equals((new LeafNode()).getClass())) {
			
			return;
		}
		//System.out.println(cNode.getValues());
		for (int i = 0; i < ((IndexNode)cNode).getChildren().size(); i++) {
			
			printInorder(((IndexNode)cNode).getChildren().get(i));
		}
		
		
	}
	
	public int nodeNum(Node cNode) {
		
		if (cNode == null) {
			return nodeNum;
		}
		nodeNum++;
		if (cNode.getClass().equals((new LeafNode()).getClass())) {
			return nodeNum;
		}
		
		for (int i = 0; i < ((IndexNode)cNode).getChildren().size(); i++) {
			nodeNum(((IndexNode)cNode).getChildren().get(i));
		}
		return nodeNum;
	}
	
	
	
}
