package BlindGraphTraversalTest;

import java.util.ArrayList;

import BlindGraphTraversal.Edgey;
import BlindGraphTraversal.Nodal;

public class Node implements Nodal {
	String name;
	ArrayList<Edge> edges;
	
	public Node(String name) {
		this.name = name;
		this.edges = new ArrayList<Edge>();
	}
	
	public void addNeighbour(Node node, int Weight) {
		this.edges.add(new Edge(this, node, Weight));
	}
	
	@Override
	public ArrayList<Edgey> getEdges() {
		return new ArrayList<Edgey>(edges);
	}
	
	public String toString() {
		return name;
	}
}
