package com.samsung.android.multiwindow;

import android.os.Bundle;

/* loaded from: classes6.dex */
public class MultiWindowCoreState {
    public static final String MW_SHARED_PREF_NAME = "multiwindow.property";
    private final Object mLock = new Object();
    public static final String TAG = MultiWindowCoreState.class.getSimpleName();
    public static boolean MW_ENABLED = false;
    public static boolean MW_MULTISTAR_BLOCKED_MINIMIZED_FREEFORM_ENABLED = false;
    public static boolean MW_FREEFORM_CORNER_GESTURE_ENABLED = false;
    public static int MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE = 0;
    public static boolean MW_SPLIT_IMMERSIVE_MODE_ENABLED = false;
    public static boolean MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED = false;
    public static int MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED = 0;
    public static boolean MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED = false;
    public static boolean MW_MULTISTAR_STAY_TOP_RESUMED_ACTIVITY_DYNAMIC_ENABLED = false;
    public static boolean MW_MULTISTAR_ENSURE_LAUNCH_SPLIT_ENABLED = false;
    public static float DEX_FONT_SCALE = 1.0f;

    public static final class Diff {
        public static final int DEX_FONT_SCALE = 32768;
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
        public static final String DEX_FONT_SCALE = "dex_font_scale";
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

    public int updateFrom(Bundle state) {
        int changes;
        if (state == null) {
            return 0;
        }
        synchronized (this.mLock) {
            int changes2 = 0 | updateMultiWindowEnabledState(state);
            changes = changes2 | updateCornerGestureState(state) | updateCornerGestureCustomValue(state) | updateMultiStarSupportCustomDensityState(state) | updateSplitImmersiveModeState(state) | updateNaviStarSplitImmersiveModeState(state) | updateMultiStarSupportStayFocusActivity(state) | updateMultiStarSupportStayTopResumedActivity(state) | updateMultiStarBlockedMinimizeFreeformState(state) | updateMultiStarEnsureLaunchSplitState(state) | updateDexFontScale(state);
        }
        return changes;
    }

