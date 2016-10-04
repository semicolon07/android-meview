package com.unigainfo.android.meview.onechoice;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.unigainfo.android.meview.R;
import com.unigainfo.android.meview.base.BaseFlexBoxLayout;
import com.unigainfo.android.meview.base.state.BundleSavedState;

/**
 * Created by nuuneoi on 11/16/2014.
 */
class ChoiceGroup extends BaseFlexBoxLayout {
    private static final String TAG = "OneChoiceGroup";
    private static final int INACTIVE_ITEM_DEFAULT_COLOR = 0xFFFFFF;
    private static final int DISABLED_ITEM_DEFAULT_COLOR = 0xFFFFFF;

    private ColorStateList activeItemColor;
    private int inactiveColor;
    private int disabledColor;

    public ChoiceGroup(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public ChoiceGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public ChoiceGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }


    private void initInflate() {
        //inflate(getContext(), R.layout.meview_onechoice_layout, this);
    }

    private void initInstances() {
        // findViewById here

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        log("onFinishInflate");
        createChildStyle();
    }

    private void createChildStyle() {
        int childCount = getChildCount();
        log("Total child count = "+childCount);
        for(int index = 0 ; index < childCount; index++){
            View v = getChildAt(index);
            if(v instanceof ChoiceItemText){
                ChoiceItemText textItem = (ChoiceItemText) v;
                setChoiceItemTextColor(textItem);
            }
        }
    }

    private void setChoiceItemTextColor(ChoiceItemText view){
        ChoiceItemState itemState = view.getItemState();
        switch (itemState){
            case INACTIVE:
                view.setTextColor(inactiveColor);
                break;
            case ACTIVE:
                float oldTextSize = view.getTextSize();
                view.setTextColor(activeItemColor);
                view.setTypeface(null, Typeface.BOLD);
                //log("Old text size = "+oldTextSize);
                view.setTextSize(TypedValue.COMPLEX_UNIT_PX, oldTextSize+8);
                break;
            case DISABLED:
                view.setTextColor(disabledColor);
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        log("onMeasure");
        log("activeItemColor : "+activeItemColor);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray attr = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MeViewChoiceGroup,
                defStyleAttr, defStyleRes);
        try {
            activeItemColor = attr.getColorStateList(R.styleable.MeViewChoiceGroup_onc_activeColor);
            inactiveColor = attr.getColor(R.styleable.MeViewChoiceGroup_onc_inactiveColor,INACTIVE_ITEM_DEFAULT_COLOR);
            disabledColor = attr.getColor(R.styleable.MeViewChoiceGroup_onc_disabledColor,DISABLED_ITEM_DEFAULT_COLOR);
        } finally {
            attr.recycle();
        }
    }


    private void log(String message){
        Log.i(TAG,message);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }

}
