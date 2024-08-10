package com.example.c196.ui.courses;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.R;

public class CoursesViewHolder extends RecyclerView.ViewHolder {


    TextView courseTitleView, courseStatusView, courseInstructorView, cIPhone, cIEmail, termAssociated, startDateView, endDateView;

    public CoursesViewHolder(@NonNull View itemView, CourseRecyclerViewInterface courseRecyclerViewInterface) {
        super(itemView);
        courseTitleView = itemView.findViewById(R.id.courseName);

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (courseRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        courseRecyclerViewInterface.onCourseHold(pos);
                    }
                }
                return true;
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (courseRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        courseRecyclerViewInterface.onCourseClick(pos);
                    }
                }
            }
        });

    }

}
