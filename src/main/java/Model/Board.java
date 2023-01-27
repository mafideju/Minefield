package Model;

import Interface.ObserverFields;
import Enum.EventField;
import Utils.EventResult;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Board implements ObserverFields {

    private final int lines;
    private final int columns;
    private final int mines;

    private final List<Field> fields = new ArrayList<>();
    private final List<Consumer<EventResult>> observers = new ArrayList<>();

    public Board(int lines, int columns, int mines) {
        this.lines = lines;
        this.columns = columns;
        this.mines = mines;


        this.generateFields();
        this.associateNeighbors();
        this.mineLottery();
    }

    public void registerObservers(Consumer<EventResult> observer) {
        observers.add(observer);
    }

    public void notifyObservers(Boolean matchResult) {
        observers.stream().forEach(observerFields -> observerFields.accept(new EventResult(matchResult)));
    }

    public void forEachCustom(Consumer<Field> function) {
        fields.forEach(function);
    }

    public void openField(int line, int column) {
        fields.parallelStream()
                .filter(field -> field.getLine() == line && field.getColumn() == column)
                .findFirst()
                .ifPresent(field -> field.openField());
    }

    private void showMines() {
        fields.stream()
            .filter(field -> field.getMinedField())
            .forEach(field -> field.setOpened(Boolean.TRUE));
    }

    public void switchMarkedField(int line, int column) {
        fields.parallelStream()
                .filter(field -> field.getLine() == line && field.getColumn() == column)
                .findFirst()
                .ifPresent(field -> field.switchMarkedField());
    }

    private void generateFields() {
        for (int line = 0; line < lines; line++) {
            for (int column = 0; column < columns; column++) {
                Field field = new Field(line, column);
                field.registerObservers(this);
                fields.add(field);
            }
        }
    }

    private void mineLottery() {
        Long armedMines = 0L;
        Predicate<Field> mined = field -> field.getMinedField();

        do {
            int random = (int) (Math.random() * fields.size());
            fields.get(random).toMineSomeField();
            armedMines = fields.stream().filter(mined).count();
        } while(armedMines < mines);
    }

    private void associateNeighbors() {
        for (Field field1: fields) {
            for (Field field2: fields) {
                field1.addNeighbors(field2);
            }
        }
    }

    public Boolean doneFields() {
        return fields.stream().allMatch(field -> field.doneFields());
    }

    public void resetGame() {
        fields.stream().forEach(field -> field.resetGame());
        this.mineLottery();
    }

    @Override
    public void eventHappened(Field field, EventField event) {
        if (event == EventField.EXPLODE) {
            this.showMines();
            notifyObservers(Boolean.FALSE);
        } else if (doneFields()) {
            notifyObservers(Boolean.TRUE);
        }
    }

    public int getLines() {
        return lines;
    }

    public int getColumns() {
        return columns;
    }

    public int getMines() {
        return mines;
    }

    public List<Field> getFields() {
        return fields;
    }

    public List<Consumer<EventResult>> getObservers() {
        return observers;
    }
}
