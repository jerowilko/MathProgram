package Logic;

import java.util.ArrayList;

public class Statement {

	ArrayList<StatementBit> Sequence;
	PunctuationalContext punctuation;

	public Statement(String initialisationString, PunctuationalContext punct) {
		this.punctuation = punct;

		this.Sequence = punctuation.getSequence(initialisationString);
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

	public String toString() {
		if (GlobalConstants.debugMode) {
			return this.literalJoinSequence();
		}

		return this.JoinSequence();
	}

}
