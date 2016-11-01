package com.unigainfo.android.meview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Semicolon07 on 10/8/2016 AD.
 */

public abstract class ViewHolderFactory<T,VH extends ItemViewHolder> {
    private final Class classOfItem;
    protected Context context;

    public ViewHolderFactory(){
        classOfItem = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public final void setContext(Context context){
        this.context = context;
    }

    public final Class getClassOfItem(){
        return classOfItem;
    }

    public final VH createViewHolder(LayoutInflater inflater, ViewGroup parent){
        View view = inflater.inflate(getLayoutRes(), parent, false);
        final VH viewHolder = getViewHolder(view);
        return viewHolder;
    }

    public abstract VH getViewHolder(View view);

    public abstract int getLayoutRes();

    public abstract void bindData(VH viewHolder,T item,int position);


}
