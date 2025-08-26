package com.samsung.android.multiwindow;

import android.os.Bundle;

/* loaded from: classes6.dex */
public class MultiWindowCoreState {
    public static float FONT_SCALE_FOR_EXTERNAL_DESKTOP = 1.0f;
    public static boolean MW_ENABLED = false;
    public static int MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE = 0;
    public static boolean MW_FREEFORM_CORNER_GESTURE_ENABLED = false;
    public static boolean MW_MULTISTAR_BLOCKED_MINIMIZED_FREEFORM_ENABLED = false;
    public static int MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED = 0;
    public static boolean MW_MULTISTAR_ENSURE_LAUNCH_SPLIT_ENABLED = false;
    public static boolean MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED = false;
    public static boolean MW_MULTISTAR_STAY_TOP_RESUMED_ACTIVITY_DYNAMIC_ENABLED = false;
    public static boolean MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED = false;
    public static final String MW_SHARED_PREF_NAME = "multiwindow.property";
    public static boolean MW_SPLIT_IMMERSIVE_MODE_ENABLED = false;
    public static final String TAG = "MultiWindowCoreState";
    private final Object mLock = new Object();

    public static final class Diff {
        public static final int DW_FONT_SCALE_FOR_EXTERNAL_DESKTOP = 32768;
        public static final int MW_ENABLED = 1;
        public static final int MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE = 8192;
        public static final int MW_FREEFORM_CORNER_GESTURE_ENABLED = 16;
        public static final int MW_MULTISTAR_BLOCKED_MINIMIZED_FREEFORM_ENABLED = 2048;
        public static final int MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED = 32;
        public static final int MW_MULTISTAR_ENSURE_LAUNCH_SPLIT_ENABLED = 16384;
        public static final int MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED = 64;
        public static final int MW_MULTISTAR_STAY_TOP_RESUMED_ACTIVITY_DYNAMIC_ENABLED = 128;
        public static final int MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED = 1024;
        public static final int MW_SPLIT_IMMERSIVE_MODE_ENABLED = 512;
    }

    public static final class Key {
        public static final String DW_FONT_SCALE_FOR_EXTERNAL_DESKTOP = "font_scale_for_external_desktop";
        public static final String MW_ENABLED = "mw_enabled";
        public static final String MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE = "corner_gesture_custom_value";
        public static final String MW_FREEFORM_CORNER_GESTURE_ENABLED = "open_in_pop_up_view";
        public static final String MW_MULTISTAR_BLOCKED_MINIMIZED_FREEFORM_ENABLED = "mw_blocked_minimized_freeform";
        public static final String MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED = "custom_density";
        public static final String MW_MULTISTAR_ENSURE_LAUNCH_SPLIT_ENABLED = "mw_ensure_launch_split";
        public static final String MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED = "stay_focus_activity";
        public static final String MW_MULTISTAR_STAY_TOP_RESUMED_ACTIVITY_DYNAMIC_ENABLED = "stay_top_resumed_activity";
        public static final String MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED = "mw_navibar_immersive_mode";
        public static final String MW_SPLIT_IMMERSIVE_MODE_ENABLED = "mw_immersive_mode";
    }

    public interface MultiWindowCoreStateListener {
        void onMultiWindowCoreStateChanged(int i);
    }

    private static class LazyHolder {
        private static final MultiWindowCoreState sInstance = new MultiWindowCoreState();

        private LazyHolder() {
        }
    }

    public static MultiWindowCoreState getInstance() {
        return LazyHolder.sInstance;
    }

    public int updateFrom(Bundle bundle) {
        int iUpdateFontScaleForExternalDesktop;
        if (bundle == null) {
            return 0;
        }
        synchronized (this.mLock) {
            iUpdateFontScaleForExternalDesktop = updateFontScaleForExternalDesktop(bundle) | updateMultiWindowEnabledState(bundle) | updateCornerGestureState(bundle) | updateCornerGestureCustomValue(bundle) | updateMultiStarSupportCustomDensityState(bundle) | updateSplitImmersiveModeState(bundle) | updateNaviStarSplitImmersiveModeState(bundle) | updateMultiStarSupportStayFocusActivity(bundle) | updateMultiStarSupportStayTopResumedActivity(bundle) | updateMultiStarBlockedMinimizeFreeformState(bundle) | updateMultiStarEnsureLaunchSplitState(bundle);
        }
        return iUpdateFontScaleForExternalDesktop;
    }

