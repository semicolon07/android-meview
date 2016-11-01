package com.unigainfo.android.meview.sample.viewholderfactory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unigainfo.android.meview.sample.R;
import com.unigainfo.android.meview.sample.model.Student;

import java.util.List;

/**
 * Created by Semicolon07 on 10/10/2016 AD.
 */

public class BasicAdapter extends RecyclerView.Adapter<BasicAdapter.ViewHolder> {

    private final Context context;
    private final Listener listener;
    private final LayoutInflater layoutInflater;
    private  List<Student> items;

    public interface Listener{
        void onStudentItemClick(Student item);
    }

    public BasicAdapter(Context context, List<Student> items,Listener listener){
        this.context = context;
        this.items = items;
        this.listener = listener;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.list_item_student, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null)
                    listener.onStudentItemClick(viewHolder.item);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Student item = items.get(position);
        holder.titleTextView.setText(item.getName());
        holder.detailTextView.setText(item.getEmail());
        holder.item = item;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;
        TextView detailTextView;
        Student item;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title_textView);
            detailTextView = (TextView) itemView.findViewById(R.id.detail_textView);
        }
    }
}
