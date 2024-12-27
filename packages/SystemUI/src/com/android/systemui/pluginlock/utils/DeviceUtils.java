package com.android.systemui.pluginlock.utils;

import com.android.systemui.Dependency;
import com.android.systemui.util.DesktopManager;
import com.samsung.android.desktopmode.SemDesktopModeState;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
