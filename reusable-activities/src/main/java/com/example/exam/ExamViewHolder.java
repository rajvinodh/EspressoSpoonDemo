package com.example.exam;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.reusable_activities.R;

public class ExamViewHolder extends RecyclerView.ViewHolder {
    TextView examName;
    TextView examMessage;
    TextView examDate;
    View view;

    ExamViewHolder(View itemView) {
        super(itemView);
        examName
                = (TextView) itemView
                .findViewById(R.id.examName);
        examDate
                = (TextView) itemView
                .findViewById(R.id.examDate);
        examMessage
                = (TextView) itemView
                .findViewById(R.id.examMessage);
        view = itemView;
    }
}
