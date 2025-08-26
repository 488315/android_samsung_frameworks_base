package com.android.systemui.statusbar.pipeline.mobile.util;

import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SimType;
import java.util.Locale;
import kotlin.text.StringsKt__StringsKt;

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
    /* JADX WARN: Code restructure failed: missing block: B:27:0x005c, code lost:
    
        if (r1.equals("311270") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0066, code lost:
    
        if (r1.equals("311180") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0070, code lost:
    
        if (r1.equals("310950") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x007a, code lost:
    
        if (r1.equals("310410") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0084, code lost:
    
        if (r1.equals("310280") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x008e, code lost:
    
        if (r1.equals("310090") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0098, code lost:
    
        if ("80FF".equals(r5) == false) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x009c, code lost:
    
        return com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.ATT_PCN;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x009f, code lost:
    
        return com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.ETC;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00a6, code lost:
    
        if (r1.equals("310004") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00b0, code lost:
    
        if (r1.equals("46007") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00ba, code lost:
    
        if (r1.equals("46002") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00c3, code lost:
    
        if (r1.equals("46000") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00cc, code lost:
    
        if (r1.equals("45412") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00d1, code lost:
    
        return com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.CMCC;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x00fc, code lost:
    
        if (r1.equals("20802") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0105, code lost:
    
        if (r1.equals("20801") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x010a, code lost:
    
        return com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.ORANGE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0023, code lost:
    
        if (r1.equals("312770") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0111, code lost:
    
        if (r1.equals("20404") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0118, code lost:
    
        if ("BAE0000000000000".equals(r5) == false) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x011c, code lost:
    
        return com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.VZW;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x011f, code lost:
    
        return com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.ETC;
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
    */
    public final SimType getSimCardInfo(int i) {
        int slotIndex = SubscriptionManager.getSlotIndex(i);
        String simOperatorNumericForPhone = this.telephonyManager.getSimOperatorNumericForPhone(slotIndex);
        String groupIdLevel1 = this.telephonyManager.getGroupIdLevel1(i);
        if (simOperatorNumericForPhone != null) {
            switch (simOperatorNumericForPhone.hashCode()) {
                case 47657530:
                    break;
                case 47661371:
                    break;
                case 47661372:
                    break;
                case 49649684:
                    if (simOperatorNumericForPhone.equals("45005")) {
                        return SimType.SKT;
                    }
                    break;
                case 49649685:
                    if (simOperatorNumericForPhone.equals("45006")) {
                        return SimType.LGT;
                    }
                    break;
                case 49649687:
                    if (simOperatorNumericForPhone.equals("45008")) {
                        return SimType.KT;
                    }
                    break;
                case 49653556:
                    break;
                case 49679470:
                    break;
                case 49679472:
                    break;
                case 49679477:
                    break;
                case 1506816866:
                    break;
                case 1506817141:
                    break;
                case 1506819032:
                    break;
                case 1506820737:
                    break;
                case 1506825666:
                    break;
                case 1506847862:
                    break;
                case 1506848792:
                    break;
                case 1506850745:
                    if (simOperatorNumericForPhone.equals("311480")) {
                        return ("BAE0000000000000".equals(groupIdLevel1) || "BAE1000000000000".equals(groupIdLevel1) || "BAE2000000000000".equals(groupIdLevel1) || "BA01270000000000".equals(groupIdLevel1)) ? SimType.VZW : SimType.ETC;
                    }
                    break;
                case 1506883388:
                    break;
            }
        }
        String simOperatorNameForPhone = this.telephonyManager.getSimOperatorNameForPhone(slotIndex);
        if (!TextUtils.isEmpty(simOperatorNameForPhone) ? StringsKt__StringsKt.contains(simOperatorNameForPhone.toLowerCase(Locale.ROOT), "airtel", false) : false) {
            return SimType.AIRTEL;
        }
        String simOperatorNameForPhone2 = this.telephonyManager.getSimOperatorNameForPhone(slotIndex);
        return TextUtils.isEmpty(simOperatorNameForPhone2) ? false : StringsKt__StringsKt.contains(simOperatorNameForPhone2.toLowerCase(Locale.ROOT), "jio", false) ? SimType.RELIANCE : SimType.ETC;
    }
}
