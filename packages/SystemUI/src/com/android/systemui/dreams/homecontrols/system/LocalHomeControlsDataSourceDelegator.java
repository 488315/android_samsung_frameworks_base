package com.android.systemui.dreams.homecontrols.system;

import com.android.systemui.controls.settings.ControlsSettingsRepository;
import com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl;
import com.android.systemui.dreams.homecontrols.shared.model.HomeControlsDataSource;
import com.android.systemui.dreams.homecontrols.system.domain.interactor.HomeControlsComponentInteractor;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
