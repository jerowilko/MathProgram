package BlindGraphTraversalTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import BlindGraphTraversal.AcyclicShortestPathSearcher;

public class AcyclicPathSearchTest {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("GraphTestFiles/SimpleGraph"));

			int numNodes = Integer.parseInt(br.readLine());

			ArrayList<Node> nodes = new ArrayList<Node>(numNodes);

			for (int i = 0; i < numNodes; i++) {
				nodes.add(new Node("" + i));
			}
			
			String line;
			
			while((line = br.readLine())!=null) {
				String[] parts = line.split(" ");
				Node node1 = nodes.get(Integer.parseInt(parts[0]));
				Node node2 = nodes.get(Integer.parseInt(parts[1]));
				int weight = Integer.parseInt(parts[2]);
				
				node1.addNeighbour(node2, weight);
				node2.addNeighbour(node1, weight);
			}
			
			EndNodeHaltingCondition halt = new EndNodeHaltingCondition(nodes.get(nodes.size()-1));
			
			AcyclicShortestPathSearcher searcher = new AcyclicShortestPathSearcher(halt);
			
			System.out.println(searcher.findPathFrom(nodes.get(0)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
