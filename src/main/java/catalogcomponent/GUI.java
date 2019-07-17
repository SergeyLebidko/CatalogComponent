package catalogcomponent;

import javax.swing.*;
import java.awt.*;

public class GUI {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    private JFrame frm;

    public GUI() {
        frm = new JFrame("CatalogComponent");
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(WIDTH, HEIGHT);
        int xPos = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2;
        int yPos = Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT / 2;
        frm.setLocation(xPos, yPos);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

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

}
