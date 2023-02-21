import java.util.HashMap;

public class Scope {
    public HashMap<Token, VariableBinding> stackSegment;
    public Scope parent;

    /**
     * Constructs an empty Scope
     */
    public Scope(Scope parentScope) {
        this.stackSegment = new HashMap<Token, VariableBinding>();
        this.parent = parentScope;
    }


    /**
     * Declares a variable (without storing an actual value).
     *   @param variable the variable to be declared
     */
    public void declareVariable(Token variable, DataValue.Type type) {
        //this declares a variable inside of the scope so it is a variable mapping
        this.stackSegment.put(variable, new VariableBinding(type));
    }
    
    /** 
     * Determines if a variable is already declared.
     * @param variable the variable to be found
     * @return true if it is declared and/or assigned
     */
    public boolean isDeclared(Token variable) {
    	return this.stackSegment.containsKey(variable);
    }
    
    /**
     * Stores a variable/value in the stack segment.
     *   @param variable the variable name
     *   @param val the value to be stored under that name
     */
    public void storeAddress(Token variable, int address)  {
    	this.stackSegment.get(variable).setAddress(address);
    }
    
    /**
     * Determines the value associated with a variable in memory.
     *   @param variable the variable to look up
     *   @return the value associated with that variable
     */      
    public int lookupAddress(Token variable) {
    	return this.stackSegment.get(variable).getAddress();
    }

    /**
     * Determines the value type associated with a variable in memory.
     *   @param variable the variable to look up
     *   @return the type associated with that variable
     */ 
    public DataValue.Type lookupType(Token variable) {
    	return this.stackSegment.get(variable).getType();
    }
}
