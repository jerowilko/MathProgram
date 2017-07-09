package Logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PunctuationalContext {

	String bitSeperator;
	ArrayList<String> collectionOpeners;
	ArrayList<String> collectionClosers;
	String variableOpener;
	String variableCloser;
	String replacementFunctionBit;
	String replacementFunctionOpener;
	String replacementFunctionSeperator;
	String replacementFunctionCloser;

	public PunctuationalContext(BufferedReader br) throws IOException {
		final String bitSeperator = br.readLine().split(": ")[1];
		final String collectionOpeners = br.readLine().split(": ")[1];
		final String collectionClosers = br.readLine().split(": ")[1];
		final String variableOpener = br.readLine().split(": ")[1];
		final String variableCloser = br.readLine().split(": ")[1];
		final String replacementFunctionBit = br.readLine().split(": ")[1];
		final String replacementFunctionOpener = br.readLine().split(": ")[1];
		final String replacementFunctionSeperator = br.readLine().split(": ")[1];
		final String replacementFunctionCloser = br.readLine().split(": ")[1];

		this.setValues(bitSeperator, collectionOpeners, collectionClosers, variableOpener, variableCloser,
				replacementFunctionBit, replacementFunctionOpener, replacementFunctionSeperator,
				replacementFunctionCloser);
	}

	public void setValues(String bitSeperator, String collectionOpeners, String collectionClosers,
			String variableOpener, String variableCloser, String replacementFunctionBit,
			String replacementFunctionOpener, String replacementFunctionSeperator, String replacementFunctionCloser) {
		this.bitSeperator = bitSeperator;
		this.variableOpener = variableOpener;
		this.variableCloser = variableCloser;

		this.collectionOpeners = new ArrayList<String>(collectionOpeners.length());
		this.collectionClosers = new ArrayList<String>(collectionClosers.length());

		for (int i = 0; i < collectionOpeners.length(); i++) {
			this.collectionOpeners.add(collectionOpeners.charAt(i) + "");
			this.collectionClosers.add(collectionClosers.charAt(i) + "");
		}

		this.replacementFunctionBit = replacementFunctionBit;
		this.replacementFunctionOpener = replacementFunctionOpener;
		this.replacementFunctionSeperator = replacementFunctionSeperator;
		this.replacementFunctionCloser = replacementFunctionCloser;

		if (!(this.collectionOpeners.contains(this.replacementFunctionOpener)
				|| this.collectionClosers.contains(this.replacementFunctionCloser))) {
			System.out
					.println("WARNING YOUR REPLACEMENT OPENERS AND/OR CLOSERS ARE NOT REGISTERED AS OPENERS/CLOSERS!");
		}
	}

	public String[] splitBits(String str) {
		return str.split(bitSeperator);
	}

	public boolean isVariableBit(String strBit) {
		if (strBit.length() < this.variableOpener.length())
			return false;

		int len = strBit.length();

		boolean startsWithOpener = strBit.substring(0, this.variableOpener.length()).equals(this.variableOpener);
		boolean endsWithCloser = strBit.substring(len - this.variableOpener.length(), len).equals(this.variableCloser);

		return startsWithOpener && endsWithCloser;
	}

	public StatementBit parseBit(String strBit, Map<String, Variable> variables) {
		if (isVariableBit(strBit)) {
			if (!variables.containsKey(strBit))
				variables.put(strBit, new Variable(strBit.substring(this.variableOpener.length(),
						strBit.length() - this.variableCloser.length())));

			return variables.get(strBit);
		} else {
			return new StatementBit(strBit);
		}
	}

	public ArrayList<StatementBit> getSequence(String initialisationString) {
		String[] strBits = this.splitBits(initialisationString);
		ArrayList<StatementBit> sequence = new ArrayList<StatementBit>(strBits.length - 2);

		Map<String, Variable> variables = new HashMap<String, Variable>();

		for (int i = 1; i < strBits.length; i++) {
			sequence.add(this.parseBit(strBits[i], variables));
		}

		return sequence;
	}

	public String joinSequence(ArrayList<StatementBit> sequence) {
		String str = "";

		for (int i = 0; i < sequence.size(); i++) {
			str += this.bitSeperator;
			if (sequence.get(i).isVariable()) {
				str += this.variableOpener;
				str += sequence.get(i);
				str += this.variableCloser;
			} else {
				str += sequence.get(i);
			}
		}

		return str + this.bitSeperator;
	}

	public boolean isCollectionOpener(StatementBit bit) {
		if (bit.isVariable())
			return false;

		return this.collectionOpeners.contains(bit.literalValue);
	}

	public boolean isCollectionCloser(StatementBit bit) {
		if (bit.isVariable())
			return false;

		return this.collectionClosers.contains(bit.literalValue);
	}

	public Statement getUnit(Statement statement, int startIndex) {
		ArrayList<StatementBit> newSequence = new ArrayList<StatementBit>();
		StatementBit startBit = statement.Sequence.get(startIndex);

		if (this.isCollectionCloser(startBit))
			return null;

		if (!this.isCollectionOpener(startBit)) {
			newSequence.add(startBit);
			return new Statement(newSequence, statement.variableAssignments, this);
		}

		int[] levels = new int[this.collectionOpeners.size()];
		levels[this.collectionOpeners.indexOf(startBit.literalValue)] += 1;
		newSequence.add(startBit);

		int i = startIndex + 1;
		while (UsefulFuncs.arraySum(levels) > 0) {
			StatementBit bit = statement.Sequence.get(i);
			newSequence.add(bit);

			if (this.isCollectionOpener(bit)) {
				levels[this.collectionOpeners.indexOf(bit.literalValue)] += 1;
			} else if (this.isCollectionCloser(bit)) {
				levels[this.collectionClosers.indexOf(bit.literalValue)] -= 1;
			}

			i++;
		}

		return new Statement(newSequence, statement.variableAssignments, this);
	}

	public String getExportString() {
		String str = "Bit Seperator: " + this.bitSeperator;

		str += "\nCollection Openers: ";
		for (int i = 0; i < this.collectionOpeners.size(); i++) {
			str += this.collectionOpeners.get(i);
		}

		str += "\nCollection Closers: ";
		for (int i = 0; i < this.collectionClosers.size(); i++) {
			str += this.collectionClosers.get(i);
		}

		str += "\nVariable Opener: " + this.variableOpener;
		str += "\nVariable Closer: " + this.variableCloser;
		str += "\nReplacement Function Bit: " + this.replacementFunctionBit;
		str += "\nReplacement Function Opener: " + this.replacementFunctionOpener;
		str += "\nReplacement Function Seperator: " + this.replacementFunctionSeperator;
		str += "\nReplacement Function Closer: " + this.replacementFunctionCloser;

		return str;
	}

}
