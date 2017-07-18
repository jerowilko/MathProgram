package ProofSystem;

import java.util.ArrayList;

import BlindGraphTraversal.AcyclicPath;
import BlindGraphTraversal.AcyclicShortestPathSearcher;
import BlindGraphTraversal.Edgey;
import BlindGraphTraversal.Nodal;
import Logic.AxiomSystem;
import Logic.Statement;

public class ProofProgram {
	public static void main(String[] args) {
		AxiomSystem.suppressErrors = true;
		
		AxiomSystem as = new AxiomSystem();
		
		String startStatement = "'<':='['<'n'['<'{'<@X@>','<@Y@>'}'['<@P@>']'>']'>','<'n'['<'{'<@X@>','<@Y@>'}'['<@P@>']'>']'>']'>'";
		String endStatement = "'<':='['<'n'['<'{'<@X@>','<@Y@>'}'['<@P@>']'>']'>','<'{'<'n'['<@X@>']'>','<'n'['<@Y@>']'>'}'['<@P@>']'>']'>'";
		
		as.importState("Saved States/LogicDraft");
		
		System.out.println(as);
		
		Statement goalStatement = new Statement(endStatement, as.punct);
		
		ProofSimilarityWeightingAssigner weighter = new ProofSimilarityWeightingAssigner(goalStatement);
		ProofStatement initialStatement = new ProofStatement(as, weighter, new Statement(startStatement, as.punct));
		
		MeaningEquivelanceHaltingCondition halt = new MeaningEquivelanceHaltingCondition(goalStatement);
		
		AcyclicShortestPathSearcher searcher = new AcyclicShortestPathSearcher(halt);
		
		System.out.println("Trying to prove the statement: " + goalStatement);
		System.out.println("Starting with statement: " + initialStatement);
		
		System.out.println("\n\n" + printProof(searcher.findPathFrom(initialStatement)));
		
		System.out.println("\n\nIn the end, " + searcher.getNumEdges() + " edges were left in the priority queue.");
	}

	public static String printProof(AcyclicPath proof) {
		String str = "Proof for the statement: " + ((ProofStatement) proof.getLastNode()).getStatement() + "\n\n";
		
		ArrayList<Nodal> lines = proof.getNodeList();
		ArrayList<Edgey> steps = proof.getEdgeList();
		
		str += "Start with statement: " + ((ProofStatement) proof.getFirstNode()).getStatement();
		
		for(int i=0;i<steps.size();i++) {
			StatementApplication step = (StatementApplication) steps.get(i);
			ProofStatement result = (ProofStatement) lines.get(i+1);
			
			str+="\n\nThen apply statement " + step.appliedStatement + " at position " + step.applicationPosition + ".";
			str+="\nThis results in the statement: " + result.getStatement();
		}
		
		str += "\nQED.";
		
		return str;
	}
}
