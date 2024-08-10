package com.example.c196.ui.terms;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196.R;
import com.example.c196.dBHelper;
import com.example.c196.ui.courses.addCoursesActivity;

import java.util.Calendar;

public class addTermsActivity extends AppCompatActivity {

    public static final String TAG = "addTermsActivity";
    private DatePickerDialog.OnDateSetListener onStartDateSetListener;
    private DatePickerDialog.OnDateSetListener onEndDateSetListener;
    dBHelper myDb;
    EditText termName;

    EditText termStart, termEnd;
    Button cancelAddTermButton, saveAddTermButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_terms);


        //Data variables from addTermsActivity to be inserted into database
        myDb = new dBHelper(this);
        termName = (EditText) findViewById(R.id.termName);
        termStart = (EditText) findViewById(R.id.startDateText);
        termEnd = (EditText) findViewById(R.id.endDateText);
        saveAddTermButton = findViewById(R.id.saveAddCourseButton);
        addTermsData();

        //Cancel button connection and functionality
        cancelAddTermButton = (Button) findViewById(R.id.cancelAddCourseButton);
        cancelAddTermButton.setOnClickListener(new View.OnClickListener() {
            /**
             * @param view
             */
            @Override
            public void onClick(View view) {
                onRestart();
                finish();
            }

        });

        termStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog startDialog = new DatePickerDialog(addTermsActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onStartDateSetListener, year, month, day);
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

                DatePickerDialog startDialog = new DatePickerDialog(addTermsActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onEndDateSetListener, year, month, day);
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

    }


    public void addTermsData() {
        saveAddTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String b = myDb.insertTerm(termName.getText().toString(), termStart.getText().toString(), termEnd.getText().toString());
                Toast.makeText(addTermsActivity.this, b, Toast.LENGTH_LONG).show();

            }
        });
    }


    public void cancelAddTermClick(View view) {

    }
}
