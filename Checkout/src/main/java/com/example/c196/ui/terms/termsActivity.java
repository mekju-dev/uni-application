package com.example.c196.ui.terms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.c196.R;
import com.example.c196.dBHelper;
import com.example.c196.ui.assessments.assessmentOptions;
import com.example.c196.ui.assessments.assessmentsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class termsActivity extends AppCompatActivity implements TermRecyclerViewInterface {
    FloatingActionButton addTermButton;
    dBHelper db = new dBHelper(this);
    //Cursor result = db.getAllTerms();
    //List<Term> terms = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        loadLatestTerms();
    }

    public List<Term> getTermsList() {
        Cursor result = db.getAllTerms();
        List<Term> terms = new ArrayList<>();

        if (result.getCount() == 0) {
            displayMessage("Uh-oh", "No terms found");
            return null;
        } else {
            //StringBuffer buffer = new StringBuffer();
            while (result.moveToNext()) {
                Term term = new Term(result.getString(0), result.getString(1), result.getString(2), result.getString(3));
                terms.add(term);

            }
            return terms;
        }
    }

    public void loadLatestTerms() {
        RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
        List<Term> terms = getTermsList();

        // new ArrayList<Term>();
        //terms = getTermsList();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TermsAdapter(getApplicationContext(), terms, this));


        addTermButton = findViewById(R.id.addTermButton);
        addTermButton.setOnClickListener(new View.OnClickListener() {
            /**
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(termsActivity.this, addTermsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void displayMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    /**
     * @param position
     */

    public void onTermHold(int position) {
        Intent intent = new Intent(termsActivity.this, termsOptions.class);

        intent.putExtra("Name", getTermsList().get(position).getName());
        intent.putExtra("Start", getTermsList().get(position).getStartDate());
        intent.putExtra("End", getTermsList().get(position).getEndDate());
        intent.putExtra("ID", getTermsList().get(position).getId());


        startActivity(intent);
    }

    /**
     * @param position
     */
    @Override
    public void onTermClick(int position) {
        Intent intent = new Intent(termsActivity.this, detailedTerm.class);

        intent.putExtra("Name", getTermsList().get(position).getName());
        intent.putExtra("Start", getTermsList().get(position).getStartDate());
        intent.putExtra("End", getTermsList().get(position).getEndDate());
        intent.putExtra("ID", getTermsList().get(position).getId());

        startActivity(intent);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}