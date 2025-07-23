package com.android.systemui.volume.panel.component.bottombar.ui.viewmodel;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
