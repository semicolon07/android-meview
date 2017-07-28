package com.unigainfo.android.meview.sample.viewholderfactory;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Semicolon07 on 3/24/2017 AD.
 */

public class MyLinearLayoutManager extends LinearLayoutManager {
    private static final String TAG = "MyLinearLayoutManager";
    public MyLinearLayoutManager(Context context) {
        super(context);
    }

    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void collectAdjacentPrefetchPositions(int dx, int dy, RecyclerView.State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        super.collectAdjacentPrefetchPositions(dx, dy, state, layoutPrefetchRegistry);
        Log.d(TAG,"dx = "+dx+", dy = "+dy+", state = "+state.toString());
    }

    @Override
    public void collectInitialPrefetchPositions(int adapterItemCount, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        super.collectInitialPrefetchPositions(adapterItemCount, layoutPrefetchRegistry);
        Log.d(TAG,"adapterItemCount = "+adapterItemCount+", layoutPrefetchRegistry = "+layoutPrefetchRegistry);
    }
}
