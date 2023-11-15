package com.example.myapplication;

import com.google.gson.Gson;

public class TaskItem {
    private String description;
    private boolean isFinished;
    private String title;


    public TaskItem(String title,String description,boolean isFinished) {
        this.description = description;
        this.isFinished = false;
        this.title=title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = true;
    }

    // Convert TaskItem to a string representation for storage in SharedPreferences
    @Override
    public String toString() {
        return description + "," + isFinished;
    }

    // Convert a string representation back to a TaskItem object
    public static TaskItem fromString(String taskString) {
        String[] parts = taskString.split(",");
        if (parts.length == 2) {
            String title=parts[0];
            String description = parts[1];
            boolean iFinished = Boolean.parseBoolean(parts[2]);
            return new TaskItem(title ,description,iFinished);
        }
        return null;
    }
    public static TaskItem fromJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, TaskItem.class);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getTitle() {
        return title;
    }
}
