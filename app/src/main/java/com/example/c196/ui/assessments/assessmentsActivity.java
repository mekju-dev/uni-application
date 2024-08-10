package com.example.c196.ui.assessments;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.example.c196.R;
import com.example.c196.dBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class assessmentsActivity extends AppCompatActivity implements AssessmentRecyclerViewInterface {
    FloatingActionButton addAssessmentButton;
    dBHelper db = new dBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);
        loadLatestAssessments();
    }

    public List<Assessment> getAssessmentsList() {
        Cursor result = db.getAllAssessments();
        List<Assessment> assessments = new ArrayList<>();

        if (result.getCount() == 0) {
            displayMessage("Uh-oh", "No assessments found");
            return null;
        } else {
            //StringBuffer buffer = new StringBuffer();
            while (result.moveToNext()) {
                Assessment assessment = new Assessment(result.getString(0), result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5));
                assessments.add(assessment);

            }
            return assessments;
        }
    }

    public void displayMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void loadLatestAssessments() {
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        List<Assessment> assessments = getAssessmentsList();

        // new ArrayList<Term>();
        //terms = getTermsList();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AssessmentsAdapter(getApplicationContext(), assessments, this));


        addAssessmentButton = findViewById(R.id.addAssessmentButton);
        addAssessmentButton.setOnClickListener(new View.OnClickListener() {
            /**
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(assessmentsActivity.this, addAssessmentsActivity.class);
                startActivity(intent);
            }

        });
    }

    /**
     * @param position
     */

    public void onAssessmentHold(int position) {
        Intent intent = new Intent(assessmentsActivity.this, assessmentOptions.class);

        intent.putExtra("ID", getAssessmentsList().get(position).getId());
        intent.putExtra("Title", getAssessmentsList().get(position).getTitle());
        intent.putExtra("Type", getAssessmentsList().get(position).getType());
        intent.putExtra("Course", getAssessmentsList().get(position).getCourse());
        intent.putExtra("StartDate", getAssessmentsList().get(position).getStartDate());
        intent.putExtra("Date", getAssessmentsList().get(position).getEndDate());


        startActivity(intent);
    }

    /**
     * @param position
     */
    @Override
    public void onAssessmentClick(int position) {
        Intent intent = new Intent(assessmentsActivity.this, detailedAssessment.class);

        intent.putExtra("Title", getAssessmentsList().get(position).getTitle());
        intent.putExtra("Type", getAssessmentsList().get(position).getType());
        intent.putExtra("Course", getAssessmentsList().get(position).getCourse());
        intent.putExtra("StartDate", getAssessmentsList().get(position).getStartDate());
        intent.putExtra("Date", getAssessmentsList().get(position).getEndDate());


        startActivity(intent);
    }

    public void makeNotification() {
        String channelId = "Channel_ID_Notification";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId);
        builder.setSmallIcon(R.drawable.baseline_assessment_24);
        builder.setContentTitle("ASSESSMENT ALERT");
        builder.setContentText("You have assessment in 15min or less!");
        builder.setAutoCancel(true).setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(getApplicationContext(), assessmentsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        //intent.putExtra("Title", getAssessmentsList().get(position).getTitle());
        //intent.putExtra("Type", getAssessmentsList().get(position).getType());
        //intent.putExtra("Course", getAssessmentsList().get(position).getCourse());
        //intent.putExtra("Date", getAssessmentsList().get(position).getEndDate());
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

}