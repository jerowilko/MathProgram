package BlindGraphTraversal;

import ProofSystem.ProofStatement;

public class AcyclicShortestPathSearcher {

	HaltingCondition haltCondition;
	
	public AcyclicShortestPathSearcher(HaltingCondition halt) {
		this.haltCondition = halt;
	}
	
	public AcyclicPath findPathFrom(Nodal startNode) {
		AcyclicBreadthFirstPathTraverser traverser = new AcyclicBreadthFirstPathTraverser(startNode);
		
		Path nextPath;
		
		int prevCost = 0;
		
		while((nextPath = traverser.nextPath()) != null) {
			if(this.haltCondition.isSatisfiedBy(nextPath)) break;
		
			if(nextPath.getCost()!=prevCost) {
				prevCost = nextPath.getCost();
				System.out.println(prevCost);
			}
			
			ProofStatement.visitedStatements.put(((ProofStatement) nextPath.getLastNode()).getStatement().toString(), true);
		}
		
		return (AcyclicPath) nextPath;
	}

}
