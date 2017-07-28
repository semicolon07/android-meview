package com.unigainfo.android.meview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.unigainfo.android.meview.adapter.viewholder.DividerViewHolderFactory;
import com.unigainfo.android.meview.adapter.viewholder.SpaceViewHolderFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Semicolon07 on 10/8/2016 AD.
 */

public class MeFlexAdapter<T> extends RecyclerView.Adapter<ItemViewHolder> {
    private static final int INITIAL_VIEW_TYPE = -800;
    private static final int NOT_FOUND_VIEW_TYPE = -2015;
    private static final String TAG = "MeFlexAdapter";
    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<Object> dynamicItems;
    private int countViewHolderFactory;
    private SparseArray<ViewHolderFactory> viewHolderFactories;
    private Map<String, Integer> classOfNormalItemViewType;

    public MeFlexAdapter(Context context) {
        this.context = context;
        this.dynamicItems = new ArrayList<>();
        this.viewHolderFactories = new SparseArray<>();
        this.classOfNormalItemViewType = new HashMap<>();
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.countViewHolderFactory = INITIAL_VIEW_TYPE;
        //Register Default View Holder Factory
        this.registerViewHolderFactory(new DividerViewHolderFactory());
        this.registerViewHolderFactory(new SpaceViewHolderFactory());
    }

    public MeFlexAdapter(Context context, List<T> items) {
        this(context);
        this.dynamicItems.addAll(items);
    }

    @Override
    public int getItemCount() {
        return isItemsEmpty() ? 0 : dynamicItems.size();
    }

    @Override
    public final ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolderFactory factory = getViewHolderFactory(viewType);

        return factory.createViewHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Object item = dynamicItems.get(position);
        ViewHolderFactory factory;
        if (item instanceof ObjectHolder) {
            ObjectHolder specificItem = (ObjectHolder) item;
            item = specificItem.getObject();
            factory = getViewHolderFactory(specificItem);
        } else {
            factory = getViewHolderFactory(item);
        }

        factory.bindData(holder,item,position);
    }

    @Override
    public final int getItemViewType(int position) {
        Object item = this.dynamicItems.get(position);
        checkRegisterViewHolder(item);

        return getItemViewType(item);
    }

    public final void setItems(Collection<T> items) {
        this.dynamicItems.clear();
        this.dynamicItems.addAll(items);
        notifyDataSetChanged();
    }

    public final void setItems(List<T> items) {
        this.dynamicItems.clear();
        this.dynamicItems.addAll(items);
        notifyDataSetChanged();
    }

    public final void clear() {
        this.dynamicItems.clear();
        notifyDataSetChanged();
    }

    /*Add Section*/
    public final void add(T item) {
        checkRegisterViewHolder(item);

        this.dynamicItems.add(item);
        notifyItemInserted(getItemCount());
    }

    public final void addAll(List<T> items) {
        int lastPosition = this.dynamicItems.size() - 1;
        this.dynamicItems.addAll(items);
        notifyItemRangeInserted(lastPosition, items.size());
    }

    public final void add(T item, int viewType) {
        checkRegisterViewHolder(item);

        ObjectHolder<T> objectHolder = new ObjectHolder(item, viewType);
        dynamicItems.add(objectHolder);
        notifyItemInserted(getItemCount());
    }

    public final void insert(T item, int position) {
        checkRegisterViewHolder(item);
        try{
            dynamicItems.add(position, item);
        }catch (IndexOutOfBoundsException e){
            dynamicItems.add(item);
        }

        notifyDataSetChanged();
    }

    /*Remove Section*/
    public final void remove(int position) {
        dynamicItems.remove(position);
        notifyItemRemoved(position);
    }

    private boolean isItemsEmpty() {
        if (dynamicItems == null || dynamicItems.size() == 0)
            return true;
        return false;
    }

    public T getItem(int position) {
        if (isItemsEmpty())
            return null;

        if (dynamicItems.get(position) instanceof ObjectHolder)
            return ((ObjectHolder<T>) dynamicItems.get(position)).getObject();

        return (T) dynamicItems.get(position);
    }

    /*View Holder Factory Section*/
    public final void registerViewHolderFactory(ViewHolderFactory factory) {
        if (!isRegisteredHolderFactory(factory) && (factory != null)) {
            countViewHolderFactory += 1;
            putViewHolderFactory(factory, countViewHolderFactory);
        }
    }

    public final void registerViewHolderFactory(ViewHolderFactory factory, int viewType) {
        if (isRegisteredViewHolderFactory(viewType))
            return;

        factory.setContext(context);
        viewHolderFactories.put(viewType, factory);
    }

    private ViewHolderFactory getViewHolderFactory(int viewType){
        ViewHolderFactory factory = viewHolderFactories.get(viewType);
        if (factory == null) {
            throw new NullPointerException("Not found the view holder type = "+viewType);
        }

        return factory;
    }

    private ViewHolderFactory getViewHolderFactory(Object item){
        checkRegisterViewHolder(item);
        int viewType = getItemViewType(item);
        ViewHolderFactory factory = getViewHolderFactory(viewType);

        return factory;
    }


    private final void putViewHolderFactory(ViewHolderFactory factory, int viewType) {
        factory.setContext(context);
        viewHolderFactories.put(viewType, factory);
        classOfNormalItemViewType.put(factory.getClassOfItem().getSimpleName(), viewType);
    }

    private int getItemViewType(Object item) {
        if (item instanceof ObjectHolder) {
            return ((ObjectHolder) item).getViewType();
        }

        String clsName = item.getClass().getSimpleName();
        Integer viewType = classOfNormalItemViewType.get(clsName);
        if (viewType == null) {
            return NOT_FOUND_VIEW_TYPE;
        }
        return viewType;
    }

    private boolean isRegisteredViewHolderFactory(int viewType) {
        return viewHolderFactories.get(viewType) != null ? true : false;
    }


    private boolean isRegisteredHolderFactory(ViewHolderFactory factory) {
        int size = viewHolderFactories.size();
        for (int count = 0; count < size; count++) {
            if (viewHolderFactories.valueAt(count).getClass().isAssignableFrom(factory.getClass())) {
                printLog("Already Register ViewHolderFactory = " + factory.getClass().getSimpleName());
                return true;
            }

        }
        return false;
    }


    private void checkRegisterViewHolder(Object item) {
        if (item == null)
            throw new NullPointerException(item.getClass().getSimpleName() + " is null");

        int viewType = getItemViewType(item);
        if (!isRegisteredViewHolderFactory(viewType)) {
            throw new NullPointerException("Must register view holder factory of '" + item.getClass().getSimpleName() + "' before");
        }
    }

    private void printLog(String message) {
        Log.d(TAG, message);
    }

    protected Context getContext() {
        return context;
    }

    @Override
    public void onViewDetachedFromWindow(ItemViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }
}
