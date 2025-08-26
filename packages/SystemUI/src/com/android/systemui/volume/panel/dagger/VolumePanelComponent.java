package com.android.systemui.volume.panel.dagger;

import com.android.systemui.volume.panel.ui.composable.ComponentsFactory;
import com.android.systemui.volume.panel.ui.layout.ComponentsLayoutManager;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public interface VolumePanelComponent {
    ComponentsFactory componentsFactory();

    ComponentsLayoutManager componentsLayoutManager();

    CoroutineScope coroutineScope();
}
