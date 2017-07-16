package BlindGraphTraversalTest;

import BlindGraphTraversal.Edgey;
import BlindGraphTraversal.Nodal;

public class Edge extends Edgey {
	Node startNode;
	Node endNode;
	int Weighting;
	
	public Edge(Node node1, Node node2, int Weight) {
		this.startNode = node1;
		this.endNode = node2;
		this.Weighting = Weight;
	}
	
	@Override
	public int getCost() {
		return this.Weighting;
	}
	@Override
	public Nodal getStartNode() {
		return startNode;
	}
	@Override
	public Nodal getEndNode() {
		return endNode;
	}
}
