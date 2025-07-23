package android.os;

import android.annotation.SystemApi;
import android.os.IMessenger;
import android.util.Log;
import android.util.Printer;

/* loaded from: classes3.dex */
public class Handler {
    private static final boolean FIND_POTENTIAL_LEAKS = false;
    private static Handler MAIN_THREAD_HANDLER = null;
    private static final String TAG = "Handler";
    final boolean mAsynchronous;
    final Callback mCallback;
    private final boolean mIsShared;
    final Looper mLooper;
    IMessenger mMessenger;
    final MessageQueue mQueue;

    public interface Callback {
        boolean handleMessage(Message message);
    }

    public void handleMessage(Message message) {
    }

    public void dispatchMessage(Message message) {
        if (message.callback != null) {
            handleCallback(message);
            return;
        }
        Callback callback = this.mCallback;
        if (callback == null || !callback.handleMessage(message)) {
            handleMessage(message);
        }
    }

    @Deprecated
    public Handler() {
        this((Callback) null, false);
    }

    @Deprecated
    public Handler(Callback callback) {
        this(callback, false);
    }

    public Handler(Looper looper) {
        this(looper, null, false);
    }

    public Handler(Looper looper, Callback callback) {
        this(looper, callback, false);
    }

    public Handler(boolean z) {
        this((Callback) null, z);
    }

    public Handler(Callback callback, boolean z) {
        Looper myLooper = Looper.myLooper();
        this.mLooper = myLooper;
        if (myLooper == null) {
            throw new RuntimeException("Can't create handler inside thread " + Thread.currentThread() + " that has not called Looper.prepare()");
        }
        this.mQueue = myLooper.mQueue;
        this.mCallback = callback;
        this.mAsynchronous = z;
        this.mIsShared = false;
    }

    public Handler(Looper looper, Callback callback, boolean z) {
        this(looper, callback, z, false);
    }

    public Handler(Looper looper, Callback callback, boolean z, boolean z2) {
        this.mLooper = looper;
        this.mQueue = looper.mQueue;
        this.mCallback = callback;
        this.mAsynchronous = z;
        this.mIsShared = z2;
    }

    public static Handler createAsync(Looper looper) {
        if (looper == null) {
            throw new NullPointerException("looper must not be null");
        }
        return new Handler(looper, null, true);
    }

    public static Handler createAsync(Looper looper, Callback callback) {
        if (looper == null) {
            throw new NullPointerException("looper must not be null");
        }
        if (callback == null) {
            throw new NullPointerException("callback must not be null");
        }
        return new Handler(looper, callback, true);
    }

    public static Handler getMain() {
        if (MAIN_THREAD_HANDLER == null) {
            MAIN_THREAD_HANDLER = new Handler(Looper.getMainLooper());
        }
        return MAIN_THREAD_HANDLER;
    }

    public static Handler mainIfNull(Handler handler) {
        return handler == null ? getMain() : handler;
    }

