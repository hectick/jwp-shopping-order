package cart.repository;

import cart.dao.MemberDao;
import cart.dao.OrderDao;
import cart.dao.OrderItemDao;
import cart.domain.Member;
import cart.domain.Order;
import cart.domain.OrderItem;
import cart.entity.OrderEntity;
import cart.exception.AuthenticationException.InvalidMember;
import cart.exception.OrderException.InvalidOrder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class OrderRepository {

    private final MemberDao memberDao;
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;

    public OrderRepository(final MemberDao memberDao, final OrderDao orderDao, final OrderItemDao orderItemDao) {
        this.memberDao = memberDao;
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
    }

    public long save(final Order order) {
        OrderEntity orderEntity = order.toEntity();
        long orderId = orderDao.insert(orderEntity);
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItemDao.insert(orderId, orderItem);
        }
        return orderId;
    }

    public Order findByOrderId(final long orderId) {
        OrderEntity orderEntity = orderDao.findById(orderId)
                .orElseThrow(InvalidOrder::new);
        Member member = memberDao.getMemberById(orderEntity.getMemberId())
                .orElseThrow(InvalidMember::new);
        List<OrderItem> orderItems = orderItemDao.findAllByOrderId(orderId);
        return Order.of(member, orderEntity, orderItems);
    }

    public List<Order> findByMemberId(final long memberId) {
        List<Order> orders = new ArrayList<>();
        Member member = memberDao.getMemberById(memberId)
                .orElseThrow(InvalidMember::new);
        List<OrderEntity> orderEntities = orderDao.findAllByMemberId(memberId);
        if (orderEntities.isEmpty()) {
            return Collections.emptyList();
        }
        for (OrderEntity orderEntity : orderEntities) {
            List<OrderItem> orderItems = orderItemDao.findAllByOrderId(orderEntity.getId());
            orders.add(Order.of(member, orderEntity, orderItems));
        }
        return orders;
    }
}
