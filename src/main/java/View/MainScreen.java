package View;

import Model.Board;
import javax.swing.JFrame;

public class MainScreen extends JFrame {

    public MainScreen() {
        Board board = new Board(30, 16,50);
        BoardPanel boardPanel = new BoardPanel(board);

        add(boardPanel);

        setTitle("Minecamp");
        setSize(690, 438);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(Boolean.TRUE);
    }

    public static void main(String[] args) {
        new MainScreen();
    }
}
