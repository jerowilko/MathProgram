package ProofSystem;

import java.util.HashMap;
import java.util.Map;

import BlindGraphTraversal.Edgey;
import Logic.Statement;
import Logic.StatementBit;
import Logic.Variable;

public class ProofSimilarityWeightingAssigner implements WeightingAssigner {

	Statement goalStatement;

	public ProofSimilarityWeightingAssigner(Statement goalStatement) {
		this.goalStatement = goalStatement;
	}
	
	public int assignWeighting(Edgey edge) {
		StatementApplication statementApplication = (StatementApplication) edge;
		
		Statement st = ((ProofStatement) statementApplication.getEndNode()).getStatement();
		
		Map<Variable, Variable> vc = new HashMap<Variable, Variable>();
		
		int errorCount = 0;
		
		for(int i=0;i<Math.min(this.goalStatement.size(),st.size());i++) {
			StatementBit bit1 = this.goalStatement.Sequence.get(i);
			StatementBit bit2 = st.Sequence.get(i);
			
			if(bit1.isVariable()!=bit2.isVariable()) {
				errorCount+=1;
				continue;
			}
			
			if(bit1.isVariable()) {
				Variable v1 = (Variable) bit1;
				Variable v2 = (Variable) bit2;
				
				if(vc.containsKey(v1)) {
					if(vc.get(v1) != v2) {
						errorCount+=1;
						continue;
					}
				} else {
					vc.put(v1, v2);
				}
			} else {
				if(!bit1.literalEquals(bit2)) {
					errorCount+=1;
					continue;
				}
			}
		}
		
		errorCount += Math.abs(this.goalStatement.size() - st.size());
		
		return errorCount;
	}

}
