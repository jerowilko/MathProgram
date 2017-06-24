package Logic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VariableContext {
	
	public Map<Variable, Statement> VariableAssignments;
	
	public VariableContext() {
		this.VariableAssignments = new HashMap<Variable, Statement>();
	}
	
	public void setVariable(Variable var, Statement value) {
		if(this.VariableAssignments.containsKey(var)) {
			System.out.println("Warning, defined variable overwrite detectd.");
		}
		this.VariableAssignments.put(var, value);
	}
	
	public Statement getValue(Variable var) {
		return this.VariableAssignments.get(var);
	}
	
	public boolean isSet(Variable var) {
		return this.VariableAssignments.containsKey(var);
	}
	
	public String toString() {
		String str = "";
		Iterator it = this.VariableAssignments.entrySet().iterator();
		
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
	        str += pair.getKey() + " = " + pair.getValue();
			if(it.hasNext()) str += "\n";
		}
		
		return str;
	}
}
