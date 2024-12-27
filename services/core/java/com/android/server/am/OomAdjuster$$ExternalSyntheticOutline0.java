package com.android.server.am;

import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract /* synthetic */ class OomAdjuster$$ExternalSyntheticOutline0 {
    public static void m(Exception exc, StringBuilder sb, String str) {
        sb.append(exc.getMessage());
        Log.e(str, sb.toString());
        exc.printStackTrace();
    }

    public static void m(String str, ProcessRecord processRecord, OomAdjuster oomAdjuster) {
        oomAdjuster.reportOomAdjMessageLocked(str + processRecord);
    }
}
