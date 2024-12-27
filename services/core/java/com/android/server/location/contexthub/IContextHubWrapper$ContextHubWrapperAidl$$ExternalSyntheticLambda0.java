package com.android.server.location.contexthub;

import android.chre.flags.Flags;
import android.os.RemoteException;
import android.util.Log;

import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */
class IContextHubWrapper$ContextHubWrapperAidl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ ContextHubService.ContextHubServiceCallback f$0;

    public /* synthetic */ IContextHubWrapper$ContextHubWrapperAidl$$ExternalSyntheticLambda0(
            ContextHubService.ContextHubServiceCallback contextHubServiceCallback) {
        this.f$0 = contextHubServiceCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ContextHubService.ContextHubServiceCallback contextHubServiceCallback = this.f$0;
        contextHubServiceCallback.getClass();
        Log.i("ContextHubService", "Recovering from Context Hub HAL restart...");
        ContextHubService contextHubService = ContextHubService.this;
        Iterator it = contextHubService.mContextHubIdToInfoMap.keySet().iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            try {
                contextHubService.mContextHubWrapper.registerExistingCallback(intValue);
                Log.i("ContextHubService", "Re-registered callback to context hub " + intValue);
            } catch (RemoteException e) {
                Log.e(
                        "ContextHubService",
                        "RemoteException while registering existing service callback for hub (ID = "
                                + intValue
                                + ")",
                        e);
            }
        }
        contextHubService.mIsTestModeEnabled.set(false);
        contextHubService.sendLocationSettingUpdate();
        contextHubService.sendWifiSettingUpdate(true);
        contextHubService.sendAirplaneModeSettingUpdate();
        contextHubService.sendMicrophoneDisableSettingUpdateForCurrentUser();
        contextHubService.sendBtSettingUpdate(true);
        if (Flags.reconnectHostEndpointsAfterHalRestart()) {
            contextHubService.mClientManager.forEachClientOfHub(
                    contextHubServiceCallback.mContextHubId,
                    new ContextHubService$ContextHubServiceCallback$$ExternalSyntheticLambda0());
        }
        Log.i("ContextHubService", "Finished recovering from Context Hub HAL restart");
    }
}
