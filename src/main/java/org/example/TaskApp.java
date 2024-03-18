package org.example;

import java.util.LinkedList;
import java.util.Scanner;

public class TaskApp {
    private TaskManager taskManager;
    private Scanner scanner;

    public TaskApp() {
        taskManager = new TaskManager();
        scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            viewTasks();
            System.out.println("-----------------------------------------------------------------------------------------------------------");
            System.out.println("1. Add New Task");
            System.out.println("2. Delete Task");
            System.out.println("3. Edit Task");
            System.out.println("4. Move Task");
            System.out.println("0. Exit");

            int choice = getIntInput("Enter your choice: ");

            scanner.nextLine();

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    deleteTask();
                    break;
                case 3:
                    editTask();
                    break;
                case 4:
                    moveTask();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewTasks() {
        System.out.println("View Tasks");
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.printf("%-12s | %-24s | %-49s | %-12s%n", "Piority", "Task Name", "Details", "Status");
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        LinkedList<Task> tasks = taskManager.getAllTask();

        if (tasks.isEmpty()) {
            System.out.println("There are no tasks currently.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                System.out.printf("%-12d | %24s | %49s | %12s%n",
                        i + 1, task.getTaskName(), task.getTaskDetail(), task.getStatus());
            }
        }

        System.out.println();
    }

    private void addTask() {
        System.out.println("Add New Task");
        System.out.println("-------------------------------------");

        while (true) {
            System.out.print("Enter task name: ");
            String taskName = scanner.nextLine();

            System.out.print("Enter task detail: ");
            String taskDetail = scanner.nextLine();

            Task task = new Task();
            task.setTaskName(taskName);
            task.setTaskDetail(taskDetail);
            task.setStatus("Pending");

            if (taskManager.addTask(task)) {
                System.out.println("Task was added successfully.");
            } else {
                System.out.println("Failed to add task. Please try again.");
            }

            System.out.println();

            System.out.print("Enter 'A' to add another task or 'M' to return to the main menu: ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("M")) {
                break;
            }
        }
    }

    private void deleteTask() {
        System.out.println("Delete Task");
        System.out.println("-------------------------------------");

        int displayOrderIndex = getIntInput("Enter the task number to delete: ");

        LinkedList<Task> tasks = taskManager.getAllTask();

        if (displayOrderIndex >= 1 && displayOrderIndex <= tasks.size()) {
            Task taskToDelete = tasks.get(displayOrderIndex - 1);

            if (taskManager.deleteTask(taskToDelete.getTaskId())) {
                System.out.println("Task was deleted successfully.");
            } else {
                System.out.println("Failed to delete task. Please try again.");
            }
        } else {
            System.out.println("Invalid display order index. Please try again.");
        }

        System.out.println();
    }

    private void editTask() {
        System.out.println("Edit Task");
        System.out.println("---------------------");

        int taskId = getIntInput("Enter the ID of the task to edit: ");

        System.out.print("Enter task name: ");
        String taskName = scanner.nextLine();

        System.out.print("Enter task detail: ");
        String taskDetail = scanner.nextLine();

        System.out.print("Enter task status: ");
        String status = scanner.nextLine();

        Task task = new Task();
        task.setTaskName(taskName);
        task.setTaskDetail(taskDetail);
        task.setStatus(status);

        if (taskManager.editTask(task)) {
            System.out.println("Task was edited successfully.");
        } else {
            System.out.println("Task not found or failed to edit. Please try again.");
        }

        System.out.println();
    }

    private void moveTask() {
        System.out.println("Move Task");
        System.out.println("-------------------------------------");

        int orderPosition = getIntInput("Enter the order position of the task to move: ");
        int newPos = getIntInput("Enter the new position for the task: ");

        LinkedList<Task> tasks = taskManager.getAllTask();

        if (orderPosition >= 1 && orderPosition <= tasks.size() && newPos >= 1 && newPos <= tasks.size()) {
            Task taskToMove = tasks.get(orderPosition - 1);

            if (taskManager.moveTask(taskToMove.getTaskId(), newPos)) {
                System.out.println("Task position was updated successfully.");
            } else {
                System.out.println("Failed to update task position. Please try again.");
            }
        } else {
            System.out.println("Invalid order position or new position. Please try again.");
        }

        System.out.println();
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Invalid input. Please enter a valid integer: ");
        }
        return scanner.nextInt();
    }


    //update task status
}