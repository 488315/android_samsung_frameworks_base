package android.content;

import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.QueuedWork;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.os.DebugStore;
import com.android.internal.os.Flags;

/* loaded from: classes.dex */
public abstract class BroadcastReceiver {
    private static final boolean DEBUG_STORE_ENABLED = Flags.debugStoreEnabled();
    private boolean mDebugUnregister;
    private PendingResult mPendingResult;

    public abstract void onReceive(Context context, Intent intent);

    public final void setOrderedHint(boolean z) {
    }

    public static class PendingResult {
        public static final int TYPE_COMPONENT = 0;
        public static final int TYPE_REGISTERED = 1;
        public static final int TYPE_UNREGISTERED = 2;
        boolean mAbortBroadcast;
        final boolean mAssumeDeliveredHint;
        boolean mFinished;
        final int mFlags;
        final boolean mInitialStickyHint;
        final boolean mOrderedHint;
        String mReceiverClassName;
        int mResultCode;
        String mResultData;
        Bundle mResultExtras;
        final int mSendingUser;
        final String mSentFromPackage;
        final int mSentFromUid;
        final IBinder mToken;
        final int mType;

        public static boolean guessAssumeDelivered(int i, boolean z) {
            if (i == 0) {
                return false;
            }
            return !z || i == 2;
        }

        public PendingResult(int i, String str, Bundle bundle, int i2, boolean z, boolean z2, IBinder iBinder, int i3, int i4) {
            this(i, str, bundle, i2, z, z2, guessAssumeDelivered(i2, z), iBinder, i3, i4, -1, null);
        }

        public PendingResult(int i, String str, Bundle bundle, int i2, boolean z, boolean z2, boolean z3, IBinder iBinder, int i3, int i4, int i5, String str2) {
            this.mResultCode = i;
            this.mResultData = str;
            this.mResultExtras = bundle;
            this.mType = i2;
            this.mOrderedHint = z;
            this.mInitialStickyHint = z2;
            this.mAssumeDeliveredHint = z3;
            this.mToken = iBinder;
            this.mSendingUser = i3;
            this.mFlags = i4;
            this.mSentFromUid = i5;
            this.mSentFromPackage = str2;
        }

        public final void setResultCode(int i) {
            checkSynchronousHint();
            this.mResultCode = i;
        }

        public final int getResultCode() {
            return this.mResultCode;
        }

        public final void setResultData(String str) {
            checkSynchronousHint();
            this.mResultData = str;
        }

        public final String getResultData() {
            return this.mResultData;
        }

        public final void setResultExtras(Bundle bundle) {
            checkSynchronousHint();
            this.mResultExtras = bundle;
        }

        public final Bundle getResultExtras(boolean z) {
            Bundle bundle = this.mResultExtras;
            if (!z || bundle != null) {
                return bundle;
            }
            Bundle bundle2 = new Bundle();
            this.mResultExtras = bundle2;
            return bundle2;
        }

        public final void setResult(int i, String str, Bundle bundle) {
            checkSynchronousHint();
            this.mResultCode = i;
            this.mResultData = str;
            this.mResultExtras = bundle;
        }

        public final boolean getAbortBroadcast() {
            return this.mAbortBroadcast;
        }

        public final void abortBroadcast() {
            checkSynchronousHint();
            this.mAbortBroadcast = true;
        }

        public final void clearAbortBroadcast() {
            this.mAbortBroadcast = false;
        }

        public final void finish() {
            if (Trace.isTagEnabled(64L)) {
                Trace.traceCounter(64L, "PendingResult#finish#ClassName:" + this.mReceiverClassName, 1);
            }
            if (BroadcastReceiver.DEBUG_STORE_ENABLED) {
                DebugStore.recordFinish(System.identityHashCode(this));
            }
            if (this.mType == 0) {
                final IActivityManager service = ActivityManager.getService();
                if (QueuedWork.hasPendingWork()) {
                    QueuedWork.queue(new Runnable() { // from class: android.content.BroadcastReceiver.PendingResult.1
                        @Override // java.lang.Runnable
                        public void run() {
                            PendingResult.this.sendFinished(service);
                        }
                    }, false);
                    return;
                } else {
                    sendFinished(service);
                    return;
                }
            }
            sendFinished(ActivityManager.getService());
        }

        public void setExtrasClassLoader(ClassLoader classLoader) {
            Bundle bundle = this.mResultExtras;
            if (bundle != null) {
                bundle.setClassLoader(classLoader);
            }
        }

        public void sendFinished(IActivityManager iActivityManager) {
            synchronized (this) {
                if (this.mFinished) {
                    throw new IllegalStateException("Broadcast already finished");
                }
                this.mFinished = true;
                try {
                    Bundle bundle = this.mResultExtras;
                    if (bundle != null) {
                        bundle.setAllowFds(false);
                    }
                    if (!this.mAssumeDeliveredHint) {
                        if (this.mOrderedHint) {
                            iActivityManager.finishReceiver(this.mToken, this.mResultCode, this.mResultData, this.mResultExtras, this.mAbortBroadcast, this.mFlags);
                        } else {
                            iActivityManager.finishReceiver(this.mToken, 0, null, null, false, this.mFlags);
                        }
                    }
                } catch (RemoteException unused) {
                }
            }
        }

        public int getSendingUserId() {
            return this.mSendingUser;
        }

