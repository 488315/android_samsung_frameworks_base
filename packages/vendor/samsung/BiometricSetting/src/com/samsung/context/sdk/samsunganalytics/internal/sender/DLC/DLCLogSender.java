package com.samsung.context.sdk.samsunganalytics.internal.sender.DLC;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.Callback;
import com.samsung.context.sdk.samsunganalytics.internal.executor.SingleThreadExecutor;
import com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.sec.spp.push.dlc.api.IDlcService;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class DLCLogSender extends BaseLogSender {
    public final DLCBinder binder;

    public DLCLogSender(Context context, Configuration configuration) {
        super(context, configuration);
        Callback callback =
                new Callback() { // from class:
                                 // com.samsung.context.sdk.samsunganalytics.internal.sender.DLC.DLCLogSender.1
                    @Override // com.samsung.context.sdk.samsunganalytics.internal.Callback
                    public final void onResult(Object obj) {
                        DLCLogSender.this.sendAll();
                    }
                };
        DLCBinder dLCBinder = new DLCBinder();
        dLCBinder.isBindToDLC = false;
        dLCBinder.onRegisterRequest = false;
        dLCBinder.dlcServiceConnection =
                new ServiceConnection() { // from class:
                                          // com.samsung.context.sdk.samsunganalytics.internal.sender.DLC.DLCBinder.1
                    public AnonymousClass1() {}

                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r1v3, types: [com.sec.spp.push.dlc.api.IDlcService] */
                    @Override // android.content.ServiceConnection
                    public final void onServiceConnected(
                            ComponentName componentName, IBinder iBinder) {
                        IDlcService.Stub.Proxy proxy;
                        Debug.LogD("DLC Sender", "DLC Client ServiceConnected");
                        DLCBinder dLCBinder2 = DLCBinder.this;
                        int i = IDlcService.Stub.$r8$clinit;
                        if (iBinder == null) {
                            proxy = null;
                        } else {
                            IInterface queryLocalInterface =
                                    iBinder.queryLocalInterface(
                                            "com.sec.spp.push.dlc.api.IDlcService");
                            if (queryLocalInterface == null
                                    || !(queryLocalInterface instanceof IDlcService)) {
                                IDlcService.Stub.Proxy proxy2 = new IDlcService.Stub.Proxy();
                                proxy2.mRemote = iBinder;
                                proxy = proxy2;
                            } else {
                                proxy = (IDlcService) queryLocalInterface;
                            }
                        }
                        dLCBinder2.dlcService = proxy;
                        DLCBinder dLCBinder3 = DLCBinder.this;
                        BroadcastReceiver broadcastReceiver = dLCBinder3.dlcRegisterReplyReceiver;
                        if (broadcastReceiver != null) {
                            dLCBinder3.context.unregisterReceiver(broadcastReceiver);
                            DLCBinder.this.dlcRegisterReplyReceiver = null;
                        }
                        Callback callback2 = DLCBinder.this.callback;
                        if (callback2 != null) {
                            callback2.onResult(null);
                        }
                    }

                    @Override // android.content.ServiceConnection
                    public final void onServiceDisconnected(ComponentName componentName) {
                        Debug.LogD("DLC Sender", "Client ServiceDisconnected");
                        DLCBinder dLCBinder2 = DLCBinder.this;
                        dLCBinder2.dlcService = null;
                        dLCBinder2.isBindToDLC = false;
                    }
                };
        dLCBinder.context = context;
        String packageName = context.getPackageName();
        dLCBinder.registerFilter = packageName;
        dLCBinder.registerFilter =
                AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                        packageName, ".REGISTER_FILTER");
        dLCBinder.callback = callback;
        this.binder = dLCBinder;
        dLCBinder.sendRegisterRequestToDLC();
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender
    public final int send(Map map) {
        insert(map);
        DLCBinder dLCBinder = this.binder;
        if (!dLCBinder.isBindToDLC) {
            dLCBinder.sendRegisterRequestToDLC();
            return 0;
        }
        if (dLCBinder.dlcService == null) {
            return 0;
        }
        sendAll();
        return 0;
    }

    public final void sendAll() {
        Queue queue = this.manager.get(0);
        while (!queue.isEmpty()) {
            SimpleLog simpleLog = (SimpleLog) queue.poll();
            SendLogTask sendLogTask = new SendLogTask();
            sendLogTask.result = -1;
            sendLogTask.binder = this.binder;
            sendLogTask.configuration = this.configuration;
            sendLogTask.simpleLog = simpleLog;
            this.executor.getClass();
            SingleThreadExecutor.execute(sendLogTask);
        }
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender
    public final Map setCommonParamToLog(Map map) {
        super.setCommonParamToLog(map);
        HashMap hashMap = (HashMap) map;
        hashMap.remove("do");
        hashMap.remove("dm");
        hashMap.remove("v");
        return map;
    }
}
