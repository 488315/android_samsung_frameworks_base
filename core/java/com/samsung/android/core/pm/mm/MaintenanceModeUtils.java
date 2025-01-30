package com.samsung.android.core.pm.mm;

import android.app.Activity;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.telecom.Logging.Session;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.C4337R;
import com.android.internal.content.NativeLibraryHelper;
import com.samsung.android.share.SemShareConstants;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.nio.charset.StandardCharsets;
import java.util.List;

/* loaded from: classes5.dex */
public class MaintenanceModeUtils {
  private static final String ACTION_LAUNCH_MYFILES_STORAGE_ANALYSIS =
      "com.sec.android.app.myfiles.RUN_STORAGE_ANALYSIS";
  private static final String ACTION_LAUNCH_SMART_SWITCH =
      "com.sec.android.easyMover.LAUNCH_SMART_SWITCH";
  private static final String ACTION_LAUNCH_SMART_SWITCH_AGENT =
      "com.sec.android.easyMover.Agent.action.AUTO_DOWNLOAD";
  static final String ACTION_NOTIFY_CLOUD_BACKUP_CANCELED =
      "com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_CANCELED";
  static final String ACTION_NOTIFY_CLOUD_BACKUP_COMPLETED =
      "com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_COMPLETED";
  static final String ACTION_NOTIFY_CLOUD_BACKUP_NOT_FINISHED =
      "com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_NOT_FINISHED";
  static final String ACTION_NOTIFY_CLOUD_BACKUP_STARTED =
      "com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_STARTED";
  private static final String ACTION_USE_APP_FEATURE_SURVEY =
      "com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY";
  static final String BACKUP_STATUS_CLOUD_BACKED_UP_FAILED = "BACKUP_NON_FINISHED";
  static final String BACKUP_STATUS_CLOUD_BACKED_UP_SUCCEEDED = "BACKUP_COMPLETED";
  static final String BACKUP_STATUS_CLOUD_BACKING_UP = "BACKUP_RUNNING";
  static final String BACKUP_STATUS_CLOUD_NONE = "NONE";
  static final String BACKUP_STATUS_CLOUD_RESTORING = "RESTORE_RUNNING";
  static final String BACKUP_STATUS_NOT_IN_PROGRESS = "NOT_IN_PROGRESS";
  static final String BACKUP_STATUS_SMART_SWITCH_BACKING_UP = "TRUE";
  static final int CLOUD_BACKUP_RETENTION_PERIOD_DEFAULT = 30;
  static final long CLOUD_BACKUP_STATUS_CHECK_DELAY = 30000;
  private static final ComponentName COMPONENT_SMART_SWITCH_AGENT =
      new ComponentName(
          "com.sec.android.easyMover.Agent", "com.sec.android.easyMover.Agent.ServiceActivity");
  static final String EVENT_ID_CLOUD_BACKUP = "7083";
  static final String EVENT_ID_CREATE_LOG = "7070";
  static final String EVENT_ID_EXTERNAL_STORAGE_BACKUP = "7074";
  static final String EVENT_ID_KEEP_BACKUP = "7069";
  static final String EVENT_ID_PAUSE_BACKUP_AND_TURN_ON = "7068";
  static final String EVENT_ID_RESTART = "7071";
  static final String EVENT_ID_TURN_ON_MAINTENANCE_MODE = "7066";
  static final String EVENT_VALUE_ONE = "1";
  static final String EVENT_VALUE_ZERO = "0";
  private static final String EXTRA_SECURE_LOCK_FROM_SEC_NON_BIOMETRICS = "from_sec_non_biometrics";
  private static final String EXTRA_SECURE_LOCK_HIDE_BIOMETRICS_MENU = "hide_biometrics_menu";
  private static final String EXTRA_SMART_SWITCH_EXTERNAL_BNR = "EXTERNAL_BNR";
  public static final String FEATURE_SUPPORT_MAINTENANCE_MODE =
      "com.samsung.feature.support_repair_mode";
  public static final int FLAG_MAINTENANCE_MODE = 524288;
  private static final String LOGGING_TYPE = "ev";
  public static final int MAINTENANCE_MODE_USER_ID = 77;
  private static final float MAX_FONT_SCALE = 1.3f;
  private static final float MAX_PAGE_WIDTH_PERCENT = 0.86f;
  private static final String PACKAGE_CLOUD = "com.samsung.android.scloud";
  private static final String PACKAGE_DEVICE_CARE = "com.samsung.android.lool";
  private static final String PACKAGE_DIAGMON_AGENT = "com.sec.android.diagmonagent";
  public static final String PACKAGE_MOBILE_DOCTOR = "com.samsung.android.app.mobiledoctor";
  private static final String PACKAGE_SMART_SWITCH = "com.sec.android.easyMover";
  public static final String PERMISSION_ACCESS_MAINTENANCE_MODE =
      "com.samsung.android.permission.ACCESS_MAINTENANCE_MODE";
  public static final String PROPERTY_DISALLOW_MAINTENANCE_MODE =
      "persist.sys.disallow_maintenance_mode";
  public static final String PROPERTY_DISALLOW_MAINTENANCE_MODE_LAST_CALLER =
      "persist.sys.disallow_maintenance_mode_last_caller";
  public static final String PROPERTY_IS_IN_MAINTENANCE_MODE = "persist.sys.is_in_maintenance_mode";
  private static final String PROVIDER_CALL_FAILED = "PROVIDER_CALL_FAILED";
  private static final String PROVIDER_CLOUD_ARGUMENT_MAINTENANCE = "maintenance";
  private static final String PROVIDER_CLOUD_AUTHORITY_STATUS_PROVIDER =
      "content://com.samsung.android.scloud.statusprovider";
  private static final String PROVIDER_CLOUD_EXTRA_IS_SKIP_CHECK_SUPPORT = "isSkipCheckSupport";
  private static final String PROVIDER_CLOUD_METHOD_CTB_SUPPORT = "ctb_support";
  private static final String PROVIDER_CLOUD_RESPONSE_KEY_FAIL_REASON = "failReason";
  private static final String PROVIDER_CLOUD_RESPONSE_KEY_INTRO_DESCRIPTION = "intro_description";
  private static final String PROVIDER_CLOUD_RESPONSE_KEY_RETENTION_PERIOD = "retentionPeriod";
  private static final String PROVIDER_CLOUD_RESPONSE_KEY_STATUS = "status";
  private static final String PROVIDER_CLOUD_RESPONSE_KEY_SUPPORT = "support";
  private static final String PROVIDER_CLOUD_RESPONSE_KEY_TARGET_INTENT = "targetIntent";
  private static final String PROVIDER_SMART_SWITCH_URI_IS_RUNNING =
      "content://com.sec.android.easyMover.statusProvider/isRunning";
  public static final String TAG = "MaintenanceMode";
  private static final String TRACKING_ID_DEVICE_CARE = "431-399-4853100";

