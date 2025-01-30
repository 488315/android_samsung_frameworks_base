package com.android.wm.shell.freeform;

import android.app.AppGlobals;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Binder;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.RemoteException;
import android.util.Log;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.wm.shell.freeform.FreeformContainerManager;
import com.samsung.android.multiwindow.IFreeformCallback;
import com.samsung.android.multiwindow.MultiWindowManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class MinimizeContainerService extends Service {
    public final MultiWindowManager mMultiWindowManager = new MultiWindowManager();
    public final IBinder mBinder = new Binder();
    public final C39941 mFreeformCallback = new IFreeformCallback.Stub() { // from class: com.android.wm.shell.freeform.MinimizeContainerService.1
        public final void onMinimizeAnimationEnd(int i) {
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("[MinimizeContainerService] IFreeformCallback_onMinimizeAnimationEnd: taskId=", i, "FreeformContainer");
            FreeformContainerManager.HandlerC3983H handlerC3983H = FreeformContainerManager.getInstance(MinimizeContainerService.this).f447mH;
            handlerC3983H.sendMessage(handlerC3983H.obtainMessage(15, i, 0));
        }

        public final void onMinimized(ComponentName componentName, int i, int i2, int i3, int i4, boolean z) {
            Log.i("FreeformContainer", "[MinimizeContainerService] IFreeformCallback_onMinimized: taskId=" + i + ", r=" + componentName);
            try {
                String str = AppGlobals.getPackageManager().getActivityInfo(componentName, 128L, i2).packageName;
                FreeformContainerManager freeformContainerManager = FreeformContainerManager.getInstance(MinimizeContainerService.this);
                MinimizeContainerItem minimizeContainerItem = new MinimizeContainerItem(freeformContainerManager.mContext, str, componentName, i, i2, false);
                FreeformContainerManager.HandlerC3983H handlerC3983H = freeformContainerManager.f447mH;
                handlerC3983H.sendMessage(13, minimizeContainerItem);
                if (i3 == -1 && i4 == -1) {
                    return;
                }
                handlerC3983H.sendMessage(handlerC3983H.obtainMessage(37, z ? 1 : 0, 0, new Point(i3, i4)));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public final void onTaskMoveEnded(int i, IRemoteCallback iRemoteCallback) {
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("[MinimizeContainerService] IFreeformCallback_onTaskMoveEnded: taskId=", i, "FreeformContainer");
            FreeformContainerManager.HandlerC3983H handlerC3983H = FreeformContainerManager.getInstance(MinimizeContainerService.this).f447mH;
            handlerC3983H.sendMessage(handlerC3983H.obtainMessage(41, i, 0, iRemoteCallback));
        }

        public final void onTaskMoveStarted(int i, Point point) {
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("[MinimizeContainerService] IFreeformCallback_onTaskMoveStarted: taskId=", i, "FreeformContainer");
            FreeformContainerManager.HandlerC3983H handlerC3983H = FreeformContainerManager.getInstance(MinimizeContainerService.this).f447mH;
            handlerC3983H.sendMessage(handlerC3983H.obtainMessage(40, i, 0, point));
        }

        public final void onUnminimized(int i) {
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("[MinimizeContainerService] IFreeformCallback_onUnminimized: taskId=", i, "FreeformContainer");
            FreeformContainerManager.HandlerC3983H handlerC3983H = FreeformContainerManager.getInstance(MinimizeContainerService.this).f447mH;
            handlerC3983H.sendMessage(handlerC3983H.obtainMessage(14, i, 0));
        }
    };

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        Log.i("FreeformContainer", "[MinimizeContainerService] onBind()");
        this.mMultiWindowManager.registerFreeformCallback(this.mFreeformCallback);
        FreeformContainerManager.getInstance(this).f447mH.sendMessage(11);
        return this.mBinder;
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        Log.i("FreeformContainer", "[MinimizeContainerService] onUnbind()");
        this.mMultiWindowManager.unregisterFreeformCallback(this.mFreeformCallback);
        FreeformContainerManager.getInstance(this).f447mH.sendMessage(12);
        return super.onUnbind(intent);
    }
}
