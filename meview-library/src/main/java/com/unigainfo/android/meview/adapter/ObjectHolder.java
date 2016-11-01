package com.unigainfo.android.meview.adapter;

/**
 * Created by Semicolon07 on 10/8/2016 AD.
 */

public final class ObjectHolder<O> {
    private O object;
    private int viewType;

    public ObjectHolder(O object){
        this.object = object;
    }

    public ObjectHolder(O object,int viewType){
        this.object = object;
        this.viewType = viewType;
    }

    public O getObject() {
        return object;
    }

    public void setObject(O object) {
        this.object = object;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
