package com.android.systemui.statusbar.phone.knox.ui.binder;

import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarControlViewModel;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
