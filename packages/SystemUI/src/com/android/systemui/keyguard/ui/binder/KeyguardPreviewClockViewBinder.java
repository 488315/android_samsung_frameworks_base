package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewClockViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.coroutines.EmptyCoroutineContext;

public final class KeyguardPreviewClockViewBinder {
    public static final KeyguardPreviewClockViewBinder INSTANCE = null;

    static {
        new KeyguardPreviewClockViewBinder();
    }

    private KeyguardPreviewClockViewBinder() {
    }

    public static final void bind(View view, View view2, KeyguardPreviewClockViewModel keyguardPreviewClockViewModel) {
        RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new KeyguardPreviewClockViewBinder$bind$1(keyguardPreviewClockViewModel, view, null));
        RepeatWhenAttachedKt.repeatWhenAttached(view2, EmptyCoroutineContext.INSTANCE, new KeyguardPreviewClockViewBinder$bind$2(keyguardPreviewClockViewModel, view2, null));
    }
}