    private int updateCornerGestureCustomValue(Bundle bundle) {
        int i = MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE;
        int i2 = bundle.getInt(Key.MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE, 0);
        if (MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE != i2) {
            MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE = i2;
        }
        return MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE != i ? 8192 : 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0012  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int updateCornerGestureState(Bundle bundle) {
        boolean z;
        boolean z2 = MW_FREEFORM_CORNER_GESTURE_ENABLED;
        if (MW_ENABLED) {
            z = bundle.getInt("open_in_pop_up_view", 0) == 1;
        }
        MW_FREEFORM_CORNER_GESTURE_ENABLED = z;
        return z != z2 ? 16 : 0;
    }

    private int updateMultiWindowEnabledState(Bundle bundle) {
        boolean z = MW_ENABLED;
        boolean z2 = bundle.getInt(Key.MW_ENABLED, 1) == 1;
        MW_ENABLED = z2;
        return z2 != z ? 1 : 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(TAG + "{");
        sb.append("mw_enabled=");
        sb.append(MW_ENABLED);
        sb.append(", f_ges=" + MW_FREEFORM_CORNER_GESTURE_ENABLED);
        sb.append(", density=" + MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED);
        sb.append(", immersive=" + MW_SPLIT_IMMERSIVE_MODE_ENABLED);
        sb.append(", nav_immersive=" + MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED);
        sb.append(", minimize_block=" + MW_MULTISTAR_BLOCKED_MINIMIZED_FREEFORM_ENABLED);
        sb.append(", ges_val=" + MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE);
        sb.append(", stay_focus=" + MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED);
        sb.append(", stay_top_resumed=" + MW_MULTISTAR_STAY_TOP_RESUMED_ACTIVITY_DYNAMIC_ENABLED);
        sb.append(", ensure_split=" + MW_MULTISTAR_ENSURE_LAUNCH_SPLIT_ENABLED);
        sb.append(", font_scale_for_external_desktop_display=" + FONT_SCALE_FOR_EXTERNAL_DESKTOP);
        sb.append("}");
        return sb.toString();
    }

    private int updateMultiStarBlockedMinimizeFreeformState(Bundle bundle) {
        boolean z = MW_MULTISTAR_BLOCKED_MINIMIZED_FREEFORM_ENABLED;
        boolean z2 = bundle.getInt(Key.MW_MULTISTAR_BLOCKED_MINIMIZED_FREEFORM_ENABLED, 0) == 1;
        MW_MULTISTAR_BLOCKED_MINIMIZED_FREEFORM_ENABLED = z2;
        return z2 != z ? 2048 : 0;
    }

    private int updateSplitImmersiveModeState(Bundle bundle) {
        boolean z = MW_SPLIT_IMMERSIVE_MODE_ENABLED;
        boolean z2 = bundle.getInt(Key.MW_SPLIT_IMMERSIVE_MODE_ENABLED, 0) == 1;
        MW_SPLIT_IMMERSIVE_MODE_ENABLED = z2;
        return z2 != z ? 512 : 0;
    }

    private int updateNaviStarSplitImmersiveModeState(Bundle bundle) {
        boolean z = MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED;
        boolean z2 = bundle.getInt(Key.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED, 0) == 1;
        MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED = z2;
        return z2 != z ? 1024 : 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0012  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int updateMultiStarSupportStayFocusActivity(Bundle bundle) {
        boolean z;
        boolean z2 = MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED;
        if (MW_ENABLED) {
            z = bundle.getInt(Key.MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED, 0) == 1;
        }
        MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED = z;
        return z != z2 ? 64 : 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0012  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int updateMultiStarSupportStayTopResumedActivity(Bundle bundle) {
        boolean z;
        boolean z2 = MW_MULTISTAR_STAY_TOP_RESUMED_ACTIVITY_DYNAMIC_ENABLED;
        if (MW_ENABLED) {
            z = bundle.getInt(Key.MW_MULTISTAR_STAY_TOP_RESUMED_ACTIVITY_DYNAMIC_ENABLED, 0) == 1;
        }
        MW_MULTISTAR_STAY_TOP_RESUMED_ACTIVITY_DYNAMIC_ENABLED = z;
        return z != z2 ? 128 : 0;
    }

    private int updateMultiStarSupportCustomDensityState(Bundle bundle) {
        int i = MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED;
        int i2 = bundle.getInt(Key.MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED, 0);
        MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED = i2;
        return i2 != i ? 32 : 0;
    }

    private int updateMultiStarEnsureLaunchSplitState(Bundle bundle) {
        boolean z = MW_MULTISTAR_ENSURE_LAUNCH_SPLIT_ENABLED;
        boolean z2 = bundle.getInt(Key.MW_MULTISTAR_ENSURE_LAUNCH_SPLIT_ENABLED, 0) == 1;
        MW_MULTISTAR_ENSURE_LAUNCH_SPLIT_ENABLED = z2;
        return z2 != z ? 16384 : 0;
    }

    private int updateFontScaleForExternalDesktop(Bundle bundle) {
        float f = FONT_SCALE_FOR_EXTERNAL_DESKTOP;
        float f2 = bundle.getFloat(Key.DW_FONT_SCALE_FOR_EXTERNAL_DESKTOP, 1.0f);
        FONT_SCALE_FOR_EXTERNAL_DESKTOP = f2;
        return f2 != f ? 32768 : 0;
    }
}
