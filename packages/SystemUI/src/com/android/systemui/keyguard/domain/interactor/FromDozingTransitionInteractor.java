package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.systemui.Flags;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.deviceentry.data.repository.DeviceEntryRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

public final class FromDozingTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_GONE_DURATION;
    public static final long TO_LOCKSCREEN_DURATION;
    public static final long TO_PRIMARY_BOUNCER_DURATION;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 canTransitionToGoneOnWake;
    public final CommunalInteractor communalInteractor;
    public final DeviceEntryRepository deviceEntryRepository;
    public final CoroutineScope scope;
    public final KeyguardTransitionRepository transitionRepository;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        long duration = DurationKt.toDuration(500, DurationUnit.MILLISECONDS);
        DEFAULT_DURATION = duration;
        TO_LOCKSCREEN_DURATION = duration;
        TO_GONE_DURATION = duration;
        TO_PRIMARY_BOUNCER_DURATION = duration;
    }

    public FromDozingTransitionInteractor(KeyguardTransitionRepository keyguardTransitionRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, KeyguardInteractor keyguardInteractor, PowerInteractor powerInteractor, CommunalInteractor communalInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor, DeviceEntryRepository deviceEntryRepository) {
        super(KeyguardState.DOZING, keyguardTransitionInteractor, coroutineDispatcher2, coroutineDispatcher, powerInteractor, keyguardOcclusionInteractor, keyguardInteractor, null);
        this.transitionRepository = keyguardTransitionRepository;
        this.scope = coroutineScope;
        this.communalInteractor = communalInteractor;
        this.deviceEntryRepository = deviceEntryRepository;
        this.canTransitionToGoneOnWake = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardInteractor.isKeyguardShowing, keyguardInteractor.isKeyguardDismissible, new FromDozingTransitionInteractor$canTransitionToGoneOnWake$1(null));
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final ValueAnimator getDefaultAnimatorForTransitionsToState(KeyguardState keyguardState) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        valueAnimator.setDuration(Duration.m2538getInWholeMillisecondsimpl(DEFAULT_DURATION));
        return valueAnimator;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final KeyguardTransitionRepository getTransitionRepository() {
        return this.transitionRepository;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final void start() {
        Flags.keyguardWmStateRefactor();
        FromDozingTransitionInteractor$listenForDozingToAny$1 fromDozingTransitionInteractor$listenForDozingToAny$1 = new FromDozingTransitionInteractor$listenForDozingToAny$1(this, null);
        CoroutineScope coroutineScope = this.scope;
        BuildersKt.launch$default(coroutineScope, null, null, fromDozingTransitionInteractor$listenForDozingToAny$1, 3);
        Flags.keyguardWmStateRefactor();
        BuildersKt.launch$default(coroutineScope, null, null, new FromDozingTransitionInteractor$listenForDozingToGoneViaBiometrics$1(this, null), 3);
        Flags.keyguardWmStateRefactor();
        listenForTransitionToCamera(coroutineScope, this.keyguardInteractor);
    }
}
