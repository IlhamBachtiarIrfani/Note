package com.irfan.ilham.percobaan;

import android.app.Dialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class JobListRecyclerviewAdapter extends RecyclerView.Adapter<JobListRecyclerviewAdapter.JobViewHolder> {

    public List<String> noteList;

    public class JobViewHolder extends RecyclerView.ViewHolder {
        public EditText content;
        public ImageView deleteBtn;

        public JobViewHolder(View view) {
            super(view);
            content = view.findViewById(R.id.JobListAddEditText);
            deleteBtn = view.findViewById(R.id.JobListDelete);
        }
    }

    public JobListRecyclerviewAdapter(List<String> noteList) {
        this.noteList = noteList;
    }

    @Override
    public JobListRecyclerviewAdapter.JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_list_add_item, parent, false);

        return new JobListRecyclerviewAdapter.JobViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final JobListRecyclerviewAdapter.JobViewHolder holder, final int position) {
        holder.content.setText(noteList.get(position));
        if (position == 0) {
            holder.deleteBtn.setVisibility(View.GONE);
        } else if (position == (noteList.size() - 1)) {
            holder.deleteBtn.setVisibility(View.VISIBLE);
        } else {
            holder.deleteBtn.setVisibility(View.GONE);
        }
        holder.content.setText("");
        holder.content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                noteList.set(position, "" + s.toString());
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteList.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}