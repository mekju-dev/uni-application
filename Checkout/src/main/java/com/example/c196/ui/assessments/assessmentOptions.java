package com.example.c196.ui.assessments;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.c196.ui.terms.addTermsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class assessmentOptions extends AppCompatActivity {

    public static final String TAG = "assessmentOptions";
    private DatePickerDialog.OnDateSetListener onStartDateSetListener, onEndDateSetListener;
    Button saveEditAssessmentButton, deleteEditAssessmentButton, cancelEditAssessmentButton;
    String ID, title, type, course, startDate, date;
    dBHelper db = new dBHelper(this);
    String[] types = {"Performance", "Objective"};
    AutoCompleteTextView assessmentType, assessmentCourse;
    ArrayAdapter<String> adapter;
    ArrayList<String> courses = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_options);

        this.ID = getIntent().getStringExtra("ID");
        this.title = getIntent().getStringExtra("Title");
        this.type = getIntent().getStringExtra("Type");
        this.course = getIntent().getStringExtra("Course");
        this.startDate = getIntent().getStringExtra("StartDate");
        this.date = getIntent().getStringExtra("Date");

        TextView assessmentTitleTextView = findViewById(R.id.editAssessmentTitle);
        TextView assessmentTypeTextView = findViewById(R.id.editAssessmentType);
        TextView assessmentCourseTextView = findViewById(R.id.editAssessmentCourse);
        TextView assessmentStartDateTextView = findViewById(R.id.editAssessmentStartDate);
        TextView assessmentDateTextView = findViewById(R.id.editAssessmentDate);

        assessmentTitleTextView.setText(title);
        assessmentTypeTextView.setText(type);
        assessmentCourseTextView.setText(course);
        assessmentStartDateTextView.setText(startDate);
        assessmentDateTextView.setText(date);

        assessmentType = (AutoCompleteTextView) findViewById(R.id.editAssessmentType);
        adapter = new ArrayAdapter<String>(this, R.layout.list_type, types);
        assessmentType.setAdapter(adapter);

        assessmentType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(assessmentOptions.this, "item: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        dBHelper myDb = new dBHelper(this);
        courses = myDb.getAllCourseNames();
        assessmentCourse = findViewById(R.id.editAssessmentCourse);
        adapter = new ArrayAdapter<>(this, R.layout.list_type, courses);
        assessmentCourse.setAdapter(adapter);

        assessmentCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });

        //Buttons

        assessmentDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog endDialog = new DatePickerDialog(assessmentOptions.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onEndDateSetListener, year, month, day);
                endDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                endDialog.show();
            }

        });
        onEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "/" + month + "/" + day;
                assessmentDateTextView.setText(date);

            }
        };

        assessmentStartDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog startDialog = new DatePickerDialog(assessmentOptions.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onStartDateSetListener, year, month, day);
                startDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                startDialog.show();
            }

        });
        onStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "/" + month + "/" + day;
                assessmentStartDateTextView.setText(date);

            }
        };

        saveEditAssessmentButton = findViewById(R.id.saveEditAssessmentButton);
        deleteEditAssessmentButton = findViewById(R.id.deleteEditAssessmentButton);
        cancelEditAssessmentButton = findViewById(R.id.cancelEditAssessmentButton);

        saveEditAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = assessmentTitleTextView.getText().toString();
                String type = assessmentTypeTextView.getText().toString();
                String course = assessmentCourseTextView.getText().toString();
                String startDate = assessmentStartDateTextView.getText().toString();
                String date = assessmentDateTextView.getText().toString();
                confirmSaveDialog(title, type, course, startDate, date);
            }
        });

        deleteEditAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDeleteDialog();
            }
        });

        cancelEditAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRestart();
                finish();
            }
        });

    }

    public void confirmDeleteDialog() {
        TextView assessmentTitleTextView = findViewById(R.id.editAssessmentTitle);
        String ID = getIntent().getStringExtra("ID");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete assessment: " + title);
        builder.setMessage("Are you sure you want to delete this assessment");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean b = db.deleteAssessment(ID);
                if (b = true) {
                    Toast.makeText(assessmentOptions.this, "Deleted Successfully!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(assessmentOptions.this, "Failed to Delete!", Toast.LENGTH_LONG).show();
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

    public void confirmSaveDialog(String title, String type, String course, String startDate, String date) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update assessment ID: " + ID.toString());
        builder.setMessage("Are you sure you want to update assessment?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (title.isEmpty() || type.isEmpty() || course.isEmpty() || startDate.isEmpty() || date.isEmpty()) {
                    Toast.makeText(assessmentOptions.this, "One or more fields are empty", Toast.LENGTH_LONG).show();
                } else if (db.limitAssessmentReached(course)) {
                    db.updateAssessment(ID, title, type, course, startDate, date);
                    Toast.makeText(assessmentOptions.this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(assessmentOptions.this, "Maximum assessments per course limit reached", Toast.LENGTH_SHORT).show();
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