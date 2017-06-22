package TestFiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Logic.Statement;

public class Main {

	public static void main(String[] args) {
		try {

			BufferedReader axiomsfile = new BufferedReader(new FileReader("AxiomSets/PeanoAxioms.txt"));

			String bitSeperator = axiomsfile.readLine().split(": ")[1];
			String varHandle = axiomsfile.readLine().split(": ")[1];

			System.out.println("Bit Seperator: " + bitSeperator);

			String line;

			while ((line = axiomsfile.readLine()) != null) {
				if (line.equals("") || line.charAt(0)=='#')
					continue;

				Statement st = new Statement(line, bitSeperator, varHandle);
				System.out.println(st);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
