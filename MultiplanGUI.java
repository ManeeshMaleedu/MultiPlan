/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multiplan_;

/**
 *
 * @author malee
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MultiplanGUI {
    private static TaskManager taskManager = new TaskManager();
    private static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Multiplan");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create a tabbed pane
            JTabbedPane tabbedPane = new JTabbedPane();

            // Create the Main Application panel
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new GridLayout(8, 1));

            // Create buttons for each option
            JButton addTaskButton = new JButton("Add Task");
            JButton markCompletedButton = new JButton("Mark Task as Completed");
            JButton viewTasksButton = new JButton("View Tasks to be Completed");
            JButton viewCompletedButton = new JButton("View Completed Tasks");
            JButton setHoursButton = new JButton("Set Hours for Task");
            JButton modifyTaskButton = new JButton("Modify a Task");
            JButton deleteTaskButton = new JButton("Delete Task");
            JButton quitButton = new JButton("Quit");

            // Add buttons to the main panel
            mainPanel.add(addTaskButton);
            mainPanel.add(markCompletedButton);
            mainPanel.add(viewTasksButton);
            mainPanel.add(viewCompletedButton);
            mainPanel.add(setHoursButton);
            mainPanel.add(modifyTaskButton);
            mainPanel.add(deleteTaskButton);
            mainPanel.add(quitButton);

            // Add the main panel to the tabbed pane
            tabbedPane.addTab("Main Application", mainPanel);

            // Add action listeners for each option
            addTaskButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = JOptionPane.showInputDialog(frame, "Enter task name:");
                    String dueDate = JOptionPane.showInputDialog(frame, "Enter due date (YYYY-MM-DD):");
                    
                    Task task = new Task(name, dueDate);
                    taskManager.addTask(task);
                    JOptionPane.showMessageDialog(frame, "Task added successfully.");
                }
            });

            markCompletedButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = JOptionPane.showInputDialog(frame, "Enter task name to mark as completed:");
                    
                    Task task = taskManager.getTaskByName(name);
                    if (task != null) {
                        task.setCompleted(true);
                        JOptionPane.showMessageDialog(frame, "Task marked as completed.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Task not found.");
                    }
                }
            });

            viewTasksButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    taskManager.displayIncompleteTasks();
                }
            });

            viewCompletedButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    taskManager.displayCompletedTasks();
                }
            });

            setHoursButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    while (true) {
                        String name = JOptionPane.showInputDialog(frame, "Enter task name to set hours (or type 'exit' to go back):");

                        if (name.equalsIgnoreCase("exit")) {
                            break;
                        }

                        Task task = taskManager.getTaskByName(name);
                        if (task != null) {
                            while (true) {
                                try {
                                    int hours = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter hours allocated:"));
                                    
                                    if (hours < 0) {
                                        JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a non-negative number for hours.");
                                        continue;
                                    }

                                    task.setHoursAllocated(hours);
                                    JOptionPane.showMessageDialog(frame, "Hours set for the task.");
                                    break;

                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number for hours.");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Task not found.");
                        }
                    }
                }
            });

            modifyTaskButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String oldName = JOptionPane.showInputDialog(frame, "Enter name of the task to modify:");

                    Task task = taskManager.getTaskByName(oldName);
                    if (task != null) {
                        String newName = JOptionPane.showInputDialog(frame, "Enter new task name (or press Enter to skip):");
                        if (!newName.isEmpty()) {
                            task.setName(newName);
                        }

                        String newDueDate = JOptionPane.showInputDialog(frame, "Enter new due date (YYYY-MM-DD or press Enter to skip):");
                        if (!newDueDate.isEmpty()) {
                            task.setDueDate(newDueDate);
                        }

                        JOptionPane.showMessageDialog(frame, "Task modified.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Task not found.");
                    }
                }
            });

            deleteTaskButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = JOptionPane.showInputDialog(frame, "Enter task name to delete:");

                    taskManager.deleteTaskByName(name);
                }
            });

            quitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    taskManager.saveTasks();
                }
            });

            // Add the tabbed pane to the frame
            frame.add(tabbedPane, BorderLayout.CENTER);

            frame.pack();
            frame.setLocationRelativeTo(null); // Center the frame
            frame.setVisible(true);
        });
    }
}