  public static boolean isMaintenanceModeUser(UserInfo info) {
    return (info == null || (info.flags & 524288) == 0 || info.f54id != 77) ? false : true;
  }

  public static boolean isMaintenanceModeFeature(String name) {
    return FEATURE_SUPPORT_MAINTENANCE_MODE.equals(name);
  }

  public static boolean hasSystemFeature() {
    try {
      return ActivityThread.getPackageManager()
          .hasSystemFeature(FEATURE_SUPPORT_MAINTENANCE_MODE, 0);
    } catch (Exception e) {
      Log.m98i(TAG, "Failed to check feature: " + e.toString());
      return false;
    }
  }

  public static boolean doesMaintenanceModeUserIdExist(Context context) {
    UserManager um = (UserManager) context.getSystemService(UserManager.class);
    List<UserInfo> allUsers = um.getUsers(false, false, false);
    for (UserInfo user : allUsers) {
      if (user.f54id == 77) {
        Log.m98i(TAG, "Maintenance mode ID already exists");
        return true;
      }
    }
    return false;
  }

  public static void setDisallowedSetting(boolean disallow) {
    if (!UserHandle.isSameApp(Binder.getCallingUid(), 1000) && !hasAccessPermission()) {
      return;
    }
    SystemProperties.set(PROPERTY_DISALLOW_MAINTENANCE_MODE, Boolean.toString(disallow));
    String stackTrace = Debug.getCallers(2);
    Log.m98i(TAG, "setDisallowedSetting: " + disallow + ", " + stackTrace);
    if (stackTrace != null) {
      if (stackTrace.getBytes(StandardCharsets.UTF_8).length > 91) {
        stackTrace = new String(stackTrace.getBytes(StandardCharsets.UTF_8), 0, 90);
      }
      try {
        SystemProperties.set(PROPERTY_DISALLOW_MAINTENANCE_MODE_LAST_CALLER, stackTrace);
      } catch (IllegalArgumentException e) {
        Log.m98i(TAG, "Failed to set property: " + e.toString());
      }
    }
  }

