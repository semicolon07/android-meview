package com.unigainfo.android.meview.permission;

import java.util.List;

/**
 * Created by Semicolon07 on 8/16/2017 AD.
 */

public interface PermissionCallback {
    void result(List<PermissionEnum> permissions);
}
