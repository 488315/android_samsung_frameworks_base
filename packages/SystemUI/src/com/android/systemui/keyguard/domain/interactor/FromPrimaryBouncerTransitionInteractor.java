package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Flags;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.wm.shell.animation.Interpolators;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class FromPrimaryBouncerTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_AOD_DURATION;
    public static final long TO_DOZING_DURATION;
    public static final long TO_GONE_DURATION;
    public static final long TO_GONE_SHORT_DURATION;
    public static final float TO_GONE_SURFACE_BEHIND_VISIBLE_THRESHOLD;
    public static final long TO_LOCKSCREEN_DURATION;
    public final CommunalInteractor communalInteractor;
    public final KeyguardSecurityModel keyguardSecurityModel;
    public final CoroutineScope scope;
    public final SelectedUserInteractor selectedUserInteractor;
    public final Flow surfaceBehindVisibility;
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
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        long duration = DurationKt.toDuration(300, durationUnit);
        DEFAULT_DURATION = duration;
        TO_AOD_DURATION = duration;
        TO_DOZING_DURATION = duration;
        TO_GONE_DURATION = DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit);
        TO_GONE_SHORT_DURATION = DurationKt.toDuration(200, durationUnit);
        TO_LOCKSCREEN_DURATION = DurationKt.toDuration(450, durationUnit);
        TO_GONE_SURFACE_BEHIND_VISIBLE_THRESHOLD = 0.5f;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public FromPrimaryBouncerTransitionInteractor(com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository r12, com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r13, kotlinx.coroutines.CoroutineScope r14, kotlinx.coroutines.CoroutineDispatcher r15, kotlinx.coroutines.CoroutineDispatcher r16, com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r17, com.android.systemui.communal.domain.interactor.CommunalInteractor r18, com.android.keyguard.KeyguardSecurityModel r19, com.android.systemui.user.domain.interactor.SelectedUserInteractor r20, com.android.systemui.power.domain.interactor.PowerInteractor r21, com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor r22) {
        /*
            r11 = this;
            r9 = r11
            com.android.systemui.keyguard.shared.model.KeyguardState r10 = com.android.systemui.keyguard.shared.model.KeyguardState.PRIMARY_BOUNCER
            r8 = 0
            r0 = r11
            r1 = r10
            r2 = r13
            r3 = r16
            r4 = r15
            r5 = r21
            r6 = r22
            r7 = r17
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            r0 = r12
            r9.transitionRepository = r0
            r0 = r14
            r9.scope = r0
            r0 = r18
            r9.communalInteractor = r0
            r0 = r19
            r9.keyguardSecurityModel = r0
            r0 = r20
            r9.selectedUserInteractor = r0
            com.android.systemui.Flags.sceneContainer()
            com.android.systemui.keyguard.shared.model.Edge$Companion r0 = com.android.systemui.keyguard.shared.model.Edge.Companion
            r0.getClass()
            com.android.systemui.keyguard.shared.model.KeyguardState r0 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
            com.android.systemui.keyguard.shared.model.Edge$StateToState r1 = new com.android.systemui.keyguard.shared.model.Edge$StateToState
            r1.<init>(r10, r0)
            r0 = r13
            kotlinx.coroutines.flow.Flow r0 = r13.transition(r1)
            com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$special$$inlined$map$1 r1 = new com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$special$$inlined$map$1
            r1.<init>()
            com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$surfaceBehindVisibility$2 r0 = new com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$surfaceBehindVisibility$2
            r2 = 0
            r0.<init>(r2)
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 r2 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1
            r2.<init>(r0, r1)
            kotlinx.coroutines.flow.Flow r0 = kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(r2)
            r9.surfaceBehindVisibility = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor.<init>(com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository, com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor, kotlinx.coroutines.CoroutineScope, kotlinx.coroutines.CoroutineDispatcher, kotlinx.coroutines.CoroutineDispatcher, com.android.systemui.keyguard.domain.interactor.KeyguardInteractor, com.android.systemui.communal.domain.interactor.CommunalInteractor, com.android.keyguard.KeyguardSecurityModel, com.android.systemui.user.domain.interactor.SelectedUserInteractor, com.android.systemui.power.domain.interactor.PowerInteractor, com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor):void");
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final ValueAnimator getDefaultAnimatorForTransitionsToState(KeyguardState keyguardState) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        int i = WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()];
        valueAnimator.setDuration(Duration.m2538getInWholeMillisecondsimpl(i != 1 ? i != 2 ? i != 3 ? i != 4 ? DEFAULT_DURATION : TO_LOCKSCREEN_DURATION : TO_GONE_DURATION : TO_DOZING_DURATION : TO_AOD_DURATION));
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
        FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1 fromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1 = new FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1(this, null);
        CoroutineScope coroutineScope = this.scope;
        BuildersKt.launch$default(coroutineScope, null, null, fromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1, 3);
        Flags.sceneContainer();
        BuildersKt.launch$default(coroutineScope, null, null, new FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToAsleep$1(this, null), 3);
        Flags.sceneContainer();
        Flags.keyguardWmStateRefactor();
        BuildersKt.launch$default(coroutineScope, null, null, new FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToLockscreenHubOrOccluded$2(this, null), 3);
        Flags.sceneContainer();
        BuildersKt.launch$default(coroutineScope, null, null, new FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToDreamingLockscreenHosted$1(this, null), 3);
        listenForTransitionToCamera(coroutineScope, this.keyguardInteractor);
    }
}
