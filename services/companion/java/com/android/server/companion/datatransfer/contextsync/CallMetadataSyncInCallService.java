package com.android.server.companion.datatransfer.contextsync;

import android.companion.AssociationInfo;
import android.telecom.Call;
import android.telecom.InCallService;
import android.telecom.TelecomManager;
import android.util.Slog;
import com.android.server.LocalServices;
import com.android.server.companion.CompanionDeviceConfig;
import com.android.server.companion.CompanionDeviceManagerServiceInternal;
import com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class CallMetadataSyncInCallService extends InCallService {
    public CompanionDeviceManagerServiceInternal mCdmsi;
    int mNumberOfActiveSyncAssociations;
    final Map mCurrentCalls = new HashMap();
    public final Call.Callback mTelecomCallback = new Call.Callback() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.1
        @Override // android.telecom.Call.Callback
        public void onDetailsChanged(Call call, Call.Details details) {
            CallMetadataSyncInCallService callMetadataSyncInCallService = CallMetadataSyncInCallService.this;
            if (callMetadataSyncInCallService.mNumberOfActiveSyncAssociations > 0) {
                CrossDeviceCall crossDeviceCall = (CrossDeviceCall) callMetadataSyncInCallService.mCurrentCalls.get(call);
                if (crossDeviceCall != null) {
                    crossDeviceCall.updateCallDetails(details);
                    CallMetadataSyncInCallService callMetadataSyncInCallService2 = CallMetadataSyncInCallService.this;
                    callMetadataSyncInCallService2.sync(callMetadataSyncInCallService2.getUserId());
                    return;
                }
                Slog.w("CallMetadataIcs", "Could not update details for nonexistent call");
            }
        }
    };
    public final CrossDeviceSyncControllerCallback mCrossDeviceSyncControllerCallback = new CrossDeviceSyncControllerCallback() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.2
        @Override // com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback
        public void processContextSyncMessage(int i, CallMetadataSyncData callMetadataSyncData) {
            Iterator it = callMetadataSyncData.getCallControlRequests().iterator();
            while (it.hasNext()) {
                CallMetadataSyncData.CallControlRequest callControlRequest = (CallMetadataSyncData.CallControlRequest) it.next();
                processCallControlAction(callControlRequest.getId(), callControlRequest.getControl());
                it.remove();
            }
        }

        public final void processCallControlAction(String str, int i) {
            CallMetadataSyncInCallService callMetadataSyncInCallService = CallMetadataSyncInCallService.this;
            CrossDeviceCall callForId = callMetadataSyncInCallService.getCallForId(str, callMetadataSyncInCallService.mCurrentCalls.values());
            switch (i) {
                case 1:
                    if (callForId != null) {
                        callForId.doAccept();
                        break;
                    } else {
                        Slog.w("CallMetadataIcs", "Failed to process accept action; no matching call");
                        break;
                    }
                case 2:
                    if (callForId != null) {
                        callForId.doReject();
                        break;
                    } else {
                        Slog.w("CallMetadataIcs", "Failed to process reject action; no matching call");
                        break;
                    }
                case 3:
                    CallMetadataSyncInCallService.this.doSilence();
                    break;
                case 4:
                    CallMetadataSyncInCallService.this.doMute();
                    break;
                case 5:
                    CallMetadataSyncInCallService.this.doUnmute();
                    break;
                case 6:
                    if (callForId != null) {
                        callForId.doEnd();
                        break;
                    } else {
                        Slog.w("CallMetadataIcs", "Failed to process end action; no matching call");
                        break;
                    }
                case 7:
                    if (callForId != null) {
                        callForId.doPutOnHold();
                        break;
                    } else {
                        Slog.w("CallMetadataIcs", "Failed to process hold action; no matching call");
                        break;
                    }
                case 8:
                    if (callForId != null) {
                        callForId.doTakeOffHold();
                        break;
                    } else {
                        Slog.w("CallMetadataIcs", "Failed to process unhold action; no matching call");
                        break;
                    }
            }
        }

        @Override // com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback
        public void requestCrossDeviceSync(AssociationInfo associationInfo) {
            if (associationInfo.getUserId() == CallMetadataSyncInCallService.this.getUserId()) {
                CallMetadataSyncInCallService.this.sync(associationInfo);
            }
        }

        @Override // com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback
        public void updateNumberOfActiveSyncAssociations(int i, boolean z) {
            if (i == CallMetadataSyncInCallService.this.getUserId()) {
                CallMetadataSyncInCallService callMetadataSyncInCallService = CallMetadataSyncInCallService.this;
                int i2 = callMetadataSyncInCallService.mNumberOfActiveSyncAssociations;
                boolean z2 = i2 > 0;
                if (z) {
                    callMetadataSyncInCallService.mNumberOfActiveSyncAssociations = i2 + 1;
                } else {
                    callMetadataSyncInCallService.mNumberOfActiveSyncAssociations = i2 - 1;
                }
                if (!z2 && callMetadataSyncInCallService.mNumberOfActiveSyncAssociations > 0) {
                    callMetadataSyncInCallService.initializeCalls();
                } else {
                    if (!z2 || callMetadataSyncInCallService.mNumberOfActiveSyncAssociations > 0) {
                        return;
                    }
                    callMetadataSyncInCallService.mCurrentCalls.clear();
                }
            }
        }
    };

    public static /* synthetic */ Call lambda$initializeCalls$0(Call call) {
        return call;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        if (CompanionDeviceConfig.isEnabled("enable_context_sync_telecom")) {
            CompanionDeviceManagerServiceInternal companionDeviceManagerServiceInternal = (CompanionDeviceManagerServiceInternal) LocalServices.getService(CompanionDeviceManagerServiceInternal.class);
            this.mCdmsi = companionDeviceManagerServiceInternal;
            companionDeviceManagerServiceInternal.registerCallMetadataSyncCallback(this.mCrossDeviceSyncControllerCallback, 2);
        }
    }

    public final void initializeCalls() {
        if (!CompanionDeviceConfig.isEnabled("enable_context_sync_telecom") || this.mNumberOfActiveSyncAssociations <= 0) {
            return;
        }
        this.mCurrentCalls.putAll((Map) getCalls().stream().collect(Collectors.toMap(new Function() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Call lambda$initializeCalls$0;
                lambda$initializeCalls$0 = CallMetadataSyncInCallService.lambda$initializeCalls$0((Call) obj);
                return lambda$initializeCalls$0;
            }
        }, new Function() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                CrossDeviceCall lambda$initializeCalls$1;
                lambda$initializeCalls$1 = CallMetadataSyncInCallService.this.lambda$initializeCalls$1((Call) obj);
                return lambda$initializeCalls$1;
            }
        })));
        this.mCurrentCalls.keySet().forEach(new Consumer() { // from class: com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CallMetadataSyncInCallService.this.lambda$initializeCalls$2((Call) obj);
            }
        });
        sync(getUserId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CrossDeviceCall lambda$initializeCalls$1(Call call) {
        return new CrossDeviceCall(this, call, getCallAudioState());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initializeCalls$2(Call call) {
        call.registerCallback(this.mTelecomCallback, getMainThreadHandler());
    }

    public CrossDeviceCall getCallForId(String str, Collection collection) {
        if (str == null) {
            return null;
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            CrossDeviceCall crossDeviceCall = (CrossDeviceCall) it.next();
            if (str.equals(crossDeviceCall.getId())) {
                return crossDeviceCall;
            }
        }
        return null;
    }

    @Override // android.telecom.InCallService
    public void onCallAdded(Call call) {
        if (!CompanionDeviceConfig.isEnabled("enable_context_sync_telecom") || this.mNumberOfActiveSyncAssociations <= 0) {
            return;
        }
        this.mCurrentCalls.put(call, new CrossDeviceCall(this, call, getCallAudioState()));
        call.registerCallback(this.mTelecomCallback);
        sync(getUserId());
    }

    @Override // android.telecom.InCallService
    public void onCallRemoved(Call call) {
        if (!CompanionDeviceConfig.isEnabled("enable_context_sync_telecom") || this.mNumberOfActiveSyncAssociations <= 0) {
            return;
        }
        this.mCurrentCalls.remove(call);
        call.unregisterCallback(this.mTelecomCallback);
        this.mCdmsi.removeSelfOwnedCallId(call.getDetails().getExtras().getString("com.android.companion.datatransfer.contextsync.extra.CALL_ID"));
        sync(getUserId());
    }

    @Override // android.telecom.InCallService
    public void onMuteStateChanged(boolean z) {
        if (!CompanionDeviceConfig.isEnabled("enable_context_sync_telecom") || this.mNumberOfActiveSyncAssociations <= 0) {
            return;
        }
        this.mCdmsi.sendCrossDeviceSyncMessageToAllDevices(getUserId(), CrossDeviceSyncController.createCallControlMessage(null, z ? 4 : 5));
    }

    @Override // android.telecom.InCallService
    public void onSilenceRinger() {
        if (!CompanionDeviceConfig.isEnabled("enable_context_sync_telecom") || this.mNumberOfActiveSyncAssociations <= 0) {
            return;
        }
        this.mCdmsi.sendCrossDeviceSyncMessageToAllDevices(getUserId(), CrossDeviceSyncController.createCallControlMessage(null, 3));
    }

    public final void doMute() {
        setMuted(true);
    }

    public final void doUnmute() {
        setMuted(false);
    }

    public final void doSilence() {
        TelecomManager telecomManager = (TelecomManager) getSystemService(TelecomManager.class);
        if (telecomManager != null) {
            telecomManager.silenceRinger();
        }
    }

    public final void sync(int i) {
        this.mCdmsi.crossDeviceSync(i, this.mCurrentCalls.values());
    }

    public final void sync(AssociationInfo associationInfo) {
        this.mCdmsi.crossDeviceSync(associationInfo, this.mCurrentCalls.values());
    }
}
