package com.unigainfo.android.meview.sample.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.unigainfo.android.meview.base.BaseCustomViewGroup;
import com.unigainfo.android.meview.base.state.BundleSavedState;
import com.unigainfo.android.meview.sample.R;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PhotoListItem extends BaseCustomViewGroup {
    TextView nameTextView;
    TextView descriptionTextView;
    ImageView ivImageView;

    public PhotoListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public PhotoListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public PhotoListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public PhotoListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_item_photo, this);
    }


    private void initInstances() {
        // findViewById here\
        nameTextView = (TextView) findViewById(R.id.tvName);
        descriptionTextView = (TextView) findViewById(R.id.tvDescription);
        ivImageView = (ImageView) findViewById(R.id.ivImg);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        savedState.getBundle().putString("key", "1234");

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        String key = bundle.getString("key");
        Toast.makeText(getContext(), "key = " + key, Toast.LENGTH_SHORT).show();
        // Restore State from bundle here
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = width * 2 / 3;
        int newHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    public void setNameText(String text){
        nameTextView.setText(text);
    }

    public void setDescriptionText(String text){
        descriptionTextView.setText(text);
    }

    public void setImageUrl(String url){
        Glide.with(getContext())
                .load(url)
                .placeholder(R.drawable.loading)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivImageView);
    }
}
