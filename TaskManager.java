/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multiplan_;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author malee
 */
class TaskManager {
      private List<Task> tasks = new ArrayList<>();
    private final String FILE_NAME = "tasks.txt";

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task getTaskByName(String name) {
        for (Task task : tasks) {
            if (task.getName().equalsIgnoreCase(name)) {
                return task;
            }
        }
        return null;
    }

    public void displayIncompleteTasks() {
        System.out.println("Tasks to be completed:");
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                System.out.println("Task: " + task.getName() + ", Due: " + task.getDueDate());
            }
        }
    }

    public void displayCompletedTasks() {
        System.out.println("Completed tasks:");
        for (Task task : tasks) {
            if (task.isCompleted()) {
                System.out.println("Task: " + task.getName());
            }
        }
    }
    public void scheduleTasks() {
        LocalTime currentTime = LocalTime.now().withSecond(0).withNano(0);

        for (Task task : tasks) {
            if (!task.isCompleted() && task.getHoursAllocated() > 0) {
                LocalDate taskDueDate = LocalDate.parse(task.getDueDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate today = LocalDate.now();

                long daysToDueDate = today.until(taskDueDate).getDays();
                if (daysToDueDate <= 0) continue;  // If task is due today or is overdue, don't schedule it

                int dailyHours = task.getHoursAllocated() / (int) daysToDueDate;
                if (task.getHoursAllocated() % daysToDueDate > 0) dailyHours++;

                if (dailyHours <= 2) {
                    task.setStartTime(currentTime);
                    currentTime = currentTime.plusHours(dailyHours);
                } else {
                    // Split the dailyHours across the day if they are more than 2
                    task.setStartTime(currentTime);
                    currentTime = currentTime.plusHours(2);  // First 2 hours
                    dailyHours -= 2;

                    while (dailyHours > 0) {
                        currentTime = currentTime.plusHours(1);  // 1-hour break
                        task.setStartTime(currentTime);
                        currentTime = currentTime.plusHours(2);  // Next 2 hours
                        dailyHours -= 2;
                    }
                }
            }
        }
        
    }
    public void displayTodaySchedule() {
        LocalDate today = LocalDate.now();
        System.out.println("Today's Schedule:");

        for (Task task : tasks) {
            if (!task.isCompleted() && task.getStartTime() != null) {
                LocalDate taskDueDate = LocalDate.parse(task.getDueDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if (taskDueDate.isAfter(today) || taskDueDate.isEqual(today)) {
                    System.out.println("Task: " + task.getName() + ", Start Time: " + task.getStartTime());
                }
            }
        }
    }
     public void saveTasks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

     public void loadTasks() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                tasks = (List<Task>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading tasks: " + e.getMessage());
            }
        }
    }

}
