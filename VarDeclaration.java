/**
 * Class that declares (and assigns) a global variable.
 *   @author Dave Reed
 *   @version 1/15/23
 */
public class VarDeclaration extends Statement {
    private Token variable;
    private Token variableType;
    private Assignment assign;

    /**
     * Constructs am object representing a global variable declaration. 
     *   @param input the stream to be read from
     */
    public VarDeclaration(TokenStream input) throws Exception {
        this.variableType = input.next();
        if (!this.variableType.toString().equals("int") &&
            !this.variableType.toString().equals("str") &&
            !this.variableType.toString().equals("boo")) {
            throw new Exception("SYNTAX ERROR: Invalid variable declaration (unknown type)");
        }
        
        this.variable = input.lookAhead();
        this.assign = new Assignment(input);
    }
    
    /**
     * Executes the current assignment statement.
     */
    public void execute() throws Exception {
        if (Interpreter.MEMORY.isDeclaredLocal(variable)) {
            throw new Exception("RUNTIME ERROR: variable " + variable + " already declared");            
        }
    
        if (this.variableType.toString().equals("int")) {
        	Interpreter.MEMORY.declareVariable(this.variable, DataValue.Type.INTEGER_VALUE);
        }
        else if (this.variableType.toString().equals("str")) {
        	Interpreter.MEMORY.declareVariable(this.variable, DataValue.Type.STRING_VALUE);
        }
        else {
        	Interpreter.MEMORY.declareVariable(this.variable, DataValue.Type.BOOLEAN_VALUE);
        }
        this.assign.execute();
    }
    
    /**
     * Converts the current assignment statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
        return variableType + " " + this.assign;
    }
}

