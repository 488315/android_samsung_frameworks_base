package com.android.systemui.ambient.touch;

import android.graphics.Region;
import android.os.RemoteException;
import android.util.Log;
import android.view.ISystemGestureExclusionListener;
import com.android.systemui.ambient.touch.TouchMonitor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class TouchMonitor$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TouchMonitor f$0;

    public /* synthetic */ TouchMonitor$$ExternalSyntheticLambda0(TouchMonitor touchMonitor, int i) {
        this.$r8$classId = i;
        this.f$0 = touchMonitor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        final TouchMonitor touchMonitor = this.f$0;
        switch (i) {
            case 0:
                touchMonitor.getClass();
                try {
                    TouchMonitor.AnonymousClass2 anonymousClass2 = touchMonitor.mGestureExclusionListener;
                    if (anonymousClass2 != null) {
                        touchMonitor.mWindowManagerService.unregisterSystemGestureExclusionListener(anonymousClass2, touchMonitor.mDisplayId);
                        touchMonitor.mGestureExclusionListener = null;
                        break;
                    }
                } catch (RemoteException e) {
                    Log.e(touchMonitor.TAG, "unregisterSystemGestureExclusionListener: failed", e);
                    return;
                }
                break;
            case 1:
                touchMonitor.mActiveTouchSessions.forEach(new TouchMonitor$$ExternalSyntheticLambda3(0));
                break;
            default:
                touchMonitor.getClass();
                try {
                    ISystemGestureExclusionListener iSystemGestureExclusionListener = new ISystemGestureExclusionListener.Stub() { // from class: com.android.systemui.ambient.touch.TouchMonitor.2
                        public final void onSystemGestureExclusionChanged(int i2, Region region, Region region2) {
                            TouchMonitor.this.mExclusionRect = region.getBounds();
                        }
                    };
                    touchMonitor.mGestureExclusionListener = iSystemGestureExclusionListener;
                    touchMonitor.mWindowManagerService.registerSystemGestureExclusionListener(iSystemGestureExclusionListener, touchMonitor.mDisplayId);
                    break;
                } catch (RemoteException e2) {
                    Log.e(touchMonitor.TAG, "Failed to register gesture exclusion listener", e2);
                }
        }
    }
}
