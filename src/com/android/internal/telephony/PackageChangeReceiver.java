package com.android.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.UserHandle;

/* loaded from: classes4.dex */
public abstract class PackageChangeReceiver extends BroadcastReceiver {
    private static HandlerThread sHandlerThread;
    static final IntentFilter sPackageIntentFilter;
    Context mRegisteredContext;

    public void onHandleForceStop(String[] strArr, boolean z) {
    }

    public void onPackageAdded(String str) {
    }

    public void onPackageAppeared() {
    }

    public void onPackageDisappeared() {
    }

    public void onPackageModified(String str) {
    }

    public void onPackageRemoved(String str) {
    }

    public void onPackageUpdateFinished(String str) {
    }

    static {
        IntentFilter intentFilter = new IntentFilter();
        sPackageIntentFilter = intentFilter;
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        intentFilter.addAction(Intent.ACTION_QUERY_PACKAGE_RESTART);
        intentFilter.addAction(Intent.ACTION_PACKAGE_RESTARTED);
        intentFilter.addDataScheme("package");
    }

    public void register(Context context, Looper looper, UserHandle userHandle) {
        if (this.mRegisteredContext != null) {
            throw new IllegalStateException("Already registered");
        }
        if (looper == null) {
            looper = getStaticLooper();
        }
        Handler handler = new Handler(looper);
        if (userHandle != null) {
            context = context.createContextAsUser(userHandle, 0);
        }
        this.mRegisteredContext = context;
        context.registerReceiver(this, sPackageIntentFilter, null, handler);
    }

    public void unregister() {
        Context context = this.mRegisteredContext;
        if (context == null) {
            throw new IllegalStateException("Not registered");
        }
        context.unregisterReceiver(this);
        this.mRegisteredContext = null;
    }

    private static synchronized Looper getStaticLooper() {
        if (sHandlerThread == null) {
            HandlerThread handlerThread = new HandlerThread("PackageChangeReceiver");
            sHandlerThread = handlerThread;
            handlerThread.start();
        }
        return sHandlerThread.getLooper();
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
            String packageName = getPackageName(intent);
            if (packageName != null) {
                if (intent.getBooleanExtra(Intent.EXTRA_REPLACING, false)) {
                    onPackageUpdateFinished(packageName);
                    onPackageModified(packageName);
                } else {
                    onPackageAdded(packageName);
                }
                onPackageAppeared();
                return;
            }
            return;
        }
        if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
            String packageName2 = getPackageName(intent);
            if (packageName2 != null) {
                if (!intent.getBooleanExtra(Intent.EXTRA_REPLACING, false)) {
                    onPackageRemoved(packageName2);
                }
                onPackageDisappeared();
                return;
            }
            return;
        }
        if (Intent.ACTION_PACKAGE_CHANGED.equals(action)) {
            String packageName3 = getPackageName(intent);
            if (packageName3 != null) {
                onPackageModified(packageName3);
                return;
            }
            return;
        }
        if (Intent.ACTION_QUERY_PACKAGE_RESTART.equals(action)) {
            onHandleForceStop(intent.getStringArrayExtra(Intent.EXTRA_PACKAGES), false);
        } else if (Intent.ACTION_PACKAGE_RESTARTED.equals(action)) {
            onHandleForceStop(new String[]{getPackageName(intent)}, true);
        }
    }

    String getPackageName(Intent intent) {
        Uri data = intent.getData();
        if (data != null) {
            return data.getSchemeSpecificPart();
        }
        return null;
    }
}
