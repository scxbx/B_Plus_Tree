import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IndexNode extends Node{
	private List<Node> children;

	public IndexNode() {
		children = new ArrayList<Node>();
	}
	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	public Node getChild(int value) { 
		int loc = Collections.binarySearch(this.getValues(), value);
		if (loc < 0) {
			loc = -(loc + 1);
		}
//		Tree.p("loc " + loc);
//		Tree.p("children " + children);
//		Tree.p("values " + this.getValues());
//		Tree.p("value " + value);
		return children.get(loc);
	}
	
	public String toString() {
		//return "Node [parent="+getParent()+", value="+getValues()+", children"+getChildren()+"]";
		return "Node [value="+getValues()+", children="+getChildren() + "]";
	}
	private List<Integer> getParentValues() {
		if (super.getParent() == null)
			return null;
		return super.getParent().getValues();
	}
}
