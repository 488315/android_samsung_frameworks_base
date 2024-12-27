package com.android.systemui.haptics.slider;

import android.view.View;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.coroutines.EmptyCoroutineContext;

public final class HapticSliderViewBinder {
    static {
        new HapticSliderViewBinder();
    }

    private HapticSliderViewBinder() {
    }

    public static final void bind(View view, SeekbarHapticPlugin seekbarHapticPlugin) {
        if (view != null) {
            RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new HapticSliderViewBinder$bind$1(seekbarHapticPlugin, null));
        }
    }
}
