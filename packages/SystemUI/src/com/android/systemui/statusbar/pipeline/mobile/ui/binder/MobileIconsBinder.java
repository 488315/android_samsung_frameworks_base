package com.android.systemui.statusbar.pipeline.mobile.ui.binder;

import android.view.View;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MobileIconsBinder {
    static {
        new MobileIconsBinder();
    }

    private MobileIconsBinder() {
    }

    public static final void bind(View view, MobileIconsViewModel mobileIconsViewModel) {
        RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new MobileIconsBinder$bind$1(mobileIconsViewModel, null));
    }
}
