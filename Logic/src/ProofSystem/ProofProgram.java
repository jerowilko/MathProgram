package ProofSystem;

import java.util.Scanner;

import BlindGraphTraversal.AcyclicBreadthFirstPathTraverser;
import BlindGraphTraversal.AcyclicShortestPathSearcher;
import Logic.AxiomSystem;
import Logic.Statement;

public class ProofProgram {
	public static void main(String[] args) {
		AxiomSystem.suppressErrors = true;
		
		AxiomSystem as = new AxiomSystem();
		
		String startStatement = "'<':='['<'n'['<'i'['<@P@>']'>']'>','<'n'['<'i'['<@P@>']'>']'>']'>'";
		String endStatement = "'<':='['<'n'['<'i'['<@P@>']'>']'>','<'n'['<@P@>']'>']'>'";
		
		as.importState("Saved States/LogicDraft");
		
		System.out.println(as);
		
		ProofStatement initialStatement = new ProofStatement(as, new Statement(startStatement, as.punct));
		Statement goalStatement = new Statement(endStatement, as.punct);
		
		MeaningEquivelanceHaltingCondition halt = new MeaningEquivelanceHaltingCondition(goalStatement);
		
		AcyclicShortestPathSearcher traverser = new AcyclicShortestPathSearcher(halt);
		
		System.out.println("Trying to prove the statement: " + goalStatement);
		System.out.println("Starting with statement: " + initialStatement);
		
		
		
		System.out.println("\n\n" + traverser.findPathFrom(initialStatement));
	}
}
