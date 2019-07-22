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
        return jdbcTemplate.query("SELECT * FROM GROUPS", rowMapper);
    }

    public List<Product> getProductList() {
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
        return jdbcTemplate.query("SELECT * FROM ITEMS", rowMapper);
    }

    public int getNextGroupId() {
        return jdbcTemplate.queryForObject("SELECT MAX(ID) FROM GROUPS", Integer.class) + 1;
    }

    public int getNextProductId() {
        return jdbcTemplate.queryForObject("SELECT MAX(ID) FROM ITEMS", Integer.class) + 1;
    }

    public void addGroup(Group group) {
        jdbcTemplate.update("INSERT INTO GROUPS (ID, PARENT_ID, NAME) VALUES(?,?,?)",
                group.getId(),
                group.getParentId(),
                group.getName());
    }

    public void editGroup(int id, String name) {
        jdbcTemplate.update("UPDATE GROUPS SET NAME=? WHERE ID=?", name, id);
    }

    public void removeGroup(int id) {
        //Получаем список подгрупп
        List<Integer> subGroupsList = jdbcTemplate.queryForList("SELECT ID FROM GROUPS WHERE PARENT_ID=?",
                new Object[]{id},
                Integer.class);

        //Удаляем все подгруппы
        for (Integer subGroupId : subGroupsList) {
            removeGroup(subGroupId);
        }

        //Удаляем переданную группу
        jdbcTemplate.update("DELETE FROM GROUPS WHERE ID=?", id);

        //Удаляем все элементы этой группы
        jdbcTemplate.update("DELETE FROM ITEMS WHERE GROUP_ID=?", id);
    }

    public void addProduct(Product product) {
        jdbcTemplate.update("INSERT INTO ITEMS (GROUP_ID, ID, NAME, SPECIFICATION, STATE, PRICE, COUNT) VALUES (?, ?, ?, ?, ?, ?, ?)",
                product.getGroupId(),
                product.getField(0),
                product.getField(1),
                product.getField(2),
                product.getField(3),
                product.getField(4),
                product.getField(5));
    }

    public void editProduct(int id, Product product) {
        jdbcTemplate.update("DELETE FROM ITEMS WHERE ID=?", id);
        addProduct(product);
    }

    public void removeProduct(int id) {
        jdbcTemplate.update("DELETE FROM ITEMS WHERE ID=?", id);
    }

    public boolean isRootGroup(int id) {
        int rootGroupId = jdbcTemplate.queryForObject("SELECT ID FROM GROUPS WHERE PARENT_ID is NULL", Integer.class);
        return rootGroupId == id;
    }

}
