package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class FromGoneTransitionInteractor$listenForGoneToDreaming$1$2 extends AdaptedFunctionReference implements Function3 {
    public static final FromGoneTransitionInteractor$listenForGoneToDreaming$1$2 INSTANCE = new FromGoneTransitionInteractor$listenForGoneToDreaming$1$2();

    public FromGoneTransitionInteractor$listenForGoneToDreaming$1$2() {
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
