package kotlinx.coroutines;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.time.Duration;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class DelayKt {
    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final CoroutineSingletons awaitCancellation(Continuation continuation) {
        DelayKt$awaitCancellation$1 delayKt$awaitCancellation$1;
        int i;
        if (continuation instanceof DelayKt$awaitCancellation$1) {
            delayKt$awaitCancellation$1 = (DelayKt$awaitCancellation$1) continuation;
            int i2 = delayKt$awaitCancellation$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                delayKt$awaitCancellation$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = delayKt$awaitCancellation$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = delayKt$awaitCancellation$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    delayKt$awaitCancellation$1.label = 1;
                    CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(delayKt$awaitCancellation$1), 1);
                    cancellableContinuationImpl.initCancellability();
                    if (cancellableContinuationImpl.getResult() == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }
        delayKt$awaitCancellation$1 = new DelayKt$awaitCancellation$1(continuation);
        Object obj2 = delayKt$awaitCancellation$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = delayKt$awaitCancellation$1.label;
        if (i != 0) {
        }
        throw new KotlinNothingValueException();
    }

    public static final Object delay(long j, Continuation continuation) {
        if (j <= 0) {
            return Unit.INSTANCE;
        }
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        if (j < Long.MAX_VALUE) {
            getDelay(cancellableContinuationImpl.context).scheduleResumeAfterDelay(j, cancellableContinuationImpl);
        }
        Object result = cancellableContinuationImpl.getResult();
        return result == CoroutineSingletons.COROUTINE_SUSPENDED ? result : Unit.INSTANCE;
    }

    /* renamed from: delay-VtjQ1oo, reason: not valid java name */
    public static final Object m2868delayVtjQ1oo(long j, Continuation continuation) {
        Duration.Companion.getClass();
        long j2 = 0;
        if (Duration.m2862compareToLRDsOJo(j, 0L) > 0) {
            j2 = Duration.m2863getInWholeMillisecondsimpl(j);
            if (j2 < 1) {
                j2 = 1;
            }
        }
        Object delay = delay(j2, continuation);
        return delay == CoroutineSingletons.COROUTINE_SUSPENDED ? delay : Unit.INSTANCE;
    }

    public static final Delay getDelay(CoroutineContext coroutineContext) {
        CoroutineContext.Element element = coroutineContext.get(ContinuationInterceptor.Key);
        Delay delay = element instanceof Delay ? (Delay) element : null;
        return delay == null ? DefaultExecutorKt.DefaultDelay : delay;
    }
}
