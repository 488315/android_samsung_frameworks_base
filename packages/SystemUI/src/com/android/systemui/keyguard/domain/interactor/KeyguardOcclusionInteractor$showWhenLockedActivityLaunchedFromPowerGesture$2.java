package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.power.shared.model.WakefulnessModel;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class KeyguardOcclusionInteractor$showWhenLockedActivityLaunchedFromPowerGesture$2 extends AdaptedFunctionReference implements Function3 {
    public static final KeyguardOcclusionInteractor$showWhenLockedActivityLaunchedFromPowerGesture$2 INSTANCE = new KeyguardOcclusionInteractor$showWhenLockedActivityLaunchedFromPowerGesture$2();

    public KeyguardOcclusionInteractor$showWhenLockedActivityLaunchedFromPowerGesture$2() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new Pair((WakefulnessModel) obj, (KeyguardState) obj2);
    }
}
