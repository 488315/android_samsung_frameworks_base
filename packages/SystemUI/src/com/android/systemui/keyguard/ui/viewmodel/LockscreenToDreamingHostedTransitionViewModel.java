package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$2$1$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LockscreenToDreamingHostedTransitionViewModel {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 shortcutsAlpha;

    public LockscreenToDreamingHostedTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromLockscreenTransitionInteractor.Companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m1961setupVtjQ1oo = keyguardTransitionAnimationFlow.m1961setupVtjQ1oo(FromLockscreenTransitionInteractor.TO_DREAMING_HOSTED_DURATION, KeyguardTransitionInteractor$2$1$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.LOCKSCREEN, KeyguardState.DREAMING_LOCKSCREEN_HOSTED));
        Duration.Companion companion = Duration.Companion;
        this.shortcutsAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(m1961setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, DurationUnit.MILLISECONDS), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingHostedTransitionViewModel$shortcutsAlpha$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(1 - ((Number) obj).floatValue());
            }
        }, 0L, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingHostedTransitionViewModel$shortcutsAlpha$2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(1.0f);
            }
        }, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingHostedTransitionViewModel$shortcutsAlpha$3
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(0.0f);
            }
        }, null, null, 204);
    }
}
