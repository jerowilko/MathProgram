package Logic;

import java.util.ArrayList;

public class Statement {

	ArrayList<StatementBit> Sequence;
	PunctuationalContext punctuation;
	VariableContext variableAssignments;

	public Statement(String initialisationString, PunctuationalContext punct) {
		this(punct.getSequence(initialisationString), new VariableContext(), punct);
	}

	public Statement(ArrayList<StatementBit> Sequence, VariableContext varc, PunctuationalContext punct) {
		this.punctuation = punct;
		this.Sequence = new ArrayList(Sequence);
	}

	public String literalJoinSequence() {
		return punctuation.joinSequence(this.Sequence);
	}

	public String JoinSequence() {
		String str = "";
		for (int i = 0; i < this.Sequence.size(); i++) {
			str += this.Sequence.get(i);
		}
		return str;
	}

	public int size() {
		return this.Sequence.size();
	}

	public String toString() {
		if (GlobalConstants.debugMode) {
			return this.literalJoinSequence();
		}

		return this.JoinSequence();
	}

	public Statement getUnit(int startIndex) {
		return this.punctuation.getUnit(this, startIndex);
	}

	public boolean isReplacementStatement() {
		if (!(this.Sequence.get(1).literalValue.equals("r") && this.Sequence.get(2).literalValue.equals("[")))
			return false;

		Statement firstUnit = this.getUnit(3);

		if (!(this.Sequence.get(2 + firstUnit.size() + 1).literalValue.equals(",")))
			return false;

		Statement secondUnit = this.getUnit(2 + firstUnit.size() + 2);

		if (!(this.Sequence.get(2 + firstUnit.size() + 1 + secondUnit.size() + 1).literalValue.equals("]")
				&& (3 + firstUnit.size() + 1 + secondUnit.size() + 1) == this.size() - 1))
			return false;

		return true;
	}
	
	public boolean isCollection() {
		return this.punctuation.isCollectionOpener(this.Sequence.get(0)) && this.getUnit(0).size() == this.size();
	}
	
	public boolean fitsSignature(Statement st) {
		return this.fitsSignature(st, new VariableContext());
	}
	
	public boolean fitsSignature(Statement st, VariableContext vc) {
		
		if(this.size()==1) {
			if(this.Sequence.get(0).isVariable()) {
				if(vc.isSet((Variable) this.Sequence.get(0))) {
					return vc.getValue((Variable) this.Sequence.get(0)).fitsSignature(st, vc);
				} else {
					vc.setVariable((Variable) this.Sequence.get(0), st);
					return true;
				}
			} else {
				if(st.size()!=1) return false;
				return this.Sequence.get(0).literalValue.equals(st.Sequence.get(0).literalValue);
			}
		}
		
		//Put checks for st unity and variable assingment here....
		
		int index1 = 0;
		int index2 = 0;
		
		if(this.isCollection()) {
			if(!this.Sequence.get(0).literalValue.equals(st.Sequence.get(0).literalValue)) return false;
			
			index1 = 1;
			index2 = 1;
		}
		
		while(index1 < this.size()-1) {
			Statement unit1 = this.getUnit(index1);
			Statement unit2 = st.getUnit(index2);
			
			if(!unit1.fitsSignature(unit2, vc)) return false;
			
			index1 += unit1.size();
			index2 += unit2.size();
		}
		
		return true;
	}

}
