package lisa;

// Created by lisa on 4/11/15.

import javax.swing.*;
import java.awt.*;
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
    private Color greenish = new Color(28, 69, 10);
    private Color grayish = new Color(238, 238, 238);

    public GameOverGUI() {

        submitNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Adds player entry in name field and score to top score list onscreen
                String playerName = playerNameTextField.getText();
                SnakeGame.currentScore.setName(playerName);
                ScoreManager.updateTopScoresFile();
                displayScoresInGUI(); // not working
                playerNameTextField.setText("");
                SnakeGame.snakeFrame.repaint();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // FINDBUGS does not like this call to exit
                System.exit(0);
            }
        });

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGame.reset();
                SnakeGame.displayOptionsGUI();
                resetScreen();
            }
        });
    }

    private void resetScreen() {
        // Reset screen before starting a new game
        endTitleLabel.setText("GAME OVER");
        highScoreInstructionLabel1.setVisible(false);
        highScoreInstructionLabel2.setVisible(false);
        playerNameTextField.setVisible(false);
        submitNameButton.setVisible(false);
        endTitleLabel.setForeground(Color.BLACK);
        rootPanel.setBackground(grayish);
        highScoreTextArea.setBackground(grayish);
        displayScoresInGUI();
    }

    public void displayScoresInGUI() {
        // Display current game score
        currentScoreLabel.setText(Integer.toString(SnakeGame.currentScore.points));
        // Display top ten scores
        topTenScores = ScoreManager.getTopTenScores();
        // FINDBUGS suggested this method of using StringBuilder
        // instead of concatenating string in a loop
        if (topTenScores.isEmpty()) {
            // First high score has just been recorded
            // TODO: format this date
            highScoreTextArea.setText("1. " + SnakeGame.currentScore.points + " points, " + SnakeGame.currentScore.date.toString());

        } else if (topTenScores.size() < 10) {
            // Less than ten scores have been recorded
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < topTenScores.size(); x ++) {
                Score topScore = topTenScores.get(x);
                String ordinal = Integer.toString(x + 1);
                String date = ScoreManager.dateFormat.format(topScore.date);
                String scoreLineString = ordinal + ". " + topScore.points + " points: " + topScore.name + ", " + date + "\n";
                sb.append(scoreLineString);
            }

            String topScoresDisplay = sb.toString();
            highScoreTextArea.setText(topScoresDisplay);

        } else {
            // There are ten or more scores in the topTenScores list
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < 10; x ++) {
                Score topScore = topTenScores.get(x);
                String ordinal = Integer.toString(x + 1);
                String date = ScoreManager.dateFormat.format(topScore.date);

                String scoreLineString = ordinal + ". " + topScore.points + " points: " + topScore.name + ", " + date + "\n";
                sb.append(scoreLineString);
            }

            String topScoresDisplay = sb.toString();
            highScoreTextArea.setText(topScoresDisplay);
        }
    }

    public void newHighScore() {
        // Adjusts GUI if player has achieved a new high score

        // These elements allow the player to enter her name for the high score list
        highScoreInstructionLabel1.setVisible(true);
        highScoreInstructionLabel2.setVisible(true);
        playerNameTextField.setVisible(true);
        submitNameButton.setVisible(true);

        // Only display high score message if game has not been won
        if (SnakeGame.getGameStage() != SnakeGame.GAME_WON) {
            endTitleLabel.setText("NEW HIGH SCORE!");
        }
    }

    public void winDisplay() {
        // Super awesome display that's totally worth all that work
        endTitleLabel.setText("CONGRATULATIONS, YOU WON SNAKE!");
        endTitleLabel.setForeground(Color.WHITE);
        rootPanel.setBackground(greenish);
        highScoreTextArea.setBackground(greenish);
    }
}
