import java.util.ArrayList;

/**
 * Derived class that represents an output statement in the SILLY language.
 *   @author Dave Reed
 *   @version 1/15/23
 */
public class Output extends Statement {
	private Expression expr;
    private boolean multExpr;
    private ArrayList<Expression> exprList = new ArrayList<>();
    private String totalStr = "";
    private String str = "";

    /**
     * Reads in an output statement from the specified TokenStream.
     *   @param input the stream to be read from
     */
    public Output(TokenStream input) throws Exception {
    	if (!input.next().toString().equals("output")) {
            throw new Exception("SYNTAX ERROR: Malformed output statement");
        } 

        if (input.lookAhead().toString().equals("{")){
            input.next();
            multExpr = true;
            while (!input.lookAhead().toString().equals("}")){
                this.expr = new Expression(input);
                exprList.add(this.expr);

                if (input.lookAhead().toString().equals(",")){
                    input.next();
                }
            }
        }
        else{
            this.expr = new Expression(input);
        }
    }
    /**
     * Executes the current output statement.
     */
    public void execute() throws Exception {
        if (multExpr == true){
            for (int i = 0; i < exprList.size(); i++){
                str = exprList.get(i).evaluate().toString();
                
                if (str.charAt(0) == '"'){
                    str = str.substring(1, str.length()-1);
                }
                if (i <= exprList.size()-2){
                    totalStr += str + " ";
                }
                if (i==exprList.size()-1){
                    totalStr += str;
                }
            }
            System.out.println(totalStr);
        }

        else{
            str = this.expr.evaluate().toString();
            if (str.charAt(0) == '"') {
                str = str.substring(1, str.length()-1);
            }
            System.out.println(str);
        }
        
    }

    /**
     * Converts the current output statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() { 
        if (multExpr == true){
            return "ouput " + this.totalStr;
        }
    	return "output " + this.expr;
    }   
}

