package com.android.systemui.keyguard.ui.composable.section;

import com.android.systemui.keyguard.ui.viewmodel.KeyguardMediaViewModel;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.view.MediaHost;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
