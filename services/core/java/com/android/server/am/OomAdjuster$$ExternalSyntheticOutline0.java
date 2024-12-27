package com.android.server.am;

import android.util.Log;

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
