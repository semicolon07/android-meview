package com.unigainfo.android.meview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;

/**
 * Created by Semicolon07 on 10/8/2016 AD.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public ItemViewHolder(View itemView) {
        super(itemView);
    }

    protected View findViewById(int id){
        return this.itemView.findViewById(id);
    }

    protected Context getContext(){
        return this.itemView.getContext();
    }

    public void clearAnimation(){
        this.itemView.clearAnimation();
    }

    public final void startAnimation(Animation animation){
        this.itemView.startAnimation(animation);
    }

}
