package com.android.systemui.haptics.slider;

import android.view.View;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class HapticSliderViewBinder {
    static {
        new HapticSliderViewBinder();
    }

    private HapticSliderViewBinder() {
    }

    public static final void bind(View view, HapticSliderPlugin hapticSliderPlugin) {
        if (view != null) {
            RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new HapticSliderViewBinder$bind$1(hapticSliderPlugin, null));
        }
    }
}
