package com.unigainfo.android.meview.adapter.viewholder;

import android.content.Context;

/**
 * Created by Semicolon07 on 10/19/2016 AD.
 */

class Utils {
    public static float dpFromPx(Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(Context context,final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
