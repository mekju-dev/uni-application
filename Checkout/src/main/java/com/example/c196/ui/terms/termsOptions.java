package com.example.c196.ui.terms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.R;
import com.example.c196.dBHelper;
import com.example.c196.ui.courses.coursesOptions;

import java.util.Calendar;

public class termsOptions extends AppCompatActivity {

    public static final String TAG = "termsOptions";
    private DatePickerDialog.OnDateSetListener onStartDateSetListener;
    private DatePickerDialog.OnDateSetListener onEndDateSetListener;
    Button saveEditTermButton, deleteEditTermButton, cancelEditTermButton;
    String id, term, start, end;
    dBHelper db = new dBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_options);

        this.id = getIntent().getStringExtra("ID");
        this.term = getIntent().getStringExtra("Name");
        this.start = getIntent().getStringExtra("Start");
        this.end = getIntent().getStringExtra("End");

        TextView termTextView = findViewById(R.id.editTermName);
        TextView startTextView = findViewById(R.id.editStartDateText);
        TextView endTextView = findViewById(R.id.editEndDateText);

        termTextView.setText(term);
        startTextView.setText(start);
        endTextView.setText(end);

        //Buttons

        startTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog startDialog = new DatePickerDialog(termsOptions.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onStartDateSetListener, year, month, day);
                startDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                startDialog.show();
            }

        });
        onStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "/" + month + "/" + day;
                startTextView.setText(date);

            }
        };

        endTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog startDialog = new DatePickerDialog(termsOptions.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onEndDateSetListener, year, month, day);
                startDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                startDialog.show();
            }

        });
        onEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "/" + month + "/" + day;
                endTextView.setText(date);

            }
        };

        saveEditTermButton = findViewById(R.id.saveEditTermButton);
        deleteEditTermButton = findViewById(R.id.deleteEditTermButton);
        cancelEditTermButton = findViewById(R.id.cancelEditTermButton);

        saveEditTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String term = termTextView.getText().toString();
                String start = startTextView.getText().toString();
                String end = endTextView.getText().toString();

                confirmSaveDialog(term, start, end);
            }
        });

        deleteEditTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDeleteDialog();
            }
        });

        cancelEditTermButton.setOnClickListener(new View.OnClickListener() {
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
        builder.setTitle("Delete term: " + term);
        builder.setMessage("Are you sure you want to delete this course");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String b = db.deleteTerm(id.toString());
                Toast.makeText(termsOptions.this, b, Toast.LENGTH_LONG).show();

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

    public void confirmSaveDialog(String term, String start, String end) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update course ID: " + id.toString());
        builder.setMessage("Are you sure you want to update this course?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String x = db.updateTerm(id, term, start, end);
                Toast.makeText(termsOptions.this, x, Toast.LENGTH_LONG).show();
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