package com.unigainfo.android.meview.onechoice;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.unigainfo.android.meview.R;

import static com.unigainfo.android.meview.onechoice.ChoiceItemState.ACTIVE;

/**
 * Created by Semicolon07 on 10/2/2016 AD.
 */

public class ChoiceItemIcon extends ImageView {
    private static final String TAG = "ChoiceItemIcon";
    public static final int ITEM_STATE_INACTIVE = 0;
    public static final int ITEM_STATE_ACTIVE = 1;
    public static final int ITEM_STATE_DISABLED = 2;
    private int mItemState;


    public ChoiceItemIcon(Context context) {
        super(context);
        init();
    }

    public ChoiceItemIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs, 0, 0);
    }

    public ChoiceItemIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public ChoiceItemIcon(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        setSaveEnabled(true);

        // Set default selected background
        int[] defaultItemBgAttrs = new int[] { android.R.attr.selectableItemBackgroundBorderless};
        TypedArray ta = getContext().obtainStyledAttributes(defaultItemBgAttrs);
        try{
            Drawable drawableFromTheme = ta.getDrawable(0);
            this.setBackground(drawableFromTheme);
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }finally {
            ta.recycle();
        }

        setClickable(true);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MeViewChoiceItem,
                defStyleAttr, defStyleRes);

        try {
            mItemState = a.getInt(R.styleable.MeViewChoiceItem_onc_itemState,ITEM_STATE_DISABLED);

        } finally {
            a.recycle();
        }

    }

    public ChoiceItemState getItemState(){
        switch (mItemState){
            case ITEM_STATE_INACTIVE:
                return ChoiceItemState.INACTIVE;
            case ITEM_STATE_ACTIVE:
                return ACTIVE;
            case ITEM_STATE_DISABLED:
                return ChoiceItemState.DISABLED;
            default:
                break;
        }
        return ChoiceItemState.DISABLED;
    }
    public void setItemState(ChoiceItemState state){
        mItemState = state.getStateCode();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
