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

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;

public class UpdateReceiver extends BroadcastReceiver {
    public Context mContext;
    public final AnonymousClass2 mHandler = new Handler(Looper.getMainLooper()) { // from class:
                // com.android.server.spay.UpdateReceiver.2
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    UpdateReceiver updateReceiver;
                    Log.d("UpdateReceiver", "Handler : " + message.what);
                    Bundle data = message.getData();
                    String string = data.getString("action");
                    if (string != null) {
                        updateReceiver = UpdateReceiver.this;
                        switch (string) {
                            case "android.intent.action.PACKAGE_REPLACED":
                            case "android.intent.action.PACKAGE_ADDED":
                                StringBuilder m =
                                        DumpUtils$$ExternalSyntheticOutline0.m(
                                                "action : ", string, ", ");
                                m.append(data.getString("getdata_of_intent"));
                                Log.d("UpdateReceiver", m.toString());
                                if (updateReceiver.mContext != null
                                        && data.getString("getdata_of_intent") != null) {
                                    if (!data.getString("getdata_of_intent")
                                            .contains("com.samsung.android.spay")) {
                                        if (!data.getString("getdata_of_intent")
                                                .contains("com.samsung.android.spaymini")) {
                                            if (!data.getString("getdata_of_intent")
                                                    .contains(
                                                            "com.samsung.android.samsungpay.gear")) {
                                                if (data.getString("getdata_of_intent")
                                                        .contains(
                                                                "com.samsung.android.rajaampat")) {
                                                    Utils.backgroundWhitelist(
                                                            updateReceiver.mContext,
                                                            "com.samsung.android.rajaampat",
                                                            false);
                                                    break;
                                                }
                                            } else {
                                                Utils.backgroundWhitelist(
                                                        updateReceiver.mContext,
                                                        "com.samsung.android.samsungpay.gear",
                                                        false);
                                                break;
                                            }
                                        } else {
                                            Utils.backgroundWhitelist(
                                                    updateReceiver.mContext,
                                                    "com.samsung.android.spaymini",
                                                    false);
                                            break;
                                        }
                                    } else {
                                        Log.d(
                                                "UpdateReceiver",
                                                string.concat(" received : triggering PF Install"));
                                        UpdateReceiver.triggerPFInstall(updateReceiver.mContext);
                                        Utils.backgroundWhitelist(
                                                updateReceiver.mContext,
                                                "com.samsung.android.spay",
                                                false);
                                        break;
                                    }
                                }
                                break;
                            case "android.intent.action.BOOT_COMPLETED":
                                Log.d(
                                        "UpdateReceiver",
                                        "ACTION_BOOT_COMPLETED received : triggering PF Install");
                                Context context = updateReceiver.mContext;
                                if (context != null) {
                                    UpdateReceiver.triggerPFInstall(context);
                                    Utils.backgroundWhitelist(
                                            updateReceiver.mContext,
                                            "com.samsung.android.spay",
                                            false);
                                    Utils.backgroundWhitelist(
                                            updateReceiver.mContext,
                                            "com.samsung.android.spayfw",
                                            false);
                                    Utils.backgroundWhitelist(
                                            updateReceiver.mContext,
                                            "com.samsung.android.spaymini",
                                            false);
                                    Utils.backgroundWhitelist(
                                            updateReceiver.mContext,
                                            "com.samsung.android.samsungpay.gear",
                                            false);
                                    Utils.backgroundWhitelist(
                                            updateReceiver.mContext,
                                            "com.samsung.android.rajaampat",
                                            false);
                                    break;
                                }
                                break;
                        }
                    }
                }
            };

    public static void triggerPFInstall(Context context) {
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.spay.action.PFINSTALL");
        intent.addFlags(32);
        intent.setComponent(
                new ComponentName(
                        "com.samsung.android.spay",
                        "com.samsung.android.spay.common.us.LocalPFBroadcastReceiver"));
        context.sendBroadcastAsUser(
                intent, UserHandle.CURRENT, "com.samsung.android.spay.permission.INSTALL_PF");
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, final Intent intent) {
        this.mContext = context;
        if (intent != null) {
            final String action = intent.getAction();
            Log.d("UpdateReceiver:", "Action: " + action);
            new Thread() { // from class: com.android.server.spay.UpdateReceiver.1
                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    try {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("action", action);
                        bundle.putString("getdata_of_intent", intent.getDataString());
                        message.setData(bundle);
                        sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
