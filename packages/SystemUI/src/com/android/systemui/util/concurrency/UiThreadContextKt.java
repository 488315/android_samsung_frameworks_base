package com.android.systemui.util.concurrency;

import android.os.Handler;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final class UiThreadContextKt {
    private static final long DEFAULT_TIMEOUT = 150;

    public static final <T> T runWithScissors(Handler handler, final Function0 function0) {
        final AtomicReference atomicReference = new AtomicReference();
        handler.runWithScissors(new Runnable() { // from class: com.android.systemui.util.concurrency.UiThreadContextKt.runWithScissors.1
            @Override // java.lang.Runnable
            public final void run() {
                atomicReference.set(function0.invoke());
            }
        }, 150L);
        T t = (T) atomicReference.get();
        t.getClass();
        return t;
    }
}
