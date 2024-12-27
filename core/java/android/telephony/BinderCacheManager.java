package android.telephony;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.telephony.BinderCacheManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

/* loaded from: classes4.dex */
public class BinderCacheManager<T extends IInterface> {
    private final BinderInterfaceFactory<T> mBinderInterfaceFactory;
    private final AtomicReference<BinderCacheManager<T>.BinderDeathTracker> mCachedConnection = new AtomicReference<>();

    public interface BinderInterfaceFactory<T> {
        T create();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class BinderDeathTracker implements IBinder.DeathRecipient {
        private final T mConnection;
        private final HashMap<Object, Runnable> mListeners = new HashMap<>();

        BinderDeathTracker(T connection) {
            this.mConnection = connection;
            try {
                this.mConnection.asBinder().linkToDeath(this, 0);
            } catch (RemoteException e) {
            }
        }

        public boolean addListener(Object key, Runnable r) {
            synchronized (this.mListeners) {
                if (!isAlive()) {
                    return false;
                }
                this.mListeners.put(key, r);
                return true;
            }
        }

        public void removeListener(Object runnableKey) {
            synchronized (this.mListeners) {
                this.mListeners.remove(runnableKey);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            ArrayList<Runnable> listeners;
            synchronized (this.mListeners) {
                listeners = new ArrayList<>(this.mListeners.values());
                this.mListeners.clear();
                try {
                    this.mConnection.asBinder().unlinkToDeath(this, 0);
                } catch (NoSuchElementException e) {
                }
            }
            listeners.forEach(new BinderCacheManager$BinderDeathTracker$$ExternalSyntheticLambda0());
        }

        public T getConnection() {
            return this.mConnection;
        }

        public boolean isAlive() {
            return this.mConnection.asBinder().isBinderAlive();
        }
    }

    public BinderCacheManager(BinderInterfaceFactory<T> factory) {
        this.mBinderInterfaceFactory = factory;
    }

    public T listenOnBinder(Object obj, Runnable runnable) {
        BinderCacheManager<T>.BinderDeathTracker tracker;
        if (obj == null || runnable == null || (tracker = getTracker()) == null || !tracker.addListener(obj, runnable)) {
            return null;
        }
        return (T) tracker.getConnection();
    }

    public T getBinder() {
        BinderCacheManager<T>.BinderDeathTracker tracker = getTracker();
        if (tracker != null) {
            return (T) tracker.getConnection();
        }
        return null;
    }

    public T removeRunnable(Object obj) {
        BinderCacheManager<T>.BinderDeathTracker tracker;
        if (obj == null || (tracker = getTracker()) == null) {
            return null;
        }
        tracker.removeListener(obj);
        return (T) tracker.getConnection();
    }

    private BinderCacheManager<T>.BinderDeathTracker getTracker() {
        return this.mCachedConnection.updateAndGet(new UnaryOperator() { // from class: android.telephony.BinderCacheManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                BinderCacheManager.BinderDeathTracker lambda$getTracker$0;
                lambda$getTracker$0 = BinderCacheManager.this.lambda$getTracker$0((BinderCacheManager.BinderDeathTracker) obj);
                return lambda$getTracker$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ BinderDeathTracker lambda$getTracker$0(BinderDeathTracker oldVal) {
        BinderDeathTracker binderDeathTracker = oldVal;
        if (binderDeathTracker == null || !binderDeathTracker.isAlive()) {
            T binder = this.mBinderInterfaceFactory.create();
            binderDeathTracker = binder != null ? new BinderDeathTracker(binder) : null;
        }
        if (binderDeathTracker == null || !binderDeathTracker.isAlive()) {
            return null;
        }
        return binderDeathTracker;
    }
}
