package android.os;

import android.app.ActivityThread;
import android.app.Instrumentation;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Printer;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.samsung.android.common.AsPackageName;
import com.samsung.android.rune.ViewRune;
import dalvik.annotation.optimization.NeverCompile;
import java.io.File;
import java.io.FileDescriptor;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes3.dex */
public final class MessageQueue {
    private static final boolean DEBUG = false;
    private static final int DEBUG_LEVEL_HIGH = 2;
    private static final int DEBUG_LEVEL_LOW = 0;
    private static final int DEBUG_LEVEL_MID = 1;
    private static final int DEBUG_PRODUCT_NO_SHIP = 1;
    private static final int DEBUG_PRODUCT_SHIP = 0;
    private static final int NOT_INITIALIZED = -1;
    private static final int STACK_NODE_ACTIVE = 1;
    private static final int STACK_NODE_MESSAGE = 0;
    private static final int STACK_NODE_PARKED = 2;
    private static final int STACK_NODE_TIMEDPARK = 3;
    private static final String TAG_C = "ConcurrentMessageQueue";
    private static final String TAG_L = "LegacyMessageQueue";
    private static final boolean TRACE = false;
    private static int debugLevel = -1;
    private static boolean mIsMainThread = false;
    private static boolean mIsSystemUI = false;
    private static Boolean sIsProcessAllowedToUseConcurrent = null;
    private static final VarHandle sNextFrontInsertSeq;
    private static final VarHandle sNextInsertSeq;
    private static final VarHandle sQuitting;
    private static final VarHandle sState;
    private static int shipBuild = -1;
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
    private final Map<Integer, RuntimeException> mRemainBarriers = new HashMap();
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

    MessageQueue(boolean z) throws ClassNotFoundException {
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
        if (mIsSystemUI && Process.myTid() == Process.myPid()) {
            mIsMainThread = true;
        }
    }

    private static void initIsProcessAllowedToUseConcurrent() throws ClassNotFoundException {
        if (sIsProcessAllowedToUseConcurrent != null) {
            return;
        }
        boolean z = false;
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
        String strMyProcessName = Process.myProcessName();
        if (strMyProcessName == null) {
            sIsProcessAllowedToUseConcurrent = false;
            return;
        }
        Boolean boolValueOf = Boolean.valueOf(UserHandle.isCore(Process.myUid()));
        sIsProcessAllowedToUseConcurrent = boolValueOf;
        if (boolValueOf.booleanValue()) {
            if (strMyProcessName.contains("test") || strMyProcessName.contains("Test")) {
                sIsProcessAllowedToUseConcurrent = false;
                return;
            }
            return;
        }
        if (isDebuggable() && (strMyProcessName.equals(AsPackageName.SYSTEMUI) || strMyProcessName.startsWith("com.android.systemui:"))) {
            z = true;
        }
        sIsProcessAllowedToUseConcurrent = Boolean.valueOf(z);
        mIsSystemUI = strMyProcessName.equals(AsPackageName.SYSTEMUI);
    }

