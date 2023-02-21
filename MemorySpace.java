import java.util.ArrayList;
import java.util.Stack;

/**
 * Class that defines the memory space for the SILLY interpreter.
 *   @author Dave Reed
 *   @version 1/15/23
 */
public class MemorySpace {
    private ArrayList<DataValue> heapSegment;
    private Stack<Scope> stackSegment;

    /**
     * Constructs an empty memory space.
     */
    public MemorySpace() {
        this.stackSegment = new Stack<Scope>();
        this.heapSegment = new ArrayList<DataValue>();
        stackSegment.add(new Scope(null));
    }
    
    /**
     * Declares a variable (without storing an actual value).
     *   @param variable the variable to be declared
     * @throws Exception
     */
    public void declareVariable(Token variable, DataValue.Type type) throws Exception{
        Scope ptr = stackSegment.peek();
        ptr.declareVariable(variable, type);
    }

    /**
     * 
     * @param variable
     * @return
     */
    public boolean isDeclaredLocal(Token variable){
        Scope ptr = stackSegment.peek();
        return ptr.isDeclared(variable);
    }
    
    /** 
     * Determines if a variable is already declared.
     * @param variable the variable to be found
     * @return true if it is declared and/or assigned
     */
    public boolean isDeclared(Token variable) {
        Scope ptr = stackSegment.peek();
        while(ptr!=null){
            if (ptr.isDeclared(variable)){
                return true;
            }
            ptr = ptr.parent;
        }
        return false;
    }
    
    
    /**
     * Stores a variable/value in the stack segment.
     *   @param variable the variable name
     *   @param val the value to be stored under that name
     */
    public void storeValue(Token variable, DataValue val)  {
        Scope ptr = stackSegment.peek();
        while (ptr!=null){
            if (ptr.isDeclared(variable)){
                int addr = this.getHeapAddress(val);
                ptr.storeAddress(variable, addr);
                break;
            }
            ptr = ptr.parent;
        }
    }
    
    /**
     * Determines the value associated with a variable in memory.
     *   @param variable the variable to look up
     *   @return the value associated with that variable
     */      
    public DataValue lookupValue(Token variable) {
        Scope ptr = stackSegment.peek();
        while (ptr!=null){
            if (ptr.isDeclared(variable)){
                int addr = ptr.lookupAddress(variable);
                return this.heapSegment.get(addr);           
            }
            ptr = ptr.parent;
        }
        return null;
    }

    /**
     * Determines the value type associated with a variable in memory.
     *   @param variable the variable to look up
     *   @return the type associated with that variable
     */ 
    public DataValue.Type lookupType(Token variable) {
        Scope ptr = stackSegment.peek();
        while (ptr!= null){
            if (ptr.isDeclared(variable)){
                return ptr.lookupType(variable);
            }
            ptr = ptr.parent;
        }
        return null;
    }
    
    /////////////////////////////////////////////////////////////////////////////
    
    public int getHeapAddress(DataValue val) {
    	int addr = this.heapSegment.indexOf(val);
    	if (addr == -1) {
    		addr = this.heapSegment.size();
    		this.heapSegment.add(val);
    	}
    	return addr;
    }

    public void addScope(){
        this.stackSegment.add(new Scope(this.stackSegment.peek()));
    }

    public void endScope(){
        this.stackSegment.pop();
    }
}
