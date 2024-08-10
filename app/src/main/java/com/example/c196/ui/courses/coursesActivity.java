package com.example.c196.ui.courses;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.example.c196.R;
import com.example.c196.dBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class coursesActivity extends AppCompatActivity implements CourseRecyclerViewInterface {
    FloatingActionButton addCourseButton;
    dBHelper db = new dBHelper(this);

    //ArrayList<Course> courses = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        loadLatestCourses();
    }

    public List<Course> getCoursesList() {
        Cursor result = db.getAllCourses();
        List<Course> courses = new ArrayList<>();

        if (result.getCount() == 0) {
            displayMessage("Uh-oh", "No courses found");
            return null;
        } else {
            //StringBuffer buffer = new StringBuffer();
            while (result.moveToNext()) {
                Course course = new Course(result.getString(0), result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), result.getString(8), result.getString(9));
                courses.add(course);

            }
            return courses;
        }
    }

    public void displayMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void loadLatestCourses() {
        RecyclerView recyclerView = findViewById(R.id.coursesRecyclerView);
        List<Course> courses = getCoursesList();

        // new ArrayList<Term>();
        //terms = getTermsList();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CoursesAdapter(getApplicationContext(), courses, this));

        addCourseButton = findViewById(R.id.addCourseButton);
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            /**
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(coursesActivity.this, addCoursesActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * @param position
     */
    @Override
    public void onCourseClick(int position) {
        Intent intent = new Intent(coursesActivity.this, detailedCourse.class);

        intent.putExtra("ID", getCoursesList().get(position).getId());
        intent.putExtra("Title", getCoursesList().get(position).getTitle());
        intent.putExtra("Status", getCoursesList().get(position).getStatus());
        intent.putExtra("Instructor", getCoursesList().get(position).getInstructor());
        intent.putExtra("Phone", getCoursesList().get(position).getcIPhone());
        intent.putExtra("Email", getCoursesList().get(position).getcIEmail());
        intent.putExtra("Term", getCoursesList().get(position).getTermAssociated());
        intent.putExtra("Start", getCoursesList().get(position).getStartDate());
        intent.putExtra("End", getCoursesList().get(position).getEndDate());
        intent.putExtra("Note", getCoursesList().get(position).getNote());

        startActivity(intent);
    }

    /**
     * @param position
     */
    @Override
    public void onCourseHold(int position) {
        Intent intent = new Intent(coursesActivity.this, coursesOptions.class);

        intent.putExtra("ID", getCoursesList().get(position).getId());
        intent.putExtra("Title", getCoursesList().get(position).getTitle());
        intent.putExtra("Status", getCoursesList().get(position).getStatus());
        intent.putExtra("Instructor", getCoursesList().get(position).getInstructor());
        intent.putExtra("Phone", getCoursesList().get(position).getcIPhone());
        intent.putExtra("Email", getCoursesList().get(position).getcIEmail());
        intent.putExtra("Term", getCoursesList().get(position).getTermAssociated());
        intent.putExtra("Start", getCoursesList().get(position).getStartDate());
        intent.putExtra("End", getCoursesList().get(position).getEndDate());
        intent.putExtra("Note", getCoursesList().get(position).getNote());

        startActivity(intent);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

}