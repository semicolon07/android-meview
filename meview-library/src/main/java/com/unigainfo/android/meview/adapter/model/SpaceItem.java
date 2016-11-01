package com.unigainfo.android.meview.adapter.model;

/**
 * Created by Semicolon07 on 10/19/2016 AD.
 */

public class SpaceItem {
    private int offsetDimenResId;
    private int bgColorResId;

    public SpaceItem(){

    }
    public SpaceItem(int offsetDimenResId){
        this.offsetDimenResId = offsetDimenResId;
        this.bgColorResId = android.R.attr.listDivider;
    }

    public SpaceItem(int offsetDimenResId, int bgColorResId){
        this.offsetDimenResId = offsetDimenResId;
        this.bgColorResId = bgColorResId;
    }

    public int getBgColorResId() {
        return bgColorResId;
    }

    public void setBgColorResId(int bgColorResId) {
        this.bgColorResId = bgColorResId;
    }

    public int getOffsetDimenResId() {
        return offsetDimenResId;
    }

    public void setOffsetDimenResId(int offsetDimenResId) {
        this.offsetDimenResId = offsetDimenResId;
    }
}
