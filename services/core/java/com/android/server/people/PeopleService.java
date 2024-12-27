package com.android.server.people;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.people.ConversationChannel;
import android.app.people.ConversationStatus;
import android.app.people.IConversationListener;
import android.app.people.IPeopleManager;
import android.app.prediction.AppPredictionContext;
import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.AppTargetEvent;
import android.app.prediction.IPredictionCallback;
import android.content.Context;
import android.content.Intent;
import android.content.LocusId;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ParceledListSlice;
import android.content.pm.ShortcutInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;

import com.android.internal.util.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.people.data.ConversationInfo;
import com.android.server.people.data.ConversationStatusExpirationBroadcastReceiver;
import com.android.server.people.data.ConversationStore;
import com.android.server.people.data.DataManager;
import com.android.server.people.data.DataManager$$ExternalSyntheticLambda0;
import com.android.server.people.data.DataManager$$ExternalSyntheticLambda12;
import com.android.server.people.data.DataManager$$ExternalSyntheticLambda4;
import com.android.server.people.data.DataManager$$ExternalSyntheticLambda5;
import com.android.server.people.data.PackageData;
import com.android.server.people.data.UserData;
import com.android.server.people.data.UserData$$ExternalSyntheticLambda0;
import com.android.server.people.prediction.AppTargetPredictor;
import com.android.server.pm.pkg.AndroidPackage;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class PeopleService extends SystemService {
    public ConversationListenerHelper mLazyConversationListenerHelper;
    public DataManager mLazyDataManager;
    public PackageManagerInternal mPackageManagerInternal;
    final IBinder mService;

    public final class ConversationListenerHelper {
        final RemoteCallbackList mListeners = new RemoteCallbackList();

        public final void onConversationsUpdate(List list) {
            int beginBroadcast = this.mListeners.beginBroadcast();
            if (beginBroadcast == 0) {
                return;
            }
            HashMap hashMap = new HashMap();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ConversationChannel conversationChannel = (ConversationChannel) it.next();
                ShortcutInfo shortcutInfo = conversationChannel.getShortcutInfo();
                hashMap.put(
                        new ListenerKey(
                                shortcutInfo.getPackage(),
                                Integer.valueOf(shortcutInfo.getUserId()),
                                shortcutInfo.getId()),
                        conversationChannel);
            }
            for (int i = 0; i < beginBroadcast; i++) {
                ListenerKey listenerKey = (ListenerKey) this.mListeners.getBroadcastCookie(i);
                if (hashMap.containsKey(listenerKey)) {
                    try {
                        this.mListeners
                                .getBroadcastItem(i)
                                .onConversationUpdate(
                                        (ConversationChannel) hashMap.get(listenerKey));
                    } catch (RemoteException unused) {
                    }
                }
            }
            this.mListeners.finishBroadcast();
        }
    }

    public final class ListenerKey {
        public final String mPackageName;
        public final String mShortcutId;
        public final Integer mUserId;

        public ListenerKey(String str, Integer num, String str2) {
            this.mPackageName = str;
            this.mUserId = num;
            this.mShortcutId = str2;
        }

        public final boolean equals(Object obj) {
            ListenerKey listenerKey = (ListenerKey) obj;
            if (listenerKey.mPackageName.equals(this.mPackageName)) {
                if (Objects.equals(listenerKey.mUserId, this.mUserId)) {
                    if (listenerKey.mShortcutId.equals(this.mShortcutId)) {
                        return true;
                    }
                }
            }
            return false;
        }

        public final int hashCode() {
            return this.mShortcutId.hashCode()
                    + this.mUserId.hashCode()
                    + this.mPackageName.hashCode();
        }
    }

    final class LocalService extends PeopleServiceInternal {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final Map mSessions = new ArrayMap();

        public LocalService() {}

        @Override // com.android.server.people.PeopleServiceInternal
        public final byte[] getBackupPayload(int i) {
            byte[] bArr;
            UserData unlockedUserData = PeopleService.this.getDataManager().getUnlockedUserData(i);
            if (unlockedUserData == null) {
                return null;
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            for (PackageData packageData : ((ArrayMap) unlockedUserData.mPackageDataMap).values()) {
                try {
                    ConversationStore conversationStore = packageData.mConversationStore;
                    conversationStore.getClass();
                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                    final DataOutputStream dataOutputStream2 =
                            new DataOutputStream(byteArrayOutputStream2);
                    conversationStore.forAllConversations(
                            new Consumer() { // from class:
                                // com.android.server.people.data.ConversationStore$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    byte[] bArr2;
                                    DataOutputStream dataOutputStream3 = dataOutputStream2;
                                    ConversationInfo conversationInfo = (ConversationInfo) obj;
                                    conversationInfo.getClass();
                                    ByteArrayOutputStream byteArrayOutputStream3 =
                                            new ByteArrayOutputStream();
                                    DataOutputStream dataOutputStream4 =
                                            new DataOutputStream(byteArrayOutputStream3);
                                    try {
                                        dataOutputStream4.writeUTF(conversationInfo.mShortcutId);
                                        LocusId locusId = conversationInfo.mLocusId;
                                        dataOutputStream4.writeUTF(
                                                locusId != null ? locusId.getId() : "");
                                        Uri uri = conversationInfo.mContactUri;
                                        dataOutputStream4.writeUTF(
                                                uri != null ? uri.toString() : "");
                                        String str = conversationInfo.mNotificationChannelId;
                                        if (str == null) {
                                            str = "";
                                        }
                                        dataOutputStream4.writeUTF(str);
                                        dataOutputStream4.writeInt(conversationInfo.mShortcutFlags);
                                        dataOutputStream4.writeInt(
                                                conversationInfo.mConversationFlags);
                                        String str2 = conversationInfo.mContactPhoneNumber;
                                        if (str2 == null) {
                                            str2 = "";
                                        }
                                        dataOutputStream4.writeUTF(str2);
                                        String str3 = conversationInfo.mParentNotificationChannelId;
                                        dataOutputStream4.writeUTF(str3 != null ? str3 : "");
                                        dataOutputStream4.writeLong(
                                                conversationInfo.mLastEventTimestamp);
                                        dataOutputStream4.writeInt(1);
                                        dataOutputStream4.writeLong(
                                                conversationInfo.mCreationTimestamp);
                                        bArr2 = byteArrayOutputStream3.toByteArray();
                                    } catch (IOException e) {
                                        Slog.e(
                                                "ConversationInfo",
                                                "Failed to write fields to backup payload.",
                                                e);
                                        bArr2 = null;
                                    }
                                    if (bArr2 == null) {
                                        return;
                                    }
                                    try {
                                        dataOutputStream3.writeInt(bArr2.length);
                                        dataOutputStream3.write(bArr2);
                                    } catch (IOException e2) {
                                        Slog.e(
                                                "ConversationStore",
                                                "Failed to write conversation info to backup"
                                                    + " payload.",
                                                e2);
                                    }
                                }
                            });
                    try {
                        dataOutputStream2.writeInt(-1);
                        bArr = byteArrayOutputStream2.toByteArray();
                    } catch (IOException e) {
                        Slog.e(
                                "ConversationStore",
                                "Failed to write conversation infos end token to backup payload.",
                                e);
                        bArr = null;
                    }
                    dataOutputStream.writeInt(bArr.length);
                    dataOutputStream.write(bArr);
                    dataOutputStream.writeUTF(packageData.mPackageName);
                } catch (IOException e2) {
                    Slog.e("UserData", "Failed to write conversations to backup payload.", e2);
                    return null;
                }
            }
            try {
                dataOutputStream.writeInt(-1);
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e3) {
                Slog.e(
                        "UserData",
                        "Failed to write conversations end token to backup payload.",
                        e3);
                return null;
            }
        }

        public SessionInfo getSessionInfo(AppPredictionSessionId appPredictionSessionId) {
            return (SessionInfo) ((ArrayMap) this.mSessions).get(appPredictionSessionId);
        }

        public final void notifyAppTargetEvent(
                AppPredictionSessionId appPredictionSessionId, AppTargetEvent appTargetEvent) {
            runForSession(
                    appPredictionSessionId,
                    new PeopleService$LocalService$$ExternalSyntheticLambda3(appTargetEvent));
        }

        public final void notifyLaunchLocationShown(
                AppPredictionSessionId appPredictionSessionId,
                String str,
                ParceledListSlice parceledListSlice) {
            runForSession(
                    appPredictionSessionId,
                    new PeopleService$LocalService$$ExternalSyntheticLambda3(
                            str, parceledListSlice));
        }

        public final void onCreatePredictionSession(
                AppPredictionContext appPredictionContext,
                AppPredictionSessionId appPredictionSessionId) {
            ((ArrayMap) this.mSessions)
                    .put(
                            appPredictionSessionId,
                            new SessionInfo(
                                    appPredictionContext,
                                    PeopleService.this.getDataManager(),
                                    appPredictionSessionId.getUserId(),
                                    PeopleService.this.getContext()));
        }

        public final void onDestroyPredictionSession(
                AppPredictionSessionId appPredictionSessionId) {
            runForSession(
                    appPredictionSessionId,
                    new PeopleService$LocalService$$ExternalSyntheticLambda0(
                            this, appPredictionSessionId, 0));
        }

        @Override // com.android.server.people.PeopleServiceInternal
        public final void pruneDataForUser(
                final int i, final CancellationSignal cancellationSignal) {
            final DataManager dataManager = PeopleService.this.getDataManager();
            UserData unlockedUserData = dataManager.getUnlockedUserData(i);
            if (unlockedUserData == null || cancellationSignal.isCanceled()) {
                return;
            }
            final ArraySet arraySet = new ArraySet();
            dataManager.mPackageManagerInternal.forEachInstalledPackage(
                    unlockedUserData.mUserId,
                    new Consumer() { // from class:
                                     // com.android.server.people.data.DataManager$$ExternalSyntheticLambda11
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            arraySet.add(((AndroidPackage) obj).getPackageName());
                        }
                    });
            ArrayList arrayList = new ArrayList();
            unlockedUserData.forAllPackages(
                    new DataManager$$ExternalSyntheticLambda12(0, arraySet, arrayList));
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                unlockedUserData.deletePackageData((String) it.next());
            }
            unlockedUserData.forAllPackages(
                    new Consumer() { // from class:
                                     // com.android.server.people.data.DataManager$$ExternalSyntheticLambda6
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            final DataManager dataManager2 = DataManager.this;
                            CancellationSignal cancellationSignal2 = cancellationSignal;
                            int i2 = i;
                            final PackageData packageData = (PackageData) obj;
                            dataManager2.getClass();
                            if (cancellationSignal2.isCanceled()) {
                                return;
                            }
                            EventStore eventStore = packageData.mEventStore;
                            synchronized (eventStore) {
                                Iterator it2 =
                                        ((ArrayList) eventStore.mEventHistoryMaps).iterator();
                                while (it2.hasNext()) {
                                    Iterator it3 = ((Map) it2.next()).values().iterator();
                                    while (it3.hasNext()) {
                                        ((EventHistoryImpl) it3.next()).pruneOldEvents();
                                    }
                                }
                            }
                            if (!packageData.mIsDefaultDialerPredicate.test(
                                    packageData.mPackageName)) {
                                packageData.mEventStore.deleteEventHistories(2);
                            }
                            if (!packageData.mIsDefaultSmsAppPredicate.test(
                                    packageData.mPackageName)) {
                                packageData.mEventStore.deleteEventHistories(3);
                            }
                            final int i3 = 0;
                            Predicate predicate =
                                    new Predicate() { // from class:
                                                      // com.android.server.people.data.PackageData$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj2) {
                                            int i4 = i3;
                                            PackageData packageData2 = packageData;
                                            String str = (String) obj2;
                                            switch (i4) {
                                                case 0:
                                                    if (packageData2.mConversationStore
                                                                    .getConversation(str)
                                                            != null) {}
                                                    break;
                                                case 1:
                                                    packageData2.getClass();
                                                    if (packageData2.mConversationStore
                                                                    .getConversationByLocusId(
                                                                            new LocusId(str))
                                                            != null) {}
                                                    break;
                                                case 2:
                                                    if (packageData2.mConversationStore
                                                                    .getConversationByPhoneNumber(
                                                                            str)
                                                            != null) {}
                                                    break;
                                                default:
                                                    if (packageData2.mConversationStore
                                                                    .getConversationByPhoneNumber(
                                                                            str)
                                                            != null) {}
                                                    break;
                                            }
                                            return false;
                                        }
                                    };
                            EventStore eventStore2 = packageData.mEventStore;
                            eventStore2.pruneOrphanEventHistories(0, predicate);
                            final int i4 = 1;
                            eventStore2.pruneOrphanEventHistories(
                                    1,
                                    new Predicate() { // from class:
                                                      // com.android.server.people.data.PackageData$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj2) {
                                            int i42 = i4;
                                            PackageData packageData2 = packageData;
                                            String str = (String) obj2;
                                            switch (i42) {
                                                case 0:
                                                    if (packageData2.mConversationStore
                                                                    .getConversation(str)
                                                            != null) {}
                                                    break;
                                                case 1:
                                                    packageData2.getClass();
                                                    if (packageData2.mConversationStore
                                                                    .getConversationByLocusId(
                                                                            new LocusId(str))
                                                            != null) {}
                                                    break;
                                                case 2:
                                                    if (packageData2.mConversationStore
                                                                    .getConversationByPhoneNumber(
                                                                            str)
                                                            != null) {}
                                                    break;
                                                default:
                                                    if (packageData2.mConversationStore
                                                                    .getConversationByPhoneNumber(
                                                                            str)
                                                            != null) {}
                                                    break;
                                            }
                                            return false;
                                        }
                                    });
                            if (packageData.mIsDefaultDialerPredicate.test(
                                    packageData.mPackageName)) {
                                final int i5 = 2;
                                eventStore2.pruneOrphanEventHistories(
                                        2,
                                        new Predicate() { // from class:
                                                          // com.android.server.people.data.PackageData$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj2) {
                                                int i42 = i5;
                                                PackageData packageData2 = packageData;
                                                String str = (String) obj2;
                                                switch (i42) {
                                                    case 0:
                                                        if (packageData2.mConversationStore
                                                                        .getConversation(str)
                                                                != null) {}
                                                        break;
                                                    case 1:
                                                        packageData2.getClass();
                                                        if (packageData2.mConversationStore
                                                                        .getConversationByLocusId(
                                                                                new LocusId(str))
                                                                != null) {}
                                                        break;
                                                    case 2:
                                                        if (packageData2.mConversationStore
                                                                        .getConversationByPhoneNumber(
                                                                                str)
                                                                != null) {}
                                                        break;
                                                    default:
                                                        if (packageData2.mConversationStore
                                                                        .getConversationByPhoneNumber(
                                                                                str)
                                                                != null) {}
                                                        break;
                                                }
                                                return false;
                                            }
                                        });
                            }
                            if (packageData.mIsDefaultSmsAppPredicate.test(
                                    packageData.mPackageName)) {
                                final int i6 = 3;
                                eventStore2.pruneOrphanEventHistories(
                                        3,
                                        new Predicate() { // from class:
                                                          // com.android.server.people.data.PackageData$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj2) {
                                                int i42 = i6;
                                                PackageData packageData2 = packageData;
                                                String str = (String) obj2;
                                                switch (i42) {
                                                    case 0:
                                                        if (packageData2.mConversationStore
                                                                        .getConversation(str)
                                                                != null) {}
                                                        break;
                                                    case 1:
                                                        packageData2.getClass();
                                                        if (packageData2.mConversationStore
                                                                        .getConversationByLocusId(
                                                                                new LocusId(str))
                                                                != null) {}
                                                        break;
                                                    case 2:
                                                        if (packageData2.mConversationStore
                                                                        .getConversationByPhoneNumber(
                                                                                str)
                                                                != null) {}
                                                        break;
                                                    default:
                                                        if (packageData2.mConversationStore
                                                                        .getConversationByPhoneNumber(
                                                                                str)
                                                                != null) {}
                                                        break;
                                                }
                                                return false;
                                            }
                                        });
                            }
                            final long currentTimeMillis = System.currentTimeMillis();
                            dataManager2.forPackagesInProfile(
                                    i2,
                                    new Consumer() { // from class:
                                                     // com.android.server.people.data.DataManager$$ExternalSyntheticLambda7
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj2) {
                                            final DataManager dataManager3 = DataManager.this;
                                            final long j = currentTimeMillis;
                                            final PackageData packageData2 = (PackageData) obj2;
                                            dataManager3.getClass();
                                            if (packageData2 == null) {
                                                return;
                                            }
                                            final ConversationStore conversationStore =
                                                    packageData2.mConversationStore;
                                            conversationStore.forAllConversations(
                                                    new Consumer() { // from class:
                                                                     // com.android.server.people.data.DataManager$$ExternalSyntheticLambda15
                                                        @Override // java.util.function.Consumer
                                                        public final void accept(Object obj3) {
                                                            DataManager dataManager4 =
                                                                    DataManager.this;
                                                            long j2 = j;
                                                            ConversationStore conversationStore2 =
                                                                    conversationStore;
                                                            PackageData packageData3 = packageData2;
                                                            ConversationInfo conversationInfo =
                                                                    (ConversationInfo) obj3;
                                                            dataManager4.getClass();
                                                            new HashMap();
                                                            String str =
                                                                    conversationInfo.mShortcutId;
                                                            LocusId locusId =
                                                                    conversationInfo.mLocusId;
                                                            Uri uri = conversationInfo.mContactUri;
                                                            Map map =
                                                                    conversationInfo.mCurrStatuses;
                                                            ArrayList arrayList2 = new ArrayList();
                                                            for (ConversationStatus
                                                                    conversationStatus :
                                                                            conversationInfo
                                                                                    .mCurrStatuses
                                                                                    .values()) {
                                                                if (conversationStatus
                                                                                        .getEndTimeMillis()
                                                                                < 0
                                                                        || j2
                                                                                < conversationStatus
                                                                                        .getEndTimeMillis()) {
                                                                    arrayList2.add(
                                                                            conversationStatus);
                                                                }
                                                            }
                                                            map.clear();
                                                            Iterator it4 = arrayList2.iterator();
                                                            while (it4.hasNext()) {
                                                                ConversationStatus
                                                                        conversationStatus2 =
                                                                                (ConversationStatus)
                                                                                        it4.next();
                                                                map.put(
                                                                        conversationStatus2.getId(),
                                                                        conversationStatus2);
                                                            }
                                                            Objects.requireNonNull(str);
                                                            ConversationInfo conversationInfo2 =
                                                                    new ConversationInfo();
                                                            conversationInfo2.mShortcutId = str;
                                                            conversationInfo2.mLocusId = locusId;
                                                            conversationInfo2.mContactUri = uri;
                                                            conversationInfo2.mContactPhoneNumber =
                                                                    conversationInfo
                                                                            .mContactPhoneNumber;
                                                            conversationInfo2
                                                                            .mNotificationChannelId =
                                                                    conversationInfo
                                                                            .mNotificationChannelId;
                                                            conversationInfo2
                                                                            .mParentNotificationChannelId =
                                                                    conversationInfo
                                                                            .mParentNotificationChannelId;
                                                            conversationInfo2.mLastEventTimestamp =
                                                                    conversationInfo
                                                                            .mLastEventTimestamp;
                                                            conversationInfo2.mCreationTimestamp =
                                                                    conversationInfo
                                                                            .mCreationTimestamp;
                                                            conversationInfo2.mShortcutFlags =
                                                                    conversationInfo.mShortcutFlags;
                                                            conversationInfo2.mConversationFlags =
                                                                    conversationInfo
                                                                            .mConversationFlags;
                                                            conversationInfo2.mCurrStatuses = map;
                                                            dataManager4
                                                                    .updateConversationStoreThenNotifyListeners(
                                                                            conversationStore2,
                                                                            conversationInfo2,
                                                                            packageData3
                                                                                    .mPackageName,
                                                                            packageData3.mUserId);
                                                        }
                                                    });
                                        }
                                    });
                            dataManager2.forPackagesInProfile(
                                    i2,
                                    new DataManager$$ExternalSyntheticLambda5(
                                            dataManager2, System.currentTimeMillis(), i2));
                            dataManager2.cleanupCachedShortcuts(i2);
                        }
                    });
        }

        public final void registerPredictionUpdates(
                AppPredictionSessionId appPredictionSessionId,
                IPredictionCallback iPredictionCallback) {
            runForSession(
                    appPredictionSessionId,
                    new PeopleService$LocalService$$ExternalSyntheticLambda1(
                            iPredictionCallback, 0));
        }

        public final void requestPredictionUpdate(AppPredictionSessionId appPredictionSessionId) {
            runForSession(
                    appPredictionSessionId,
                    new PeopleService$LocalService$$ExternalSyntheticLambda2());
        }

        public final void requestServiceFeatures(
                AppPredictionSessionId appPredictionSessionId, IRemoteCallback iRemoteCallback) {}

        @Override // com.android.server.people.PeopleServiceInternal
        public final void restore(int i, byte[] bArr) {
            UserData unlockedUserData = PeopleService.this.getDataManager().getUnlockedUserData(i);
            if (unlockedUserData == null) {
                return;
            }
            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
            try {
                for (int readInt = dataInputStream.readInt();
                        readInt != -1;
                        readInt = dataInputStream.readInt()) {
                    byte[] bArr2 = new byte[readInt];
                    dataInputStream.readFully(bArr2, 0, readInt);
                    String readUTF = dataInputStream.readUTF();
                    ConversationStore conversationStore =
                            ((PackageData)
                                            unlockedUserData.mPackageDataMap.computeIfAbsent(
                                                    readUTF,
                                                    new UserData$$ExternalSyntheticLambda0(
                                                            unlockedUserData, readUTF)))
                                    .mConversationStore;
                    conversationStore.getClass();
                    DataInputStream dataInputStream2 =
                            new DataInputStream(new ByteArrayInputStream(bArr2));
                    try {
                        for (int readInt2 = dataInputStream2.readInt();
                                readInt2 != -1;
                                readInt2 = dataInputStream2.readInt()) {
                            byte[] bArr3 = new byte[readInt2];
                            dataInputStream2.readFully(bArr3, 0, readInt2);
                            ConversationInfo readFromBackupPayload =
                                    ConversationInfo.readFromBackupPayload(bArr3);
                            if (readFromBackupPayload != null) {
                                conversationStore.updateConversationsInMemory(
                                        readFromBackupPayload);
                                conversationStore.scheduleUpdateConversationsOnDisk();
                            }
                        }
                    } catch (IOException e) {
                        Slog.e(
                                "ConversationStore",
                                "Failed to read conversation info from payload.",
                                e);
                    }
                }
            } catch (IOException e2) {
                Slog.e("UserData", "Failed to restore conversations from backup payload.", e2);
            }
        }

        public final void runForSession(
                AppPredictionSessionId appPredictionSessionId, Consumer consumer) {
            SessionInfo sessionInfo =
                    (SessionInfo) ((ArrayMap) this.mSessions).get(appPredictionSessionId);
            if (sessionInfo != null) {
                consumer.accept(sessionInfo);
                return;
            }
            Slog.e("PeopleService", "Failed to find the session: " + appPredictionSessionId);
        }

        public final void sortAppTargets(
                AppPredictionSessionId appPredictionSessionId,
                final ParceledListSlice parceledListSlice,
                final IPredictionCallback iPredictionCallback) {
            runForSession(
                    appPredictionSessionId,
                    new Consumer() { // from class:
                                     // com.android.server.people.PeopleService$LocalService$$ExternalSyntheticLambda5
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            PeopleService.LocalService localService =
                                    PeopleService.LocalService.this;
                            ParceledListSlice parceledListSlice2 = parceledListSlice;
                            IPredictionCallback iPredictionCallback2 = iPredictionCallback;
                            int i = PeopleService.LocalService.$r8$clinit;
                            localService.getClass();
                            final AppTargetPredictor appTargetPredictor =
                                    ((SessionInfo) obj).mAppTargetPredictor;
                            final List list = parceledListSlice2.getList();
                            final PeopleService$LocalService$$ExternalSyntheticLambda0
                                    peopleService$LocalService$$ExternalSyntheticLambda0 =
                                            new PeopleService$LocalService$$ExternalSyntheticLambda0(
                                                    localService, iPredictionCallback2, 1);
                            appTargetPredictor.mCallbackExecutor.execute(
                                    new Runnable() { // from class:
                                                     // com.android.server.people.prediction.AppTargetPredictor$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            AppTargetPredictor.this.sortTargets(
                                                    list,
                                                    (PeopleService$LocalService$$ExternalSyntheticLambda0)
                                                            peopleService$LocalService$$ExternalSyntheticLambda0);
                                        }
                                    });
                        }
                    });
        }

        public final void unregisterPredictionUpdates(
                AppPredictionSessionId appPredictionSessionId,
                IPredictionCallback iPredictionCallback) {
            runForSession(
                    appPredictionSessionId,
                    new PeopleService$LocalService$$ExternalSyntheticLambda1(
                            iPredictionCallback, 1));
        }
    }

    /* renamed from: -$$Nest$mcheckCallerIsSameApp, reason: not valid java name */
    public static void m748$$Nest$mcheckCallerIsSameApp(PeopleService peopleService, String str) {
        peopleService.getClass();
        int callingUid = Binder.getCallingUid();
        if (peopleService.mPackageManagerInternal.getPackageUid(
                        str, 0L, UserHandle.getUserId(callingUid))
                != callingUid) {
            throw new SecurityException(
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                            callingUid, "Calling uid ", " cannot query eventsfor package ", str));
        }
    }

    /* renamed from: -$$Nest$mhandleIncomingUser, reason: not valid java name */
    public static void m749$$Nest$mhandleIncomingUser(PeopleService peopleService, int i) {
        peopleService.getClass();
        try {
            ActivityManager.getService()
                    .handleIncomingUser(
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            true,
                            true,
                            "",
                            (String) null);
        } catch (RemoteException unused) {
        }
    }

    public PeopleService(Context context) {
        super(context);
        this.mService =
                new IPeopleManager.Stub() { // from class: com.android.server.people.PeopleService.1
                    public final void addOrUpdateStatus(
                            String str, int i, String str2, ConversationStatus conversationStatus) {
                        PeopleService.m749$$Nest$mhandleIncomingUser(PeopleService.this, i);
                        PeopleService.m748$$Nest$mcheckCallerIsSameApp(PeopleService.this, str);
                        if (conversationStatus.getStartTimeMillis() > System.currentTimeMillis()) {
                            throw new IllegalArgumentException("Start time must be in the past");
                        }
                        DataManager dataManager = PeopleService.this.getDataManager();
                        ConversationStore conversationStoreOrThrow =
                                dataManager.getConversationStoreOrThrow(i, str);
                        ConversationInfo conversationInfoOrThrow =
                                DataManager.getConversationInfoOrThrow(
                                        conversationStoreOrThrow, str2);
                        new HashMap();
                        LocusId locusId = conversationInfoOrThrow.mLocusId;
                        Uri uri = conversationInfoOrThrow.mContactUri;
                        Map map = conversationInfoOrThrow.mCurrStatuses;
                        map.put(conversationStatus.getId(), conversationStatus);
                        String str3 = conversationInfoOrThrow.mShortcutId;
                        Objects.requireNonNull(str3);
                        ConversationInfo conversationInfo = new ConversationInfo();
                        conversationInfo.mShortcutId = str3;
                        conversationInfo.mLocusId = locusId;
                        conversationInfo.mContactUri = uri;
                        conversationInfo.mContactPhoneNumber =
                                conversationInfoOrThrow.mContactPhoneNumber;
                        conversationInfo.mNotificationChannelId =
                                conversationInfoOrThrow.mNotificationChannelId;
                        conversationInfo.mParentNotificationChannelId =
                                conversationInfoOrThrow.mParentNotificationChannelId;
                        conversationInfo.mLastEventTimestamp =
                                conversationInfoOrThrow.mLastEventTimestamp;
                        conversationInfo.mCreationTimestamp =
                                conversationInfoOrThrow.mCreationTimestamp;
                        conversationInfo.mShortcutFlags = conversationInfoOrThrow.mShortcutFlags;
                        conversationInfo.mConversationFlags =
                                conversationInfoOrThrow.mConversationFlags;
                        conversationInfo.mCurrStatuses = map;
                        dataManager.updateConversationStoreThenNotifyListeners(
                                conversationStoreOrThrow, conversationInfo, str, i);
                        if (conversationStatus.getEndTimeMillis() >= 0) {
                            ConversationStatusExpirationBroadcastReceiver
                                    conversationStatusExpirationBroadcastReceiver =
                                            dataManager.mStatusExpReceiver;
                            Context context2 = dataManager.mContext;
                            conversationStatusExpirationBroadcastReceiver.getClass();
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                ((AlarmManager) context2.getSystemService(AlarmManager.class))
                                        .setExactAndAllowWhileIdle(
                                                0,
                                                conversationStatus.getEndTimeMillis(),
                                                PendingIntent.getBroadcast(
                                                        context2,
                                                        10,
                                                        new Intent("ConversationStatusExpiration")
                                                                .setPackage("android")
                                                                .setData(
                                                                        new Uri.Builder()
                                                                                .scheme("expStatus")
                                                                                .appendPath(
                                                                                        i
                                                                                                + str
                                                                                                + str2
                                                                                                + conversationStatus
                                                                                                        .getId())
                                                                                .build())
                                                                .addFlags(268435456)
                                                                .putExtra("userId", i),
                                                        201326592));
                            } finally {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                    }

                    public final void clearStatus(String str, int i, String str2, String str3) {
                        PeopleService.m749$$Nest$mhandleIncomingUser(PeopleService.this, i);
                        PeopleService.m748$$Nest$mcheckCallerIsSameApp(PeopleService.this, str);
                        DataManager dataManager = PeopleService.this.getDataManager();
                        ConversationStore conversationStoreOrThrow =
                                dataManager.getConversationStoreOrThrow(i, str);
                        ConversationInfo conversationInfoOrThrow =
                                DataManager.getConversationInfoOrThrow(
                                        conversationStoreOrThrow, str2);
                        new HashMap();
                        LocusId locusId = conversationInfoOrThrow.mLocusId;
                        Uri uri = conversationInfoOrThrow.mContactUri;
                        Map map = conversationInfoOrThrow.mCurrStatuses;
                        map.remove(str3);
                        String str4 = conversationInfoOrThrow.mShortcutId;
                        Objects.requireNonNull(str4);
                        ConversationInfo conversationInfo = new ConversationInfo();
                        conversationInfo.mShortcutId = str4;
                        conversationInfo.mLocusId = locusId;
                        conversationInfo.mContactUri = uri;
                        conversationInfo.mContactPhoneNumber =
                                conversationInfoOrThrow.mContactPhoneNumber;
                        conversationInfo.mNotificationChannelId =
                                conversationInfoOrThrow.mNotificationChannelId;
                        conversationInfo.mParentNotificationChannelId =
                                conversationInfoOrThrow.mParentNotificationChannelId;
                        conversationInfo.mLastEventTimestamp =
                                conversationInfoOrThrow.mLastEventTimestamp;
                        conversationInfo.mCreationTimestamp =
                                conversationInfoOrThrow.mCreationTimestamp;
                        conversationInfo.mShortcutFlags = conversationInfoOrThrow.mShortcutFlags;
                        conversationInfo.mConversationFlags =
                                conversationInfoOrThrow.mConversationFlags;
                        conversationInfo.mCurrStatuses = map;
                        dataManager.updateConversationStoreThenNotifyListeners(
                                conversationStoreOrThrow, conversationInfo, str, i);
                    }

                    public final void clearStatuses(String str, int i, String str2) {
                        PeopleService.m749$$Nest$mhandleIncomingUser(PeopleService.this, i);
                        PeopleService.m748$$Nest$mcheckCallerIsSameApp(PeopleService.this, str);
                        DataManager dataManager = PeopleService.this.getDataManager();
                        ConversationStore conversationStoreOrThrow =
                                dataManager.getConversationStoreOrThrow(i, str);
                        ConversationInfo conversationInfoOrThrow =
                                DataManager.getConversationInfoOrThrow(
                                        conversationStoreOrThrow, str2);
                        new HashMap();
                        LocusId locusId = conversationInfoOrThrow.mLocusId;
                        Uri uri = conversationInfoOrThrow.mContactUri;
                        Map map = conversationInfoOrThrow.mCurrStatuses;
                        map.clear();
                        String str3 = conversationInfoOrThrow.mShortcutId;
                        Objects.requireNonNull(str3);
                        ConversationInfo conversationInfo = new ConversationInfo();
                        conversationInfo.mShortcutId = str3;
                        conversationInfo.mLocusId = locusId;
                        conversationInfo.mContactUri = uri;
                        conversationInfo.mContactPhoneNumber =
                                conversationInfoOrThrow.mContactPhoneNumber;
                        conversationInfo.mNotificationChannelId =
                                conversationInfoOrThrow.mNotificationChannelId;
                        conversationInfo.mParentNotificationChannelId =
                                conversationInfoOrThrow.mParentNotificationChannelId;
                        conversationInfo.mLastEventTimestamp =
                                conversationInfoOrThrow.mLastEventTimestamp;
                        conversationInfo.mCreationTimestamp =
                                conversationInfoOrThrow.mCreationTimestamp;
                        conversationInfo.mShortcutFlags = conversationInfoOrThrow.mShortcutFlags;
                        conversationInfo.mConversationFlags =
                                conversationInfoOrThrow.mConversationFlags;
                        conversationInfo.mCurrStatuses = map;
                        dataManager.updateConversationStoreThenNotifyListeners(
                                conversationStoreOrThrow, conversationInfo, str, i);
                    }

                    public final void dump(
                            FileDescriptor fileDescriptor,
                            PrintWriter printWriter,
                            String[] strArr) {
                        if (DumpUtils.checkDumpAndUsageStatsPermission(
                                PeopleService.this.getContext(), "PeopleService", printWriter)) {
                            PeopleService peopleService = PeopleService.this;
                            peopleService.getClass();
                            IndentingPrintWriter indentingPrintWriter =
                                    new IndentingPrintWriter(printWriter, "  ");
                            DataManager dataManager = peopleService.getDataManager();
                            dataManager.getClass();
                            indentingPrintWriter.println("DataManager dump");
                            for (int i = 0; i < dataManager.mUserDataArray.size(); i++) {
                                int keyAt = dataManager.mUserDataArray.keyAt(i);
                                indentingPrintWriter.print("UserId : " + keyAt);
                                UserData userData =
                                        (UserData) dataManager.mUserDataArray.get(keyAt);
                                if (userData.mIsUnlocked) {
                                    indentingPrintWriter.println(
                                            ", mPackageDataMap size : "
                                                    + ((ArrayMap) userData.mPackageDataMap).size());
                                    for (PackageData packageData :
                                            ((ArrayMap) userData.mPackageDataMap).values()) {
                                        indentingPrintWriter.printPair(
                                                "packageName", packageData.mPackageName);
                                        ConversationStore conversationStore =
                                                packageData.mConversationStore;
                                        indentingPrintWriter.printPair(
                                                "mConversationInfoMap",
                                                Integer.valueOf(
                                                        ((ArrayMap)
                                                                        conversationStore
                                                                                .mConversationInfoMap)
                                                                .size()));
                                        indentingPrintWriter.printPair(
                                                "mLocusIdToShortcutIdMap",
                                                Integer.valueOf(
                                                        ((ArrayMap)
                                                                        conversationStore
                                                                                .mLocusIdToShortcutIdMap)
                                                                .size()));
                                        indentingPrintWriter.printPair(
                                                "mContactUriToShortcutIdMap",
                                                Integer.valueOf(
                                                        ((ArrayMap)
                                                                        conversationStore
                                                                                .mContactUriToShortcutIdMap)
                                                                .size()));
                                        indentingPrintWriter.printPair(
                                                "mPhoneNumberToShortcutIdMap",
                                                Integer.valueOf(
                                                        ((ArrayMap)
                                                                        conversationStore
                                                                                .mPhoneNumberToShortcutIdMap)
                                                                .size()));
                                        indentingPrintWriter.printPair(
                                                "mNotifChannelIdToShortcutIdMap",
                                                Integer.valueOf(
                                                        ((ArrayMap)
                                                                        conversationStore
                                                                                .mNotifChannelIdToShortcutIdMap)
                                                                .size()));
                                    }
                                    indentingPrintWriter.println();
                                }
                            }
                        }
                    }

                    public final ConversationChannel getConversation(
                            String str, int i, String str2) {
                        PackageData packageData;
                        PeopleService peopleService = PeopleService.this;
                        peopleService.enforceSystemRootOrSystemUI(
                                peopleService.getContext(), "get conversation");
                        DataManager dataManager = PeopleService.this.getDataManager();
                        UserData unlockedUserData = dataManager.getUnlockedUserData(i);
                        if (unlockedUserData == null
                                || (packageData =
                                                (PackageData)
                                                        ((ArrayMap)
                                                                        unlockedUserData
                                                                                .mPackageDataMap)
                                                                .get(str))
                                        == null) {
                            return null;
                        }
                        return dataManager.getConversationChannel(
                                str, i, str2, packageData.mConversationStore.getConversation(str2));
                    }

                    public final long getLastInteraction(String str, int i, String str2) {
                        ConversationInfo conversation;
                        PeopleService peopleService = PeopleService.this;
                        peopleService.enforceSystemRootOrSystemUI(
                                peopleService.getContext(), "get last interaction");
                        PackageData packageData =
                                PeopleService.this.getDataManager().getPackage(i, str);
                        if (packageData == null
                                || (conversation =
                                                packageData.mConversationStore.getConversation(
                                                        str2))
                                        == null) {
                            return 0L;
                        }
                        return conversation.mLastEventTimestamp;
                    }

                    public final ParceledListSlice getRecentConversations() {
                        PeopleService peopleService = PeopleService.this;
                        peopleService.enforceSystemRootOrSystemUI(
                                peopleService.getContext(), "get recent conversations");
                        DataManager dataManager = PeopleService.this.getDataManager();
                        int identifier = Binder.getCallingUserHandle().getIdentifier();
                        dataManager.getClass();
                        ArrayList arrayList = new ArrayList();
                        dataManager.forPackagesInProfile(
                                identifier,
                                new DataManager$$ExternalSyntheticLambda4(
                                        dataManager, arrayList, 0));
                        return new ParceledListSlice(arrayList);
                    }

                    public final ParceledListSlice getStatuses(String str, int i, String str2) {
                        ArrayList arrayList;
                        PeopleService.m749$$Nest$mhandleIncomingUser(PeopleService.this, i);
                        if (!PeopleService.isSystemOrRoot()) {
                            PeopleService.m748$$Nest$mcheckCallerIsSameApp(PeopleService.this, str);
                        }
                        Collection values =
                                DataManager.getConversationInfoOrThrow(
                                                PeopleService.this
                                                        .getDataManager()
                                                        .getConversationStoreOrThrow(i, str),
                                                str2)
                                        .mCurrStatuses
                                        .values();
                        if (values != null) {
                            arrayList = new ArrayList(values.size());
                            arrayList.addAll(values);
                        } else {
                            arrayList = new ArrayList();
                        }
                        return new ParceledListSlice(arrayList);
                    }

                    public final boolean isConversation(String str, int i, String str2) {
                        PackageData packageData;
                        if (PeopleService.this
                                        .getContext()
                                        .checkCallingPermission(
                                                "android.permission.READ_PEOPLE_DATA")
                                != 0) {
                            throw new SecurityException(
                                    "Caller doesn't have READ_PEOPLE_DATA permission.");
                        }
                        PeopleService.m749$$Nest$mhandleIncomingUser(PeopleService.this, i);
                        DataManager dataManager = PeopleService.this.getDataManager();
                        UserData unlockedUserData = dataManager.getUnlockedUserData(i);
                        ConversationChannel conversationChannel =
                                (unlockedUserData == null
                                                || (packageData =
                                                                (PackageData)
                                                                        ((ArrayMap)
                                                                                        unlockedUserData
                                                                                                .mPackageDataMap)
                                                                                .get(str))
                                                        == null)
                                        ? null
                                        : dataManager.getConversationChannel(
                                                str,
                                                i,
                                                str2,
                                                packageData.mConversationStore.getConversation(
                                                        str2));
                        return (conversationChannel == null
                                        || conversationChannel.getShortcutInfo() == null
                                        || TextUtils.isEmpty(
                                                conversationChannel.getShortcutInfo().getLabel()))
                                ? false
                                : true;
                    }

                    public final void registerConversationListener(
                            String str,
                            int i,
                            String str2,
                            IConversationListener iConversationListener) {
                        PeopleService peopleService = PeopleService.this;
                        peopleService.enforceSystemRootOrSystemUI(
                                peopleService.getContext(), "register conversation listener");
                        ConversationListenerHelper conversationListenerHelper =
                                PeopleService.this.getConversationListenerHelper();
                        ListenerKey listenerKey = new ListenerKey(str, Integer.valueOf(i), str2);
                        synchronized (conversationListenerHelper) {
                            conversationListenerHelper.mListeners.unregister(iConversationListener);
                            conversationListenerHelper.mListeners.register(
                                    iConversationListener, listenerKey);
                        }
                    }

                    public final void removeAllRecentConversations() {
                        if (!PeopleService.isSystemOrRoot()) {
                            throw new SecurityException(
                                    "Only system may ".concat("remove all recent conversations"));
                        }
                        DataManager dataManager = PeopleService.this.getDataManager();
                        int identifier = Binder.getCallingUserHandle().getIdentifier();
                        dataManager.getClass();
                        dataManager.forPackagesInProfile(
                                identifier,
                                new DataManager$$ExternalSyntheticLambda5(
                                        dataManager, Long.MAX_VALUE, identifier));
                    }

                    public final void removeRecentConversation(String str, int i, String str2) {
                        if (!PeopleService.isSystemOrRoot()) {
                            throw new SecurityException(
                                    "Only system may ".concat("remove a recent conversation"));
                        }
                        DataManager dataManager = PeopleService.this.getDataManager();
                        int identifier = Binder.getCallingUserHandle().getIdentifier();
                        if (dataManager.hasActiveNotifications(i, str, str2)) {
                            return;
                        }
                        dataManager.mShortcutServiceInternal.uncacheShortcuts(
                                identifier,
                                dataManager.mContext.getPackageName(),
                                str,
                                Collections.singletonList(str2),
                                i,
                                EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION);
                    }

                    public final void unregisterConversationListener(
                            IConversationListener iConversationListener) {
                        PeopleService peopleService = PeopleService.this;
                        peopleService.enforceSystemRootOrSystemUI(
                                peopleService.getContext(), "unregister conversation listener");
                        ConversationListenerHelper conversationListenerHelper =
                                PeopleService.this.getConversationListenerHelper();
                        synchronized (conversationListenerHelper) {
                            conversationListenerHelper.mListeners.unregister(iConversationListener);
                        }
                    }
                };
    }

    public static boolean isSystemOrRoot() {
        int callingUid = Binder.getCallingUid();
        return UserHandle.isSameApp(callingUid, 1000) || callingUid == 0;
    }

    public void enforceSystemRootOrSystemUI(Context context, String str) {
        if (isSystemOrRoot()) {
            return;
        }
        context.enforceCallingPermission("android.permission.STATUS_BAR_SERVICE", str);
    }

    public ConversationListenerHelper getConversationListenerHelper() {
        if (this.mLazyConversationListenerHelper == null) {
            initLazyStuff();
        }
        return this.mLazyConversationListenerHelper;
    }

    public final DataManager getDataManager() {
        if (this.mLazyDataManager == null) {
            initLazyStuff();
        }
        return this.mLazyDataManager;
    }

    public final synchronized void initLazyStuff() {
        if (this.mLazyDataManager == null) {
            DataManager dataManager = new DataManager(getContext());
            this.mLazyDataManager = dataManager;
            dataManager.initialize();
            ConversationListenerHelper conversationListenerHelper =
                    new ConversationListenerHelper();
            this.mLazyConversationListenerHelper = conversationListenerHelper;
            DataManager dataManager2 = this.mLazyDataManager;
            synchronized (dataManager2.mLock) {
                List list = dataManager2.mConversationsListeners;
                Objects.requireNonNull(conversationListenerHelper);
                ((ArrayList) list).add(conversationListenerHelper);
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        onStart(false);
    }

    public void onStart(boolean z) {
        if (!z) {
            publishBinderService("people", this.mService);
        }
        publishLocalService(PeopleServiceInternal.class, new LocalService());
        this.mPackageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
    }

    @Override // com.android.server.SystemService
    public final void onUserStopping(SystemService.TargetUser targetUser) {
        DataManager dataManager = getDataManager();
        int userIdentifier = targetUser.getUserIdentifier();
        synchronized (dataManager.mLock) {
            UserData userData = (UserData) dataManager.mUserDataArray.get(userIdentifier);
            if (userData != null) {
                userData.mIsUnlocked = false;
            }
        }
        dataManager.mScheduledExecutor.execute(
                new DataManager$$ExternalSyntheticLambda0(dataManager, userIdentifier, 0));
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocked(SystemService.TargetUser targetUser) {
        DataManager dataManager = getDataManager();
        int userIdentifier = targetUser.getUserIdentifier();
        synchronized (dataManager.mLock) {
            try {
                UserData userData = (UserData) dataManager.mUserDataArray.get(userIdentifier);
                if (userData == null) {
                    userData = new UserData(userIdentifier, dataManager.mScheduledExecutor);
                    dataManager.mUserDataArray.put(userIdentifier, userData);
                }
                userData.mIsUnlocked = true;
            } catch (Throwable th) {
                throw th;
            }
        }
        dataManager.mScheduledExecutor.execute(
                new DataManager$$ExternalSyntheticLambda0(dataManager, userIdentifier, 1));
    }
}
