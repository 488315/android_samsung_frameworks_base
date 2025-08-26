package android.telecom;

import android.media.MediaMetrics;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.OutcomeReceiver;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.text.TextUtils;
import com.android.internal.telecom.ICallControl;
import com.android.internal.telephony.SemRILConstants;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public final class CallControl {
    private static final String TAG = "CallControl";
    private final String mCallId;
    private final ICallControl mServerInterface;

    public CallControl(String str, ICallControl iCallControl) {
        this.mCallId = str;
        this.mServerInterface = iCallControl;
    }

    public ParcelUuid getCallId() {
        return ParcelUuid.fromString(this.mCallId);
    }

    public void setActive(Executor executor, OutcomeReceiver<Void, CallException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            this.mServerInterface.setActive(this.mCallId, new CallControlResultReceiver("setActive", executor, outcomeReceiver));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void answer(int i, Executor executor, OutcomeReceiver<Void, CallException> outcomeReceiver) {
        validateVideoState(i);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            this.mServerInterface.answer(i, this.mCallId, new CallControlResultReceiver(SemRILConstants.CmcCall.CMC_CALL_SD_ANSWER, executor, outcomeReceiver));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void setInactive(Executor executor, OutcomeReceiver<Void, CallException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            this.mServerInterface.setInactive(this.mCallId, new CallControlResultReceiver("setInactive", executor, outcomeReceiver));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void disconnect(DisconnectCause disconnectCause, Executor executor, OutcomeReceiver<Void, CallException> outcomeReceiver) {
        Objects.requireNonNull(disconnectCause);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        validateDisconnectCause(disconnectCause);
        try {
            this.mServerInterface.disconnect(this.mCallId, disconnectCause, new CallControlResultReceiver(MediaMetrics.Value.DISCONNECT, executor, outcomeReceiver));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void startCallStreaming(Executor executor, OutcomeReceiver<Void, CallException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            this.mServerInterface.startCallStreaming(this.mCallId, new CallControlResultReceiver("startCallStreaming", executor, outcomeReceiver));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void requestCallEndpointChange(CallEndpoint callEndpoint, Executor executor, OutcomeReceiver<Void, CallException> outcomeReceiver) {
        Objects.requireNonNull(callEndpoint);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            this.mServerInterface.requestCallEndpointChange(callEndpoint, new CallControlResultReceiver("requestCallEndpointChange", executor, outcomeReceiver));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void requestMuteState(boolean z, Executor executor, OutcomeReceiver<Void, CallException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            this.mServerInterface.setMuteState(z, new CallControlResultReceiver("requestMuteState", executor, outcomeReceiver));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void requestVideoState(int i, Executor executor, OutcomeReceiver<Void, CallException> outcomeReceiver) {
        validateVideoState(i);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            this.mServerInterface.requestVideoState(i, this.mCallId, new CallControlResultReceiver("requestVideoState", executor, outcomeReceiver));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void sendEvent(String str, Bundle bundle) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(bundle);
        try {
            this.mServerInterface.sendEvent(this.mCallId, str, bundle);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class CallControlResultReceiver extends ResultReceiver {
        private final String mCallingMethod;
        private final OutcomeReceiver<Void, CallException> mClientCallback;
        private final Executor mExecutor;

        CallControlResultReceiver(String str, Executor executor, OutcomeReceiver<Void, CallException> outcomeReceiver) {
            super((Handler) null);
            this.mCallingMethod = str;
            this.mExecutor = executor;
            this.mClientCallback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, final Bundle bundle) {
            Log.d(CallControl.TAG, "%s: oRR: resultCode=[%s]", this.mCallingMethod, Integer.valueOf(i));
            super.onReceiveResult(i, bundle);
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (i == 0) {
                    this.mExecutor.execute(new Runnable() { // from class: android.telecom.CallControl$CallControlResultReceiver$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onReceiveResult$0();
                        }
                    });
                } else {
                    this.mExecutor.execute(new Runnable() { // from class: android.telecom.CallControl$CallControlResultReceiver$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onReceiveResult$1(bundle);
                        }
                    });
                }
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceiveResult$0() {
            this.mClientCallback.onResult(null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceiveResult$1(Bundle bundle) {
            this.mClientCallback.onError(CallControl.this.getTransactionException(bundle));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CallException getTransactionException(Bundle bundle) {
        if (bundle != null && bundle.containsKey(CallException.TRANSACTION_EXCEPTION_KEY)) {
            return (CallException) bundle.getParcelable(CallException.TRANSACTION_EXCEPTION_KEY, CallException.class);
        }
        return new CallException("unknown error", 1);
    }

    private void validateDisconnectCause(DisconnectCause disconnectCause) {
        int code = disconnectCause.getCode();
        if (code != 2 && code != 3 && code != 5 && code != 6) {
            throw new IllegalArgumentException(TextUtils.formatSimple("The DisconnectCause code provided, %d , is not a valid Disconnect code. Valid DisconnectCause codes are limited to [DisconnectCause.LOCAL, DisconnectCause.REMOTE, DisconnectCause.MISSED, or DisconnectCause.REJECTED]", Integer.valueOf(disconnectCause.getCode())));
        }
    }

    private void validateVideoState(int i) {
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(TextUtils.formatSimple("The VideoState argument passed in, %d , is not a valid VideoState. The VideoState choices are limited to CallAttributes.AUDIO_CALL orCallAttributes.VIDEO_CALL", Integer.valueOf(i)));
        }
    }
}
