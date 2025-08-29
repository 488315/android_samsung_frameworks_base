package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import android.app.DreamManager;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepository;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl;
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
public final class FromDozingTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_DREAMING_DURATION;
    public static final long TO_GLANCEABLE_HUB_DURATION;
    public static final long TO_GONE_DURATION;
    public static final long TO_LOCKSCREEN_DURATION;
    public static final long TO_OCCLUDED_DURATION;
    public static final long TO_PRIMARY_BOUNCER_DURATION;
    public final CommunalInteractor communalInteractor;
    public final CommunalSceneInteractor communalSceneInteractor;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final DeviceEntryInteractor deviceEntryInteractor;
    public final DreamManager dreamManager;
    public final InternalKeyguardTransitionInteractor internalTransitionInteractor;
    public final KeyguardBouncerRepository keyguardBouncerRepository;
    public final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl;
    public final CoroutineScope scope;
    public final KeyguardTransitionRepository transitionRepository;
    public final KeyguardWakeDirectlyToGoneInteractor wakeToGoneInteractor;

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
                iArr[KeyguardState.DREAMING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardState.GONE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KeyguardState.GLANCEABLE_HUB.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KeyguardState.LOCKSCREEN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KeyguardState.OCCLUDED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[KeyguardState.PRIMARY_BOUNCER.ordinal()] = 6;
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
        TO_DREAMING_DURATION = DurationKt.toDuration(300, durationUnit);
        TO_GLANCEABLE_HUB_DURATION = duration;
        TO_GONE_DURATION = duration;
        TO_LOCKSCREEN_DURATION = duration;
        TO_OCCLUDED_DURATION = DurationKt.toDuration(550, durationUnit);
        TO_PRIMARY_BOUNCER_DURATION = duration;
    }

    public FromDozingTransitionInteractor(KeyguardBouncerRepository keyguardBouncerRepository, KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, KeyguardTransitionRepository keyguardTransitionRepository, InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, KeyguardInteractor keyguardInteractor, PowerInteractor powerInteractor, CommunalInteractor communalInteractor, CommunalSettingsInteractor communalSettingsInteractor, CommunalSceneInteractor communalSceneInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor, DeviceEntryInteractor deviceEntryInteractor, KeyguardWakeDirectlyToGoneInteractor keyguardWakeDirectlyToGoneInteractor, DreamManager dreamManager) {
        super(KeyguardState.DOZING, keyguardTransitionInteractor, coroutineDispatcher2, coroutineDispatcher, powerInteractor, keyguardOcclusionInteractor, keyguardInteractor, null);
        this.keyguardBouncerRepository = keyguardBouncerRepository;
        this.keyguardViewMediatorHelperImpl = keyguardViewMediatorHelperImpl;
        this.transitionRepository = keyguardTransitionRepository;
        this.internalTransitionInteractor = internalKeyguardTransitionInteractor;
        this.scope = coroutineScope;
        this.communalInteractor = communalInteractor;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.communalSceneInteractor = communalSceneInteractor;
        this.deviceEntryInteractor = deviceEntryInteractor;
        this.wakeToGoneInteractor = keyguardWakeDirectlyToGoneInteractor;
        this.dreamManager = dreamManager;
    }

    public static final boolean access$canDismissLockscreen(FromDozingTransitionInteractor fromDozingTransitionInteractor) {
        KeyguardInteractor keyguardInteractor = fromDozingTransitionInteractor.keyguardInteractor;
        return !((Boolean) keyguardInteractor.isKeyguardShowing.getValue()).booleanValue() && ((Boolean) keyguardInteractor.isKeyguardDismissible.getValue()).booleanValue();
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final ValueAnimator getDefaultAnimatorForTransitionsToState(KeyguardState keyguardState) {
        long j;
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        switch (WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()]) {
            case 1:
                j = TO_DREAMING_DURATION;
                break;
            case 2:
                j = TO_GONE_DURATION;
                break;
            case 3:
                j = TO_GLANCEABLE_HUB_DURATION;
                break;
            case 4:
                j = TO_LOCKSCREEN_DURATION;
                break;
            case 5:
                j = TO_OCCLUDED_DURATION;
                break;
            case 6:
                j = TO_PRIMARY_BOUNCER_DURATION;
                break;
            default:
                j = DEFAULT_DURATION;
                break;
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
        FromDozingTransitionInteractor$listenForDozingToAny$1 fromDozingTransitionInteractor$listenForDozingToAny$1 = new FromDozingTransitionInteractor$listenForDozingToAny$1(this, null);
        CoroutineScope coroutineScope = this.scope;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, fromDozingTransitionInteractor$listenForDozingToAny$1, 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromDozingTransitionInteractor$listenForDozingToDreaming$1(this, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromDozingTransitionInteractor$listenForDozingToGoneViaBiometrics$1(this, null), 7);
        listenForTransitionToCamera(coroutineScope, this.keyguardInteractor);
    }
}
