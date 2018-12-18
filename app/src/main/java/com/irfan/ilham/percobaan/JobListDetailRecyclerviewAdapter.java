package com.irfan.ilham.percobaan;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class JobListDetailRecyclerviewAdapter extends RecyclerView.Adapter<JobListDetailRecyclerviewAdapter.JobViewHolder> {

    public List<String> noteList;

    public class JobViewHolder extends RecyclerView.ViewHolder {
        public TextView content;

        public JobViewHolder(View view) {
            super(view);
            content = view.findViewById(R.id.JobListDetail);
        }
    }

    public JobListDetailRecyclerviewAdapter(List<String> noteList) {
        this.noteList = noteList;
    }

    @Override
    public JobListDetailRecyclerviewAdapter.JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_list_detail_item, parent, false);

        return new JobListDetailRecyclerviewAdapter.JobViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final JobListDetailRecyclerviewAdapter.JobViewHolder holder, final int position) {
        final String note = noteList.get(position);
        holder.content.setText(note);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}