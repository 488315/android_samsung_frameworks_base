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

/* loaded from: classes2.dex */
public final class FromGlanceableHubTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_AOD_DURATION;
    public static final long TO_BOUNCER_DURATION;
    public static final long TO_LOCKSCREEN_DURATION;
    public static final long TO_OCCLUDED_DURATION;
    public final CommunalSceneInteractor communalSceneInteractor;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final InternalKeyguardTransitionInteractor internalTransitionInteractor;
    public final CoroutineScope scope;
    public final KeyguardTransitionRepository transitionRepository;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KeyguardState.values().length];
            try {
                iArr[KeyguardState.LOCKSCREEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardState.OCCLUDED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KeyguardState.ALTERNATE_BOUNCER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KeyguardState.PRIMARY_BOUNCER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        DEFAULT_DURATION = DurationKt.toDuration(400, durationUnit);
        TO_LOCKSCREEN_DURATION = DurationKt.toDuration(1, DurationUnit.SECONDS);
        TO_BOUNCER_DURATION = DurationKt.toDuration(400, durationUnit);
        TO_OCCLUDED_DURATION = DurationKt.toDuration(450, durationUnit);
        TO_AOD_DURATION = DurationKt.toDuration(500, durationUnit);
    }

    public FromGlanceableHubTransitionInteractor(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, CommunalSettingsInteractor communalSettingsInteractor, KeyguardInteractor keyguardInteractor, CommunalSceneInteractor communalSceneInteractor, KeyguardTransitionRepository keyguardTransitionRepository, InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, PowerInteractor powerInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor) {
        super(KeyguardState.GLANCEABLE_HUB, keyguardTransitionInteractor, coroutineDispatcher, coroutineDispatcher2, powerInteractor, keyguardOcclusionInteractor, keyguardInteractor, null);
        this.scope = coroutineScope;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.communalSceneInteractor = communalSceneInteractor;
        this.transitionRepository = keyguardTransitionRepository;
        this.internalTransitionInteractor = internalKeyguardTransitionInteractor;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final ValueAnimator getDefaultAnimatorForTransitionsToState(KeyguardState keyguardState) {
        long j;
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        int i = WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()];
        if (i == 1) {
            j = TO_LOCKSCREEN_DURATION;
        } else if (i != 2) {
            j = TO_BOUNCER_DURATION;
            if (i != 3 && i != 4) {
                j = DEFAULT_DURATION;
            }
        } else {
            j = TO_OCCLUDED_DURATION;
        }
        valueAnimator.setDuration(Duration.m3456getInWholeMillisecondsimpl(j));
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
        CommunalSettingsInteractor communalSettingsInteractor = this.communalSettingsInteractor;
        if (communalSettingsInteractor.isCommunalFlagEnabled()) {
            FromGlanceableHubTransitionInteractor$listenForHubToAodOrDozing$1 fromGlanceableHubTransitionInteractor$listenForHubToAodOrDozing$1 = new FromGlanceableHubTransitionInteractor$listenForHubToAodOrDozing$1(this, null);
            CoroutineScope coroutineScope = this.scope;
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, fromGlanceableHubTransitionInteractor$listenForHubToAodOrDozing$1, 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromGlanceableHubTransitionInteractor$listenForHubToPrimaryBouncer$1(this, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromGlanceableHubTransitionInteractor$listenForHubToAlternateBouncer$1(this, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromGlanceableHubTransitionInteractor$listenForHubToOccluded$2(this, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromGlanceableHubTransitionInteractor$listenForHubToGone$1(this, null), 7);
            communalSettingsInteractor.isV2FlagEnabled();
        }
    }
}