  private static boolean hasAccessPermission() {
    try {
      return AppGlobals.getPackageManager()
              .checkUidPermission(
                  "com.samsung.android.permission.ACCESS_MAINTENANCE_MODE", Binder.getCallingUid())
          == 0;
    } catch (Exception e) {
      Log.m98i(TAG, "Failed to check access permission: " + e.toString());
      return false;
    }
  }

  public static boolean isLowOnStorage(Context context) {
    try {
      long usableSpace = Environment.getDataDirectory().getUsableSpace();
      long memoryLowThreshold =
          ((StorageManager) context.getSystemService(StorageManager.class))
              .getStorageLowBytes(Environment.getDataDirectory());
      return usableSpace < memoryLowThreshold;
    } catch (Exception e) {
      Log.m98i(TAG, "Failed to check storage capacity: " + e.toString());
      return false;
    }
  }

  static boolean isTablet() {
    String deviceType = SystemProperties.get("ro.build.characteristics");
    return deviceType != null && deviceType.contains(BnRConstants.DEVICETYPE_TABLET);
  }

  static boolean isFold() {
    return false;
  }

  static boolean isSecureLockSet(Context context) {
    KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
    if (km != null) {
      return km.isDeviceSecure();
    }
    Log.m98i(TAG, "KeyguardManager is unavailable");
    return false;
  }

  static float getFontSize(Context context, int dimenResId) {
    return getFontSize(context, dimenResId, MAX_FONT_SCALE);
  }

  static float getFontSize(Context context, int dimenResId, float maxFontScale) {
    float baseSize = context.getResources().getDimensionPixelSize(dimenResId);
    float currentFontScale = context.getResources().getConfiguration().fontScale;
    if (currentFontScale > maxFontScale) {
      float scaleBase = baseSize / currentFontScale;
      return scaleBase * maxFontScale;
    }
    return baseSize;
  }

  static void configureLayout(
      Activity context,
      Resources r,
      Configuration config,
      boolean isTablet,
      boolean isFold,
      int resIdLayoutPortrait,
      int resIdLayoutLandscape,
      int resIdContainer) {
    boolean isLandscape = config.orientation == 2;
    boolean needToAdjustContainerSize = false;
    if (isTablet) {
      needToAdjustContainerSize = true;
      context.setContentView(resIdLayoutPortrait);
    } else if (isFold) {
      if (config.semDisplayDeviceType == 5) {
        configureLayoutConsideringFullScreen(
            context, isLandscape, resIdLayoutPortrait, resIdLayoutLandscape);
      } else {
        needToAdjustContainerSize = true;
        configureLayoutConsideringFullScreen(
            context, false, resIdLayoutPortrait, resIdLayoutLandscape);
      }
    } else {
      configureLayoutConsideringFullScreen(
          context, isLandscape, resIdLayoutPortrait, resIdLayoutLandscape);
    }
    if (!needToAdjustContainerSize) {
      return;
    }
    int currentWindowWidth =
        context.getWindowManager().getCurrentWindowMetrics().getBounds().width();
    int currentWindowHeight =
        context.getWindowManager().getCurrentWindowMetrics().getBounds().height();
    int breakpointLargeWidth =
        r.getDimensionPixelSize(C4337R.dimen.maintenance_mode_breakpoint_screen_width_large);
    int breakpointMiddleWidth =
        r.getDimensionPixelSize(C4337R.dimen.maintenance_mode_breakpoint_screen_width_middle);
    int breakpointMiddleHeight =
        r.getDimensionPixelSize(C4337R.dimen.maintenance_mode_breakpoint_screen_height_middle);
    int maxWidth = 0;
    if (currentWindowWidth >= breakpointLargeWidth) {
      maxWidth = r.getDimensionPixelSize(C4337R.dimen.maintenance_mode_page_max_width);
    } else if (currentWindowWidth >= breakpointMiddleWidth
        && currentWindowHeight > breakpointMiddleHeight) {
      maxWidth = (int) (currentWindowWidth * MAX_PAGE_WIDTH_PERCENT);
    }
    if (maxWidth > 0) {
      View container = context.findViewById(resIdContainer);
      ViewGroup.LayoutParams containerParams = container.getLayoutParams();
      containerParams.width = maxWidth;
      container.setLayoutParams(containerParams);
    }
  }

  private static void configureLayoutConsideringFullScreen(
      Activity context, boolean isLandscape, int resIdLayoutPortrait, int resIdLayoutLandscape) {
    if (isLandscape) {
      context.setContentView(resIdLayoutLandscape);
      context.getWindow().addFlags(1024);
    } else {
      context.setContentView(resIdLayoutPortrait);
      context.getWindow().clearFlags(1024);
    }
  }

