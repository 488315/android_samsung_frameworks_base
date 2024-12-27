package com.android.systemui.deviceentry.domain.interactor;

import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final /* synthetic */ class DeviceEntryHapticsInteractor$playErrorHaptic$2 extends AdaptedFunctionReference implements Function3 {
    public static final DeviceEntryHapticsInteractor$playErrorHaptic$2 INSTANCE = new DeviceEntryHapticsInteractor$playErrorHaptic$2();

    public DeviceEntryHapticsInteractor$playErrorHaptic$2() {
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
