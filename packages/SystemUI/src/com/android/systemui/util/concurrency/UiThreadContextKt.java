package com.android.systemui.util.concurrency;

import android.os.Handler;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

public final class UiThreadContextKt {
    private static final long DEFAULT_TIMEOUT = 150;

    public static final <T> T runWithScissors(Handler handler, final Function0 function0) {
        final AtomicReference atomicReference = new AtomicReference();
        handler.runWithScissors(new Runnable() { // from class: com.android.systemui.util.concurrency.UiThreadContextKt$runWithScissors$1
            @Override // java.lang.Runnable
            public final void run() {
                atomicReference.set(function0.invoke());
            }
        }, DEFAULT_TIMEOUT);
        T t = (T) atomicReference.get();
        Intrinsics.checkNotNull(t);
        return t;
    }
}
