package com.android.systemui.biometrics.ui.binder;

import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.biometrics.ui.view.UdfpsTouchOverlay;
import com.android.systemui.biometrics.ui.viewmodel.UdfpsTouchOverlayViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class UdfpsTouchOverlayBinder {
    static {
        new UdfpsTouchOverlayBinder();
    }

    private UdfpsTouchOverlayBinder() {
    }

    public static final void bind(UdfpsTouchOverlay udfpsTouchOverlay, UdfpsTouchOverlayViewModel udfpsTouchOverlayViewModel, UdfpsOverlayInteractor udfpsOverlayInteractor) {
        RepeatWhenAttachedKt.repeatWhenAttached(udfpsTouchOverlay, EmptyCoroutineContext.INSTANCE, new UdfpsTouchOverlayBinder$bind$1(udfpsTouchOverlayViewModel, udfpsTouchOverlay, udfpsOverlayInteractor, null));
    }
}
