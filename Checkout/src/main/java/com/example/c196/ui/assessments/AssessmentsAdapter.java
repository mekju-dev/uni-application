package com.example.c196.ui.assessments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.R;

import java.util.List;

public class AssessmentsAdapter extends RecyclerView.Adapter<AssessmentsViewHolder> {
    private final AssessmentRecyclerViewInterface assessmentRecyclerViewInterface;
    Context context;
    List<Assessment> assessments;

    public AssessmentsAdapter(Context applicationContext, List<Assessment> assessments, AssessmentRecyclerViewInterface assessmentRecyclerViewInterface) {
        this.context = applicationContext;
        this.assessments = assessments;
        this.assessmentRecyclerViewInterface = assessmentRecyclerViewInterface;
    }

    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public AssessmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AssessmentsViewHolder(LayoutInflater.from(context).inflate(R.layout.assessment_view, parent, false), assessmentRecyclerViewInterface);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull AssessmentsViewHolder holder, int position) {
        holder.assessmentsTitleView.setText(assessments.get(position).getTitle());
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        if (assessments == null) return 0;
        return assessments.size();
    }
}
