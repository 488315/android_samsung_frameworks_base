package com.android.server.content;

import android.accounts.Account;
import android.accounts.AccountManagerInternal;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.compat.CompatChanges;
import android.app.job.JobInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IContentService;
import android.content.ISyncStatusObserver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SyncAdapterType;
import android.content.SyncInfo;
import android.content.SyncRequest;
import android.content.SyncStatusInfo;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ProviderInfo;
import android.content.pm.RegisteredServicesCache;
import android.database.IContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.FactoryTest;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.BinderDeathDispatcher;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.IntPair;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.alarm.AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0;
import com.android.server.content.ContentService;
import com.android.server.content.SyncStorageEngine;
import com.android.server.pm.permission.DefaultPermissionGrantPolicy;
import com.android.server.pm.permission.LegacyPermissionManagerService;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import com.samsung.android.knox.restriction.IRoamingPolicy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public final class ContentService extends IContentService.Stub {
    public static final BinderDeathDispatcher sObserverDeathDispatcher = new BinderDeathDispatcher();
    public static final ArraySet sObserverLeakDetectedUid = new ArraySet(0);
    public final AccountManagerInternal mAccountManagerInternal;
    public final AnonymousClass1 mCacheReceiver;
    public final Context mContext;
    public final boolean mFactoryTest;
    public final ObserverNode mRootNode = new ObserverNode("");
    public SyncManager mSyncManager = null;
    public final Object mSyncManagerLock = new Object();
    public final SparseArray mCache = new SparseArray();

    public final class Lifecycle extends SystemService {
        public ContentService mService;

        public Lifecycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            ContentService contentService = this.mService;
            if (i == 550) {
                contentService.getSyncManager();
            }
            SyncManager syncManager = contentService.mSyncManager;
            if (syncManager == null || i != 550) {
                return;
            }
            final SyncManagerConstants syncManagerConstants = syncManager.mConstants;
            syncManagerConstants.getClass();
            BackgroundThread.getHandler().post(new Runnable() { // from class: com.android.server.content.SyncManagerConstants$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SyncManagerConstants syncManagerConstants2 = SyncManagerConstants.this;
                    syncManagerConstants2.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("sync_manager_constants"), false, syncManagerConstants2);
                    syncManagerConstants2.refresh();
                }
            });
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            ?? contentService = new ContentService(getContext(), FactoryTest.getMode() == 1);
            this.mService = contentService;
            publishBinderService("content", contentService);
        }

        @Override // com.android.server.SystemService
        public final void onUserStarting(SystemService.TargetUser targetUser) {
            ContentService contentService = this.mService;
            int userIdentifier = targetUser.getUserIdentifier();
            SyncManager syncManager = contentService.mSyncManager;
            if (syncManager != null) {
                syncManager.mSyncHandler.post(new SyncManager$$ExternalSyntheticLambda3(syncManager, userIdentifier, 0));
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserStopped(SystemService.TargetUser targetUser) {
            synchronized (this.mService.mCache) {
                this.mService.mCache.remove(targetUser.getUserIdentifier());
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserStopping(SystemService.TargetUser targetUser) {
            ContentService contentService = this.mService;
            int userIdentifier = targetUser.getUserIdentifier();
            SyncManager syncManager = contentService.mSyncManager;
            if (syncManager != null) {
                synchronized (syncManager.mUnlockedUsers) {
                    syncManager.mUnlockedUsers.put(userIdentifier, false);
                }
                syncManager.mSyncHandler.post(new SyncManager$$ExternalSyntheticLambda3(syncManager, userIdentifier, 1));
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocking(SystemService.TargetUser targetUser) {
            ContentService contentService = this.mService;
            int userIdentifier = targetUser.getUserIdentifier();
            SyncManager syncManager = contentService.mSyncManager;
            if (syncManager != null) {
                synchronized (syncManager.mUnlockedUsers) {
                    syncManager.mUnlockedUsers.put(userIdentifier, true);
                }
                syncManager.mSyncHandler.post(new SyncManager$$ExternalSyntheticLambda3(syncManager, userIdentifier, 2));
            }
        }
    }

    public class ObserverCollector {
        public final ArrayMap collected = new ArrayMap();

        public final class Key {
            public final int flags;
            public final IContentObserver observer;
            public final boolean selfChange;
            public final int uid;
            public final int userId;

            public Key(IContentObserver iContentObserver, int i, boolean z, int i2, int i3) {
                this.observer = iContentObserver;
                this.uid = i;
                this.selfChange = z;
                this.flags = i2;
                this.userId = i3;
            }

            public final boolean equals(Object obj) {
                if (!(obj instanceof Key)) {
                    return false;
                }
                Key key = (Key) obj;
                return Objects.equals(this.observer, key.observer) && this.uid == key.uid && this.selfChange == key.selfChange && this.flags == key.flags && this.userId == key.userId;
            }

            public final int hashCode() {
                return Objects.hash(this.observer, Integer.valueOf(this.uid), Boolean.valueOf(this.selfChange), Integer.valueOf(this.flags), Integer.valueOf(this.userId));
            }
        }

        public final void dispatch() {
            for (int i = 0; i < this.collected.size(); i++) {
                final Key key = (Key) this.collected.keyAt(i);
                final List list = (List) this.collected.valueAt(i);
                Runnable runnable = new Runnable() { // from class: com.android.server.content.ContentService$ObserverCollector$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ContentService.ObserverCollector.Key key2 = ContentService.ObserverCollector.Key.this;
                        List list2 = list;
                        try {
                            key2.observer.onChangeEtc(key2.selfChange, (Uri[]) list2.toArray(new Uri[list2.size()]), key2.flags, key2.userId);
                        } catch (RemoteException unused) {
                        }
                    }
                };
                boolean z = (key.flags & 32768) != 0;
                if (((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).getUidProcessState(key.uid) <= 6 || z) {
                    runnable.run();
                } else {
                    BackgroundThread.getHandler().postDelayed(runnable, 10000L);
                }
            }
        }
    }

    public final class ObserverNode {
        public final String mName;
        public final ArrayList mChildren = new ArrayList();
        public final ArrayList mObservers = new ArrayList();

        public final class ObserverEntry implements IBinder.DeathRecipient {
            public final boolean noDelay;
            public final boolean notifyForDescendants;
            public final IContentObserver observer;
            public final Object observersLock;
            public final int pid;
            public final int uid;
            public final int userHandle;

            public ObserverEntry(IContentObserver iContentObserver, boolean z, Object obj, int i, int i2, int i3, Uri uri) {
                boolean contains;
                String str;
                String nameForUid;
                this.observersLock = obj;
                this.observer = iContentObserver;
                this.uid = i;
                this.pid = i2;
                this.userHandle = i3;
                this.notifyForDescendants = z;
                int linkToDeath = ContentService.sObserverDeathDispatcher.linkToDeath(iContentObserver, this);
                if (linkToDeath == -1) {
                    binderDied();
                } else if (linkToDeath == 1000) {
                    ArraySet arraySet = ContentService.sObserverLeakDetectedUid;
                    synchronized (arraySet) {
                        try {
                            contains = arraySet.contains(Integer.valueOf(i));
                            if (!contains) {
                                arraySet.add(Integer.valueOf(i));
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    if (!contains) {
                        try {
                            str = (String) ArrayUtils.firstOrNull(AppGlobals.getPackageManager().getPackagesForUid(i));
                        } catch (RemoteException unused) {
                            str = null;
                        }
                        StringBuilder sb = new StringBuilder("Observer registered too many times. Leak? cpid=");
                        sb.append(this.pid);
                        sb.append(" cuid=");
                        AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.uid, " cpkg=", str, " url=", sb);
                        sb.append(uri);
                        Slog.wtf("ContentService", sb.toString());
                    }
                }
                this.noDelay = "media".equals(uri.getAuthority()) && (nameForUid = ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).getNameForUid(this.uid)) != null && nameForUid.startsWith("android.media:");
            }

            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                synchronized (this.observersLock) {
                    ObserverNode.this.removeObserverLocked(this.observer);
                }
            }
        }

        public ObserverNode(String str) {
            this.mName = str;
        }

        public final void addObserverLocked(Uri uri, int i, IContentObserver iContentObserver, boolean z, Object obj, int i2, int i3, int i4) {
            if (i == (uri == null ? 0 : uri.getPathSegments().size() + 1)) {
                this.mObservers.add(new ObserverEntry(iContentObserver, z, obj, i2, i3, i4, uri));
                return;
            }
            String authority = uri != null ? i == 0 ? uri.getAuthority() : uri.getPathSegments().get(i - 1) : null;
            if (authority == null) {
                throw new IllegalArgumentException("Invalid Uri (" + uri + ") used for observer");
            }
            int size = this.mChildren.size();
            for (int i5 = 0; i5 < size; i5++) {
                ObserverNode observerNode = (ObserverNode) this.mChildren.get(i5);
                if (observerNode.mName.equals(authority)) {
                    observerNode.addObserverLocked(uri, i + 1, iContentObserver, z, obj, i2, i3, i4);
                    return;
                }
            }
            ObserverNode observerNode2 = new ObserverNode(authority);
            this.mChildren.add(observerNode2);
            observerNode2.addObserverLocked(uri, i + 1, iContentObserver, z, obj, i2, i3, i4);
        }

        public final void collectMyObserversLocked(Uri uri, boolean z, IContentObserver iContentObserver, boolean z2, int i, int i2, ObserverCollector observerCollector) {
            int i3;
            int size = this.mObservers.size();
            IBinder asBinder = iContentObserver == null ? null : iContentObserver.asBinder();
            int i4 = i;
            for (int i5 = 0; i5 < size; i5++) {
                ObserverEntry observerEntry = (ObserverEntry) this.mObservers.get(i5);
                boolean z3 = observerEntry.observer.asBinder() == asBinder;
                if ((!z3 || z2) && ((i2 == -1 || (i3 = observerEntry.userHandle) == -1 || i2 == i3) && (!z ? observerEntry.notifyForDescendants : (i4 & 2) == 0 || !observerEntry.notifyForDescendants))) {
                    if (observerEntry.noDelay) {
                        i4 |= 32768;
                    }
                    int i6 = i4;
                    IContentObserver iContentObserver2 = observerEntry.observer;
                    int i7 = observerEntry.uid;
                    observerCollector.getClass();
                    ObserverCollector.Key key = new ObserverCollector.Key(iContentObserver2, i7, z3, i6, i2);
                    List list = (List) observerCollector.collected.get(key);
                    if (list == null) {
                        list = new ArrayList();
                        observerCollector.collected.put(key, list);
                    }
                    list.add(uri);
                    i4 = i6;
                }
            }
        }

        public final void collectObserversLocked(Uri uri, int i, int i2, IContentObserver iContentObserver, boolean z, int i3, int i4, ObserverCollector observerCollector) {
            int i5 = i2;
            if (i5 >= i) {
                collectMyObserversLocked(uri, true, iContentObserver, z, i3, i4, observerCollector);
            } else if (i5 < i) {
                r9 = uri != null ? i5 == 0 ? uri.getAuthority() : uri.getPathSegments().get(i5 - 1) : null;
                collectMyObserversLocked(uri, false, iContentObserver, z, i3, i4, observerCollector);
            }
            String str = r9;
            int size = this.mChildren.size();
            int i6 = 0;
            while (i6 < size) {
                ObserverNode observerNode = (ObserverNode) this.mChildren.get(i6);
                if (str == null || observerNode.mName.equals(str)) {
                    observerNode.collectObserversLocked(uri, i, i5 + 1, iContentObserver, z, i3, i4, observerCollector);
                    if (str != null) {
                        return;
                    }
                }
                i6++;
                i5 = i2;
            }
        }

        public void collectObserversLocked(Uri uri, int i, IContentObserver iContentObserver, boolean z, int i2, int i3, ObserverCollector observerCollector) {
            collectObserversLocked(uri, uri == null ? 0 : uri.getPathSegments().size() + 1, i, iContentObserver, z, i2, i3, observerCollector);
        }

        public final void dumpLocked(PrintWriter printWriter, String[] strArr, String str, int[] iArr, SparseIntArray sparseIntArray) {
            String str2;
            int size = this.mObservers.size();
            String str3 = this.mName;
            String str4 = null;
            if (size > 0) {
                String m = "".equals(str) ? str3 : AnyMotionDetector$$ExternalSyntheticOutline0.m(str, "/", str3);
                for (int i = 0; i < this.mObservers.size(); i++) {
                    iArr[1] = iArr[1] + 1;
                    ObserverEntry observerEntry = (ObserverEntry) this.mObservers.get(i);
                    int i2 = observerEntry.pid;
                    sparseIntArray.put(i2, sparseIntArray.get(i2) + 1);
                    printWriter.print("  ");
                    printWriter.print(m);
                    printWriter.print(": pid=");
                    printWriter.print(observerEntry.pid);
                    printWriter.print(" uid=");
                    printWriter.print(observerEntry.uid);
                    printWriter.print(" user=");
                    printWriter.print(observerEntry.userHandle);
                    printWriter.print(" target=");
                    IContentObserver iContentObserver = observerEntry.observer;
                    printWriter.println(Integer.toHexString(System.identityHashCode(iContentObserver != null ? iContentObserver.asBinder() : null)));
                }
                str4 = m;
            }
            if (this.mChildren.size() > 0) {
                if (str4 == null) {
                    if (!"".equals(str)) {
                        str3 = AnyMotionDetector$$ExternalSyntheticOutline0.m(str, "/", str3);
                    }
                    str2 = str3;
                } else {
                    str2 = str4;
                }
                for (int i3 = 0; i3 < this.mChildren.size(); i3++) {
                    iArr[0] = iArr[0] + 1;
                    ((ObserverNode) this.mChildren.get(i3)).dumpLocked(printWriter, strArr, str2, iArr, sparseIntArray);
                }
            }
        }

        public final boolean removeObserverLocked(IContentObserver iContentObserver) {
            int size = this.mChildren.size();
            int i = 0;
            while (i < size) {
                if (((ObserverNode) this.mChildren.get(i)).removeObserverLocked(iContentObserver)) {
                    this.mChildren.remove(i);
                    i--;
                    size--;
                }
                i++;
            }
            IBinder asBinder = iContentObserver.asBinder();
            int size2 = this.mObservers.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size2) {
                    break;
                }
                ObserverEntry observerEntry = (ObserverEntry) this.mObservers.get(i2);
                if (observerEntry.observer.asBinder() == asBinder) {
                    this.mObservers.remove(i2);
                    ContentService.sObserverDeathDispatcher.unlinkToDeath(iContentObserver, observerEntry);
                    break;
                }
                i2++;
            }
            return this.mChildren.size() == 0 && this.mObservers.size() == 0;
        }
    }

    public ContentService(Context context, boolean z) {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.content.ContentService.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                synchronized (ContentService.this.mCache) {
                    try {
                        if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                            ContentService.this.mCache.clear();
                        } else {
                            Uri data = intent.getData();
                            if (data != null) {
                                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                                ContentService.this.invalidateCacheLocked(data.getSchemeSpecificPart(), null, intExtra);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mContext = context;
        this.mFactoryTest = z;
        LegacyPermissionManagerService.Internal internal = (LegacyPermissionManagerService.Internal) LocalServices.getService(LegacyPermissionManagerService.Internal.class);
        ContentService$$ExternalSyntheticLambda0 contentService$$ExternalSyntheticLambda0 = new ContentService$$ExternalSyntheticLambda0(this);
        DefaultPermissionGrantPolicy defaultPermissionGrantPolicy = LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy;
        synchronized (defaultPermissionGrantPolicy.mLock) {
            defaultPermissionGrantPolicy.mSyncAdapterPackagesProvider = contentService$$ExternalSyntheticLambda0;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addDataScheme("package");
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(broadcastReceiver, userHandle, intentFilter, null, null);
        context.registerReceiverAsUser(broadcastReceiver, userHandle, BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.LOCALE_CHANGED"), null, null);
        this.mAccountManagerInternal = (AccountManagerInternal) LocalServices.getService(AccountManagerInternal.class);
    }

    public static long clampPeriod(long j) {
        long minPeriodMillis = JobInfo.getMinPeriodMillis() / 1000;
        if (j >= minPeriodMillis) {
            return j;
        }
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("Requested poll frequency of ", j, " seconds being rounded up to ");
        m.append(minPeriodMillis);
        m.append("s.");
        Slog.w("ContentService", m.toString());
        return minPeriodMillis;
    }

    public static int getSyncExemptionAndCleanUpExtrasForCaller(int i, Bundle bundle) {
        int i2;
        if (bundle != null) {
            int i3 = bundle.getInt("v_exemption", -1);
            bundle.remove("v_exemption");
            if (i3 != -1) {
                return i3;
            }
        }
        ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        int i4 = 0;
        if (activityManagerInternal == null) {
            return 0;
        }
        int uidProcessState = activityManagerInternal.getUidProcessState(i);
        boolean isUidActive = activityManagerInternal.isUidActive(i);
        if (uidProcessState <= 2 || uidProcessState == 3) {
            return 2;
        }
        if (uidProcessState > 6 && !isUidActive) {
            return 0;
        }
        switch (uidProcessState) {
            case 0:
                i2 = 1;
                break;
            case 1:
                i2 = 2;
                break;
            case 2:
                i2 = 3;
                break;
            case 3:
                i2 = 4;
                break;
            case 4:
                i2 = 5;
                break;
            case 5:
                i2 = 6;
                break;
            case 6:
                i2 = 7;
                break;
            case 7:
                i2 = 8;
                break;
            case 8:
                i2 = 9;
                break;
            case 9:
                i2 = 10;
                break;
            case 10:
                i2 = 11;
                break;
            case 11:
                i2 = 12;
                break;
            case 12:
                i2 = 13;
                break;
            case 13:
                i2 = 14;
                break;
            case 14:
            default:
                i2 = 0;
                break;
            case 15:
                i2 = 16;
                break;
            case 16:
                i2 = 17;
                break;
            case 17:
                i2 = 18;
                break;
            case 18:
                i2 = 19;
                break;
            case 19:
                i2 = 20;
                break;
        }
        int restrictionLevel = activityManagerInternal.getRestrictionLevel(i);
        if (restrictionLevel == 10) {
            i4 = 1;
        } else if (restrictionLevel == 20) {
            i4 = 2;
        } else if (restrictionLevel == 30) {
            i4 = 3;
        } else if (restrictionLevel == 40) {
            i4 = 4;
        } else if (restrictionLevel == 50) {
            i4 = 5;
        } else if (restrictionLevel == 60) {
            i4 = 6;
        }
        FrameworkStatsLog.write(FrameworkStatsLog.SYNC_EXEMPTION_OCCURRED, i, i2, isUidActive, i4);
        return 1;
    }

    public static void validateExtras(int i, Bundle bundle) {
        if (!bundle.containsKey("v_exemption") || i == 0 || i == 1000 || i == 2000) {
            return;
        }
        Log.w("ContentService", "Invalid extras specified. requestsync -f/-F needs to run on 'adb shell'");
        throw new SecurityException("Invalid extras specified.");
    }

    public final void addPeriodicSync(Account account, String str, Bundle bundle, long j) {
        Bundle.setDefusable(bundle, true);
        if (account == null) {
            throw new IllegalArgumentException("Account must not be null");
        }
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Authority must not be empty.");
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SYNC_SETTINGS", "no permission to write the sync settings");
        int callingUid = Binder.getCallingUid();
        int callingUserId = UserHandle.getCallingUserId();
        if (hasAccountAccess(account, callingUid) && hasAuthorityAccess(callingUid, callingUserId, str)) {
            validateExtras(callingUid, bundle);
            long clampPeriod = clampPeriod(j);
            long calculateDefaultFlexTime = SyncStorageEngine.calculateDefaultFlexTime(clampPeriod);
            long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
            try {
                getSyncManager().updateOrAddPeriodicSync(new SyncStorageEngine.EndPoint(account, str, callingUserId), clampPeriod, calculateDefaultFlexTime, bundle);
            } finally {
                IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void addStatusChangeListener(int i, ISyncStatusObserver iSyncStatusObserver) {
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
        if (iSyncStatusObserver != null) {
            try {
                SyncStorageEngine syncStorageEngine = getSyncManager().mSyncStorageEngine;
                synchronized (syncStorageEngine.mAuthorities) {
                    syncStorageEngine.mChangeListeners.register(iSyncStatusObserver, Long.valueOf(IntPair.of(callingUid, i)));
                }
            } finally {
                IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void cancelRequest(SyncRequest syncRequest) {
        if (syncRequest.isPeriodic()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SYNC_SETTINGS", "no permission to write the sync settings");
        }
        int callingUid = Binder.getCallingUid();
        Bundle bundle = new Bundle(syncRequest.getBundle());
        validateExtras(callingUid, bundle);
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
        try {
            SyncStorageEngine.EndPoint endPoint = new SyncStorageEngine.EndPoint(syncRequest.getAccount(), syncRequest.getProvider(), callingUserId);
            if (syncRequest.isPeriodic()) {
                SyncManager syncManager = getSyncManager();
                syncManager.getClass();
                Message obtainMessage = syncManager.mSyncHandler.obtainMessage(14, Pair.create(endPoint, "cancelRequest() by uid=" + callingUid));
                obtainMessage.setData(bundle);
                obtainMessage.sendToTarget();
            }
            getSyncManager().cancelScheduledSyncOperation(endPoint, bundle);
            getSyncManager().cancelActiveSync(endPoint, bundle, "API");
            IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void cancelSync(Account account, String str, ComponentName componentName) {
        cancelSyncAsUser(account, str, componentName, UserHandle.getCallingUserId());
    }

    public final void cancelSyncAsUser(Account account, String str, ComponentName componentName, int i) {
        if (str != null && str.length() == 0) {
            throw new IllegalArgumentException("Authority must be non-empty");
        }
        enforceCrossUserPermission(i, "no permission to modify the sync settings for user " + i);
        if (componentName != null) {
            Slog.e("ContentService", "cname not null.");
            return;
        }
        long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
        try {
            SyncStorageEngine.EndPoint endPoint = new SyncStorageEngine.EndPoint(account, str, i);
            getSyncManager().clearScheduledSyncOperations(endPoint);
            getSyncManager().cancelActiveSync(endPoint, null, "API");
        } finally {
            IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final synchronized void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i;
        if (DumpUtils.checkDumpAndUsageStatsPermission(this.mContext, "ContentService", printWriter)) {
            PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
            boolean contains = ArrayUtils.contains(strArr, "-a");
            long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
            try {
                SyncManager syncManager = this.mSyncManager;
                if (syncManager == null) {
                    indentingPrintWriter.println("SyncManager not available yet");
                } else {
                    syncManager.dump(indentingPrintWriter, contains);
                }
                indentingPrintWriter.println();
                indentingPrintWriter.println("Observer tree:");
                synchronized (this.mRootNode) {
                    try {
                        int[] iArr = new int[2];
                        final SparseIntArray sparseIntArray = new SparseIntArray();
                        this.mRootNode.dumpLocked(indentingPrintWriter, strArr, "", iArr, sparseIntArray);
                        indentingPrintWriter.println();
                        ArrayList arrayList = new ArrayList();
                        for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
                            arrayList.add(Integer.valueOf(sparseIntArray.keyAt(i2)));
                        }
                        Collections.sort(arrayList, new Comparator() { // from class: com.android.server.content.ContentService.2
                            @Override // java.util.Comparator
                            public final int compare(Object obj, Object obj2) {
                                int i3 = sparseIntArray.get(((Integer) obj).intValue());
                                int i4 = sparseIntArray.get(((Integer) obj2).intValue());
                                if (i3 < i4) {
                                    return 1;
                                }
                                return i3 > i4 ? -1 : 0;
                            }
                        });
                        for (int i3 = 0; i3 < arrayList.size(); i3++) {
                            int intValue = ((Integer) arrayList.get(i3)).intValue();
                            indentingPrintWriter.print("  pid ");
                            indentingPrintWriter.print(intValue);
                            indentingPrintWriter.print(": ");
                            indentingPrintWriter.print(sparseIntArray.get(intValue));
                            indentingPrintWriter.println(" observers");
                        }
                        indentingPrintWriter.println();
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.print("Total number of nodes: ");
                        indentingPrintWriter.println(iArr[0]);
                        indentingPrintWriter.print("Total number of observers: ");
                        indentingPrintWriter.println(iArr[1]);
                        sObserverDeathDispatcher.dump(indentingPrintWriter);
                        indentingPrintWriter.decreaseIndent();
                    } finally {
                    }
                }
                ArraySet arraySet = sObserverLeakDetectedUid;
                synchronized (arraySet) {
                    indentingPrintWriter.println();
                    indentingPrintWriter.print("Observer leaking UIDs: ");
                    indentingPrintWriter.println(arraySet.toString());
                }
                synchronized (this.mCache) {
                    try {
                        indentingPrintWriter.println();
                        indentingPrintWriter.println("Cached content:");
                        indentingPrintWriter.increaseIndent();
                        for (i = 0; i < this.mCache.size(); i++) {
                            indentingPrintWriter.println("User " + this.mCache.keyAt(i) + ":");
                            indentingPrintWriter.increaseIndent();
                            indentingPrintWriter.println(this.mCache.valueAt(i));
                            indentingPrintWriter.decreaseIndent();
                        }
                        indentingPrintWriter.decreaseIndent();
                    } finally {
                    }
                }
                IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public final void enforceCrossUserPermission(int i, String str) {
        if (UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", str);
        }
    }

    public final ArrayMap findOrCreateCacheLocked(int i, String str) {
        ArrayMap arrayMap = (ArrayMap) this.mCache.get(i);
        if (arrayMap == null) {
            arrayMap = new ArrayMap();
            this.mCache.put(i, arrayMap);
        }
        ArrayMap arrayMap2 = (ArrayMap) arrayMap.get(str);
        if (arrayMap2 != null) {
            return arrayMap2;
        }
        ArrayMap arrayMap3 = new ArrayMap();
        arrayMap.put(str, arrayMap3);
        return arrayMap3;
    }

    public final Bundle getCache(String str, Uri uri, int i) {
        Bundle bundle;
        if (UserHandle.getCallingUserId() != i && this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS") != 0) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "ContentService");
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.CACHE_CONTENT", "ContentService");
        ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class)).checkPackage(Binder.getCallingUid(), str);
        String providerPackageName = getProviderPackageName(i, uri);
        Pair create = Pair.create(str, uri);
        synchronized (this.mCache) {
            bundle = (Bundle) findOrCreateCacheLocked(i, providerPackageName).get(create);
        }
        return bundle;
    }

    public final List getCurrentSyncs() {
        return getCurrentSyncsAsUser(UserHandle.getCallingUserId());
    }

    public final List getCurrentSyncsAsUser(final int i) {
        enforceCrossUserPermission(i, "no permission to read the sync settings for user " + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_SYNC_STATS", "no permission to read the sync stats");
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.GET_ACCOUNTS") == 0;
        final int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
        try {
            List currentSyncsCopy = getSyncManager().mSyncStorageEngine.getCurrentSyncsCopy(i, z);
            IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            ((ArrayList) currentSyncsCopy).removeIf(new Predicate() { // from class: com.android.server.content.ContentService$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    ContentService contentService = ContentService.this;
                    int i2 = callingUid;
                    int i3 = i;
                    contentService.getClass();
                    return !contentService.hasAuthorityAccess(i2, i3, ((SyncInfo) obj).authority);
                }
            });
            return currentSyncsCopy;
        } catch (Throwable th) {
            IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int getIsSyncable(Account account, String str) {
        return getIsSyncableAsUser(account, str, UserHandle.getCallingUserId());
    }

    public final int getIsSyncableAsUser(Account account, String str, int i) {
        enforceCrossUserPermission(i, "no permission to read the sync settings for user " + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_SYNC_SETTINGS", "no permission to read the sync settings");
        int callingUid = Binder.getCallingUid();
        if (!hasAccountAccess(account, callingUid) || !hasAuthorityAccess(callingUid, i, str)) {
            return 0;
        }
        long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
        try {
            return getSyncManager().computeSyncable(account, i, str, false, false);
        } finally {
            IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean getMasterSyncAutomatically() {
        return getMasterSyncAutomaticallyAsUser(UserHandle.getCallingUserId());
    }

    public final boolean getMasterSyncAutomaticallyAsUser(int i) {
        enforceCrossUserPermission(i, "no permission to read the sync settings for user " + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_SYNC_SETTINGS", "no permission to read the sync settings");
        long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
        try {
            return getSyncManager().mSyncStorageEngine.getMasterSyncAutomatically(i);
        } finally {
            IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getPeriodicSyncs(Account account, String str, ComponentName componentName) {
        if (account == null) {
            throw new IllegalArgumentException("Account must not be null");
        }
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Authority must not be empty");
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_SYNC_SETTINGS", "no permission to read the sync settings");
        int callingUid = Binder.getCallingUid();
        int callingUserId = UserHandle.getCallingUserId();
        if (hasAccountAccess(account, callingUid) && hasAuthorityAccess(callingUid, callingUserId, str)) {
            long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
            try {
                return getSyncManager().getPeriodicSyncs(new SyncStorageEngine.EndPoint(account, str, callingUserId));
            } finally {
                IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return new ArrayList();
    }

    public final String getProviderPackageName(int i, Uri uri) {
        ProviderInfo resolveContentProviderAsUser = this.mContext.getPackageManager().resolveContentProviderAsUser(uri.getAuthority(), 0, i);
        if (resolveContentProviderAsUser != null) {
            return resolveContentProviderAsUser.packageName;
        }
        return null;
    }

    public final String getSyncAdapterPackageAsUser(String str, String str2, int i) {
        RegisteredServicesCache.ServiceInfo serviceInfo;
        enforceCrossUserPermission(i, "no permission to read sync settings for user " + i);
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
        try {
            SyncManager syncManager = getSyncManager();
            syncManager.getClass();
            String str3 = null;
            if (str != null && str2 != null && (serviceInfo = syncManager.mSyncAdapters.getServiceInfo(SyncAdapterType.newKey(str2, str), i)) != null) {
                String packageName = ((SyncAdapterType) serviceInfo.type).getPackageName();
                if (!TextUtils.isEmpty(packageName)) {
                    if (!syncManager.mPackageManagerInternal.filterAppAccess(callingUid, i, packageName, true)) {
                        str3 = packageName;
                    }
                }
            }
            return str3;
        } finally {
            IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String[] getSyncAdapterPackagesForAuthorityAsUser(String str, int i) {
        enforceCrossUserPermission(i, "no permission to read sync settings for user " + i);
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
        try {
            SyncManager syncManager = getSyncManager();
            String[] syncAdapterPackagesForAuthority = syncManager.mSyncAdapters.getSyncAdapterPackagesForAuthority(str, i);
            ArrayList arrayList = new ArrayList(syncAdapterPackagesForAuthority.length);
            for (String str2 : syncAdapterPackagesForAuthority) {
                if (!TextUtils.isEmpty(str2) && !syncManager.mPackageManagerInternal.filterAppAccess(callingUid, i, str2, true)) {
                    arrayList.add(str2);
                }
            }
            return (String[]) arrayList.toArray(new String[0]);
        } finally {
            IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final SyncAdapterType[] getSyncAdapterTypes() {
        return getSyncAdapterTypesAsUser(UserHandle.getCallingUserId());
    }

    public final SyncAdapterType[] getSyncAdapterTypesAsUser(int i) {
        enforceCrossUserPermission(i, "no permission to read sync settings for user " + i);
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
        try {
            return getSyncManager().getSyncAdapterTypes(callingUid, i);
        } finally {
            IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean getSyncAutomatically(Account account, String str) {
        return getSyncAutomaticallyAsUser(account, str, UserHandle.getCallingUserId());
    }

    public final boolean getSyncAutomaticallyAsUser(Account account, String str, int i) {
        enforceCrossUserPermission(i, "no permission to read the sync settings for user " + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_SYNC_SETTINGS", "no permission to read the sync settings");
        int callingUid = Binder.getCallingUid();
        if (!hasAccountAccess(account, callingUid) || !hasAuthorityAccess(callingUid, i, str)) {
            return false;
        }
        long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
        try {
            return getSyncManager().mSyncStorageEngine.getSyncAutomatically(account, str, i);
        } finally {
            IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final SyncManager getSyncManager() {
        SyncManager syncManager;
        synchronized (this.mSyncManagerLock) {
            try {
                if (this.mSyncManager == null) {
                    this.mSyncManager = new SyncManager(this.mContext, this.mFactoryTest);
                }
                syncManager = this.mSyncManager;
            } catch (Throwable th) {
                throw th;
            }
        }
        return syncManager;
    }

    public final SyncStatusInfo getSyncStatus(Account account, String str, ComponentName componentName) {
        return getSyncStatusAsUser(account, str, componentName, UserHandle.getCallingUserId());
    }

    public final SyncStatusInfo getSyncStatusAsUser(Account account, String str, ComponentName componentName, int i) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Authority must not be empty");
        }
        enforceCrossUserPermission(i, "no permission to read the sync stats for user " + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_SYNC_STATS", "no permission to read the sync stats");
        int callingUid = Binder.getCallingUid();
        if (!hasAccountAccess(account, callingUid) || !hasAuthorityAccess(callingUid, i, str)) {
            return null;
        }
        long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
        try {
            if (account == null || str == null) {
                throw new IllegalArgumentException("Must call sync status with valid authority");
            }
            return getSyncManager().mSyncStorageEngine.getStatusByAuthority(new SyncStorageEngine.EndPoint(account, str, i));
        } finally {
            IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int handleIncomingUser(Uri uri, int i, int i2, int i3, int i4) {
        if (i4 == -2) {
            i4 = ActivityManager.getCurrentUser();
        }
        int i5 = -1;
        if (i4 == -1) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "No access to " + uri);
        } else {
            if (i4 < 0) {
                throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i4, "Invalid user: "));
            }
            if (i4 != UserHandle.getCallingUserId()) {
                try {
                    i5 = ActivityManager.getService().checkUriPermission(uri, i, i2, i3, i4, (IBinder) null);
                } catch (RemoteException unused) {
                }
                if (i5 != 0) {
                    boolean z = true;
                    if (this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS") != 0) {
                        z = false;
                    }
                    if (!z) {
                        throw new SecurityException("No access to " + uri + ": neither user " + i2 + " nor current process has android.permission.INTERACT_ACROSS_USERS_FULL or android.permission.INTERACT_ACROSS_USERS");
                    }
                }
            }
        }
        return i4;
    }

    public final boolean hasAccountAccess(Account account, int i) {
        if (account == null || !CompatChanges.isChangeEnabled(201794303L, i)) {
            return true;
        }
        long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
        try {
            return this.mAccountManagerInternal.hasAccountAccess(account, i);
        } finally {
            IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean hasAuthorityAccess(int i, int i2, String str) {
        if (!TextUtils.isEmpty(str) && CompatChanges.isChangeEnabled(207133734L, i)) {
            return !ArrayUtils.isEmpty(getSyncAdapterPackagesForAuthorityAsUser(str, i2));
        }
        return true;
    }

    public final void invalidateCacheLocked(String str, Uri uri, int i) {
        ArrayMap arrayMap;
        ArrayMap arrayMap2 = (ArrayMap) this.mCache.get(i);
        if (arrayMap2 == null || (arrayMap = (ArrayMap) arrayMap2.get(str)) == null) {
            return;
        }
        if (uri == null) {
            arrayMap.clear();
            return;
        }
        int i2 = 0;
        while (i2 < arrayMap.size()) {
            Object obj = ((Pair) arrayMap.keyAt(i2)).second;
            if (obj == null || !((Uri) obj).toString().startsWith(uri.toString())) {
                i2++;
            } else {
                arrayMap.removeAt(i2);
            }
        }
    }

    public final boolean isSyncActive(Account account, String str, ComponentName componentName) {
        isSyncActive_enforcePermission();
        int callingUid = Binder.getCallingUid();
        int callingUserId = UserHandle.getCallingUserId();
        if (!hasAccountAccess(account, callingUid) || !hasAuthorityAccess(callingUid, callingUserId, str)) {
            return false;
        }
        long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
        try {
            return getSyncManager().mSyncStorageEngine.isSyncActive(new SyncStorageEngine.EndPoint(account, str, callingUserId));
        } finally {
            IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isSyncPending(Account account, String str, ComponentName componentName) {
        return isSyncPendingAsUser(account, str, componentName, UserHandle.getCallingUserId());
    }

    public final boolean isSyncPendingAsUser(Account account, String str, ComponentName componentName, int i) {
        isSyncPendingAsUser_enforcePermission();
        enforceCrossUserPermission(i, "no permission to retrieve the sync settings for user " + i);
        int callingUid = Binder.getCallingUid();
        if (!hasAccountAccess(account, callingUid) || !hasAuthorityAccess(callingUid, i, str)) {
            return false;
        }
        long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
        try {
            if (account == null || str == null) {
                throw new IllegalArgumentException("Invalid authority specified");
            }
            return getSyncManager().mSyncStorageEngine.isSyncPending(new SyncStorageEngine.EndPoint(account, str, i));
        } finally {
            IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void notifyChange(Uri[] uriArr, IContentObserver iContentObserver, boolean z, int i, int i2, int i3, String str) {
        String str2;
        boolean z2;
        ArrayMap arrayMap;
        int i4;
        String str3;
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        int callingUserId = UserHandle.getCallingUserId();
        ObserverCollector observerCollector = new ObserverCollector();
        ArrayMap arrayMap2 = new ArrayMap();
        int length = uriArr.length;
        int i5 = 0;
        while (true) {
            boolean z3 = true;
            if (i5 >= length) {
                long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
                try {
                    observerCollector.dispatch();
                    SyncManager syncManager = getSyncManager();
                    int i6 = 0;
                    while (i6 < arrayMap2.size()) {
                        String str4 = (String) ((Pair) arrayMap2.keyAt(i6)).first;
                        int intValue = ((Integer) ((Pair) arrayMap2.keyAt(i6)).second).intValue();
                        String str5 = (String) arrayMap2.valueAt(i6);
                        if ((i & 1) != 0) {
                            int syncExemptionAndCleanUpExtrasForCaller = getSyncExemptionAndCleanUpExtrasForCaller(callingUid, null);
                            syncManager.getClass();
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("upload", z3);
                            str2 = str5;
                            z2 = z3;
                            arrayMap = arrayMap2;
                            i4 = callingUid;
                            syncManager.scheduleSync(null, callingUserId, callingUid, str4, bundle, -2, SyncManager.LOCAL_SYNC_DELAY, true, syncExemptionAndCleanUpExtrasForCaller, i4, callingPid, str);
                        } else {
                            str2 = str5;
                            z2 = z3;
                            arrayMap = arrayMap2;
                            i4 = callingUid;
                        }
                        synchronized (this.mCache) {
                            try {
                                int length2 = uriArr.length;
                                int i7 = 0;
                                while (i7 < length2) {
                                    Uri uri = uriArr[i7];
                                    if (Objects.equals(uri.getAuthority(), str4)) {
                                        str3 = str2;
                                        invalidateCacheLocked(str3, uri, intValue);
                                    } else {
                                        str3 = str2;
                                    }
                                    i7++;
                                    str2 = str3;
                                }
                            } finally {
                            }
                        }
                        i6++;
                        z3 = z2;
                        arrayMap2 = arrayMap;
                        callingUid = i4;
                    }
                    return;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            Uri uri2 = uriArr[i5];
            int handleIncomingUser = handleIncomingUser(uri2, callingPid, callingUid, 2, i2);
            Pair create = Pair.create(uri2.getAuthority(), Integer.valueOf(handleIncomingUser));
            if (!arrayMap2.containsKey(create)) {
                String checkContentProviderAccess = ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).checkContentProviderAccess(uri2.getAuthority(), handleIncomingUser);
                if (checkContentProviderAccess != null) {
                    if (i3 >= 26) {
                        if (!SemDualAppManager.isDualAppId(handleIncomingUser)) {
                            throw new SecurityException(checkContentProviderAccess);
                        }
                        Log.w("ContentService", "Ignoring notify for " + uri2 + " from " + callingUid + ": " + checkContentProviderAccess);
                        return;
                    }
                    if (!checkContentProviderAccess.startsWith("Failed to find provider")) {
                        Log.w("ContentService", "Ignoring notify for " + uri2 + " from " + callingUid + ": " + checkContentProviderAccess);
                        i5++;
                    }
                }
                arrayMap2.put(create, getProviderPackageName(handleIncomingUser, uri2));
            }
            synchronized (this.mRootNode) {
                this.mRootNode.collectObserversLocked(uri2, uri2.getPathSegments().size() + 1, 0, iContentObserver, z, i, handleIncomingUser, observerCollector);
            }
            i5++;
        }
    }

    public final void onDbCorruption(String str, String str2, String str3) {
        Slog.e(str, str2);
        Slog.e(str, "at " + str3);
        Slog.wtf(str, str2);
    }

    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        new ContentShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public final void putCache(String str, Uri uri, Bundle bundle, int i) {
        Bundle.setDefusable(bundle, true);
        if (UserHandle.getCallingUserId() != i && this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS") != 0) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "ContentService");
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.CACHE_CONTENT", "ContentService");
        ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class)).checkPackage(Binder.getCallingUid(), str);
        String providerPackageName = getProviderPackageName(i, uri);
        Pair create = Pair.create(str, uri);
        synchronized (this.mCache) {
            try {
                ArrayMap findOrCreateCacheLocked = findOrCreateCacheLocked(i, providerPackageName);
                if (bundle != null) {
                    findOrCreateCacheLocked.put(create, bundle);
                } else {
                    findOrCreateCacheLocked.remove(create);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerContentObserver(Uri uri, boolean z, IContentObserver iContentObserver, int i, int i2) {
        if (iContentObserver == null || uri == null) {
            throw new IllegalArgumentException("You must pass a valid uri and observer");
        }
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        int handleIncomingUser = handleIncomingUser(uri, callingPid, callingUid, 1, i);
        int i3 = (!SemDualAppManager.isDualAppId(handleIncomingUser) || (SemDualAppManager.shouldAddUserId(uri, handleIncomingUser) && this.mContext.getPackageManager().resolveContentProviderAsUser(uri.getAuthority(), 0, handleIncomingUser) != null)) ? handleIncomingUser : 0;
        String checkContentProviderAccess = ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).checkContentProviderAccess(uri.getAuthority(), i3);
        if (checkContentProviderAccess != null) {
            if (i2 >= 26) {
                throw new SecurityException(checkContentProviderAccess);
            }
            if (!checkContentProviderAccess.startsWith("Failed to find provider")) {
                Log.w("ContentService", "Ignoring content changes for " + uri + " from " + callingUid + ": " + checkContentProviderAccess);
                return;
            }
        }
        synchronized (this.mRootNode) {
            ObserverNode observerNode = this.mRootNode;
            observerNode.addObserverLocked(uri, 0, iContentObserver, z, observerNode, callingUid, callingPid, i3);
        }
    }

    public final void removePeriodicSync(Account account, String str, Bundle bundle) {
        Bundle.setDefusable(bundle, true);
        if (account == null) {
            throw new IllegalArgumentException("Account must not be null");
        }
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Authority must not be empty");
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SYNC_SETTINGS", "no permission to write the sync settings");
        int callingUid = Binder.getCallingUid();
        int callingUserId = UserHandle.getCallingUserId();
        if (hasAccountAccess(account, callingUid) && hasAuthorityAccess(callingUid, callingUserId, str)) {
            validateExtras(callingUid, bundle);
            long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
            try {
                SyncManager syncManager = getSyncManager();
                syncManager.getClass();
                Message obtainMessage = syncManager.mSyncHandler.obtainMessage(14, Pair.create(new SyncStorageEngine.EndPoint(account, str, callingUserId), "removePeriodicSync() by uid=" + callingUid));
                obtainMessage.setData(bundle);
                obtainMessage.sendToTarget();
            } finally {
                IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void removeStatusChangeListener(ISyncStatusObserver iSyncStatusObserver) {
        long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
        if (iSyncStatusObserver != null) {
            try {
                SyncStorageEngine syncStorageEngine = getSyncManager().mSyncStorageEngine;
                synchronized (syncStorageEngine.mAuthorities) {
                    syncStorageEngine.mChangeListeners.unregister(iSyncStatusObserver);
                }
            } finally {
                IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void requestSync(Account account, String str, Bundle bundle, String str2) {
        Bundle.setDefusable(bundle, true);
        ContentResolver.validateSyncExtrasBundle(bundle);
        int callingUserId = UserHandle.getCallingUserId();
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if (hasAccountAccess(account, callingUid) && hasAuthorityAccess(callingUid, callingUserId, str)) {
            validateExtras(callingUid, bundle);
            int syncExemptionAndCleanUpExtrasForCaller = getSyncExemptionAndCleanUpExtrasForCaller(callingUid, bundle);
            long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
            try {
                getSyncManager().scheduleSync(account, callingUserId, callingUid, str, bundle, -2, syncExemptionAndCleanUpExtrasForCaller, callingUid, callingPid, str2);
            } finally {
                IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void resetTodayStats() {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 2000 && callingUid != 0) {
            throw new SecurityException("Non-shell user attempted to call resetTodayStats");
        }
        if (this.mSyncManager != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mSyncManager.mSyncStorageEngine.resetTodayStats(true);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void setIsSyncable(Account account, String str, int i) {
        setIsSyncableAsUser(account, str, i, UserHandle.getCallingUserId());
    }

    public final void setIsSyncableAsUser(Account account, String str, int i, int i2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Authority must not be empty");
        }
        enforceCrossUserPermission(i2, "no permission to set the sync settings for user " + i2);
        this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SYNC_SETTINGS", "no permission to write the sync settings");
        int i3 = i > 0 ? 1 : i == 0 ? 0 : -2;
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if (hasAccountAccess(account, callingUid) && hasAuthorityAccess(callingUid, i2, str)) {
            long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
            try {
                getSyncManager().mSyncStorageEngine.setIsSyncable(account, i2, str, i3, callingUid, callingPid);
            } finally {
                IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void setMasterSyncAutomatically(boolean z) {
        setMasterSyncAutomaticallyAsUser(z, UserHandle.getCallingUserId());
    }

    public final void setMasterSyncAutomaticallyAsUser(boolean z, int i) {
        String telephonyProperty;
        enforceCrossUserPermission(i, "no permission to set the sync status for user " + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SYNC_SETTINGS", "no permission to write the sync settings");
        try {
            IRoamingPolicy asInterface = IRoamingPolicy.Stub.asInterface(ServiceManager.getService("roaming_policy"));
            TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
            ContextInfo contextInfo = new ContextInfo(i);
            boolean z2 = SystemProperties.getInt("ro.multisim.simslotcount", 1) > 1;
            if (asInterface != null) {
                if (telephonyManager != null && telephonyManager.isNetworkRoaming() && !asInterface.isRoamingSyncEnabled(contextInfo) && !z2) {
                    Log.v("ContentService", "setMasterSyncAutomaticallyAsUser() : policy restricts sync by network-roaming.");
                    z = false;
                }
                if (z2 && (telephonyProperty = TelephonyManager.getTelephonyProperty(SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId()), "gsm.operator.isroaming", "false")) != null && telephonyProperty.equals("true") && !asInterface.isRoamingSyncEnabled(contextInfo)) {
                    Log.v("ContentService", "setMasterSyncAutomaticallyAsUser() : policy restricts sync by network-roaming. (dual-sim)");
                    z = false;
                }
                IRestrictionPolicy asInterface2 = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
                if (asInterface2 != null && !asInterface2.isSettingsChangesAllowed(contextInfo, true)) {
                    Log.v("ContentService", "setMasterSyncAutomaticallyAsUser() : policy restricts changed settings.");
                    return;
                }
            }
        } catch (RemoteException unused) {
        }
        boolean z3 = z;
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
        try {
            getSyncManager().mSyncStorageEngine.setMasterSyncAutomatically(i, getSyncExemptionAndCleanUpExtrasForCaller(callingUid, null), callingUid, callingPid, z3);
        } finally {
            IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setSyncAutomatically(Account account, String str, boolean z) {
        setSyncAutomaticallyAsUser(account, str, z, UserHandle.getCallingUserId());
    }

    public final void setSyncAutomaticallyAsUser(Account account, String str, boolean z, int i) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Authority must be non-empty");
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SYNC_SETTINGS", "no permission to write the sync settings");
        enforceCrossUserPermission(i, "no permission to modify the sync settings for user " + i);
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if (hasAccountAccess(account, callingUid) && hasAuthorityAccess(callingUid, i, str)) {
            int syncExemptionAndCleanUpExtrasForCaller = getSyncExemptionAndCleanUpExtrasForCaller(callingUid, null);
            if ("com.google".equals(account.type)) {
                try {
                    IRestrictionPolicy asInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
                    if (asInterface != null && !asInterface.isGoogleAccountsAutoSyncAllowedAsUser(i)) {
                        Log.v("ContentService", "policy restricts sync of google account");
                        return;
                    }
                } catch (RemoteException unused) {
                }
            }
            long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
            try {
                getSyncManager().mSyncStorageEngine.setSyncAutomatically(account, i, str, z, syncExemptionAndCleanUpExtrasForCaller, callingUid, callingPid);
            } finally {
                IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void sync(SyncRequest syncRequest, String str) {
        syncAsUser(syncRequest, UserHandle.getCallingUserId(), str);
    }

    public final void syncAsUser(SyncRequest syncRequest, int i, String str) {
        enforceCrossUserPermission(i, "no permission to request sync as user: " + i);
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if (hasAccountAccess(syncRequest.getAccount(), callingUid) && hasAuthorityAccess(callingUid, i, syncRequest.getProvider())) {
            Bundle bundle = syncRequest.getBundle();
            validateExtras(callingUid, bundle);
            int syncExemptionAndCleanUpExtrasForCaller = getSyncExemptionAndCleanUpExtrasForCaller(callingUid, bundle);
            long clearCallingIdentity = IContentService.Stub.clearCallingIdentity();
            try {
                long syncFlexTime = syncRequest.getSyncFlexTime();
                long syncRunTime = syncRequest.getSyncRunTime();
                if (syncRequest.isPeriodic()) {
                    this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SYNC_SETTINGS", "no permission to write the sync settings");
                    getSyncManager().updateOrAddPeriodicSync(new SyncStorageEngine.EndPoint(syncRequest.getAccount(), syncRequest.getProvider(), i), clampPeriod(syncRunTime), syncFlexTime, bundle);
                } else {
                    getSyncManager().scheduleSync(syncRequest.getAccount(), i, callingUid, syncRequest.getProvider(), bundle, -2, syncExemptionAndCleanUpExtrasForCaller, callingUid, callingPid, str);
                }
                IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public final void unregisterContentObserver(IContentObserver iContentObserver) {
        if (iContentObserver == null) {
            throw new IllegalArgumentException("You must pass a valid observer");
        }
        synchronized (this.mRootNode) {
            this.mRootNode.removeObserverLocked(iContentObserver);
        }
    }
}
