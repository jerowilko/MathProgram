package ProofSystem;

import BlindGraphTraversal.Edgey;
import BlindGraphTraversal.Nodal;
import Logic.Statement;

public class StatementApplication extends Edgey {

	ProofStatement initialStatement;
	Statement appliedStatement;
	int applicationPosition;

	public StatementApplication(ProofStatement initialStatement, Statement appliedStatement, int applicationPosition) {
		this.initialStatement = initialStatement;
		this.appliedStatement = appliedStatement;
		this.applicationPosition = applicationPosition;
	}

	@Override
	public int getCost() {
		return 1;
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
