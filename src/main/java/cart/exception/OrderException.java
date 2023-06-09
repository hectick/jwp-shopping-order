package cart.exception;

public class OrderException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public OrderException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public static class InvalidOrder extends OrderException {
        public InvalidOrder() {
            super(ExceptionCode.OE0001);
        }
    }

    public static class IllegalMember extends OrderException {
        public IllegalMember() {
            super(ExceptionCode.OE0002);
        }
    }

    public static class LackOfPoint extends OrderException {
        public LackOfPoint() {
            super(ExceptionCode.OE0003);
        }
    }

    public static class NegativePoint extends OrderException {
        public NegativePoint() {
            super(ExceptionCode.OE0004);
        }
    }

    public static class OveruseOfPoint extends OrderException {
        public OveruseOfPoint() {
            super(ExceptionCode.OE0005);
        }
    }
}
