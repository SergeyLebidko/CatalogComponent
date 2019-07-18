package catalogcomponent.dataaccess;

import catalogcomponent.dataelements.TreeElement;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CatalogDAO {

    private JdbcTemplate jdbcTemplate;

    public CatalogDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TreeElement> getTreeElementList(){
        return null;
    }

}
