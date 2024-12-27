package com.android.server.wm;

import android.graphics.Rect;

import com.samsung.android.core.CompatUtils;

import java.util.function.BiConsumer;

public final /* synthetic */ class DexSizeCompatController$$ExternalSyntheticLambda1
        implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        CompatUtils.adjustBoundsToCenter((Rect) obj, (Rect) obj2);
    }
}
