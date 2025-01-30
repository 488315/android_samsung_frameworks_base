package com.samsung.android.knoxguard.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Slog;
import com.samsung.android.knoxguard.service.IntentRelayManager;
import com.samsung.android.knoxguard.service.KnoxGuardSeService;
import java.util.List;

/* loaded from: classes2.dex */
public class IntentRelayReceiver extends BroadcastReceiver {
    public static final String TAG = "KG." + IntentRelayReceiver.class.getSimpleName();

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            Slog.w(TAG, "intent is null");
            return;
        }
        String action = intent.getAction();
        Slog.i(TAG, "onReceive " + action);
        List list = KnoxGuardSeService.mActionList;
        if (list == null || !list.contains(action)) {
            return;
        }
        IntentRelayManager.sendRequestedIntent(context, KnoxGuardSeService.mPreFix + "." + action, intent.getExtras());
    }
}
