package Logic;

import java.util.HashMap;
import java.util.Map;

public class VariableContext {
	
	public Map<Variable, Statement> VariableAssignments;
	
	public VariableContext() {
		this.VariableAssignments = new HashMap<Variable, Statement>();
	}
	
	public void setVariable(Variable var, Statement value) {
		this.VariableAssignments.put(var, value);
	}
	
	public Statement getVariableValue(Variable var) {
		return this.VariableAssignments.get(var);
	}
}
