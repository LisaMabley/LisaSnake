package lisa;

// Created by lisa on 4/11/15.

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class GameOverGUI {
    public JPanel rootPanel;
    private JButton playAgainButton;
    private JButton quitButton;
    private JTextArea highScoreTextArea;
    private JLabel endTitleLabel;
    private JTextField textField1;
    private JButton submitNameButton;
    private JLabel currentScoreLabel;

    public GameOverGUI() {
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGame.displayOptionsGUI();
            }
        });
    }
}
