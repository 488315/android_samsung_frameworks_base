package com.samsung.android.multiwindow;

import android.app.AppGlobals;
import android.app.TaskInfo;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
    public static final String FLEX_PANEL_MEDIA_IMMERSIVE_CLASS_NAME = "com.android.wm.shell.controlpanel.activity.FlexDimActivity";
    private static final String FLEX_PANEL_PACKAGE_NAME = "com.android.systemui";
    public static final int FREEFORM_CAPTION_CHANGE_ANIM_DURATION = 400;
    private static final float FREEFORM_DEFAULT_LONG_SIZE_RATIO = 0.5f;
    private static final float FREEFORM_DEFAULT_LONG_SIZE_RATIO_FOR_TABLET = 0.72f;
    private static final float FREEFORM_DEFAULT_SHORT_SIZE_RATIO = 0.85f;
    private static final float FREEFORM_DEFAULT_SHORT_SIZE_RATIO_FOR_TABLET = 0.3f;
    private static final float FREEFORM_DEFAULT_SIZE_RATIO_FOR_MULTI_SPLIT = 0.45f;
    public static final String GEMINI_END_ACTIVITY = "com.google.android.apps.search.assistant.surfaces.voice.robin.main.MainActivity";
    public static final String GEMINI_START_ACTIVITY = "com.google.android.apps.bard.shellapp.BardEntryPointActivity";
    public static final String GEMINI_START_COMPONENT = "com.google.android.apps.bard/.shellapp.BardEntryPointActivity";
    public static final String GEMINI_START_PACKAGE = "com.google.android.apps.bard";
    private static final String HONEY_SPACE_EDGE_APP_PICKER_CLASS_NAME = "com.samsung.app.honeyspace.edge.fromrecent.FromRecentActivity";
    public static final String HONEY_SPACE_EDGE_PANEL_PROVIDER = "com.samsung.app.honeyspace.edge.appsedge.ui.panel.AppsEdgePanelProvider";
    private static final String HONEY_SPACE_EDGE_PKG_NAME = "com.sec.android.app.launcher";
    private static final String HONEY_SPACE_OVERLAY_ALLAPPS_SERVICE_CLS = "com.sec.android.app.launcher.overlayapps.OverlayAppsService";
    public static final int MAX_ACTIVE_TASKS_LIMIT = 15;
    public static final int MAX_BOUNDS_CONFLICT_COUNT = 200;
    private static final String PERMISSION_CONTROLLER_PACKAGE = "com.google.android.permissioncontroller";
    private static final String SCREEN_CAPTURE_PACKAGE = "com.samsung.android.app.smartcapture";
    private static final String SEC_LAUNCHER_PACKAGE_NAME = "com.sec.android.app.launcher";
    public static final int SEND_SPLIT_STATE_CHANGED_INFO = 3;
    public static final String START_DND_SPLIT_WITH_ALL_APPS = "start_dnd_split_with_all_apps";
    public static final String TRAMPOLINE_APP_PACKAGE = "com.google.android.googlequicksearchbox";
    private static final int VALUE_MODE_FROM_MW = 3;
    private static final String VISION_INTELLIGENCE = "com.samsung.android.visionintelligence";
    public static final String FLEX_PANEL_CLASS_NAME = "com.android.wm.shell.controlpanel.activity.FlexPanelActivity";
    public static final ComponentName FLEX_PANEL_COMPONENT_NAME = new ComponentName("com.android.systemui", FLEX_PANEL_CLASS_NAME);
    private static final boolean sIsTablet = checkIsTablet();
    public static final PointF DEX_DEFAULT_SIZE_RATIO = new PointF(0.42f, 0.56f);
    public static final PointF DEX_DEFAULT_SIZE_RATIO_FOR_STANDALONE = new PointF(0.55f, 0.66f);
    public static final PointF DEX_DEFAULT_SIZE_RATIO_FOR_NEW_DEX = new PointF(0.541f, 0.65f);

    private static int getDensityBucket(int i) {
        if (i <= 120) {
            return 120;
        }
        if (i <= 160) {
            return 160;
        }
        if (i <= 240) {
            return 240;
        }
        if (i <= 320) {
            return 320;
        }
        return i <= 480 ? 480 : 640;
    }

    private static int getMinimumDensityWithinBucket(int i) {
        if (i == 160) {
            return 121;
        }
        if (i == 240) {
            return 161;
        }
        if (i == 320) {
            return 241;
        }
        if (i == 480) {
            return 321;
        }
        if (i != 640) {
            return i;
        }
        return 481;
    }

    public static boolean isFlexPanelEnabled(Context context) {
        return false;
    }

    public static boolean isSplitEnabled(int i) {
        return ((i & 1) == 0 && (i & 4) == 0) ? false : true;
    }

    private static boolean checkIsTablet() {
        String str = SystemProperties.get("ro.build.characteristics");
        return str != null && str.contains(BnRConstants.DEVICETYPE_TABLET);
    }

    public static boolean isTablet() {
        return sIsTablet;
    }

    public static boolean isInSubDisplay(Context context) {
        return context.getResources().getConfiguration().semDisplayDeviceType == 5;
    }

    public static boolean isWindowManagerCtsPackage(String str) {
        return "android.server.wm.app".equals(str);
    }

    public static boolean isDefaultLauncher(Context context) {
        String str;
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        try {
            str = context.getPackageManager().resolveActivity(intent, 65536).activityInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
            str = null;
        }
        return CoreSaConstant.PACKAGE_NAME_RECENTS.equals(str);
    }

    public static void getDefaultFreeformBounds(Rect rect, Rect rect2, ActivityInfo.WindowLayout windowLayout, Rect rect3) {
        float f;
        float f2;
        int i;
        int i2;
        int width = rect.width();
        int height = rect.height();
        boolean z = width > height;
        if (isTablet()) {
            f = FREEFORM_DEFAULT_SHORT_SIZE_RATIO_FOR_TABLET;
            f2 = FREEFORM_DEFAULT_LONG_SIZE_RATIO_FOR_TABLET;
            if (!z) {
                f2 = 0.3f;
                f = 0.72f;
            }
        } else if (CoreRune.MW_MULTI_SPLIT) {
            f = FREEFORM_DEFAULT_SIZE_RATIO_FOR_MULTI_SPLIT;
            f2 = 0.45f;
        } else {
            f = FREEFORM_DEFAULT_SHORT_SIZE_RATIO;
            if (z) {
                f2 = 0.85f;
                f = 0.5f;
            } else {
                f2 = 0.5f;
            }
        }
        if (!isTablet() || z) {
            int i3 = (int) ((width * f) + 0.5f);
            i = (int) ((height * f2) + 0.5f);
            i2 = i3;
        } else {
            i2 = (int) ((height * f2) + 0.5f);
            i = (int) ((width * f) + 0.5f);
        }
        rect3.set(0, 0, Math.min(rect2.width(), Math.max(i2, windowLayout == null ? -1 : windowLayout.minWidth)), Math.min(rect2.height(), Math.max(i, windowLayout != null ? windowLayout.minHeight : -1)));
    }

    public static ActivityInfo.WindowLayout recalculateWindowLayout(float f, float f2, ActivityInfo.WindowLayout windowLayout, String str) {
        if (f2 <= 0.0f) {
            return windowLayout;
        }
        if (isWindowManagerCtsPackage(str)) {
            f = DisplayMetrics.DENSITY_DEVICE_STABLE;
        }
        float f3 = f / f2;
        return new ActivityInfo.WindowLayout(windowLayout.width < 0 ? -1 : (int) ((windowLayout.width * f3) + 0.5f), windowLayout.widthFraction, windowLayout.height < 0 ? -1 : (int) ((windowLayout.height * f3) + 0.5f), windowLayout.heightFraction, windowLayout.gravity, windowLayout.minWidth < 0 ? -1 : (int) ((windowLayout.minWidth * f3) + 0.5f), windowLayout.minHeight >= 0 ? (int) ((windowLayout.minHeight * f3) + 0.5f) : -1);
    }

    public static int getRoundedCornerColor(Context context) {
        return context.getResources().getColor(R.color.split_divider_background, null);
    }

    public static int getRoundedCornerRadius(Context context) {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.rounded_corner_radius_for_multiwindow);
        if (MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED) {
            Resources resources = context.getResources();
            String uniqueId = context.getDisplayNoVerify().getUniqueId();
            int min = Math.min(RoundedCorners.getRoundedCornerTopRadius(resources, uniqueId), RoundedCorners.getRoundedCornerBottomRadius(resources, uniqueId));
            if (min < dimensionPixelSize) {
                return min;
            }
        }
        return dimensionPixelSize;
    }

    public static float getFreeformRoundedCornerRadius(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.freeform_rounded_corner_radius);
    }

    public static Intent getEdgeAllAppsActivityIntent(ComponentName componentName, int i, int i2) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(CoreSaConstant.PACKAGE_NAME_RECENTS, HONEY_SPACE_EDGE_APP_PICKER_CLASS_NAME));
        intent.putExtra(EXTRA_SPLIT_COMPONENT, componentName);
        intent.putExtra(EXTRA_SPLIT_TASK_USER_ID, i);
        intent.putExtra(EXTRA_SPLIT_TASK_ID, i2);
        intent.setFlags(805568512);
        return intent;
    }

    public static boolean isAppsEdgeActivity(ComponentName componentName) {
        return componentName.getClassName().equals(HONEY_SPACE_EDGE_APP_PICKER_CLASS_NAME);
    }

    public static ComponentName getEdgeAllAppsComponent() {
        return new ComponentName(CoreSaConstant.PACKAGE_NAME_RECENTS, HONEY_SPACE_EDGE_APP_PICKER_CLASS_NAME);
    }

    public static boolean isNightMode(TaskInfo taskInfo) {
        if (taskInfo == null) {
            return false;
        }
        return taskInfo.getConfiguration().isNightModeActive();
    }

    public static int dipToPixel(int i, DisplayMetrics displayMetrics) {
        return (int) TypedValue.applyDimension(1, i, displayMetrics);
    }

    public static boolean isDesktopModeSingleTopActivityTranslucent(TaskInfo taskInfo) {
        return taskInfo != null && taskInfo.isTopActivityTransparent && taskInfo.numActivities == 1;
    }

    public static boolean isSystemUiTask(Context context, TaskInfo taskInfo) {
        if (context != null && taskInfo != null) {
            String string = context.getResources().getString(17039418);
            if (taskInfo.baseActivity != null && taskInfo.baseActivity.getPackageName().equals(string)) {
                return true;
            }
        }
        return false;
    }

    public static void logForMultiWindowModeChange(int i, int i2, int i3, int i4) {
        String str;
        if (i == i2) {
            return;
        }
        if (WindowConfiguration.inMultiWindowMode(i) || WindowConfiguration.inMultiWindowMode(i2)) {
            int i5 = 2;
            if (i == 1) {
                if (i2 == 6) {
                    str = CoreSaConstant.DETAIL_FULLSCREEN_TO_SPLIT;
                    i5 = 1;
                } else if (i2 == 5) {
                    str = CoreSaConstant.DETAIL_FULLSCREEN_TO_FREEFORM;
                } else {
                    if (i2 == 2) {
                        i5 = 3;
                        str = CoreSaConstant.DETAIL_FULLSCREEN_TO_PIP;
                    }
                    i5 = 0;
                    str = null;
                }
            } else if (i == 6) {
                if (i2 == 1) {
                    i5 = 4;
                    str = CoreSaConstant.DETAIL_SPLIT_TO_FULLSCREEN;
                } else if (i2 == 5) {
                    str = CoreSaConstant.DETAIL_SPLIT_TO_FREEFORM;
                    i5 = 5;
                } else {
                    if (i2 == 2) {
                        str = CoreSaConstant.DETAIL_SPLIT_TO_PIP;
                        i5 = 6;
                    }
                    i5 = 0;
                    str = null;
                }
            } else if (i != 5) {
                if (i == 2) {
                    if (i2 == 1) {
                        i5 = 10;
                        str = CoreSaConstant.DETAIL_PIP_TO_FULLSCREEN;
                    } else if (i2 == 6) {
                        i5 = 11;
                        str = CoreSaConstant.DETAIL_PIP_TO_SPLIT;
                    } else if (i2 == 5) {
                        i5 = 12;
                        str = CoreSaConstant.DETAIL_PIP_TO_FREEFORM;
                    }
                }
                i5 = 0;
                str = null;
            } else if (i2 == 1) {
                i5 = 7;
                str = CoreSaConstant.DETAIL_FREEFORM_TO_FULLSCREEN;
            } else if (i2 == 6) {
                i5 = 8;
                str = CoreSaConstant.DETAIL_FREEFORM_TO_SPLIT;
            } else {
                if (i2 == 2) {
                    i5 = 9;
                    str = CoreSaConstant.DETAIL_FREEFORM_TO_PIP;
                }
                i5 = 0;
                str = null;
            }
            if (i5 != 0) {
                CoreSaLogger.logForAdvanced(CoreSaConstant.MULTI_WINDOW_MODE_CHANGE_ID, str, i5);
            }
        }
    }

    public static Intent getLaunchIntentForPackageAsUser(String str, int i) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_INFO);
            intent.setPackage(str);
            List list = AppGlobals.getPackageManager().queryIntentActivities(intent, null, 0L, i).getList();
            if (list == null || list.isEmpty()) {
                intent.removeCategory(Intent.CATEGORY_INFO);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                list = AppGlobals.getPackageManager().queryIntentActivities(intent, null, 0L, i).getList();
            }
            if (list != null && !list.isEmpty()) {
                Intent intent2 = new Intent(intent);
                intent2.setFlags(268435456);
                intent2.setClassName(((ResolveInfo) list.get(0)).activityInfo.packageName, ((ResolveInfo) list.get(0)).activityInfo.name);
                return intent2;
            }
            return null;
        } catch (RemoteException e) {
            Slog.d("MultiWindowUtils", "getLaunchIntentForPackageAsUser, e : " + e.getMessage());
            return null;
        }
    }

    public static int getScaleDownDensity(int i, int i2) {
        if (!hasCustomDensity() && (isTablet() || i >= 600)) {
            return -1;
        }
        int i3 = (i2 * 75) / 100;
        int densityBucket = getDensityBucket(i2);
        return getDensityBucket(i3) < densityBucket ? getMinimumDensityWithinBucket(densityBucket) : i3;
    }

    public static boolean hasCustomDensity() {
        return MultiWindowCoreState.MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED != 0;
    }

    private static boolean isEnabledCustomDensityType(int i, int i2, boolean z) {
        if (MultiWindowCoreState.MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED != 0 && ((i2 == 1 || i2 == 0) && i != 2 && i != 1 && i != 0)) {
            if (i == 5) {
                return (MultiWindowCoreState.MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED & 4) > 0;
            }
            if (i == 6 && z && (MultiWindowCoreState.MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED & 2) > 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean needToUpdateDensity(int i, int i2, boolean z) {
        if (hasCustomDensity()) {
            return isEnabledCustomDensityType(i, i2, z);
        }
        return false;
    }

    public static Intent getExternalAppsServiceIntent(int i, int[] iArr) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ALL_APPS_BUTTON_POSITION, iArr);
        intent.putExtra("mode", 3);
        intent.putExtra(EXTRA_LAUNCH_TASK_ID, i);
        intent.setClassName(CoreSaConstant.PACKAGE_NAME_RECENTS, HONEY_SPACE_OVERLAY_ALLAPPS_SERVICE_CLS);
        return intent;
    }

    public static boolean isSingleInstancePerTask(Context context, String str) {
        int i;
        String string;
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(str);
        if (launchIntentForPackage == null) {
            return false;
        }
        try {
            ActivityInfo activityInfo = context.getPackageManager().getActivityInfo(launchIntentForPackage.getComponent(), PackageManager.ComponentInfoFlags.of(128L));
            i = activityInfo != null ? activityInfo.launchMode : -1;
            string = (activityInfo == null || activityInfo.metaData == null) ? null : activityInfo.metaData.getString(ParsingPackageUtils.METADATA_ACTIVITY_LAUNCH_MODE);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (i == 4) {
            return true;
        }
        if (string != null) {
            if (string.equals("singleInstancePerTask")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isKeepFlexPanelTask(String str) {
        return SCREEN_CAPTURE_PACKAGE.equalsIgnoreCase(str) || PERMISSION_CONTROLLER_PACKAGE.equalsIgnoreCase(str) || VISION_INTELLIGENCE.equalsIgnoreCase(str);
    }

    public static boolean isFlexPanelActivity(String str) {
        return str.equals(FLEX_PANEL_CLASS_NAME) || str.equals(FLEX_PANEL_MEDIA_IMMERSIVE_CLASS_NAME);
    }

    public static void adjustBoundsForScreenRatio(Rect rect, Rect rect2, Rect rect3, Rect rect4) {
        boolean z;
        int i;
        int i2;
        if (rect3 == null || rect3.isEmpty()) {
            Slog.d("RotationUtils", "adjustBoundsForScreenRatio: sourceBounds is null or empty.");
            return;
        }
        int width = rect.width();
        int height = rect.height();
        int width2 = rect2.width();
        int height2 = rect2.height();
        if (width == width2 && height == height2) {
            Slog.d("RotationUtils", "adjustBoundsForScreenRatio: Since the screen ratio has not changed, there is no need to calculate new bounds.");
            return;
        }
        int width3 = rect3.width();
        int height3 = rect3.height();
        boolean z2 = true;
        if (width3 > width2) {
            width3 = (int) ((width2 * 0.8f) + 0.5f);
            z = true;
        } else {
            z = false;
        }
        if (height3 > height2) {
            height3 = (int) ((height2 * 0.8f) + 0.5f);
        } else {
            z2 = false;
        }
        if (rect3.left < 0) {
            i = rect3.right;
        } else {
            i = rect.right < rect3.right ? rect.right - rect3.left : width3;
        }
        if (rect3.top < 0) {
            i2 = rect3.bottom;
        } else {
            i2 = rect.bottom < rect3.bottom ? rect.bottom - rect3.top : height3;
        }
        float f = rect3.left / (width <= i ? 1.0f : width - i);
        float f2 = rect3.top / (height > i2 ? height - i2 : 1.0f);
        if (z) {
            rect4.left = (int) ((width2 * 0.1f) + 0.5f);
            rect4.right = rect4.left + width3;
        } else if (rect3.left < 0) {
            rect4.left = rect3.left;
            rect4.right = rect4.left + width3;
        } else if (rect.right < rect3.right) {
            rect4.right = rect2.right + (rect3.right - rect.right);
            rect4.left = rect4.right - width3;
        } else {
            rect4.left = (int) ((width2 - i) * f);
            rect4.right = rect4.left + width3;
        }
        if (z2) {
            rect4.top = (int) ((height2 * 0.1f) + 0.5f);
            rect4.bottom = rect4.top + height3;
        } else if (rect3.top < 0) {
            rect4.top = rect3.top;
            rect4.bottom = rect4.top + height3;
        } else if (rect.bottom < rect3.bottom) {
            rect4.bottom = rect2.bottom + (rect3.bottom - rect.bottom);
            rect4.top = rect4.bottom - height3;
        } else {
            rect4.top = (int) ((height2 - i2) * f2);
            rect4.bottom = rect4.top + height3;
        }
    }

    public static Drawable getAppIcon(Context context, ComponentName componentName, int i, String str) {
        try {
            return context.getPackageManager().semGetActivityIconForIconTray(componentName, 48);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                return context.getPackageManager().semGetApplicationIconForIconTray(context.getPackageManager().getApplicationInfoAsUser(str, 0, i), 48);
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
    }

    public static boolean isAiKeyAction(String str) {
        return AI_ASSIST_ACTION.equals(str);
    }

    public static String getAiKeyAction() {
        return AI_ASSIST_ACTION;
    }

    public static boolean isAiKeyTrampolineActivity(String str) {
        return "com.google.android.googlequicksearchbox".equals(str);
    }

    public static boolean isDeferSyncSplitTransitionApps(String str) {
        return GEMINI_START_ACTIVITY.equals(str);
    }
}
