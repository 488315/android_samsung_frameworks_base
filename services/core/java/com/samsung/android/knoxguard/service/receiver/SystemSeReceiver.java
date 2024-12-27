package com.samsung.android.knoxguard.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Slog;

import com.samsung.android.knoxguard.service.IntentRelayManager;
import com.samsung.android.knoxguard.service.KGEventHandler;
import com.samsung.android.knoxguard.service.KGEventQueue;
import com.samsung.android.knoxguard.service.SystemIntentProcessor;
import com.samsung.android.knoxguard.service.utils.Constants;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SystemSeReceiver extends BroadcastReceiver {
    public static final String INTENT_PERMISSION = "com.samsung.android.permission.RMM_INIT";
    public static final String RMM_BLINK_STOP = "com.samsung.android.rmm.blink_stop";
    public static final String TAG = "KG.SystemSeReceiver";

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            Slog.w(TAG, "intent is null");
            return;
        }
        String action = intent.getAction();
        Slog.i(TAG, "onReceive " + action);
        if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
            KGEventQueue.getInstance()
                    .enqueueEvent(context, KGEventHandler.SystemEvent.ON_BOOT_COMPLETED, null);
            return;
        }
        if ("android.intent.action.USER_PRESENT".equals(action)) {
            KGEventQueue.getInstance()
                    .enqueueEvent(context, KGEventHandler.SystemEvent.ON_USER_PRESENT, null);
            return;
        }
        if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
            if (intent.getData() == null
                    || !"com.samsung.android.kgclient"
                            .equals(intent.getData().getSchemeSpecificPart())) {
                return;
            }
            IntentRelayManager.sendRequestedIntent(
                    context, Constants.INTENT_KG_PACKAGE_ADDED, intent.getExtras());
            return;
        }
        if ("android.intent.action.PACKAGE_REPLACED".equals(action)
                || "android.intent.action.PACKAGE_REMOVED".equals(action)
                || "android.intent.action.PACKAGE_CHANGED".equals(action)) {
            if (intent.getData() != null) {
                Uri data = intent.getData();
                Bundle bundle = new Bundle();
                bundle.putParcelable(SystemIntentProcessor.KEY_URI, data);
                KGEventQueue.getInstance()
                        .enqueueEvent(
                                context,
                                KGEventHandler.SystemEvent.ON_PACKAGE_REPLACED_OR_REMOVED,
                                bundle);
                return;
            }
            return;
        }
        if (Constants.INTENT_SECSETUPWIZARD_COMPLETE.equals(action)
                || Constants.INTENT_SETUPWIZARD_COMPLETE.equals(action)) {
            KGEventQueue.getInstance()
                    .enqueueEvent(
                            context, KGEventHandler.SystemEvent.ON_SETUP_WIZARD_COMPLETED, null);
            return;
        }
        if (!"android.intent.action.PACKAGE_DATA_CLEARED".equals(action)
                || intent.getData() == null) {
            return;
        }
        Uri data2 = intent.getData();
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable(SystemIntentProcessor.KEY_URI, data2);
        KGEventQueue.getInstance()
                .enqueueEvent(context, KGEventHandler.SystemEvent.ON_PACKAGE_DATA_CLEARED, bundle2);
    }
}
