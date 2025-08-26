package com.samsung.android.core;

import android.app.ActivityThread;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.graphics.Rect;
import android.util.Log;
import android.view.DisplayInfo;
import android.view.InsetsSourceControl;
import android.view.MotionEvent;
import android.view.View;

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

        private LazyHolder() {
        }
    }

    public static Rect getEmptyRect() {
        return LazyHolder.EMPTY_RECT;
    }

    public static boolean isAppCompatOverrideEnabled(Configuration configuration) {
        return configuration != null && hasCompatSandboxFlags(configuration, 256);
    }

    public static void applyDisplaySandboxingIfNeeded(DisplayInfo displayInfo) {
        ActivityThread activityThreadCurrentActivityThread = ActivityThread.currentActivityThread();
        Configuration configuration = activityThreadCurrentActivityThread != null ? activityThreadCurrentActivityThread.getConfiguration() : null;
        if (configuration == null || !hasCompatSandboxFlags(configuration, 2)) {
            return;
        }
        Rect appBounds = configuration.windowConfiguration.getAppBounds();
        displayInfo.appWidth = appBounds.width();
        displayInfo.appHeight = appBounds.height();
        Rect maxBounds = configuration.windowConfiguration.getMaxBounds();
        displayInfo.logicalWidth = maxBounds.width();
        displayInfo.logicalHeight = maxBounds.height();
    }

    public static boolean applyViewBoundsSandboxingIfNeeded(Configuration configuration, Rect rect, boolean z) {
        if (!hasCompatSandboxFlags(configuration, 4)) {
            return false;
        }
        Rect compatSandboxScaledBounds = configuration.windowConfiguration.getCompatSandboxScaledBounds();
        int i = compatSandboxScaledBounds.left;
        if (!z) {
            i = -i;
        }
        int i2 = compatSandboxScaledBounds.top;
        if (!z) {
            i2 = -i2;
        }
        rect.offset(i, i2);
        return true;
    }

    public static boolean applyViewLocationSandboxingIfNeeded(Configuration configuration, int[] iArr) {
        if (!hasCompatSandboxFlags(configuration, 4)) {
            return false;
        }
        Rect compatSandboxScaledBounds = configuration.windowConfiguration.getCompatSandboxScaledBounds();
        iArr[0] = iArr[0] - compatSandboxScaledBounds.left;
        iArr[1] = iArr[1] - compatSandboxScaledBounds.top;
        return true;
    }

    public static boolean shouldIgnoreInsetsAnimation(Configuration configuration, View view, String str) {
        if (view == null || !hasCompatSandboxFlags(configuration, 4) || !isAppCompatOverrideEnabled(configuration)) {
            return false;
        }
        int left = view.getLeft();
        int top = view.getTop();
        Rect compatSandboxScaledBounds = configuration.windowConfiguration.getCompatSandboxScaledBounds();
        if (left == compatSandboxScaledBounds.left && top == compatSandboxScaledBounds.top) {
            return false;
        }
        Log.d(str, "IgnoreInsetsAnimation: view=" + left + "," + top + " bounds=" + compatSandboxScaledBounds.left + ", " + compatSandboxScaledBounds.top);
        return true;
    }

    public static void applyMotionEventSandboxingIfNeeded(Configuration configuration, MotionEvent motionEvent) {
        if (hasCompatSandboxFlags(configuration, 8)) {
            Rect compatSandboxBounds = configuration.windowConfiguration.getCompatSandboxBounds();
            motionEvent.setCompatSandboxScale(-compatSandboxBounds.left, -compatSandboxBounds.top, configuration.windowConfiguration.getCompatSandboxInvScale() * CompatibilityInfo.getOverrideInvertedScale());
        }
    }

    public static void applyInsetsHintSandboxingIfNeeded(Configuration configuration, InsetsSourceControl[] insetsSourceControlArr) {
        if (hasCompatSandboxFlags(configuration, 32)) {
            float compatSandboxInvScale = configuration.windowConfiguration.getCompatSandboxInvScale();
            if (compatSandboxInvScale == 1.0f) {
                return;
            }
            for (InsetsSourceControl insetsSourceControl : insetsSourceControlArr) {
                if (insetsSourceControl != null) {
                    Insets insetsHint = insetsSourceControl.getInsetsHint();
                    insetsSourceControl.setInsetsHint((int) (insetsHint.left * compatSandboxInvScale), (int) (insetsHint.top * compatSandboxInvScale), (int) (insetsHint.right * compatSandboxInvScale), (int) (insetsHint.bottom * compatSandboxInvScale));
                }
            }
        }
    }

    public static int getCompatWindowingMode(Configuration configuration, int i) {
        if (hasCompatSandboxFlags(configuration, 16)) {
            return 1;
        }
        return i;
    }

    public static boolean updateConfigWithoutWindowConfigurationIfNeeded(Configuration configuration, Configuration configuration2, Configuration configuration3) {
        int compatSandboxFlags = configuration2.windowConfiguration.getCompatSandboxFlags();
        if (compatSandboxFlags == 0 && compatSandboxFlags == configuration3.windowConfiguration.getCompatSandboxFlags()) {
            return false;
        }
        configuration.updateFrom(configuration3, true);
        return true;
    }

    public static void resetCompatSandBoxValuesIfNeeded(Configuration configuration, Configuration configuration2) {
        if (configuration.windowConfiguration.getCompatSandboxFlags() == 0 || configuration2.windowConfiguration.getCompatSandboxFlags() != 0) {
            return;
        }
        configuration.windowConfiguration.setCompatSandboxValues(0, -1.0f, null);
    }

    private static boolean hasCompatSandboxFlags(Configuration configuration, int i) {
        return (configuration.windowConfiguration.getCompatSandboxFlags() & i) != 0;
    }
}
