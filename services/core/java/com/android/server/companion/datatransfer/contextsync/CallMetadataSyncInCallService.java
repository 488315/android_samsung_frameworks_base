package com.android.server.companion.datatransfer.contextsync;

import android.companion.AssociationInfo;
import android.telecom.Call;
import android.telecom.InCallService;
import android.telecom.TelecomManager;
import android.util.Slog;

import com.android.server.LocalServices;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda1;
import com.android.server.companion.CompanionDeviceConfig;
import com.android.server.companion.CompanionDeviceManagerService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class CallMetadataSyncInCallService extends InCallService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public CompanionDeviceManagerService.LocalService mCdmsi;
    int mNumberOfActiveSyncAssociations;
    final Map mCurrentCalls = new HashMap();
    public final AnonymousClass1 mTelecomCallback =
            new Call
                    .Callback() { // from class:
                                  // com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.1
                @Override // android.telecom.Call.Callback
                public final void onDetailsChanged(Call call, Call.Details details) {
                    CallMetadataSyncInCallService callMetadataSyncInCallService =
                            CallMetadataSyncInCallService.this;
                    if (callMetadataSyncInCallService.mNumberOfActiveSyncAssociations > 0) {
                        CrossDeviceCall crossDeviceCall =
                                (CrossDeviceCall)
                                        callMetadataSyncInCallService.mCurrentCalls.get(call);
                        if (crossDeviceCall == null) {
                            Slog.w(
                                    "CallMetadataIcs",
                                    "Could not update details for nonexistent call");
                            return;
                        }
                        crossDeviceCall.updateCallDetails(details);
                        CallMetadataSyncInCallService callMetadataSyncInCallService2 =
                                CallMetadataSyncInCallService.this;
                        callMetadataSyncInCallService2.sync(
                                callMetadataSyncInCallService2.getUserId());
                    }
                }
            };
    public final AnonymousClass2 mCrossDeviceSyncControllerCallback =
            new CrossDeviceSyncControllerCallback() { // from class:
                                                      // com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService.2
                @Override // com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback
                public final void processContextSyncMessage(
                        int i, CallMetadataSyncData callMetadataSyncData) {
                    Iterator it =
                            ((ArrayList) callMetadataSyncData.mCallControlRequests).iterator();
                    while (it.hasNext()) {
                        CallMetadataSyncData.CallControlRequest callControlRequest =
                                (CallMetadataSyncData.CallControlRequest) it.next();
                        String str = callControlRequest.mId;
                        int i2 = callControlRequest.mControl;
                        CallMetadataSyncInCallService callMetadataSyncInCallService =
                                CallMetadataSyncInCallService.this;
                        CrossDeviceCall callForId =
                                callMetadataSyncInCallService.getCallForId(
                                        str, callMetadataSyncInCallService.mCurrentCalls.values());
                        switch (i2) {
                            case 1:
                                if (callForId == null) {
                                    Slog.w(
                                            "CallMetadataIcs",
                                            "Failed to process accept action; no matching call");
                                    break;
                                } else {
                                    callForId.mCall.answer(0);
                                    break;
                                }
                            case 2:
                                if (callForId == null) {
                                    Slog.w(
                                            "CallMetadataIcs",
                                            "Failed to process reject action; no matching call");
                                    break;
                                } else if (callForId.mStatus != 1) {
                                    break;
                                } else {
                                    callForId.mCall.reject(1);
                                    break;
                                }
                            case 3:
                                TelecomManager telecomManager =
                                        (TelecomManager)
                                                callMetadataSyncInCallService.getSystemService(
                                                        TelecomManager.class);
                                if (telecomManager == null) {
                                    break;
                                } else {
                                    telecomManager.silenceRinger();
                                    break;
                                }
                            case 4:
                                callMetadataSyncInCallService.setMuted(true);
                                break;
                            case 5:
                                callMetadataSyncInCallService.setMuted(false);
                                break;
                            case 6:
                                if (callForId == null) {
                                    Slog.w(
                                            "CallMetadataIcs",
                                            "Failed to process end action; no matching call");
                                    break;
                                } else {
                                    callForId.mCall.disconnect();
                                    break;
                                }
                            case 7:
                                if (callForId == null) {
                                    Slog.w(
                                            "CallMetadataIcs",
                                            "Failed to process hold action; no matching call");
                                    break;
                                } else {
                                    callForId.mCall.hold();
                                    break;
                                }
                            case 8:
                                if (callForId == null) {
                                    Slog.w(
                                            "CallMetadataIcs",
                                            "Failed to process unhold action; no matching call");
                                    break;
                                } else {
                                    callForId.mCall.unhold();
                                    break;
                                }
                        }
                        it.remove();
                    }
                }

                @Override // com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback
                public final void requestCrossDeviceSync(AssociationInfo associationInfo) {
                    int userId = associationInfo.getUserId();
                    CallMetadataSyncInCallService callMetadataSyncInCallService =
                            CallMetadataSyncInCallService.this;
                    if (userId == callMetadataSyncInCallService.getUserId()) {
                        CompanionDeviceManagerService.LocalService localService =
                                callMetadataSyncInCallService.mCdmsi;
                        Collection values = callMetadataSyncInCallService.mCurrentCalls.values();
                        localService.getClass();
                        if (CompanionDeviceConfig.isEnabled()) {
                            CrossDeviceSyncController crossDeviceSyncController =
                                    CompanionDeviceManagerService.this.mCrossDeviceSyncController;
                            crossDeviceSyncController.getClass();
                            if (CrossDeviceSyncController.isAssociationBlocked(associationInfo)) {
                                Slog.e(
                                        "CrossDeviceSyncController",
                                        "Cannot sync to requested device; connection is blocked");
                                return;
                            }
                            crossDeviceSyncController.mCompanionTransportManager.sendMessage(
                                    1667729539,
                                    crossDeviceSyncController.createCallUpdateMessage(
                                            values, associationInfo.getUserId()),
                                    new int[] {associationInfo.getId()});
                        }
                    }
                }

                @Override // com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback
                public final void updateNumberOfActiveSyncAssociations(int i, boolean z) {
                    final CallMetadataSyncInCallService callMetadataSyncInCallService =
                            CallMetadataSyncInCallService.this;
                    if (i == callMetadataSyncInCallService.getUserId()) {
                        int i2 = callMetadataSyncInCallService.mNumberOfActiveSyncAssociations;
                        boolean z2 = i2 > 0;
                        if (z) {
                            callMetadataSyncInCallService.mNumberOfActiveSyncAssociations = i2 + 1;
                        } else {
                            callMetadataSyncInCallService.mNumberOfActiveSyncAssociations = i2 - 1;
                        }
                        if (z2
                                || callMetadataSyncInCallService.mNumberOfActiveSyncAssociations
                                        <= 0) {
                            if (!z2
                                    || callMetadataSyncInCallService.mNumberOfActiveSyncAssociations
                                            > 0) {
                                return;
                            }
                            callMetadataSyncInCallService.mCurrentCalls.clear();
                            return;
                        }
                        if (!CompanionDeviceConfig.isEnabled()
                                || callMetadataSyncInCallService.mNumberOfActiveSyncAssociations
                                        <= 0) {
                            return;
                        }
                        callMetadataSyncInCallService.mCurrentCalls.putAll(
                                (Map)
                                        callMetadataSyncInCallService.getCalls().stream()
                                                .collect(
                                                        Collectors.toMap(
                                                                new CallMetadataSyncInCallService$$ExternalSyntheticLambda0(),
                                                                new Function() { // from class:
                                                                                 // com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService$$ExternalSyntheticLambda1
                                                                    @Override // java.util.function.Function
                                                                    public final Object apply(
                                                                            Object obj) {
                                                                        CallMetadataSyncInCallService
                                                                                callMetadataSyncInCallService2 =
                                                                                        CallMetadataSyncInCallService
                                                                                                .this;
                                                                        int i3 =
                                                                                CallMetadataSyncInCallService
                                                                                        .$r8$clinit;
                                                                        return new CrossDeviceCall(
                                                                                callMetadataSyncInCallService2,
                                                                                (Call) obj,
                                                                                callMetadataSyncInCallService2
                                                                                        .getCallAudioState());
                                                                    }
                                                                })));
                        callMetadataSyncInCallService
                                .mCurrentCalls
                                .keySet()
                                .forEach(
                                        new Consumer() { // from class:
                                                         // com.android.server.companion.datatransfer.contextsync.CallMetadataSyncInCallService$$ExternalSyntheticLambda2
                                            @Override // java.util.function.Consumer
                                            public final void accept(Object obj) {
                                                CallMetadataSyncInCallService
                                                        callMetadataSyncInCallService2 =
                                                                CallMetadataSyncInCallService.this;
                                                ((Call) obj)
                                                        .registerCallback(
                                                                callMetadataSyncInCallService2
                                                                        .mTelecomCallback,
                                                                callMetadataSyncInCallService2
                                                                        .getMainThreadHandler());
                                            }
                                        });
                        callMetadataSyncInCallService.sync(
                                callMetadataSyncInCallService.getUserId());
                    }
                }
            };

    public CrossDeviceCall getCallForId(String str, Collection collection) {
        if (str == null) {
            return null;
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            CrossDeviceCall crossDeviceCall = (CrossDeviceCall) it.next();
            if (str.equals(crossDeviceCall.mId)) {
                return crossDeviceCall;
            }
        }
        return null;
    }

    @Override // android.telecom.InCallService
    public final void onCallAdded(Call call) {
        if (!CompanionDeviceConfig.isEnabled() || this.mNumberOfActiveSyncAssociations <= 0) {
            return;
        }
        this.mCurrentCalls.put(call, new CrossDeviceCall(this, call, getCallAudioState()));
        call.registerCallback(this.mTelecomCallback);
        sync(getUserId());
    }

    @Override // android.telecom.InCallService
    public final void onCallRemoved(Call call) {
        if (!CompanionDeviceConfig.isEnabled() || this.mNumberOfActiveSyncAssociations <= 0) {
            return;
        }
        this.mCurrentCalls.remove(call);
        call.unregisterCallback(this.mTelecomCallback);
        CompanionDeviceManagerService.LocalService localService = this.mCdmsi;
        String string =
                call.getDetails()
                        .getExtras()
                        .getString("com.android.companion.datatransfer.contextsync.extra.CALL_ID");
        localService.getClass();
        if (CompanionDeviceConfig.isEnabled()) {
            CrossDeviceSyncController crossDeviceSyncController =
                    CompanionDeviceManagerService.this.mCrossDeviceSyncController;
            if (string != null) {
                crossDeviceSyncController.mCallManager.mSelfOwnedCalls.remove(string);
            } else {
                crossDeviceSyncController.getClass();
            }
        }
        sync(getUserId());
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        if (CompanionDeviceConfig.isEnabled()) {
            CompanionDeviceManagerService.LocalService localService =
                    (CompanionDeviceManagerService.LocalService)
                            LocalServices.getService(
                                    CompanionDeviceManagerService.LocalService.class);
            this.mCdmsi = localService;
            localService.registerCallMetadataSyncCallback(
                    this.mCrossDeviceSyncControllerCallback, 2);
        }
    }

    @Override // android.telecom.InCallService
    public final void onMuteStateChanged(boolean z) {
        if (!CompanionDeviceConfig.isEnabled() || this.mNumberOfActiveSyncAssociations <= 0) {
            return;
        }
        this.mCdmsi.sendCrossDeviceSyncMessageToAllDevices(
                getUserId(), CrossDeviceSyncController.createCallControlMessage(z ? 4 : 5, null));
    }

    @Override // android.telecom.InCallService
    public final void onSilenceRinger() {
        if (!CompanionDeviceConfig.isEnabled() || this.mNumberOfActiveSyncAssociations <= 0) {
            return;
        }
        this.mCdmsi.sendCrossDeviceSyncMessageToAllDevices(
                getUserId(), CrossDeviceSyncController.createCallControlMessage(3, null));
    }

    public final void sync(int i) {
        CompanionDeviceManagerService.LocalService localService = this.mCdmsi;
        Collection values = this.mCurrentCalls.values();
        localService.getClass();
        if (CompanionDeviceConfig.isEnabled()) {
            CrossDeviceSyncController crossDeviceSyncController =
                    CompanionDeviceManagerService.this.mCrossDeviceSyncController;
            crossDeviceSyncController.getClass();
            HashSet hashSet = new HashSet();
            Iterator it = ((ArrayList) crossDeviceSyncController.mConnectedAssociations).iterator();
            while (it.hasNext()) {
                AssociationInfo associationInfo = (AssociationInfo) it.next();
                if (associationInfo.getUserId() == i
                        && !CrossDeviceSyncController.isAssociationBlocked(associationInfo)) {
                    hashSet.add(Integer.valueOf(associationInfo.getId()));
                }
            }
            if (hashSet.isEmpty()) {
                Slog.w("CrossDeviceSyncController", "No eligible devices to sync to");
                return;
            }
            crossDeviceSyncController.mCompanionTransportManager.sendMessage(
                    1667729539,
                    crossDeviceSyncController.createCallUpdateMessage(values, i),
                    hashSet.stream()
                            .mapToInt(new AudioService$$ExternalSyntheticLambda1(2))
                            .toArray());
        }
    }
}
