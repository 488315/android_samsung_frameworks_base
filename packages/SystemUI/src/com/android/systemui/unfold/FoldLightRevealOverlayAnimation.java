package com.android.systemui.unfold;

import android.animation.ValueAnimator;
import com.android.internal.foldables.FoldLockSettingAvailabilityProvider;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.display.data.repository.DeviceStateRepository;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.unfold.FullscreenLightRevealAnimationController;
import com.android.systemui.util.animation.data.repository.AnimationStatusRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FoldLightRevealOverlayAnimation implements FullscreenLightRevealAnimation {
    public final AnimationStatusRepository animationStatusRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public FoldLightRevealOverlayAnimation(CoroutineDispatcher coroutineDispatcher, DeviceStateRepository deviceStateRepository, PowerInteractor powerInteractor, CoroutineScope coroutineScope, AnimationStatusRepository animationStatusRepository, FullscreenLightRevealAnimationController.Factory factory, FoldLockSettingAvailabilityProvider foldLockSettingAvailabilityProvider, InteractionJankMonitor interactionJankMonitor) {
        this.animationStatusRepository = animationStatusRepository;
        ValueAnimator.ofFloat(0.0f, 1.0f);
    }

    @Override // com.android.systemui.unfold.FullscreenLightRevealAnimation
    public final void onScreenTurningOn(Runnable runnable) {
        runnable.run();
    }
}
