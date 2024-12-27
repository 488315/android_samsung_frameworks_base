package com.android.systemui.volume.panel.component.bottombar.ui.viewmodel;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BottomBarViewModel {
    public final ActivityStarter activityStarter;
    public final UiEventLogger uiEventLogger;
    public final VolumePanelViewModel volumePanelViewModel;

    public BottomBarViewModel(ActivityStarter activityStarter, VolumePanelViewModel volumePanelViewModel, UiEventLogger uiEventLogger) {
        this.activityStarter = activityStarter;
        this.volumePanelViewModel = volumePanelViewModel;
        this.uiEventLogger = uiEventLogger;
    }
}
