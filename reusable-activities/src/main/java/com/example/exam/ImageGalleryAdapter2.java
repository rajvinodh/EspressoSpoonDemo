package com.example.exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.reusable_activities.R;

import java.util.Collections;
import java.util.List;

public class ImageGalleryAdapter2 extends RecyclerView.Adapter<ExamViewHolder> {

    List<ExamData> list
            = Collections.emptyList();

    Context context;
    ClickListiner listiner;

    public ImageGalleryAdapter2(List<ExamData> list,
                                Context context, ClickListiner listiner) {
        this.list = list;
        this.context = context;
        this.listiner = listiner;
    }

    @Override
    public ExamViewHolder
    onCreateViewHolder(ViewGroup parent,
                       int viewType) {

        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);

        // Inflate the layout

        View photoView
                = inflater
                .inflate(R.layout.card_exam,
                        parent, false);

        ExamViewHolder viewHolder
                = new ExamViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void
    onBindViewHolder(final ExamViewHolder viewHolder,
                     final int position) {
        final int index = viewHolder.getAdapterPosition();
        viewHolder.examName
                .setText(list.get(position).name);
        viewHolder.examDate
                .setText(list.get(position).date);
        viewHolder.examMessage
                .setText(list.get(position).message);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listiner.click(index);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
