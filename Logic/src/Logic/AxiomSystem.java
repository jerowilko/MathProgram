package Logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

public class AxiomSystem {

	public static boolean suppressErrors = false;
	public ArrayList<Statement> axioms = new ArrayList<Statement>();
	public ArrayList<Statement> definitions = new ArrayList<Statement>();
	public ArrayList<Statement> theorems = new ArrayList<Statement>();
	public ArrayList<Statement> importantResults = new ArrayList<Statement>();
	
	public PunctuationalContext punct;

	public AxiomSystem() {
		
	}
	
	public AxiomSystem(ArrayList<Statement> statements, PunctuationalContext punct) {
		this.punct = punct;
		this.axioms = new ArrayList<Statement>(statements);
	}
	
	public void readStatementFileIntoArr(String filname, ArrayList<Statement> arr) {
		try {
			BufferedReader stmtfile = new BufferedReader(new FileReader(filname));

			PunctuationalContext punct = getPunctuationContext(stmtfile);
			
			this.punct = punct;

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
		return new PunctuationalContext(stmtfile);
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
	
	public ArrayList<Statement> getAllApplyableStatements() {
		ArrayList<Statement> statements = new ArrayList<Statement>();
		
		for(int i = 0;i<this.axioms.size();i++) {
			if(this.axioms.get(i).isReplacementStatement()) {
				statements.add(this.axioms.get(i));
			}
		}
		
		for(int i = 0;i<this.definitions.size();i++) {
			if(this.definitions.get(i).isReplacementStatement()) {
				statements.add(this.definitions.get(i));
			}
		}
		
		return statements;
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

	public void addAxiom(String stmt) {
		this.addAxiom(new Statement(stmt, this.punct));
	}
	
	public void addAxiom(Statement axiom) {
		this.axioms.add(axiom);
	}
	
	public void addDefinition(String stmt) {
		this.addDefinition(new Statement(stmt, this.punct));
	}
	
	public void addDefinition(Statement definition) {
		this.definitions.add(definition);
	}
	
	public void addTheorem(Statement theorem) {
		this.theorems.add(theorem);
	}
	
	public void addImportantResult(Statement st) {
		this.importantResults.add(st);
	}
	
	public void removeAxiom(Statement st) {
		this.axioms.remove(st);
	}

	public void removeDefinition(Statement st) {
		this.definitions.remove(st);
	}
	
	public void removeTheorem(Statement st) {
		this.theorems.remove(st);
	}
	
	public void removeImportantResult(Statement st) {
		this.importantResults.remove(st);
	}
	
	public void exportState(String filname) {
		Writer writer;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filname), "utf-8"));
		
			writer.write(this.punct.getExportString());
			
			writer.write("\nAxioms:");
			for(int i=0;i<this.axioms.size();i++) {
				writer.write("\n"+this.axioms.get(i).literalJoinSequence());
			}
			
			writer.write("\nDefinitions:");
			for(int i=0;i<this.definitions.size();i++) {
				writer.write("\n"+this.definitions.get(i).literalJoinSequence());
			}
			
			writer.write("\nTheorems:");
			for(int i=0;i<this.theorems.size();i++) {
				writer.write("\n"+this.theorems.get(i).literalJoinSequence());
			}
			
			writer.write("\nImportant Results:");
			for(int i=0;i<this.importantResults.size();i++) {
				writer.write("\n"+this.importantResults.get(i).literalJoinSequence());
			}
		
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void importState(String filname) {
		try {
			BufferedReader statefile = new BufferedReader(new FileReader(filname));
			
			this.punct = this.getPunctuationContext(statefile);
			
			String line;
			
			line = statefile.readLine();
			
			while (!(line = statefile.readLine()).equals("Definitions:")) {
				if (line.equals("") || line.charAt(0) == '#')
					continue;

				Statement st = new Statement(line, punct);
				this.axioms.add(st);
			}
			
			while (!(line = statefile.readLine()).equals("Theorems:")) {
				if (line.equals("") || line.charAt(0) == '#')
					continue;

				Statement st = new Statement(line, punct);
				this.definitions.add(st);
			}
			
			while (!(line = statefile.readLine()).equals("Important Results:")) {
				if (line.equals("") || line.charAt(0) == '#')
					continue;

				Statement st = new Statement(line, punct);
				this.theorems.add(st);
			}
			
			while ((line = statefile.readLine()) != null) {
				if (line.equals("") || line.charAt(0) == '#')
					continue;

				Statement st = new Statement(line, punct);
				this.importantResults.add(st);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void importPunctuation(String filname) {

		try {
			this.punct = this.getPunctuationContext(new BufferedReader(new FileReader(filname)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
