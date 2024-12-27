package com.android.systemui.haptics.slider;

import android.view.View;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
