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
		
		for(int i=1;i<strBits.length;i++) {
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
	
	public boolean isCollectionOpener(StatementBit bit) {
		if(bit.isVariable()) return false;
		
		return this.collectionOpeners.contains(bit.literalValue);
	}
	
	public boolean isCollectionCloser(StatementBit bit) {
		if(bit.isVariable()) return false;
		
		return this.collectionClosers.contains(bit.literalValue);
	}

	public Statement getUnit(Statement statement, int startIndex) {
		ArrayList<StatementBit> newSequence = new ArrayList<StatementBit>();
		StatementBit startBit = statement.Sequence.get(startIndex);
		
		if(this.isCollectionCloser(startBit)) return null;
		
		if(!this.isCollectionOpener(startBit)) {
			newSequence.add(startBit);
			return new Statement(newSequence, statement.variableAssignments, this);
		}
		
		int[] levels = new int[this.collectionOpeners.size()];
		levels[this.collectionOpeners.indexOf(startBit.literalValue)] += 1;
		newSequence.add(startBit);
		
		int i = startIndex + 1;
		while(UsefulFuncs.arraySum(levels)>0) {
			StatementBit bit = statement.Sequence.get(i);
			newSequence.add(bit);
			
			if(this.isCollectionOpener(bit)) {
				levels[this.collectionOpeners.indexOf(bit.literalValue)] += 1;
			} else if(this.isCollectionCloser(bit)) {
				levels[this.collectionClosers.indexOf(bit.literalValue)] -= 1;
			}
			
			i++;
		}
		
		return new Statement(newSequence, statement.variableAssignments, this);
	}
	
}
