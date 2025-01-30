package com.android.systemui.facewidget.plugin;

import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.plugins.keyguardstatusview.PluginLockPatternUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FaceWidgetLockPatternUtilsWrapper implements PluginLockPatternUtils {
    public final LockPatternUtils mLockPatternUtils;
    public final KeyguardUpdateMonitor mUpdateMonitor;

    public FaceWidgetLockPatternUtilsWrapper(LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mLockPatternUtils = lockPatternUtils;
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
