package com.unigainfo.android.meview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;

/**
 * Created by Semicolon07 on 10/8/2016 AD.
 */

public abstract class ViewHolderFactory<T,VH extends ItemViewHolder> {
    private final Class classOfItem;
    private Class holderClass;
    protected Context context;

    public ViewHolderFactory(){
        classOfItem = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        holderClass = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        //holderClass = DividerViewHolderFactory.ViewHolder.class;
    }

    public final void setContext(Context context){
        this.context = context;
    }

    public final Class getClassOfItem(){
        return classOfItem;
    }

    public final VH createViewHolder(LayoutInflater inflater, ViewGroup parent) throws Exception{
        View view = inflater.inflate(getLayoutRes(), parent, false);
        final VH viewHolder = getViewHolder(view);
        if(viewHolder == null) throw new NullPointerException("Can't create the ViewHolder from :"+holderClass.getName());
        initViewHolder(viewHolder);
        return viewHolder;
    }

    // Support Inner Class Only
    protected VH getViewHolder(View view){
        try{
            //Constructor[] cs = holderClass.getConstructors();
            Constructor constructor = holderClass.getConstructor(this.getClass(),View.class);
            VH viewHolder = (VH) constructor.newInstance(this,view);
            return viewHolder;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    protected void initViewHolder(final VH viewHolder){

    }

    public abstract int getLayoutRes();

    public abstract void bindData(VH viewHolder,T item,int position);


}
