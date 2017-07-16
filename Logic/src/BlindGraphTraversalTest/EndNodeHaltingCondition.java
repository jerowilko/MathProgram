package BlindGraphTraversalTest;

import BlindGraphTraversal.HaltingCondition;
import BlindGraphTraversal.Path;

public class EndNodeHaltingCondition extends HaltingCondition {

	Node endNode;
	
	public EndNodeHaltingCondition(Node endNode) {
		this.endNode = endNode;
	}
	
	@Override
	public boolean isSatisfiedBy(Path path) {
		Node lastNode = (Node) path.getLastNode();
		
		return lastNode.equals(endNode);
	}

}
