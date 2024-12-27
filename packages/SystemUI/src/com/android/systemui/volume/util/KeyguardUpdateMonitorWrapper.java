package com.android.systemui.volume.util;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class KeyguardUpdateMonitorWrapper {
    public KeyguardUpdateMonitorWrapper$registerCallback$1 callback;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
}
