package com.android.server.autofill;

import android.content.Intent;
import android.content.IntentSender;

import com.android.internal.util.function.QuintConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
