package com.android.systemui.statusbar.pipeline.mobile.ui.binder;

import android.view.View;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel;
import kotlin.coroutines.EmptyCoroutineContext;

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
