/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import java.awt.*;

/**
 * Class untuk menampilkan papan skor.
 */
public class ScoreBoard extends JFrame {
    private Connection connection;

    public ScoreBoard() {
        connection = DatabaseConnection.getConnection();
        setTitle("Score Board");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        displayScores();
    }

    private void displayScores() {
        String query = "SELECT * FROM scores ORDER BY score DESC";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            JTextArea textArea = new JTextArea();
            while (resultSet.next()) {
                String playerName = resultSet.getString("player_name");
                int score = resultSet.getInt("score");
                textArea.append(playerName + ": " + score + "\n");
            }
            add(new JScrollPane(textArea), BorderLayout.CENTER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ScoreBoard().setVisible(true);
        });
    }
}



