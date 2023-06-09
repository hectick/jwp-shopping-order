package cart.exception;

import java.util.List;

public class CartItemException extends RuntimeException {
    
    private final ExceptionCode exceptionCode;
    
    public CartItemException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public static class IllegalMember extends CartItemException {
        public IllegalMember() {
            super(ExceptionCode.CIE0001);
        }
    }

    public static class InvalidCartItem extends CartItemException {
        public InvalidCartItem() {
            super(ExceptionCode.CIE0002);
        }
    }

    public static class DuplicatedCartItem extends CartItemException {
        public DuplicatedCartItem() {
            super(ExceptionCode.CIE0003);
        }
    }

    public static class UnknownCartItem extends CartItemException {

        private final List<Long> unknownCartItemIds;

        public UnknownCartItem(final List<Long> unknownCartItemIds) {
            super(ExceptionCode.CIE0004);
            this.unknownCartItemIds = unknownCartItemIds;
        }

        public List<Long> getUnknownCartItemIds() {
            return unknownCartItemIds;
        }
    }

    public static class QuantityNotSame extends CartItemException {

        public QuantityNotSame() {
            super(ExceptionCode.CIE0005);
        }

    }

    public static class TotalPriceNotSame extends CartItemException {
        public TotalPriceNotSame() {
            super(ExceptionCode.CIE0006);
        }
    }
}
