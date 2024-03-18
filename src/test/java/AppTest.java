import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.JSONManager;
import org.example.Task;
import org.example.TaskManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class AppTest {

    @Mock
    private ObjectMapper objectMapper;

    JSONManager jsonManager = new JSONManager(new ObjectMapper());
    TaskManager taskManager = new TaskManager();


    @Test
    public void testReadTaskList() {
        // Create an instance of the J SONManager class

        // Call the readTaskList() method
        LinkedList<Task> tasks = jsonManager.readTaskList();

        // Assertions
        assertNotNull(tasks);

        assertNotNull(tasks);
    }



    @Test
    void writeTaskList_ReturnsTrueOnSuccess() throws IOException {
        // Arrange
        LinkedList<Task> tasks = new LinkedList<>();

        Task task1 = new Task(1, "task1", "taskDetail1", "pending", null, null);
        Task task2 = new Task(2, "task2", "taskDetail2", "completed", null, null);
        Task task3 = new Task(3, "task3", "taskDetail3", "completed", null, null);

        task1.setNext(task2);
        task2.setNext(task3);
        task2.setPrevious(task1);
        task3.setPrevious(task2);

        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        // Act
        boolean result = jsonManager.writeTaskList(tasks);

        // Assert
        assertTrue(result);
    }

    @Test
    void getAllTask_returnsTaskLinkedListOnSuccess() {
        LinkedList<Task> tasks = taskManager.getAllTask();

        // Assert the linked list structure
        for (int i = 0; i < tasks.size(); i++) {
            Task currentTask = tasks.get(i);

            // Assert previous task
            if (i > 0) {
                Task previousTask = tasks.get(i - 1);
                assertEquals(previousTask, currentTask.getPrevious(), "Incorrect previous task for Task " + currentTask.getTaskId());
            } else {
                assertNull(currentTask.getPrevious(), "Previous task should be null for the first task");
            }

            // Assert next task
            if (i < tasks.size() - 1) {
                Task nextTask = tasks.get(i + 1);
                assertEquals(nextTask, currentTask.getNext(), "Incorrect next task for Task " + currentTask.getTaskId());
            } else {
                assertNull(currentTask.getNext(), "Next task should be null for the last task");
            }
        }
    }

    @Test
    void addTask_returnsTrueOnSuccess() {
        Task newTask = new Task();
        newTask.setTaskName("names");
        newTask.setTaskDetail("details");
        newTask.setStatus("pending");

        Boolean results = taskManager.addTask(newTask);
        assertTrue(results);
    }

    @Test
    void deleteTask_returnsTrueOnSuccess() {
        // Read the initial task list from the JSON file
        LinkedList<Task> initialTaskList = taskManager.getAllTask();
        System.out.println("initialTaskList" + initialTaskList);

        // Ensure that there is at least one task in the initial list
        assertFalse(initialTaskList.isEmpty());

        // Get the task to delete (last entry in the list)
        Task taskToDelete = initialTaskList.getLast();

        // Delete the task
        Boolean deleteResult = taskManager.deleteTask(taskToDelete.getTaskId());
        assertTrue(deleteResult);

        // Read the updated task list from the JSON file
        LinkedList<Task> updatedTaskList = taskManager.getAllTask();
        System.out.println("deleted task on TaskList" + updatedTaskList);

        // Assert that the task has been deleted
        assertFalse(updatedTaskList.contains(taskToDelete));
    }

    @Test
    void moveTask_returnsTrueOnSuccess() {
        LinkedList<Task> tasks = jsonManager.readTaskList();
        System.out.println("initial TaskList" + tasks);

        boolean result = taskManager.moveTask(1, 100);

        assertTrue(result);

        LinkedList<Task> tasks2 = jsonManager.readTaskList();

        System.out.println("updated TaskList" + tasks2);
        assertEquals(1, tasks2.getLast().getTaskId());
    }
}
