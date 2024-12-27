package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.systemui.Flags;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.wm.shell.animation.Interpolators;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

public final class FromAlternateBouncerTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long TO_AOD_DURATION;
    public static final long TO_DOZING_DURATION;
    public static final long TO_GONE_DURATION;
    public static final long TO_OCCLUDED_DURATION;
    public static final long TO_PRIMARY_BOUNCER_DURATION;
    public static final long TRANSITION_DURATION_MS;
    public final CommunalInteractor communalInteractor;
    public final PrimaryBouncerInteractor primaryBouncerInteractor;
    public final CoroutineScope scope;
    public final Flow surfaceBehindVisibility;
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
                iArr[KeyguardState.GONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        long duration = DurationKt.toDuration(300, durationUnit);
        TRANSITION_DURATION_MS = duration;
        TO_GONE_DURATION = DurationKt.toDuration(500, durationUnit);
        TO_AOD_DURATION = duration;
        TO_PRIMARY_BOUNCER_DURATION = duration;
        TO_DOZING_DURATION = duration;
        TO_OCCLUDED_DURATION = duration;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public FromAlternateBouncerTransitionInteractor(com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository r12, com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r13, kotlinx.coroutines.CoroutineScope r14, kotlinx.coroutines.CoroutineDispatcher r15, kotlinx.coroutines.CoroutineDispatcher r16, com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r17, com.android.systemui.communal.domain.interactor.CommunalInteractor r18, com.android.systemui.power.domain.interactor.PowerInteractor r19, com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor r20, com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor r21) {
        /*
            r11 = this;
            r9 = r11
            com.android.systemui.keyguard.shared.model.KeyguardState r10 = com.android.systemui.keyguard.shared.model.KeyguardState.ALTERNATE_BOUNCER
            r8 = 0
            r0 = r11
            r1 = r10
            r2 = r13
            r3 = r16
            r4 = r15
            r5 = r19
            r6 = r20
            r7 = r17
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            r0 = r12
            r9.transitionRepository = r0
            r0 = r14
            r9.scope = r0
            r0 = r18
            r9.communalInteractor = r0
            r0 = r21
            r9.primaryBouncerInteractor = r0
            com.android.systemui.keyguard.shared.model.Edge$Companion r0 = com.android.systemui.keyguard.shared.model.Edge.Companion
            com.android.compose.animation.scene.SceneKey r1 = com.android.systemui.scene.shared.model.Scenes.Gone
            r0.getClass()
            com.android.systemui.keyguard.shared.model.Edge$StateToScene r0 = new com.android.systemui.keyguard.shared.model.Edge$StateToScene
            r0.<init>(r10, r1)
            com.android.systemui.keyguard.shared.model.KeyguardState r0 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
            com.android.systemui.keyguard.shared.model.Edge$StateToState r1 = new com.android.systemui.keyguard.shared.model.Edge$StateToState
            r1.<init>(r10, r0)
            r0 = r13
            kotlinx.coroutines.flow.Flow r0 = r13.transition(r1)
            com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$special$$inlined$map$1 r1 = new com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$special$$inlined$map$1
            r1.<init>()
            com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$surfaceBehindVisibility$2 r0 = new com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$surfaceBehindVisibility$2
            r2 = 0
            r0.<init>(r2)
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 r2 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1
            r2.<init>(r0, r1)
            kotlinx.coroutines.flow.Flow r0 = kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(r2)
            r9.surfaceBehindVisibility = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor.<init>(com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository, com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor, kotlinx.coroutines.CoroutineScope, kotlinx.coroutines.CoroutineDispatcher, kotlinx.coroutines.CoroutineDispatcher, com.android.systemui.keyguard.domain.interactor.KeyguardInteractor, com.android.systemui.communal.domain.interactor.CommunalInteractor, com.android.systemui.power.domain.interactor.PowerInteractor, com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor, com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor):void");
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final ValueAnimator getDefaultAnimatorForTransitionsToState(KeyguardState keyguardState) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        valueAnimator.setDuration(Duration.m2538getInWholeMillisecondsimpl(WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()] == 1 ? TO_GONE_DURATION : TRANSITION_DURATION_MS));
        return valueAnimator;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final KeyguardTransitionRepository getTransitionRepository() {
        return this.transitionRepository;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final void start() {
        Flags.sceneContainer();
        Flags.keyguardWmStateRefactor();
        FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToGone$1 fromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToGone$1 = new FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToGone$1(this, null);
        CoroutineScope coroutineScope = this.scope;
        BuildersKt.launch$default(coroutineScope, null, null, fromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToGone$1, 3);
        BuildersKt.launch$default(coroutineScope, null, null, new FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToLockscreenHubAodOrDozing$1(this, null), 3);
        Flags.sceneContainer();
        BuildersKt.launch$default(coroutineScope, null, null, new FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToPrimaryBouncer$1(this, null), 3);
        listenForTransitionToCamera(coroutineScope, this.keyguardInteractor);
    }
}
