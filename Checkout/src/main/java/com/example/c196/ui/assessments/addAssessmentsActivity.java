package com.example.c196.ui.assessments;

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
import android.widget.Toast;

import com.example.c196.R;
import com.example.c196.dBHelper;
import com.example.c196.ui.terms.termsOptions;

import java.util.ArrayList;
import java.util.Calendar;

public class addAssessmentsActivity extends AppCompatActivity {


    public static final String TAG = "addAssessmentsActivity";
    private DatePickerDialog.OnDateSetListener onStartDateSetListener, onEndDateSetListener;
    dBHelper myDb;
    EditText assessmentTitle, assessmentStartDate, assessmentDate;
    Button cancelAddAssessmentButton, saveAddAssessmentButton;
    String[] types = {"Performance", "Objective"};
    AutoCompleteTextView assessmentType, assessmentCourse;
    ArrayAdapter<String> adapter;
    ArrayList<String> courses = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessments);

        myDb = new dBHelper(this);
        assessmentTitle = (EditText) findViewById(R.id.addAssessmentTitle);

        assessmentType = (AutoCompleteTextView) findViewById(R.id.addAssessmentType);
        adapter = new ArrayAdapter<String>(this, R.layout.list_type, types);
        assessmentType.setAdapter(adapter);

        assessmentType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(addAssessmentsActivity.this, "item: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        courses = myDb.getAllCourseNames();
        assessmentCourse = findViewById(R.id.addAssessmentCourse);
        adapter = new ArrayAdapter<>(this, R.layout.list_type, courses);
        assessmentCourse.setAdapter(adapter);

        assessmentCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });

        assessmentStartDate = (EditText) findViewById(R.id.addAssessmentStartDate);
        assessmentDate = (EditText) findViewById(R.id.addAssessmentDate);
        saveAddAssessmentButton = findViewById(R.id.saveAddAssessmentButton);
        addAsssessmentData();

        //Cancel button connection and functionality

        assessmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog endDialog = new DatePickerDialog(addAssessmentsActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onEndDateSetListener, year, month, day);
                endDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                endDialog.show();
            }

        });
        onEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "/" + month + "/" + day;
                assessmentDate.setText(date);

            }
        };
        //start date here

        assessmentStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog startDialog = new DatePickerDialog(addAssessmentsActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onStartDateSetListener, year, month, day);
                startDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                startDialog.show();
            }

        });
        onStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "/" + month + "/" + day;
                assessmentStartDate.setText(date);

            }
        };


        cancelAddAssessmentButton = (Button) findViewById(R.id.cancelAddAssessmentButton);
        cancelAddAssessmentButton.setOnClickListener(new View.OnClickListener() {
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

    public void addAsssessmentData() {
        saveAddAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (assessmentTitle.getText().toString().isEmpty() || assessmentType.getText().toString().isEmpty() || assessmentCourse.getText().toString().isEmpty() || assessmentStartDate.getText().toString().isEmpty() || assessmentDate.getText().toString().isEmpty()) {
                    Toast.makeText(addAssessmentsActivity.this, "One or more fields are empty", Toast.LENGTH_LONG).show();
                } else if (myDb.limitAssessmentReached(assessmentCourse.getText().toString())) {
                    String b = myDb.insertAssessment(assessmentTitle.getText().toString(), assessmentType.getText().toString(), assessmentCourse.getText().toString(), assessmentStartDate.getText().toString(), assessmentDate.getText().toString());
                    Toast.makeText(addAssessmentsActivity.this, b, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(addAssessmentsActivity.this, "Max Assessments per course limit reached", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    public void cancelAddTermClick(View view) {
    }

    public void saveAddCoursesClick(View view) {
    }
}