package com.android.systemui.keyguard.ui.binder;

import android.util.Log;
import android.view.ViewGroup;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractorKt;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardJankViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$1;
import com.android.systemui.plugins.clocks.ClockConfig;
import com.android.systemui.plugins.clocks.ClockController;
import kotlin.NoWhenBranchMatchedException;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardJankBinder {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TransitionState.values().length];
            try {
                iArr[TransitionState.STARTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TransitionState.CANCELED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TransitionState.FINISHED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[TransitionState.RUNNING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new KeyguardJankBinder();
    }

    private KeyguardJankBinder() {
    }

    public static final void access$bind$processStep(KeyguardClockInteractor keyguardClockInteractor, ViewGroup viewGroup, InteractionJankMonitor interactionJankMonitor, TransitionStep transitionStep, int i) {
        String str;
        ClockConfig config;
        ClockController clockController = keyguardClockInteractor.clock$receiver.clock;
        if (clockController == null || (config = clockController.getConfig()) == null || (str = config.getId()) == null) {
            Log.e(KeyguardClockInteractorKt.TAG, "No clock is available");
            str = "MISSING_CLOCK_ID";
        }
        int i2 = WhenMappings.$EnumSwitchMapping$0[transitionStep.transitionState.ordinal()];
        if (i2 == 1) {
            interactionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withView(i, viewGroup).setTag(str));
            return;
        }
        if (i2 == 2) {
            interactionJankMonitor.cancel(i);
        } else if (i2 == 3) {
            interactionJankMonitor.end(i);
        } else if (i2 != 4) {
            throw new NoWhenBranchMatchedException();
        }
    }

    public static final RepeatWhenAttachedKt$repeatWhenAttached$1 bind(ViewGroup viewGroup, KeyguardJankViewModel keyguardJankViewModel, InteractionJankMonitor interactionJankMonitor, KeyguardClockInteractor keyguardClockInteractor, KeyguardViewMediator keyguardViewMediator, CoroutineDispatcher coroutineDispatcher) {
        if (interactionJankMonitor == null) {
            return null;
        }
        return RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, coroutineDispatcher, new KeyguardJankBinder$bind$1(keyguardJankViewModel, keyguardViewMediator, keyguardClockInteractor, viewGroup, interactionJankMonitor, null));
    }
}
