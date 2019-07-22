package catalogcomponent;

import catalogcomponent.dataaccess.CatalogDAO;
import catalogcomponent.dataelements.Group;
import catalogcomponent.dataelements.Product;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        String name = showInputNameDialog();
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
        if (catalogDAO.isRootGroup(selectedGroup.getId())) return;

        String name = showInputNameDialog(selectedGroup.getName());
        if (name == null) return;
        int id = selectedGroup.getId();

        catalogDAO.editGroup(id, name);
        uniTree.editGroup(id, name);
    }

    public void removeGroup() {
        Group selectedGroup = uniTree.getSelectedGroup();
        if (selectedGroup == null) return;
        if (catalogDAO.isRootGroup(selectedGroup.getId())) return;

        catalogDAO.removeGroup(selectedGroup.getId());
        uniTree.removeGroup(selectedGroup.getId());
    }

    public void addElement() {
        showInputProductDialog();
        //Вставить код
    }

    public void editElement() {
        //Вставить код
    }

    public void removeElement() {
        //Вставить код
    }

    private String showInputNameDialog() {
        return showInputNameDialog("");
    }

    private String showInputNameDialog(String startText) {
        String str;
        while (true) {
            str = JOptionPane.showInputDialog(null, "", startText);
            if (str == null) return null;
            str = str.trim();
            if (str.equals("")) continue;
            break;
        }
        return str;
    }

    private Map<String, Object> showInputProductDialog() {
        return showInputProductDialog(null);
    }

    private Map<String, Object> showInputProductDialog(Product product) {
        JTextField idField = new JTextField(15);
        idField.setEditable(false);
        idField.setText(product == null ? "" : product.getField(0).toString());

        JTextField nameField = new JTextField(15);
        nameField.setText(product == null ? "" : product.getField(1).toString());

        JTextField specificationField = new JTextField(15);
        specificationField.setText(product == null ? "" : product.getField(2).toString());

        JTextField stateField = new JTextField(15);
        stateField.setText(product == null ? "" : product.getField(3).toString());

        JTextField priceField = new JTextField(15);
        priceField.setText(product == null ? "" : product.getField(4).toString());

        JTextField countField = new JTextField(15);
        countField.setText(product == null ? "" : product.getField(5).toString());

        Box contentBox = Box.createVerticalBox();
        JPanel idPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel namePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel specificationPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel statePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pricePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel countPane = new JPanel(new FlowLayout(FlowLayout.LEFT));

        idPane.add(new JLabel("Номер"));
        idPane.add(Box.createHorizontalStrut(5));
        idPane.add(idField);

        namePane.add(new JLabel("Наименование"));
        namePane.add(Box.createHorizontalStrut(5));
        namePane.add(nameField);

        specificationPane.add(new JLabel("Спецификация"));
        specificationPane.add(Box.createHorizontalStrut(5));
        specificationPane.add(specificationField);

        statePane.add(new JLabel("Состояние"));
        statePane.add(Box.createHorizontalStrut(5));
        statePane.add(stateField);

        pricePane.add(new JLabel("Цена"));
        pricePane.add(Box.createHorizontalStrut(5));
        pricePane.add(priceField);

        countPane.add(new JLabel("Количество"));
        countPane.add(Box.createHorizontalStrut(5));
        countPane.add(countField);

        contentBox.add(idPane);
        contentBox.add(namePane);
        contentBox.add(specificationPane);
        contentBox.add(statePane);
        contentBox.add(pricePane);
        contentBox.add(countPane);

        int answer;
        String name;
        String specification;
        String state;
        Integer price = 0;
        Integer count = 0;
        while (true) {
            answer = JOptionPane.showConfirmDialog(null, contentBox, "", JOptionPane.OK_CANCEL_OPTION);
            if (answer != 0) {
                return null;
            }

            name = nameField.getText().trim();
            specification = specificationField.getText().trim();
            state = stateField.getText();

            if (name.equals("") || specification.equals("") || state.equals("")) continue;

            try {
                price = Integer.parseInt(priceField.getText().trim());
                count = Integer.parseInt(countField.getText().trim());
            } catch (Exception e) {
                continue;
            }
            break;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("specification", specification);
        map.put("state", state);
        map.put("price", price);
        map.put("count", count);

        return map;
    }

}
