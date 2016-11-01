package com.unigainfo.android.meview.adapter.viewholder;

import android.view.View;
import android.widget.LinearLayout;

import com.unigainfo.android.meview.R;
import com.unigainfo.android.meview.adapter.ItemViewHolder;
import com.unigainfo.android.meview.adapter.ViewHolderFactory;
import com.unigainfo.android.meview.adapter.model.DividerItem;

/**
 * Created by Semicolon07 on 10/17/2016 AD.
 */

public class DividerViewHolderFactory extends ViewHolderFactory<DividerItem,DividerViewHolderFactory.ViewHolder> {

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_divider;
    }

    @Override
    public void bindData(ViewHolder viewHolder, DividerItem item, int position) {
    }

    static class ViewHolder extends ItemViewHolder {

        private final LinearLayout dividerLine;

        public ViewHolder(View itemView) {
            super(itemView);
            dividerLine = (LinearLayout) findViewById(R.id.dividerLine);
        }
    }
}
