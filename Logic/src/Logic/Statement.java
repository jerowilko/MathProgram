package Logic;

import java.util.ArrayList;

public class Statement {

	ArrayList<StatementBit> Sequence;
	PunctuationalContext punctuation;
	VariableContext variableAssignments;
	ArrayList<StatementApplicationRecord> derrivation = new ArrayList<StatementApplicationRecord>();

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
	
	public Statement getLHS() {
		if(!this.isReplacementStatement()) return null;
		return this.getUnit(3);
	}

	public Statement getRHS() {
		if(!this.isReplacementStatement()) return null;
		return this.getUnit(3+this.getLHS().size()+1);
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
	
	public ArrayList<Integer> getApplicableIndices(Statement st) {
		if(!this.isReplacementStatement()) return null;
		
		Statement LHS = this.getLHS();
		
		ArrayList<Integer> indices = new ArrayList<Integer>();
		
		for(int i=0;i<st.Sequence.size();i++) {
			Statement unit = st.getUnit(i);
			if(unit!=null && LHS.fitsSignature(unit)) indices.add(i);
		}
		
		return indices;
	}
	
	public boolean isApplicableTo(Statement unit, VariableContext vc) {
		Statement LHS = this.getLHS();
		return LHS.fitsSignature(unit, vc);
	}
	
	public boolean isApplicableTo(Statement unit) {
		return this.isApplicableTo(unit, new VariableContext());
	}
	
	public Statement applyTo(Statement st, int unitIndex) {
		VariableContext vc = new VariableContext();
		Statement unit = st.getUnit(unitIndex);
		
		if(!this.isApplicableTo(unit, vc)) return null;
		
		Statement newUnit = this.getRHS().substitute(vc);
	
		ArrayList<StatementBit> newSequence = new ArrayList<StatementBit>();
		
		newSequence.addAll(st.Sequence.subList(0, unitIndex));
		newSequence.addAll(newUnit.Sequence);
		newSequence.addAll(st.Sequence.subList(unitIndex + unit.size(), st.size()));
		
		Statement theorem = new Statement(newSequence, st.variableAssignments, st.punctuation);
		
		theorem.derrivation.addAll(this.derrivation);
		theorem.derrivation.addAll(st.derrivation);
		theorem.derrivation.add(new StatementApplicationRecord(this, st, theorem, unitIndex));
		
		return theorem;
	}

	public Statement substitute(VariableContext vc) {
		ArrayList<StatementBit> newSequence = new ArrayList<StatementBit>();
		
		for(int i=0;i<this.size();i++) {
			if(!this.Sequence.get(i).isVariable()) newSequence.add(this.Sequence.get(i));
			else {
				if(vc.isSet((Variable) this.Sequence.get(i))) {
					newSequence.addAll(vc.getValue((Variable) this.Sequence.get(i)).Sequence);
				} else {
					newSequence.add(this.Sequence.get(i));
				}
			}
		}
				
		return new Statement(newSequence, this.variableAssignments, this.punctuation);
	}

}
