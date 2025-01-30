package com.android.systemui.statusbar.pipeline.mobile.p026ui.binder;

import android.view.View;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconsViewModel;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
