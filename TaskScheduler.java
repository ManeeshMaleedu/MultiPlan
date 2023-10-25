/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multiplan_;

/**
 *
 * @author malee
 */
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;






public class TaskScheduler {
    private static TaskManager taskManager = new TaskManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
            taskManager.loadTasks(); 
            
             boolean keepRunning = true;
             do {
              displayMenu();
               try {
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

            
            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    markTaskAsCompleted();
                    break;
                case 3:
                    taskManager.scheduleTasks();
                    checkForReminders();
                    taskManager.displayTodaySchedule();
                    break;
                case 4:
                    taskManager.displayCompletedTasks();
                    break;
                case 5:
                    setHoursForTask();
                    break;
                case 6:
                    keepRunning = false;  // Exit the loop
                    break;
        default:
                        System.out.println("Invalid choice. Please choose again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();  // Consume invalid input
            }
        } while (keepRunning);

        taskManager.saveTasks();  // Save tasks before program exits
    }

    private static void displayMenu() {
            System.out.println("\nOptions:");
            System.out.println("1. Add task");
            System.out.println("2. Mark task as completed");
            System.out.println("3. View tasks to be completed");
            System.out.println("4. View completed tasks");
            System.out.println("5. Set hours for task");
            System.out.println("6. Quit");
    }

            
     private static void checkForReminders() {
        LocalDate today = LocalDate.now();
        for (Task task : taskManager.getTasks()) {
            if (!task.isCompleted()) {
                LocalDate taskDueDate = LocalDate.parse(task.getDueDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if (today.plusDays(1).isEqual(taskDueDate)) {
                    System.out.println("Reminder: Task '" + task.getName() + "' is due soon!");
                }
            }
        }
    }

    private static void addTask() {
        System.out.print("Enter task name: ");
        String name = scanner.nextLine();

        System.out.print("Enter due date (YYYY-MM-DD): ");
        String dueDate = scanner.nextLine();

        Task task = new Task(name, dueDate);
        taskManager.addTask(task);
        System.out.println("Task added.");
    }

    private static void markTaskAsCompleted() {
        System.out.print("Enter task name to mark as completed: ");
        String name = scanner.nextLine();

        Task task = taskManager.getTaskByName(name);
        if (task != null) {
            task.setCompleted(true);
            System.out.println("Task marked as completed.");
        } else {
            System.out.println("Task not found.");
        }
    }

private static void setHoursForTask() {
    while (true) { // This loop will keep asking for input until valid input is provided or user chooses to exit
        System.out.print("Enter task name to set hours (or type 'exit' to go back): ");
        String name = scanner.nextLine();

        if (name.equalsIgnoreCase("exit")) {
            break; // Break out of the loop to go back
        }

        Task task = taskManager.getTaskByName(name);
        if (task != null) {
            while (true) { // This inner loop is for getting a valid hour input
                System.out.print("Enter hours allocated: ");
                try {
                    int hours = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (hours < 0) { // Check for negative hours
                        System.out.println("Invalid input. Please enter a non-negative number for hours.");
                        continue;
                    }

                    task.setHoursAllocated(hours);
                    System.out.println("Hours set for the task.");
                    break; // Break out of the inner loop after successful operation

                } catch (InputMismatchException e) { // Catch invalid input that's not an integer
                    System.out.println("Invalid input. Please enter a valid number for hours.");
                    scanner.nextLine(); // Consume the invalid input
                }
            }
        } else {
            System.out.println("Task not found.");
        }
    }
}

}




