package com.android.systemui.volume.util;

import com.android.systemui.Dependency;
import com.android.systemui.util.DesktopManager;

public final class DesktopManagerWrapper {
    public final DesktopManager desktopManager = (DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class);
}
