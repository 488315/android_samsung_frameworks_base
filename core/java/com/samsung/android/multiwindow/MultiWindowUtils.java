package com.samsung.android.multiwindow;

import android.app.AppGlobals;
import android.app.TaskInfo;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.DisplayMetrics;
import android.util.Slog;
import android.util.TypedValue;
import android.view.RoundedCorners;

import com.android.internal.R;
import com.android.internal.pm.pkg.parsing.ParsingPackageUtils;

import com.samsung.android.core.CoreSaConstant;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.wallpaperbackup.BnRConstants;

import java.util.List;

/* loaded from: classes6.dex */
public class MultiWindowUtils {
    public static final int ADD_APP_PAIR_SHORTCUT_EDGE_PANEL = 2;
    public static final int ADD_APP_PAIR_SHORTCUT_HOME = 1;
    public static final int ADD_APP_PAIR_SHORTCUT_TASKBAR = 0;
    private static final String AI_ASSIST_ACTION = "com.samsung.android.intent.action.AI_ASSIST";
    private static final int DENSITY_FREEFORM = 4;
    private static final int DENSITY_NONE = 0;
    private static final int DENSITY_SPLIT = 2;
    private static final String EXTRA_ALL_APPS_BUTTON_POSITION = "buttonPos";
    private static final String EXTRA_LAUNCH_TASK_ID = "launchTaskId";
    private static final String EXTRA_MODE = "mode";
    private static final String EXTRA_SPLIT_COMPONENT = "split_component_name";
    private static final String EXTRA_SPLIT_TASK_ID = "split_task_id";
    private static final String EXTRA_SPLIT_TASK_USER_ID = "split_task_user_id";
    public static final String FLEX_PANEL_MEDIA_IMMERSIVE_CLASS_NAME =
            "com.android.wm.shell.controlpanel.activity.FlexDimActivity";
    private static final String FLEX_PANEL_PACKAGE_NAME = "com.android.systemui";
    private static final float FREEFORM_DEFAULT_LONG_SIZE_RATIO = 0.5f;
    private static final float FREEFORM_DEFAULT_LONG_SIZE_RATIO_FOR_TABLET = 0.72f;
    private static final float FREEFORM_DEFAULT_SHORT_SIZE_RATIO = 0.85f;
    private static final float FREEFORM_DEFAULT_SHORT_SIZE_RATIO_FOR_TABLET = 0.3f;
    private static final float FREEFORM_DEFAULT_SIZE_RATIO_FOR_MULTI_SPLIT = 0.45f;
    private static final String HONEY_SPACE_EDGE_APP_PICKER_CLASS_NAME =
            "com.samsung.app.honeyspace.edge.fromrecent.FromRecentActivity";
    public static final String HONEY_SPACE_EDGE_PANEL_PROVIDER =
            "com.samsung.app.honeyspace.edge.appsedge.ui.panel.AppsEdgePanelProvider";
    private static final String HONEY_SPACE_EDGE_PKG_NAME = "com.sec.android.app.launcher";
    private static final String HONEY_SPACE_OVERLAY_ALLAPPS_SERVICE_CLS =
            "com.sec.android.app.launcher.overlayapps.OverlayAppsService";
    public static final int MAX_BOUNDS_CONFLICT_COUNT = 200;
    private static final String PERMISSION_CONTROLLER_PACKAGE =
            "com.google.android.permissioncontroller";
    private static final String SCREEN_CAPTURE_PACKAGE = "com.samsung.android.app.smartcapture";
    private static final String SEC_LAUNCHER_PACKAGE_NAME = "com.sec.android.app.launcher";
    public static final int SEND_SPLIT_STATE_CHANGED_INFO = 3;
    public static final String START_DND_SPLIT_WITH_ALL_APPS = "start_dnd_split_with_all_apps";
    private static final String TRAMPOLINE_APP_PACKAGE = "com.google.android.googlequicksearchbox";
    private static final int VALUE_MODE_FROM_MW = 3;
    public static final String FLEX_PANEL_CLASS_NAME =
            "com.android.wm.shell.controlpanel.activity.FlexPanelActivity";
    public static final ComponentName FLEX_PANEL_COMPONENT_NAME =
            new ComponentName("com.android.systemui", FLEX_PANEL_CLASS_NAME);
    private static final boolean sIsTablet = checkIsTablet();
    public static final PointF DEX_DEFAULT_SIZE_RATIO = new PointF(0.42f, 0.56f);
    public static final PointF DEX_DEFAULT_SIZE_RATIO_FOR_STANDALONE = new PointF(0.55f, 0.66f);

