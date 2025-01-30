package com.android.systemui.accessibility;

import android.content.Context;
import android.content.res.Configuration;
import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FlexModePanel implements ConfigurationController.ConfigurationListener, CoreStartable {
    public FlexModePanel(Context context, ConfigurationController configurationController, CommandQueue commandQueue, SettingsHelper settingsHelper, AutoHideController autoHideController) {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
