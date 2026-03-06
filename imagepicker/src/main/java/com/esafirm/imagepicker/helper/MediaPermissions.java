package com.esafirm.imagepicker.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.util.ArrayList;
import java.util.List;

public class MediaPermissions {
    public static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 102;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 103;


    Activity activity;

    public MediaPermissions() {

    }

    public MediaPermissions(Activity activity) {
        this.activity = activity;
    }


    public boolean checkPermissionForExternalStorage() {
        return checkPermissionForExternalStorage(activity);
    }

    public boolean checkPermissionForExternalStorage(Context context) {
        return checkPermissionsForAll(context, getFilePermissions());
    }

    public boolean checkPermissionForCamera() {
        return checkPermissionForCamera(activity);
    }


    public boolean checkPermissionForCamera(Context context) {
        List<String> permissions = getPhotoPermissions();
        return checkPermissionsForAll(context, permissions);
    }

    public boolean checkPermissionsForAll(Context context, List<String> permissions) {
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(context, permission);
            if (result != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    public void requestPermissionsForAll(Activity activity, int requestCode, List<String> permissions) {
        String[] perArray = permissions.toArray(new String[permissions.size()]);
        ActivityCompat.requestPermissions(activity, perArray, requestCode);
    }

    public boolean shouldShowRequestRationaleForAll(Activity context, List<String> permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                return true;
            }
        }
        return false;
    }

    public boolean shouldShowRequestRationaleForStorage(Activity context) {
        return shouldShowRequestRationaleForAll(context, getFilePermissions());
    }

    public boolean shouldShowRequestRationaleForStorage() {
        return shouldShowRequestRationaleForStorage(activity);
    }


    public boolean isPermissionDisabledByUser(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                permission)
        ) {
            return true;
        }
        return false;
    }


    public void requestPermissionForExternalStorage() {
        if (shouldShowRequestRationaleForAll(activity, getFilePermissions())) {
            Toast.makeText(activity, "External Storage permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            requestPermissionsForAll(activity, EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE, getFilePermissions());
        }
    }

    public void requestPermissionForCamera() {
        List<String> permissions = getPhotoPermissions();
        if (shouldShowRequestRationaleForAll(activity, permissions)) {
            Toast.makeText(activity, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            requestPermissionsForAll(activity, CAMERA_PERMISSION_REQUEST_CODE, permissions);
        }
    }


    public static List<String> getPhotoPermissions() {
        ArrayList<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.CAMERA);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.READ_MEDIA_IMAGES);
            permissions.add(Manifest.permission.READ_MEDIA_AUDIO);
        }
        return permissions;
    }

    public static List<String> getFilePermissions() {
        List<String> permissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {
            permissions.add(Manifest.permission.READ_MEDIA_IMAGES);
            permissions.add(Manifest.permission.READ_MEDIA_AUDIO);
            permissions.add(Manifest.permission.READ_MEDIA_VIDEO);
        }
        return permissions;
    }
}
