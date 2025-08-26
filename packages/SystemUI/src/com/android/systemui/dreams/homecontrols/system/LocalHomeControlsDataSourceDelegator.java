package com.android.systemui.dreams.homecontrols.system;

import com.android.systemui.controls.settings.ControlsSettingsRepository;
import com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl;
import com.android.systemui.dreams.homecontrols.shared.model.HomeControlsDataSource;
import com.android.systemui.dreams.homecontrols.system.domain.interactor.HomeControlsComponentInteractor;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class LocalHomeControlsDataSourceDelegator implements HomeControlsDataSource {
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 componentInfo;

    public LocalHomeControlsDataSourceDelegator(HomeControlsComponentInteractor homeControlsComponentInteractor, ControlsSettingsRepository controlsSettingsRepository) {
        this.componentInfo = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(homeControlsComponentInteractor.panelComponent, ((ControlsSettingsRepositoryImpl) controlsSettingsRepository).allowActionOnTrivialControlsInLockscreen, new LocalHomeControlsDataSourceDelegator$componentInfo$1(null));
    }

    @Override // com.android.systemui.dreams.homecontrols.shared.model.HomeControlsDataSource
    public final Flow getComponentInfo() {
        return this.componentInfo;
    }
}
