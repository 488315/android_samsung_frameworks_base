package com.android.systemui.clipboardoverlay;

import android.view.WindowInsets;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public final /* synthetic */ class ClipboardOverlayController$$ExternalSyntheticLambda0 implements BiConsumer {
    public final /* synthetic */ ClipboardOverlayController f$0;

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        this.f$0.onInsetsChanged((WindowInsets) obj, ((Integer) obj2).intValue());
    }
}
