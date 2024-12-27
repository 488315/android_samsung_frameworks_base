package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.systemui.Flags;
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

public final class FromDreamingLockscreenHostedTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_LOCKSCREEN_DURATION;
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
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        DEFAULT_DURATION = DurationKt.toDuration(500, durationUnit);
        TO_LOCKSCREEN_DURATION = DurationKt.toDuration(1167, durationUnit);
    }

    public FromDreamingLockscreenHostedTransitionInteractor(KeyguardTransitionRepository keyguardTransitionRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, KeyguardInteractor keyguardInteractor, PowerInteractor powerInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor) {
        super(KeyguardState.DREAMING_LOCKSCREEN_HOSTED, keyguardTransitionInteractor, coroutineDispatcher2, coroutineDispatcher, powerInteractor, keyguardOcclusionInteractor, keyguardInteractor, null);
        this.transitionRepository = keyguardTransitionRepository;
        this.scope = coroutineScope;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final ValueAnimator getDefaultAnimatorForTransitionsToState(KeyguardState keyguardState) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        valueAnimator.setDuration(keyguardState == KeyguardState.LOCKSCREEN ? Duration.m2538getInWholeMillisecondsimpl(TO_LOCKSCREEN_DURATION) : Duration.m2538getInWholeMillisecondsimpl(DEFAULT_DURATION));
        return valueAnimator;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final KeyguardTransitionRepository getTransitionRepository() {
        return this.transitionRepository;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final void start() {
        FromDreamingLockscreenHostedTransitionInteractor$listenForDreamingLockscreenHostedToLockscreen$1 fromDreamingLockscreenHostedTransitionInteractor$listenForDreamingLockscreenHostedToLockscreen$1 = new FromDreamingLockscreenHostedTransitionInteractor$listenForDreamingLockscreenHostedToLockscreen$1(this, null);
        CoroutineScope coroutineScope = this.scope;
        BuildersKt.launch$default(coroutineScope, null, null, fromDreamingLockscreenHostedTransitionInteractor$listenForDreamingLockscreenHostedToLockscreen$1, 3);
        Flags.sceneContainer();
        BuildersKt.launch$default(coroutineScope, null, null, new FromDreamingLockscreenHostedTransitionInteractor$listenForDreamingLockscreenHostedToGone$1(this, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new FromDreamingLockscreenHostedTransitionInteractor$listenForDreamingLockscreenHostedToDozing$1(this, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new FromDreamingLockscreenHostedTransitionInteractor$listenForDreamingLockscreenHostedToOccluded$1(this, null), 3);
        Flags.sceneContainer();
        BuildersKt.launch$default(coroutineScope, null, null, new FromDreamingLockscreenHostedTransitionInteractor$listenForDreamingLockscreenHostedToPrimaryBouncer$1(this, null), 3);
    }
}
