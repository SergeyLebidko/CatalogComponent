package catalogcomponent;

import catalogcomponent.dataaccess.CatalogDAO;
import catalogcomponent.dataelements.Group;
import catalogcomponent.dataelements.Product;

import java.util.List;

public class ActionHandler {

    private CatalogDAO catalogDAO;
    private UniTree uniTree;

    public void setCatalogDAO(CatalogDAO catalogDAO) {
        this.catalogDAO = catalogDAO;
    }

    public void setUniTree(UniTree uniTree) {
        this.uniTree = uniTree;
    }

    public void showCatalog() {
        List<Group> groupList = catalogDAO.getGroupList();
        List<Product> productList = catalogDAO.getProductList();
        uniTree.setContent(groupList, productList);
    }

}
