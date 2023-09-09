# DiffTool

The DiffTool is a Java utility for comparing two objects and tracking the differences between them. It can identify changes in properties and lists within objects. This README.md provides an overview of how the DiffTool works and includes information about the development time.

## How It Works

The DiffTool provides a simple and efficient way to compare two objects and generate a list of changes (AuditEntries). Here's how it works:

1. **Creating an Audit Entry**: Whenever a property or list in the objects differs between the previous and current states, an Audit Entry is created to track the change.

2. **Nested Objects and Lists**: The DiffTool handles nested objects and lists by using dot notation for property paths. For example, if you have a nested object `address` with a property `city`, the property path will be `address.city`.

3. **List Changes**: When items are added or removed from a list, the DiffTool creates Audit Entries with the `.added` or `.removed` suffix in the property path.

4. **Usage**: You can use the DiffTool by calling the `diff` method, passing the previous and current states as arguments. It returns a list of Audit Entries representing the changes between the two objects.

## Example

Here's a simple example of how to use the DiffTool:

```java
// Define your previous and current state objects
User previousUser = new User("John", 30);
User currentUser = new User("Jim", 35);

// Get the list of changes
List<AuditEntry> changes = DiffTool.diff(previousUser, currentUser);

// Iterate through the `changes` list and handle the changes
for (AuditEntry change : changes) {
    System.out.println("Property: " + change.getPropertyPath());
    System.out.println("Previous: " + change.getPreviousValue());
    System.out.println("Current: " + change.getCurrentValue());
    System.out.println();
}
