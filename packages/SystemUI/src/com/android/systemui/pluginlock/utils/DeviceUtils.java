package com.android.systemui.pluginlock.utils;

import com.android.systemui.Dependency;
import com.android.systemui.util.DesktopManager;
import com.samsung.android.desktopmode.SemDesktopModeState;

public class DeviceUtils {
    public static boolean isDesktopMode() {
        SemDesktopModeState semDesktopModeState;
        DesktopManager desktopManager = (DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class);
        if (desktopManager == null || (semDesktopModeState = desktopManager.getSemDesktopModeState()) == null) {
            return false;
        }
        return semDesktopModeState.state != 0 || semDesktopModeState.getEnabled() == 4;
    }
}
