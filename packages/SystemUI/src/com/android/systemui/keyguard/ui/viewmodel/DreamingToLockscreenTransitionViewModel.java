package com.android.systemui.keyguard.ui.viewmodel;

import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$2$1$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DreamingToLockscreenTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryBackgroundViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 dreamOverlayAlpha;
    public final FromDreamingTransitionInteractor fromDreamingTransitionInteractor;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 shortcutsAlpha;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;

    public DreamingToLockscreenTransitionViewModel(FromDreamingTransitionInteractor fromDreamingTransitionInteractor, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        this.fromDreamingTransitionInteractor = fromDreamingTransitionInteractor;
        FromDreamingTransitionInteractor.Companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m1961setupVtjQ1oo = keyguardTransitionAnimationFlow.m1961setupVtjQ1oo(FromDreamingTransitionInteractor.TO_LOCKSCREEN_DURATION, KeyguardTransitionInteractor$2$1$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.DREAMING, KeyguardState.LOCKSCREEN));
        this.transitionAnimation = m1961setupVtjQ1oo;
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        this.dreamOverlayAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(m1961setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel$dreamOverlayAlpha$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(1.0f - ((Number) obj).floatValue());
            }
        }, 0L, null, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList);
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m1962sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(m1961setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel$lockscreenAlpha$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(((Number) obj).floatValue());
            }
        }, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_removeWidget, durationUnit), null, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_removeDexShortcut);
        this.lockscreenAlpha = m1962sharedFlow74qcysc$default;
        this.shortcutsAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(m1961setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel$shortcutsAlpha$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(((Number) obj).floatValue());
            }
        }, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_removeWidget, durationUnit), null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel$shortcutsAlpha$2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(0.0f);
            }
        }, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_addWidget);
        this.deviceEntryBackgroundViewAlpha = m1961setupVtjQ1oo.immediatelyTransitionTo(1.0f);
        this.deviceEntryParentViewAlpha = m1962sharedFlow74qcysc$default;
    }

    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 dreamOverlayTranslationY(final int i) {
        FromDreamingTransitionInteractor.Companion.getClass();
        return KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(this.transitionAnimation, FromDreamingTransitionInteractor.TO_LOCKSCREEN_DURATION, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel$dreamOverlayTranslationY$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(((Number) obj).floatValue() * i);
            }
        }, 0L, null, null, null, Interpolators.EMPHASIZED, null, 188);
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }

    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenTranslationY(final int i) {
        FromDreamingTransitionInteractor.Companion.getClass();
        long j = FromDreamingTransitionInteractor.TO_LOCKSCREEN_DURATION;
        Interpolator interpolator = Interpolators.EMPHASIZED;
        Function1 function1 = new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel$lockscreenTranslationY$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf((((Number) obj).floatValue() * i) + (-r1));
            }
        };
        DreamingToLockscreenTransitionViewModel$lockscreenTranslationY$2 dreamingToLockscreenTransitionViewModel$lockscreenTranslationY$2 = new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel$lockscreenTranslationY$2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(0.0f);
            }
        };
        DreamingToLockscreenTransitionViewModel$lockscreenTranslationY$3 dreamingToLockscreenTransitionViewModel$lockscreenTranslationY$3 = new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel$lockscreenTranslationY$3
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(0.0f);
            }
        };
        Intrinsics.checkNotNull(interpolator);
        return KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(this.transitionAnimation, j, function1, 0L, null, dreamingToLockscreenTransitionViewModel$lockscreenTranslationY$2, dreamingToLockscreenTransitionViewModel$lockscreenTranslationY$3, interpolator, null, 140);
    }
}
