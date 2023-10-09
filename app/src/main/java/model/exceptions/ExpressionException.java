package model.exceptions;

public class ExpressionException extends Exception {
    public ExpressionException(String errMsg) {
        super(errMsg);
    }
    public ExpressionException(String errMsg, Throwable err) {
        super(errMsg, err);
    }
}
