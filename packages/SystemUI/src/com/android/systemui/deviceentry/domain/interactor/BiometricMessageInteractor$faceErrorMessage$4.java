package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.deviceentry.shared.model.ErrorFaceAuthenticationStatus;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class BiometricMessageInteractor$faceErrorMessage$4 extends AdaptedFunctionReference implements Function3 {
    public static final BiometricMessageInteractor$faceErrorMessage$4 INSTANCE = new BiometricMessageInteractor$faceErrorMessage$4();

    public BiometricMessageInteractor$faceErrorMessage$4() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Boolean bool = (Boolean) obj2;
        bool.booleanValue();
        return new Pair((ErrorFaceAuthenticationStatus) obj, bool);
    }
}
