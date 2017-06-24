package TestFiles;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Logic.PunctuationalContext;
import Logic.Statement;
import Logic.UsefulFuncs;
import Logic.VariableContext;

public class Main {

	public static void main(String[] args) {
		ArrayList<Statement> stmts = readStatementFile("AxiomSets/TestAxioms.txt");

		for (int i = 0; i < stmts.size(); i++) {

			System.out.println(i + " " + stmts.get(i));

		}
		
		VariableContext vc = new VariableContext();
		
		System.out.println("\n\n");
		
		System.out.println(stmts.get(0) + " is replacement statement: " + stmts.get(0).isReplacementStatement());
		
		System.out.println(stmts.get(1) + " matches the signature of " + stmts.get(0).getUnit(3)+ ": " + stmts.get(0).getUnit(3).fitsSignature(stmts.get(1),vc) + ". provided:\n" + vc);
		
		System.out.println("Axiom 0 can be applied to axiom 3 at the following indices: "+UsefulFuncs.arrToString(stmts.get(0).getApplicableIndices(stmts.get(3))));
		
		System.out.println("Applying axiom 0 to axiom 3 at position 3 results in: "+stmts.get(0).applyTo(stmts.get(3),3));
		
		System.out.println("\n\nHere comes the first proof:\n\n");
		System.out.println("Given that "+stmts.get(2)+" and "+stmts.get(0)+" we conclude that "+stmts.get(0).applyTo(stmts.get(2), 0)+".");
		stmts.add(stmts.get(0).applyTo(stmts.get(2), 0));
		System.out.println("Given that "+stmts.get(1)+" and "+stmts.get(4)+" we conclude that "+stmts.get(4).applyTo(stmts.get(1), 5)+".");
	}

	public static ArrayList<Statement> readStatementFile(String fname) {
		try {
			BufferedReader stmtfile = new BufferedReader(new FileReader(fname));

			PunctuationalContext punct = getPunctuationContext(stmtfile);

			ArrayList<Statement> stmts = new ArrayList<Statement>();

			String line;
			while ((line = stmtfile.readLine()) != null) {
				if (line.equals("") || line.charAt(0) == '#')
					continue;

				Statement st = new Statement(line, punct);
				stmts.add(st);
			}

			return stmts;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static PunctuationalContext getPunctuationContext(BufferedReader stmtfile) throws IOException {
		String bitSeperator = stmtfile.readLine().split(": ")[1];
		String collectionOpeners = stmtfile.readLine().split(": ")[1];
		String collectionClosers = stmtfile.readLine().split(": ")[1];
		String variableOpener = stmtfile.readLine().split(": ")[1];
		String variableCloser = stmtfile.readLine().split(": ")[1];

		PunctuationalContext punct = new PunctuationalContext(bitSeperator, collectionOpeners, collectionClosers,
				variableOpener, variableCloser);
		return punct;
	}

}
