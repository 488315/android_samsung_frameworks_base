package com.android.systemui.ambient.touch;

import android.os.RemoteException;
import com.android.systemui.ambient.touch.TouchMonitor.AnonymousClass2;
import com.android.systemui.log.core.Logger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class TouchMonitor$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TouchMonitor f$0;

    public /* synthetic */ TouchMonitor$$ExternalSyntheticLambda1(TouchMonitor touchMonitor, int i) {
        this.$r8$classId = i;
        this.f$0 = touchMonitor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        TouchMonitor touchMonitor = this.f$0;
        switch (i) {
            case 0:
                Logger logger = touchMonitor.mLogger;
                try {
                    if (touchMonitor.mGestureExclusionListener != null) {
                        logger.i("Unregistering system gesture exclusion listener");
                        touchMonitor.mWindowManagerService.unregisterSystemGestureExclusionListener(touchMonitor.mGestureExclusionListener, touchMonitor.mDisplayId);
                        touchMonitor.mGestureExclusionListener = null;
                        break;
                    }
                } catch (RemoteException e) {
                    logger.e("unregisterSystemGestureExclusionListener: failed", e);
                    return;
                }
                break;
            case 1:
                touchMonitor.mActiveTouchSessions.forEach(new TouchMonitor$$ExternalSyntheticLambda6(0));
                touchMonitor.mActiveTouchSessions.clear();
                break;
            default:
                Logger logger2 = touchMonitor.mLogger;
                try {
                    touchMonitor.mGestureExclusionListener = touchMonitor.new AnonymousClass2();
                    logger2.i("Registering system gesture exclusion listener");
                    touchMonitor.mWindowManagerService.registerSystemGestureExclusionListener(touchMonitor.mGestureExclusionListener, touchMonitor.mDisplayId);
                    break;
                } catch (RemoteException e2) {
                    logger2.e("Failed to register gesture exclusion listener", e2);
                }
        }
    }
}
