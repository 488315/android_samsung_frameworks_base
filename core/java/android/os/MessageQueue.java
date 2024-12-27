package android.os;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Printer;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;

import java.io.FileDescriptor;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public final class MessageQueue {
    private static final boolean DEBUG = false;
    private static final String TAG = "MessageQueue";
    private int mAsyncMessageCount;
    private boolean mBlocked;
    private SparseArray<FileDescriptorRecord> mFileDescriptorRecords;
    private Message mLast;
    Message mMessages;
    private int mNextBarrierToken;
    private IdleHandler[] mPendingIdleHandlers;
    private final boolean mQuitAllowed;
    private boolean mQuitting;
    private final ArrayList<IdleHandler> mIdleHandlers = new ArrayList<>();
    private long mPtr = nativeInit();

    public interface IdleHandler {
        boolean queueIdle();
    }

    public interface OnFileDescriptorEventListener {
        public static final int EVENT_ERROR = 4;
        public static final int EVENT_INPUT = 1;
        public static final int EVENT_OUTPUT = 2;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Events {}

        int onFileDescriptorEvents(FileDescriptor fileDescriptor, int i);
    }

    private static native void nativeDestroy(long j);

    private static native long nativeInit();

    private static native boolean nativeIsPolling(long j);

    private native void nativePollOnce(long j, int i);

    private static native void nativeSetFileDescriptorEvents(long j, int i, int i2);

    private static native void nativeWake(long j);

    MessageQueue(boolean quitAllowed) {
        this.mQuitAllowed = quitAllowed;
    }

    protected void finalize() throws Throwable {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    private void dispose() {
        if (this.mPtr != 0) {
            nativeDestroy(this.mPtr);
            this.mPtr = 0L;
        }
    }

    public boolean isIdle() {
        boolean z;
        synchronized (this) {
            long now = SystemClock.uptimeMillis();
            z = this.mMessages == null || now < this.mMessages.when;
        }
        return z;
    }

    public void addIdleHandler(IdleHandler handler) {
        if (handler == null) {
            throw new NullPointerException("Can't add a null IdleHandler");
        }
        synchronized (this) {
            this.mIdleHandlers.add(handler);
        }
    }

    public void removeIdleHandler(IdleHandler handler) {
        synchronized (this) {
            this.mIdleHandlers.remove(handler);
        }
    }

    public boolean isPolling() {
        boolean isPollingLocked;
        synchronized (this) {
            isPollingLocked = isPollingLocked();
        }
        return isPollingLocked;
    }

    private boolean isPollingLocked() {
        return !this.mQuitting && nativeIsPolling(this.mPtr);
    }

    public void addOnFileDescriptorEventListener(
            FileDescriptor fd, int events, OnFileDescriptorEventListener listener) {
        if (fd == null) {
            throw new IllegalArgumentException("fd must not be null");
        }
        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        synchronized (this) {
            updateOnFileDescriptorEventListenerLocked(fd, events, listener);
        }
    }

    public void removeOnFileDescriptorEventListener(FileDescriptor fd) {
        if (fd == null) {
            throw new IllegalArgumentException("fd must not be null");
        }
        synchronized (this) {
            updateOnFileDescriptorEventListenerLocked(fd, 0, null);
        }
    }

    private void updateOnFileDescriptorEventListenerLocked(
            FileDescriptor fd, int events, OnFileDescriptorEventListener listener) {
        int fdNum = fd.getInt$();
        int index = -1;
        FileDescriptorRecord record = null;
        if (this.mFileDescriptorRecords != null
                && (index = this.mFileDescriptorRecords.indexOfKey(fdNum)) >= 0
                && (record = this.mFileDescriptorRecords.valueAt(index)) != null
                && record.mEvents == events) {
            return;
        }
        if (events != 0) {
            int events2 = events | 4;
            if (record == null) {
                if (this.mFileDescriptorRecords == null) {
                    this.mFileDescriptorRecords = new SparseArray<>();
                }
                this.mFileDescriptorRecords.put(
                        fdNum, new FileDescriptorRecord(fd, events2, listener));
            } else {
                record.mListener = listener;
                record.mEvents = events2;
                record.mSeq++;
            }
            nativeSetFileDescriptorEvents(this.mPtr, fdNum, events2);
            return;
        }
        if (record != null) {
            record.mEvents = 0;
            this.mFileDescriptorRecords.removeAt(index);
            nativeSetFileDescriptorEvents(this.mPtr, fdNum, 0);
        }
    }

    private int dispatchEvents(int fd, int events) {
        synchronized (this) {
            FileDescriptorRecord record = this.mFileDescriptorRecords.get(fd);
            if (record == null) {
                return 0;
            }
            int oldWatchedEvents = record.mEvents;
            int events2 = events & oldWatchedEvents;
            if (events2 == 0) {
                return oldWatchedEvents;
            }
            OnFileDescriptorEventListener listener = record.mListener;
            int seq = record.mSeq;
            int newWatchedEvents = listener.onFileDescriptorEvents(record.mDescriptor, events2);
            if (newWatchedEvents != 0) {
                newWatchedEvents |= 4;
            }
            if (newWatchedEvents != oldWatchedEvents) {
                synchronized (this) {
                    int index = this.mFileDescriptorRecords.indexOfKey(fd);
                    if (index >= 0
                            && this.mFileDescriptorRecords.valueAt(index) == record
                            && record.mSeq == seq) {
                        record.mEvents = newWatchedEvents;
                        if (newWatchedEvents == 0) {
                            this.mFileDescriptorRecords.removeAt(index);
                        }
                    }
                }
            }
            return newWatchedEvents;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00af, code lost:

       r5 = 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00b0, code lost:

       if (r5 >= r2) goto L92;
    */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00b2, code lost:

       r6 = r14.mPendingIdleHandlers[r5];
       r14.mPendingIdleHandlers[r5] = null;
       r7 = false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00bf, code lost:

       r7 = r6.queueIdle();
    */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00c1, code lost:

       r8 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00c2, code lost:

       android.util.Log.wtf(android.os.MessageQueue.TAG, "IdleHandler threw exception", r8);
    */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00d9, code lost:

       r2 = 0;
       r4 = 0;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    android.os.Message next() {
        /*
            Method dump skipped, instructions count: 224
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.os.MessageQueue.next():android.os.Message");
    }

    void quit(boolean safe) {
        if (!this.mQuitAllowed) {
            throw new IllegalStateException("Main thread not allowed to quit.");
        }
        synchronized (this) {
            if (this.mQuitting) {
                return;
            }
            this.mQuitting = true;
            if (safe) {
                removeAllFutureMessagesLocked();
            } else {
                removeAllMessagesLocked();
            }
            nativeWake(this.mPtr);
        }
    }

    public int postSyncBarrier() {
        return postSyncBarrier(SystemClock.uptimeMillis());
    }

    private int postSyncBarrier(long when) {
        synchronized (this) {
            int token = this.mNextBarrierToken;
            this.mNextBarrierToken = token + 1;
            Message msg = Message.obtain();
            msg.markInUse();
            msg.when = when;
            msg.arg1 = token;
            if (Flags.messageQueueTailTracking() && this.mLast != null && this.mLast.when <= when) {
                this.mLast.next = msg;
                this.mLast = msg;
                msg.next = null;
                return token;
            }
            Message prev = null;
            Message p = this.mMessages;
            if (when != 0) {
                while (p != null && p.when <= when) {
                    prev = p;
                    p = p.next;
                }
            }
            if (p == null) {
                this.mLast = msg;
            }
            if (prev != null) {
                msg.next = p;
                prev.next = msg;
            } else {
                msg.next = p;
                this.mMessages = msg;
            }
            return token;
        }
    }

    public void removeSyncBarrier(int token) {
        boolean needWake;
        synchronized (this) {
            Message prev = null;
            Message p = this.mMessages;
            while (p != null && (p.target != null || p.arg1 != token)) {
                prev = p;
                p = p.next;
            }
            if (p == null) {
                throw new IllegalStateException(
                        "The specified message queue synchronization  barrier token has not been"
                            + " posted or has already been removed.");
            }
            if (prev != null) {
                prev.next = p.next;
                if (prev.next == null) {
                    this.mLast = prev;
                }
                needWake = false;
            } else {
                this.mMessages = p.next;
                if (this.mMessages == null) {
                    this.mLast = null;
                }
                if (this.mMessages != null && this.mMessages.target == null) {
                    needWake = false;
                }
                needWake = true;
            }
            p.recycleUnchecked();
            if (needWake && !this.mQuitting) {
                nativeWake(this.mPtr);
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    boolean enqueueMessage(android.os.Message r9, long r10) {
        /*
            Method dump skipped, instructions count: 260
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.os.MessageQueue.enqueueMessage(android.os.Message,"
                    + " long):boolean");
    }

    boolean hasMessages(Handler h, int what, Object object) {
        if (h == null) {
            return false;
        }
        synchronized (this) {
            for (Message p = this.mMessages; p != null; p = p.next) {
                if (p.target == h && p.what == what && (object == null || p.obj == object)) {
                    return true;
                }
            }
            return false;
        }
    }

    boolean hasEqualMessages(Handler h, int what, Object object) {
        if (h == null) {
            return false;
        }
        synchronized (this) {
            for (Message p = this.mMessages; p != null; p = p.next) {
                if (p.target == h && p.what == what && (object == null || object.equals(p.obj))) {
                    return true;
                }
            }
            return false;
        }
    }

    boolean hasMessages(Handler h, Runnable r, Object object) {
        if (h == null) {
            return false;
        }
        synchronized (this) {
            for (Message p = this.mMessages; p != null; p = p.next) {
                if (p.target == h && p.callback == r && (object == null || p.obj == object)) {
                    return true;
                }
            }
            return false;
        }
    }

    boolean hasMessages(Handler h) {
        if (h == null) {
            return false;
        }
        synchronized (this) {
            for (Message p = this.mMessages; p != null; p = p.next) {
                if (p.target == h) {
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x002d, code lost:

       r4.mLast = r4.mMessages;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void removeMessages(android.os.Handler r5, int r6, java.lang.Object r7) {
        /*
            r4 = this;
            if (r5 != 0) goto L3
            return
        L3:
            monitor-enter(r4)
            android.os.Message r0 = r4.mMessages     // Catch: java.lang.Throwable -> L63
        L6:
            if (r0 == 0) goto L2b
            android.os.Handler r1 = r0.target     // Catch: java.lang.Throwable -> L63
            if (r1 != r5) goto L2b
            int r1 = r0.what     // Catch: java.lang.Throwable -> L63
            if (r1 != r6) goto L2b
            if (r7 == 0) goto L16
            java.lang.Object r1 = r0.obj     // Catch: java.lang.Throwable -> L63
            if (r1 != r7) goto L2b
        L16:
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L63
            r4.mMessages = r1     // Catch: java.lang.Throwable -> L63
            boolean r2 = r0.isAsynchronous()     // Catch: java.lang.Throwable -> L63
            if (r2 == 0) goto L26
            int r2 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L63
            int r2 = r2 + (-1)
            r4.mAsyncMessageCount = r2     // Catch: java.lang.Throwable -> L63
        L26:
            r0.recycleUnchecked()     // Catch: java.lang.Throwable -> L63
            r0 = r1
            goto L6
        L2b:
            if (r0 != 0) goto L31
            android.os.Message r1 = r4.mMessages     // Catch: java.lang.Throwable -> L63
            r4.mLast = r1     // Catch: java.lang.Throwable -> L63
        L31:
            if (r0 == 0) goto L61
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L63
            if (r1 == 0) goto L5f
            android.os.Handler r2 = r1.target     // Catch: java.lang.Throwable -> L63
            if (r2 != r5) goto L5f
            int r2 = r1.what     // Catch: java.lang.Throwable -> L63
            if (r2 != r6) goto L5f
            if (r7 == 0) goto L45
            java.lang.Object r2 = r1.obj     // Catch: java.lang.Throwable -> L63
            if (r2 != r7) goto L5f
        L45:
            android.os.Message r2 = r1.next     // Catch: java.lang.Throwable -> L63
            boolean r3 = r1.isAsynchronous()     // Catch: java.lang.Throwable -> L63
            if (r3 == 0) goto L53
            int r3 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L63
            int r3 = r3 + (-1)
            r4.mAsyncMessageCount = r3     // Catch: java.lang.Throwable -> L63
        L53:
            r1.recycleUnchecked()     // Catch: java.lang.Throwable -> L63
            r0.next = r2     // Catch: java.lang.Throwable -> L63
            android.os.Message r3 = r0.next     // Catch: java.lang.Throwable -> L63
            if (r3 != 0) goto L31
            r4.mLast = r0     // Catch: java.lang.Throwable -> L63
            goto L31
        L5f:
            r0 = r1
            goto L31
        L61:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L63
            return
        L63:
            r0 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L63
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.os.MessageQueue.removeMessages(android.os.Handler,"
                    + " int, java.lang.Object):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0031, code lost:

       r4.mLast = r4.mMessages;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void removeEqualMessages(android.os.Handler r5, int r6, java.lang.Object r7) {
        /*
            r4 = this;
            if (r5 != 0) goto L3
            return
        L3:
            monitor-enter(r4)
            android.os.Message r0 = r4.mMessages     // Catch: java.lang.Throwable -> L6b
        L6:
            if (r0 == 0) goto L2f
            android.os.Handler r1 = r0.target     // Catch: java.lang.Throwable -> L6b
            if (r1 != r5) goto L2f
            int r1 = r0.what     // Catch: java.lang.Throwable -> L6b
            if (r1 != r6) goto L2f
            if (r7 == 0) goto L1a
            java.lang.Object r1 = r0.obj     // Catch: java.lang.Throwable -> L6b
            boolean r1 = r7.equals(r1)     // Catch: java.lang.Throwable -> L6b
            if (r1 == 0) goto L2f
        L1a:
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L6b
            r4.mMessages = r1     // Catch: java.lang.Throwable -> L6b
            boolean r2 = r0.isAsynchronous()     // Catch: java.lang.Throwable -> L6b
            if (r2 == 0) goto L2a
            int r2 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L6b
            int r2 = r2 + (-1)
            r4.mAsyncMessageCount = r2     // Catch: java.lang.Throwable -> L6b
        L2a:
            r0.recycleUnchecked()     // Catch: java.lang.Throwable -> L6b
            r0 = r1
            goto L6
        L2f:
            if (r0 != 0) goto L35
            android.os.Message r1 = r4.mMessages     // Catch: java.lang.Throwable -> L6b
            r4.mLast = r1     // Catch: java.lang.Throwable -> L6b
        L35:
            if (r0 == 0) goto L69
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L6b
            if (r1 == 0) goto L67
            android.os.Handler r2 = r1.target     // Catch: java.lang.Throwable -> L6b
            if (r2 != r5) goto L67
            int r2 = r1.what     // Catch: java.lang.Throwable -> L6b
            if (r2 != r6) goto L67
            if (r7 == 0) goto L4d
            java.lang.Object r2 = r1.obj     // Catch: java.lang.Throwable -> L6b
            boolean r2 = r7.equals(r2)     // Catch: java.lang.Throwable -> L6b
            if (r2 == 0) goto L67
        L4d:
            android.os.Message r2 = r1.next     // Catch: java.lang.Throwable -> L6b
            boolean r3 = r1.isAsynchronous()     // Catch: java.lang.Throwable -> L6b
            if (r3 == 0) goto L5b
            int r3 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L6b
            int r3 = r3 + (-1)
            r4.mAsyncMessageCount = r3     // Catch: java.lang.Throwable -> L6b
        L5b:
            r1.recycleUnchecked()     // Catch: java.lang.Throwable -> L6b
            r0.next = r2     // Catch: java.lang.Throwable -> L6b
            android.os.Message r3 = r0.next     // Catch: java.lang.Throwable -> L6b
            if (r3 != 0) goto L35
            r4.mLast = r0     // Catch: java.lang.Throwable -> L6b
            goto L35
        L67:
            r0 = r1
            goto L35
        L69:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L6b
            return
        L6b:
            r0 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L6b
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.os.MessageQueue.removeEqualMessages(android.os.Handler, int,"
                    + " java.lang.Object):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x002f, code lost:

       r4.mLast = r4.mMessages;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void removeMessages(android.os.Handler r5, java.lang.Runnable r6, java.lang.Object r7) {
        /*
            r4 = this;
            if (r5 == 0) goto L68
            if (r6 != 0) goto L5
            goto L68
        L5:
            monitor-enter(r4)
            android.os.Message r0 = r4.mMessages     // Catch: java.lang.Throwable -> L65
        L8:
            if (r0 == 0) goto L2d
            android.os.Handler r1 = r0.target     // Catch: java.lang.Throwable -> L65
            if (r1 != r5) goto L2d
            java.lang.Runnable r1 = r0.callback     // Catch: java.lang.Throwable -> L65
            if (r1 != r6) goto L2d
            if (r7 == 0) goto L18
            java.lang.Object r1 = r0.obj     // Catch: java.lang.Throwable -> L65
            if (r1 != r7) goto L2d
        L18:
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L65
            r4.mMessages = r1     // Catch: java.lang.Throwable -> L65
            boolean r2 = r0.isAsynchronous()     // Catch: java.lang.Throwable -> L65
            if (r2 == 0) goto L28
            int r2 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L65
            int r2 = r2 + (-1)
            r4.mAsyncMessageCount = r2     // Catch: java.lang.Throwable -> L65
        L28:
            r0.recycleUnchecked()     // Catch: java.lang.Throwable -> L65
            r0 = r1
            goto L8
        L2d:
            if (r0 != 0) goto L33
            android.os.Message r1 = r4.mMessages     // Catch: java.lang.Throwable -> L65
            r4.mLast = r1     // Catch: java.lang.Throwable -> L65
        L33:
            if (r0 == 0) goto L63
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L65
            if (r1 == 0) goto L61
            android.os.Handler r2 = r1.target     // Catch: java.lang.Throwable -> L65
            if (r2 != r5) goto L61
            java.lang.Runnable r2 = r1.callback     // Catch: java.lang.Throwable -> L65
            if (r2 != r6) goto L61
            if (r7 == 0) goto L47
            java.lang.Object r2 = r1.obj     // Catch: java.lang.Throwable -> L65
            if (r2 != r7) goto L61
        L47:
            android.os.Message r2 = r1.next     // Catch: java.lang.Throwable -> L65
            boolean r3 = r1.isAsynchronous()     // Catch: java.lang.Throwable -> L65
            if (r3 == 0) goto L55
            int r3 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L65
            int r3 = r3 + (-1)
            r4.mAsyncMessageCount = r3     // Catch: java.lang.Throwable -> L65
        L55:
            r1.recycleUnchecked()     // Catch: java.lang.Throwable -> L65
            r0.next = r2     // Catch: java.lang.Throwable -> L65
            android.os.Message r3 = r0.next     // Catch: java.lang.Throwable -> L65
            if (r3 != 0) goto L33
            r4.mLast = r0     // Catch: java.lang.Throwable -> L65
            goto L33
        L61:
            r0 = r1
            goto L33
        L63:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L65
            return
        L65:
            r0 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L65
            throw r0
        L68:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.os.MessageQueue.removeMessages(android.os.Handler,"
                    + " java.lang.Runnable, java.lang.Object):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0034, code lost:

       r4.mLast = r4.mMessages;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void removeEqualMessages(android.os.Handler r5, java.lang.Runnable r6, java.lang.Object r7) {
        /*
            r4 = this;
            if (r5 == 0) goto L71
            if (r6 != 0) goto L6
            goto L71
        L6:
            monitor-enter(r4)
            android.os.Message r0 = r4.mMessages     // Catch: java.lang.Throwable -> L6e
        L9:
            if (r0 == 0) goto L32
            android.os.Handler r1 = r0.target     // Catch: java.lang.Throwable -> L6e
            if (r1 != r5) goto L32
            java.lang.Runnable r1 = r0.callback     // Catch: java.lang.Throwable -> L6e
            if (r1 != r6) goto L32
            if (r7 == 0) goto L1d
            java.lang.Object r1 = r0.obj     // Catch: java.lang.Throwable -> L6e
            boolean r1 = r7.equals(r1)     // Catch: java.lang.Throwable -> L6e
            if (r1 == 0) goto L32
        L1d:
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L6e
            r4.mMessages = r1     // Catch: java.lang.Throwable -> L6e
            boolean r2 = r0.isAsynchronous()     // Catch: java.lang.Throwable -> L6e
            if (r2 == 0) goto L2d
            int r2 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L6e
            int r2 = r2 + (-1)
            r4.mAsyncMessageCount = r2     // Catch: java.lang.Throwable -> L6e
        L2d:
            r0.recycleUnchecked()     // Catch: java.lang.Throwable -> L6e
            r0 = r1
            goto L9
        L32:
            if (r0 != 0) goto L38
            android.os.Message r1 = r4.mMessages     // Catch: java.lang.Throwable -> L6e
            r4.mLast = r1     // Catch: java.lang.Throwable -> L6e
        L38:
            if (r0 == 0) goto L6c
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L6e
            if (r1 == 0) goto L6a
            android.os.Handler r2 = r1.target     // Catch: java.lang.Throwable -> L6e
            if (r2 != r5) goto L6a
            java.lang.Runnable r2 = r1.callback     // Catch: java.lang.Throwable -> L6e
            if (r2 != r6) goto L6a
            if (r7 == 0) goto L50
            java.lang.Object r2 = r1.obj     // Catch: java.lang.Throwable -> L6e
            boolean r2 = r7.equals(r2)     // Catch: java.lang.Throwable -> L6e
            if (r2 == 0) goto L6a
        L50:
            android.os.Message r2 = r1.next     // Catch: java.lang.Throwable -> L6e
            boolean r3 = r1.isAsynchronous()     // Catch: java.lang.Throwable -> L6e
            if (r3 == 0) goto L5e
            int r3 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L6e
            int r3 = r3 + (-1)
            r4.mAsyncMessageCount = r3     // Catch: java.lang.Throwable -> L6e
        L5e:
            r1.recycleUnchecked()     // Catch: java.lang.Throwable -> L6e
            r0.next = r2     // Catch: java.lang.Throwable -> L6e
            android.os.Message r3 = r0.next     // Catch: java.lang.Throwable -> L6e
            if (r3 != 0) goto L38
            r4.mLast = r0     // Catch: java.lang.Throwable -> L6e
            goto L38
        L6a:
            r0 = r1
            goto L38
        L6c:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L6e
            return
        L6e:
            r0 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L6e
            throw r0
        L71:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.os.MessageQueue.removeEqualMessages(android.os.Handler,"
                    + " java.lang.Runnable, java.lang.Object):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0029, code lost:

       r4.mLast = r4.mMessages;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void removeCallbacksAndMessages(android.os.Handler r5, java.lang.Object r6) {
        /*
            r4 = this;
            if (r5 != 0) goto L3
            return
        L3:
            monitor-enter(r4)
            android.os.Message r0 = r4.mMessages     // Catch: java.lang.Throwable -> L5b
        L6:
            if (r0 == 0) goto L27
            android.os.Handler r1 = r0.target     // Catch: java.lang.Throwable -> L5b
            if (r1 != r5) goto L27
            if (r6 == 0) goto L12
            java.lang.Object r1 = r0.obj     // Catch: java.lang.Throwable -> L5b
            if (r1 != r6) goto L27
        L12:
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L5b
            r4.mMessages = r1     // Catch: java.lang.Throwable -> L5b
            boolean r2 = r0.isAsynchronous()     // Catch: java.lang.Throwable -> L5b
            if (r2 == 0) goto L22
            int r2 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L5b
            int r2 = r2 + (-1)
            r4.mAsyncMessageCount = r2     // Catch: java.lang.Throwable -> L5b
        L22:
            r0.recycleUnchecked()     // Catch: java.lang.Throwable -> L5b
            r0 = r1
            goto L6
        L27:
            if (r0 != 0) goto L2d
            android.os.Message r1 = r4.mMessages     // Catch: java.lang.Throwable -> L5b
            r4.mLast = r1     // Catch: java.lang.Throwable -> L5b
        L2d:
            if (r0 == 0) goto L59
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L5b
            if (r1 == 0) goto L57
            android.os.Handler r2 = r1.target     // Catch: java.lang.Throwable -> L5b
            if (r2 != r5) goto L57
            if (r6 == 0) goto L3d
            java.lang.Object r2 = r1.obj     // Catch: java.lang.Throwable -> L5b
            if (r2 != r6) goto L57
        L3d:
            android.os.Message r2 = r1.next     // Catch: java.lang.Throwable -> L5b
            boolean r3 = r1.isAsynchronous()     // Catch: java.lang.Throwable -> L5b
            if (r3 == 0) goto L4b
            int r3 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L5b
            int r3 = r3 + (-1)
            r4.mAsyncMessageCount = r3     // Catch: java.lang.Throwable -> L5b
        L4b:
            r1.recycleUnchecked()     // Catch: java.lang.Throwable -> L5b
            r0.next = r2     // Catch: java.lang.Throwable -> L5b
            android.os.Message r3 = r0.next     // Catch: java.lang.Throwable -> L5b
            if (r3 != 0) goto L2d
            r4.mLast = r0     // Catch: java.lang.Throwable -> L5b
            goto L2d
        L57:
            r0 = r1
            goto L2d
        L59:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L5b
            return
        L5b:
            r0 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L5b
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.os.MessageQueue.removeCallbacksAndMessages(android.os.Handler,"
                    + " java.lang.Object):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x002d, code lost:

       r4.mLast = r4.mMessages;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void removeCallbacksAndEqualMessages(android.os.Handler r5, java.lang.Object r6) {
        /*
            r4 = this;
            if (r5 != 0) goto L3
            return
        L3:
            monitor-enter(r4)
            android.os.Message r0 = r4.mMessages     // Catch: java.lang.Throwable -> L63
        L6:
            if (r0 == 0) goto L2b
            android.os.Handler r1 = r0.target     // Catch: java.lang.Throwable -> L63
            if (r1 != r5) goto L2b
            if (r6 == 0) goto L16
            java.lang.Object r1 = r0.obj     // Catch: java.lang.Throwable -> L63
            boolean r1 = r6.equals(r1)     // Catch: java.lang.Throwable -> L63
            if (r1 == 0) goto L2b
        L16:
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L63
            r4.mMessages = r1     // Catch: java.lang.Throwable -> L63
            boolean r2 = r0.isAsynchronous()     // Catch: java.lang.Throwable -> L63
            if (r2 == 0) goto L26
            int r2 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L63
            int r2 = r2 + (-1)
            r4.mAsyncMessageCount = r2     // Catch: java.lang.Throwable -> L63
        L26:
            r0.recycleUnchecked()     // Catch: java.lang.Throwable -> L63
            r0 = r1
            goto L6
        L2b:
            if (r0 != 0) goto L31
            android.os.Message r1 = r4.mMessages     // Catch: java.lang.Throwable -> L63
            r4.mLast = r1     // Catch: java.lang.Throwable -> L63
        L31:
            if (r0 == 0) goto L61
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L63
            if (r1 == 0) goto L5f
            android.os.Handler r2 = r1.target     // Catch: java.lang.Throwable -> L63
            if (r2 != r5) goto L5f
            if (r6 == 0) goto L45
            java.lang.Object r2 = r1.obj     // Catch: java.lang.Throwable -> L63
            boolean r2 = r6.equals(r2)     // Catch: java.lang.Throwable -> L63
            if (r2 == 0) goto L5f
        L45:
            android.os.Message r2 = r1.next     // Catch: java.lang.Throwable -> L63
            boolean r3 = r1.isAsynchronous()     // Catch: java.lang.Throwable -> L63
            if (r3 == 0) goto L53
            int r3 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L63
            int r3 = r3 + (-1)
            r4.mAsyncMessageCount = r3     // Catch: java.lang.Throwable -> L63
        L53:
            r1.recycleUnchecked()     // Catch: java.lang.Throwable -> L63
            r0.next = r2     // Catch: java.lang.Throwable -> L63
            android.os.Message r3 = r0.next     // Catch: java.lang.Throwable -> L63
            if (r3 != 0) goto L31
            r4.mLast = r0     // Catch: java.lang.Throwable -> L63
            goto L31
        L5f:
            r0 = r1
            goto L31
        L61:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L63
            return
        L63:
            r0 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L63
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.os.MessageQueue.removeCallbacksAndEqualMessages(android.os.Handler,"
                    + " java.lang.Object):void");
    }

    private void removeAllMessagesLocked() {
        Message p = this.mMessages;
        while (p != null) {
            Message n = p.next;
            p.recycleUnchecked();
            p = n;
        }
        this.mMessages = null;
        this.mLast = null;
        this.mAsyncMessageCount = 0;
    }

    private void removeAllFutureMessagesLocked() {
        long now = SystemClock.uptimeMillis();
        Message p = this.mMessages;
        if (p == null) {
            return;
        }
        if (p.when > now) {
            removeAllMessagesLocked();
            return;
        }
        while (true) {
            Message n = p.next;
            if (n == null) {
                return;
            }
            if (n.when <= now) {
                p = n;
            } else {
                p.next = null;
                this.mLast = p;
                do {
                    Message p2 = n;
                    n = p2.next;
                    if (p2.isAsynchronous()) {
                        this.mAsyncMessageCount--;
                    }
                    p2.recycleUnchecked();
                } while (n != null);
                return;
            }
        }
    }

    void dump(Printer pw, String prefix, Handler h) {
        synchronized (this) {
            long now = SystemClock.uptimeMillis();
            int n = 0;
            for (Message msg = this.mMessages; msg != null; msg = msg.next) {
                if (h == null || h == msg.target) {
                    pw.println(prefix + "Message " + n + ": " + msg.toString(now));
                }
                n++;
            }
            pw.println(
                    prefix
                            + "(Total messages: "
                            + n
                            + ", polling="
                            + isPollingLocked()
                            + ", quitting="
                            + this.mQuitting
                            + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long messageQueueToken = proto.start(fieldId);
        synchronized (this) {
            for (Message msg = this.mMessages; msg != null; msg = msg.next) {
                msg.dumpDebug(proto, 2246267895809L);
            }
            proto.write(1133871366146L, isPollingLocked());
            proto.write(1133871366147L, this.mQuitting);
        }
        proto.end(messageQueueToken);
    }

    private static final class FileDescriptorRecord {
        public final FileDescriptor mDescriptor;
        public int mEvents;
        public OnFileDescriptorEventListener mListener;
        public int mSeq;

        public FileDescriptorRecord(
                FileDescriptor descriptor, int events, OnFileDescriptorEventListener listener) {
            this.mDescriptor = descriptor;
            this.mEvents = events;
            this.mListener = listener;
        }
    }
}
