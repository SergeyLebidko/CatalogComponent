package catalogcomponent.filters;

import catalogcomponent.UniTable;
import catalogcomponent.dataelements.DataElement;
import catalogcomponent.dataelements.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductFilter implements Filter {

    private static final Font mainFont = new Font(null, Font.PLAIN, 14);

    private UniTable table;

    private JPanel contentPane;
    private JTextField nameField;
    private JButton clearBtn;
    private JButton startBtn;

    public ProductFilter() {
        contentPane = new JPanel();
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));

        nameField = new JTextField(25);
        nameField.setFont(mainFont);
        clearBtn = new JButton("X");
        startBtn = new JButton(">");

        contentPane.add(new JLabel("Наименование"));
        contentPane.add(nameField);
        contentPane.add(clearBtn);
        contentPane.add(startBtn);

        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startBtn.doClick();
            }
        });

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.refresh();
            }
        });

        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                table.refresh();
            }
        });

    }

    @Override
    public void setTable(UniTable table) {
        this.table = table;
    }

    @Override
    public JPanel getVisualComponent() {
        return contentPane;
    }

    @Override
    public boolean check(DataElement element) {
        String filterString = nameField.getText();
        if (filterString.equals("")) return true;
        filterString = filterString.toLowerCase();

        String nameElement = (String) element.getField(1);
        nameElement = nameElement.toLowerCase();

        return nameElement.indexOf(filterString) != (-1);
    }

}
