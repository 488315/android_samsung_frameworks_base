package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.systemui.Flags;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

public final class FromGoneTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_AOD_DURATION;
    public static final long TO_DOZING_DURATION;
    public static final long TO_DREAMING_DURATION;
    public static final long TO_GLANCEABLE_HUB_DURATION;
    public static final long TO_LOCKSCREEN_DURATION;
    public final BiometricSettingsRepository biometricSettingsRepository;
    public final CommunalInteractor communalInteractor;
    public final KeyguardEnabledInteractor keyguardEnabledInteractor;
    public final KeyguardRepository keyguardRepository;
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
                iArr[KeyguardState.DREAMING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardState.AOD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KeyguardState.DOZING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KeyguardState.LOCKSCREEN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KeyguardState.GLANCEABLE_HUB.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        long duration = DurationKt.toDuration(500, durationUnit);
        DEFAULT_DURATION = duration;
        TO_DREAMING_DURATION = DurationKt.toDuration(933, durationUnit);
        TO_AOD_DURATION = DurationKt.toDuration(1300, durationUnit);
        TO_DOZING_DURATION = DurationKt.toDuration(933, durationUnit);
        TO_LOCKSCREEN_DURATION = duration;
        TO_GLANCEABLE_HUB_DURATION = duration;
    }

    public FromGoneTransitionInteractor(KeyguardTransitionRepository keyguardTransitionRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, KeyguardInteractor keyguardInteractor, PowerInteractor powerInteractor, CommunalInteractor communalInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor, BiometricSettingsRepository biometricSettingsRepository, KeyguardRepository keyguardRepository, KeyguardEnabledInteractor keyguardEnabledInteractor) {
        super(KeyguardState.GONE, keyguardTransitionInteractor, coroutineDispatcher2, coroutineDispatcher, powerInteractor, keyguardOcclusionInteractor, keyguardInteractor, null);
        this.transitionRepository = keyguardTransitionRepository;
        this.scope = coroutineScope;
        this.communalInteractor = communalInteractor;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final ValueAnimator getDefaultAnimatorForTransitionsToState(KeyguardState keyguardState) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        int i = WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()];
        valueAnimator.setDuration(Duration.m2538getInWholeMillisecondsimpl(i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? DEFAULT_DURATION : TO_GLANCEABLE_HUB_DURATION : TO_LOCKSCREEN_DURATION : TO_DOZING_DURATION : TO_AOD_DURATION : TO_DREAMING_DURATION));
        return valueAnimator;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final KeyguardTransitionRepository getTransitionRepository() {
        return this.transitionRepository;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final void start() {
        Flags.sceneContainer();
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        FromGoneTransitionInteractor$listenForGoneToAodOrDozing$$inlined$launch$default$1 fromGoneTransitionInteractor$listenForGoneToAodOrDozing$$inlined$launch$default$1 = new FromGoneTransitionInteractor$listenForGoneToAodOrDozing$$inlined$launch$default$1("FromGoneTransitionInteractor#listenForGoneToAodOrDozing", null, this);
        CoroutineScope coroutineScope = this.scope;
        BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, fromGoneTransitionInteractor$listenForGoneToAodOrDozing$$inlined$launch$default$1, 2);
        BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new FromGoneTransitionInteractor$listenForGoneToDreaming$$inlined$launch$default$1("FromGoneTransitionInteractor#listenForGoneToDreaming", null, this), 2);
        Flags.keyguardWmStateRefactor();
        BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new FromGoneTransitionInteractor$listenForGoneToLockscreenOrHub$$inlined$launch$default$2("FromGoneTransitionInteractor#listenForGoneToLockscreenOrHub", null, this), 2);
        BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new FromGoneTransitionInteractor$listenForGoneToDreamingLockscreenHosted$$inlined$launch$default$1("FromGoneTransitionInteractor#listenForGoneToDreamingLockscreenHosted", null, this), 2);
    }
}
