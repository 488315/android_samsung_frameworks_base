package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.keyguard.shared.model.ErrorFingerprintAuthenticationStatus;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class BiometricMessageInteractor$fingerprintErrorMessage$3 extends AdaptedFunctionReference implements Function3 {
    public static final BiometricMessageInteractor$fingerprintErrorMessage$3 INSTANCE = new BiometricMessageInteractor$fingerprintErrorMessage$3();

    public BiometricMessageInteractor$fingerprintErrorMessage$3() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Boolean bool = (Boolean) obj2;
        bool.booleanValue();
        return new Pair((ErrorFingerprintAuthenticationStatus) obj, bool);
    }
}
