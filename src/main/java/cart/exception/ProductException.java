package cart.exception;

public class ProductException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public ProductException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public static class InvalidProduct extends ProductException {
        public InvalidProduct() {
            super(ExceptionCode.PE0001);
        }
    }

    public static class DuplicatedProduct extends ProductException {
        public DuplicatedProduct() {
            super(ExceptionCode.PE0002);
        }
    }
}
