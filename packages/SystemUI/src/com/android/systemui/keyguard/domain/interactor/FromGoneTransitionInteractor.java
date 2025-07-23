package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class FromGoneTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_AOD_DURATION;
    public static final long TO_DOZING_DURATION;
    public static final long TO_DREAMING_DURATION;
    public static final long TO_GLANCEABLE_HUB_DURATION;
    public static final long TO_LOCKSCREEN_DURATION;
    public static final long TO_OCCLUDED_DURATION;
    public final CommunalSceneInteractor communalSceneInteractor;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final InternalKeyguardTransitionInteractor internalTransitionInteractor;
    public final KeyguardShowWhileAwakeInteractor keyguardShowWhileAwakeInteractor;
    public final CoroutineScope scope;
    public final KeyguardTransitionRepository transitionRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KeyguardState.values().length];
            try {
                iArr[KeyguardState.AOD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardState.DOZING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KeyguardState.DREAMING.ordinal()] = 3;
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
            try {
                iArr[KeyguardState.OCCLUDED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        long duration = DurationKt.toDuration(500, durationUnit);
        DEFAULT_DURATION = duration;
        TO_AOD_DURATION = DurationKt.toDuration(1300, durationUnit);
        TO_DOZING_DURATION = DurationKt.toDuration(933, durationUnit);
        TO_DREAMING_DURATION = DurationKt.toDuration(933, durationUnit);
        TO_LOCKSCREEN_DURATION = duration;
        TO_GLANCEABLE_HUB_DURATION = duration;
        TO_OCCLUDED_DURATION = DurationKt.toDuration(100, durationUnit);
    }

    public FromGoneTransitionInteractor(KeyguardTransitionRepository keyguardTransitionRepository, InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, KeyguardInteractor keyguardInteractor, PowerInteractor powerInteractor, CommunalSceneInteractor communalSceneInteractor, CommunalSettingsInteractor communalSettingsInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor, KeyguardShowWhileAwakeInteractor keyguardShowWhileAwakeInteractor) {
        super(KeyguardState.GONE, keyguardTransitionInteractor, coroutineDispatcher2, coroutineDispatcher, powerInteractor, keyguardOcclusionInteractor, keyguardInteractor, null);
        this.transitionRepository = keyguardTransitionRepository;
        this.internalTransitionInteractor = internalKeyguardTransitionInteractor;
        this.scope = coroutineScope;
        this.communalSceneInteractor = communalSceneInteractor;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.keyguardShowWhileAwakeInteractor = keyguardShowWhileAwakeInteractor;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final ValueAnimator getDefaultAnimatorForTransitionsToState(KeyguardState keyguardState) {
        long j;
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        switch (WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()]) {
            case 1:
                j = TO_AOD_DURATION;
                break;
            case 2:
                j = TO_DOZING_DURATION;
                break;
            case 3:
                j = TO_DREAMING_DURATION;
                break;
            case 4:
                j = TO_LOCKSCREEN_DURATION;
                break;
            case 5:
                j = TO_GLANCEABLE_HUB_DURATION;
                break;
            case 6:
                j = TO_OCCLUDED_DURATION;
                break;
            default:
                j = DEFAULT_DURATION;
                break;
        }
        valueAnimator.setDuration(Duration.m3437getInWholeMillisecondsimpl(j));
        return valueAnimator;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final InternalKeyguardTransitionInteractor getInternalTransitionInteractor() {
        return this.internalTransitionInteractor;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final KeyguardTransitionRepository getTransitionRepository() {
        return this.transitionRepository;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final void start() {
        FromGoneTransitionInteractor$listenForGoneToAodOrDozing$1 fromGoneTransitionInteractor$listenForGoneToAodOrDozing$1 = new FromGoneTransitionInteractor$listenForGoneToAodOrDozing$1(this, null);
        CoroutineScope coroutineScope = this.scope;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, fromGoneTransitionInteractor$listenForGoneToAodOrDozing$1, 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromGoneTransitionInteractor$listenForGoneToDreaming$1(this, null), 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, this.mainDispatcher, null, new FromGoneTransitionInteractor$listenForGoneToLockscreenOrHubOrOccluded$2(this, null), 4);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromGoneTransitionInteractor$listenForGoneToOccluded$1(this, null), 6);
    }
}
