package com.unigainfo.android.meview.sample.viewholderfactory;

import android.view.View;
import android.widget.TextView;

import com.unigainfo.android.meview.adapter.ItemViewHolder;
import com.unigainfo.android.meview.adapter.ViewHolderFactory;
import com.unigainfo.android.meview.sample.R;
import com.unigainfo.android.meview.sample.model.HeaderViewItemModel;

/**
 * Created by Semicolon07 on 10/8/2016 AD.
 */

public class HeaderViewHolderFactory extends ViewHolderFactory<HeaderViewItemModel,HeaderViewHolderFactory.ViewHolder> {

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.list_item_header;
    }

    @Override
    public void bindData(ViewHolder viewHolder, HeaderViewItemModel item, int position) {
        viewHolder.headerTextView.setText(item.getText());
    }

    class ViewHolder extends ItemViewHolder{
        TextView headerTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            headerTextView = (TextView) findViewById(R.id.header_textView);
        }
    }
}
