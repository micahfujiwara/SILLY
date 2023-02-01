import java.util.Arrays;
import java.util.Scanner;
import java.io.File;

/**
 * Class for reading SILLY language tokens from an input stream, either
 * standard input or from a file.
 *   @author Dave Reed
 *   @version 1/15/23
 */
public class TokenStream {
    private Scanner input;
    private Token nextToken;
    private String buffer;

    /**
     * Constructs a TokenStream connected to System.in.
     */
    public TokenStream() {
        this.input = new Scanner(System.in);
        this.buffer = "";
    }
    
    /**
     * Constructs a TokenStream connected to a file.
     *   @param filename the file to read from
     */
    public TokenStream(String filename) throws java.io.FileNotFoundException {
        this.input = new Scanner(new File(filename));
        this.buffer = "";
    }

    /**
     * Returns the next token in the TokenStream (without removing it).
     *   @return the next token
     */
    public Token lookAhead() {
    	if (this.nextToken == null) {    		
            if (this.buffer.equals("") && this.input.hasNext()) {
            	this.buffer = this.input.next().strip();
            }
            
            int index = 1;
            if (Character.isLetter(this.buffer.charAt(0))) {
            	while (index < this.buffer.length() && (Character.isLetter(this.buffer.charAt(index)) || Character.isDigit(this.buffer.charAt(index)))) {
            		index++;
            	}
        	}
            else if (Character.isDigit(this.buffer.charAt(0))) {
            	while (index < this.buffer.length() && Character.isDigit(this.buffer.charAt(index))) {
            		index++;
            	}
        	}
            else if (this.buffer.charAt(0) == '"') {
            	while (index < this.buffer.length() && this.buffer.charAt(index) != '"') {
            		index++;
            	}
            	index++;
            }
            else if (this.buffer.length() > 1 && Arrays.asList(Token.binary_ops).contains(this.buffer.substring(0, 2))) {
            	index = 2;
            }
            this.nextToken = new Token(this.buffer.substring(0, index));
        }
        return this.nextToken;
    }
    
    /**
     * Returns the next token in the TokenStream (and removes it).
     *   @return the next token
     */
    public Token next() {
        Token safe = this.lookAhead();
        this.nextToken = null;
        this.buffer = this.buffer.substring(safe.toString().length()).strip();
        return safe;
     }
     
     /**
      * Determines whether there are any more tokens to read.
      *   @return true if tokens remaining, else false
      */
     public boolean hasNext() {
        return (this.nextToken != null || !this.buffer.equals("") || this.input.hasNext());
     }
}
