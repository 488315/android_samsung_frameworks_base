package com.android.systemui.statusbar.phone.knox.p024ui.binder;

import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.phone.knox.p024ui.viewmodel.KnoxStatusBarControlViewModel;
import com.android.systemui.statusbar.phone.knox.p024ui.viewmodel.KnoxStatusBarViewControl;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KnoxStatusBarControlBinder {
    static {
        new KnoxStatusBarControlBinder();
    }

    private KnoxStatusBarControlBinder() {
    }

    public static final void bind(KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, KnoxStatusBarViewControl knoxStatusBarViewControl) {
        RepeatWhenAttachedKt.repeatWhenAttached(knoxStatusBarViewControl.getStatusBarView(), EmptyCoroutineContext.INSTANCE, new KnoxStatusBarControlBinder$bind$1(knoxStatusBarControlViewModel, knoxStatusBarViewControl, null));
    }
}