    private int updateCornerGestureCustomValue(Bundle state) {
        int lastCustomValue = MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE;
        int customValue = state.getInt(Key.MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE, 0);
        if (MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE != customValue) {
            MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE = customValue;
        }
        return MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE != lastCustomValue ? 8192 : 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x000e, code lost:
    
        if (r5.getInt("open_in_pop_up_view", 0) == 1) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int updateCornerGestureState(android.os.Bundle r5) {
        /*
            r4 = this;
            boolean r0 = com.samsung.android.multiwindow.MultiWindowCoreState.MW_FREEFORM_CORNER_GESTURE_ENABLED
            boolean r1 = com.samsung.android.multiwindow.MultiWindowCoreState.MW_ENABLED
            r2 = 0
            if (r1 == 0) goto L11
            java.lang.String r1 = "open_in_pop_up_view"
            int r1 = r5.getInt(r1, r2)
            r3 = 1
            if (r1 != r3) goto L11
            goto L12
        L11:
            r3 = r2
        L12:
            com.samsung.android.multiwindow.MultiWindowCoreState.MW_FREEFORM_CORNER_GESTURE_ENABLED = r3
            boolean r1 = com.samsung.android.multiwindow.MultiWindowCoreState.MW_FREEFORM_CORNER_GESTURE_ENABLED
            if (r1 == r0) goto L1b
            r2 = 16
            goto L1c
        L1b:
        L1c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.multiwindow.MultiWindowCoreState.updateCornerGestureState(android.os.Bundle):int");
    }

    private int updateMultiWindowEnabledState(Bundle state) {
        boolean wasEnabled = MW_ENABLED;
        MW_ENABLED = state.getInt(Key.MW_ENABLED, 1) == 1;
        return MW_ENABLED != wasEnabled ? 1 : 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(TAG + "{");
        sb.append("mw_enabled=").append(MW_ENABLED);
        sb.append(", f_ges=" + MW_FREEFORM_CORNER_GESTURE_ENABLED);
        sb.append(", density=" + MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED);
        sb.append(", immersive=" + MW_SPLIT_IMMERSIVE_MODE_ENABLED);
        sb.append(", nav_immersive=" + MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED);
        sb.append(", minimize_block=" + MW_MULTISTAR_BLOCKED_MINIMIZED_FREEFORM_ENABLED);
        sb.append(", ges_val=" + MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE);
        sb.append(", stay_focus=" + MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED);
        sb.append(", stay_top_resumed=" + MW_MULTISTAR_STAY_TOP_RESUMED_ACTIVITY_DYNAMIC_ENABLED);
        sb.append(", ensure_split=" + MW_MULTISTAR_ENSURE_LAUNCH_SPLIT_ENABLED);
        sb.append(", dex_font=" + DEX_FONT_SCALE);
        sb.append("}");
        return sb.toString();
    }

    private int updateMultiStarBlockedMinimizeFreeformState(Bundle state) {
        boolean wasEnabled = MW_MULTISTAR_BLOCKED_MINIMIZED_FREEFORM_ENABLED;
        MW_MULTISTAR_BLOCKED_MINIMIZED_FREEFORM_ENABLED = state.getInt(Key.MW_MULTISTAR_BLOCKED_MINIMIZED_FREEFORM_ENABLED, 0) == 1;
        return MW_MULTISTAR_BLOCKED_MINIMIZED_FREEFORM_ENABLED != wasEnabled ? 2048 : 0;
    }

    private int updateSplitImmersiveModeState(Bundle state) {
        boolean lastImmersiveModeEnabled = MW_SPLIT_IMMERSIVE_MODE_ENABLED;
        MW_SPLIT_IMMERSIVE_MODE_ENABLED = state.getInt(Key.MW_SPLIT_IMMERSIVE_MODE_ENABLED, 0) == 1;
        return MW_SPLIT_IMMERSIVE_MODE_ENABLED != lastImmersiveModeEnabled ? 512 : 0;
    }

    private int updateNaviStarSplitImmersiveModeState(Bundle state) {
        boolean lastNaviOnlyImmersiveModeEnabled = MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED;
        MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED = state.getInt(Key.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED, 0) == 1;
        return MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED != lastNaviOnlyImmersiveModeEnabled ? 1024 : 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x000e, code lost:
    
        if (r5.getInt(com.samsung.android.multiwindow.MultiWindowCoreState.Key.MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED, 0) == 1) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int updateMultiStarSupportStayFocusActivity(android.os.Bundle r5) {
        /*
            r4 = this;
            boolean r0 = com.samsung.android.multiwindow.MultiWindowCoreState.MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED
            boolean r1 = com.samsung.android.multiwindow.MultiWindowCoreState.MW_ENABLED
            r2 = 0
            if (r1 == 0) goto L11
            java.lang.String r1 = "stay_focus_activity"
            int r1 = r5.getInt(r1, r2)
            r3 = 1
            if (r1 != r3) goto L11
            goto L12
        L11:
            r3 = r2
        L12:
            com.samsung.android.multiwindow.MultiWindowCoreState.MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED = r3
            boolean r1 = com.samsung.android.multiwindow.MultiWindowCoreState.MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED
            if (r1 == r0) goto L1b
            r2 = 64
            goto L1c
        L1b:
        L1c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.multiwindow.MultiWindowCoreState.updateMultiStarSupportStayFocusActivity(android.os.Bundle):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x000e, code lost:
    
        if (r5.getInt(com.samsung.android.multiwindow.MultiWindowCoreState.Key.MW_MULTISTAR_STAY_TOP_RESUMED_ACTIVITY_DYNAMIC_ENABLED, 0) == 1) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int updateMultiStarSupportStayTopResumedActivity(android.os.Bundle r5) {
        /*
            r4 = this;
            boolean r0 = com.samsung.android.multiwindow.MultiWindowCoreState.MW_MULTISTAR_STAY_TOP_RESUMED_ACTIVITY_DYNAMIC_ENABLED
            boolean r1 = com.samsung.android.multiwindow.MultiWindowCoreState.MW_ENABLED
            r2 = 0
            if (r1 == 0) goto L11
            java.lang.String r1 = "stay_top_resumed_activity"
            int r1 = r5.getInt(r1, r2)
            r3 = 1
            if (r1 != r3) goto L11
            goto L12
        L11:
            r3 = r2
        L12:
            com.samsung.android.multiwindow.MultiWindowCoreState.MW_MULTISTAR_STAY_TOP_RESUMED_ACTIVITY_DYNAMIC_ENABLED = r3
            boolean r1 = com.samsung.android.multiwindow.MultiWindowCoreState.MW_MULTISTAR_STAY_TOP_RESUMED_ACTIVITY_DYNAMIC_ENABLED
            if (r1 == r0) goto L1b
            r2 = 128(0x80, float:1.794E-43)
            goto L1c
        L1b:
        L1c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.multiwindow.MultiWindowCoreState.updateMultiStarSupportStayTopResumedActivity(android.os.Bundle):int");
    }

    private int updateMultiStarSupportCustomDensityState(Bundle state) {
        int lastCustomDensityEnabledFlags = MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED;
        MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED = state.getInt(Key.MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED, 0);
        return MW_MULTISTAR_CUSTOM_DENSITY_DYNAMIC_ENABLED != lastCustomDensityEnabledFlags ? 32 : 0;
    }

    private int updateMultiStarEnsureLaunchSplitState(Bundle state) {
        boolean lastEnsureLaunchSplitEnabled = MW_MULTISTAR_ENSURE_LAUNCH_SPLIT_ENABLED;
        MW_MULTISTAR_ENSURE_LAUNCH_SPLIT_ENABLED = state.getInt(Key.MW_MULTISTAR_ENSURE_LAUNCH_SPLIT_ENABLED, 0) == 1;
        return MW_MULTISTAR_ENSURE_LAUNCH_SPLIT_ENABLED != lastEnsureLaunchSplitEnabled ? 16384 : 0;
    }

    private int updateDexFontScale(Bundle state) {
        float lastDexFontScale = DEX_FONT_SCALE;
        DEX_FONT_SCALE = state.getFloat(Key.DEX_FONT_SCALE, 1.0f);
        return DEX_FONT_SCALE != lastDexFontScale ? 32768 : 0;
    }
}
