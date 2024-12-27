package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.keyguard.shared.model.AuthenticationFlags;
import com.android.systemui.util.kotlin.Quad;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class DeviceEntryInteractor$deviceEntryRestrictionReason$1$2 extends AdaptedFunctionReference implements Function5 {
    public static final DeviceEntryInteractor$deviceEntryRestrictionReason$1$2 INSTANCE = new DeviceEntryInteractor$deviceEntryRestrictionReason$1$2();

    public DeviceEntryInteractor$deviceEntryRestrictionReason$1$2() {
        super(5, Quad.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        Boolean bool = (Boolean) obj2;
        bool.booleanValue();
        Boolean bool2 = (Boolean) obj3;
        bool2.booleanValue();
        Boolean bool3 = (Boolean) obj4;
        bool3.booleanValue();
        int i = DeviceEntryInteractor.$r8$clinit;
        return new Quad((AuthenticationFlags) obj, bool, bool2, bool3);
    }
}
