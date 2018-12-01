package com.irfan.ilham.percobaan;

import android.app.Dialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class JobRecyclerviewAdapter extends RecyclerView.Adapter<JobRecyclerviewAdapter.JobViewHolder> {

    private List<Note> noteList;

    public class JobViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date, time;
        public View upline, bottomline;
        public CardView card;

        public JobViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.TitleJobItem);
            date = (TextView) view.findViewById(R.id.DateJobItem);
            time = (TextView) view.findViewById(R.id.TimeJobItem);
            upline = view.findViewById(R.id.UpLineJobItem);
            bottomline = view.findViewById(R.id.BottomLineJobItem);
            card = view.findViewById(R.id.CardJobItem);
        }
    }

    public JobRecyclerviewAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_item, parent, false);

        return new JobViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final JobViewHolder holder, int position) {
        final Note note = noteList.get(position);
        holder.title.setText(note.getTitle());
        holder.date.setText(note.getDate());
        holder.time.setText(note.getTime());

        if (position == 0) {
            holder.upline.setVisibility(View.INVISIBLE);
            holder.bottomline.setVisibility(View.VISIBLE);
        } else if(position == (noteList.size() - 1 )) {
            holder.upline.setVisibility(View.VISIBLE);
            holder.bottomline.setVisibility(View.INVISIBLE);
        } else {
            holder.upline.setVisibility(View.VISIBLE);
            holder.bottomline.setVisibility(View.VISIBLE);
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog DetailDialog = new Dialog(holder.card.getContext());
                DetailDialog.setContentView(R.layout.job_detail_dialog);
                TextView Title = DetailDialog.findViewById(R.id.TitleJobDetail);
                TextView Date = DetailDialog.findViewById(R.id.DateJobDetail);
                TextView Time = DetailDialog.findViewById(R.id.TimeJobDetail);
                TextView Note = DetailDialog.findViewById(R.id.NoteJobDetail);

                Title.setText(note.getTitle());
                Date.setText(note.getDate());
                Time.setText(note.getTime());
                Note.setText(note.getNote());

                DetailDialog.show();
                DetailDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                DetailDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}
