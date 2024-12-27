package com.android.systemui.qs.pipeline.domain.interactor;

import com.android.systemui.shade.ShadeController;

public final class PanelInteractorImpl implements PanelInteractor {
    public final ShadeController shadeController;

    public PanelInteractorImpl(ShadeController shadeController) {
        this.shadeController = shadeController;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor
    public final void collapsePanels() {
        this.shadeController.postAnimateCollapseShade();
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor
    public final void forceCollapsePanels() {
        this.shadeController.postAnimateForceCollapseShade();
    }
}
