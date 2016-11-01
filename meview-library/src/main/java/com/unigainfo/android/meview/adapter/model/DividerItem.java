package com.unigainfo.android.meview.adapter.model;

/**
 * Created by Semicolon07 on 10/17/2016 AD.
 */

public class DividerItem {
    private int offsetDimenResId;

    public DividerItem(){

    }
    public DividerItem(int offsetDimenResId){
        this.offsetDimenResId = offsetDimenResId;
    }

    public int getOffsetDimenResId() {
        return offsetDimenResId;
    }

    public void setOffsetDimenResId(int offsetDimenResId) {
        this.offsetDimenResId = offsetDimenResId;
    }
}

