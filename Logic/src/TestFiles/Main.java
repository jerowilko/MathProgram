package TestFiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Logic.PunctuationalContext;
import Logic.Statement;

public class Main {

	public static void main(String[] args) {
		try {

			BufferedReader axiomsfile = new BufferedReader(new FileReader("AxiomSets/PeanoAxioms.txt"));

			String bitSeperator = axiomsfile.readLine().split(": ")[1];
			String collectionOpeners = axiomsfile.readLine().split(": ")[1];
			String collectionClosers = axiomsfile.readLine().split(": ")[1];
			String variableOpener = axiomsfile.readLine().split(": ")[1];
			String variableCloser = axiomsfile.readLine().split(": ")[1];

			System.out.println("Bit Seperator: " + bitSeperator);

			String line;

			PunctuationalContext punct = new PunctuationalContext(bitSeperator, collectionOpeners, collectionClosers, variableOpener, variableCloser);
			
			while ((line = axiomsfile.readLine()) != null) {
				if (line.equals("") || line.charAt(0) == '#')
					continue;

				Statement st = new Statement(line, punct);
				System.out.println(st);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
