package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class KeyguardSurfaceBehindInteractor$isNotificationLaunchAnimationRunningOnKeyguard$2 extends AdaptedFunctionReference implements Function3 {
    public static final KeyguardSurfaceBehindInteractor$isNotificationLaunchAnimationRunningOnKeyguard$2 INSTANCE = new KeyguardSurfaceBehindInteractor$isNotificationLaunchAnimationRunningOnKeyguard$2();

    public KeyguardSurfaceBehindInteractor$isNotificationLaunchAnimationRunningOnKeyguard$2() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return new Pair(bool, (KeyguardState) obj2);
    }
}
