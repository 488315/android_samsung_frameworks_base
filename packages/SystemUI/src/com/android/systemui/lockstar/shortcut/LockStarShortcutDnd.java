package com.android.systemui.lockstar.shortcut;

import android.content.Context;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.pluginlock.component.PluginLockShortcutTask;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LockStarShortcutDnd extends LockStarShortcutTask implements ZenModeController.Callback {
    public final String name;
    public final PluginLockStar pluginLockStar;
    public final ZenModeController zenModeController;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public LockStarShortcutDnd(Context context, PluginLockStar pluginLockStar, ZenModeController zenModeController) {
        super(context);
        this.pluginLockStar = pluginLockStar;
        this.zenModeController = zenModeController;
        this.name = PluginLockShortcutTask.DO_NOT_DISTURB_TASK;
        context.getString(R.string.lock_app_shortcut_dnd_app_label);
    }

    @Override // com.android.systemui.lockstar.shortcut.LockStarShortcutTask
    public final void execute() {
        int i = ((ZenModeControllerImpl) this.zenModeController).mZenMode == 1 ? 0 : 1;
        KeyguardSecPatternView$$ExternalSyntheticOutline0.m(i, "execute to ", "PluginLockShortcutDnd");
        ((ZenModeControllerImpl) this.zenModeController).setZen(i, null, "Keyguard");
    }

    @Override // com.android.systemui.lockstar.shortcut.LockStarShortcutTask
    public final String getName() {
        return this.name;
    }

    @Override // com.android.systemui.lockstar.shortcut.LockStarShortcutTask
    public final void init() {
        ((ZenModeControllerImpl) this.zenModeController).addCallback(this);
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public final void onZenChanged(int i) {
        KeyguardSecPatternView$$ExternalSyntheticOutline0.m(i, "onZenChanged [zen] ", "PluginLockShortcutDnd");
        this.pluginLockStar.onChangedNoNeedUnlockServiceState(PluginLockShortcutTask.DO_NOT_DISTURB_TASK, ((ZenModeControllerImpl) this.zenModeController).mZenMode == 1);
    }

    @Override // com.android.systemui.lockstar.shortcut.LockStarShortcutTask
    public final void terminate() {
        ((ZenModeControllerImpl) this.zenModeController).removeCallback(this);
    }
}
