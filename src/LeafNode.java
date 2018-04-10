
public class LeafNode extends Node{
	private LeafNode lSibling;
	private LeafNode rSibling;
	
	public LeafNode() {}

	public LeafNode getlSibling() {
		return lSibling;
	}

	public void setlSibling(LeafNode lSibling) {
		this.lSibling = lSibling;
	}

	public LeafNode getrSibling() {
		return rSibling;
	}

	public void setrSibling(LeafNode rSibling) {
		this.rSibling = rSibling;
	}
	
	
}
