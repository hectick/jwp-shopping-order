package cart.dao;

import cart.entity.OrderEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class OrderDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertAction;

    public OrderDao(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");
    }

    public long insert(final OrderEntity orderEntity) {
        Map<String, Object> params = new HashMap<>();
        params.put("member_id", orderEntity.getMemberId());
        params.put("shipping_fee", orderEntity.getShippingFee());
        params.put("total_products_price", orderEntity.getTotalProductsPrice());
        params.put("used_point", orderEntity.getUsedPoint());
        params.put("created_at", new Timestamp(System.currentTimeMillis()));
        return insertAction.executeAndReturnKey(params).longValue();
    }

    private final RowMapper<OrderEntity> rowMapper = (rs, rowNum) -> {
        return new OrderEntity(
                rs.getLong("id"),
                rs.getLong("member_id"),
                rs.getLong("shipping_fee"),
                rs.getLong("total_products_price"),
                rs.getLong("used_point"),
                rs.getString("created_at")
        );
    };

    public Optional<OrderEntity> findById(final long id) {
        try {
            String sql = "SELECT * FROM orders WHERE id = ?";
            return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public List<OrderEntity> findAllByMemberId(final long memberId) {
        String sql = "SELECT * FROM orders WHERE member_id = ?";
        return jdbcTemplate.query(sql, rowMapper, memberId);
    }
}
