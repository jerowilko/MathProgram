package Logic;

import java.util.ArrayList;

public class Statement {

	ArrayList<StatementBit> Sequence;
	String literalBitSep = "'";
	String variableHandle = "@";

	public Statement(String initialisationString, String bitSep, String varHandle) {
		this.literalBitSep = bitSep;
		this.variableHandle = varHandle;

		String[] StringBits = initialisationString.split(this.literalBitSep);
		this.Sequence = new ArrayList<StatementBit>(StringBits.length - 2);

		for (int i = 1; i < StringBits.length; i++) {
			if (StringBits[i].length() > this.variableHandle.length() + 1
					&& StringBits[i].substring(0, this.variableHandle.length() + 1)
							.equals(Variable.variableOpener + this.variableHandle)) {
				this.Sequence.add(new Variable(StringBits[i].split(this.variableHandle)[1]));
			} else {
				this.Sequence.add(new StatementBit(StringBits[i]));
			}
		}
	}

	public String literalJoinSequence() {
		String str = "";
		for (int i = 0; i < this.Sequence.size(); i++) {
			if(this.Sequence.get(i).isVariable()) {
				str += this.literalBitSep + Variable.variableOpener + this.variableHandle + this.Sequence.get(i) + this.variableHandle + Variable.variableCloser;
			} else {
				str += this.literalBitSep + this.Sequence.get(i);
			}
		}
		return str + this.literalBitSep;
	}

	public String JoinSequence() {
		String str = "";
		for (int i = 0; i < this.Sequence.size(); i++) {
			str += this.Sequence.get(i);
		}
		return str;
	}

	public String toString() {
		if (GlobalConstants.debugMode) {
			return this.literalJoinSequence();
		}

		return this.JoinSequence();
	}

}
