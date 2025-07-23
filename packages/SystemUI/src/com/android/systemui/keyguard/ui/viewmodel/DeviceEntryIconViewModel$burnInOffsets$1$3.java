package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.shared.model.TransitionStep;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class DeviceEntryIconViewModel$burnInOffsets$1$3 extends AdaptedFunctionReference implements Function3 {
    public static final DeviceEntryIconViewModel$burnInOffsets$1$3 INSTANCE = new DeviceEntryIconViewModel$burnInOffsets$1$3();

    public DeviceEntryIconViewModel$burnInOffsets$1$3() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Boolean bool = (Boolean) obj2;
        bool.booleanValue();
        int i = DeviceEntryIconViewModel.$r8$clinit;
        return new Pair((TransitionStep) obj, bool);
    }
}
