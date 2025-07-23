package android.window;

import android.os.Binder;
import android.os.BinderProxy;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.Trace;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.view.AttachedSurfaceControl;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceView;
import android.view.WindowManagerGlobal;
import android.window.ISurfaceSyncGroup;
import android.window.ISurfaceSyncGroupCompletedListener;
import android.window.ITransactionReadyCallback;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public final class SurfaceSyncGroup {
    private static final boolean DEBUG = false;
    private static final int MAX_COUNT = 100;
    private static final String TAG = "SurfaceSyncGroup";
    private static HandlerThread sHandlerThread;
    private Runnable mAddedToSyncListener;
    private boolean mFinished;
    private Handler mHandler;
    private boolean mHasWMSync;
    public final ISurfaceSyncGroup mISurfaceSyncGroup;
    private final Object mLock;
    private final String mName;
    private ISurfaceSyncGroup mParentSyncGroup;
    private final ArraySet<ITransactionReadyCallback> mPendingSyncs;
    private ISurfaceSyncGroupCompletedListener mSurfaceSyncGroupCompletedListener;
    private final ArraySet<Pair<Executor, Runnable>> mSyncCompleteCallbacks;
    private boolean mSyncReady;
    private boolean mTimeoutAdded;
    private boolean mTimeoutDisabled;
    private boolean mTimeoutOccurred;
    private final Binder mToken;
    private final String mTrackName;
    private final SurfaceControl.Transaction mTransaction;
    private Consumer<SurfaceControl.Transaction> mTransactionReadyConsumer;
    private static final AtomicInteger sCounter = new AtomicInteger(0);
    public static final int TRANSACTION_READY_TIMEOUT = Build.HW_TIMEOUT_MULTIPLIER * 1000;
    private static Supplier<SurfaceControl.Transaction> sTransactionFactory = new Supplier() { // from class: android.window.SurfaceSyncGroup$$ExternalSyntheticLambda1
        @Override // java.util.function.Supplier
        public final Object get() {
            return new SurfaceControl.Transaction();
        }
    };
    private static final Object sHandlerThreadLock = new Object();

    public interface SurfaceViewFrameCallback {
        void onFrameStarted();
    }

    private static boolean isLocalBinder(IBinder iBinder) {
        return !(iBinder instanceof BinderProxy);
    }

    private static SurfaceSyncGroup getSurfaceSyncGroup(ISurfaceSyncGroup iSurfaceSyncGroup) {
        if (iSurfaceSyncGroup instanceof ISurfaceSyncGroupImpl) {
            return ((ISurfaceSyncGroupImpl) iSurfaceSyncGroup).getSurfaceSyncGroup();
        }
        return null;
    }

    public static void setTransactionFactory(Supplier<SurfaceControl.Transaction> supplier) {
        sTransactionFactory = supplier;
    }

    public SurfaceSyncGroup(String str) {
        this(str, new Consumer() { // from class: android.window.SurfaceSyncGroup$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SurfaceSyncGroup.lambda$new$0((SurfaceControl.Transaction) obj);
            }
        });
    }

    static /* synthetic */ void lambda$new$0(SurfaceControl.Transaction transaction) {
        if (transaction != null) {
            transaction.apply();
        }
    }

    public SurfaceSyncGroup(String str, final Consumer<SurfaceControl.Transaction> consumer) {
        this.mLock = new Object();
        this.mPendingSyncs = new ArraySet<>();
        this.mTransaction = sTransactionFactory.get();
        this.mSyncCompleteCallbacks = new ArraySet<>();
        this.mISurfaceSyncGroup = new ISurfaceSyncGroupImpl();
        this.mToken = new Binder();
        AtomicInteger atomicInteger = sCounter;
        if (atomicInteger.get() >= 100) {
            atomicInteger.set(0);
        }
        String str2 = str + "#" + atomicInteger.getAndIncrement();
        this.mName = str2;
        String str3 = "SurfaceSyncGroup " + str;
        this.mTrackName = str3;
        this.mTransactionReadyConsumer = new Consumer() { // from class: android.window.SurfaceSyncGroup$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SurfaceSyncGroup.this.lambda$new$1(consumer, (SurfaceControl.Transaction) obj);
            }
        };
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceForTrackBegin(8L, str3, str2, hashCode());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(Consumer consumer, SurfaceControl.Transaction transaction) {
        if (Trace.isTagEnabled(8L)) {
            Trace.instantForTrack(8L, this.mTrackName, "Final TransactionCallback with " + transaction);
        }
        Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
        consumer.accept(transaction);
        synchronized (this.mLock) {
            if (this.mSurfaceSyncGroupCompletedListener == null) {
                invokeSyncCompleteCallbacks();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeSyncCompleteCallbacks() {
        this.mSyncCompleteCallbacks.forEach(new Consumer() { // from class: android.window.SurfaceSyncGroup$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Executor) r1.first).execute((Runnable) ((Pair) obj).second);
            }
        });
    }

    public void addSyncCompleteCallback(Executor executor, Runnable runnable) {
        synchronized (this.mLock) {
            if (this.mFinished) {
                executor.execute(runnable);
            } else {
                this.mSyncCompleteCallbacks.add(new Pair<>(executor, runnable));
            }
        }
    }

    public void markSyncReady() {
        if (Trace.isTagEnabled(8L)) {
            Trace.instantForTrack(8L, this.mTrackName, "markSyncReady");
        }
        synchronized (this.mLock) {
            if (this.mHasWMSync) {
                try {
                    WindowManagerGlobal.getWindowManagerService().markSurfaceSyncGroupReady(this.mToken);
                } catch (RemoteException unused) {
                }
            }
            this.mSyncReady = true;
            checkIfSyncIsComplete();
        }
    }

    public boolean add(final SurfaceView surfaceView, Consumer<SurfaceViewFrameCallback> consumer) {
        final SurfaceSyncGroup surfaceSyncGroup = new SurfaceSyncGroup(surfaceView.getName());
        if (!add(surfaceSyncGroup.mISurfaceSyncGroup, false, null)) {
            return false;
        }
        consumer.accept(new SurfaceViewFrameCallback() { // from class: android.window.SurfaceSyncGroup$$ExternalSyntheticLambda0
            @Override // android.window.SurfaceSyncGroup.SurfaceViewFrameCallback
            public final void onFrameStarted() {
                SurfaceView.this.syncNextFrame(new Consumer() { // from class: android.window.SurfaceSyncGroup$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SurfaceSyncGroup.lambda$add$3(SurfaceSyncGroup.this, (SurfaceControl.Transaction) obj);
                    }
                });
            }
        });
        return true;
    }

    static /* synthetic */ void lambda$add$3(SurfaceSyncGroup surfaceSyncGroup, SurfaceControl.Transaction transaction) {
        surfaceSyncGroup.addTransaction(transaction);
        surfaceSyncGroup.markSyncReady();
    }

    public boolean add(AttachedSurfaceControl attachedSurfaceControl, Runnable runnable) {
        SurfaceSyncGroup orCreateSurfaceSyncGroup;
        if (attachedSurfaceControl == null || (orCreateSurfaceSyncGroup = attachedSurfaceControl.getOrCreateSurfaceSyncGroup()) == null) {
            return false;
        }
        return add(orCreateSurfaceSyncGroup, runnable);
    }

    public boolean add(SurfaceControlViewHost.SurfacePackage surfacePackage, Runnable runnable) {
        try {
            ISurfaceSyncGroup surfaceSyncGroup = surfacePackage.getRemoteInterface().getSurfaceSyncGroup();
            if (surfaceSyncGroup == null) {
                Log.e(TAG, "Failed to add SurfaceControlViewHost to SurfaceSyncGroup. SCVH returned null SurfaceSyncGroup");
                return false;
            }
            return add(surfaceSyncGroup, false, runnable);
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to add SurfaceControlViewHost to SurfaceSyncGroup");
            return false;
        }
    }

    public boolean add(SurfaceSyncGroup surfaceSyncGroup, Runnable runnable) {
        return add(surfaceSyncGroup.mISurfaceSyncGroup, false, runnable);
    }

    public boolean add(ISurfaceSyncGroup iSurfaceSyncGroup, boolean z, Runnable runnable) {
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceForTrackBegin(8L, this.mTrackName, "addToSync token=" + this.mToken.hashCode(), hashCode());
        }
        synchronized (this.mLock) {
            if (this.mSyncReady) {
                Log.w(TAG, "Trying to add to sync when already marked as ready " + this.mName);
                if (Trace.isTagEnabled(8L)) {
                    Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
                }
                return false;
            }
            if (runnable != null) {
                runnable.run();
            }
            if (isLocalBinder(iSurfaceSyncGroup.asBinder())) {
                boolean addLocalSync = addLocalSync(iSurfaceSyncGroup, z);
                if (Trace.isTagEnabled(8L)) {
                    Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
                }
                return addLocalSync;
            }
            synchronized (this.mLock) {
                if (!this.mHasWMSync) {
                    ISurfaceSyncGroupCompletedListener.Stub stub = new ISurfaceSyncGroupCompletedListener.Stub() { // from class: android.window.SurfaceSyncGroup.1
                        @Override // android.window.ISurfaceSyncGroupCompletedListener
                        public void onSurfaceSyncGroupComplete() {
                            synchronized (SurfaceSyncGroup.this.mLock) {
                                SurfaceSyncGroup.this.invokeSyncCompleteCallbacks();
                            }
                        }
                    };
                    this.mSurfaceSyncGroupCompletedListener = stub;
                    if (!addSyncToWm(this.mToken, false, stub)) {
                        this.mSurfaceSyncGroupCompletedListener = null;
                        if (Trace.isTagEnabled(8L)) {
                            Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
                        }
                        return false;
                    }
                    this.mHasWMSync = true;
                }
                try {
                    iSurfaceSyncGroup.onAddedToSyncGroup(this.mToken, z);
                    if (Trace.isTagEnabled(8L)) {
                        Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
                    }
                    return true;
                } catch (RemoteException unused) {
                    if (Trace.isTagEnabled(8L)) {
                        Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
                    }
                    return false;
                }
            }
        }
    }

    public void addTransaction(SurfaceControl.Transaction transaction) {
        synchronized (this.mLock) {
            if (this.mFinished) {
                Log.w(TAG, "Adding transaction to a completed SurfaceSyncGroup(" + this.mName + ").  Applying immediately");
                transaction.apply();
            } else {
                this.mTransaction.merge(transaction);
            }
        }
    }

    public void setAddedToSyncListener(Runnable runnable) {
        synchronized (this.mLock) {
            this.mAddedToSyncListener = runnable;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean addSyncToWm(IBinder iBinder, boolean z, ISurfaceSyncGroupCompletedListener iSurfaceSyncGroupCompletedListener) {
        try {
            if (Trace.isTagEnabled(8L)) {
                Trace.asyncTraceForTrackBegin(8L, this.mTrackName, "addSyncToWm=" + iBinder.hashCode(), hashCode());
            }
            AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult = new AddToSurfaceSyncGroupResult();
            if (!WindowManagerGlobal.getWindowManagerService().addToSurfaceSyncGroup(iBinder, z, iSurfaceSyncGroupCompletedListener, addToSurfaceSyncGroupResult)) {
                if (Trace.isTagEnabled(8L)) {
                    Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
                }
                return false;
            }
            setTransactionCallbackFromParent(addToSurfaceSyncGroupResult.mParentSyncGroup, addToSurfaceSyncGroupResult.mTransactionReadyCallback);
            if (!Trace.isTagEnabled(8L)) {
                return true;
            }
            Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
            return true;
        } catch (RemoteException unused) {
            if (Trace.isTagEnabled(8L)) {
                Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
            }
            return false;
        }
    }

    private boolean addLocalSync(ISurfaceSyncGroup iSurfaceSyncGroup, boolean z) {
        SurfaceSyncGroup surfaceSyncGroup = getSurfaceSyncGroup(iSurfaceSyncGroup);
        if (surfaceSyncGroup == null) {
            Log.e(TAG, "Trying to add a local sync that's either not valid or not from the local process=" + iSurfaceSyncGroup);
            return false;
        }
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceForTrackBegin(8L, this.mTrackName, "addLocalSync=" + surfaceSyncGroup.mName, hashCode());
        }
        ITransactionReadyCallback createTransactionReadyCallback = createTransactionReadyCallback(z);
        if (createTransactionReadyCallback == null) {
            return false;
        }
        surfaceSyncGroup.setTransactionCallbackFromParent(this.mISurfaceSyncGroup, createTransactionReadyCallback);
        if (!Trace.isTagEnabled(8L)) {
            return true;
        }
        Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
        return true;
    }

    private void setTransactionCallbackFromParent(ISurfaceSyncGroup iSurfaceSyncGroup, final ITransactionReadyCallback iTransactionReadyCallback) {
        boolean z;
        Runnable runnable;
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceForTrackBegin(8L, this.mTrackName, "setTransactionCallbackFromParent " + this.mName + " callback=" + iTransactionReadyCallback.hashCode(), hashCode());
        }
        addTimeout();
        synchronized (this.mLock) {
            z = true;
            if (this.mFinished) {
                runnable = null;
            } else {
                ISurfaceSyncGroup iSurfaceSyncGroup2 = this.mParentSyncGroup;
                if (iSurfaceSyncGroup2 != null && iSurfaceSyncGroup2 != iSurfaceSyncGroup) {
                    try {
                        iSurfaceSyncGroup.addToSync(iSurfaceSyncGroup2, true);
                    } catch (RemoteException unused) {
                    }
                }
                final Consumer<SurfaceControl.Transaction> consumer = this.mTransactionReadyConsumer;
                this.mParentSyncGroup = iSurfaceSyncGroup;
                this.mTransactionReadyConsumer = new Consumer() { // from class: android.window.SurfaceSyncGroup$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SurfaceSyncGroup.this.lambda$setTransactionCallbackFromParent$5(iTransactionReadyCallback, consumer, (SurfaceControl.Transaction) obj);
                    }
                };
                runnable = this.mAddedToSyncListener;
                z = false;
            }
        }
        if (z) {
            try {
                iTransactionReadyCallback.onTransactionReady(null);
            } catch (RemoteException unused2) {
            }
        } else if (runnable != null) {
            runnable.run();
        }
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTransactionCallbackFromParent$5(ITransactionReadyCallback iTransactionReadyCallback, Consumer consumer, SurfaceControl.Transaction transaction) {
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceForTrackBegin(8L, this.mTrackName, "Invoke transactionReadyCallback=" + iTransactionReadyCallback.hashCode(), hashCode());
        }
        consumer.accept(null);
        try {
            iTransactionReadyCallback.onTransactionReady(transaction);
        } catch (RemoteException unused) {
            transaction.apply();
        }
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
        }
    }

    public String getName() {
        return this.mName;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkIfSyncIsComplete() {
        if (this.mTimeoutOccurred) {
            Log.i(TAG, "checkIfSyncIsComplete: Callers=" + Debug.getCallers(5));
        }
        if (this.mFinished) {
            if (this.mTimeoutOccurred) {
                Log.d(TAG, "SurfaceSyncGroup=" + this.mName + " is already complete");
            }
            this.mTransaction.apply();
            return;
        }
        if (Trace.isTagEnabled(8L)) {
            Trace.instantForTrack(8L, this.mTrackName, "checkIfSyncIsComplete mSyncReady=" + this.mSyncReady + " mPendingSyncs=" + this.mPendingSyncs.size());
        }
        if (!this.mSyncReady || !this.mPendingSyncs.isEmpty()) {
            if (this.mTimeoutOccurred) {
                Log.d(TAG, "SurfaceSyncGroup=" + this.mName + " is not complete. mSyncReady=" + this.mSyncReady + " mPendingSyncs=" + this.mPendingSyncs.size());
                return;
            }
            return;
        }
        if (this.mTimeoutOccurred) {
            Log.d(TAG, "Successfully finished sync id=" + this.mName);
        }
        this.mTransactionReadyConsumer.accept(this.mTransaction);
        this.mFinished = true;
        if (this.mTimeoutAdded) {
            this.mHandler.removeCallbacksAndMessages(this);
        }
        if (this.mTimeoutOccurred) {
            this.mTimeoutOccurred = false;
        }
    }

    public ITransactionReadyCallback createTransactionReadyCallback(final boolean z) {
        ITransactionReadyCallback.Stub stub = new ITransactionReadyCallback.Stub() { // from class: android.window.SurfaceSyncGroup.2
            @Override // android.window.ITransactionReadyCallback
            public void onTransactionReady(SurfaceControl.Transaction transaction) {
                synchronized (SurfaceSyncGroup.this.mLock) {
                    if (transaction != null) {
                        transaction.sanitize(Binder.getCallingPid(), Binder.getCallingUid());
                        if (z) {
                            transaction.merge(SurfaceSyncGroup.this.mTransaction);
                        }
                        SurfaceSyncGroup.this.mTransaction.merge(transaction);
                    }
                    SurfaceSyncGroup.this.mPendingSyncs.remove(this);
                    if (Trace.isTagEnabled(8L)) {
                        Trace.instantForTrack(8L, SurfaceSyncGroup.this.mTrackName, "onTransactionReady callback=" + hashCode());
                    }
                    SurfaceSyncGroup.this.checkIfSyncIsComplete();
                }
            }
        };
        synchronized (this.mLock) {
            if (this.mSyncReady) {
                Log.e(TAG, "Sync " + this.mName + " was already marked as ready. No more SurfaceSyncGroups can be added.");
                return null;
            }
            this.mPendingSyncs.add(stub);
            if (Trace.isTagEnabled(8L)) {
                Trace.instantForTrack(8L, this.mTrackName, "createTransactionReadyCallback mPendingSyncs=" + this.mPendingSyncs.size() + " transactionReady=" + stub.hashCode());
            }
            addTimeout();
            return stub;
        }
    }

    private class ISurfaceSyncGroupImpl extends ISurfaceSyncGroup.Stub {
        private ISurfaceSyncGroupImpl() {
        }

        @Override // android.window.ISurfaceSyncGroup
        public boolean onAddedToSyncGroup(IBinder iBinder, boolean z) {
            if (Trace.isTagEnabled(8L)) {
                Trace.asyncTraceForTrackBegin(8L, SurfaceSyncGroup.this.mTrackName, "onAddedToSyncGroup token=" + iBinder.hashCode(), hashCode());
            }
            boolean addSyncToWm = SurfaceSyncGroup.this.addSyncToWm(iBinder, z, null);
            if (Trace.isTagEnabled(8L)) {
                Trace.asyncTraceForTrackEnd(8L, SurfaceSyncGroup.this.mTrackName, hashCode());
            }
            return addSyncToWm;
        }

        @Override // android.window.ISurfaceSyncGroup
        public boolean addToSync(ISurfaceSyncGroup iSurfaceSyncGroup, boolean z) {
            return SurfaceSyncGroup.this.add(iSurfaceSyncGroup, z, null);
        }

        SurfaceSyncGroup getSurfaceSyncGroup() {
            return SurfaceSyncGroup.this;
        }
    }

    public void toggleTimeout(boolean z) {
        synchronized (this.mLock) {
            this.mTimeoutDisabled = !z;
            boolean z2 = this.mTimeoutAdded;
            if (z2 && !z) {
                this.mHandler.removeCallbacksAndMessages(this);
                this.mTimeoutAdded = false;
            } else if (!z2 && z) {
                addTimeout();
            }
        }
    }

    private void addTimeout() {
        Looper looper;
        synchronized (sHandlerThreadLock) {
            if (sHandlerThread == null) {
                HandlerThread handlerThread = new HandlerThread("SurfaceSyncGroupTimer");
                sHandlerThread = handlerThread;
                handlerThread.start();
            }
            looper = sHandlerThread.getLooper();
        }
        synchronized (this.mLock) {
            if (!this.mTimeoutAdded && !this.mTimeoutDisabled && looper != null) {
                if (this.mHandler == null) {
                    this.mHandler = new Handler(looper);
                }
                this.mTimeoutAdded = true;
                this.mHandler.postDelayed(new Runnable() { // from class: android.window.SurfaceSyncGroup$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        SurfaceSyncGroup.this.lambda$addTimeout$6();
                    }
                }, this, TRANSACTION_READY_TIMEOUT);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addTimeout$6() {
        Log.e(TAG, "Failed to receive transaction ready in " + TRANSACTION_READY_TIMEOUT + "ms. Marking SurfaceSyncGroup(" + this.mName + ") as ready");
        synchronized (this.mLock) {
            this.mPendingSyncs.clear();
        }
        this.mTimeoutOccurred = true;
        markSyncReady();
    }

    public boolean isComplete() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mFinished;
        }
        return z;
    }
}
