package Logic;

import java.util.ArrayList;

public class Statement {

	ArrayList<StatementBit> Sequence;
	String literalBitSep = "'";

	public Statement(String initialisationString, String bitSep) {
		this.literalBitSep = bitSep;

		String[] StringBits = initialisationString.split(this.literalBitSep);
		this.Sequence = new ArrayList<StatementBit>(StringBits.length - 2);

		for (int i = 1; i < StringBits.length; i++) {
			this.Sequence.add(new StatementBit(StringBits[i]));
		}
	}

	public String literalJoinSequence() {
		String str = "";
		for (int i = 0; i < this.Sequence.size(); i++) {
			str += this.literalBitSep + this.Sequence.get(i);
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
