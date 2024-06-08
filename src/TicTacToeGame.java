/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LUTHFI
 */
/**
 * Kelas dasar untuk permainan.
 */
/*import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;*/

/*public abstract class Game {
    protected char currentPlayer;

    public Game() {
        currentPlayer = 'X';
    }

    public abstract void initializeGame();
    public abstract void makeMove(int x, int y);
    public abstract boolean checkForWin();
    public abstract void resetGame();
}*/

/**
 * Kelas TicTacToeGame mewarisi dari kelas Game dan mengimplementasikan Playable.
 */
/*
public class TicTacToeGame extends Game implements Playable {
    private JButton[][] buttons;
    private ScoreManager scoreManager;

    public TicTacToeGame() {
        setTitle("Tic-Tac-Toe Game");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scoreManager = new ScoreManager();
        initializeGame();
    }

    @Override
    public void initializeGame() {
        setLayout(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[i][j].setFocusable(false);
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                add(buttons[i][j]);
            }
        }
    }

    @Override
    public void makeMove(int x, int y) {
        if (buttons[x][y].getText().equals("")) {
            buttons[x][y].setText(String.valueOf(currentPlayer));
            if (checkForWin()) {
                JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                String playerName = JOptionPane.showInputDialog("Enter your name:");
                scoreManager.saveScore(playerName, 10);  // Save the score, adjust scoring as needed
                resetGame();
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(null, "The game is a tie!");
                resetGame();
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
    }

    @Override
    public boolean checkForWin() {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[i][2].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
            if (buttons[0][i].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][i].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][i].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
        }
        if (buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[2][2].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }
        if (buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[2][0].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }
        return false;
    }

    @Override
    public void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        currentPlayer = 'X';
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private class ButtonClickListener implements ActionListener {
        private int x, y;

        public ButtonClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            makeMove(x, y);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TicTacToeGame().setVisible(true);
        });
    }
}

public void saveGameToFile() {
    try (FileWriter writer = new FileWriter("game_state.txt")) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                writer.write(buttons[i][j].getText() + " ");
            }
            writer.write("\n");
        }
        writer.write(currentPlayer + "\n");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public void loadGameFromFile() {
    try (BufferedReader reader = new BufferedReader(new FileReader("game_state.txt"))) {
        for (int i = 0; i < 3; i++) {
            String[] line = reader.readLine().split(" ");
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(line[j]);
            }
        }
        currentPlayer = reader.readLine().charAt(0);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Class utama untuk permainan Tic-Tac-Toe.
 * Mengimplementasikan interface Playable untuk mendefinisikan metode permainan.
 */
public class TicTacToeGame extends JFrame implements Playable {
    private JButton[][] buttons;
    private char currentPlayer;
    private ScoreManager scoreManager;
    private Connection connection;

    public TicTacToeGame() {
        setTitle("Tic-Tac-Toe Game");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currentPlayer = 'X';
        scoreManager = new ScoreManager();
        connection = DatabaseConnection.getConnection();
        initializeGame();
    }

    @Override
    public void initializeGame() {
        setLayout(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[i][j].setFocusable(false);
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                add(buttons[i][j]);
            }
        }
    }

    @Override
    public void makeMove(int x, int y) {
        if (buttons[x][y].getText().equals("")) {
            buttons[x][y].setText(String.valueOf(currentPlayer));
            if (checkForWin()) {
                JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                String playerName = JOptionPane.showInputDialog("Enter your name:");
                scoreManager.saveScore(playerName, 10);  // Save the score, adjust scoring as needed
                resetGame();
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(null, "The game is a tie!");
                resetGame();
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
    }

    @Override
    public boolean checkForWin() {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[i][2].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
            if (buttons[0][i].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][i].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][i].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
        }
        if (buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[2][2].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }
        if (buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[2][0].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }
        return false;
    }

    @Override
    public void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        currentPlayer = 'X';
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private class ButtonClickListener implements ActionListener {
        private int x, y;

        public ButtonClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            makeMove(x, y);
        }
    }

    /**
     * Menyimpan status permainan ke dalam file.
     */
    public void saveGameToFile() {
        try (FileWriter writer = new FileWriter("game_state.txt")) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    writer.write(buttons[i][j].getText() + " ");
                }
                writer.write("\n");
            }
            writer.write(currentPlayer + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Memuat status permainan dari file.
     */
    public void loadGameFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("game_state.txt"))) {
            for (int i = 0; i < 3; i++) {
                String[] line = reader.readLine().split(" ");
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setText(line[j]);
                }
            }
            currentPlayer = reader.readLine().charAt(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeGame game = new TicTacToeGame();
            game.setVisible(true);

            // Menjalankan musik di latar belakang
            Thread musicThread = new Thread(new MusicPlayer("path/to/your/musicfile.wav"));
            musicThread.start();
        });
    }
}
