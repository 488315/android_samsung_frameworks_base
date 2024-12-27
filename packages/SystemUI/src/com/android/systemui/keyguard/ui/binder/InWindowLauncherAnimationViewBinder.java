package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.keyguard.ui.view.InWindowLauncherUnlockAnimationManager;
import com.android.systemui.keyguard.ui.viewmodel.InWindowLauncherAnimationViewModel;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class InWindowLauncherAnimationViewBinder {
    static {
        new InWindowLauncherAnimationViewBinder();
    }

    private InWindowLauncherAnimationViewBinder() {
    }

    public static final void bind(InWindowLauncherAnimationViewModel inWindowLauncherAnimationViewModel, InWindowLauncherUnlockAnimationManager inWindowLauncherUnlockAnimationManager, CoroutineScope coroutineScope) {
        BuildersKt.launch$default(coroutineScope, null, null, new InWindowLauncherAnimationViewBinder$bind$1(inWindowLauncherAnimationViewModel, inWindowLauncherUnlockAnimationManager, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new InWindowLauncherAnimationViewBinder$bind$2(inWindowLauncherAnimationViewModel, inWindowLauncherUnlockAnimationManager, null), 3);
    }
}
