package BlindGraphTraversal;

import java.util.ArrayList;

import ProofSystem.ProofStatement;

public class PathChange extends Edgey {

	Edgey nextEdge;
	Path curPath;
	
	public PathChange(Path p, Edgey edge) {
		this.curPath = p;
		this.nextEdge = edge;
	}
	
	@Override
	public int getCost() {
		return curPath.getCost() + nextEdge.getCost();
	}

	@Override
	public Nodal getStartNode() {
		return curPath;
	}

	@Override
	public Nodal getEndNode() {
		return curPath.traverse(nextEdge);
	}
	
	public static ArrayList<Edgey> getPathChangesFromEdges(Path p, ArrayList<Edgey> edges) {
		ArrayList<Edgey> changes = new ArrayList<Edgey>(edges.size());
		
		for(int i=0; i<edges.size(); i++) {
			changes.add(new PathChange(p, edges.get(i)));
		}
		
		return changes;
	}
}
