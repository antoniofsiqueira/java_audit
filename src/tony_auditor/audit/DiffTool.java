package tony_auditor.audit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DiffTool {

    public static List<Change> diff(Object previousState, Object currentState) {
        List<Change> changes = new ArrayList<>();
        compareObjects("", previousState, currentState, changes);
        return changes;
    }

    private static void compareObjects(String propertyPath, Object previousState, Object currentState,
            List<Change> changes) {
        Class<?> clazz = previousState.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = propertyPath.isEmpty() ? field.getName() : propertyPath + "." + field.getName();

            try {
                Object previousValue = field.get(previousState);
                Object currentValue = field.get(currentState);

                if (!Objects.equals(previousValue, currentValue)) {
                    changes.add(new Change(fieldName, previousValue, currentValue));
                }

                if (isListField(field)) {
                    compareLists(fieldName, (List<?>) previousValue, (List<?>) currentValue, changes);
                } else {
                    compareObjects(fieldName, previousValue, currentValue, changes);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isListField(Field field) {
        return List.class.isAssignableFrom(field.getType());
    }

    private static void compareLists(String propertyPath, List<?> previousList, List<?> currentList,
            List<Change> changes) {
        if (!Objects.equals(previousList, currentList)) {
            List<Object> addedItems = new ArrayList<>(currentList);
            addedItems.removeAll(previousList);

            List<Object> removedItems = new ArrayList<>(previousList);
            removedItems.removeAll(currentList);

            changes.add(new Change(propertyPath + ".added", addedItems, null));
            changes.add(new Change(propertyPath + ".removed", null, removedItems));
        }
    }

    public static class Change {
        private final String propertyPath;
        private final Object previousValue;
        private final Object currentValue;

        public Change(String propertyPath, Object previousValue, Object currentValue) {
            this.propertyPath = propertyPath;
            this.previousValue = previousValue;
            this.currentValue = currentValue;
        }

        public String getPropertyPath() {
            return propertyPath;
        }

        public Object getPreviousValue() {
            return previousValue;
        }

        public Object getCurrentValue() {
            return currentValue;
        }
    }
}
