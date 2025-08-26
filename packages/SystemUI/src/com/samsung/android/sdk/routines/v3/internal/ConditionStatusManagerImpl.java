package com.samsung.android.sdk.routines.v3.internal;

import android.content.Context;
import android.net.Uri;

/* loaded from: classes4.dex */
public class ConditionStatusManagerImpl {
    public static void notifyConditionChanged(Context context) {
        context.getContentResolver().notifyChange(Uri.parse("content://" + context.getPackageName() + ".provider.routines.v3/playing_audio"), null);
    }
}