    private static boolean checkIsTablet() {
        String deviceType = SystemProperties.get("ro.build.characteristics");
        return deviceType != null && deviceType.contains(BnRConstants.DEVICETYPE_TABLET);
    }

    public static boolean isTablet() {
        return sIsTablet;
    }

    public static boolean isInSubDisplay(Context context) {
        return context.getResources().getConfiguration().semDisplayDeviceType == 5;
    }

    public static boolean isDefaultLauncher(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        String defaultLauncher = null;
        try {
            defaultLauncher =
                    context.getPackageManager()
                            .resolveActivity(intent, 65536)
                            .activityInfo
                            .packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CoreSaConstant.PACKAGE_NAME_RECENTS.equals(defaultLauncher);
    }

    public static void getDefaultFreeformBounds(Rect displayBounds, Rect outBounds) {
        float widthRatio;
        float heightRatio;
        int displayWidth = displayBounds.width();
        int displayHeight = displayBounds.height();
        if (isTablet()) {
            if (displayWidth > displayHeight) {
                widthRatio = FREEFORM_DEFAULT_SHORT_SIZE_RATIO_FOR_TABLET;
                heightRatio = 0.72f;
            } else {
                widthRatio = 0.72f;
                heightRatio = FREEFORM_DEFAULT_SHORT_SIZE_RATIO_FOR_TABLET;
            }
        } else if (CoreRune.MW_MULTI_SPLIT) {
            heightRatio = 0.45f;
            widthRatio = 0.45f;
        } else if (displayWidth > displayHeight) {
            widthRatio = 0.5f;
            heightRatio = FREEFORM_DEFAULT_SHORT_SIZE_RATIO;
        } else {
            widthRatio = FREEFORM_DEFAULT_SHORT_SIZE_RATIO;
            heightRatio = 0.5f;
        }
        int width = (int) ((displayWidth * widthRatio) + 0.5f);
        int height = (int) ((displayHeight * heightRatio) + 0.5f);
        if (isTablet() && displayWidth < displayHeight) {
            width = height;
            height = width;
        }
        outBounds.set(0, 0, width, height);
    }

    public static int getRoundedCornerColor(Context context) {
        return context.getResources().getColor(R.color.split_divider_background, null);
    }

    public static int getRoundedCornerRadius(Context context) {
        int multiWindowRadius =
                context.getResources()
                        .getDimensionPixelSize(R.dimen.rounded_corner_radius_for_multiwindow);
        if (MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED) {
            Resources resources = context.getResources();
            String displayUniqueId = context.getDisplayNoVerify().getUniqueId();
            int deviceRadius =
                    Math.min(
                            RoundedCorners.getRoundedCornerTopRadius(resources, displayUniqueId),
                            RoundedCorners.getRoundedCornerBottomRadius(
                                    resources, displayUniqueId));
            if (deviceRadius < multiWindowRadius) {
                return deviceRadius;
            }
        }
        return multiWindowRadius;
    }

    public static Intent getEdgeAllAppsActivityIntent(
            ComponentName splitComponent, int userId, int taskId) {
        Intent intent = new Intent();
        intent.setComponent(
                new ComponentName(
                        CoreSaConstant.PACKAGE_NAME_RECENTS,
                        HONEY_SPACE_EDGE_APP_PICKER_CLASS_NAME));
        intent.putExtra(EXTRA_SPLIT_COMPONENT, splitComponent);
        intent.putExtra(EXTRA_SPLIT_TASK_USER_ID, userId);
        intent.putExtra(EXTRA_SPLIT_TASK_ID, taskId);
        intent.setFlags(805568512);
        return intent;
    }

    public static boolean isAppsEdgeActivity(ComponentName comp) {
        return comp.getClassName().equals(HONEY_SPACE_EDGE_APP_PICKER_CLASS_NAME);
    }

    public static ComponentName getEdgeAllAppsComponent() {
        return new ComponentName(
                CoreSaConstant.PACKAGE_NAME_RECENTS, HONEY_SPACE_EDGE_APP_PICKER_CLASS_NAME);
    }

    public static boolean isNightMode(TaskInfo taskInfo) {
        if (taskInfo == null) {
            return false;
        }
        if (taskInfo.topActivityUiMode != 0) {
            return (taskInfo.topActivityUiMode & 48) == 32;
        }
        return taskInfo.getConfiguration().isNightModeActive();
    }

    public static int dipToPixel(int dip, DisplayMetrics displayMetrics) {
        return (int) TypedValue.applyDimension(1, dip, displayMetrics);
    }

    public static boolean isSplitEnabled(int multiSplitFlags) {
        return ((multiSplitFlags & 1) == 0 && (multiSplitFlags & 4) == 0) ? false : true;
    }

    public static boolean isDesktopModeSingleTopActivityTranslucent(TaskInfo taskInfo) {
        return taskInfo != null
                && taskInfo.configuration.isDesktopModeEnabled()
                && taskInfo.isTopActivityTransparent
                && taskInfo.numActivities == 1;
    }

    public static boolean isSystemUiTask(Context context, TaskInfo taskInfo) {
        if (context == null || taskInfo == null) {
            return false;
        }
        String sysUiPackageName = context.getResources().getString(17039418);
        return taskInfo.baseActivity != null
                && taskInfo.baseActivity.getPackageName().equals(sysUiPackageName);
    }

    public static void logForMultiWindowModeChange(
            int prevWinMode, int newWinMode, int prevStageType, int newStageType) {
        if (prevWinMode == newWinMode) {
            return;
        }
        if (!WindowConfiguration.inMultiWindowMode(prevWinMode)
                && !WindowConfiguration.inMultiWindowMode(newWinMode)) {
            return;
        }
        int value = 0;
        String detail = null;
        if (prevWinMode == 1) {
            if (newWinMode == 6) {
                value = 1;
                detail = CoreSaConstant.DETAIL_FULLSCREEN_TO_SPLIT;
            } else if (newWinMode == 5) {
                value = 2;
                detail = CoreSaConstant.DETAIL_FULLSCREEN_TO_FREEFORM;
            } else if (newWinMode == 2) {
                value = 3;
                detail = CoreSaConstant.DETAIL_FULLSCREEN_TO_PIP;
            }
        } else if (prevWinMode == 6) {
            if (newWinMode == 1) {
                value = 4;
                detail = CoreSaConstant.DETAIL_SPLIT_TO_FULLSCREEN;
            } else if (newWinMode == 5) {
                value = 5;
                detail = CoreSaConstant.DETAIL_SPLIT_TO_FREEFORM;
            } else if (newWinMode == 2) {
                value = 6;
                detail = CoreSaConstant.DETAIL_SPLIT_TO_PIP;
            }
        } else if (prevWinMode == 5) {
            if (newWinMode == 1) {
                value = 7;
                detail = CoreSaConstant.DETAIL_FREEFORM_TO_FULLSCREEN;
            } else if (newWinMode == 6) {
                value = 8;
                detail = CoreSaConstant.DETAIL_FREEFORM_TO_SPLIT;
            } else if (newWinMode == 2) {
                value = 9;
                detail = CoreSaConstant.DETAIL_FREEFORM_TO_PIP;
            }
        } else if (prevWinMode == 2) {
            if (newWinMode == 1) {
                value = 10;
                detail = CoreSaConstant.DETAIL_PIP_TO_FULLSCREEN;
            } else if (newWinMode == 6) {
                value = 11;
                detail = CoreSaConstant.DETAIL_PIP_TO_SPLIT;
            } else if (newWinMode == 5) {
                value = 12;
                detail = CoreSaConstant.DETAIL_PIP_TO_FREEFORM;
            }
        }
        if (value != 0) {
            CoreSaLogger.logForAdvanced(CoreSaConstant.MULTI_WINDOW_MODE_CHANGE_ID, detail, value);
        }
    }

    public static Intent getLaunchIntentForPackageAsUser(String packageName, int userId) {
        try {
            Intent intentToResolve = new Intent(Intent.ACTION_MAIN);
            intentToResolve.addCategory(Intent.CATEGORY_INFO);
            intentToResolve.setPackage(packageName);
            List<ResolveInfo> ris =
                    AppGlobals.getPackageManager()
                            .queryIntentActivities(intentToResolve, null, 0L, userId)
                            .getList();
            if (ris == null || ris.isEmpty()) {
                intentToResolve.removeCategory(Intent.CATEGORY_INFO);
                intentToResolve.addCategory(Intent.CATEGORY_LAUNCHER);
                ris =
                        AppGlobals.getPackageManager()
                                .queryIntentActivities(intentToResolve, null, 0L, userId)
                                .getList();
            }
            if (ris != null && !ris.isEmpty()) {
                Intent intent = new Intent(intentToResolve);
                intent.setFlags(268435456);
                intent.setClassName(
                        ris.get(0).activityInfo.packageName, ris.get(0).activityInfo.name);
                return intent;
            }
            return null;
        } catch (RemoteException e) {
            Slog.d("MultiWindowUtils", "getLaunchIntentForPackageAsUser, e : " + e.getMessage());
            return null;
        }
    }

    private static int getDensityBucket(int densityDpi) {
        if (densityDpi <= 120) {
            return 120;
        }
        if (densityDpi <= 160) {
            return 160;
        }
        if (densityDpi <= 240) {
            return 240;
        }
        if (densityDpi <= 320) {
            return 320;
        }
        if (densityDpi <= 480) {
            return 480;
        }
        return 640;
    }

    public static int getScaleDownDensity(int smallestWidth, int densityDpi) {
        if (!hasCustomDensity() && (isTablet() || smallestWidth >= 600)) {
            return -1;
        }
        int scaledDensityDpi = (densityDpi * 75) / 100;
        int densityBucket = getDensityBucket(densityDpi);
        int scaledDensityBucket = getDensityBucket(scaledDensityDpi);
        if (scaledDensityBucket < densityBucket) {
            return getMinimumDensityWithinBucket(densityBucket);
        }
        return scaledDensityDpi;
    }

    private static int getMinimumDensityWithinBucket(int densityBucket) {
        switch (densityBucket) {
            case 160:
                return 121;
            case 240:
                return 161;
            case 320:
                return 241;
            case 480:
                return 321;
            case 640:
                return 481;
            default:
                return densityBucket;
        }
    }

    public static boolean hasCustomDensity() {
        return MultiWindowCoreState.MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED != 0;
    }

    private static boolean isEnabledCustomDensityType(
            int windowingMode, int activityType, boolean isSplitScreenWindowingMode) {
        if (MultiWindowCoreState.MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED == 0
                || (!(activityType == 1 || activityType == 0)
                        || windowingMode == 2
                        || windowingMode == 1
                        || windowingMode == 0)) {
            return false;
        }
        return windowingMode == 5
                ? (MultiWindowCoreState.MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED & 4) > 0
                : windowingMode == 6
                        && isSplitScreenWindowingMode
                        && (2 & MultiWindowCoreState.MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED)
                                > 0;
    }

    public static boolean needToUpdateDensity(
            int windowingMode, int activityType, boolean isSplitScreenWindowingMode) {
        if (hasCustomDensity()) {
            return isEnabledCustomDensityType(
                    windowingMode, activityType, isSplitScreenWindowingMode);
        }
        return false;
    }

    public static boolean isFlexPanelEnabled(Context context) {
        if (!CoreRune.MW_SPLIT_FLEX_PANEL_MODE) {
            return false;
        }
        return context.getResources().getConfiguration().windowConfiguration.isFlexPanelEnabled();
    }

    public static Intent getExternalAppsServiceIntent(int taskId, int[] buttonPosition) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ALL_APPS_BUTTON_POSITION, buttonPosition);
        intent.putExtra("mode", 3);
        intent.putExtra(EXTRA_LAUNCH_TASK_ID, taskId);
        intent.setClassName(
                CoreSaConstant.PACKAGE_NAME_RECENTS, HONEY_SPACE_OVERLAY_ALLAPPS_SERVICE_CLS);
        return intent;
    }

    public static class MetaKeyBoundsChecker {
        public static final Rect sInvalidBounds = new Rect(-1, -1, -1, -1);
        public static final Rect sMinimizeBounds = new Rect(-2, -2, -2, -2);
        public static final Rect sMoveToDefaultDisplayBounds = new Rect(-3, -3, -3, -3);

        public static boolean isInvalidBounds(Rect bounds) {
            return sInvalidBounds.equals(bounds);
        }

        public static boolean isMinimizeBounds(Rect bounds) {
            return sMinimizeBounds.equals(bounds);
        }

        public static boolean isMoveToDefaultDisplayBounds(Rect bounds) {
            return sMoveToDefaultDisplayBounds.equals(bounds);
        }
    }

    public static boolean isSingleInstancePerTask(Context ctx, String pkg) {
        int launchMode;
        String launchMode2;
        Intent i = ctx.getPackageManager().getLaunchIntentForPackage(pkg);
        if (i == null) {
            return false;
        }
        ComponentName cn = i.getComponent();
        try {
            ActivityInfo ai =
                    ctx.getPackageManager()
                            .getActivityInfo(cn, PackageManager.ComponentInfoFlags.of(128L));
            launchMode = ai != null ? ai.launchMode : -1;
            launchMode2 =
                    (ai == null || ai.metaData == null)
                            ? null
                            : ai.metaData.getString(
                                    ParsingPackageUtils.METADATA_ACTIVITY_LAUNCH_MODE);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (launchMode != 4) {
            if (launchMode2 != null) {
                if (launchMode2.equals("singleInstancePerTask")) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public static boolean isKeepFlexPanelTask(String pkg) {
        return SCREEN_CAPTURE_PACKAGE.equalsIgnoreCase(pkg)
                || PERMISSION_CONTROLLER_PACKAGE.equalsIgnoreCase(pkg);
    }

    public static boolean isFlexPanelActivity(String s) {
        return s.equals(FLEX_PANEL_CLASS_NAME) || s.equals(FLEX_PANEL_MEDIA_IMMERSIVE_CLASS_NAME);
    }

    public static void adjustBoundsForScreenRatio(
            Rect prevScreenBounds, Rect nextScreenBounds, Rect sourceBounds, Rect outBounds) {
        if (sourceBounds == null || sourceBounds.isEmpty()) {
            Slog.d("RotationUtils", "adjustBoundsForScreenRatio: sourceBounds is null or empty.");
            return;
        }
        int prevScreenWidth = prevScreenBounds.width();
        int prevScreenHeight = prevScreenBounds.height();
        int nextScreenWidth = nextScreenBounds.width();
        int nextScreenHeight = nextScreenBounds.height();
        if (prevScreenWidth == nextScreenWidth && prevScreenHeight == nextScreenHeight) {
            Slog.d(
                    "RotationUtils",
                    "adjustBoundsForScreenRatio: Since the screen ratio has not changed, there is"
                        + " no need to calculate new bounds.");
            return;
        }
        boolean isAdjustWidth = false;
        boolean isAdjustHeight = false;
        int appWidth = sourceBounds.width();
        int appHeight = sourceBounds.height();
        if (appWidth > nextScreenWidth) {
            appWidth = (int) ((nextScreenWidth * 0.8f) + 0.5f);
            isAdjustWidth = true;
        }
        if (appHeight > nextScreenHeight) {
            appHeight = (int) ((nextScreenHeight * 0.8f) + 0.5f);
            isAdjustHeight = true;
        }
        int shownAppWidth = appWidth;
        int shownAppHeight = appHeight;
        if (sourceBounds.left < 0) {
            shownAppWidth = sourceBounds.right;
        } else if (prevScreenBounds.right < sourceBounds.right) {
            shownAppWidth = prevScreenBounds.right - sourceBounds.left;
        }
        if (sourceBounds.top < 0) {
            shownAppHeight = sourceBounds.bottom;
        } else if (prevScreenBounds.bottom < sourceBounds.bottom) {
            shownAppHeight = prevScreenBounds.bottom - sourceBounds.top;
        }
        float tempDenominator =
                prevScreenWidth <= shownAppWidth ? 1.0f : prevScreenWidth - shownAppWidth;
        float appLeftRatio = sourceBounds.left / tempDenominator;
        float tempDenominator2 =
                prevScreenHeight <= shownAppHeight ? 1.0f : prevScreenHeight - shownAppHeight;
        float appTopRatio = sourceBounds.top / tempDenominator2;
        if (isAdjustWidth) {
            outBounds.left = (int) ((nextScreenWidth * 0.1f) + 0.5f);
            outBounds.right = outBounds.left + appWidth;
        } else if (sourceBounds.left < 0) {
            outBounds.left = sourceBounds.left;
            outBounds.right = outBounds.left + appWidth;
        } else if (prevScreenBounds.right < sourceBounds.right) {
            outBounds.right =
                    nextScreenBounds.right + (sourceBounds.right - prevScreenBounds.right);
            outBounds.left = outBounds.right - appWidth;
        } else {
            outBounds.left = (int) ((nextScreenWidth - shownAppWidth) * appLeftRatio);
            outBounds.right = outBounds.left + appWidth;
        }
        if (isAdjustHeight) {
            outBounds.top = (int) ((nextScreenHeight * 0.1f) + 0.5f);
            outBounds.bottom = outBounds.top + appHeight;
        } else if (sourceBounds.top < 0) {
            outBounds.top = sourceBounds.top;
            outBounds.bottom = outBounds.top + appHeight;
        } else if (prevScreenBounds.bottom < sourceBounds.bottom) {
            outBounds.bottom =
                    nextScreenBounds.bottom + (sourceBounds.bottom - prevScreenBounds.bottom);
            outBounds.top = outBounds.bottom - appHeight;
        } else {
            outBounds.top = (int) ((nextScreenHeight - shownAppHeight) * appTopRatio);
            outBounds.bottom = outBounds.top + appHeight;
        }
    }

    public static Drawable getAppIcon(
            Context context, ComponentName componentName, int userId, String mPackageName) {
        try {
            return context.getPackageManager().semGetActivityIconForIconTray(componentName, 48);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                ApplicationInfo appInfo =
                        context.getPackageManager()
                                .getApplicationInfoAsUser(mPackageName, 0, userId);
                return context.getPackageManager().semGetApplicationIconForIconTray(appInfo, 48);
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
    }

    public static boolean isAiKeyAction(String action) {
        return AI_ASSIST_ACTION.equals(action);
    }

    public static String getAiKeyAction() {
        return AI_ASSIST_ACTION;
    }

    public static boolean isAiKeyTrampolineActivity(String packageName) {
        return "com.google.android.googlequicksearchbox".equals(packageName);
    }
}
