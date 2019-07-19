package catalogcomponent;

import catalogcomponent.dataelements.GroupDataElement;
import catalogcomponent.filters.Filter;

import javax.swing.*;

public class UniCatalogPane {

    private UniTree uniTree;
    private UniTable uniTable;

    private JPanel contentPane;

    public UniCatalogPane(Class<? extends GroupDataElement> uniTableClass, Filter filter) {
    }

    public JPanel getVisualComponent(){
        return contentPane;
    }


}
