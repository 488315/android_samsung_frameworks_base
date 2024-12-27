package com.android.systemui.biometrics.ui.binder;

import com.android.systemui.Flags;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.biometrics.ui.view.UdfpsTouchOverlay;
import com.android.systemui.biometrics.ui.viewmodel.UdfpsTouchOverlayViewModel;
import com.android.systemui.deviceentry.shared.DeviceEntryUdfpsRefactor;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
