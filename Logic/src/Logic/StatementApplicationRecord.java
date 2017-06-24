package Logic;

public class StatementApplicationRecord {

	public Statement st1;
	public Statement st2;
	public Statement st3;
	public int index;

	public StatementApplicationRecord(Statement st1, Statement st2, Statement st3, int index) {
		this.st1 = st1;
		this.st2 = st2;
		this.st3 = st3;
		this.index = index;
	}

	public String toString() {
		String str = "Statement " + st1 + " was applied to statement " + st2 + " to obtain " + st3 + ".";
		return str;
	}

}
