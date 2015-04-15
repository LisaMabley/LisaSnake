package lisa;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

// Created by lisa on 3/30/15.

public class GameOptionsGUI {
    public JPanel rootPanel;
    private JButton playButton;
    private JButton quitButton;
    private JComboBox sizeComboBox;
    private JCheckBox soundEffectsOnCheckBox;
    private JCheckBox warpWallsOnCheckBox;
    private JCheckBox avocadosOnCheckBox;
    private JComboBox speedComboBox;
    private JTextArea instructionsTextArea;
    // FINDBUGS: removed unused variables

    // Constructor
    public GameOptionsGUI() {

        // Display instructions
        final String basicInstructions = "Eat the yellow kibble so your snake will grow. Game ends if you hit a wall or yourself. Fill the entire screen to win.";
        instructionsTextArea.setText(basicInstructions);

        // Set options for size ComboBox
        final String small = "8 x 8";
        final String medium = "12 x 12";
        final String large = "16 x 16";
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

        speedComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String speed = speedComboBox.getSelectedItem().toString();
                if (speed.equals(s)) {
                    SnakeGame.setGameSpeed(500);
                } else if (speed.equals(m)) {
                    SnakeGame.setGameSpeed(350);
                } else if (speed.equals(f)) {
                    SnakeGame.setGameSpeed(200);
                }
            }
        });

        sizeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String size = sizeComboBox.getSelectedItem().toString();
                if (size.equals(small)) {
                    SnakeGame.setGridSize(401);
                } else if (size.equals(medium)) {
                    SnakeGame.setGridSize(601);
                } else if (size.equals(large)) {
                    SnakeGame.setGridSize(801);
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
                    instructionsTextArea.setText("With warp walls turned on, you can exit on one side of the screen, only to reemerge unscathed on the opposite side.");

                } else {
                    SnakeGame.setWarpWallsOn(false);
                    instructionsTextArea.setText(basicInstructions);
                }
            }
        });

        avocadosOnCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (avocadosOnCheckBox.isSelected()) {
                    SnakeGame.setAvocadosOn(true);
                    instructionsTextArea.setText("Delicious avocados are worth more points than kibble -- but they can only be eaten during the short period when they turn bright green.");

                } else {
                    SnakeGame.setAvocadosOn(false);
                    instructionsTextArea.setText(basicInstructions);
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
                SnakeGame.displayGameGrid();
            }
        });
    }
}
