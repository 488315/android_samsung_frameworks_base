package com.android.systemui.deviceentry.domain.interactor;

import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* loaded from: classes2.dex */
final /* synthetic */ class BiometricMessageInteractor$filterConditionForFaceHelpMessages$3 extends AdaptedFunctionReference implements Function3 {
    public static final BiometricMessageInteractor$filterConditionForFaceHelpMessages$3 INSTANCE = new BiometricMessageInteractor$filterConditionForFaceHelpMessages$3();

    public BiometricMessageInteractor$filterConditionForFaceHelpMessages$3() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        Boolean bool2 = (Boolean) obj2;
        bool2.booleanValue();
        return new Pair(bool, bool2);
    }
}
