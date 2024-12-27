package com.android.systemui.volume.panel.dagger;

import com.android.systemui.volume.panel.domain.interactor.ComponentsInteractor;
import com.android.systemui.volume.panel.ui.composable.ComponentsFactory;
import com.android.systemui.volume.panel.ui.layout.ComponentsLayoutManager;
import java.util.Set;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface VolumePanelComponent {
    ComponentsFactory componentsFactory();

    ComponentsInteractor componentsInteractor();

    ComponentsLayoutManager componentsLayoutManager();

    CoroutineScope coroutineScope();

    Set volumePanelStartables();
}
