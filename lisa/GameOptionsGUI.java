package lisa;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

// Created by lisa on 3/30/15.

public class GameOptionsGUI extends JFrame {
    private JPanel rootPanel;
    private JButton playButton;
    private JButton quitButton;
    private JLabel sizeLabel;
    private JComboBox sizeComboBox;
    private JCheckBox soundEffectsOnCheckBox;
    private JCheckBox warpWallsOnCheckBox;
    private JCheckBox obstaclesOnCheckBox;
    private JCheckBox preyOnCheckBox;
    private JLabel titleLabel;
    private JComboBox speedComboBox;

    // Constructor
    public GameOptionsGUI() {
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(501, 501);

        // Set options for size ComboBox
        final String small = "6 x 6";
        final String medium = "10 x 10";
        final String large = "14 x 14";
        sizeComboBox.addItem(small);
        sizeComboBox.addItem(medium);
        sizeComboBox.addItem(large);
        sizeComboBox.setSelectedItem(small);

        // Set options for speed ComboBox
        final String s = "slow";
        final String m = "medium";
        final String f = "fast";
        speedComboBox.addItem(s);
        speedComboBox.addItem(m);
        speedComboBox.addItem(f);
        sizeComboBox.setSelectedItem(medium);

        sizeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String size = sizeComboBox.getSelectedItem().toString();
                if (size.equals(small)) {
                    SnakeGame.setGridSize(301);
                } else if (size.equals(medium)) {
                    SnakeGame.setGridSize(501);
                } else if (size.equals(large)) {
                    SnakeGame.setGridSize(701);
                }
            }
        });

        soundEffectsOnCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (soundEffectsOnCheckBox.isSelected()) {
                    SnakeGame.setSoundsOn(true);
                } else {
                    SnakeGame.setSoundsOn(false);
                }
            }
        });

        warpWallsOnCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (warpWallsOnCheckBox.isSelected()) {
                    SnakeGame.setWarpWallsOn(true);
                } else {
                    SnakeGame.setWarpWallsOn(false);
                }
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGame.setGameStage(SnakeGame.DURING_GAME);
                SnakeGame.newGame();
                dispose();
            }
        });
    }
}
