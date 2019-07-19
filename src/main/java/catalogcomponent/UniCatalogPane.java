package catalogcomponent;

import catalogcomponent.dataelements.GroupDataElement;
import catalogcomponent.dataelements.Group;
import catalogcomponent.filters.Filter;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UniCatalogPane {

    private UniTree uniTree;
    private UniTable uniTable;

    private JPanel contentPane;

    public UniCatalogPane(Class<? extends GroupDataElement> uniTableClass, Filter filter) {
        uniTree = new UniTree();
        uniTable = new UniTable(uniTableClass, filter);

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(5, 5));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.4);
        splitPane.setDividerSize(3);
        splitPane.add(uniTree.getVisualComponent(), JSplitPane.LEFT);
        splitPane.add(uniTable.getVisualComponent(), JSplitPane.RIGHT);

        contentPane.add(splitPane, BorderLayout.CENTER);
    }

    public JPanel getVisualComponent() {
        return contentPane;
    }

    public void setContent(List<Group> groupList, List<? extends GroupDataElement> elementList) {
        uniTree.setContent(groupList);
        uniTable.setContent(elementList);
    }

}
