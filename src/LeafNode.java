import java.util.Collections;
import java.util.List;

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
	
	public int searchPosition(int value) {
		if(this.getValues()==null)
			return 0;
		int loc = Collections.binarySearch(this.getValues(), value);
		if (loc < 0) {
			loc = -(loc + 1);
		}
		return loc;
	}
	
	public String toString() {
		//return "Node [parent="+getParent()+", value="+getValues()+", children"+getChildren()+"]";
		return "LeafNode [value="+getValues()+ "]";
	}
	private List<Integer> getParentValues() {
		if (super.getParent() == null)
			return null;
		return super.getParent().getValues();
	}
	
}
