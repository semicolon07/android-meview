package com.unigainfo.android.meview.sample;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.unigainfo.android.meview.permission.AskAgainCallback;
import com.unigainfo.android.meview.permission.FullCallback;
import com.unigainfo.android.meview.permission.PermissionEnum;
import com.unigainfo.android.meview.permission.PermissionManager;
import com.unigainfo.android.meview.permission.PermissionUtils;
import com.unigainfo.android.meview.permission.SimpleCallback;

import java.util.ArrayList;
import java.util.List;

public class MPermissionActivity extends AppCompatActivity implements FullCallback {

    interface DialogResult{
        void result(boolean yes);
    }

    private void showMessageOKCancel(String message, final DialogResult dialogResult) {
        new AlertDialog.Builder(MPermissionActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(dialogResult!=null) dialogResult.result(true);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(dialogResult!=null) dialogResult.result(false);
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handleResult(this,requestCode,permissions,grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_permission);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle("Activity");
        }

        Button askOnePermission = (Button) findViewById(R.id.ask_one_permission);
        Button askThreePermission = (Button) findViewById(R.id.ask_three_permission);
        Button askOnePermissionSimple = (Button) findViewById(R.id.ask_one_permission_simple);
        Button askThreePermissionSimple = (Button) findViewById(R.id.ask_three_permission_simple);
        Button askOnePermissionSmart = (Button) findViewById(R.id.ask_one_permission_smart);
        Button askThreePermissionSmart = (Button) findViewById(R.id.ask_three_permission_smart);

        Button checkPermission = (Button) findViewById(R.id.check_permission);


        askThreePermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionManager.Builder()
                        .key(800)
                        .permission(PermissionEnum.GET_ACCOUNTS, PermissionEnum.ACCESS_FINE_LOCATION, PermissionEnum.READ_SMS)
                        .callback(new SimpleCallback() {
                            @Override
                            public void result(boolean allPermissionsGranted) {
                                Log.d("Permission", String.valueOf(allPermissionsGranted));
                            }
                        })
                        .onShowRationale(request -> {
                            String message = "You need to grant access to " + request.permissions().get(0).toString();
                            for (int i = 1; i < request.permissions().size(); i++)
                                message = message + ", " + request.permissions().get(i).toString();
                            showMessageOKCancel(message, yes -> {
                                if(yes) request.proceed();
                                else request.cancel();
                            });
                        })
                        .onNeverAskAgain(permissions -> {
                            Toast.makeText(MPermissionActivity.this, "onNeverAskAgain", Toast.LENGTH_SHORT).show();
                        })
                        .onPermissionAllowed(permissions -> {
                            Toast.makeText(MPermissionActivity.this, "onPermissionAllowed", Toast.LENGTH_SHORT).show();
                        })
                        .onPermissionDenied(permissions -> {
                            Toast.makeText(MPermissionActivity.this, "onPermissionDenied", Toast.LENGTH_SHORT).show();
                        })
                        //.callback(MPermissionActivity.this)
                        .ask(MPermissionActivity.this);
            }
        });


    }


    @Override
    public void result(List<PermissionEnum> permissionsGranted, List<PermissionEnum> permissionsDenied, List<PermissionEnum> permissionsDeniedForever, List<PermissionEnum> permissionsAsked) {
        List<String> msg = new ArrayList<>();
        for (PermissionEnum permissionEnum : permissionsGranted) {
            msg.add(permissionEnum.toString() + " [Granted]");
        }
        for (PermissionEnum permissionEnum : permissionsDenied) {
            msg.add(permissionEnum.toString() + " [Denied]");
        }
        for (PermissionEnum permissionEnum : permissionsDeniedForever) {
            msg.add(permissionEnum.toString() + " [DeniedForever]");
        }
        for (PermissionEnum permissionEnum : permissionsAsked) {
            msg.add(permissionEnum.toString() + " [Asked]");
        }
        String[] items = msg.toArray(new String[msg.size()]);
        new AlertDialog.Builder(MPermissionActivity.this)
                .setTitle("Permission result")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                PermissionUtils.openApplicationSettings(MPermissionActivity.this, R.class.getPackage().getName());
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog(final AskAgainCallback.UserResponse response) {
        new AlertDialog.Builder(MPermissionActivity.this)
                .setTitle("Permission needed")
                .setMessage("This app realy need to use this permission, you wont to authorize it?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        response.result(true);
                    }
                })
                .setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        response.result(false);
                    }
                })
                .setCancelable(false)
                .show();
    }

    private Spanned fromHtml(String value) {
        if (value == null) {
            value = "";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(value);
        }
    }
}
