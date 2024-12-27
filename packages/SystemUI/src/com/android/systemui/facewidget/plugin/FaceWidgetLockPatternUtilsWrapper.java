package com.android.systemui.facewidget.plugin;

import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.plugins.keyguardstatusview.PluginLockPatternUtils;

public final class FaceWidgetLockPatternUtilsWrapper implements PluginLockPatternUtils {
    public final KeyguardUpdateMonitor mUpdateMonitor;

    public FaceWidgetLockPatternUtilsWrapper(LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mUpdateMonitor = keyguardUpdateMonitor;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginLockPatternUtils
    public final String getDeviceOwnerInfo() {
        return this.mUpdateMonitor.getDeviceOwnerInfo();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginLockPatternUtils
    public final String getOwnerInfo(int i) {
        return this.mUpdateMonitor.getOwnerInfo();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginLockPatternUtils
    public final boolean isDeviceOwnerInfoEnabled() {
        return this.mUpdateMonitor.isDeviceOwnerInfoEnabled();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginLockPatternUtils
    public final boolean isOwnerInfoEnabled(int i) {
        return this.mUpdateMonitor.isOwnerInfoEnabled();
    }
}
