package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.systemui.Flags;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.shade.data.repository.ShadeRepository;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FromLockscreenTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_AOD_DURATION;
    public static final long TO_DOZING_DURATION;
    public static final long TO_DREAMING_DURATION;
    public static final long TO_DREAMING_HOSTED_DURATION;
    public static final long TO_GLANCEABLE_HUB_DURATION;
    public static final long TO_GONE_DURATION;
    public static final long TO_OCCLUDED_DURATION;
    public static final long TO_PRIMARY_BOUNCER_DURATION;
    public final GlanceableHubTransitions glanceableHubTransitions;
    public final CoroutineScope scope;
    public final ShadeRepository shadeRepository;
    public final Flow surfaceBehindVisibility;
    public final SwipeToDismissInteractor swipeToDismissInteractor;
    public final KeyguardTransitionRepository transitionRepository;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                iArr[KeyguardState.DREAMING_LOCKSCREEN_HOSTED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[KeyguardState.GLANCEABLE_HUB.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
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
        TO_DREAMING_HOSTED_DURATION = DurationKt.toDuration(933, durationUnit);
        TO_OCCLUDED_DURATION = DurationKt.toDuration(450, durationUnit);
        TO_AOD_DURATION = DurationKt.toDuration(500, durationUnit);
        TO_PRIMARY_BOUNCER_DURATION = duration;
        TO_GONE_DURATION = DurationKt.toDuration(633, durationUnit);
        TO_GLANCEABLE_HUB_DURATION = DurationKt.toDuration(1, DurationUnit.SECONDS);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public FromLockscreenTransitionInteractor(com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository r12, com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r13, kotlinx.coroutines.CoroutineScope r14, kotlinx.coroutines.CoroutineDispatcher r15, kotlinx.coroutines.CoroutineDispatcher r16, com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r17, com.android.systemui.shade.data.repository.ShadeRepository r18, com.android.systemui.power.domain.interactor.PowerInteractor r19, com.android.systemui.keyguard.domain.interactor.GlanceableHubTransitions r20, com.android.systemui.keyguard.domain.interactor.SwipeToDismissInteractor r21, com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor r22) {
        /*
            r11 = this;
            r9 = r11
            com.android.systemui.keyguard.shared.model.KeyguardState r10 = com.android.systemui.keyguard.shared.model.KeyguardState.LOCKSCREEN
            r8 = 0
            r0 = r11
            r1 = r10
            r2 = r13
            r3 = r16
            r4 = r15
            r5 = r19
            r6 = r22
            r7 = r17
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            r0 = r12
            r9.transitionRepository = r0
            r0 = r14
            r9.scope = r0
            r0 = r18
            r9.shadeRepository = r0
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
            com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$special$$inlined$map$1 r1 = new com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$special$$inlined$map$1
            r1.<init>()
            com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$surfaceBehindVisibility$2 r0 = new com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$surfaceBehindVisibility$2
            r2 = 0
            r0.<init>(r2)
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 r2 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1
            r2.<init>(r0, r1)
            kotlinx.coroutines.flow.Flow r0 = kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(r2)
            r9.surfaceBehindVisibility = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor.<init>(com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository, com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor, kotlinx.coroutines.CoroutineScope, kotlinx.coroutines.CoroutineDispatcher, kotlinx.coroutines.CoroutineDispatcher, com.android.systemui.keyguard.domain.interactor.KeyguardInteractor, com.android.systemui.shade.data.repository.ShadeRepository, com.android.systemui.power.domain.interactor.PowerInteractor, com.android.systemui.keyguard.domain.interactor.GlanceableHubTransitions, com.android.systemui.keyguard.domain.interactor.SwipeToDismissInteractor, com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor):void");
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final ValueAnimator getDefaultAnimatorForTransitionsToState(KeyguardState keyguardState) {
        long m2542plusLRDsOJo;
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        switch (WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()]) {
            case 1:
                Duration.Companion companion = Duration.Companion;
                m2542plusLRDsOJo = Duration.m2542plusLRDsOJo(TO_DREAMING_DURATION, DurationKt.toDuration(100, DurationUnit.MILLISECONDS));
                break;
            case 2:
                m2542plusLRDsOJo = TO_OCCLUDED_DURATION;
                break;
            case 3:
                m2542plusLRDsOJo = TO_AOD_DURATION;
                break;
            case 4:
                m2542plusLRDsOJo = TO_DOZING_DURATION;
                break;
            case 5:
                m2542plusLRDsOJo = TO_DREAMING_HOSTED_DURATION;
                break;
            case 6:
                m2542plusLRDsOJo = TO_GLANCEABLE_HUB_DURATION;
                break;
            default:
                m2542plusLRDsOJo = DEFAULT_DURATION;
                break;
        }
        valueAnimator.setDuration(Duration.m2538getInWholeMillisecondsimpl(m2542plusLRDsOJo));
        return valueAnimator;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final KeyguardTransitionRepository getTransitionRepository() {
        return this.transitionRepository;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final void start() {
        Flags.keyguardWmStateRefactor();
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        FromLockscreenTransitionInteractor$listenForLockscreenToGone$$inlined$launch$default$1 fromLockscreenTransitionInteractor$listenForLockscreenToGone$$inlined$launch$default$1 = new FromLockscreenTransitionInteractor$listenForLockscreenToGone$$inlined$launch$default$1("FromLockscreenTransitionInteractor#listenForLockscreenToGone", null, this);
        CoroutineScope coroutineScope = this.scope;
        BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, fromLockscreenTransitionInteractor$listenForLockscreenToGone$$inlined$launch$default$1, 2);
        Flags.sceneContainer();
        Flags.keyguardWmStateRefactor();
        Flags.keyguardWmStateRefactor();
        BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new FromLockscreenTransitionInteractor$listenForLockscreenToOccludedOrDreaming$$inlined$launch$default$2("FromLockscreenTransitionInteractor#listenForLockscreenToOccludedOrDreaming", null, this), 2);
        BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new FromLockscreenTransitionInteractor$listenForLockscreenToAodOrDozing$$inlined$launch$default$1("FromLockscreenTransitionInteractor#listenForLockscreenToAodOrDozing", null, this), 2);
        Flags.sceneContainer();
        BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncer$$inlined$launch$default$1("FromLockscreenTransitionInteractor#listenForLockscreenToPrimaryBouncer", null, this), 2);
        Flags.keyguardWmStateRefactor();
        BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new FromLockscreenTransitionInteractor$listenForLockscreenToDreaming$$inlined$launch$default$1("FromLockscreenTransitionInteractor#listenForLockscreenToDreaming", null, this, SetsKt__SetsKt.setOf(KeyguardState.AOD, KeyguardState.DOZING)), 2);
        Flags.sceneContainer();
        BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$$inlined$launch$default$1("FromLockscreenTransitionInteractor#listenForLockscreenToPrimaryBouncerDragging", null, this, new Ref$ObjectRef()), 2);
        BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new FromLockscreenTransitionInteractor$listenForLockscreenToAlternateBouncer$$inlined$launch$default$1("FromLockscreenTransitionInteractor#listenForLockscreenToAlternateBouncer", null, this), 2);
        listenForTransitionToCamera(coroutineScope, this.keyguardInteractor);
        Flags.sceneContainer();
        Flags.communalHub();
    }
}
