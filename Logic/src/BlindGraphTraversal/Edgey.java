package BlindGraphTraversal;

import java.util.ArrayList;

public interface Edgey {
	public abstract int getCost();
	
	public abstract Nodal getStartNode();
	
	public abstract Nodal getEndNode();
	
	public abstract ArrayList<Edgey> travel();
}
