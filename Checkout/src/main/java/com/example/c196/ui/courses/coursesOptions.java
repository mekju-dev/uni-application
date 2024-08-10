package com.example.c196.ui.courses;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.R;
import com.example.c196.dBHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class coursesOptions extends AppCompatActivity {

    public static final String TAG = "courseOptions";
    private DatePickerDialog.OnDateSetListener onStartDateSetListener;
    private DatePickerDialog.OnDateSetListener onEndDateSetListener;
    Button saveEditCourseButton, deleteEditCourseButton, cancelEditCourseButton;
    String id, name, oldName, status, instructor, phone, email, term, start, end, note;
    dBHelper db = new dBHelper(this);
    String[] statuses = {"Completed", "Dropped", "In Progress", "Plan to Take"};
    ArrayList<String> terms = new ArrayList<String>();
    AutoCompleteTextView statusAC, termAC;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_options);

        this.id = getIntent().getStringExtra("ID");
        this.name = getIntent().getStringExtra("Title");
        this.oldName = name;
        this.status = getIntent().getStringExtra("Status");
        this.instructor = getIntent().getStringExtra("Instructor");
        this.phone = getIntent().getStringExtra("Phone");
        this.email = getIntent().getStringExtra("Email");
        this.term = getIntent().getStringExtra("Term");
        this.start = getIntent().getStringExtra("Start");
        this.end = getIntent().getStringExtra("End");
        this.note = getIntent().getStringExtra("Note");

        TextView courseTitleTextView = findViewById(R.id.editCourseTitle);
        TextView courseStatusTextView = findViewById(R.id.editCourseStatus);
        TextView courseInstructorTextView = findViewById(R.id.editCourseInstructor);
        TextView coursePhoneTextView = findViewById(R.id.editCoursePhone);
        TextView courseEmailTextView = findViewById(R.id.editCourseEmail);
        TextView courseTermTextView = findViewById(R.id.editCourseTerm);
        TextView courseStartTextView = findViewById(R.id.editCourseStartDate);
        TextView courseEndTextView = findViewById(R.id.editCourseEndDate);
        TextView courseNoteTextView = findViewById(R.id.editCourseNote);

        courseTitleTextView.setText(name);
        courseStatusTextView.setText(status);
        courseInstructorTextView.setText(instructor);
        coursePhoneTextView.setText(phone);
        courseEmailTextView.setText(email);
        courseTermTextView.setText(term);
        courseStartTextView.setText(start);
        courseEndTextView.setText(end);
        courseNoteTextView.setText(note);

        statusAC = (AutoCompleteTextView) findViewById(R.id.editCourseStatus);
        adapter = new ArrayAdapter<String>(this, R.layout.list_type, statuses);
        statusAC.setAdapter(adapter);

        statusAC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(coursesOptions.this, "item: " + item, Toast.LENGTH_SHORT).show();
            }
        });
        dBHelper myDb = new dBHelper(this);
        terms = myDb.getAllTermNames();
        termAC = (AutoCompleteTextView) findViewById(R.id.editCourseTerm);
        adapter = new ArrayAdapter<String>(this, R.layout.list_type, terms);
        termAC.setAdapter(adapter);

        termAC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String termName = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(coursesOptions.this, "item: " + termName, Toast.LENGTH_SHORT).show();
            }
        });

        //Buttons
        courseStartTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog startDialog = new DatePickerDialog(coursesOptions.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onStartDateSetListener, year, month, day);
                startDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                startDialog.show();
            }

        });
        onStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "/" + month + "/" + day;
                courseStartTextView.setText(date);

            }
        };

        courseEndTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog startDialog = new DatePickerDialog(coursesOptions.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onEndDateSetListener, year, month, day);
                startDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                startDialog.show();
            }

        });
        onEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "/" + month + "/" + day;
                courseEndTextView.setText(date);

            }
        };

        saveEditCourseButton = findViewById(R.id.saveEditTermButton);
        deleteEditCourseButton = findViewById(R.id.deleteEditTermButton);
        cancelEditCourseButton = findViewById(R.id.cancelEditTermButton);

        saveEditCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = courseTitleTextView.getText().toString();
                String status = courseStatusTextView.getText().toString();
                String instructor = courseInstructorTextView.getText().toString();
                String phone = coursePhoneTextView.getText().toString();
                String email = courseEmailTextView.getText().toString();
                String term = courseTermTextView.getText().toString();
                String start = courseStartTextView.getText().toString();
                String end = courseEndTextView.getText().toString();
                String note = courseNoteTextView.getText().toString();

                confirmSaveDialog(name, oldName, status, instructor, phone, email, term, start, end, note);
            }
        });

        deleteEditCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDeleteDialog();
            }
        });

        cancelEditCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRestart();
                finish();
            }
        });

    }

    public void confirmDeleteDialog() {
        TextView assessmentTitleTextView = findViewById(R.id.editCourseTitle);
        //String ID = getIntent().getStringExtra("ID");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete course: " + name);
        builder.setMessage("Are you sure you want to delete this course and all associated assessments?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean d = db.deleteAssociatedAsssessments(name);
                boolean b = db.deleteCourse(id);

                if (b == true && d == true) {
                    Toast.makeText(coursesOptions.this, "Deleted Successfully!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(coursesOptions.this, "Failed to Delete! Check courses and associated assessments", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    public void confirmSaveDialog(String name, String oldName, String status, String instructor, String phone, String email, String term, String start, String end, String note) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update course ID: " + id.toString());
        builder.setMessage("Altering the name of this course will delete all associated assessments. Are you sure you want to update this course?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (name.isEmpty() || term.isEmpty() || start.isEmpty() || end.isEmpty()) {
                    Toast.makeText(coursesOptions.this, "Missing Parts: Check Course name, Term, Start, and End Date. Cannot be empty", Toast.LENGTH_LONG).show();
                } else {
                    db.deleteAssociatedAsssessments(oldName);
                    String x = db.updateCourse(id, name, status, instructor, phone, email, term, start, end, note);
                    Toast.makeText(coursesOptions.this, x, Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

}