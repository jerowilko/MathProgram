package TestFiles;

import Logic.AxiomSystem;

public class Main {

	public static void main(String[] args) {
		
		AxiomSystem as = new AxiomSystem();
		
		as.importAxioms("AxiomSets/TestAxioms.txt");
		as.importDefinitions("DefinitionSets/PeanoAddition.txt");
		
		System.out.println(as);
		
	}
	
}
