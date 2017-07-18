package BlindGraphTraversal;

import java.util.ArrayList;

public class Path implements Nodal {
	protected ArrayList<Nodal> nodeList;
	protected ArrayList<Edgey> edgeList;
	
	public Path() {
		this.nodeList = new ArrayList<Nodal>();
		this.edgeList = new ArrayList<Edgey>();
	}
	
	public Path(Nodal startNode) {
		this();
		
		this.nodeList.add(startNode);
	}
	
	public Path(Path path) {
		this.nodeList = new ArrayList<Nodal>(path.nodeList);
		this.edgeList = new ArrayList<Edgey>(path.edgeList);
	}

	public Nodal getLastNode() {
		if(this.nodeList.size()==0) return null;
		
		return this.nodeList.get(this.nodeList.size()-1);
	}
	
	public Nodal getFirstNode() {
		if(this.nodeList.size()==0) return null;
		
		return this.nodeList.get(0);
	}
	
	@Override
	public ArrayList<Edgey> getEdges() {
		if(this.nodeList.isEmpty()) return new ArrayList<Edgey>();
		
		ArrayList<Edgey> availableEdges = this.getLastNode().getEdges();
	
		return PathChange.getPathChangesFromEdges(this, availableEdges);
	}
	
	public int getCost() {
		int cost = 0;
		
		for(int i=0;i<this.edgeList.size();i++) {
			cost += this.edgeList.get(i).getCost();
		}
		
		return cost;
	}

	public Path traverse(Edgey nextEdge) {
		Nodal nextNode = nextEdge.getEndNode();
		Path nextPath = new Path(this);
		
		nextPath.nodeList.add(nextNode);
		nextPath.edgeList.add(nextEdge);
		
		return nextPath;
	}
	
	public String toString() {
		String str = "Path: ";
		
		for(int i=0;i<this.nodeList.size();i++) {
			if(i!=0) str += ", ";
			str += this.nodeList.get(i);
		}
		str += ". Total cost: " + this.getCost() + ".";
		
		return str;
	}
	
	public ArrayList<Nodal> getNodeList() {
		return this.nodeList;
	}
	
	public ArrayList<Edgey> getEdgeList() {
		return this.edgeList;
	}

}
