package com.samsung.android.knoxguard.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Slog;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.samsung.android.knoxguard.service.IntentRelayManager;
import com.samsung.android.knoxguard.service.KnoxGuardSeService;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class IntentRelayReceiver extends BroadcastReceiver {
    public static final String TAG = "KG.IntentRelayReceiver";

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
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
        IntentRelayManager.sendRequestedIntent(context, BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder(), KnoxGuardSeService.mPreFix, ".", action), intent.getExtras());
    }
}
