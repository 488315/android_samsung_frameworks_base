package com.samsung.android.mcfds.lib;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.util.Log;

import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;

import com.samsung.android.server.continuity.McfDeviceSyncManager;
import com.samsung.android.server.continuity.PreconditionObserver;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DeviceSyncManager {
    public final Context mContext;
    public McfDeviceSyncManager.AnonymousClass3 mListener;
    public IMcfDeviceSyncService mService;
    public int mServiceState;
    public final AbstractDeviceSyncManager$1 mCoreInterface = new AbstractDeviceSyncManager$1(this);
    public final AbstractDeviceSyncManager$2 mServiceStateListener =
            new AbstractDeviceSyncManager$2(this);
    public final AbstractDeviceSyncManager$3 mServiceConnection =
            new ServiceConnection() { // from class:
                                      // com.samsung.android.mcfds.lib.AbstractDeviceSyncManager$3
                @Override // android.content.ServiceConnection
                public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    IMcfDeviceSyncService iMcfDeviceSyncService;
                    Log.i("[MCF_DS_LIB]_DeviceSyncManager", "onServiceConnected");
                    DeviceSyncManager deviceSyncManager = DeviceSyncManager.this;
                    deviceSyncManager.mServiceState = 3;
                    int i = IMcfDeviceSyncService.Stub.$r8$clinit;
                    if (iBinder == null) {
                        iMcfDeviceSyncService = null;
                    } else {
                        IInterface queryLocalInterface =
                                iBinder.queryLocalInterface(
                                        "com.samsung.android.mcfds.lib.IMcfDeviceSyncService");
                        if (queryLocalInterface == null
                                || !(queryLocalInterface instanceof IMcfDeviceSyncService)) {
                            IMcfDeviceSyncService.Stub.Proxy proxy =
                                    new IMcfDeviceSyncService.Stub.Proxy();
                            proxy.mRemote = iBinder;
                            iMcfDeviceSyncService = proxy;
                        } else {
                            iMcfDeviceSyncService = (IMcfDeviceSyncService) queryLocalInterface;
                        }
                    }
                    deviceSyncManager.mService = iMcfDeviceSyncService;
                    DeviceSyncManager.access$200(DeviceSyncManager.this, 3);
                    DeviceSyncManager deviceSyncManager2 = DeviceSyncManager.this;
                    deviceSyncManager2.getClass();
                    Bundle bundle = new Bundle();
                    AbstractDeviceSyncManager$2 abstractDeviceSyncManager$2 =
                            deviceSyncManager2.mServiceStateListener;
                    abstractDeviceSyncManager$2.getClass();
                    bundle.putBinder("CALLBACK", abstractDeviceSyncManager$2);
                    AbstractDeviceSyncManager$1 abstractDeviceSyncManager$1 =
                            deviceSyncManager2.mCoreInterface;
                    abstractDeviceSyncManager$1.getClass();
                    Message obtain = Message.obtain();
                    obtain.what = 10;
                    obtain.obj = bundle;
                    abstractDeviceSyncManager$1.sendMessage(obtain);
                }

                @Override // android.content.ServiceConnection
                public final void onServiceDisconnected(ComponentName componentName) {
                    Log.i("[MCF_DS_LIB]_DeviceSyncManager", "onServiceDisconnected");
                    DeviceSyncManager deviceSyncManager = DeviceSyncManager.this;
                    deviceSyncManager.mServiceState = 1;
                    deviceSyncManager.mService = null;
                    deviceSyncManager.getClass();
                    DeviceSyncManager.access$200(DeviceSyncManager.this, 1);
                }
            };

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.mcfds.lib.AbstractDeviceSyncManager$3] */
    public DeviceSyncManager(Context context) {
        this.mContext = context;
    }

    public static void access$200(DeviceSyncManager deviceSyncManager, int i) {
        McfDeviceSyncManager.AnonymousClass3 anonymousClass3 = deviceSyncManager.mListener;
        if (anonymousClass3 != null) {
            if (i != 1) {
                McfDeviceSyncManager mcfDeviceSyncManager = McfDeviceSyncManager.this;
                if (i == 3) {
                    AudioService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("bindMcf : SERVICE_STATE_CONNECTED (bindReason: "),
                            anonymousClass3.mBindReason,
                            ")",
                            "[MCF_DS_SYS]_McfDsManager");
                    mcfDeviceSyncManager.initMcfDeviceSyncMainController(
                            anonymousClass3.mBindReason);
                } else if (i == 4) {
                    Log.i("[MCF_DS_SYS]_McfDsManager", "bindMcf : SERVICE_STATE_UNAVAILABLE");
                } else if (i == 5) {
                    Log.i("[MCF_DS_SYS]_McfDsManager", "bindMcf : SERVICE_STATE_AVAILABLE");
                    if (PreconditionObserver.isSupported(8)) {
                        mcfDeviceSyncManager.mHandler.sendEmptyMessage(5);
                    }
                }
            } else {
                Log.e("[MCF_DS_SYS]_McfDsManager", "bindMcf : SERVICE_STATE_DISCONNECTED");
            }
            anonymousClass3.mBindReason = 0;
        }
    }
}
