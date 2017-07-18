package BlindGraphTraversal;

import java.util.PriorityQueue;

public class BreadthFirstPathTraverser {
	protected PriorityQueue<Edgey> edges = new PriorityQueue<Edgey>();
	protected Nodal startNode;
	protected Path startPath;
	protected boolean hasStarted;
	
	public BreadthFirstPathTraverser(Nodal startNode) {
		this.startNode = startNode;
		
		this.startPath = new Path(this.startNode);
		
		this.edges.addAll(this.startPath.getEdges());
	}
	
	public Path nextPath() {
		if(!hasStarted) {
			this.hasStarted = true;
			return startPath;
		}
		
		if(edges.size()==0) return null;
		
		Edgey nextEdge = edges.poll();
		Path nextPath = (Path) nextEdge.getEndNode();
		
		this.edges.addAll(nextPath.getEdges());
		
		return nextPath;
	}
	
	public int getNumEdges() {
		return this.edges.size();
	}
	
	public PriorityQueue<Edgey> getEdges() {
		return this.edges;
	}
}
