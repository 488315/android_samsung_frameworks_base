package com.android.systemui.statusbar.pipeline.mobile.util;

import android.telephony.TelephonyManager;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
                return ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "UNKNOWN(", ")");
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x011c, code lost:
    
        return com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.VZW;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x011f, code lost:
    
        return com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.ETC;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x005c, code lost:
    
        if (r1.equals("311270") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0066, code lost:
    
        if (r1.equals("311180") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0098, code lost:
    
        if ("80FF".equals(r5) == false) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x009c, code lost:
    
        return com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.ATT_PCN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x009f, code lost:
    
        return com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.ETC;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0070, code lost:
    
        if (r1.equals("310950") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x007a, code lost:
    
        if (r1.equals("310410") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0084, code lost:
    
        if (r1.equals("310280") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x008e, code lost:
    
        if (r1.equals("310090") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00a6, code lost:
    
        if (r1.equals("310004") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00b0, code lost:
    
        if (r1.equals("46007") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00d1, code lost:
    
        return com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.CMCC;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00ba, code lost:
    
        if (r1.equals("46002") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00c3, code lost:
    
        if (r1.equals("46000") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00cc, code lost:
    
        if (r1.equals("45412") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0023, code lost:
    
        if (r1.equals("312770") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00fc, code lost:
    
        if (r1.equals("20802") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x010a, code lost:
    
        return com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.ORANGE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0105, code lost:
    
        if (r1.equals("20801") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0111, code lost:
    
        if (r1.equals("20404") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0118, code lost:
    
        if ("BAE0000000000000".equals(r5) == false) goto L97;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.statusbar.pipeline.mobile.data.model.SimType getSimCardInfo(int r5) {
        /*
            Method dump skipped, instructions count: 430
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.util.SimCardInfoUtil.getSimCardInfo(int):com.android.systemui.statusbar.pipeline.mobile.data.model.SimType");
    }
}
