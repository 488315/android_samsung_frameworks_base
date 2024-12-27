package com.samsung.android.displayquality;

public class SemDisplayQualityFeature {
    public static final boolean ADAPTIVE_SYNC_SUPPORT;
    public static final boolean DP_BACKOFF_SUPPORT;
    public static final boolean DP_DEBUG_SUPPORT;
    public static final boolean DP_RATIO_SUPPORT;
    private static final String DQ_SVC_FEATURE = "";
    public static final boolean ENABLED;
    public static final boolean HAL_SUPPORT;
    private static final boolean HAS_OPTION;
    public static final boolean LTM_SUPPORT;
    public static final boolean OUTDOOR_VISIBILITY_SUPPORT;
    public static final String PLATFORM;
    public static final boolean SVI_SUPPORT;
    public static final boolean VIVID_PLUS_SUPPORT;

    static {
        boolean z = false;
        ENABLED = "".contains("QCT") || "".contains("LSI") || "".contains("MTK");
        PLATFORM = ENABLED ? "".trim().substring(0, 3) : "";
        HAS_OPTION = "".split(",").length > 1;
        if ("".contains("outdoor") || (PLATFORM.contains("MTK") && !HAS_OPTION)) {
            z = true;
        }
        OUTDOOR_VISIBILITY_SUPPORT = z;
        ADAPTIVE_SYNC_SUPPORT = "".contains("adaptivesync");
        LTM_SUPPORT = "".contains("ltm");
        SVI_SUPPORT = "".contains("svi");
        HAL_SUPPORT = "".contains("hal");
        VIVID_PLUS_SUPPORT = "".contains("vividplus");
        DP_RATIO_SUPPORT = "".contains("dp_ratio");
        DP_DEBUG_SUPPORT = "".contains("dp_debug");
        DP_BACKOFF_SUPPORT = "".contains("dp_backoff");
    }
}
