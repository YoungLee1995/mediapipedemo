package com.example.testdemo.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraFilter;

import com.google.gson.Gson;

import java.util.Iterator;
import java.util.LinkedHashSet;

@SuppressLint("UnsafeOptInUsageError")
public class MyCameraFilter implements CameraFilter {
    private static final String TAG = "zmr";

    @NonNull
    @Override
    public LinkedHashSet<Camera> filter(@NonNull LinkedHashSet<Camera> cameras) {
        Iterator<Camera> cameraIterator = cameras.iterator();
        Camera camera = null;
        while (cameraIterator.hasNext()) {
            camera = cameraIterator.next();
            @SuppressLint("RestrictedApi") String getImplementationType = camera.getCameraInfo().getImplementationType();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add(camera); // 最后一个camera
        return linkedHashSet;
    }
}
