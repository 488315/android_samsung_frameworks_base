package com.android.server.desktopmode;

import android.content.ComponentName;
import android.os.IBinder;
import android.os.RemoteException;
import com.android.server.desktopmode.UiManager;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.IDesktopModeUiService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class UiManager$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UiManager.AnonymousClass1 f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ UiManager$1$$ExternalSyntheticLambda0(UiManager.AnonymousClass1 anonymousClass1, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                UiManager.AnonymousClass1 anonymousClass1 = this.f$0;
                ComponentName componentName = (ComponentName) this.f$1;
                anonymousClass1.getClass();
                Log.w("[DMS]UiManager", "Null binding of '" + componentName + "', try reconnecting");
                UiManager.this.retryConnectionWithBackoff();
                break;
            case 1:
                UiManager.AnonymousClass1 anonymousClass12 = this.f$0;
                ComponentName componentName2 = (ComponentName) this.f$1;
                anonymousClass12.getClass();
                Log.w("[DMS]UiManager", "Binding died of '" + componentName2 + "', try reconnecting");
                UiManager.this.retryConnectionWithBackoff();
                break;
            default:
                UiManager.AnonymousClass1 anonymousClass13 = this.f$0;
                IBinder iBinder = (IBinder) this.f$1;
                UiManager uiManager = UiManager.this;
                if (uiManager.mService == null) {
                    uiManager.mConnectionBackoffAttempts = 0;
                    uiManager.mHandler.removeCallbacks(uiManager.mDeferredConnectionCallback);
                    UiManager uiManager2 = UiManager.this;
                    uiManager2.mHandler.removeCallbacks(uiManager2.mBindServiceRunnable);
                    try {
                        iBinder.linkToDeath(UiManager.this.mDeathRecipient, 0);
                        UiManager.this.mService = IDesktopModeUiService.Stub.asInterface(iBinder);
                        if (DesktopModeFeature.DEBUG) {
                            Log.d("[DMS]UiManager", "onServiceConnected(), mService=" + UiManager.this.mService);
                        }
                        UiManager.this.mPendingUiCommands.flushCommands();
                        break;
                    } catch (RemoteException e) {
                        Log.e("[DMS]UiManager", "Lost connection to the service", e);
                        UiManager.this.unbindService();
                        UiManager.this.retryConnectionWithBackoff();
                    }
                }
                break;
        }
    }
}
