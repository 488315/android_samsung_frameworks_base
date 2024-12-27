package com.android.systemui.biometrics.ui.binder;

import com.android.systemui.Flags;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.biometrics.ui.view.UdfpsTouchOverlay;
import com.android.systemui.biometrics.ui.viewmodel.UdfpsTouchOverlayViewModel;
import com.android.systemui.deviceentry.shared.DeviceEntryUdfpsRefactor;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.coroutines.EmptyCoroutineContext;

public final class UdfpsTouchOverlayBinder {
    static {
        new UdfpsTouchOverlayBinder();
    }

    private UdfpsTouchOverlayBinder() {
    }

    public static final void bind(UdfpsTouchOverlay udfpsTouchOverlay, UdfpsTouchOverlayViewModel udfpsTouchOverlayViewModel, UdfpsOverlayInteractor udfpsOverlayInteractor) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        DeviceEntryUdfpsRefactor deviceEntryUdfpsRefactor = DeviceEntryUdfpsRefactor.INSTANCE;
        Flags.deviceEntryUdfpsRefactor();
        RepeatWhenAttachedKt.repeatWhenAttached(udfpsTouchOverlay, EmptyCoroutineContext.INSTANCE, new UdfpsTouchOverlayBinder$bind$1(udfpsTouchOverlayViewModel, udfpsTouchOverlay, udfpsOverlayInteractor, null));
    }
}
