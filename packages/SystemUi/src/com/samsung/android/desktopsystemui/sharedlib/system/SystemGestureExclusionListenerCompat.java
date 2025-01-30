package com.samsung.android.desktopsystemui.sharedlib.system;

import android.graphics.Region;
import android.os.RemoteException;
import android.util.Log;
import android.view.ISystemGestureExclusionListener;
import android.view.WindowManagerGlobal;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class SystemGestureExclusionListenerCompat {
    private static final String TAG = "[DS]SGEListenerCompat";
    private final int mDisplayId;
    private ISystemGestureExclusionListener mGestureExclusionListener = new ISystemGestureExclusionListener.Stub() { // from class: com.samsung.android.desktopsystemui.sharedlib.system.SystemGestureExclusionListenerCompat.1
        public void onSystemGestureExclusionChanged(int i, Region region, Region region2) {
            if (i == SystemGestureExclusionListenerCompat.this.mDisplayId) {
                if (region2 == null) {
                    region2 = region;
                }
                SystemGestureExclusionListenerCompat.this.onExclusionChanged(region, region2);
            }
        }
    };
    private boolean mRegistered;

    public SystemGestureExclusionListenerCompat(int i) {
        this.mDisplayId = i;
    }

    public abstract void onExclusionChanged(Region region);

    public void onExclusionChanged(Region region, Region region2) {
        onExclusionChanged(region);
    }

    public void register() {
        if (this.mRegistered) {
            return;
        }
        try {
            WindowManagerGlobal.getWindowManagerService().registerSystemGestureExclusionListener(this.mGestureExclusionListener, this.mDisplayId);
            this.mRegistered = true;
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to register window manager callbacks", e);
        }
    }

    public void unregister() {
        if (this.mRegistered) {
            try {
                WindowManagerGlobal.getWindowManagerService().unregisterSystemGestureExclusionListener(this.mGestureExclusionListener, this.mDisplayId);
                this.mRegistered = false;
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to unregister window manager callbacks", e);
            }
        }
    }
}