        public int getSentFromUid() {
            return this.mSentFromUid;
        }

        public String getSentFromPackage() {
            return this.mSentFromPackage;
        }

        void checkSynchronousHint() {
            if (this.mOrderedHint || this.mInitialStickyHint) {
                return;
            }
            RuntimeException runtimeException = new RuntimeException("BroadcastReceiver trying to return result during a non-ordered broadcast");
            Log.e("BroadcastReceiver", runtimeException.getMessage(), runtimeException);
        }
    }

    public final PendingResult goAsync() {
        PendingResult pendingResult = this.mPendingResult;
        this.mPendingResult = null;
        if (DEBUG_STORE_ENABLED) {
            DebugStore.recordGoAsync(System.identityHashCode(pendingResult));
        }
        if (pendingResult != null && Trace.isTagEnabled(64L)) {
            pendingResult.mReceiverClassName = getClass().getName();
            Trace.traceCounter(64L, "BroadcastReceiver#goAsync#ClassName:" + pendingResult.mReceiverClassName, 1);
        }
        return pendingResult;
    }

    public IBinder peekService(Context context, Intent intent) {
        IActivityManager service = ActivityManager.getService();
        try {
            intent.prepareToLeaveProcess(context);
            return service.peekService(intent, intent.resolveTypeIfNeeded(context.getContentResolver()), context.getOpPackageName());
        } catch (RemoteException unused) {
            return null;
        }
    }

    public final void setResultCode(int i) {
        checkSynchronousHint();
        this.mPendingResult.mResultCode = i;
    }

    public final int getResultCode() {
        PendingResult pendingResult = this.mPendingResult;
        if (pendingResult != null) {
            return pendingResult.mResultCode;
        }
        return 0;
    }

    public final void setResultData(String str) {
        checkSynchronousHint();
        this.mPendingResult.mResultData = str;
    }

    public final String getResultData() {
        PendingResult pendingResult = this.mPendingResult;
        if (pendingResult != null) {
            return pendingResult.mResultData;
        }
        return null;
    }

    public final void setResultExtras(Bundle bundle) {
        checkSynchronousHint();
        this.mPendingResult.mResultExtras = bundle;
    }

    public final Bundle getResultExtras(boolean z) {
        PendingResult pendingResult = this.mPendingResult;
        if (pendingResult == null) {
            return null;
        }
        Bundle bundle = pendingResult.mResultExtras;
        if (!z || bundle != null) {
            return bundle;
        }
        PendingResult pendingResult2 = this.mPendingResult;
        Bundle bundle2 = new Bundle();
        pendingResult2.mResultExtras = bundle2;
        return bundle2;
    }

    public final void setResult(int i, String str, Bundle bundle) {
        checkSynchronousHint();
        this.mPendingResult.mResultCode = i;
        this.mPendingResult.mResultData = str;
        this.mPendingResult.mResultExtras = bundle;
    }

    public final boolean getAbortBroadcast() {
        PendingResult pendingResult = this.mPendingResult;
        if (pendingResult != null) {
            return pendingResult.mAbortBroadcast;
        }
        return false;
    }

    public final void abortBroadcast() {
        checkSynchronousHint();
        this.mPendingResult.mAbortBroadcast = true;
    }

    public final void clearAbortBroadcast() {
        PendingResult pendingResult = this.mPendingResult;
        if (pendingResult != null) {
            pendingResult.mAbortBroadcast = false;
        }
    }

    public final boolean isOrderedBroadcast() {
        PendingResult pendingResult = this.mPendingResult;
        if (pendingResult != null) {
            return pendingResult.mOrderedHint;
        }
        return false;
    }

    public final boolean isInitialStickyBroadcast() {
        PendingResult pendingResult = this.mPendingResult;
        if (pendingResult != null) {
            return pendingResult.mInitialStickyHint;
        }
        return false;
    }

    public final void setPendingResult(PendingResult pendingResult) {
        this.mPendingResult = pendingResult;
    }

    public final PendingResult getPendingResult() {
        return this.mPendingResult;
    }

    @SystemApi
    public final UserHandle getSendingUser() {
        return UserHandle.of(getSendingUserId());
    }

    public int getSendingUserId() {
        return this.mPendingResult.mSendingUser;
    }

    public int getSentFromUid() {
        PendingResult pendingResult = this.mPendingResult;
        if (pendingResult != null) {
            return pendingResult.mSentFromUid;
        }
        return -1;
    }

    public String getSentFromPackage() {
        PendingResult pendingResult = this.mPendingResult;
        if (pendingResult != null) {
            return pendingResult.mSentFromPackage;
        }
        return null;
    }

    public final void setDebugUnregister(boolean z) {
        this.mDebugUnregister = z;
    }

    public final boolean getDebugUnregister() {
        return this.mDebugUnregister;
    }

    void checkSynchronousHint() {
        PendingResult pendingResult = this.mPendingResult;
        if (pendingResult == null) {
            throw new IllegalStateException("Call while result is not pending");
        }
        if (pendingResult.mOrderedHint || this.mPendingResult.mInitialStickyHint) {
            return;
        }
        RuntimeException runtimeException = new RuntimeException("BroadcastReceiver trying to return result during a non-ordered broadcast");
        Log.e("BroadcastReceiver", runtimeException.getMessage(), runtimeException);
    }
}
