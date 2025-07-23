package com.android.systemui.biometrics.domain.interactor;

import java.util.Map;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class SideFpsSensorInteractor$sensorLocationForCurrentDisplay$3 extends AdaptedFunctionReference implements Function3 {
    public static final SideFpsSensorInteractor$sensorLocationForCurrentDisplay$3 INSTANCE = new SideFpsSensorInteractor$sensorLocationForCurrentDisplay$3();

    public SideFpsSensorInteractor$sensorLocationForCurrentDisplay$3() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new Pair(new Integer(((Number) obj).intValue()), (Map) obj2);
    }
}
