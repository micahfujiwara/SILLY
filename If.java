/**
 * Derived class that represents an if statement in the SILLY language.
 *   @author Dave Reed
 *   @version 1/15/23
 */
public class If extends Statement {
    private Expression test;
    private Compound ifBody;
    private Compound elseBody;
    //private Expression elseTest;
    private boolean elseCheck = false;

    /**
     * Reads in an if statement from the specified stream
     *   @param input the stream to be read from
     */
    public If(TokenStream input) throws Exception {
        if (!input.next().toString().equals("if")) {
            throw new Exception("SYNTAX ERROR: Malformed if statement");
        }
        this.test = new Expression(input);
        
        if (!input.next().toString().equals("then")) {
            throw new Exception("SYNTAX ERROR: Malformed if statement");
        }
        this.ifBody = new Compound(input);

        if (!input.next().toString().equals("noelse") || !input.next().toString().equals("else")) {
            throw new Exception("SYNTAX ERROR: Malformed if statement");
        }

        if (input.next().toString().equals("else")){
            elseCheck = true;
        }
        this.elseBody = new Compound(input);
    }

    /**
     * Executes the current if statement.
     */
    public void execute() throws Exception {
        DataValue test = this.test.evaluate();
        //DataValue otherTest = this.elseTest.evaluate();
        if (test.getType() != DataValue.Type.BOOLEAN_VALUE) {
            throw new Exception("RUNTIME ERROR: If statement requires Boolean test.");
        } 
        
        if (((Boolean) test.getValue())) {
            this.ifBody.execute();
        } 

        if (elseCheck == true){
            this.elseBody.execute();
        }
        
    }

    /**
     * Converts the current if statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
        if (elseCheck == true){
            return "if " + this.test + " then\n" + this.ifBody + "\nelse\n" + this.elseBody;  
        }
        return "if " + this.test + " then\n" + this.ifBody + "\nnoelse";
    }
}
