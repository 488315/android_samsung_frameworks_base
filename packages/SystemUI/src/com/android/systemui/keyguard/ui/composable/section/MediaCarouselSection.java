package com.android.systemui.keyguard.ui.composable.section;

import com.android.systemui.keyguard.ui.viewmodel.KeyguardMediaViewModel;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.view.MediaHost;

public final class MediaCarouselSection {
    public final KeyguardMediaViewModel keyguardMediaViewModel;
    public final MediaCarouselController mediaCarouselController;
    public final MediaHost mediaHost;

    public MediaCarouselSection(MediaCarouselController mediaCarouselController, MediaHost mediaHost, KeyguardMediaViewModel keyguardMediaViewModel) {
    }
}
