package com.android.systemui;

import android.os.Build;
import android.os.FactoryTest;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import com.android.systemui.util.DeviceType;
import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class QpRune extends Rune {
    public static final boolean QUICK_BAR_BRIGHTNESS_EXTRA_BRIGHTNESS;
    public static final boolean QUICK_BAR_BRIGHTNESS_PERSONAL_CONTROL;
    public static final boolean QUICK_BAR_MULTISIM;
    public static final boolean QUICK_BLUETOOTH_MUSIC_SHARE;
    public static final boolean QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR;
    public static final boolean QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR_HIJRI;
    public static final boolean QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR_LUNAR_IN_VIETNAM;
    public static final boolean QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR_PERSIAN;
    public static final boolean QUICK_DATA_USAGE_LABEL;
    public static final boolean QUICK_MUM_TWO_PHONE;
    public static final boolean QUICK_PANEL_BLUR_DEFAULT;
    public static final boolean QUICK_PANEL_BLUR_MASSIVE;
    public static final boolean QUICK_PANEL_GUIDE;
    public static final boolean QUICK_SUBSCREEN_PANEL;
    public static final boolean QUICK_SUBSCREEN_PANEL_WINDOW;
    public static final boolean QUICK_SUBSCREEN_SETTINGS;
    public static final boolean QUICK_TABLET;
    public static final boolean QUICK_TABLET_BG;
    public static final boolean QUICK_TILE_BLUELIGHT_FILTER_ADAPTIVE_MODE;
    public static final boolean QUICK_TILE_BLUELIGHT_FILTER_NIGHT_DIM;
    public static final boolean QUICK_TILE_FLASHLIGHT_INTENSITY;
    public static final boolean QUICK_TILE_HIDE_FROM_BAR;
    public static final boolean QUICK_TILE_ROTATION_MANUAL;

    static {
        boolean z = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
        QUICK_PANEL_BLUR_DEFAULT = z;
        if (!"1".equalsIgnoreCase(SystemProperties.get("ro.surface_flinger.protected_contents"))) {
            "true".equalsIgnoreCase(SystemProperties.get("ro.surface_flinger.protected_contents"));
        }
        QUICK_PANEL_BLUR_MASSIVE = !z && SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_CAPTURED_BLUR");
        QUICK_PANEL_GUIDE = !FactoryTest.isFactoryBinary();
        boolean isTablet = DeviceType.isTablet();
        QUICK_TABLET = isTablet;
        QUICK_TABLET_BG = isTablet;
        boolean equalsIgnoreCase = "IR".equalsIgnoreCase(SemSystemProperties.getCountryIso());
        QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR_PERSIAN = equalsIgnoreCase;
        boolean z2 = SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportHijriLunarCalendar", false);
        QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR_HIJRI = z2;
        boolean equals = "VI".equals(SemCscFeature.getInstance().getString("CscFeature_Calendar_EnableLocalHolidayDisplay", ""));
        QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR_LUNAR_IN_VIETNAM = equals;
        QUICK_CLOCK_BELL_TOWER_ALTERNATE_CALENDAR = equalsIgnoreCase || z2 || equals;
        QUICK_MUM_TWO_PHONE = SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportTwoPhoneService");
        QUICK_BAR_BRIGHTNESS_PERSONAL_CONTROL = List.of("3", "4", "5").contains("5");
        QUICK_BAR_BRIGHTNESS_EXTRA_BRIGHTNESS = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_EXTRA_BRIGHTNESS");
        QUICK_BAR_MULTISIM = Rune.SYSUI_MULTI_SIM;
        QUICK_TILE_BLUELIGHT_FILTER_ADAPTIVE_MODE = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_SUPPORT_BLUE_FILTER_ADAPTIVE_MODE", 0) != 0;
        QUICK_TILE_BLUELIGHT_FILTER_NIGHT_DIM = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_NIGHT_DIM", 0) == 1;
        QUICK_BLUETOOTH_MUSIC_SHARE = SemBluetoothCastAdapter.isBluetoothCastSupported();
        QUICK_TILE_FLASHLIGHT_INTENSITY = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CAMERA_SUPPORT_TORCH_BRIGHTNESS_LEVEL");
        QUICK_TILE_HIDE_FROM_BAR = SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportZProjectFunctionInGlobal", false);
        QUICK_TILE_ROTATION_MANUAL = !SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_NAVIGATION_BAR_THEME", "").isEmpty();
        String string = ("user".equals(Build.TYPE) || (SystemProperties.getInt("persist.debug.subdisplay_test_mode", 0) & 1) == 0) ? SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY") : "";
        boolean z3 = NotiRune.NOTI_SUBSCREEN_NOTIFICATION_SECOND;
        QUICK_SUBSCREEN_SETTINGS = z3;
        boolean z4 = z3 && string.contains("LARGESCREEN");
        QUICK_SUBSCREEN_PANEL = z4;
        QUICK_SUBSCREEN_PANEL_WINDOW = z4;
        QUICK_DATA_USAGE_LABEL = Rune.SYSUI_CHINA_FEATURE;
    }
}
