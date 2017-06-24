package Logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AxiomSystem {

	public ArrayList<Statement> axioms = new ArrayList<Statement>();
	public ArrayList<Statement> definitions = new ArrayList<Statement>();
	public ArrayList<Statement> theorems = new ArrayList<Statement>();

	public void readStatementFileIntoArr(String filname, ArrayList<Statement> arr) {
		try {
			BufferedReader stmtfile = new BufferedReader(new FileReader(filname));

			PunctuationalContext punct = getPunctuationContext(stmtfile);

			String line;
			while ((line = stmtfile.readLine()) != null) {
				if (line.equals("") || line.charAt(0) == '#')
					continue;

				Statement st = new Statement(line, punct);
				arr.add(st);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PunctuationalContext getPunctuationContext(BufferedReader stmtfile) throws IOException {
		String bitSeperator = stmtfile.readLine().split(": ")[1];
		String collectionOpeners = stmtfile.readLine().split(": ")[1];
		String collectionClosers = stmtfile.readLine().split(": ")[1];
		String variableOpener = stmtfile.readLine().split(": ")[1];
		String variableCloser = stmtfile.readLine().split(": ")[1];

		PunctuationalContext punct = new PunctuationalContext(bitSeperator, collectionOpeners, collectionClosers,
				variableOpener, variableCloser);
		return punct;
	}

	public void importAxioms(String filname) {
		readStatementFileIntoArr(filname, this.axioms);
	}

	public void importDefinitions(String filname) {
		readStatementFileIntoArr(filname, this.definitions);
	}

	public Statement getStatementFromIdentifier(String str) {
		int num = Integer.parseInt(str.substring(1));

		if (str.charAt(0) == 'A') {
			return this.axioms.get(num);
		} else if (str.charAt(0) == 'D') {
			return this.definitions.get(num);
		} else if (str.charAt(0) == 'T') {
			return this.theorems.get(num);
		}

		return null;
	}

	public ArrayList<Statement> getAllStatements() {
		ArrayList<Statement> allStatements = new ArrayList<Statement>();
		allStatements.addAll(this.axioms);
		allStatements.addAll(this.definitions);
		allStatements.addAll(this.theorems);
		
		return allStatements;
	}
	
	public String toString() {
		String str = "Axioms: \n";

		for (int i = 0; i < this.axioms.size(); i++) {
			str += "\n" + i + " " + this.axioms.get(i);
		}

		if (this.axioms.size() == 0)
			str += "\nNone.";

		str += "\n\nDefinitions: \n";

		for (int i = 0; i < this.definitions.size(); i++) {
			str += "\n" + i + " " + this.definitions.get(i);
		}

		if (this.definitions.size() == 0)
			str += "\nNone.";

		str += "\n\nTheorems: \n";

		for (int i = 0; i < this.theorems.size(); i++) {
			str += "\n" + i + " " + this.theorems.get(i);
		}

		if (this.theorems.size() == 0)
			str += "\nNone.";

		return str;
	}

	public void addTheorem(Statement theorem) {
		this.theorems.add(theorem);
	}

}
