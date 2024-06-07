/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author LUTHFI
 */
/**
 * Interface untuk mendefinisikan metode dasar permainan.
 */
public interface Playable {
    void initializeGame();
    void makeMove(int x, int y);
    boolean checkForWin();
    void resetGame();
}
