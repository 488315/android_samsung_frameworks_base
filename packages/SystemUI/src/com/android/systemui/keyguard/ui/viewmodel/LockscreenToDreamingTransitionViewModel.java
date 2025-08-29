package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* loaded from: classes2.dex */
public final class LockscreenToDreamingTransitionViewModel implements DeviceEntryIconTransition {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long DREAMING_ANIMATION_DURATION_MS;
    public final ChannelLimitedFlowMerge deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 shortcutsAlpha;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        FromLockscreenTransitionInteractor.Companion.getClass();
        DREAMING_ANIMATION_DURATION_MS = Duration.m3456getInWholeMillisecondsimpl(FromLockscreenTransitionInteractor.TO_DREAMING_DURATION);
    }

    public LockscreenToDreamingTransitionViewModel(ShadeDependentFlows shadeDependentFlows, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        final int i = 0;
        final int i2 = 1;
        FromLockscreenTransitionInteractor.Companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilderM2613setupVtjQ1oo = keyguardTransitionAnimationFlow.m2613setupVtjQ1oo(FromLockscreenTransitionInteractor.TO_DREAMING_DURATION, KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.LOCKSCREEN, KeyguardState.DREAMING));
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        this.lockscreenAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilderM2613setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Float f = (Float) obj;
                switch (i) {
                    case 0:
                        float fFloatValue = f.floatValue();
                        int i3 = LockscreenToDreamingTransitionViewModel.$r8$clinit;
                        return Float.valueOf(1.0f - fFloatValue);
                    case 1:
                        float fFloatValue2 = f.floatValue();
                        int i4 = LockscreenToDreamingTransitionViewModel.$r8$clinit;
                        return Float.valueOf(1 - fFloatValue2);
                    default:
                        float fFloatValue3 = f.floatValue();
                        int i5 = LockscreenToDreamingTransitionViewModel.$r8$clinit;
                        return Float.valueOf(1.0f - fFloatValue3);
                }
            }
        }, 0L, null, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList);
        this.shortcutsAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilderM2613setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Float f = (Float) obj;
                switch (i2) {
                    case 0:
                        float fFloatValue = f.floatValue();
                        int i3 = LockscreenToDreamingTransitionViewModel.$r8$clinit;
                        return Float.valueOf(1.0f - fFloatValue);
                    case 1:
                        float fFloatValue2 = f.floatValue();
                        int i4 = LockscreenToDreamingTransitionViewModel.$r8$clinit;
                        return Float.valueOf(1 - fFloatValue2);
                    default:
                        float fFloatValue3 = f.floatValue();
                        int i5 = LockscreenToDreamingTransitionViewModel.$r8$clinit;
                        return Float.valueOf(1.0f - fFloatValue3);
                }
            }
        }, 0L, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        int i3 = LockscreenToDreamingTransitionViewModel.$r8$clinit;
                        return Float.valueOf(1.0f);
                    default:
                        int i4 = LockscreenToDreamingTransitionViewModel.$r8$clinit;
                        return Float.valueOf(0.0f);
                }
            }
        }, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i3 = LockscreenToDreamingTransitionViewModel.$r8$clinit;
                        return Float.valueOf(1.0f);
                    default:
                        int i4 = LockscreenToDreamingTransitionViewModel.$r8$clinit;
                        return Float.valueOf(0.0f);
                }
            }
        }, null, null, 204);
        final int i3 = 2;
        this.deviceEntryParentViewAlpha = shadeDependentFlows.transitionFlow(flowBuilderM2613setupVtjQ1oo.immediatelyTransitionTo(0.0f), KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilderM2613setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Float f = (Float) obj;
                switch (i3) {
                    case 0:
                        float fFloatValue = f.floatValue();
                        int i32 = LockscreenToDreamingTransitionViewModel.$r8$clinit;
                        return Float.valueOf(1.0f - fFloatValue);
                    case 1:
                        float fFloatValue2 = f.floatValue();
                        int i4 = LockscreenToDreamingTransitionViewModel.$r8$clinit;
                        return Float.valueOf(1 - fFloatValue2);
                    default:
                        float fFloatValue3 = f.floatValue();
                        int i5 = LockscreenToDreamingTransitionViewModel.$r8$clinit;
                        return Float.valueOf(1.0f - fFloatValue3);
                }
            }
        }, 0L, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i32 = LockscreenToDreamingTransitionViewModel.$r8$clinit;
                        return Float.valueOf(1.0f);
                    default:
                        int i4 = LockscreenToDreamingTransitionViewModel.$r8$clinit;
                        return Float.valueOf(0.0f);
                }
            }
        }, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getAppsButtonState));
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
