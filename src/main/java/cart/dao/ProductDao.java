package cart.dao;

import cart.domain.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertAction;

    public ProductDao(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long productId = rs.getLong("id");
            String name = rs.getString("name");
            long price = rs.getLong("price");
            String imageUrl = rs.getString("image_url");
            return new Product(productId, name, price, imageUrl);
        });
    }

    public Optional<Product> getProductById(Long productId) {
        try {
            String sql = "SELECT * FROM product WHERE id = ?";
            return Optional.of(jdbcTemplate.queryForObject(sql, new Object[]{productId}, (rs, rowNum) -> {
                String name = rs.getString("name");
                long price = rs.getLong("price");
                String imageUrl = rs.getString("image_url");
                return new Product(productId, name, price, imageUrl);
            }));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public Long createProduct(Product product) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", product.getName());
        params.put("price", product.getPrice());
        params.put("image_url", product.getImageUrl());
        return insertAction.executeAndReturnKey(params).longValue();
    }

    public int updateProduct(Long productId, Product product) {
        String sql = "UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?";
        return jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), productId);
    }

    public int deleteProduct(Long productId) {
        String sql = "DELETE FROM product WHERE id = ?";
        return jdbcTemplate.update(sql, productId);
    }

    public int countByProduct(final Product product) {
        String sql = "SELECT COUNT(*) AS count " +
                "FROM product " +
                "WHERE name = ? AND price = ? AND image_url = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, product.getName(), product.getPrice(), product.getImageUrl());
    }
}
