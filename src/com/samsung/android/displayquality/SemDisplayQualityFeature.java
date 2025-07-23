package com.samsung.android.displayquality;

/* loaded from: classes6.dex */
public class SemDisplayQualityFeature {
    public static final boolean ADAPTIVE_SYNC_SUPPORT;
    public static final boolean DP_BACKOFF_SUPPORT;
    public static final boolean DP_DEBUG_SUPPORT;
    public static final boolean DP_RATIO_SUPPORT;
    private static final String DQ_SVC_FEATURE = "";
    public static final boolean ENABLED = false;
    public static final boolean HAL_SUPPORT;
    private static final boolean HAS_OPTION;
    public static final boolean LTM_SUPPORT;
    public static final boolean OUTDOOR_VISIBILITY_SUPPORT;
    public static final String PLATFORM = "";
    public static final boolean SVI_SUPPORT;
    public static final boolean VIVID_PLUS_SUPPORT;

    static {
        boolean z = "".split(",").length > 1;
        HAS_OPTION = z;
        OUTDOOR_VISIBILITY_SUPPORT = "".contains("MTK") && !z;
        ADAPTIVE_SYNC_SUPPORT = false;
        LTM_SUPPORT = false;
        SVI_SUPPORT = false;
        HAL_SUPPORT = false;
        VIVID_PLUS_SUPPORT = false;
        DP_RATIO_SUPPORT = false;
        DP_DEBUG_SUPPORT = false;
        DP_BACKOFF_SUPPORT = false;
    }
}
