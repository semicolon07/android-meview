/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.unigainfo.android.meview.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by raphaelbussa on 22/06/16.
 */
public class PermissionManager {
    private static PermissionManager instance;

    private FullCallback fullCallback;
    private SimpleCallback simpleCallback;
    private AskAgainCallback askAgainCallback;
    private SmartCallback smartCallback;
    private RationaleCallback rationaleCallback;


    private List<PermissionEnum> permissions;
    private List<PermissionEnum> permissionsGranted;
    private List<PermissionEnum> permissionsDenied;
    private List<PermissionEnum> permissionsDeniedForever;
    private List<PermissionEnum> permissionToAsk;

    private int key = PermissionConstant.KEY_PERMISSION;
    private boolean askAgain = false;
    private Context context;

    private PermissionCallback onPermissionDeniedCallback;
    private PermissionCallback onNeverAskAgainCallback;
    private PermissionCallback onPermissionAllowedCallback;


    /**
     * @return current instance
     */
    public static PermissionManager Builder() {
        if (instance == null) {
            instance = new PermissionManager();
        }
        return instance;
    }

    private PermissionManager(){
        this.permissionsGranted = new ArrayList<>();
        this.permissionsDenied = new ArrayList<>();
        this.permissionsDeniedForever = new ArrayList<>();
        this.permissionToAsk = new ArrayList<>();
    }


    /**
     * @param activity     target activity
     * @param requestCode  requestCode
     * @param permissions  permissions
     * @param grantResults grantResults
     */
    public static void handleResult(@NonNull android.app.Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        handleResult(activity, null, null, requestCode, permissions, grantResults);
    }

    /**
     * @param v4fragment   target v4 fragment
     * @param requestCode  requestCode
     * @param permissions  permissions
     * @param grantResults grantResults
     */
    public static void handleResult(@NonNull android.support.v4.app.Fragment v4fragment, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        handleResult(null, v4fragment, null, requestCode, permissions, grantResults);
    }

    /**
     * @param fragment     target fragment
     * @param requestCode  requestCode
     * @param permissions  permissions
     * @param grantResults grantResults
     */
    public static void handleResult(@NonNull android.app.Fragment fragment, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        handleResult(null, null, fragment, requestCode, permissions, grantResults);
    }

