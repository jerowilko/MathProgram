package Logic;

public class StatementBit {

	String literalValue;

	public StatementBit(String literal) {
		this.literalValue = literal;
	}

	public String toString() {
		return literalValue;
	}

	public boolean isVariable() {
		return this instanceof Variable;
	}
	
	public boolean literalEquals(StatementBit bit) {
		return this.literalValue.equals(bit.literalValue);
	}
	
}
