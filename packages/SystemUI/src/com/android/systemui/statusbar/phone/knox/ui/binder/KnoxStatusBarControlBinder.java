package com.android.systemui.statusbar.phone.knox.ui.binder;

import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarControlViewModel;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
