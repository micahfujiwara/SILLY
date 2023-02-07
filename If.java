/**
 * Derived class that represents an if statement in the SILLY language.
 *   @author Dave Reed
 *   @version 1/15/23
 */
public class If extends Statement {
    private Expression test;
    private Compound ifBody;
    private Compound elseBody;
    private boolean elseCheck;

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

        if (input.lookAhead().toString().equals("else")){
            input.next();
            this.elseBody = new Compound(input);
            this.elseCheck = true;
        }
        
        else if (!input.next().toString().equals("noelse")){
            this.elseCheck = false;
            throw new Exception("SYNTAX ERROR: Malformed if statement");
        }
    }

    /**
     * Executes the current if statement.
     */
    public void execute() throws Exception {
        DataValue test = this.test.evaluate();
        if (test.getType() != DataValue.Type.BOOLEAN_VALUE) {
            throw new Exception("RUNTIME ERROR: If statement requires Boolean test.");
        } 
        
        if (((Boolean) test.getValue())) {
            this.ifBody.execute();
        } 

        if (!((Boolean) test.getValue()) && elseCheck==true){
            this.elseBody.execute();
        }
    }

    /**
     * Converts the current if statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
        if (this.elseCheck == true){
            return "if " + this.test + " then\n" + this.ifBody + "\nelse\n" + this.elseBody;
        }
        return "if " + this.test + " then\n" + this.ifBody + "\nnoelse";
    }
}