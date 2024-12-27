package com.android.server.location.contexthub;

import android.hardware.location.IContextHubTransactionCallback;
import android.hardware.location.NanoAppBinary;
import android.os.RemoteException;
import android.util.Log;
import android.util.StatsEvent;
import android.util.StatsLog;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class ContextHubTransactionManager {
    public final ContextHubClientManager mClientManager;
    public final IContextHubWrapper mContextHubProxy;
    public final NanoAppStateManager mNanoAppStateManager;
    public final ArrayDeque mTransactionQueue = new ArrayDeque();
    public final AtomicInteger mNextAvailableId = new AtomicInteger();
    public final AtomicInteger mNextAvailableMessageSequenceNumber =
            new AtomicInteger(new Random().nextInt(1073741823));
    public final ScheduledThreadPoolExecutor mTimeoutExecutor = new ScheduledThreadPoolExecutor(1);
    public ScheduledFuture mTimeoutFuture = null;
    public final ConcurrentLinkedEvictingDeque mTransactionRecordDeque =
            new ConcurrentLinkedEvictingDeque();

    /* renamed from: com.android.server.location.contexthub.ContextHubTransactionManager$1, reason: invalid class name */
    public final class AnonymousClass1 extends ContextHubServiceTransaction {
        public final /* synthetic */ int val$contextHubId;
        public final /* synthetic */ NanoAppBinary val$nanoAppBinary;
        public final /* synthetic */ IContextHubTransactionCallback val$onCompleteCallback;

        public AnonymousClass1(
                int i,
                long j,
                String str,
                int i2,
                NanoAppBinary nanoAppBinary,
                IContextHubTransactionCallback iContextHubTransactionCallback) {
            super(i, 0, j, str);
            this.val$contextHubId = i2;
            this.val$nanoAppBinary = nanoAppBinary;
            this.val$onCompleteCallback = iContextHubTransactionCallback;
        }

        @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
        public final int onTransact() {
            try {
                return ContextHubTransactionManager.this.mContextHubProxy.loadNanoapp(
                        this.val$contextHubId, this.val$nanoAppBinary, this.mTransactionId);
            } catch (RemoteException e) {
                Log.e(
                        "ContextHubTransactionManager",
                        "RemoteException while trying to load nanoapp with ID 0x"
                                + Long.toHexString(this.val$nanoAppBinary.getNanoAppId()),
                        e);
                return 1;
            }
        }

        @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
        public final void onTransactionComplete(int i) {
            int i2;
            long nanoAppId = this.val$nanoAppBinary.getNanoAppId();
            int nanoAppVersion = this.val$nanoAppBinary.getNanoAppVersion();
            ContextHubTransactionManager.this.getClass();
            if (i != 0) {
                switch (i) {
                    case 2:
                        i2 = 2;
                        break;
                    case 3:
                        i2 = 3;
                        break;
                    case 4:
                        i2 = 4;
                        break;
                    case 5:
                        i2 = 5;
                        break;
                    case 6:
                        i2 = 6;
                        break;
                    case 7:
                        i2 = 7;
                        break;
                    case 8:
                        i2 = 8;
                        break;
                    default:
                        i2 = 1;
                        break;
                }
            } else {
                i2 = 0;
            }
            StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
            newBuilder.setAtomId(401);
            newBuilder.writeLong(nanoAppId);
            newBuilder.writeInt(nanoAppVersion);
            newBuilder.writeInt(1);
            newBuilder.writeInt(i2);
            newBuilder.usePooledBuffer();
            StatsLog.write(newBuilder.build());
            ContextHubEventLogger contextHubEventLogger = ContextHubEventLogger.getInstance();
            int i3 = this.val$contextHubId;
            long nanoAppId2 = this.val$nanoAppBinary.getNanoAppId();
            int nanoAppVersion2 = this.val$nanoAppBinary.getNanoAppVersion();
            long length = this.val$nanoAppBinary.getBinary().length;
            boolean z = i == 0;
            synchronized (contextHubEventLogger) {
                ContextHubEventLogger.NanoappLoadEvent nanoappLoadEvent =
                        new ContextHubEventLogger.NanoappLoadEvent(
                                System.currentTimeMillis(),
                                i3,
                                nanoAppId2,
                                nanoAppVersion2,
                                length,
                                z);
                if (!contextHubEventLogger.mNanoappLoadEventQueue.add(nanoappLoadEvent)) {
                    Log.e(
                            "ContextHubEventLogger",
                            "Unable to add nanoapp load event to queue: " + nanoappLoadEvent);
                }
            }
            if (i == 0) {
                ContextHubTransactionManager.this.mNanoAppStateManager.addNanoAppInstance(
                        this.val$contextHubId,
                        this.val$nanoAppBinary.getNanoAppVersion(),
                        this.val$nanoAppBinary.getNanoAppId());
            }
            try {
                this.val$onCompleteCallback.onTransactionComplete(i);
                if (i == 0) {
                    ContextHubClientManager contextHubClientManager =
                            ContextHubTransactionManager.this.mClientManager;
                    int i4 = this.val$contextHubId;
                    long nanoAppId3 = this.val$nanoAppBinary.getNanoAppId();
                    contextHubClientManager.getClass();
                    contextHubClientManager.forEachClientOfHub(
                            i4,
                            new ContextHubClientManager$$ExternalSyntheticLambda3(nanoAppId3, 1));
                }
            } catch (RemoteException e) {
                Log.e(
                        "ContextHubTransactionManager",
                        "RemoteException while calling client onTransactionComplete",
                        e);
            }
        }
    }

    /* renamed from: com.android.server.location.contexthub.ContextHubTransactionManager$2, reason: invalid class name */
    public final class AnonymousClass2 extends ContextHubServiceTransaction {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ ContextHubTransactionManager this$0;
        public final /* synthetic */ int val$contextHubId;
        public final /* synthetic */ long val$nanoAppId;
        public final /* synthetic */ IContextHubTransactionCallback val$onCompleteCallback;

        public AnonymousClass2(
                ContextHubTransactionManager contextHubTransactionManager,
                int i,
                long j,
                String str,
                int i2,
                long j2,
                IContextHubTransactionCallback iContextHubTransactionCallback) {
            super(i, 1, j, str);
            this.$r8$classId = 0;
            this.this$0 = contextHubTransactionManager;
            this.val$contextHubId = i2;
            this.val$nanoAppId = j2;
            this.val$onCompleteCallback = iContextHubTransactionCallback;
        }

        public AnonymousClass2(
                ContextHubTransactionManager contextHubTransactionManager,
                int i,
                String str,
                int i2,
                long j,
                IContextHubTransactionCallback iContextHubTransactionCallback,
                int i3) {
            super(i, 2, str);
            this.$r8$classId = i3;
            switch (i3) {
                case 2:
                    this.this$0 = contextHubTransactionManager;
                    this.val$contextHubId = i2;
                    this.val$nanoAppId = j;
                    this.val$onCompleteCallback = iContextHubTransactionCallback;
                    super(i, 3, str);
                    break;
                default:
                    this.this$0 = contextHubTransactionManager;
                    this.val$contextHubId = i2;
                    this.val$nanoAppId = j;
                    this.val$onCompleteCallback = iContextHubTransactionCallback;
                    break;
            }
        }

        @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
        public final int onTransact() {
            switch (this.$r8$classId) {
                case 0:
                    long j = this.val$nanoAppId;
                    try {
                        break;
                    } catch (RemoteException e) {
                        Log.e(
                                "ContextHubTransactionManager",
                                "RemoteException while trying to unload nanoapp with ID 0x"
                                        + Long.toHexString(j),
                                e);
                        return 1;
                    }
                case 1:
                    long j2 = this.val$nanoAppId;
                    try {
                        break;
                    } catch (RemoteException e2) {
                        Log.e(
                                "ContextHubTransactionManager",
                                "RemoteException while trying to enable nanoapp with ID 0x"
                                        + Long.toHexString(j2),
                                e2);
                        return 1;
                    }
                default:
                    long j3 = this.val$nanoAppId;
                    try {
                        break;
                    } catch (RemoteException e3) {
                        Log.e(
                                "ContextHubTransactionManager",
                                "RemoteException while trying to disable nanoapp with ID 0x"
                                        + Long.toHexString(j3),
                                e3);
                        return 1;
                    }
            }
            return 1;
        }

        @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
        public final void onTransactionComplete(int i) {
            int i2;
            switch (this.$r8$classId) {
                case 0:
                    long j = this.val$nanoAppId;
                    this.this$0.getClass();
                    if (i != 0) {
                        switch (i) {
                            case 2:
                                i2 = 2;
                                break;
                            case 3:
                                i2 = 3;
                                break;
                            case 4:
                                i2 = 4;
                                break;
                            case 5:
                                i2 = 5;
                                break;
                            case 6:
                                i2 = 6;
                                break;
                            case 7:
                                i2 = 7;
                                break;
                            case 8:
                                i2 = 8;
                                break;
                            default:
                                i2 = 1;
                                break;
                        }
                    } else {
                        i2 = 0;
                    }
                    StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
                    newBuilder.setAtomId(401);
                    newBuilder.writeLong(j);
                    newBuilder.writeInt(0);
                    newBuilder.writeInt(2);
                    newBuilder.writeInt(i2);
                    newBuilder.usePooledBuffer();
                    StatsLog.write(newBuilder.build());
                    ContextHubEventLogger contextHubEventLogger =
                            ContextHubEventLogger.getInstance();
                    int i3 = this.val$contextHubId;
                    long j2 = this.val$nanoAppId;
                    boolean z = i == 0;
                    synchronized (contextHubEventLogger) {
                        ContextHubEventLogger.NanoappUnloadEvent nanoappUnloadEvent =
                                new ContextHubEventLogger.NanoappUnloadEvent(
                                        i3, System.currentTimeMillis(), j2, z);
                        if (!contextHubEventLogger.mNanoappUnloadEventQueue.add(
                                nanoappUnloadEvent)) {
                            Log.e(
                                    "ContextHubEventLogger",
                                    "Unable to add nanoapp unload event to queue: "
                                            + nanoappUnloadEvent);
                        }
                    }
                    if (i == 0) {
                        NanoAppStateManager nanoAppStateManager = this.this$0.mNanoAppStateManager;
                        int i4 = this.val$contextHubId;
                        long j3 = this.val$nanoAppId;
                        synchronized (nanoAppStateManager) {
                            nanoAppStateManager.mNanoAppHash.remove(
                                    Integer.valueOf(nanoAppStateManager.getNanoAppHandle(i4, j3)));
                        }
                    }
                    try {
                        this.val$onCompleteCallback.onTransactionComplete(i);
                        if (i == 0) {
                            ContextHubClientManager contextHubClientManager =
                                    this.this$0.mClientManager;
                            int i5 = this.val$contextHubId;
                            long j4 = this.val$nanoAppId;
                            contextHubClientManager.getClass();
                            contextHubClientManager.forEachClientOfHub(
                                    i5,
                                    new ContextHubClientManager$$ExternalSyntheticLambda3(j4, 0));
                            return;
                        }
                        return;
                    } catch (RemoteException e) {
                        Log.e(
                                "ContextHubTransactionManager",
                                "RemoteException while calling client onTransactionComplete",
                                e);
                        return;
                    }
                case 1:
                    try {
                        this.val$onCompleteCallback.onTransactionComplete(i);
                        return;
                    } catch (RemoteException e2) {
                        Log.e(
                                "ContextHubTransactionManager",
                                "RemoteException while calling client onTransactionComplete",
                                e2);
                        return;
                    }
                default:
                    try {
                        this.val$onCompleteCallback.onTransactionComplete(i);
                        return;
                    } catch (RemoteException e3) {
                        Log.e(
                                "ContextHubTransactionManager",
                                "RemoteException while calling client onTransactionComplete",
                                e3);
                        return;
                    }
            }
        }
    }

    /* renamed from: com.android.server.location.contexthub.ContextHubTransactionManager$6, reason: invalid class name */
    public final class AnonymousClass6 extends ContextHubServiceTransaction {
        public final /* synthetic */ int val$contextHubId;
        public final /* synthetic */ IContextHubTransactionCallback val$onCompleteCallback;

        public AnonymousClass6(
                int i,
                String str,
                int i2,
                IContextHubTransactionCallback iContextHubTransactionCallback) {
            super(i, 4, str);
            this.val$contextHubId = i2;
            this.val$onCompleteCallback = iContextHubTransactionCallback;
        }

        @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
        public final void onQueryResponse(int i, List list) {
            try {
                this.val$onCompleteCallback.onQueryResponse(i, list);
            } catch (RemoteException e) {
                Log.e(
                        "ContextHubTransactionManager",
                        "RemoteException while calling client onQueryComplete",
                        e);
            }
        }

        @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
        public final int onTransact() {
            try {
                return ContextHubTransactionManager.this.mContextHubProxy.queryNanoapps(
                        this.val$contextHubId);
            } catch (RemoteException e) {
                Log.e(
                        "ContextHubTransactionManager",
                        "RemoteException while trying to query for nanoapps",
                        e);
                return 1;
            }
        }

        @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
        public final void onTransactionComplete(int i) {
            onQueryResponse(i, Collections.emptyList());
        }
    }

    public final class TransactionRecord {
        public final long mTimestamp = System.currentTimeMillis();
        public final String mTransaction;

        public TransactionRecord(String str) {
            this.mTransaction = str;
        }

        public final String toString() {
            return ContextHubServiceUtil.formatDateFromTimestamp(this.mTimestamp)
                    + " "
                    + this.mTransaction;
        }
    }

    public ContextHubTransactionManager(
            IContextHubWrapper iContextHubWrapper,
            ContextHubClientManager contextHubClientManager,
            NanoAppStateManager nanoAppStateManager) {
        this.mContextHubProxy = iContextHubWrapper;
        this.mClientManager = contextHubClientManager;
        this.mNanoAppStateManager = nanoAppStateManager;
    }

    public final synchronized void addTransaction(
            ContextHubServiceTransaction contextHubServiceTransaction) {
        if (this.mTransactionQueue.size() == 10000) {
            throw new IllegalStateException("Transaction queue is full (capacity = 10000)");
        }
        this.mTransactionQueue.add(contextHubServiceTransaction);
        this.mTransactionRecordDeque.add(
                new TransactionRecord(contextHubServiceTransaction.toString()));
        if (this.mTransactionQueue.size() == 1) {
            startNextTransaction();
        }
    }

    public final void removeTransactionAndStartNext() {
        ScheduledFuture scheduledFuture = this.mTimeoutFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            this.mTimeoutFuture = null;
        }
        ((ContextHubServiceTransaction) this.mTransactionQueue.remove()).mIsComplete = true;
        if (this.mTransactionQueue.isEmpty()) {
            return;
        }
        startNextTransaction();
    }

    public final void startNextTransaction() {
        int i = 1;
        while (i != 0 && !this.mTransactionQueue.isEmpty()) {
            final ContextHubServiceTransaction contextHubServiceTransaction =
                    (ContextHubServiceTransaction) this.mTransactionQueue.peek();
            int onTransact = contextHubServiceTransaction.onTransact();
            if (onTransact == 0) {
                Runnable runnable =
                        new Runnable() { // from class:
                                         // com.android.server.location.contexthub.ContextHubTransactionManager$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                ContextHubTransactionManager contextHubTransactionManager =
                                        ContextHubTransactionManager.this;
                                ContextHubServiceTransaction contextHubServiceTransaction2 =
                                        contextHubServiceTransaction;
                                synchronized (contextHubTransactionManager) {
                                    try {
                                        if (!contextHubServiceTransaction2.mIsComplete) {
                                            Log.d(
                                                    "ContextHubTransactionManager",
                                                    contextHubServiceTransaction2 + " timed out");
                                            contextHubServiceTransaction2.onTransactionComplete(6);
                                            contextHubTransactionManager
                                                    .removeTransactionAndStartNext();
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        };
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                int i2 = contextHubServiceTransaction.mTransactionType;
                try {
                    this.mTimeoutFuture =
                            this.mTimeoutExecutor.schedule(
                                    runnable,
                                    i2 != 0
                                            ? i2 != 5
                                                    ? timeUnit.convert(5L, TimeUnit.SECONDS)
                                                    : timeUnit.convert(1000L, timeUnit)
                                            : timeUnit.convert(30L, TimeUnit.SECONDS),
                                    timeUnit);
                } catch (Exception e) {
                    Log.e("ContextHubTransactionManager", "Error when schedule a timer", e);
                }
            } else {
                contextHubServiceTransaction.onTransactionComplete(
                        ContextHubServiceUtil.toTransactionResult(onTransact));
                this.mTransactionQueue.remove();
            }
            i = onTransact;
        }
    }

    public final String toString() {
        int i;
        ContextHubServiceTransaction[] contextHubServiceTransactionArr;
        StringBuilder sb = new StringBuilder(100);
        synchronized (this) {
            contextHubServiceTransactionArr =
                    (ContextHubServiceTransaction[])
                            this.mTransactionQueue.toArray(new ContextHubServiceTransaction[0]);
        }
        for (i = 0; i < contextHubServiceTransactionArr.length; i++) {
            sb.append(i + ": " + contextHubServiceTransactionArr[i] + "\n");
        }
        sb.append("Transaction History:\n");
        Iterator descendingIterator = this.mTransactionRecordDeque.descendingIterator();
        while (descendingIterator.hasNext()) {
            sb.append(descendingIterator.next() + "\n");
        }
        return sb.toString();
    }
}
