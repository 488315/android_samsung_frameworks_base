package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.deviceentry.shared.model.HelpFaceAuthenticationStatus;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class BiometricMessageInteractor$faceHelpMessage$3 extends AdaptedFunctionReference implements Function3 {
    public static final BiometricMessageInteractor$faceHelpMessage$3 INSTANCE = new BiometricMessageInteractor$faceHelpMessage$3();

    public BiometricMessageInteractor$faceHelpMessage$3() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new Pair((HelpFaceAuthenticationStatus) obj, (Function1) obj2);
    }
}
