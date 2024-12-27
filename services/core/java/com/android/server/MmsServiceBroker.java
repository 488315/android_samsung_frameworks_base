package com.android.server;

import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.os.UserHandle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Slog;
import com.android.internal.telephony.IMms;
import com.android.server.uri.UriGrantsManagerInternal;
import com.android.server.uri.UriGrantsManagerService;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MmsServiceBroker extends SystemService {
    public volatile AppOpsManager mAppOpsManager;
    public final AnonymousClass2 mConnection;
    public final AnonymousClass1 mConnectionHandler;
    public final Context mContext;
    public volatile IMms mService;
    public final AnonymousClass3 mServiceStubForFailure;
    public static final ComponentName MMS_SERVICE_COMPONENT = new ComponentName("com.android.mms.service", "com.android.mms.service.MmsService");
    public static final Uri FAKE_SMS_SENT_URI = Uri.parse("content://sms/sent/0");
    public static final Uri FAKE_MMS_SENT_URI = Uri.parse("content://mms/sent/0");
    public static final Uri FAKE_SMS_DRAFT_URI = Uri.parse("content://sms/draft/0");
    public static final Uri FAKE_MMS_DRAFT_URI = Uri.parse("content://mms/draft/0");

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BinderService extends IMms.Stub {
        public BinderService() {
        }

        public final Uri addMultimediaMessageDraft(String str, Uri uri) {
            return MmsServiceBroker.m71$$Nest$mgetAppOpsManager(MmsServiceBroker.this).noteOp(15, Binder.getCallingUid(), str, (String) null, (String) null) != 0 ? MmsServiceBroker.FAKE_MMS_DRAFT_URI : MmsServiceBroker.m72$$Nest$mgetServiceGuarded(MmsServiceBroker.this).addMultimediaMessageDraft(str, uri);
        }

        public final Uri addTextMessageDraft(String str, String str2, String str3) {
            return MmsServiceBroker.m71$$Nest$mgetAppOpsManager(MmsServiceBroker.this).noteOp(15, Binder.getCallingUid(), str, (String) null, (String) null) != 0 ? MmsServiceBroker.FAKE_SMS_DRAFT_URI : MmsServiceBroker.m72$$Nest$mgetServiceGuarded(MmsServiceBroker.this).addTextMessageDraft(str, str2, str3);
        }

        public final Uri adjustUriForUserAndGrantPermission(int i, int i2, Uri uri) {
            SubscriptionInfo activeSubscriptionInfo;
            Uri uri2 = uri;
            Intent intent = new Intent();
            intent.setData(uri2);
            intent.setFlags(i);
            int callingUid = Binder.getCallingUid();
            int callingUserId = UserHandle.getCallingUserId();
            if (callingUserId != 0) {
                uri2 = ContentProvider.maybeAddUserId(uri2, callingUserId);
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UriGrantsManagerInternal uriGrantsManagerInternal = (UriGrantsManagerInternal) LocalServices.getService(UriGrantsManagerInternal.class);
                UriGrantsManagerService.LocalService localService = (UriGrantsManagerService.LocalService) uriGrantsManagerInternal;
                localService.grantUriPermissionUncheckedFromIntent(((UriGrantsManagerService.LocalService) uriGrantsManagerInternal).internalCheckGrantUriPermissionFromIntent(intent, callingUid, "com.android.phone", 0, null), null);
                Intent intent2 = new Intent("android.service.carrier.CarrierMessagingService");
                TelephonyManager telephonyManager = (TelephonyManager) MmsServiceBroker.this.mContext.getSystemService("phone");
                SubscriptionManager subscriptionManager = (SubscriptionManager) MmsServiceBroker.this.mContext.getSystemService("telephony_subscription_service");
                int i3 = -1;
                if (subscriptionManager != null && (activeSubscriptionInfo = subscriptionManager.getActiveSubscriptionInfo(i2)) != null) {
                    i3 = activeSubscriptionInfo.getSimSlotIndex();
                }
                List carrierPackageNamesForIntentAndPhone = telephonyManager.getCarrierPackageNamesForIntentAndPhone(intent2, i3);
                if (carrierPackageNamesForIntentAndPhone != null && carrierPackageNamesForIntentAndPhone.size() == 1) {
                    localService.grantUriPermissionUncheckedFromIntent(((UriGrantsManagerService.LocalService) uriGrantsManagerInternal).internalCheckGrantUriPermissionFromIntent(intent, callingUid, (String) carrierPackageNamesForIntentAndPhone.get(0), 0, null), null);
                }
                return uri2;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean archiveStoredConversation(String str, long j, boolean z) {
            if (MmsServiceBroker.m71$$Nest$mgetAppOpsManager(MmsServiceBroker.this).noteOp(15, Binder.getCallingUid(), str, (String) null, (String) null) != 0) {
                return false;
            }
            return MmsServiceBroker.m72$$Nest$mgetServiceGuarded(MmsServiceBroker.this).archiveStoredConversation(str, j, z);
        }

        public final boolean deleteStoredConversation(String str, long j) {
            if (MmsServiceBroker.m71$$Nest$mgetAppOpsManager(MmsServiceBroker.this).noteOp(15, Binder.getCallingUid(), str, (String) null, (String) null) != 0) {
                return false;
            }
            return MmsServiceBroker.m72$$Nest$mgetServiceGuarded(MmsServiceBroker.this).deleteStoredConversation(str, j);
        }

        public final boolean deleteStoredMessage(String str, Uri uri) {
            if (MmsServiceBroker.m71$$Nest$mgetAppOpsManager(MmsServiceBroker.this).noteOp(15, Binder.getCallingUid(), str, (String) null, (String) null) != 0) {
                return false;
            }
            return MmsServiceBroker.m72$$Nest$mgetServiceGuarded(MmsServiceBroker.this).deleteStoredMessage(str, uri);
        }

        public final void downloadMessage(int i, String str, String str2, Uri uri, Bundle bundle, PendingIntent pendingIntent, long j, String str3) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m("downloadMessage() by ", str, "MmsServiceBroker");
            MmsServiceBroker.this.mContext.enforceCallingPermission("android.permission.RECEIVE_MMS", "Download MMS message");
            if (MmsServiceBroker.m71$$Nest$mgetAppOpsManager(MmsServiceBroker.this).noteOp(18, Binder.getCallingUid(), str, str3, (String) null) == 0) {
                MmsServiceBroker.m72$$Nest$mgetServiceGuarded(MmsServiceBroker.this).downloadMessage(i, str, str2, adjustUriForUserAndGrantPermission(3, i, uri), bundle, pendingIntent, j, str3);
            } else {
                Slog.e("MmsServiceBroker", str + " is not allowed to call downloadMessage()");
            }
        }

        public final boolean getAutoPersisting() {
            return MmsServiceBroker.m72$$Nest$mgetServiceGuarded(MmsServiceBroker.this).getAutoPersisting();
        }

        public final Uri importMultimediaMessage(String str, Uri uri, String str2, long j, boolean z, boolean z2) {
            return MmsServiceBroker.m71$$Nest$mgetAppOpsManager(MmsServiceBroker.this).noteOp(15, Binder.getCallingUid(), str, (String) null, (String) null) != 0 ? MmsServiceBroker.FAKE_MMS_SENT_URI : MmsServiceBroker.m72$$Nest$mgetServiceGuarded(MmsServiceBroker.this).importMultimediaMessage(str, uri, str2, j, z, z2);
        }

        public final Uri importTextMessage(String str, String str2, int i, String str3, long j, boolean z, boolean z2) {
            return MmsServiceBroker.m71$$Nest$mgetAppOpsManager(MmsServiceBroker.this).noteOp(15, Binder.getCallingUid(), str, (String) null, (String) null) != 0 ? MmsServiceBroker.FAKE_SMS_SENT_URI : MmsServiceBroker.m72$$Nest$mgetServiceGuarded(MmsServiceBroker.this).importTextMessage(str, str2, i, str3, j, z, z2);
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x004a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void sendMessage(int r12, java.lang.String r13, android.net.Uri r14, java.lang.String r15, android.os.Bundle r16, android.app.PendingIntent r17, long r18, java.lang.String r20) {
            /*
                r11 = this;
                r0 = r11
                r1 = r12
                r8 = r13
                java.lang.String r2 = "sendMessage() by "
                java.lang.String r9 = "MmsServiceBroker"
                com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0.m(r2, r13, r9)
                com.android.server.MmsServiceBroker r2 = com.android.server.MmsServiceBroker.this
                android.content.Context r2 = r2.mContext
                java.lang.String r3 = "android.permission.SEND_SMS"
                java.lang.String r4 = "Send MMS message"
                r2.enforceCallingPermission(r3, r4)
                com.android.server.MmsServiceBroker r2 = com.android.server.MmsServiceBroker.this
                android.content.Context r2 = r2.mContext
                android.os.UserHandle r3 = android.os.Binder.getCallingUserHandle()
                boolean r2 = com.android.internal.telephony.TelephonyPermissions.checkSubscriptionAssociatedWithUser(r2, r12, r3)
                r10 = 1
                if (r2 != 0) goto L5a
                com.android.server.MmsServiceBroker r2 = com.android.server.MmsServiceBroker.this
                r2.getClass()
                long r3 = android.os.Binder.clearCallingIdentity()
                android.content.Context r2 = r2.mContext     // Catch: java.lang.Throwable -> L42
                java.lang.Class<android.telephony.SubscriptionManager> r5 = android.telephony.SubscriptionManager.class
                java.lang.Object r2 = r2.getSystemService(r5)     // Catch: java.lang.Throwable -> L42
                android.telephony.SubscriptionManager r2 = (android.telephony.SubscriptionManager) r2     // Catch: java.lang.Throwable -> L42
                if (r2 == 0) goto L44
                boolean r2 = r2.isActiveSubscriptionId(r12)     // Catch: java.lang.Throwable -> L42
                if (r2 == 0) goto L44
                r2 = r10
                goto L45
            L42:
                r0 = move-exception
                goto L56
            L44:
                r2 = 0
            L45:
                android.os.Binder.restoreCallingIdentity(r3)
                if (r2 == 0) goto L5a
                com.android.server.MmsServiceBroker r0 = com.android.server.MmsServiceBroker.this
                android.content.Context r0 = r0.mContext
                int r2 = android.os.Binder.getCallingUid()
                com.android.internal.telephony.util.TelephonyUtils.showSwitchToManagedProfileDialogIfAppropriate(r0, r12, r2, r13)
                return
            L56:
                android.os.Binder.restoreCallingIdentity(r3)
                throw r0
            L5a:
                com.android.server.MmsServiceBroker r2 = com.android.server.MmsServiceBroker.this
                android.app.AppOpsManager r2 = com.android.server.MmsServiceBroker.m71$$Nest$mgetAppOpsManager(r2)
                int r4 = android.os.Binder.getCallingUid()
                r7 = 0
                r3 = 20
                r5 = r13
                r6 = r20
                int r2 = r2.noteOp(r3, r4, r5, r6, r7)
                if (r2 == 0) goto L85
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                r0.append(r13)
                java.lang.String r1 = " is not allowed to call sendMessage()"
                r0.append(r1)
                java.lang.String r0 = r0.toString()
                android.util.Slog.e(r9, r0)
                return
            L85:
                r2 = r14
                android.net.Uri r3 = r11.adjustUriForUserAndGrantPermission(r10, r12, r14)
                com.android.server.MmsServiceBroker r0 = com.android.server.MmsServiceBroker.this
                com.android.internal.telephony.IMms r0 = com.android.server.MmsServiceBroker.m72$$Nest$mgetServiceGuarded(r0)
                r1 = r12
                r2 = r13
                r4 = r15
                r5 = r16
                r6 = r17
                r7 = r18
                r9 = r20
                r0.sendMessage(r1, r2, r3, r4, r5, r6, r7, r9)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.MmsServiceBroker.BinderService.sendMessage(int, java.lang.String, android.net.Uri, java.lang.String, android.os.Bundle, android.app.PendingIntent, long, java.lang.String):void");
        }

        public final void sendStoredMessage(int i, String str, Uri uri, Bundle bundle, PendingIntent pendingIntent) {
            if (MmsServiceBroker.m71$$Nest$mgetAppOpsManager(MmsServiceBroker.this).noteOp(20, Binder.getCallingUid(), str, (String) null, (String) null) != 0) {
                return;
            }
            MmsServiceBroker.m72$$Nest$mgetServiceGuarded(MmsServiceBroker.this).sendStoredMessage(i, str, uri, bundle, pendingIntent);
        }

        public final void setAutoPersisting(String str, boolean z) {
            if (MmsServiceBroker.m71$$Nest$mgetAppOpsManager(MmsServiceBroker.this).noteOp(15, Binder.getCallingUid(), str, (String) null, (String) null) != 0) {
                return;
            }
            MmsServiceBroker.m72$$Nest$mgetServiceGuarded(MmsServiceBroker.this).setAutoPersisting(str, z);
        }

        public final boolean updateStoredMessageStatus(String str, Uri uri, ContentValues contentValues) {
            if (MmsServiceBroker.m71$$Nest$mgetAppOpsManager(MmsServiceBroker.this).noteOp(15, Binder.getCallingUid(), str, (String) null, (String) null) != 0) {
                return false;
            }
            return MmsServiceBroker.m72$$Nest$mgetServiceGuarded(MmsServiceBroker.this).updateStoredMessageStatus(str, uri, contentValues);
        }
    }

    /* renamed from: -$$Nest$mgetAppOpsManager, reason: not valid java name */
    public static AppOpsManager m71$$Nest$mgetAppOpsManager(MmsServiceBroker mmsServiceBroker) {
        if (mmsServiceBroker.mAppOpsManager == null) {
            mmsServiceBroker.mAppOpsManager = (AppOpsManager) mmsServiceBroker.mContext.getSystemService("appops");
        }
        return mmsServiceBroker.mAppOpsManager;
    }

    /* renamed from: -$$Nest$mgetServiceGuarded, reason: not valid java name */
    public static IMms m72$$Nest$mgetServiceGuarded(MmsServiceBroker mmsServiceBroker) {
        IMms iMms;
        synchronized (mmsServiceBroker) {
            try {
                if (mmsServiceBroker.mService == null) {
                    Slog.w("MmsServiceBroker", "MmsService not connected. Try connecting...");
                    AnonymousClass1 anonymousClass1 = mmsServiceBroker.mConnectionHandler;
                    anonymousClass1.sendMessage(anonymousClass1.obtainMessage(1));
                    long j = 4000;
                    long elapsedRealtime = SystemClock.elapsedRealtime() + 4000;
                    while (true) {
                        if (j <= 0) {
                            Slog.e("MmsServiceBroker", "Can not connect to MmsService (timed out)");
                            iMms = null;
                            break;
                        }
                        try {
                            mmsServiceBroker.wait(j);
                        } catch (InterruptedException e) {
                            Slog.w("MmsServiceBroker", "Connection wait interrupted", e);
                        }
                        if (mmsServiceBroker.mService != null) {
                            iMms = mmsServiceBroker.mService;
                        } else {
                            j = elapsedRealtime - SystemClock.elapsedRealtime();
                        }
                    }
                } else {
                    iMms = mmsServiceBroker.mService;
                }
            } finally {
            }
        }
        return iMms != null ? iMms : mmsServiceBroker.mServiceStubForFailure;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.MmsServiceBroker$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.MmsServiceBroker$2] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.MmsServiceBroker$3] */
    public MmsServiceBroker(Context context) {
        super(context);
        this.mAppOpsManager = null;
        this.mConnectionHandler = new Handler() { // from class: com.android.server.MmsServiceBroker.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 1) {
                    Slog.e("MmsServiceBroker", "Unknown message");
                    return;
                }
                MmsServiceBroker mmsServiceBroker = MmsServiceBroker.this;
                mmsServiceBroker.getClass();
                Slog.i("MmsServiceBroker", "Connecting to MmsService");
                synchronized (mmsServiceBroker) {
                    try {
                        if (mmsServiceBroker.mService != null) {
                            Slog.d("MmsServiceBroker", "Already connected");
                            return;
                        }
                        Intent intent = new Intent();
                        intent.setComponent(MmsServiceBroker.MMS_SERVICE_COMPONENT);
                        try {
                            if (!mmsServiceBroker.mContext.bindService(intent, mmsServiceBroker.mConnection, 1)) {
                                Slog.e("MmsServiceBroker", "Failed to bind to MmsService");
                            }
                        } catch (SecurityException e) {
                            Slog.e("MmsServiceBroker", "Forbidden to bind to MmsService", e);
                        }
                    } finally {
                    }
                }
            }
        };
        this.mConnection = new ServiceConnection() { // from class: com.android.server.MmsServiceBroker.2
            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Slog.i("MmsServiceBroker", "MmsService connected");
                synchronized (MmsServiceBroker.this) {
                    MmsServiceBroker.this.mService = IMms.Stub.asInterface(Binder.allowBlocking(iBinder));
                    MmsServiceBroker.this.notifyAll();
                }
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
                Slog.i("MmsServiceBroker", "MmsService unexpectedly disconnected");
                synchronized (MmsServiceBroker.this) {
                    MmsServiceBroker.this.mService = null;
                    MmsServiceBroker.this.notifyAll();
                }
                AnonymousClass1 anonymousClass1 = MmsServiceBroker.this.mConnectionHandler;
                anonymousClass1.sendMessageDelayed(anonymousClass1.obtainMessage(1), 3000L);
            }
        };
        this.mServiceStubForFailure = new IMms() { // from class: com.android.server.MmsServiceBroker.3
            public final Uri addMultimediaMessageDraft(String str, Uri uri) {
                return null;
            }

            public final Uri addTextMessageDraft(String str, String str2, String str3) {
                return null;
            }

            public final boolean archiveStoredConversation(String str, long j, boolean z) {
                return false;
            }

            public final IBinder asBinder() {
                return null;
            }

            public final boolean deleteStoredConversation(String str, long j) {
                return false;
            }

            public final boolean deleteStoredMessage(String str, Uri uri) {
                return false;
            }

            public final void downloadMessage(int i, String str, String str2, Uri uri, Bundle bundle, PendingIntent pendingIntent, long j, String str3) {
                returnPendingIntentWithError(pendingIntent);
            }

            public final boolean getAutoPersisting() {
                return false;
            }

            public final Uri importMultimediaMessage(String str, Uri uri, String str2, long j, boolean z, boolean z2) {
                return null;
            }

            public final Uri importTextMessage(String str, String str2, int i, String str3, long j, boolean z, boolean z2) {
                return null;
            }

            public final void returnPendingIntentWithError(PendingIntent pendingIntent) {
                try {
                    pendingIntent.send(MmsServiceBroker.this.mContext, 1, (Intent) null);
                } catch (PendingIntent.CanceledException e) {
                    Slog.e("MmsServiceBroker", "Failed to return pending intent result", e);
                }
            }

            public final void sendMessage(int i, String str, Uri uri, String str2, Bundle bundle, PendingIntent pendingIntent, long j, String str3) {
                returnPendingIntentWithError(pendingIntent);
            }

            public final void sendStoredMessage(int i, String str, Uri uri, Bundle bundle, PendingIntent pendingIntent) {
                returnPendingIntentWithError(pendingIntent);
            }

            public final void setAutoPersisting(String str, boolean z) {
            }

            public final boolean updateStoredMessageStatus(String str, Uri uri, ContentValues contentValues) {
                return false;
            }
        };
        this.mContext = context;
        this.mService = null;
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("imms", new BinderService());
    }
}
