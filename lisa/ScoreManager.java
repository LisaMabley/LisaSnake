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

public class ScoreManager {
    // Reads a list of top ten scores from file,
    // compares current score to top scores list,
    // and updates file if necessary

    private static int lowestHighScore = 0;
    private static LinkedList<Score> topTenScores;

    // First set format for dates
    public static String datePattern = "MM/dd/yy";
    public static SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

    public ScoreManager() {
        createTopScoreListFromFile();
    }

    private static void createTopScoreListFromFile() {
        topTenScores = new LinkedList<Score>();

        try {
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
                System.out.println("New top score: " + topScore);

                // Read next line
                line = buffReader.readLine();

            }
            buffReader.close();

        } catch (IOException ioe) {
            System.out.println("Could not open or read TopScores.txt");
            System.out.println(ioe.toString());
        }
        Collections.sort(topTenScores, Collections.reverseOrder());
        if (!topTenScores.isEmpty()) {
            lowestHighScore = topTenScores.getLast().points;
        }
    }

    public static String newHighScore(Score newScore) {

        if (newScore.points > lowestHighScore) {
            if (!topTenScores.contains(newScore)) {
                topTenScores.add(newScore);
                Collections.sort(topTenScores, Collections.reverseOrder());
                updateTopScoresFile();
            }

            if (!topTenScores.isEmpty()) {
                lowestHighScore = topTenScores.getLast().points;
            }

            return "New High Score!!";

        } else {
            return "";
        }
    }

    public static String getStringHighScore() {
        return Integer.toString(lowestHighScore);
    }

    private static void updateTopScoresFile() {
        // When top scores change, update the entire file

        try {
            // Initialize the buffered writer
            FileWriter writer = new FileWriter("TopScores.txt");
            BufferedWriter buffWriter = new BufferedWriter(writer);

            // Write each score to file
            for (int x = 0; x < 10; x ++) {
                String topScoreString = topTenScores.get(x).toString();
                buffWriter.write(topScoreString);
                buffWriter.newLine();
            }

            // Close buffered writer
            buffWriter.close();

        } catch (IOException ioe) {
            System.out.println("Could not create TopScores.txt");
            System.out.println(ioe.toString());
        }
    }
}
