package com.android.server.companion.datatransfer.contextsync;

import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Connection;
import android.telecom.ConnectionRequest;
import android.telecom.ConnectionService;
import android.telecom.DisconnectCause;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.util.Slog;
import com.android.server.LocalServices;
import com.android.server.companion.CompanionDeviceManagerServiceInternal;
import com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService;
import com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class CallMetadataSyncConnectionService extends ConnectionService {
    AudioManager mAudioManager;
    public CompanionDeviceManagerServiceInternal mCdmsi;
    TelecomManager mTelecomManager;
    final Map mActiveConnections = new HashMap();
    final CrossDeviceSyncControllerCallback mCrossDeviceSyncControllerCallback = new C10131();

    abstract class CallMetadataSyncConnectionCallback {
        public abstract void sendCallAction(int i, String str, int i2);
    }

    /* renamed from: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService$1 */
    public class C10131 extends CrossDeviceSyncControllerCallback {
        public C10131() {
        }

        @Override // com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback
        public void processContextSyncMessage(final int i, final CallMetadataSyncData callMetadataSyncData) {
            CallMetadataSyncConnectionIdentifier callMetadataSyncConnectionIdentifier;
            for (CallMetadataSyncData.Call call : callMetadataSyncData.getCalls()) {
                CallMetadataSyncConnection callMetadataSyncConnection = (CallMetadataSyncConnection) CallMetadataSyncConnectionService.this.mActiveConnections.get(new CallMetadataSyncConnectionIdentifier(i, call.getId()));
                if (callMetadataSyncConnection != null) {
                    callMetadataSyncConnection.update(call);
                } else {
                    Iterator it = CallMetadataSyncConnectionService.this.mActiveConnections.entrySet().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            callMetadataSyncConnectionIdentifier = null;
                            break;
                        }
                        Map.Entry entry = (Map.Entry) it.next();
                        if (((CallMetadataSyncConnection) entry.getValue()).getAssociationId() == i && !((CallMetadataSyncConnection) entry.getValue()).isIdFinalized() && call.getId().endsWith(((CallMetadataSyncConnection) entry.getValue()).getCallId())) {
                            callMetadataSyncConnectionIdentifier = (CallMetadataSyncConnectionIdentifier) entry.getKey();
                            break;
                        }
                    }
                    if (callMetadataSyncConnectionIdentifier != null) {
                        CallMetadataSyncConnection callMetadataSyncConnection2 = (CallMetadataSyncConnection) CallMetadataSyncConnectionService.this.mActiveConnections.remove(callMetadataSyncConnectionIdentifier);
                        callMetadataSyncConnection2.update(call);
                        CallMetadataSyncConnectionService.this.mActiveConnections.put(new CallMetadataSyncConnectionIdentifier(i, call.getId()), callMetadataSyncConnection2);
                    }
                }
            }
            CallMetadataSyncConnectionService.this.mActiveConnections.values().removeIf(new Predicate() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$processContextSyncMessage$0;
                    lambda$processContextSyncMessage$0 = CallMetadataSyncConnectionService.C10131.lambda$processContextSyncMessage$0(i, callMetadataSyncData, (CallMetadataSyncConnectionService.CallMetadataSyncConnection) obj);
                    return lambda$processContextSyncMessage$0;
                }
            });
        }

        public static /* synthetic */ boolean lambda$processContextSyncMessage$0(int i, CallMetadataSyncData callMetadataSyncData, CallMetadataSyncConnection callMetadataSyncConnection) {
            if (!callMetadataSyncConnection.isIdFinalized() || i != callMetadataSyncConnection.getAssociationId() || callMetadataSyncData.hasCall(callMetadataSyncConnection.getCallId())) {
                return false;
            }
            callMetadataSyncConnection.setDisconnected(new DisconnectCause(3));
            return true;
        }

        @Override // com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback
        public void cleanUpCallIds(final Set set) {
            CallMetadataSyncConnectionService.this.mActiveConnections.values().removeIf(new Predicate() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$cleanUpCallIds$1;
                    lambda$cleanUpCallIds$1 = CallMetadataSyncConnectionService.C10131.lambda$cleanUpCallIds$1(set, (CallMetadataSyncConnectionService.CallMetadataSyncConnection) obj);
                    return lambda$cleanUpCallIds$1;
                }
            });
        }

        public static /* synthetic */ boolean lambda$cleanUpCallIds$1(Set set, CallMetadataSyncConnection callMetadataSyncConnection) {
            if (!set.contains(callMetadataSyncConnection.getCallId())) {
                return false;
            }
            callMetadataSyncConnection.setDisconnected(new DisconnectCause(3));
            return true;
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mAudioManager = (AudioManager) getSystemService(AudioManager.class);
        this.mTelecomManager = (TelecomManager) getSystemService(TelecomManager.class);
        CompanionDeviceManagerServiceInternal companionDeviceManagerServiceInternal = (CompanionDeviceManagerServiceInternal) LocalServices.getService(CompanionDeviceManagerServiceInternal.class);
        this.mCdmsi = companionDeviceManagerServiceInternal;
        companionDeviceManagerServiceInternal.registerCallMetadataSyncCallback(this.mCrossDeviceSyncControllerCallback, 1);
    }

    @Override // android.telecom.ConnectionService
    public Connection onCreateIncomingConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        int i = connectionRequest.getExtras().getInt("com.android.server.companion.datatransfer.contextsync.extra.ASSOCIATION_ID");
        CallMetadataSyncData.Call fromBundle = CallMetadataSyncData.Call.fromBundle(connectionRequest.getExtras().getBundle("com.android.server.companion.datatransfer.contextsync.extra.CALL"));
        fromBundle.setDirection(1);
        connectionRequest.getExtras().remove("com.android.server.companion.datatransfer.contextsync.extra.CALL");
        connectionRequest.getExtras().remove("com.android.server.companion.datatransfer.contextsync.extra.CALL_FACILITATOR_ID");
        connectionRequest.getExtras().remove("com.android.server.companion.datatransfer.contextsync.extra.ASSOCIATION_ID");
        CallMetadataSyncConnection callMetadataSyncConnection = new CallMetadataSyncConnection(this.mTelecomManager, this.mAudioManager, i, fromBundle, new CallMetadataSyncConnectionCallback() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.2
            @Override // com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnectionCallback
            public void sendCallAction(int i2, String str, int i3) {
                CallMetadataSyncConnectionService.this.mCdmsi.sendCrossDeviceSyncMessage(i2, CrossDeviceSyncController.createCallControlMessage(str, i3));
            }
        });
        callMetadataSyncConnection.setConnectionProperties(16);
        callMetadataSyncConnection.setInitializing();
        return callMetadataSyncConnection;
    }

    @Override // android.telecom.ConnectionService
    public void onCreateIncomingConnectionFailed(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        Slog.e("CallMetadataSyncConnectionService", "onCreateOutgoingConnectionFailed for: " + (phoneAccountHandle != null ? phoneAccountHandle.getId() : "unknown PhoneAccount"));
    }

    @Override // android.telecom.ConnectionService
    public Connection onCreateOutgoingConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        String shortClassName;
        String packageName;
        if (phoneAccountHandle == null) {
            phoneAccountHandle = connectionRequest.getAccountHandle();
        }
        PhoneAccount phoneAccount = this.mTelecomManager.getPhoneAccount(phoneAccountHandle);
        CallMetadataSyncData.Call call = new CallMetadataSyncData.Call();
        call.setId(connectionRequest.getExtras().getString("com.android.companion.datatransfer.contextsync.extra.CALL_ID"));
        call.setStatus(0);
        if (phoneAccount != null) {
            shortClassName = phoneAccount.getLabel().toString();
        } else {
            shortClassName = phoneAccountHandle.getComponentName().getShortClassName();
        }
        if (phoneAccount != null) {
            packageName = phoneAccount.getExtras().getString("com.android.server.companion.datatransfer.contextsync.extra.CALL_FACILITATOR_ID");
        } else {
            packageName = phoneAccountHandle.getComponentName().getPackageName();
        }
        call.setFacilitator(new CallMetadataSyncData.CallFacilitator(shortClassName, packageName, phoneAccountHandle.getComponentName().flattenToString()));
        call.setDirection(2);
        call.setCallerId(connectionRequest.getAddress().getSchemeSpecificPart());
        int i = phoneAccount.getExtras().getInt("com.android.server.companion.datatransfer.contextsync.extra.ASSOCIATION_ID");
        connectionRequest.getExtras().remove("com.android.server.companion.datatransfer.contextsync.extra.CALL");
        connectionRequest.getExtras().remove("com.android.server.companion.datatransfer.contextsync.extra.CALL_FACILITATOR_ID");
        connectionRequest.getExtras().remove("com.android.server.companion.datatransfer.contextsync.extra.ASSOCIATION_ID");
        CallMetadataSyncConnection callMetadataSyncConnection = new CallMetadataSyncConnection(this.mTelecomManager, this.mAudioManager, i, call, new CallMetadataSyncConnectionCallback() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.3
            @Override // com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnectionCallback
            public void sendCallAction(int i2, String str, int i3) {
                CallMetadataSyncConnectionService.this.mCdmsi.sendCrossDeviceSyncMessage(i2, CrossDeviceSyncController.createCallControlMessage(str, i3));
            }
        });
        callMetadataSyncConnection.setCallerDisplayName(call.getCallerId(), 1);
        this.mCdmsi.addSelfOwnedCallId(call.getId());
        this.mCdmsi.sendCrossDeviceSyncMessage(i, CrossDeviceSyncController.createCallCreateMessage(call.getId(), connectionRequest.getAddress().toString(), call.getFacilitator().getIdentifier()));
        callMetadataSyncConnection.setInitializing();
        return callMetadataSyncConnection;
    }

    @Override // android.telecom.ConnectionService
    public void onCreateOutgoingConnectionFailed(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        Slog.e("CallMetadataSyncConnectionService", "onCreateOutgoingConnectionFailed for: " + (phoneAccountHandle != null ? phoneAccountHandle.getId() : "unknown PhoneAccount"));
    }

    public void onCreateConnectionComplete(Connection connection) {
        if (connection instanceof CallMetadataSyncConnection) {
            CallMetadataSyncConnection callMetadataSyncConnection = (CallMetadataSyncConnection) connection;
            callMetadataSyncConnection.initialize();
            this.mActiveConnections.put(new CallMetadataSyncConnectionIdentifier(callMetadataSyncConnection.getAssociationId(), callMetadataSyncConnection.getCallId()), callMetadataSyncConnection);
        }
    }

    final class CallMetadataSyncConnectionIdentifier {
        public final int mAssociationId;
        public final String mCallId;

        public CallMetadataSyncConnectionIdentifier(int i, String str) {
            this.mAssociationId = i;
            this.mCallId = str;
        }

        public int getAssociationId() {
            return this.mAssociationId;
        }

        public String getCallId() {
            return this.mCallId;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mAssociationId), this.mCallId);
        }

        public boolean equals(Object obj) {
            String str;
            if (!(obj instanceof CallMetadataSyncConnectionIdentifier)) {
                return false;
            }
            CallMetadataSyncConnectionIdentifier callMetadataSyncConnectionIdentifier = (CallMetadataSyncConnectionIdentifier) obj;
            return callMetadataSyncConnectionIdentifier.getAssociationId() == this.mAssociationId && (str = this.mCallId) != null && str.equals(callMetadataSyncConnectionIdentifier.getCallId());
        }
    }

    class CallMetadataSyncConnection extends Connection {
        public final int mAssociationId;
        public final AudioManager mAudioManager;
        public final CallMetadataSyncData.Call mCall;
        public final CallMetadataSyncConnectionCallback mCallback;
        public boolean mIsIdFinalized;
        public final TelecomManager mTelecomManager;

        public CallMetadataSyncConnection(TelecomManager telecomManager, AudioManager audioManager, int i, CallMetadataSyncData.Call call, CallMetadataSyncConnectionCallback callMetadataSyncConnectionCallback) {
            this.mTelecomManager = telecomManager;
            this.mAudioManager = audioManager;
            this.mAssociationId = i;
            this.mCall = call;
            this.mCallback = callMetadataSyncConnectionCallback;
        }

        public String getCallId() {
            return this.mCall.getId();
        }

        public int getAssociationId() {
            return this.mAssociationId;
        }

        public boolean isIdFinalized() {
            return this.mIsIdFinalized;
        }

        public final void initialize() {
            int status = this.mCall.getStatus();
            if (status == 4) {
                this.mTelecomManager.silenceRinger();
            }
            int convertStatusToState = CrossDeviceCall.convertStatusToState(status);
            if (convertStatusToState == 2) {
                setRinging();
            } else if (convertStatusToState == 4) {
                setActive();
            } else if (convertStatusToState == 3) {
                setOnHold();
            } else if (convertStatusToState == 7) {
                setDisconnected(new DisconnectCause(3));
            } else if (convertStatusToState == 1) {
                setDialing();
            } else {
                setInitialized();
            }
            String callerId = this.mCall.getCallerId();
            if (callerId != null) {
                setCallerDisplayName(callerId, 1);
                setAddress(Uri.fromParts("custom", this.mCall.getCallerId(), null), 1);
            }
            Bundle bundle = new Bundle();
            bundle.putString("com.android.companion.datatransfer.contextsync.extra.CALL_ID", this.mCall.getId());
            putExtras(bundle);
            int connectionCapabilities = getConnectionCapabilities();
            int i = this.mCall.hasControl(7) ? connectionCapabilities | 1 : connectionCapabilities & (-2);
            int i2 = this.mCall.hasControl(4) ? i | 64 : i & (-65);
            this.mAudioManager.setMicrophoneMute(this.mCall.hasControl(5));
            if (i2 != getConnectionCapabilities()) {
                setConnectionCapabilities(i2);
            }
        }

        public final void update(CallMetadataSyncData.Call call) {
            boolean z = true;
            if (!this.mIsIdFinalized) {
                this.mCall.setId(call.getId());
                this.mIsIdFinalized = true;
            }
            int status = call.getStatus();
            if (status == 4 && this.mCall.getStatus() != 4) {
                this.mTelecomManager.silenceRinger();
            }
            this.mCall.setStatus(status);
            int convertStatusToState = CrossDeviceCall.convertStatusToState(status);
            if (convertStatusToState != getState()) {
                if (convertStatusToState == 2) {
                    setRinging();
                } else if (convertStatusToState == 4) {
                    setActive();
                } else if (convertStatusToState == 3) {
                    setOnHold();
                } else if (convertStatusToState == 7) {
                    setDisconnected(new DisconnectCause(3));
                } else if (convertStatusToState == 1) {
                    setDialing();
                } else {
                    Slog.e("CallMetadataSyncConnectionService", "Could not update call to unknown state");
                }
            }
            int connectionCapabilities = getConnectionCapabilities();
            this.mCall.setControls(call.getControls());
            int i = this.mCall.hasControl(7) || this.mCall.hasControl(8) ? connectionCapabilities | 1 : connectionCapabilities & (-2);
            if (!this.mCall.hasControl(4) && !this.mCall.hasControl(5)) {
                z = false;
            }
            int i2 = z ? i | 64 : i & (-65);
            this.mAudioManager.setMicrophoneMute(this.mCall.hasControl(5));
            if (i2 != getConnectionCapabilities()) {
                setConnectionCapabilities(i2);
            }
        }

        @Override // android.telecom.Connection
        public void onAnswer(int i) {
            sendCallAction(1);
        }

        @Override // android.telecom.Connection
        public void onReject() {
            sendCallAction(2);
        }

        @Override // android.telecom.Connection
        public void onReject(int i) {
            onReject();
        }

        @Override // android.telecom.Connection
        public void onReject(String str) {
            onReject();
        }

        @Override // android.telecom.Connection
        public void onSilence() {
            sendCallAction(3);
        }

        @Override // android.telecom.Connection
        public void onHold() {
            sendCallAction(7);
        }

        @Override // android.telecom.Connection
        public void onUnhold() {
            sendCallAction(8);
        }

        @Override // android.telecom.Connection
        public void onMuteStateChanged(boolean z) {
            sendCallAction(z ? 4 : 5);
        }

        @Override // android.telecom.Connection
        public void onDisconnect() {
            sendCallAction(6);
        }

        public final void sendCallAction(int i) {
            this.mCallback.sendCallAction(this.mAssociationId, this.mCall.getId(), i);
        }
    }
}
