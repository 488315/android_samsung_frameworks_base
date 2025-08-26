package com.android.systemui.accessibility;

import android.content.Context;
import android.content.res.Configuration;
import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.policy.ConfigurationController;

/* loaded from: classes.dex */
public class FlexModePanel implements ConfigurationController.ConfigurationListener, CoreStartable {
    public FlexModePanel(Context context, ConfigurationController configurationController, CommandQueue commandQueue, AutoHideController autoHideController) {
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
    }
}
