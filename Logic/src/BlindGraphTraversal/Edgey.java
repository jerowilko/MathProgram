package BlindGraphTraversal;

import java.util.ArrayList;

public abstract class Edgey implements Comparable {
	public abstract int getCost();
	
	public abstract Nodal getStartNode();
	
	public abstract Nodal getEndNode();
	
	public int compareTo(Object o) {
		if(!(o instanceof Edgey)) throw new java.lang.RuntimeException("Cannot compare Edgey to non-edgey.");
		
		Edgey e = (Edgey) o;
		
		return this.getCost() - e.getCost();
	}
}
