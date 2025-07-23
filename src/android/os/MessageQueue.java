package android.os;

import android.app.ActivityThread;
import android.app.Instrumentation;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import android.util.Printer;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.samsung.android.common.AsPackageName;
import dalvik.annotation.optimization.NeverCompile;
import java.io.FileDescriptor;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes3.dex */
public final class MessageQueue {
    private static final boolean DEBUG = false;
    private static final int STACK_NODE_ACTIVE = 1;
    private static final int STACK_NODE_MESSAGE = 0;
    private static final int STACK_NODE_PARKED = 2;
    private static final int STACK_NODE_TIMEDPARK = 3;
    private static final String TAG_C = "ConcurrentMessageQueue";
    private static final String TAG_L = "LegacyMessageQueue";
    private static final boolean TRACE = false;
    private static Boolean sIsProcessAllowedToUseConcurrent;
    private static final VarHandle sNextFrontInsertSeq;
    private static final VarHandle sNextInsertSeq;
    private static final VarHandle sQuitting;
    private static final VarHandle sState;
    private int mAsyncMessageCount;
    private boolean mBlocked;
    private final Condition mDrainCompleted;
    private final ReentrantLock mDrainingLock;
    private SparseArray<FileDescriptorRecord> mFileDescriptorRecords;
    private Message mLast;
    private final MatchAllFutureMessages mMatchAllFutureMessages;
    private final MatchAllMessages mMatchAllMessages;
    private final MatchDeliverableMessages mMatchDeliverableMessages;
    private final MatchHandler mMatchHandler;
    private final MatchHandlerAndObject mMatchHandlerAndObject;
    private final MatchHandlerAndObjectEquals mMatchHandlerAndObjectEquals;
    private final MatchHandlerRunnableAndObject mMatchHandlerRunnableAndObject;
    private final MatchHandlerRunnableAndObjectEquals mMatchHandlerRunnableAndObjectEquals;
    private final MatchHandlerWhatAndObject mMatchHandlerWhatAndObject;
    private final MatchHandlerWhatAndObjectEquals mMatchHandlerWhatAndObjectEquals;
    private final MessageCounts mMessageCounts;
    private boolean mMessageDirectlyQueued;
    Message mMessages;
    private int mNextBarrierToken;
    private boolean mNextIsDrainingStack;
    private int mNextPollTimeoutMillis;
    private IdleHandler[] mPendingIdleHandlers;
    private long mPtr;
    private final boolean mQuitAllowed;
    private boolean mQuitting;
    private final String mThreadName;
    private final long mTid;
    private final boolean mUseConcurrent;
    private static final AtomicLong mMessagesDelivered = new AtomicLong();
    private static final StateNode sStackStateActive = new StateNode(1);
    private static final StateNode sStackStateParked = new StateNode(2);
    private final ArrayList<IdleHandler> mIdleHandlers = new ArrayList<>();
    private final AtomicLong mMessageCount = new AtomicLong();
    private final TimedParkStateNode mStackStateTimedPark = new TimedParkStateNode();
    private volatile StackNode mStateValue = sStackStateParked;
    private final ConcurrentSkipListSet<MessageNode> mPriorityQueue = new ConcurrentSkipListSet<>();
    private final ConcurrentSkipListSet<MessageNode> mAsyncPriorityQueue = new ConcurrentSkipListSet<>();
    private volatile long mNextInsertSeqValue = 0;
    private volatile long mNextFrontInsertSeqValue = -1;
    private final Object mIdleHandlersLock = new Object();
    private final Object mFileDescriptorRecordsLock = new Object();
    private boolean mQuittingValue = false;
    private final AtomicInteger mNextBarrierTokenAtomic = new AtomicInteger(1);

    public interface IdleHandler {
        boolean queueIdle();
    }

    public interface OnFileDescriptorEventListener {
        public static final int EVENT_ERROR = 4;
        public static final int EVENT_INPUT = 1;
        public static final int EVENT_OUTPUT = 2;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Events {
        }

