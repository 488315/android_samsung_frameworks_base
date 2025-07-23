package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.wm.shell.shared.animation.Interpolators;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class FromAlternateBouncerTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long TO_AOD_DURATION;
    public static final long TO_DOZING_DURATION;
    public static final long TO_GONE_DURATION;
    public static final long TO_LOCKSCREEN_DURATION;
    public static final long TO_OCCLUDED_DURATION;
    public static final long TO_PRIMARY_BOUNCER_DURATION;
    public static final long TRANSITION_DURATION_MS;
    public final CommunalInteractor communalInteractor;
    public final CommunalSceneInteractor communalSceneInteractor;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final InternalKeyguardTransitionInteractor internalTransitionInteractor;
    public final PrimaryBouncerInteractor primaryBouncerInteractor;
    public final CoroutineScope scope;
    public final Flow surfaceBehindVisibility;
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
                iArr[KeyguardState.GONE.ordinal()] = 3;
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
        long duration = DurationKt.toDuration(300, durationUnit);
        TRANSITION_DURATION_MS = duration;
        TO_AOD_DURATION = duration;
        TO_DOZING_DURATION = duration;
        TO_GONE_DURATION = DurationKt.toDuration(500, durationUnit);
        TO_LOCKSCREEN_DURATION = DurationKt.toDuration(300, durationUnit);
        TO_OCCLUDED_DURATION = duration;
        TO_PRIMARY_BOUNCER_DURATION = duration;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public FromAlternateBouncerTransitionInteractor(com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository r10, com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor r11, com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r12, kotlinx.coroutines.CoroutineScope r13, kotlinx.coroutines.CoroutineDispatcher r14, kotlinx.coroutines.CoroutineDispatcher r15, com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r16, com.android.systemui.communal.domain.interactor.CommunalInteractor r17, com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor r18, com.android.systemui.communal.domain.interactor.CommunalSceneInteractor r19, com.android.systemui.power.domain.interactor.PowerInteractor r20, com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor r21, com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor r22) {
        /*
            r9 = this;
            com.android.systemui.keyguard.shared.model.KeyguardState r1 = com.android.systemui.keyguard.shared.model.KeyguardState.ALTERNATE_BOUNCER
            r8 = 0
            r0 = r9
            r2 = r12
            r4 = r14
            r3 = r15
            r7 = r16
            r5 = r20
            r6 = r21
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            r9.transitionRepository = r10
            r9.internalTransitionInteractor = r11
            r9.scope = r13
            r10 = r17
            r9.communalInteractor = r10
            r10 = r18
            r9.communalSettingsInteractor = r10
            r10 = r19
            r9.communalSceneInteractor = r10
            r10 = r22
            r9.primaryBouncerInteractor = r10
            com.android.systemui.keyguard.shared.model.Edge$Companion r10 = com.android.systemui.keyguard.shared.model.Edge.Companion
            com.android.compose.animation.scene.SceneKey r11 = com.android.systemui.scene.shared.model.Scenes.Gone
            r10.getClass()
            com.android.systemui.keyguard.shared.model.Edge$StateToContent r13 = new com.android.systemui.keyguard.shared.model.Edge$StateToContent
            r13.<init>(r1, r11)
            com.android.systemui.keyguard.shared.model.KeyguardState r11 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
            r10.getClass()
            com.android.systemui.keyguard.shared.model.Edge$StateToState r10 = new com.android.systemui.keyguard.shared.model.Edge$StateToState
            r10.<init>(r1, r11)
            r12.getClass()
            kotlinx.coroutines.flow.Flow r10 = r12.transition(r10)
            com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$special$$inlined$map$1 r11 = new com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$special$$inlined$map$1
            r11.<init>()
            com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$surfaceBehindVisibility$2 r10 = new com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$surfaceBehindVisibility$2
            r12 = 0
            r10.<init>(r12)
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 r12 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1
            r12.<init>(r10, r11)
            kotlinx.coroutines.flow.Flow r10 = kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(r12)
            r9.surfaceBehindVisibility = r10
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor.<init>(com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository, com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor, com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor, kotlinx.coroutines.CoroutineScope, kotlinx.coroutines.CoroutineDispatcher, kotlinx.coroutines.CoroutineDispatcher, com.android.systemui.keyguard.domain.interactor.KeyguardInteractor, com.android.systemui.communal.domain.interactor.CommunalInteractor, com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor, com.android.systemui.communal.domain.interactor.CommunalSceneInteractor, com.android.systemui.power.domain.interactor.PowerInteractor, com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor, com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor):void");
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
                j = TO_GONE_DURATION;
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
                j = TRANSITION_DURATION_MS;
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
        FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToGone$1 fromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToGone$1 = new FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToGone$1(this, null);
        CoroutineScope coroutineScope = this.scope;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, fromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToGone$1, 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToLockscreenHubAodOrDozing$1(this, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToPrimaryBouncer$1(this, null), 7);
        listenForTransitionToCamera(coroutineScope, this.keyguardInteractor);
    }
}
