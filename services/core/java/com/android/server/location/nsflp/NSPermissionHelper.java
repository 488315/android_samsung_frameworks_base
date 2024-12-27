package com.android.server.location.nsflp;

import android.app.ActivityManager;
import android.app.IUidObserver;
import android.util.Log;

import com.android.server.location.LocationManagerService;

import java.util.HashMap;

public final class NSPermissionHelper {
    public final NSConnectionHelper mNSConnectionHelper;
    public final UidObserver mUidObserver;

    public final class UidObserver extends IUidObserver.Stub {
        public HashMap mUidState;

        public final void onUidActive(int i) {}

        public final void onUidCachedChanged(int i, boolean z) {}

        public final void onUidGone(int i, boolean z) {
            this.mUidState.remove(Integer.valueOf(i));
        }

        public final void onUidIdle(int i, boolean z) {}

        public final void onUidProcAdjChanged(int i, int i2) {}

        public final void onUidStateChanged(int i, int i2, long j, int i3) {
            UidState uidState = (UidState) this.mUidState.get(Integer.valueOf(i));
            if (uidState != null) {
                uidState.state = i2;
                uidState.capability = i3;
            } else {
                UidState uidState2 = new UidState();
                uidState2.state = i2;
                uidState2.capability = i3;
                this.mUidState.put(Integer.valueOf(i), uidState2);
            }
        }
    }

    public final class UidState {
        public int capability;
        public int state;
    }

    public NSPermissionHelper(
            LocationManagerService.Lifecycle.LifecycleUserInfoHelper lifecycleUserInfoHelper,
            NSConnectionHelper nSConnectionHelper) {
        Log.i("NSPermissionHelper", "constructor");
        this.mNSConnectionHelper = nSConnectionHelper;
        UidObserver uidObserver = new UidObserver();
        uidObserver.mUidState = new HashMap();
        this.mUidObserver = uidObserver;
        try {
            synchronized (lifecycleUserInfoHelper) {
                try {
                    if (lifecycleUserInfoHelper.mActivityManager == null) {
                        lifecycleUserInfoHelper.mActivityManager = ActivityManager.getService();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            lifecycleUserInfoHelper.mActivityManager.registerUidObserver(
                    uidObserver, 3, -1, (String) null);
            Log.i("SystemUserInfoHelper", "Success to registerUidObserver");
        } catch (Exception e) {
            Log.w("SystemUserInfoHelper", "Failed to registerUidObserver, " + e.toString());
        }
    }
}
