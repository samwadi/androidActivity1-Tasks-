package com.example.myapplication;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private TaskManager taskManager;

    private EditText editTextTask;
    private ArrayAdapter<TaskItem> adapter;
    public static ArrayList<TaskItem> taskList;
    private SharedPreferences sharedPreferences;
    private static final String TASKS_KEY = "tasks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskManager = new TaskManager(this);

        editTextTask = findViewById(R.id.editTextTask);
        Button btnAdd = findViewById(R.id.btnAdd);
        ListView listViewTasks = findViewById(R.id.listViewTasks);

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        taskList = new ArrayList<>(loadTasksFromSharedPreferences());
        adapter = new TaskAdapter(this, taskList);
        listViewTasks.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle task item click
            }
        });
        Button btnShowDueTasks = findViewById(R.id.btnShowDueTasks);

        btnShowDueTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDueTasks();
            }
        });
        Button btnShowFinish = findViewById(R.id.btnFinish);
        btnShowFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFinishTasks();
            }
        });

    }
    private void showDueTasks() {
        Intent intent = new Intent(this, DueTasksActivity.class);
        startActivity(intent);
    }
 private void showFinishTasks() {
        Intent intent = new Intent(this, FinishedTasksActivity.class);
        startActivity(intent);
    }

    private void addTask() {
        String taskDescription = editTextTask.getText().toString().trim();

        if (!taskDescription.isEmpty()) {
            TaskItem newTask = new TaskItem(taskDescription,taskDescription, false);
            taskList.add(newTask);
            adapter.notifyDataSetChanged();
            saveTaskToSharedPreferences(newTask);
            editTextTask.setText("");
            taskManager.saveActiveTask(newTask);
        } else {
            Toast.makeText(this, "Task description cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveTaskToSharedPreferences(TaskItem taskItem) {
        String taskJson = taskItem.toJson();

        Set<String> existingTasks = sharedPreferences.getStringSet(TASKS_KEY, new HashSet<>());

        existingTasks.add(taskJson);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(TASKS_KEY, existingTasks);
        editor.apply();
    }


    private ArrayList<TaskItem> loadTasksFromSharedPreferences() {
        try {
            Set<String> taskJsonSet = sharedPreferences.getStringSet(TASKS_KEY, null);

            if (taskJsonSet != null) {
                ArrayList<TaskItem> loadedTasks = new ArrayList<>();

                for (String taskJson : taskJsonSet) {
                    loadedTasks.add(TaskItem.fromJson(taskJson));
                }

                return loadedTasks;
            } else {
                return new ArrayList<>();
            }

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.e("JsonSyntaxException", "Error in JSON parsing: " + e.getMessage());
        }
        return new ArrayList<>();
    }

}
