package com.android.systemui.pluginlock.component;

import android.content.Context;
import android.os.Bundle;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.Dependency;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import com.android.systemui.util.LogUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginLockShortcutFlashLight extends PluginLockShortcutTask implements FlashlightController.FlashlightListener {
    public final FlashlightController mFlashlightController;
    public final PluginLockMediator mMediator;

    public PluginLockShortcutFlashLight(Context context, PluginLockMediator pluginLockMediator) {
        super(context);
        FlashlightController flashlightController = (FlashlightController) Dependency.get(FlashlightController.class);
        this.mFlashlightController = flashlightController;
        ((FlashlightControllerImpl) flashlightController).addListener(this);
        this.mMediator = pluginLockMediator;
    }

    @Override // com.android.systemui.statusbar.policy.FlashlightController.FlashlightListener
    public final void onFlashlightChanged(boolean z) {
        LogUtil.m223d("PluginLockShortcutFlashLight", AbstractC0866xb1ce8deb.m86m("onFlashlightChanged [enabled] ", z), new Object[0]);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEnable", z);
        Bundle bundle2 = new Bundle();
        bundle2.putString("action", "get_lockstar_task_shortcut_state");
        bundle2.putString("arg", "Flashlight");
        bundle2.putBundle("extras", bundle);
        ((PluginLockMediatorImpl) this.mMediator).onEventReceived(bundle2);
    }

    @Override // com.android.systemui.statusbar.policy.FlashlightController.FlashlightListener
    public final void onFlashlightAvailabilityChanged(boolean z) {
    }

    @Override // com.android.systemui.statusbar.policy.FlashlightController.FlashlightListener
    public final void onFlashlightError() {
    }
}
