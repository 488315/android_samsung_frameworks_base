package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import android.app.DreamManager;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
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
public final class FromDreamingTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_AOD_DURATION;
    public static final long TO_GLANCEABLE_HUB_DURATION;
    public static final long TO_GONE_DURATION;
    public static final long TO_LOCKSCREEN_DURATION;
    public static final long TO_PRIMARY_BOUNCER_DURATION;
    public final CommunalInteractor communalInteractor;
    public final CommunalSceneInteractor communalSceneInteractor;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final DreamManager dreamManager;
    public final InternalKeyguardTransitionInteractor internalTransitionInteractor;
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
                iArr[KeyguardState.LOCKSCREEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardState.GLANCEABLE_HUB.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        long duration = DurationKt.toDuration(500, durationUnit);
        DEFAULT_DURATION = duration;
        TO_GLANCEABLE_HUB_DURATION = DurationKt.toDuration(1, DurationUnit.SECONDS);
        TO_LOCKSCREEN_DURATION = DurationKt.toDuration(1167, durationUnit);
        TO_AOD_DURATION = DurationKt.toDuration(300, durationUnit);
        TO_GONE_DURATION = duration;
        TO_PRIMARY_BOUNCER_DURATION = duration;
    }

    public FromDreamingTransitionInteractor(KeyguardTransitionRepository keyguardTransitionRepository, InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, KeyguardInteractor keyguardInteractor, CommunalInteractor communalInteractor, CommunalSceneInteractor communalSceneInteractor, CommunalSettingsInteractor communalSettingsInteractor, PowerInteractor powerInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor, DreamManager dreamManager, DeviceEntryInteractor deviceEntryInteractor) {
        super(KeyguardState.DREAMING, keyguardTransitionInteractor, coroutineDispatcher2, coroutineDispatcher, powerInteractor, keyguardOcclusionInteractor, keyguardInteractor, null);
        this.transitionRepository = keyguardTransitionRepository;
        this.internalTransitionInteractor = internalKeyguardTransitionInteractor;
        this.scope = coroutineScope;
        this.communalInteractor = communalInteractor;
        this.communalSceneInteractor = communalSceneInteractor;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.dreamManager = dreamManager;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final ValueAnimator getDefaultAnimatorForTransitionsToState(KeyguardState keyguardState) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        int i = WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()];
        valueAnimator.setDuration(Duration.m3437getInWholeMillisecondsimpl(i != 1 ? i != 2 ? DEFAULT_DURATION : TO_GLANCEABLE_HUB_DURATION : TO_LOCKSCREEN_DURATION));
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
        FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$1 fromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$1 = new FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$1(this, null);
        CoroutineScope coroutineScope = this.scope;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, fromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$1, 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromDreamingTransitionInteractor$listenForDreamingToOccluded$1(this, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromDreamingTransitionInteractor$listenForDreamingToGoneWhenDismissable$1(this, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromDreamingTransitionInteractor$listenForDreamingToGoneFromBiometricUnlock$1(this, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromDreamingTransitionInteractor$listenForDreamingToAodOrDozing$1(this, null), 7);
        listenForTransitionToCamera(coroutineScope, this.keyguardInteractor);
        if (this.communalSettingsInteractor.isCommunalFlagEnabled()) {
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromDreamingTransitionInteractor$listenForDreamingToGlanceableHubFromPowerButton$1(this, null), 7);
        }
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromDreamingTransitionInteractor$listenForDreamingToPrimaryBouncer$1(this, null), 7);
    }

    public final void startToLockscreenOrGlanceableHubTransition(boolean z) {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new FromDreamingTransitionInteractor$startToLockscreenOrGlanceableHubTransition$1(this, z, null), 7);
    }
}
