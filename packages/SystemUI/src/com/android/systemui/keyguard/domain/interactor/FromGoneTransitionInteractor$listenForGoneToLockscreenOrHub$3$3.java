package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class FromGoneTransitionInteractor$listenForGoneToLockscreenOrHub$3$3 extends AdaptedFunctionReference implements Function3 {
    public static final FromGoneTransitionInteractor$listenForGoneToLockscreenOrHub$3$3 INSTANCE = new FromGoneTransitionInteractor$listenForGoneToLockscreenOrHub$3$3();

    public FromGoneTransitionInteractor$listenForGoneToLockscreenOrHub$3$3() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        Boolean bool2 = (Boolean) obj2;
        bool2.booleanValue();
        FromGoneTransitionInteractor.Companion companion = FromGoneTransitionInteractor.Companion;
        return new Pair(bool, bool2);
    }
}
