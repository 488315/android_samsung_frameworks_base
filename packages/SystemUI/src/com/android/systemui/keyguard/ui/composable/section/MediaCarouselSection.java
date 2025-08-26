package com.android.systemui.keyguard.ui.composable.section;

import com.android.systemui.keyguard.ui.viewmodel.KeyguardMediaViewModel;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.view.MediaHost;

/* loaded from: classes2.dex */
public final class MediaCarouselSection {
    public final KeyguardMediaViewModel.Factory keyguardMediaViewModelFactory;
    public final MediaCarouselController mediaCarouselController;
    public final MediaHost mediaHost;

    public MediaCarouselSection(MediaCarouselController mediaCarouselController, MediaHost mediaHost, KeyguardMediaViewModel.Factory factory) {
        this.mediaCarouselController = mediaCarouselController;
        this.mediaHost = mediaHost;
        this.keyguardMediaViewModelFactory = factory;
    }
}
