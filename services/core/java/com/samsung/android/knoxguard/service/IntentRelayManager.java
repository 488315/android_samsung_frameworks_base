package com.samsung.android.knoxguard.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Slog;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

/* loaded from: classes2.dex */
public abstract class IntentRelayManager {
    public static final String TAG = "KG." + IntentRelayManager.class.getSimpleName();

    public static void sendRequestedIntent(Context context, String str, Bundle bundle) {
        Intent intent = new Intent(str);
        intent.setPackage(KnoxCustomManagerService.KG_PKG_NAME);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.addFlags(32);
        context.sendBroadcastAsUser(intent, UserHandle.ALL, "com.samsung.android.knoxguard.STATUS");
        Slog.i(TAG, "sends to KG " + str);
    }
}
