package com.android.systemui.qs.pipeline.domain.interactor;

import com.android.systemui.shade.ShadeController;

/* loaded from: classes2.dex */
public final class PanelInteractorImpl implements PanelInteractor {
    public final ShadeController shadeController;

    public PanelInteractorImpl(ShadeController shadeController) {
        this.shadeController = shadeController;
    }

    public final void collapsePanels() {
        this.shadeController.postAnimateCollapseShade();
    }
}
