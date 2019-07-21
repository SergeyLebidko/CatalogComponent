package catalogcomponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    private static final int WIDTH = 1300;
    private static final int HEIGHT = 900;

    private JFrame frm;

    private JButton showCatalogBtn;

    private JButton addGroupBtn;
    private JButton editGroupBtn;
    private JButton removeGroupBtn;

    private JButton addElementBtn;
    private JButton editElementBtn;
    private JButton removeElementBtn;

    private ActionHandler actionHandler;
    private UniTree uniTree;

    public GUI() {
        frm = new JFrame("UniTree");
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(WIDTH, HEIGHT);
        int xPos = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2;
        int yPos = Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT / 2;
        frm.setLocation(xPos, yPos);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel toolPane = new JPanel();
        toolPane.setLayout(new FlowLayout(FlowLayout.LEFT));

        showCatalogBtn = new JButton("Получить данные из БД");
        addGroupBtn = new JButton("Добавить группу");
        editGroupBtn = new JButton("Переименовать группу");
        removeGroupBtn = new JButton("Удалить группу");
        addElementBtn = new JButton("Добавить элемент в группу");
        editElementBtn = new JButton("Переименовать элемент");
        removeElementBtn = new JButton("Удалить элемент");

        toolPane.add(showCatalogBtn);
        toolPane.add(Box.createHorizontalStrut(15));
        toolPane.add(addGroupBtn);
        toolPane.add(editGroupBtn);
        toolPane.add(removeGroupBtn);
        toolPane.add(Box.createHorizontalStrut(15));
        toolPane.add(addElementBtn);
        toolPane.add(editElementBtn);
        toolPane.add(removeElementBtn);

        showCatalogBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionHandler.showCatalog();
            }
        });

        addGroupBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionHandler.addGroup();
            }
        });

        editGroupBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionHandler.editGroup();
            }
        });

        removeGroupBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionHandler.removeGroup();
            }
        });

        contentPane.add(toolPane, BorderLayout.NORTH);
        frm.setContentPane(contentPane);
    }

    public void showGui() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frm.setVisible(true);
            }
        });
    }

    public void setUniTree(UniTree uniTree) {
        this.uniTree = uniTree;
        frm.getContentPane().add(uniTree.getVisualComponent(), BorderLayout.CENTER);
    }

    public void setActionHandler(ActionHandler actionHandler) {
        this.actionHandler = actionHandler;
    }

}
