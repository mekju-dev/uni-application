package com.example.c196.ui.assessments;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.R;

public class AssessmentsViewHolder extends RecyclerView.ViewHolder {
    TextView assessmentsTitleView, assessmentTypeView, assessmentCourseView, assessmentDateView;

    public AssessmentsViewHolder(@NonNull View itemView, AssessmentRecyclerViewInterface assessmentRecyclerViewInterface) {
        super(itemView);
        assessmentsTitleView = itemView.findViewById(R.id.assessmentTitle);
        /*
        assessmentTypeView = itemView.findViewById(R.id.addAssessmentType);
        assessmentCourseView = itemView.findViewById(R.id.addAssessmentCourse);
        assessmentDateView = itemView.findViewById(R.id.addAssessmentDate);

         */


        //GET THIS TO WORK
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (assessmentRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        assessmentRecyclerViewInterface.onAssessmentHold(pos);
                    }
                }
                return true;
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (assessmentRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        assessmentRecyclerViewInterface.onAssessmentClick(pos);
                    }
                }
            }
        });


    }

}
