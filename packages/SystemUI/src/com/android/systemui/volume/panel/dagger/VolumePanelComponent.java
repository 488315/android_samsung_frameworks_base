package com.android.systemui.volume.panel.dagger;

import com.android.systemui.volume.panel.ui.composable.ComponentsFactory;
import com.android.systemui.volume.panel.ui.layout.ComponentsLayoutManager;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface VolumePanelComponent {
    ComponentsFactory componentsFactory();

    ComponentsLayoutManager componentsLayoutManager();

    CoroutineScope coroutineScope();
}
