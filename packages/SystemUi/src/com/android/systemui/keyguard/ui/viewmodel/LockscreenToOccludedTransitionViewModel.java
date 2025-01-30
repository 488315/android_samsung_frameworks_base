package com.android.systemui.keyguard.ui.viewmodel;

import com.android.app.animation.Interpolators;
import com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LockscreenToOccludedTransitionViewModel {
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 lockscreenAlpha;
    public final KeyguardTransitionAnimationFlow transitionAnimation;

    public LockscreenToOccludedTransitionViewModel(KeyguardTransitionInteractor keyguardTransitionInteractor) {
        FromLockscreenTransitionInteractor.Companion.getClass();
        KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow = new KeyguardTransitionAnimationFlow(FromLockscreenTransitionInteractor.TO_OCCLUDED_DURATION, keyguardTransitionInteractor.lockscreenToOccludedTransition, null);
        this.transitionAnimation = keyguardTransitionAnimationFlow;
        Duration.Companion companion = Duration.Companion;
        this.lockscreenAlpha = KeyguardTransitionAnimationFlow.m1579createFlow53AowQI$default(keyguardTransitionAnimationFlow, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, DurationUnit.MILLISECONDS), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToOccludedTransitionViewModel$lockscreenAlpha$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(1.0f - ((Number) obj).floatValue());
            }
        }, 0L, null, null, null, null, 124);
    }

    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 lockscreenTranslationY(final int i) {
        KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow = this.transitionAnimation;
        FromLockscreenTransitionInteractor.Companion.getClass();
        return KeyguardTransitionAnimationFlow.m1579createFlow53AowQI$default(keyguardTransitionAnimationFlow, FromLockscreenTransitionInteractor.TO_OCCLUDED_DURATION, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToOccludedTransitionViewModel$lockscreenTranslationY$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(((Number) obj).floatValue() * i);
            }
        }, 0L, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToOccludedTransitionViewModel$lockscreenTranslationY$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Float.valueOf(0.0f);
            }
        }, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToOccludedTransitionViewModel$lockscreenTranslationY$3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Float.valueOf(0.0f);
            }
        }, Interpolators.EMPHASIZED_ACCELERATE, 12);
    }
}
