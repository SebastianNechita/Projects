package exceptions;

public class InvalidSymbolException extends RuntimeException{
    public InvalidSymbolException(String msg){
        super(msg);
    }
}
