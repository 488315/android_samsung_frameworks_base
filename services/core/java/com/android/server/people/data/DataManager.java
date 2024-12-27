package com.android.server.people.data;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.people.ConversationChannel;
import android.app.usage.UsageEvents;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.LocusId;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutServiceInternal;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.internal.content.PackageMonitor;
import com.android.internal.os.BackgroundThread;
import com.android.internal.telephony.SmsApplication;
import com.android.server.LocalServices;
import com.android.server.notification.NotificationManagerInternal;
import com.android.server.notification.NotificationManagerService;
import com.android.server.people.PeopleService;
import com.android.server.people.data.ConversationInfo;
import com.android.server.usage.UsageStatsService;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import libcore.util.EmptyArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DataManager {
    static final int MAX_CACHED_RECENT_SHORTCUTS = 30;
    public final SparseArray mBroadcastReceivers;
    public CallLogContentObserver mCallLogContentObserver;
    public final SparseArray mContactsContentObservers;
    public final Context mContext;
    public final List mConversationsListeners;
    public final Handler mHandler;
    public final Injector mInjector;
    public final Object mLock;
    public MmsSmsContentObserver mMmsSmsContentObserver;
    public final SparseArray mNotificationListeners;
    public NotificationManagerInternal mNotificationManagerInternal;
    public PackageManagerInternal mPackageManagerInternal;
    public final SparseArray mPackageMonitors;
    public final ScheduledExecutorService mScheduledExecutor;
    public ShortcutServiceInternal mShortcutServiceInternal;
    public ConversationStatusExpirationBroadcastReceiver mStatusExpReceiver;
    public final SparseArray mUsageStatsQueryFutures;
    public final SparseArray mUserDataArray;
    public UserManager mUserManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CallLogContentObserver extends ContentObserver implements BiConsumer {
        public final CallLogQueryHelper mCallLogQueryHelper;
        public long mLastCallTimestamp;

        public CallLogContentObserver(Handler handler) {
            super(handler);
            Context context = DataManager.this.mContext;
            DataManager.this.mInjector.getClass();
            this.mCallLogQueryHelper = new CallLogQueryHelper(context, this);
            this.mLastCallTimestamp = System.currentTimeMillis() - 300000;
        }

        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            DataManager.m750$$Nest$mforAllUnlockedUsers(DataManager.this, new DataManager$MmsSmsContentObserver$$ExternalSyntheticLambda0((String) obj, (Event) obj2, 1));
        }

        /* JADX WARN: Removed duplicated region for block: B:29:0x00ba A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:38:? A[Catch: SecurityException -> 0x003e, SYNTHETIC, TRY_LEAVE, TryCatch #3 {SecurityException -> 0x003e, blocks: (B:3:0x0023, B:53:0x003a, B:37:0x00c3, B:36:0x00c0, B:41:0x00b3, B:30:0x00ba), top: B:2:0x0023, inners: #4 }] */
        /* JADX WARN: Removed duplicated region for block: B:44:0x00d9  */
        /* JADX WARN: Removed duplicated region for block: B:46:? A[RETURN, SYNTHETIC] */
        @Override // android.database.ContentObserver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onChange(boolean r18) {
            /*
                r17 = this;
                r1 = r17
                com.android.server.people.data.CallLogQueryHelper r0 = r1.mCallLogQueryHelper
                long r2 = r1.mLastCallTimestamp
                r0.getClass()
                java.lang.String r4 = "CallLogQueryHelper"
                java.lang.String r5 = "normalized_number"
                java.lang.String r6 = "date"
                java.lang.String r7 = "duration"
                java.lang.String r8 = "type"
                java.lang.String[] r11 = new java.lang.String[]{r5, r6, r7, r8}
                java.lang.String r12 = "date > ?"
                java.lang.String r2 = java.lang.Long.toString(r2)
                java.lang.String[] r13 = new java.lang.String[]{r2}
                android.content.Context r3 = r0.mContext     // Catch: java.lang.SecurityException -> L3e
                android.content.ContentResolver r9 = r3.getContentResolver()     // Catch: java.lang.SecurityException -> L3e
                android.net.Uri r10 = android.provider.CallLog.Calls.CONTENT_URI     // Catch: java.lang.SecurityException -> L3e
                java.lang.String r14 = "date DESC"
                android.database.Cursor r3 = r9.query(r10, r11, r12, r13, r14)     // Catch: java.lang.SecurityException -> L3e
                if (r3 != 0) goto L4a
                java.lang.String r0 = "Cursor is null when querying call log."
                android.util.Slog.w(r4, r0)     // Catch: java.lang.Throwable -> L44
                if (r3 == 0) goto L41
                r3.close()     // Catch: java.lang.SecurityException -> L3e
                goto L41
            L3e:
                r0 = move-exception
                goto Lc4
            L41:
                r2 = 0
                goto Ld7
            L44:
                r0 = move-exception
                r2 = r0
                r16 = r3
                goto Lb8
            L4a:
                r9 = 0
            L4b:
                boolean r10 = r3.moveToNext()     // Catch: java.lang.Throwable -> Lad
                if (r10 == 0) goto Lb1
                int r10 = r3.getColumnIndex(r5)     // Catch: java.lang.Throwable -> Lad
                java.lang.String r10 = r3.getString(r10)     // Catch: java.lang.Throwable -> Lad
                int r11 = r3.getColumnIndex(r6)     // Catch: java.lang.Throwable -> Lad
                long r11 = r3.getLong(r11)     // Catch: java.lang.Throwable -> Lad
                int r13 = r3.getColumnIndex(r7)     // Catch: java.lang.Throwable -> Lad
                long r13 = r3.getLong(r13)     // Catch: java.lang.Throwable -> Lad
                int r15 = r3.getColumnIndex(r8)     // Catch: java.lang.Throwable -> Lad
                int r15 = r3.getInt(r15)     // Catch: java.lang.Throwable -> Lad
                r16 = r3
                long r2 = r0.mLastCallTimestamp     // Catch: java.lang.Throwable -> Lab
                long r2 = java.lang.Math.max(r2, r11)     // Catch: java.lang.Throwable -> Lab
                r0.mLastCallTimestamp = r2     // Catch: java.lang.Throwable -> Lab
                boolean r2 = android.text.TextUtils.isEmpty(r10)     // Catch: java.lang.Throwable -> Lab
                if (r2 != 0) goto La6
                r2 = 0
                int r2 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
                if (r2 <= 0) goto La6
                android.util.SparseIntArray r2 = com.android.server.people.data.CallLogQueryHelper.CALL_TYPE_TO_EVENT_TYPE     // Catch: java.lang.Throwable -> Lab
                int r3 = r2.indexOfKey(r15)     // Catch: java.lang.Throwable -> Lab
                if (r3 < 0) goto La6
                int r2 = r2.get(r15)     // Catch: java.lang.Throwable -> Lab
                com.android.server.people.data.Event$Builder r3 = new com.android.server.people.data.Event$Builder     // Catch: java.lang.Throwable -> Lab
                r3.<init>(r11, r2)     // Catch: java.lang.Throwable -> Lab
                int r2 = (int) r13     // Catch: java.lang.Throwable -> Lab
                r3.mDurationSeconds = r2     // Catch: java.lang.Throwable -> Lab
                com.android.server.people.data.Event r2 = new com.android.server.people.data.Event     // Catch: java.lang.Throwable -> Lab
                r2.<init>(r3)     // Catch: java.lang.Throwable -> Lab
                java.util.function.BiConsumer r3 = r0.mEventConsumer     // Catch: java.lang.Throwable -> Lab
                r3.accept(r10, r2)     // Catch: java.lang.Throwable -> Lab
                r9 = 1
            La6:
                r3 = r16
                goto L4b
            La9:
                r2 = r0
                goto Lb8
            Lab:
                r0 = move-exception
                goto La9
            Lad:
                r0 = move-exception
                r16 = r3
                goto La9
            Lb1:
                r16 = r3
                r16.close()     // Catch: java.lang.SecurityException -> L3e
                r2 = r9
                goto Ld7
            Lb8:
                if (r16 == 0) goto Lc3
                r16.close()     // Catch: java.lang.Throwable -> Lbe
                goto Lc3
            Lbe:
                r0 = move-exception
                r3 = r0
                r2.addSuppressed(r3)     // Catch: java.lang.SecurityException -> L3e
            Lc3:
                throw r2     // Catch: java.lang.SecurityException -> L3e
            Lc4:
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                java.lang.String r3 = "Query call log failed: "
                r2.<init>(r3)
                r2.append(r0)
                java.lang.String r0 = r2.toString()
                android.util.Slog.e(r4, r0)
                goto L41
            Ld7:
                if (r2 == 0) goto Ldf
                com.android.server.people.data.CallLogQueryHelper r0 = r1.mCallLogQueryHelper
                long r2 = r0.mLastCallTimestamp
                r1.mLastCallTimestamp = r2
            Ldf:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.people.data.DataManager.CallLogContentObserver.onChange(boolean):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ContactsContentObserver extends ContentObserver {
        public long mLastUpdatedTimestamp;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class ConversationSelector {
            public ConversationInfo mConversationInfo;
            public ConversationStore mConversationStore;
            public String mPackageName;
        }

        public ContactsContentObserver(Handler handler) {
            super(handler);
            this.mLastUpdatedTimestamp = System.currentTimeMillis();
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            DataManager dataManager = DataManager.this;
            Injector injector = dataManager.mInjector;
            Context context = dataManager.mContext;
            injector.getClass();
            ContactsQueryHelper contactsQueryHelper = new ContactsQueryHelper(context);
            if (contactsQueryHelper.queryContact(ContactsContract.Contacts.CONTENT_URI, new String[]{KnoxCustomManagerService.ID, "lookup", "starred", "has_phone_number", "contact_last_updated_timestamp"}, "contact_last_updated_timestamp > ?", new String[]{Long.toString(this.mLastUpdatedTimestamp)})) {
                Uri uri2 = contactsQueryHelper.mContactUri;
                ConversationSelector conversationSelector = new ConversationSelector();
                conversationSelector.mConversationStore = null;
                conversationSelector.mConversationInfo = null;
                conversationSelector.mPackageName = null;
                UserData unlockedUserData = DataManager.this.getUnlockedUserData(i);
                if (unlockedUserData == null) {
                    return;
                }
                unlockedUserData.forAllPackages(new DataManager$$ExternalSyntheticLambda12(1, uri2, conversationSelector));
                ConversationInfo conversationInfo = conversationSelector.mConversationInfo;
                if (conversationInfo == null) {
                    return;
                }
                new HashMap();
                String str = conversationInfo.mShortcutId;
                LocusId locusId = conversationInfo.mLocusId;
                Uri uri3 = conversationInfo.mContactUri;
                String str2 = conversationInfo.mNotificationChannelId;
                String str3 = conversationInfo.mParentNotificationChannelId;
                long j = conversationInfo.mLastEventTimestamp;
                long j2 = conversationInfo.mCreationTimestamp;
                int i2 = conversationInfo.mShortcutFlags;
                int i3 = conversationInfo.mConversationFlags;
                Map map = conversationInfo.mCurrStatuses;
                int i4 = contactsQueryHelper.mIsStarred ? i3 | 32 : (~32) & i3;
                String str4 = contactsQueryHelper.mPhoneNumber;
                DataManager dataManager2 = DataManager.this;
                ConversationStore conversationStore = conversationSelector.mConversationStore;
                Objects.requireNonNull(str);
                ConversationInfo conversationInfo2 = new ConversationInfo();
                conversationInfo2.mShortcutId = str;
                conversationInfo2.mLocusId = locusId;
                conversationInfo2.mContactUri = uri3;
                conversationInfo2.mContactPhoneNumber = str4;
                conversationInfo2.mNotificationChannelId = str2;
                conversationInfo2.mParentNotificationChannelId = str3;
                conversationInfo2.mLastEventTimestamp = j;
                conversationInfo2.mCreationTimestamp = j2;
                conversationInfo2.mShortcutFlags = i2;
                conversationInfo2.mConversationFlags = i4;
                conversationInfo2.mCurrStatuses = map;
                dataManager2.updateConversationStoreThenNotifyListeners(conversationStore, conversationInfo2, conversationSelector.mPackageName, i);
                this.mLastUpdatedTimestamp = contactsQueryHelper.mLastUpdatedTimestamp;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MmsSmsContentObserver extends ContentObserver implements BiConsumer {
        public long mLastMmsTimestamp;
        public long mLastSmsTimestamp;
        public final MmsQueryHelper mMmsQueryHelper;
        public final SmsQueryHelper mSmsQueryHelper;

        public MmsSmsContentObserver(Handler handler) {
            super(handler);
            Context context = DataManager.this.mContext;
            Injector injector = DataManager.this.mInjector;
            injector.getClass();
            this.mMmsQueryHelper = new MmsQueryHelper(context, this);
            Context context2 = DataManager.this.mContext;
            injector.getClass();
            this.mSmsQueryHelper = new SmsQueryHelper(context2, this);
            long currentTimeMillis = System.currentTimeMillis() - 300000;
            this.mLastMmsTimestamp = currentTimeMillis;
            this.mLastSmsTimestamp = currentTimeMillis;
        }

        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            DataManager.m750$$Nest$mforAllUnlockedUsers(DataManager.this, new DataManager$MmsSmsContentObserver$$ExternalSyntheticLambda0((String) obj, (Event) obj2, 0));
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            boolean z2;
            boolean z3;
            MmsQueryHelper mmsQueryHelper = this.mMmsQueryHelper;
            long j = this.mLastMmsTimestamp;
            mmsQueryHelper.getClass();
            String str = KnoxCustomManagerService.ID;
            String[] strArr = {KnoxCustomManagerService.ID, "date", "msg_box"};
            long j2 = 1000;
            String[] strArr2 = {Long.toString(j / 1000)};
            Binder.allowBlockingForCurrentThread();
            try {
                Cursor query = mmsQueryHelper.mContext.getContentResolver().query(Telephony.Mms.CONTENT_URI, strArr, "date > ?", strArr2, null);
                try {
                    if (query == null) {
                        Slog.w("MmsQueryHelper", "Cursor is null when querying MMS table.");
                        if (query != null) {
                            query.close();
                        }
                        Binder.defaultBlockingForCurrentThread();
                        z2 = false;
                    } else {
                        boolean z4 = false;
                        while (query.moveToNext()) {
                            String string = query.getString(query.getColumnIndex(KnoxCustomManagerService.ID));
                            boolean z5 = z4;
                            long j3 = query.getLong(query.getColumnIndex("date")) * j2;
                            int i = query.getInt(query.getColumnIndex("msg_box"));
                            mmsQueryHelper.mLastMessageTimestamp = Math.max(mmsQueryHelper.mLastMessageTimestamp, j3);
                            String mmsAddress = mmsQueryHelper.getMmsAddress(i, string);
                            if (mmsAddress != null && !TextUtils.isEmpty(mmsAddress) && j3 > 0) {
                                SparseIntArray sparseIntArray = MmsQueryHelper.MSG_BOX_TO_EVENT_TYPE;
                                if (sparseIntArray.indexOfKey(i) >= 0) {
                                    mmsQueryHelper.mEventConsumer.accept(mmsAddress, new Event(j3, sparseIntArray.get(i)));
                                    z4 = true;
                                    j2 = 1000;
                                }
                            }
                            z4 = z5;
                            j2 = 1000;
                        }
                        boolean z6 = z4;
                        query.close();
                        Binder.defaultBlockingForCurrentThread();
                        z2 = z6;
                    }
                    if (z2) {
                        this.mLastMmsTimestamp = this.mMmsQueryHelper.mLastMessageTimestamp;
                    }
                    SmsQueryHelper smsQueryHelper = this.mSmsQueryHelper;
                    long j4 = this.mLastSmsTimestamp;
                    smsQueryHelper.getClass();
                    String[] strArr3 = {KnoxCustomManagerService.ID, "date", "type", "address"};
                    String[] strArr4 = {Long.toString(j4)};
                    Binder.allowBlockingForCurrentThread();
                    try {
                        query = smsQueryHelper.mContext.getContentResolver().query(Telephony.Sms.CONTENT_URI, strArr3, "date > ?", strArr4, null);
                        try {
                            if (query == null) {
                                Slog.w("SmsQueryHelper", "Cursor is null when querying SMS table.");
                                if (query != null) {
                                    query.close();
                                }
                                Binder.defaultBlockingForCurrentThread();
                                z3 = false;
                            } else {
                                boolean z7 = false;
                                while (query.moveToNext()) {
                                    query.getString(query.getColumnIndex(str));
                                    long j5 = query.getLong(query.getColumnIndex("date"));
                                    int i2 = query.getInt(query.getColumnIndex("type"));
                                    String formatNumberToE164 = PhoneNumberUtils.formatNumberToE164(query.getString(query.getColumnIndex("address")), smsQueryHelper.mCurrentCountryIso);
                                    String str2 = str;
                                    smsQueryHelper.mLastMessageTimestamp = Math.max(smsQueryHelper.mLastMessageTimestamp, j5);
                                    if (formatNumberToE164 != null && !TextUtils.isEmpty(formatNumberToE164) && j5 > 0) {
                                        SparseIntArray sparseIntArray2 = SmsQueryHelper.SMS_TYPE_TO_EVENT_TYPE;
                                        if (sparseIntArray2.indexOfKey(i2) >= 0) {
                                            smsQueryHelper.mEventConsumer.accept(formatNumberToE164, new Event(j5, sparseIntArray2.get(i2)));
                                            z7 = true;
                                        }
                                    }
                                    str = str2;
                                }
                                query.close();
                                Binder.defaultBlockingForCurrentThread();
                                z3 = z7;
                            }
                            if (z3) {
                                this.mLastSmsTimestamp = this.mSmsQueryHelper.mLastMessageTimestamp;
                            }
                        } finally {
                        }
                    } finally {
                    }
                } finally {
                    if (query == null) {
                        throw th;
                    }
                    try {
                        query.close();
                        throw th;
                    } catch (Throwable th) {
                        th.addSuppressed(th);
                    }
                }
            } finally {
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NotificationListener extends NotificationListenerService {
        public final Map mActiveNotifKeys = new ArrayMap();
        public final int mUserId;

        public NotificationListener(int i) {
            this.mUserId = i;
        }

        @Override // android.service.notification.NotificationListenerService
        public final void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
            ConversationStore conversationStore;
            ConversationInfo conversation;
            if (userHandle.getIdentifier() != this.mUserId) {
                return;
            }
            PackageData packageData = DataManager.this.getPackage(userHandle.getIdentifier(), str);
            String conversationId = notificationChannel.getConversationId();
            if (packageData == null || conversationId == null || (conversation = (conversationStore = packageData.mConversationStore).getConversation(conversationId)) == null) {
                return;
            }
            ConversationInfo.Builder builder = new ConversationInfo.Builder(conversation);
            if (i == 1 || i == 2) {
                builder.mNotificationChannelId = notificationChannel.getId();
                builder.setConversationFlag(1, notificationChannel.isImportantConversation());
                builder.setConversationFlag(64, notificationChannel.isDemoted());
                builder.setConversationFlag(2, notificationChannel.getImportance() <= 2);
                builder.setConversationFlag(4, notificationChannel.canBubble());
            } else if (i == 3) {
                builder.mNotificationChannelId = null;
                builder.setConversationFlag(1, false);
                builder.setConversationFlag(64, false);
                builder.setConversationFlag(2, false);
                builder.setConversationFlag(4, false);
            }
            DataManager.this.updateConversationStoreThenNotifyListeners(conversationStore, builder.build(), str, packageData.mUserId);
        }

        @Override // android.service.notification.NotificationListenerService
        public final void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
            if (statusBarNotification.getUser().getIdentifier() != this.mUserId) {
                return;
            }
            String shortcutId = statusBarNotification.getNotification().getShortcutId();
            PackageData m751$$Nest$mgetPackageIfConversationExists = DataManager.m751$$Nest$mgetPackageIfConversationExists(DataManager.this, statusBarNotification, new DataManager$NotificationListener$$ExternalSyntheticLambda0(this, statusBarNotification, shortcutId, 1));
            if (m751$$Nest$mgetPackageIfConversationExists != null) {
                NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
                rankingMap.getRanking(statusBarNotification.getKey(), ranking);
                ConversationStore conversationStore = m751$$Nest$mgetPackageIfConversationExists.mConversationStore;
                ConversationInfo conversation = conversationStore.getConversation(shortcutId);
                if (conversation == null) {
                    return;
                }
                new HashMap();
                LocusId locusId = conversation.mLocusId;
                Uri uri = conversation.mContactUri;
                Map map = conversation.mCurrStatuses;
                long postTime = statusBarNotification.getPostTime();
                String id = ranking.getChannel().getId();
                String parentChannelId = !TextUtils.isEmpty(ranking.getChannel().getParentChannelId()) ? ranking.getChannel().getParentChannelId() : statusBarNotification.getNotification().getChannelId();
                String str = conversation.mShortcutId;
                Objects.requireNonNull(str);
                ConversationInfo conversationInfo = new ConversationInfo();
                conversationInfo.mShortcutId = str;
                conversationInfo.mLocusId = locusId;
                conversationInfo.mContactUri = uri;
                conversationInfo.mContactPhoneNumber = conversation.mContactPhoneNumber;
                conversationInfo.mNotificationChannelId = id;
                conversationInfo.mParentNotificationChannelId = parentChannelId;
                conversationInfo.mLastEventTimestamp = postTime;
                conversationInfo.mCreationTimestamp = conversation.mCreationTimestamp;
                conversationInfo.mShortcutFlags = conversation.mShortcutFlags;
                conversationInfo.mConversationFlags = conversation.mConversationFlags;
                conversationInfo.mCurrStatuses = map;
                conversationStore.updateConversationsInMemory(conversationInfo);
                conversationStore.scheduleUpdateConversationsOnDisk();
                m751$$Nest$mgetPackageIfConversationExists.mEventStore.getOrCreateEventHistory(0, shortcutId).addEvent(new Event(statusBarNotification.getPostTime(), 2));
            }
        }

        @Override // android.service.notification.NotificationListenerService
        public final synchronized void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
            if (statusBarNotification.getUser().getIdentifier() != this.mUserId) {
                return;
            }
            String shortcutId = statusBarNotification.getNotification().getShortcutId();
            PackageData m751$$Nest$mgetPackageIfConversationExists = DataManager.m751$$Nest$mgetPackageIfConversationExists(DataManager.this, statusBarNotification, new DataManager$NotificationListener$$ExternalSyntheticLambda0(this, statusBarNotification, shortcutId, 0));
            if (i == 1 && m751$$Nest$mgetPackageIfConversationExists != null) {
                long currentTimeMillis = System.currentTimeMillis();
                ConversationInfo conversation = m751$$Nest$mgetPackageIfConversationExists.mConversationStore.getConversation(shortcutId);
                if (conversation == null) {
                    return;
                }
                new HashMap();
                String str = conversation.mShortcutId;
                LocusId locusId = conversation.mLocusId;
                Uri uri = conversation.mContactUri;
                String str2 = conversation.mContactPhoneNumber;
                String str3 = conversation.mNotificationChannelId;
                String str4 = conversation.mParentNotificationChannelId;
                long j = conversation.mCreationTimestamp;
                int i2 = conversation.mShortcutFlags;
                int i3 = conversation.mConversationFlags;
                Map map = conversation.mCurrStatuses;
                Objects.requireNonNull(str);
                ConversationInfo conversationInfo = new ConversationInfo();
                conversationInfo.mShortcutId = str;
                conversationInfo.mLocusId = locusId;
                conversationInfo.mContactUri = uri;
                conversationInfo.mContactPhoneNumber = str2;
                conversationInfo.mNotificationChannelId = str3;
                conversationInfo.mParentNotificationChannelId = str4;
                conversationInfo.mLastEventTimestamp = currentTimeMillis;
                conversationInfo.mCreationTimestamp = j;
                conversationInfo.mShortcutFlags = i2;
                conversationInfo.mConversationFlags = i3;
                conversationInfo.mCurrStatuses = map;
                ConversationStore conversationStore = m751$$Nest$mgetPackageIfConversationExists.mConversationStore;
                conversationStore.updateConversationsInMemory(conversationInfo);
                conversationStore.scheduleUpdateConversationsOnDisk();
                m751$$Nest$mgetPackageIfConversationExists.mEventStore.getOrCreateEventHistory(0, shortcutId).addEvent(new Event(currentTimeMillis, 3));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PerUserBroadcastReceiver extends BroadcastReceiver {
        public final int mUserId;

        public PerUserBroadcastReceiver(int i) {
            this.mUserId = i;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            UserData unlockedUserData = DataManager.this.getUnlockedUserData(this.mUserId);
            if (unlockedUserData == null) {
                return;
            }
            if ("android.telecom.action.DEFAULT_DIALER_CHANGED".equals(intent.getAction())) {
                unlockedUserData.mDefaultDialer = intent.getStringExtra("android.telecom.extra.CHANGE_DEFAULT_DIALER_PACKAGE_NAME");
            } else if ("android.provider.action.DEFAULT_SMS_PACKAGE_CHANGED_INTERNAL".equals(intent.getAction())) {
                ComponentName defaultSmsApplicationAsUser = SmsApplication.getDefaultSmsApplicationAsUser(DataManager.this.mContext, false, UserHandle.of(unlockedUserData.mUserId));
                unlockedUserData.mDefaultSmsApp = defaultSmsApplicationAsUser != null ? defaultSmsApplicationAsUser.getPackageName() : null;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PerUserPackageMonitor extends PackageMonitor {
        public PerUserPackageMonitor() {
        }

        public final void onPackageRemoved(String str, int i) {
            super.onPackageRemoved(str, i);
            UserData unlockedUserData = DataManager.this.getUnlockedUserData(getChangingUserId());
            if (unlockedUserData != null) {
                unlockedUserData.deletePackageData(str);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ShortcutServiceCallback implements LauncherApps.ShortcutChangeCallback {
        public ShortcutServiceCallback() {
        }

        public final void onShortcutsAddedOrUpdated(String str, List list, UserHandle userHandle) {
            DataManager.this.mInjector.getClass();
            BackgroundThread.getExecutor().execute(new DataManager$ShortcutServiceCallback$$ExternalSyntheticLambda0(this, str, userHandle, list));
        }

        public final void onShortcutsRemoved(String str, List list, UserHandle userHandle) {
            DataManager.this.mInjector.getClass();
            BackgroundThread.getExecutor().execute(new DataManager$ShortcutServiceCallback$$ExternalSyntheticLambda0(this, list, str, userHandle));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ShutdownBroadcastReceiver extends BroadcastReceiver {
        public ShutdownBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            DataManager.m750$$Nest$mforAllUnlockedUsers(DataManager.this, new DataManager$ShutdownBroadcastReceiver$$ExternalSyntheticLambda0(0));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UsageStatsQueryRunnable implements Runnable {
        public long mLastEventTimestamp;
        public final UsageStatsQueryHelper mUsageStatsQueryHelper;

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.people.data.DataManager$UsageStatsQueryRunnable$$ExternalSyntheticLambda0] */
        public UsageStatsQueryRunnable(final int i) {
            ?? r0 = new Function() { // from class: com.android.server.people.data.DataManager$UsageStatsQueryRunnable$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return DataManager.this.getPackage(i, (String) obj);
                }
            };
            DataManager.this.mInjector.getClass();
            this.mUsageStatsQueryHelper = new UsageStatsQueryHelper(i, r0, this);
            this.mLastEventTimestamp = System.currentTimeMillis() - 300000;
        }

        public final void onEvent(PackageData packageData, ConversationInfo conversationInfo, Event event) {
            if (event.mType == 13) {
                new HashMap();
                String str = conversationInfo.mShortcutId;
                LocusId locusId = conversationInfo.mLocusId;
                Uri uri = conversationInfo.mContactUri;
                Map map = conversationInfo.mCurrStatuses;
                Objects.requireNonNull(str);
                ConversationInfo conversationInfo2 = new ConversationInfo();
                conversationInfo2.mShortcutId = str;
                conversationInfo2.mLocusId = locusId;
                conversationInfo2.mContactUri = uri;
                conversationInfo2.mContactPhoneNumber = conversationInfo.mContactPhoneNumber;
                conversationInfo2.mNotificationChannelId = conversationInfo.mNotificationChannelId;
                conversationInfo2.mParentNotificationChannelId = conversationInfo.mParentNotificationChannelId;
                conversationInfo2.mLastEventTimestamp = event.mTimestamp;
                conversationInfo2.mCreationTimestamp = conversationInfo.mCreationTimestamp;
                conversationInfo2.mShortcutFlags = conversationInfo.mShortcutFlags;
                conversationInfo2.mConversationFlags = conversationInfo.mConversationFlags;
                conversationInfo2.mCurrStatuses = map;
                DataManager.this.updateConversationStoreThenNotifyListeners(packageData.mConversationStore, conversationInfo2, packageData.mPackageName, packageData.mUserId);
            }
        }

        @Override // java.lang.Runnable
        public final void run() {
            UsageStatsQueryHelper usageStatsQueryHelper = this.mUsageStatsQueryHelper;
            long j = this.mLastEventTimestamp;
            usageStatsQueryHelper.getClass();
            long currentTimeMillis = System.currentTimeMillis();
            UsageStatsService usageStatsService = UsageStatsService.this;
            usageStatsService.getClass();
            UsageEvents queryEventsWithQueryFilters = usageStatsService.queryEventsWithQueryFilters(usageStatsQueryHelper.mUserId, j, currentTimeMillis, 0, EmptyArray.INT, null);
            boolean z = false;
            if (queryEventsWithQueryFilters != null) {
                boolean z2 = false;
                while (queryEventsWithQueryFilters.hasNextEvent()) {
                    UsageEvents.Event event = new UsageEvents.Event();
                    queryEventsWithQueryFilters.getNextEvent(event);
                    usageStatsQueryHelper.mLastEventTimestamp = Math.max(usageStatsQueryHelper.mLastEventTimestamp, event.getTimeStamp());
                    String packageName = event.getPackageName();
                    PackageData packageData = (PackageData) usageStatsQueryHelper.mPackageDataGetter.apply(packageName);
                    if (packageData != null) {
                        int eventType = event.getEventType();
                        if (eventType != 2) {
                            ConversationStore conversationStore = packageData.mConversationStore;
                            if (eventType == 8) {
                                String shortcutId = event.getShortcutId();
                                Event event2 = new Event(event.getTimeStamp(), 1);
                                ConversationInfo conversation = conversationStore.getConversation(shortcutId);
                                if (conversation != null) {
                                    packageData.mEventStore.getOrCreateEventHistory(0, shortcutId).addEvent(event2);
                                    usageStatsQueryHelper.mEventListener.onEvent(packageData, conversation, event2);
                                }
                            } else if (eventType == 30) {
                                usageStatsQueryHelper.onInAppConversationEnded(packageData, event);
                                LocusId locusId = event.getLocusId() != null ? new LocusId(event.getLocusId()) : null;
                                if (locusId != null && conversationStore.getConversationByLocusId(locusId) != null) {
                                    ((ArrayMap) usageStatsQueryHelper.mConvoStartEvents).put(new ComponentName(packageName, event.getClassName()), event);
                                }
                            } else if (eventType != 23 && eventType != 24) {
                            }
                        }
                        usageStatsQueryHelper.onInAppConversationEnded(packageData, event);
                    }
                    z2 = true;
                }
                z = z2;
            }
            if (z) {
                this.mLastEventTimestamp = this.mUsageStatsQueryHelper.mLastEventTimestamp;
            }
        }
    }

    /* renamed from: -$$Nest$mforAllUnlockedUsers, reason: not valid java name */
    public static void m750$$Nest$mforAllUnlockedUsers(DataManager dataManager, Consumer consumer) {
        for (int i = 0; i < dataManager.mUserDataArray.size(); i++) {
            UserData userData = (UserData) dataManager.mUserDataArray.get(dataManager.mUserDataArray.keyAt(i));
            if (userData.mIsUnlocked) {
                consumer.accept(userData);
            }
        }
    }

    /* renamed from: -$$Nest$mgetPackageIfConversationExists, reason: not valid java name */
    public static PackageData m751$$Nest$mgetPackageIfConversationExists(DataManager dataManager, StatusBarNotification statusBarNotification, Consumer consumer) {
        ConversationInfo conversation;
        String shortcutId = statusBarNotification.getNotification().getShortcutId();
        if (shortcutId == null) {
            return null;
        }
        PackageData packageData = dataManager.getPackage(statusBarNotification.getUser().getIdentifier(), statusBarNotification.getPackageName());
        if (packageData == null || (conversation = packageData.mConversationStore.getConversation(shortcutId)) == null) {
            return null;
        }
        consumer.accept(conversation);
        return packageData;
    }

    public DataManager(Context context) {
        Injector injector = new Injector();
        Looper looper = BackgroundThread.get().getLooper();
        this.mLock = new Object();
        this.mUserDataArray = new SparseArray();
        this.mBroadcastReceivers = new SparseArray();
        this.mContactsContentObservers = new SparseArray();
        this.mUsageStatsQueryFutures = new SparseArray();
        this.mNotificationListeners = new SparseArray();
        this.mPackageMonitors = new SparseArray();
        this.mConversationsListeners = new ArrayList(1);
        this.mContext = context;
        this.mInjector = injector;
        this.mScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        this.mHandler = new Handler(looper);
    }

    public static ConversationInfo getConversationInfoOrThrow(ConversationStore conversationStore, String str) {
        ConversationInfo conversation = conversationStore.getConversation(str);
        if (conversation != null) {
            return conversation;
        }
        throw new IllegalArgumentException("Conversation does not exist");
    }

    public static int mimeTypeToShareEventType(String str) {
        if (str == null) {
            return 7;
        }
        if (str.startsWith("text/")) {
            return 4;
        }
        if (str.startsWith("image/")) {
            return 5;
        }
        return str.startsWith("video/") ? 6 : 7;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0100  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void addOrUpdateConversationInfo(android.content.pm.ShortcutInfo r15) {
        /*
            Method dump skipped, instructions count: 312
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.people.data.DataManager.addOrUpdateConversationInfo(android.content.pm.ShortcutInfo):void");
    }

    public final void cleanupCachedShortcuts(int i) {
        UserData unlockedUserData = getUnlockedUserData(i);
        if (unlockedUserData == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        unlockedUserData.forAllPackages(new DataManager$$ExternalSyntheticLambda4(this, arrayList, 1));
        if (arrayList.size() <= 30) {
            return;
        }
        int size = arrayList.size();
        int i2 = size - 30;
        PriorityQueue priorityQueue = new PriorityQueue(size - 29, Comparator.comparingLong(new DataManager$$ExternalSyntheticLambda10()).reversed());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (!hasActiveNotifications(i, (String) pair.first, ((ConversationInfo) pair.second).mShortcutId)) {
                priorityQueue.offer(pair);
                if (priorityQueue.size() > i2) {
                    priorityQueue.poll();
                }
            }
        }
        while (!priorityQueue.isEmpty()) {
            Pair pair2 = (Pair) priorityQueue.poll();
            this.mShortcutServiceInternal.uncacheShortcuts(i, this.mContext.getPackageName(), (String) pair2.first, Collections.singletonList(((ConversationInfo) pair2.second).mShortcutId), i, EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION);
        }
    }

    public final void forPackagesInProfile(int i, Consumer consumer) {
        Iterator it = this.mUserManager.getEnabledProfiles(i).iterator();
        while (it.hasNext()) {
            UserData unlockedUserData = getUnlockedUserData(((UserInfo) it.next()).id);
            if (unlockedUserData != null) {
                unlockedUserData.forAllPackages(consumer);
            }
        }
    }

    public ContentObserver getCallLogContentObserverForTesting() {
        return this.mCallLogContentObserver;
    }

    public ContentObserver getContactsContentObserverForTesting(int i) {
        return (ContentObserver) this.mContactsContentObservers.get(i);
    }

    public final ConversationChannel getConversationChannel(ShortcutInfo shortcutInfo, ConversationInfo conversationInfo, final String str, final int i, final String str2) {
        ArrayList arrayList;
        NotificationChannelGroup notificationChannelGroup = null;
        if (conversationInfo != null && !conversationInfo.hasConversationFlags(64)) {
            if (shortcutInfo == null) {
                Slog.e("DataManager", "Shortcut no longer found");
                this.mInjector.getClass();
                BackgroundThread.getExecutor().execute(new Runnable() { // from class: com.android.server.people.data.DataManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DataManager dataManager = DataManager.this;
                        String str3 = str;
                        int i2 = i;
                        String str4 = str2;
                        dataManager.getClass();
                        dataManager.removeConversations(i2, str3, Set.of(str4));
                    }
                });
                return null;
            }
            int packageUid = this.mPackageManagerInternal.getPackageUid(str, 0L, i);
            NotificationChannel notificationChannel = ((NotificationManagerService.AnonymousClass17) this.mNotificationManagerInternal).getNotificationChannel(packageUid, str, conversationInfo.mNotificationChannelId);
            if (notificationChannel != null) {
                notificationChannelGroup = ((NotificationManagerService.AnonymousClass17) this.mNotificationManagerInternal).getNotificationChannelGroup(packageUid, str, notificationChannel.getId());
            }
            NotificationChannelGroup notificationChannelGroup2 = notificationChannelGroup;
            boolean hasActiveNotifications = hasActiveNotifications(i, str, str2);
            Collection values = conversationInfo.mCurrStatuses.values();
            if (values != null) {
                ArrayList arrayList2 = new ArrayList(values.size());
                arrayList2.addAll(values);
                arrayList = arrayList2;
            } else {
                arrayList = new ArrayList();
            }
            notificationChannelGroup = new ConversationChannel(shortcutInfo, packageUid, notificationChannel, notificationChannelGroup2, conversationInfo.mLastEventTimestamp, hasActiveNotifications, false, arrayList);
        }
        return notificationChannelGroup;
    }

    public final ConversationChannel getConversationChannel(String str, int i, String str2, ConversationInfo conversationInfo) {
        List shortcuts = this.mShortcutServiceInternal.getShortcuts(0, this.mContext.getPackageName(), 0L, str, Collections.singletonList(str2), (List) null, (ComponentName) null, 3091, i, Process.myPid(), Process.myUid());
        return getConversationChannel((shortcuts == null || shortcuts.isEmpty()) ? null : (ShortcutInfo) shortcuts.get(0), conversationInfo, str, i, str2);
    }

    public final ConversationStore getConversationStoreOrThrow(int i, String str) {
        PackageData packageData = getPackage(i, str);
        if (packageData == null) {
            throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("No settings exist for package ", str));
        }
        ConversationStore conversationStore = packageData.mConversationStore;
        if (conversationStore != null) {
            return conversationStore;
        }
        throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("No conversations exist for package ", str));
    }

    public ContentObserver getMmsSmsContentObserverForTesting() {
        return this.mMmsSmsContentObserver;
    }

    public NotificationListener getNotificationListenerServiceForTesting(int i) {
        return (NotificationListener) this.mNotificationListeners.get(i);
    }

    public final PackageData getPackage(int i, String str) {
        UserData unlockedUserData = getUnlockedUserData(i);
        if (unlockedUserData != null) {
            return (PackageData) ((ArrayMap) unlockedUserData.mPackageDataMap).get(str);
        }
        return null;
    }

    public PackageMonitor getPackageMonitorForTesting(int i) {
        return (PackageMonitor) this.mPackageMonitors.get(i);
    }

    public final UserData getUnlockedUserData(int i) {
        UserData userData = (UserData) this.mUserDataArray.get(i);
        if (userData == null || !userData.mIsUnlocked) {
            return null;
        }
        return userData;
    }

    public UserData getUserDataForTesting(int i) {
        return (UserData) this.mUserDataArray.get(i);
    }

    public final boolean hasActiveNotifications(int i, String str, String str2) {
        boolean containsKey;
        NotificationListener notificationListener = (NotificationListener) this.mNotificationListeners.get(i);
        if (notificationListener != null) {
            synchronized (notificationListener) {
                containsKey = ((ArrayMap) notificationListener.mActiveNotifKeys).containsKey(Pair.create(str, str2));
            }
            if (containsKey) {
                return true;
            }
        }
        return false;
    }

    public final void initialize() {
        this.mShortcutServiceInternal = (ShortcutServiceInternal) LocalServices.getService(ShortcutServiceInternal.class);
        this.mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mNotificationManagerInternal = (NotificationManagerInternal) LocalServices.getService(NotificationManagerInternal.class);
        this.mUserManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        this.mShortcutServiceInternal.addShortcutChangeCallback(new ShortcutServiceCallback());
        ConversationStatusExpirationBroadcastReceiver conversationStatusExpirationBroadcastReceiver = new ConversationStatusExpirationBroadcastReceiver();
        this.mStatusExpReceiver = conversationStatusExpirationBroadcastReceiver;
        Context context = this.mContext;
        IntentFilter intentFilter = new IntentFilter("ConversationStatusExpiration");
        intentFilter.addDataScheme("expStatus");
        context.registerReceiver(conversationStatusExpirationBroadcastReceiver, intentFilter, 4);
        IntentFilter intentFilter2 = new IntentFilter("android.intent.action.ACTION_SHUTDOWN");
        this.mContext.registerReceiver(new ShutdownBroadcastReceiver(), intentFilter2);
    }

    public void notifyConversationsListeners(final List list) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.people.data.DataManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ArrayList arrayList;
                DataManager dataManager = DataManager.this;
                List list2 = list;
                dataManager.getClass();
                try {
                    synchronized (dataManager.mLock) {
                        arrayList = new ArrayList(dataManager.mConversationsListeners);
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ((PeopleService.ConversationListenerHelper) it.next()).onConversationsUpdate(list2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    public final void removeConversations(int i, String str, Set set) {
        ConversationInfo conversationInfo;
        PackageData packageData = getPackage(i, str);
        if (packageData != null) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                ConversationStore conversationStore = packageData.mConversationStore;
                synchronized (conversationStore) {
                    try {
                        conversationInfo = (ConversationInfo) ((ArrayMap) conversationStore.mConversationInfoMap).remove(str2);
                        if (conversationInfo == null) {
                            conversationInfo = null;
                        } else {
                            LocusId locusId = conversationInfo.mLocusId;
                            if (locusId != null) {
                                ((ArrayMap) conversationStore.mLocusIdToShortcutIdMap).remove(locusId);
                            }
                            Uri uri = conversationInfo.mContactUri;
                            if (uri != null) {
                                ((ArrayMap) conversationStore.mContactUriToShortcutIdMap).remove(uri);
                            }
                            String str3 = conversationInfo.mContactPhoneNumber;
                            if (str3 != null) {
                                ((ArrayMap) conversationStore.mPhoneNumberToShortcutIdMap).remove(str3);
                            }
                            String str4 = conversationInfo.mNotificationChannelId;
                            if (str4 != null) {
                                ((ArrayMap) conversationStore.mNotifChannelIdToShortcutIdMap).remove(str4);
                            }
                            conversationStore.scheduleUpdateConversationsOnDisk();
                        }
                    } finally {
                    }
                }
                if (conversationInfo != null) {
                    packageData.mEventStore.deleteEventHistory(0, str2);
                    LocusId locusId2 = conversationInfo.mLocusId;
                    if (locusId2 != null) {
                        packageData.mEventStore.deleteEventHistory(1, locusId2.getId());
                    }
                    String str5 = conversationInfo.mContactPhoneNumber;
                    if (!TextUtils.isEmpty(str5)) {
                        if (packageData.mIsDefaultDialerPredicate.test(packageData.mPackageName)) {
                            packageData.mEventStore.deleteEventHistory(2, str5);
                        }
                        if (packageData.mIsDefaultSmsAppPredicate.test(packageData.mPackageName)) {
                            packageData.mEventStore.deleteEventHistory(3, str5);
                        }
                    }
                }
            }
        }
        try {
            ((NotificationManagerService.AnonymousClass17) this.mNotificationManagerInternal).onConversationRemoved(this.mContext.getPackageManager().getPackageUidAsUser(str, i), str, set);
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e("DataManager", "Package not found when removing conversation: " + str, e);
        }
    }

    public void updateConversationStoreThenNotifyListeners(ConversationStore conversationStore, ConversationInfo conversationInfo, String str, int i) {
        conversationStore.updateConversationsInMemory(conversationInfo);
        conversationStore.scheduleUpdateConversationsOnDisk();
        ConversationChannel conversationChannel = getConversationChannel(str, i, conversationInfo.mShortcutId, conversationInfo);
        if (conversationChannel != null) {
            notifyConversationsListeners(Arrays.asList(conversationChannel));
        }
    }
}
