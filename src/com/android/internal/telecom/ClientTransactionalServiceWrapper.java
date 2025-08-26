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
import com.android.internal.telecom.ClientTransactionalServiceWrapper;
import com.android.internal.telecom.ICallEventCallback;
import com.android.server.telecom.flags.Flags;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class ClientTransactionalServiceWrapper {
    private static final String EXECUTOR_FAIL_MSG = "Telecom hit an exception while handling a CallEventCallback on an executor: ";
    private static final String TAG = "ClientTransactionalServiceWrapper";
    private final PhoneAccountHandle mPhoneAccountHandle;
    private final ClientTransactionalServiceRepository mRepository;
    private final ConcurrentHashMap<String, TransactionalCall> mCallIdToTransactionalCall = new ConcurrentHashMap<>();
    private final ICallEventCallback mCallEventCallback = new AnonymousClass1();

    public ClientTransactionalServiceWrapper(PhoneAccountHandle phoneAccountHandle, ClientTransactionalServiceRepository clientTransactionalServiceRepository) {
        this.mPhoneAccountHandle = phoneAccountHandle;
        this.mRepository = clientTransactionalServiceRepository;
    }

    public void untrackCall(String str) {
        Log.i(TAG, TextUtils.formatSimple("removeCall: with id=[%s]", str));
        if (this.mCallIdToTransactionalCall.containsKey(str)) {
            TransactionalCall transactionalCallRemove = this.mCallIdToTransactionalCall.remove(str);
            if (transactionalCallRemove.getCallControl() != null) {
                transactionalCallRemove.setCallControl(null);
            }
        }
        if (this.mCallIdToTransactionalCall.size() == 0) {
            this.mRepository.removeServiceWrapper(this.mPhoneAccountHandle);
        }
    }

    public String trackCall(CallAttributes callAttributes, Executor executor, OutcomeReceiver<CallControl, CallException> outcomeReceiver, CallControlCallback callControlCallback, CallEventCallback callEventCallback) {
        String string = UUID.randomUUID().toString();
        this.mCallIdToTransactionalCall.put(string, new TransactionalCall(string, callAttributes, executor, outcomeReceiver, callControlCallback, callEventCallback));
        return string;
    }

    public ICallEventCallback getCallEventCallback() {
        return this.mCallEventCallback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ReceiverWrapper implements Consumer<Boolean> {
        private final ResultReceiver mRepeaterReceiver;

        ReceiverWrapper(ClientTransactionalServiceWrapper clientTransactionalServiceWrapper, ResultReceiver resultReceiver) {
            this.mRepeaterReceiver = resultReceiver;
        }

        @Override // java.util.function.Consumer
        public void accept(Boolean bool) {
            if (bool.booleanValue()) {
                this.mRepeaterReceiver.send(0, null);
            } else {
                this.mRepeaterReceiver.send(1, null);
            }
        }

        @Override // java.util.function.Consumer
        public Consumer<Boolean> andThen(Consumer<? super Boolean> consumer) {
            return super.andThen(consumer);
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

        AnonymousClass1() {
        }

        private void handleCallEventCallback(final String str, final String str2, ResultReceiver resultReceiver, final Object... objArr) {
            Log.i(ClientTransactionalServiceWrapper.TAG, TextUtils.formatSimple("hCEC: id=[%s], action=[%s]", str2, str));
            TransactionalCall transactionalCall = (TransactionalCall) ClientTransactionalServiceWrapper.this.mCallIdToTransactionalCall.get(str2);
            if (transactionalCall != null) {
                final CallControlCallback callControlCallback = transactionalCall.getCallControlCallback();
                final ReceiverWrapper receiverWrapper = new ReceiverWrapper(ClientTransactionalServiceWrapper.this, resultReceiver);
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        transactionalCall.getExecutor().execute(new Runnable() { // from class: com.android.internal.telecom.ClientTransactionalServiceWrapper$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleCallEventCallback$0(str, callControlCallback, receiverWrapper, objArr, str2);
                            }
                        });
                        Binder.restoreCallingIdentity(jClearCallingIdentity);
                    } catch (Exception e) {
                        Log.e(ClientTransactionalServiceWrapper.TAG, ClientTransactionalServiceWrapper.EXECUTOR_FAIL_MSG + e);
                        Binder.restoreCallingIdentity(jClearCallingIdentity);
                    }
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleCallEventCallback$0(String str, CallControlCallback callControlCallback, ReceiverWrapper receiverWrapper, Object[] objArr, String str2) {
            str.hashCode();
            switch (str) {
                case "onSetInactive":
                    callControlCallback.onSetInactive(receiverWrapper);
                    break;
                case "onDisconnect":
                    callControlCallback.onDisconnect((DisconnectCause) objArr[0], receiverWrapper);
                    ClientTransactionalServiceWrapper.this.untrackCall(str2);
                    break;
                case "onSetActive":
                    callControlCallback.onSetActive(receiverWrapper);
                    break;
                case "onAnswer":
                    callControlCallback.onAnswer(((Integer) objArr[0]).intValue(), receiverWrapper);
                    break;
                case "onStreamingStarted":
                    callControlCallback.onCallStreamingStarted(receiverWrapper);
                    break;
            }
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onAddCallControl(String str, int i, ICallControl iCallControl, CallException callException) {
            Log.i(ClientTransactionalServiceWrapper.TAG, TextUtils.formatSimple("oACC: id=[%s], code=[%d]", str, Integer.valueOf(i)));
            TransactionalCall transactionalCall = (TransactionalCall) ClientTransactionalServiceWrapper.this.mCallIdToTransactionalCall.get(str);
            if (transactionalCall != null) {
                OutcomeReceiver<CallControl, CallException> pendingControl = transactionalCall.getPendingControl();
                if (i == 0) {
                    CallControl callControl = new CallControl(str, iCallControl);
                    pendingControl.onResult(callControl);
                    transactionalCall.setCallControl(callControl);
                    return;
                } else {
                    pendingControl.onError(callException);
                    ClientTransactionalServiceWrapper.this.mCallIdToTransactionalCall.remove(str);
                    return;
                }
            }
            ClientTransactionalServiceWrapper.this.untrackCall(str);
            Log.e(ClientTransactionalServiceWrapper.TAG, "oACC: TransactionalCall object not found for call w/ id=" + str);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onSetActive(String str, ResultReceiver resultReceiver) {
            handleCallEventCallback(ON_SET_ACTIVE, str, resultReceiver, new Object[0]);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onSetInactive(String str, ResultReceiver resultReceiver) {
            handleCallEventCallback(ON_SET_INACTIVE, str, resultReceiver, new Object[0]);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onAnswer(String str, int i, ResultReceiver resultReceiver) {
            handleCallEventCallback(ON_ANSWER, str, resultReceiver, Integer.valueOf(i));
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onDisconnect(String str, DisconnectCause disconnectCause, ResultReceiver resultReceiver) {
            handleCallEventCallback(ON_DISCONNECT, str, resultReceiver, disconnectCause);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onCallEndpointChanged(String str, CallEndpoint callEndpoint) {
            handleEventCallback(str, ON_REQ_ENDPOINT_CHANGE, callEndpoint);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onAvailableCallEndpointsChanged(String str, List<CallEndpoint> list) {
            handleEventCallback(str, ON_AVAILABLE_CALL_ENDPOINTS, list);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onMuteStateChanged(String str, boolean z) {
            handleEventCallback(str, ON_MUTE_STATE_CHANGED, Boolean.valueOf(z));
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onVideoStateChanged(String str, int i) {
            handleEventCallback(str, ON_VIDEO_STATE_CHANGED, Integer.valueOf(i));
        }

        public void handleEventCallback(String str, final String str2, final Object obj) {
            Log.d(ClientTransactionalServiceWrapper.TAG, TextUtils.formatSimple("hEC: [%s], callId=[%s]", str2, str));
            TransactionalCall transactionalCall = (TransactionalCall) ClientTransactionalServiceWrapper.this.mCallIdToTransactionalCall.get(str);
            if (transactionalCall != null) {
                final CallEventCallback callStateCallback = transactionalCall.getCallStateCallback();
                Executor executor = transactionalCall.getExecutor();
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    executor.execute(new Runnable() { // from class: com.android.internal.telecom.ClientTransactionalServiceWrapper$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ClientTransactionalServiceWrapper.AnonymousClass1.lambda$handleEventCallback$1(str2, callStateCallback, obj);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            }
        }

        static /* synthetic */ void lambda$handleEventCallback$1(String str, CallEventCallback callEventCallback, Object obj) {
            str.hashCode();
            switch (str) {
                case "onMuteStateChanged":
                    callEventCallback.onMuteStateChanged(((Boolean) obj).booleanValue());
                    break;
                case "onCallStreamingFailed":
                    callEventCallback.onCallStreamingFailed(((Integer) obj).intValue());
                    break;
                case "onAvailableCallEndpointsChanged":
                    callEventCallback.onAvailableCallEndpointsChanged((List) obj);
                    break;
                case "onVideoStateChanged":
                    if (Flags.transactionalVideoState()) {
                        callEventCallback.onVideoStateChanged(((Integer) obj).intValue());
                        break;
                    }
                    break;
                case "onRequestEndpointChange":
                    callEventCallback.onCallEndpointChanged((CallEndpoint) obj);
                    break;
            }
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void removeCallFromTransactionalServiceWrapper(String str) {
            ClientTransactionalServiceWrapper.this.untrackCall(str);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onCallStreamingStarted(String str, ResultReceiver resultReceiver) {
            handleCallEventCallback(ON_STREAMING_STARTED, str, resultReceiver, new Object[0]);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onCallStreamingFailed(String str, int i) {
            Log.i(ClientTransactionalServiceWrapper.TAG, TextUtils.formatSimple("oCSF: id=[%s], reason=[%s]", str, Integer.valueOf(i)));
            handleEventCallback(str, ON_CALL_STREAMING_FAILED, Integer.valueOf(i));
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onEvent(String str, final String str2, final Bundle bundle) {
            TransactionalCall transactionalCall = (TransactionalCall) ClientTransactionalServiceWrapper.this.mCallIdToTransactionalCall.get(str);
            if (transactionalCall != null) {
                final CallEventCallback callStateCallback = transactionalCall.getCallStateCallback();
                Executor executor = transactionalCall.getExecutor();
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    executor.execute(new Runnable() { // from class: com.android.internal.telecom.ClientTransactionalServiceWrapper$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            callStateCallback.onEvent(str2, bundle);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            }
        }
    }
}
