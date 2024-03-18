package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.ListIterator;


public class TaskManager {
    private LinkedList<Task> tasks;
    private JSONManager jsonManager = new JSONManager(new ObjectMapper());
    public static Logger logger = LogManager.getLogger(App.class);

    public TaskManager() {
        tasks = new LinkedList<>();
    }

    public void printTasks() {
        for (Task task : tasks) {
            System.out.println("taskId:" + task.getTaskId() + ", taskName:" + task.getTaskName() + ", taskDetail:" + task.getTaskDetail() + ", status" + task.getStatus());
        }
    }

    //get all task from JSON
    public LinkedList<Task> getAllTask() {
        LinkedList<Task> tasks = jsonManager.readTaskList();

        ListIterator<Task> iterator = tasks.listIterator();
        Task previousTask = null;

        while (iterator.hasNext()) {
            Task currentTask = iterator.next();
            currentTask.setPrevious(previousTask);

            if (previousTask != null) {
                previousTask.setNext(currentTask);
            }

            previousTask = currentTask;
        }

        // Set the first and last task's references
        if (!tasks.isEmpty()) {
            tasks.getFirst().setPrevious(null);
            tasks.getLast().setNext(null);
        }

        logger.info("Task retrieved");
        return tasks;
    }

    //add a new task to JSON
    public boolean addTask(Task task) {
        LinkedList<Task> tasks = jsonManager.readTaskList();

        if (tasks.isEmpty()) {
            task.setTaskId(1);
            task.setNext(null);
            task.setPrevious(null);
        } else {
            Task lastNode = tasks.getLast();
            task.setPrevious(lastNode);
            task.setNext(null);
            task.setTaskId(lastNode.getTaskId()+1);
        }

        tasks.add(task);

        boolean result = jsonManager.writeTaskList(tasks);

        if (result){
            logger.info("Task was added");
            return true;
        }else {
            return false;
        }
    }

    //Delete a Task
    public boolean deleteTask(int taskId) {
        LinkedList<Task> tasks = jsonManager.readTaskList();

        Task taskToDelete = null;
        for (Task task : tasks) {
            if (task.getTaskId() == taskId) {
                taskToDelete = task;
                break;
            }
        }

        if (taskToDelete != null) {
            int index = tasks.indexOf(taskToDelete);

            if (index > 0) {
                Task previousTask = tasks.get(index - 1);
                previousTask.setNext(taskToDelete.getNext());
            }
            if (index < tasks.size() - 1) {
                Task nextTask = tasks.get(index + 1);
                nextTask.setPrevious(taskToDelete.getPrevious());
            }

            tasks.remove(taskToDelete);

            jsonManager.writeTaskList(tasks);

            logger.info("Task was deleted");
            return true;
        }
        logger.error("Task was not found");
        return false; // Task not found
    }

    //Edit a Task
    public boolean editTask(Task task) {
        LinkedList<Task> tasks = jsonManager.readTaskList();

        Task taskToEdit = null;
        for (Task existingTask : tasks) {
            if (existingTask.getTaskId() == task.getTaskId()) {
                taskToEdit = existingTask;
                break;
            }
        }

        // If the task is found, update its properties
        if (taskToEdit != null) {
            taskToEdit.setTaskName(task.getTaskName());
            taskToEdit.setTaskDetail(task.getTaskDetail());
            taskToEdit.setStatus(task.getStatus());

            jsonManager.writeTaskList(tasks);

            logger.info("Task was updated");
            return true;
        }

        logger.error("Task was not found");
        return false; // Task not found
    }

    //move the position of a Task
    public boolean moveTask(int taskId, int newPos) {
        LinkedList<Task> taskList = jsonManager.readTaskList();


        Task taskToMove = null;
        int currentIndex = -1;
        ListIterator<Task> iterator = taskList.listIterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getTaskId() == taskId) {
                taskToMove = task;
                currentIndex = iterator.previousIndex();
                break;
            }
        }

        if (taskToMove != null) {
            newPos = Math.max(0, Math.min(newPos, taskList.size() - 1));

            taskList.remove(currentIndex);

            taskList.add(newPos, taskToMove);

            jsonManager.writeTaskList(taskList);
            logger.info("Task position updated");
            return true;
        }

        logger.error("Task was not found");
        return false;
    }
}