package catalogcomponent;

import catalogcomponent.dataelements.TreeElement;

import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.util.ArrayList;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class UniTree {

    private JPanel contentPane;

    private JTree tree;
    private Model model;

    private ArrayList<TreeElement> content;
    private ElementsComparator elementsComparator;

    private class ElementsComparator implements Comparator<TreeElement> {

        @Override
        public int compare(TreeElement o1, TreeElement o2) {
            return o1.getName().compareTo(o2.getName());
        }

    }

    private class Model implements TreeModel {

        @Override
        public Object getRoot() {
            for (Object element : content) {
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
            TreeElement element1 = (TreeElement) node1;
            TreeElement element2 = (TreeElement) node2;
            if (element2.getParentId() == null) return false;
            return element2.getParentId().equals(element1.getId());
        }

        //Возвращает true, если узел является корневым
        private boolean isRoot(Object node) {
            return ((TreeElement) node).getParentId() == null;
        }

        //Возвращает отсортированный по возрастанию список потомков данного узла
        private ArrayList<TreeElement> getChildList(Object parent) {
            ArrayList<TreeElement> childsList = new ArrayList<>();
            for (Object element : content) {
                if (isChild(parent, element)) {
                    childsList.add((TreeElement) element);
                }
            }
            childsList.sort(elementsComparator);
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

    public UniTree() {
        content = new ArrayList<>();
        elementsComparator = new ElementsComparator();

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(5, 5));

        model = new Model();
        tree = new JTree(model);

        tree.setRootVisible(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setFont(new Font(null, Font.PLAIN, 14));


        contentPane.add(new JScrollPane(tree), BorderLayout.CENTER);
    }

    public JPanel getVisualComponent() {
        return contentPane;
    }

    public void setContent(List<TreeElement> list) {
        content.clear();
        content.addAll(list);

        tree.updateUI();
        tree.expandRow(0);
    }

}
