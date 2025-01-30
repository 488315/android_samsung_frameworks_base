package com.samsung.android.knox.dar;

/* loaded from: classes5.dex */
public class DarRune {
    public static final boolean KNOX_SUPPORT_DAR = true;
    public static final boolean KNOX_SUPPORT_DAR_DUAL = true;
    public static final boolean KNOX_SUPPORT_DAR_DUAL_DO = true;
    public static final boolean KNOX_SUPPORT_DAR_SDP = false;
    public static final boolean KNOX_SUPPORT_DAR_SDP_LOG = true;
    public static final boolean KNOX_SUPPORT_DAR_SDP_OR_DUAL = true;
    public static final boolean KNOX_SUPPORT_DAR_SECURE_FOLDER = true;
    public static final boolean KNOX_SUPPORT_DAR_VIRTUAL_USER = true;
    public static final boolean KNOX_SUPPORT_DAR_WEAVER = true;
    public static final boolean KNOX_SUPPORT_ENDPOINT_MONITORING = true;
    private static final String TAG = "DarRune";
    private static DarRune sInstance;

    private DarRune() {
    }

    public static DarRune getInstance() {
        if (sInstance == null) {
            sInstance = new DarRune();
        }
        return sInstance;
    }
}
