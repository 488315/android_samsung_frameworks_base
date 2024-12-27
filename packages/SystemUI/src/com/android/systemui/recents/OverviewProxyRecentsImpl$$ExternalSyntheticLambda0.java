package com.android.systemui.recents;

import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import com.android.systemui.shared.recents.IOverviewProxy;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                OverviewProxyService overviewProxyService = overviewProxyRecentsImpl.mOverviewProxyService;
                switch (keyCode) {
                    case 1085:
                        if (overviewProxyRecentsImpl.mThreeFingerKeyReleased) {
                            overviewProxyService.getClass();
                            try {
                                IOverviewProxy iOverviewProxy = overviewProxyService.mOverviewProxy;
                                if (iOverviewProxy != null) {
                                    ((IOverviewProxy.Stub.Proxy) iOverviewProxy).onQuickScrubStart();
                                }
                            } catch (RemoteException e) {
                                Log.e("OverviewProxyService", "Failed to notify back action", e);
                            }
                            overviewProxyRecentsImpl.mThreeFingerKeyReleased = false;
                            break;
                        }
                        break;
                    case 1086:
                        if (overviewProxyRecentsImpl.mThreeFingerKeyReleased) {
                            overviewProxyService.getClass();
                            try {
                                IOverviewProxy iOverviewProxy2 = overviewProxyService.mOverviewProxy;
                                if (iOverviewProxy2 != null) {
                                    ((IOverviewProxy.Stub.Proxy) iOverviewProxy2).onQuickScrubEnd();
                                }
                            } catch (RemoteException e2) {
                                Log.e("OverviewProxyService", "Failed to notify back action", e2);
                            }
                            overviewProxyRecentsImpl.mThreeFingerKeyReleased = false;
                            break;
                        }
                        break;
                    case 1087:
                        if (overviewProxyRecentsImpl.mThreeFingerKeyReleased) {
                            overviewProxyService.getClass();
                            try {
                                IOverviewProxy iOverviewProxy3 = overviewProxyService.mOverviewProxy;
                                if (iOverviewProxy3 != null) {
                                    ((IOverviewProxy.Stub.Proxy) iOverviewProxy3).onOverviewToggle();
                                }
                            } catch (RemoteException e3) {
                                Log.e("OverviewProxyService", "Failed to notify back action", e3);
                            }
                            overviewProxyRecentsImpl.mThreeFingerKeyReleased = false;
                            break;
                        }
                        break;
                    case 1088:
                        if (overviewProxyRecentsImpl.mThreeFingerKeyReleased) {
                            overviewProxyService.getClass();
                            Log.d("OverviewProxyService", "notifyThreeFingerGestureBottom");
                            overviewProxyRecentsImpl.mThreeFingerKeyReleased = false;
                            break;
                        }
                        break;
                    case 1089:
                        overviewProxyRecentsImpl.mThreeFingerKeyReleased = true;
                        break;
                }
            default:
                this.f$0.mHandler.post((Runnable) this.f$1);
                break;
        }
    }
}
