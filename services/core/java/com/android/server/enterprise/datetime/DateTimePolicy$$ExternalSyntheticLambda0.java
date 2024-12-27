package com.android.server.enterprise.datetime;

import android.content.Intent;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DateTimePolicy$$ExternalSyntheticLambda0
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ DateTimePolicy f$0;

    public final void runOrThrow() {
        this.f$0.mContext.sendBroadcast(
                new Intent(
                        "com.samsung.android.knox.intent.action.UPDATE_NTP_PARAMETERS_INTERNAL"));
    }
}
