package catalogcomponent;

import catalogcomponent.dataaccess.CatalogDAO;
import catalogcomponent.dataelements.GroupElement;

import java.util.List;

public class ActionHandler {

    private CatalogDAO catalogDAO;
    private UniTree uniTree;

    public ActionHandler() {
    }

    public void setCatalogDAO(CatalogDAO catalogDAO) {
        this.catalogDAO = catalogDAO;
    }

    public void setUniTree(UniTree uniTree) {
        this.uniTree = uniTree;
    }

    public void showCatalog() {
        List<GroupElement> list = catalogDAO.getTreeElementList();
        uniTree.setContent(list);
    }

}
