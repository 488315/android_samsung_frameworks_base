package com.android.systemui.pluginlock.component;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.media.AbstractC0000x2c234b15;
import com.android.systemui.Dependency;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.LogUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginLockShortcutDnd extends PluginLockShortcutTask implements ZenModeController.Callback {
    public final PluginLockMediator mMediator;
    public final ZenModeController mZenModeController;

    public PluginLockShortcutDnd(Context context, PluginLockMediator pluginLockMediator) {
        super(context);
        ZenModeController zenModeController = (ZenModeController) Dependency.get(ZenModeController.class);
        this.mZenModeController = zenModeController;
        ((ZenModeControllerImpl) zenModeController).addCallback(this);
        this.mMediator = pluginLockMediator;
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public final void onZenChanged(int i) {
        LogUtil.m223d("PluginLockShortcutDnd", AbstractC0000x2c234b15.m0m("onZenChanged [zen] ", i), new Object[0]);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEnable", ((ZenModeControllerImpl) this.mZenModeController).mZenMode == 1);
        Bundle bundle2 = new Bundle();
        bundle2.putString("action", "get_lockstar_task_shortcut_state");
        bundle2.putString("arg", "Dnd");
        bundle2.putBundle("extras", bundle);
        ((PluginLockMediatorImpl) this.mMediator).onEventReceived(bundle2);
    }
}
