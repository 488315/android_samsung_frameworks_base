package com.android.systemui.clipboardoverlay;

import android.view.WindowInsets;
import java.util.function.BiConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class ClipboardOverlayController$$ExternalSyntheticLambda0 implements BiConsumer {
    public final /* synthetic */ ClipboardOverlayController f$0;

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        this.f$0.onInsetsChanged((WindowInsets) obj, ((Integer) obj2).intValue());
    }
}
