package com.example.myapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<TaskItem> {

    public TaskAdapter(Context context, ArrayList<TaskItem> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskItem task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }

        TextView textViewTask = convertView.findViewById(R.id.textViewTask);
        CheckBox checkBoxFinished = convertView.findViewById(R.id.checkBoxFinished);

        textViewTask.setText(task.getDescription());
        checkBoxFinished.setChecked(task.isFinished());

        checkBoxFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setFinished(checkBoxFinished.isChecked());
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
