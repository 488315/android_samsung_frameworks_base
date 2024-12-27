package com.android.systemui.volume.util;

import com.android.systemui.Dependency;
import com.android.systemui.util.DesktopManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DesktopManagerWrapper {
    public final DesktopManager desktopManager = (DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class);
}
