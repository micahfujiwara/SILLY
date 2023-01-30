import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that defines the memory space for the SILLY interpreter.
 *   @author Dave Reed
 *   @version 1/15/23
 */
public class MemorySpace {
    private HashMap<Token, VariableBinding> stackSegment;
    private ArrayList<DataValue> heapSegment;

    /**
     * Constructs an empty memory space.
     */
    public MemorySpace() {
        this.stackSegment = new HashMap<Token, VariableBinding>();
        this.heapSegment = new ArrayList<DataValue>();
    }
    
    /**
     * Declares a variable (without storing an actual value).
     *   @param variable the variable to be declared
     */
    public void declareVariable(Token variable, DataValue.Type type) {
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
    public void storeValue(Token variable, DataValue val)  {
    	int addr = this.getHeapAddress(val);
    	this.stackSegment.get(variable).setAddress(addr);
    }
    
    /**
     * Determines the value associated with a variable in memory.
     *   @param variable the variable to look up
     *   @return the value associated with that variable
     */      
    public DataValue lookupValue(Token variable) {
    	int addr = this.stackSegment.get(variable).getAddress();
    	return this.heapSegment.get(addr);
    }

    /**
     * Determines the value type associated with a variable in memory.
     *   @param variable the variable to look up
     *   @return the type associated with that variable
     */ 
    public DataValue.Type lookupType(Token variable) {
    	return this.stackSegment.get(variable).getType();
    }
    
    /////////////////////////////////////////////////////////////////////////////
    
    private int getHeapAddress(DataValue val) {
    	int addr = this.heapSegment.indexOf(val);
    	if (addr == -1) {
    		addr = this.heapSegment.size();
    		this.heapSegment.add(val);
    	}
    	return addr;
    }
}
