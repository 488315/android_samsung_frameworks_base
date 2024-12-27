package com.android.systemui.accessibility;

import android.content.Context;
import android.content.res.Configuration;
import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.wm.shell.flexpanel.FlexPanelStartController;
import com.samsung.android.rune.CoreRune;

public final class FlexModePanel implements ConfigurationController.ConfigurationListener, CoreStartable {
    public final FlexPanelInteractor mFlexPanelInteractor;

    public FlexModePanel(Context context, ConfigurationController configurationController, CommandQueue commandQueue, AutoHideController autoHideController) {
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE) {
            ((ConfigurationControllerImpl) configurationController).addCallback(this);
            this.mFlexPanelInteractor = new FlexPanelInteractor(context, commandQueue, autoHideController);
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE) {
            final int rotation = configuration.windowConfiguration.getRotation();
            final FlexPanelStartController flexPanelStartController = this.mFlexPanelInteractor.mFlexPanelStartController;
            flexPanelStartController.mHandler.post(new Runnable() { // from class: com.android.wm.shell.flexpanel.FlexPanelStartController$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    FlexPanelStartController flexPanelStartController2 = FlexPanelStartController.this;
                    flexPanelStartController2.mRotation = rotation;
                    flexPanelStartController2.onFlexModeChanged(true);
                }
            });
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
