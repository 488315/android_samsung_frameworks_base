package com.samsung.context.sdk.samsunganalytics.internal.sender.DLC;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.samsung.context.sdk.samsunganalytics.internal.Callback;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.sec.spp.push.dlc.api.IDlcService;

public final class DLCBinder {
    public Callback callback;
    public Context context;
    public BroadcastReceiver dlcRegisterReplyReceiver;
    public IDlcService dlcService;
    public AnonymousClass1 dlcServiceConnection;
    public boolean isBindToDLC;
    public boolean onRegisterRequest;
    public String registerFilter;

    public final void sendRegisterRequestToDLC() {
        BroadcastReceiver broadcastReceiver = this.dlcRegisterReplyReceiver;
        String str = this.registerFilter;
        if (broadcastReceiver == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(str);
            if (this.dlcRegisterReplyReceiver == null) {
                this.dlcRegisterReplyReceiver =
                        new BroadcastReceiver() { // from class:
                                                  // com.samsung.context.sdk.samsunganalytics.internal.sender.DLC.DLCBinder.2
                            @Override // android.content.BroadcastReceiver
                            public final void onReceive(Context context, Intent intent) {
                                DLCBinder.this.onRegisterRequest = false;
                                if (intent == null) {
                                    Debug.LogD("DLC Sender", "dlc register reply fail");
                                    return;
                                }
                                String action = intent.getAction();
                                Bundle extras = intent.getExtras();
                                if (action == null || extras == null) {
                                    Debug.LogD("DLC Sender", "dlc register reply fail");
                                    return;
                                }
                                if (action.equals(DLCBinder.this.registerFilter)) {
                                    String string = extras.getString("EXTRA_STR");
                                    int i = extras.getInt("EXTRA_RESULT_CODE");
                                    Debug.LogD("DLC Sender", "register DLC result:" + string);
                                    if (i < 0) {
                                        Debug.LogD(
                                                "DLC Sender", "register DLC result fail:" + string);
                                        return;
                                    }
                                    String string2 = extras.getString("EXTRA_STR_ACTION");
                                    DLCBinder dLCBinder = DLCBinder.this;
                                    boolean z = dLCBinder.isBindToDLC;
                                    if (z && z) {
                                        try {
                                            Debug.LogD("DLCBinder", "unbind");
                                            dLCBinder.context.unbindService(
                                                    dLCBinder.dlcServiceConnection);
                                            dLCBinder.isBindToDLC = false;
                                        } catch (Exception e) {
                                            Debug.LogException(DLCBinder.class, e);
                                        }
                                    }
                                    try {
                                        Intent intent2 = new Intent(string2);
                                        intent2.setClassName(
                                                "com.sec.spp.push",
                                                "com.sec.spp.push.dlc.writer.WriterService");
                                        dLCBinder.isBindToDLC =
                                                dLCBinder.context.bindService(
                                                        intent2, dLCBinder.dlcServiceConnection, 1);
                                        Debug.LogD("DLCBinder", "bind");
                                    } catch (Exception e2) {
                                        Debug.LogException(DLCBinder.class, e2);
                                    }
                                }
                            }
                        };
            }
            this.context.registerReceiver(this.dlcRegisterReplyReceiver, intentFilter);
        }
        if (this.onRegisterRequest) {
            Debug.LogD("DLCBinder", "already send register request");
            return;
        }
        Intent intent = new Intent("com.sec.spp.push.REQUEST_REGISTER");
        intent.putExtra("EXTRA_PACKAGENAME", this.context.getPackageName());
        intent.putExtra("EXTRA_INTENTFILTER", str);
        intent.setPackage("com.sec.spp.push");
        this.context.sendBroadcast(intent);
        this.onRegisterRequest = true;
        Debug.LogD("DLCBinder", "send register Request");
        Debug.LogENG("send register Request:" + this.context.getPackageName());
    }
}
