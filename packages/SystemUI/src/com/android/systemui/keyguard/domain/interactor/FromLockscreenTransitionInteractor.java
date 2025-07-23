package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.power.shared.model.WakeSleepReason;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.shade.data.repository.ShadeRepository;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class FromLockscreenTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_AOD_DURATION;
    public static final long TO_AOD_FOLD_DURATION;
    public static final long TO_DOZING_DURATION;
    public static final long TO_DREAMING_DURATION;
    public static final long TO_GLANCEABLE_HUB_DURATION;
    public static final long TO_GONE_DURATION;
    public static final long TO_OCCLUDED_DURATION;
    public static final long TO_PRIMARY_BOUNCER_DURATION;
    public final CoroutineScope applicationScope;
    public final CommunalSceneInteractor communalSceneInteractor;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final InternalKeyguardTransitionInteractor internalTransitionInteractor;
    public final CoroutineScope scope;
    public final ShadeRepository shadeRepository;
    public final Flow surfaceBehindVisibility;
    public final SwipeToDismissInteractor swipeToDismissInteractor;
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
                iArr[KeyguardState.DREAMING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardState.OCCLUDED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KeyguardState.AOD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KeyguardState.DOZING.ordinal()] = 4;
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
        long duration = DurationKt.toDuration(400, durationUnit);
        DEFAULT_DURATION = duration;
        TO_DOZING_DURATION = DurationKt.toDuration(500, durationUnit);
        TO_DREAMING_DURATION = DurationKt.toDuration(933, durationUnit);
        TO_OCCLUDED_DURATION = DurationKt.toDuration(550, durationUnit);
        TO_AOD_DURATION = DurationKt.toDuration(500, durationUnit);
        TO_AOD_FOLD_DURATION = DurationKt.toDuration(1100, durationUnit);
        TO_PRIMARY_BOUNCER_DURATION = duration;
        TO_GONE_DURATION = DurationKt.toDuration(633, durationUnit);
        TO_GLANCEABLE_HUB_DURATION = DurationKt.toDuration(1, DurationUnit.SECONDS);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public FromLockscreenTransitionInteractor(com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository r10, com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor r11, com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r12, kotlinx.coroutines.CoroutineScope r13, kotlinx.coroutines.CoroutineScope r14, kotlinx.coroutines.CoroutineDispatcher r15, kotlinx.coroutines.CoroutineDispatcher r16, com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r17, com.android.systemui.shade.data.repository.ShadeRepository r18, com.android.systemui.power.domain.interactor.PowerInteractor r19, com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor r20, com.android.systemui.communal.domain.interactor.CommunalSceneInteractor r21, com.android.systemui.keyguard.domain.interactor.SwipeToDismissInteractor r22, com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor r23) {
        /*
            r9 = this;
            com.android.systemui.keyguard.shared.model.KeyguardState r1 = com.android.systemui.keyguard.shared.model.KeyguardState.LOCKSCREEN
            r8 = 0
            r0 = r9
            r2 = r12
            r4 = r15
            r3 = r16
            r7 = r17
            r5 = r19
            r6 = r23
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            r9.transitionRepository = r10
            r9.internalTransitionInteractor = r11
            r9.scope = r13
            r9.applicationScope = r14
            r10 = r18
            r9.shadeRepository = r10
            r10 = r20
            r9.communalSettingsInteractor = r10
            r10 = r21
            r9.communalSceneInteractor = r10
            r10 = r22
            r9.swipeToDismissInteractor = r10
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
            com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$special$$inlined$map$1 r11 = new com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$special$$inlined$map$1
            r11.<init>()
            com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$surfaceBehindVisibility$2 r10 = new com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$surfaceBehindVisibility$2
            r12 = 0
            r10.<init>(r12)
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 r12 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1
            r12.<init>(r10, r11)
            kotlinx.coroutines.flow.Flow r10 = kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(r12)
            r9.surfaceBehindVisibility = r10
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor.<init>(com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository, com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor, com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor, kotlinx.coroutines.CoroutineScope, kotlinx.coroutines.CoroutineScope, kotlinx.coroutines.CoroutineDispatcher, kotlinx.coroutines.CoroutineDispatcher, com.android.systemui.keyguard.domain.interactor.KeyguardInteractor, com.android.systemui.shade.data.repository.ShadeRepository, com.android.systemui.power.domain.interactor.PowerInteractor, com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor, com.android.systemui.communal.domain.interactor.CommunalSceneInteractor, com.android.systemui.keyguard.domain.interactor.SwipeToDismissInteractor, com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor):void");
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final ValueAnimator getDefaultAnimatorForTransitionsToState(KeyguardState keyguardState) {
        long m3441plusLRDsOJo;
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        int i = WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()];
        if (i != 1) {
            m3441plusLRDsOJo = i != 2 ? i != 3 ? i != 4 ? i != 5 ? DEFAULT_DURATION : TO_GLANCEABLE_HUB_DURATION : TO_DOZING_DURATION : ((WakefulnessModel) this.powerInteractor.detailedWakefulness.$$delegate_0.getValue()).lastSleepReason == WakeSleepReason.FOLD ? TO_AOD_FOLD_DURATION : TO_AOD_DURATION : TO_OCCLUDED_DURATION;
        } else {
            Duration.Companion companion = Duration.Companion;
            m3441plusLRDsOJo = Duration.m3441plusLRDsOJo(TO_DREAMING_DURATION, DurationKt.toDuration(100, DurationUnit.MILLISECONDS));
        }
        valueAnimator.setDuration(Duration.m3437getInWholeMillisecondsimpl(m3441plusLRDsOJo));
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
        FromLockscreenTransitionInteractor$listenForLockscreenToGone$1 fromLockscreenTransitionInteractor$listenForLockscreenToGone$1 = new FromLockscreenTransitionInteractor$listenForLockscreenToGone$1(this, null);
        CoroutineScope coroutineScope = this.scope;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, fromLockscreenTransitionInteractor$listenForLockscreenToGone$1, 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromLockscreenTransitionInteractor$listenForLockscreenToOccludedOrDreaming$2(this, null), 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromLockscreenTransitionInteractor$listenForLockscreenToAodOrDozing$1(this, null), 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncer$1(this, null), 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromLockscreenTransitionInteractor$listenForLockscreenToDreaming$1(this, ArraysKt___ArraysKt.toSet(new KeyguardState[]{KeyguardState.AOD, KeyguardState.DOZING}), null), 6);
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1(this, ref$ObjectRef, null), 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$2(this, ref$ObjectRef, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromLockscreenTransitionInteractor$listenForLockscreenToAlternateBouncer$1(this, null), 6);
        listenForTransitionToCamera(coroutineScope, this.keyguardInteractor);
        this.communalSettingsInteractor.isV2FlagEnabled();
    }
}
