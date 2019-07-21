package catalogcomponent;

import catalogcomponent.Listeners.UniTreeSelectionListener;
import catalogcomponent.dataelements.GroupDataElement;
import catalogcomponent.dataelements.Group;
import catalogcomponent.filters.Filter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UniCatalogPane implements UniTreeSelectionListener {

    private UniTree uniTree;
    private UniTable uniTable;

    private ArrayList<Group> treeContent;
    private ArrayList<GroupDataElement> tableContent;

    private JPanel contentPane;

    public UniCatalogPane(Class<? extends GroupDataElement> uniTableClass, Filter filter) {
        treeContent = new ArrayList<>();
        tableContent = new ArrayList<>();

        uniTree = new UniTree();
        uniTable = new UniTable(uniTableClass, filter);

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(5, 5));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.3);
        splitPane.setDividerSize(3);
        splitPane.add(uniTree.getVisualComponent(), JSplitPane.LEFT);
        splitPane.add(uniTable.getVisualComponent(), JSplitPane.RIGHT);

        contentPane.add(splitPane, BorderLayout.CENTER);

        uniTree.addUniTreeSelectionListener(this);
    }

    public JPanel getVisualComponent() {
        return contentPane;
    }

    public void setContent(List<Group> groupList, List<? extends GroupDataElement> elementList) {
        treeContent.clear();
        tableContent.clear();
        if (groupList!=null & elementList!=null){
            treeContent.addAll(groupList);
            tableContent.addAll(elementList);
        }
        uniTree.setContent(treeContent);
        uniTable.setContent(null);
    }

    @Override
    public void groupSelection(Group selectedGroup) {
        List<GroupDataElement> groupContent = new LinkedList<>();
        for (GroupDataElement element : tableContent) {
            if (element.getGroupId() == selectedGroup.getId()) {
                groupContent.add(element);
            }
        }
        uniTable.setContent(groupContent);
    }

}
