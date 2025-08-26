package com.android.wm.shell.controlpanel.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.wm.shell.controlpanel.action.ControlPanelAction;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.context.sdk.samsunganalytics.LogBuilders$EventBuilder;
import com.samsung.context.sdk.samsunganalytics.SamsungAnalytics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class ControlPanelUtils {
    public static final boolean IS_WIDE_WIDTH_TYPE;
    public static final String TALKBACK_SERVICE = "com.samsung.android.marvin.talkback.TalkBackService";
    static final String UNIVERSAL_SWITCH_SERVICE = "com.samsung.accessibility.universalswitch.UniversalSwitchService";

    static {
        IS_WIDE_WIDTH_TYPE = SystemProperties.get("ro.product.name", "").startsWith("q7") || SystemProperties.get("ro.product.system.name", "").startsWith("q7");
    }

    public static void eventLogging(String str, String str2, Map map) {
        HashMap map2 = (HashMap) map;
        map2.put("det", str2);
        Log.d("FlexPanelSALogging", "eventName : " + str + ", detail : " + str2 + ", customDimen : " + map2);
        SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
        LogBuilders$EventBuilder logBuilders$EventBuilder = new LogBuilders$EventBuilder();
        logBuilders$EventBuilder.setEventName(str);
        logBuilders$EventBuilder.setDimension(map2);
        samsungAnalytics.sendLog(logBuilders$EventBuilder.build());
    }

    public static int getDisplayX(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        return point.x;
    }

    public static int getDisplayY(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        return point.y;
    }

    public static String getPackageNameForMediaPanel(Context context, boolean z) {
        ComponentName componentName;
        try {
            if (z) {
                componentName = getTopActivity(context);
            } else {
                ActivityManager.RunningTaskInfo runningTaskExcept = getRunningTaskExcept(context);
                componentName = runningTaskExcept != null ? runningTaskExcept.baseActivity : new ComponentName("", "");
            }
        } catch (NullPointerException e) {
            Log.e("ControlPanelUtils", e.toString(), e);
        }
        if (!"com.android.systemui.stackdivider.ForcedResizableInfoActivity".equals(componentName.getClassName())) {
            return componentName.getPackageName();
        }
        ActivityManager.RunningTaskInfo runningTaskExcept2 = getRunningTaskExcept(context);
        if (runningTaskExcept2 != null) {
            return runningTaskExcept2.baseActivity.getPackageName();
        }
        return "";
    }

    public static LinearLayout.LayoutParams getRatioLayoutParams(FlexPanelActivity flexPanelActivity, double d, double d2) {
        WindowManager windowManager = (WindowManager) flexPanelActivity.getSystemService("window");
        windowManager.getDefaultDisplay().getRealSize(new Point());
        return new LinearLayout.LayoutParams((int) ((r0.x * d) / 100.0d), (int) ((r0.y * d2) / 100.0d));
    }

    public static RelativeLayout.LayoutParams getRatioRelativeLayoutParams(Context context, double d, double d2) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        windowManager.getDefaultDisplay().getRealSize(new Point());
        return new RelativeLayout.LayoutParams((int) ((r0.x * d) / 100.0d), (int) ((r0.y * d2) / 100.0d));
    }

    public static ActivityManager.RunningTaskInfo getRunningTaskExcept(Context context) {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) context.getSystemService(ActivityManager.class)).getRunningTasks(2);
        if (runningTasks.size() >= 2) {
            return "com.android.wm.shell.controlpanel.activity.FlexPanelActivity".equalsIgnoreCase(runningTasks.get(0).topActivity.getShortClassName()) ? runningTasks.get(1) : runningTasks.get(0);
        }
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(5, "ControlPanelUtils", new StringBuilder("no running Tasks callers="));
        return null;
    }

    public static ComponentName getTopActivity(Context context) {
        ActivityManager.RunningTaskInfo runningTaskExcept = getRunningTaskExcept(context);
        return runningTaskExcept != null ? runningTaskExcept.topActivity : new ComponentName("", "");
    }

    public static boolean isAccessibilityEnabled(Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), SettingsHelper.INDEX_ENABLED_ACCESSIBILITY_SERVICES);
        if (string == null) {
            return false;
        }
        return string.contains("com.samsung.android.marvin.talkback.TalkBackService") || string.contains(UNIVERSAL_SWITCH_SERVICE);
    }

    public static boolean isClockActivity(Context context) {
        return getTopActivity(context).toString().contains("com.sec.android.app.clockpackage.alarm.activity.AlarmSoundMainActivity") || getTopActivity(context).toString().contains("com.sec.android.app.clockpackage.ringtonepicker.viewmodel.RingtonePickerActivity") || getTopActivity(context).toString().contains("com.sec.android.app.clockpackage.alarm.activity.SpotifyActivity");
    }

    public static boolean isKidsMode(Context context) {
        ComponentName componentName = new ComponentName("com.sec.android.app.kidshome", "com.sec.android.app.kidshome.start.ui.StartActivity");
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        ActivityInfo activityInfo = packageManager.resolveActivity(intent, 65536).activityInfo;
        return new ComponentName(activityInfo.packageName, activityInfo.name).equals(componentName);
    }

    public static boolean isQuickPanelPressAvailable(Context context, String str) {
        return (isKidsMode(context) || "com.sec.android.app.clockpackage.alarm.AlarmAlert".equalsIgnoreCase(str) || "com.sec.android.app.clockpackage.alarm.AlarmSmartAlert".equalsIgnoreCase(str) || "com.sec.android.app.clockpackage.timer.TimerAlarm".equalsIgnoreCase(str) || Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) == 0 || "com.samsung.android.app.telephonyui.emergencydialer.view.EmergencyDialerActivity".equals(str)) ? false : true;
    }

    public static boolean isTouchPadEnabled(SharedPreferences sharedPreferences) {
        return (sharedPreferences.getBoolean("TOUCH_PAD_ENABLED", true) && !sharedPreferences.getBoolean("MEDIA_PANEL", false)) || (sharedPreferences.getBoolean("MEDIA_TOUCH_PAD_ENABLED", false) && sharedPreferences.getBoolean("MEDIA_PANEL", false));
    }

    public static boolean isTypeFold() {
        return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
    }

    public static boolean makeGridButton(Context context, RelativeLayout relativeLayout, int i, int i2, boolean z, boolean z2) {
        String className;
        ImageButton imageButton = (ImageButton) relativeLayout.findViewById(R.id.menubutton);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.menubutton_icon);
        RelativeLayout relativeLayout2 = (RelativeLayout) relativeLayout.findViewById(R.id.grid_button);
        boolean zIsTypeFold = isTypeFold();
        boolean z3 = IS_WIDE_WIDTH_TYPE;
        RelativeLayout.LayoutParams ratioRelativeLayoutParams = zIsTypeFold ? z3 ? getRatioRelativeLayoutParams(context, 3.37d, 3.71d) : getRatioRelativeLayoutParams(context, 3.37d, 4.1d) : getRatioRelativeLayoutParams(context, 8.88d, 3.64d);
        ratioRelativeLayoutParams.addRule(13);
        imageView.setLayoutParams(ratioRelativeLayoutParams);
        relativeLayout.findViewById(R.id.button_focus).setLayoutParams(ratioRelativeLayoutParams);
        RelativeLayout.LayoutParams ratioRelativeLayoutParams2 = isTypeFold() ? z3 ? getRatioRelativeLayoutParams(context, 4.82d, 5.36d) : getRatioRelativeLayoutParams(context, 4.82d, 5.7d) : getRatioRelativeLayoutParams(context, 11.11d, 4.55d);
        ratioRelativeLayoutParams2.addRule(13);
        imageButton.setLayoutParams(ratioRelativeLayoutParams2);
        relativeLayout.setGravity(17);
        relativeLayout.setLayoutParams(ratioRelativeLayoutParams2);
        boolean z4 = true;
        relativeLayout2.semSetHoverPopupType(1);
        imageView.setBackgroundResource(i2);
        imageView.setBackgroundTintList(ContextCompat.getColorStateList(R.color.panel_menu_icon_color_expand, context));
        boolean z5 = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SystemUIAnalytics.FLEX_PANEL_PREF_NAME, 0);
        if (i == ControlPanelAction.Action.QuickPanel.getValue()) {
            try {
                className = getTopActivity(context).getClassName();
            } catch (NullPointerException e) {
                Log.e("ControlPanelUtils", e.toString(), e);
                className = "";
            }
            if (!isQuickPanelPressAvailable(context, className)) {
                relativeLayout.setEnabled(false);
                relativeLayout.setAlpha(0.4f);
                z4 = false;
            }
        }
        if (i == ControlPanelAction.Action.TouchPad.getValue() && isTouchPadEnabled(sharedPreferences)) {
            imageButton.setBackgroundResource(R.drawable.grid_button_background);
            imageButton.setBackgroundTintList(ResourcesCompat.getColorStateList(R.color.panel_menu_icon_selected_color, context.getTheme(), context.getResources()));
            imageButton.setVisibility(0);
        }
        if (i == ControlPanelAction.Action.DragCircle.getValue()) {
            imageButton.setBackgroundResource(R.drawable.drag_circle_background);
            imageButton.setBackgroundTintList(ResourcesCompat.getColorStateList(R.color.drag_circle_stroke_color, context.getTheme(), context.getResources()));
            imageButton.setVisibility(0);
        }
        if (((ActivityManager) context.getSystemService("activity")).getLockTaskModeState() != 0) {
            relativeLayout.setEnabled(false);
            relativeLayout.setAlpha(0.4f);
        } else {
            z5 = z4;
        }
        if (z) {
            if (z2 && i == ControlPanelAction.Action.EditPanel.getValue()) {
                relativeLayout2.setContentDescription(context.getResources().getString(R.string.flex_panel_toolbar_minimized));
            } else {
                relativeLayout2.setContentDescription(context.getResources().getString(ControlPanelAction.getStringIdByActionValue(i)));
            }
            relativeLayout.setGravity(17);
            relativeLayout2.setGravity(17);
            relativeLayout2.setTag(R.id.grid_button, Integer.valueOf(i));
        }
        return z5;
    }

    public static void setRatioPadding(Context context, View view, double d, double d2, double d3, double d4) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        int i = point.x;
        int i2 = point.y;
        view.setPadding((int) ((i * d) / 100.0d), (int) ((i2 * d2) / 100.0d), (int) ((i * d3) / 100.0d), (int) ((i2 * d4) / 100.0d));
    }
}
