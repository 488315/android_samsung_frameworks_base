package com.android.systemui.deviceentry.data.repository;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class FaceWakeUpTriggersConfigImpl$processStringArray$1$1 extends FunctionReferenceImpl implements Function1 {
    public static final FaceWakeUpTriggersConfigImpl$processStringArray$1$1 INSTANCE = new FaceWakeUpTriggersConfigImpl$processStringArray$1$1();

    public FaceWakeUpTriggersConfigImpl$processStringArray$1$1() {
        super(1, Integer.class, "parseInt", "parseInt(Ljava/lang/String;)I", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return Integer.valueOf(Integer.parseInt((String) obj));
    }
}
