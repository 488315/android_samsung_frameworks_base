package com.android.systemui.lockstar.shortcut;

import android.content.Context;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.pluginlock.component.PluginLockShortcutTask;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class LockStarShortcutFlashLight extends LockStarShortcutTask implements FlashlightController.FlashlightListener {
    public final FlashlightController flashlightController;
    public final String name;
    public final PluginLockStar pluginLockStar;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public LockStarShortcutFlashLight(Context context, PluginLockStar pluginLockStar) {
        super(context);
        this.pluginLockStar = pluginLockStar;
        this.flashlightController = (FlashlightController) Dependency.sDependency.getDependencyInner(FlashlightController.class);
        this.name = PluginLockShortcutTask.FLASH_LIGHT_TASK;
    }

    @Override // com.android.systemui.lockstar.shortcut.LockStarShortcutTask
    public final void execute() {
        FlashlightController flashlightController = this.flashlightController;
        EmergencyButtonController$$ExternalSyntheticOutline0.m("execute() to ", "PluginLockShortcutFlashLight", !((FlashlightControllerImpl) flashlightController).isEnabled());
        ((FlashlightControllerImpl) flashlightController).setFlashlight(!((FlashlightControllerImpl) flashlightController).isEnabled());
    }

    @Override // com.android.systemui.lockstar.shortcut.LockStarShortcutTask
    public final String getName() {
        return this.name;
    }

    @Override // com.android.systemui.lockstar.shortcut.LockStarShortcutTask
    public final void init() {
        ((FlashlightControllerImpl) this.flashlightController).addCallback(this);
    }

    @Override // com.android.systemui.statusbar.policy.FlashlightController.FlashlightListener
    public final void onFlashlightChanged(boolean z) {
        EmergencyButtonController$$ExternalSyntheticOutline0.m("onFlashlightChanged [enabled] ", "PluginLockShortcutFlashLight", z);
        this.pluginLockStar.onChangedNoNeedUnlockServiceState(PluginLockShortcutTask.FLASH_LIGHT_TASK, z);
    }

    @Override // com.android.systemui.lockstar.shortcut.LockStarShortcutTask
    public final void terminate() {
        ((FlashlightControllerImpl) this.flashlightController).removeCallback(this);
    }

    @Override // com.android.systemui.statusbar.policy.FlashlightController.FlashlightListener
    public final void onFlashlightAvailabilityChanged(boolean z) {
    }

    @Override // com.android.systemui.statusbar.policy.FlashlightController.FlashlightListener
    public final void onFlashlightError() {
    }
}