    private static void throwIfNotTest() {
        Instrumentation instrumentation;
        ActivityThread activityThreadCurrentActivityThread = ActivityThread.currentActivityThread();
        if (activityThreadCurrentActivityThread != null && (instrumentation = activityThreadCurrentActivityThread.getInstrumentation()) != null && !instrumentation.isInstrumenting()) {
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

    private boolean isIdleConcurrent() {
        MessageNode messageNodeFirst;
        long jUptimeMillis = SystemClock.uptimeMillis();
        if (stackHasMessages(null, 0, null, null, jUptimeMillis, this.mMatchDeliverableMessages, false)) {
            return false;
        }
        MessageNode messageNodeFirst2 = null;
        if (this.mPriorityQueue.isEmpty()) {
            messageNodeFirst = null;
        } else {
            try {
                messageNodeFirst = this.mPriorityQueue.first();
            } catch (NoSuchElementException unused) {
            }
        }
        if (!this.mAsyncPriorityQueue.isEmpty()) {
            try {
                messageNodeFirst2 = this.mAsyncPriorityQueue.first();
            } catch (NoSuchElementException unused2) {
            }
        }
        return (messageNodeFirst == null || messageNodeFirst.getWhen() > jUptimeMillis) && (messageNodeFirst2 == null || messageNodeFirst2.getWhen() > jUptimeMillis);
    }

    private boolean isIdleLegacy() {
        boolean z;
        synchronized (this) {
            long jUptimeMillis = SystemClock.uptimeMillis();
            Message message = this.mMessages;
            z = message == null || jUptimeMillis < message.when;
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
        boolean zIsPollingLocked;
        synchronized (this) {
            zIsPollingLocked = isPollingLocked();
        }
        return zIsPollingLocked;
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
        int iIndexOfKey;
        int int$ = fileDescriptor.getInt$();
        SparseArray<FileDescriptorRecord> sparseArray = this.mFileDescriptorRecords;
        FileDescriptorRecord fileDescriptorRecordValueAt = null;
        if (sparseArray != null) {
            iIndexOfKey = sparseArray.indexOfKey(int$);
            if (iIndexOfKey >= 0 && (fileDescriptorRecordValueAt = this.mFileDescriptorRecords.valueAt(iIndexOfKey)) != null && fileDescriptorRecordValueAt.mEvents == i) {
                return;
            }
        } else {
            iIndexOfKey = -1;
        }
        if (i == 0) {
            if (fileDescriptorRecordValueAt != null) {
                fileDescriptorRecordValueAt.mEvents = 0;
                this.mFileDescriptorRecords.removeAt(iIndexOfKey);
                nativeSetFileDescriptorEvents(this.mPtr, int$, 0);
                return;
            }
            return;
        }
        int i2 = i | 4;
        if (fileDescriptorRecordValueAt == null) {
            if (this.mFileDescriptorRecords == null) {
                this.mFileDescriptorRecords = new SparseArray<>();
            }
            this.mFileDescriptorRecords.put(int$, new FileDescriptorRecord(fileDescriptor, i2, onFileDescriptorEventListener));
        } else {
            fileDescriptorRecordValueAt.mListener = onFileDescriptorEventListener;
            fileDescriptorRecordValueAt.mEvents = i2;
            fileDescriptorRecordValueAt.mSeq++;
        }
        nativeSetFileDescriptorEvents(this.mPtr, int$, i2);
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
        int iOnFileDescriptorEvents = onFileDescriptorEventListener.onFileDescriptorEvents(fileDescriptorRecord.mDescriptor, i4);
        if (iOnFileDescriptorEvents != 0) {
            iOnFileDescriptorEvents |= 4;
        }
        if (iOnFileDescriptorEvents == i3) {
            return iOnFileDescriptorEvents;
        }
        if (this.mUseConcurrent) {
            synchronized (this.mFileDescriptorRecordsLock) {
                int iIndexOfKey = this.mFileDescriptorRecords.indexOfKey(i);
                if (iIndexOfKey >= 0 && this.mFileDescriptorRecords.valueAt(iIndexOfKey) == fileDescriptorRecord && fileDescriptorRecord.mSeq == i5) {
                    fileDescriptorRecord.mEvents = iOnFileDescriptorEvents;
                    if (iOnFileDescriptorEvents == 0) {
                        this.mFileDescriptorRecords.removeAt(iIndexOfKey);
                    }
                }
            }
            return iOnFileDescriptorEvents;
        }
        synchronized (this) {
            int iIndexOfKey2 = this.mFileDescriptorRecords.indexOfKey(i);
            if (iIndexOfKey2 >= 0 && this.mFileDescriptorRecords.valueAt(iIndexOfKey2) == fileDescriptorRecord && fileDescriptorRecord.mSeq == i5) {
                fileDescriptorRecord.mEvents = iOnFileDescriptorEvents;
                if (iOnFileDescriptorEvents == 0) {
                    this.mFileDescriptorRecords.removeAt(iIndexOfKey2);
                }
            }
        }
        return iOnFileDescriptorEvents;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0054 A[PHI: r2
      0x0054: PHI (r2v5 android.os.MessageQueue$MessageNode) = 
      (r2v3 android.os.MessageQueue$MessageNode)
      (r2v2 android.os.MessageQueue$MessageNode)
      (r2v2 android.os.MessageQueue$MessageNode)
     binds: [B:16:0x0065, B:6:0x0047, B:9:0x0051] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0067 A[PHI: r2
      0x0067: PHI (r2v8 android.os.MessageQueue$MessageNode) = 
      (r2v3 android.os.MessageQueue$MessageNode)
      (r2v3 android.os.MessageQueue$MessageNode)
      (r2v2 android.os.MessageQueue$MessageNode)
      (r2v2 android.os.MessageQueue$MessageNode)
     binds: [B:14:0x005d, B:16:0x0065, B:7:0x0049, B:9:0x0051] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Message nextMessage(boolean z, boolean z2) {
        MessageNode messageNodeIterateNext;
        MessageNode messageNode;
        StateNode stateNode;
        while (true) {
            this.mDrainingLock.lock();
            this.mNextIsDrainingStack = true;
            this.mDrainingLock.unlock();
            drainStack(swapAndSetStackStateActive());
            this.mDrainingLock.lock();
            this.mNextIsDrainingStack = false;
            this.mDrainCompleted.signalAll();
            this.mDrainingLock.unlock();
            MessageNode messageNodeIterateNext2 = iterateNext(this.mPriorityQueue.iterator());
            messageNodeIterateNext = iterateNext(this.mAsyncPriorityQueue.iterator());
            long jUptimeMillis = SystemClock.uptimeMillis();
            if (messageNodeIterateNext2 == null || !messageNodeIterateNext2.isBarrier()) {
                messageNodeIterateNext = pickEarliestNode(messageNodeIterateNext2, messageNodeIterateNext);
                if (messageNodeIterateNext == null) {
                    messageNode = null;
                    messageNodeIterateNext = null;
                } else if (z2 || jUptimeMillis >= messageNodeIterateNext.getWhen()) {
                    messageNode = null;
                } else {
                    messageNode = messageNodeIterateNext;
                    messageNodeIterateNext = null;
                }
            } else if (messageNodeIterateNext == null || (!z2 && jUptimeMillis < messageNodeIterateNext.getWhen())) {
            }
            StateNode stateNode2 = sStackStateActive;
            if (messageNodeIterateNext != null) {
                stateNode = stateNode2;
            } else if (messageNode == null) {
                this.mNextPollTimeoutMillis = -1;
                stateNode = sStackStateParked;
            } else {
                long when = messageNode.getWhen();
                if (when > jUptimeMillis) {
                    this.mNextPollTimeoutMillis = (int) Math.min(when - jUptimeMillis, 2147483647L);
                } else {
                    this.mNextPollTimeoutMillis = 0;
                }
                this.mStackStateTimedPark.mWhenToWake = jUptimeMillis + this.mNextPollTimeoutMillis;
                stateNode = this.mStackStateTimedPark;
            }
            if ((boolean) sState.compareAndSet(this, stateNode2, stateNode)) {
                this.mMessageCounts.clearCounts();
                if (messageNodeIterateNext == null) {
                    return null;
                }
                if (z || removeFromPriorityQueue(messageNodeIterateNext)) {
                    break;
                }
            }
        }
        return messageNodeIterateNext.mMessage;
    }

    private static void makeHeapdump() {
        String str = "/data/log/core/" + String.format("%s_%d_%s.hprof", "BARRIER", Integer.valueOf(Process.myPid()), new SimpleDateFormat("yyMMdd_HHmmss").format(new Date()));
        try {
            Log.e(TAG_C, "@@@### GET HEAPDUMP FOR BARRIER : " + str);
            Debug.dumpHprofData(str);
        } catch (Exception e) {
            Log.e(TAG_C, "@@@### Exception : " + e.getMessage());
            e.printStackTrace();
            Log.d(TAG_C, "@@@### heapDumpFilePath= " + str + ", canWrite= " + new File(str).canWrite());
        }
    }

    private Message nextConcurrent() {
        boolean zQueueIdle;
        long j = this.mPtr;
        if (j == 0) {
            return null;
        }
        this.mNextPollTimeoutMillis = 0;
        int size = -1;
        while (true) {
            if (this.mNextPollTimeoutMillis != 0) {
                Binder.flushPendingCommands();
            }
            this.mMessageDirectlyQueued = false;
            nativePollOnce(j, this.mNextPollTimeoutMillis);
            Message messageNextMessage = nextMessage(false, false);
            if (messageNextMessage != null) {
                messageNextMessage.markInUse();
                decAndTraceMessageCount();
                return messageNextMessage;
            }
            if ((boolean) sQuitting.getVolatile(this)) {
                return null;
            }
            synchronized (this.mIdleHandlersLock) {
                if (size < 0) {
                    try {
                        if (isIdle()) {
                            size = this.mIdleHandlers.size();
                        }
                    } finally {
                    }
                }
                if (size > 0) {
                    if (this.mPendingIdleHandlers == null) {
                        this.mPendingIdleHandlers = new IdleHandler[Math.max(size, 4)];
                    }
                    this.mPendingIdleHandlers = (IdleHandler[]) this.mIdleHandlers.toArray(this.mPendingIdleHandlers);
                    for (int i = 0; i < size; i++) {
                        IdleHandler[] idleHandlerArr = this.mPendingIdleHandlers;
                        IdleHandler idleHandler = idleHandlerArr[i];
                        idleHandlerArr[i] = null;
                        try {
                            zQueueIdle = idleHandler.queueIdle();
                        } catch (Throwable th) {
                            Log.wtf(TAG_C, "IdleHandler threw exception", th);
                            zQueueIdle = false;
                        }
                        if (!zQueueIdle) {
                            synchronized (this.mIdleHandlersLock) {
                                this.mIdleHandlers.remove(idleHandler);
                            }
                        }
                    }
                    this.mNextPollTimeoutMillis = 0;
                    size = 0;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x00b9, code lost:
    
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00ba, code lost:
    
        if (r8 >= r7) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00bc, code lost:
    
        r0 = r17.mPendingIdleHandlers;
        r9 = r0[r8];
        r0[r8] = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00c2, code lost:
    
        r0 = r9.queueIdle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00c7, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00c8, code lost:
    
        android.util.Log.wtf(android.os.MessageQueue.TAG_L, "IdleHandler threw exception", r0);
        r0 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x00e0, code lost:
    
        r0 = 0;
        r7 = 0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Message nextLegacy() {
        Message message;
        IdleHandler idleHandler;
        boolean zQueueIdle;
        Message message2;
        Message message3;
        long j = this.mPtr;
        if (j == 0) {
            return null;
        }
        int size = -1;
        int iMin = 0;
        while (true) {
            if (iMin != 0) {
                Binder.flushPendingCommands();
            }
            nativePollOnce(j, iMin);
            synchronized (this) {
                long jUptimeMillis = SystemClock.uptimeMillis();
                Message message4 = this.mMessages;
                if (message4 == null || message4.target != null) {
                    message = null;
                } else {
                    while (true) {
                        message3 = message4.next;
                        if (message3 == null || message3.isAsynchronous()) {
                            break;
                        }
                        message4 = message3;
                    }
                    message = message4;
                    message4 = message3;
                }
                if (message4 == null) {
                    iMin = -1;
                } else if (jUptimeMillis < message4.when) {
                    iMin = (int) Math.min(message4.when - jUptimeMillis, 2147483647L);
                } else {
                    this.mBlocked = false;
                    if (message != null) {
                        message.next = message4.next;
                        if (message.next == null) {
                            this.mLast = message;
                        }
                    } else {
                        this.mMessages = message4.next;
                        if (message4.next == null) {
                            this.mLast = null;
                        }
                    }
                    message4.next = null;
                    message4.markInUse();
                    if (message4.isAsynchronous()) {
                        this.mAsyncMessageCount--;
                    }
                    decAndTraceMessageCount();
                    return message4;
                }
                if (this.mQuitting) {
                    dispose();
                    return null;
                }
                if (size < 0 && ((message2 = this.mMessages) == null || jUptimeMillis < message2.when)) {
                    size = this.mIdleHandlers.size();
                }
                if (size <= 0) {
                    this.mBlocked = true;
                } else {
                    if (this.mPendingIdleHandlers == null) {
                        this.mPendingIdleHandlers = new IdleHandler[Math.max(size, 4)];
                    }
                    this.mPendingIdleHandlers = (IdleHandler[]) this.mIdleHandlers.toArray(this.mPendingIdleHandlers);
                }
            }
        }
        if (!zQueueIdle) {
            synchronized (this) {
                this.mIdleHandlers.remove(idleHandler);
            }
        }
        int i = i + 1;
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
            Message messageObtain = Message.obtain();
            messageObtain.markInUse();
            messageObtain.arg1 = andIncrement;
            if (isDebugableForSystemUI()) {
                Log.d(TAG_C, "@@@### postSyncBarrier token : " + andIncrement);
            }
            if (!enqueueMessageUnchecked(messageObtain, j)) {
                Log.wtf(TAG_C, "Unexpected error while adding sync barrier!");
                return -1;
            }
            if (isDebugableForSystemUI()) {
                this.mRemainBarriers.put(Integer.valueOf(andIncrement), new RuntimeException());
            }
            return andIncrement;
        }
        synchronized (this) {
            int i = this.mNextBarrierToken;
            this.mNextBarrierToken = i + 1;
            Message messageObtain2 = Message.obtain();
            messageObtain2.markInUse();
            messageObtain2.when = j;
            messageObtain2.arg1 = i;
            Message message2 = null;
            if (Flags.messageQueueTailTracking() && (message = this.mLast) != null && message.when <= j) {
                this.mLast.next = messageObtain2;
                this.mLast = messageObtain2;
                messageObtain2.next = null;
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
                this.mLast = messageObtain2;
            }
            if (message2 != null) {
                messageObtain2.next = message3;
                message2.next = messageObtain2;
            } else {
                messageObtain2.next = message3;
                this.mMessages = messageObtain2;
            }
            return i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isDebugableForSystemUI() {
        return isDebuggable() && mIsSystemUI && mIsMainThread;
    }

    public static int getDebugLevel() {
        if (debugLevel == -1) {
            String str = SystemProperties.get("ro.boot.debug_level", "");
            if (TextUtils.isEmpty(str)) {
                str = SystemProperties.get("ro.debug_level", "");
            }
            if (TextUtils.isEmpty(str) || "0x4f4c".equalsIgnoreCase(str)) {
                debugLevel = 0;
            } else if (ViewRune.DEBUG_LEVEL_MID.equalsIgnoreCase(str)) {
                debugLevel = 1;
            } else if ("0x4948".equalsIgnoreCase(str)) {
                debugLevel = 2;
            }
        }
        return debugLevel;
    }

    public static boolean isShipBuild() {
        if (shipBuild == -1) {
            shipBuild = !"true".equals(SystemProperties.get("ro.product_ship", "false")) ? 1 : 0;
        }
        return shipBuild == 0;
    }

    public static boolean isDebuggable() {
        return !isShipBuild() && getDebugLevel() == 1;
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
        MessageNode messageNodeFirst;
        MatchBarrierToken matchBarrierToken = new MatchBarrierToken(i);
        if (isDebugableForSystemUI()) {
            Log.d(TAG_C, "@@@### removeSyncBarrierConcurrent token : " + i);
        }
        try {
            messageNodeFirst = this.mPriorityQueue.first();
        } catch (NoSuchElementException unused) {
            messageNodeFirst = null;
        }
        MessageNode messageNode = messageNodeFirst;
        boolean zFindOrRemoveMessages = findOrRemoveMessages(null, 0, null, null, 0L, matchBarrierToken, true);
        if (zFindOrRemoveMessages && messageNode != null) {
            Message message = messageNode.mMessage;
            if (message.target == null && message.arg1 == i) {
                nativeWake(this.mPtr);
            }
        } else if (!zFindOrRemoveMessages) {
            if (isDebugableForSystemUI()) {
                Log.w(TAG_C, "@@@### /// removeSyncBarrierConcurrent findOrRemoveMessages returns FALSE / token : " + i);
                printRemainBarrierInfo();
                makeHeapdump();
            }
            throw new IllegalStateException("The specified message queue synchronization  barrier token has not been posted or has already been removed.");
        }
        if (isDebugableForSystemUI()) {
            this.mRemainBarriers.remove(Integer.valueOf(i));
        }
    }

    private void printRemainBarrierInfo() {
        for (Map.Entry<Integer, RuntimeException> entry : this.mRemainBarriers.entrySet()) {
            Log.w(TAG_C, "@@@### ///  REMAIN BARRIER msg / token : " + entry.getKey());
            for (StackTraceElement stackTraceElement : entry.getValue().getStackTrace()) {
                Log.w(TAG_C, "@@@### /// at " + stackTraceElement.toString());
            }
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

    private boolean enqueueMessageLegacy(Message message, long j) {
        Message message2;
        Message message3;
        synchronized (this) {
            if (message.isInUse()) {
                throw new IllegalStateException(message + " This message is already in use.");
            }
            boolean z = false;
            if (this.mQuitting) {
                IllegalStateException illegalStateException = new IllegalStateException(message.target + " sending message to a Handler on a dead thread");
                Log.w(TAG_L, illegalStateException.getMessage(), illegalStateException);
                message.recycle();
                return false;
            }
            message.markInUse();
            message.when = j;
            incAndTraceMessageCount(message, j);
            Message message4 = this.mMessages;
            if (message4 == null || j == 0 || j < message4.when) {
                message.next = message4;
                this.mMessages = message;
                z = this.mBlocked;
                if (message4 == null) {
                    this.mLast = message;
                }
            } else {
                boolean z2 = this.mBlocked && message4.target == null && message.isAsynchronous();
                if (!Flags.messageQueueTailTracking()) {
                    while (true) {
                        message2 = message4.next;
                        if (message2 == null || j < message2.when) {
                            break;
                        }
                        if (z2 && message2.isAsynchronous()) {
                            z2 = false;
                        }
                        message4 = message2;
                    }
                    message.next = message2;
                    message4.next = message;
                    this.mLast = null;
                } else if (j >= this.mLast.when) {
                    if (z2 && this.mAsyncMessageCount == 0) {
                        z = true;
                    }
                    message.next = null;
                    this.mLast.next = message;
                    this.mLast = message;
                } else {
                    while (true) {
                        message3 = message4.next;
                        if (message3 == null || j < message3.when) {
                            break;
                        }
                        if (z2 && message3.isAsynchronous()) {
                            z2 = false;
                        }
                        message4 = message3;
                    }
                    if (message3 == null) {
                        this.mLast = message;
                    }
                    message.next = message3;
                    message4.next = message;
                }
                z = z2;
            }
            if (message.isAsynchronous()) {
                this.mAsyncMessageCount++;
            }
            if (z) {
                nativeWake(this.mPtr);
            }
            return true;
        }
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
            long jUptimeMillis = SystemClock.uptimeMillis();
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
            if (jUptimeMillis >= message3.when) {
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
        Message messageLegacyPeekOrPoll;
        throwIfNotTest();
        if (this.mUseConcurrent) {
            messageLegacyPeekOrPoll = nextMessage(true, true);
        } else {
            messageLegacyPeekOrPoll = legacyPeekOrPoll(true);
        }
        if (messageLegacyPeekOrPoll != null) {
            return Long.valueOf(messageLegacyPeekOrPoll.when);
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
            MessageNode messageNodeIterateNext = iterateNext(this.mPriorityQueue.iterator());
            return messageNodeIterateNext != null && messageNodeIterateNext.isBarrier();
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

    private void removeMessagesLegacy(Handler handler, int i, Object obj) {
        synchronized (this) {
            Message message = this.mMessages;
            while (message != null && message.target == handler && message.what == i && (obj == null || message.obj == obj)) {
                Message message2 = message.next;
                this.mMessages = message2;
                if (message.isAsynchronous()) {
                    this.mAsyncMessageCount--;
                }
                message.recycleUnchecked();
                decAndTraceMessageCount();
                message = message2;
            }
            if (message == null) {
                this.mLast = this.mMessages;
            }
            while (message != null) {
                Message message3 = message.next;
                if (message3 != null && message3.target == handler && message3.what == i && (obj == null || message3.obj == obj)) {
                    Message message4 = message3.next;
                    if (message3.isAsynchronous()) {
                        this.mAsyncMessageCount--;
                    }
                    message3.recycleUnchecked();
                    decAndTraceMessageCount();
                    message.next = message4;
                    if (message.next == null) {
                        this.mLast = message;
                    }
                } else {
                    message = message3;
                }
            }
        }
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

    private void removeEqualMessagesLegacy(Handler handler, int i, Object obj) {
        synchronized (this) {
            Message message = this.mMessages;
            while (message != null && message.target == handler && message.what == i && (obj == null || obj.equals(message.obj))) {
                Message message2 = message.next;
                this.mMessages = message2;
                if (message.isAsynchronous()) {
                    this.mAsyncMessageCount--;
                }
                message.recycleUnchecked();
                message = message2;
            }
            if (message == null) {
                this.mLast = this.mMessages;
            }
            while (message != null) {
                Message message3 = message.next;
                if (message3 != null && message3.target == handler && message3.what == i && (obj == null || obj.equals(message3.obj))) {
                    Message message4 = message3.next;
                    if (message3.isAsynchronous()) {
                        this.mAsyncMessageCount--;
                    }
                    message3.recycleUnchecked();
                    decAndTraceMessageCount();
                    message.next = message4;
                    if (message.next == null) {
                        this.mLast = message;
                    }
                } else {
                    message = message3;
                }
            }
        }
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

    private void removeMessagesLegacy(Handler handler, Runnable runnable, Object obj) {
        synchronized (this) {
            Message message = this.mMessages;
            while (message != null && message.target == handler && message.callback == runnable && (obj == null || message.obj == obj)) {
                Message message2 = message.next;
                this.mMessages = message2;
                if (message.isAsynchronous()) {
                    this.mAsyncMessageCount--;
                }
                message.recycleUnchecked();
                decAndTraceMessageCount();
                message = message2;
            }
            if (message == null) {
                this.mLast = this.mMessages;
            }
            while (message != null) {
                Message message3 = message.next;
                if (message3 != null && message3.target == handler && message3.callback == runnable && (obj == null || message3.obj == obj)) {
                    Message message4 = message3.next;
                    if (message3.isAsynchronous()) {
                        this.mAsyncMessageCount--;
                    }
                    message3.recycleUnchecked();
                    decAndTraceMessageCount();
                    message.next = message4;
                    if (message.next == null) {
                        this.mLast = message;
                    }
                } else {
                    message = message3;
                }
            }
        }
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

    private void removeEqualMessagesLegacy(Handler handler, Runnable runnable, Object obj) {
        synchronized (this) {
            Message message = this.mMessages;
            while (message != null && message.target == handler && message.callback == runnable && (obj == null || obj.equals(message.obj))) {
                Message message2 = message.next;
                this.mMessages = message2;
                if (message.isAsynchronous()) {
                    this.mAsyncMessageCount--;
                }
                message.recycleUnchecked();
                decAndTraceMessageCount();
                message = message2;
            }
            if (message == null) {
                this.mLast = this.mMessages;
            }
            while (message != null) {
                Message message3 = message.next;
                if (message3 != null && message3.target == handler && message3.callback == runnable && (obj == null || obj.equals(message3.obj))) {
                    Message message4 = message3.next;
                    if (message3.isAsynchronous()) {
                        this.mAsyncMessageCount--;
                    }
                    message3.recycleUnchecked();
                    decAndTraceMessageCount();
                    message.next = message4;
                    if (message.next == null) {
                        this.mLast = message;
                    }
                } else {
                    message = message3;
                }
            }
        }
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

    private void removeCallbacksAndMessagesLegacy(Handler handler, Object obj) {
        synchronized (this) {
            Message message = this.mMessages;
            while (message != null && message.target == handler && (obj == null || message.obj == obj)) {
                Message message2 = message.next;
                this.mMessages = message2;
                if (message.isAsynchronous()) {
                    this.mAsyncMessageCount--;
                }
                message.recycleUnchecked();
                decAndTraceMessageCount();
                message = message2;
            }
            if (message == null) {
                this.mLast = this.mMessages;
            }
            while (message != null) {
                Message message3 = message.next;
                if (message3 != null && message3.target == handler && (obj == null || message3.obj == obj)) {
                    Message message4 = message3.next;
                    if (message3.isAsynchronous()) {
                        this.mAsyncMessageCount--;
                    }
                    message3.recycleUnchecked();
                    decAndTraceMessageCount();
                    message.next = message4;
                    if (message.next == null) {
                        this.mLast = message;
                    }
                } else {
                    message = message3;
                }
            }
        }
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

    void removeCallbacksAndEqualMessagesLegacy(Handler handler, Object obj) {
        synchronized (this) {
            Message message = this.mMessages;
            while (message != null && message.target == handler && (obj == null || obj.equals(message.obj))) {
                Message message2 = message.next;
                this.mMessages = message2;
                if (message.isAsynchronous()) {
                    this.mAsyncMessageCount--;
                }
                message.recycleUnchecked();
                decAndTraceMessageCount();
                message = message2;
            }
            if (message == null) {
                this.mLast = this.mMessages;
            }
            while (message != null) {
                Message message3 = message.next;
                if (message3 != null && message3.target == handler && (obj == null || obj.equals(message3.obj))) {
                    Message message4 = message3.next;
                    if (message3.isAsynchronous()) {
                        this.mAsyncMessageCount--;
                    }
                    message3.recycleUnchecked();
                    decAndTraceMessageCount();
                    message.next = message4;
                    if (message.next == null) {
                        this.mLast = message;
                    }
                } else {
                    message = message3;
                }
            }
        }
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
        long jUptimeMillis = SystemClock.uptimeMillis();
        Message message = this.mMessages;
        if (message == null) {
            return;
        }
        if (message.when > jUptimeMillis) {
            removeAllMessagesLocked();
            return;
        }
        while (true) {
            Message message2 = message.next;
            if (message2 == null) {
                return;
            }
            if (message2.when > jUptimeMillis) {
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
        long jUptimeMillis = SystemClock.uptimeMillis();
        Iterator<MessageNode> it = concurrentSkipListSet.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            Message message = it.next().mMessage;
            if (handler == null || handler == message.target) {
                printer.println(str + "Message " + (i + i2) + ": " + message.toString(jUptimeMillis));
            }
            i2++;
        }
        return i2;
    }

    @NeverCompile
    void dump(Printer printer, String str, Handler handler) {
        int i = 0;
        if (this.mUseConcurrent) {
            long jUptimeMillis = SystemClock.uptimeMillis();
            printer.println(str + "(MessageQueue is using Concurrent implementation)");
            StackNode stackNode = (StackNode) sState.getVolatile(this);
            int i2 = 0;
            while (stackNode != null) {
                if (stackNode.isMessageNode()) {
                    MessageNode messageNode = (MessageNode) stackNode;
                    Message message = messageNode.mMessage;
                    if (handler == null || handler == message.target) {
                        printer.println(str + "Message " + i2 + ": " + message.toString(jUptimeMillis));
                    }
                    stackNode = messageNode.mNext;
                } else {
                    printer.println(str + "State: " + stackNode);
                    stackNode = null;
                }
                i2++;
            }
            printer.println(str + "PriorityQueue Messages: ");
            int iDumpPriorityQueue = i2 + dumpPriorityQueue(this.mPriorityQueue, printer, str, handler, i2);
            printer.println(str + "AsyncPriorityQueue Messages: ");
            printer.println(str + "(Total messages: " + (iDumpPriorityQueue + dumpPriorityQueue(this.mAsyncPriorityQueue, printer, str, handler, iDumpPriorityQueue)) + ", polling=" + isPolling() + ", quitting=" + (boolean) sQuitting.getVolatile(this) + NavigationBarInflaterView.KEY_CODE_END);
            return;
        }
        synchronized (this) {
            printer.println(str + "(MessageQueue is using Legacy implementation)");
            long jUptimeMillis2 = SystemClock.uptimeMillis();
            for (Message message2 = this.mMessages; message2 != null; message2 = message2.next) {
                if (handler == null || handler == message2.target) {
                    printer.println(str + "Message " + i + ": " + message2.toString(jUptimeMillis2));
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
            long jStart = protoOutputStream.start(j);
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
            protoOutputStream.end(jStart);
            return;
        }
        long jStart2 = protoOutputStream.start(j);
        synchronized (this) {
            for (Message message = this.mMessages; message != null; message = message.next) {
                message.dumpDebug(protoOutputStream, 2246267895809L);
            }
            protoOutputStream.write(1133871366146L, isPollingLocked());
            protoOutputStream.write(1133871366147L, this.mQuitting);
        }
        protoOutputStream.end(jStart2);
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
            Message message = messageNode.mMessage;
            int iCompare = Long.compare(this.mMessage.when, message.when);
            if (iCompare == 0) {
                iCompare = Long.compare(this.mInsertSeq, messageNode.mInsertSeq);
            }
            if (MessageQueue.isDebugableForSystemUI() && iCompare != 0 && this == messageNode) {
                Log.w(MessageQueue.TAG_C, "!!!@@@### /// this : " + this);
                Log.w(MessageQueue.TAG_C, "!!!@@@### /// messageNode : " + messageNode);
                Log.w(MessageQueue.TAG_C, "!!!@@@### /// mInsertSeq : " + this.mInsertSeq);
                Log.w(MessageQueue.TAG_C, "!!!@@@### /// messageNode.mInsertSeq : " + messageNode.mInsertSeq);
                Log.w(MessageQueue.TAG_C, "!!!@@@### /// mMessage : " + this.mMessage);
                Log.w(MessageQueue.TAG_C, "!!!@@@### /// other : " + message);
                Log.w(MessageQueue.TAG_C, "!!!@@@### /// mMessage.when : " + this.mMessage.when);
                Log.w(MessageQueue.TAG_C, "!!!@@@### /// other.when : " + message.when);
                Log.w(MessageQueue.TAG_C, "!!!@@@### /// isBarrier() : " + isBarrier());
                StringBuilder sb = new StringBuilder("!!!@@@### /// other.target == null : ");
                sb.append(message.target == null);
                Log.w(MessageQueue.TAG_C, sb.toString());
                Log.w(MessageQueue.TAG_C, "!!!@@@### /// mMessage.arg1 : " + this.mMessage.arg1);
                Log.w(MessageQueue.TAG_C, "!!!@@@### /// other.arg1 : " + message.arg1);
                StringBuilder sb2 = new StringBuilder("!!!@@@### /// this == messageNode : ");
                sb2.append(this == messageNode);
                Log.w(MessageQueue.TAG_C, sb2.toString());
                StringBuilder sb3 = new StringBuilder("!!!@@@### /// mMessage == other : ");
                sb3.append(this.mMessage == message);
                Log.w(MessageQueue.TAG_C, sb3.toString());
            }
            if (MessageQueue.isDebugableForSystemUI() && iCompare != 0 && isBarrier() && message.target == null && this.mMessage.arg1 == message.arg1) {
                Log.w(MessageQueue.TAG_C, "@@@### /// this : " + this);
                Log.w(MessageQueue.TAG_C, "@@@### /// messageNode : " + messageNode);
                Log.w(MessageQueue.TAG_C, "@@@### /// mInsertSeq : " + this.mInsertSeq);
                Log.w(MessageQueue.TAG_C, "@@@### /// messageNode.mInsertSeq : " + messageNode.mInsertSeq);
                Log.w(MessageQueue.TAG_C, "@@@### /// mMessage : " + this.mMessage);
                Log.w(MessageQueue.TAG_C, "@@@### /// other : " + message);
                Log.w(MessageQueue.TAG_C, "@@@### /// mMessage.when : " + this.mMessage.when);
                Log.w(MessageQueue.TAG_C, "@@@### /// other.when : " + message.when);
                Log.w(MessageQueue.TAG_C, "@@@### /// mMessage.arg1 : " + this.mMessage.arg1);
                Log.w(MessageQueue.TAG_C, "@@@### /// other.arg1 : " + message.arg1);
                StringBuilder sb4 = new StringBuilder("@@@### /// this == messageNode : ");
                sb4.append(this == messageNode);
                Log.w(MessageQueue.TAG_C, sb4.toString());
                StringBuilder sb5 = new StringBuilder("@@@### /// mMessage == other : ");
                sb5.append(this.mMessage == message);
                Log.w(MessageQueue.TAG_C, sb5.toString());
            }
            return iCompare;
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
                int iNumQueued = numQueued(j);
                long jCombineCounts = combineCounts(Math.max(iNumQueued + 1, iNumQueued), numCancelled(j));
                if (j == Long.MAX_VALUE) {
                    return;
                }
                MessageCounts messageCounts = this;
                if ((boolean) sCounts.compareAndSet(messageCounts, j, jCombineCounts)) {
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
                int iNumQueued = numQueued(j);
                int iNumCancelled = numCancelled(j);
                if (iNumQueued > 10 && (iNumQueued >> 1) < iNumCancelled) {
                    z = true;
                }
                boolean z2 = z;
                MessageCounts messageCounts = this;
                if ((boolean) sCounts.compareAndSet(messageCounts, j, z2 ? Long.MAX_VALUE : combineCounts(iNumQueued, Math.max(iNumCancelled + 1, iNumCancelled)))) {
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
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper != null && looperMyLooper.getQueue() == this) {
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

    /* JADX WARN: Removed duplicated region for block: B:20:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0050 A[EDGE_INSN: B:27:0x0050->B:22:0x0050 BREAK  A[LOOP:0: B:7:0x0014->B:25:0x0055], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
                stackNode = messageNode.mNext;
                if (stackNode != null) {
                    break;
                }
            } else {
                stackNode = messageNode.mNext;
                if (stackNode != null) {
                }
            }
        } while (stackNode.isMessageNode());
        z2 = z3;
        waitForDrainCompleted();
        return z2;
    }

    private boolean priorityQueueHasMessage(ConcurrentSkipListSet<MessageNode> concurrentSkipListSet, Handler handler, int i, Object obj, Runnable runnable, long j, MessageCompare messageCompare, boolean z) {
        Iterator<MessageNode> it;
        boolean z2;
        ConcurrentSkipListSet<MessageNode> concurrentSkipListSet2 = concurrentSkipListSet;
        Iterator<MessageNode> it2 = concurrentSkipListSet2.iterator();
        boolean z3 = false;
        while (it2.hasNext()) {
            MessageNode next = it2.next();
            if (!messageCompare.compareMessage(next, handler, i, obj, runnable, j)) {
                it = it2;
            } else {
                if (!z) {
                    return true;
                }
                if (!isDebugableForSystemUI()) {
                    z3 = true;
                }
                if (concurrentSkipListSet2.remove(next)) {
                    next.mMessage.recycleUnchecked();
                    decAndTraceMessageCount();
                    if (isDebugableForSystemUI()) {
                        it = it2;
                        z3 = true;
                    }
                } else {
                    if (isDebugableForSystemUI() && next.isBarrier()) {
                        Log.w(TAG_C, "@@@### // SOMETHING WRONG??? queue : " + concurrentSkipListSet2);
                        Log.w(TAG_C, "!!!@@@### /// msg : " + next);
                        Iterator<MessageNode> it3 = concurrentSkipListSet2.iterator();
                        Iterator<MessageNode> it4 = concurrentSkipListSet2.iterator();
                        while (true) {
                            it = it2;
                            z2 = z3;
                            Iterator<MessageNode> it5 = it3;
                            Iterator<MessageNode> it6 = it4;
                            if (it3.hasNext()) {
                                MessageNode next2 = it5.next();
                                if (next2 == next) {
                                    Log.w(TAG_C, "!!!@@@### /// ===== msg is in Q =====");
                                    Log.w(TAG_C, "!!!@@@### /// compareMsgNode : " + next2);
                                    Log.w(TAG_C, "!!!@@@### /// msg.mInsertSeq : " + next.mInsertSeq);
                                    Log.w(TAG_C, "!!!@@@### /// compareMsgNode.mInsertSeq : " + next2.mInsertSeq);
                                    Log.w(TAG_C, "!!!@@@### /// msg.mMessage : " + next.mMessage);
                                    Log.w(TAG_C, "!!!@@@### /// compareMsgNode.mMessage : " + next2.mMessage);
                                    Log.w(TAG_C, "!!!@@@### /// msg.mMessage.when : " + next.mMessage.when);
                                    Log.w(TAG_C, "!!!@@@### /// compareMsgNode.mMessage.when : " + next2.mMessage.when);
                                    Log.w(TAG_C, "!!!@@@### /// msg.mMessage.target : " + next.mMessage.target);
                                    Log.w(TAG_C, "!!!@@@### /// compareMsgNode.mMessage.targe : " + next2.mMessage.target);
                                    Log.w(TAG_C, "!!!@@@### /// msg.mMessage.arg1 : " + next.mMessage.arg1);
                                    Log.w(TAG_C, "!!!@@@### /// compareMsgNode.mMessage.arg1 : " + next2.mMessage.arg1);
                                    Log.w(TAG_C, "!!!@@@### // queue.contains(compareMsgNode) : " + concurrentSkipListSet.contains(next2));
                                    Log.w(TAG_C, "!!!@@@### // queue.contains(msg) : " + concurrentSkipListSet.contains(next));
                                    StringBuilder sb = new StringBuilder("!!!@@@### // msg.mMessage == compareMsgNode.mMessage : ");
                                    sb.append(next.mMessage == next2.mMessage);
                                    Log.w(TAG_C, sb.toString());
                                    concurrentSkipListSet2 = concurrentSkipListSet;
                                } else {
                                    it2 = it;
                                    z3 = z2;
                                    it3 = it5;
                                    it4 = it6;
                                }
                            } else {
                                Log.w(TAG_C, "!!!@@@### /// ===== msg is NOT in Q? =====");
                                Log.w(TAG_C, "!!!@@@### /// msg.mInsertSeq : " + next.mInsertSeq);
                                Log.w(TAG_C, "!!!@@@### /// msg.mMessage : " + next.mMessage);
                                Log.w(TAG_C, "!!!@@@### /// msg.mMessage.when : " + next.mMessage.when);
                                Log.w(TAG_C, "!!!@@@### /// msg.mMessage.target : " + next.mMessage.target);
                                Log.w(TAG_C, "!!!@@@### /// msg.mMessage.arg1 : " + next.mMessage.arg1);
                                Log.w(TAG_C, "!!!@@@### // queue.contains(msg) : " + concurrentSkipListSet2.contains(next));
                                if (it6.hasNext()) {
                                    MessageNode next3 = it6.next();
                                    Log.w(TAG_C, "!!!@@@### /// ===== messages in Q ======");
                                    Log.w(TAG_C, "!!!@@@### /// compareMsgNode : " + next3);
                                    Log.w(TAG_C, "!!!@@@### /// compareMsgNode.mInsertSeq : " + next3.mInsertSeq);
                                    Log.w(TAG_C, "!!!@@@### /// compareMsgNode.mMessage : " + next3.mMessage);
                                    Log.w(TAG_C, "!!!@@@### /// compareMsgNode.mMessage.when : " + next3.mMessage.when);
                                    Log.w(TAG_C, "!!!@@@### /// compareMsgNode.mMessage.targe : " + next3.mMessage.target);
                                    Log.w(TAG_C, "!!!@@@### /// compareMsgNode.mMessage.arg1 : " + next3.mMessage.arg1);
                                    Log.w(TAG_C, "!!!@@@### // queue.contains(compareMsgNode) : " + concurrentSkipListSet2.contains(next3));
                                    StringBuilder sb2 = new StringBuilder("!!!@@@### // msg.mMessage == compareMsgNode.mMessage : ");
                                    sb2.append(next.mMessage == next3.mMessage);
                                    Log.w(TAG_C, sb2.toString());
                                }
                            }
                        }
                        Debug.startMethodTracing("/data/log/core/" + String.format("%s_%d_%s.trace", "BARRIER", Integer.valueOf(Process.myPid()), new SimpleDateFormat("yyMMdd_HHmmss").format(new Date())));
                        boolean zRemove = concurrentSkipListSet2.remove(next);
                        Debug.stopMethodTracing();
                        if (zRemove) {
                            printRemainBarrierInfo();
                            makeHeapdump();
                            throw new RuntimeException("BARRIER was not removed!!! but retrying succeed.");
                        }
                    }
                    z3 = z2;
                }
                it = it2;
                z2 = z3;
                z3 = z2;
            }
            it2 = it;
        }
        return z3;
    }

    private boolean findOrRemoveMessages(Handler handler, int i, Object obj, Runnable runnable, long j, MessageCompare messageCompare, boolean z) {
        MessageCompare messageCompare2;
        boolean zStackHasMessages = stackHasMessages(handler, i, obj, runnable, j, messageCompare, z);
        boolean zPriorityQueueHasMessage = priorityQueueHasMessage(this.mPriorityQueue, handler, i, obj, runnable, j, messageCompare, z);
        if (isDebugableForSystemUI() && zStackHasMessages && !zPriorityQueueHasMessage) {
            messageCompare2 = messageCompare;
            if (messageCompare2 instanceof MatchBarrierToken) {
                Log.w(TAG_C, "@@@### /// IN mPriorityQueue / foundInStack TRUE && foundInQueue IS FALSE");
            }
        } else {
            messageCompare2 = messageCompare;
        }
        return zStackHasMessages || priorityQueueHasMessage(this.mAsyncPriorityQueue, handler, i, obj, runnable, j, messageCompare2, z) || zPriorityQueueHasMessage;
    }
}
