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

/* loaded from: classes5.dex */
public abstract class PackageChangeReceiver extends BroadcastReceiver {
    private static HandlerThread sHandlerThread;
    static final IntentFilter sPackageIntentFilter = new IntentFilter();
    Context mRegisteredContext;

    static {
        sPackageIntentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        sPackageIntentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        sPackageIntentFilter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        sPackageIntentFilter.addAction(Intent.ACTION_QUERY_PACKAGE_RESTART);
        sPackageIntentFilter.addAction(Intent.ACTION_PACKAGE_RESTARTED);
        sPackageIntentFilter.addDataScheme("package");
    }

    public void register(Context context, Looper thread, UserHandle user) {
        if (this.mRegisteredContext != null) {
            throw new IllegalStateException("Already registered");
        }
        Handler handler = new Handler(thread == null ? getStaticLooper() : thread);
        this.mRegisteredContext = user == null ? context : context.createContextAsUser(user, 0);
        this.mRegisteredContext.registerReceiver(this, sPackageIntentFilter, null, handler);
    }

    public void unregister() {
        if (this.mRegisteredContext == null) {
            throw new IllegalStateException("Not registered");
        }
        this.mRegisteredContext.unregisterReceiver(this);
        this.mRegisteredContext = null;
    }

    private static synchronized Looper getStaticLooper() {
        Looper looper;
        synchronized (PackageChangeReceiver.class) {
            if (sHandlerThread == null) {
                sHandlerThread = new HandlerThread(PackageChangeReceiver.class.getSimpleName());
                sHandlerThread.start();
            }
            looper = sHandlerThread.getLooper();
        }
        return looper;
    }

    public void onPackageAdded(String packageName) {
    }

    public void onPackageRemoved(String packageName) {
    }

    public void onPackageUpdateFinished(String packageName) {
    }

    public void onPackageModified(String packageName) {
    }

    public void onHandleForceStop(String[] packages, boolean doit) {
    }

    public void onPackageDisappeared() {
    }

    public void onPackageAppeared() {
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
            String pkg = getPackageName(intent);
            if (pkg != null) {
                if (intent.getBooleanExtra(Intent.EXTRA_REPLACING, false)) {
                    onPackageUpdateFinished(pkg);
                    onPackageModified(pkg);
                } else {
                    onPackageAdded(pkg);
                }
                onPackageAppeared();
                return;
            }
            return;
        }
        if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
            String pkg2 = getPackageName(intent);
            if (pkg2 != null) {
                if (!intent.getBooleanExtra(Intent.EXTRA_REPLACING, false)) {
                    onPackageRemoved(pkg2);
                }
                onPackageDisappeared();
                return;
            }
            return;
        }
        if (Intent.ACTION_PACKAGE_CHANGED.equals(action)) {
            String pkg3 = getPackageName(intent);
            if (pkg3 != null) {
                onPackageModified(pkg3);
                return;
            }
            return;
        }
        if (Intent.ACTION_QUERY_PACKAGE_RESTART.equals(action)) {
            String[] disappearingPackages = intent.getStringArrayExtra(Intent.EXTRA_PACKAGES);
            onHandleForceStop(disappearingPackages, false);
        } else if (Intent.ACTION_PACKAGE_RESTARTED.equals(action)) {
            String[] disappearingPackages2 = {getPackageName(intent)};
            onHandleForceStop(disappearingPackages2, true);
        }
    }

    String getPackageName(Intent intent) {
        Uri uri = intent.getData();
        if (uri == null) {
            return null;
        }
        String pkg = uri.getSchemeSpecificPart();
        return pkg;
    }
}
