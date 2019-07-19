package catalogcomponent;

import catalogcomponent.dataaccess.CatalogDAO;
import catalogcomponent.dataelements.Group;
import catalogcomponent.dataelements.Product;

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
        List<Group> groupList = catalogDAO.getGroupList();
        List<Product> productList = catalogDAO.getProductList();
        uniCatalogPane.setContent(groupList, productList);
    }

}
