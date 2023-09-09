package tony_auditor.model;

public class AuditEntry {
    private final String propertyPath;
    private final Object previousValue;
    private final Object currentValue;

    public AuditEntry(String propertyPath, Object previousValue, Object currentValue) {
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
