package android.telecom;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.RemoteException;
import android.telecom.Call;
import android.telecom.CallDiagnostics;
import android.telephony.CallQuality;
import android.util.ArrayMap;
import com.android.internal.telecom.ICallDiagnosticService;
import com.android.internal.telecom.ICallDiagnosticServiceAdapter;
import java.util.Map;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes4.dex */
public abstract class CallDiagnosticService extends Service {
    public static final String SERVICE_INTERFACE = "android.telecom.CallDiagnosticService";
    private ICallDiagnosticServiceAdapter mAdapter;
    private CallDiagnostics.Listener mDiagnosticCallListener = new CallDiagnostics.Listener() { // from class: android.telecom.CallDiagnosticService.1
        @Override // android.telecom.CallDiagnostics.Listener
        public void onSendDeviceToDeviceMessage(CallDiagnostics callDiagnostics, int i, int i2) {
            CallDiagnosticService.this.handleSendDeviceToDeviceMessage(callDiagnostics, i, i2);
        }

        @Override // android.telecom.CallDiagnostics.Listener
        public void onDisplayDiagnosticMessage(CallDiagnostics callDiagnostics, int i, CharSequence charSequence) {
            CallDiagnosticService.this.handleDisplayDiagnosticMessage(callDiagnostics, i, charSequence);
        }

        @Override // android.telecom.CallDiagnostics.Listener
        public void onClearDiagnosticMessage(CallDiagnostics callDiagnostics, int i) {
            CallDiagnosticService.this.handleClearDiagnosticMessage(callDiagnostics, i);
        }
    };
    private final Map<String, Call.Details> mCallByTelecomCallId = new ArrayMap();
    private final Map<String, CallDiagnostics> mDiagnosticCallByTelecomCallId = new ArrayMap();
    private final Object mLock = new Object();

    /* renamed from: onBluetoothCallQualityReportReceived, reason: merged with bridge method [inline-methods] */
    public abstract void lambda$handleBluetoothCallQualityReport$4(BluetoothCallQualityReport bluetoothCallQualityReport);

    public abstract void onCallAudioStateChanged(CallAudioState callAudioState);

    public abstract CallDiagnostics onInitializeCallDiagnostics(Call.Details details);

    /* renamed from: onRemoveCallDiagnostics, reason: merged with bridge method [inline-methods] */
    public abstract void lambda$handleCallRemoved$2(CallDiagnostics callDiagnostics);

