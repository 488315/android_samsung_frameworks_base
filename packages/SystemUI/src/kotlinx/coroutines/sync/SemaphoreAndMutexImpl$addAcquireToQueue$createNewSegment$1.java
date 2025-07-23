package kotlinx.coroutines.sync;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final /* synthetic */ class SemaphoreAndMutexImpl$addAcquireToQueue$createNewSegment$1 extends FunctionReferenceImpl implements Function2 {
    public static final SemaphoreAndMutexImpl$addAcquireToQueue$createNewSegment$1 INSTANCE = new SemaphoreAndMutexImpl$addAcquireToQueue$createNewSegment$1();

    public SemaphoreAndMutexImpl$addAcquireToQueue$createNewSegment$1() {
        super(2, SemaphoreKt.class, "createSegment", "createSegment(JLkotlinx/coroutines/sync/SemaphoreSegment;)Lkotlinx/coroutines/sync/SemaphoreSegment;", 1);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = SemaphoreKt.MAX_SPIN_CYCLES;
        return new SemaphoreSegment(((Number) obj).longValue(), (SemaphoreSegment) obj2, 0);
    }
}
