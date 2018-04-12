import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tree {
	private Node root;
	static final int fan_out = 5;
	private int nodeNum;
	private int dataNum;
	private int indexNum;
	private int indexSpaceNum;
	
	public Tree() {
		nodeNum = 0;
		dataNum = 0;
		indexNum = 0;
		indexSpaceNum = 0;
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
					Node oldFlag = null;
					do {
						if (flag == root) {
//							p("214");
//							p(flag);
//							p(root);
							
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
								p(i);
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
							p("267");
							p(flag);
							p(root);
							
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
							

							//for parent, remove original children and set new children
							posi = ((IndexNode) parent).getChildren().indexOf(flag);
							((IndexNode)parent).getChildren().remove(posi);
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
							
//							p("317");
//							p("flag:" + flag);
//							p("root:" + root);
//							p("parent: " + parent);
//							p(flag.getParent());
						}
						
						
//						p("328");
//						p("flag" + flag);
//						p("flag.getParent()" + flag.getParent());
						
						oldFlag = flag;
						flag = flag.getParent();
						
//						p("332");
//						p(flag);
						
						
//						p("parent: " + parent);
//						p(this);
					} while(isOverFlow(flag));
					
					
					
					
					
					
					
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
	
	private boolean isOverFlow(Node aNode) {
		if(aNode == null)
			return false;
		if(aNode.getValues() == null)
			return false;
		return aNode.getValues().size() > fan_out-1;
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
		tree.print();
		tree.insert(12);
		tree.print();
		tree.insert(16);
		tree.print(); 
		tree.insert(6);
		tree.print(); 
		tree.insert(18);
		tree.print(); 
		tree.insert(8);
		tree.print();
		tree.insert(9);
		tree.print();
		tree.insert(5);
		tree.print(); 
		tree.insert(10);
		tree.print(); 
		tree.insert(11);
		tree.print(); 
		tree.insert(19);
		tree.print();
		tree.insert(20);
		tree.print(); 
		tree.insert(21);
		tree.print(); 
		tree.insert(22);
		tree.print(); 
		tree.insert(2);
		tree.print();
//		tree.insert(23);
//		tree.insert(24);
//		tree.insert(25);
//		tree.insert(26);
//		tree.insert(27);
//		tree.insert(28);
//		tree.insert(29);
//		tree.insert(30);
//		tree.insert(31);
//		tree.insert(32);
		
		int ceiling = 46;
		for (int i = 23; i <= ceiling; i++) {
			p(i);
			tree.insert(i);
			tree.print();
		}
		
		
//		Node lowNode = tree.searchNode(10);
//		p(lowNode);
//		Node highNode = tree.searchNode(15);
//		p(highNode);
		
//		tree.printInorder(tree.root);
//		p(tree.nodeNum(tree.root));
		
		
		
//		tree.dataNum = 0;
//		tree.indexNum = 0;
//		tree.nodeNum = 0;
//		
//		tree.traversal(tree.root);
//		
//		p("node: " + tree.nodeNum);
//		p("data: " + tree.dataNum);
//		p("index: " +tree.indexNum);
		
		p("height: " + tree.getHeight());
		
		p("node: " + tree.getNodeNum());
		p("data " + tree.getDataNum());
		p("index: " + tree.getIndexNum());
		p("fill factor: " + tree.getFillFactor());
	}

	
//	public void printInorder(Node cNode) {
//		if (cNode == null) {
//			return;
//		}
//		System.out.println(cNode.getValues());
//		if (cNode.getClass().equals((new LeafNode()).getClass())) {
//			
//			return;
//		}
//		//System.out.println(cNode.getValues());
//		for (int i = 0; i < ((IndexNode)cNode).getChildren().size(); i++) {
//			
//			printInorder(((IndexNode)cNode).getChildren().get(i));
//		}
//		
//		
//	}
//	
//	public int nodeNum(Node cNode) {
//		
//		if (cNode == null) {
//			return nodeNum;
//		}
//		nodeNum++;
//		if (cNode.getClass().equals((new LeafNode()).getClass())) {
//			return nodeNum;
//		}
//		
//		for (int i = 0; i < ((IndexNode)cNode).getChildren().size(); i++) {
//			nodeNum(((IndexNode)cNode).getChildren().get(i));
//		}
//		return nodeNum;
//	}
	
	public void traversal(Node node) {
		if (node == null) {
			return;
		}
		nodeNum++;
		if (node.getClass().equals((new LeafNode()).getClass())) {
			dataNum += node.getValues().size();
			return;
		}
		indexNum += ((IndexNode)node).getValues().size();
//		indexSpaceNum += ((IndexNode)node).getValues().size();
		for (int i = 0; i < ((IndexNode)node).getChildren().size(); i++) {
			
			traversal(((IndexNode)node).getChildren().get(i));
		}
	}
	
	public int getIndexNum() {
		this.indexNum = 0;
		this.traversal(this.root);
		return this.indexNum;
	}
	
	public int getNodeNum() {
		this.nodeNum = 0;
		this.traversal(this.root);
		return this.nodeNum;
	}
	
	public int getDataNum() {
		this.dataNum = 0;
		this.traversal(this.root);
		return this.dataNum;
	}
	
	public int getIndexSpaceNum() {
		this.indexSpaceNum = 0;
		this.traversal(root);
		return this.indexSpaceNum;
	}
	
	public int getHeight() {
		Node node = root;
		int height = 0;
		while (node != null && (!node.getClass().equals((new LeafNode()).getClass()))) {
			height++;
			node = ((IndexNode)node).getChildren().get(0);
		}
		return height;
	}
	
	public double getFillFactor() {
		return ((double)(this.getIndexNum() + this.getDataNum())) / ((double)this.getNodeNum() * 4);
	}
}
