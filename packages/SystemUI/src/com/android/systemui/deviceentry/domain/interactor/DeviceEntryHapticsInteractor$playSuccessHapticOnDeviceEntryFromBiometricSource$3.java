package com.android.systemui.deviceentry.domain.interactor;

import kotlin.Triple;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class DeviceEntryHapticsInteractor$playSuccessHapticOnDeviceEntryFromBiometricSource$3 extends AdaptedFunctionReference implements Function4 {
    public static final DeviceEntryHapticsInteractor$playSuccessHapticOnDeviceEntryFromBiometricSource$3 INSTANCE = new DeviceEntryHapticsInteractor$playSuccessHapticOnDeviceEntryFromBiometricSource$3();

    public DeviceEntryHapticsInteractor$playSuccessHapticOnDeviceEntryFromBiometricSource$3() {
        super(4, Triple.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        Boolean bool2 = (Boolean) obj2;
        bool2.booleanValue();
        return new Triple(bool, bool2, new Long(((Number) obj3).longValue()));
    }
}
