package com.example.c196.ui.terms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.R;
import com.example.c196.dBHelper;
import com.example.c196.ui.courses.Course;
import com.example.c196.ui.courses.CourseRecyclerViewInterface;
import com.example.c196.ui.courses.CoursesAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class detailedTerm extends AppCompatActivity implements CourseRecyclerViewInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_term);
        dBHelper db = new dBHelper(detailedTerm.this);

        String name = getIntent().getStringExtra("Name");
        String startDate = getIntent().getStringExtra("Start");
        String endDate = getIntent().getStringExtra("End");
        String id = getIntent().getStringExtra("ID");

        TextView nameTextView = findViewById(R.id.detailedTermName);
        TextView startDateTextView = findViewById(R.id.detailedTermStart);
        TextView endDateTextView = findViewById(R.id.detailedTermEnd);
        TextView termIdTextView = findViewById(R.id.detailedTermId);

        nameTextView.setText(name);
        startDateTextView.setText(startDate);
        endDateTextView.setText(endDate);
        termIdTextView.setText(id);


        RecyclerView recyclerView = findViewById(R.id.associateRecyclerView);
        ArrayList<Course> courses = db.getAllCoursesFromTerm(name);
        Toast.makeText(this, String.valueOf(courses.size()), Toast.LENGTH_LONG).show();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CoursesAdapter(getApplicationContext(), courses, this));
        //ArrayList<Course> s = db.getAllCoursesFromTerm(name);

    }

    /**
     * @param position
     */
    @Override
    public void onCourseClick(int position) {

    }

    /**
     * @param position
     */
    @Override
    public void onCourseHold(int position) {

    }
}