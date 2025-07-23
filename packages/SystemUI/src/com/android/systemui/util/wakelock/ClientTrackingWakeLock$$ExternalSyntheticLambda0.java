package com.android.systemui.util.wakelock;

import java.util.concurrent.atomic.AtomicInteger;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ClientTrackingWakeLock$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        AtomicInteger acquire$lambda$0;
        acquire$lambda$0 = ClientTrackingWakeLock.acquire$lambda$0((String) obj);
        return acquire$lambda$0;
    }
}
