package View.Widgets;

import Interface.ObserverFields;
import Model.Field;
import Enum.EventField;
import Enum.Colors;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;


public class FieldButton extends JButton implements ObserverFields, MouseListener {

    private Field field;


    public FieldButton(Field field) {
        this.field = field;

        setBackground(Colors.DEFAULT);
        setBorder(BorderFactory.createBevelBorder(0));

        addMouseListener(this);
        field.registerObservers(this);
    }


    @Override
    public void eventHappened(Field field, EventField event) {
        switch (event) {
            case OPEN:
                this.applyOpenStyle();
                break;
            case CHECK:
                this.applyCheckStyle();
                break;
            case EXPLODE:
                this.applyExplodeStyle();
                break;
            default:
                this.applyBasicStyle();
        }
    }

    private void applyOpenStyle() {
        setBackground(Colors.DEFAULT);
        setBorder(BorderFactory.createLineBorder(Colors.GRAY));
    }

    private void applyCheckStyle() {
    }

    private void applyExplodeStyle() {
    }

    private void applyBasicStyle() {
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1) {
            field.openField();
            System.out.println("Botão Esquerdo");
        } else {
            field.switchMarkedField();
            System.out.println("Botão Direito");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
