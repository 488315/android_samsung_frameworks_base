package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManagerExtKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlow;

public final class DefaultUdfpsTouchOverlayViewModel implements UdfpsTouchOverlayViewModel {
    public final StateFlow shadeExpandedOrExpanding;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 shouldHandleTouches;

    public DefaultUdfpsTouchOverlayViewModel(ShadeInteractor shadeInteractor, SystemUIDialogManager systemUIDialogManager) {
        this.shouldHandleTouches = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.isAnyExpanded(), SystemUIDialogManagerExtKt.getHideAffordancesRequest(systemUIDialogManager), new DefaultUdfpsTouchOverlayViewModel$shouldHandleTouches$1(null));
    }

    @Override // com.android.systemui.biometrics.ui.viewmodel.UdfpsTouchOverlayViewModel
    public final Flow getShouldHandleTouches() {
        return this.shouldHandleTouches;
    }
}
