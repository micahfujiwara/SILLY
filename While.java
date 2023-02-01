/**
 * Derived class that represents a while statement in the SILLY language.
 *   @author Dave Reed
 *   @version 1/15/23
 */
public class While extends Statement {
    private Expression expr;
    private Compound body;  
    
    /**
     * Reads in a while statement from the specified stream
     *   @param input the stream to be read from
     */
    public While(TokenStream input) throws Exception {
        if (!input.next().toString().equals("while")) {
            throw new Exception("SYNTAX ERROR: Malformed while statement");
        }
        this.expr = new Expression(input);     
        
        if (!input.next().toString().equals("do")) {
            throw new Exception("SYNTAX ERROR: Malformed while statement");
        }
        this.body = new Compound(input);
    }

    /**
     * Executes the current while statement.
     */
    public void execute() throws Exception {
        while (this.expr.evaluate().getType() == DataValue.Type.BOOLEAN_VALUE && ((Boolean) (this.expr.evaluate().getValue()))) {
            this.body.execute();
        }
        if (this.expr.evaluate().getType() != DataValue.Type.BOOLEAN_VALUE) {
            throw new Exception("RUNTIME ERROR: while statement requires Boolean test.");
        }
    }

    
    /**
     * Converts the current while statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
        return "while " + this.expr + " do\n" + this.body;
    }
}
