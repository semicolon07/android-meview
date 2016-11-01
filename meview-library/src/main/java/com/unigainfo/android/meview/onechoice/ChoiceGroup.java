package com.unigainfo.android.meview.onechoice;

import android.content.Context;
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
public class ChoiceGroup extends BaseFlexBoxLayout{

    public interface Listener{
        void onChoiceItemClick(View view);
    }


    private int totalChildCount;
    private static final String TAG = "OneChoiceGroup";
    private static final int INACTIVE_ITEM_DEFAULT_COLOR = 0xFFFFFF;
    private static final int DISABLED_ITEM_DEFAULT_COLOR = 0xFFFFFF;
    private static final int ACTIVE_ITEM_DEFAULT_COLOR = 0xFFFFFF;

    private int activeItemColor;
    private int inactiveColor;
    private int disabledColor;
    private Listener listener;

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

    public void setListener(Listener listener) {
        this.listener = listener;
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
        totalChildCount = getChildCount();

        for(int index = 0 ; index < totalChildCount; index++){
            View v = getChildAt(index);
            if(v instanceof ChoiceItemText){
                ChoiceItemText textItem = (ChoiceItemText) v;
                setChoiceItemTextColor(textItem);
                textItem.setOnClickListener(onItemClickListener);
            }
            if(v instanceof ChoiceItemIcon){
                ChoiceItemIcon iconItem = (ChoiceItemIcon) v;
                setChoiceItemIconColor(iconItem);
                iconItem.setOnClickListener(onItemClickListener);
            }
        }
    }

    private void setChoiceItemTextColor(ChoiceItemText view){
        ChoiceItemState itemState = view.getItemState();
        switch (itemState){
            case INACTIVE:
                view.setTextColor(inactiveColor);
                view.setTypeface(null, Typeface.NORMAL);
                break;
            case ACTIVE:
                float oldTextSize = view.getTextSize();
                view.setTextColor(activeItemColor);
                view.setTypeface(null, Typeface.BOLD);
                //log("Old text size = "+oldTextSize);
                view.setTextSize(TypedValue.COMPLEX_UNIT_PX, oldTextSize);
                break;
            case DISABLED:
                view.setTextColor(disabledColor);
                break;
        }
    }

    private void setChoiceItemIconColor(ChoiceItemIcon iconItem){
        ChoiceItemState itemState = iconItem.getItemState();
        switch (itemState){
            case INACTIVE:
                iconItem.setColorFilter(inactiveColor);
                break;
            case ACTIVE:
                iconItem.setColorFilter(activeItemColor);
                break;
            case DISABLED:
                iconItem.setColorFilter(disabledColor);
                break;
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray attr = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MeViewChoiceGroup,
                defStyleAttr, defStyleRes);
        try {
            activeItemColor = attr.getColor(R.styleable.MeViewChoiceGroup_onc_activeColor,ACTIVE_ITEM_DEFAULT_COLOR);
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

    private final View.OnClickListener onItemClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            manageViewState(view);
        }
    };


    private void manageViewState(View view) {
        if(view instanceof ChoiceItemText){
            ChoiceItemText textItem = (ChoiceItemText) view;
            if(textItem.getItemState() == ChoiceItemState.DISABLED) return;

            textItem.setItemState(ChoiceItemState.ACTIVE);
            setChoiceItemTextColor(textItem);
            triggerOnItemClickListener(view);
        }

        if(view instanceof ChoiceItemIcon){
            ChoiceItemIcon iconItem = (ChoiceItemIcon) view;
            if(iconItem.getItemState() == ChoiceItemState.DISABLED) return;

            iconItem.setItemState(ChoiceItemState.ACTIVE);
            setChoiceItemIconColor(iconItem);
            triggerOnItemClickListener(view);
        }

        for(int index = 0 ; index < totalChildCount; index++){
            View v = getChildAt(index);
            if(v == view) continue;
            if(v instanceof ChoiceItemText){
                ChoiceItemText textItem = (ChoiceItemText) v;
                if(textItem.getItemState() != ChoiceItemState.ACTIVE) continue;

                textItem.setItemState(ChoiceItemState.INACTIVE);
                setChoiceItemTextColor(textItem);
            }
            if(v instanceof ChoiceItemIcon){
                ChoiceItemIcon iconItem = (ChoiceItemIcon) v;
                if(iconItem.getItemState() != ChoiceItemState.ACTIVE) return;

                iconItem.setItemState(ChoiceItemState.INACTIVE);
                setChoiceItemIconColor(iconItem);
            }
        }
    }

    private void triggerOnItemClickListener(View view){
        if(listener!=null)
            listener.onChoiceItemClick(view);
    }
}
