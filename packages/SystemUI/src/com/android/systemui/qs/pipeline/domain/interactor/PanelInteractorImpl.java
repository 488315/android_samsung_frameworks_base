package com.android.systemui.qs.pipeline.domain.interactor;

import com.android.systemui.shade.ShadeController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
