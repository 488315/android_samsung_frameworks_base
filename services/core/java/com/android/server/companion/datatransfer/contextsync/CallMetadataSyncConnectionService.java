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
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.companion.CompanionDeviceConfig;
import com.android.server.companion.CompanionDeviceManagerService;
import com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService;
import com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class CallMetadataSyncConnectionService extends ConnectionService {
    AudioManager mAudioManager;
    public CompanionDeviceManagerService.LocalService mCdmsi;
    TelecomManager mTelecomManager;
    final Map mActiveConnections = new HashMap();
    final CrossDeviceSyncControllerCallback mCrossDeviceSyncControllerCallback = new CrossDeviceSyncControllerCallback() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.1
        @Override // com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback
        public final void cleanUpCallIds(final Set set) {
            CallMetadataSyncConnectionService.this.mActiveConnections.values().removeIf(new Predicate() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    CallMetadataSyncConnectionService.CallMetadataSyncConnection callMetadataSyncConnection = (CallMetadataSyncConnectionService.CallMetadataSyncConnection) obj;
                    if (!set.contains(callMetadataSyncConnection.mCall.mId)) {
                        return false;
                    }
                    callMetadataSyncConnection.setDisconnected(new DisconnectCause(3));
                    return true;
                }
            });
        }

        @Override // com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback
        public final void processContextSyncMessage(final int i, final CallMetadataSyncData callMetadataSyncData) {
            CallMetadataSyncConnectionIdentifier callMetadataSyncConnectionIdentifier;
            Iterator it = ((HashMap) callMetadataSyncData.mCalls).values().iterator();
            while (true) {
                boolean hasNext = it.hasNext();
                CallMetadataSyncConnectionService callMetadataSyncConnectionService = CallMetadataSyncConnectionService.this;
                if (!hasNext) {
                    callMetadataSyncConnectionService.mActiveConnections.values().removeIf(new Predicate() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService$1$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            int i2 = i;
                            CallMetadataSyncData callMetadataSyncData2 = callMetadataSyncData;
                            CallMetadataSyncConnectionService.CallMetadataSyncConnection callMetadataSyncConnection = (CallMetadataSyncConnectionService.CallMetadataSyncConnection) obj;
                            if (callMetadataSyncConnection.mIsIdFinalized && i2 == callMetadataSyncConnection.mAssociationId) {
                                if (!((HashMap) callMetadataSyncData2.mCalls).containsKey(callMetadataSyncConnection.mCall.mId)) {
                                    callMetadataSyncConnection.setDisconnected(new DisconnectCause(3));
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
                    return;
                }
                CallMetadataSyncData.Call call = (CallMetadataSyncData.Call) it.next();
                CallMetadataSyncConnection callMetadataSyncConnection = (CallMetadataSyncConnection) callMetadataSyncConnectionService.mActiveConnections.get(new CallMetadataSyncConnectionIdentifier(i, call.mId));
                if (callMetadataSyncConnection != null) {
                    CallMetadataSyncConnection.m345$$Nest$mupdate(callMetadataSyncConnection, call);
                } else {
                    Iterator it2 = callMetadataSyncConnectionService.mActiveConnections.entrySet().iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            callMetadataSyncConnectionIdentifier = null;
                            break;
                        }
                        Map.Entry entry = (Map.Entry) it2.next();
                        if (((CallMetadataSyncConnection) entry.getValue()).mAssociationId == i && !((CallMetadataSyncConnection) entry.getValue()).mIsIdFinalized && call.mId.endsWith(((CallMetadataSyncConnection) entry.getValue()).mCall.mId)) {
                            callMetadataSyncConnectionIdentifier = (CallMetadataSyncConnectionIdentifier) entry.getKey();
                            break;
                        }
                    }
                    if (callMetadataSyncConnectionIdentifier != null) {
                        CallMetadataSyncConnection callMetadataSyncConnection2 = (CallMetadataSyncConnection) callMetadataSyncConnectionService.mActiveConnections.remove(callMetadataSyncConnectionIdentifier);
                        CallMetadataSyncConnection.m345$$Nest$mupdate(callMetadataSyncConnection2, call);
                        callMetadataSyncConnectionService.mActiveConnections.put(new CallMetadataSyncConnectionIdentifier(i, call.mId), callMetadataSyncConnection2);
                    }
                }
            }
        }
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService$2, reason: invalid class name */
    public final class AnonymousClass2 extends CallMetadataSyncConnectionCallback {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ CallMetadataSyncConnectionService this$0;

        public /* synthetic */ AnonymousClass2(CallMetadataSyncConnectionService callMetadataSyncConnectionService, int i) {
            this.$r8$classId = i;
            this.this$0 = callMetadataSyncConnectionService;
        }

        @Override // com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.CallMetadataSyncConnectionCallback
        public final void sendCallAction(int i, int i2, String str) {
            switch (this.$r8$classId) {
                case 0:
                    CompanionDeviceManagerService.LocalService localService = this.this$0.mCdmsi;
                    byte[] createCallControlMessage = CrossDeviceSyncController.createCallControlMessage(i2, str);
                    localService.getClass();
                    if (CompanionDeviceConfig.isEnabled()) {
                        CompanionDeviceManagerService.this.mCrossDeviceSyncController.syncMessageToDevice(i, createCallControlMessage);
                        break;
                    }
                    break;
                default:
                    CompanionDeviceManagerService.LocalService localService2 = this.this$0.mCdmsi;
                    byte[] createCallControlMessage2 = CrossDeviceSyncController.createCallControlMessage(i2, str);
                    localService2.getClass();
                    if (CompanionDeviceConfig.isEnabled()) {
                        CompanionDeviceManagerService.this.mCrossDeviceSyncController.syncMessageToDevice(i, createCallControlMessage2);
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class CallMetadataSyncConnection extends Connection {
        public final int mAssociationId;
        public final AudioManager mAudioManager;
        public final CallMetadataSyncData.Call mCall;
        public final CallMetadataSyncConnectionCallback mCallback;
        public boolean mIsIdFinalized;
        public final TelecomManager mTelecomManager;

        /* renamed from: -$$Nest$mupdate, reason: not valid java name */
        public static void m345$$Nest$mupdate(CallMetadataSyncConnection callMetadataSyncConnection, CallMetadataSyncData.Call call) {
            int i;
            if (!callMetadataSyncConnection.mIsIdFinalized) {
                callMetadataSyncConnection.mCall.mId = call.mId;
                callMetadataSyncConnection.mIsIdFinalized = true;
            }
            int i2 = call.mStatus;
            if (i2 == 4 && callMetadataSyncConnection.mCall.mStatus != 4) {
                callMetadataSyncConnection.mTelecomManager.silenceRinger();
            }
            callMetadataSyncConnection.mCall.mStatus = i2;
            switch (i2) {
                case 1:
                case 4:
                    i = 2;
                    break;
                case 2:
                    i = 4;
                    break;
                case 3:
                    i = 3;
                    break;
                case 5:
                    i = 12;
                    break;
                case 6:
                    i = 13;
                    break;
                case 7:
                    i = 7;
                    break;
                case 8:
                    i = 1;
                    break;
                default:
                    i = 0;
                    break;
            }
            if (i != callMetadataSyncConnection.getState()) {
                if (i == 2) {
                    callMetadataSyncConnection.setRinging();
                } else if (i == 4) {
                    callMetadataSyncConnection.setActive();
                } else if (i == 3) {
                    callMetadataSyncConnection.setOnHold();
                } else if (i == 7) {
                    callMetadataSyncConnection.setDisconnected(new DisconnectCause(3));
                } else if (i == 1) {
                    callMetadataSyncConnection.setDialing();
                } else {
                    Slog.e("CallMetadataSyncConnectionService", "Could not update call to unknown state");
                }
            }
            int connectionCapabilities = callMetadataSyncConnection.getConnectionCapabilities();
            CallMetadataSyncData.Call call2 = callMetadataSyncConnection.mCall;
            Set set = call.mControls;
            ((HashSet) call2.mControls).clear();
            call2.mControls.addAll(set);
            int i3 = (callMetadataSyncConnection.mCall.hasControl(7) || callMetadataSyncConnection.mCall.hasControl(8)) ? connectionCapabilities | 1 : connectionCapabilities & (-2);
            int i4 = (callMetadataSyncConnection.mCall.hasControl(4) || callMetadataSyncConnection.mCall.hasControl(5)) ? i3 | 64 : i3 & (-65);
            callMetadataSyncConnection.mAudioManager.setMicrophoneMute(callMetadataSyncConnection.mCall.hasControl(5));
            if (i4 != callMetadataSyncConnection.getConnectionCapabilities()) {
                callMetadataSyncConnection.setConnectionCapabilities(i4);
            }
        }

        public CallMetadataSyncConnection(TelecomManager telecomManager, AudioManager audioManager, int i, CallMetadataSyncData.Call call, CallMetadataSyncConnectionCallback callMetadataSyncConnectionCallback) {
            this.mTelecomManager = telecomManager;
            this.mAudioManager = audioManager;
            this.mAssociationId = i;
            this.mCall = call;
            this.mCallback = callMetadataSyncConnectionCallback;
        }

        @Override // android.telecom.Connection
        public final void onAnswer(int i) {
            sendCallAction(1);
        }

        @Override // android.telecom.Connection
        public final void onDisconnect() {
            sendCallAction(6);
        }

        @Override // android.telecom.Connection
        public final void onHold() {
            sendCallAction(7);
        }

        @Override // android.telecom.Connection
        public final void onMuteStateChanged(boolean z) {
            sendCallAction(z ? 4 : 5);
        }

        @Override // android.telecom.Connection
        public final void onReject() {
            sendCallAction(2);
        }

        @Override // android.telecom.Connection
        public final void onReject(int i) {
            sendCallAction(2);
        }

        @Override // android.telecom.Connection
        public final void onReject(String str) {
            sendCallAction(2);
        }

        @Override // android.telecom.Connection
        public final void onSilence() {
            sendCallAction(3);
        }

        @Override // android.telecom.Connection
        public final void onUnhold() {
            sendCallAction(8);
        }

        public final void sendCallAction(int i) {
            this.mCallback.sendCallAction(this.mAssociationId, i, this.mCall.mId);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    abstract class CallMetadataSyncConnectionCallback {
        public abstract void sendCallAction(int i, int i2, String str);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class CallMetadataSyncConnectionIdentifier {
        public final int mAssociationId;
        public final String mCallId;

        public CallMetadataSyncConnectionIdentifier(int i, String str) {
            this.mAssociationId = i;
            this.mCallId = str;
        }

        public final boolean equals(Object obj) {
            String str;
            if (!(obj instanceof CallMetadataSyncConnectionIdentifier)) {
                return false;
            }
            CallMetadataSyncConnectionIdentifier callMetadataSyncConnectionIdentifier = (CallMetadataSyncConnectionIdentifier) obj;
            return callMetadataSyncConnectionIdentifier.mAssociationId == this.mAssociationId && (str = this.mCallId) != null && str.equals(callMetadataSyncConnectionIdentifier.mCallId);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.mAssociationId), this.mCallId);
        }
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        this.mAudioManager = (AudioManager) getSystemService(AudioManager.class);
        this.mTelecomManager = (TelecomManager) getSystemService(TelecomManager.class);
        CompanionDeviceManagerService.LocalService localService = (CompanionDeviceManagerService.LocalService) LocalServices.getService(CompanionDeviceManagerService.LocalService.class);
        this.mCdmsi = localService;
        localService.registerCallMetadataSyncCallback(this.mCrossDeviceSyncControllerCallback, 1);
    }

    public final void onCreateConnectionComplete(Connection connection) {
        char c;
        if (connection instanceof CallMetadataSyncConnection) {
            CallMetadataSyncConnection callMetadataSyncConnection = (CallMetadataSyncConnection) connection;
            int i = callMetadataSyncConnection.mCall.mStatus;
            if (i == 4) {
                callMetadataSyncConnection.mTelecomManager.silenceRinger();
            }
            switch (i) {
                case 1:
                case 4:
                    c = 2;
                    break;
                case 2:
                    c = 4;
                    break;
                case 3:
                    c = 3;
                    break;
                case 5:
                    c = '\f';
                    break;
                case 6:
                    c = '\r';
                    break;
                case 7:
                    c = 7;
                    break;
                case 8:
                    c = 1;
                    break;
                default:
                    c = 0;
                    break;
            }
            if (c == 2) {
                callMetadataSyncConnection.setRinging();
            } else if (c == 4) {
                callMetadataSyncConnection.setActive();
            } else if (c == 3) {
                callMetadataSyncConnection.setOnHold();
            } else if (c == 7) {
                callMetadataSyncConnection.setDisconnected(new DisconnectCause(3));
            } else if (c == 1) {
                callMetadataSyncConnection.setDialing();
            } else {
                callMetadataSyncConnection.setInitialized();
            }
            String str = callMetadataSyncConnection.mCall.mCallerId;
            if (str != null) {
                callMetadataSyncConnection.setCallerDisplayName(str, 1);
                callMetadataSyncConnection.setAddress(Uri.fromParts("custom", callMetadataSyncConnection.mCall.mCallerId, null), 1);
            }
            Bundle bundle = new Bundle();
            bundle.putString("com.android.companion.datatransfer.contextsync.extra.CALL_ID", callMetadataSyncConnection.mCall.mId);
            callMetadataSyncConnection.putExtras(bundle);
            int connectionCapabilities = callMetadataSyncConnection.getConnectionCapabilities();
            int i2 = callMetadataSyncConnection.mCall.hasControl(7) ? connectionCapabilities | 1 : connectionCapabilities & (-2);
            int i3 = callMetadataSyncConnection.mCall.hasControl(4) ? i2 | 64 : i2 & (-65);
            callMetadataSyncConnection.mAudioManager.setMicrophoneMute(callMetadataSyncConnection.mCall.hasControl(5));
            if (i3 != callMetadataSyncConnection.getConnectionCapabilities()) {
                callMetadataSyncConnection.setConnectionCapabilities(i3);
            }
            this.mActiveConnections.put(new CallMetadataSyncConnectionIdentifier(callMetadataSyncConnection.mAssociationId, callMetadataSyncConnection.mCall.mId), callMetadataSyncConnection);
        }
    }

    @Override // android.telecom.ConnectionService
    public final Connection onCreateIncomingConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        int i = connectionRequest.getExtras().getInt("com.android.server.companion.datatransfer.contextsync.extra.ASSOCIATION_ID");
        Bundle bundle = connectionRequest.getExtras().getBundle("com.android.server.companion.datatransfer.contextsync.extra.CALL");
        CallMetadataSyncData.Call call = new CallMetadataSyncData.Call();
        if (bundle != null) {
            call.mId = bundle.getString("com.android.companion.datatransfer.contextsync.extra.CALL_ID");
            call.mCallerId = bundle.getString("com.android.server.companion.datatransfer.contextsync.extra.CALLER_ID");
            call.mAppIcon = bundle.getByteArray("com.android.server.companion.datatransfer.contextsync.extra.APP_ICON");
            call.mFacilitator = new CallMetadataSyncData.CallFacilitator(bundle.getString("com.android.server.companion.datatransfer.contextsync.extra.FACILITATOR_NAME"), bundle.getString("com.android.server.companion.datatransfer.contextsync.extra.FACILITATOR_ID"), bundle.getString("com.android.server.companion.datatransfer.contextsync.extra.FACILITATOR_EXT_ID"));
            call.mStatus = bundle.getInt("com.android.server.companion.datatransfer.contextsync.extra.STATUS");
            call.mDirection = bundle.getInt("com.android.server.companion.datatransfer.contextsync.extra.DIRECTION");
            HashSet hashSet = new HashSet(bundle.getIntegerArrayList("com.android.server.companion.datatransfer.contextsync.extra.CONTROLS"));
            ((HashSet) call.mControls).clear();
            call.mControls.addAll(hashSet);
        }
        call.mDirection = 1;
        connectionRequest.getExtras().remove("com.android.server.companion.datatransfer.contextsync.extra.CALL");
        connectionRequest.getExtras().remove("com.android.server.companion.datatransfer.contextsync.extra.CALL_FACILITATOR_ID");
        connectionRequest.getExtras().remove("com.android.server.companion.datatransfer.contextsync.extra.ASSOCIATION_ID");
        CallMetadataSyncConnection callMetadataSyncConnection = new CallMetadataSyncConnection(this.mTelecomManager, this.mAudioManager, i, call, new AnonymousClass2(this, 0));
        callMetadataSyncConnection.setConnectionProperties(16);
        callMetadataSyncConnection.setInitializing();
        return callMetadataSyncConnection;
    }

    @Override // android.telecom.ConnectionService
    public final void onCreateIncomingConnectionFailed(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        BootReceiver$$ExternalSyntheticOutline0.m("onCreateOutgoingConnectionFailed for: ", phoneAccountHandle != null ? phoneAccountHandle.getId() : "unknown PhoneAccount", "CallMetadataSyncConnectionService");
    }

    @Override // android.telecom.ConnectionService
    public final Connection onCreateOutgoingConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        if (phoneAccountHandle == null) {
            phoneAccountHandle = connectionRequest.getAccountHandle();
        }
        PhoneAccount phoneAccount = this.mTelecomManager.getPhoneAccount(phoneAccountHandle);
        CallMetadataSyncData.Call call = new CallMetadataSyncData.Call();
        call.mId = connectionRequest.getExtras().getString("com.android.companion.datatransfer.contextsync.extra.CALL_ID");
        call.mStatus = 0;
        call.mFacilitator = new CallMetadataSyncData.CallFacilitator(phoneAccount != null ? phoneAccount.getLabel().toString() : phoneAccountHandle.getComponentName().getShortClassName(), phoneAccount != null ? phoneAccount.getExtras().getString("com.android.server.companion.datatransfer.contextsync.extra.CALL_FACILITATOR_ID") : phoneAccountHandle.getComponentName().getPackageName(), phoneAccountHandle.getComponentName().flattenToString());
        call.mDirection = 2;
        call.mCallerId = connectionRequest.getAddress().getSchemeSpecificPart();
        int i = phoneAccount.getExtras().getInt("com.android.server.companion.datatransfer.contextsync.extra.ASSOCIATION_ID");
        connectionRequest.getExtras().remove("com.android.server.companion.datatransfer.contextsync.extra.CALL");
        connectionRequest.getExtras().remove("com.android.server.companion.datatransfer.contextsync.extra.CALL_FACILITATOR_ID");
        connectionRequest.getExtras().remove("com.android.server.companion.datatransfer.contextsync.extra.ASSOCIATION_ID");
        CallMetadataSyncConnection callMetadataSyncConnection = new CallMetadataSyncConnection(this.mTelecomManager, this.mAudioManager, i, call, new AnonymousClass2(this, 1));
        callMetadataSyncConnection.setCallerDisplayName(call.mCallerId, 1);
        CompanionDeviceManagerService.LocalService localService = this.mCdmsi;
        String str = call.mId;
        localService.getClass();
        if (CompanionDeviceConfig.isEnabled()) {
            CompanionDeviceManagerService.this.mCrossDeviceSyncController.mCallManager.mSelfOwnedCalls.add(str);
        }
        CompanionDeviceManagerService.LocalService localService2 = this.mCdmsi;
        byte[] createCallCreateMessage = CrossDeviceSyncController.createCallCreateMessage(call.mId, connectionRequest.getAddress().toString(), call.mFacilitator.mIdentifier);
        localService2.getClass();
        if (CompanionDeviceConfig.isEnabled()) {
            CompanionDeviceManagerService.this.mCrossDeviceSyncController.syncMessageToDevice(i, createCallCreateMessage);
        }
        callMetadataSyncConnection.setInitializing();
        return callMetadataSyncConnection;
    }

    @Override // android.telecom.ConnectionService
    public final void onCreateOutgoingConnectionFailed(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        BootReceiver$$ExternalSyntheticOutline0.m("onCreateOutgoingConnectionFailed for: ", phoneAccountHandle != null ? phoneAccountHandle.getId() : "unknown PhoneAccount", "CallMetadataSyncConnectionService");
    }
}
