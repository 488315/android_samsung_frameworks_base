package com.samsung.android.mcfds.lib;

import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AbstractDeviceSyncManager$1 {
    public final /* synthetic */ DeviceSyncManager this$0;

    public AbstractDeviceSyncManager$1(DeviceSyncManager deviceSyncManager) {
        this.this$0 = deviceSyncManager;
    }

    public final int sendMessage(Message message) {
        IMcfDeviceSyncService iMcfDeviceSyncService = this.this$0.mService;
        if (iMcfDeviceSyncService == null) {
            Log.w("[MCF_DS_LIB]_DeviceSyncManager", "sendMessage : Service is invalid");
            return -1;
        }
        try {
            return ((IMcfDeviceSyncService.Stub.Proxy) iMcfDeviceSyncService)
                    .internalCommand(message);
        } catch (RemoteException e) {
            Log.w(
                    "[MCF_DS_LIB]_DeviceSyncManager",
                    "sendMessage : [ " + message.what + " ] " + e.getMessage());
            return -1;
        }
    }
}
