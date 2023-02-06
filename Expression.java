/**
 * Derived class that represents an expression in the SILLY language.
 *   @author Dave Reed
 *   @version 1/15/23
 */
public class Expression {
    private Token tok;					// used for simple expressions (no operations)
    private Token op;					// used for expressions involving operations
    private Expression expr1, expr2;	// expr2 is used if unary op; both are used for binary

    /**
     * Creates an expression from the specified TokenStream.
     *   @param input the TokenStream from which the program is read
     */
    public Expression(TokenStream input) throws Exception {
    	this.tok = input.next();

        if (this.tok.toString().equals("(")) {
    		if (input.lookAhead().getType() == Token.Type.UNARY_OP) {
    			this.op = input.next();
    			this.expr2 = new Expression(input);
    		} 
    		else {
    			this.expr1 = new Expression(input);
    			if (input.lookAhead().getType() == Token.Type.BINARY_OP) {
    			    this.op = input.next();
    			    this.expr2 = new Expression(input);
    			}
    			else {
    				throw new Exception("SYNTAX ERROR: Malformed expression");
    			}
            }
    		if (!(input.next().toString().equals(")"))) {
    		    throw new Exception("SYNTAX ERROR: Malformed expression");
    		}
        }
    	    else if (this.tok.getType() != Token.Type.IDENTIFIER &&
                this.tok.getType() != Token.Type.INTEGER_LITERAL &&    
                this.tok.getType() != Token.Type.STRING_LITERAL &&
                this.tok.getType() != Token.Type.BOOLEAN_LITERAL) {
            throw new Exception("SYNTAX ERROR: malformed expression" + this.tok);
    	    }
        }

    /**
     * Evaluates the current expression.
     *   @return the value represented by the expression
     */
    public DataValue evaluate() throws Exception {
        if (this.op == null) {
        	if (this.tok.getType() == Token.Type.IDENTIFIER) {
                if (!Interpreter.MEMORY.isDeclared(this.tok)) {
                    throw new Exception("RUNTIME ERROR: variable " + this.tok + " is undeclared");
                }
                return Interpreter.MEMORY.lookupValue(this.tok);
            } 
            
            else if (this.tok.getType() == Token.Type.INTEGER_LITERAL) {
                return new IntegerValue(Integer.valueOf(this.tok.toString()));
            } 
            
            else if (this.tok.getType() == Token.Type.STRING_LITERAL) {
                return new StringValue(this.tok.toString());
            } 
            
            else if (this.tok.getType() == Token.Type.BOOLEAN_LITERAL) {
                return new BooleanValue(Boolean.valueOf(this.tok.toString()));
            }
        } 
        
        else if (this.op.getType() == Token.Type.UNARY_OP) {
            DataValue rhs = this.expr2.evaluate();
            
            if (this.op.toString().equals("not")) {
                if (rhs.getType() == DataValue.Type.BOOLEAN_VALUE) {
                    boolean b2 = ((Boolean) (rhs.getValue()));
                    return new BooleanValue(!b2);
                }
            }
            
            if (this.op.toString().equals("#")){
                if(rhs.getType() == DataValue.Type.STRING_VALUE){
                    String storeSize = ((String) (rhs.getValue()));
                    if (storeSize.charAt(0) == '"') {
                        storeSize =  storeSize.substring(1, storeSize.length()-1);
                    }
                    return new IntegerValue(storeSize.length());
                }
            }
            throw new Exception("RUNTIME ERROR: Type mismatch in unary expression");
        } 
        
        else if (this.op.getType() == Token.Type.BINARY_OP) {
            DataValue lhs = this.expr1.evaluate();
            DataValue rhs = this.expr2.evaluate();

            if (lhs.getType() == rhs.getType()) {
                if (op.toString().equals("==")) {
                    return new BooleanValue(lhs.compareTo(rhs) == 0);
                } 
                else if (op.toString().equals("!=")) {
                    return new BooleanValue(lhs.compareTo(rhs) != 0);
                } 
                else if (op.toString().equals(">")) {
                    return new BooleanValue(lhs.compareTo(rhs) > 0);
                } 
                else if (op.toString().equals(">=")) {
                    return new BooleanValue(lhs.compareTo(rhs) >= 0);
                } 
                else if (op.toString().equals("<")) {
                    return new BooleanValue(lhs.compareTo(rhs) < 0);
                } 
                else if (op.toString().equals("<=")) {
                    return new BooleanValue(lhs.compareTo(rhs) <= 0);
                }
                
                else if (lhs.getType() == DataValue.Type.STRING_VALUE) {
                    if (op.toString().equals("+")) {
                        String str1 = lhs.toString();
                        String str2 = rhs.toString();
                        return new StringValue(str1.substring(0, str1.length() - 1) + str2.substring(1));
                    }
                }

                else if (lhs.getType() == DataValue.Type.INTEGER_VALUE) {
                    int num1 = ((Integer) (lhs.getValue()));
                    int num2 = ((Integer) (rhs.getValue()));

                    if (op.toString().equals("+")) {
                        return new IntegerValue(num1 + num2);
                    } 
                    
                    else if (op.toString().equals("-")) {
                        return new IntegerValue(num1 - num2);
                    } 
                    
                    else if (op.toString().equals("*")) {
                        return new IntegerValue(num1 * num2);
                    } 
                    
                    else if (op.toString().equals("/")) {
                        return new IntegerValue(num1 / num2);
                    } 
                    
                    else if (op.toString().equals("%")) {
                        return new IntegerValue(num1 % num2);
                    } 
                    
                    else if (op.toString().equals("^")) {
                        return new IntegerValue((int)Math.pow(num1, num2));
                    }
                } 
                
                else if (lhs.getType() == DataValue.Type.BOOLEAN_VALUE) {
                    boolean b1 = ((Boolean) (lhs.getValue()));
                    boolean b2 = ((Boolean) (rhs.getValue()));

                    if (op.toString().equals("and")) {
                        return new BooleanValue(b1 && b2);
                    } 
                    else if (op.toString().equals("or")) {
                        return new BooleanValue(b1 || b2);
                    }
                }
            } 
            else if (lhs.getType() == DataValue.Type.STRING_VALUE && rhs.getType() == DataValue.Type.INTEGER_VALUE){
                if (op.toString().equals("@")){
                    String testWord = lhs.toString();
                    int indexKey = Integer.parseInt(rhs.toString());
                    return new StringValue(testWord.substring(indexKey+1, indexKey+2));
                }
            }
            
            throw new Exception("RUNTIME ERROR: Type mismatch in binary expression");
        }
        
        return null;
    }

    /**
     * Converts the current expression into a String.
     *   @return the String representation of this expression
     */
    public String toString() {
        if (this.op == null) {
            return this.tok.toString();
        }
        else if (this.op.getType() == Token.Type.UNARY_OP){
	        return "(" + this.op + " " + this.expr2 + ")";
        }
        else {
	        return "(" + this.expr1 + " " + this.op + " " + this.expr2 + ")";
        }
    }
}
