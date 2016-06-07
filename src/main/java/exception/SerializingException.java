package exception;

/**
 * Created by Mayer Roman on 08.06.2016.
 */
public class SerializingException extends RuntimeException{

    public SerializingException(String message, Throwable exception) {
        super(message, exception);
    }
}
