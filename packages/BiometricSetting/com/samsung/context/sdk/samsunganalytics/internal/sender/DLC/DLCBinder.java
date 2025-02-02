package com.samsung.context.sdk.samsunganalytics.internal.sender.DLC;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import com.samsung.context.sdk.samsunganalytics.internal.Callback;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.sec.spp.push.dlc.api.IDlcService;

/* loaded from: classes.dex */
public final class DLCBinder {
    private Callback callback;
    private Context context;
    private BroadcastReceiver dlcRegisterReplyReceiver;
    private IDlcService dlcService;
    private String registerFilter;
    private boolean isBindToDLC = false;
    private boolean onRegisterRequest = false;
    private ServiceConnection dlcServiceConnection = new ServiceConnection() { // from class: com.samsung.context.sdk.samsunganalytics.internal.sender.DLC.DLCBinder.1
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Debug.LogD("DLC Sender", "DLC Client ServiceConnected");
            DLCBinder.this.dlcService = IDlcService.Stub.asInterface(iBinder);
            if (DLCBinder.this.dlcRegisterReplyReceiver != null) {
                DLCBinder.this.context.unregisterReceiver(DLCBinder.this.dlcRegisterReplyReceiver);
                DLCBinder.this.dlcRegisterReplyReceiver = null;
            }
            if (DLCBinder.this.callback != null) {
                DLCBinder.this.callback.onResult(null);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            Debug.LogD("DLC Sender", "Client ServiceDisconnected");
            DLCBinder.this.dlcService = null;
            DLCBinder.this.isBindToDLC = false;
        }
    };

    public DLCBinder(Context context, Callback callback) {
        this.context = context;
        this.registerFilter = context.getPackageName();
        this.registerFilter += ".REGISTER_FILTER";
        this.callback = callback;
    }

    static void access$700(DLCBinder dLCBinder, String str) {
        boolean z = dLCBinder.isBindToDLC;
        if (z && z) {
            try {
                Debug.LogD("DLCBinder", "unbind");
                dLCBinder.context.unbindService(dLCBinder.dlcServiceConnection);
                dLCBinder.isBindToDLC = false;
            } catch (Exception e) {
                Debug.LogException(DLCBinder.class, e);
            }
        }
        try {
            Intent intent = new Intent(str);
            intent.setClassName("com.sec.spp.push", "com.sec.spp.push.dlc.writer.WriterService");
            dLCBinder.isBindToDLC = dLCBinder.context.bindService(intent, dLCBinder.dlcServiceConnection, 1);
            Debug.LogD("DLCBinder", "bind");
        } catch (Exception e2) {
            Debug.LogException(DLCBinder.class, e2);
        }
    }

    public final IDlcService getDlcService() {
        return this.dlcService;
    }

    public final boolean isBindToDLC() {
        return this.isBindToDLC;
    }

    public final void sendRegisterRequestToDLC() {
        if (this.dlcRegisterReplyReceiver == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(this.registerFilter);
            if (this.dlcRegisterReplyReceiver == null) {
                this.dlcRegisterReplyReceiver = new BroadcastReceiver() { // from class: com.samsung.context.sdk.samsunganalytics.internal.sender.DLC.DLCBinder.2
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
                            if (i >= 0) {
                                DLCBinder.access$700(DLCBinder.this, extras.getString("EXTRA_STR_ACTION"));
                            } else {
                                Debug.LogD("DLC Sender", "register DLC result fail:" + string);
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
        intent.putExtra("EXTRA_INTENTFILTER", this.registerFilter);
        intent.setPackage("com.sec.spp.push");
        this.context.sendBroadcast(intent);
        this.onRegisterRequest = true;
        Debug.LogD("DLCBinder", "send register Request");
        Debug.LogENG("send register Request:" + this.context.getPackageName());
    }
}
