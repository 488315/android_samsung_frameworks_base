package android.app;

import android.app.ConfigurationChangedListenerController;
import android.os.IBinder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes.dex */
class ConfigurationChangedListenerController {
    private final Object mLock = new Object();
    private final List<ListenerContainer> mListenerContainers = new ArrayList();

    ConfigurationChangedListenerController() {
    }

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
            int indexOf = indexOf(consumer);
            if (indexOf > -1) {
                this.mListenerContainers.remove(indexOf);
            }
        }
    }

    void dispatchOnConfigurationChanged(IBinder iBinder) {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mListenerContainers);
        }
        for (int i = 0; i < arrayList.size(); i++) {
            ((ListenerContainer) arrayList.get(i)).accept(iBinder);
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

    /* JADX INFO: Access modifiers changed from: private */
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

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$accept$0(IBinder iBinder) {
            this.mConsumer.accept(iBinder);
        }

        public void accept(final IBinder iBinder) {
            this.mExecutor.execute(new Runnable() { // from class: android.app.ConfigurationChangedListenerController$ListenerContainer$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ConfigurationChangedListenerController.ListenerContainer.this.lambda$accept$0(iBinder);
                }
            });
        }
    }
}
