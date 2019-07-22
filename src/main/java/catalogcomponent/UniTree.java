package catalogcomponent;

import catalogcomponent.dataelements.Group;
import catalogcomponent.dataelements.GroupDataElement;
import catalogcomponent.filters.Filter;

import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.util.ArrayList;
import java.awt.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class UniTree {

    private static final Font mainFont = new Font(null, Font.PLAIN, 14);

    private JPanel contentPane;

    private JLabel selectedItemLab;
    private Group selectedGroup;

    private JTree tree;
    private Model model;
    private UniTable table;

    private ArrayList<Group> treeContent;
    private ArrayList<GroupDataElement> tableContent;

    private TreeElementsComparator treeElementsComparator;

    private class TreeElementsComparator implements Comparator<Group> {

        @Override
        public int compare(Group o1, Group o2) {
            return o1.getName().compareTo(o2.getName());
        }

    }

    private class Model implements TreeModel {

        @Override
        public Object getRoot() {
            for (Object element : treeContent) {
                if (isRoot(element)) return element;
            }
            return null;
        }

        @Override
        public Object getChild(Object parent, int index) {
            return getChildList(parent).get(index);
        }

        @Override
        public int getChildCount(Object parent) {
            return getChildList(parent).size();
        }

        @Override
        public boolean isLeaf(Object node) {
            int childCount = getChildCount(node);
            return childCount == 0;
        }

        @Override
        public int getIndexOfChild(Object parent, Object child) {
            int index = 0;
            for (Object element : getChildList(parent)) {
                if (child.equals(element)) break;
                index++;
            }
            return index;
        }

        //Возвращает true, если node2 является потомком для node1
        private boolean isChild(Object node1, Object node2) {
            Group element1 = (Group) node1;
            Group element2 = (Group) node2;
            if (element2.getParentId() == null) return false;
            return element2.getParentId().equals(element1.getId());
        }

        //Возвращает true, если узел является корневым
        private boolean isRoot(Object node) {
            return ((Group) node).getParentId() == null;
        }

        //Возвращает отсортированный по возрастанию список потомков данного узла
        private ArrayList<Group> getChildList(Object parent) {
            ArrayList<Group> childsList = new ArrayList<>();
            for (Object element : treeContent) {
                if (isChild(parent, element)) {
                    childsList.add((Group) element);
                }
            }
            childsList.sort(treeElementsComparator);
            return childsList;
        }

        @Override
        public void valueForPathChanged(TreePath path, Object newValue) {
        }

        @Override
        public void addTreeModelListener(TreeModelListener l) {
        }

        @Override
        public void removeTreeModelListener(TreeModelListener l) {
        }

    }

    public UniTree(Class<? extends GroupDataElement> tableDataClass, Filter tableDataFilter) {
        treeContent = new ArrayList<>();
        tableContent = new ArrayList<>();
        table = new UniTable(tableDataClass, tableDataFilter);
        selectedGroup = null;
        treeElementsComparator = new TreeElementsComparator();

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(5, 5));

        JPanel treePane = new JPanel();
        treePane.setLayout(new BorderLayout(5, 5));

        selectedItemLab = new JLabel("");
        treePane.add(selectedItemLab, BorderLayout.NORTH);

        model = new Model();
        tree = new JTree(model);

        tree.setRootVisible(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setFont(mainFont);

        tree.addTreeSelectionListener(selectionListener);

        treePane.add(new JScrollPane(tree), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.3);
        splitPane.setDividerSize(3);
        splitPane.add(treePane, JSplitPane.LEFT);
        splitPane.add(table.getVisualComponent(), JSplitPane.RIGHT);

        contentPane.add(splitPane, BorderLayout.CENTER);
    }

    public JPanel getVisualComponent() {
        return contentPane;
    }

    public void setContent(List<Group> groupList, List<? extends GroupDataElement> elementList) {
        treeContent.clear();
        tableContent.clear();

        treeContent.addAll(groupList);
        tableContent.addAll(elementList);

        tree.updateUI();
        tree.expandRow(0);
        table.clear();
    }

    public void addGroup(Group group) {
        //Нельзя добавить группу в пустой список групп
        if (treeContent.size() == 0) return;

        treeContent.add(group);
        tree.updateUI();
    }

    public void editGroup(int id, String name) {
        int i = -1;
        for (Group group : treeContent) {
            i++;
            if (group.getId() == id) break;
        }
        if (i == (-1)) return;
        treeContent.get(i).setName(name);

        selectedGroup = treeContent.get(i);
        selectedItemLab.setText(selectedGroup.getName());

        tree.updateUI();
        tree.expandRow(0);
    }

    public void removeGroup(int id) {
        //Нельзя удалить группу из пустого списка
        if (treeContent.size() == 0) return;

        //Рекурсивно удаляем группу и все ее подгруппы
        recursiveRemoveGroup(id);

        //Удаляем все элементы таблицы данных, не связанные более ни с одной группой
        boolean findParent;
        for (int i = 0; i < tableContent.size(); i++) {
            findParent = false;

            for (Group group : treeContent) {
                if (tableContent.get(i).getGroupId() == group.getId()) {
                    findParent = true;
                    break;
                }
            }

            if (findParent) continue;
            tableContent.remove(i);
            i--;
        }

        selectedItemLab.setText("");
        selectedGroup = null;

        tree.updateUI();
        table.clear();
    }

    public void addElement(GroupDataElement element) {
        //Нельзя добавить элемент в список элементов, если пуст список групп
        if (treeContent.size() == 0) return;

        tableContent.add(element);
        refreshCurrentTableContent();
    }

    public Group getSelectedGroup() {
        return selectedGroup;
    }

    public GroupDataElement getSelectedElement() {
        return (GroupDataElement) table.getSelectedElement();
    }

    private void recursiveRemoveGroup(int id) {
        //Получаем список подгрупп
        List<Integer> subGroupsId = new LinkedList<>();
        for (Group group : treeContent) {
            if (group.getParentId() != null && group.getParentId() == id) {
                subGroupsId.add(group.getId());
            }
        }

        //Сперва удаляем все подгруппы
        for (Integer subGroupId : subGroupsId) {
            removeGroup(subGroupId);
        }

        //Теперь удаляем группу с переданным id
        for (int i = 0; i < treeContent.size(); i++) {
            if (treeContent.get(i).getId() == id) {
                treeContent.remove(i);
                return;
            }
        }
    }

    private void refreshCurrentTableContent() {
        List<GroupDataElement> groupContent = new LinkedList<>();
        for (GroupDataElement element : tableContent) {
            if (element.getGroupId() == selectedGroup.getId()) {
                groupContent.add(element);
            }
        }
        table.setContent(groupContent);
    }

    private TreeSelectionListener selectionListener = new TreeSelectionListener() {
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            if (e.getNewLeadSelectionPath() == null) {
                return;
            }
            selectedGroup = (Group) e.getNewLeadSelectionPath().getLastPathComponent();
            selectedItemLab.setText(selectedGroup.toString());

            refreshCurrentTableContent();
        }
    };

}
