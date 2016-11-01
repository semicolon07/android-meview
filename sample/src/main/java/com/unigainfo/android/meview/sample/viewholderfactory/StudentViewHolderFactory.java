package com.unigainfo.android.meview.sample.viewholderfactory;

import android.view.View;
import android.widget.TextView;

import com.unigainfo.android.meview.adapter.ItemViewHolder;
import com.unigainfo.android.meview.adapter.ViewHolderFactory;
import com.unigainfo.android.meview.sample.R;
import com.unigainfo.android.meview.sample.model.Student;

/**
 * Created by Semicolon07 on 10/8/2016 AD.
 */

public class StudentViewHolderFactory extends ViewHolderFactory<Student,StudentViewHolderFactory.ViewHolder> {
    private final Listener listener;

    public interface Listener{
        void onStudentItemClick(Student item);
    }
    public StudentViewHolderFactory(Listener listener){
        this.listener = listener;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        final ViewHolder viewHolder = new ViewHolder(view);
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
    public int getLayoutRes() {
        return R.layout.list_item_student;
    }

    @Override
    public void bindData(ViewHolder viewHolder, Student item, int position) {
        viewHolder.titleTextView.setText(item.getName());
        viewHolder.detailTextView.setText(item.getEmail());
        viewHolder.item = item;
    }

    class ViewHolder extends ItemViewHolder{
        TextView titleTextView;
        TextView detailTextView;
        Student item;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) findViewById(R.id.title_textView);
            detailTextView = (TextView) findViewById(R.id.detail_textView);
        }
    }
}
