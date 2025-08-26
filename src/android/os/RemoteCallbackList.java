package android.os;

import android.os.IBinder;
import android.os.IInterface;
import android.util.ArrayMap;
import android.util.Slog;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class RemoteCallbackList<E extends IInterface> {
    private static final int DEFAULT_MAX_QUEUE_SIZE = 1000;
    public static final int FROZEN_CALLEE_POLICY_DROP = 3;
    public static final int FROZEN_CALLEE_POLICY_ENQUEUE_ALL = 1;
    public static final int FROZEN_CALLEE_POLICY_ENQUEUE_MOST_RECENT = 2;
    public static final int FROZEN_CALLEE_POLICY_UNSET = 0;
    private static final String TAG = "RemoteCallbackList";
    private Object[] mActiveBroadcast;
    private int mBroadcastCount;
    private final Executor mExecutor;
    private final int mFrozenCalleePolicy;
    ArrayMap<IBinder, RemoteCallbackList<E>.Interface> mInterfaces;
    private boolean mKilled;
    private final int mMaxQueueSize;
    private StringBuilder mRecentCallers;

    @Retention(RetentionPolicy.SOURCE)
    @interface FrozenCalleePolicy {
    }

    public void onCallbackDied(E e) {
    }

    private final class Interface implements IBinder.DeathRecipient, IBinder.FrozenStateChangeCallback {
        final IBinder mBinder;
        final Queue<Consumer<E>> mCallbackQueue;
        final Object mCookie;
        int mCurrentState = 1;
        final E mInterface;

        Interface(E e, Object obj) {
            this.mBinder = e.asBinder();
            this.mInterface = e;
            this.mCookie = obj;
            this.mCallbackQueue = (RemoteCallbackList.this.mFrozenCalleePolicy == 1 || RemoteCallbackList.this.mFrozenCalleePolicy == 2) ? new ConcurrentLinkedQueue() : null;
        }

        @Override // android.os.IBinder.FrozenStateChangeCallback
        public synchronized void onFrozenStateChanged(IBinder iBinder, int i) {
            if (i == 1) {
                if (this.mCallbackQueue != null) {
                    while (!this.mCallbackQueue.isEmpty()) {
                        this.mCallbackQueue.poll().accept(this.mInterface);
                    }
                }
                this.mCurrentState = i;
            } else {
                this.mCurrentState = i;
            }
        }

        void addCallback(Consumer<E> consumer) {
            if (RemoteCallbackList.this.mFrozenCalleePolicy == 0) {
                consumer.accept(this.mInterface);
                return;
            }
            synchronized (this) {
                if (this.mCurrentState == 1) {
                    consumer.accept(this.mInterface);
                    return;
                }
                int i = RemoteCallbackList.this.mFrozenCalleePolicy;
                if (i == 1) {
                    if (this.mCallbackQueue.size() >= RemoteCallbackList.this.mMaxQueueSize) {
                        this.mCallbackQueue.poll();
                    }
                    this.mCallbackQueue.offer(consumer);
                } else if (i == 2) {
                    this.mCallbackQueue.clear();
                    this.mCallbackQueue.offer(consumer);
                }
            }
        }

        void maybeSubscribeToFrozenCallback() throws RemoteException {
            if (RemoteCallbackList.this.mFrozenCalleePolicy != 0) {
                try {
                    this.mBinder.addFrozenStateChangeCallback(RemoteCallbackList.this.mExecutor, this);
                } catch (UnsupportedOperationException unused) {
                }
            }
        }

        void maybeUnsubscribeFromFrozenCallback() {
            if (RemoteCallbackList.this.mFrozenCalleePolicy != 0) {
                try {
                    this.mBinder.removeFrozenStateChangeCallback(this);
                } catch (IllegalArgumentException | UnsupportedOperationException unused) {
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (RemoteCallbackList.this.mInterfaces) {
                RemoteCallbackList.this.mInterfaces.remove(this.mBinder);
                maybeUnsubscribeFromFrozenCallback();
            }
            RemoteCallbackList.this.onCallbackDied(this.mInterface, this.mCookie);
        }
    }

    public static final class Builder<E extends IInterface> {
        private Executor mExecutor;
        private int mFrozenCalleePolicy;
        private InterfaceDiedCallback mInterfaceDiedCallback;
        private int mMaxQueueSize = 1000;

        public interface InterfaceDiedCallback<E extends IInterface> {
            void onInterfaceDied(RemoteCallbackList<E> remoteCallbackList, E e, Object obj);
        }

        public Builder(int i) {
            this.mFrozenCalleePolicy = i;
        }

        public Builder setMaxQueueSize(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("maxQueueSize must be positive");
            }
            if (this.mFrozenCalleePolicy != 1) {
                throw new UnsupportedOperationException("setMaxQueueSize can only be called for FROZEN_CALLEE_POLICY_ENQUEUE_ALL");
            }
            this.mMaxQueueSize = i;
            return this;
        }

        public Builder setInterfaceDiedCallback(InterfaceDiedCallback<E> interfaceDiedCallback) {
            this.mInterfaceDiedCallback = interfaceDiedCallback;
            return this;
        }

        public Builder setExecutor(Executor executor) {
            this.mExecutor = executor;
            return this;
        }

        public RemoteCallbackList<E> build() {
            Executor handlerExecutor = this.mExecutor;
            if (handlerExecutor == null && this.mFrozenCalleePolicy != 0) {
                handlerExecutor = new HandlerExecutor(Handler.getMain());
            }
            if (this.mInterfaceDiedCallback != null) {
                return (RemoteCallbackList<E>) new RemoteCallbackList<E>(this.mFrozenCalleePolicy, this.mMaxQueueSize, handlerExecutor) { // from class: android.os.RemoteCallbackList.Builder.1
                    @Override // android.os.RemoteCallbackList
                    public void onCallbackDied(E e, Object obj) {
                        Builder.this.mInterfaceDiedCallback.onInterfaceDied(this, e, obj);
                    }
                };
            }
            return new RemoteCallbackList<>(this.mFrozenCalleePolicy, this.mMaxQueueSize, handlerExecutor);
        }
    }

    public int getFrozenCalleePolicy() {
        return this.mFrozenCalleePolicy;
    }

    public int getMaxQueueSize() {
        return this.mMaxQueueSize;
    }

    public Executor getExecutor() {
        return this.mExecutor;
    }

    public RemoteCallbackList() {
        this(0, 1000, null);
    }

    private RemoteCallbackList(int i, int i2, Executor executor) {
        this.mInterfaces = new ArrayMap<>();
        this.mBroadcastCount = -1;
        this.mKilled = false;
        this.mFrozenCalleePolicy = i;
        this.mMaxQueueSize = i2;
        this.mExecutor = executor;
    }

    public boolean register(E e) {
        return register(e, null);
    }

    public boolean register(E e, Object obj) {
        synchronized (this.mInterfaces) {
            if (this.mKilled) {
                return false;
            }
            logExcessiveInterfaces();
            IBinder iBinderAsBinder = e.asBinder();
            try {
                RemoteCallbackList<E>.Interface r3 = new Interface(e, obj);
                unregister(e);
                iBinderAsBinder.linkToDeath(r3, 0);
                r3.maybeSubscribeToFrozenCallback();
                this.mInterfaces.put(iBinderAsBinder, r3);
                return true;
            } catch (RemoteException unused) {
                return false;
            }
        }
    }

    public boolean unregister(E e) {
        synchronized (this.mInterfaces) {
            RemoteCallbackList<E>.Interface interfaceRemove = this.mInterfaces.remove(e.asBinder());
            if (interfaceRemove == null) {
                return false;
            }
            interfaceRemove.mInterface.asBinder().unlinkToDeath(interfaceRemove, 0);
            interfaceRemove.maybeUnsubscribeFromFrozenCallback();
            return true;
        }
    }

    public void kill() {
        synchronized (this.mInterfaces) {
            for (int size = this.mInterfaces.size() - 1; size >= 0; size--) {
                RemoteCallbackList<E>.Interface interfaceValueAt = this.mInterfaces.valueAt(size);
                interfaceValueAt.mInterface.asBinder().unlinkToDeath(interfaceValueAt, 0);
                interfaceValueAt.maybeUnsubscribeFromFrozenCallback();
            }
            this.mInterfaces.clear();
            this.mKilled = true;
        }
    }

    public void onCallbackDied(E e, Object obj) {
        onCallbackDied(e);
    }

    public int beginBroadcast() {
        if (this.mFrozenCalleePolicy != 0) {
            throw new UnsupportedOperationException();
        }
        return beginBroadcastInternal();
    }

    private int beginBroadcastInternal() {
        synchronized (this.mInterfaces) {
            if (this.mBroadcastCount > 0) {
                throw new IllegalStateException("beginBroadcast() called while already in a broadcast");
            }
            int size = this.mInterfaces.size();
            this.mBroadcastCount = size;
            if (size <= 0) {
                return 0;
            }
            Object[] objArr = this.mActiveBroadcast;
            if (objArr == null || objArr.length < size) {
                objArr = new Object[size];
                this.mActiveBroadcast = objArr;
            }
            for (int i = 0; i < size; i++) {
                objArr[i] = this.mInterfaces.valueAt(i);
            }
            return size;
        }
    }

    public E getBroadcastItem(int i) {
        return ((Interface) this.mActiveBroadcast[i]).mInterface;
    }

    public Object getBroadcastCookie(int i) {
        return ((Interface) this.mActiveBroadcast[i]).mCookie;
    }

    public void finishBroadcast() {
        synchronized (this.mInterfaces) {
            int i = this.mBroadcastCount;
            if (i < 0) {
                throw new IllegalStateException("finishBroadcast() called outside of a broadcast");
            }
            Object[] objArr = this.mActiveBroadcast;
            if (objArr != null) {
                for (int i2 = 0; i2 < i; i2++) {
                    objArr[i2] = null;
                }
            }
            this.mBroadcastCount = -1;
        }
    }

    public void broadcast(Consumer<E> consumer) {
        int iBeginBroadcastInternal = beginBroadcastInternal();
        for (int i = 0; i < iBeginBroadcastInternal; i++) {
            try {
                ((Interface) this.mActiveBroadcast[i]).addCallback(consumer);
            } finally {
                finishBroadcast();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <C> void broadcastForEachCookie(Consumer<C> consumer) {
        int iBeginBroadcast = beginBroadcast();
        for (int i = 0; i < iBeginBroadcast; i++) {
            try {
                consumer.accept(getBroadcastCookie(i));
            } finally {
                finishBroadcast();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <C> void broadcast(BiConsumer<E, C> biConsumer) {
        int iBeginBroadcast = beginBroadcast();
        for (int i = 0; i < iBeginBroadcast; i++) {
            try {
                biConsumer.accept(getBroadcastItem(i), getBroadcastCookie(i));
            } finally {
                finishBroadcast();
            }
        }
    }

    public int getRegisteredCallbackCount() {
        synchronized (this.mInterfaces) {
            if (this.mKilled) {
                return 0;
            }
            return this.mInterfaces.size();
        }
    }

    public E getRegisteredCallbackItem(int i) {
        synchronized (this.mInterfaces) {
            if (this.mKilled) {
                return null;
            }
            return this.mInterfaces.valueAt(i).mInterface;
        }
    }

    public Object getRegisteredCallbackCookie(int i) {
        synchronized (this.mInterfaces) {
            if (this.mKilled) {
                return null;
            }
            return this.mInterfaces.valueAt(i).mCookie;
        }
    }

    public void dump(PrintWriter printWriter, String str) {
        synchronized (this.mInterfaces) {
            printWriter.print(str);
            printWriter.print("callbacks: ");
            printWriter.println(this.mInterfaces.size());
            printWriter.print(str);
            printWriter.print("killed: ");
            printWriter.println(this.mKilled);
            printWriter.print(str);
            printWriter.print("broadcasts count: ");
            printWriter.println(this.mBroadcastCount);
        }
    }

    private void logExcessiveInterfaces() {
        long size = this.mInterfaces.size();
        if (size >= 3000) {
            if (size == 3000 && this.mRecentCallers == null) {
                this.mRecentCallers = new StringBuilder();
            }
            if (this.mRecentCallers == null || r0.length() >= 1000) {
                return;
            }
            this.mRecentCallers.append(Debug.getCallers(5));
            this.mRecentCallers.append('\n');
            if (this.mRecentCallers.length() >= 1000) {
                Slog.wtf(TAG, "More than 3000 remote callbacks registered. Recent callers:\n" + this.mRecentCallers.toString());
                this.mRecentCallers = null;
            }
        }
    }
}
