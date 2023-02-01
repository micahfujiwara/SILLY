/**
 * Class that stores the bindings associated with a variable (name, type & value).
 *   @author Dave Reed
 *   @version 1/15/23
 */
public class VariableBinding {
    private DataValue.Type type;
    private int address;
    
    /**
     * Constructs a variable binding object.
     *   @param t the type of the variable
     */
    public VariableBinding(DataValue.Type t) {
    	this.type = t;
        //-1 is equal to null 
    	this.address = -1;
    }
    
    /** 
     * Accessor method for the variable type.
     *   @return the variable type (a DataValue.Type)
     */
    public DataValue.Type getType() {
    	return this.type;
    }
    
    /** 
     * Accessor method for the variable address.
     *   @return the variable value (a DataValue)
     */
    public int getAddress() {
    	return this.address;
    }
    
    /** 
     * Setter method for the variable address.
     *   @param a the address in the heap where the new value is stored
     */
    public void setAddress(int a) {
    	this.address = a;
    }
}
