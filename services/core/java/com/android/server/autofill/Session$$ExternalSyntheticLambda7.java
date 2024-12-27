package com.android.server.autofill;

import android.content.Intent;
import android.content.IntentSender;

import com.android.internal.util.function.QuintConsumer;

public final /* synthetic */ class Session$$ExternalSyntheticLambda7 implements QuintConsumer {
    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        ((Session) obj)
                .startAuthentication(
                        ((Integer) obj2).intValue(),
                        (IntentSender) obj3,
                        (Intent) obj4,
                        ((Boolean) obj5).booleanValue());
    }
}
