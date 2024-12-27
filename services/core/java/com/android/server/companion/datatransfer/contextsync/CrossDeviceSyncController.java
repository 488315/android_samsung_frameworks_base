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
import android.text.TextUtils;
import android.util.Slog;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoParseException;
import android.util.proto.ProtoUtils;

import com.android.server.am.FreecessController$$ExternalSyntheticOutline0;
import com.android.server.companion.CompanionDeviceConfig;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class CrossDeviceSyncController {
    public final CallManager mCallManager;
    public final CompanionTransportManager mCompanionTransportManager;
    public WeakReference mConnectionServiceCallbackRef;
    public final Context mContext;
    public WeakReference mInCallServiceCallbackRef;
    public final PhoneAccountManager mPhoneAccountManager;
    public final List mConnectedAssociations = new ArrayList();
    public final Set mBlocklist = new HashSet();
    public final List mCallFacilitators = new ArrayList();

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
    }

    public final class PhoneAccountHandleIdentifier {
        public final String mAppIdentifier;
        public final int mAssociationId;

        public PhoneAccountHandleIdentifier(int i, String str) {
            this.mAssociationId = i;
            this.mAppIdentifier = str;
        }

        public final boolean equals(Object obj) {
            String str;
            if (!(obj instanceof PhoneAccountHandleIdentifier)) {
                return false;
            }
            PhoneAccountHandleIdentifier phoneAccountHandleIdentifier =
                    (PhoneAccountHandleIdentifier) obj;
            return phoneAccountHandleIdentifier.mAssociationId == this.mAssociationId
                    && (str = this.mAppIdentifier) != null
                    && str.equals(phoneAccountHandleIdentifier.mAppIdentifier);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.mAssociationId), this.mAppIdentifier);
        }
    }

    public final class PhoneAccountManager {
        public final ComponentName mConnectionServiceComponentName;
        public final Map mPhoneAccountHandles = new HashMap();
        public final TelecomManager mTelecomManager;

        public PhoneAccountManager(Context context) {
            this.mTelecomManager = (TelecomManager) context.getSystemService(TelecomManager.class);
            this.mConnectionServiceComponentName =
                    new ComponentName(context, (Class<?>) CallMetadataSyncConnectionService.class);
        }

        public static PhoneAccount createPhoneAccount(
                PhoneAccountHandle phoneAccountHandle, String str, String str2, int i, boolean z) {
            return new PhoneAccount.Builder(phoneAccountHandle, str)
                    .setExtras(
                            FreecessController$$ExternalSyntheticOutline0.m(
                                    i,
                                    "com.android.server.companion.datatransfer.contextsync.extra.CALL_FACILITATOR_ID",
                                    str2,
                                    "com.android.server.companion.datatransfer.contextsync.extra.ASSOCIATION_ID"))
                    .setSupportedUriSchemes(List.of(z ? "tel" : "sip"))
                    .setCapabilities(3)
                    .build();
        }
    }

    /* renamed from: -$$Nest$mclearInProgressCalls, reason: not valid java name */
    public static void m346$$Nest$mclearInProgressCalls(
            CrossDeviceSyncController crossDeviceSyncController, int i) {
        Set set = (Set) crossDeviceSyncController.mCallManager.mCallIds.remove(Integer.valueOf(i));
        WeakReference weakReference = crossDeviceSyncController.mConnectionServiceCallbackRef;
        CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback =
                weakReference != null
                        ? (CrossDeviceSyncControllerCallback) weakReference.get()
                        : null;
        if (crossDeviceSyncControllerCallback != null) {
            crossDeviceSyncControllerCallback.cleanUpCallIds(set);
        }
    }

    public CrossDeviceSyncController(
            Context context, CompanionTransportManager companionTransportManager) {
        this.mContext = context;
        this.mCompanionTransportManager = companionTransportManager;
        companionTransportManager.addListener(
                new IOnTransportsChangedListener.Stub() { // from class:
                    // com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.1
                    public final void onTransportsChanged(List list) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            if (CompanionDeviceConfig.isEnabled()) {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                ArrayList arrayList =
                                        new ArrayList(
                                                CrossDeviceSyncController.this
                                                        .mConnectedAssociations);
                                ((ArrayList) CrossDeviceSyncController.this.mConnectedAssociations)
                                        .clear();
                                ((ArrayList) CrossDeviceSyncController.this.mConnectedAssociations)
                                        .addAll(list);
                                Iterator it = list.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    AssociationInfo associationInfo = (AssociationInfo) it.next();
                                    if (!arrayList.contains(associationInfo)) {
                                        if (CrossDeviceSyncController.isAssociationBlocked(
                                                associationInfo)) {
                                            ((HashSet) CrossDeviceSyncController.this.mBlocklist)
                                                    .add(Integer.valueOf(associationInfo.getId()));
                                            Slog.i(
                                                    "CrossDeviceSyncController",
                                                    "New association was blocked from context"
                                                        + " syncing");
                                        } else {
                                            WeakReference weakReference =
                                                    CrossDeviceSyncController.this
                                                            .mInCallServiceCallbackRef;
                                            CrossDeviceSyncControllerCallback
                                                    crossDeviceSyncControllerCallback =
                                                            weakReference != null
                                                                    ? (CrossDeviceSyncControllerCallback)
                                                                            weakReference.get()
                                                                    : null;
                                            if (crossDeviceSyncControllerCallback != null) {
                                                crossDeviceSyncControllerCallback
                                                        .updateNumberOfActiveSyncAssociations(
                                                                associationInfo.getUserId(), true);
                                                crossDeviceSyncControllerCallback
                                                        .requestCrossDeviceSync(associationInfo);
                                            } else {
                                                Slog.w(
                                                        "CrossDeviceSyncController",
                                                        "No callback to report new transport");
                                                CrossDeviceSyncController
                                                        crossDeviceSyncController =
                                                                CrossDeviceSyncController.this;
                                                int id = associationInfo.getId();
                                                CrossDeviceSyncController
                                                        crossDeviceSyncController2 =
                                                                CrossDeviceSyncController.this;
                                                crossDeviceSyncController2.getClass();
                                                crossDeviceSyncController.syncMessageToDevice(
                                                        id,
                                                        crossDeviceSyncController2
                                                                .createCallUpdateMessage(
                                                                        Collections.emptyList(),
                                                                        -1));
                                            }
                                        }
                                    }
                                }
                                Iterator it2 = arrayList.iterator();
                                while (it2.hasNext()) {
                                    AssociationInfo associationInfo2 = (AssociationInfo) it2.next();
                                    if (list.contains(associationInfo2)) {
                                        boolean isAssociationBlocked =
                                                CrossDeviceSyncController.isAssociationBlocked(
                                                        associationInfo2);
                                        if (CrossDeviceSyncController.this
                                                        .isAssociationBlockedLocal(
                                                                associationInfo2.getId())
                                                != isAssociationBlocked) {
                                            WeakReference weakReference2 =
                                                    CrossDeviceSyncController.this
                                                            .mInCallServiceCallbackRef;
                                            CrossDeviceSyncControllerCallback
                                                    crossDeviceSyncControllerCallback2 =
                                                            weakReference2 != null
                                                                    ? (CrossDeviceSyncControllerCallback)
                                                                            weakReference2.get()
                                                                    : null;
                                            if (isAssociationBlocked) {
                                                Slog.i(
                                                        "CrossDeviceSyncController",
                                                        "Blocking existing association for context"
                                                            + " sync");
                                                ((HashSet)
                                                                CrossDeviceSyncController.this
                                                                        .mBlocklist)
                                                        .add(
                                                                Integer.valueOf(
                                                                        associationInfo2.getId()));
                                                if (crossDeviceSyncControllerCallback2 != null) {
                                                    crossDeviceSyncControllerCallback2
                                                            .updateNumberOfActiveSyncAssociations(
                                                                    associationInfo2.getUserId(),
                                                                    false);
                                                } else {
                                                    Slog.w(
                                                            "CrossDeviceSyncController",
                                                            "No callback to report changed"
                                                                + " transport");
                                                }
                                                CrossDeviceSyncController
                                                        crossDeviceSyncController3 =
                                                                CrossDeviceSyncController.this;
                                                int id2 = associationInfo2.getId();
                                                ProtoOutputStream protoOutputStream =
                                                        new ProtoOutputStream();
                                                protoOutputStream.write(1120986464257L, 1);
                                                crossDeviceSyncController3.syncMessageToDevice(
                                                        id2, protoOutputStream.getBytes());
                                                CrossDeviceSyncController
                                                        .m346$$Nest$mclearInProgressCalls(
                                                                CrossDeviceSyncController.this,
                                                                associationInfo2.getId());
                                            } else {
                                                Slog.i(
                                                        "CrossDeviceSyncController",
                                                        "Unblocking existing association for"
                                                            + " context sync");
                                                ((HashSet)
                                                                CrossDeviceSyncController.this
                                                                        .mBlocklist)
                                                        .remove(
                                                                Integer.valueOf(
                                                                        associationInfo2.getId()));
                                                if (crossDeviceSyncControllerCallback2 != null) {
                                                    crossDeviceSyncControllerCallback2
                                                            .updateNumberOfActiveSyncAssociations(
                                                                    associationInfo2.getUserId(),
                                                                    true);
                                                    crossDeviceSyncControllerCallback2
                                                            .requestCrossDeviceSync(
                                                                    associationInfo2);
                                                } else {
                                                    Slog.w(
                                                            "CrossDeviceSyncController",
                                                            "No callback to report changed"
                                                                + " transport");
                                                    CrossDeviceSyncController
                                                            crossDeviceSyncController4 =
                                                                    CrossDeviceSyncController.this;
                                                    int id3 = associationInfo2.getId();
                                                    CrossDeviceSyncController
                                                            crossDeviceSyncController5 =
                                                                    CrossDeviceSyncController.this;
                                                    crossDeviceSyncController5.getClass();
                                                    crossDeviceSyncController4.syncMessageToDevice(
                                                            id3,
                                                            crossDeviceSyncController5
                                                                    .createCallUpdateMessage(
                                                                            Collections.emptyList(),
                                                                            -1));
                                                }
                                            }
                                        }
                                    } else {
                                        ((HashSet) CrossDeviceSyncController.this.mBlocklist)
                                                .remove(Integer.valueOf(associationInfo2.getId()));
                                        if (!CrossDeviceSyncController.this
                                                .isAssociationBlockedLocal(
                                                        associationInfo2.getId())) {
                                            WeakReference weakReference3 =
                                                    CrossDeviceSyncController.this
                                                            .mInCallServiceCallbackRef;
                                            CrossDeviceSyncControllerCallback
                                                    crossDeviceSyncControllerCallback3 =
                                                            weakReference3 != null
                                                                    ? (CrossDeviceSyncControllerCallback)
                                                                            weakReference3.get()
                                                                    : null;
                                            if (crossDeviceSyncControllerCallback3 != null) {
                                                crossDeviceSyncControllerCallback3
                                                        .updateNumberOfActiveSyncAssociations(
                                                                associationInfo2.getUserId(),
                                                                false);
                                            } else {
                                                Slog.w(
                                                        "CrossDeviceSyncController",
                                                        "No callback to report removed transport");
                                            }
                                        }
                                        CrossDeviceSyncController.m346$$Nest$mclearInProgressCalls(
                                                CrossDeviceSyncController.this,
                                                associationInfo2.getId());
                                    }
                                }
                            }
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                });
        companionTransportManager.addListener(
                1667729539,
                new IOnMessageReceivedListener.Stub() { // from class:
                    // com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.2
                    public final void onMessageReceived(int i, byte[] bArr) {
                        if (CrossDeviceSyncController.this.isAssociationBlockedLocal(i)) {
                            return;
                        }
                        CallMetadataSyncData processTelecomDataFromSync =
                                CrossDeviceSyncController.this.processTelecomDataFromSync(bArr);
                        boolean z =
                                (((ArrayList) processTelecomDataFromSync.mCallControlRequests)
                                                                .size()
                                                        == 0
                                                && ((ArrayList)
                                                                        processTelecomDataFromSync
                                                                                .mCallCreateRequests)
                                                                .size()
                                                        == 0)
                                        ? false
                                        : true;
                        if (z) {
                            CrossDeviceSyncController crossDeviceSyncController =
                                    CrossDeviceSyncController.this;
                            crossDeviceSyncController.getClass();
                            Iterator it =
                                    ((ArrayList) processTelecomDataFromSync.mCallCreateRequests)
                                            .iterator();
                            while (it.hasNext()) {
                                CallMetadataSyncData.CallCreateRequest callCreateRequest =
                                        (CallMetadataSyncData.CallCreateRequest) it.next();
                                if ("system".equals(callCreateRequest.mFacilitator.mIdentifier)) {
                                    String str = callCreateRequest.mAddress;
                                    if (str != null && str.startsWith("tel")) {
                                        crossDeviceSyncController.mCallManager.mSelfOwnedCalls.add(
                                                callCreateRequest.mId);
                                        Uri fromParts =
                                                Uri.fromParts(
                                                        "tel",
                                                        callCreateRequest.mAddress.replaceAll(
                                                                "\\D+", ""),
                                                        null);
                                        Bundle bundle = new Bundle();
                                        bundle.putString(
                                                "com.android.companion.datatransfer.contextsync.extra.CALL_ID",
                                                callCreateRequest.mId);
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putParcelable(
                                                "android.telecom.extra.OUTGOING_CALL_EXTRAS",
                                                bundle);
                                        ((TelecomManager)
                                                        crossDeviceSyncController.mContext
                                                                .getSystemService(
                                                                        TelecomManager.class))
                                                .placeCall(fromParts, bundle2);
                                    }
                                } else {
                                    Slog.e(
                                            "CrossDeviceSyncController",
                                            "Non-system facilitated calls are not supported yet");
                                }
                                it.remove();
                            }
                        } else {
                            PhoneAccountManager phoneAccountManager =
                                    CrossDeviceSyncController.this.mPhoneAccountManager;
                            phoneAccountManager.getClass();
                            ArrayList arrayList = new ArrayList();
                            Iterator it2 =
                                    ((HashMap) processTelecomDataFromSync.mCalls)
                                            .values()
                                            .iterator();
                            while (it2.hasNext()) {
                                arrayList.add(
                                        ((CallMetadataSyncData.Call) it2.next()).mFacilitator);
                            }
                            arrayList.addAll(processTelecomDataFromSync.mCallFacilitators);
                            Iterator it3 =
                                    ((HashMap) phoneAccountManager.mPhoneAccountHandles)
                                            .keySet()
                                            .iterator();
                            while (it3.hasNext()) {
                                PhoneAccountHandleIdentifier phoneAccountHandleIdentifier =
                                        (PhoneAccountHandleIdentifier) it3.next();
                                final String str2 = phoneAccountHandleIdentifier.mAppIdentifier;
                                if (i == phoneAccountHandleIdentifier.mAssociationId
                                        && arrayList.stream()
                                                .noneMatch(
                                                        new Predicate() { // from class:
                                                            // com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController$PhoneAccountManager$$ExternalSyntheticLambda0
                                                            @Override // java.util.function.Predicate
                                                            public final boolean test(Object obj) {
                                                                String str3 = str2;
                                                                return str3 != null
                                                                        && str3.equals(
                                                                                ((CallMetadataSyncData
                                                                                                        .CallFacilitator)
                                                                                                obj)
                                                                                        .mIdentifier);
                                                            }
                                                        })) {
                                    phoneAccountManager.mTelecomManager.unregisterPhoneAccount(
                                            (PhoneAccountHandle)
                                                    ((HashMap)
                                                                    phoneAccountManager
                                                                            .mPhoneAccountHandles)
                                                            .get(phoneAccountHandleIdentifier));
                                    it3.remove();
                                }
                            }
                            Iterator it4 = arrayList.iterator();
                            while (it4.hasNext()) {
                                CallMetadataSyncData.CallFacilitator callFacilitator =
                                        (CallMetadataSyncData.CallFacilitator) it4.next();
                                String str3 = callFacilitator.mIdentifier;
                                PhoneAccountHandleIdentifier phoneAccountHandleIdentifier2 =
                                        new PhoneAccountHandleIdentifier(i, str3);
                                if (!((HashMap) phoneAccountManager.mPhoneAccountHandles)
                                        .containsKey(phoneAccountHandleIdentifier2)) {
                                    String str4 = callFacilitator.mName;
                                    boolean z2 = callFacilitator.mIsTel;
                                    if (!((HashMap) phoneAccountManager.mPhoneAccountHandles)
                                            .containsKey(phoneAccountHandleIdentifier2)) {
                                        PhoneAccountHandle phoneAccountHandle =
                                                new PhoneAccountHandle(
                                                        phoneAccountManager
                                                                .mConnectionServiceComponentName,
                                                        UUID.randomUUID().toString());
                                        ((HashMap) phoneAccountManager.mPhoneAccountHandles)
                                                .put(
                                                        phoneAccountHandleIdentifier2,
                                                        phoneAccountHandle);
                                        phoneAccountManager.mTelecomManager.registerPhoneAccount(
                                                PhoneAccountManager.createPhoneAccount(
                                                        phoneAccountHandle, str4, str3, i, z2));
                                        phoneAccountManager.mTelecomManager.enablePhoneAccount(
                                                (PhoneAccountHandle)
                                                        ((HashMap)
                                                                        phoneAccountManager
                                                                                .mPhoneAccountHandles)
                                                                .get(phoneAccountHandleIdentifier2),
                                                true);
                                    }
                                }
                            }
                            CallManager callManager = CrossDeviceSyncController.this.mCallManager;
                            Set set =
                                    (Set)
                                            callManager.mCallIds.getOrDefault(
                                                    Integer.valueOf(i), new HashSet());
                            Set set2 =
                                    (Set)
                                            ((HashMap) processTelecomDataFromSync.mCalls)
                                                    .values().stream()
                                                            .map(
                                                                    new CrossDeviceSyncController$CallManager$$ExternalSyntheticLambda0())
                                                            .collect(Collectors.toSet());
                            if (!set.equals(set2)) {
                                for (CallMetadataSyncData.Call call :
                                        ((HashMap) processTelecomDataFromSync.mCalls).values()) {
                                    if (!set.contains(call.mId) && call.mFacilitator != null) {
                                        String str5 = call.mId;
                                        Iterator it5 = callManager.mSelfOwnedCalls.iterator();
                                        while (true) {
                                            if (it5.hasNext()) {
                                                if (str5.endsWith((String) it5.next())) {
                                                    break;
                                                }
                                            } else {
                                                callManager.mExternallyOwnedCalls.add(call.mId);
                                                Bundle bundle3 = new Bundle();
                                                bundle3.putInt(
                                                        "com.android.server.companion.datatransfer.contextsync.extra.ASSOCIATION_ID",
                                                        i);
                                                bundle3.putBoolean(
                                                        "com.android.companion.datatransfer.contextsync.extra.IS_REMOTE_ORIGIN",
                                                        true);
                                                Bundle bundle4 = new Bundle();
                                                bundle4.putString(
                                                        "com.android.companion.datatransfer.contextsync.extra.CALL_ID",
                                                        call.mId);
                                                bundle4.putString(
                                                        "com.android.server.companion.datatransfer.contextsync.extra.CALLER_ID",
                                                        call.mCallerId);
                                                bundle4.putByteArray(
                                                        "com.android.server.companion.datatransfer.contextsync.extra.APP_ICON",
                                                        call.mAppIcon);
                                                bundle4.putString(
                                                        "com.android.server.companion.datatransfer.contextsync.extra.FACILITATOR_NAME",
                                                        call.mFacilitator.mName);
                                                bundle4.putString(
                                                        "com.android.server.companion.datatransfer.contextsync.extra.FACILITATOR_ID",
                                                        call.mFacilitator.mIdentifier);
                                                bundle4.putString(
                                                        "com.android.server.companion.datatransfer.contextsync.extra.FACILITATOR_EXT_ID",
                                                        call.mFacilitator.mExtendedIdentifier);
                                                bundle4.putInt(
                                                        "com.android.server.companion.datatransfer.contextsync.extra.STATUS",
                                                        call.mStatus);
                                                bundle4.putInt(
                                                        "com.android.server.companion.datatransfer.contextsync.extra.DIRECTION",
                                                        call.mDirection);
                                                bundle4.putIntegerArrayList(
                                                        "com.android.server.companion.datatransfer.contextsync.extra.CONTROLS",
                                                        new ArrayList<>(call.mControls));
                                                bundle3.putBundle(
                                                        "com.android.server.companion.datatransfer.contextsync.extra.CALL",
                                                        bundle4);
                                                bundle3.putString(
                                                        "com.android.companion.datatransfer.contextsync.extra.CALL_ID",
                                                        call.mId);
                                                bundle3.putByteArray(
                                                        "com.android.companion.datatransfer.contextsync.extra.FACILITATOR_ICON",
                                                        call.mAppIcon);
                                                PhoneAccountHandle phoneAccountHandle2 =
                                                        (PhoneAccountHandle)
                                                                ((HashMap)
                                                                                callManager
                                                                                        .mPhoneAccountManager
                                                                                        .mPhoneAccountHandles)
                                                                        .get(
                                                                                new PhoneAccountHandleIdentifier(
                                                                                        i,
                                                                                        call.mFacilitator
                                                                                                .mIdentifier));
                                                int i2 = call.mDirection;
                                                if (i2 == 1) {
                                                    callManager.mTelecomManager.addNewIncomingCall(
                                                            phoneAccountHandle2, bundle3);
                                                } else if (i2 == 2) {
                                                    Bundle bundle5 = new Bundle();
                                                    bundle5.putParcelable(
                                                            "android.telecom.extra.OUTGOING_CALL_EXTRAS",
                                                            bundle3);
                                                    bundle5.putParcelable(
                                                            "android.telecom.extra.PHONE_ACCOUNT_HANDLE",
                                                            phoneAccountHandle2);
                                                    String str6 = call.mCallerId;
                                                    if (str6 != null) {
                                                        callManager.mTelecomManager.placeCall(
                                                                Uri.fromParts("sip", str6, null),
                                                                bundle5);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                callManager.mCallIds.put(Integer.valueOf(i), set2);
                            }
                        }
                        CrossDeviceSyncController crossDeviceSyncController2 =
                                CrossDeviceSyncController.this;
                        WeakReference weakReference =
                                crossDeviceSyncController2.mInCallServiceCallbackRef;
                        if (weakReference == null
                                && crossDeviceSyncController2.mConnectionServiceCallbackRef
                                        == null) {
                            Slog.w(
                                    "CrossDeviceSyncController",
                                    "No callback to process context sync message");
                            return;
                        }
                        CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback =
                                weakReference != null
                                        ? (CrossDeviceSyncControllerCallback) weakReference.get()
                                        : null;
                        if (crossDeviceSyncControllerCallback == null) {
                            CrossDeviceSyncController.this.mInCallServiceCallbackRef = null;
                        } else if (z) {
                            crossDeviceSyncControllerCallback.processContextSyncMessage(
                                    i, processTelecomDataFromSync);
                        }
                        WeakReference weakReference2 =
                                CrossDeviceSyncController.this.mConnectionServiceCallbackRef;
                        CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback2 =
                                weakReference2 != null
                                        ? (CrossDeviceSyncControllerCallback) weakReference2.get()
                                        : null;
                        if (crossDeviceSyncControllerCallback2 == null) {
                            CrossDeviceSyncController.this.mConnectionServiceCallbackRef = null;
                        } else {
                            if (z) {
                                return;
                            }
                            crossDeviceSyncControllerCallback2.processContextSyncMessage(
                                    i, processTelecomDataFromSync);
                        }
                    }
                });
        PhoneAccountManager phoneAccountManager = new PhoneAccountManager(context);
        this.mPhoneAccountManager = phoneAccountManager;
        this.mCallManager = new CallManager(context, phoneAccountManager);
    }

    public static byte[] createCallControlMessage(int i, String str) {
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

    public static boolean isAssociationBlocked(AssociationInfo associationInfo) {
        return (associationInfo.getSystemDataSyncFlags() & 1) != 1;
    }

    public static CallMetadataSyncData.CallControlRequest processCallControlRequestDataFromSync(
            ProtoInputStream protoInputStream) {
        CallMetadataSyncData.CallControlRequest callControlRequest =
                new CallMetadataSyncData.CallControlRequest();
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                callControlRequest.mId = protoInputStream.readString(1138166333441L);
            } else if (fieldNumber != 2) {
                Slog.e(
                        "CrossDeviceSyncController",
                        "Unhandled field in ControlAction:"
                                + ProtoUtils.currentFieldToString(protoInputStream));
            } else {
                callControlRequest.mControl = protoInputStream.readInt(1159641169922L);
            }
        }
        return callControlRequest;
    }

    public static CallMetadataSyncData.CallCreateRequest processCallCreateRequestDataFromSync(
            ProtoInputStream protoInputStream) {
        CallMetadataSyncData.CallCreateRequest callCreateRequest =
                new CallMetadataSyncData.CallCreateRequest();
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                callCreateRequest.mId = protoInputStream.readString(1138166333441L);
            } else if (fieldNumber == 2) {
                callCreateRequest.mAddress = protoInputStream.readString(1138166333442L);
            } else if (fieldNumber != 3) {
                Slog.e(
                        "CrossDeviceSyncController",
                        "Unhandled field in CreateAction:"
                                + ProtoUtils.currentFieldToString(protoInputStream));
            } else {
                long start = protoInputStream.start(1146756268035L);
                callCreateRequest.mFacilitator = processFacilitatorDataFromSync(protoInputStream);
                protoInputStream.end(start);
            }
        }
        return callCreateRequest;
    }

    public static CallMetadataSyncData.CallFacilitator processFacilitatorDataFromSync(
            ProtoInputStream protoInputStream) {
        CallMetadataSyncData.CallFacilitator callFacilitator =
                new CallMetadataSyncData.CallFacilitator();
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                callFacilitator.mName = protoInputStream.readString(1138166333441L);
            } else if (fieldNumber == 2) {
                callFacilitator.mIdentifier = protoInputStream.readString(1138166333442L);
            } else if (fieldNumber != 3) {
                Slog.e(
                        "CrossDeviceSyncController",
                        "Unhandled field in Facilitator:"
                                + ProtoUtils.currentFieldToString(protoInputStream));
            } else {
                callFacilitator.mExtendedIdentifier = protoInputStream.readString(1138166333443L);
            }
        }
        return callFacilitator;
    }

    public byte[] createCallUpdateMessage(Collection collection, int i) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        protoOutputStream.write(1120986464257L, 1);
        long start = protoOutputStream.start(1146756268036L);
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            CrossDeviceCall crossDeviceCall = (CrossDeviceCall) it.next();
            if (!crossDeviceCall.mIsCallPlacedByContextSync) {
                Set set = this.mCallManager.mExternallyOwnedCalls;
                String str = crossDeviceCall.mId;
                if (!set.contains(str)) {
                    long start2 = protoOutputStream.start(2246267895809L);
                    protoOutputStream.write(1138166333441L, str);
                    long start3 = protoOutputStream.start(1146756268034L);
                    protoOutputStream.write(
                            1138166333441L,
                            (crossDeviceCall.mIsEnterprise
                                            && ((DevicePolicyManager)
                                                            this.mContext.getSystemService(
                                                                    DevicePolicyManager.class))
                                                    .getBluetoothContactSharingDisabled(
                                                            UserHandle.of(crossDeviceCall.mUserId)))
                                    ? crossDeviceCall.getNonContactString()
                                    : TextUtils.isEmpty(crossDeviceCall.mContactDisplayName)
                                            ? crossDeviceCall.getNonContactString()
                                            : crossDeviceCall.mContactDisplayName);
                    protoOutputStream.write(1151051235330L, crossDeviceCall.mCallingAppIcon);
                    long start4 = protoOutputStream.start(1146756268035L);
                    protoOutputStream.write(1138166333441L, crossDeviceCall.mCallingAppName);
                    protoOutputStream.write(1138166333442L, crossDeviceCall.mCallingAppPackageName);
                    protoOutputStream.write(
                            1138166333443L, crossDeviceCall.mSerializedPhoneAccountHandle);
                    protoOutputStream.end(start4);
                    protoOutputStream.end(start3);
                    protoOutputStream.write(1159641169923L, crossDeviceCall.mStatus);
                    protoOutputStream.write(1159641169925L, crossDeviceCall.mDirection);
                    Iterator it2 = ((HashSet) crossDeviceCall.mControls).iterator();
                    while (it2.hasNext()) {
                        protoOutputStream.write(2259152797700L, ((Integer) it2.next()).intValue());
                    }
                    protoOutputStream.end(start2);
                }
            }
        }
        Iterator it3 = ((ArrayList) this.mCallFacilitators).iterator();
        while (it3.hasNext()) {
            CallMetadataSyncData.CallFacilitator callFacilitator =
                    (CallMetadataSyncData.CallFacilitator) it3.next();
            long start5 = protoOutputStream.start(2246267895811L);
            protoOutputStream.write(1138166333441L, callFacilitator.mName);
            protoOutputStream.write(1138166333442L, callFacilitator.mIdentifier);
            protoOutputStream.write(1138166333443L, callFacilitator.mExtendedIdentifier);
            protoOutputStream.end(start5);
        }
        protoOutputStream.end(start);
        return protoOutputStream.getBytes();
    }

    public final boolean isAssociationBlockedLocal(int i) {
        return ((HashSet) this.mBlocklist).contains(Integer.valueOf(i));
    }

    public CallMetadataSyncData.Call processCallDataFromSync(ProtoInputStream protoInputStream)
            throws IOException {
        CallMetadataSyncData.Call call = new CallMetadataSyncData.Call();
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                call.mId = protoInputStream.readString(1138166333441L);
            } else if (fieldNumber == 2) {
                long start = protoInputStream.start(1146756268034L);
                while (protoInputStream.nextField() != -1) {
                    int fieldNumber2 = protoInputStream.getFieldNumber();
                    if (fieldNumber2 == 1) {
                        call.mCallerId = protoInputStream.readString(1138166333441L);
                    } else if (fieldNumber2 == 2) {
                        call.mAppIcon = protoInputStream.readBytes(1151051235330L);
                    } else if (fieldNumber2 != 3) {
                        Slog.e(
                                "CrossDeviceSyncController",
                                "Unhandled field in Origin:"
                                        + ProtoUtils.currentFieldToString(protoInputStream));
                    } else {
                        long start2 = protoInputStream.start(1146756268035L);
                        call.mFacilitator = processFacilitatorDataFromSync(protoInputStream);
                        protoInputStream.end(start2);
                    }
                }
                protoInputStream.end(start);
            } else if (fieldNumber == 3) {
                call.mStatus = protoInputStream.readInt(1159641169923L);
            } else if (fieldNumber == 4) {
                ((HashSet) call.mControls)
                        .add(Integer.valueOf(protoInputStream.readInt(2259152797700L)));
            } else if (fieldNumber != 5) {
                Slog.e(
                        "CrossDeviceSyncController",
                        "Unhandled field in Telecom:"
                                + ProtoUtils.currentFieldToString(protoInputStream));
            } else {
                call.mDirection = protoInputStream.readInt(1159641169925L);
            }
        }
        return call;
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
                    Slog.e(
                            "CrossDeviceSyncController",
                            "Processing context sync message version " + i);
                } else if (fieldNumber != 4) {
                    Slog.e(
                            "CrossDeviceSyncController",
                            "Unhandled field in ContextSyncMessage:"
                                    + ProtoUtils.currentFieldToString(protoInputStream));
                } else if (i == 1) {
                    long start = protoInputStream.start(1146756268036L);
                    while (protoInputStream.nextField() != -1) {
                        if (protoInputStream.getFieldNumber() == 1) {
                            long start2 = protoInputStream.start(2246267895809L);
                            CallMetadataSyncData.Call processCallDataFromSync =
                                    processCallDataFromSync(protoInputStream);
                            ((HashMap) callMetadataSyncData.mCalls)
                                    .put(processCallDataFromSync.mId, processCallDataFromSync);
                            protoInputStream.end(start2);
                        } else if (protoInputStream.getFieldNumber() == 2) {
                            long start3 = protoInputStream.start(2246267895810L);
                            while (protoInputStream.nextField() != -1) {
                                int fieldNumber2 = protoInputStream.getFieldNumber();
                                if (fieldNumber2 == 1) {
                                    long start4 = protoInputStream.start(1146756268033L);
                                    ((ArrayList) callMetadataSyncData.mCallCreateRequests)
                                            .add(
                                                    processCallCreateRequestDataFromSync(
                                                            protoInputStream));
                                    protoInputStream.end(start4);
                                } else if (fieldNumber2 != 2) {
                                    Slog.e(
                                            "CrossDeviceSyncController",
                                            "Unhandled field in Request:"
                                                    + ProtoUtils.currentFieldToString(
                                                            protoInputStream));
                                } else {
                                    long start5 = protoInputStream.start(1146756268034L);
                                    ((ArrayList) callMetadataSyncData.mCallControlRequests)
                                            .add(
                                                    processCallControlRequestDataFromSync(
                                                            protoInputStream));
                                    protoInputStream.end(start5);
                                }
                            }
                            protoInputStream.end(start3);
                        } else if (protoInputStream.getFieldNumber() == 3) {
                            long start6 = protoInputStream.start(2246267895811L);
                            CallMetadataSyncData.CallFacilitator processFacilitatorDataFromSync =
                                    processFacilitatorDataFromSync(protoInputStream);
                            processFacilitatorDataFromSync.mIsTel = true;
                            ((ArrayList) callMetadataSyncData.mCallFacilitators)
                                    .add(processFacilitatorDataFromSync);
                            protoInputStream.end(start6);
                        } else {
                            Slog.e(
                                    "CrossDeviceSyncController",
                                    "Unhandled field in Telecom:"
                                            + ProtoUtils.currentFieldToString(protoInputStream));
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

    public final void syncMessageToDevice(int i, byte[] bArr) {
        if (isAssociationBlockedLocal(i)) {
            Slog.e(
                    "CrossDeviceSyncController",
                    "Cannot sync to requested device; connection is blocked");
        } else {
            this.mCompanionTransportManager.sendMessage(1667729539, bArr, new int[] {i});
        }
    }
}
