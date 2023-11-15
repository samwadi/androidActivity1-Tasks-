package com.example.myapplication;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TaskManager {

    // Define keys for SharedPreferences
    private static final String ACTIVE_TASKS_KEY = "active_tasks";
    private static final String FINISHED_TASKS_KEY = "finished_tasks";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    // Constructor
    public TaskManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new Gson();
    }

    public ArrayList<TaskItem> loadActiveTasks() {
        return loadTasks(ACTIVE_TASKS_KEY);
    }



    public ArrayList<TaskItem> loadFinishedTasks() {
        Set<String> finishedTaskJsonSet = sharedPreferences.getStringSet(FINISHED_TASKS_KEY, null);

        if (finishedTaskJsonSet != null) {
            ArrayList<TaskItem> loadedFinishedTasks = new ArrayList<>();

            for (String taskJson : finishedTaskJsonSet) {
                loadedFinishedTasks.add(TaskItem.fromJson(taskJson));
            }

            return loadedFinishedTasks;
        } else {
            return new ArrayList<>();
        }
    }

    public void saveActiveTask(TaskItem taskItem) {
        saveTask(taskItem, ACTIVE_TASKS_KEY);
    }

    public void saveFinishedTask(TaskItem taskItem) {
        saveTask(taskItem, FINISHED_TASKS_KEY);
    }

    private ArrayList<TaskItem> loadTasks(String key) {
        Set<String> taskJsonSet = sharedPreferences.getStringSet(key, null);

        if (taskJsonSet != null) {
            ArrayList<TaskItem> loadedTasks = new ArrayList<>();

            for (String taskJson : taskJsonSet) {
                loadedTasks.add(TaskItem.fromJson(taskJson));
            }

            return loadedTasks;
        } else {
            return new ArrayList<>();
        }
    }

    private void saveTask(TaskItem taskItem, String key) {
        // Convert TaskItem to JSON using Gson
        String taskJson = taskItem.toJson();

        // Get the existing set of tasks from SharedPreferences
        Set<String> existingTasks = sharedPreferences.getStringSet(key, new HashSet<>());

        // Add the new task JSON to the set
        existingTasks.add(taskJson);

        // Save the updated set back to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, existingTasks);
        editor.apply();
    }
}
