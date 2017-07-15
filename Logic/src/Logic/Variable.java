package Logic;

public class Variable extends StatementBit {
	
	public Variable(String literal) {
		super(literal);
	}
	
	public String toString() {
		return super.toString();// + java.lang.System.identityHashCode(this);
	}

}
