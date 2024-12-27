package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.systemui.Flags;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

public final class FromOccludedTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_AOD_DURATION;
    public static final long TO_DOZING_DURATION;
    public static final long TO_GLANCEABLE_HUB_DURATION;
    public static final long TO_LOCKSCREEN_DURATION;
    public final CommunalInteractor communalInteractor;
    public final CoroutineScope scope;
    public final KeyguardTransitionRepository transitionRepository;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KeyguardState.values().length];
            try {
                iArr[KeyguardState.ALTERNATE_BOUNCER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardState.LOCKSCREEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KeyguardState.GLANCEABLE_HUB.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        long duration = DurationKt.toDuration(500, durationUnit);
        DEFAULT_DURATION = duration;
        TO_LOCKSCREEN_DURATION = DurationKt.toDuration(933, durationUnit);
        TO_GLANCEABLE_HUB_DURATION = DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit);
        TO_AOD_DURATION = duration;
        TO_DOZING_DURATION = duration;
    }

    public FromOccludedTransitionInteractor(KeyguardTransitionRepository keyguardTransitionRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, KeyguardInteractor keyguardInteractor, PowerInteractor powerInteractor, CommunalInteractor communalInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor) {
        super(KeyguardState.OCCLUDED, keyguardTransitionInteractor, coroutineDispatcher2, coroutineDispatcher, powerInteractor, keyguardOcclusionInteractor, keyguardInteractor, null);
        this.transitionRepository = keyguardTransitionRepository;
        this.scope = coroutineScope;
        this.communalInteractor = communalInteractor;
    }

    public static final Object access$startTransitionToLockscreenOrHub(FromOccludedTransitionInteractor fromOccludedTransitionInteractor, FromOccludedTransitionInteractor fromOccludedTransitionInteractor2, boolean z, boolean z2, Continuation continuation) {
        fromOccludedTransitionInteractor.getClass();
        Flags.FEATURE_FLAGS.getClass();
        if (!z && !z2) {
            Object startTransitionTo$default = TransitionInteractor.startTransitionTo$default(fromOccludedTransitionInteractor2, KeyguardState.LOCKSCREEN, null, null, null, continuation, 14);
            return startTransitionTo$default == CoroutineSingletons.COROUTINE_SUSPENDED ? startTransitionTo$default : Unit.INSTANCE;
        }
        Flags.sceneContainer();
        Object startTransitionTo$default2 = TransitionInteractor.startTransitionTo$default(fromOccludedTransitionInteractor2, KeyguardState.GLANCEABLE_HUB, null, null, null, continuation, 14);
        return startTransitionTo$default2 == CoroutineSingletons.COROUTINE_SUSPENDED ? startTransitionTo$default2 : Unit.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final ValueAnimator getDefaultAnimatorForTransitionsToState(KeyguardState keyguardState) {
        long duration;
        ValueAnimator valueAnimator = new ValueAnimator();
        int[] iArr = WhenMappings.$EnumSwitchMapping$0;
        valueAnimator.setInterpolator(iArr[keyguardState.ordinal()] == 1 ? Interpolators.FAST_OUT_SLOW_IN : Interpolators.LINEAR);
        int i = iArr[keyguardState.ordinal()];
        if (i != 2) {
            duration = i != 3 ? DEFAULT_DURATION : TO_GLANCEABLE_HUB_DURATION;
        } else {
            Duration.Companion companion = Duration.Companion;
            duration = DurationKt.toDuration(0, DurationUnit.MILLISECONDS);
        }
        valueAnimator.setDuration(Duration.m2538getInWholeMillisecondsimpl(duration));
        return valueAnimator;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final KeyguardTransitionRepository getTransitionRepository() {
        return this.transitionRepository;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final void start() {
        Flags.keyguardWmStateRefactor();
        FromOccludedTransitionInteractor$listenForOccludedToLockscreenOrHub$2 fromOccludedTransitionInteractor$listenForOccludedToLockscreenOrHub$2 = new FromOccludedTransitionInteractor$listenForOccludedToLockscreenOrHub$2(this, null);
        CoroutineScope coroutineScope = this.scope;
        BuildersKt.launch$default(coroutineScope, null, null, fromOccludedTransitionInteractor$listenForOccludedToLockscreenOrHub$2, 3);
        BuildersKt.launch$default(coroutineScope, null, null, new FromOccludedTransitionInteractor$listenForOccludedToDreaming$1(this, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new FromOccludedTransitionInteractor$listenForOccludedToAsleep$1(this, null), 3);
        Flags.sceneContainer();
        Flags.keyguardWmStateRefactor();
        BuildersKt.launch$default(coroutineScope, null, null, new FromOccludedTransitionInteractor$listenForOccludedToGone$1(this, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new FromOccludedTransitionInteractor$listenForOccludedToAlternateBouncer$1(this, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new FromOccludedTransitionInteractor$listenForOccludedToPrimaryBouncer$1(this, null), 3);
    }
}
