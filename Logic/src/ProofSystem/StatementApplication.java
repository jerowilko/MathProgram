package ProofSystem;

import BlindGraphTraversal.Edgey;
import BlindGraphTraversal.Nodal;
import Logic.Statement;

public class StatementApplication extends Edgey {

	ProofStatement initialStatement;
	Statement appliedStatement;
	int applicationPosition;
	WeightingAssigner weighter;

	public StatementApplication(ProofStatement initialStatement, Statement appliedStatement, WeightingAssigner was, int applicationPosition) {
		this.initialStatement = initialStatement;
		this.appliedStatement = appliedStatement;
		this.applicationPosition = applicationPosition;
		this.weighter = was;
	}

	@Override
	public int getCost() {
		return weighter.assignWeighting(this);
	}

	@Override
	public Nodal getStartNode() {
		return initialStatement;
	}

	@Override
	public Nodal getEndNode() {
		return initialStatement.apply(appliedStatement, applicationPosition);
	}

}
