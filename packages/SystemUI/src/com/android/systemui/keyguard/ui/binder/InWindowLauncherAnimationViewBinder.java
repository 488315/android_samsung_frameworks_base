package com.android.systemui.keyguard.ui.binder;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.keyguard.ui.view.InWindowLauncherUnlockAnimationManager;
import com.android.systemui.keyguard.ui.viewmodel.InWindowLauncherAnimationViewModel;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class InWindowLauncherAnimationViewBinder {
    static {
        new InWindowLauncherAnimationViewBinder();
    }

    private InWindowLauncherAnimationViewBinder() {
    }

    public static final void bind(InWindowLauncherAnimationViewModel inWindowLauncherAnimationViewModel, InWindowLauncherUnlockAnimationManager inWindowLauncherUnlockAnimationManager, CoroutineScope coroutineScope) {
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new InWindowLauncherAnimationViewBinder$bind$1(inWindowLauncherAnimationViewModel, inWindowLauncherUnlockAnimationManager, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new InWindowLauncherAnimationViewBinder$bind$2(inWindowLauncherAnimationViewModel, inWindowLauncherUnlockAnimationManager, null), 7);
    }
}
