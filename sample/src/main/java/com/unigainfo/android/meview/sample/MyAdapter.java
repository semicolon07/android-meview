package com.unigainfo.android.meview.sample;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import com.unigainfo.android.meview.adapter.ItemViewHolder;
import com.unigainfo.android.meview.adapter.MeFlexAdapter;

import java.util.List;

/**
 * Created by Semicolon07 on 10/10/2016 AD.
 */

public class MyAdapter extends MeFlexAdapter {
    private int lastPosition = -1;

    public MyAdapter(Context context) {
        super(context);
    }

    public MyAdapter(Context context, List items) {
        super(context, items);
    }


    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        setAnimation(holder.itemView,position);
    }

    private void setAnimation(View itemView, int position) {
        if(position > lastPosition){
            Animation anim = getUpFromButtonAnimation();
            itemView.startAnimation(anim);
            lastPosition = position;
        }
    }

    private Animation getUpFromButtonAnimation(){
        Animation anim = AnimationUtils.loadAnimation(getContext(),
                R.anim.up_from_bottom);
        return anim;
    }

    private Animation getFadeAnimation(){
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        return anim;
    }

    private Animation getScaleAnimation() {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(350);
        return anim;
    }

    @Override
    public void onViewDetachedFromWindow(ItemViewHolder holder) {
        holder.clearAnimation();
    }
}
