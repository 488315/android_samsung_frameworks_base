package com.android.server.am.mars.database;

import android.net.Uri;
import android.os.IInstalld;

/* loaded from: classes.dex */
public abstract class FASTableContract {
  public static final Uri SMART_MGR_FORCED_APP_STANDBY_URI =
      Uri.parse("content://com.sec.android.sdhms.fasprovider/ForcedAppStandby");
  public static final Uri DC_AUTHORITY_FAS_URI =
      Uri.parse("content://com.samsung.android.sm.battery.bridge");
  public static final String[] preBattetyUsageProjection = {"prevCurrent"};
  public static final String[] disableReasonProjection = {"disableReason"};
  public static final String[] FASQueryProjectionV1 = {
    "package_name",
    "uid",
    "mode",
    "new",
    "reason",
    "extras",
    "resetTime",
    "packageType",
    "level",
    "disableType",
    "disableResetTime"
  };
  public static final String[] FASQueryProjectionV2 = {
    "package_name",
    "uid",
    "mode",
    "new",
    "reason",
    "extras",
    "resetTime",
    "packageType",
    "level",
    "disableType",
    "disableResetTime",
    "prevCurrent"
  };
  public static final String[] FASQueryProjectionV3 = {
    "package_name",
    "uid",
    "mode",
    "new",
    "reason",
    "extras",
    "resetTime",
    "packageType",
    "level",
    "disableType",
    "disableResetTime",
    "prevCurrent",
    "disableReason"
  };
  public static final Uri SMART_MGR_SETTINGS_URI =
      Uri.parse("content://com.samsung.android.sm/settings");
  public static final Uri SMART_MGR_DEFAULT_ALLOWED_LIST_URI =
      Uri.parse("content://com.samsung.android.sm/DefaultAllowedList");
  public static final Uri SMART_MGR_APP_STARTUP_URI =
      Uri.parse("content://com.samsung.android.sm.appstart/appstart_record");
  public static final Uri SMART_MGR_FREEZE_EXCLUDE_LIST_URI =
      Uri.parse("content://com.samsung.android.sm/FreezeExcludeList");

  public static String convertDisableReasonToDBValue(int i) {
    return i != 0
        ? i != 1
            ? i != 2
                ? i != 4
                    ? i != 8
                        ? i != 16
                            ? i != 32 ? "null" : "added_from_anomaly_manual"
                            : "added_from_mars_auto_specific"
                        : "deleted_from_user_manual"
                    : "deleted_from_mars_auto"
                : "added_from_user_manual"
            : "added_from_mars_auto"
        : "default";
  }

  public static String convertFASTypeToReason(int i) {
    return i != 0
        ? i != 1
            ? i != 2
                ? i != 4
                    ? i != 8
                        ? i != 16
                            ? i != 32
                                ? i != 64
                                    ? i != 128
                                        ? i != 256
                                            ? i != 512
                                                ? i != 2048
                                                    ? i != 4096 ? "null" : "deleted_from_unknown"
                                                    : "deleted_from_policy_in_china"
                                                : "deleted_from_post_o"
                                            : "deleted_from_user_manual"
                                        : "deleted_from_mars_auto"
                                    : "added_from_unknown"
                                : "added_from_policy_in_china"
                            : "added_from_pre_o"
                        : "added_from_anomaly_manual"
                    : "added_from_anomaly_auto"
                : "added_from_user_manual"
            : "added_from_mars_auto"
        : "default";
  }

  public static String convertStateToDBExtrasValue(int i) {
    return i != 2 ? i != 4 ? i != 8 ? i != 16 ? "0" : "4" : "3" : "2" : "1";
  }

  public static int convertFASReasonToValue(String str) {
    if (str == null) {
      return -1;
    }
    if ("default".equals(str)) {
      return 0;
    }
    if ("added_from_mars_auto".equals(str)) {
      return 1;
    }
    if ("added_from_user_manual".equals(str)) {
      return 2;
    }
    if ("added_from_anomaly_auto".equals(str)) {
      return 4;
    }
    if ("added_from_anomaly_manual".equals(str)) {
      return 8;
    }
    if ("added_from_pre_o".equals(str)) {
      return 16;
    }
    if ("added_from_policy_in_china".equals(str)) {
      return 32;
    }
    if ("added_from_unknown".equals(str)) {
      return 64;
    }
    if ("deleted_from_mars_auto".equals(str)) {
      return 128;
    }
    if ("deleted_from_user_manual".equals(str)) {
      return 256;
    }
    if ("deleted_from_post_o".equals(str)) {
      return 512;
    }
    if ("deleted_from_policy_in_china".equals(str)) {
      return IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES;
    }
    if ("deleted_from_unknown".equals(str)) {
      return IInstalld.FLAG_USE_QUOTA;
    }
    return -1;
  }

  public static int convertDBValueToState(String str) {
    if (str == null || "0".equals(str)) {
      return 1;
    }
    if ("1".equals(str)) {
      return 2;
    }
    if ("2".equals(str)) {
      return 4;
    }
    if ("3".equals(str)) {
      return 8;
    }
    return "4".equals(str) ? 16 : 1;
  }

  public static int convertDBValueToDisableReason(String str) {
    if ("default".equals(str)) {
      return 0;
    }
    if ("added_from_mars_auto".equals(str)) {
      return 1;
    }
    if ("added_from_user_manual".equals(str)) {
      return 2;
    }
    if ("deleted_from_mars_auto".equals(str)) {
      return 4;
    }
    if ("deleted_from_user_manual".equals(str)) {
      return 8;
    }
    if ("added_from_mars_auto_specific".equals(str)) {
      return 16;
    }
    return "added_from_anomaly_manual".equals(str) ? 32 : -1;
  }
}
