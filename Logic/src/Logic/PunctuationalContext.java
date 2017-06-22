package Logic;

import java.util.ArrayList;

public class PunctuationalContext {

	String bitSeperator;
	ArrayList<String> collectionOpeners;
	ArrayList<String> collectionClosers;
	String variableOpener;
	String variableCloser;

	public PunctuationalContext(String bitSeperator, String collectionOpeners, String collectionClosers,
			String variableOpener, String variableCloser) {
		this.bitSeperator = bitSeperator;
		this.variableOpener = variableOpener;
		this.variableCloser = variableCloser;

		this.collectionOpeners = new ArrayList<String>(collectionOpeners.length());
		this.collectionClosers = new ArrayList<String>(collectionClosers.length());

		for (int i = 0; i < collectionOpeners.length(); i++) {
			this.collectionOpeners.add(collectionOpeners.charAt(i) + "");
			this.collectionClosers.add(collectionClosers.charAt(i) + "");
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

	public StatementBit parseBit(String strBit) {
		if (isVariableBit(strBit)) {
			return new Variable(
					strBit.substring(this.variableOpener.length(), strBit.length() - this.variableCloser.length()));
		} else {
			return new StatementBit(strBit);
		}
	}

	public ArrayList<StatementBit> getSequence(String initialisationString) {
		String[] strBits = this.splitBits(initialisationString);
		ArrayList<StatementBit> sequence = new ArrayList<StatementBit>(strBits.length-2);
		
		for(int i=1;i<strBits.length-1;i++) {
			sequence.add(this.parseBit(strBits[i]));
		}
		
		return sequence;
	}

	public String joinSequence(ArrayList<StatementBit> sequence) {
		String str = "";
		
		for(int i=0;i<sequence.size();i++){
			str+=this.bitSeperator;
			if(sequence.get(i).isVariable()) {
				str += this.variableOpener;
				str += sequence.get(i);
				str += this.variableCloser;
			} else {
				str += sequence.get(i);
			}
		}
		
		return str + this.bitSeperator;
	}
	
}