  static void startActivityToSetSecureLock(Context activityContext) {
    Intent intent = new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD);
    intent.putExtra(EXTRA_SECURE_LOCK_HIDE_BIOMETRICS_MENU, true);
    intent.putExtra(EXTRA_SECURE_LOCK_FROM_SEC_NON_BIOMETRICS, true);
    startActivity(activityContext, intent);
  }

  static void startCloudActivity(Context context) {
    Bundle res = callCloudProvider(context, true);
    if (res == null) {
      return;
    }
    try {
      Intent targetIntent =
          (Intent) res.getParcelable(PROVIDER_CLOUD_RESPONSE_KEY_TARGET_INTENT, Intent.class);
      if (targetIntent == null) {
        Log.m98i(TAG, "Failed to start SCloud: targetIntent is null");
      } else {
        targetIntent.setFlags(268435456);
        startActivity(context, targetIntent);
      }
    } catch (Exception e) {
      Log.m98i(TAG, "Failed to getParcelable: " + e.toString());
    }
  }

  static void startSmartSwitchActivity(Context context) {
    Intent intent;
    if (isPackageInstalled(context, PACKAGE_SMART_SWITCH)) {
      intent = new Intent(ACTION_LAUNCH_SMART_SWITCH);
    } else {
      intent = new Intent(ACTION_LAUNCH_SMART_SWITCH_AGENT);
      intent.setComponent(COMPONENT_SMART_SWITCH_AGENT);
    }
    intent.putExtra(EXTRA_SMART_SWITCH_EXTERNAL_BNR, true);
    intent.setFlags(268435456);
    startActivity(context, intent);
  }

  static void startMyFilesActivity(Activity activity) {
    try {
      Intent intent = new Intent(ACTION_LAUNCH_MYFILES_STORAGE_ANALYSIS);
      activity.startActivityForResult(intent, 0);
    } catch (Exception e) {
      Log.m98i(TAG, "Failed to start: " + e.toString());
    }
  }

  private static void startActivity(Context context, Intent intent) {
    try {
      context.startActivity(intent);
    } catch (Exception e) {
      Log.m98i(TAG, "Failed to start: " + e.toString());
    }
  }

  static class CloudInfo {
    String introDescription;
    boolean isSupported;
    int retentionPeriod;

    CloudInfo(boolean isSupported, int retentionPeriod, String introDescription) {
      this.isSupported = isSupported;
      this.retentionPeriod = retentionPeriod;
      this.introDescription = introDescription;
    }
  }

  static CloudInfo checkCloudBackupSupport(Context context) {
    if (!isPackageInstalled(context, PACKAGE_CLOUD)) {
      Log.m98i(TAG, "SCloud is not installed");
      return new CloudInfo(false, 30, null);
    }
    Bundle res = callCloudProvider(context, false);
    return new CloudInfo(
        isCloudBackupSupported(res),
        getCloudBackupRetentionPeriod(res),
        getCloudBackupIntroDescription(res));
  }

  private static boolean isCloudBackupSupported(Bundle bundle) {
    if (bundle == null) {
      return false;
    }
    boolean isSupported = bundle.getBoolean("support", false);
    Log.m98i(TAG, "Cloud backup support: " + isSupported);
    return isSupported;
  }

  private static int getCloudBackupRetentionPeriod(Bundle bundle) {
    if (bundle == null) {
      return 30;
    }
    int retentionPeriod = bundle.getInt(PROVIDER_CLOUD_RESPONSE_KEY_RETENTION_PERIOD, 0);
    Log.m98i(TAG, "Cloud backup retention period: " + retentionPeriod);
    if (retentionPeriod != 0) {
      return retentionPeriod;
    }
    return 30;
  }

  private static String getCloudBackupIntroDescription(Bundle bundle) {
    if (bundle == null) {
      return null;
    }
    String introDescription = bundle.getString(PROVIDER_CLOUD_RESPONSE_KEY_INTRO_DESCRIPTION);
    Log.m98i(TAG, "Cloud backup intro description: " + introDescription);
    return introDescription;
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  static String getStatusOfBackupInProgress(Context context) {
    char c;
    String cloudBackupStatus = getCloudBackupStatus(context);
    switch (cloudBackupStatus.hashCode()) {
      case -1070966578:
        if (cloudBackupStatus.equals(BACKUP_STATUS_CLOUD_RESTORING)) {
          c = 2;
          break;
        }
        c = 65535;
        break;
      case -739684063:
        if (cloudBackupStatus.equals(BACKUP_STATUS_CLOUD_BACKED_UP_FAILED)) {
          c = 1;
          break;
        }
        c = 65535;
        break;
      case -226439134:
        if (cloudBackupStatus.equals(BACKUP_STATUS_CLOUD_BACKING_UP)) {
          c = 0;
          break;
        }
        c = 65535;
        break;
      default:
        c = 65535;
        break;
    }
    switch (c) {
      case 0:
      case 1:
      case 2:
        return cloudBackupStatus;
      default:
        String smartSwitchBackupStatus = getSmartSwitchBackupStatus(context);
        if (BACKUP_STATUS_SMART_SWITCH_BACKING_UP.equals(smartSwitchBackupStatus)) {
          return BACKUP_STATUS_SMART_SWITCH_BACKING_UP;
        }
        return BACKUP_STATUS_NOT_IN_PROGRESS;
    }
  }

  static String getCloudBackupStatus(Context context) {
    Bundle res = callCloudProvider(context, true);
    if (res == null) {
      return PROVIDER_CALL_FAILED;
    }
    String status = res.getString("status");
    Log.m98i(TAG, "Cloud backup status: " + status);
    return status != null ? status : PROVIDER_CALL_FAILED;
  }

  static String getSmartSwitchBackupStatus(Context context) {
    String status = getType(context, PROVIDER_SMART_SWITCH_URI_IS_RUNNING);
    Log.m98i(TAG, "SmartSwitch backup status: " + status);
    return status != null ? status : PROVIDER_CALL_FAILED;
  }

  private static boolean isPackageInstalled(Context context, String packageName) {
    try {
      PackageManager pm = context.getPackageManager();
      return pm.getApplicationInfo(packageName, 0) != null;
    } catch (Exception e) {
      Log.m98i(TAG, e.toString());
      return false;
    }
  }

  private static Bundle callCloudProvider(Context context, boolean skipCheckSupport) {
    Bundle extras = new Bundle();
    extras.putBoolean(PROVIDER_CLOUD_EXTRA_IS_SKIP_CHECK_SUPPORT, skipCheckSupport);
    Bundle res =
        call(
            context,
            PROVIDER_CLOUD_AUTHORITY_STATUS_PROVIDER,
            PROVIDER_CLOUD_METHOD_CTB_SUPPORT,
            PROVIDER_CLOUD_ARGUMENT_MAINTENANCE,
            extras);
    if (res == null) {
      Log.m98i(TAG, "Failed to call: Response is null");
      return null;
    }
    String failReason = res.getString(PROVIDER_CLOUD_RESPONSE_KEY_FAIL_REASON);
    if (failReason != null) {
      Log.m98i(TAG, "Failed to call, failReason: " + failReason);
      return null;
    }
    return res;
  }

  private static Bundle call(
      Context context, String authority, String method, String arg, Bundle extras) {
    try {
      return context.getContentResolver().call(Uri.parse(authority), method, arg, extras);
    } catch (Exception e) {
      Log.m98i(TAG, "Failed to call: " + e.toString());
      return null;
    }
  }

  static String getType(Context context, String uriString) {
    try {
      return context.getContentResolver().getType(Uri.parse(uriString));
    } catch (Exception e) {
      Log.m98i(TAG, "Failed to getType: " + e.toString());
      return PROVIDER_CALL_FAILED;
    }
  }

  static void sendLoggingDataToSA(Context context, String eventId, String eventValue) {
    try {
      Bundle bundle = new Bundle();
      bundle.putString(SemShareConstants.DMA_SURVEY_FEATURE_TRACKING_ID, TRACKING_ID_DEVICE_CARE);
      bundle.putString("feature", eventId);
      bundle.putString(SemShareConstants.SURVERY_EXTRA_OWN_PACKAGE, PACKAGE_DEVICE_CARE);
      bundle.putString("type", "ev");
      if (eventValue != null) {
        bundle.putString("value", eventValue);
      }
      Intent intent = new Intent("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY");
      intent.setPackage("com.sec.android.diagmonagent");
      intent.putExtras(bundle);
      Log.m94d(
          TAG,
          Session.EXTERNAL_INDICATOR
              + eventId
              + (eventValue == null ? "" : NativeLibraryHelper.CLEAR_ABI_OVERRIDE + eventValue));
      context.sendBroadcast(intent);
    } catch (Exception e) {
    }
  }
}
