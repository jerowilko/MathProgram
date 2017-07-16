package ProofSystem;

import java.util.HashMap;
import java.util.Map;

import BlindGraphTraversal.HaltingCondition;
import BlindGraphTraversal.Path;
import Logic.Statement;
import Logic.StatementBit;
import Logic.Variable;

public class MeaningEquivelanceHaltingCondition extends HaltingCondition {

	Statement goalStatement;
	
	public MeaningEquivelanceHaltingCondition(Statement st) {
		this.goalStatement = st;
	}
	
	@Override
	public boolean isSatisfiedBy(Path path) {
		ProofStatement proofstmt = (ProofStatement) path.getLastNode();
		Statement st = proofstmt.getStatement();
		
		return this.fitsMeaning(st);
	}
	
	public boolean fitsMeaning(Statement st) {
		if(st.size() != this.goalStatement.size()) return false;
		
		Map<Variable, Variable> vc = new HashMap<Variable, Variable>();
		
		for(int i=0;i<this.goalStatement.size();i++) {
			StatementBit bit1 = this.goalStatement.Sequence.get(i);
			StatementBit bit2 = st.Sequence.get(i);
			
			if(bit1.isVariable()!=bit2.isVariable()) return false;
			
			if(bit1.isVariable()) {
				Variable v1 = (Variable) bit1;
				Variable v2 = (Variable) bit2;
				
				if(vc.containsKey(v1)) {
					if(vc.get(v1) != v2) return false;
				} else {
					vc.put(v1, v2);
				}
			} else {
				if(!bit1.literalEquals(bit2)) return false;
			}
		}
		
		return true;
	}
	
}
