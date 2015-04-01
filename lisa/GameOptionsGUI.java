package lisa;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by lisa on 3/30/15.
 */
public class GameOptionsGUI extends JFrame {
    private JPanel rootPanel;
    private JButton playButton;
    private JButton quitButton;
    private JLabel sizeLabel;
    private JComboBox sizeComboBox;

    // Constructor
    public GameOptionsGUI(int xPixelMax, int yPixelMax) {
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Set priority options for ComboBox
        final String small = "10 x 10";
        final String medium = "15 x 15";
        final String large = "20 x 20";
        sizeComboBox.addItem(small);
        sizeComboBox.addItem(medium);
        sizeComboBox.addItem(large);
        sizeComboBox.setSelectedItem(small);

        this.setSize(xPixelMax, yPixelMax);

        sizeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                SnakeGame.setGridSize(501);

            }
        });
    }
}
