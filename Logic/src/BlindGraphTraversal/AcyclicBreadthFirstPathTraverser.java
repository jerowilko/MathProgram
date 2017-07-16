package BlindGraphTraversal;

public class AcyclicBreadthFirstPathTraverser extends BreadthFirstPathTraverser {

	public AcyclicBreadthFirstPathTraverser(Nodal startNode) {
		super(startNode);
		
		this.startPath = new AcyclicPath(startNode);
		
		this.edges.clear();
		
		this.edges.addAll(this.startPath.getEdges());
	}

}
