package com.android.systemui.keyguard.domain.interactor;

import kotlin.Triple;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class KeyguardDismissInteractor$onTrustGrantedRequestDismissKeyguard$2 extends AdaptedFunctionReference implements Function4 {
    public static final KeyguardDismissInteractor$onTrustGrantedRequestDismissKeyguard$2 INSTANCE = new KeyguardDismissInteractor$onTrustGrantedRequestDismissKeyguard$2();

    public KeyguardDismissInteractor$onTrustGrantedRequestDismissKeyguard$2() {
        super(4, Triple.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        Boolean bool2 = (Boolean) obj2;
        bool2.booleanValue();
        Boolean bool3 = (Boolean) obj3;
        bool3.booleanValue();
        return new Triple(bool, bool2, bool3);
    }
}
