package tony_auditor;

import java.util.List;

import tony_auditor.audit.DiffTool;
import tony_auditor.model.AuditEntry;

public class App {
    public static void main(String[] args) {

        User previousUser = new User("John", 30);
        User currentUser = new User("Jim", 35);

        List<AuditEntry> changes = DiffTool.diff(previousUser, currentUser);

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
    }
}
