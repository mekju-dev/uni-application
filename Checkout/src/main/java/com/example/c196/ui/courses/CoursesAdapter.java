package com.example.c196.ui.courses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.R;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesViewHolder> {

    private final CourseRecyclerViewInterface courseRecyclerViewInterface;
    Context context;
    List<Course> courses;

    public CoursesAdapter(Context context, List<Course> courses, CourseRecyclerViewInterface courseRecyclerViewInterface) {
        this.context = context;
        this.courses = courses;
        this.courseRecyclerViewInterface = courseRecyclerViewInterface;
    }

    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CoursesViewHolder(LayoutInflater.from(context).inflate(R.layout.courses_view, parent, false), courseRecyclerViewInterface);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CoursesViewHolder holder, int position) {
        holder.courseTitleView.setText(courses.get(position).getTitle());

        /*
        holder.courseTitleView.setText(courses.get(position).getTitle());
        holder.courseStatusView.setText(courses.get(position).getStatus());
        holder.courseInstructorView.setText(courses.get(position).getInstructor());
        holder.cIPhone.setText(courses.get(position).getcIPhone());
        holder.cIEmail.setText(courses.get(position).getcIEmail());
        holder.termAssociated.setText(courses.get(position).getTermAssociated());
        holder.startDateView.setText(courses.get(position).getStartDate());
        holder.endDateView.setText(courses.get(position).getEndDate());

         */
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        if (courses == null) return 0;
        return courses.size();
    }
}
