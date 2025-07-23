package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManagerExtKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DefaultUdfpsTouchOverlayViewModel implements UdfpsTouchOverlayViewModel {
    public final StateFlow shadeExpandedOrExpanding;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 shouldHandleTouches;

    public DefaultUdfpsTouchOverlayViewModel(ShadeInteractor shadeInteractor, SystemUIDialogManager systemUIDialogManager) {
        StateFlow isAnyExpanded = ((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.isAnyExpanded();
        this.shadeExpandedOrExpanding = isAnyExpanded;
        this.shouldHandleTouches = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(isAnyExpanded, SystemUIDialogManagerExtKt.getHideAffordancesRequest(systemUIDialogManager), new DefaultUdfpsTouchOverlayViewModel$shouldHandleTouches$1(null));
    }

    @Override // com.android.systemui.biometrics.ui.viewmodel.UdfpsTouchOverlayViewModel
    public final Flow getShouldHandleTouches() {
        return this.shouldHandleTouches;
    }
}
