package com.android.systemui.media.dagger;

import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.controller.MediaCarouselControllerLogger;
import com.android.systemui.media.controls.ui.controller.MediaHierarchyManager;
import com.android.systemui.media.controls.ui.controller.MediaHostStatesManager;
import com.android.systemui.media.controls.ui.view.MediaHost;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaModule_ProvidesKeyguardMediaHostFactory implements Provider {
    public final Provider carouselControllerProvider;
    public final Provider dataManagerProvider;
    public final Provider hierarchyManagerProvider;
    public final Provider loggerProvider;
    public final Provider stateHolderProvider;
    public final Provider statesManagerProvider;

    public MediaModule_ProvidesKeyguardMediaHostFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.stateHolderProvider = provider;
        this.hierarchyManagerProvider = provider2;
        this.dataManagerProvider = provider3;
        this.statesManagerProvider = provider4;
        this.carouselControllerProvider = provider5;
        this.loggerProvider = provider6;
    }

    public static MediaHost providesKeyguardMediaHost(MediaHost.MediaHostStateHolder mediaHostStateHolder, MediaHierarchyManager mediaHierarchyManager, MediaDataManager mediaDataManager, MediaHostStatesManager mediaHostStatesManager, MediaCarouselController mediaCarouselController, MediaCarouselControllerLogger mediaCarouselControllerLogger) {
        return new MediaHost(mediaHostStateHolder, mediaHierarchyManager, mediaDataManager, mediaHostStatesManager, mediaCarouselController, mediaCarouselControllerLogger);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new MediaHost((MediaHost.MediaHostStateHolder) this.stateHolderProvider.get(), (MediaHierarchyManager) this.hierarchyManagerProvider.get(), (MediaDataManager) this.dataManagerProvider.get(), (MediaHostStatesManager) this.statesManagerProvider.get(), (MediaCarouselController) this.carouselControllerProvider.get(), (MediaCarouselControllerLogger) this.loggerProvider.get());
    }
}
