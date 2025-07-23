package com.android.systemui.facewidget.plugin;

import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.plugins.keyguardstatusview.PluginLockPatternUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class FaceWidgetLockPatternUtilsWrapper implements PluginLockPatternUtils {
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
