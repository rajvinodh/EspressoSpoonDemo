package com.example.reusable_activities;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exam.ClickListiner;
import com.example.exam.ExamData;
import com.example.exam.ImageGalleryAdapter2;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageGalleryAdapter2 adapter;
    RecyclerView recyclerView;
    ClickListiner listiner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_exam);
//        Toolbar toolbar
//                = (Toolbar)findViewById(R.id.toolbar);
//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);

        List<ExamData> list = new ArrayList<>();
        list = getData();

        recyclerView
                = (RecyclerView)findViewById(
                R.id.recyclerView);
        listiner = new ClickListiner() {
            @Override
            public void click(int index){
                Toast.makeText(getApplicationContext(), String.format("clicked item index is %d" + index) ,Toast.LENGTH_LONG).show();
            }
        };
        adapter
                = new ImageGalleryAdapter2(
                list, getApplication(),listiner);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    // Sample data for RecyclerView
    private List<ExamData> getData()
    {
        List<ExamData> list = new ArrayList<>();
        list.add(new ExamData("First Exam",
                "May 23, 2015",
                "Best Of Luck"));
        list.add(new ExamData("Second Exam",
                "June 09, 2015",
                "b of l"));
        list.add(new ExamData("Third Exam",
                "June 11, 2015",
                "b of l"));
        list.add(new ExamData("4th Exam",
                "June 13, 2015",
                "b of l"));
        list.add(new ExamData("5th Exam",
                "June 14, 2015",
                "b of l"));
        list.add(new ExamData("6th Exam",
                "June 17, 2015",
                "b of l"));
        list.add(new ExamData("7th Exam",
                "June 25, 2015",
                "b of l"));
        list.add(new ExamData("8th Exam",
                "June 27, 2015",
                "b of l"));
        list.add(new ExamData("9th Exam",
                "June 29, 2015",
                "b of l"));
        list.add(new ExamData("My Test Exam",
                "June 30, 2017",
                "This is testing exam .."));

        return list;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}

