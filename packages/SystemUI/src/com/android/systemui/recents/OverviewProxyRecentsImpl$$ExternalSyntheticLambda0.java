package com.android.systemui.recents;

import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import com.android.systemui.shared.recents.ILauncherProxy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class OverviewProxyRecentsImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OverviewProxyRecentsImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ OverviewProxyRecentsImpl$$ExternalSyntheticLambda0(OverviewProxyRecentsImpl overviewProxyRecentsImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = overviewProxyRecentsImpl;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                OverviewProxyRecentsImpl overviewProxyRecentsImpl = this.f$0;
                KeyEvent keyEvent = (KeyEvent) this.f$1;
                overviewProxyRecentsImpl.getClass();
                Log.e("OverviewProxyRecentsImpl", "sendThreeFingerGestureKeyEvent : " + keyEvent.getKeyCode());
                int keyCode = keyEvent.getKeyCode();
                LauncherProxyService launcherProxyService = overviewProxyRecentsImpl.mLauncherProxyService;
                switch (keyCode) {
                    case 1085:
                        launcherProxyService.notifyThreeFingerGestureEvent(keyEvent);
                        overviewProxyRecentsImpl.mLeftOrRightEventSent = true;
                        if (overviewProxyRecentsImpl.mThreeFingerKeyReleased) {
                            try {
                                ILauncherProxy iLauncherProxy = launcherProxyService.mLauncherProxy;
                                if (iLauncherProxy != null) {
                                    ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onQuickScrubStart();
                                }
                            } catch (RemoteException e) {
                                Log.e("LauncherProxyService", "Failed to notify back action", e);
                            }
                            overviewProxyRecentsImpl.mThreeFingerKeyReleased = false;
                            break;
                        }
                        break;
                    case 1086:
                        launcherProxyService.notifyThreeFingerGestureEvent(keyEvent);
                        overviewProxyRecentsImpl.mLeftOrRightEventSent = true;
                        if (overviewProxyRecentsImpl.mThreeFingerKeyReleased) {
                            try {
                                ILauncherProxy iLauncherProxy2 = launcherProxyService.mLauncherProxy;
                                if (iLauncherProxy2 != null) {
                                    ((ILauncherProxy.Stub.Proxy) iLauncherProxy2).onQuickScrubEnd();
                                }
                            } catch (RemoteException e2) {
                                Log.e("LauncherProxyService", "Failed to notify back action", e2);
                            }
                            overviewProxyRecentsImpl.mThreeFingerKeyReleased = false;
                            break;
                        }
                        break;
                    case 1087:
                        overviewProxyRecentsImpl.mLeftOrRightEventSent = false;
                        if (overviewProxyRecentsImpl.mThreeFingerKeyReleased) {
                            launcherProxyService.getClass();
                            try {
                                ILauncherProxy iLauncherProxy3 = launcherProxyService.mLauncherProxy;
                                if (iLauncherProxy3 != null) {
                                    ((ILauncherProxy.Stub.Proxy) iLauncherProxy3).onOverviewToggle();
                                }
                            } catch (RemoteException e3) {
                                Log.e("LauncherProxyService", "Failed to notify back action", e3);
                            }
                            overviewProxyRecentsImpl.mThreeFingerKeyReleased = false;
                            break;
                        }
                        break;
                    case 1088:
                        overviewProxyRecentsImpl.mLeftOrRightEventSent = false;
                        if (overviewProxyRecentsImpl.mThreeFingerKeyReleased) {
                            launcherProxyService.getClass();
                            Log.d("LauncherProxyService", "notifyThreeFingerGestureBottom");
                            overviewProxyRecentsImpl.mThreeFingerKeyReleased = false;
                            break;
                        }
                        break;
                    case 1089:
                        if (overviewProxyRecentsImpl.mLeftOrRightEventSent) {
                            launcherProxyService.notifyThreeFingerGestureEvent(keyEvent);
                        }
                        overviewProxyRecentsImpl.mLeftOrRightEventSent = false;
                        overviewProxyRecentsImpl.mThreeFingerKeyReleased = true;
                        break;
                }
            default:
                this.f$0.mHandler.post((OverviewProxyRecentsImpl$$ExternalSyntheticLambda1) this.f$1);
                break;
        }
    }
}
