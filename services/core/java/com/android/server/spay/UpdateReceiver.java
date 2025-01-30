package com.android.server.spay;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;

/* loaded from: classes3.dex */
public class UpdateReceiver extends BroadcastReceiver {
    public Context mContext;
    public Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.server.spay.UpdateReceiver.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Log.d("UpdateReceiver", "Handler : " + message.what);
            Bundle data = message.getData();
            String string = data.getString("action");
            if (string != null) {
                switch (string) {
                    case "android.intent.action.PACKAGE_REPLACED":
                    case "android.intent.action.PACKAGE_ADDED":
                        Log.d("UpdateReceiver", "action : " + string + ", " + data.getString("getdata_of_intent"));
                        if (UpdateReceiver.this.mContext != null && data.getString("getdata_of_intent") != null) {
                            if (data.getString("getdata_of_intent").contains("com.samsung.android.spay")) {
                                Log.d("UpdateReceiver", string + " received : triggering PF Install");
                                UpdateReceiver updateReceiver = UpdateReceiver.this;
                                updateReceiver.triggerPFInstall(updateReceiver.mContext);
                                Utils.backgroundWhitelist(UpdateReceiver.this.mContext, "com.samsung.android.spay");
                                break;
                            } else if (data.getString("getdata_of_intent").contains("com.samsung.android.spaymini")) {
                                Utils.backgroundWhitelist(UpdateReceiver.this.mContext, "com.samsung.android.spaymini");
                                break;
                            } else if (data.getString("getdata_of_intent").contains("com.samsung.android.samsungpay.gear")) {
                                Utils.backgroundWhitelist(UpdateReceiver.this.mContext, "com.samsung.android.samsungpay.gear");
                                break;
                            } else if (data.getString("getdata_of_intent").contains("com.samsung.android.rajaampat")) {
                                Utils.backgroundWhitelist(UpdateReceiver.this.mContext, "com.samsung.android.rajaampat");
                                break;
                            }
                        }
                        break;
                    case "android.intent.action.BOOT_COMPLETED":
                        Log.d("UpdateReceiver", "ACTION_BOOT_COMPLETED received : triggering PF Install");
                        if (UpdateReceiver.this.mContext != null) {
                            UpdateReceiver updateReceiver2 = UpdateReceiver.this;
                            updateReceiver2.triggerPFInstall(updateReceiver2.mContext);
                            Utils.backgroundWhitelist(UpdateReceiver.this.mContext, "com.samsung.android.spay");
                            Utils.backgroundWhitelist(UpdateReceiver.this.mContext, "com.samsung.android.spayfw");
                            Utils.backgroundWhitelist(UpdateReceiver.this.mContext, "com.samsung.android.spaymini");
                            Utils.backgroundWhitelist(UpdateReceiver.this.mContext, "com.samsung.android.samsungpay.gear");
                            Utils.backgroundWhitelist(UpdateReceiver.this.mContext, "com.samsung.android.rajaampat");
                            break;
                        }
                        break;
                }
            }
        }
    };

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, final Intent intent) {
        this.mContext = context;
        if (intent != null) {
            final String action = intent.getAction();
            Log.d("UpdateReceiver:", "Action: " + action);
            new Thread() { // from class: com.android.server.spay.UpdateReceiver.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("action", action);
                        bundle.putString("getdata_of_intent", intent.getDataString());
                        message.setData(bundle);
                        UpdateReceiver.this.mHandler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    public void triggerPFInstall(Context context) {
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.spay.action.PFINSTALL");
        intent.addFlags(32);
        intent.setComponent(new ComponentName("com.samsung.android.spay", "com.samsung.android.spay.common.us.LocalPFBroadcastReceiver"));
        context.sendBroadcastAsUser(intent, UserHandle.CURRENT, "com.samsung.android.spay.permission.INSTALL_PF");
    }
}
