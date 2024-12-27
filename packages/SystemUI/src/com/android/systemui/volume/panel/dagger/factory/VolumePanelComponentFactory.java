package com.android.systemui.volume.panel.dagger.factory;

import com.android.systemui.volume.panel.dagger.VolumePanelComponent;
import com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface VolumePanelComponentFactory {
    VolumePanelComponent create(VolumePanelViewModel volumePanelViewModel, CoroutineScope coroutineScope);
}
