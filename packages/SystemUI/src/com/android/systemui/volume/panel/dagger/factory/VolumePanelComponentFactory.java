package com.android.systemui.volume.panel.dagger.factory;

import com.android.systemui.volume.panel.dagger.VolumePanelComponent;
import com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface VolumePanelComponentFactory {
    VolumePanelComponent create(VolumePanelViewModel volumePanelViewModel, CoroutineScope coroutineScope);
}
