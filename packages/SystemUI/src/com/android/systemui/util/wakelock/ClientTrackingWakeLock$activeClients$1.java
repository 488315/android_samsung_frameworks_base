package com.android.systemui.util.wakelock;

import java.util.concurrent.atomic.AtomicInteger;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class ClientTrackingWakeLock$activeClients$1 extends FunctionReferenceImpl implements Function1 {
    public static final ClientTrackingWakeLock$activeClients$1 INSTANCE = new ClientTrackingWakeLock$activeClients$1();

    public ClientTrackingWakeLock$activeClients$1() {
        super(1, AtomicInteger.class, "get", "get()I", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke, reason: merged with bridge method [inline-methods] */
    public final Integer mo779invoke(AtomicInteger atomicInteger) {
        return Integer.valueOf(atomicInteger.get());
    }
}
