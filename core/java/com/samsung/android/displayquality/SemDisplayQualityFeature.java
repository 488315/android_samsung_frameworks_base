package com.samsung.android.displayquality;

/* loaded from: classes5.dex */
public class SemDisplayQualityFeature {
  public static final boolean ADAPTIVE_SYNC_SUPPORT;
  public static final boolean DP_BACKOFF_SUPPORT;
  public static final boolean DP_DEBUG_SUPPORT;
  public static final boolean DP_RATIO_SUPPORT;
  private static final String DQ_SVC_FEATURE = "QCT,ltm,hal";
  public static final boolean ENABLED;
  public static final boolean HAL_SUPPORT;
  private static final boolean HAS_OPTION;
  public static final boolean LTM_SUPPORT;
  public static final boolean OUTDOOR_VISIBILITY_SUPPORT;
  public static final String PLATFORM;
  public static final boolean VIVID_PLUS_SUPPORT;

  static {
    boolean z = false;
    boolean z2 =
        DQ_SVC_FEATURE.contains("QCT")
            || DQ_SVC_FEATURE.contains("LSI")
            || DQ_SVC_FEATURE.contains("MTK");
    ENABLED = z2;
    String substring = z2 ? DQ_SVC_FEATURE.trim().substring(0, 3) : "";
    PLATFORM = substring;
    boolean z3 = DQ_SVC_FEATURE.split(",").length > 1;
    HAS_OPTION = z3;
    if (DQ_SVC_FEATURE.contains("outdoor") || (substring.contains("MTK") && !z3)) {
      z = true;
    }
    OUTDOOR_VISIBILITY_SUPPORT = z;
    ADAPTIVE_SYNC_SUPPORT = DQ_SVC_FEATURE.contains("adaptivesync");
    LTM_SUPPORT = DQ_SVC_FEATURE.contains("ltm");
    HAL_SUPPORT = DQ_SVC_FEATURE.contains("hal");
    VIVID_PLUS_SUPPORT = DQ_SVC_FEATURE.contains("vividplus");
    DP_RATIO_SUPPORT = DQ_SVC_FEATURE.contains("dp_ratio");
    DP_DEBUG_SUPPORT = DQ_SVC_FEATURE.contains("dp_debug");
    DP_BACKOFF_SUPPORT = DQ_SVC_FEATURE.contains("dp_backoff");
  }
}