    public String getTraceName(Message message) {
        if (message.callback instanceof TraceNameSupplier) {
            return ((TraceNameSupplier) message.callback).getTraceName();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(": ");
        if (message.callback != null) {
            sb.append(message.callback.getClass().getName());
        } else {
            sb.append("#");
            sb.append(message.what);
        }
        return sb.toString();
    }

    public String getMessageName(Message message) {
        if (message.callback != null) {
            return message.callback.getClass().getName();
        }
        return "0x" + Integer.toHexString(message.what);
    }

    public final Message obtainMessage() {
        return Message.obtain(this);
    }

    public final Message obtainMessage(int i) {
        return Message.obtain(this, i);
    }

    public final Message obtainMessage(int i, Object obj) {
        return Message.obtain(this, i, obj);
    }

    public final Message obtainMessage(int i, int i2, int i3) {
        return Message.obtain(this, i, i2, i3);
    }

    public final Message obtainMessage(int i, int i2, int i3, Object obj) {
        return Message.obtain(this, i, i2, i3, obj);
    }

    public final boolean post(Runnable runnable) {
        return sendMessageDelayed(getPostMessage(runnable), 0L);
    }

    public final boolean postAtTime(Runnable runnable, long j) {
        return sendMessageAtTime(getPostMessage(runnable), j);
    }

    public final boolean postAtTime(Runnable runnable, Object obj, long j) {
        return sendMessageAtTime(getPostMessage(runnable, obj), j);
    }

    public final boolean postDelayed(Runnable runnable, long j) {
        return sendMessageDelayed(getPostMessage(runnable), j);
    }

    public final boolean postDelayed(Runnable runnable, int i, long j) {
        return sendMessageDelayed(getPostMessage(runnable).setWhat(i), j);
    }

    public final boolean postDelayed(Runnable runnable, Object obj, long j) {
        return sendMessageDelayed(getPostMessage(runnable, obj), j);
    }

    public final boolean postAtFrontOfQueue(Runnable runnable) {
        return sendMessageAtFrontOfQueue(getPostMessage(runnable));
    }

    public final boolean runWithScissors(Runnable runnable, long j) {
        if (runnable == null) {
            throw new IllegalArgumentException("runnable must not be null");
        }
        if (j < 0) {
            throw new IllegalArgumentException("timeout must be non-negative");
        }
        if (Looper.myLooper() == this.mLooper) {
            runnable.run();
            return true;
        }
        return new BlockingRunnable(runnable).postAndWait(this, j);
    }

    public final void removeCallbacks(Runnable runnable) {
        this.mQueue.removeMessages(this, runnable, (Object) null);
    }

    public final void removeCallbacks(Runnable runnable, Object obj) {
        this.mQueue.removeMessages(this, runnable, obj);
    }

    public final boolean sendMessage(Message message) {
        return sendMessageDelayed(message, 0L);
    }

    public final boolean sendEmptyMessage(int i) {
        return sendEmptyMessageDelayed(i, 0L);
    }

    public final boolean sendEmptyMessageDelayed(int i, long j) {
        Message obtain = Message.obtain();
        obtain.what = i;
        return sendMessageDelayed(obtain, j);
    }

    public final boolean sendEmptyMessageAtTime(int i, long j) {
        Message obtain = Message.obtain();
        obtain.what = i;
        return sendMessageAtTime(obtain, j);
    }

    public final boolean sendMessageDelayed(Message message, long j) {
        if (j < 0) {
            j = 0;
        }
        return sendMessageAtTime(message, SystemClock.uptimeMillis() + j);
    }

    public boolean sendMessageAtTime(Message message, long j) {
        MessageQueue messageQueue = this.mQueue;
        if (messageQueue == null) {
            RuntimeException runtimeException = new RuntimeException(this + " sendMessageAtTime() called with no mQueue");
            Log.w("Looper", runtimeException.getMessage(), runtimeException);
            return false;
        }
        return enqueueMessage(messageQueue, message, j);
    }

    public final boolean sendMessageAtFrontOfQueue(Message message) {
        MessageQueue messageQueue = this.mQueue;
        if (messageQueue == null) {
            RuntimeException runtimeException = new RuntimeException(this + " sendMessageAtFrontOfQueue() called with no mQueue");
            Log.w("Looper", runtimeException.getMessage(), runtimeException);
            return false;
        }
        return enqueueMessage(messageQueue, message, 0L);
    }

    public final boolean executeOrSendMessage(Message message) {
        if (this.mLooper == Looper.myLooper()) {
            dispatchMessage(message);
            return true;
        }
        return sendMessage(message);
    }

    private boolean enqueueMessage(MessageQueue messageQueue, Message message, long j) {
        message.target = this;
        message.workSourceUid = ThreadLocalWorkSource.getUid();
        if (this.mAsynchronous) {
            message.setAsynchronous(true);
        }
        return messageQueue.enqueueMessage(message, j);
    }

    private Object disallowNullArgumentIfShared(Object obj) {
        if (this.mIsShared && obj == null) {
            throw new IllegalArgumentException("Null argument disallowed for shared handler. Consider creating your own Handler instance.");
        }
        return obj;
    }

    public final void removeMessages(int i) {
        this.mQueue.removeMessages(this, i, (Object) null);
    }

    public final void removeMessages(int i, Object obj) {
        this.mQueue.removeMessages(this, i, disallowNullArgumentIfShared(obj));
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public final void removeEqualMessages(int i, Object obj) {
        this.mQueue.removeEqualMessages(this, i, disallowNullArgumentIfShared(obj));
    }

    public final void removeCallbacksAndMessages(Object obj) {
        this.mQueue.removeCallbacksAndMessages(this, disallowNullArgumentIfShared(obj));
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public final void removeCallbacksAndEqualMessages(Object obj) {
        this.mQueue.removeCallbacksAndEqualMessages(this, disallowNullArgumentIfShared(obj));
    }

    public final boolean hasMessages(int i) {
        return this.mQueue.hasMessages(this, i, (Object) null);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public final boolean hasMessagesOrCallbacks() {
        return this.mQueue.hasMessages(this);
    }

    public final boolean hasMessages(int i, Object obj) {
        return this.mQueue.hasMessages(this, i, obj);
    }

    public final boolean hasEqualMessages(int i, Object obj) {
        return this.mQueue.hasEqualMessages(this, i, obj);
    }

    public final boolean hasCallbacks(Runnable runnable) {
        return this.mQueue.hasMessages(this, runnable, (Object) null);
    }

    public final Looper getLooper() {
        return this.mLooper;
    }

    public final void dump(Printer printer, String str) {
        printer.println(str + this + " @ " + SystemClock.uptimeMillis());
        Looper looper = this.mLooper;
        if (looper == null) {
            printer.println(str + "looper uninitialized");
            return;
        }
        looper.dump(printer, str + "  ");
    }

    public final void dumpMine(Printer printer, String str) {
        printer.println(str + this + " @ " + SystemClock.uptimeMillis());
        Looper looper = this.mLooper;
        if (looper == null) {
            printer.println(str + "looper uninitialized");
            return;
        }
        looper.dump(printer, str + "  ", this);
    }

    public String toString() {
        return "Handler (" + getClass().getName() + ") {" + Integer.toHexString(System.identityHashCode(this)) + "}";
    }

    final IMessenger getIMessenger() {
        synchronized (this.mQueue) {
            IMessenger iMessenger = this.mMessenger;
            if (iMessenger != null) {
                return iMessenger;
            }
            MessengerImpl messengerImpl = new MessengerImpl();
            this.mMessenger = messengerImpl;
            return messengerImpl;
        }
    }

    private final class MessengerImpl extends IMessenger.Stub {
        private MessengerImpl() {
        }

        @Override // android.os.IMessenger
        public void send(Message message) {
            message.sendingUid = Binder.getCallingUid();
            Handler.this.sendMessage(message);
        }
    }

    private static Message getPostMessage(Runnable runnable) {
        Message obtain = Message.obtain();
        obtain.callback = runnable;
        return obtain;
    }

    private static Message getPostMessage(Runnable runnable, Object obj) {
        Message obtain = Message.obtain();
        obtain.obj = obj;
        obtain.callback = runnable;
        return obtain;
    }

    private static void handleCallback(Message message) {
        message.callback.run();
    }

    private static final class BlockingRunnable implements Runnable {
        private boolean mDone;
        private final Runnable mTask;

        public BlockingRunnable(Runnable runnable) {
            this.mTask = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.mTask.run();
                synchronized (this) {
                    this.mDone = true;
                    notifyAll();
                }
            } catch (Throwable th) {
                synchronized (this) {
                    this.mDone = true;
                    notifyAll();
                    throw th;
                }
            }
        }

        public boolean postAndWait(Handler handler, long j) {
            if (!handler.post(this)) {
                return false;
            }
            synchronized (this) {
                if (j > 0) {
                    long uptimeMillis = SystemClock.uptimeMillis() + j;
                    while (!this.mDone) {
                        long uptimeMillis2 = uptimeMillis - SystemClock.uptimeMillis();
                        if (uptimeMillis2 <= 0) {
                            return false;
                        }
                        try {
                            wait(uptimeMillis2);
                        } catch (InterruptedException unused) {
                        }
                    }
                } else {
                    while (!this.mDone) {
                        try {
                            wait();
                        } catch (InterruptedException unused2) {
                        }
                    }
                }
                return true;
            }
        }
    }
}
