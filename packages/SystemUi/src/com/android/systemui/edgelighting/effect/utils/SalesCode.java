package com.android.systemui.edgelighting.effect.utils;

import android.os.SemSystemProperties;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        isVn = m133is(str, "XXV", "XEV");
        isPHI = m133is(str, "XTC", "SMA", "GLB", "XTE");
        isMAL = m133is(str, "XME");
        isTHL = m133is(str, "THL");
        isIND = m133is(str, "XID", "XSE", "INS", "INU");
        isMYM = m133is(str, "MYM");
        isSIN = m133is(str, "XSP", "SIN", "MM1", "STH");
        isKor = m133is(str, "SKC", "KTC", "LUC", "KOO", "SKT", "SKO", "KTT", "KTO", "LGT", "LUO", "K06", "K01");
        isChn = m133is(str, "CHM", "CHU", "CTC", "CHC", "CHN");
    }

    /* renamed from: is */
    public static boolean m133is(String str, String... strArr) {
        if (strArr.length > 0) {
            for (String str2 : strArr) {
                if (str2.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }
}
