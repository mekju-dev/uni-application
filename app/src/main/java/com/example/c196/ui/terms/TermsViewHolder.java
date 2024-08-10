package com.example.c196.ui.terms;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.R;
import com.example.c196.ui.terms.TermRecyclerViewInterface;

public class TermsViewHolder extends RecyclerView.ViewHolder {

    TextView idView, nameView, startDateView, endDateView;

    public TermsViewHolder(@NonNull View itemView, TermRecyclerViewInterface termRecyclerViewInterface) {
        super(itemView);
        //idView = itemView.findViewById(R.id.courseName);
        nameView = itemView.findViewById(R.id.termName);
        //startDateView = itemView.findViewById(R.id.insName);
        //endDateView = itemView.findViewById(R.id.insPhone);

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (termRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        termRecyclerViewInterface.onTermHold(pos);
                    }
                }
                return true;
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (termRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        termRecyclerViewInterface.onTermClick(pos);
                    }
                }
            }
        });

    }
}
