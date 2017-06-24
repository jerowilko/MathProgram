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
