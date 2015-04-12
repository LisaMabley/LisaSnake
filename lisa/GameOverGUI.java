package lisa;

// Created by lisa on 4/11/15.

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class GameOverGUI {
    public JPanel rootPanel;
    private JButton playAgainButton;
    private JButton quitButton;
    private JTextArea highScoreTextArea;
    private JLabel endTitleLabel;
    private JButton submitNameButton;
    private JLabel currentScoreLabel;
    private JTextField playerNameTextField;
    private JLabel highScoreInstructionLabel1;
    private JLabel highScoreInstructionLabel2;
    private LinkedList<Score> topTenScores;

    public GameOverGUI() {

        submitNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = playerNameTextField.getText();
                SnakeGame.currentScore.setName(playerName);
                ScoreManager.updateTopScoresFile();
                displayScoresInGUI();
                playerNameTextField.setText("");
                SnakeGame.snakeFrame.repaint();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGame.reset();
                SnakeGame.displayOptionsGUI();
                endTitleLabel.setText("GAME OVER");
            }
        });
    }

    public void displayScoresInGUI() {
        currentScoreLabel.setText(Integer.toString(SnakeGame.currentScore.points));

        topTenScores = ScoreManager.getTopTenScores();
        String topScoresDisplay = "";
        for (int x = 0; x < topTenScores.size(); x ++) {
            Score topScore = topTenScores.get(x);
            String ordinal = Integer.toString(x + 1);
            String date = ScoreManager.dateFormat.format(topScore.date);

            topScoresDisplay += ordinal + ". " + topScore.points + " points: " + topScore.name + ", " + date + "\n";
        }

        highScoreTextArea.setText(topScoresDisplay);
    }

    public void newHighScore() {
        highScoreInstructionLabel1.setVisible(true);
        highScoreInstructionLabel2.setVisible(true);
        playerNameTextField.setVisible(true);
        submitNameButton.setVisible(true);

        endTitleLabel.setText("NEW HIGH SCORE!");
    }
}
