package catalogcomponent.dataaccess;

import catalogcomponent.dataelements.TreeElement;
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

    public List<TreeElement> getTreeElementList() {
        RowMapper<TreeElement> rowMapper = new RowMapper<TreeElement>() {
            @Override
            public TreeElement mapRow(ResultSet resultSet, int i) throws SQLException {
                int id = resultSet.getInt(1);
                Integer parentId = resultSet.getInt(2);
                String name = resultSet.getString(3);
                return new TreeElement(id, parentId, name);
            }
        };
        List<TreeElement> list = jdbcTemplate.query("SELECT * FROM GROUPS", rowMapper);
        return list;
    }

}
