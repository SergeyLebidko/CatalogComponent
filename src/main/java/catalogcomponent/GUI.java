package catalogcomponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    private JFrame frm;

    private JButton showCatalogBtn;

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

        showCatalogBtn = new JButton("Показать каталог");
        toolPane.add(showCatalogBtn);

        showCatalogBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionHandler.showCatalog();
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
