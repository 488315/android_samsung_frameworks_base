package com.android.systemui.ambient.touch.scrim;

import com.android.systemui.dreams.DreamOverlayContainerViewController;
import java.util.function.Consumer;

public final /* synthetic */ class BouncerlessScrimController$$ExternalSyntheticLambda5 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DreamOverlayContainerViewController.this.mWakingUpFromSwipe = true;
    }
}
