package android.telephony.ims.stub;

import android.annotation.SystemApi;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.os.RemoteException;
import android.telephony.ims.ImsExternalCallState;
import android.util.Log;

import com.android.ims.internal.IImsExternalCallStateListener;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.internal.telephony.util.TelephonyUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes4.dex */
public class ImsMultiEndpointImplBase {
    private static final String TAG = "MultiEndpointImplBase";
    private IImsExternalCallStateListener mListener;
    private final Object mLock = new Object();
    private Executor mExecutor = new PendingIntent$$ExternalSyntheticLambda0();
    private final IImsMultiEndpoint mImsMultiEndpoint = new AnonymousClass1();

    /* renamed from: android.telephony.ims.stub.ImsMultiEndpointImplBase$1, reason: invalid class name */
    class AnonymousClass1 extends IImsMultiEndpoint.Stub {
        AnonymousClass1() {}

        @Override // com.android.ims.internal.IImsMultiEndpoint
        public void setListener(final IImsExternalCallStateListener listener)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.ims.stub.ImsMultiEndpointImplBase$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ImsMultiEndpointImplBase.AnonymousClass1.this.lambda$setListener$0(
                                    listener);
                        }
                    },
                    "setListener");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setListener$0(IImsExternalCallStateListener listener) {
            if (ImsMultiEndpointImplBase.this.mListener != null
                    && !ImsMultiEndpointImplBase.this.mListener.asBinder().isBinderAlive()) {
                Log.w(ImsMultiEndpointImplBase.TAG, "setListener: discarding dead Binder");
                ImsMultiEndpointImplBase.this.mListener = null;
            }
            if (ImsMultiEndpointImplBase.this.mListener != null
                    && listener != null
                    && Objects.equals(
                            ImsMultiEndpointImplBase.this.mListener.asBinder(),
                            listener.asBinder())) {
                return;
            }
            if (listener == null) {
                ImsMultiEndpointImplBase.this.mListener = null;
            } else if (listener != null && ImsMultiEndpointImplBase.this.mListener == null) {
                ImsMultiEndpointImplBase.this.mListener = listener;
            } else {
                Log.w(
                        ImsMultiEndpointImplBase.TAG,
                        "setListener is being called when there is already an active listener");
                ImsMultiEndpointImplBase.this.mListener = listener;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestImsExternalCallStateInfo$1() {
            ImsMultiEndpointImplBase.this.requestImsExternalCallStateInfo();
        }

        @Override // com.android.ims.internal.IImsMultiEndpoint
        public void requestImsExternalCallStateInfo() throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.ims.stub.ImsMultiEndpointImplBase$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            ImsMultiEndpointImplBase.AnonymousClass1.this
                                    .lambda$requestImsExternalCallStateInfo$1();
                        }
                    },
                    "requestImsExternalCallStateInfo");
        }

        private void executeMethodAsync(final Runnable r, String errorLogName) {
            try {
                CompletableFuture.runAsync(
                                new Runnable() { // from class:
                                                 // android.telephony.ims.stub.ImsMultiEndpointImplBase$1$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        TelephonyUtils.runWithCleanCallingIdentity(r);
                                    }
                                },
                                ImsMultiEndpointImplBase.this.mExecutor)
                        .join();
            } catch (CancellationException | CompletionException e) {
                Log.w(
                        ImsMultiEndpointImplBase.TAG,
                        "ImsMultiEndpointImplBase Binder - "
                                + errorLogName
                                + " exception: "
                                + e.getMessage());
            }
        }
    }

    public IImsMultiEndpoint getIImsMultiEndpoint() {
        return this.mImsMultiEndpoint;
    }

    public final void onImsExternalCallStateUpdate(List<ImsExternalCallState> externalCallDialogs) {
        IImsExternalCallStateListener listener;
        Log.d(TAG, "ims external call state update triggered.");
        synchronized (this.mLock) {
            listener = this.mListener;
        }
        if (listener != null) {
            try {
                listener.onImsExternalCallStateUpdate(externalCallDialogs);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void requestImsExternalCallStateInfo() {
        Log.d(TAG, "requestImsExternalCallStateInfo() not implemented");
    }

    public final void setDefaultExecutor(Executor executor) {
        this.mExecutor = executor;
    }
}
