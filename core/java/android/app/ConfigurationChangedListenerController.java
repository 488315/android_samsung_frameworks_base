package android.app;

import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

class ConfigurationChangedListenerController {
    private final Object mLock = new Object();
    private final List<ListenerContainer> mListenerContainers = new ArrayList();

    ConfigurationChangedListenerController() {}

    void addListener(Executor executor, Consumer<IBinder> consumer) {
        synchronized (this.mLock) {
            if (indexOf(consumer) > -1) {
                return;
            }
            this.mListenerContainers.add(new ListenerContainer(executor, consumer));
        }
    }

    void removeListener(Consumer<IBinder> consumer) {
        synchronized (this.mLock) {
            int index = indexOf(consumer);
            if (index > -1) {
                this.mListenerContainers.remove(index);
            }
        }
    }

    void dispatchOnConfigurationChanged(IBinder activityToken) {
        List<ListenerContainer> consumers;
        synchronized (this.mLock) {
            consumers = new ArrayList<>(this.mListenerContainers);
        }
        for (int i = 0; i < consumers.size(); i++) {
            consumers.get(i).accept(activityToken);
        }
    }

    private int indexOf(Consumer<IBinder> consumer) {
        for (int i = 0; i < this.mListenerContainers.size(); i++) {
            if (this.mListenerContainers.get(i).isMatch(consumer)) {
                return i;
            }
        }
        return -1;
    }

    static final class ListenerContainer {
        private final Consumer<IBinder> mConsumer;
        private final Executor mExecutor;

        ListenerContainer(Executor executor, Consumer<IBinder> consumer) {
            this.mExecutor = executor;
            this.mConsumer = consumer;
        }

        public boolean isMatch(Consumer<IBinder> consumer) {
            return this.mConsumer.equals(consumer);
        }

        public /* synthetic */ void lambda$accept$0(IBinder activityToken) {
            this.mConsumer.accept(activityToken);
        }

        public void accept(final IBinder activityToken) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.app.ConfigurationChangedListenerController$ListenerContainer$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ConfigurationChangedListenerController.ListenerContainer.this
                                    .lambda$accept$0(activityToken);
                        }
                    });
        }
    }
}
