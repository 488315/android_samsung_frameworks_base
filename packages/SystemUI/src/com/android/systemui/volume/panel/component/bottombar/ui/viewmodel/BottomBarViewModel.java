package com.android.systemui.volume.panel.component.bottombar.ui.viewmodel;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel;

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
