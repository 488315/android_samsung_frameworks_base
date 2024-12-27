package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.biometrics.domain.model.SideFpsSensorLocation;
import com.android.systemui.biometrics.shared.model.DisplayRotation;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class SideFpsProgressBarViewModel$rotation$2 extends AdaptedFunctionReference implements Function3 {
    public static final SideFpsProgressBarViewModel$rotation$2 INSTANCE = new SideFpsProgressBarViewModel$rotation$2();

    public SideFpsProgressBarViewModel$rotation$2() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new Pair((DisplayRotation) obj, (SideFpsSensorLocation) obj2);
    }
}
