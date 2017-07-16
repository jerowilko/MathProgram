package ProofSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BlindGraphTraversal.Edgey;
import BlindGraphTraversal.Nodal;
import Logic.AxiomSystem;
import Logic.Statement;

public class ProofStatement implements Nodal {

	public static Map<String, Boolean> visitedStatements = new HashMap<String, Boolean>();
	
	private AxiomSystem as;
	private Statement statement;

	public ProofStatement(AxiomSystem as, Statement statement) {
		this.as = as;
		this.statement = statement;
	}

	@Override
	public ArrayList<Edgey> getEdges() {
		ArrayList<Statement> applyableStatements = this.as.getAllApplyableStatements();
		ArrayList<Edgey> statementChanges = new ArrayList<Edgey>();

		for (int i = 0; i < applyableStatements.size(); i++) {
			Statement st = applyableStatements.get(i);
			try {
				ArrayList<Integer> positions = st.getApplicableIndices(this.statement);

				for (int n = 0; n < positions.size(); n++) {
					StatementApplication stmt = new StatementApplication(this, st, positions.get(n));
					Statement st2 = ((ProofStatement) stmt.getEndNode()).getStatement();
					
					if(!(ProofStatement.visitedStatements.containsKey(st2.toString()))) {
						statementChanges.add(stmt);
						ProofStatement.visitedStatements.put(st2.toString(), true);
					}
					else System.out.println("Found duplicate");
				}
			} catch(Exception e) {
				if(!AxiomSystem.suppressErrors) System.out.println("Error occured when getting indices for applying " + st + " to " + this );
			}
		}

		return statementChanges;
	}

	public Nodal apply(Statement appliedStatement, int applicationPosition) {
		Statement resultingStatement = appliedStatement.applyTo(this.statement, applicationPosition);

		ProofStatement resultingProof = new ProofStatement(as, resultingStatement);

		return resultingProof;
	}

	public String toString() {
		return "Proof Statement: " + this.statement;
	}

	public Statement getStatement() {
		return this.statement;
	}
	
}
