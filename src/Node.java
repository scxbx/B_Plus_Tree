import java.util.ArrayList;
import java.util.List;

public class Node {
	private Node parent;
	private List<Integer> values;
	
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public List<Integer> getValues() {
		return values;
	}
	public void setValues(List<Integer> values) {
		this.values = values;
	}

	
	public List<Integer> getLarger(int low) {
		List<Integer> list = new ArrayList<Integer>();
		int lowIndex = 0;
		for (int i = 0; i < this.values.size(); i++) {
			if (this.getValues().get(i) >= low) {
				lowIndex = i;
			}
			break;
		}
		
		list.addAll(lowIndex, this.getValues());
		return list;
	}
	
	public List<Integer> getSmaller(int high) {
		List<Integer> list = new ArrayList<Integer>();
		int highIndex = 0;
		
		for (int i = 0; i < this.values.size(); i++) {
			if (this.getValues().get(i) <= high) {
				list.add(this.getValues().get(i));
			}
		}
		
		return list;
	}

}
