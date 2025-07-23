package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.deviceentry.shared.model.HelpFaceAuthenticationStatus;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class BiometricMessageInteractor$faceHelpMessage$4 extends AdaptedFunctionReference implements Function3 {
    public static final BiometricMessageInteractor$faceHelpMessage$4 INSTANCE = new BiometricMessageInteractor$faceHelpMessage$4();

    public BiometricMessageInteractor$faceHelpMessage$4() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new Pair((HelpFaceAuthenticationStatus) obj, (Function1) obj2);
    }
}
