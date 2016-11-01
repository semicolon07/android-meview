package com.unigainfo.android.meview.onechoice;

/**
 * Created by Semicolon07 on 10/3/2016 AD.
 */

enum  ChoiceItemState {
    ACTIVE(1),INACTIVE(0),DISABLED(2);

    private final int stateCode;

    ChoiceItemState(int stateCode) {

        this.stateCode = stateCode;
    }

    public int getStateCode() {
        return stateCode;
    }
}
