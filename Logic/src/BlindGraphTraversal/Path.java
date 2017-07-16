package BlindGraphTraversal;

import java.util.ArrayList;

public class Path implements Nodal {
	private ArrayList<Nodal> nodeList;
	private ArrayList<Edgey> edgeList;
	
	public Path() {
		this.nodeList = new ArrayList<Nodal>();
		this.edgeList = new ArrayList<Edgey>();
	}
	
	public Path(Path path) {
		this.nodeList = new ArrayList<Nodal>(path.nodeList);
		this.edgeList = new ArrayList<Edgey>(path.edgeList);
	}

	public Nodal getLastNode() {
		return this.nodeList.get(this.nodeList.size());
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

}
