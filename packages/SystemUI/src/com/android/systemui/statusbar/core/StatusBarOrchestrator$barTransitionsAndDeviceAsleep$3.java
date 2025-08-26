package com.android.systemui.statusbar.core;

import com.android.systemui.statusbar.phone.PhoneStatusBarTransitions;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* loaded from: classes3.dex */
final /* synthetic */ class StatusBarOrchestrator$barTransitionsAndDeviceAsleep$3 extends AdaptedFunctionReference implements Function3 {
    public static final StatusBarOrchestrator$barTransitionsAndDeviceAsleep$3 INSTANCE = new StatusBarOrchestrator$barTransitionsAndDeviceAsleep$3();

    public StatusBarOrchestrator$barTransitionsAndDeviceAsleep$3() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Boolean bool = (Boolean) obj2;
        bool.booleanValue();
        return new Pair((PhoneStatusBarTransitions) obj, bool);
    }
}
