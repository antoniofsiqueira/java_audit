package tony_auditor.test;

import java.util.ArrayList;
import java.util.List;

public class DiffToolTest {
    public static void main(String[] args) {

        User previousUser = new User("John", 30);
        User currentUser = new User("Jim", 35);

        List<AuditEntry> changes = auditUser(previousUser, currentUser);

        for (AuditEntry entry : changes) {
            System.out.println("Property: " + entry.getPropertyPath());
            System.out.println("Previous: " + entry.getPreviousValue());
            System.out.println("Current: " + entry.getCurrentValue());
            System.out.println();
        }
    }

    static class User {
        private String firstName;
        private int age;

        public User(String firstName, int age) {
            this.firstName = firstName;
            this.age = age;
        }

        public String getFirstName() {
            return firstName;
        }

        public int getAge() {
            return age;
        }
    }

    static class AuditEntry {
        private String propertyPath;
        private Object previousValue;
        private Object currentValue;

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

    static List<AuditEntry> auditUser(User previousUser, User currentUser) {
        List<AuditEntry> changes = new ArrayList<>();

        if (!previousUser.getFirstName().equals(currentUser.getFirstName())) {
            changes.add(new AuditEntry("firstName", previousUser.getFirstName(), currentUser.getFirstName()));
        }

        if (previousUser.getAge() != currentUser.getAge()) {
            changes.add(new AuditEntry("age", previousUser.getAge(), currentUser.getAge()));
        }

        return changes;
    }
}
