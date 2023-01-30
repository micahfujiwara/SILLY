/**
 * Derived class that represents an output statement in the SILLY language.
 *   @author Dave Reed
 *   @version 1/15/23
 */
public class Output extends Statement {
	private Expression expr;

    /**
     * Reads in an output statement from the specified TokenStream.
     *   @param input the stream to be read from
     */
    public Output(TokenStream input) throws Exception {
    	if (!input.next().toString().equals("output")) {
            throw new Exception("SYNTAX ERROR: Malformed output statement");
        } 
    	
    	this.expr = new Expression(input);
    }

    /**
     * Executes the current output statement.
     */
    public void execute() throws Exception {
    	String str = this.expr.evaluate().toString();
    	if (str.charAt(0) == '"') {
    		str =  str.substring(1, str.length()-1);
    	}

    	System.out.println(str);
    }

    /**
     * Converts the current output statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
    	return "output " + this.expr;
    }   
}


