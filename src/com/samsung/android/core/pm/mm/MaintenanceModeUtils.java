package com.samsung.android.core.pm.mm;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.hardware.biometrics.BiometricPrompt;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Debug;
import android.os.Environment;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.telecom.Logging.Session;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import com.android.internal.R;
import com.android.internal.content.NativeLibraryHelper;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.share.SemShareConstants;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class MaintenanceModeUtils {
    private static final String ACTION_LAUNCH_MYFILES_STORAGE_ANALYSIS = "com.sec.android.app.myfiles.RUN_STORAGE_ANALYSIS";
    private static final String ACTION_LAUNCH_SMART_SWITCH = "com.sec.android.easyMover.LAUNCH_SMART_SWITCH";
    private static final String ACTION_LAUNCH_SMART_SWITCH_AGENT = "com.sec.android.easyMover.Agent.action.AUTO_DOWNLOAD";
    static final String ACTION_NOTIFY_CLOUD_BACKUP_CANCELED = "com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_CANCELED";
    static final String ACTION_NOTIFY_CLOUD_BACKUP_COMPLETED = "com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_COMPLETED";
    static final String ACTION_NOTIFY_CLOUD_BACKUP_NOT_FINISHED = "com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_NOT_FINISHED";
    static final String ACTION_NOTIFY_CLOUD_BACKUP_STARTED = "com.samsung.android.scloud.temporarybackup.NOTIFY_BACKUP_STARTED";
    private static final String ACTION_USE_APP_FEATURE_SURVEY = "com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY";
    static final String BACKUP_STATUS_CLOUD_BACKED_UP_FAILED = "BACKUP_NON_FINISHED";
    static final String BACKUP_STATUS_CLOUD_BACKED_UP_SUCCEEDED = "BACKUP_COMPLETED";
    static final String BACKUP_STATUS_CLOUD_BACKING_UP = "BACKUP_RUNNING";
    static final String BACKUP_STATUS_CLOUD_NONE = "NONE";
    static final String BACKUP_STATUS_CLOUD_RESTORING = "RESTORE_RUNNING";
    static final String BACKUP_STATUS_NOT_IN_PROGRESS = "NOT_IN_PROGRESS";
    static final String BACKUP_STATUS_SMART_SWITCH_BACKING_UP = "TRUE";
    static final int CLOUD_BACKUP_RETENTION_PERIOD_DEFAULT = 30;
    static final long CLOUD_BACKUP_STATUS_CHECK_DELAY = 30000;
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
    public static final String EXTRA_USER_CONSENT_ABOUT_CREATING_LOG = "user_consent_about_creating_log";
    public static final String FEATURE_SUPPORT_MAINTENANCE_MODE = "com.samsung.feature.support_repair_mode";
    public static final int FLAG_MAINTENANCE_MODE = 524288;
    private static final String LOGGING_TYPE = "ev";
    public static final int MAINTENANCE_MODE_USER_ID = 77;
    private static final float MAX_FONT_SCALE = 1.3f;
    private static final float MAX_PAGE_WIDTH_PERCENT = 0.86f;
    private static final int MAX_POWER_SAVING_MODE_ENABLED = 1;
    private static final String NEW_DEX_MODE_ENABLED = "1";
    private static final String NEW_DEX_MODE_KEY = "new_dex";
    private static final String PACKAGE_CLOUD = "com.samsung.android.scloud";
    private static final String PACKAGE_DEVICE_CARE = "com.samsung.android.lool";
    private static final String PACKAGE_DIAGMON_AGENT = "com.sec.android.diagmonagent";
    public static final String PACKAGE_MOBILE_DOCTOR = "com.samsung.android.app.mobiledoctor";
    private static final String PACKAGE_SMART_SWITCH = "com.sec.android.easyMover";
    public static final String PERMISSION_ACCESS_MAINTENANCE_MODE = "com.samsung.android.permission.ACCESS_MAINTENANCE_MODE";
    public static final String PROPERTY_DISALLOW_MAINTENANCE_MODE = "persist.sys.disallow_maintenance_mode";
    public static final String PROPERTY_DISALLOW_MAINTENANCE_MODE_LAST_CALLER = "persist.sys.disallow_maintenance_mode_last_caller";
    public static final String PROPERTY_IS_IN_MAINTENANCE_MODE = "persist.sys.is_in_maintenance_mode";
    private static final String PROVIDER_CALL_FAILED = "PROVIDER_CALL_FAILED";
    private static final String PROVIDER_CLOUD_ARGUMENT_MAINTENANCE = "maintenance";
    private static final String PROVIDER_CLOUD_AUTHORITY_STATUS_PROVIDER = "content://com.samsung.android.scloud.statusprovider";
    private static final String PROVIDER_CLOUD_EXTRA_IS_SKIP_CHECK_SUPPORT = "isSkipCheckSupport";
    private static final String PROVIDER_CLOUD_METHOD_CTB_SUPPORT = "ctb_support";
    private static final String PROVIDER_CLOUD_RESPONSE_KEY_FAIL_REASON = "failReason";
    private static final String PROVIDER_CLOUD_RESPONSE_KEY_INTRO_DESCRIPTION = "intro_description";
    private static final String PROVIDER_CLOUD_RESPONSE_KEY_RETENTION_PERIOD = "retentionPeriod";
    private static final String PROVIDER_CLOUD_RESPONSE_KEY_STATUS = "status";
    private static final String PROVIDER_CLOUD_RESPONSE_KEY_SUPPORT = "support";
    private static final String PROVIDER_CLOUD_RESPONSE_KEY_TARGET_INTENT = "targetIntent";
    private static final String PROVIDER_SMART_SWITCH_URI_IS_RUNNING = "content://com.sec.android.easyMover.statusProvider/isRunning";
    public static final String TAG = "MaintenanceMode";
    private static final String TRACKING_ID_DEVICE_CARE = "431-399-4853100";
    static final int UNSUPPORTED_REASON_DEX_MODE = 4;
    static final int UNSUPPORTED_REASON_INTERNAL_ALREADY_EXISTS = -1;
    static final int UNSUPPORTED_REASON_MAX_POWER_SAVING_MODE = 5;
    static final int UNSUPPORTED_REASON_NONE = 0;
    static final int UNSUPPORTED_REASON_NOT_IN_OWNER_USER = 2;
    static final int UNSUPPORTED_REASON_NOT_SUPPORTED_ON_DEVICE = 1;
    static final int UNSUPPORTED_REASON_NO_ADD_USER = 3;
    public static final String USER_TYPE_FULL_MAINTENANCE_MODE = "com.samsung.android.os.usertype.full.MAINTENANCE_MODE";
    private static final ComponentName COMPONENT_SMART_SWITCH_AGENT = new ComponentName("com.sec.android.easyMover.Agent", "com.sec.android.easyMover.Agent.ServiceActivity");
    public static boolean sUserConsentAboutCreatingLog = false;

    static boolean isFold() {
        return false;
    }

    public static boolean isMaintenanceModeUser(UserInfo userInfo) {
        if (userInfo == null) {
            return false;
        }
        return ((userInfo.flags & 524288) != 0 || isUserTypeMaintenanceMode(userInfo.userType)) && userInfo.id == 77;
    }

    public static boolean isUserTypeMaintenanceMode(String str) {
        return USER_TYPE_FULL_MAINTENANCE_MODE.equals(str);
    }

    public static boolean isMaintenanceModeFeature(String str) {
        return FEATURE_SUPPORT_MAINTENANCE_MODE.equals(str);
    }

    public static boolean hasSystemFeature() {
        try {
            return ActivityThread.getPackageManager().hasSystemFeature(FEATURE_SUPPORT_MAINTENANCE_MODE, 0);
        } catch (Exception e) {
            Log.i(TAG, "Failed to check feature: " + e.toString());
            return false;
        }
    }

    public static boolean doesMaintenanceModeUserIdExist(Context context) {
        Iterator<UserInfo> it = ((UserManager) context.getSystemService(UserManager.class)).getUsers(false, false, false).iterator();
        while (it.hasNext()) {
            if (it.next().id == 77) {
                Log.i(TAG, "Maintenance mode ID already exists");
                return true;
            }
        }
        return false;
    }

    public static void setDisallowedSetting(boolean z) {
        if (UserHandle.isSameApp(Binder.getCallingUid(), 1000) || hasAccessPermission()) {
            SystemProperties.set(PROPERTY_DISALLOW_MAINTENANCE_MODE, Boolean.toString(z));
            String callers = Debug.getCallers(2);
            Log.i(TAG, "setDisallowedSetting: " + z + ", " + callers);
            if (callers != null) {
                if (callers.getBytes(StandardCharsets.UTF_8).length > 91) {
                    callers = new String(callers.getBytes(StandardCharsets.UTF_8), 0, 90);
                }
                try {
                    SystemProperties.set(PROPERTY_DISALLOW_MAINTENANCE_MODE_LAST_CALLER, callers);
                } catch (IllegalArgumentException e) {
                    Log.i(TAG, "Failed to set property: " + e.toString());
                }
            }
        }
    }

    private static boolean hasAccessPermission() {
        try {
            return AppGlobals.getPackageManager().checkUidPermission("com.samsung.android.permission.ACCESS_MAINTENANCE_MODE", Binder.getCallingUid()) == 0;
        } catch (Exception e) {
            Log.i(TAG, "Failed to check access permission: " + e.toString());
            return false;
        }
    }

    public static boolean isLowOnStorage(Context context) {
        try {
            return Environment.getDataDirectory().getUsableSpace() < ((StorageManager) context.getSystemService(StorageManager.class)).getStorageLowBytes(Environment.getDataDirectory());
        } catch (Exception e) {
            Log.i(TAG, "Failed to check storage capacity: " + e.toString());
            return false;
        }
    }

    public static void setUserConsentAboutCreatingLog(boolean z) {
        sUserConsentAboutCreatingLog = z;
    }

    public static boolean getUserConsentAboutCreatingLog() {
        return sUserConsentAboutCreatingLog;
    }

    static boolean isTablet() {
        String str = SystemProperties.get("ro.build.characteristics");
        return str != null && str.contains(BnRConstants.DEVICETYPE_TABLET);
    }

    static int checkRequiredConditions(Context context, boolean z) {
        if (!hasSystemFeature()) {
            return 1;
        }
        if (ActivityManager.getCurrentUser() != 0) {
            return 2;
        }
        if (hasNoAddUserRestriction(context)) {
            return 3;
        }
        if (isDexMode(context)) {
            return 4;
        }
        if (isMaxPowerSavingMode(context)) {
            return 5;
        }
        return (z && doesMaintenanceModeUserIdExist(context)) ? -1 : 0;
    }

    private static boolean hasNoAddUserRestriction(Context context) {
        return ((UserManager) context.getSystemService(UserManager.class)).hasUserRestrictionForUser(UserManager.DISALLOW_ADD_USER, UserHandle.SYSTEM);
    }

    private static boolean isDexMode(Context context) {
        SemDesktopModeState desktopModeState;
        try {
        } catch (Exception e) {
            Log.i(TAG, "Failed to check Dex mode: " + e.toString());
        }
        if ("1".equals(Settings.System.getString(context.getContentResolver(), NEW_DEX_MODE_KEY))) {
            return true;
        }
        SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) context.getSystemService(Context.SEM_DESKTOP_MODE_SERVICE);
        if (semDesktopModeManager != null && (desktopModeState = semDesktopModeManager.getDesktopModeState()) != null) {
            if (desktopModeState.enabled != 4) {
                if (desktopModeState.enabled != 3) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private static boolean isMaxPowerSavingMode(Context context) {
        try {
        } catch (Exception e) {
            Log.i(TAG, "Failed to check MPSM: " + e.toString());
        }
        return Settings.System.getInt(context.getContentResolver(), Settings.System.SEM_MINIMAL_BATTERY_USE, 0) == 1;
    }

    static boolean isSecureLockSet(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager != null) {
            return keyguardManager.isDeviceSecure();
        }
        Log.i(TAG, "KeyguardManager is unavailable");
        return false;
    }

    static void confirmSecureLock(Context context, final Runnable runnable) {
        BiometricPrompt.Builder builder = new BiometricPrompt.Builder(context);
        builder.setUseDefaultTitle();
        builder.setAllowedAuthenticators(32768);
        builder.build().authenticateUser(new CancellationSignal(), context.getMainExecutor(), new BiometricPrompt.AuthenticationCallback() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeUtils.1
            @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback, android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
            public void onAuthenticationError(int i, CharSequence charSequence) {
                super.onAuthenticationError(i, charSequence);
            }

            @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult authenticationResult) {
                super.onAuthenticationSucceeded(authenticationResult);
                runnable.run();
            }

            @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback, android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        }, 0);
    }

    static float getFontSize(Context context, int i) {
        return getFontSize(context, i, MAX_FONT_SCALE);
    }

    static float getFontSize(Context context, int i, float f) {
        float dimensionPixelSize = context.getResources().getDimensionPixelSize(i);
        float f2 = context.getResources().getConfiguration().fontScale;
        return f2 > f ? (dimensionPixelSize / f2) * f : dimensionPixelSize;
    }

    static void configureLayout(Activity activity, Resources resources, Configuration configuration, boolean z, boolean z2, int i, int i2, int i3) {
        boolean z3 = true;
        int dimensionPixelSize = 0;
        boolean z4 = configuration.orientation == 2;
        if (z) {
            activity.setContentView(i);
        } else if (!z2 || configuration.semDisplayDeviceType == 5) {
            configureLayoutConsideringFullScreen(activity, z4, i, i2);
            z3 = false;
        } else {
            configureLayoutConsideringFullScreen(activity, false, i, i2);
        }
        activity.findViewById(R.id.maintenance_mode_outermost_container).setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeUtils$$ExternalSyntheticLambda0
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                return MaintenanceModeUtils.lambda$configureLayout$0(view, windowInsets);
            }
        });
        setSystemBarsAppearanceIfNeeded(activity);
        if (z3) {
            int iWidth = activity.getWindowManager().getCurrentWindowMetrics().getBounds().width();
            int iHeight = activity.getWindowManager().getCurrentWindowMetrics().getBounds().height();
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.maintenance_mode_breakpoint_screen_width_large);
            int dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.maintenance_mode_breakpoint_screen_width_middle);
            int dimensionPixelSize4 = resources.getDimensionPixelSize(R.dimen.maintenance_mode_breakpoint_screen_height_middle);
            if (iWidth >= dimensionPixelSize2) {
                dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.maintenance_mode_page_max_width);
            } else if (iWidth >= dimensionPixelSize3 && iHeight > dimensionPixelSize4) {
                dimensionPixelSize = (int) (iWidth * MAX_PAGE_WIDTH_PERCENT);
            }
            if (dimensionPixelSize > 0) {
                View viewFindViewById = activity.findViewById(i3);
                ViewGroup.LayoutParams layoutParams = viewFindViewById.getLayoutParams();
                layoutParams.width = dimensionPixelSize;
                viewFindViewById.setLayoutParams(layoutParams);
            }
        }
    }

    static /* synthetic */ WindowInsets lambda$configureLayout$0(View view, WindowInsets windowInsets) {
        Insets insets = windowInsets.getInsets(WindowInsets.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return WindowInsets.CONSUMED;
    }

    private static void configureLayoutConsideringFullScreen(Activity activity, boolean z, int i, int i2) {
        if (z) {
            activity.setContentView(i2);
            activity.getWindow().addFlags(1024);
        } else {
            activity.setContentView(i);
            activity.getWindow().clearFlags(1024);
        }
    }

    private static void setSystemBarsAppearanceIfNeeded(Activity activity) {
        WindowInsetsController insetsController;
        try {
            if (!isOpenThemeSet(activity) || (insetsController = activity.getWindow().getInsetsController()) == null) {
                return;
            }
            insetsController.setSystemBarsAppearance(isLightStatusBar(activity) ? 8 : 0, 8);
        } catch (Exception e) {
            Log.i(TAG, "Failed to set systemBars appearance: " + e.toString());
        }
    }

    private static boolean isOpenThemeSet(Context context) {
        return context.getResources().getAssets().getSamsungThemeOverlays().size() > 0;
    }

    private static boolean isLightStatusBar(Context context) {
        return context.getResources().getBoolean(R.bool.sem_window_light_status_bar);
    }

    static void startActivityToSetSecureLock(Context context) {
        Intent intent = new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD);
        intent.putExtra(EXTRA_SECURE_LOCK_HIDE_BIOMETRICS_MENU, true);
        intent.putExtra(EXTRA_SECURE_LOCK_FROM_SEC_NON_BIOMETRICS, true);
        startActivity(context, intent);
    }

    static void startCloudActivity(Context context) {
        Bundle bundleCallCloudProvider = callCloudProvider(context, true);
        if (bundleCallCloudProvider == null) {
            return;
        }
        try {
            Intent intent = (Intent) bundleCallCloudProvider.getParcelable(PROVIDER_CLOUD_RESPONSE_KEY_TARGET_INTENT, Intent.class);
            if (intent == null) {
                Log.i(TAG, "Failed to start SCloud: targetIntent is null");
            } else {
                startActivity(context, intent);
            }
        } catch (Exception e) {
            Log.i(TAG, "Failed to getParcelable: " + e.toString());
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
            activity.startActivityForResult(new Intent(ACTION_LAUNCH_MYFILES_STORAGE_ANALYSIS), 0);
        } catch (Exception e) {
            Log.i(TAG, "Failed to start: " + e.toString());
        }
    }

    private static void startActivity(Context context, Intent intent) {
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Log.i(TAG, "Failed to start: " + e.toString());
        }
    }

    static class CloudInfo {
        String introDescription;
        boolean isSupported;
        int retentionPeriod;

        CloudInfo(boolean z, int i, String str) {
            this.isSupported = z;
            this.retentionPeriod = i;
            this.introDescription = str;
        }
    }

    static CloudInfo checkCloudBackupSupport(Context context) {
        if (!isPackageInstalled(context, PACKAGE_CLOUD)) {
            Log.i(TAG, "SCloud is not installed");
            return new CloudInfo(false, 30, null);
        }
        Bundle bundleCallCloudProvider = callCloudProvider(context, false);
        return new CloudInfo(isCloudBackupSupported(bundleCallCloudProvider), getCloudBackupRetentionPeriod(bundleCallCloudProvider), getCloudBackupIntroDescription(bundleCallCloudProvider));
    }

    private static boolean isCloudBackupSupported(Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        boolean z = bundle.getBoolean("support", false);
        Log.i(TAG, "Cloud backup support: " + z);
        return z;
    }

    private static int getCloudBackupRetentionPeriod(Bundle bundle) {
        if (bundle == null) {
            return 30;
        }
        int i = bundle.getInt(PROVIDER_CLOUD_RESPONSE_KEY_RETENTION_PERIOD, 0);
        Log.i(TAG, "Cloud backup retention period: " + i);
        if (i != 0) {
            return i;
        }
        return 30;
    }

    private static String getCloudBackupIntroDescription(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String string = bundle.getString(PROVIDER_CLOUD_RESPONSE_KEY_INTRO_DESCRIPTION);
        Log.i(TAG, "Cloud backup intro description: " + string);
        return string;
    }

    static String getStatusOfBackupInProgress(Context context) {
        String cloudBackupStatus;
        cloudBackupStatus = getCloudBackupStatus(context);
        cloudBackupStatus.hashCode();
        switch (cloudBackupStatus) {
            case "RESTORE_RUNNING":
            case "BACKUP_NON_FINISHED":
            case "BACKUP_RUNNING":
                return cloudBackupStatus;
            default:
                if (BACKUP_STATUS_SMART_SWITCH_BACKING_UP.equals(getSmartSwitchBackupStatus(context))) {
                    return BACKUP_STATUS_SMART_SWITCH_BACKING_UP;
                }
                return BACKUP_STATUS_NOT_IN_PROGRESS;
        }
    }

    static String getCloudBackupStatus(Context context) {
        Bundle bundleCallCloudProvider = callCloudProvider(context, true);
        if (bundleCallCloudProvider == null) {
            return PROVIDER_CALL_FAILED;
        }
        String string = bundleCallCloudProvider.getString("status");
        Log.i(TAG, "Cloud backup status: " + string);
        return string != null ? string : PROVIDER_CALL_FAILED;
    }

    static String getSmartSwitchBackupStatus(Context context) {
        String type = getType(context, PROVIDER_SMART_SWITCH_URI_IS_RUNNING);
        Log.i(TAG, "SmartSwitch backup status: " + type);
        return type != null ? type : PROVIDER_CALL_FAILED;
    }

    private static boolean isPackageInstalled(Context context, String str) {
        try {
            return context.getPackageManager().getApplicationInfo(str, 0) != null;
        } catch (Exception e) {
            Log.i(TAG, e.toString());
            return false;
        }
    }

    private static Bundle callCloudProvider(Context context, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(PROVIDER_CLOUD_EXTRA_IS_SKIP_CHECK_SUPPORT, z);
        Bundle bundleCall = call(context, PROVIDER_CLOUD_AUTHORITY_STATUS_PROVIDER, PROVIDER_CLOUD_METHOD_CTB_SUPPORT, PROVIDER_CLOUD_ARGUMENT_MAINTENANCE, bundle);
        if (bundleCall == null) {
            Log.i(TAG, "Failed to call: Response is null");
            return null;
        }
        String string = bundleCall.getString(PROVIDER_CLOUD_RESPONSE_KEY_FAIL_REASON);
        if (string == null) {
            return bundleCall;
        }
        Log.i(TAG, "Failed to call, failReason: " + string);
        return null;
    }

    private static Bundle call(Context context, String str, String str2, String str3, Bundle bundle) {
        try {
            return context.getContentResolver().call(Uri.parse(str), str2, str3, bundle);
        } catch (Exception e) {
            Log.i(TAG, "Failed to call: " + e.toString());
            return null;
        }
    }

    static String getType(Context context, String str) {
        try {
            return context.getContentResolver().getType(Uri.parse(str));
        } catch (Exception e) {
            Log.i(TAG, "Failed to getType: " + e.toString());
            return PROVIDER_CALL_FAILED;
        }
    }

    static void sendLoggingDataToSA(Context context, String str, String str2) {
        String str3;
        try {
            Bundle bundle = new Bundle();
            bundle.putString(SemShareConstants.DMA_SURVEY_FEATURE_TRACKING_ID, TRACKING_ID_DEVICE_CARE);
            bundle.putString("feature", str);
            bundle.putString(SemShareConstants.SURVEY_EXTRA_OWN_PACKAGE, PACKAGE_DEVICE_CARE);
            bundle.putString("type", "ev");
            if (str2 != null) {
                bundle.putString("value", str2);
            }
            Intent intent = new Intent("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY");
            intent.setPackage("com.sec.android.diagmonagent");
            intent.putExtras(bundle);
            StringBuilder sb = new StringBuilder(Session.EXTERNAL_INDICATOR);
            sb.append(str);
            if (str2 == null) {
                str3 = "";
            } else {
                str3 = NativeLibraryHelper.CLEAR_ABI_OVERRIDE + str2;
            }
            sb.append(str3);
            Log.d(TAG, sb.toString());
            context.sendBroadcast(intent);
        } catch (Exception unused) {
        }
    }
}
