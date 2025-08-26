package com.android.systemui.lottie;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieListener;
import com.airbnb.lottie.LottieTask;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

/* loaded from: classes2.dex */
public abstract class LottieTaskExtKt {
    public static final Object await(final LottieTask lottieTask, Continuation continuation) {
        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final LottieListener lottieListener = new LottieListener() { // from class: com.android.systemui.lottie.LottieTaskExtKt$await$2$resultListener$1
            @Override // com.airbnb.lottie.LottieListener
            public final void onResult(Object obj) {
                LottieComposition lottieComposition = (LottieComposition) obj;
                CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                if (cancellableContinuation.isCancelled$1() || cancellableContinuation.isCompleted()) {
                    return;
                }
                int i = Result.$r8$clinit;
                cancellableContinuation.resumeWith(lottieComposition);
            }
        };
        final LottieListener lottieListener2 = new LottieListener() { // from class: com.android.systemui.lottie.LottieTaskExtKt$await$2$failureListener$1
            @Override // com.airbnb.lottie.LottieListener
            public final void onResult(Object obj) {
                Throwable th = (Throwable) obj;
                CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                if (cancellableContinuation.isCancelled$1() || cancellableContinuation.isCompleted()) {
                    return;
                }
                int i = Result.$r8$clinit;
                th.getClass();
                cancellableContinuation.resumeWith(new Result.Failure(th));
            }
        };
        lottieTask.addListener(lottieListener);
        lottieTask.addFailureListener(lottieListener2);
        cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.android.systemui.lottie.LottieTaskExtKt$await$2$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                LottieTask lottieTask2 = lottieTask;
                LottieListener lottieListener3 = lottieListener;
                synchronized (lottieTask2) {
                    lottieTask2.successListeners.remove(lottieListener3);
                }
                LottieListener lottieListener4 = lottieListener2;
                synchronized (lottieTask2) {
                    lottieTask2.failureListeners.remove(lottieListener4);
                }
                return Unit.INSTANCE;
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return result;
    }
}
