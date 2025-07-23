package com.android.systemui.ambient.touch.scrim;

import com.android.systemui.dreams.DreamOverlayContainerViewController;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class BouncerlessScrimController$$ExternalSyntheticLambda5 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DreamOverlayContainerViewController.this.mWakingUpFromSwipe = true;
    }
}
