import java.util.Collections;
import java.util.List;

public class IndexNode extends Node{
	private List<Node> children;

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
		return children.get(loc);
	}
	
}
