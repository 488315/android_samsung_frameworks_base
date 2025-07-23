package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewSmartspaceViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.clocks.ClockPreviewConfig;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardPreviewSmartspaceViewBinder {
    public static final KeyguardPreviewSmartspaceViewBinder INSTANCE = new KeyguardPreviewSmartspaceViewBinder();

    private KeyguardPreviewSmartspaceViewBinder() {
    }

    public static final void bind(KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel, ClockPreviewConfig clockPreviewConfig, View view) {
        RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new KeyguardPreviewSmartspaceViewBinder$bind$2(keyguardPreviewSmartspaceViewModel, clockPreviewConfig, view, null));
    }
}
