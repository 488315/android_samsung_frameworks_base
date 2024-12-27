package com.android.systemui.pluginlock.component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import com.android.systemui.util.LogUtil;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class PluginLockShortcutFlashLight extends PluginLockShortcutTask implements FlashlightController.FlashlightListener {
    private static final String TAG = "PluginLockShortcutFlashLight";
    private final FlashlightController mFlashlightController;
    private final PluginLockMediator mMediator;

    public PluginLockShortcutFlashLight(Context context, PluginLockMediator pluginLockMediator) {
        super(context);
        this.mFlashlightController = (FlashlightController) Dependency.sDependency.getDependencyInner(FlashlightController.class);
        this.mMediator = pluginLockMediator;
    }

    @Override // com.android.systemui.pluginlock.component.PluginLockShortcutTask
    public void excute() {
        LogUtil.d(TAG, "excute()", new Object[0]);
        ((FlashlightControllerImpl) this.mFlashlightController).setFlashlight(!((FlashlightControllerImpl) r3).isEnabled());
    }

    @Override // com.android.systemui.pluginlock.component.PluginLockShortcutTask
    public String getAppLabel() {
        return "";
    }

    @Override // com.android.systemui.pluginlock.component.PluginLockShortcutTask
    public Drawable getDrawble() {
        return null;
    }

    @Override // com.android.systemui.pluginlock.component.PluginLockShortcutTask
    public boolean isEnabled() {
        boolean isEnabled = ((FlashlightControllerImpl) this.mFlashlightController).isEnabled();
        LogUtil.d(TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("isEnabled [isEnabled] ", isEnabled), new Object[0]);
        return isEnabled;
    }

    @Override // com.android.systemui.statusbar.policy.FlashlightController.FlashlightListener
    public void onFlashlightChanged(boolean z) {
        LogUtil.d(TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("onFlashlightChanged [enabled] ", z), new Object[0]);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEnable", z);
        Bundle bundle2 = new Bundle();
        bundle2.putString("action", PluginLockShortcutTask.GET_LOCKSTAR_TASK_SHORTCUT_STATE);
        bundle2.putString("arg", PluginLockShortcutTask.FLASH_LIGHT_TASK);
        bundle2.putBundle("extras", bundle);
        this.mMediator.onEventReceived(bundle2);
    }

    @Override // com.android.systemui.pluginlock.component.PluginLockShortcutTask
    public void setState(boolean z) {
        LogUtil.d(TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("setState [state] ", z), new Object[0]);
        ((FlashlightControllerImpl) this.mFlashlightController).setFlashlight(z);
    }

    @Override // com.android.systemui.statusbar.policy.FlashlightController.FlashlightListener
    public void onFlashlightError() {
    }

    @Override // com.android.systemui.pluginlock.component.PluginLockShortcutTask
    public void removeListener() {
    }

    @Override // com.android.systemui.statusbar.policy.FlashlightController.FlashlightListener
    public void onFlashlightAvailabilityChanged(boolean z) {
    }
}
