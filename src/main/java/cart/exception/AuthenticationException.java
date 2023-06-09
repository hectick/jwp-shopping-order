package cart.exception;

public class AuthenticationException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public AuthenticationException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public static class InvalidMember extends AuthenticationException {
        public InvalidMember() {
            super(ExceptionCode.AE0001);
        }
    }
}
