package com.android.server.desktopmode;

import android.content.ComponentName;
import android.os.IBinder;
import android.os.RemoteException;
import com.android.server.desktopmode.BleAdvertiserServiceManager;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.IBleAdvertiserService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BleAdvertiserServiceManager$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BleAdvertiserServiceManager.AnonymousClass1 f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BleAdvertiserServiceManager$1$$ExternalSyntheticLambda0(BleAdvertiserServiceManager.AnonymousClass1 anonymousClass1, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BleAdvertiserServiceManager.AnonymousClass1 anonymousClass1 = this.f$0;
                ComponentName componentName = (ComponentName) this.f$1;
                anonymousClass1.getClass();
                Log.w("[DMS]BleAdvertiserServiceManager", "Null binding of '" + componentName + "', try reconnecting");
                BleAdvertiserServiceManager.this.retryConnectionWithBackoff();
                break;
            case 1:
                BleAdvertiserServiceManager.AnonymousClass1 anonymousClass12 = this.f$0;
                ComponentName componentName2 = (ComponentName) this.f$1;
                anonymousClass12.getClass();
                Log.w("[DMS]BleAdvertiserServiceManager", "Binding died of '" + componentName2 + "', try reconnecting");
                BleAdvertiserServiceManager.this.retryConnectionWithBackoff();
                break;
            default:
                BleAdvertiserServiceManager.AnonymousClass1 anonymousClass13 = this.f$0;
                IBinder iBinder = (IBinder) this.f$1;
                BleAdvertiserServiceManager bleAdvertiserServiceManager = BleAdvertiserServiceManager.this;
                if (bleAdvertiserServiceManager.mService == null) {
                    bleAdvertiserServiceManager.mConnectionBackoffAttempts = 0;
                    bleAdvertiserServiceManager.mHandler.removeCallbacks(bleAdvertiserServiceManager.mDeferredConnectionCallback);
                    BleAdvertiserServiceManager bleAdvertiserServiceManager2 = BleAdvertiserServiceManager.this;
                    bleAdvertiserServiceManager2.mHandler.removeCallbacks(bleAdvertiserServiceManager2.mBindServiceRunnable);
                    try {
                        iBinder.linkToDeath(BleAdvertiserServiceManager.this.mDeathRecipient, 0);
                        BleAdvertiserServiceManager.this.mService = IBleAdvertiserService.Stub.asInterface(iBinder);
                        if (DesktopModeFeature.DEBUG) {
                            Log.d("[DMS]BleAdvertiserServiceManager", "onServiceConnected(), mService=" + BleAdvertiserServiceManager.this.mService);
                            break;
                        }
                    } catch (RemoteException e) {
                        Log.e("[DMS]BleAdvertiserServiceManager", "Lost connection to the service", e);
                        BleAdvertiserServiceManager.this.unbindService();
                        BleAdvertiserServiceManager.this.retryConnectionWithBackoff();
                    }
                }
                break;
        }
    }
}
