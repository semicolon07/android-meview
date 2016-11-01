package com.unigainfo.android.meview.adapter.viewholder;

import android.view.View;
import android.view.ViewGroup;

import com.unigainfo.android.meview.R;
import com.unigainfo.android.meview.adapter.ItemViewHolder;
import com.unigainfo.android.meview.adapter.ViewHolderFactory;
import com.unigainfo.android.meview.adapter.model.SpaceItem;

/**
 * Created by Semicolon07 on 10/19/2016 AD.
 */

public class SpaceViewHolderFactory extends ViewHolderFactory<SpaceItem,SpaceViewHolderFactory.ViewHolder>{

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_free_space;
    }

    @Override
    public void bindData(ViewHolder viewHolder, SpaceItem item, int position) {
        ViewGroup.LayoutParams params = viewHolder.spaceView.getLayoutParams();
        float heightDp = params.height;
        if(item.getOffsetDimenResId() > 0){
            try{
                heightDp = context.getResources().getDimension(item.getOffsetDimenResId());
            }catch (Exception e){
                heightDp = params.height;
            }
        }
        params.height = (int) heightDp;
    }

    static class ViewHolder extends ItemViewHolder {
        private View spaceView;
        public ViewHolder(View itemView) {
            super(itemView);
            spaceView = findViewById(R.id.spacerView);
        }
    }
}
