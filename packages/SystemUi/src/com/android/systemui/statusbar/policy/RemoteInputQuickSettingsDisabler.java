package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.res.Configuration;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.LargeScreenUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RemoteInputQuickSettingsDisabler implements ConfigurationController.ConfigurationListener {
    public final Context context;
    public boolean isLandscape;
    public boolean remoteInputActive;
    public boolean shouldUseSplitNotificationShade;

    public RemoteInputQuickSettingsDisabler(Context context, CommandQueue commandQueue, ConfigurationController configurationController) {
        this.context = context;
        this.isLandscape = context.getResources().getConfiguration().orientation == 2;
        this.shouldUseSplitNotificationShade = LargeScreenUtils.shouldUseSplitNotificationShade(context.getResources());
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        boolean z = configuration.orientation == 2;
        if (z != this.isLandscape) {
            this.isLandscape = z;
        }
        boolean shouldUseSplitNotificationShade = LargeScreenUtils.shouldUseSplitNotificationShade(this.context.getResources());
        if (shouldUseSplitNotificationShade != this.shouldUseSplitNotificationShade) {
            this.shouldUseSplitNotificationShade = shouldUseSplitNotificationShade;
        }
    }
}
