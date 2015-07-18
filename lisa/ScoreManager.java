package lisa;

// Created by lisa on 4/9/15.

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.text.ParseException;
import java.io.File;

public class ScoreManager {
    // Reads a list of top ten scores from file,
    // compares current score to top scores list,
    // and updates file if necessary

    private static int lowestHighScore = 0;
    private static LinkedList<Score> topTenScores;

    // First set format for dates
    public static final String datePattern = "MM/dd/yy";
    public static SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

    public ScoreManager() {
        createTopScoreList();}

    private static void createTopScoreList() {
        topTenScores = new LinkedList<Score>();
        File topScoresTextFile = new File("TopScores.txt");

        if (topScoresTextFile.exists()) {
            try {
                // Try to create top score list from file
                FileReader reader = new FileReader("TopScores.txt");
                BufferedReader buffReader = new BufferedReader(reader);
                String line = buffReader.readLine();
                String[] splitLine;

                while (line != null) {
                    // Iterate through lines until there are none left
                    splitLine = line.split(":");

                    int numberPoints = Integer.parseInt(splitLine[0]);
                    String playerName = splitLine[1];
                    Date datePlayed = new Date();

                    try {
                        // Set format for dates
                        String datePattern = "MM/dd/yy";
                        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
                        datePlayed = dateFormat.parse(splitLine[2]);


                    } catch (ParseException pException) {
                        System.out.println("Not a valid date");
                        pException.printStackTrace();
                    }

                    // Create new Score object for each line
                    Score topScore = new Score();
                    topScore.points = numberPoints;
                    topScore.name = playerName;
                    if (datePlayed != null) {
                        topScore.date = datePlayed;
                    }
                    topTenScores.add(topScore);

                    // Read next line
                    line = buffReader.readLine();

                }
                buffReader.close();

            } catch (IOException ioe){
                System.out.println("Error opening score file.");
                System.out.println(ioe.toString());
            }

        } else  {
            // TopScores.txt does not exist
            try {
                // Create TopScores.txt
                FileWriter writer = new FileWriter("TopScores.txt");
                BufferedWriter buffWriter = new BufferedWriter(writer);
                buffWriter.newLine();
                buffWriter.close();

            } catch (IOException ioException) {
                System.out.println("Could not find or create top score file.");
                // TODO: make there be some way game can continue without high scores
            }
        }

        // Sort on score order, highest score first
        Collections.sort(topTenScores, Collections.reverseOrder());
        if (!topTenScores.isEmpty()) {
            lowestHighScore = topTenScores.getLast().points;
        }
    }

    public static void checkIfNewHighScore(Score newScore) {
        // If fewer than 10 scores have been recorded, score is always a high score
        // If more than 10 scores have been recorded, but  given score is higher than
        // the lowest high score, the lowest bumps off the list and this one should be added

        if (newScore.points > lowestHighScore || topTenScores.size() < 10) {
            updateHighScores(newScore);
        }
    }

    protected static void updateHighScores(Score newHighScore) {
        // This can be called more than once for the same score
        // (if player corrects her name entry, for example)
        // so we need to make sure we don't add duplicates

        if (!topTenScores.contains(newHighScore)) {
            topTenScores.add(newHighScore);
            Collections.sort(topTenScores, Collections.reverseOrder());
            updateTopScoresFile();
            SnakeGame.gameOverGUI.newHighScore();
        }

        if (!topTenScores.isEmpty()) {
            lowestHighScore = topTenScores.getLast().points;
        }
    }

    protected static void updateTopScoresFile() {
        // When top scores change, update the entire file
        try {
            // Initialize the buffered writer
            FileWriter writer = new FileWriter("TopScores.txt");
            BufferedWriter buffWriter = new BufferedWriter(writer);
            System.out.println("Updating top scores file");

            if (topTenScores.size() < 10) {
                System.out.println("Top score list less than ten");
                // Write all scores to file
                for (int x = 0; x < topTenScores.size(); x ++) {
                    System.out.println("Top score loop");
                    String topScoreString = topTenScores.get(x).toString();
                    buffWriter.write(topScoreString);
                    buffWriter.newLine();
                }

            } else {
                // Write only top ten scores to file
                for (int x = 0; x < 10; x ++) {
                    String topScoreString = topTenScores.get(x).toString();
                    buffWriter.write(topScoreString);
                    buffWriter.newLine();
                }
            }

            // Close buffered writer
            buffWriter.close();

        } catch (IOException ioe) {
            System.out.println("Could not create TopScores.txt");
            System.out.println(ioe.toString());
        }
    }

    public static LinkedList<Score> getTopTenScores() {
        return topTenScores;
    }
}
