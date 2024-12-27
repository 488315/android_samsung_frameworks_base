package com.android.systemui.keyguard.ui.viewmodel;

import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$2$1$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

public final class LockscreenToDreamingTransitionViewModel implements DeviceEntryIconTransition {
    public static final long DREAMING_ANIMATION_DURATION_MS;
    public final ChannelLimitedFlowMerge deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 shortcutsAlpha;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        FromLockscreenTransitionInteractor.Companion.getClass();
        DREAMING_ANIMATION_DURATION_MS = Duration.m2538getInWholeMillisecondsimpl(FromLockscreenTransitionInteractor.TO_DREAMING_DURATION);
    }

    public LockscreenToDreamingTransitionViewModel(ShadeDependentFlows shadeDependentFlows, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromLockscreenTransitionInteractor.Companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m1961setupVtjQ1oo = keyguardTransitionAnimationFlow.m1961setupVtjQ1oo(FromLockscreenTransitionInteractor.TO_DREAMING_DURATION, KeyguardTransitionInteractor$2$1$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.LOCKSCREEN, KeyguardState.DREAMING));
        this.transitionAnimation = m1961setupVtjQ1oo;
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m1962sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(m1961setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel$lockscreenAlpha$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(1.0f - ((Number) obj).floatValue());
            }
        }, 0L, null, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList);
        this.lockscreenAlpha = m1962sharedFlow74qcysc$default;
        this.shortcutsAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(m1961setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel$shortcutsAlpha$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(1 - ((Number) obj).floatValue());
            }
        }, 0L, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel$shortcutsAlpha$2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(1.0f);
            }
        }, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel$shortcutsAlpha$3
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(0.0f);
            }
        }, null, null, 204);
        this.deviceEntryParentViewAlpha = shadeDependentFlows.transitionFlow(m1961setupVtjQ1oo.immediatelyTransitionTo(0.0f), m1962sharedFlow74qcysc$default);
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }

    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenTranslationY(final int i) {
        Duration.Companion companion = Duration.Companion;
        long duration = DurationKt.toDuration(500, DurationUnit.MILLISECONDS);
        Interpolator interpolator = Interpolators.EMPHASIZED_ACCELERATE;
        Function1 function1 = new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel$lockscreenTranslationY$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(((Number) obj).floatValue() * i);
            }
        };
        LockscreenToDreamingTransitionViewModel$lockscreenTranslationY$2 lockscreenToDreamingTransitionViewModel$lockscreenTranslationY$2 = new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel$lockscreenTranslationY$2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(0.0f);
            }
        };
        LockscreenToDreamingTransitionViewModel$lockscreenTranslationY$3 lockscreenToDreamingTransitionViewModel$lockscreenTranslationY$3 = new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel$lockscreenTranslationY$3
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(0.0f);
            }
        };
        Intrinsics.checkNotNull(interpolator);
        return KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(this.transitionAnimation, duration, function1, 0L, null, lockscreenToDreamingTransitionViewModel$lockscreenTranslationY$2, lockscreenToDreamingTransitionViewModel$lockscreenTranslationY$3, interpolator, null, 140);
    }
}
