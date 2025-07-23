package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.modifiers.AnimatedBackgroundKt$$ExternalSyntheticLambda0;
import com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DreamingToLockscreenTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryBackgroundViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 dreamOverlayAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 shortcutsAlpha;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;

    public DreamingToLockscreenTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromDreamingTransitionInteractor.Companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m2598setupVtjQ1oo = keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(FromDreamingTransitionInteractor.TO_LOCKSCREEN_DURATION, KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.DREAMING, KeyguardState.LOCKSCREEN));
        this.transitionAnimation = m2598setupVtjQ1oo;
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        this.dreamOverlayAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m2598setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new DreamingToLockscreenTransitionViewModel$$ExternalSyntheticLambda1(), 0L, null, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList);
        this.lockscreenAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m2598setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda6(), DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_removeWidget, durationUnit), null, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_removeDexShortcut);
        this.shortcutsAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m2598setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda6(), DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_removeWidget, durationUnit), null, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), null, null, null, IKnoxCustomManager.Stub.TRANSACTION_addWidget);
        this.deviceEntryBackgroundViewAlpha = m2598setupVtjQ1oo.immediatelyTransitionTo(1.0f);
        this.deviceEntryParentViewAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m2598setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda6(), DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_removeWidget, durationUnit), null, new AnimatedBackgroundKt$$ExternalSyntheticLambda0(), null, null, null, IKnoxCustomManager.Stub.TRANSACTION_addWidget);
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
