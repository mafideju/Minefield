package Model;

import Interface.ObserverFields;
import Enum.EventField;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private final Integer line;
    private final Integer column;
    private Boolean minedField = false;
    private Boolean opened = false;
    private Boolean closed = false;
    private Boolean marked = false;
    private List<Field> neighbors = new ArrayList<>();

    private List<ObserverFields> observers = new ArrayList<>();

    public Field(Integer line, Integer column) {
        this.line = line;
        this.column = column;
    }

    public void registerObservers(ObserverFields observerField) {
        observers.add(observerField);
    }

    public void notifyObservers(EventField event) {
        observers.stream().forEach(observerFields -> observerFields.eventHappened(this, event));
    }

    Boolean addNeighbors(Field neighbor) {
        Boolean differentLine = line != neighbor.line;
        Boolean differentColumn = column != neighbor.line;
        Boolean diagonal = differentLine && differentColumn;
        Integer deltaLine = Math.abs(line - neighbor.line);
        Integer deltaColumn = Math.abs(column - neighbor.column);
        Integer generalDelta = deltaLine + deltaColumn;

        if (generalDelta == 1 && !diagonal) {
            neighbors.add(neighbor);
            return Boolean.TRUE;
        } else if (generalDelta == 2 && diagonal) {
            neighbors.add(neighbor);
            return Boolean.TRUE;
        } else {
            neighbors.add(neighbor);
            return Boolean.FALSE;
        }
    }

    public void switchMarkedField() {
        if (!opened) {
            marked = !marked;
            if (marked) {
                notifyObservers(EventField.CHECK);
            } else {
                notifyObservers(EventField.UNCHECK);
            }

        }
    }

    public Boolean openField() {
        if (!opened && !marked) {
            if (minedField) {
                notifyObservers(EventField.EXPLODE);
                return Boolean.TRUE;
            }

            setOpened(Boolean.TRUE);

            if (secureNeighborhood()) {
                neighbors.forEach(nb -> nb.openField());
            }
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    Boolean secureNeighborhood() {
        return neighbors.stream().noneMatch(neigh -> neigh.minedField);
    }

    void toMineSomeField() {
        minedField = Boolean.TRUE;
    }

    public Boolean doneFields() {
        Boolean unfolded = !minedField && opened;
        Boolean protecteed = minedField && marked;

        return unfolded || protecteed;
    }

    Long minesInTheField() {
        return  neighbors.stream().filter(neighbor -> neighbor.minedField).count();
    }

    void resetGame() {
        minedField = Boolean.FALSE;
        marked = Boolean.FALSE;
        opened = Boolean.FALSE;
    }


    public Boolean getMinedField() {
        return minedField;
    }

    public void setMinedField(Boolean minedField) {
        this.minedField = minedField;
    }

    public Boolean getOpened() {
        return opened;
    }

    public Boolean getClosed() {
        return closed;
    }

    public Boolean getMarked() {
        return marked;
    }

    public Integer getLine() {
        return line;
    }

    public Integer getColumn() {
        return column;
    }

    public Boolean isOpened() {
        return opened;
    }

    public void setOpened(Boolean opened) {
        this.opened = opened;

        if (opened) {
            notifyObservers(EventField.OPEN);
        }
    }

    public Boolean isClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Boolean isMarked() {
        return marked;
    }

    public void setMarked(Boolean marked) {
        this.marked = marked;
    }

    public List<Field> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Field> neighbors) {
        this.neighbors = neighbors;
    }
}
