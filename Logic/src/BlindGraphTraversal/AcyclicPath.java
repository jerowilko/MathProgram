package BlindGraphTraversal;

import java.util.ArrayList;

public class AcyclicPath extends Path {

	public AcyclicPath() {
		this.nodeList = new ArrayList<Nodal>();
		this.edgeList = new ArrayList<Edgey>();
	}
	
	public AcyclicPath(Nodal startNode) {
		super(startNode);
	}
	
	public AcyclicPath(AcyclicPath path) {
		this.nodeList = new ArrayList<Nodal>(path.nodeList);
		this.edgeList = new ArrayList<Edgey>(path.edgeList);
	}

	@Override
	public ArrayList<Edgey> getEdges() {
		ArrayList<Edgey> availableEdges = this.getLastNode().getEdges();

		ArrayList<Edgey> changes = new ArrayList<Edgey>();

		for (int i = 0; i < availableEdges.size(); i++) {
			if(!this.nodeList.contains(availableEdges.get(i).getEndNode())) {
				changes.add(new PathChange(this, availableEdges.get(i)));
			}
		}

		return changes;
	}

	public Path traverse(Edgey nextEdge) {
		Nodal nextNode = nextEdge.getEndNode();
		AcyclicPath nextPath = new AcyclicPath(this);
		
		nextPath.nodeList.add(nextNode);
		nextPath.edgeList.add(nextEdge);
		
		return nextPath;
	}
	
	public String toString() {
		String str = super.toString();
		
		return "Acyclic "+str;
	}
	
}
