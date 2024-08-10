package com.example.c196.ui.terms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.R;

import java.util.List;

public class TermsAdapter extends RecyclerView.Adapter<TermsViewHolder> {
    private final TermRecyclerViewInterface termRecyclerViewInterface;

    Context context;
    List<Term> terms;

    public TermsAdapter(Context context, List<Term> terms, TermRecyclerViewInterface termRecyclerViewInterface) {
        this.context = context;
        this.terms = terms;
        this.termRecyclerViewInterface = termRecyclerViewInterface;
    }

    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public TermsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TermsViewHolder(LayoutInflater.from(context).inflate(R.layout.term_view, parent, false), termRecyclerViewInterface);


    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull TermsViewHolder holder, int position) {
        //holder.idView.setText(terms.get(position).getId());
        holder.nameView.setText(terms.get(position).getName());
        // holder.startDateView.setText(terms.get(position).getStartDate());
        //holder.endDateView.setText(terms.get(position).getEndDate());


    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        if (terms == null) return 0;
        return terms.size();
    }
}
