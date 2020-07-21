public class UnderflowException extends Exception {

    public UnderflowException() {
        super();
    }

    public UnderflowException(String message) {
        super(message);
    }

    public UnderflowException(Throwable whereItCameFrom) {
        super(whereItCameFrom);
    }

    public UnderflowException(String message,Throwable whereItCameFrom) {
        super(message,whereItCameFrom);
    }
    

} // end class
