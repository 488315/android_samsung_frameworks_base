package com.android.systemui.edgelighting.effect.utils;

import android.os.SemSystemProperties;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SalesCode {
    public static final boolean isChn;
    public static final boolean isIND;
    public static final boolean isKor;
    public static final boolean isMAL;
    public static final boolean isMYM;
    public static final boolean isPHI;
    public static final boolean isSIN;
    public static final boolean isTHL;
    public static final boolean isVn;

    static {
        String str = SemSystemProperties.get("ro.csc.sales_code", "unknown");
        isVn = is(str, "XXV", "XEV");
        isPHI = is(str, "XTC", "SMA", "GLB", "XTE");
        isMAL = is(str, "XME");
        isTHL = is(str, "THL");
        isIND = is(str, "XID", "XSE", "INS", "INU");
        isMYM = is(str, "MYM");
        isSIN = is(str, "XSP", "SIN", "MM1", "STH");
        isKor = is(str, "SKC", "KTC", "LUC", "KOO", "SKT", "SKO", "KTT", "KTO", "LGT", "LUO", "K06", "K01");
        isChn = is(str, "CHM", "CHU", "CTC", "CHC", "CHN");
    }

    public static boolean is(String str, String... strArr) {
        if (strArr.length <= 0) {
            return false;
        }
        for (String str2 : strArr) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }
}
