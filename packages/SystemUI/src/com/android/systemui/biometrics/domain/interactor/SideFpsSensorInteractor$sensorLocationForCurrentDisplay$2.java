package com.android.systemui.biometrics.domain.interactor;

import java.util.Map;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class SideFpsSensorInteractor$sensorLocationForCurrentDisplay$2 extends AdaptedFunctionReference implements Function3 {
    public static final SideFpsSensorInteractor$sensorLocationForCurrentDisplay$2 INSTANCE = new SideFpsSensorInteractor$sensorLocationForCurrentDisplay$2();

    public SideFpsSensorInteractor$sensorLocationForCurrentDisplay$2() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new Pair(new Integer(((Number) obj).intValue()), (Map) obj2);
    }
}
