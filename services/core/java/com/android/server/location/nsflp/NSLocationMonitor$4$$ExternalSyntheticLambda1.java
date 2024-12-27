package com.android.server.location.nsflp;

import android.location.LocationConstants;
import android.util.Log;

import java.util.HashMap;

public final /* synthetic */ class NSLocationMonitor$4$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NSLocationMonitor.AnonymousClass4 f$0;

    public /* synthetic */ NSLocationMonitor$4$$ExternalSyntheticLambda1(
            NSLocationMonitor.AnonymousClass4 anonymousClass4, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass4;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        NSLocationMonitor.AnonymousClass4 anonymousClass4 = this.f$0;
        switch (i) {
            case 0:
                anonymousClass4.getClass();
                Log.w("NSLocationMonitor", "onListenerDisconnected for NotificationListener");
                ((HashMap) NSLocationMonitor.this.mForegroundNotificationList).clear();
                NSLocationMonitor.this.mNSConnectionHelper.onStateUpdated(
                        LocationConstants.STATE_TYPE.NOTIFICATION_LISTENER_DISCONNECTED, null);
                break;
            default:
                NSLocationMonitor.AnonymousClass4.$r8$lambda$UqTBjMb3xqJQs7mYPjsLobcSlD8(
                        anonymousClass4);
                break;
        }
    }
}
