package com.android.systemui.p016qs.pipeline.domain.interactor;

import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PanelInteractorImpl implements PanelInteractor {
    public final Optional centralSurfaces;

    public PanelInteractorImpl(Optional<CentralSurfaces> optional) {
        this.centralSurfaces = optional;
    }

    @Override // com.android.systemui.p016qs.pipeline.domain.interactor.PanelInteractor
    public final void collapsePanels() {
        this.centralSurfaces.ifPresent(new Consumer() { // from class: com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl$collapsePanels$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((CentralSurfacesImpl) ((CentralSurfaces) obj)).postAnimateCollapsePanels();
            }
        });
    }

    @Override // com.android.systemui.p016qs.pipeline.domain.interactor.PanelInteractor
    public final void forceCollapsePanels() {
        this.centralSurfaces.ifPresent(new Consumer() { // from class: com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl$forceCollapsePanels$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((CentralSurfacesImpl) ((CentralSurfaces) obj)).postAnimateForceCollapsePanels();
            }
        });
    }
}
