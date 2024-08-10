package com.example.c196.ui.courses;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.R;
import com.example.c196.dBHelper;
import com.example.c196.ui.assessments.addAssessmentsActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class addCoursesActivity extends AppCompatActivity {

    public static final String TAG = "addCoursesActivity";
    private DatePickerDialog.OnDateSetListener onStartDateSetListener;
    private DatePickerDialog.OnDateSetListener onEndDateSetListener;
    dBHelper myDb;
    EditText courseTitle, courseStatus, courseInstructor, cIPhone, cIEmail, termName, termStart, termEnd, note;
    Button cancelAddCourseButton, saveAddCourseButton;
    String[] statuses = {"Completed", "Dropped", "In Progress", "Plan to Take"};
    ArrayList<String> terms = new ArrayList<String>();
    AutoCompleteTextView statusAC, termAC;
    ArrayAdapter<String> adapter, adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);

        statusAC = (AutoCompleteTextView) findViewById(R.id.addCourseStatus);
        adapter = new ArrayAdapter<String>(this, R.layout.list_type, statuses);
        statusAC.setAdapter(adapter);

        statusAC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(addCoursesActivity.this, "item: " + item, Toast.LENGTH_SHORT).show();
            }
        });
        myDb = new dBHelper(this);
        terms = myDb.getAllTermNames();
        termAC = (AutoCompleteTextView) findViewById(R.id.addCourseTerm);
        adapter = new ArrayAdapter<String>(this, R.layout.list_type, terms);
        termAC.setAdapter(adapter);

        termAC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String termName = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(addCoursesActivity.this, "item: " + termName, Toast.LENGTH_SHORT).show();
            }
        });


        courseTitle = (EditText) findViewById(R.id.addCourseTitle);

        courseInstructor = (EditText) findViewById(R.id.addCourseInstructor);
        cIPhone = (EditText) findViewById(R.id.addCoursePhone);
        cIEmail = (EditText) findViewById(R.id.addCourseEmail);
        //termName = (EditText) findViewById(R.id.addCourseTerm);
        termStart = (EditText) findViewById(R.id.addCourseStartDate);
        termEnd = (EditText) findViewById(R.id.addCourseEndDate);
        note = (EditText) findViewById(R.id.addNote);
        saveAddCourseButton = findViewById(R.id.saveAddCourseButton);
        addCoursesData();

        termStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog startDialog = new DatePickerDialog(addCoursesActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onStartDateSetListener, year, month, day);
                startDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                startDialog.show();
            }

        });
        onStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "/" + month + "/" + day;
                termStart.setText(date);

            }
        };

        termEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog startDialog = new DatePickerDialog(addCoursesActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onEndDateSetListener, year, month, day);
                startDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                startDialog.show();
            }

        });
        onEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "/" + month + "/" + day;
                termEnd.setText(date);

            }
        };


        //Cancel button connection and functionality
        cancelAddCourseButton = (Button) findViewById(R.id.cancelAddCourseButton);
        cancelAddCourseButton.setOnClickListener(new View.OnClickListener() {
            /**
             * @param view
             */
            @Override
            public void onClick(View view) {
                onRestart();
                finish();
            }

        });
    }

    public void addCoursesData() {
        saveAddCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (courseTitle.getText().toString().isEmpty() || termAC.getText().toString().isEmpty() || termStart.getText().toString().isEmpty() || termEnd.getText().toString().isEmpty()) {
                    Toast.makeText(addCoursesActivity.this, "Missing Parts: Check Course name, Term, Start, and End Date. Cannot be empty", Toast.LENGTH_LONG).show();
                } else {
                    String b = myDb.insertCourse(courseTitle.getText().toString(), statusAC.getText().toString(), courseInstructor.getText().toString(), cIPhone.getText().toString(), cIEmail.getText().toString(), termAC.getText().toString(), termStart.getText().toString(), termEnd.getText().toString(), note.getText().toString());
                    Toast.makeText(addCoursesActivity.this, b, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void cancelAddTermClick(View view) {
    }

    public void saveAddCoursesClick(View view) {
    }
}