package com.android.server.location.contexthub;

import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.chre.flags.Flags;
import android.compat.Compatibility;
import android.content.Context;
import android.content.Intent;
import android.hardware.contexthub.HostEndpointInfo;
import android.hardware.contexthub.MessageDeliveryStatus;
import android.hardware.location.ContextHubInfo;
import android.hardware.location.IContextHubClient;
import android.hardware.location.IContextHubClientCallback;
import android.hardware.location.IContextHubTransactionCallback;
import android.hardware.location.NanoAppMessage;
import android.hardware.location.NanoAppState;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.WorkSource;
import android.util.Log;

import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.location.contexthub.ContextHubTransactionManager.AnonymousClass6;

import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ContextHubClientBroker extends IContextHubClient.Stub
        implements IBinder.DeathRecipient,
                AppOpsManager.OnOpChangedListener,
                PendingIntent.OnFinished {
    public final AppOpsManager mAppOpsManager;
    public final ContextHubInfo mAttachedContextHubInfo;
    public String mAttributionTag;
    public final ContextHubClientManager mClientManager;
    public final Context mContext;
    public IContextHubClientCallback mContextHubClientCallback;
    public final IContextHubWrapper mContextHubProxy;
    public final short mHostEndPointId;
    public final String mPackage;
    public final PendingIntentRequest mPendingIntentRequest;
    public final int mPid;
    public final ContextHubTransactionManager mTransactionManager;
    public final int mUid;
    public final PowerManager.WakeLock mWakeLock;
    public boolean mRegistered = true;
    public final AtomicBoolean mIsWakelockUsable = new AtomicBoolean(true);
    public final AtomicBoolean mIsPendingIntentCancelled = new AtomicBoolean(false);
    public final AtomicBoolean mIsPermQueryIssued = new AtomicBoolean(false);
    public final Map mMessageChannelNanoappIdMap = new ConcurrentHashMap();
    public final Set mForceDeniedNapps = new HashSet();
    public final Map mNappToAuthTimerMap = new ConcurrentHashMap();
    public final AnonymousClass1 mQueryPermsCallback =
            new IContextHubTransactionCallback
                    .Stub() { // from class:
                              // com.android.server.location.contexthub.ContextHubClientBroker.1
                public final void onQueryResponse(int i, List list) {
                    ContextHubClientBroker.this.mIsPermQueryIssued.set(false);
                    if (i != 0 && list != null) {
                        Log.e(
                                "ContextHubClientBroker",
                                "Permissions query failed, but still received nanoapp state");
                        return;
                    }
                    if (list != null) {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            NanoAppState nanoAppState = (NanoAppState) it.next();
                            if (((ConcurrentHashMap)
                                            ContextHubClientBroker.this.mMessageChannelNanoappIdMap)
                                    .containsKey(Long.valueOf(nanoAppState.getNanoAppId()))) {
                                ContextHubClientBroker.this.updateNanoAppAuthState(
                                        nanoAppState.getNanoAppId(),
                                        nanoAppState.getNanoAppPermissions(),
                                        false,
                                        false);
                            }
                        }
                    }
                }

                public final void onTransactionComplete(int i) {}
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface CallbackConsumer {
        void accept(IContextHubClientCallback iContextHubClientCallback);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PendingIntentRequest {
        public long mNanoAppId;
        public PendingIntent mPendingIntent;
        public boolean mValid;
    }

    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.server.location.contexthub.ContextHubClientBroker$1] */
    public ContextHubClientBroker(
            Context context,
            IContextHubWrapper iContextHubWrapper,
            ContextHubClientManager contextHubClientManager,
            ContextHubInfo contextHubInfo,
            short s,
            IContextHubClientCallback iContextHubClientCallback,
            String str,
            ContextHubTransactionManager contextHubTransactionManager,
            PendingIntent pendingIntent,
            long j,
            String str2) {
        this.mContext = context;
        this.mContextHubProxy = iContextHubWrapper;
        this.mClientManager = contextHubClientManager;
        this.mAttachedContextHubInfo = contextHubInfo;
        this.mHostEndPointId = s;
        this.mContextHubClientCallback = iContextHubClientCallback;
        if (pendingIntent == null) {
            PendingIntentRequest pendingIntentRequest = new PendingIntentRequest();
            pendingIntentRequest.mValid = false;
            this.mPendingIntentRequest = pendingIntentRequest;
        } else {
            PendingIntentRequest pendingIntentRequest2 = new PendingIntentRequest();
            pendingIntentRequest2.mPendingIntent = pendingIntent;
            pendingIntentRequest2.mNanoAppId = j;
            pendingIntentRequest2.mValid = true;
            this.mPendingIntentRequest = pendingIntentRequest2;
        }
        if (str2 == null) {
            String[] packagesForUid =
                    context.getPackageManager().getPackagesForUid(Binder.getCallingUid());
            if (packagesForUid != null && packagesForUid.length > 0) {
                str2 = packagesForUid[0];
            }
            StorageManagerService$$ExternalSyntheticOutline0.m(
                    "createClient: Provided package name null. Using first package name ",
                    str2,
                    "ContextHubClientBroker");
        }
        this.mPackage = str2;
        this.mAttributionTag = str;
        this.mTransactionManager = contextHubTransactionManager;
        this.mPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        this.mUid = callingUid;
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mAppOpsManager = appOpsManager;
        PowerManager.WakeLock newWakeLock =
                ((PowerManager) context.getSystemService(PowerManager.class))
                        .newWakeLock(1, "ContextHubClientBroker");
        this.mWakeLock = newWakeLock;
        newWakeLock.setWorkSource(new WorkSource(callingUid, str2));
        newWakeLock.setReferenceCounted(true);
        appOpsManager.startWatchingMode(-1, str2, this);
        sendHostEndpointConnectedEvent();
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        onClientExit();
    }

    public final void callbackFinished() {
        Binder.withCleanCallingIdentity(
                new ContextHubClientBroker$$ExternalSyntheticLambda0(this, 1));
    }

    public final void checkNanoappPermsAsync() {
        if (this.mIsPermQueryIssued.getAndSet(true)) {
            return;
        }
        ContextHubTransactionManager contextHubTransactionManager = this.mTransactionManager;
        int id = this.mAttachedContextHubInfo.getId();
        AnonymousClass1 anonymousClass1 = this.mQueryPermsCallback;
        this.mTransactionManager.addTransaction(
                contextHubTransactionManager
                .new AnonymousClass6(
                        contextHubTransactionManager.mNextAvailableId.getAndIncrement(),
                        this.mPackage,
                        id,
                        anonymousClass1));
    }

    public final void close() {
        synchronized (this) {
            this.mPendingIntentRequest.mPendingIntent = null;
        }
        onClientExit();
    }

    public final Intent createIntent(int i) {
        Intent intent = new Intent();
        intent.putExtra("android.hardware.location.extra.EVENT_TYPE", i);
        intent.putExtra(
                "android.hardware.location.extra.CONTEXT_HUB_INFO",
                (Parcelable) this.mAttachedContextHubInfo);
        return intent;
    }

    public final int doSendMessageToNanoApp(
            final NanoAppMessage nanoAppMessage,
            final IContextHubTransactionCallback iContextHubTransactionCallback) {
        boolean z;
        int i;
        Context context = this.mContext;
        DateTimeFormatter dateTimeFormatter = ContextHubServiceUtil.DATE_FORMATTER;
        context.enforceCallingOrSelfPermission(
                "android.permission.ACCESS_CONTEXT_HUB",
                "ACCESS_CONTEXT_HUB permission required to use Context Hub");
        nanoAppMessage.setIsReliable(false);
        nanoAppMessage.setMessageSequenceNumber(0);
        synchronized (this) {
            z = this.mRegistered;
        }
        if (!z) {
            Log.e(
                    "ContextHubClientBroker",
                    String.format(
                            "Failed to send message (connection closed): hostEndpointId= %1$d"
                                + " payload %2$s",
                            Short.valueOf(this.mHostEndPointId),
                            Base64.getEncoder().encodeToString(nanoAppMessage.getMessageBody())));
            return 1;
        }
        int intValue =
                ((Integer)
                                ((ConcurrentHashMap) this.mMessageChannelNanoappIdMap)
                                        .getOrDefault(
                                                Long.valueOf(nanoAppMessage.getNanoAppId()), -1))
                        .intValue();
        if (intValue == 0) {
            if (!Compatibility.isChangeEnabled(181350407L)) {
                return 1;
            }
            throw new SecurityException(
                    "Client doesn't have valid permissions to send message to "
                            + nanoAppMessage.getNanoAppId());
        }
        if (intValue == -1) {
            checkNanoappPermsAsync();
        }
        if (!Flags.reliableMessageImplementation() || iContextHubTransactionCallback == null) {
            try {
                i =
                        this.mContextHubProxy.sendMessageToContextHub(
                                this.mHostEndPointId,
                                this.mAttachedContextHubInfo.getId(),
                                nanoAppMessage);
            } catch (RemoteException e) {
                Log.e(
                        "ContextHubClientBroker",
                        "RemoteException in sendMessageToNanoApp (target hub ID = "
                                + this.mAttachedContextHubInfo.getId()
                                + ")",
                        e);
                i = 1;
            }
        } else {
            final ContextHubTransactionManager contextHubTransactionManager =
                    this.mTransactionManager;
            final short s = this.mHostEndPointId;
            final int id = this.mAttachedContextHubInfo.getId();
            final String str = this.mPackage;
            final int andIncrement =
                    contextHubTransactionManager.mNextAvailableId.getAndIncrement();
            final int andIncrement2 =
                    contextHubTransactionManager.mNextAvailableMessageSequenceNumber
                            .getAndIncrement();
            try {
                this.mTransactionManager.addTransaction(
                        new ContextHubServiceTransaction(
                                andIncrement, str, andIncrement2) { // from class:
                            // com.android.server.location.contexthub.ContextHubTransactionManager.5
                            @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
                            public final int onTransact() {
                                try {
                                    nanoAppMessage.setIsReliable(true);
                                    nanoAppMessage.setMessageSequenceNumber(
                                            this.mMessageSequenceNumber.intValue());
                                    return ContextHubTransactionManager.this.mContextHubProxy
                                            .sendMessageToContextHub(s, id, nanoAppMessage);
                                } catch (RemoteException e2) {
                                    Log.e(
                                            "ContextHubTransactionManager",
                                            "RemoteException while trying to send a reliable"
                                                + " message",
                                            e2);
                                    return 1;
                                }
                            }

                            @Override // com.android.server.location.contexthub.ContextHubServiceTransaction
                            public final void onTransactionComplete(int i2) {
                                try {
                                    iContextHubTransactionCallback.onTransactionComplete(i2);
                                } catch (RemoteException e2) {
                                    Log.e(
                                            "ContextHubTransactionManager",
                                            "RemoteException while calling client"
                                                + " onTransactionComplete",
                                            e2);
                                }
                            }
                        });
                i = 0;
            } catch (IllegalStateException e2) {
                Log.e(
                        "ContextHubClientBroker",
                        "Unable to add a transaction in sendMessageToNanoApp (target hub ID = "
                                + this.mAttachedContextHubInfo.getId()
                                + ")",
                        e2);
                i = 7;
            }
        }
        ContextHubEventLogger contextHubEventLogger = ContextHubEventLogger.getInstance();
        int id2 = this.mAttachedContextHubInfo.getId();
        boolean z2 = i == 0;
        synchronized (contextHubEventLogger) {
            ContextHubEventLogger.NanoappMessageEvent nanoappMessageEvent =
                    new ContextHubEventLogger.NanoappMessageEvent(
                            System.currentTimeMillis(), id2, nanoAppMessage, z2);
            if (!contextHubEventLogger.mMessageToNanoappQueue.add(nanoappMessageEvent)) {
                Log.e(
                        "ContextHubEventLogger",
                        "Unable to add message to nanoapp event to queue: " + nanoappMessageEvent);
            }
        }
        return i;
    }

    public byte doSendPendingIntent(
            PendingIntent pendingIntent, Intent intent, PendingIntent.OnFinished onFinished) {
        try {
            Binder.withCleanCallingIdentity(
                    new ContextHubClientBroker$$ExternalSyntheticLambda0(this, 2));
            pendingIntent.send(
                    this.mContext,
                    0,
                    intent,
                    onFinished,
                    null,
                    "android.permission.ACCESS_CONTEXT_HUB",
                    null);
            return (byte) 0;
        } catch (PendingIntent.CanceledException unused) {
            this.mIsPendingIntentCancelled.set(true);
            Log.w(
                    "ContextHubClientBroker",
                    "PendingIntent has been canceled, unregistering from client (host endpoint ID "
                            + ((int) this.mHostEndPointId)
                            + ")");
            close();
            return (byte) 2;
        }
    }

    public final int getId() {
        return this.mHostEndPointId;
    }

    public PowerManager.WakeLock getWakeLock() {
        PowerManager.WakeLock wakeLock;
        synchronized (this.mWakeLock) {
            wakeLock = this.mWakeLock;
        }
        return wakeLock;
    }

    public final synchronized byte invokeCallback(CallbackConsumer callbackConsumer) {
        if (this.mContextHubClientCallback != null) {
            try {
                Binder.withCleanCallingIdentity(
                        new ContextHubClientBroker$$ExternalSyntheticLambda0(this, 2));
                callbackConsumer.accept(this.mContextHubClientCallback);
            } catch (RemoteException e) {
                Log.e(
                        "ContextHubClientBroker",
                        "RemoteException while invoking client callback (host endpoint ID = "
                                + ((int) this.mHostEndPointId)
                                + ")",
                        e);
                return (byte) 2;
            }
        }
        return (byte) 0;
    }

    public boolean isWakelockUsable() {
        boolean z;
        synchronized (this.mWakeLock) {
            z = this.mIsWakelockUsable.get();
        }
        return z;
    }

    public final synchronized void onClientExit() {
        try {
            IContextHubClientCallback iContextHubClientCallback = this.mContextHubClientCallback;
            if (iContextHubClientCallback != null) {
                iContextHubClientCallback.asBinder().unlinkToDeath(this, 0);
                this.mContextHubClientCallback = null;
            }
            if (this.mPendingIntentRequest.mPendingIntent == null && this.mRegistered) {
                this.mClientManager.unregisterClient(this.mHostEndPointId);
                this.mRegistered = false;
                this.mAppOpsManager.stopWatchingMode(this);
                this.mContextHubProxy.onHostEndpointDisconnected(this.mHostEndPointId);
                Binder.withCleanCallingIdentity(
                        new ContextHubClientBroker$$ExternalSyntheticLambda0(this, 0));
            }
        } finally {
        }
    }

    @Override // android.app.AppOpsManager.OnOpChangedListener
    public final void onOpChanged(String str, String str2) {
        if (!str2.equals(this.mPackage)
                || ((ConcurrentHashMap) this.mMessageChannelNanoappIdMap).isEmpty()) {
            return;
        }
        checkNanoappPermsAsync();
    }

    @Override // android.app.PendingIntent.OnFinished
    public final void onSendFinished(
            PendingIntent pendingIntent, Intent intent, int i, String str, Bundle bundle) {
        Binder.withCleanCallingIdentity(
                new ContextHubClientBroker$$ExternalSyntheticLambda0(this, 1));
    }

    public final void reliableMessageCallbackFinished(int i, byte b) {
        sendMessageDeliveryStatusToContextHub(b, i);
        Binder.withCleanCallingIdentity(
                new ContextHubClientBroker$$ExternalSyntheticLambda0(this, 1));
    }

    public final void sendHostEndpointConnectedEvent() {
        HostEndpointInfo hostEndpointInfo = new HostEndpointInfo();
        hostEndpointInfo.hostEndpointId = (char) this.mHostEndPointId;
        hostEndpointInfo.packageName = this.mPackage;
        hostEndpointInfo.attributionTag = this.mAttributionTag;
        hostEndpointInfo.type = this.mUid == 1000 ? 1 : 2;
        this.mContextHubProxy.onHostEndpointConnected(hostEndpointInfo);
    }

    public final void sendMessageDeliveryStatusToContextHub(byte b, int i) {
        if (Flags.reliableMessageImplementation()) {
            MessageDeliveryStatus messageDeliveryStatus = new MessageDeliveryStatus();
            messageDeliveryStatus.messageSequenceNumber = i;
            messageDeliveryStatus.errorCode = b;
            if (this.mContextHubProxy.sendMessageDeliveryStatusToContextHub(
                            this.mAttachedContextHubInfo.getId(), messageDeliveryStatus)
                    != 0) {
                Log.e("ContextHubClientBroker", "Failed to send the reliable message status");
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00ba, code lost:

       if (r17.isReliable() != false) goto L34;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda7] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final byte sendMessageToClient(
            final android.hardware.location.NanoAppMessage r17,
            java.util.List r18,
            java.util.List r19) {
        /*
            r16 = this;
            r7 = r16
            r0 = r17
            long r8 = r17.getNanoAppId()
            r6 = 0
            r5 = 0
            r1 = r16
            r2 = r8
            r4 = r18
            int r1 = r1.updateNanoAppAuthState(r2, r4, r5, r6)
            r2 = 1
            r3 = 3
            java.lang.String r4 = ". "
            java.lang.String r5 = "Dropping message from "
            java.lang.String r6 = "ContextHubClientBroker"
            if (r1 != r2) goto L44
            boolean r2 = r19.isEmpty()
            if (r2 != 0) goto L44
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r5)
            java.lang.String r1 = java.lang.Long.toHexString(r8)
            r0.append(r1)
            r0.append(r4)
            java.lang.String r1 = r7.mPackage
            r0.append(r1)
            java.lang.String r1 = " in grace period and napp msg has permissions"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.e(r6, r0)
            return r3
        L44:
            if (r1 == 0) goto Lc3
            java.lang.String r1 = "NanoappMessageDelivery "
            java.lang.String r1 = com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m(r8, r1)
            java.util.Iterator r2 = r19.iterator()
        L50:
            boolean r10 = r2.hasNext()
            if (r10 == 0) goto L9a
            java.lang.Object r10 = r2.next()
            java.lang.String r10 = (java.lang.String) r10
            int r15 = android.app.AppOpsManager.permissionToOpCode(r10)
            r10 = -1
            if (r15 == r10) goto L98
            android.app.AppOpsManager r10 = r7.mAppOpsManager     // Catch: java.lang.SecurityException -> L77
            int r12 = r7.mUid     // Catch: java.lang.SecurityException -> L77
            java.lang.String r13 = r7.mPackage     // Catch: java.lang.SecurityException -> L77
            java.lang.String r14 = r7.mAttributionTag     // Catch: java.lang.SecurityException -> L77
            r11 = r15
            r3 = r15
            r15 = r1
            int r3 = r10.noteOp(r11, r12, r13, r14, r15)     // Catch: java.lang.SecurityException -> L75
            if (r3 == 0) goto L98
            goto Lc3
        L75:
            r0 = move-exception
            goto L79
        L77:
            r0 = move-exception
            r3 = r15
        L79:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "SecurityException: noteOp for pkg "
            r1.<init>(r2)
            java.lang.String r2 = r7.mPackage
            java.lang.String r10 = " opcode "
            java.lang.String r11 = ": "
            com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0.m(r3, r2, r10, r11, r1)
            java.lang.String r0 = r0.getMessage()
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            android.util.Log.e(r6, r0)
            goto Lc3
        L98:
            r3 = 3
            goto L50
        L9a:
            com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda5 r1 = new com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda5
            r1.<init>()
            byte r1 = r7.invokeCallback(r1)
            if (r1 == 0) goto La6
            return r1
        La6:
            com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda6 r1 = new com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda6
            r1.<init>()
            com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda7 r2 = new com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda7
            r2.<init>()
            boolean r3 = android.chre.flags.Flags.reliableMessageImplementation()
            if (r3 == 0) goto Lbd
            boolean r0 = r17.isReliable()
            if (r0 == 0) goto Lbd
            goto Lbe
        Lbd:
            r2 = 0
        Lbe:
            byte r0 = r7.sendPendingIntent(r1, r8, r2)
            return r0
        Lc3:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r5)
            java.lang.String r1 = java.lang.Long.toHexString(r8)
            r0.append(r1)
            r0.append(r4)
            java.lang.String r1 = r7.mPackage
            r0.append(r1)
            java.lang.String r1 = " doesn't have permission"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.e(r6, r0)
            r1 = 3
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.location.contexthub.ContextHubClientBroker.sendMessageToClient(android.hardware.location.NanoAppMessage,"
                    + " java.util.List, java.util.List):byte");
    }

    public final int sendMessageToNanoApp(NanoAppMessage nanoAppMessage) {
        return doSendMessageToNanoApp(nanoAppMessage, null);
    }

    public final synchronized byte sendPendingIntent(
            Supplier supplier,
            long j,
            final ContextHubClientBroker$$ExternalSyntheticLambda7
                    contextHubClientBroker$$ExternalSyntheticLambda7) {
        PendingIntentRequest pendingIntentRequest = this.mPendingIntentRequest;
        PendingIntent pendingIntent = pendingIntentRequest.mPendingIntent;
        if (pendingIntent == null || pendingIntentRequest.mNanoAppId != j) {
            return (byte) 0;
        }
        return doSendPendingIntent(
                pendingIntent,
                (Intent) supplier.get(),
                new PendingIntent
                        .OnFinished() { // from class:
                                        // com.android.server.location.contexthub.ContextHubClientBroker.2
                    @Override // android.app.PendingIntent.OnFinished
                    public final void onSendFinished(
                            PendingIntent pendingIntent2,
                            Intent intent,
                            int i,
                            String str,
                            Bundle bundle) {
                        Consumer consumer = contextHubClientBroker$$ExternalSyntheticLambda7;
                        if (consumer != null) {
                            consumer.accept(Byte.valueOf(i == 0 ? (byte) 0 : (byte) 1));
                        }
                        ContextHubClientBroker contextHubClientBroker = this;
                        contextHubClientBroker.getClass();
                        Binder.withCleanCallingIdentity(
                                new ContextHubClientBroker$$ExternalSyntheticLambda0(
                                        contextHubClientBroker, 1));
                    }
                });
    }

    public final int sendReliableMessageToNanoApp(
            NanoAppMessage nanoAppMessage,
            IContextHubTransactionCallback iContextHubTransactionCallback) {
        return doSendMessageToNanoApp(nanoAppMessage, iContextHubTransactionCallback);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("endpointID: ");
        sb.append((int) this.mHostEndPointId);
        sb.append(", contextHub: ");
        sb.append(this.mAttachedContextHubInfo.getId());
        sb.append(", ");
        if (this.mAttributionTag != null) {
            sb.append("attributionTag: ");
            sb.append(this.mAttributionTag);
            sb.append(", ");
        }
        if (this.mPendingIntentRequest.mValid) {
            sb.append("intentCreatorPackage: ");
            sb.append(this.mPackage);
            sb.append(", nanoAppId: 0x");
            sb.append(Long.toHexString(this.mPendingIntentRequest.mNanoAppId));
            sb.append(", ");
        } else {
            sb.append("package: ");
            sb.append(this.mPackage);
            sb.append(", ");
        }
        if (((ConcurrentHashMap) this.mMessageChannelNanoappIdMap).size() > 0) {
            sb.append("messageChannelNanoappSet: (");
            Iterator it =
                    ((ConcurrentHashMap) this.mMessageChannelNanoappIdMap).entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                sb.append("Nanoapp 0x");
                sb.append(Long.toHexString(((Long) entry.getKey()).longValue()));
                sb.append(": Auth state: ");
                int intValue = ((Integer) entry.getValue()).intValue();
                sb.append(
                        intValue != 0
                                ? intValue != 1
                                        ? intValue != 2 ? "UNKNOWN" : "GRANTED"
                                        : "DENIED_GRACE_PERIOD"
                                : "DENIED");
                if (it.hasNext()) {
                    sb.append(", ");
                }
            }
            sb.append("), ");
        }
        synchronized (this.mWakeLock) {
            sb.append("wakelock: ");
            sb.append(this.mWakeLock);
        }
        sb.append("]");
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0067, code lost:

       if (r2 == 1) goto L22;
    */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0087 A[Catch: all -> 0x0051, TryCatch #2 {all -> 0x0051, blocks: (B:4:0x0005, B:5:0x0009, B:7:0x0011, B:11:0x0026, B:13:0x0040, B:15:0x0056, B:22:0x0087, B:24:0x0097, B:27:0x009f, B:31:0x00a2, B:32:0x00a3, B:35:0x00d6, B:36:0x00e5, B:50:0x00a6, B:51:0x00ba, B:54:0x00cf, B:58:0x00d2, B:59:0x00d3, B:67:0x0079, B:53:0x00bb, B:26:0x0098), top: B:3:0x0005, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00d6 A[Catch: all -> 0x0051, TryCatch #2 {all -> 0x0051, blocks: (B:4:0x0005, B:5:0x0009, B:7:0x0011, B:11:0x0026, B:13:0x0040, B:15:0x0056, B:22:0x0087, B:24:0x0097, B:27:0x009f, B:31:0x00a2, B:32:0x00a3, B:35:0x00d6, B:36:0x00e5, B:50:0x00a6, B:51:0x00ba, B:54:0x00cf, B:58:0x00d2, B:59:0x00d3, B:67:0x0079, B:53:0x00bb, B:26:0x0098), top: B:3:0x0005, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00a4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int updateNanoAppAuthState(
            long r15, java.util.List r17, boolean r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.location.contexthub.ContextHubClientBroker.updateNanoAppAuthState(long,"
                    + " java.util.List, boolean, boolean):int");
    }
}
