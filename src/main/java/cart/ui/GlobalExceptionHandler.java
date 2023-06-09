package cart.ui;

import cart.dto.response.exception.CartItemIdExceptionResponse;
import cart.dto.response.exception.ExceptionResponse;
import cart.dto.response.exception.Payload;
import cart.exception.AuthenticationException;
import cart.exception.CartItemException;
import cart.exception.ExceptionCode;
import cart.exception.OrderException;
import cart.exception.ProductException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.EnumMap;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final EnumMap<ExceptionCode, String> exceptionMessages = new EnumMap<>(ExceptionCode.class);

    public GlobalExceptionHandler() {
        exceptionMessages.put(ExceptionCode.AE0001, "존재하지 않는 회원입니다");

        exceptionMessages.put(ExceptionCode.CIE0001, "잘못된 요청입니다");
        exceptionMessages.put(ExceptionCode.CIE0002, "더 이상 존재하지 않는 상품입니다");
        exceptionMessages.put(ExceptionCode.CIE0003, "이미 장바구니에 존재하는 상품입니다");
        exceptionMessages.put(ExceptionCode.CIE0004, "등록되지 않은 상품이 포함되어 있습니다. 다시 한번 확인해주세요");
        exceptionMessages.put(ExceptionCode.CIE0005, "문제가 발생했습니다. 상품의 수량을 다시 한번 확인해주세요");
        exceptionMessages.put(ExceptionCode.CIE0006, "상품 정보에 변동사항이 존재합니다. 금액을 다시 한번 확인해주세요");

        exceptionMessages.put(ExceptionCode.OE0001, "존재하지 않는 주문 내역입니다");
        exceptionMessages.put(ExceptionCode.OE0002, "잘못된 요청입니다");
        exceptionMessages.put(ExceptionCode.OE0003, "포인트가 부족합니다");
        exceptionMessages.put(ExceptionCode.OE0004, "포인트는 양수여야 합니다");
        exceptionMessages.put(ExceptionCode.OE0005, "잘못된 요청입니다");

        exceptionMessages.put(ExceptionCode.PE0001, "존재하지 않는 상품입니다");
        exceptionMessages.put(ExceptionCode.PE0002, "이미 존재하는 상품입니다");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Payload> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        log.error(exception.getMessage());
        String errorMessage = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(System.lineSeparator()));
        ExceptionResponse response = new ExceptionResponse(errorMessage);
        return ResponseEntity.badRequest().body(new Payload(response));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Payload> handlerAuthenticationException(AuthenticationException e) {
        log.error(e.getMessage(), e);
        ExceptionResponse response = new ExceptionResponse(exceptionMessages.get(e.getExceptionCode()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Payload(response));
    }

    @ExceptionHandler(OrderException.IllegalMember.class)
    public ResponseEntity<Payload> handleException(OrderException.IllegalMember e) {
        log.error(e.getMessage(), e);
        ExceptionResponse response = new ExceptionResponse(exceptionMessages.get(e.getExceptionCode()));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Payload(response));
    }

    @ExceptionHandler(CartItemException.IllegalMember.class)
    public ResponseEntity<Payload> handleException(CartItemException.IllegalMember e) {
        log.error(e.getMessage(), e);
        ExceptionResponse response = new ExceptionResponse(exceptionMessages.get(e.getExceptionCode()));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Payload(response));
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<Payload> handleException(OrderException e) {
        log.error(e.getMessage(), e);
        ExceptionResponse response = new ExceptionResponse(exceptionMessages.get(e.getExceptionCode()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Payload(response));
    }

    @ExceptionHandler(CartItemException.UnknownCartItem.class)
    public ResponseEntity<Payload> handleException(CartItemException.UnknownCartItem e) {
        log.error(e.getMessage(), e);
        ExceptionResponse response = new CartItemIdExceptionResponse(exceptionMessages.get(e.getExceptionCode()), e.getUnknownCartItemIds());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Payload(response));
    }

    @ExceptionHandler(CartItemException.class)
    public ResponseEntity<Payload> handleException(CartItemException e) {
        log.error(e.getMessage(), e);
        ExceptionResponse response = new ExceptionResponse(exceptionMessages.get(e.getExceptionCode()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Payload(response));
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<Payload> handleException(ProductException e) {
        log.error(e.getMessage(), e);
        ExceptionResponse response = new ExceptionResponse(exceptionMessages.get(e.getExceptionCode()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Payload(response));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Payload> handleException(Exception e) {
        log.error(e.getMessage(), e);
        ExceptionResponse response = new ExceptionResponse("서버에 장애가 발생하였습니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Payload(response));
    }

}
