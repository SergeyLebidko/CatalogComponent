package catalogcomponent;

import catalogcomponent.dataaccess.CatalogDAO;
import catalogcomponent.dataelements.Group;

import java.util.List;

public class ActionHandler {

    private CatalogDAO catalogDAO;
    private UniCatalogPane uniCatalogPane;

    public ActionHandler() {
    }

    public void setCatalogDAO(CatalogDAO catalogDAO) {
        this.catalogDAO = catalogDAO;
    }

    public void setUniCatalogPane(UniCatalogPane uniCatalogPane) {
        this.uniCatalogPane = uniCatalogPane;
    }

    public void showCatalog() {
        List<Group> groupList = catalogDAO.getTreeElementList();
        uniCatalogPane.setContent(groupList, null);
    }

}
