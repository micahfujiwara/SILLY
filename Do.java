/**
 * Derived class that represents a do statement in the SILLY language.
 *   @author Micah Fujiwara
 *   @version 2/12/23
 */
public class Do extends Statement {
    private Expression expr;
    private Compound body;
    
    /**
     * Reads in a do statement from the specified stream
     *   @param input the stream to be read from
     */
    public Do(TokenStream input) throws Exception{
        if (!input.next().toString().equals("do")) {
            throw new Exception("SYNTAX ERROR: Malformed do statement");
        }
        this.body = new Compound(input);

        if (!input.next().toString().equals("until")){
            throw new Exception("SYNTAX ERROR: Malformed do statement");
        }
        this.expr = new Expression(input);
    }

    @Override
     /**
     * Executes the current while statement.
     */
    public void execute() throws Exception {
        if (this.expr.evaluate().getType() != DataValue.Type.BOOLEAN_VALUE){
            throw new Exception("RUNTIME ERROR: do statement requires Boolean test.");
        }

        //executes at least once 
        this.body.execute();

        while (this.expr.evaluate().getType() == DataValue.Type.BOOLEAN_VALUE && !((Boolean) (this.expr.evaluate().getValue()))) {
            this.body.execute();
        }    
    }   

      /**
     * Converts the current while statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
        return "do " + this.body + " until\n" + this.expr;
    }
}
