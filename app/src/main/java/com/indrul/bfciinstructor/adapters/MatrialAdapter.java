package com.indrul.bfciinstructor.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indrul.bfciinstructor.MatrialActivity;
import com.indrul.bfciinstructor.MatrialDetailsActivity;
import com.indrul.bfciinstructor.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MatrialAdapter extends RecyclerView.Adapter<MatrialAdapter.ViewHolder> {
    Context context;
    ArrayList<String> matrialName=new ArrayList<>();
    ArrayList<String> matrialimage=new ArrayList<>();
    String semester_name;

    public MatrialAdapter(Context context, ArrayList<String> matrialName, ArrayList<String> matrialimage,String semester_name) {
        this.context = context;
        this.matrialName = matrialName;
        this.matrialimage = matrialimage;
        this.semester_name=semester_name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from (parent.getContext ()).inflate (R.layout.subject_row,parent,false);
        ViewHolder vh=new ViewHolder (view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.text.setText(matrialName.get(position));
        holder.semester.setText(semester_name);
        Picasso.get().load(matrialimage.get(position)).into(holder.image);
        holder.subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, MatrialDetailsActivity.class);
                intent.putExtra("matrial",matrialName.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
         return matrialName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text,semester;
        LinearLayout subject;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subject=itemView.findViewById(R.id.subject);
            image=itemView.findViewById(R.id.subject_image);
            text=itemView.findViewById(R.id.subject_name);
            semester=itemView.findViewById(R.id.semestername);
        }
    }
}
