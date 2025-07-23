package android.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class ListenerGroup<T> {
    private T mLastValue;
    private final List<ListenerWrapper<T>> mListeners = new ArrayList();

    public ListenerGroup(T t) {
        this.mLastValue = t;
    }

    public void accept(T t) {
        this.mLastValue = (T) Objects.requireNonNull(t);
        for (int i = 0; i < this.mListeners.size(); i++) {
            this.mListeners.get(i).accept(t);
        }
    }

    public void addListener(Executor executor, Consumer<T> consumer) {
        if (isConsumerPresent(consumer)) {
            return;
        }
        ListenerWrapper<T> listenerWrapper = new ListenerWrapper<>(executor, consumer);
        this.mListeners.add(listenerWrapper);
        listenerWrapper.accept(this.mLastValue);
    }

    public void removeListener(Consumer<T> consumer) {
        int computeIndex = computeIndex(consumer);
        if (computeIndex > -1) {
            this.mListeners.remove(computeIndex);
        }
    }

    public boolean isConsumerPresent(Consumer<T> consumer) {
        return computeIndex(consumer) > -1;
    }

    private int computeIndex(Consumer<T> consumer) {
        for (int i = 0; i < this.mListeners.size(); i++) {
            if (this.mListeners.get(i).isConsumerSame(consumer)) {
                return i;
            }
        }
        return -1;
    }
}
