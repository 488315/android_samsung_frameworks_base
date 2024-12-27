package com.android.systemui.unfold;

import android.animation.ValueAnimator;
import com.android.internal.foldables.FoldLockSettingAvailabilityProvider;
import com.android.systemui.display.data.repository.DeviceStateRepository;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.unfold.FullscreenLightRevealAnimationController;
import com.android.systemui.util.animation.data.repository.AnimationStatusRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class FoldLightRevealOverlayAnimation implements FullscreenLightRevealAnimation {
    public final AnimationStatusRepository animationStatusRepository;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public FoldLightRevealOverlayAnimation(CoroutineDispatcher coroutineDispatcher, DeviceStateRepository deviceStateRepository, PowerInteractor powerInteractor, CoroutineScope coroutineScope, AnimationStatusRepository animationStatusRepository, FullscreenLightRevealAnimationController.Factory factory, FoldLockSettingAvailabilityProvider foldLockSettingAvailabilityProvider) {
        this.animationStatusRepository = animationStatusRepository;
        ValueAnimator.ofFloat(0.0f, 1.0f);
    }

    @Override // com.android.systemui.unfold.FullscreenLightRevealAnimation
    public final void onScreenTurningOn(Runnable runnable) {
        runnable.run();
    }
}
