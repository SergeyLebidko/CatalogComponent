package catalogcomponent.dataaccess;

import catalogcomponent.dataelements.Group;
import catalogcomponent.dataelements.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CatalogDAO {

    private JdbcTemplate jdbcTemplate;

    public CatalogDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Group> getGroupList() {
        RowMapper<Group> rowMapper = new RowMapper<Group>() {
            @Override
            public Group mapRow(ResultSet resultSet, int i) throws SQLException {
                int id = resultSet.getInt(1);
                Integer parentId = resultSet.getInt(2);
                if (resultSet.wasNull()) parentId = null;
                String name = resultSet.getString(3);
                return new Group(id, parentId, name);
            }
        };
        List<Group> list = jdbcTemplate.query("SELECT * FROM GROUPS", rowMapper);
        return list;
    }

    public List<Product> getProductList(){
        RowMapper<Product> rowMapper = new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                int groupId = resultSet.getInt(1);
                int id = resultSet.getInt(2);
                String name = resultSet.getString(3);
                String specification = resultSet.getString(4);
                String state = resultSet.getString(5);
                int price = resultSet.getInt(6);
                int count = resultSet.getInt(7);
                return new Product(groupId, id, name, specification, state, price, count);
            }
        };
        List<Product> list = jdbcTemplate.query("SELECT * FROM ITEMS", rowMapper);
        return list;
    }

}
