package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.systemui.Flags;
import com.android.systemui.deviceentry.data.repository.DeviceEntryRepository;
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
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FromAodTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_GONE_DURATION;
    public static final long TO_LOCKSCREEN_DURATION;
    public static final long TO_OCCLUDED_DURATION;
    public static final long TO_PRIMARY_BOUNCER_DURATION;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 canDismissLockscreen;
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
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        long duration = DurationKt.toDuration(500, durationUnit);
        DEFAULT_DURATION = duration;
        TO_LOCKSCREEN_DURATION = DurationKt.toDuration(500, durationUnit);
        TO_GONE_DURATION = duration;
        TO_OCCLUDED_DURATION = duration;
        TO_PRIMARY_BOUNCER_DURATION = duration;
    }

    public FromAodTransitionInteractor(KeyguardTransitionRepository keyguardTransitionRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, KeyguardInteractor keyguardInteractor, PowerInteractor powerInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor, DeviceEntryRepository deviceEntryRepository) {
        super(KeyguardState.AOD, keyguardTransitionInteractor, coroutineDispatcher2, coroutineDispatcher, powerInteractor, keyguardOcclusionInteractor, keyguardInteractor, null);
        this.transitionRepository = keyguardTransitionRepository;
        this.scope = coroutineScope;
        this.canDismissLockscreen = FlowKt.combine(keyguardInteractor.isKeyguardShowing, keyguardInteractor.isKeyguardDismissible, keyguardInteractor.biometricUnlockState, new FromAodTransitionInteractor$canDismissLockscreen$1(null));
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final ValueAnimator getDefaultAnimatorForTransitionsToState(KeyguardState keyguardState) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        valueAnimator.setDuration(Duration.m2538getInWholeMillisecondsimpl(WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()] == 1 ? TO_LOCKSCREEN_DURATION : DEFAULT_DURATION));
        return valueAnimator;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final KeyguardTransitionRepository getTransitionRepository() {
        return this.transitionRepository;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final void start() {
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        FromAodTransitionInteractor$listenForAodToAwake$$inlined$launch$default$1 fromAodTransitionInteractor$listenForAodToAwake$$inlined$launch$default$1 = new FromAodTransitionInteractor$listenForAodToAwake$$inlined$launch$default$1("FromAodTransitionInteractor#listenForAodToAwake", null, this);
        CoroutineScope coroutineScope = this.scope;
        BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, fromAodTransitionInteractor$listenForAodToAwake$$inlined$launch$default$1, 2);
        Flags.keyguardWmStateRefactor();
        BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new FromAodTransitionInteractor$listenForAodToOccluded$$inlined$launch$default$1("FromAodTransitionInteractor#listenForAodToOccluded", null, this), 2);
        Flags.sceneContainer();
        BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new FromAodTransitionInteractor$listenForAodToPrimaryBouncer$$inlined$launch$default$1("FromAodTransitionInteractor#listenForAodToPrimaryBouncer", null, this), 2);
        listenForTransitionToCamera(coroutineScope, this.keyguardInteractor);
    }
}
