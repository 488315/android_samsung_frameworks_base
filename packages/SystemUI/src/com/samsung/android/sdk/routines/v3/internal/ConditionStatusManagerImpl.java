package com.samsung.android.sdk.routines.v3.internal;

import android.content.Context;
import android.net.Uri;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ConditionStatusManagerImpl {
    public static void notifyConditionChanged(Context context) {
        context.getContentResolver().notifyChange(Uri.parse("content://" + context.getPackageName() + ".provider.routines.v3/playing_audio"), null);
    }
}
