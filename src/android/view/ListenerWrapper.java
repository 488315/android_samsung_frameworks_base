package android.view;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class ListenerWrapper<T> {
    private final Consumer<T> mConsumer;
    private final Executor mExecutor;

    public ListenerWrapper(Executor executor, Consumer<T> consumer) {
        this.mExecutor = (Executor) Objects.requireNonNull(executor);
        this.mConsumer = (Consumer) Objects.requireNonNull(consumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$accept$0(Object obj) {
        this.mConsumer.accept(obj);
    }

    public void accept(final T t) {
        this.mExecutor.execute(new Runnable() { // from class: android.view.ListenerWrapper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ListenerWrapper.this.lambda$accept$0(t);
            }
        });
    }

    public boolean isConsumerSame(Consumer<T> consumer) {
        return this.mConsumer.equals(consumer);
    }
}
