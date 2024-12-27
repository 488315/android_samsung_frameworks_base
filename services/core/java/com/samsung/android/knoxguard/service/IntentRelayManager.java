package com.samsung.android.knoxguard.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Slog;

import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;

import com.samsung.android.knoxguard.service.utils.Constants;

public final class IntentRelayManager {
    public static final boolean LOG = false;
    public static final String TAG = "KG.IntentRelayManager";

    public static void sendRequestedIntent(Context context, String str, Bundle bundle) {
        Intent m =
                ExplicitHealthCheckController$$ExternalSyntheticOutline0.m(
                        str, "com.samsung.android.kgclient");
        if (bundle != null) {
            m.putExtras(bundle);
        }
        m.addFlags(32);
        context.sendBroadcastAsUser(m, UserHandle.ALL, Constants.KG_PERMISSION);
        Slog.i(TAG, "sends to KG " + str);
    }
}
