package catalogcomponent.dataaccess;

import catalogcomponent.dataelements.Group;
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

    public List<Group> getTreeElementList() {
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

}
