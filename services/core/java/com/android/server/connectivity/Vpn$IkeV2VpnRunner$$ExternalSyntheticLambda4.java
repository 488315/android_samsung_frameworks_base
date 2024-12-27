package com.android.server.connectivity;

import android.util.Log;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final /* synthetic */ class Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda4
        implements Vpn.ValidationStatusCallback {
    public final /* synthetic */ Vpn.VpnRunner f$0;

    @Override // com.android.server.connectivity.Vpn.ValidationStatusCallback
    public void onValidationStatus(int i) {
        Vpn.IkeV2VpnRunner ikeV2VpnRunner = (Vpn.IkeV2VpnRunner) this.f$0;
        ikeV2VpnRunner.this$0.mEventChanges.log("[Validation] validation status " + i);
        if (i == 1) {
            ikeV2VpnRunner.mExecutor.execute(
                    new Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda3(ikeV2VpnRunner, 1));
            return;
        }
        if (ikeV2VpnRunner.mScheduledHandleDataStallFuture != null) {
            return;
        }
        if (ikeV2VpnRunner.mValidationFailRetryCount == 0) {
            ikeV2VpnRunner.this$0.mConnectivityManager.reportNetworkConnectivity(
                    ikeV2VpnRunner.mActiveNetwork, false);
        }
        int i2 = ikeV2VpnRunner.mValidationFailRetryCount;
        if (i2 >= 2) {
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = ikeV2VpnRunner.mExecutor;
            Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda3
                    vpn$IkeV2VpnRunner$$ExternalSyntheticLambda3 =
                            new Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda3(ikeV2VpnRunner, 2);
            Vpn.Dependencies dependencies = ikeV2VpnRunner.this$0.mDeps;
            ikeV2VpnRunner.mValidationFailRetryCount = i2 + 1;
            dependencies.getClass();
            long[] jArr = Vpn.DATA_STALL_RECOVERY_DELAYS_MS;
            ikeV2VpnRunner.mScheduledHandleDataStallFuture =
                    scheduledThreadPoolExecutor.schedule(
                            vpn$IkeV2VpnRunner$$ExternalSyntheticLambda3,
                            i2 >= 8 ? jArr[7] : jArr[i2],
                            TimeUnit.MILLISECONDS);
            return;
        }
        Log.d("IkeV2VpnRunner", "Validation failed");
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor2 = ikeV2VpnRunner.mExecutor;
        Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda3 vpn$IkeV2VpnRunner$$ExternalSyntheticLambda32 =
                new Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda3(ikeV2VpnRunner, 4);
        Vpn.Dependencies dependencies2 = ikeV2VpnRunner.this$0.mDeps;
        int i3 = ikeV2VpnRunner.mValidationFailRetryCount;
        ikeV2VpnRunner.mValidationFailRetryCount = i3 + 1;
        dependencies2.getClass();
        long[] jArr2 = Vpn.DATA_STALL_RECOVERY_DELAYS_MS;
        scheduledThreadPoolExecutor2.schedule(
                vpn$IkeV2VpnRunner$$ExternalSyntheticLambda32,
                i3 >= 8 ? jArr2[7] : jArr2[i3],
                TimeUnit.MILLISECONDS);
    }
}
