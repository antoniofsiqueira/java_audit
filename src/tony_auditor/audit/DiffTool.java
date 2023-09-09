package tony_auditor.audit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import tony_auditor.model.AuditEntry;

public class DiffTool {

    public static List<AuditEntry> diff(Object previousState, Object currentState) {
        List<AuditEntry> changes = new ArrayList<>();
        compareObjects("", previousState, currentState, changes);
        return changes;
    }

    private static void compareObjects(String propertyPath, Object previousState, Object currentState,
            List<AuditEntry> changes) {
        Class<?> clazz = previousState.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = propertyPath.isEmpty() ? field.getName() : propertyPath + "." + field.getName();

            try {
                Object previousValue = field.get(previousState);
                Object currentValue = field.get(currentState);

                if (!Objects.equals(previousValue, currentValue)) {
                    changes.add(new AuditEntry(fieldName, previousValue, currentValue));
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
            List<AuditEntry> changes) {
        if (!Objects.equals(previousList, currentList)) {
            List<Object> addedItems = new ArrayList<>(currentList);
            addedItems.removeAll(previousList);

            List<Object> removedItems = new ArrayList<>(previousList);
            removedItems.removeAll(currentList);

            changes.add(new AuditEntry(propertyPath + ".added", addedItems, null));
            changes.add(new AuditEntry(propertyPath + ".removed", null, removedItems));
        }
    }
}
