package View;

import Model.Board;
import View.Widgets.FieldButton;
import java.awt.GridLayout;
import javax.swing.JPanel;


public class BoardPanel extends JPanel {

    public BoardPanel(Board board) {
        setLayout(new GridLayout(board.getColumns(), board.getLines()));

        board.forEachCustom(field -> add(new FieldButton(field)));

        board.registerObservers(eventResult -> System.out.println(eventResult.toString()));
    }
}
