package nl.cge.jbh.common;

public class ApplicationException extends RuntimeException {

    public ApplicationException(Throwable e) {
        super(e);
    }

    public static ApplicationException wrap(Throwable e) {
        return new ApplicationException(e);
    }
}
