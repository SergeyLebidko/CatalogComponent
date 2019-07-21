package catalogcomponent;

import catalogcomponent.dataaccess.CatalogDAO;
import catalogcomponent.dataelements.Group;
import catalogcomponent.dataelements.Product;

import javax.swing.*;
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

    public void addGroup() {
        Group selectedGroup = uniTree.getSelectedGroup();
        if (selectedGroup == null) return;

        String name = showInputNameDialog("");
        if (name == null) return;
        int id = catalogDAO.getNextGroupId();
        int parentId = selectedGroup.getId();

        Group group = new Group(id, parentId, name);
        catalogDAO.addGroup(group);

        uniTree.addGroup(group);
    }

    public void editGroup() {
        Group selectedGroup = uniTree.getSelectedGroup();
        if (selectedGroup == null) return;

        String name = showInputNameDialog(selectedGroup.getName());
        if (name == null) return;
        int id = selectedGroup.getId();

        catalogDAO.editGroup(id, name);

        uniTree.editGroup(id, name);
    }

    public void removeGroup(){
        Group selectedGroup = uniTree.getSelectedGroup();
        if (selectedGroup == null) return;

        //Удаление группы из базы данных

        uniTree.removeGroup(selectedGroup.getId());
    }

    private String showInputNameDialog(String startText) {
        String str = "";
        while (true) {
            str = JOptionPane.showInputDialog(null, "", startText);
            if (str == null) return null;
            str = str.trim();
            if (str.equals("")) continue;
            break;
        }
        return str;
    }

}
