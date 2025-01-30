package com.android.wm.shell.controlpanel.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.core.content.ContextCompat;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.wm.shell.controlpanel.action.ControlPanelAction;
import com.android.systemui.R;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.context.sdk.samsunganalytics.LogBuilders$EventBuilder;
import com.samsung.context.sdk.samsunganalytics.SamsungAnalytics;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ControlPanelUtils {
    public static final String TALKBACK_SERVICE = "com.samsung.android.marvin.talkback.TalkBackService";
    static final String UNIVERSAL_SWITCH_SERVICE = "com.samsung.accessibility.universalswitch.UniversalSwitchService";

    public static void eventLogging(String str, String str2, Map map) {
        map.put("det", str2);
        Log.d("FlexPanelSALogging", "eventName : " + str + ", detail : " + str2 + ", customDimen : " + map);
        SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
        LogBuilders$EventBuilder logBuilders$EventBuilder = new LogBuilders$EventBuilder();
        logBuilders$EventBuilder.setEventName(str);
        logBuilders$EventBuilder.setDimension(map);
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
            if (!"com.android.systemui.stackdivider.ForcedResizableInfoActivity".equals(componentName.getClassName())) {
                return componentName.getPackageName();
            }
            ActivityManager.RunningTaskInfo runningTaskExcept2 = getRunningTaskExcept(context);
            return runningTaskExcept2 != null ? runningTaskExcept2.baseActivity.getPackageName() : "";
        } catch (NullPointerException e) {
            Log.e("ControlPanelUtils", e.toString(), e);
            return "";
        }
    }

    public static LinearLayout.LayoutParams getRatioLayoutParams(Context context, double d, double d2) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
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
        if (runningTasks.size() < 2) {
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m78m(5, new StringBuilder("no running Tasks callers="), "ControlPanelUtils");
            return null;
        }
        String shortClassName = runningTasks.get(0).topActivity.getShortClassName();
        return runningTasks.get(("com.android.wm.shell.controlpanel.activity.FlexPanelActivity".equalsIgnoreCase(shortClassName) || "com.android.wm.shell.controlpanel.activity.VideoControlsActivity".equalsIgnoreCase(shortClassName)) ? 1 : 0);
    }

    public static ComponentName getTopActivity(Context context) {
        ActivityManager.RunningTaskInfo runningTaskExcept = getRunningTaskExcept(context);
        return runningTaskExcept != null ? runningTaskExcept.topActivity : new ComponentName("", "");
    }

    public static int getTopTaskUserId(Context context) {
        ActivityManager.RunningTaskInfo runningTaskExcept = getRunningTaskExcept(context);
        if (runningTaskExcept != null) {
            return runningTaskExcept.userId;
        }
        return 0;
    }

    public static boolean isAccessibilityEnabled(Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), "enabled_accessibility_services");
        if (string == null) {
            return false;
        }
        return string.contains(TALKBACK_SERVICE) || string.contains(UNIVERSAL_SWITCH_SERVICE);
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
        if (isKidsMode(context) || "com.sec.android.app.clockpackage.alarm.AlarmAlert".equalsIgnoreCase(str) || "com.sec.android.app.clockpackage.alarm.AlarmSmartAlert".equalsIgnoreCase(str) || "com.sec.android.app.clockpackage.timer.TimerAlarm".equalsIgnoreCase(str)) {
            return false;
        }
        return (Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 0) && !"com.samsung.android.app.telephonyui.emergencydialer.view.EmergencyDialerActivity".equals(str);
    }

    public static boolean isTouchPadEnabled(SharedPreferences sharedPreferences) {
        if (!sharedPreferences.getBoolean("TOUCH_PAD_ENABLED", true) || sharedPreferences.getBoolean("MEDIA_PANEL", false)) {
            return sharedPreferences.getBoolean("MEDIA_TOUCH_PAD_ENABLED", false) && sharedPreferences.getBoolean("MEDIA_PANEL", false);
        }
        return true;
    }

    public static boolean isTypeFold() {
        return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
    }

    public static boolean isWheelActive(Context context) {
        int i = Settings.Global.getInt(context.getContentResolver(), "flex_mode_scroll_wheel_pos", 2);
        return (i == -1 || i == 0) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x011d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean makeGridButton(Context context, RelativeLayout relativeLayout, int i, int i2, boolean z, boolean z2) {
        String str;
        boolean z3;
        ImageButton imageButton = (ImageButton) relativeLayout.findViewById(R.id.menubutton);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.menubutton_icon);
        RelativeLayout relativeLayout2 = (RelativeLayout) relativeLayout.findViewById(R.id.grid_button);
        RelativeLayout.LayoutParams ratioRelativeLayoutParams = isTypeFold() ? getRatioRelativeLayoutParams(context, 3.37d, 4.1d) : getRatioRelativeLayoutParams(context, 8.88d, 3.64d);
        ratioRelativeLayoutParams.addRule(13);
        imageView.setLayoutParams(ratioRelativeLayoutParams);
        relativeLayout.findViewById(R.id.button_focus).setLayoutParams(ratioRelativeLayoutParams);
        RelativeLayout.LayoutParams ratioRelativeLayoutParams2 = isTypeFold() ? getRatioRelativeLayoutParams(context, 4.82d, 5.7d) : getRatioRelativeLayoutParams(context, 11.11d, 4.55d);
        ratioRelativeLayoutParams2.addRule(13);
        imageButton.setLayoutParams(ratioRelativeLayoutParams2);
        relativeLayout.setGravity(17);
        relativeLayout.setLayoutParams(ratioRelativeLayoutParams2);
        relativeLayout2.semSetHoverPopupType(1);
        imageView.setBackgroundResource(i2);
        imageView.setBackgroundTintList(ContextCompat.getColorStateList(R.color.panel_menu_icon_color_expand, context));
        boolean z4 = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences("flex_panel_pref", 0);
        if (i == ControlPanelAction.Action.QuickPanel.getValue()) {
            try {
                str = getTopActivity(context).getClassName();
            } catch (NullPointerException e) {
                Log.e("ControlPanelUtils", e.toString(), e);
                str = "";
            }
            if (!isQuickPanelPressAvailable(context, str)) {
                relativeLayout.setEnabled(false);
                relativeLayout.setAlpha(0.4f);
                z3 = false;
                if (i == ControlPanelAction.Action.TouchPad.getValue() && isTouchPadEnabled(sharedPreferences)) {
                    imageButton.setBackgroundResource(R.drawable.grid_button_background);
                    imageButton.setBackgroundTintList(ContextCompat.getColorStateList(R.color.panel_menu_icon_selected_color, context));
                    imageButton.setVisibility(0);
                }
                if (i == ControlPanelAction.Action.DragCircle.getValue()) {
                    imageButton.setBackgroundResource(R.drawable.drag_circle_background);
                    imageButton.setBackgroundTintList(ContextCompat.getColorStateList(R.color.drag_circle_stroke_color, context));
                    imageButton.setVisibility(0);
                }
                if (((ActivityManager) context.getSystemService("activity")).getLockTaskModeState() != 0) {
                    z4 = z3;
                } else {
                    relativeLayout.setEnabled(false);
                    relativeLayout.setAlpha(0.4f);
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
                return z4;
            }
        }
        z3 = true;
        if (i == ControlPanelAction.Action.TouchPad.getValue()) {
            imageButton.setBackgroundResource(R.drawable.grid_button_background);
            imageButton.setBackgroundTintList(ContextCompat.getColorStateList(R.color.panel_menu_icon_selected_color, context));
            imageButton.setVisibility(0);
        }
        if (i == ControlPanelAction.Action.DragCircle.getValue()) {
        }
        if (((ActivityManager) context.getSystemService("activity")).getLockTaskModeState() != 0) {
        }
        if (z) {
        }
        return z4;
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
