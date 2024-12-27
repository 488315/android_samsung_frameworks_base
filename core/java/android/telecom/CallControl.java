package android.telecom;

import android.media.MediaMetrics;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.OutcomeReceiver;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.telecom.CallControl;
import android.text.TextUtils;
import com.android.internal.telecom.ICallControl;
import com.android.internal.telephony.SemRILConstants;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class CallControl {
    private static final String TAG = CallControl.class.getSimpleName();
    private final String mCallId;
    private final ICallControl mServerInterface;

    public CallControl(String callId, ICallControl serverInterface) {
        this.mCallId = callId;
        this.mServerInterface = serverInterface;
    }

    public ParcelUuid getCallId() {
        return ParcelUuid.fromString(this.mCallId);
    }

    public void setActive(Executor executor, OutcomeReceiver<Void, CallException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            this.mServerInterface.setActive(this.mCallId, new CallControlResultReceiver("setActive", executor, callback));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void answer(int videoState, Executor executor, OutcomeReceiver<Void, CallException> callback) {
        validateVideoState(videoState);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            this.mServerInterface.answer(videoState, this.mCallId, new CallControlResultReceiver(SemRILConstants.CmcCall.CMC_CALL_SD_ANSWER, executor, callback));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void setInactive(Executor executor, OutcomeReceiver<Void, CallException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            this.mServerInterface.setInactive(this.mCallId, new CallControlResultReceiver("setInactive", executor, callback));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void disconnect(DisconnectCause disconnectCause, Executor executor, OutcomeReceiver<Void, CallException> callback) {
        Objects.requireNonNull(disconnectCause);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        validateDisconnectCause(disconnectCause);
        try {
            this.mServerInterface.disconnect(this.mCallId, disconnectCause, new CallControlResultReceiver(MediaMetrics.Value.DISCONNECT, executor, callback));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void startCallStreaming(Executor executor, OutcomeReceiver<Void, CallException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            this.mServerInterface.startCallStreaming(this.mCallId, new CallControlResultReceiver("startCallStreaming", executor, callback));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void requestCallEndpointChange(CallEndpoint callEndpoint, Executor executor, OutcomeReceiver<Void, CallException> callback) {
        Objects.requireNonNull(callEndpoint);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            this.mServerInterface.requestCallEndpointChange(callEndpoint, new CallControlResultReceiver("requestCallEndpointChange", executor, callback));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void requestMuteState(boolean isMuted, Executor executor, OutcomeReceiver<Void, CallException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            this.mServerInterface.setMuteState(isMuted, new CallControlResultReceiver("requestMuteState", executor, callback));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void requestVideoState(int videoState, Executor executor, OutcomeReceiver<Void, CallException> callback) {
        validateVideoState(videoState);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            this.mServerInterface.requestVideoState(videoState, this.mCallId, new CallControlResultReceiver("requestVideoState", executor, callback));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void sendEvent(String event, Bundle extras) {
        Objects.requireNonNull(event);
        Objects.requireNonNull(extras);
        try {
            this.mServerInterface.sendEvent(this.mCallId, event, extras);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class CallControlResultReceiver extends ResultReceiver {
        private final String mCallingMethod;
        private final OutcomeReceiver<Void, CallException> mClientCallback;
        private final Executor mExecutor;

        CallControlResultReceiver(String method, Executor executor, OutcomeReceiver<Void, CallException> clientCallback) {
            super((Handler) null);
            this.mCallingMethod = method;
            this.mExecutor = executor;
            this.mClientCallback = clientCallback;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int resultCode, final Bundle resultData) {
            Log.d(CallControl.TAG, "%s: oRR: resultCode=[%s]", this.mCallingMethod, Integer.valueOf(resultCode));
            super.onReceiveResult(resultCode, resultData);
            long identity = Binder.clearCallingIdentity();
            try {
                if (resultCode == 0) {
                    this.mExecutor.execute(new Runnable() { // from class: android.telecom.CallControl$CallControlResultReceiver$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            CallControl.CallControlResultReceiver.this.lambda$onReceiveResult$0();
                        }
                    });
                } else {
                    this.mExecutor.execute(new Runnable() { // from class: android.telecom.CallControl$CallControlResultReceiver$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            CallControl.CallControlResultReceiver.this.lambda$onReceiveResult$1(resultData);
                        }
                    });
                }
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceiveResult$0() {
            this.mClientCallback.onResult(null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceiveResult$1(Bundle resultData) {
            this.mClientCallback.onError(CallControl.this.getTransactionException(resultData));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CallException getTransactionException(Bundle resultData) {
        if (resultData != null && resultData.containsKey(CallException.TRANSACTION_EXCEPTION_KEY)) {
            return (CallException) resultData.getParcelable(CallException.TRANSACTION_EXCEPTION_KEY, CallException.class);
        }
        return new CallException("unknown error", 1);
    }

    private void validateDisconnectCause(DisconnectCause disconnectCause) {
        int code = disconnectCause.getCode();
        if (code != 2 && code != 3 && code != 5 && code != 6) {
            throw new IllegalArgumentException(TextUtils.formatSimple("The DisconnectCause code provided, %d , is not a valid Disconnect code. Valid DisconnectCause codes are limited to [DisconnectCause.LOCAL, DisconnectCause.REMOTE, DisconnectCause.MISSED, or DisconnectCause.REJECTED]", Integer.valueOf(disconnectCause.getCode())));
        }
    }

    private void validateVideoState(int videoState) {
        if (videoState != 1 && videoState != 2) {
            throw new IllegalArgumentException(TextUtils.formatSimple("The VideoState argument passed in, %d , is not a valid VideoState. The VideoState choices are limited to CallAttributes.AUDIO_CALL orCallAttributes.VIDEO_CALL", Integer.valueOf(videoState)));
        }
    }
}
