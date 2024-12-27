package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.systemui.Flags;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FromDreamingTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_AOD_DURATION;
    public static final long TO_GLANCEABLE_HUB_DURATION;
    public static final long TO_GONE_DURATION;
    public static final long TO_LOCKSCREEN_DURATION;
    public final GlanceableHubTransitions glanceableHubTransitions;
    public final CoroutineScope scope;
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
    }

    public FromDreamingTransitionInteractor(KeyguardTransitionRepository keyguardTransitionRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, KeyguardInteractor keyguardInteractor, GlanceableHubTransitions glanceableHubTransitions, PowerInteractor powerInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor) {
        super(KeyguardState.DREAMING, keyguardTransitionInteractor, coroutineDispatcher2, coroutineDispatcher, powerInteractor, keyguardOcclusionInteractor, keyguardInteractor, null);
        this.transitionRepository = keyguardTransitionRepository;
        this.scope = coroutineScope;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final ValueAnimator getDefaultAnimatorForTransitionsToState(KeyguardState keyguardState) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        int i = WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()];
        valueAnimator.setDuration(Duration.m2538getInWholeMillisecondsimpl(i != 1 ? i != 2 ? DEFAULT_DURATION : TO_GLANCEABLE_HUB_DURATION : TO_LOCKSCREEN_DURATION));
        return valueAnimator;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final KeyguardTransitionRepository getTransitionRepository() {
        return this.transitionRepository;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final void start() {
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$$inlined$launch$default$1 fromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$$inlined$launch$default$1 = new FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$$inlined$launch$default$1("FromDreamingTransitionInteractor#listenForDreamingToAlternateBouncer", null, this);
        CoroutineScope coroutineScope = this.scope;
        BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, fromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$$inlined$launch$default$1, 2);
        Flags.keyguardWmStateRefactor();
        BuildersKt.launch$default(coroutineScope, null, null, new FromDreamingTransitionInteractor$listenForDreamingToOccluded$2(this, null), 3);
        Flags.sceneContainer();
        BuildersKt.launch$default(coroutineScope, null, null, new FromDreamingTransitionInteractor$listenForDreamingToGoneWhenDismissable$1(this, null), 3);
        Flags.sceneContainer();
        BuildersKt.launch$default(coroutineScope, null, null, new FromDreamingTransitionInteractor$listenForDreamingToGoneFromBiometricUnlock$1(this, null), 3);
        Flags.keyguardWmStateRefactor();
        BuildersKt.launch$default(coroutineScope, null, null, new FromDreamingTransitionInteractor$listenForDreamingToAodOrDozing$1(this, null), 3);
        listenForTransitionToCamera(coroutineScope, this.keyguardInteractor);
        Flags.communalHub();
        BuildersKt.launch$default(coroutineScope, null, null, new FromDreamingTransitionInteractor$listenForDreamingToPrimaryBouncer$1(this, null), 3);
    }

    public final void startToLockscreenTransition() {
        BuildersKt.launch$default(this.scope, null, null, new FromDreamingTransitionInteractor$startToLockscreenTransition$1(this, null), 3);
    }
}
