package com.android.systemui.deviceentry.data.repository;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class FaceWakeUpTriggersConfigImpl$processStringArray$1$1 extends FunctionReferenceImpl implements Function1 {
    public static final FaceWakeUpTriggersConfigImpl$processStringArray$1$1 INSTANCE = new FaceWakeUpTriggersConfigImpl$processStringArray$1$1();

    public FaceWakeUpTriggersConfigImpl$processStringArray$1$1() {
        super(1, Integer.class, "parseInt", "parseInt(Ljava/lang/String;)I", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return Integer.valueOf(Integer.parseInt((String) obj));
    }
}
