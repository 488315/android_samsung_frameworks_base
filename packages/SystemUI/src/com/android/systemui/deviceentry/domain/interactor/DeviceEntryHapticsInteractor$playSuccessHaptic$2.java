package com.android.systemui.deviceentry.domain.interactor;

import kotlin.Triple;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class DeviceEntryHapticsInteractor$playSuccessHaptic$2 extends AdaptedFunctionReference implements Function4 {
    public static final DeviceEntryHapticsInteractor$playSuccessHaptic$2 INSTANCE = new DeviceEntryHapticsInteractor$playSuccessHaptic$2();

    public DeviceEntryHapticsInteractor$playSuccessHaptic$2() {
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