        int onFileDescriptorEvents(FileDescriptor fileDescriptor, int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface StackNodeType {
    }

    private static native void nativeDestroy(long j);

    private static native long nativeInit();

    private static native boolean nativeIsPolling(long j);

    private native void nativePollOnce(long j, int i);

    private static native void nativeSetFileDescriptorEvents(long j, int i, int i2);

    private static native void nativeWake(long j);

    private static void throwIfNotTest$ravenwood() {
    }

    static {
        try {
            sState = MethodHandles.lookup().findVarHandle(MessageQueue.class, "mStateValue", StackNode.class);
            try {
                MethodHandles.Lookup lookup = MethodHandles.lookup();
                sNextInsertSeq = lookup.findVarHandle(MessageQueue.class, "mNextInsertSeqValue", Long.TYPE);
                sNextFrontInsertSeq = lookup.findVarHandle(MessageQueue.class, "mNextFrontInsertSeqValue", Long.TYPE);
                try {
                    sQuitting = MethodHandles.lookup().findVarHandle(MessageQueue.class, "mQuittingValue", Boolean.TYPE);
                } catch (Exception e) {
                    Log.wtf(TAG_C, "VarHandle lookup failed with exception: " + e);
                    throw new ExceptionInInitializerError(e);
                }
            } catch (Exception e2) {
                Log.wtf(TAG_C, "VarHandle lookup failed with exception: " + e2);
                throw new ExceptionInInitializerError(e2);
            }
        } catch (Exception e3) {
            Log.wtf(TAG_C, "VarHandle lookup failed with exception: " + e3);
            throw new ExceptionInInitializerError(e3);
        }
    }

    MessageQueue(boolean z) {
        this.mMatchDeliverableMessages = new MatchDeliverableMessages();
        this.mMatchHandlerWhatAndObject = new MatchHandlerWhatAndObject();
        this.mMatchHandlerWhatAndObjectEquals = new MatchHandlerWhatAndObjectEquals();
        this.mMatchHandlerRunnableAndObject = new MatchHandlerRunnableAndObject();
        this.mMatchHandler = new MatchHandler();
        this.mMatchHandlerRunnableAndObjectEquals = new MatchHandlerRunnableAndObjectEquals();
        this.mMatchHandlerAndObject = new MatchHandlerAndObject();
        this.mMatchHandlerAndObjectEquals = new MatchHandlerAndObjectEquals();
        this.mMatchAllMessages = new MatchAllMessages();
        this.mMatchAllFutureMessages = new MatchAllFutureMessages();
        this.mMessageCounts = new MessageCounts();
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mDrainingLock = reentrantLock;
        this.mNextIsDrainingStack = false;
        this.mDrainCompleted = reentrantLock.newCondition();
        initIsProcessAllowedToUseConcurrent();
        this.mUseConcurrent = sIsProcessAllowedToUseConcurrent.booleanValue();
        this.mQuitAllowed = z;
        this.mPtr = nativeInit();
        this.mThreadName = Thread.currentThread().getName();
        this.mTid = Process.myTid();
    }

    private static void initIsProcessAllowedToUseConcurrent() {
        if (sIsProcessAllowedToUseConcurrent != null) {
            return;
        }
        if (Flags.messageQueueForceLegacy()) {
            sIsProcessAllowedToUseConcurrent = false;
            return;
        }
        if (Flags.forceConcurrentMessageQueue()) {
            try {
                Class.forName("org.robolectric.Robolectric");
            } catch (ClassNotFoundException unused) {
                sIsProcessAllowedToUseConcurrent = true;
                return;
            }
        }
        String myProcessName = Process.myProcessName();
        if (myProcessName == null) {
            sIsProcessAllowedToUseConcurrent = false;
            return;
        }
        Boolean valueOf = Boolean.valueOf(UserHandle.isCore(Process.myUid()));
        sIsProcessAllowedToUseConcurrent = valueOf;
        if (valueOf.booleanValue()) {
            if (myProcessName.contains("test") || myProcessName.contains("Test")) {
                sIsProcessAllowedToUseConcurrent = false;
                return;
            }
            return;
        }
        sIsProcessAllowedToUseConcurrent = Boolean.valueOf(myProcessName.equals(AsPackageName.SYSTEMUI) || myProcessName.startsWith("com.android.systemui:"));
    }

    private static void throwIfNotTest() {
        Instrumentation instrumentation;
        ActivityThread currentActivityThread = ActivityThread.currentActivityThread();
        if (currentActivityThread != null && (instrumentation = currentActivityThread.getInstrumentation()) != null && !instrumentation.isInstrumenting()) {
            throw new IllegalStateException("Test-only API called not from a test!");
        }
    }

    protected void finalize() throws Throwable {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    private void decAndTraceMessageCount() {
        this.mMessageCount.decrementAndGet();
        traceMessageCount();
    }

    private void incAndTraceMessageCount(Message message, long j) {
        this.mMessageCount.incrementAndGet();
        message.mSendingThreadName = Thread.currentThread().getName();
        message.mEventId.set(PerfettoTrace.getFlowId());
        traceMessageCount();
        PerfettoTrace.instant(PerfettoTrace.MQ_CATEGORY, "message_queue_send").setFlow(message.mEventId.get()).beginProto().beginNested(2004L).addField(2L, this.mThreadName).addField(3L, message.what).addField(4L, j - SystemClock.uptimeMillis()).endNested().endProto().emit();
    }

    private void traceMessageCount() {
        PerfettoTrace.counter(PerfettoTrace.MQ_CATEGORY, this.mMessageCount.get()).usingThreadCounterTrack(this.mTid, this.mThreadName).emit();
    }

    private void dispose() {
        long j = this.mPtr;
        if (j != 0) {
            nativeDestroy(j);
            this.mPtr = 0L;
        }
    }

    private static final class MatchDeliverableMessages extends MessageCompare {
        private MatchDeliverableMessages() {
            super();
        }

        @Override // android.os.MessageQueue.MessageCompare
        public boolean compareMessage(MessageNode messageNode, Handler handler, int i, Object obj, Runnable runnable, long j) {
            return messageNode.mMessage.when <= j;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x002f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean isIdleConcurrent() {
        /*
            r9 = this;
            long r5 = android.os.SystemClock.uptimeMillis()
            android.os.MessageQueue$MatchDeliverableMessages r7 = r9.mMatchDeliverableMessages
            r8 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r0 = r9
            boolean r9 = r0.stackHasMessages(r1, r2, r3, r4, r5, r7, r8)
            r1 = 0
            if (r9 == 0) goto L14
            return r1
        L14:
            java.util.concurrent.ConcurrentSkipListSet<android.os.MessageQueue$MessageNode> r9 = r0.mPriorityQueue
            boolean r9 = r9.isEmpty()
            r2 = 0
            if (r9 != 0) goto L26
            java.util.concurrent.ConcurrentSkipListSet<android.os.MessageQueue$MessageNode> r9 = r0.mPriorityQueue     // Catch: java.util.NoSuchElementException -> L26
            java.lang.Object r9 = r9.first()     // Catch: java.util.NoSuchElementException -> L26
            android.os.MessageQueue$MessageNode r9 = (android.os.MessageQueue.MessageNode) r9     // Catch: java.util.NoSuchElementException -> L26
            goto L27
        L26:
            r9 = r2
        L27:
            java.util.concurrent.ConcurrentSkipListSet<android.os.MessageQueue$MessageNode> r3 = r0.mAsyncPriorityQueue
            boolean r3 = r3.isEmpty()
            if (r3 != 0) goto L38
            java.util.concurrent.ConcurrentSkipListSet<android.os.MessageQueue$MessageNode> r0 = r0.mAsyncPriorityQueue     // Catch: java.util.NoSuchElementException -> L38
            java.lang.Object r0 = r0.first()     // Catch: java.util.NoSuchElementException -> L38
            android.os.MessageQueue$MessageNode r0 = (android.os.MessageQueue.MessageNode) r0     // Catch: java.util.NoSuchElementException -> L38
            r2 = r0
        L38:
            if (r9 == 0) goto L42
            long r3 = r9.getWhen()
            int r9 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r9 <= 0) goto L4c
        L42:
            if (r2 == 0) goto L4d
            long r2 = r2.getWhen()
            int r9 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r9 > 0) goto L4d
        L4c:
            return r1
        L4d:
            r9 = 1
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.MessageQueue.isIdleConcurrent():boolean");
    }

    private boolean isIdleLegacy() {
        boolean z;
        synchronized (this) {
            long uptimeMillis = SystemClock.uptimeMillis();
            Message message = this.mMessages;
            z = message == null || uptimeMillis < message.when;
        }
        return z;
    }

    public boolean isIdle() {
        if (this.mUseConcurrent) {
            return isIdleConcurrent();
        }
        return isIdleLegacy();
    }

    private void addIdleHandlerConcurrent(IdleHandler idleHandler) {
        synchronized (this.mIdleHandlersLock) {
            this.mIdleHandlers.add(idleHandler);
        }
    }

    private void addIdleHandlerLegacy(IdleHandler idleHandler) {
        synchronized (this) {
            this.mIdleHandlers.add(idleHandler);
        }
    }

    public void addIdleHandler(IdleHandler idleHandler) {
        if (idleHandler == null) {
            throw new NullPointerException("Can't add a null IdleHandler");
        }
        if (this.mUseConcurrent) {
            addIdleHandlerConcurrent(idleHandler);
        } else {
            addIdleHandlerLegacy(idleHandler);
        }
    }

    private void removeIdleHandlerConcurrent(IdleHandler idleHandler) {
        synchronized (this.mIdleHandlersLock) {
            this.mIdleHandlers.remove(idleHandler);
        }
    }

    private void removeIdleHandlerLegacy(IdleHandler idleHandler) {
        synchronized (this) {
            this.mIdleHandlers.remove(idleHandler);
        }
    }

    public void removeIdleHandler(IdleHandler idleHandler) {
        if (this.mUseConcurrent) {
            removeIdleHandlerConcurrent(idleHandler);
        } else {
            removeIdleHandlerLegacy(idleHandler);
        }
    }

    private boolean isPollingConcurrent() {
        return !(boolean) sQuitting.getVolatile(this) && nativeIsPolling(this.mPtr);
    }

    private boolean isPollingLegacy() {
        boolean isPollingLocked;
        synchronized (this) {
            isPollingLocked = isPollingLocked();
        }
        return isPollingLocked;
    }

    public boolean isPolling() {
        if (this.mUseConcurrent) {
            return isPollingConcurrent();
        }
        return isPollingLegacy();
    }

    private boolean isPollingLocked() {
        return !this.mQuitting && nativeIsPolling(this.mPtr);
    }

    private void addOnFileDescriptorEventListenerConcurrent(FileDescriptor fileDescriptor, int i, OnFileDescriptorEventListener onFileDescriptorEventListener) {
        synchronized (this.mFileDescriptorRecordsLock) {
            updateOnFileDescriptorEventListenerLocked(fileDescriptor, i, onFileDescriptorEventListener);
        }
    }

    private void addOnFileDescriptorEventListenerLegacy(FileDescriptor fileDescriptor, int i, OnFileDescriptorEventListener onFileDescriptorEventListener) {
        synchronized (this) {
            updateOnFileDescriptorEventListenerLocked(fileDescriptor, i, onFileDescriptorEventListener);
        }
    }

    public void addOnFileDescriptorEventListener(FileDescriptor fileDescriptor, int i, OnFileDescriptorEventListener onFileDescriptorEventListener) {
        if (fileDescriptor == null) {
            throw new IllegalArgumentException("fd must not be null");
        }
        if (onFileDescriptorEventListener == null) {
            throw new IllegalArgumentException("listener must not be null");
        }
        if (this.mUseConcurrent) {
            addOnFileDescriptorEventListenerConcurrent(fileDescriptor, i, onFileDescriptorEventListener);
        } else {
            addOnFileDescriptorEventListenerLegacy(fileDescriptor, i, onFileDescriptorEventListener);
        }
    }

    private void removeOnFileDescriptorEventListenerConcurrent(FileDescriptor fileDescriptor) {
        synchronized (this.mFileDescriptorRecordsLock) {
            updateOnFileDescriptorEventListenerLocked(fileDescriptor, 0, null);
        }
    }

    private void removeOnFileDescriptorEventListenerLegacy(FileDescriptor fileDescriptor) {
        synchronized (this) {
            updateOnFileDescriptorEventListenerLocked(fileDescriptor, 0, null);
        }
    }

    public void removeOnFileDescriptorEventListener(FileDescriptor fileDescriptor) {
        if (fileDescriptor == null) {
            throw new IllegalArgumentException("fd must not be null");
        }
        if (this.mUseConcurrent) {
            removeOnFileDescriptorEventListenerConcurrent(fileDescriptor);
        } else {
            removeOnFileDescriptorEventListenerLegacy(fileDescriptor);
        }
    }

    private void updateOnFileDescriptorEventListenerLocked(FileDescriptor fileDescriptor, int i, OnFileDescriptorEventListener onFileDescriptorEventListener) {
        int i2;
        int int$ = fileDescriptor.getInt$();
        SparseArray<FileDescriptorRecord> sparseArray = this.mFileDescriptorRecords;
        FileDescriptorRecord fileDescriptorRecord = null;
        if (sparseArray != null) {
            i2 = sparseArray.indexOfKey(int$);
            if (i2 >= 0 && (fileDescriptorRecord = this.mFileDescriptorRecords.valueAt(i2)) != null && fileDescriptorRecord.mEvents == i) {
                return;
            }
        } else {
            i2 = -1;
        }
        if (i == 0) {
            if (fileDescriptorRecord != null) {
                fileDescriptorRecord.mEvents = 0;
                this.mFileDescriptorRecords.removeAt(i2);
                nativeSetFileDescriptorEvents(this.mPtr, int$, 0);
                return;
            }
            return;
        }
        int i3 = i | 4;
        if (fileDescriptorRecord == null) {
            if (this.mFileDescriptorRecords == null) {
                this.mFileDescriptorRecords = new SparseArray<>();
            }
            this.mFileDescriptorRecords.put(int$, new FileDescriptorRecord(fileDescriptor, i3, onFileDescriptorEventListener));
        } else {
            fileDescriptorRecord.mListener = onFileDescriptorEventListener;
            fileDescriptorRecord.mEvents = i3;
            fileDescriptorRecord.mSeq++;
        }
        nativeSetFileDescriptorEvents(this.mPtr, int$, i3);
    }

    private int dispatchEvents(int i, int i2) {
        FileDescriptorRecord fileDescriptorRecord;
        int i3;
        int i4;
        OnFileDescriptorEventListener onFileDescriptorEventListener;
        int i5;
        if (this.mUseConcurrent) {
            synchronized (this.mFileDescriptorRecordsLock) {
                fileDescriptorRecord = this.mFileDescriptorRecords.get(i);
                if (fileDescriptorRecord == null) {
                    return 0;
                }
                i3 = fileDescriptorRecord.mEvents;
                i4 = i2 & i3;
                if (i4 == 0) {
                    return i3;
                }
                onFileDescriptorEventListener = fileDescriptorRecord.mListener;
                i5 = fileDescriptorRecord.mSeq;
            }
        } else {
            synchronized (this) {
                fileDescriptorRecord = this.mFileDescriptorRecords.get(i);
                if (fileDescriptorRecord == null) {
                    return 0;
                }
                i3 = fileDescriptorRecord.mEvents;
                i4 = i2 & i3;
                if (i4 == 0) {
                    return i3;
                }
                onFileDescriptorEventListener = fileDescriptorRecord.mListener;
                i5 = fileDescriptorRecord.mSeq;
            }
        }
        int onFileDescriptorEvents = onFileDescriptorEventListener.onFileDescriptorEvents(fileDescriptorRecord.mDescriptor, i4);
        if (onFileDescriptorEvents != 0) {
            onFileDescriptorEvents |= 4;
        }
        if (onFileDescriptorEvents == i3) {
            return onFileDescriptorEvents;
        }
        if (this.mUseConcurrent) {
            synchronized (this.mFileDescriptorRecordsLock) {
                int indexOfKey = this.mFileDescriptorRecords.indexOfKey(i);
                if (indexOfKey >= 0 && this.mFileDescriptorRecords.valueAt(indexOfKey) == fileDescriptorRecord && fileDescriptorRecord.mSeq == i5) {
                    fileDescriptorRecord.mEvents = onFileDescriptorEvents;
                    if (onFileDescriptorEvents == 0) {
                        this.mFileDescriptorRecords.removeAt(indexOfKey);
                    }
                }
            }
            return onFileDescriptorEvents;
        }
        synchronized (this) {
            int indexOfKey2 = this.mFileDescriptorRecords.indexOfKey(i);
            if (indexOfKey2 >= 0 && this.mFileDescriptorRecords.valueAt(indexOfKey2) == fileDescriptorRecord && fileDescriptorRecord.mSeq == i5) {
                fileDescriptorRecord.mEvents = onFileDescriptorEvents;
                if (onFileDescriptorEvents == 0) {
                    this.mFileDescriptorRecords.removeAt(indexOfKey2);
                }
            }
        }
        return onFileDescriptorEvents;
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x0065, code lost:
    
        if (r3 < r2.getWhen()) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0051, code lost:
    
        if (r3 >= r2.getWhen()) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.os.Message nextMessage(boolean r10, boolean r11) {
        /*
            r9 = this;
        L0:
            java.util.concurrent.locks.ReentrantLock r0 = r9.mDrainingLock
            r0.lock()
            r0 = 1
            r9.mNextIsDrainingStack = r0
            java.util.concurrent.locks.ReentrantLock r0 = r9.mDrainingLock
            r0.unlock()
            android.os.MessageQueue$StackNode r0 = r9.swapAndSetStackStateActive()
            r9.drainStack(r0)
            java.util.concurrent.locks.ReentrantLock r0 = r9.mDrainingLock
            r0.lock()
            r0 = 0
            r9.mNextIsDrainingStack = r0
            java.util.concurrent.locks.Condition r1 = r9.mDrainCompleted
            r1.signalAll()
            java.util.concurrent.locks.ReentrantLock r1 = r9.mDrainingLock
            r1.unlock()
            java.util.concurrent.ConcurrentSkipListSet<android.os.MessageQueue$MessageNode> r1 = r9.mPriorityQueue
            java.util.Iterator r1 = r1.iterator()
            android.os.MessageQueue$MessageNode r1 = r9.iterateNext(r1)
            java.util.concurrent.ConcurrentSkipListSet<android.os.MessageQueue$MessageNode> r2 = r9.mAsyncPriorityQueue
            java.util.Iterator r2 = r2.iterator()
            android.os.MessageQueue$MessageNode r2 = r9.iterateNext(r2)
            long r3 = android.os.SystemClock.uptimeMillis()
            r5 = 0
            if (r1 == 0) goto L57
            boolean r6 = r1.isBarrier()
            if (r6 == 0) goto L57
            if (r2 == 0) goto L54
            if (r11 != 0) goto L67
            long r6 = r2.getWhen()
            int r1 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r1 < 0) goto L54
            goto L67
        L54:
            r1 = r2
            r2 = r5
            goto L6b
        L57:
            android.os.MessageQueue$MessageNode r2 = r9.pickEarliestNode(r1, r2)
            if (r2 == 0) goto L69
            if (r11 != 0) goto L67
            long r6 = r2.getWhen()
            int r1 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r1 < 0) goto L54
        L67:
            r1 = r5
            goto L6b
        L69:
            r1 = r5
            r2 = r1
        L6b:
            android.os.MessageQueue$StateNode r6 = android.os.MessageQueue.sStackStateActive
            if (r2 != 0) goto L98
            if (r1 != 0) goto L77
            r0 = -1
            r9.mNextPollTimeoutMillis = r0
            android.os.MessageQueue$StateNode r0 = android.os.MessageQueue.sStackStateParked
            goto L99
        L77:
            long r7 = r1.getWhen()
            int r1 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r1 <= 0) goto L8b
            long r7 = r7 - r3
            r0 = 2147483647(0x7fffffff, double:1.060997895E-314)
            long r0 = java.lang.Math.min(r7, r0)
            int r0 = (int) r0
            r9.mNextPollTimeoutMillis = r0
            goto L8d
        L8b:
            r9.mNextPollTimeoutMillis = r0
        L8d:
            android.os.MessageQueue$TimedParkStateNode r0 = r9.mStackStateTimedPark
            int r1 = r9.mNextPollTimeoutMillis
            long r7 = (long) r1
            long r3 = r3 + r7
            r0.mWhenToWake = r3
            android.os.MessageQueue$TimedParkStateNode r0 = r9.mStackStateTimedPark
            goto L99
        L98:
            r0 = r6
        L99:
            java.lang.invoke.VarHandle r1 = android.os.MessageQueue.sState
            boolean r0 = (boolean) r1.compareAndSet(r9, r6, r0)
            if (r0 == 0) goto L0
            android.os.MessageQueue$MessageCounts r0 = r9.mMessageCounts
            r0.clearCounts()
            if (r2 == 0) goto Lb8
            if (r10 != 0) goto Lb3
            boolean r0 = r9.removeFromPriorityQueue(r2)
            if (r0 != 0) goto Lb3
            goto L0
        Lb3:
            android.os.Message r9 = android.os.MessageQueue.MessageNode.m3544$$Nest$fgetmMessage(r2)
            return r9
        Lb8:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.MessageQueue.nextMessage(boolean, boolean):android.os.Message");
    }

    private Message nextConcurrent() {
        boolean z;
        long j = this.mPtr;
        if (j == 0) {
            return null;
        }
        this.mNextPollTimeoutMillis = 0;
        int i = -1;
        while (true) {
            if (this.mNextPollTimeoutMillis != 0) {
                Binder.flushPendingCommands();
            }
            this.mMessageDirectlyQueued = false;
            nativePollOnce(j, this.mNextPollTimeoutMillis);
            Message nextMessage = nextMessage(false, false);
            if (nextMessage != null) {
                nextMessage.markInUse();
                decAndTraceMessageCount();
                return nextMessage;
            }
            if ((boolean) sQuitting.getVolatile(this)) {
                return null;
            }
            synchronized (this.mIdleHandlersLock) {
                if (i < 0) {
                    try {
                        if (isIdle()) {
                            i = this.mIdleHandlers.size();
                        }
                    } finally {
                    }
                }
                if (i > 0) {
                    if (this.mPendingIdleHandlers == null) {
                        this.mPendingIdleHandlers = new IdleHandler[Math.max(i, 4)];
                    }
                    this.mPendingIdleHandlers = (IdleHandler[]) this.mIdleHandlers.toArray(this.mPendingIdleHandlers);
                    for (int i2 = 0; i2 < i; i2++) {
                        IdleHandler[] idleHandlerArr = this.mPendingIdleHandlers;
                        IdleHandler idleHandler = idleHandlerArr[i2];
                        idleHandlerArr[i2] = null;
                        try {
                            z = idleHandler.queueIdle();
                        } catch (Throwable th) {
                            Log.wtf(TAG_C, "IdleHandler threw exception", th);
                            z = false;
                        }
                        if (!z) {
                            synchronized (this.mIdleHandlersLock) {
                                this.mIdleHandlers.remove(idleHandler);
                            }
                        }
                    }
                    this.mNextPollTimeoutMillis = 0;
                    i = 0;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x00b9, code lost:
    
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ba, code lost:
    
        if (r8 >= r7) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00bc, code lost:
    
        r0 = r17.mPendingIdleHandlers;
        r9 = r0[r8];
        r0[r8] = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00c2, code lost:
    
        r0 = r9.queueIdle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00c7, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00c8, code lost:
    
        android.util.Log.wtf(android.os.MessageQueue.TAG_L, "IdleHandler threw exception", r0);
        r0 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00e0, code lost:
    
        r0 = 0;
        r7 = 0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.os.Message nextLegacy() {
        /*
            Method dump skipped, instructions count: 231
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.MessageQueue.nextLegacy():android.os.Message");
    }

    Message next() {
        if (this.mUseConcurrent) {
            return nextConcurrent();
        }
        return nextLegacy();
    }

    void quit(boolean z) {
        if (!this.mQuitAllowed) {
            throw new IllegalStateException("Main thread not allowed to quit.");
        }
        if (this.mUseConcurrent) {
            synchronized (this.mIdleHandlersLock) {
                if ((boolean) sQuitting.compareAndSet(this, false, true)) {
                    if (z) {
                        removeAllFutureMessages();
                    } else {
                        removeAllMessages();
                    }
                    nativeWake(this.mPtr);
                }
            }
            return;
        }
        synchronized (this) {
            if (this.mQuitting) {
                return;
            }
            this.mQuitting = true;
            if (z) {
                removeAllFutureMessagesLocked();
            } else {
                removeAllMessagesLocked();
            }
            nativeWake(this.mPtr);
        }
    }

    private int postSyncBarrierConcurrent() {
        return postSyncBarrier(SystemClock.uptimeMillis());
    }

    private int postSyncBarrierLegacy() {
        return postSyncBarrier(SystemClock.uptimeMillis());
    }

    public int postSyncBarrier() {
        if (this.mUseConcurrent) {
            return postSyncBarrierConcurrent();
        }
        return postSyncBarrierLegacy();
    }

    private int postSyncBarrier(long j) {
        Message message;
        if (this.mUseConcurrent) {
            int andIncrement = this.mNextBarrierTokenAtomic.getAndIncrement();
            this.mNextBarrierToken = andIncrement + 1;
            Message obtain = Message.obtain();
            obtain.markInUse();
            obtain.arg1 = andIncrement;
            if (enqueueMessageUnchecked(obtain, j)) {
                return andIncrement;
            }
            Log.wtf(TAG_C, "Unexpected error while adding sync barrier!");
            return -1;
        }
        synchronized (this) {
            int i = this.mNextBarrierToken;
            this.mNextBarrierToken = i + 1;
            Message obtain2 = Message.obtain();
            obtain2.markInUse();
            obtain2.when = j;
            obtain2.arg1 = i;
            Message message2 = null;
            if (Flags.messageQueueTailTracking() && (message = this.mLast) != null && message.when <= j) {
                this.mLast.next = obtain2;
                this.mLast = obtain2;
                obtain2.next = null;
                return i;
            }
            Message message3 = this.mMessages;
            if (j != 0) {
                while (message3 != null && message3.when <= j) {
                    message2 = message3;
                    message3 = message3.next;
                }
            }
            if (message3 == null) {
                this.mLast = obtain2;
            }
            if (message2 != null) {
                obtain2.next = message3;
                message2.next = obtain2;
            } else {
                obtain2.next = message3;
                this.mMessages = obtain2;
            }
            return i;
        }
    }

    private static final class MatchBarrierToken extends MessageCompare {
        int mBarrierToken;

        MatchBarrierToken(int i) {
            super();
            this.mBarrierToken = i;
        }

        @Override // android.os.MessageQueue.MessageCompare
        public boolean compareMessage(MessageNode messageNode, Handler handler, int i, Object obj, Runnable runnable, long j) {
            Message message = messageNode.mMessage;
            return message.target == null && message.arg1 == this.mBarrierToken;
        }
    }

    private void removeSyncBarrierConcurrent(int i) {
        MessageNode messageNode;
        MatchBarrierToken matchBarrierToken = new MatchBarrierToken(i);
        try {
            messageNode = this.mPriorityQueue.first();
        } catch (NoSuchElementException unused) {
            messageNode = null;
        }
        MessageNode messageNode2 = messageNode;
        boolean findOrRemoveMessages = findOrRemoveMessages(null, 0, null, null, 0L, matchBarrierToken, true);
        if (!findOrRemoveMessages || messageNode2 == null) {
            if (!findOrRemoveMessages) {
                throw new IllegalStateException("The specified message queue synchronization  barrier token has not been posted or has already been removed.");
            }
            return;
        }
        Message message = messageNode2.mMessage;
        if (message.target == null && message.arg1 == i) {
            nativeWake(this.mPtr);
        }
    }

    private void removeSyncBarrierLegacy(int i) {
        synchronized (this) {
            Message message = this.mMessages;
            Message message2 = null;
            while (message != null && (message.target != null || message.arg1 != i)) {
                message2 = message;
                message = message.next;
            }
            if (message == null) {
                throw new IllegalStateException("The specified message queue synchronization  barrier token has not been posted or has already been removed.");
            }
            boolean z = false;
            if (message2 != null) {
                message2.next = message.next;
                if (message2.next == null) {
                    this.mLast = message2;
                }
            } else {
                Message message3 = message.next;
                this.mMessages = message3;
                if (message3 == null) {
                    this.mLast = null;
                }
                if (message3 == null || message3.target != null) {
                    z = true;
                }
            }
            message.recycleUnchecked();
            decAndTraceMessageCount();
            if (z && !this.mQuitting) {
                nativeWake(this.mPtr);
            }
        }
    }

    public void removeSyncBarrier(int i) {
        if (this.mUseConcurrent) {
            removeSyncBarrierConcurrent(i);
        } else {
            removeSyncBarrierLegacy(i);
        }
    }

    private boolean enqueueMessageConcurrent(Message message, long j) {
        if (message.isInUse()) {
            throw new IllegalStateException(message + " This message is already in use.");
        }
        return enqueueMessageUnchecked(message, j);
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00ca A[Catch: all -> 0x00ef, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:7:0x000c, B:8:0x0030, B:11:0x0032, B:15:0x0045, B:18:0x004d, B:20:0x0051, B:22:0x0055, B:25:0x005e, B:27:0x0065, B:30:0x006f, B:33:0x0074, B:34:0x00c4, B:36:0x00ca, B:38:0x00d1, B:39:0x00d6, B:41:0x007d, B:43:0x0081, B:46:0x008a, B:56:0x0095, B:57:0x0097, B:60:0x009c, B:62:0x00a0, B:65:0x00a9, B:74:0x00b2, B:77:0x00ba, B:79:0x00c2, B:80:0x00d8, B:81:0x00ee), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00d1 A[Catch: all -> 0x00ef, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:7:0x000c, B:8:0x0030, B:11:0x0032, B:15:0x0045, B:18:0x004d, B:20:0x0051, B:22:0x0055, B:25:0x005e, B:27:0x0065, B:30:0x006f, B:33:0x0074, B:34:0x00c4, B:36:0x00ca, B:38:0x00d1, B:39:0x00d6, B:41:0x007d, B:43:0x0081, B:46:0x008a, B:56:0x0095, B:57:0x0097, B:60:0x009c, B:62:0x00a0, B:65:0x00a9, B:74:0x00b2, B:77:0x00ba, B:79:0x00c2, B:80:0x00d8, B:81:0x00ee), top: B:2:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean enqueueMessageLegacy(android.os.Message r9, long r10) {
        /*
            Method dump skipped, instructions count: 242
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.MessageQueue.enqueueMessageLegacy(android.os.Message, long):boolean");
    }

    boolean enqueueMessage(Message message, long j) {
        if (message.target == null) {
            throw new IllegalArgumentException("Message must have a target.");
        }
        if (this.mUseConcurrent) {
            return enqueueMessageConcurrent(message, j);
        }
        return enqueueMessageLegacy(message, j);
    }

    private Message legacyPeekOrPoll(boolean z) {
        Message message;
        Message message2;
        synchronized (this) {
            long uptimeMillis = SystemClock.uptimeMillis();
            Message message3 = this.mMessages;
            if (message3 == null || message3.target != null) {
                message = null;
            } else {
                while (true) {
                    message2 = message3.next;
                    if (message2 == null || message2.isAsynchronous()) {
                        break;
                    }
                    message3 = message2;
                }
                message = message3;
                message3 = message2;
            }
            if (message3 == null) {
                return null;
            }
            if (z) {
                return message3;
            }
            if (uptimeMillis >= message3.when) {
                this.mBlocked = false;
            }
            if (message != null) {
                message.next = message3.next;
                if (message.next == null) {
                    this.mLast = message;
                }
            } else {
                this.mMessages = message3.next;
                if (message3.next == null) {
                    this.mLast = null;
                }
            }
            message3.next = null;
            message3.markInUse();
            if (message3.isAsynchronous()) {
                this.mAsyncMessageCount--;
            }
            decAndTraceMessageCount();
            return message3;
        }
    }

    Long peekWhenForTest() {
        Message legacyPeekOrPoll;
        throwIfNotTest();
        if (this.mUseConcurrent) {
            legacyPeekOrPoll = nextMessage(true, true);
        } else {
            legacyPeekOrPoll = legacyPeekOrPoll(true);
        }
        if (legacyPeekOrPoll != null) {
            return Long.valueOf(legacyPeekOrPoll.when);
        }
        return null;
    }

    Message pollForTest() {
        throwIfNotTest();
        if (this.mUseConcurrent) {
            return nextMessage(false, true);
        }
        return legacyPeekOrPoll(false);
    }

    boolean isBlockedOnSyncBarrier() {
        throwIfNotTest();
        if (this.mUseConcurrent) {
            nextMessage(true, false);
            MessageNode iterateNext = iterateNext(this.mPriorityQueue.iterator());
            return iterateNext != null && iterateNext.isBarrier();
        }
        Message message = this.mMessages;
        return message != null && message.target == null;
    }

    private static final class MatchHandlerWhatAndObject extends MessageCompare {
        private MatchHandlerWhatAndObject() {
            super();
        }

        @Override // android.os.MessageQueue.MessageCompare
        public boolean compareMessage(MessageNode messageNode, Handler handler, int i, Object obj, Runnable runnable, long j) {
            Message message = messageNode.mMessage;
            if (message.target == handler && message.what == i) {
                return obj == null || message.obj == obj;
            }
            return false;
        }
    }

    private boolean hasMessagesConcurrent(Handler handler, int i, Object obj) {
        return findOrRemoveMessages(handler, i, obj, null, 0L, this.mMatchHandlerWhatAndObject, false);
    }

    private boolean hasMessagesLegacy(Handler handler, int i, Object obj) {
        synchronized (this) {
            for (Message message = this.mMessages; message != null; message = message.next) {
                if (message.target == handler && message.what == i && (obj == null || message.obj == obj)) {
                    return true;
                }
            }
            return false;
        }
    }

    boolean hasMessages(Handler handler, int i, Object obj) {
        if (handler == null) {
            return false;
        }
        if (this.mUseConcurrent) {
            return hasMessagesConcurrent(handler, i, obj);
        }
        return hasMessagesLegacy(handler, i, obj);
    }

    private static final class MatchHandlerWhatAndObjectEquals extends MessageCompare {
        private MatchHandlerWhatAndObjectEquals() {
            super();
        }

        @Override // android.os.MessageQueue.MessageCompare
        public boolean compareMessage(MessageNode messageNode, Handler handler, int i, Object obj, Runnable runnable, long j) {
            Message message = messageNode.mMessage;
            if (message.target == handler && message.what == i) {
                return obj == null || obj.equals(message.obj);
            }
            return false;
        }
    }

    private boolean hasEqualMessagesConcurrent(Handler handler, int i, Object obj) {
        return findOrRemoveMessages(handler, i, obj, null, 0L, this.mMatchHandlerWhatAndObjectEquals, false);
    }

    private boolean hasEqualMessagesLegacy(Handler handler, int i, Object obj) {
        synchronized (this) {
            for (Message message = this.mMessages; message != null; message = message.next) {
                if (message.target == handler && message.what == i && (obj == null || obj.equals(message.obj))) {
                    return true;
                }
            }
            return false;
        }
    }

    boolean hasEqualMessages(Handler handler, int i, Object obj) {
        if (handler == null) {
            return false;
        }
        if (this.mUseConcurrent) {
            return hasEqualMessagesConcurrent(handler, i, obj);
        }
        return hasEqualMessagesLegacy(handler, i, obj);
    }

    private static final class MatchHandlerRunnableAndObject extends MessageCompare {
        private MatchHandlerRunnableAndObject() {
            super();
        }

        @Override // android.os.MessageQueue.MessageCompare
        public boolean compareMessage(MessageNode messageNode, Handler handler, int i, Object obj, Runnable runnable, long j) {
            Message message = messageNode.mMessage;
            if (message.target == handler && message.callback == runnable) {
                return obj == null || message.obj == obj;
            }
            return false;
        }
    }

    private boolean hasMessagesConcurrent(Handler handler, Runnable runnable, Object obj) {
        return findOrRemoveMessages(handler, -1, obj, runnable, 0L, this.mMatchHandlerRunnableAndObject, false);
    }

    private boolean hasMessagesLegacy(Handler handler, Runnable runnable, Object obj) {
        synchronized (this) {
            for (Message message = this.mMessages; message != null; message = message.next) {
                if (message.target == handler && message.callback == runnable && (obj == null || message.obj == obj)) {
                    return true;
                }
            }
            return false;
        }
    }

    boolean hasMessages(Handler handler, Runnable runnable, Object obj) {
        if (handler == null) {
            return false;
        }
        if (this.mUseConcurrent) {
            return hasMessagesConcurrent(handler, runnable, obj);
        }
        return hasMessagesLegacy(handler, runnable, obj);
    }

    private static final class MatchHandler extends MessageCompare {
        private MatchHandler() {
            super();
        }

        @Override // android.os.MessageQueue.MessageCompare
        public boolean compareMessage(MessageNode messageNode, Handler handler, int i, Object obj, Runnable runnable, long j) {
            return messageNode.mMessage.target == handler;
        }
    }

    private boolean hasMessagesConcurrent(Handler handler) {
        return findOrRemoveMessages(handler, -1, null, null, 0L, this.mMatchHandler, false);
    }

    private boolean hasMessagesLegacy(Handler handler) {
        synchronized (this) {
            for (Message message = this.mMessages; message != null; message = message.next) {
                if (message.target == handler) {
                    return true;
                }
            }
            return false;
        }
    }

    boolean hasMessages(Handler handler) {
        if (handler == null) {
            return false;
        }
        if (this.mUseConcurrent) {
            return hasMessagesConcurrent(handler);
        }
        return hasMessagesLegacy(handler);
    }

    private void removeMessagesConcurrent(Handler handler, int i, Object obj) {
        findOrRemoveMessages(handler, i, obj, null, 0L, this.mMatchHandlerWhatAndObject, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002d, code lost:
    
        r4.mLast = r4.mMessages;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void removeMessagesLegacy(android.os.Handler r5, int r6, java.lang.Object r7) {
        /*
            r4 = this;
            monitor-enter(r4)
            android.os.Message r0 = r4.mMessages     // Catch: java.lang.Throwable -> L66
        L3:
            if (r0 == 0) goto L2b
            android.os.Handler r1 = r0.target     // Catch: java.lang.Throwable -> L66
            if (r1 != r5) goto L2b
            int r1 = r0.what     // Catch: java.lang.Throwable -> L66
            if (r1 != r6) goto L2b
            if (r7 == 0) goto L13
            java.lang.Object r1 = r0.obj     // Catch: java.lang.Throwable -> L66
            if (r1 != r7) goto L2b
        L13:
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L66
            r4.mMessages = r1     // Catch: java.lang.Throwable -> L66
            boolean r2 = r0.isAsynchronous()     // Catch: java.lang.Throwable -> L66
            if (r2 == 0) goto L23
            int r2 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L66
            int r2 = r2 + (-1)
            r4.mAsyncMessageCount = r2     // Catch: java.lang.Throwable -> L66
        L23:
            r0.recycleUnchecked()     // Catch: java.lang.Throwable -> L66
            r4.decAndTraceMessageCount()     // Catch: java.lang.Throwable -> L66
            r0 = r1
            goto L3
        L2b:
            if (r0 != 0) goto L31
            android.os.Message r1 = r4.mMessages     // Catch: java.lang.Throwable -> L66
            r4.mLast = r1     // Catch: java.lang.Throwable -> L66
        L31:
            if (r0 == 0) goto L64
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L66
            if (r1 == 0) goto L62
            android.os.Handler r2 = r1.target     // Catch: java.lang.Throwable -> L66
            if (r2 != r5) goto L62
            int r2 = r1.what     // Catch: java.lang.Throwable -> L66
            if (r2 != r6) goto L62
            if (r7 == 0) goto L45
            java.lang.Object r2 = r1.obj     // Catch: java.lang.Throwable -> L66
            if (r2 != r7) goto L62
        L45:
            android.os.Message r2 = r1.next     // Catch: java.lang.Throwable -> L66
            boolean r3 = r1.isAsynchronous()     // Catch: java.lang.Throwable -> L66
            if (r3 == 0) goto L53
            int r3 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L66
            int r3 = r3 + (-1)
            r4.mAsyncMessageCount = r3     // Catch: java.lang.Throwable -> L66
        L53:
            r1.recycleUnchecked()     // Catch: java.lang.Throwable -> L66
            r4.decAndTraceMessageCount()     // Catch: java.lang.Throwable -> L66
            r0.next = r2     // Catch: java.lang.Throwable -> L66
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L66
            if (r1 != 0) goto L31
            r4.mLast = r0     // Catch: java.lang.Throwable -> L66
            goto L31
        L62:
            r0 = r1
            goto L31
        L64:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L66
            return
        L66:
            r5 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L66
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.MessageQueue.removeMessagesLegacy(android.os.Handler, int, java.lang.Object):void");
    }

    void removeMessages(Handler handler, int i, Object obj) {
        if (handler == null) {
            return;
        }
        if (this.mUseConcurrent) {
            removeMessagesConcurrent(handler, i, obj);
        } else {
            removeMessagesLegacy(handler, i, obj);
        }
    }

    private void removeEqualMessagesConcurrent(Handler handler, int i, Object obj) {
        findOrRemoveMessages(handler, i, obj, null, 0L, this.mMatchHandlerWhatAndObjectEquals, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002e, code lost:
    
        r4.mLast = r4.mMessages;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void removeEqualMessagesLegacy(android.os.Handler r5, int r6, java.lang.Object r7) {
        /*
            r4 = this;
            monitor-enter(r4)
            android.os.Message r0 = r4.mMessages     // Catch: java.lang.Throwable -> L6b
        L3:
            if (r0 == 0) goto L2c
            android.os.Handler r1 = r0.target     // Catch: java.lang.Throwable -> L6b
            if (r1 != r5) goto L2c
            int r1 = r0.what     // Catch: java.lang.Throwable -> L6b
            if (r1 != r6) goto L2c
            if (r7 == 0) goto L17
            java.lang.Object r1 = r0.obj     // Catch: java.lang.Throwable -> L6b
            boolean r1 = r7.equals(r1)     // Catch: java.lang.Throwable -> L6b
            if (r1 == 0) goto L2c
        L17:
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L6b
            r4.mMessages = r1     // Catch: java.lang.Throwable -> L6b
            boolean r2 = r0.isAsynchronous()     // Catch: java.lang.Throwable -> L6b
            if (r2 == 0) goto L27
            int r2 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L6b
            int r2 = r2 + (-1)
            r4.mAsyncMessageCount = r2     // Catch: java.lang.Throwable -> L6b
        L27:
            r0.recycleUnchecked()     // Catch: java.lang.Throwable -> L6b
            r0 = r1
            goto L3
        L2c:
            if (r0 != 0) goto L32
            android.os.Message r1 = r4.mMessages     // Catch: java.lang.Throwable -> L6b
            r4.mLast = r1     // Catch: java.lang.Throwable -> L6b
        L32:
            if (r0 == 0) goto L69
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L6b
            if (r1 == 0) goto L67
            android.os.Handler r2 = r1.target     // Catch: java.lang.Throwable -> L6b
            if (r2 != r5) goto L67
            int r2 = r1.what     // Catch: java.lang.Throwable -> L6b
            if (r2 != r6) goto L67
            if (r7 == 0) goto L4a
            java.lang.Object r2 = r1.obj     // Catch: java.lang.Throwable -> L6b
            boolean r2 = r7.equals(r2)     // Catch: java.lang.Throwable -> L6b
            if (r2 == 0) goto L67
        L4a:
            android.os.Message r2 = r1.next     // Catch: java.lang.Throwable -> L6b
            boolean r3 = r1.isAsynchronous()     // Catch: java.lang.Throwable -> L6b
            if (r3 == 0) goto L58
            int r3 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L6b
            int r3 = r3 + (-1)
            r4.mAsyncMessageCount = r3     // Catch: java.lang.Throwable -> L6b
        L58:
            r1.recycleUnchecked()     // Catch: java.lang.Throwable -> L6b
            r4.decAndTraceMessageCount()     // Catch: java.lang.Throwable -> L6b
            r0.next = r2     // Catch: java.lang.Throwable -> L6b
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L6b
            if (r1 != 0) goto L32
            r4.mLast = r0     // Catch: java.lang.Throwable -> L6b
            goto L32
        L67:
            r0 = r1
            goto L32
        L69:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L6b
            return
        L6b:
            r5 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L6b
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.MessageQueue.removeEqualMessagesLegacy(android.os.Handler, int, java.lang.Object):void");
    }

    void removeEqualMessages(Handler handler, int i, Object obj) {
        if (handler == null) {
            return;
        }
        if (this.mUseConcurrent) {
            removeEqualMessagesConcurrent(handler, i, obj);
        } else {
            removeEqualMessagesLegacy(handler, i, obj);
        }
    }

    private void removeMessagesConcurrent(Handler handler, Runnable runnable, Object obj) {
        findOrRemoveMessages(handler, -1, obj, runnable, 0L, this.mMatchHandlerRunnableAndObject, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002d, code lost:
    
        r4.mLast = r4.mMessages;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void removeMessagesLegacy(android.os.Handler r5, java.lang.Runnable r6, java.lang.Object r7) {
        /*
            r4 = this;
            monitor-enter(r4)
            android.os.Message r0 = r4.mMessages     // Catch: java.lang.Throwable -> L66
        L3:
            if (r0 == 0) goto L2b
            android.os.Handler r1 = r0.target     // Catch: java.lang.Throwable -> L66
            if (r1 != r5) goto L2b
            java.lang.Runnable r1 = r0.callback     // Catch: java.lang.Throwable -> L66
            if (r1 != r6) goto L2b
            if (r7 == 0) goto L13
            java.lang.Object r1 = r0.obj     // Catch: java.lang.Throwable -> L66
            if (r1 != r7) goto L2b
        L13:
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L66
            r4.mMessages = r1     // Catch: java.lang.Throwable -> L66
            boolean r2 = r0.isAsynchronous()     // Catch: java.lang.Throwable -> L66
            if (r2 == 0) goto L23
            int r2 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L66
            int r2 = r2 + (-1)
            r4.mAsyncMessageCount = r2     // Catch: java.lang.Throwable -> L66
        L23:
            r0.recycleUnchecked()     // Catch: java.lang.Throwable -> L66
            r4.decAndTraceMessageCount()     // Catch: java.lang.Throwable -> L66
            r0 = r1
            goto L3
        L2b:
            if (r0 != 0) goto L31
            android.os.Message r1 = r4.mMessages     // Catch: java.lang.Throwable -> L66
            r4.mLast = r1     // Catch: java.lang.Throwable -> L66
        L31:
            if (r0 == 0) goto L64
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L66
            if (r1 == 0) goto L62
            android.os.Handler r2 = r1.target     // Catch: java.lang.Throwable -> L66
            if (r2 != r5) goto L62
            java.lang.Runnable r2 = r1.callback     // Catch: java.lang.Throwable -> L66
            if (r2 != r6) goto L62
            if (r7 == 0) goto L45
            java.lang.Object r2 = r1.obj     // Catch: java.lang.Throwable -> L66
            if (r2 != r7) goto L62
        L45:
            android.os.Message r2 = r1.next     // Catch: java.lang.Throwable -> L66
            boolean r3 = r1.isAsynchronous()     // Catch: java.lang.Throwable -> L66
            if (r3 == 0) goto L53
            int r3 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L66
            int r3 = r3 + (-1)
            r4.mAsyncMessageCount = r3     // Catch: java.lang.Throwable -> L66
        L53:
            r1.recycleUnchecked()     // Catch: java.lang.Throwable -> L66
            r4.decAndTraceMessageCount()     // Catch: java.lang.Throwable -> L66
            r0.next = r2     // Catch: java.lang.Throwable -> L66
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L66
            if (r1 != 0) goto L31
            r4.mLast = r0     // Catch: java.lang.Throwable -> L66
            goto L31
        L62:
            r0 = r1
            goto L31
        L64:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L66
            return
        L66:
            r5 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L66
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.MessageQueue.removeMessagesLegacy(android.os.Handler, java.lang.Runnable, java.lang.Object):void");
    }

    void removeMessages(Handler handler, Runnable runnable, Object obj) {
        if (handler == null || runnable == null) {
            return;
        }
        if (this.mUseConcurrent) {
            removeMessagesConcurrent(handler, runnable, obj);
        } else {
            removeMessagesLegacy(handler, runnable, obj);
        }
    }

    private static final class MatchHandlerRunnableAndObjectEquals extends MessageCompare {
        private MatchHandlerRunnableAndObjectEquals() {
            super();
        }

        @Override // android.os.MessageQueue.MessageCompare
        public boolean compareMessage(MessageNode messageNode, Handler handler, int i, Object obj, Runnable runnable, long j) {
            Message message = messageNode.mMessage;
            if (message.target == handler && message.callback == runnable) {
                return obj == null || obj.equals(message.obj);
            }
            return false;
        }
    }

    private void removeEqualMessagesConcurrent(Handler handler, Runnable runnable, Object obj) {
        findOrRemoveMessages(handler, -1, obj, runnable, 0L, this.mMatchHandlerRunnableAndObjectEquals, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0031, code lost:
    
        r4.mLast = r4.mMessages;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void removeEqualMessagesLegacy(android.os.Handler r5, java.lang.Runnable r6, java.lang.Object r7) {
        /*
            r4 = this;
            monitor-enter(r4)
            android.os.Message r0 = r4.mMessages     // Catch: java.lang.Throwable -> L6e
        L3:
            if (r0 == 0) goto L2f
            android.os.Handler r1 = r0.target     // Catch: java.lang.Throwable -> L6e
            if (r1 != r5) goto L2f
            java.lang.Runnable r1 = r0.callback     // Catch: java.lang.Throwable -> L6e
            if (r1 != r6) goto L2f
            if (r7 == 0) goto L17
            java.lang.Object r1 = r0.obj     // Catch: java.lang.Throwable -> L6e
            boolean r1 = r7.equals(r1)     // Catch: java.lang.Throwable -> L6e
            if (r1 == 0) goto L2f
        L17:
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L6e
            r4.mMessages = r1     // Catch: java.lang.Throwable -> L6e
            boolean r2 = r0.isAsynchronous()     // Catch: java.lang.Throwable -> L6e
            if (r2 == 0) goto L27
            int r2 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L6e
            int r2 = r2 + (-1)
            r4.mAsyncMessageCount = r2     // Catch: java.lang.Throwable -> L6e
        L27:
            r0.recycleUnchecked()     // Catch: java.lang.Throwable -> L6e
            r4.decAndTraceMessageCount()     // Catch: java.lang.Throwable -> L6e
            r0 = r1
            goto L3
        L2f:
            if (r0 != 0) goto L35
            android.os.Message r1 = r4.mMessages     // Catch: java.lang.Throwable -> L6e
            r4.mLast = r1     // Catch: java.lang.Throwable -> L6e
        L35:
            if (r0 == 0) goto L6c
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L6e
            if (r1 == 0) goto L6a
            android.os.Handler r2 = r1.target     // Catch: java.lang.Throwable -> L6e
            if (r2 != r5) goto L6a
            java.lang.Runnable r2 = r1.callback     // Catch: java.lang.Throwable -> L6e
            if (r2 != r6) goto L6a
            if (r7 == 0) goto L4d
            java.lang.Object r2 = r1.obj     // Catch: java.lang.Throwable -> L6e
            boolean r2 = r7.equals(r2)     // Catch: java.lang.Throwable -> L6e
            if (r2 == 0) goto L6a
        L4d:
            android.os.Message r2 = r1.next     // Catch: java.lang.Throwable -> L6e
            boolean r3 = r1.isAsynchronous()     // Catch: java.lang.Throwable -> L6e
            if (r3 == 0) goto L5b
            int r3 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L6e
            int r3 = r3 + (-1)
            r4.mAsyncMessageCount = r3     // Catch: java.lang.Throwable -> L6e
        L5b:
            r1.recycleUnchecked()     // Catch: java.lang.Throwable -> L6e
            r4.decAndTraceMessageCount()     // Catch: java.lang.Throwable -> L6e
            r0.next = r2     // Catch: java.lang.Throwable -> L6e
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L6e
            if (r1 != 0) goto L35
            r4.mLast = r0     // Catch: java.lang.Throwable -> L6e
            goto L35
        L6a:
            r0 = r1
            goto L35
        L6c:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L6e
            return
        L6e:
            r5 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L6e
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.MessageQueue.removeEqualMessagesLegacy(android.os.Handler, java.lang.Runnable, java.lang.Object):void");
    }

    void removeEqualMessages(Handler handler, Runnable runnable, Object obj) {
        if (handler == null || runnable == null) {
            return;
        }
        if (this.mUseConcurrent) {
            removeEqualMessagesConcurrent(handler, runnable, obj);
        } else {
            removeEqualMessagesLegacy(handler, runnable, obj);
        }
    }

    private static final class MatchHandlerAndObject extends MessageCompare {
        private MatchHandlerAndObject() {
            super();
        }

        @Override // android.os.MessageQueue.MessageCompare
        public boolean compareMessage(MessageNode messageNode, Handler handler, int i, Object obj, Runnable runnable, long j) {
            Message message = messageNode.mMessage;
            if (message.target == handler) {
                return obj == null || message.obj == obj;
            }
            return false;
        }
    }

    private void removeCallbacksAndMessagesConcurrent(Handler handler, Object obj) {
        findOrRemoveMessages(handler, -1, obj, null, 0L, this.mMatchHandlerAndObject, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0029, code lost:
    
        r4.mLast = r4.mMessages;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void removeCallbacksAndMessagesLegacy(android.os.Handler r5, java.lang.Object r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            android.os.Message r0 = r4.mMessages     // Catch: java.lang.Throwable -> L5e
        L3:
            if (r0 == 0) goto L27
            android.os.Handler r1 = r0.target     // Catch: java.lang.Throwable -> L5e
            if (r1 != r5) goto L27
            if (r6 == 0) goto Lf
            java.lang.Object r1 = r0.obj     // Catch: java.lang.Throwable -> L5e
            if (r1 != r6) goto L27
        Lf:
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L5e
            r4.mMessages = r1     // Catch: java.lang.Throwable -> L5e
            boolean r2 = r0.isAsynchronous()     // Catch: java.lang.Throwable -> L5e
            if (r2 == 0) goto L1f
            int r2 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L5e
            int r2 = r2 + (-1)
            r4.mAsyncMessageCount = r2     // Catch: java.lang.Throwable -> L5e
        L1f:
            r0.recycleUnchecked()     // Catch: java.lang.Throwable -> L5e
            r4.decAndTraceMessageCount()     // Catch: java.lang.Throwable -> L5e
            r0 = r1
            goto L3
        L27:
            if (r0 != 0) goto L2d
            android.os.Message r1 = r4.mMessages     // Catch: java.lang.Throwable -> L5e
            r4.mLast = r1     // Catch: java.lang.Throwable -> L5e
        L2d:
            if (r0 == 0) goto L5c
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L5e
            if (r1 == 0) goto L5a
            android.os.Handler r2 = r1.target     // Catch: java.lang.Throwable -> L5e
            if (r2 != r5) goto L5a
            if (r6 == 0) goto L3d
            java.lang.Object r2 = r1.obj     // Catch: java.lang.Throwable -> L5e
            if (r2 != r6) goto L5a
        L3d:
            android.os.Message r2 = r1.next     // Catch: java.lang.Throwable -> L5e
            boolean r3 = r1.isAsynchronous()     // Catch: java.lang.Throwable -> L5e
            if (r3 == 0) goto L4b
            int r3 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L5e
            int r3 = r3 + (-1)
            r4.mAsyncMessageCount = r3     // Catch: java.lang.Throwable -> L5e
        L4b:
            r1.recycleUnchecked()     // Catch: java.lang.Throwable -> L5e
            r4.decAndTraceMessageCount()     // Catch: java.lang.Throwable -> L5e
            r0.next = r2     // Catch: java.lang.Throwable -> L5e
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L5e
            if (r1 != 0) goto L2d
            r4.mLast = r0     // Catch: java.lang.Throwable -> L5e
            goto L2d
        L5a:
            r0 = r1
            goto L2d
        L5c:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L5e
            return
        L5e:
            r5 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L5e
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.MessageQueue.removeCallbacksAndMessagesLegacy(android.os.Handler, java.lang.Object):void");
    }

    void removeCallbacksAndMessages(Handler handler, Object obj) {
        if (handler == null) {
            return;
        }
        if (this.mUseConcurrent) {
            removeCallbacksAndMessagesConcurrent(handler, obj);
        } else {
            removeCallbacksAndMessagesLegacy(handler, obj);
        }
    }

    private static final class MatchHandlerAndObjectEquals extends MessageCompare {
        private MatchHandlerAndObjectEquals() {
            super();
        }

        @Override // android.os.MessageQueue.MessageCompare
        public boolean compareMessage(MessageNode messageNode, Handler handler, int i, Object obj, Runnable runnable, long j) {
            Message message = messageNode.mMessage;
            if (message.target == handler) {
                return obj == null || obj.equals(message.obj);
            }
            return false;
        }
    }

    void removeCallbacksAndEqualMessagesConcurrent(Handler handler, Object obj) {
        findOrRemoveMessages(handler, -1, obj, null, 0L, this.mMatchHandlerAndObjectEquals, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002d, code lost:
    
        r4.mLast = r4.mMessages;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void removeCallbacksAndEqualMessagesLegacy(android.os.Handler r5, java.lang.Object r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            android.os.Message r0 = r4.mMessages     // Catch: java.lang.Throwable -> L66
        L3:
            if (r0 == 0) goto L2b
            android.os.Handler r1 = r0.target     // Catch: java.lang.Throwable -> L66
            if (r1 != r5) goto L2b
            if (r6 == 0) goto L13
            java.lang.Object r1 = r0.obj     // Catch: java.lang.Throwable -> L66
            boolean r1 = r6.equals(r1)     // Catch: java.lang.Throwable -> L66
            if (r1 == 0) goto L2b
        L13:
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L66
            r4.mMessages = r1     // Catch: java.lang.Throwable -> L66
            boolean r2 = r0.isAsynchronous()     // Catch: java.lang.Throwable -> L66
            if (r2 == 0) goto L23
            int r2 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L66
            int r2 = r2 + (-1)
            r4.mAsyncMessageCount = r2     // Catch: java.lang.Throwable -> L66
        L23:
            r0.recycleUnchecked()     // Catch: java.lang.Throwable -> L66
            r4.decAndTraceMessageCount()     // Catch: java.lang.Throwable -> L66
            r0 = r1
            goto L3
        L2b:
            if (r0 != 0) goto L31
            android.os.Message r1 = r4.mMessages     // Catch: java.lang.Throwable -> L66
            r4.mLast = r1     // Catch: java.lang.Throwable -> L66
        L31:
            if (r0 == 0) goto L64
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L66
            if (r1 == 0) goto L62
            android.os.Handler r2 = r1.target     // Catch: java.lang.Throwable -> L66
            if (r2 != r5) goto L62
            if (r6 == 0) goto L45
            java.lang.Object r2 = r1.obj     // Catch: java.lang.Throwable -> L66
            boolean r2 = r6.equals(r2)     // Catch: java.lang.Throwable -> L66
            if (r2 == 0) goto L62
        L45:
            android.os.Message r2 = r1.next     // Catch: java.lang.Throwable -> L66
            boolean r3 = r1.isAsynchronous()     // Catch: java.lang.Throwable -> L66
            if (r3 == 0) goto L53
            int r3 = r4.mAsyncMessageCount     // Catch: java.lang.Throwable -> L66
            int r3 = r3 + (-1)
            r4.mAsyncMessageCount = r3     // Catch: java.lang.Throwable -> L66
        L53:
            r1.recycleUnchecked()     // Catch: java.lang.Throwable -> L66
            r4.decAndTraceMessageCount()     // Catch: java.lang.Throwable -> L66
            r0.next = r2     // Catch: java.lang.Throwable -> L66
            android.os.Message r1 = r0.next     // Catch: java.lang.Throwable -> L66
            if (r1 != 0) goto L31
            r4.mLast = r0     // Catch: java.lang.Throwable -> L66
            goto L31
        L62:
            r0 = r1
            goto L31
        L64:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L66
            return
        L66:
            r5 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L66
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.MessageQueue.removeCallbacksAndEqualMessagesLegacy(android.os.Handler, java.lang.Object):void");
    }

    void removeCallbacksAndEqualMessages(Handler handler, Object obj) {
        if (handler == null) {
            return;
        }
        if (this.mUseConcurrent) {
            removeCallbacksAndEqualMessagesConcurrent(handler, obj);
        } else {
            removeCallbacksAndEqualMessagesLegacy(handler, obj);
        }
    }

    private void removeAllMessagesLocked() {
        Message message = this.mMessages;
        while (message != null) {
            Message message2 = message.next;
            message.recycleUnchecked();
            message = message2;
        }
        this.mMessages = null;
        this.mLast = null;
        this.mAsyncMessageCount = 0;
        this.mMessageCount.set(0L);
        traceMessageCount();
    }

    private void removeAllFutureMessagesLocked() {
        long uptimeMillis = SystemClock.uptimeMillis();
        Message message = this.mMessages;
        if (message == null) {
            return;
        }
        if (message.when > uptimeMillis) {
            removeAllMessagesLocked();
            return;
        }
        while (true) {
            Message message2 = message.next;
            if (message2 == null) {
                return;
            }
            if (message2.when > uptimeMillis) {
                message.next = null;
                this.mLast = message;
                while (true) {
                    Message message3 = message2.next;
                    if (message2.isAsynchronous()) {
                        this.mAsyncMessageCount--;
                    }
                    message2.recycleUnchecked();
                    decAndTraceMessageCount();
                    if (message3 == null) {
                        return;
                    } else {
                        message2 = message3;
                    }
                }
            } else {
                message = message2;
            }
        }
    }

    private static final class MatchAllMessages extends MessageCompare {
        @Override // android.os.MessageQueue.MessageCompare
        public boolean compareMessage(MessageNode messageNode, Handler handler, int i, Object obj, Runnable runnable, long j) {
            return true;
        }

        private MatchAllMessages() {
            super();
        }
    }

    private void removeAllMessages() {
        findOrRemoveMessages(null, -1, null, null, 0L, this.mMatchAllMessages, true);
    }

    private static final class MatchAllFutureMessages extends MessageCompare {
        private MatchAllFutureMessages() {
            super();
        }

        @Override // android.os.MessageQueue.MessageCompare
        public boolean compareMessage(MessageNode messageNode, Handler handler, int i, Object obj, Runnable runnable, long j) {
            return messageNode.mMessage.when > j;
        }
    }

    private void removeAllFutureMessages() {
        findOrRemoveMessages(null, -1, null, null, SystemClock.uptimeMillis(), this.mMatchAllFutureMessages, true);
    }

    @NeverCompile
    private void printPriorityQueueNodes() {
        Iterator<MessageNode> it = this.mPriorityQueue.iterator();
        Log.d(TAG_C, "* Dump priority queue");
        while (it.hasNext()) {
            MessageNode next = it.next();
            Log.d(TAG_C, "** MessageNode what: " + next.mMessage.what + " when " + next.mMessage.when + " seq: " + next.mInsertSeq);
        }
    }

    @NeverCompile
    private int dumpPriorityQueue(ConcurrentSkipListSet<MessageNode> concurrentSkipListSet, Printer printer, String str, Handler handler, int i) {
        long uptimeMillis = SystemClock.uptimeMillis();
        Iterator<MessageNode> it = concurrentSkipListSet.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            Message message = it.next().mMessage;
            if (handler == null || handler == message.target) {
                printer.println(str + "Message " + (i + i2) + ": " + message.toString(uptimeMillis));
            }
            i2++;
        }
        return i2;
    }

    @NeverCompile
    void dump(Printer printer, String str, Handler handler) {
        int i = 0;
        if (this.mUseConcurrent) {
            long uptimeMillis = SystemClock.uptimeMillis();
            printer.println(str + "(MessageQueue is using Concurrent implementation)");
            StackNode stackNode = (StackNode) sState.getVolatile(this);
            int i2 = 0;
            while (stackNode != null) {
                if (stackNode.isMessageNode()) {
                    MessageNode messageNode = (MessageNode) stackNode;
                    Message message = messageNode.mMessage;
                    if (handler == null || handler == message.target) {
                        printer.println(str + "Message " + i2 + ": " + message.toString(uptimeMillis));
                    }
                    stackNode = messageNode.mNext;
                } else {
                    printer.println(str + "State: " + stackNode);
                    stackNode = null;
                }
                i2++;
            }
            printer.println(str + "PriorityQueue Messages: ");
            int dumpPriorityQueue = i2 + dumpPriorityQueue(this.mPriorityQueue, printer, str, handler, i2);
            printer.println(str + "AsyncPriorityQueue Messages: ");
            printer.println(str + "(Total messages: " + (dumpPriorityQueue + dumpPriorityQueue(this.mAsyncPriorityQueue, printer, str, handler, dumpPriorityQueue)) + ", polling=" + isPolling() + ", quitting=" + (boolean) sQuitting.getVolatile(this) + NavigationBarInflaterView.KEY_CODE_END);
            return;
        }
        synchronized (this) {
            printer.println(str + "(MessageQueue is using Legacy implementation)");
            long uptimeMillis2 = SystemClock.uptimeMillis();
            for (Message message2 = this.mMessages; message2 != null; message2 = message2.next) {
                if (handler == null || handler == message2.target) {
                    printer.println(str + "Message " + i + ": " + message2.toString(uptimeMillis2));
                }
                i++;
            }
            printer.println(str + "(Total messages: " + i + ", polling=" + isPollingLocked() + ", quitting=" + this.mQuitting + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    @NeverCompile
    private int dumpPriorityQueue(ConcurrentSkipListSet<MessageNode> concurrentSkipListSet, ProtoOutputStream protoOutputStream) {
        Iterator<MessageNode> it = concurrentSkipListSet.iterator();
        int i = 0;
        while (it.hasNext()) {
            it.next().mMessage.dumpDebug(protoOutputStream, 2246267895809L);
            i++;
        }
        return i;
    }

    @NeverCompile
    void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        if (this.mUseConcurrent) {
            long start = protoOutputStream.start(j);
            StackNode stackNode = (StackNode) sState.getVolatile(this);
            while (stackNode.isMessageNode()) {
                MessageNode messageNode = (MessageNode) stackNode;
                messageNode.mMessage.dumpDebug(protoOutputStream, 2246267895809L);
                stackNode = messageNode.mNext;
            }
            dumpPriorityQueue(this.mPriorityQueue, protoOutputStream);
            dumpPriorityQueue(this.mAsyncPriorityQueue, protoOutputStream);
            protoOutputStream.write(1133871366146L, isPolling());
            protoOutputStream.write(1133871366147L, (boolean) sQuitting.getVolatile(this));
            protoOutputStream.end(start);
            return;
        }
        long start2 = protoOutputStream.start(j);
        synchronized (this) {
            for (Message message = this.mMessages; message != null; message = message.next) {
                message.dumpDebug(protoOutputStream, 2246267895809L);
            }
            protoOutputStream.write(1133871366146L, isPollingLocked());
            protoOutputStream.write(1133871366147L, this.mQuitting);
        }
        protoOutputStream.end(start2);
    }

    private static final class FileDescriptorRecord {
        public final FileDescriptor mDescriptor;
        public int mEvents;
        public OnFileDescriptorEventListener mListener;
        public int mSeq;

        public FileDescriptorRecord(FileDescriptor fileDescriptor, int i, OnFileDescriptorEventListener onFileDescriptorEventListener) {
            this.mDescriptor = fileDescriptor;
            this.mEvents = i;
            this.mListener = onFileDescriptorEventListener;
        }
    }

    private void insertIntoPriorityQueue(MessageNode messageNode) {
        if (messageNode.isAsync()) {
            this.mAsyncPriorityQueue.add(messageNode);
        } else {
            this.mPriorityQueue.add(messageNode);
        }
    }

    private boolean removeFromPriorityQueue(MessageNode messageNode) {
        if (messageNode.isAsync()) {
            return this.mAsyncPriorityQueue.remove(messageNode);
        }
        return this.mPriorityQueue.remove(messageNode);
    }

    private MessageNode pickEarliestNode(MessageNode messageNode, MessageNode messageNode2) {
        return (messageNode == null || messageNode2 == null ? messageNode == null : messageNode.compareTo(messageNode2) >= 0) ? messageNode2 : messageNode;
    }

    private MessageNode iterateNext(Iterator<MessageNode> it) {
        if (!it.hasNext()) {
            return null;
        }
        try {
            return it.next();
        } catch (NoSuchElementException unused) {
            return null;
        }
    }

    private void drainStack(StackNode stackNode) {
        while (stackNode.isMessageNode()) {
            MessageNode messageNode = (MessageNode) stackNode;
            if (messageNode.removeFromStack()) {
                insertIntoPriorityQueue(messageNode);
            }
            StackNode stackNode2 = messageNode.mNext;
            messageNode.mNext = null;
            stackNode = stackNode2;
        }
    }

    private StackNode swapAndSetStackStateActive() {
        VarHandle varHandle;
        StackNode stackNode;
        StateNode stateNode;
        do {
            varHandle = sState;
            stackNode = (StackNode) varHandle.getVolatile(this);
            stateNode = sStackStateActive;
            if (stackNode == stateNode) {
                break;
            }
        } while (!(boolean) varHandle.compareAndSet(this, stackNode, stateNode));
        return stackNode;
    }

    private StateNode getStateNode(StackNode stackNode) {
        if (stackNode.isMessageNode()) {
            return ((MessageNode) stackNode).mBottomOfStack;
        }
        return (StateNode) stackNode;
    }

    private void waitForDrainCompleted() {
        this.mDrainingLock.lock();
        while (this.mNextIsDrainingStack) {
            this.mDrainCompleted.awaitUninterruptibly();
        }
        this.mDrainingLock.unlock();
    }

    static class StackNode {
        private final int mType;

        StackNode(int i) {
            this.mType = i;
        }

        final int getNodeType() {
            return this.mType;
        }

        final boolean isMessageNode() {
            return this.mType == 0;
        }
    }

    static final class MessageNode extends StackNode implements Comparable<MessageNode> {
        private static final VarHandle sRemovedFromStack;
        StateNode mBottomOfStack;
        final long mInsertSeq;
        private final Message mMessage;
        volatile StackNode mNext;
        private volatile boolean mRemovedFromStackValue;
        boolean mWokeUp;

        static {
            try {
                sRemovedFromStack = MethodHandles.lookup().findVarHandle(MessageNode.class, "mRemovedFromStackValue", Boolean.TYPE);
            } catch (Exception e) {
                Log.wtf(MessageQueue.TAG_C, "VarHandle lookup failed with exception: " + e);
                throw new ExceptionInInitializerError(e);
            }
        }

        MessageNode(Message message, long j) {
            super(0);
            this.mMessage = message;
            this.mInsertSeq = j;
        }

        long getWhen() {
            return this.mMessage.when;
        }

        boolean removeFromStack() {
            return (boolean) sRemovedFromStack.compareAndSet(this, false, true);
        }

        boolean isAsync() {
            return this.mMessage.isAsynchronous();
        }

        boolean isBarrier() {
            return this.mMessage.target == null;
        }

        @Override // java.lang.Comparable
        public int compareTo(MessageNode messageNode) {
            int compare = Long.compare(this.mMessage.when, messageNode.mMessage.when);
            return compare == 0 ? Long.compare(this.mInsertSeq, messageNode.mInsertSeq) : compare;
        }
    }

    static class StateNode extends StackNode {
        StateNode(int i) {
            super(i);
        }
    }

    static final class TimedParkStateNode extends StateNode {
        long mWhenToWake;

        TimedParkStateNode() {
            super(3);
        }
    }

    private static final class MessageCounts {
        private static final long AWAKE = Long.MAX_VALUE;
        private static final int MESSAGE_FLUSH_THRESHOLD = 10;
        private static VarHandle sCounts;
        private volatile long mCountsValue;

        private static long combineCounts(int i, int i2) {
            return i2 | (i << 32);
        }

        private static int numCancelled(long j) {
            return (int) j;
        }

        private static int numQueued(long j) {
            return (int) (j >>> 32);
        }

        private MessageCounts() {
            this.mCountsValue = 0L;
        }

        static {
            try {
                sCounts = MethodHandles.lookup().findVarHandle(MessageCounts.class, "mCountsValue", Long.TYPE);
            } catch (Exception e) {
                Log.wtf(MessageQueue.TAG_C, "VarHandle lookup failed with exception: " + e);
                throw new ExceptionInInitializerError(e);
            }
        }

        public void incrementQueued() {
            while (true) {
                long j = this.mCountsValue;
                int numQueued = numQueued(j);
                long combineCounts = combineCounts(Math.max(numQueued + 1, numQueued), numCancelled(j));
                if (j == Long.MAX_VALUE) {
                    return;
                }
                MessageCounts messageCounts = this;
                if ((boolean) sCounts.compareAndSet(messageCounts, j, combineCounts)) {
                    return;
                } else {
                    this = messageCounts;
                }
            }
        }

        public boolean incrementCancelled() {
            while (true) {
                long j = this.mCountsValue;
                boolean z = false;
                if (j == Long.MAX_VALUE) {
                    return false;
                }
                int numQueued = numQueued(j);
                int numCancelled = numCancelled(j);
                if (numQueued > 10 && (numQueued >> 1) < numCancelled) {
                    z = true;
                }
                boolean z2 = z;
                MessageCounts messageCounts = this;
                if ((boolean) sCounts.compareAndSet(messageCounts, j, z2 ? Long.MAX_VALUE : combineCounts(numQueued, Math.max(numCancelled + 1, numCancelled)))) {
                    return z2;
                }
                this = messageCounts;
            }
        }

        public void clearCounts() {
            this.mCountsValue = 0L;
        }
    }

    private boolean enqueueMessageUnchecked(Message message, long j) {
        long andAdd;
        VarHandle varHandle;
        StackNode stackNode;
        boolean z;
        boolean z2;
        if ((boolean) sQuitting.getVolatile(this)) {
            IllegalStateException illegalStateException = new IllegalStateException(message.target + " sending message to a Handler on a dead thread");
            Log.w(TAG_C, illegalStateException.getMessage(), illegalStateException);
            message.recycleUnchecked();
            return false;
        }
        if (j != 0) {
            andAdd = (long) sNextInsertSeq.getAndAdd(this, 1L) + 1;
        } else {
            andAdd = (long) sNextFrontInsertSeq.getAndAdd(this, -1L) - 1;
        }
        MessageNode messageNode = new MessageNode(message, andAdd);
        message.when = j;
        message.markInUse();
        incAndTraceMessageCount(message, j);
        Looper myLooper = Looper.myLooper();
        if (myLooper != null && myLooper.getQueue() == this) {
            messageNode.removeFromStack();
            insertIntoPriorityQueue(messageNode);
            if (!this.mMessageDirectlyQueued) {
                this.mMessageDirectlyQueued = true;
                nativeWake(this.mPtr);
            }
            return true;
        }
        do {
            varHandle = sState;
            stackNode = (StackNode) varHandle.getVolatile(this);
            messageNode.mNext = stackNode;
            int nodeType = stackNode.getNodeType();
            if (nodeType == 1) {
                messageNode.mBottomOfStack = (StateNode) stackNode;
                messageNode.mWokeUp = true;
                z = false;
            } else if (nodeType == 2) {
                messageNode.mBottomOfStack = (StateNode) stackNode;
                messageNode.mWokeUp = true;
                z = true;
            } else if (nodeType == 3) {
                messageNode.mBottomOfStack = (StateNode) stackNode;
                z2 = this.mStackStateTimedPark.mWhenToWake >= messageNode.getWhen();
                messageNode.mWokeUp = z2;
                z = true;
            } else {
                MessageNode messageNode2 = (MessageNode) stackNode;
                messageNode.mBottomOfStack = messageNode2.mBottomOfStack;
                int nodeType2 = messageNode.mBottomOfStack.getNodeType();
                z = nodeType2 >= 2;
                z2 = nodeType2 == 3 && this.mStackStateTimedPark.mWhenToWake >= messageNode.getWhen() && !messageNode2.mWokeUp;
                messageNode.mWokeUp = messageNode2.mWokeUp || z2;
            }
            z2 = z;
        } while (!(boolean) varHandle.compareAndSet(this, stackNode, messageNode));
        if (z) {
            if (z2) {
                nativeWake(this.mPtr);
            } else {
                this.mMessageCounts.incrementQueued();
            }
        }
        return true;
    }

    private static abstract class MessageCompare {
        public abstract boolean compareMessage(MessageNode messageNode, Handler handler, int i, Object obj, Runnable runnable, long j);

        private MessageCompare() {
        }
    }

    private boolean stackHasMessages(Handler handler, int i, Object obj, Runnable runnable, long j, MessageCompare messageCompare, boolean z) {
        boolean z2;
        StackNode stackNode = (StackNode) sState.getVolatile(this);
        boolean z3 = false;
        if (stackNode == getStateNode(stackNode)) {
            waitForDrainCompleted();
            return false;
        }
        do {
            MessageNode messageNode = (MessageNode) stackNode;
            if (messageCompare.compareMessage(messageNode, handler, i, obj, runnable, j)) {
                z2 = true;
                if (!z) {
                    break;
                }
                if (messageNode.removeFromStack()) {
                    messageNode.mMessage.recycleUnchecked();
                    decAndTraceMessageCount();
                    if (this.mMessageCounts.incrementCancelled()) {
                        nativeWake(this.mPtr);
                    }
                }
                z3 = true;
            }
            stackNode = messageNode.mNext;
            if (stackNode == null) {
                break;
            }
        } while (stackNode.isMessageNode());
        z2 = z3;
        waitForDrainCompleted();
        return z2;
    }

    private boolean priorityQueueHasMessage(ConcurrentSkipListSet<MessageNode> concurrentSkipListSet, Handler handler, int i, Object obj, Runnable runnable, long j, MessageCompare messageCompare, boolean z) {
        Iterator<MessageNode> it = concurrentSkipListSet.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            MessageNode next = it.next();
            if (messageCompare.compareMessage(next, handler, i, obj, runnable, j)) {
                z2 = true;
                if (!z) {
                    break;
                }
                if (concurrentSkipListSet.remove(next)) {
                    next.mMessage.recycleUnchecked();
                    decAndTraceMessageCount();
                }
            }
        }
        return z2;
    }

    private boolean findOrRemoveMessages(Handler handler, int i, Object obj, Runnable runnable, long j, MessageCompare messageCompare, boolean z) {
        return stackHasMessages(handler, i, obj, runnable, j, messageCompare, z) || priorityQueueHasMessage(this.mAsyncPriorityQueue, handler, i, obj, runnable, j, messageCompare, z) || priorityQueueHasMessage(this.mPriorityQueue, handler, i, obj, runnable, j, messageCompare, z);
    }
}
