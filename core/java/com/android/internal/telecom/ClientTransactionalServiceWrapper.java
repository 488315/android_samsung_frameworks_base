package com.android.internal.telecom;

import android.os.Binder;
import android.os.Bundle;
import android.os.OutcomeReceiver;
import android.os.ResultReceiver;
import android.telecom.CallAttributes;
import android.telecom.CallControl;
import android.telecom.CallControlCallback;
import android.telecom.CallEndpoint;
import android.telecom.CallEventCallback;
import android.telecom.CallException;
import android.telecom.DisconnectCause;
import android.telecom.PhoneAccountHandle;
import android.text.TextUtils;
import android.util.Log;

import com.android.server.telecom.flags.Flags;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class ClientTransactionalServiceWrapper {
    private static final String EXECUTOR_FAIL_MSG =
            "Telecom hit an exception while handling a CallEventCallback on an executor: ";
    private static final String TAG = ClientTransactionalServiceWrapper.class.getSimpleName();
    private final PhoneAccountHandle mPhoneAccountHandle;
    private final ClientTransactionalServiceRepository mRepository;
    private final ConcurrentHashMap<String, TransactionalCall> mCallIdToTransactionalCall =
            new ConcurrentHashMap<>();
    private final ICallEventCallback mCallEventCallback = new AnonymousClass1();

    public ClientTransactionalServiceWrapper(
            PhoneAccountHandle handle, ClientTransactionalServiceRepository repo) {
        this.mPhoneAccountHandle = handle;
        this.mRepository = repo;
    }

    public void untrackCall(String callId) {
        Log.i(TAG, TextUtils.formatSimple("removeCall: with id=[%s]", callId));
        if (this.mCallIdToTransactionalCall.containsKey(callId)) {
            TransactionalCall call = this.mCallIdToTransactionalCall.remove(callId);
            CallControl control = call.getCallControl();
            if (control != null) {
                call.setCallControl(null);
            }
        }
        if (this.mCallIdToTransactionalCall.size() == 0) {
            this.mRepository.removeServiceWrapper(this.mPhoneAccountHandle);
        }
    }

    public String trackCall(
            CallAttributes callAttributes,
            Executor executor,
            OutcomeReceiver<CallControl, CallException> pendingControl,
            CallControlCallback handshakes,
            CallEventCallback events) {
        String newCallId = UUID.randomUUID().toString();
        this.mCallIdToTransactionalCall.put(
                newCallId,
                new TransactionalCall(
                        newCallId, callAttributes, executor, pendingControl, handshakes, events));
        return newCallId;
    }

    public ICallEventCallback getCallEventCallback() {
        return this.mCallEventCallback;
    }

    class ReceiverWrapper implements Consumer<Boolean> {
        private final ResultReceiver mRepeaterReceiver;

        ReceiverWrapper(ResultReceiver resultReceiver) {
            this.mRepeaterReceiver = resultReceiver;
        }

        @Override // java.util.function.Consumer
        public void accept(Boolean clientCompletedCallbackSuccessfully) {
            if (clientCompletedCallbackSuccessfully.booleanValue()) {
                this.mRepeaterReceiver.send(0, null);
            } else {
                this.mRepeaterReceiver.send(1, null);
            }
        }

        @Override // java.util.function.Consumer
        public Consumer<Boolean> andThen(Consumer<? super Boolean> after) {
            return super.andThen(after);
        }
    }

    /* renamed from: com.android.internal.telecom.ClientTransactionalServiceWrapper$1, reason: invalid class name */
    class AnonymousClass1 extends ICallEventCallback.Stub {
        private static final String ON_ANSWER = "onAnswer";
        private static final String ON_AVAILABLE_CALL_ENDPOINTS = "onAvailableCallEndpointsChanged";
        private static final String ON_CALL_STREAMING_FAILED = "onCallStreamingFailed";
        private static final String ON_DISCONNECT = "onDisconnect";
        private static final String ON_EVENT = "onEvent";
        private static final String ON_MUTE_STATE_CHANGED = "onMuteStateChanged";
        private static final String ON_REQ_ENDPOINT_CHANGE = "onRequestEndpointChange";
        private static final String ON_SET_ACTIVE = "onSetActive";
        private static final String ON_SET_INACTIVE = "onSetInactive";
        private static final String ON_STREAMING_STARTED = "onStreamingStarted";
        private static final String ON_VIDEO_STATE_CHANGED = "onVideoStateChanged";

        AnonymousClass1() {}

        private void handleCallEventCallback(
                final String action,
                final String callId,
                ResultReceiver ackResultReceiver,
                final Object... args) {
            Log.i(
                    ClientTransactionalServiceWrapper.TAG,
                    TextUtils.formatSimple("hCEC: id=[%s], action=[%s]", callId, action));
            TransactionalCall call =
                    (TransactionalCall)
                            ClientTransactionalServiceWrapper.this.mCallIdToTransactionalCall.get(
                                    callId);
            if (call != null) {
                final CallControlCallback callback = call.getCallControlCallback();
                final ReceiverWrapper outcomeReceiverWrapper =
                        ClientTransactionalServiceWrapper.this
                        .new ReceiverWrapper(ackResultReceiver);
                long identity = Binder.clearCallingIdentity();
                try {
                    try {
                        call.getExecutor()
                                .execute(
                                        new Runnable() { // from class:
                                                         // com.android.internal.telecom.ClientTransactionalServiceWrapper$1$$ExternalSyntheticLambda0
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                ClientTransactionalServiceWrapper.AnonymousClass1
                                                        .this
                                                        .lambda$handleCallEventCallback$0(
                                                                action,
                                                                callback,
                                                                outcomeReceiverWrapper,
                                                                args,
                                                                callId);
                                            }
                                        });
                    } catch (Exception e) {
                        Log.e(
                                ClientTransactionalServiceWrapper.TAG,
                                ClientTransactionalServiceWrapper.EXECUTOR_FAIL_MSG + e);
                    }
                } finally {
                    Binder.restoreCallingIdentity(identity);
                }
            }
        }

        public /* synthetic */ void lambda$handleCallEventCallback$0(
                String action,
                CallControlCallback callback,
                ReceiverWrapper outcomeReceiverWrapper,
                Object[] args,
                String callId) {
            char c;
            switch (action.hashCode()) {
                case -1801878642:
                    if (action.equals(ON_SET_INACTIVE)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -423970853:
                    if (action.equals(ON_DISCONNECT)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 27661033:
                    if (action.equals(ON_SET_ACTIVE)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 985601661:
                    if (action.equals(ON_ANSWER)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1633591742:
                    if (action.equals(ON_STREAMING_STARTED)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    callback.onSetActive(outcomeReceiverWrapper);
                    break;
                case 1:
                    callback.onSetInactive(outcomeReceiverWrapper);
                    break;
                case 2:
                    callback.onDisconnect((DisconnectCause) args[0], outcomeReceiverWrapper);
                    ClientTransactionalServiceWrapper.this.untrackCall(callId);
                    break;
                case 3:
                    callback.onAnswer(((Integer) args[0]).intValue(), outcomeReceiverWrapper);
                    break;
                case 4:
                    callback.onCallStreamingStarted(outcomeReceiverWrapper);
                    break;
            }
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onAddCallControl(
                String callId,
                int resultCode,
                ICallControl callControl,
                CallException transactionalException) {
            Log.i(
                    ClientTransactionalServiceWrapper.TAG,
                    TextUtils.formatSimple(
                            "oACC: id=[%s], code=[%d]", callId, Integer.valueOf(resultCode)));
            TransactionalCall call =
                    (TransactionalCall)
                            ClientTransactionalServiceWrapper.this.mCallIdToTransactionalCall.get(
                                    callId);
            if (call != null) {
                OutcomeReceiver<CallControl, CallException> pendingControl =
                        call.getPendingControl();
                if (resultCode == 0) {
                    CallControl control = new CallControl(callId, callControl);
                    pendingControl.onResult(control);
                    call.setCallControl(control);
                    return;
                } else {
                    pendingControl.onError(transactionalException);
                    ClientTransactionalServiceWrapper.this.mCallIdToTransactionalCall.remove(
                            callId);
                    return;
                }
            }
            ClientTransactionalServiceWrapper.this.untrackCall(callId);
            Log.e(
                    ClientTransactionalServiceWrapper.TAG,
                    "oACC: TransactionalCall object not found for call w/ id=" + callId);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onSetActive(String callId, ResultReceiver resultReceiver) {
            handleCallEventCallback(ON_SET_ACTIVE, callId, resultReceiver, new Object[0]);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onSetInactive(String callId, ResultReceiver resultReceiver) {
            handleCallEventCallback(ON_SET_INACTIVE, callId, resultReceiver, new Object[0]);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onAnswer(String callId, int videoState, ResultReceiver resultReceiver) {
            handleCallEventCallback(ON_ANSWER, callId, resultReceiver, Integer.valueOf(videoState));
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onDisconnect(
                String callId, DisconnectCause cause, ResultReceiver resultReceiver) {
            handleCallEventCallback(ON_DISCONNECT, callId, resultReceiver, cause);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onCallEndpointChanged(String callId, CallEndpoint endpoint) {
            handleEventCallback(callId, ON_REQ_ENDPOINT_CHANGE, endpoint);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onAvailableCallEndpointsChanged(String callId, List<CallEndpoint> endpoints) {
            handleEventCallback(callId, ON_AVAILABLE_CALL_ENDPOINTS, endpoints);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onMuteStateChanged(String callId, boolean isMuted) {
            handleEventCallback(callId, ON_MUTE_STATE_CHANGED, Boolean.valueOf(isMuted));
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onVideoStateChanged(String callId, int videoState) {
            handleEventCallback(callId, ON_VIDEO_STATE_CHANGED, Integer.valueOf(videoState));
        }

        public void handleEventCallback(String callId, final String action, final Object arg) {
            Log.d(
                    ClientTransactionalServiceWrapper.TAG,
                    TextUtils.formatSimple("hEC: [%s], callId=[%s]", action, callId));
            TransactionalCall call =
                    (TransactionalCall)
                            ClientTransactionalServiceWrapper.this.mCallIdToTransactionalCall.get(
                                    callId);
            if (call != null) {
                final CallEventCallback callback = call.getCallStateCallback();
                Executor executor = call.getExecutor();
                long identity = Binder.clearCallingIdentity();
                try {
                    executor.execute(
                            new Runnable() { // from class:
                                             // com.android.internal.telecom.ClientTransactionalServiceWrapper$1$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ClientTransactionalServiceWrapper.AnonymousClass1
                                            .lambda$handleEventCallback$1(action, callback, arg);
                                }
                            });
                } finally {
                    Binder.restoreCallingIdentity(identity);
                }
            }
        }

        static /* synthetic */ void lambda$handleEventCallback$1(
                String action, CallEventCallback callback, Object arg) {
            char c;
            switch (action.hashCode()) {
                case -2134827621:
                    if (action.equals(ON_MUTE_STATE_CHANGED)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -564325214:
                    if (action.equals(ON_CALL_STREAMING_FAILED)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -103636834:
                    if (action.equals(ON_AVAILABLE_CALL_ENDPOINTS)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 581203167:
                    if (action.equals(ON_VIDEO_STATE_CHANGED)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 879322485:
                    if (action.equals(ON_REQ_ENDPOINT_CHANGE)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    callback.onCallEndpointChanged((CallEndpoint) arg);
                    break;
                case 1:
                    callback.onAvailableCallEndpointsChanged((List) arg);
                    break;
                case 2:
                    callback.onMuteStateChanged(((Boolean) arg).booleanValue());
                    break;
                case 3:
                    if (Flags.transactionalVideoState()) {
                        callback.onVideoStateChanged(((Integer) arg).intValue());
                        break;
                    }
                    break;
                case 4:
                    callback.onCallStreamingFailed(((Integer) arg).intValue());
                    break;
            }
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void removeCallFromTransactionalServiceWrapper(String callId) {
            ClientTransactionalServiceWrapper.this.untrackCall(callId);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onCallStreamingStarted(String callId, ResultReceiver resultReceiver) {
            handleCallEventCallback(ON_STREAMING_STARTED, callId, resultReceiver, new Object[0]);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onCallStreamingFailed(String callId, int reason) {
            Log.i(
                    ClientTransactionalServiceWrapper.TAG,
                    TextUtils.formatSimple(
                            "oCSF: id=[%s], reason=[%s]", callId, Integer.valueOf(reason)));
            handleEventCallback(callId, ON_CALL_STREAMING_FAILED, Integer.valueOf(reason));
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onEvent(String callId, final String event, final Bundle extras) {
            TransactionalCall call =
                    (TransactionalCall)
                            ClientTransactionalServiceWrapper.this.mCallIdToTransactionalCall.get(
                                    callId);
            if (call != null) {
                final CallEventCallback callback = call.getCallStateCallback();
                Executor executor = call.getExecutor();
                long identity = Binder.clearCallingIdentity();
                try {
                    executor.execute(
                            new Runnable() { // from class:
                                             // com.android.internal.telecom.ClientTransactionalServiceWrapper$1$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CallEventCallback.this.onEvent(event, extras);
                                }
                            });
                } finally {
                    Binder.restoreCallingIdentity(identity);
                }
            }
        }
    }
}
