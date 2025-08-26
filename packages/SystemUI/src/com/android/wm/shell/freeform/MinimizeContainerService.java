package com.android.wm.shell.freeform;

import android.app.AppGlobals;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Binder;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.RemoteException;
import android.util.Log;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.wm.shell.freeform.FreeformContainerManager;
import com.samsung.android.multiwindow.IFreeformCallback;
import com.samsung.android.multiwindow.MultiWindowManager;

/* loaded from: classes3.dex */
public class MinimizeContainerService extends Service {
    public static int sFlipFont;
    public final MultiWindowManager mMultiWindowManager = new MultiWindowManager();
    public final IBinder mBinder = new Binder();
    public final AnonymousClass1 mFreeformCallback = new IFreeformCallback.Stub() { // from class: com.android.wm.shell.freeform.MinimizeContainerService.1
        public final void onMinimizeAnimationEnd(int i) {
            ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "[MinimizeContainerService] IFreeformCallback_onMinimizeAnimationEnd: taskId=", "FreeformContainer");
            FreeformContainerManager.H h = FreeformContainerManager.getInstance(MinimizeContainerService.this).mH;
            h.sendMessage(h.obtainMessage(15, i, 0));
        }

        public final void onMinimized(ComponentName componentName, int i, int i2, int i3, int i4, boolean z) {
            Log.i("FreeformContainer", "[MinimizeContainerService] IFreeformCallback_onMinimized: taskId=" + i + ", r=" + componentName);
            try {
                String str = AppGlobals.getPackageManager().getActivityInfo(componentName, 128L, i2).packageName;
                FreeformContainerManager freeformContainerManager = FreeformContainerManager.getInstance(MinimizeContainerService.this);
                MinimizeContainerItem minimizeContainerItem = new MinimizeContainerItem(freeformContainerManager.mContext, str, componentName, i, i2, false);
                FreeformContainerManager.H h = freeformContainerManager.mH;
                h.sendMessage(13, minimizeContainerItem);
                if (i3 == -1 && i4 == -1) {
                    return;
                }
                h.sendMessage(h.obtainMessage(37, z ? 1 : 0, 0, new Point(i3, i4)));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public final void onTaskMoveEnded(int i, IRemoteCallback iRemoteCallback) {
            ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "[MinimizeContainerService] IFreeformCallback_onTaskMoveEnded: taskId=", "FreeformContainer");
            FreeformContainerManager.H h = FreeformContainerManager.getInstance(MinimizeContainerService.this).mH;
            h.sendMessage(h.obtainMessage(41, i, 0, iRemoteCallback));
        }

        public final void onTaskMoveStarted(int i, Point point) {
            ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "[MinimizeContainerService] IFreeformCallback_onTaskMoveStarted: taskId=", "FreeformContainer");
            FreeformContainerManager.H h = FreeformContainerManager.getInstance(MinimizeContainerService.this).mH;
            h.sendMessage(h.obtainMessage(40, i, 0, point));
        }

        public final void onUnminimized(int i) {
            ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "[MinimizeContainerService] IFreeformCallback_onUnminimized: taskId=", "FreeformContainer");
            FreeformContainerManager.H h = FreeformContainerManager.getInstance(MinimizeContainerService.this).mH;
            h.sendMessage(h.obtainMessage(14, i, 0));
        }
    };

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        Log.i("FreeformContainer", "[MinimizeContainerService] onBind()");
        boolean booleanExtra = intent.getBooleanExtra("load_all_items", false);
        this.mMultiWindowManager.registerFreeformCallback(this.mFreeformCallback);
        FreeformContainerManager.H h = FreeformContainerManager.getInstance(this).mH;
        h.sendMessage(h.obtainMessage(11, booleanExtra ? 1 : 0, 0));
        return this.mBinder;
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        FreeformContainerManager freeformContainerManager = FreeformContainerManager.getInstance(this);
        int iDiff = freeformContainerManager.mConfiguration.diff(configuration);
        freeformContainerManager.mConfiguration.updateFrom(configuration);
        if (((-2147405308) & iDiff) != 0) {
            freeformContainerManager.rebuildAll("ConfigurationChanged");
        } else {
            freeformContainerManager.mH.sendMessage(36);
            Log.i("FreeformContainer", "[Manager] updateConfigurationChanged: diff=0x" + Integer.toHexString(iDiff) + ", No need to rebuild all");
        }
        int i = configuration.FlipFont;
        if (i <= 0 || sFlipFont == i) {
            return;
        }
        Typeface.setFlipFonts();
        sFlipFont = configuration.FlipFont;
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        Log.i("FreeformContainer", "[MinimizeContainerService] onUnbind()");
        this.mMultiWindowManager.unregisterFreeformCallback(this.mFreeformCallback);
        FreeformContainerManager.getInstance(this).mH.sendMessage(12);
        return super.onUnbind(intent);
    }
}
