package com.android.systemui.volume.panel.dagger;

import com.android.systemui.volume.panel.domain.interactor.ComponentsInteractor;
import com.android.systemui.volume.panel.ui.composable.ComponentsFactory;
import com.android.systemui.volume.panel.ui.layout.ComponentsLayoutManager;
import java.util.Set;
import kotlinx.coroutines.CoroutineScope;

public interface VolumePanelComponent {
    ComponentsFactory componentsFactory();

    ComponentsInteractor componentsInteractor();

    ComponentsLayoutManager componentsLayoutManager();

    CoroutineScope coroutineScope();

    Set volumePanelStartables();
}
