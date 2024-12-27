package com.samsung.android.core;

import android.app.ActivityThread;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.graphics.Rect;
import android.view.DisplayInfo;
import android.view.InsetsSourceControl;
import android.view.MotionEvent;

/* loaded from: classes6.dex */
public class CompatSandbox {
    public static final int APP_COMPAT_OVERRIDE_ENABLED = 256;
    public static final float OFFSET_DEFAULT = 0.0f;
    public static final int SANDBOX_DISABLED = 1;
    public static final int SANDBOX_DISPLAY = 2;
    public static final int SANDBOX_INSETS_HINT = 32;
    public static final int SANDBOX_MOCK_FULL_SCREEN = 16;
    public static final int SANDBOX_MOTION_EVENT = 8;
    public static final int SANDBOX_UNDEFINED = 0;
    public static final int SANDBOX_VIEW_BOUNDS = 4;
    public static final float SCALE_DEFAULT = 1.0f;
    public static final float SCALE_UNDEFINED = -1.0f;

    private static class LazyHolder {
        private static final Rect EMPTY_RECT = new Rect();

        private LazyHolder() {}
    }

    public static Rect getEmptyRect() {
        return LazyHolder.EMPTY_RECT;
    }

    public static boolean isAppCompatOverrideEnabled(Configuration config) {
        return config != null && hasCompatSandboxFlags(config, 256);
    }

    public static void applyDisplaySandboxingIfNeeded(DisplayInfo info) {
        ActivityThread thread = ActivityThread.currentActivityThread();
        Configuration currentConfig = thread != null ? thread.getConfiguration() : null;
        if (currentConfig == null || !hasCompatSandboxFlags(currentConfig, 2)) {
            return;
        }
        Rect appBounds = currentConfig.windowConfiguration.getAppBounds();
        info.appWidth = appBounds.width();
        info.appHeight = appBounds.height();
        Rect maxBounds = currentConfig.windowConfiguration.getMaxBounds();
        info.logicalWidth = maxBounds.width();
        info.logicalHeight = maxBounds.height();
    }

    public static boolean applyViewBoundsSandboxingIfNeeded(
            Configuration config, Rect inOutRect, boolean inverse) {
        if (!hasCompatSandboxFlags(config, 4)) {
            return false;
        }
        Rect bounds = config.windowConfiguration.getCompatSandboxScaledBounds();
        int left = bounds.left;
        if (!inverse) {
            left = -left;
        }
        int top = bounds.top;
        if (!inverse) {
            top = -top;
        }
        inOutRect.offset(left, top);
        return true;
    }

    public static boolean applyViewLocationSandboxingIfNeeded(
            Configuration config, int[] outLocation) {
        if (!hasCompatSandboxFlags(config, 4)) {
            return false;
        }
        Rect bounds = config.windowConfiguration.getCompatSandboxScaledBounds();
        outLocation[0] = outLocation[0] - bounds.left;
        outLocation[1] = outLocation[1] - bounds.top;
        return true;
    }

    public static void applyMotionEventSandboxingIfNeeded(Configuration config, MotionEvent event) {
        if (!hasCompatSandboxFlags(config, 8)) {
            return;
        }
        Rect bounds = config.windowConfiguration.getCompatSandboxBounds();
        float xOffset = -bounds.left;
        float yOffset = -bounds.top;
        float invScale = config.windowConfiguration.getCompatSandboxInvScale();
        float overrideInvertedScale = CompatibilityInfo.getOverrideInvertedScale();
        event.setCompatSandboxScale(xOffset, yOffset, invScale * overrideInvertedScale);
    }

    public static void applyInsetsHintSandboxingIfNeeded(
            Configuration config, InsetsSourceControl[] controls) {
        if (!hasCompatSandboxFlags(config, 32)) {
            return;
        }
        float scale = config.windowConfiguration.getCompatSandboxInvScale();
        if (scale == 1.0f) {
            return;
        }
        for (InsetsSourceControl control : controls) {
            if (control != null) {
                Insets hint = control.getInsetsHint();
                control.setInsetsHint(
                        (int) (hint.left * scale),
                        (int) (hint.top * scale),
                        (int) (hint.right * scale),
                        (int) (hint.bottom * scale));
            }
        }
    }

    public static int getCompatWindowingMode(Configuration config, int defaultValue) {
        if (!hasCompatSandboxFlags(config, 16)) {
            return defaultValue;
        }
        return 1;
    }

    public static boolean updateConfigWithoutWindowConfigurationIfNeeded(
            Configuration newConfig, Configuration base, Configuration override) {
        int flags = base.windowConfiguration.getCompatSandboxFlags();
        if (flags != 0 || flags != override.windowConfiguration.getCompatSandboxFlags()) {
            newConfig.updateFrom(override, true);
            return true;
        }
        return false;
    }

    public static void resetCompatSandBoxValuesIfNeeded(
            Configuration config, Configuration overrideConfig) {
        if (config.windowConfiguration.getCompatSandboxFlags() != 0
                && overrideConfig.windowConfiguration.getCompatSandboxFlags() == 0) {
            config.windowConfiguration.setCompatSandboxValues(0, -1.0f, null);
        }
    }

    private static boolean hasCompatSandboxFlags(Configuration config, int mask) {
        return (config.windowConfiguration.getCompatSandboxFlags() & mask) != 0;
    }
}
