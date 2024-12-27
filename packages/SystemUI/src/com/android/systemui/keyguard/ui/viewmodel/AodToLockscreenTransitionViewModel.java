package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$2$1$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;

public final class AodToLockscreenTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryBackgroundViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public boolean isShadeExpanded;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 notificationAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 shortcutsAlpha;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;

    public AodToLockscreenTransitionViewModel(final ShadeInteractor shadeInteractor, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromAodTransitionInteractor.Companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m1961setupVtjQ1oo = keyguardTransitionAnimationFlow.m1961setupVtjQ1oo(FromAodTransitionInteractor.TO_LOCKSCREEN_DURATION, KeyguardTransitionInteractor$2$1$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.AOD, KeyguardState.LOCKSCREEN));
        this.transitionAnimation = m1961setupVtjQ1oo;
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(m1961setupVtjQ1oo, DurationKt.toDuration(500, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToLockscreenTransitionViewModel$notificationAlpha$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                float floatValue = ((Number) obj).floatValue();
                if (AodToLockscreenTransitionViewModel.this.isShadeExpanded) {
                    floatValue = 1.0f;
                }
                return Float.valueOf(floatValue);
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToLockscreenTransitionViewModel$notificationAlpha$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AodToLockscreenTransitionViewModel.this.isShadeExpanded = ((Number) ((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.getShadeExpansion().getValue()).floatValue() > 0.0f || ((Number) ((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.getQsExpansion().getValue()).floatValue() > 0.0f;
                return Unit.INSTANCE;
            }
        }, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode);
        this.shortcutsAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(m1961setupVtjQ1oo, DurationKt.toDuration(167, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToLockscreenTransitionViewModel$shortcutsAlpha$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(((Number) obj).floatValue());
            }
        }, DurationKt.toDuration(67, durationUnit), null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToLockscreenTransitionViewModel$shortcutsAlpha$2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(0.0f);
            }
        }, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_addWidget);
        this.deviceEntryBackgroundViewAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(m1961setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToLockscreenTransitionViewModel$deviceEntryBackgroundViewAlpha$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(((Number) obj).floatValue());
            }
        }, 0L, null, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToLockscreenTransitionViewModel$deviceEntryBackgroundViewAlpha$2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(1.0f);
            }
        }, null, null, 220);
        this.deviceEntryParentViewAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(m1961setupVtjQ1oo, DurationKt.toDuration(500, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToLockscreenTransitionViewModel$deviceEntryParentViewAlpha$1
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                ((Number) obj).floatValue();
                return Float.valueOf(1.0f);
            }
        }, 0L, null, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList);
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
