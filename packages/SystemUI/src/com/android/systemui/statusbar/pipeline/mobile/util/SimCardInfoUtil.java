package com.android.systemui.statusbar.pipeline.mobile.util;

import android.telephony.TelephonyManager;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SimCardInfoUtil {
    public final TelephonyManager telephonyManager;

    public SimCardInfoUtil(TelephonyManager telephonyManager) {
        this.telephonyManager = telephonyManager;
    }

    public static String simStateToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "ABSENT";
            case 2:
                return "PIN_REQUIRED";
            case 3:
                return "PUK_REQUIRED";
            case 4:
                return "NETWORK_LOCKED";
            case 5:
                return "READY";
            case 6:
                return "NOT_READY";
            case 7:
                return "PERM_DISABLED";
            case 8:
                return "CARD_IO_ERROR";
            case 9:
                return "CARD_RESTRICTED";
            case 10:
                return "LOADED";
            case 11:
                return "PRESENT";
            default:
                return LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "UNKNOWN(", ")");
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x005e, code lost:
    
        if (r1.equals("311270") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0068, code lost:
    
        if (r1.equals("310004") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0072, code lost:
    
        if (r1.equals("46007") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x007c, code lost:
    
        if (r1.equals("46002") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0086, code lost:
    
        if (r1.equals("46000") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0090, code lost:
    
        if (r1.equals("45412") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00c4, code lost:
    
        if (r1.equals("20802") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00cd, code lost:
    
        if (r1.equals("20801") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00d9, code lost:
    
        if (r1.equals("20404") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0023, code lost:
    
        if (r1.equals("312770") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x00e0, code lost:
    
        if ("BAE0000000000000".equals(r5) == false) goto L68;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.statusbar.pipeline.mobile.data.model.SimType getSimCardInfo(int r5) {
        /*
            Method dump skipped, instructions count: 354
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.util.SimCardInfoUtil.getSimCardInfo(int):com.android.systemui.statusbar.pipeline.mobile.data.model.SimType");
    }
}
