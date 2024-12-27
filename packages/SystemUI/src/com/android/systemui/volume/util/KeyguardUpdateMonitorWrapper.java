package com.android.systemui.volume.util;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;

public final class KeyguardUpdateMonitorWrapper {
    public KeyguardUpdateMonitorWrapper$registerCallback$1 callback;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
}
