package com.unigainfo.android.meview.base.state;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View.BaseSavedState;

/**
 * Created by pongphop on 1/9/2016.
 */
public class BundleSavedState extends BaseSavedState {

    private Bundle bundle = new Bundle();
    public Parcelable superState;

    public BundleSavedState(Parcel source) {
        super(source);
        this.superState = source.readParcelable(BundleSavedState.class.getClassLoader());
        this.bundle = source.readBundle();
        //bundle = source.readBundle(BundleSavedState.class.getClassLoader());
    }

    public BundleSavedState(Parcelable superState) {
        super(EMPTY_STATE);
        this.superState = superState;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeParcelable(superState, flags);
        out.writeBundle(bundle);
    }

    public Bundle getBundle() {
        return bundle;
    }

    public static final Creator CREATOR = new Creator() {

        @Override
        public Object createFromParcel(Parcel source) {
            return new BundleSavedState(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new BundleSavedState[size];
        }
    };

}
