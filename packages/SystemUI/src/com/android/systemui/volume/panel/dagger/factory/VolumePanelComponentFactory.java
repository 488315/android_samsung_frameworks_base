package com.android.systemui.volume.panel.dagger.factory;

import com.android.systemui.volume.panel.dagger.VolumePanelComponent;
import com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel;
import kotlinx.coroutines.CoroutineScope;

public interface VolumePanelComponentFactory {
    VolumePanelComponent create(VolumePanelViewModel volumePanelViewModel, CoroutineScope coroutineScope);
}
