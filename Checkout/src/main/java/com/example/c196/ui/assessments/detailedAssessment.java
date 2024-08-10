package com.example.c196.ui.assessments;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.c196.MainActivity;
import com.example.c196.R;
import com.example.c196.databinding.ActivityMainBinding;
import com.example.c196.ui.AlarmReceiver;
import com.example.c196.ui.courses.detailedCourse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

public class detailedAssessment extends AppCompatActivity {
    private ActivityMainBinding binding;
    AlertDialog.Builder builder;
    Button button;
    //private Button alertButton = (Button) (R.id.assessmentAlertSet);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        setContentView(R.layout.activity_detailed_assessment);

        button = (Button) findViewById(R.id.assessmentAlertSet);
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          setAlert();
                                      }
                                  }
        );


        String title = getIntent().getStringExtra("Title");
        String type = getIntent().getStringExtra("Type");
        String course = getIntent().getStringExtra("Course");
        String startDate = getIntent().getStringExtra("StartDate");
        String date = getIntent().getStringExtra("Date");


        TextView assessmentTitleTextView = findViewById(R.id.detailedAssessmentTitle);
        TextView assessmentTypeTextView = findViewById(R.id.detailedAssessmentType);
        TextView assessmentCourseTextView = findViewById(R.id.detailedAssessmentCourse);
        TextView assessmentStartTextView = findViewById(R.id.detailedAssessmentStartDate);
        TextView assessmentDateTextView = findViewById(R.id.detailedAssessmentDate);

        assessmentTitleTextView.setText(title);
        assessmentTypeTextView.setText(type);
        assessmentCourseTextView.setText(course);
        assessmentStartTextView.setText(startDate);
        assessmentDateTextView.setText(date);

    }

    public void setAlert() {
        //TextView assessmentTitleTextView = findViewById(R.id.editCourseTitle);
        //String ID = getIntent().getStringExtra("ID");
        String title = getIntent().getStringExtra("Title");
        String startDate = getIntent().getStringExtra("StartDate");
        String date = getIntent().getStringExtra("Date");


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
                    tempEnd = simpleDateFormat.parse(date);
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
                Long trigger = tempEnd.getTime();
                //Long trigger1 = Date.from(Instant.now().plusSeconds(10)).getTime();

                Intent intent = new Intent(detailedAssessment.this, AlarmReceiver.class);
                intent.putExtra("key", "Assessment: " + title + " Ends today!");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(detailedAssessment.this, ++MainActivity.numNotify, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, trigger, pendingIntent);
                try {
                    Toast.makeText(detailedAssessment.this, trigger.toString() + " + " + tempEnd.getTime() + " + " + simpleDateFormat.parse(date), Toast.LENGTH_LONG).show();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

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
                    tempStart = simpleDateFormat.parse(startDate);
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
                Long trigger = tempStart.getTime();

                Intent intent = new Intent(detailedAssessment.this, AlarmReceiver.class);
                intent.putExtra("key", "Assessment: " + title + " Starts today!");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(detailedAssessment.this, ++MainActivity.numNotify, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, trigger, pendingIntent);
                Toast.makeText(detailedAssessment.this, trigger.toString(), Toast.LENGTH_SHORT).show();

                ;
            }
        });
        builder.create().show();

/*
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.text_sms_icon)
                .setContentTitle("Notification")
                .setContentText("Notification");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());
 */

    }

}
