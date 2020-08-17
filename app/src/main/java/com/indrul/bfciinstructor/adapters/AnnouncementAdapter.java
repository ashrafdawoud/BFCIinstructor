package com.indrul.bfciinstructor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indrul.bfciinstructor.R;

import java.util.ArrayList;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {
    ArrayList<String> notyname = new ArrayList<>();
    ArrayList<String> notytime = new ArrayList<>();
    ArrayList<String> notytext = new ArrayList<>();

    public AnnouncementAdapter(ArrayList<String> notyname, ArrayList<String> notytime, ArrayList<String> notytext) {
        this.notyname = notyname;
        this.notytext = notytext;
        this.notytime = notytime;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.annuonce_row, parent, false);
        AnnouncementAdapter.ViewHolder vh = new AnnouncementAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(notyname.get(position));
        holder.time.setText(notytime.get(position));
        holder.text.setText(notytext.get(position));
    }

    @Override
    public int getItemCount() {
        return notyname.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, time, text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.docname);
            time = itemView.findViewById(R.id.time);
            text = itemView.findViewById(R.id.text);
        }
    }
}
