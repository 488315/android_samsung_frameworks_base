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
import android.view.InsetsController$$ExternalSyntheticLambda13;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceView;
import android.view.WindowManagerGlobal;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
    private static Supplier<SurfaceControl.Transaction> sTransactionFactory =
            new InsetsController$$ExternalSyntheticLambda13();
    private static final Object sHandlerThreadLock = new Object();

    public interface SurfaceViewFrameCallback {
        void onFrameStarted();
    }

    private static boolean isLocalBinder(IBinder binder) {
        return !(binder instanceof BinderProxy);
    }

    private static SurfaceSyncGroup getSurfaceSyncGroup(ISurfaceSyncGroup iSurfaceSyncGroup) {
        if (iSurfaceSyncGroup instanceof ISurfaceSyncGroupImpl) {
            return ((ISurfaceSyncGroupImpl) iSurfaceSyncGroup).getSurfaceSyncGroup();
        }
        return null;
    }

    public static void setTransactionFactory(
            Supplier<SurfaceControl.Transaction> transactionFactory) {
        sTransactionFactory = transactionFactory;
    }

    public SurfaceSyncGroup(String name) {
        this(
                name,
                new Consumer() { // from class:
                                 // android.window.SurfaceSyncGroup$$ExternalSyntheticLambda6
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

    public SurfaceSyncGroup(
            String name, final Consumer<SurfaceControl.Transaction> transactionReadyConsumer) {
        this.mLock = new Object();
        this.mPendingSyncs = new ArraySet<>();
        this.mTransaction = sTransactionFactory.get();
        this.mSyncCompleteCallbacks = new ArraySet<>();
        this.mISurfaceSyncGroup = new ISurfaceSyncGroupImpl();
        this.mToken = new Binder();
        if (sCounter.get() >= 100) {
            sCounter.set(0);
        }
        this.mName = name + "#" + sCounter.getAndIncrement();
        this.mTrackName = "SurfaceSyncGroup " + name;
        this.mTransactionReadyConsumer =
                new Consumer() { // from class:
                                 // android.window.SurfaceSyncGroup$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SurfaceSyncGroup.this.lambda$new$1(
                                transactionReadyConsumer, (SurfaceControl.Transaction) obj);
                    }
                };
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceForTrackBegin(8L, this.mTrackName, this.mName, hashCode());
        }
    }

    public /* synthetic */ void lambda$new$1(
            Consumer transactionReadyConsumer, SurfaceControl.Transaction transaction) {
        if (Trace.isTagEnabled(8L)) {
            Trace.instantForTrack(
                    8L, this.mTrackName, "Final TransactionCallback with " + transaction);
        }
        Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
        transactionReadyConsumer.accept(transaction);
        synchronized (this.mLock) {
            if (this.mSurfaceSyncGroupCompletedListener == null) {
                invokeSyncCompleteCallbacks();
            }
        }
    }

    public void invokeSyncCompleteCallbacks() {
        this.mSyncCompleteCallbacks.forEach(
                new Consumer() { // from class:
                                 // android.window.SurfaceSyncGroup$$ExternalSyntheticLambda1
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
                    WindowManagerGlobal.getWindowManagerService()
                            .markSurfaceSyncGroupReady(this.mToken);
                } catch (RemoteException e) {
                }
            }
            this.mSyncReady = true;
            checkIfSyncIsComplete();
        }
    }

    public boolean add(
            final SurfaceView surfaceView,
            Consumer<SurfaceViewFrameCallback> frameCallbackConsumer) {
        final SurfaceSyncGroup surfaceSyncGroup = new SurfaceSyncGroup(surfaceView.getName());
        if (!add(surfaceSyncGroup.mISurfaceSyncGroup, false, null)) {
            return false;
        }
        frameCallbackConsumer.accept(
                new SurfaceViewFrameCallback() { // from class:
                                                 // android.window.SurfaceSyncGroup$$ExternalSyntheticLambda0
                    @Override // android.window.SurfaceSyncGroup.SurfaceViewFrameCallback
                    public final void onFrameStarted() {
                        SurfaceView.this.syncNextFrame(
                                new Consumer() { // from class:
                                                 // android.window.SurfaceSyncGroup$$ExternalSyntheticLambda5
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        SurfaceSyncGroup.lambda$add$3(
                                                SurfaceSyncGroup.this,
                                                (SurfaceControl.Transaction) obj);
                                    }
                                });
                    }
                });
        return true;
    }

    static /* synthetic */ void lambda$add$3(
            SurfaceSyncGroup surfaceSyncGroup, SurfaceControl.Transaction transaction) {
        surfaceSyncGroup.addTransaction(transaction);
        surfaceSyncGroup.markSyncReady();
    }

    public boolean add(AttachedSurfaceControl attachedSurfaceControl, Runnable runnable) {
        SurfaceSyncGroup surfaceSyncGroup;
        if (attachedSurfaceControl == null
                || (surfaceSyncGroup = attachedSurfaceControl.getOrCreateSurfaceSyncGroup())
                        == null) {
            return false;
        }
        return add(surfaceSyncGroup, runnable);
    }

    public boolean add(SurfaceControlViewHost.SurfacePackage surfacePackage, Runnable runnable) {
        try {
            ISurfaceSyncGroup surfaceSyncGroup =
                    surfacePackage.getRemoteInterface().getSurfaceSyncGroup();
            if (surfaceSyncGroup == null) {
                Log.e(
                        TAG,
                        "Failed to add SurfaceControlViewHost to SurfaceSyncGroup. SCVH returned"
                            + " null SurfaceSyncGroup");
                return false;
            }
            return add(surfaceSyncGroup, false, runnable);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to add SurfaceControlViewHost to SurfaceSyncGroup");
            return false;
        }
    }

    public boolean add(SurfaceSyncGroup surfaceSyncGroup, Runnable runnable) {
        return add(surfaceSyncGroup.mISurfaceSyncGroup, false, runnable);
    }

    public boolean add(
            ISurfaceSyncGroup surfaceSyncGroup, boolean parentSyncGroupMerge, Runnable runnable) {
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceForTrackBegin(
                    8L, this.mTrackName, "addToSync token=" + this.mToken.hashCode(), hashCode());
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
            if (isLocalBinder(surfaceSyncGroup.asBinder())) {
                boolean didAddLocalSync = addLocalSync(surfaceSyncGroup, parentSyncGroupMerge);
                if (Trace.isTagEnabled(8L)) {
                    Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
                }
                return didAddLocalSync;
            }
            synchronized (this.mLock) {
                if (!this.mHasWMSync) {
                    this.mSurfaceSyncGroupCompletedListener =
                            new ISurfaceSyncGroupCompletedListener
                                    .Stub() { // from class: android.window.SurfaceSyncGroup.1
                                @Override // android.window.ISurfaceSyncGroupCompletedListener
                                public void onSurfaceSyncGroupComplete() {
                                    synchronized (SurfaceSyncGroup.this.mLock) {
                                        SurfaceSyncGroup.this.invokeSyncCompleteCallbacks();
                                    }
                                }
                            };
                    if (!addSyncToWm(this.mToken, false, this.mSurfaceSyncGroupCompletedListener)) {
                        this.mSurfaceSyncGroupCompletedListener = null;
                        if (Trace.isTagEnabled(8L)) {
                            Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
                        }
                        return false;
                    }
                    this.mHasWMSync = true;
                }
                try {
                    surfaceSyncGroup.onAddedToSyncGroup(this.mToken, parentSyncGroupMerge);
                    if (Trace.isTagEnabled(8L)) {
                        Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
                    }
                    return true;
                } catch (RemoteException e) {
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
                Log.w(
                        TAG,
                        "Adding transaction to a completed SurfaceSyncGroup("
                                + this.mName
                                + ").  Applying immediately");
                transaction.apply();
            } else {
                this.mTransaction.merge(transaction);
            }
        }
    }

    public void setAddedToSyncListener(Runnable addedToSyncListener) {
        synchronized (this.mLock) {
            this.mAddedToSyncListener = addedToSyncListener;
        }
    }

    public boolean addSyncToWm(
            IBinder token,
            boolean parentSyncGroupMerge,
            ISurfaceSyncGroupCompletedListener surfaceSyncGroupCompletedListener) {
        try {
            if (Trace.isTagEnabled(8L)) {
                Trace.asyncTraceForTrackBegin(
                        8L, this.mTrackName, "addSyncToWm=" + token.hashCode(), hashCode());
            }
            AddToSurfaceSyncGroupResult addToSyncGroupResult = new AddToSurfaceSyncGroupResult();
            if (!WindowManagerGlobal.getWindowManagerService()
                    .addToSurfaceSyncGroup(
                            token,
                            parentSyncGroupMerge,
                            surfaceSyncGroupCompletedListener,
                            addToSyncGroupResult)) {
                if (Trace.isTagEnabled(8L)) {
                    Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
                }
                return false;
            }
            setTransactionCallbackFromParent(
                    addToSyncGroupResult.mParentSyncGroup,
                    addToSyncGroupResult.mTransactionReadyCallback);
            if (Trace.isTagEnabled(8L)) {
                Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
                return true;
            }
            return true;
        } catch (RemoteException e) {
            if (Trace.isTagEnabled(8L)) {
                Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
            }
            return false;
        }
    }

    private boolean addLocalSync(ISurfaceSyncGroup childSyncToken, boolean parentSyncGroupMerge) {
        SurfaceSyncGroup childSurfaceSyncGroup = getSurfaceSyncGroup(childSyncToken);
        if (childSurfaceSyncGroup == null) {
            Log.e(
                    TAG,
                    "Trying to add a local sync that's either not valid or not from the local"
                        + " process="
                            + childSyncToken);
            return false;
        }
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceForTrackBegin(
                    8L, this.mTrackName, "addLocalSync=" + childSurfaceSyncGroup.mName, hashCode());
        }
        ITransactionReadyCallback callback = createTransactionReadyCallback(parentSyncGroupMerge);
        if (callback == null) {
            return false;
        }
        childSurfaceSyncGroup.setTransactionCallbackFromParent(this.mISurfaceSyncGroup, callback);
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
            return true;
        }
        return true;
    }

    private void setTransactionCallbackFromParent(
            ISurfaceSyncGroup parentSyncGroup,
            final ITransactionReadyCallback transactionReadyCallback) {
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceForTrackBegin(
                    8L,
                    this.mTrackName,
                    "setTransactionCallbackFromParent "
                            + this.mName
                            + " callback="
                            + transactionReadyCallback.hashCode(),
                    hashCode());
        }
        addTimeout();
        boolean finished = false;
        Runnable addedToSyncListener = null;
        synchronized (this.mLock) {
            if (this.mFinished) {
                finished = true;
            } else {
                if (this.mParentSyncGroup != null && this.mParentSyncGroup != parentSyncGroup) {
                    try {
                        parentSyncGroup.addToSync(this.mParentSyncGroup, true);
                    } catch (RemoteException e) {
                    }
                }
                final Consumer<SurfaceControl.Transaction> lastCallback =
                        this.mTransactionReadyConsumer;
                this.mParentSyncGroup = parentSyncGroup;
                this.mTransactionReadyConsumer =
                        new Consumer() { // from class:
                                         // android.window.SurfaceSyncGroup$$ExternalSyntheticLambda4
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                SurfaceSyncGroup.this.lambda$setTransactionCallbackFromParent$5(
                                        transactionReadyCallback,
                                        lastCallback,
                                        (SurfaceControl.Transaction) obj);
                            }
                        };
                addedToSyncListener = this.mAddedToSyncListener;
            }
        }
        if (finished) {
            try {
                transactionReadyCallback.onTransactionReady(null);
            } catch (RemoteException e2) {
            }
        } else if (addedToSyncListener != null) {
            addedToSyncListener.run();
        }
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
        }
    }

    public /* synthetic */ void lambda$setTransactionCallbackFromParent$5(
            ITransactionReadyCallback transactionReadyCallback,
            Consumer lastCallback,
            SurfaceControl.Transaction transaction) {
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceForTrackBegin(
                    8L,
                    this.mTrackName,
                    "Invoke transactionReadyCallback=" + transactionReadyCallback.hashCode(),
                    hashCode());
        }
        lastCallback.accept(null);
        try {
            transactionReadyCallback.onTransactionReady(transaction);
        } catch (RemoteException e) {
            transaction.apply();
        }
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceForTrackEnd(8L, this.mTrackName, hashCode());
        }
    }

    public String getName() {
        return this.mName;
    }

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
            Trace.instantForTrack(
                    8L,
                    this.mTrackName,
                    "checkIfSyncIsComplete mSyncReady="
                            + this.mSyncReady
                            + " mPendingSyncs="
                            + this.mPendingSyncs.size());
        }
        if (!this.mSyncReady || !this.mPendingSyncs.isEmpty()) {
            if (this.mTimeoutOccurred) {
                Log.d(
                        TAG,
                        "SurfaceSyncGroup="
                                + this.mName
                                + " is not complete. mSyncReady="
                                + this.mSyncReady
                                + " mPendingSyncs="
                                + this.mPendingSyncs.size());
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

    public ITransactionReadyCallback createTransactionReadyCallback(
            final boolean parentSyncGroupMerge) {
        ITransactionReadyCallback transactionReadyCallback =
                new ITransactionReadyCallback
                        .Stub() { // from class: android.window.SurfaceSyncGroup.2
                    @Override // android.window.ITransactionReadyCallback
                    public void onTransactionReady(SurfaceControl.Transaction t) {
                        synchronized (SurfaceSyncGroup.this.mLock) {
                            if (t != null) {
                                t.sanitize(Binder.getCallingPid(), Binder.getCallingUid());
                                if (parentSyncGroupMerge) {
                                    t.merge(SurfaceSyncGroup.this.mTransaction);
                                }
                                SurfaceSyncGroup.this.mTransaction.merge(t);
                            }
                            SurfaceSyncGroup.this.mPendingSyncs.remove(this);
                            if (Trace.isTagEnabled(8L)) {
                                Trace.instantForTrack(
                                        8L,
                                        SurfaceSyncGroup.this.mTrackName,
                                        "onTransactionReady callback=" + hashCode());
                            }
                            SurfaceSyncGroup.this.checkIfSyncIsComplete();
                        }
                    }
                };
        synchronized (this.mLock) {
            if (this.mSyncReady) {
                Log.e(
                        TAG,
                        "Sync "
                                + this.mName
                                + " was already marked as ready. No more SurfaceSyncGroups can be"
                                + " added.");
                return null;
            }
            this.mPendingSyncs.add(transactionReadyCallback);
            if (Trace.isTagEnabled(8L)) {
                Trace.instantForTrack(
                        8L,
                        this.mTrackName,
                        "createTransactionReadyCallback mPendingSyncs="
                                + this.mPendingSyncs.size()
                                + " transactionReady="
                                + transactionReadyCallback.hashCode());
            }
            addTimeout();
            return transactionReadyCallback;
        }
    }

    private class ISurfaceSyncGroupImpl extends ISurfaceSyncGroup.Stub {
        private ISurfaceSyncGroupImpl() {}

        @Override // android.window.ISurfaceSyncGroup
        public boolean onAddedToSyncGroup(
                IBinder parentSyncGroupToken, boolean parentSyncGroupMerge) {
            if (Trace.isTagEnabled(8L)) {
                Trace.asyncTraceForTrackBegin(
                        8L,
                        SurfaceSyncGroup.this.mTrackName,
                        "onAddedToSyncGroup token=" + parentSyncGroupToken.hashCode(),
                        hashCode());
            }
            boolean didAdd =
                    SurfaceSyncGroup.this.addSyncToWm(
                            parentSyncGroupToken, parentSyncGroupMerge, null);
            if (Trace.isTagEnabled(8L)) {
                Trace.asyncTraceForTrackEnd(8L, SurfaceSyncGroup.this.mTrackName, hashCode());
            }
            return didAdd;
        }

        @Override // android.window.ISurfaceSyncGroup
        public boolean addToSync(ISurfaceSyncGroup surfaceSyncGroup, boolean parentSyncGroupMerge) {
            return SurfaceSyncGroup.this.add(surfaceSyncGroup, parentSyncGroupMerge, null);
        }

        SurfaceSyncGroup getSurfaceSyncGroup() {
            return SurfaceSyncGroup.this;
        }
    }

    public void toggleTimeout(boolean enable) {
        synchronized (this.mLock) {
            this.mTimeoutDisabled = !enable;
            if (this.mTimeoutAdded && !enable) {
                this.mHandler.removeCallbacksAndMessages(this);
                this.mTimeoutAdded = false;
            } else if (!this.mTimeoutAdded && enable) {
                addTimeout();
            }
        }
    }

    private void addTimeout() {
        Looper looper;
        synchronized (sHandlerThreadLock) {
            if (sHandlerThread == null) {
                sHandlerThread = new HandlerThread("SurfaceSyncGroupTimer");
                sHandlerThread.start();
            }
            looper = sHandlerThread.getLooper();
        }
        synchronized (this.mLock) {
            if (!this.mTimeoutAdded && !this.mTimeoutDisabled && looper != null) {
                if (this.mHandler == null) {
                    this.mHandler = new Handler(looper);
                }
                this.mTimeoutAdded = true;
                Runnable runnable =
                        new Runnable() { // from class:
                                         // android.window.SurfaceSyncGroup$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                SurfaceSyncGroup.this.lambda$addTimeout$6();
                            }
                        };
                this.mHandler.postDelayed(runnable, this, TRANSACTION_READY_TIMEOUT);
            }
        }
    }

    public /* synthetic */ void lambda$addTimeout$6() {
        Log.e(
                TAG,
                "Failed to receive transaction ready in "
                        + TRANSACTION_READY_TIMEOUT
                        + "ms. Marking SurfaceSyncGroup("
                        + this.mName
                        + ") as ready");
        synchronized (this.mLock) {
            this.mPendingSyncs.clear();
        }
        this.mTimeoutOccurred = true;
        markSyncReady();
    }
}
