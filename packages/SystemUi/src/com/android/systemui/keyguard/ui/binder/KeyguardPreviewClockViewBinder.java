package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewClockViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardPreviewClockViewBinder {
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
