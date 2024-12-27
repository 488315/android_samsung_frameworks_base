package com.android.systemui.volume.panel.component.captioning;

import com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent;
import com.android.systemui.volume.panel.component.captioning.ui.viewmodel.CaptioningViewModel;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class CaptioningModule_Companion_ProvideVolumePanelUiComponentFactory implements Provider {
    public final javax.inject.Provider viewModelProvider;

    public CaptioningModule_Companion_ProvideVolumePanelUiComponentFactory(javax.inject.Provider provider) {
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