    /* JADX INFO: Access modifiers changed from: private */
    final class CallDiagnosticServiceBinder extends ICallDiagnosticService.Stub {
        private CallDiagnosticServiceBinder() {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void setAdapter(ICallDiagnosticServiceAdapter iCallDiagnosticServiceAdapter) throws RemoteException {
            CallDiagnosticService.this.handleSetAdapter(iCallDiagnosticServiceAdapter);
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void initializeDiagnosticCall(ParcelableCall parcelableCall) throws RemoteException {
            CallDiagnosticService.this.handleCallAdded(parcelableCall);
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void updateCall(ParcelableCall parcelableCall) throws RemoteException {
            CallDiagnosticService.this.handleCallUpdated(parcelableCall);
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void removeDiagnosticCall(String str) throws RemoteException {
            CallDiagnosticService.this.handleCallRemoved(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateCallAudioState$0(CallAudioState callAudioState) {
            CallDiagnosticService.this.onCallAudioStateChanged(callAudioState);
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void updateCallAudioState(final CallAudioState callAudioState) throws RemoteException {
            CallDiagnosticService.this.getExecutor().execute(new Runnable() { // from class: android.telecom.CallDiagnosticService$CallDiagnosticServiceBinder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updateCallAudioState$0(callAudioState);
                }
            });
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void receiveDeviceToDeviceMessage(String str, int i, int i2) {
            CallDiagnosticService.this.handleReceivedD2DMessage(str, i, i2);
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void receiveBluetoothCallQualityReport(BluetoothCallQualityReport bluetoothCallQualityReport) throws RemoteException {
            CallDiagnosticService.this.handleBluetoothCallQualityReport(bluetoothCallQualityReport);
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void notifyCallDisconnected(String str, DisconnectCause disconnectCause) throws RemoteException {
            CallDiagnosticService.this.handleCallDisconnected(str, disconnectCause);
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void callQualityChanged(String str, CallQuality callQuality) throws RemoteException {
            CallDiagnosticService.this.handleCallQualityChanged(str, callQuality);
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.i(this, "onBind!", new Object[0]);
        return new CallDiagnosticServiceBinder();
    }

    public Executor getExecutor() {
        return new HandlerExecutor(Handler.createAsync(getMainLooper()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSetAdapter(ICallDiagnosticServiceAdapter iCallDiagnosticServiceAdapter) {
        this.mAdapter = iCallDiagnosticServiceAdapter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCallAdded(ParcelableCall parcelableCall) {
        final String id = parcelableCall.getId();
        Log.i(this, "handleCallAdded: callId=%s - added", id);
        final Call.Details detailsCreateFromParcelableCall = Call.Details.createFromParcelableCall(parcelableCall);
        synchronized (this.mLock) {
            this.mCallByTelecomCallId.put(id, detailsCreateFromParcelableCall);
        }
        getExecutor().execute(new Runnable() { // from class: android.telecom.CallDiagnosticService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$handleCallAdded$0(detailsCreateFromParcelableCall, id);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleCallAdded$0(Call.Details details, String str) {
        CallDiagnostics callDiagnosticsOnInitializeCallDiagnostics = onInitializeCallDiagnostics(details);
        if (callDiagnosticsOnInitializeCallDiagnostics == null) {
            throw new IllegalArgumentException("A valid DiagnosticCall instance was not provided.");
        }
        synchronized (this.mLock) {
            callDiagnosticsOnInitializeCallDiagnostics.setListener(this.mDiagnosticCallListener);
            callDiagnosticsOnInitializeCallDiagnostics.setCallId(str);
            this.mDiagnosticCallByTelecomCallId.put(str, callDiagnosticsOnInitializeCallDiagnostics);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCallUpdated(ParcelableCall parcelableCall) {
        String id = parcelableCall.getId();
        Log.i(this, "handleCallUpdated: callId=%s - updated", id);
        final Call.Details detailsCreateFromParcelableCall = Call.Details.createFromParcelableCall(parcelableCall);
        synchronized (this.mLock) {
            final CallDiagnostics callDiagnostics = this.mDiagnosticCallByTelecomCallId.get(id);
            if (callDiagnostics == null) {
                return;
            }
            this.mCallByTelecomCallId.put(id, detailsCreateFromParcelableCall);
            getExecutor().execute(new Runnable() { // from class: android.telecom.CallDiagnosticService$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    callDiagnostics.handleCallUpdated(detailsCreateFromParcelableCall);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCallRemoved(String str) {
        final CallDiagnostics callDiagnosticsRemove;
        Log.i(this, "handleCallRemoved: callId=%s - removed", str);
        synchronized (this.mLock) {
            if (this.mCallByTelecomCallId.containsKey(str)) {
                this.mCallByTelecomCallId.remove(str);
            }
            callDiagnosticsRemove = this.mDiagnosticCallByTelecomCallId.containsKey(str) ? this.mDiagnosticCallByTelecomCallId.remove(str) : null;
        }
        if (callDiagnosticsRemove != null) {
            getExecutor().execute(new Runnable() { // from class: android.telecom.CallDiagnosticService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$handleCallRemoved$2(callDiagnosticsRemove);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleReceivedD2DMessage(String str, final int i, final int i2) {
        final CallDiagnostics callDiagnostics;
        Log.i(this, "handleReceivedD2DMessage: callId=%s, msg=%d/%d", str, Integer.valueOf(i), Integer.valueOf(i2));
        synchronized (this.mLock) {
            callDiagnostics = this.mDiagnosticCallByTelecomCallId.get(str);
        }
        if (callDiagnostics != null) {
            getExecutor().execute(new Runnable() { // from class: android.telecom.CallDiagnosticService$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    callDiagnostics.onReceiveDeviceToDeviceMessage(i, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCallDisconnected(String str, DisconnectCause disconnectCause) {
        CallDiagnostics callDiagnostics;
        CharSequence charSequenceOnCallDisconnected;
        Log.i(this, "handleCallDisconnected: call=%s; cause=%s", str, disconnectCause);
        synchronized (this.mLock) {
            callDiagnostics = this.mDiagnosticCallByTelecomCallId.get(str);
        }
        if (disconnectCause.getImsReasonInfo() != null) {
            charSequenceOnCallDisconnected = callDiagnostics.onCallDisconnected(disconnectCause.getImsReasonInfo());
        } else {
            charSequenceOnCallDisconnected = callDiagnostics.onCallDisconnected(disconnectCause.getTelephonyDisconnectCause(), disconnectCause.getTelephonyPreciseDisconnectCause());
        }
        try {
            this.mAdapter.overrideDisconnectMessage(str, charSequenceOnCallDisconnected);
        } catch (RemoteException e) {
            Log.w(this, "handleCallDisconnected: call=%s; cause=%s; %s", str, disconnectCause, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBluetoothCallQualityReport(final BluetoothCallQualityReport bluetoothCallQualityReport) {
        Log.i(this, "handleBluetoothCallQualityReport; report=%s", bluetoothCallQualityReport);
        getExecutor().execute(new Runnable() { // from class: android.telecom.CallDiagnosticService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$handleBluetoothCallQualityReport$4(bluetoothCallQualityReport);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCallQualityChanged(String str, CallQuality callQuality) {
        CallDiagnostics callDiagnostics;
        Log.i(this, "handleCallQualityChanged; call=%s, cq=%s", str, callQuality);
        synchronized (this.mLock) {
            callDiagnostics = this.mDiagnosticCallByTelecomCallId.get(str);
        }
        if (callDiagnostics != null) {
            callDiagnostics.onCallQualityReceived(callQuality);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSendDeviceToDeviceMessage(CallDiagnostics callDiagnostics, int i, int i2) {
        String callId = callDiagnostics.getCallId();
        try {
            this.mAdapter.sendDeviceToDeviceMessage(callId, i, i2);
            Log.i(this, "handleSendDeviceToDeviceMessage: call=%s; msg=%d/%d", callId, Integer.valueOf(i), Integer.valueOf(i2));
        } catch (RemoteException e) {
            Log.w(this, "handleSendDeviceToDeviceMessage: call=%s; msg=%d/%d failed %s", callId, Integer.valueOf(i), Integer.valueOf(i2), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDisplayDiagnosticMessage(CallDiagnostics callDiagnostics, int i, CharSequence charSequence) {
        String callId = callDiagnostics.getCallId();
        try {
            this.mAdapter.displayDiagnosticMessage(callId, i, charSequence);
            Log.i(this, "handleDisplayDiagnosticMessage: call=%s; msg=%d/%s", callId, Integer.valueOf(i), charSequence);
        } catch (RemoteException e) {
            Log.w(this, "handleDisplayDiagnosticMessage: call=%s; msg=%d/%s failed %s", callId, Integer.valueOf(i), charSequence, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleClearDiagnosticMessage(CallDiagnostics callDiagnostics, int i) {
        String callId = callDiagnostics.getCallId();
        try {
            this.mAdapter.clearDiagnosticMessage(callId, i);
            Log.i(this, "handleClearDiagnosticMessage: call=%s; msg=%d", callId, Integer.valueOf(i));
        } catch (RemoteException e) {
            Log.w(this, "handleClearDiagnosticMessage: call=%s; msg=%d failed %s", callId, Integer.valueOf(i), e);
        }
    }
}
