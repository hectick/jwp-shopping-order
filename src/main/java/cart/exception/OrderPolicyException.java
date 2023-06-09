package cart.exception;

public class OrderPolicyException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public OrderPolicyException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public static class NoShippingFee extends OrderPolicyException {
        public NoShippingFee() {
            super(ExceptionCode.OPE0001, "배송비를 가져올 수 없습니다");
        }
    }

    public static class NoShippingDiscountThreshold extends OrderPolicyException {
        public NoShippingDiscountThreshold() {
            super(ExceptionCode.OPE0002, "배송비 할인 기준 금액을 가져올 수 없습니다");
        }
    }
}
