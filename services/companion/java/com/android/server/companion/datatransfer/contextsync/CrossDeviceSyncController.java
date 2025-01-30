package com.android.server.companion.datatransfer.contextsync;

import android.app.admin.DevicePolicyManager;
import android.companion.AssociationInfo;
import android.companion.IOnMessageReceivedListener;
import android.companion.IOnTransportsChangedListener;
import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.UserHandle;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.util.Slog;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoParseException;
import android.util.proto.ProtoUtils;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda0;
import com.android.server.companion.CompanionDeviceConfig;
import com.android.server.companion.datatransfer.contextsync.CallMetadataSyncData;
import com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController;
import com.android.server.companion.transport.CompanionTransportManager;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class CrossDeviceSyncController {
    public final CallManager mCallManager;
    public final CompanionTransportManager mCompanionTransportManager;
    public WeakReference mConnectionServiceCallbackRef;
    public final Context mContext;
    public WeakReference mInCallServiceCallbackRef;
    public final PhoneAccountManager mPhoneAccountManager;
    public final List mConnectedAssociations = new ArrayList();
    public final Set mBlocklist = new HashSet();
    public final List mCallFacilitators = new ArrayList();

    public CrossDeviceSyncController(Context context, CompanionTransportManager companionTransportManager) {
        this.mContext = context;
        this.mCompanionTransportManager = companionTransportManager;
        companionTransportManager.addListener(new IOnTransportsChangedListener.Stub() { // from class: com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.1
            public void onTransportsChanged(List list) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (CompanionDeviceConfig.isEnabled("enable_context_sync_telecom")) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        ArrayList<AssociationInfo> arrayList = new ArrayList(CrossDeviceSyncController.this.mConnectedAssociations);
                        CrossDeviceSyncController.this.mConnectedAssociations.clear();
                        CrossDeviceSyncController.this.mConnectedAssociations.addAll(list);
                        Iterator it = list.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            AssociationInfo associationInfo = (AssociationInfo) it.next();
                            if (!arrayList.contains(associationInfo)) {
                                if (!CrossDeviceSyncController.isAssociationBlocked(associationInfo)) {
                                    CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback = CrossDeviceSyncController.this.mInCallServiceCallbackRef != null ? (CrossDeviceSyncControllerCallback) CrossDeviceSyncController.this.mInCallServiceCallbackRef.get() : null;
                                    if (crossDeviceSyncControllerCallback != null) {
                                        crossDeviceSyncControllerCallback.updateNumberOfActiveSyncAssociations(associationInfo.getUserId(), true);
                                        crossDeviceSyncControllerCallback.requestCrossDeviceSync(associationInfo);
                                    } else {
                                        Slog.w("CrossDeviceSyncController", "No callback to report new transport");
                                        CrossDeviceSyncController.this.syncMessageToDevice(associationInfo.getId(), CrossDeviceSyncController.this.createFacilitatorMessage());
                                    }
                                } else {
                                    CrossDeviceSyncController.this.mBlocklist.add(Integer.valueOf(associationInfo.getId()));
                                    Slog.i("CrossDeviceSyncController", "New association was blocked from context syncing");
                                }
                            }
                        }
                        for (AssociationInfo associationInfo2 : arrayList) {
                            if (!list.contains(associationInfo2)) {
                                CrossDeviceSyncController.this.mBlocklist.remove(Integer.valueOf(associationInfo2.getId()));
                                if (!CrossDeviceSyncController.this.isAssociationBlockedLocal(associationInfo2.getId())) {
                                    CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback2 = CrossDeviceSyncController.this.mInCallServiceCallbackRef != null ? (CrossDeviceSyncControllerCallback) CrossDeviceSyncController.this.mInCallServiceCallbackRef.get() : null;
                                    if (crossDeviceSyncControllerCallback2 != null) {
                                        crossDeviceSyncControllerCallback2.updateNumberOfActiveSyncAssociations(associationInfo2.getUserId(), false);
                                    } else {
                                        Slog.w("CrossDeviceSyncController", "No callback to report removed transport");
                                    }
                                }
                                CrossDeviceSyncController.this.clearInProgressCalls(associationInfo2.getId());
                            } else {
                                boolean isAssociationBlocked = CrossDeviceSyncController.isAssociationBlocked(associationInfo2);
                                if (CrossDeviceSyncController.this.isAssociationBlockedLocal(associationInfo2.getId()) != isAssociationBlocked) {
                                    CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback3 = CrossDeviceSyncController.this.mInCallServiceCallbackRef != null ? (CrossDeviceSyncControllerCallback) CrossDeviceSyncController.this.mInCallServiceCallbackRef.get() : null;
                                    if (!isAssociationBlocked) {
                                        Slog.i("CrossDeviceSyncController", "Unblocking existing association for context sync");
                                        CrossDeviceSyncController.this.mBlocklist.remove(Integer.valueOf(associationInfo2.getId()));
                                        if (crossDeviceSyncControllerCallback3 != null) {
                                            crossDeviceSyncControllerCallback3.updateNumberOfActiveSyncAssociations(associationInfo2.getUserId(), true);
                                            crossDeviceSyncControllerCallback3.requestCrossDeviceSync(associationInfo2);
                                        } else {
                                            Slog.w("CrossDeviceSyncController", "No callback to report changed transport");
                                            CrossDeviceSyncController.this.syncMessageToDevice(associationInfo2.getId(), CrossDeviceSyncController.this.createFacilitatorMessage());
                                        }
                                    } else {
                                        Slog.i("CrossDeviceSyncController", "Blocking existing association for context sync");
                                        CrossDeviceSyncController.this.mBlocklist.add(Integer.valueOf(associationInfo2.getId()));
                                        if (crossDeviceSyncControllerCallback3 != null) {
                                            crossDeviceSyncControllerCallback3.updateNumberOfActiveSyncAssociations(associationInfo2.getUserId(), false);
                                        } else {
                                            Slog.w("CrossDeviceSyncController", "No callback to report changed transport");
                                        }
                                        CrossDeviceSyncController.this.syncMessageToDevice(associationInfo2.getId(), CrossDeviceSyncController.createEmptyMessage());
                                        CrossDeviceSyncController.this.clearInProgressCalls(associationInfo2.getId());
                                    }
                                }
                            }
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        });
        companionTransportManager.addListener(1667729539, new IOnMessageReceivedListener.Stub() { // from class: com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.2
            public void onMessageReceived(int i, byte[] bArr) {
                if (CrossDeviceSyncController.this.isAssociationBlockedLocal(i)) {
                    return;
                }
                CallMetadataSyncData processTelecomDataFromSync = CrossDeviceSyncController.this.processTelecomDataFromSync(bArr);
                boolean z = (processTelecomDataFromSync.getCallControlRequests().size() == 0 && processTelecomDataFromSync.getCallCreateRequests().size() == 0) ? false : true;
                if (!z) {
                    CrossDeviceSyncController.this.mPhoneAccountManager.updateFacilitators(i, processTelecomDataFromSync);
                    CrossDeviceSyncController.this.mCallManager.updateCalls(i, processTelecomDataFromSync);
                } else {
                    CrossDeviceSyncController.this.processCallCreateRequests(processTelecomDataFromSync);
                }
                if (CrossDeviceSyncController.this.mInCallServiceCallbackRef == null && CrossDeviceSyncController.this.mConnectionServiceCallbackRef == null) {
                    Slog.w("CrossDeviceSyncController", "No callback to process context sync message");
                    return;
                }
                CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback = CrossDeviceSyncController.this.mInCallServiceCallbackRef != null ? (CrossDeviceSyncControllerCallback) CrossDeviceSyncController.this.mInCallServiceCallbackRef.get() : null;
                if (crossDeviceSyncControllerCallback == null) {
                    CrossDeviceSyncController.this.mInCallServiceCallbackRef = null;
                } else if (z) {
                    crossDeviceSyncControllerCallback.processContextSyncMessage(i, processTelecomDataFromSync);
                }
                CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback2 = CrossDeviceSyncController.this.mConnectionServiceCallbackRef != null ? (CrossDeviceSyncControllerCallback) CrossDeviceSyncController.this.mConnectionServiceCallbackRef.get() : null;
                if (crossDeviceSyncControllerCallback2 == null) {
                    CrossDeviceSyncController.this.mConnectionServiceCallbackRef = null;
                } else {
                    if (z) {
                        return;
                    }
                    crossDeviceSyncControllerCallback2.processContextSyncMessage(i, processTelecomDataFromSync);
                }
            }
        });
        PhoneAccountManager phoneAccountManager = new PhoneAccountManager(context);
        this.mPhoneAccountManager = phoneAccountManager;
        this.mCallManager = new CallManager(context, phoneAccountManager);
    }

    public final void clearInProgressCalls(int i) {
        Set clearCallIdsForAssociationId = this.mCallManager.clearCallIdsForAssociationId(i);
        WeakReference weakReference = this.mConnectionServiceCallbackRef;
        CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback = weakReference != null ? (CrossDeviceSyncControllerCallback) weakReference.get() : null;
        if (crossDeviceSyncControllerCallback != null) {
            crossDeviceSyncControllerCallback.cleanUpCallIds(clearCallIdsForAssociationId);
        }
    }

    public static boolean isAssociationBlocked(AssociationInfo associationInfo) {
        return (associationInfo.getSystemDataSyncFlags() & 1) != 1;
    }

    public void onBootCompleted() {
        PhoneAccountHandle defaultOutgoingPhoneAccount;
        PhoneAccount phoneAccount;
        if (CompanionDeviceConfig.isEnabled("enable_context_sync_telecom")) {
            this.mPhoneAccountManager.onBootCompleted();
            TelecomManager telecomManager = (TelecomManager) this.mContext.getSystemService(TelecomManager.class);
            if (telecomManager == null || telecomManager.getCallCapablePhoneAccounts().size() == 0 || (defaultOutgoingPhoneAccount = telecomManager.getDefaultOutgoingPhoneAccount("tel")) == null || (phoneAccount = telecomManager.getPhoneAccount(defaultOutgoingPhoneAccount)) == null || phoneAccount.getLabel() == null) {
                return;
            }
            this.mCallFacilitators.add(new CallMetadataSyncData.CallFacilitator(phoneAccount.getLabel().toString(), "system", "system"));
        }
    }

    public final void processCallCreateRequests(CallMetadataSyncData callMetadataSyncData) {
        Iterator it = callMetadataSyncData.getCallCreateRequests().iterator();
        while (it.hasNext()) {
            CallMetadataSyncData.CallCreateRequest callCreateRequest = (CallMetadataSyncData.CallCreateRequest) it.next();
            if ("system".equals(callCreateRequest.getFacilitator().getIdentifier())) {
                if (callCreateRequest.getAddress() != null && callCreateRequest.getAddress().startsWith("tel")) {
                    this.mCallManager.addSelfOwnedCallId(callCreateRequest.getId());
                    Uri fromParts = Uri.fromParts("tel", callCreateRequest.getAddress().replaceAll("\\D+", ""), null);
                    Bundle bundle = new Bundle();
                    bundle.putString("com.android.companion.datatransfer.contextsync.extra.CALL_ID", callCreateRequest.getId());
                    Bundle bundle2 = new Bundle();
                    bundle2.putParcelable("android.telecom.extra.OUTGOING_CALL_EXTRAS", bundle);
                    ((TelecomManager) this.mContext.getSystemService(TelecomManager.class)).placeCall(fromParts, bundle2);
                }
            } else {
                Slog.e("CrossDeviceSyncController", "Non-system facilitated calls are not supported yet");
            }
            it.remove();
        }
    }

    public final boolean isAssociationBlockedLocal(int i) {
        return this.mBlocklist.contains(Integer.valueOf(i));
    }

    public void registerCallMetadataSyncCallback(CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback, int i) {
        if (i != 2) {
            if (i == 1) {
                this.mConnectionServiceCallbackRef = new WeakReference(crossDeviceSyncControllerCallback);
                return;
            }
            Slog.e("CrossDeviceSyncController", "Cannot register callback of unknown type: " + i);
            return;
        }
        this.mInCallServiceCallbackRef = new WeakReference(crossDeviceSyncControllerCallback);
        for (AssociationInfo associationInfo : this.mConnectedAssociations) {
            if (!isAssociationBlocked(associationInfo)) {
                this.mBlocklist.remove(Integer.valueOf(associationInfo.getId()));
                crossDeviceSyncControllerCallback.updateNumberOfActiveSyncAssociations(associationInfo.getUserId(), true);
                crossDeviceSyncControllerCallback.requestCrossDeviceSync(associationInfo);
            } else {
                this.mBlocklist.add(Integer.valueOf(associationInfo.getId()));
            }
        }
    }

    public final boolean isAdminBlocked(int i) {
        return ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getBluetoothContactSharingDisabled(UserHandle.of(i));
    }

    public void syncToAllDevicesForUserId(int i, Collection collection) {
        HashSet hashSet = new HashSet();
        for (AssociationInfo associationInfo : this.mConnectedAssociations) {
            if (associationInfo.getUserId() == i && !isAssociationBlocked(associationInfo)) {
                hashSet.add(Integer.valueOf(associationInfo.getId()));
            }
        }
        if (hashSet.isEmpty()) {
            Slog.w("CrossDeviceSyncController", "No eligible devices to sync to");
        } else {
            this.mCompanionTransportManager.sendMessage(1667729539, createCallUpdateMessage(collection, i), hashSet.stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray());
        }
    }

    public void syncToSingleDevice(AssociationInfo associationInfo, Collection collection) {
        if (isAssociationBlocked(associationInfo)) {
            Slog.e("CrossDeviceSyncController", "Cannot sync to requested device; connection is blocked");
        } else {
            this.mCompanionTransportManager.sendMessage(1667729539, createCallUpdateMessage(collection, associationInfo.getUserId()), new int[]{associationInfo.getId()});
        }
    }

    public void syncMessageToDevice(int i, byte[] bArr) {
        if (isAssociationBlockedLocal(i)) {
            Slog.e("CrossDeviceSyncController", "Cannot sync to requested device; connection is blocked");
        } else {
            this.mCompanionTransportManager.sendMessage(1667729539, bArr, new int[]{i});
        }
    }

    public void syncMessageToAllDevicesForUserId(int i, byte[] bArr) {
        HashSet hashSet = new HashSet();
        for (AssociationInfo associationInfo : this.mConnectedAssociations) {
            if (associationInfo.getUserId() == i && !isAssociationBlocked(associationInfo)) {
                hashSet.add(Integer.valueOf(associationInfo.getId()));
            }
        }
        if (hashSet.isEmpty()) {
            Slog.w("CrossDeviceSyncController", "No eligible devices to sync to");
        } else {
            this.mCompanionTransportManager.sendMessage(1667729539, bArr, hashSet.stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray());
        }
    }

    public void addSelfOwnedCallId(String str) {
        this.mCallManager.addSelfOwnedCallId(str);
    }

    public void removeSelfOwnedCallId(String str) {
        if (str != null) {
            this.mCallManager.removeSelfOwnedCallId(str);
        }
    }

    public CallMetadataSyncData processTelecomDataFromSync(byte[] bArr) {
        CallMetadataSyncData callMetadataSyncData = new CallMetadataSyncData();
        ProtoInputStream protoInputStream = new ProtoInputStream(bArr);
        int i = -1;
        while (protoInputStream.nextField() != -1) {
            try {
                int fieldNumber = protoInputStream.getFieldNumber();
                if (fieldNumber == 1) {
                    i = protoInputStream.readInt(1120986464257L);
                    Slog.e("CrossDeviceSyncController", "Processing context sync message version " + i);
                } else if (fieldNumber != 4) {
                    Slog.e("CrossDeviceSyncController", "Unhandled field in ContextSyncMessage:" + ProtoUtils.currentFieldToString(protoInputStream));
                } else if (i == 1) {
                    long start = protoInputStream.start(1146756268036L);
                    while (protoInputStream.nextField() != -1) {
                        if (protoInputStream.getFieldNumber() == 1) {
                            long start2 = protoInputStream.start(2246267895809L);
                            callMetadataSyncData.addCall(processCallDataFromSync(protoInputStream));
                            protoInputStream.end(start2);
                        } else if (protoInputStream.getFieldNumber() == 2) {
                            long start3 = protoInputStream.start(2246267895810L);
                            while (protoInputStream.nextField() != -1) {
                                int fieldNumber2 = protoInputStream.getFieldNumber();
                                if (fieldNumber2 == 1) {
                                    long start4 = protoInputStream.start(1146756268033L);
                                    callMetadataSyncData.addCallCreateRequest(processCallCreateRequestDataFromSync(protoInputStream));
                                    protoInputStream.end(start4);
                                } else if (fieldNumber2 == 2) {
                                    long start5 = protoInputStream.start(1146756268034L);
                                    callMetadataSyncData.addCallControlRequest(processCallControlRequestDataFromSync(protoInputStream));
                                    protoInputStream.end(start5);
                                } else {
                                    Slog.e("CrossDeviceSyncController", "Unhandled field in Request:" + ProtoUtils.currentFieldToString(protoInputStream));
                                }
                            }
                            protoInputStream.end(start3);
                        } else if (protoInputStream.getFieldNumber() == 3) {
                            long start6 = protoInputStream.start(2246267895811L);
                            CallMetadataSyncData.CallFacilitator processFacilitatorDataFromSync = processFacilitatorDataFromSync(protoInputStream);
                            processFacilitatorDataFromSync.setIsTel(true);
                            callMetadataSyncData.addFacilitator(processFacilitatorDataFromSync);
                            protoInputStream.end(start6);
                        } else {
                            Slog.e("CrossDeviceSyncController", "Unhandled field in Telecom:" + ProtoUtils.currentFieldToString(protoInputStream));
                        }
                    }
                    protoInputStream.end(start);
                } else {
                    Slog.e("CrossDeviceSyncController", "Cannot process unsupported version " + i);
                }
            } catch (IOException | ProtoParseException e) {
                throw new RuntimeException(e);
            }
        }
        return callMetadataSyncData;
    }

    public static CallMetadataSyncData.CallCreateRequest processCallCreateRequestDataFromSync(ProtoInputStream protoInputStream) {
        CallMetadataSyncData.CallCreateRequest callCreateRequest = new CallMetadataSyncData.CallCreateRequest();
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                callCreateRequest.setId(protoInputStream.readString(1138166333441L));
            } else if (fieldNumber == 2) {
                callCreateRequest.setAddress(protoInputStream.readString(1138166333442L));
            } else if (fieldNumber == 3) {
                long start = protoInputStream.start(1146756268035L);
                callCreateRequest.setFacilitator(processFacilitatorDataFromSync(protoInputStream));
                protoInputStream.end(start);
            } else {
                Slog.e("CrossDeviceSyncController", "Unhandled field in CreateAction:" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return callCreateRequest;
    }

    public static CallMetadataSyncData.CallControlRequest processCallControlRequestDataFromSync(ProtoInputStream protoInputStream) {
        CallMetadataSyncData.CallControlRequest callControlRequest = new CallMetadataSyncData.CallControlRequest();
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                callControlRequest.setId(protoInputStream.readString(1138166333441L));
            } else if (fieldNumber == 2) {
                callControlRequest.setControl(protoInputStream.readInt(1159641169922L));
            } else {
                Slog.e("CrossDeviceSyncController", "Unhandled field in ControlAction:" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return callControlRequest;
    }

    public static CallMetadataSyncData.CallFacilitator processFacilitatorDataFromSync(ProtoInputStream protoInputStream) {
        CallMetadataSyncData.CallFacilitator callFacilitator = new CallMetadataSyncData.CallFacilitator();
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                callFacilitator.setName(protoInputStream.readString(1138166333441L));
            } else if (fieldNumber == 2) {
                callFacilitator.setIdentifier(protoInputStream.readString(1138166333442L));
            } else if (fieldNumber == 3) {
                callFacilitator.setExtendedIdentifier(protoInputStream.readString(1138166333443L));
            } else {
                Slog.e("CrossDeviceSyncController", "Unhandled field in Facilitator:" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return callFacilitator;
    }

    public CallMetadataSyncData.Call processCallDataFromSync(ProtoInputStream protoInputStream) {
        CallMetadataSyncData.Call call = new CallMetadataSyncData.Call();
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                call.setId(protoInputStream.readString(1138166333441L));
            } else if (fieldNumber == 2) {
                long start = protoInputStream.start(1146756268034L);
                while (protoInputStream.nextField() != -1) {
                    int fieldNumber2 = protoInputStream.getFieldNumber();
                    if (fieldNumber2 == 1) {
                        call.setCallerId(protoInputStream.readString(1138166333441L));
                    } else if (fieldNumber2 == 2) {
                        call.setAppIcon(protoInputStream.readBytes(1151051235330L));
                    } else if (fieldNumber2 == 3) {
                        long start2 = protoInputStream.start(1146756268035L);
                        call.setFacilitator(processFacilitatorDataFromSync(protoInputStream));
                        protoInputStream.end(start2);
                    } else {
                        Slog.e("CrossDeviceSyncController", "Unhandled field in Origin:" + ProtoUtils.currentFieldToString(protoInputStream));
                    }
                }
                protoInputStream.end(start);
            } else if (fieldNumber == 3) {
                call.setStatus(protoInputStream.readInt(1159641169923L));
            } else if (fieldNumber == 4) {
                call.addControl(protoInputStream.readInt(2259152797700L));
            } else if (fieldNumber == 5) {
                call.setDirection(protoInputStream.readInt(1159641169925L));
            } else {
                Slog.e("CrossDeviceSyncController", "Unhandled field in Telecom:" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return call;
    }

    public byte[] createCallUpdateMessage(Collection collection, int i) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        protoOutputStream.write(1120986464257L, 1);
        long start = protoOutputStream.start(1146756268036L);
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            CrossDeviceCall crossDeviceCall = (CrossDeviceCall) it.next();
            if (!crossDeviceCall.isCallPlacedByContextSync() && !this.mCallManager.isExternallyOwned(crossDeviceCall.getId())) {
                long start2 = protoOutputStream.start(2246267895809L);
                protoOutputStream.write(1138166333441L, crossDeviceCall.getId());
                long start3 = protoOutputStream.start(1146756268034L);
                protoOutputStream.write(1138166333441L, crossDeviceCall.getReadableCallerId(isAdminBlocked(crossDeviceCall.getUserId())));
                protoOutputStream.write(1151051235330L, crossDeviceCall.getCallingAppIcon());
                long start4 = protoOutputStream.start(1146756268035L);
                protoOutputStream.write(1138166333441L, crossDeviceCall.getCallingAppName());
                protoOutputStream.write(1138166333442L, crossDeviceCall.getCallingAppPackageName());
                protoOutputStream.write(1138166333443L, crossDeviceCall.getSerializedPhoneAccountHandle());
                protoOutputStream.end(start4);
                protoOutputStream.end(start3);
                protoOutputStream.write(1159641169923L, crossDeviceCall.getStatus());
                protoOutputStream.write(1159641169925L, crossDeviceCall.getDirection());
                Iterator it2 = crossDeviceCall.getControls().iterator();
                while (it2.hasNext()) {
                    protoOutputStream.write(2259152797700L, ((Integer) it2.next()).intValue());
                }
                protoOutputStream.end(start2);
            }
        }
        for (CallMetadataSyncData.CallFacilitator callFacilitator : this.mCallFacilitators) {
            long start5 = protoOutputStream.start(2246267895811L);
            protoOutputStream.write(1138166333441L, callFacilitator.getName());
            protoOutputStream.write(1138166333442L, callFacilitator.getIdentifier());
            protoOutputStream.write(1138166333443L, callFacilitator.getExtendedIdentifier());
            protoOutputStream.end(start5);
        }
        protoOutputStream.end(start);
        return protoOutputStream.getBytes();
    }

    public static byte[] createCallControlMessage(String str, int i) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        protoOutputStream.write(1120986464257L, 1);
        long start = protoOutputStream.start(1146756268036L);
        long start2 = protoOutputStream.start(2246267895810L);
        long start3 = protoOutputStream.start(1146756268034L);
        protoOutputStream.write(1138166333441L, str);
        protoOutputStream.write(1159641169922L, i);
        protoOutputStream.end(start3);
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
        return protoOutputStream.getBytes();
    }

    public static byte[] createCallCreateMessage(String str, String str2, String str3) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        protoOutputStream.write(1120986464257L, 1);
        long start = protoOutputStream.start(1146756268036L);
        long start2 = protoOutputStream.start(2246267895810L);
        long start3 = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1138166333441L, str);
        protoOutputStream.write(1138166333442L, str2);
        long start4 = protoOutputStream.start(1146756268035L);
        protoOutputStream.write(1138166333442L, str3);
        protoOutputStream.end(start4);
        protoOutputStream.end(start3);
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
        return protoOutputStream.getBytes();
    }

    public static byte[] createEmptyMessage() {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        protoOutputStream.write(1120986464257L, 1);
        return protoOutputStream.getBytes();
    }

    public final byte[] createFacilitatorMessage() {
        return createCallUpdateMessage(Collections.emptyList(), -1);
    }

    class CallManager {
        public final PhoneAccountManager mPhoneAccountManager;
        public final TelecomManager mTelecomManager;
        final Set mSelfOwnedCalls = new HashSet();
        final Set mExternallyOwnedCalls = new HashSet();
        final Map mCallIds = new HashMap();

        public CallManager(Context context, PhoneAccountManager phoneAccountManager) {
            this.mTelecomManager = (TelecomManager) context.getSystemService(TelecomManager.class);
            this.mPhoneAccountManager = phoneAccountManager;
        }

        public void updateCalls(int i, CallMetadataSyncData callMetadataSyncData) {
            Set set = (Set) this.mCallIds.getOrDefault(Integer.valueOf(i), new HashSet());
            Set set2 = (Set) callMetadataSyncData.getCalls().stream().map(new Function() { // from class: com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController$CallManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((CallMetadataSyncData.Call) obj).getId();
                }
            }).collect(Collectors.toSet());
            if (set.equals(set2)) {
                return;
            }
            for (CallMetadataSyncData.Call call : callMetadataSyncData.getCalls()) {
                if (!set.contains(call.getId()) && call.getFacilitator() != null && !isSelfOwned(call.getId())) {
                    this.mExternallyOwnedCalls.add(call.getId());
                    Bundle bundle = new Bundle();
                    bundle.putInt("com.android.server.companion.datatransfer.contextsync.extra.ASSOCIATION_ID", i);
                    bundle.putBoolean("com.android.companion.datatransfer.contextsync.extra.IS_REMOTE_ORIGIN", true);
                    bundle.putBundle("com.android.server.companion.datatransfer.contextsync.extra.CALL", call.writeToBundle());
                    bundle.putString("com.android.companion.datatransfer.contextsync.extra.CALL_ID", call.getId());
                    bundle.putByteArray("com.android.companion.datatransfer.contextsync.extra.FACILITATOR_ICON", call.getAppIcon());
                    PhoneAccountHandle phoneAccountHandle = this.mPhoneAccountManager.getPhoneAccountHandle(i, call.getFacilitator().getIdentifier());
                    if (call.getDirection() == 1) {
                        this.mTelecomManager.addNewIncomingCall(phoneAccountHandle, bundle);
                    } else if (call.getDirection() == 2) {
                        Bundle bundle2 = new Bundle();
                        bundle2.putParcelable("android.telecom.extra.OUTGOING_CALL_EXTRAS", bundle);
                        bundle2.putParcelable("android.telecom.extra.PHONE_ACCOUNT_HANDLE", phoneAccountHandle);
                        String callerId = call.getCallerId();
                        if (callerId != null) {
                            this.mTelecomManager.placeCall(Uri.fromParts("sip", callerId, null), bundle2);
                        }
                    }
                }
            }
            this.mCallIds.put(Integer.valueOf(i), set2);
        }

        public Set clearCallIdsForAssociationId(int i) {
            return (Set) this.mCallIds.remove(Integer.valueOf(i));
        }

        public void addSelfOwnedCallId(String str) {
            this.mSelfOwnedCalls.add(str);
        }

        public void removeSelfOwnedCallId(String str) {
            this.mSelfOwnedCalls.remove(str);
        }

        public boolean isExternallyOwned(String str) {
            return this.mExternallyOwnedCalls.contains(str);
        }

        public final boolean isSelfOwned(String str) {
            Iterator it = this.mSelfOwnedCalls.iterator();
            while (it.hasNext()) {
                if (str.endsWith((String) it.next())) {
                    return true;
                }
            }
            return false;
        }
    }

    public class PhoneAccountManager {
        public final ComponentName mConnectionServiceComponentName;
        public final Map mPhoneAccountHandles = new HashMap();
        public final TelecomManager mTelecomManager;

        public PhoneAccountManager(Context context) {
            this.mTelecomManager = (TelecomManager) context.getSystemService(TelecomManager.class);
            this.mConnectionServiceComponentName = new ComponentName(context, (Class<?>) CallMetadataSyncConnectionService.class);
        }

        public void onBootCompleted() {
            this.mTelecomManager.clearPhoneAccounts();
        }

        public PhoneAccountHandle getPhoneAccountHandle(int i, String str) {
            return (PhoneAccountHandle) this.mPhoneAccountHandles.get(new PhoneAccountHandleIdentifier(i, str));
        }

        public void updateFacilitators(int i, CallMetadataSyncData callMetadataSyncData) {
            ArrayList arrayList = new ArrayList();
            Iterator it = callMetadataSyncData.getCalls().iterator();
            while (it.hasNext()) {
                arrayList.add(((CallMetadataSyncData.Call) it.next()).getFacilitator());
            }
            arrayList.addAll(callMetadataSyncData.getFacilitators());
            updateFacilitators(i, arrayList);
        }

        public final void updateFacilitators(int i, List list) {
            Iterator it = this.mPhoneAccountHandles.keySet().iterator();
            while (it.hasNext()) {
                PhoneAccountHandleIdentifier phoneAccountHandleIdentifier = (PhoneAccountHandleIdentifier) it.next();
                final String appIdentifier = phoneAccountHandleIdentifier.getAppIdentifier();
                if (i == phoneAccountHandleIdentifier.getAssociationId() && list.stream().noneMatch(new Predicate() { // from class: com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController$PhoneAccountManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$updateFacilitators$0;
                        lambda$updateFacilitators$0 = CrossDeviceSyncController.PhoneAccountManager.lambda$updateFacilitators$0(appIdentifier, (CallMetadataSyncData.CallFacilitator) obj);
                        return lambda$updateFacilitators$0;
                    }
                })) {
                    unregisterPhoneAccount((PhoneAccountHandle) this.mPhoneAccountHandles.get(phoneAccountHandleIdentifier));
                    it.remove();
                }
            }
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                CallMetadataSyncData.CallFacilitator callFacilitator = (CallMetadataSyncData.CallFacilitator) it2.next();
                PhoneAccountHandleIdentifier phoneAccountHandleIdentifier2 = new PhoneAccountHandleIdentifier(i, callFacilitator.getIdentifier());
                if (!this.mPhoneAccountHandles.containsKey(phoneAccountHandleIdentifier2)) {
                    registerPhoneAccount(phoneAccountHandleIdentifier2, callFacilitator.getName(), callFacilitator.isTel());
                }
            }
        }

        public static /* synthetic */ boolean lambda$updateFacilitators$0(String str, CallMetadataSyncData.CallFacilitator callFacilitator) {
            return str != null && str.equals(callFacilitator.getIdentifier());
        }

        public final void registerPhoneAccount(PhoneAccountHandleIdentifier phoneAccountHandleIdentifier, String str, boolean z) {
            if (this.mPhoneAccountHandles.containsKey(phoneAccountHandleIdentifier)) {
                return;
            }
            PhoneAccountHandle phoneAccountHandle = new PhoneAccountHandle(this.mConnectionServiceComponentName, UUID.randomUUID().toString());
            this.mPhoneAccountHandles.put(phoneAccountHandleIdentifier, phoneAccountHandle);
            this.mTelecomManager.registerPhoneAccount(createPhoneAccount(phoneAccountHandle, str, phoneAccountHandleIdentifier.getAppIdentifier(), phoneAccountHandleIdentifier.getAssociationId(), z));
            this.mTelecomManager.enablePhoneAccount((PhoneAccountHandle) this.mPhoneAccountHandles.get(phoneAccountHandleIdentifier), true);
        }

        public final void unregisterPhoneAccount(PhoneAccountHandle phoneAccountHandle) {
            this.mTelecomManager.unregisterPhoneAccount(phoneAccountHandle);
        }

        public static PhoneAccount createPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str, String str2, int i, boolean z) {
            Bundle bundle = new Bundle();
            bundle.putString("com.android.server.companion.datatransfer.contextsync.extra.CALL_FACILITATOR_ID", str2);
            bundle.putInt("com.android.server.companion.datatransfer.contextsync.extra.ASSOCIATION_ID", i);
            return new PhoneAccount.Builder(phoneAccountHandle, str).setExtras(bundle).setSupportedUriSchemes(List.of(z ? "tel" : "sip")).setCapabilities(3).build();
        }
    }

    public final class PhoneAccountHandleIdentifier {
        public final String mAppIdentifier;
        public final int mAssociationId;

        public PhoneAccountHandleIdentifier(int i, String str) {
            this.mAssociationId = i;
            this.mAppIdentifier = str;
        }

        public int getAssociationId() {
            return this.mAssociationId;
        }

        public String getAppIdentifier() {
            return this.mAppIdentifier;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mAssociationId), this.mAppIdentifier);
        }

        public boolean equals(Object obj) {
            String str;
            if (!(obj instanceof PhoneAccountHandleIdentifier)) {
                return false;
            }
            PhoneAccountHandleIdentifier phoneAccountHandleIdentifier = (PhoneAccountHandleIdentifier) obj;
            return phoneAccountHandleIdentifier.getAssociationId() == this.mAssociationId && (str = this.mAppIdentifier) != null && str.equals(phoneAccountHandleIdentifier.getAppIdentifier());
        }
    }
}
