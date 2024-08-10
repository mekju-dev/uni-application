package com.example.c196.ui.courses;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.MainActivity;
import com.example.c196.R;
import com.example.c196.dBHelper;
import com.example.c196.ui.AlarmReceiver;
import com.example.c196.ui.assessments.Assessment;
import com.example.c196.ui.assessments.AssessmentRecyclerViewInterface;
import com.example.c196.ui.assessments.AssessmentsAdapter;
import com.example.c196.ui.assessments.detailedAssessment;
import com.example.c196.ui.terms.detailedTerm;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class detailedCourse extends AppCompatActivity implements AssessmentRecyclerViewInterface {
    ImageView sendText;
    AlertDialog alertDialog;
    Date date1 = null;
    Button button;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_course);
        dBHelper db = new dBHelper(detailedCourse.this);
        button = (Button) findViewById(R.id.courseAlertSet);
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          setAlert();
                                      }
                                  }
        );

        String title = getIntent().getStringExtra("Title");
        String status = getIntent().getStringExtra("Status");
        String instructor = getIntent().getStringExtra("Instructor");
        String id = getIntent().getStringExtra("ID");
        String phone = getIntent().getStringExtra("Phone");
        String email = getIntent().getStringExtra("Email");
        String term = getIntent().getStringExtra("Term");
        String start = getIntent().getStringExtra("Start");
        String end = getIntent().getStringExtra("End");
        String note = getIntent().getStringExtra("Note");


        TextView courseTitleTextView = findViewById(R.id.detailedCourseTitle);
        TextView courseIdTextView = findViewById(R.id.detaileCourseId);
        TextView courseStatusTextView = findViewById(R.id.detailedCourseStatus);
        TextView courseInstructorTextView = findViewById(R.id.detailedCourseInstructor);
        TextView coursePhoneTextView = findViewById(R.id.detailedCoursePhone);
        TextView courseEmailTextView = findViewById(R.id.detailedCourseEmail);
        TextView courseTermTextView = findViewById(R.id.detailedCourseAssociatedTerm);
        TextView startDateTextView = findViewById(R.id.detailedCourseStart);
        TextView endDateTextView = findViewById(R.id.detailedCourseEnd);
        TextView noteTextView = findViewById(R.id.detailedCourseNote);

        courseTitleTextView.setText(title);
        courseIdTextView.setText(id);
        courseStatusTextView.setText(status);
        courseInstructorTextView.setText(instructor);
        coursePhoneTextView.setText(phone);
        courseEmailTextView.setText(email);
        courseTermTextView.setText(term);
        startDateTextView.setText(start);
        endDateTextView.setText(end);
        noteTextView.setText(note);

        RecyclerView recyclerView = findViewById(R.id.associateRecyclerView);
        ArrayList<Assessment> assessments = db.getAllAssessmentsFromCourse(title);
        Toast.makeText(this, String.valueOf(assessments.size()), Toast.LENGTH_LONG).show();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AssessmentsAdapter(getApplicationContext(), assessments, this));
        //ArrayList<Course> s = db.getAllCoursesFromTerm(name);

        sendText = (ImageView) findViewById(R.id.sendTextButton);
        sendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", "", null));
                intent.putExtra("sms_body", "Note for course: " + title + "\n\n" + note);
                startActivity(intent);
            }
        });
    }

    public void setAlert() {
        //TextView assessmentTitleTextView = findViewById(R.id.editCourseTitle);
        //String ID = getIntent().getStringExtra("ID");
        String title = getIntent().getStringExtra("Title");
        String start = getIntent().getStringExtra("Start");
        String end = getIntent().getStringExtra("End");
        String myFormat = "yyyy/MM/dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Set alert");
        builder.setMessage("Would you like to set alert for start date or end date?");
        builder.setPositiveButton("End date", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Date tempEnd = null;
                try {
                    tempEnd = simpleDateFormat.parse(end);
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }


                Long trigger = tempEnd.getTime();

                Intent intent = new Intent(detailedCourse.this, AlarmReceiver.class);
                intent.putExtra("key", "Course: " + title + " Ends today!");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(detailedCourse.this, ++MainActivity.numNotify, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, trigger, pendingIntent);
                Toast.makeText(detailedCourse.this, trigger.toString() + "    " + MainActivity.numNotify, Toast.LENGTH_SHORT).show();
            }
        });


        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("Start date", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Date tempStart = null;
                try {
                    tempStart = simpleDateFormat.parse(start);
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }


                Long trigger = tempStart.getTime();

                Intent intent = new Intent(detailedCourse.this, AlarmReceiver.class);
                intent.putExtra("key", "Course: " + title + " Starts today!");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(detailedCourse.this, ++MainActivity.numNotify, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, trigger, pendingIntent);
                Toast.makeText(detailedCourse.this, trigger.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();

    }

    /**
     * @param position
     */
    @Override
    public void onAssessmentClick(int position) {

    }

    /**
     * @param position
     */
    @Override
    public void onAssessmentHold(int position) {

    }
}