package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.res.Configuration;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RemoteInputQuickSettingsDisabler implements ConfigurationController.ConfigurationListener {
    public final Context context;
    public boolean isLandscape;
    public boolean remoteInputActive;
    public final SplitShadeStateController splitShadeStateController;

    public RemoteInputQuickSettingsDisabler(Context context, CommandQueue commandQueue, SplitShadeStateController splitShadeStateController, ConfigurationController configurationController) {
        this.context = context;
        this.splitShadeStateController = splitShadeStateController;
        this.isLandscape = context.getResources().getConfiguration().orientation == 2;
        context.getResources();
        ((SplitShadeStateControllerImpl) splitShadeStateController).shouldUseSplitNotificationShade();
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        boolean z = configuration.orientation == 2;
        if (z != this.isLandscape) {
            this.isLandscape = z;
        }
        this.context.getResources();
        ((SplitShadeStateControllerImpl) this.splitShadeStateController).shouldUseSplitNotificationShade();
    }
}
