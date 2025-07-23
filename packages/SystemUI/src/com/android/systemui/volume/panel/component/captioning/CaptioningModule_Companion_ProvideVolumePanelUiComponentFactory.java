package com.android.systemui.volume.panel.component.captioning;

import com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent;
import com.android.systemui.volume.panel.component.captioning.ui.viewmodel.CaptioningViewModel;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CaptioningModule_Companion_ProvideVolumePanelUiComponentFactory implements Provider {
    public final Provider viewModelProvider;

    public CaptioningModule_Companion_ProvideVolumePanelUiComponentFactory(Provider provider) {
        this.viewModelProvider = provider;
    }

    public static ToggleButtonComponent provideVolumePanelUiComponent(CaptioningViewModel captioningViewModel) {
        CaptioningModule.Companion.getClass();
        return new ToggleButtonComponent(captioningViewModel.buttonViewModel, new CaptioningModule$Companion$provideVolumePanelUiComponent$1(captioningViewModel));
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideVolumePanelUiComponent((CaptioningViewModel) this.viewModelProvider.get());
    }
}
