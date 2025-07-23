package com.android.systemui.qs.pipeline.domain.interactor;

import com.android.systemui.shade.ShadeController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
