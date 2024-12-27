package com.android.server.location.injector;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Binder;

import com.android.internal.util.Preconditions;
import com.android.server.location.nsflp.NSPermissionHelper;

import java.util.concurrent.CopyOnWriteArrayList;

public final class SystemAppForegroundHelper {
    public ActivityManager mActivityManager;
    public final Context mContext;
    public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();
    public NSPermissionHelper mNSPermissionHelper;

    public SystemAppForegroundHelper(Context context) {
        this.mContext = context;
    }

    public final boolean isAppForeground(int i) {
        Preconditions.checkState(this.mActivityManager != null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mActivityManager.getUidImportance(i) <= 125;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
