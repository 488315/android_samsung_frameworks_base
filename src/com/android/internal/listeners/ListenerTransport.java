package com.android.internal.listeners;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes5.dex */
public interface ListenerTransport<TListener> {
    TListener getListener();

    void unregister();

    default void execute(Executor executor, final Consumer<TListener> consumer) {
        Objects.requireNonNull(consumer);
        if (getListener() == null) {
            return;
        }
        executor.execute(new Runnable() { // from class: com.android.internal.listeners.ListenerTransport$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ListenerTransport.this.lambda$execute$0(consumer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* synthetic */ default void lambda$execute$0(Consumer consumer) {
        TListener listener = getListener();
        if (listener == null) {
            return;
        }
        consumer.accept(listener);
    }
}