    private static void handleResult(final android.app.Activity activity, final android.support.v4.app.Fragment v4fragment, final android.app.Fragment fragment, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (instance == null) return;
        if (requestCode == instance.key) {

            instance.permissionsGranted.clear();
            for (int i = 0; i < permissions.length; i++) {
                PermissionEnum permission = PermissionEnum.fromManifestPermission(permissions[i]);
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    instance.permissionsGranted.add(permission);
                } else {
                    boolean permissionsDeniedForever = false;
                    if (activity != null) {
                        permissionsDeniedForever = ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i]);
                    } else if (fragment != null) {
                        permissionsDeniedForever = FragmentCompat.shouldShowRequestPermissionRationale(fragment, permissions[i]);
                    } else if (v4fragment != null) {
                        permissionsDeniedForever = v4fragment.shouldShowRequestPermissionRationale(permissions[i]);
                    }
                    if (!permissionsDeniedForever) {
                        instance.permissionsDeniedForever.add(permission);
                    }
                    instance.permissionsDenied.add(PermissionEnum.fromManifestPermission(permissions[i]));
                    instance.permissionToAsk.add(PermissionEnum.fromManifestPermission(permissions[i]));
                }
            }

            instance.showResult();



            /*if (instance.permissionToAsk.size() != 0 && instance.askAgain) {
                instance.askAgain = false;
                if (instance.askAgainCallback != null && instance.permissionsDeniedForever.size() != instance.permissionsDenied.size()) {
                    instance.askAgainCallback.showRequestPermission(new AskAgainCallback.UserResponse() {
                        @Override
                        public void result(boolean askAgain) {
                            if (askAgain) {
                                instance.ask(activity, v4fragment, fragment);
                            } else {
                                instance.showResult();
                            }
                        }
                    });
                } else {
                    instance.ask(activity, v4fragment, fragment);

                }
            } else {
                instance.showResult();
            }*/
        }
    }


    /**
     * @param permissions an array of permission that you need to ask
     * @return current instance
     */
    public PermissionManager permissions(ArrayList<PermissionEnum> permissions) {
        this.permissions = new ArrayList<>();
        this.permissions.addAll(permissions);
        return this;
    }

    /**
     * @param permission permission you need to ask
     * @return current instance
     */
    public PermissionManager permission(PermissionEnum permission) {
        this.permissions = new ArrayList<>();
        this.permissions.add(permission);
        return this;
    }

    /**
     * @param permissions permission you need to ask
     * @return current instance
     */
    public PermissionManager permission(PermissionEnum... permissions) {
        this.permissions = new ArrayList<>();
        Collections.addAll(this.permissions, permissions);
        return this;
    }

    /**
     * @param askAgain ask again when permission not granted
     * @return current instance
     */
    public PermissionManager askAgain(boolean askAgain) {
        this.askAgain = askAgain;
        return this;
    }

    /**
     * @param fullCallback set fullCallback for the request
     * @return current instance
     */
    public PermissionManager callback(FullCallback fullCallback) {
        this.simpleCallback = null;
        this.smartCallback = null;
        this.fullCallback = fullCallback;
        return this;
    }

    /**
     * @param simpleCallback set simpleCallback for the request
     * @return current instance
     */
    public PermissionManager callback(SimpleCallback simpleCallback) {
        this.fullCallback = null;
        this.smartCallback = null;
        this.simpleCallback = simpleCallback;
        return this;
    }

    /**
     * @param smartCallback set smartCallback for the request
     * @return current instance
     */
    public PermissionManager callback(SmartCallback smartCallback) {
        this.fullCallback = null;
        this.simpleCallback = null;
        this.smartCallback = smartCallback;
        return this;
    }

    /**
     * @param askAgainCallback set askAgainCallback for the request
     * @return current instance
     */
    public PermissionManager askAgainCallback(AskAgainCallback askAgainCallback) {
        this.askAgainCallback = askAgainCallback;
        return this;
    }

    public PermissionManager onShowRationale(RationaleCallback rationaleCallback) {
        this.rationaleCallback = rationaleCallback;
        return this;
    }

    public PermissionManager onPermissionDenied(PermissionCallback callback) {
        this.onPermissionDeniedCallback = callback;
        return this;
    }

    public PermissionManager onNeverAskAgain(PermissionCallback callback) {
        this.onNeverAskAgainCallback = callback;
        return this;
    }
    public PermissionManager onPermissionAllowed(PermissionCallback callback) {
        this.onPermissionAllowedCallback = callback;
        return this;
    }

    /**
     * @param key set a custom request code
     * @return current instance
     */
    public PermissionManager key(int key) {
        this.key = key;
        return this;
    }

    /**
     * @param activity target activity
     *                 just start all permission manager
     */
    public void ask(android.app.Activity activity) {
        ask(activity, null, null);
    }

    /**
     * @param v4fragment target v4 fragment
     *                   just start all permission manager
     */
    public void ask(android.support.v4.app.Fragment v4fragment) {
        ask(null, v4fragment, null);
    }

    /**
     * @param fragment target fragment
     *                 just start all permission manager
     */
    public void ask(android.app.Fragment fragment) {
        ask(null, null, fragment);
    }

    private void ask(final android.app.Activity activity, final android.support.v4.app.Fragment v4fragment, final android.app.Fragment fragment) {
        if(permissions == null)
            throw new NullPointerException("Permission can't be empty");

        setContext(activity,v4fragment,fragment);
        initArray();
        final String[] allPermissions = rawPermissions(permissions);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(PermissionUtils.hasSelfPermissions(context,allPermissions)){
                permissionsGranted.addAll(permissions);
                showResult();
                return;
            }

            permissionsGranted.clear();
            final List<PermissionEnum> rationalePermissions = permissionToShouldRationale(activity,v4fragment,fragment);
            final String[] rationalePermissionsStrings = rawPermissions(rationalePermissions);

            if (rationalePermissionsStrings.length > 0 && rationaleCallback != null) {
                rationaleCallback.showRequestPermission(new PermissionRequest() {
                    @Override
                    public List<PermissionEnum> permissions() {
                        return rationalePermissions;
                    }

                    @Override
                    public void proceed() {
                        requestPermission(activity, v4fragment, fragment, rationalePermissionsStrings);
                    }

                    @Override
                    public void cancel() {
                        permissionsDenied = rationalePermissions;
                        showResult();
                    }
                });
                return;
            }

            requestPermission(activity, v4fragment, fragment, allPermissions);

        } else {
            permissionsGranted.addAll(permissions);
            showResult();
        }
    }

    private void requestPermission(Activity activity, Fragment v4fragment, android.app.Fragment fragment, String[] permissions) {
        if (activity != null) {
            ActivityCompat.requestPermissions(activity, permissions, key);
        } else if (fragment != null) {
            FragmentCompat.requestPermissions(fragment, permissions, key);
        } else if (v4fragment != null) {
            v4fragment.requestPermissions(permissions, key);
        }
    }

    private String[] rawPermissions(List<PermissionEnum> p_permissions){
        ArrayList<String> ps = new ArrayList<>();
        for (PermissionEnum permission : p_permissions) {
            ps.add(permission.toString());
        }
        return ps.toArray(new String[ps.size()]);
    }

    private void setContext(android.app.Activity activity, android.support.v4.app.Fragment v4fragment, android.app.Fragment fragment){
        if (activity != null) {
            context = activity;
        } else if (fragment != null) {
            context = fragment.getActivity();
        } else if (v4fragment != null) {
            context = v4fragment.getActivity();
        }
    }


    /**
     * @return permission that you really need to ask
     */
    @NonNull
    private String[] permissionToAsk(android.app.Activity activity, android.support.v4.app.Fragment v4fragment, android.app.Fragment fragment) {
        ArrayList<String> permissionToAsk = new ArrayList<>();
        for (PermissionEnum permission : permissions) {
            boolean isGranted = false;
            if (activity != null) {
                isGranted = PermissionUtils.isGranted(activity, permission);
            } else if (fragment != null) {
                isGranted = PermissionUtils.isGranted(fragment.getActivity(), permission);
            } else if (v4fragment != null) {
                isGranted = PermissionUtils.isGranted(v4fragment.getActivity(), permission);
            }
            if (!isGranted) {
                permissionToAsk.add(permission.toString());
            } else {
                permissionsGranted.add(permission);
            }
        }
        return permissionToAsk.toArray(new String[permissionToAsk.size()]);
    }

    @NonNull
    private List<PermissionEnum> permissionToShouldRationale(android.app.Activity activity, android.support.v4.app.Fragment v4fragment, android.app.Fragment fragment) {
        ArrayList<PermissionEnum> permissionToShouldRationale = new ArrayList<>();
        for (PermissionEnum permission : permissions) {
            boolean isGranted = false;
            boolean isShowRationale = false;
            if (activity != null) {
                isGranted = PermissionUtils.isGranted(activity, permission);
                isShowRationale = PermissionUtils.shouldShowRequestPermissionRationale(activity,permission.toString());
            } else if (fragment != null) {
                isGranted = PermissionUtils.isGranted(fragment.getActivity(), permission);
                isShowRationale = PermissionUtils.shouldShowRequestPermissionRationale(fragment,permission.toString());
            } else if (v4fragment != null) {
                isGranted = PermissionUtils.isGranted(v4fragment.getActivity(), permission);
                isShowRationale = PermissionUtils.shouldShowRequestPermissionRationale(v4fragment,permission.toString());
            }
            if (!isGranted && isShowRationale) {
                permissionToShouldRationale.add(permission);
            } else {
                permissionsGranted.add(permission);
            }
        }
        return permissionToShouldRationale;
    }


    /**
     * init permissions ArrayList
     */
    private void initArray() {
        this.permissionsGranted.clear();
        this.permissionsDenied.clear();
        this.permissionsDeniedForever.clear();
        this.permissionToAsk.clear();
    }

    /**
     * check if one of three types of callback are not null and pass data
     */
    private void showResult() {
        if (simpleCallback != null)
            simpleCallback.result(permissionToAsk.size() == 0 || permissionsGranted.size() == permissions.size());
        if (fullCallback != null)
            fullCallback.result(permissionsGranted, permissionsDenied, permissionsDeniedForever, permissions);
        if (smartCallback != null)
            smartCallback.result(permissionToAsk.size() == 0 || permissionsGranted.size() == permissions.size(), !permissionsDeniedForever.isEmpty());
        if (onPermissionAllowedCallback != null && permissionsGranted.size() > 0)
            onPermissionAllowedCallback.result(permissionsGranted);
        if (onNeverAskAgainCallback != null && permissionsDeniedForever.size() > 0)
            onNeverAskAgainCallback.result(permissionsDeniedForever);
        if (onPermissionDeniedCallback != null && permissionsDenied.size() > 0)
            onPermissionDeniedCallback.result(permissionsDenied);

        instance = null;
    }

}