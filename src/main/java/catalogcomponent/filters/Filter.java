package catalogcomponent.filters;

import catalogcomponent.UniTable;
import catalogcomponent.dataelements.DataElement;

import javax.swing.*;

public interface Filter {

    void setTable(UniTable table);

    JPanel getVisualComponent();

    boolean check(DataElement element);

}
