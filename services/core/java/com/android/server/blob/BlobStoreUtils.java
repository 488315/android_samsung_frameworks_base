package com.android.server.blob;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.UserHandle;
import android.util.Slog;

import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;

public abstract class BlobStoreUtils {
    public static final Object sLock = new Object();
    public static Handler sRevocableFdHandler;

    public static Resources getPackageResources(Context context, String str, int i) {
        try {
            return context.createContextAsUser(UserHandle.of(i), 0)
                    .getPackageManager()
                    .getResourcesForApplication(str);
        } catch (PackageManager.NameNotFoundException e) {
            Slog.d(
                    "BlobStore",
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                            i, "Unknown package in user ", ": ", str),
                    e);
            return null;
        }
    }

    public static Handler getRevocableFdHandler() {
        synchronized (sLock) {
            try {
                Handler handler = sRevocableFdHandler;
                if (handler != null) {
                    return handler;
                }
                HandlerThread handlerThread = new HandlerThread("BlobFuseLooper");
                handlerThread.start();
                Handler handler2 = new Handler(handlerThread.getLooper());
                sRevocableFdHandler = handler2;
                return handler2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
