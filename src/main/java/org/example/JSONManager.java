package org.example;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JSONManager {

    public static Logger logger = LogManager.getLogger(App.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static File tasksJSONFile = new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "tasks.json");

    public JSONManager(ObjectMapper objectMapper) {
    }

    public LinkedList<Task> readTaskList() {
        LinkedList<Task> tasks = new LinkedList<>();

        try {
            logger.info("Attempt to read tasks data");
            tasks = objectMapper.readValue(tasksJSONFile, new TypeReference<LinkedList<Task>>() {});
        } catch (FileNotFoundException e) {
            logger.error("File Not Found Exception: " + e.getMessage());
        } catch (JsonMappingException e) {
            logger.error("JSON Mapping Exception: " + e.getMessage());
        } catch (IOException e) {
            logger.error("IO Exception: " + e.getMessage());
        }

        logger.info("Successfully retrieved tasks data");
        return tasks;
    }


    public boolean writeTaskList(List<Task> tasks) {
        try {

            logger.info("Attempt to write tasks data");
            objectMapper.writeValue(tasksJSONFile, tasks);
            logger.info("Successfully wrote tasks data");
            return true;

        } catch (FileNotFoundException e) {

            logger.error("File Not Found Exception: " + e.getMessage());

        } catch (JsonGenerationException e) {

            logger.error("JSON Generation Exception: " + e.getMessage());

        } catch (JsonMappingException e) {

            logger.error("JSON Mapping Exception: " + e.getMessage());

        } catch (IOException e) {

            logger.error("IO Exception: " + e.getMessage());

        }
        return false;
    }



}
