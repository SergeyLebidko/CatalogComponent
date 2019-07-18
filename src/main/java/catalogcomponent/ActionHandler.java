package catalogcomponent;

import catalogcomponent.dataaccess.CatalogDAO;
import catalogcomponent.dataelements.TreeElement;

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
        List<TreeElement> list = catalogDAO.getTreeElementList();
        for (TreeElement element : list) {
            System.out.println(element);
        }
    }

}
