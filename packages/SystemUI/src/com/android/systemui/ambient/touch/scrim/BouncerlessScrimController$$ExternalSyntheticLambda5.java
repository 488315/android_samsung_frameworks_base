package com.android.systemui.ambient.touch.scrim;

import com.android.systemui.dreams.DreamOverlayContainerViewController;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class BouncerlessScrimController$$ExternalSyntheticLambda5 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DreamOverlayContainerViewController.this.mWakingUpFromSwipe = true;
    }
}
