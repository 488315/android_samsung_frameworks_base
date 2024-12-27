package com.android.server.enterprise.datetime;

import android.content.Intent;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class DateTimePolicy$$ExternalSyntheticLambda0
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ DateTimePolicy f$0;

    public final void runOrThrow() {
        this.f$0.mContext.sendBroadcast(
                new Intent(
                        "com.samsung.android.knox.intent.action.UPDATE_NTP_PARAMETERS_INTERNAL"));
    }
}
