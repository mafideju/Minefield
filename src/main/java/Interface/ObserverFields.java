package Interface;

import Model.Field;
import Enum.EventField;

@FunctionalInterface
public interface ObserverFields {
    void eventHappened(Field field, EventField event);
}
