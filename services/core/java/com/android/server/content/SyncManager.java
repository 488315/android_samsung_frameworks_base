package com.android.server.content;

import android.R;
import android.accounts.Account;
import android.accounts.AccountAndUser;
import android.accounts.AccountManager;
import android.accounts.AccountManagerInternal;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ISyncAdapter;
import android.content.ISyncAdapterUnsyncableAccountCallback;
import android.content.ISyncContext;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.PeriodicSync;
import android.content.ServiceConnection;
import android.content.SyncAdapterType;
import android.content.SyncAdaptersCache;
import android.content.SyncInfo;
import android.content.SyncResult;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.RegisteredServicesCache;
import android.content.pm.RegisteredServicesCacheListener;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.net.ConnectivityManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.WorkSource;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.TimeMigrationUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseBooleanArray;
import com.android.internal.app.IBatteryStats;
import com.android.internal.config.appcloning.AppCloningDeviceConfigHelper;
import com.android.internal.content.PackageMonitor;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ArrayUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService;
import com.android.server.backup.AccountSyncSettingsBackupHelper;
import com.android.server.content.SyncLogger;
import com.android.server.content.SyncStorageEngine;
import com.android.server.job.JobSchedulerInternal;
import com.android.server.pm.PackageManagerService;
import com.google.android.collect.Lists;
import com.google.android.collect.Maps;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SyncManager {
    public static SyncManager sInstance;
    public static final SyncManager$$ExternalSyntheticLambda0 sOpDumpComparator;
    public static final SyncManager$$ExternalSyntheticLambda0 sOpRuntimeComparator;
    public final AccountManager mAccountManager;
    public final AccountManagerInternal mAccountManagerInternal;
    public final AnonymousClass2 mAccountsUpdatedReceiver;
    public final ActivityManagerInternal mAmi;
    public final AppCloningDeviceConfigHelper mAppCloningDeviceConfigHelper;
    public final IBatteryStats mBatteryStats;
    public ConnectivityManager mConnManagerDoNotUseDirectly;
    public final AnonymousClass2 mConnectivityIntentReceiver;
    public final SyncManagerConstants mConstants;
    public final Context mContext;
    public JobScheduler mJobScheduler;
    public final SyncLogger mLogger;
    public final NotificationManager mNotificationMgr;
    public final AnonymousClass2 mOtherIntentsReceiver;
    public final PackageManagerInternal mPackageManagerInternal;
    public final PowerManager mPowerManager;
    public volatile boolean mProvisioned;
    public final AnonymousClass2 mShutdownIntentReceiver;
    public final SyncAdaptersCache mSyncAdapters;
    public final SyncHandler mSyncHandler;
    public volatile PowerManager.WakeLock mSyncManagerWakeLock;
    public final SyncStorageEngine mSyncStorageEngine;
    public final SparseBooleanArray mUnlockedUsers;
    public final AnonymousClass2 mUserIntentReceiver;
    public final UserManager mUserManager;
    public static final boolean ENABLE_SUSPICIOUS_CHECK = Build.IS_DEBUGGABLE;
    public static final long LOCAL_SYNC_DELAY = SystemProperties.getLong("sync.local_sync_delay", 30000);
    public static final Context.BindServiceFlags SYNC_ADAPTER_CONNECTION_FLAGS = Context.BindServiceFlags.of(21);
    public static final AccountAndUser[] INITIAL_ACCOUNTS_ARRAY = new AccountAndUser[0];
    public final Object mAccountsLock = new Object();
    public volatile AccountAndUser[] mRunningAccounts = INITIAL_ACCOUNTS_ARRAY;
    public volatile boolean mDataConnectionIsConnected = false;
    public volatile int mNextJobId = 0;
    public final ArrayList mActiveSyncContexts = Lists.newArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.content.SyncManager$12, reason: invalid class name */
    public final class AnonymousClass12 implements Comparator {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ AnonymousClass12(int i) {
            this.$r8$classId = i;
        }

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            switch (this.$r8$classId) {
                case 0:
                    return ((SyncAdapterType) ((RegisteredServicesCache.ServiceInfo) obj).type).authority.compareTo(((SyncAdapterType) ((RegisteredServicesCache.ServiceInfo) obj2).type).authority);
                case 1:
                    AuthoritySyncStats authoritySyncStats = (AuthoritySyncStats) obj;
                    AuthoritySyncStats authoritySyncStats2 = (AuthoritySyncStats) obj2;
                    int compare = Integer.compare(authoritySyncStats2.times, authoritySyncStats.times);
                    return compare == 0 ? Long.compare(authoritySyncStats2.elapsedTime, authoritySyncStats.elapsedTime) : compare;
                default:
                    AccountSyncStats accountSyncStats = (AccountSyncStats) obj;
                    AccountSyncStats accountSyncStats2 = (AccountSyncStats) obj2;
                    int compare2 = Integer.compare(accountSyncStats2.times, accountSyncStats.times);
                    return compare2 == 0 ? Long.compare(accountSyncStats2.elapsedTime, accountSyncStats.elapsedTime) : compare2;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.content.SyncManager$7, reason: invalid class name */
    public final class AnonymousClass7 {
        public /* synthetic */ AnonymousClass7() {
        }

        public void onAuthorityRemoved(SyncStorageEngine.EndPoint endPoint) {
            SyncManager syncManager = SyncManager.this;
            SyncLogger syncLogger = syncManager.mLogger;
            syncLogger.log("removeSyncsForAuthority: ", endPoint, "onAuthorityRemoved");
            syncManager.verifyJobScheduler();
            Iterator it = ((ArrayList) syncManager.getAllPendingSyncs()).iterator();
            while (it.hasNext()) {
                SyncOperation syncOperation = (SyncOperation) it.next();
                if (syncOperation.target.matchesSpec(endPoint)) {
                    syncLogger.log("canceling: ", syncOperation);
                    syncManager.cancelJob(syncOperation, "onAuthorityRemoved");
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AccountSyncStats {
        public long elapsedTime;
        public String name;
        public int times;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ActiveSyncContext extends ISyncContext.Stub implements ServiceConnection, IBinder.DeathRecipient {
        public boolean mBound;
        public long mBytesTransferredAtLastPoll;
        public String mEventName;
        public final long mHistoryRowId;
        public long mLastPolledTimeElapsed;
        public final long mStartTime;
        public final int mSyncAdapterUid;
        public SyncInfo mSyncInfo;
        public final SyncOperation mSyncOperation;
        public final PowerManager.WakeLock mSyncWakeLock;
        public final long mTimeoutStartTime;
        public boolean mIsLinkedToDeath = false;
        public ISyncAdapter mSyncAdapter = null;

        public ActiveSyncContext(SyncOperation syncOperation, long j, int i) {
            this.mSyncAdapterUid = i;
            this.mSyncOperation = syncOperation;
            this.mHistoryRowId = j;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.mStartTime = elapsedRealtime;
            this.mTimeoutStartTime = elapsedRealtime;
            SyncHandler syncHandler = SyncManager.this.mSyncHandler;
            syncHandler.getClass();
            String wakeLockName = syncOperation.wakeLockName();
            PowerManager.WakeLock wakeLock = (PowerManager.WakeLock) syncHandler.mWakeLocks.get(wakeLockName);
            if (wakeLock == null) {
                wakeLock = SyncManager.this.mPowerManager.newWakeLock(1, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("*sync*/", wakeLockName));
                wakeLock.setReferenceCounted(false);
                syncHandler.mWakeLocks.put(wakeLockName, wakeLock);
            }
            this.mSyncWakeLock = wakeLock;
            wakeLock.setWorkSource(new WorkSource(i));
            wakeLock.acquire();
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            SyncManager.m382$$Nest$msendSyncFinishedOrCanceledMessage(SyncManager.this, this, null);
        }

        public final void onFinished(SyncResult syncResult) {
            if (Log.isLoggable("SyncManager", 2)) {
                Slog.v("SyncManager", "onFinished: " + this);
            }
            SyncLogger syncLogger = SyncManager.this.mLogger;
            SyncOperation syncOperation = this.mSyncOperation;
            syncLogger.log("onFinished result=", syncResult, " endpoint=", syncOperation == null ? "null" : syncOperation.target);
            SyncManager.m382$$Nest$msendSyncFinishedOrCanceledMessage(SyncManager.this, this, syncResult);
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Message obtainMessage = SyncManager.this.mSyncHandler.obtainMessage();
            obtainMessage.what = 4;
            SyncManager syncManager = SyncManager.this;
            obtainMessage.obj = new ServiceConnectionData(this, iBinder);
            syncManager.mSyncHandler.sendMessage(obtainMessage);
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            Message obtainMessage = SyncManager.this.mSyncHandler.obtainMessage();
            obtainMessage.what = 5;
            SyncManager syncManager = SyncManager.this;
            obtainMessage.obj = new ServiceConnectionData(this, null);
            syncManager.mSyncHandler.sendMessage(obtainMessage);
        }

        public final void sendHeartbeat() {
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            toString(sb, false);
            return sb.toString();
        }

        public final void toString(StringBuilder sb, boolean z) {
            sb.append("startTime ");
            sb.append(this.mStartTime);
            sb.append(", mTimeoutStartTime ");
            sb.append(this.mTimeoutStartTime);
            sb.append(", mHistoryRowId ");
            sb.append(this.mHistoryRowId);
            sb.append(", syncOperation ");
            SyncOperation syncOperation = this.mSyncOperation;
            String str = syncOperation;
            if (z) {
                str = syncOperation == null ? "[null]" : syncOperation.dump(null, true, null, true);
            }
            sb.append((Object) str);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AuthoritySyncStats {
        public Map accountMap;
        public long elapsedTime;
        public String name;
        public int times;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OnUnsyncableAccountCheck implements ServiceConnection {
        public final SyncManager$$ExternalSyntheticLambda5 mOnReadyCallback;
        public final RegisteredServicesCache.ServiceInfo mSyncAdapterInfo;

        public OnUnsyncableAccountCheck(RegisteredServicesCache.ServiceInfo serviceInfo, SyncManager$$ExternalSyntheticLambda5 syncManager$$ExternalSyntheticLambda5) {
            this.mSyncAdapterInfo = serviceInfo;
            this.mOnReadyCallback = syncManager$$ExternalSyntheticLambda5;
        }

        public final void onReady() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mOnReadyCallback.onReady();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                ISyncAdapter.Stub.asInterface(iBinder).onUnsyncableAccount(new ISyncAdapterUnsyncableAccountCallback.Stub() { // from class: com.android.server.content.SyncManager.OnUnsyncableAccountCheck.1
                    public final void onUnsyncableAccountDone(boolean z) {
                        if (z) {
                            OnUnsyncableAccountCheck.this.onReady();
                        }
                    }
                });
            } catch (RemoteException e) {
                Slog.e("SyncManager", "Could not call onUnsyncableAccountDone " + this.mSyncAdapterInfo, e);
                onReady();
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageMonitorImpl extends PackageMonitor {
        public final boolean onHandleForceStop(Intent intent, String[] strArr, int i, boolean z, Bundle bundle) {
            if (!Log.isLoggable("SyncManager", 3)) {
                return false;
            }
            Log.d("SyncManager", "Package force-stopped: " + Arrays.toString(strArr) + ", uid: " + i);
            return false;
        }

        public final void onPackageUnstopped(String str, int i, Bundle bundle) {
            if (Log.isLoggable("SyncManager", 3)) {
                NetworkScoreService$$ExternalSyntheticOutline0.m(i, "Package unstopped: ", str, ", uid: ", "SyncManager");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PrintTable {
        public final ArrayList mTable = Lists.newArrayList();
        public final int mCols = 16;

        public static void printRow(PrintWriter printWriter, String[] strArr, Object[] objArr) {
            int length = objArr.length;
            for (int i = 0; i < length; i++) {
                printWriter.printf(String.format(strArr[i], objArr[i].toString()), new Object[0]);
                printWriter.print("  ");
            }
            printWriter.println();
        }

        public final void set(int i, int i2, Object... objArr) {
            int i3;
            int length = objArr.length + i2;
            int i4 = this.mCols;
            if (length > i4) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i4, "Table only has ", " columns. can't set ");
                m.append(objArr.length);
                m.append(" at column ");
                m.append(i2);
                throw new IndexOutOfBoundsException(m.toString());
            }
            int size = this.mTable.size();
            while (true) {
                i3 = 0;
                if (size > i) {
                    break;
                }
                String[] strArr = new String[i4];
                this.mTable.add(strArr);
                while (i3 < i4) {
                    strArr[i3] = "";
                    i3++;
                }
                size++;
            }
            String[] strArr2 = (String[]) this.mTable.get(i);
            while (i3 < objArr.length) {
                Object obj = objArr[i3];
                strArr2[i2 + i3] = obj == null ? "" : obj.toString();
                i3++;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ScheduleSyncMessagePayload {
        public final long minDelayMillis;
        public final SyncOperation syncOperation;

        public ScheduleSyncMessagePayload(SyncOperation syncOperation, long j) {
            this.syncOperation = syncOperation;
            this.minDelayMillis = j;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ServiceConnectionData {
        public final ActiveSyncContext activeSyncContext;
        public final IBinder adapter;

        public ServiceConnectionData(ActiveSyncContext activeSyncContext, IBinder iBinder) {
            this.activeSyncContext = activeSyncContext;
            this.adapter = iBinder;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SyncFinishedOrCancelledMessagePayload {
        public final ActiveSyncContext activeSyncContext;
        public final SyncResult syncResult;

        public SyncFinishedOrCancelledMessagePayload(SyncResult syncResult, ActiveSyncContext activeSyncContext) {
            this.activeSyncContext = activeSyncContext;
            this.syncResult = syncResult;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SyncHandler extends Handler {
        public final SyncTimeTracker mSyncTimeTracker;
        public final HashMap mWakeLocks;

        public SyncHandler(Looper looper) {
            super(looper);
            this.mSyncTimeTracker = SyncManager.this.new SyncTimeTracker();
            this.mWakeLocks = Maps.newHashMap();
        }

        public final void cancelActiveSyncH(SyncStorageEngine.EndPoint endPoint, Bundle bundle) {
            Iterator it = new ArrayList(SyncManager.this.mActiveSyncContexts).iterator();
            while (it.hasNext()) {
                ActiveSyncContext activeSyncContext = (ActiveSyncContext) it.next();
                if (activeSyncContext != null && activeSyncContext.mSyncOperation.target.matchesSpec(endPoint) && (bundle == null || SyncManager.syncExtrasEquals(activeSyncContext.mSyncOperation.mImmutableExtras, bundle, false))) {
                    SyncJobService.callJobFinished(activeSyncContext.mSyncOperation.jobId, "MESSAGE_CANCEL");
                    runSyncFinishedOrCanceledH(null, activeSyncContext);
                }
            }
        }

        public final void closeActiveSyncContext(ActiveSyncContext activeSyncContext) {
            ArrayList arrayList;
            activeSyncContext.getClass();
            if (Log.isLoggable("SyncManager", 2)) {
                Log.d("SyncManager", "unBindFromSyncAdapter: connection " + activeSyncContext);
            }
            if (activeSyncContext.mBound) {
                activeSyncContext.mBound = false;
                SyncManager.this.mLogger.log("unbindService for ", activeSyncContext);
                SyncManager.this.mContext.unbindService(activeSyncContext);
                try {
                    SyncManager.this.mBatteryStats.noteSyncFinish(activeSyncContext.mEventName, activeSyncContext.mSyncAdapterUid);
                } catch (RemoteException unused) {
                }
            }
            activeSyncContext.mSyncWakeLock.release();
            String str = null;
            activeSyncContext.mSyncWakeLock.setWorkSource(null);
            SyncManager.this.mActiveSyncContexts.remove(activeSyncContext);
            SyncStorageEngine syncStorageEngine = SyncManager.this.mSyncStorageEngine;
            SyncInfo syncInfo = activeSyncContext.mSyncInfo;
            int i = activeSyncContext.mSyncOperation.target.userId;
            synchronized (syncStorageEngine.mAuthorities) {
                try {
                    if (Log.isLoggable("SyncManager", 2)) {
                        Slog.v("SyncManager", "removeActiveSync: account=" + syncInfo.account + " user=" + i + " auth=" + syncInfo.authority);
                    }
                    synchronized (syncStorageEngine.mAuthorities) {
                        arrayList = (ArrayList) syncStorageEngine.mCurrentSyncs.get(i);
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                            syncStorageEngine.mCurrentSyncs.put(i, arrayList);
                        }
                    }
                    arrayList.remove(syncInfo);
                } catch (Throwable th) {
                    throw th;
                }
            }
            Account account = syncInfo.account;
            String str2 = syncInfo.authority;
            if (account != null && str2 != null) {
                str = ContentResolver.getSyncAdapterPackageAsUser(account.type, str2, i);
            }
            syncStorageEngine.reportChange(4, i, str);
            if (Log.isLoggable("SyncManager", 2)) {
                Slog.v("SyncManager", "removing all MESSAGE_MONITOR_SYNC & MESSAGE_SYNC_EXPIRED for " + activeSyncContext.toString());
            }
            SyncManager.this.mSyncHandler.removeMessages(8, activeSyncContext);
            SyncManager.this.mLogger.log("closeActiveSyncContext: ", activeSyncContext);
        }

        public final int computeSyncOpState(SyncOperation syncOperation) {
            boolean z;
            boolean isLoggable = Log.isLoggable("SyncManager", 2);
            SyncStorageEngine.EndPoint endPoint = syncOperation.target;
            synchronized (SyncManager.this.mAccountsLock) {
                try {
                    if (!SyncManager.m377$$Nest$mcontainsAccountAndUser(SyncManager.this, SyncManager.this.mRunningAccounts, endPoint.account, endPoint.userId)) {
                        if (isLoggable) {
                            Slog.v("SyncManager", "    Dropping sync operation: account doesn't exist.");
                        }
                        Slog.wtf("SyncManager", "SYNC_OP_STATE_INVALID: account doesn't exist.");
                        return 3;
                    }
                    int computeSyncable = SyncManager.this.computeSyncable(endPoint.account, endPoint.userId, endPoint.provider, true, true);
                    if (computeSyncable == 3) {
                        if (isLoggable) {
                            Slog.v("SyncManager", "    Dropping sync operation: isSyncable == SYNCABLE_NO_ACCOUNT_ACCESS");
                        }
                        Slog.wtf("SyncManager", "SYNC_OP_STATE_INVALID_NO_ACCOUNT_ACCESS");
                        return 2;
                    }
                    if (computeSyncable == 0) {
                        if (isLoggable) {
                            Slog.v("SyncManager", "    Dropping sync operation: isSyncable == NOT_SYNCABLE");
                        }
                        Slog.wtf("SyncManager", "SYNC_OP_STATE_INVALID: NOT_SYNCABLE");
                        return 4;
                    }
                    boolean z2 = true;
                    if (SyncManager.this.mSyncStorageEngine.getMasterSyncAutomatically(endPoint.userId)) {
                        if (SyncManager.this.mSyncStorageEngine.getSyncAutomatically(endPoint.account, endPoint.provider, endPoint.userId)) {
                            z = true;
                            if (!syncOperation.mImmutableExtras.getBoolean("ignore_settings", false) && computeSyncable >= 0) {
                                z2 = false;
                            }
                            if (!z || z2) {
                                return 0;
                            }
                            if (isLoggable) {
                                Slog.v("SyncManager", "    Dropping sync operation: disallowed by settings/network.");
                            }
                            Slog.wtf("SyncManager", "SYNC_OP_STATE_INVALID: disallowed by settings/network");
                            return 5;
                        }
                    }
                    z = false;
                    if (!syncOperation.mImmutableExtras.getBoolean("ignore_settings", false)) {
                        z2 = false;
                    }
                    if (z) {
                    }
                    return 0;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void deferSyncH(SyncOperation syncOperation, long j, String str) {
            SyncManager syncManager = SyncManager.this;
            syncManager.mLogger.log("deferSyncH() ", syncOperation.isPeriodic ? "periodic " : "", "sync.  op=", syncOperation, " delay=", Long.valueOf(j), " why=", str);
            SyncJobService.callJobFinished(syncOperation.jobId, str);
            if (syncOperation.isPeriodic) {
                syncManager.scheduleSyncOperationH(syncOperation.createOneTimeSyncOperation(), j);
            } else {
                syncManager.cancelJob(syncOperation, "deferSyncH()");
                syncManager.scheduleSyncOperationH(syncOperation, j);
            }
        }

        public final ActiveSyncContext findActiveSyncContextH(int i) {
            Iterator it = SyncManager.this.mActiveSyncContexts.iterator();
            while (it.hasNext()) {
                ActiveSyncContext activeSyncContext = (ActiveSyncContext) it.next();
                SyncOperation syncOperation = activeSyncContext.mSyncOperation;
                if (syncOperation != null && syncOperation.jobId == i) {
                    return activeSyncContext;
                }
            }
            return null;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            SyncManager.this.mSyncManagerWakeLock.acquire();
            try {
                handleSyncMessage(message);
            } finally {
                SyncManager.this.mSyncManagerWakeLock.release();
            }
        }

        public final void handleSyncMessage(Message message) {
            boolean isLoggable = Log.isLoggable("SyncManager", 2);
            try {
                SyncManager syncManager = SyncManager.this;
                NetworkInfo activeNetworkInfo = syncManager.getConnectivityManager().getActiveNetworkInfo();
                boolean z = true;
                syncManager.mDataConnectionIsConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
                int i = message.what;
                if (i == 1) {
                    SyncFinishedOrCancelledMessagePayload syncFinishedOrCancelledMessagePayload = (SyncFinishedOrCancelledMessagePayload) message.obj;
                    if (SyncManager.m379$$Nest$misSyncStillActiveH(SyncManager.this, syncFinishedOrCancelledMessagePayload.activeSyncContext)) {
                        if (isLoggable) {
                            Slog.v("SyncManager", "syncFinished" + syncFinishedOrCancelledMessagePayload.activeSyncContext.mSyncOperation);
                        }
                        SyncJobService.callJobFinished(syncFinishedOrCancelledMessagePayload.activeSyncContext.mSyncOperation.jobId, "sync finished");
                        runSyncFinishedOrCanceledH(syncFinishedOrCancelledMessagePayload.syncResult, syncFinishedOrCancelledMessagePayload.activeSyncContext);
                    } else if (isLoggable) {
                        Log.d("SyncManager", "handleSyncHandlerMessage: dropping since the sync is no longer active: " + syncFinishedOrCancelledMessagePayload.activeSyncContext);
                    }
                } else if (i == 4) {
                    ServiceConnectionData serviceConnectionData = (ServiceConnectionData) message.obj;
                    if (isLoggable) {
                        Log.d("SyncManager", "handleSyncHandlerMessage: MESSAGE_SERVICE_CONNECTED: " + serviceConnectionData.activeSyncContext);
                    }
                    if (SyncManager.m379$$Nest$misSyncStillActiveH(SyncManager.this, serviceConnectionData.activeSyncContext)) {
                        runBoundToAdapterH(serviceConnectionData.activeSyncContext, serviceConnectionData.adapter);
                    }
                } else if (i == 5) {
                    ActiveSyncContext activeSyncContext = ((ServiceConnectionData) message.obj).activeSyncContext;
                    if (isLoggable) {
                        Log.d("SyncManager", "handleSyncHandlerMessage: MESSAGE_SERVICE_DISCONNECTED: " + activeSyncContext);
                    }
                    if (SyncManager.m379$$Nest$misSyncStillActiveH(SyncManager.this, activeSyncContext)) {
                        try {
                            ISyncAdapter iSyncAdapter = activeSyncContext.mSyncAdapter;
                            if (iSyncAdapter != null) {
                                SyncManager.this.mLogger.log("Calling cancelSync for SERVICE_DISCONNECTED ", activeSyncContext, " adapter=", iSyncAdapter);
                                activeSyncContext.mSyncAdapter.cancelSync(activeSyncContext);
                                SyncManager.this.mLogger.log("Canceled");
                            }
                        } catch (RemoteException e) {
                            SyncManager.this.mLogger.log("RemoteException ", Log.getStackTraceString(e));
                        }
                        SyncResult syncResult = new SyncResult();
                        syncResult.stats.numIoExceptions++;
                        SyncJobService.callJobFinished(activeSyncContext.mSyncOperation.jobId, "service disconnected");
                        runSyncFinishedOrCanceledH(syncResult, activeSyncContext);
                    }
                } else if (i != 6) {
                    switch (i) {
                        case 8:
                            ActiveSyncContext activeSyncContext2 = (ActiveSyncContext) message.obj;
                            if (isLoggable) {
                                Log.d("SyncManager", "handleSyncHandlerMessage: MESSAGE_MONITOR_SYNC: " + activeSyncContext2.mSyncOperation.target);
                            }
                            if (!isSyncNotUsingNetworkH(activeSyncContext2)) {
                                SyncManager.m380$$Nest$mpostMonitorSyncProgressMessage(SyncManager.this, activeSyncContext2);
                                break;
                            } else {
                                StringBuilder sb = new StringBuilder();
                                activeSyncContext2.toString(sb, true);
                                Log.w("SyncManager", "Detected sync making no progress for " + sb.toString() + ". cancelling.");
                                SyncJobService.callJobFinished(activeSyncContext2.mSyncOperation.jobId, "no network activity");
                                runSyncFinishedOrCanceledH(null, activeSyncContext2);
                                break;
                            }
                        case 9:
                            if (Log.isLoggable("SyncManager", 2)) {
                                Slog.v("SyncManager", "handleSyncHandlerMessage: MESSAGE_ACCOUNTS_UPDATED");
                            }
                            updateRunningAccountsH((SyncStorageEngine.EndPoint) message.obj);
                            break;
                        case 10:
                            startSyncH((SyncOperation) message.obj);
                            break;
                        case 11:
                            SyncOperation syncOperation = (SyncOperation) message.obj;
                            if (isLoggable) {
                                Slog.v("SyncManager", "Stop sync received.");
                            }
                            ActiveSyncContext findActiveSyncContextH = findActiveSyncContextH(syncOperation.jobId);
                            if (findActiveSyncContextH != null) {
                                runSyncFinishedOrCanceledH(null, findActiveSyncContextH);
                                boolean z2 = message.arg1 != 0;
                                if (message.arg2 == 0) {
                                    z = false;
                                }
                                if (isLoggable) {
                                    Slog.v("SyncManager", "Stopping sync. Reschedule: " + z2 + "Backoff: " + z);
                                }
                                if (z) {
                                    SyncManager.m378$$Nest$mincreaseBackoffSetting(SyncManager.this, syncOperation.target);
                                }
                                if (z2) {
                                    boolean z3 = syncOperation.isPeriodic;
                                    SyncManager syncManager2 = SyncManager.this;
                                    if (!z3) {
                                        syncManager2.scheduleSyncOperationH(syncOperation, 0L);
                                        break;
                                    } else {
                                        syncManager2.scheduleSyncOperationH(syncOperation.createOneTimeSyncOperation(), 0L);
                                        break;
                                    }
                                }
                            }
                            break;
                        case 12:
                            ScheduleSyncMessagePayload scheduleSyncMessagePayload = (ScheduleSyncMessagePayload) message.obj;
                            SyncManager.this.scheduleSyncOperationH(scheduleSyncMessagePayload.syncOperation, scheduleSyncMessagePayload.minDelayMillis);
                            break;
                        case 13:
                            UpdatePeriodicSyncMessagePayload updatePeriodicSyncMessagePayload = (UpdatePeriodicSyncMessagePayload) message.obj;
                            updateOrAddPeriodicSyncH(updatePeriodicSyncMessagePayload.target, updatePeriodicSyncMessagePayload.pollFrequency, updatePeriodicSyncMessagePayload.flex, updatePeriodicSyncMessagePayload.extras);
                            break;
                        case 14:
                            Pair pair = (Pair) message.obj;
                            removePeriodicSyncH((SyncStorageEngine.EndPoint) pair.first, message.getData(), (String) pair.second);
                            break;
                    }
                } else {
                    SyncStorageEngine.EndPoint endPoint = (SyncStorageEngine.EndPoint) message.obj;
                    Bundle peekData = message.peekData();
                    if (isLoggable) {
                        Log.d("SyncManager", "handleSyncHandlerMessage: MESSAGE_CANCEL: " + endPoint + " bundle: " + peekData);
                    }
                    cancelActiveSyncH(endPoint, peekData);
                }
            } finally {
                this.mSyncTimeTracker.update();
            }
        }

        public final boolean isSyncNotUsingNetworkH(ActiveSyncContext activeSyncContext) {
            int i = activeSyncContext.mSyncAdapterUid;
            SyncManager.this.getClass();
            long uidTxBytes = (TrafficStats.getUidTxBytes(i) + TrafficStats.getUidRxBytes(i)) - activeSyncContext.mBytesTransferredAtLastPoll;
            if (Log.isLoggable("SyncManager", 3)) {
                long j = uidTxBytes % 1048576;
                Log.d("SyncManager", String.format("Time since last update: %ds. Delta transferred: %dMBs,%dKBs,%dBs", Long.valueOf((SystemClock.elapsedRealtime() - activeSyncContext.mLastPolledTimeElapsed) / 1000), Long.valueOf(uidTxBytes / 1048576), Long.valueOf(j / 1024), Long.valueOf(j % 1024)));
            }
            return uidTxBytes <= 10;
        }

        public final void removePeriodicSyncH(SyncStorageEngine.EndPoint endPoint, Bundle bundle, String str) {
            SyncManager.this.verifyJobScheduler();
            Iterator it = ((ArrayList) SyncManager.this.getAllPendingSyncs()).iterator();
            while (it.hasNext()) {
                SyncOperation syncOperation = (SyncOperation) it.next();
                if (syncOperation.isPeriodic && syncOperation.target.matchesSpec(endPoint) && SyncManager.syncExtrasEquals(syncOperation.mImmutableExtras, bundle, true)) {
                    SyncManager syncManager = SyncManager.this;
                    Iterator it2 = ((ArrayList) syncManager.getAllPendingSyncs()).iterator();
                    while (it2.hasNext()) {
                        SyncOperation syncOperation2 = (SyncOperation) it2.next();
                        int i = syncOperation2.sourcePeriodicId;
                        int i2 = syncOperation.jobId;
                        if (i == i2 || syncOperation2.jobId == i2) {
                            ActiveSyncContext findActiveSyncContextH = findActiveSyncContextH(i2);
                            if (findActiveSyncContextH != null) {
                                SyncJobService.callJobFinished(syncOperation.jobId, "removePeriodicSyncInternalH");
                                runSyncFinishedOrCanceledH(null, findActiveSyncContextH);
                            }
                            syncManager.mLogger.log("removePeriodicSyncInternalH-canceling: ", syncOperation2);
                            syncManager.cancelJob(syncOperation2, str);
                        }
                    }
                }
            }
        }

        public final void runBoundToAdapterH(ActiveSyncContext activeSyncContext, IBinder iBinder) {
            SyncManager syncManager = SyncManager.this;
            SyncOperation syncOperation = activeSyncContext.mSyncOperation;
            try {
                activeSyncContext.mIsLinkedToDeath = true;
                iBinder.linkToDeath(activeSyncContext, 0);
                SyncLogger syncLogger = syncManager.mLogger;
                syncLogger.getClass();
                if (syncLogger instanceof SyncLogger.RotatingFileLogger) {
                    SyncLogger syncLogger2 = syncManager.mLogger;
                    String str = "Sync start: account=" + syncOperation.target.account;
                    String str2 = syncOperation.target.provider;
                    String reasonToString = SyncOperation.reasonToString(null, syncOperation.reason);
                    Bundle bundle = syncOperation.mImmutableExtras;
                    StringBuilder sb = new StringBuilder();
                    SyncOperation.extrasToStringBuilder(bundle, sb);
                    syncLogger2.log(str, " authority=", str2, " reason=", reasonToString, " extras=", sb.toString(), " adapter=", activeSyncContext.mSyncAdapter);
                }
                ISyncAdapter asInterface = ISyncAdapter.Stub.asInterface(iBinder);
                activeSyncContext.mSyncAdapter = asInterface;
                SyncStorageEngine.EndPoint endPoint = syncOperation.target;
                asInterface.startSync(activeSyncContext, endPoint.provider, endPoint.account, new Bundle(syncOperation.mImmutableExtras));
                syncManager.mLogger.log("Sync is running now...");
            } catch (RemoteException e) {
                syncManager.mLogger.log("Sync failed with RemoteException: ", e.toString());
                Log.d("SyncManager", "maybeStartNextSync: caught a RemoteException, rescheduling", e);
                closeActiveSyncContext(activeSyncContext);
                SyncManager.m378$$Nest$mincreaseBackoffSetting(syncManager, syncOperation.target);
                syncManager.scheduleSyncOperationH(syncOperation);
            } catch (RuntimeException e2) {
                syncManager.mLogger.log("Sync failed with RuntimeException: ", e2.toString());
                closeActiveSyncContext(activeSyncContext);
                StringBuilder sb2 = new StringBuilder("Caught RuntimeException while starting the sync ");
                sb2.append(syncOperation == null ? "[null]" : syncOperation.dump(null, true, null, true));
                Slog.e("SyncManager", sb2.toString(), e2);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:133:0x0547 A[Catch: all -> 0x0436, TryCatch #1 {all -> 0x0436, blocks: (B:69:0x041b, B:71:0x0424, B:72:0x0439, B:74:0x0441, B:79:0x045a, B:80:0x0471, B:113:0x0478, B:126:0x0508, B:128:0x0513, B:131:0x0536, B:133:0x0547, B:135:0x054f, B:139:0x0559, B:140:0x05b1, B:142:0x05f3, B:146:0x0600, B:147:0x0604, B:148:0x060b, B:149:0x0611, B:151:0x0624, B:153:0x063f, B:154:0x0658, B:156:0x0643, B:158:0x064c, B:159:0x0628, B:161:0x0631, B:163:0x056a, B:165:0x0573, B:168:0x057e, B:170:0x05a1, B:171:0x051d, B:173:0x0521, B:174:0x04c1, B:175:0x04cd, B:176:0x04d9, B:177:0x04e5, B:178:0x04f1, B:179:0x04fd), top: B:68:0x041b }] */
        /* JADX WARN: Removed duplicated region for block: B:142:0x05f3 A[Catch: all -> 0x0436, TryCatch #1 {all -> 0x0436, blocks: (B:69:0x041b, B:71:0x0424, B:72:0x0439, B:74:0x0441, B:79:0x045a, B:80:0x0471, B:113:0x0478, B:126:0x0508, B:128:0x0513, B:131:0x0536, B:133:0x0547, B:135:0x054f, B:139:0x0559, B:140:0x05b1, B:142:0x05f3, B:146:0x0600, B:147:0x0604, B:148:0x060b, B:149:0x0611, B:151:0x0624, B:153:0x063f, B:154:0x0658, B:156:0x0643, B:158:0x064c, B:159:0x0628, B:161:0x0631, B:163:0x056a, B:165:0x0573, B:168:0x057e, B:170:0x05a1, B:171:0x051d, B:173:0x0521, B:174:0x04c1, B:175:0x04cd, B:176:0x04d9, B:177:0x04e5, B:178:0x04f1, B:179:0x04fd), top: B:68:0x041b }] */
        /* JADX WARN: Removed duplicated region for block: B:151:0x0624 A[Catch: all -> 0x0436, TryCatch #1 {all -> 0x0436, blocks: (B:69:0x041b, B:71:0x0424, B:72:0x0439, B:74:0x0441, B:79:0x045a, B:80:0x0471, B:113:0x0478, B:126:0x0508, B:128:0x0513, B:131:0x0536, B:133:0x0547, B:135:0x054f, B:139:0x0559, B:140:0x05b1, B:142:0x05f3, B:146:0x0600, B:147:0x0604, B:148:0x060b, B:149:0x0611, B:151:0x0624, B:153:0x063f, B:154:0x0658, B:156:0x0643, B:158:0x064c, B:159:0x0628, B:161:0x0631, B:163:0x056a, B:165:0x0573, B:168:0x057e, B:170:0x05a1, B:171:0x051d, B:173:0x0521, B:174:0x04c1, B:175:0x04cd, B:176:0x04d9, B:177:0x04e5, B:178:0x04f1, B:179:0x04fd), top: B:68:0x041b }] */
        /* JADX WARN: Removed duplicated region for block: B:153:0x063f A[Catch: all -> 0x0436, TryCatch #1 {all -> 0x0436, blocks: (B:69:0x041b, B:71:0x0424, B:72:0x0439, B:74:0x0441, B:79:0x045a, B:80:0x0471, B:113:0x0478, B:126:0x0508, B:128:0x0513, B:131:0x0536, B:133:0x0547, B:135:0x054f, B:139:0x0559, B:140:0x05b1, B:142:0x05f3, B:146:0x0600, B:147:0x0604, B:148:0x060b, B:149:0x0611, B:151:0x0624, B:153:0x063f, B:154:0x0658, B:156:0x0643, B:158:0x064c, B:159:0x0628, B:161:0x0631, B:163:0x056a, B:165:0x0573, B:168:0x057e, B:170:0x05a1, B:171:0x051d, B:173:0x0521, B:174:0x04c1, B:175:0x04cd, B:176:0x04d9, B:177:0x04e5, B:178:0x04f1, B:179:0x04fd), top: B:68:0x041b }] */
        /* JADX WARN: Removed duplicated region for block: B:156:0x0643 A[Catch: all -> 0x0436, TryCatch #1 {all -> 0x0436, blocks: (B:69:0x041b, B:71:0x0424, B:72:0x0439, B:74:0x0441, B:79:0x045a, B:80:0x0471, B:113:0x0478, B:126:0x0508, B:128:0x0513, B:131:0x0536, B:133:0x0547, B:135:0x054f, B:139:0x0559, B:140:0x05b1, B:142:0x05f3, B:146:0x0600, B:147:0x0604, B:148:0x060b, B:149:0x0611, B:151:0x0624, B:153:0x063f, B:154:0x0658, B:156:0x0643, B:158:0x064c, B:159:0x0628, B:161:0x0631, B:163:0x056a, B:165:0x0573, B:168:0x057e, B:170:0x05a1, B:171:0x051d, B:173:0x0521, B:174:0x04c1, B:175:0x04cd, B:176:0x04d9, B:177:0x04e5, B:178:0x04f1, B:179:0x04fd), top: B:68:0x041b }] */
        /* JADX WARN: Removed duplicated region for block: B:159:0x0628 A[Catch: all -> 0x0436, TryCatch #1 {all -> 0x0436, blocks: (B:69:0x041b, B:71:0x0424, B:72:0x0439, B:74:0x0441, B:79:0x045a, B:80:0x0471, B:113:0x0478, B:126:0x0508, B:128:0x0513, B:131:0x0536, B:133:0x0547, B:135:0x054f, B:139:0x0559, B:140:0x05b1, B:142:0x05f3, B:146:0x0600, B:147:0x0604, B:148:0x060b, B:149:0x0611, B:151:0x0624, B:153:0x063f, B:154:0x0658, B:156:0x0643, B:158:0x064c, B:159:0x0628, B:161:0x0631, B:163:0x056a, B:165:0x0573, B:168:0x057e, B:170:0x05a1, B:171:0x051d, B:173:0x0521, B:174:0x04c1, B:175:0x04cd, B:176:0x04d9, B:177:0x04e5, B:178:0x04f1, B:179:0x04fd), top: B:68:0x041b }] */
        /* JADX WARN: Removed duplicated region for block: B:163:0x056a A[Catch: all -> 0x0436, TryCatch #1 {all -> 0x0436, blocks: (B:69:0x041b, B:71:0x0424, B:72:0x0439, B:74:0x0441, B:79:0x045a, B:80:0x0471, B:113:0x0478, B:126:0x0508, B:128:0x0513, B:131:0x0536, B:133:0x0547, B:135:0x054f, B:139:0x0559, B:140:0x05b1, B:142:0x05f3, B:146:0x0600, B:147:0x0604, B:148:0x060b, B:149:0x0611, B:151:0x0624, B:153:0x063f, B:154:0x0658, B:156:0x0643, B:158:0x064c, B:159:0x0628, B:161:0x0631, B:163:0x056a, B:165:0x0573, B:168:0x057e, B:170:0x05a1, B:171:0x051d, B:173:0x0521, B:174:0x04c1, B:175:0x04cd, B:176:0x04d9, B:177:0x04e5, B:178:0x04f1, B:179:0x04fd), top: B:68:0x041b }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void runSyncFinishedOrCanceledH(android.content.SyncResult r26, com.android.server.content.SyncManager.ActiveSyncContext r27) {
            /*
                Method dump skipped, instructions count: 2001
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.content.SyncManager.SyncHandler.runSyncFinishedOrCanceledH(android.content.SyncResult, com.android.server.content.SyncManager$ActiveSyncContext):void");
        }

        /* JADX WARN: Removed duplicated region for block: B:73:0x0154  */
        /* JADX WARN: Removed duplicated region for block: B:75:0x0169  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void startSyncH(com.android.server.content.SyncOperation r23) {
            /*
                Method dump skipped, instructions count: 1170
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.content.SyncManager.SyncHandler.startSyncH(com.android.server.content.SyncOperation):void");
        }

        /* JADX WARN: Removed duplicated region for block: B:48:0x01a5 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:49:0x01a6  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void updateOrAddPeriodicSyncH(final com.android.server.content.SyncStorageEngine.EndPoint r31, final long r32, final long r34, final android.os.Bundle r36) {
            /*
                Method dump skipped, instructions count: 508
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.content.SyncManager.SyncHandler.updateOrAddPeriodicSyncH(com.android.server.content.SyncStorageEngine$EndPoint, long, long, android.os.Bundle):void");
        }

        public final void updateRunningAccountsH(SyncStorageEngine.EndPoint endPoint) {
            int i;
            synchronized (SyncManager.this.mAccountsLock) {
                try {
                    AccountAndUser[] accountAndUserArr = SyncManager.this.mRunningAccounts;
                    SyncManager syncManager = SyncManager.this;
                    AccountManagerService singleton = AccountManagerService.getSingleton();
                    singleton.getClass();
                    try {
                        syncManager.mRunningAccounts = singleton.getAccountsForSystem(ActivityManager.getService().getRunningUserIds());
                        if (Log.isLoggable("SyncManager", 2)) {
                            Slog.v("SyncManager", "Accounts list: ");
                            for (AccountAndUser accountAndUser : SyncManager.this.mRunningAccounts) {
                                Slog.v("SyncManager", accountAndUser.toString());
                            }
                        }
                        SyncLogger syncLogger = SyncManager.this.mLogger;
                        syncLogger.getClass();
                        if (syncLogger instanceof SyncLogger.RotatingFileLogger) {
                            SyncManager syncManager2 = SyncManager.this;
                            syncManager2.mLogger.log("updateRunningAccountsH: ", Arrays.toString(syncManager2.mRunningAccounts));
                        }
                        SyncManager.m381$$Nest$mremoveStaleAccounts(SyncManager.this);
                        AccountAndUser[] accountAndUserArr2 = SyncManager.this.mRunningAccounts;
                        int size = SyncManager.this.mActiveSyncContexts.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            ActiveSyncContext activeSyncContext = (ActiveSyncContext) SyncManager.this.mActiveSyncContexts.get(i2);
                            SyncManager syncManager3 = SyncManager.this;
                            SyncStorageEngine.EndPoint endPoint2 = activeSyncContext.mSyncOperation.target;
                            if (!SyncManager.m377$$Nest$mcontainsAccountAndUser(syncManager3, accountAndUserArr2, endPoint2.account, endPoint2.userId)) {
                                Log.d("SyncManager", "canceling sync since the account is no longer running");
                                SyncManager.m382$$Nest$msendSyncFinishedOrCanceledMessage(SyncManager.this, activeSyncContext, null);
                            }
                        }
                        if (endPoint != null) {
                            int length = SyncManager.this.mRunningAccounts.length;
                            int i3 = 0;
                            while (true) {
                                if (i3 >= length) {
                                    break;
                                }
                                AccountAndUser accountAndUser2 = SyncManager.this.mRunningAccounts[i3];
                                if (SyncManager.m377$$Nest$mcontainsAccountAndUser(SyncManager.this, accountAndUserArr, accountAndUser2.account, accountAndUser2.userId)) {
                                    i3++;
                                } else {
                                    if (Log.isLoggable("SyncManager", 3)) {
                                        Log.d("SyncManager", "Account " + accountAndUser2.account + " added, checking sync restore data");
                                    }
                                    AccountSyncSettingsBackupHelper.accountAdded(SyncManager.this.mContext, endPoint.userId);
                                }
                            }
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            AccountAndUser[] allAccountsForSystemProcess = AccountManagerService.getSingleton().getAllAccountsForSystemProcess();
            ArrayList arrayList = (ArrayList) SyncManager.this.getAllPendingSyncs();
            int size2 = arrayList.size();
            for (i = 0; i < size2; i++) {
                SyncOperation syncOperation = (SyncOperation) arrayList.get(i);
                SyncManager syncManager4 = SyncManager.this;
                SyncStorageEngine.EndPoint endPoint3 = syncOperation.target;
                if (!SyncManager.m377$$Nest$mcontainsAccountAndUser(syncManager4, allAccountsForSystemProcess, endPoint3.account, endPoint3.userId)) {
                    SyncManager.this.mLogger.log("canceling: ", syncOperation);
                    SyncManager.this.cancelJob(syncOperation, "updateRunningAccountsH()");
                }
            }
            if (endPoint != null) {
                SyncManager.this.scheduleSync(endPoint.account, endPoint.userId, -2, endPoint.provider, null, -1, 0, Process.myUid(), -4, null);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SyncTimeTracker {
        public long mTimeSpentSyncing;
        public boolean mLastWasSyncing = false;
        public long mWhenSyncStarted = 0;

        public SyncTimeTracker() {
        }

        public final synchronized void update() {
            try {
                boolean z = !SyncManager.this.mActiveSyncContexts.isEmpty();
                if (z == this.mLastWasSyncing) {
                    return;
                }
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (z) {
                    this.mWhenSyncStarted = elapsedRealtime;
                } else {
                    this.mTimeSpentSyncing = (elapsedRealtime - this.mWhenSyncStarted) + this.mTimeSpentSyncing;
                }
                this.mLastWasSyncing = z;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UpdatePeriodicSyncMessagePayload {
        public final Bundle extras;
        public final long flex;
        public final long pollFrequency;
        public final SyncStorageEngine.EndPoint target;

        public UpdatePeriodicSyncMessagePayload(SyncStorageEngine.EndPoint endPoint, long j, long j2, Bundle bundle) {
            this.target = endPoint;
            this.pollFrequency = j;
            this.flex = j2;
            this.extras = bundle;
        }
    }

    /* renamed from: -$$Nest$mcontainsAccountAndUser, reason: not valid java name */
    public static boolean m377$$Nest$mcontainsAccountAndUser(SyncManager syncManager, AccountAndUser[] accountAndUserArr, Account account, int i) {
        syncManager.getClass();
        for (AccountAndUser accountAndUser : accountAndUserArr) {
            if (accountAndUser.userId == i && accountAndUser.account.equals(account)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: -$$Nest$mincreaseBackoffSetting, reason: not valid java name */
    public static void m378$$Nest$mincreaseBackoffSetting(SyncManager syncManager, SyncStorageEngine.EndPoint endPoint) {
        long j;
        int i;
        int i2;
        float f;
        syncManager.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Pair backoff = syncManager.mSyncStorageEngine.getBackoff(endPoint);
        if (backoff == null) {
            j = -1;
        } else {
            if (elapsedRealtime < ((Long) backoff.first).longValue()) {
                if (Log.isLoggable("SyncManager", 2)) {
                    Slog.v("SyncManager", "Still in backoff, do not increase it. Remaining: " + ((((Long) backoff.first).longValue() - elapsedRealtime) / 1000) + " seconds.");
                    return;
                }
                return;
            }
            float longValue = ((Long) backoff.second).longValue();
            SyncManagerConstants syncManagerConstants = syncManager.mConstants;
            synchronized (syncManagerConstants.mLock) {
                f = syncManagerConstants.mRetryTimeIncreaseFactor;
            }
            j = (long) (longValue * f);
        }
        if (j <= 0) {
            SyncManagerConstants syncManagerConstants2 = syncManager.mConstants;
            synchronized (syncManagerConstants2.mLock) {
                i2 = syncManagerConstants2.mInitialSyncRetryTimeInSeconds;
            }
            long j2 = i2 * 1000;
            Random random = new Random(SystemClock.elapsedRealtime());
            if (((long) (j2 * 1.1d)) - j2 > 2147483647L) {
                throw new IllegalArgumentException("the difference between the maxValue and the minValue must be less than 2147483647");
            }
            j = j2 + random.nextInt((int) r10);
        }
        SyncManagerConstants syncManagerConstants3 = syncManager.mConstants;
        synchronized (syncManagerConstants3.mLock) {
            i = syncManagerConstants3.mMaxSyncRetryTimeInSeconds;
        }
        long j3 = i * 1000;
        if (j > j3) {
            j = j3;
        }
        long j4 = elapsedRealtime + j;
        if (Log.isLoggable("SyncManager", 2)) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("Backoff until: ", j4, ", delayTime: ");
            m.append(j);
            Slog.v("SyncManager", m.toString());
        }
        syncManager.mSyncStorageEngine.setBackoff(endPoint, j4, j);
        syncManager.rescheduleSyncs(endPoint, "increaseBackoffSetting");
    }

    /* renamed from: -$$Nest$misSyncStillActiveH, reason: not valid java name */
    public static boolean m379$$Nest$misSyncStillActiveH(SyncManager syncManager, ActiveSyncContext activeSyncContext) {
        Iterator it = syncManager.mActiveSyncContexts.iterator();
        while (it.hasNext()) {
            if (((ActiveSyncContext) it.next()) == activeSyncContext) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: -$$Nest$mpostMonitorSyncProgressMessage, reason: not valid java name */
    public static void m380$$Nest$mpostMonitorSyncProgressMessage(SyncManager syncManager, ActiveSyncContext activeSyncContext) {
        syncManager.getClass();
        if (Log.isLoggable("SyncManager", 2)) {
            Slog.v("SyncManager", "posting MESSAGE_SYNC_MONITOR in 60s");
        }
        int i = activeSyncContext.mSyncAdapterUid;
        activeSyncContext.mBytesTransferredAtLastPoll = TrafficStats.getUidTxBytes(i) + TrafficStats.getUidRxBytes(i);
        activeSyncContext.mLastPolledTimeElapsed = SystemClock.elapsedRealtime();
        SyncHandler syncHandler = syncManager.mSyncHandler;
        syncHandler.sendMessageDelayed(syncHandler.obtainMessage(8, activeSyncContext), 60000L);
    }

    /* renamed from: -$$Nest$mremoveStaleAccounts, reason: not valid java name */
    public static void m381$$Nest$mremoveStaleAccounts(SyncManager syncManager) {
        for (UserInfo userInfo : syncManager.mUserManager.getAliveUsers()) {
            if (!userInfo.partial) {
                syncManager.mSyncStorageEngine.removeStaleAccounts(AccountManagerService.getSingleton().getAccounts(userInfo.id, syncManager.mContext.getOpPackageName()), userInfo.id);
            }
        }
    }

    /* renamed from: -$$Nest$msendSyncFinishedOrCanceledMessage, reason: not valid java name */
    public static void m382$$Nest$msendSyncFinishedOrCanceledMessage(SyncManager syncManager, ActiveSyncContext activeSyncContext, SyncResult syncResult) {
        syncManager.getClass();
        if (Log.isLoggable("SyncManager", 2)) {
            Slog.v("SyncManager", "sending MESSAGE_SYNC_FINISHED");
        }
        SyncHandler syncHandler = syncManager.mSyncHandler;
        Message obtainMessage = syncHandler.obtainMessage();
        obtainMessage.what = 1;
        obtainMessage.obj = new SyncFinishedOrCancelledMessagePayload(syncResult, activeSyncContext);
        syncHandler.sendMessage(obtainMessage);
    }

    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.server.content.SyncManager$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.server.content.SyncManager$$ExternalSyntheticLambda0] */
    static {
        final int i = 0;
        sOpDumpComparator = new Comparator() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                SyncOperation syncOperation = (SyncOperation) obj;
                SyncOperation syncOperation2 = (SyncOperation) obj2;
                switch (i) {
                    case 0:
                        int compare = Integer.compare(syncOperation.target.userId, syncOperation2.target.userId);
                        if (compare != 0) {
                            return compare;
                        }
                        Comparator comparator = String.CASE_INSENSITIVE_ORDER;
                        SyncStorageEngine.EndPoint endPoint = syncOperation.target;
                        String str = endPoint.account.type;
                        SyncStorageEngine.EndPoint endPoint2 = syncOperation2.target;
                        int compare2 = comparator.compare(str, endPoint2.account.type);
                        if (compare2 != 0 || (compare2 = comparator.compare(endPoint.account.name, endPoint2.account.name)) != 0) {
                            return compare2;
                        }
                        int compare3 = comparator.compare(endPoint.provider, endPoint2.provider);
                        if (compare3 != 0) {
                            return compare3;
                        }
                        int compare4 = Integer.compare(syncOperation.reason, syncOperation2.reason);
                        if (compare4 != 0) {
                            return compare4;
                        }
                        int compare5 = Long.compare(syncOperation.periodMillis, syncOperation2.periodMillis);
                        if (compare5 != 0) {
                            return compare5;
                        }
                        int compare6 = Long.compare(syncOperation.expectedRuntime, syncOperation2.expectedRuntime);
                        if (compare6 != 0) {
                            return compare6;
                        }
                        int compare7 = Long.compare(syncOperation.jobId, syncOperation2.jobId);
                        if (compare7 != 0) {
                            return compare7;
                        }
                        return 0;
                    default:
                        int compare8 = Long.compare(syncOperation.expectedRuntime, syncOperation2.expectedRuntime);
                        return compare8 != 0 ? compare8 : SyncManager.sOpDumpComparator.compare(syncOperation, syncOperation2);
                }
            }
        };
        final int i2 = 1;
        sOpRuntimeComparator = new Comparator() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                SyncOperation syncOperation = (SyncOperation) obj;
                SyncOperation syncOperation2 = (SyncOperation) obj2;
                switch (i2) {
                    case 0:
                        int compare = Integer.compare(syncOperation.target.userId, syncOperation2.target.userId);
                        if (compare != 0) {
                            return compare;
                        }
                        Comparator comparator = String.CASE_INSENSITIVE_ORDER;
                        SyncStorageEngine.EndPoint endPoint = syncOperation.target;
                        String str = endPoint.account.type;
                        SyncStorageEngine.EndPoint endPoint2 = syncOperation2.target;
                        int compare2 = comparator.compare(str, endPoint2.account.type);
                        if (compare2 != 0 || (compare2 = comparator.compare(endPoint.account.name, endPoint2.account.name)) != 0) {
                            return compare2;
                        }
                        int compare3 = comparator.compare(endPoint.provider, endPoint2.provider);
                        if (compare3 != 0) {
                            return compare3;
                        }
                        int compare4 = Integer.compare(syncOperation.reason, syncOperation2.reason);
                        if (compare4 != 0) {
                            return compare4;
                        }
                        int compare5 = Long.compare(syncOperation.periodMillis, syncOperation2.periodMillis);
                        if (compare5 != 0) {
                            return compare5;
                        }
                        int compare6 = Long.compare(syncOperation.expectedRuntime, syncOperation2.expectedRuntime);
                        if (compare6 != 0) {
                            return compare6;
                        }
                        int compare7 = Long.compare(syncOperation.jobId, syncOperation2.jobId);
                        if (compare7 != 0) {
                            return compare7;
                        }
                        return 0;
                    default:
                        int compare8 = Long.compare(syncOperation.expectedRuntime, syncOperation2.expectedRuntime);
                        return compare8 != 0 ? compare8 : SyncManager.sOpDumpComparator.compare(syncOperation, syncOperation2);
                }
            }
        };
    }

    public SyncManager(Context context, boolean z) {
        List list;
        int i = 0;
        final int i2 = 2;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver(this) { // from class: com.android.server.content.SyncManager.2
            public final /* synthetic */ SyncManager this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                SyncStorageEngine syncStorageEngine;
                SyncStorageEngine syncStorageEngine2;
                switch (i2) {
                    case 0:
                        boolean z2 = this.this$0.mDataConnectionIsConnected;
                        SyncManager syncManager = this.this$0;
                        NetworkInfo activeNetworkInfo = syncManager.getConnectivityManager().getActiveNetworkInfo();
                        syncManager.mDataConnectionIsConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
                        if (!this.this$0.mDataConnectionIsConnected || z2) {
                            return;
                        }
                        int i3 = 2;
                        if (Log.isLoggable("SyncManager", 2)) {
                            Slog.v("SyncManager", "Reconnection detected: clearing all backoffs");
                        }
                        SyncManager syncManager2 = this.this$0;
                        SyncStorageEngine syncStorageEngine3 = syncManager2.mSyncStorageEngine;
                        syncStorageEngine3.getClass();
                        ArraySet arraySet = new ArraySet();
                        synchronized (syncStorageEngine3.mAuthorities) {
                            try {
                                for (SyncStorageEngine.AccountInfo accountInfo : syncStorageEngine3.mAccounts.values()) {
                                    for (SyncStorageEngine.AuthorityInfo authorityInfo : accountInfo.authorities.values()) {
                                        if (authorityInfo.backoffTime == -1 && authorityInfo.backoffDelay == -1) {
                                            syncStorageEngine2 = syncStorageEngine3;
                                            syncStorageEngine3 = syncStorageEngine2;
                                            i3 = 2;
                                        }
                                        if (Log.isLoggable("SyncManager", i3)) {
                                            StringBuilder sb = new StringBuilder();
                                            sb.append("clearAllBackoffsLocked: authority:");
                                            sb.append(authorityInfo.target);
                                            sb.append(" account:");
                                            sb.append(accountInfo.accountAndUser.account.name);
                                            sb.append(" user:");
                                            sb.append(accountInfo.accountAndUser.userId);
                                            sb.append(" backoffTime was: ");
                                            syncStorageEngine2 = syncStorageEngine3;
                                            sb.append(authorityInfo.backoffTime);
                                            sb.append(" backoffDelay was: ");
                                            sb.append(authorityInfo.backoffDelay);
                                            Slog.v("SyncManager", sb.toString());
                                        } else {
                                            syncStorageEngine2 = syncStorageEngine3;
                                        }
                                        authorityInfo.backoffTime = -1L;
                                        authorityInfo.backoffDelay = -1L;
                                        arraySet.add(Integer.valueOf(accountInfo.accountAndUser.userId));
                                        syncStorageEngine3 = syncStorageEngine2;
                                        i3 = 2;
                                    }
                                }
                                syncStorageEngine = syncStorageEngine3;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        for (int size = arraySet.size() - 1; size > 0; size--) {
                            syncStorageEngine.reportChange(1, ((Integer) arraySet.valueAt(size)).intValue(), null);
                        }
                        syncManager2.rescheduleSyncs(SyncStorageEngine.EndPoint.USER_ALL_PROVIDER_ALL_ACCOUNTS_ALL, "network reconnect");
                        return;
                    case 1:
                        Log.w("SyncManager", "Writing sync state before shutdown...");
                        SyncStorageEngine syncStorageEngine4 = this.this$0.mSyncStorageEngine;
                        synchronized (syncStorageEngine4.mAuthorities) {
                            syncStorageEngine4.writeStatusLocked();
                            syncStorageEngine4.writeStatisticsLocked();
                        }
                        this.this$0.mLogger.log(SyncManager.getJobStats());
                        this.this$0.mLogger.log("Shutting down.");
                        return;
                    case 2:
                        this.this$0.updateRunningAccounts(new SyncStorageEngine.EndPoint(null, null, getSendingUserId()));
                        return;
                    case 3:
                        if ("android.intent.action.TIME_SET".equals(intent.getAction())) {
                            SyncStorageEngine syncStorageEngine5 = this.this$0.mSyncStorageEngine;
                            if (syncStorageEngine5.mIsClockValid) {
                                return;
                            }
                            syncStorageEngine5.mIsClockValid = true;
                            Slog.w("SyncManager", "Clock is valid now.");
                            return;
                        }
                        return;
                    default:
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        if (intExtra == -10000) {
                            return;
                        }
                        if ("android.intent.action.USER_REMOVED".equals(action)) {
                            SyncManager syncManager3 = this.this$0;
                            syncManager3.mLogger.log("onUserRemoved: u", Integer.valueOf(intExtra));
                            syncManager3.updateRunningAccounts(null);
                            syncManager3.mSyncStorageEngine.removeStaleAccounts(null, intExtra);
                            Iterator it = ((ArrayList) syncManager3.getAllPendingSyncs()).iterator();
                            while (it.hasNext()) {
                                SyncOperation syncOperation = (SyncOperation) it.next();
                                if (syncOperation.target.userId == intExtra) {
                                    syncManager3.cancelJob(syncOperation, "user removed u" + intExtra);
                                }
                            }
                            return;
                        }
                        if (!"android.intent.action.USER_UNLOCKED".equals(action)) {
                            if ("android.intent.action.USER_STOPPED".equals(action)) {
                                SyncManager syncManager4 = this.this$0;
                                syncManager4.updateRunningAccounts(null);
                                syncManager4.cancelActiveSync(new SyncStorageEngine.EndPoint(null, null, intExtra), null, "onUserStopped");
                                return;
                            }
                            return;
                        }
                        SyncManager syncManager5 = this.this$0;
                        syncManager5.getClass();
                        AccountManagerService singleton = AccountManagerService.getSingleton();
                        singleton.validateAccountsInternal(singleton.getUserAccounts(intExtra), true);
                        syncManager5.mSyncAdapters.invalidateCache(intExtra);
                        syncManager5.updateRunningAccounts(new SyncStorageEngine.EndPoint(null, null, intExtra));
                        for (Account account : AccountManagerService.getSingleton().getAccounts(intExtra, syncManager5.mContext.getOpPackageName())) {
                            syncManager5.scheduleSync(account, intExtra, -8, null, null, -1, 0, Process.myUid(), -3, null);
                        }
                        return;
                }
            }
        };
        final int i3 = 0;
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver(this) { // from class: com.android.server.content.SyncManager.2
            public final /* synthetic */ SyncManager this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                SyncStorageEngine syncStorageEngine;
                SyncStorageEngine syncStorageEngine2;
                switch (i3) {
                    case 0:
                        boolean z2 = this.this$0.mDataConnectionIsConnected;
                        SyncManager syncManager = this.this$0;
                        NetworkInfo activeNetworkInfo = syncManager.getConnectivityManager().getActiveNetworkInfo();
                        syncManager.mDataConnectionIsConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
                        if (!this.this$0.mDataConnectionIsConnected || z2) {
                            return;
                        }
                        int i32 = 2;
                        if (Log.isLoggable("SyncManager", 2)) {
                            Slog.v("SyncManager", "Reconnection detected: clearing all backoffs");
                        }
                        SyncManager syncManager2 = this.this$0;
                        SyncStorageEngine syncStorageEngine3 = syncManager2.mSyncStorageEngine;
                        syncStorageEngine3.getClass();
                        ArraySet arraySet = new ArraySet();
                        synchronized (syncStorageEngine3.mAuthorities) {
                            try {
                                for (SyncStorageEngine.AccountInfo accountInfo : syncStorageEngine3.mAccounts.values()) {
                                    for (SyncStorageEngine.AuthorityInfo authorityInfo : accountInfo.authorities.values()) {
                                        if (authorityInfo.backoffTime == -1 && authorityInfo.backoffDelay == -1) {
                                            syncStorageEngine2 = syncStorageEngine3;
                                            syncStorageEngine3 = syncStorageEngine2;
                                            i32 = 2;
                                        }
                                        if (Log.isLoggable("SyncManager", i32)) {
                                            StringBuilder sb = new StringBuilder();
                                            sb.append("clearAllBackoffsLocked: authority:");
                                            sb.append(authorityInfo.target);
                                            sb.append(" account:");
                                            sb.append(accountInfo.accountAndUser.account.name);
                                            sb.append(" user:");
                                            sb.append(accountInfo.accountAndUser.userId);
                                            sb.append(" backoffTime was: ");
                                            syncStorageEngine2 = syncStorageEngine3;
                                            sb.append(authorityInfo.backoffTime);
                                            sb.append(" backoffDelay was: ");
                                            sb.append(authorityInfo.backoffDelay);
                                            Slog.v("SyncManager", sb.toString());
                                        } else {
                                            syncStorageEngine2 = syncStorageEngine3;
                                        }
                                        authorityInfo.backoffTime = -1L;
                                        authorityInfo.backoffDelay = -1L;
                                        arraySet.add(Integer.valueOf(accountInfo.accountAndUser.userId));
                                        syncStorageEngine3 = syncStorageEngine2;
                                        i32 = 2;
                                    }
                                }
                                syncStorageEngine = syncStorageEngine3;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        for (int size = arraySet.size() - 1; size > 0; size--) {
                            syncStorageEngine.reportChange(1, ((Integer) arraySet.valueAt(size)).intValue(), null);
                        }
                        syncManager2.rescheduleSyncs(SyncStorageEngine.EndPoint.USER_ALL_PROVIDER_ALL_ACCOUNTS_ALL, "network reconnect");
                        return;
                    case 1:
                        Log.w("SyncManager", "Writing sync state before shutdown...");
                        SyncStorageEngine syncStorageEngine4 = this.this$0.mSyncStorageEngine;
                        synchronized (syncStorageEngine4.mAuthorities) {
                            syncStorageEngine4.writeStatusLocked();
                            syncStorageEngine4.writeStatisticsLocked();
                        }
                        this.this$0.mLogger.log(SyncManager.getJobStats());
                        this.this$0.mLogger.log("Shutting down.");
                        return;
                    case 2:
                        this.this$0.updateRunningAccounts(new SyncStorageEngine.EndPoint(null, null, getSendingUserId()));
                        return;
                    case 3:
                        if ("android.intent.action.TIME_SET".equals(intent.getAction())) {
                            SyncStorageEngine syncStorageEngine5 = this.this$0.mSyncStorageEngine;
                            if (syncStorageEngine5.mIsClockValid) {
                                return;
                            }
                            syncStorageEngine5.mIsClockValid = true;
                            Slog.w("SyncManager", "Clock is valid now.");
                            return;
                        }
                        return;
                    default:
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        if (intExtra == -10000) {
                            return;
                        }
                        if ("android.intent.action.USER_REMOVED".equals(action)) {
                            SyncManager syncManager3 = this.this$0;
                            syncManager3.mLogger.log("onUserRemoved: u", Integer.valueOf(intExtra));
                            syncManager3.updateRunningAccounts(null);
                            syncManager3.mSyncStorageEngine.removeStaleAccounts(null, intExtra);
                            Iterator it = ((ArrayList) syncManager3.getAllPendingSyncs()).iterator();
                            while (it.hasNext()) {
                                SyncOperation syncOperation = (SyncOperation) it.next();
                                if (syncOperation.target.userId == intExtra) {
                                    syncManager3.cancelJob(syncOperation, "user removed u" + intExtra);
                                }
                            }
                            return;
                        }
                        if (!"android.intent.action.USER_UNLOCKED".equals(action)) {
                            if ("android.intent.action.USER_STOPPED".equals(action)) {
                                SyncManager syncManager4 = this.this$0;
                                syncManager4.updateRunningAccounts(null);
                                syncManager4.cancelActiveSync(new SyncStorageEngine.EndPoint(null, null, intExtra), null, "onUserStopped");
                                return;
                            }
                            return;
                        }
                        SyncManager syncManager5 = this.this$0;
                        syncManager5.getClass();
                        AccountManagerService singleton = AccountManagerService.getSingleton();
                        singleton.validateAccountsInternal(singleton.getUserAccounts(intExtra), true);
                        syncManager5.mSyncAdapters.invalidateCache(intExtra);
                        syncManager5.updateRunningAccounts(new SyncStorageEngine.EndPoint(null, null, intExtra));
                        for (Account account : AccountManagerService.getSingleton().getAccounts(intExtra, syncManager5.mContext.getOpPackageName())) {
                            syncManager5.scheduleSync(account, intExtra, -8, null, null, -1, 0, Process.myUid(), -3, null);
                        }
                        return;
                }
            }
        };
        final int i4 = 1;
        BroadcastReceiver broadcastReceiver3 = new BroadcastReceiver(this) { // from class: com.android.server.content.SyncManager.2
            public final /* synthetic */ SyncManager this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                SyncStorageEngine syncStorageEngine;
                SyncStorageEngine syncStorageEngine2;
                switch (i4) {
                    case 0:
                        boolean z2 = this.this$0.mDataConnectionIsConnected;
                        SyncManager syncManager = this.this$0;
                        NetworkInfo activeNetworkInfo = syncManager.getConnectivityManager().getActiveNetworkInfo();
                        syncManager.mDataConnectionIsConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
                        if (!this.this$0.mDataConnectionIsConnected || z2) {
                            return;
                        }
                        int i32 = 2;
                        if (Log.isLoggable("SyncManager", 2)) {
                            Slog.v("SyncManager", "Reconnection detected: clearing all backoffs");
                        }
                        SyncManager syncManager2 = this.this$0;
                        SyncStorageEngine syncStorageEngine3 = syncManager2.mSyncStorageEngine;
                        syncStorageEngine3.getClass();
                        ArraySet arraySet = new ArraySet();
                        synchronized (syncStorageEngine3.mAuthorities) {
                            try {
                                for (SyncStorageEngine.AccountInfo accountInfo : syncStorageEngine3.mAccounts.values()) {
                                    for (SyncStorageEngine.AuthorityInfo authorityInfo : accountInfo.authorities.values()) {
                                        if (authorityInfo.backoffTime == -1 && authorityInfo.backoffDelay == -1) {
                                            syncStorageEngine2 = syncStorageEngine3;
                                            syncStorageEngine3 = syncStorageEngine2;
                                            i32 = 2;
                                        }
                                        if (Log.isLoggable("SyncManager", i32)) {
                                            StringBuilder sb = new StringBuilder();
                                            sb.append("clearAllBackoffsLocked: authority:");
                                            sb.append(authorityInfo.target);
                                            sb.append(" account:");
                                            sb.append(accountInfo.accountAndUser.account.name);
                                            sb.append(" user:");
                                            sb.append(accountInfo.accountAndUser.userId);
                                            sb.append(" backoffTime was: ");
                                            syncStorageEngine2 = syncStorageEngine3;
                                            sb.append(authorityInfo.backoffTime);
                                            sb.append(" backoffDelay was: ");
                                            sb.append(authorityInfo.backoffDelay);
                                            Slog.v("SyncManager", sb.toString());
                                        } else {
                                            syncStorageEngine2 = syncStorageEngine3;
                                        }
                                        authorityInfo.backoffTime = -1L;
                                        authorityInfo.backoffDelay = -1L;
                                        arraySet.add(Integer.valueOf(accountInfo.accountAndUser.userId));
                                        syncStorageEngine3 = syncStorageEngine2;
                                        i32 = 2;
                                    }
                                }
                                syncStorageEngine = syncStorageEngine3;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        for (int size = arraySet.size() - 1; size > 0; size--) {
                            syncStorageEngine.reportChange(1, ((Integer) arraySet.valueAt(size)).intValue(), null);
                        }
                        syncManager2.rescheduleSyncs(SyncStorageEngine.EndPoint.USER_ALL_PROVIDER_ALL_ACCOUNTS_ALL, "network reconnect");
                        return;
                    case 1:
                        Log.w("SyncManager", "Writing sync state before shutdown...");
                        SyncStorageEngine syncStorageEngine4 = this.this$0.mSyncStorageEngine;
                        synchronized (syncStorageEngine4.mAuthorities) {
                            syncStorageEngine4.writeStatusLocked();
                            syncStorageEngine4.writeStatisticsLocked();
                        }
                        this.this$0.mLogger.log(SyncManager.getJobStats());
                        this.this$0.mLogger.log("Shutting down.");
                        return;
                    case 2:
                        this.this$0.updateRunningAccounts(new SyncStorageEngine.EndPoint(null, null, getSendingUserId()));
                        return;
                    case 3:
                        if ("android.intent.action.TIME_SET".equals(intent.getAction())) {
                            SyncStorageEngine syncStorageEngine5 = this.this$0.mSyncStorageEngine;
                            if (syncStorageEngine5.mIsClockValid) {
                                return;
                            }
                            syncStorageEngine5.mIsClockValid = true;
                            Slog.w("SyncManager", "Clock is valid now.");
                            return;
                        }
                        return;
                    default:
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        if (intExtra == -10000) {
                            return;
                        }
                        if ("android.intent.action.USER_REMOVED".equals(action)) {
                            SyncManager syncManager3 = this.this$0;
                            syncManager3.mLogger.log("onUserRemoved: u", Integer.valueOf(intExtra));
                            syncManager3.updateRunningAccounts(null);
                            syncManager3.mSyncStorageEngine.removeStaleAccounts(null, intExtra);
                            Iterator it = ((ArrayList) syncManager3.getAllPendingSyncs()).iterator();
                            while (it.hasNext()) {
                                SyncOperation syncOperation = (SyncOperation) it.next();
                                if (syncOperation.target.userId == intExtra) {
                                    syncManager3.cancelJob(syncOperation, "user removed u" + intExtra);
                                }
                            }
                            return;
                        }
                        if (!"android.intent.action.USER_UNLOCKED".equals(action)) {
                            if ("android.intent.action.USER_STOPPED".equals(action)) {
                                SyncManager syncManager4 = this.this$0;
                                syncManager4.updateRunningAccounts(null);
                                syncManager4.cancelActiveSync(new SyncStorageEngine.EndPoint(null, null, intExtra), null, "onUserStopped");
                                return;
                            }
                            return;
                        }
                        SyncManager syncManager5 = this.this$0;
                        syncManager5.getClass();
                        AccountManagerService singleton = AccountManagerService.getSingleton();
                        singleton.validateAccountsInternal(singleton.getUserAccounts(intExtra), true);
                        syncManager5.mSyncAdapters.invalidateCache(intExtra);
                        syncManager5.updateRunningAccounts(new SyncStorageEngine.EndPoint(null, null, intExtra));
                        for (Account account : AccountManagerService.getSingleton().getAccounts(intExtra, syncManager5.mContext.getOpPackageName())) {
                            syncManager5.scheduleSync(account, intExtra, -8, null, null, -1, 0, Process.myUid(), -3, null);
                        }
                        return;
                }
            }
        };
        final int i5 = 3;
        BroadcastReceiver broadcastReceiver4 = new BroadcastReceiver(this) { // from class: com.android.server.content.SyncManager.2
            public final /* synthetic */ SyncManager this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                SyncStorageEngine syncStorageEngine;
                SyncStorageEngine syncStorageEngine2;
                switch (i5) {
                    case 0:
                        boolean z2 = this.this$0.mDataConnectionIsConnected;
                        SyncManager syncManager = this.this$0;
                        NetworkInfo activeNetworkInfo = syncManager.getConnectivityManager().getActiveNetworkInfo();
                        syncManager.mDataConnectionIsConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
                        if (!this.this$0.mDataConnectionIsConnected || z2) {
                            return;
                        }
                        int i32 = 2;
                        if (Log.isLoggable("SyncManager", 2)) {
                            Slog.v("SyncManager", "Reconnection detected: clearing all backoffs");
                        }
                        SyncManager syncManager2 = this.this$0;
                        SyncStorageEngine syncStorageEngine3 = syncManager2.mSyncStorageEngine;
                        syncStorageEngine3.getClass();
                        ArraySet arraySet = new ArraySet();
                        synchronized (syncStorageEngine3.mAuthorities) {
                            try {
                                for (SyncStorageEngine.AccountInfo accountInfo : syncStorageEngine3.mAccounts.values()) {
                                    for (SyncStorageEngine.AuthorityInfo authorityInfo : accountInfo.authorities.values()) {
                                        if (authorityInfo.backoffTime == -1 && authorityInfo.backoffDelay == -1) {
                                            syncStorageEngine2 = syncStorageEngine3;
                                            syncStorageEngine3 = syncStorageEngine2;
                                            i32 = 2;
                                        }
                                        if (Log.isLoggable("SyncManager", i32)) {
                                            StringBuilder sb = new StringBuilder();
                                            sb.append("clearAllBackoffsLocked: authority:");
                                            sb.append(authorityInfo.target);
                                            sb.append(" account:");
                                            sb.append(accountInfo.accountAndUser.account.name);
                                            sb.append(" user:");
                                            sb.append(accountInfo.accountAndUser.userId);
                                            sb.append(" backoffTime was: ");
                                            syncStorageEngine2 = syncStorageEngine3;
                                            sb.append(authorityInfo.backoffTime);
                                            sb.append(" backoffDelay was: ");
                                            sb.append(authorityInfo.backoffDelay);
                                            Slog.v("SyncManager", sb.toString());
                                        } else {
                                            syncStorageEngine2 = syncStorageEngine3;
                                        }
                                        authorityInfo.backoffTime = -1L;
                                        authorityInfo.backoffDelay = -1L;
                                        arraySet.add(Integer.valueOf(accountInfo.accountAndUser.userId));
                                        syncStorageEngine3 = syncStorageEngine2;
                                        i32 = 2;
                                    }
                                }
                                syncStorageEngine = syncStorageEngine3;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        for (int size = arraySet.size() - 1; size > 0; size--) {
                            syncStorageEngine.reportChange(1, ((Integer) arraySet.valueAt(size)).intValue(), null);
                        }
                        syncManager2.rescheduleSyncs(SyncStorageEngine.EndPoint.USER_ALL_PROVIDER_ALL_ACCOUNTS_ALL, "network reconnect");
                        return;
                    case 1:
                        Log.w("SyncManager", "Writing sync state before shutdown...");
                        SyncStorageEngine syncStorageEngine4 = this.this$0.mSyncStorageEngine;
                        synchronized (syncStorageEngine4.mAuthorities) {
                            syncStorageEngine4.writeStatusLocked();
                            syncStorageEngine4.writeStatisticsLocked();
                        }
                        this.this$0.mLogger.log(SyncManager.getJobStats());
                        this.this$0.mLogger.log("Shutting down.");
                        return;
                    case 2:
                        this.this$0.updateRunningAccounts(new SyncStorageEngine.EndPoint(null, null, getSendingUserId()));
                        return;
                    case 3:
                        if ("android.intent.action.TIME_SET".equals(intent.getAction())) {
                            SyncStorageEngine syncStorageEngine5 = this.this$0.mSyncStorageEngine;
                            if (syncStorageEngine5.mIsClockValid) {
                                return;
                            }
                            syncStorageEngine5.mIsClockValid = true;
                            Slog.w("SyncManager", "Clock is valid now.");
                            return;
                        }
                        return;
                    default:
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        if (intExtra == -10000) {
                            return;
                        }
                        if ("android.intent.action.USER_REMOVED".equals(action)) {
                            SyncManager syncManager3 = this.this$0;
                            syncManager3.mLogger.log("onUserRemoved: u", Integer.valueOf(intExtra));
                            syncManager3.updateRunningAccounts(null);
                            syncManager3.mSyncStorageEngine.removeStaleAccounts(null, intExtra);
                            Iterator it = ((ArrayList) syncManager3.getAllPendingSyncs()).iterator();
                            while (it.hasNext()) {
                                SyncOperation syncOperation = (SyncOperation) it.next();
                                if (syncOperation.target.userId == intExtra) {
                                    syncManager3.cancelJob(syncOperation, "user removed u" + intExtra);
                                }
                            }
                            return;
                        }
                        if (!"android.intent.action.USER_UNLOCKED".equals(action)) {
                            if ("android.intent.action.USER_STOPPED".equals(action)) {
                                SyncManager syncManager4 = this.this$0;
                                syncManager4.updateRunningAccounts(null);
                                syncManager4.cancelActiveSync(new SyncStorageEngine.EndPoint(null, null, intExtra), null, "onUserStopped");
                                return;
                            }
                            return;
                        }
                        SyncManager syncManager5 = this.this$0;
                        syncManager5.getClass();
                        AccountManagerService singleton = AccountManagerService.getSingleton();
                        singleton.validateAccountsInternal(singleton.getUserAccounts(intExtra), true);
                        syncManager5.mSyncAdapters.invalidateCache(intExtra);
                        syncManager5.updateRunningAccounts(new SyncStorageEngine.EndPoint(null, null, intExtra));
                        for (Account account : AccountManagerService.getSingleton().getAccounts(intExtra, syncManager5.mContext.getOpPackageName())) {
                            syncManager5.scheduleSync(account, intExtra, -8, null, null, -1, 0, Process.myUid(), -3, null);
                        }
                        return;
                }
            }
        };
        final int i6 = 4;
        BroadcastReceiver broadcastReceiver5 = new BroadcastReceiver(this) { // from class: com.android.server.content.SyncManager.2
            public final /* synthetic */ SyncManager this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                SyncStorageEngine syncStorageEngine;
                SyncStorageEngine syncStorageEngine2;
                switch (i6) {
                    case 0:
                        boolean z2 = this.this$0.mDataConnectionIsConnected;
                        SyncManager syncManager = this.this$0;
                        NetworkInfo activeNetworkInfo = syncManager.getConnectivityManager().getActiveNetworkInfo();
                        syncManager.mDataConnectionIsConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
                        if (!this.this$0.mDataConnectionIsConnected || z2) {
                            return;
                        }
                        int i32 = 2;
                        if (Log.isLoggable("SyncManager", 2)) {
                            Slog.v("SyncManager", "Reconnection detected: clearing all backoffs");
                        }
                        SyncManager syncManager2 = this.this$0;
                        SyncStorageEngine syncStorageEngine3 = syncManager2.mSyncStorageEngine;
                        syncStorageEngine3.getClass();
                        ArraySet arraySet = new ArraySet();
                        synchronized (syncStorageEngine3.mAuthorities) {
                            try {
                                for (SyncStorageEngine.AccountInfo accountInfo : syncStorageEngine3.mAccounts.values()) {
                                    for (SyncStorageEngine.AuthorityInfo authorityInfo : accountInfo.authorities.values()) {
                                        if (authorityInfo.backoffTime == -1 && authorityInfo.backoffDelay == -1) {
                                            syncStorageEngine2 = syncStorageEngine3;
                                            syncStorageEngine3 = syncStorageEngine2;
                                            i32 = 2;
                                        }
                                        if (Log.isLoggable("SyncManager", i32)) {
                                            StringBuilder sb = new StringBuilder();
                                            sb.append("clearAllBackoffsLocked: authority:");
                                            sb.append(authorityInfo.target);
                                            sb.append(" account:");
                                            sb.append(accountInfo.accountAndUser.account.name);
                                            sb.append(" user:");
                                            sb.append(accountInfo.accountAndUser.userId);
                                            sb.append(" backoffTime was: ");
                                            syncStorageEngine2 = syncStorageEngine3;
                                            sb.append(authorityInfo.backoffTime);
                                            sb.append(" backoffDelay was: ");
                                            sb.append(authorityInfo.backoffDelay);
                                            Slog.v("SyncManager", sb.toString());
                                        } else {
                                            syncStorageEngine2 = syncStorageEngine3;
                                        }
                                        authorityInfo.backoffTime = -1L;
                                        authorityInfo.backoffDelay = -1L;
                                        arraySet.add(Integer.valueOf(accountInfo.accountAndUser.userId));
                                        syncStorageEngine3 = syncStorageEngine2;
                                        i32 = 2;
                                    }
                                }
                                syncStorageEngine = syncStorageEngine3;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        for (int size = arraySet.size() - 1; size > 0; size--) {
                            syncStorageEngine.reportChange(1, ((Integer) arraySet.valueAt(size)).intValue(), null);
                        }
                        syncManager2.rescheduleSyncs(SyncStorageEngine.EndPoint.USER_ALL_PROVIDER_ALL_ACCOUNTS_ALL, "network reconnect");
                        return;
                    case 1:
                        Log.w("SyncManager", "Writing sync state before shutdown...");
                        SyncStorageEngine syncStorageEngine4 = this.this$0.mSyncStorageEngine;
                        synchronized (syncStorageEngine4.mAuthorities) {
                            syncStorageEngine4.writeStatusLocked();
                            syncStorageEngine4.writeStatisticsLocked();
                        }
                        this.this$0.mLogger.log(SyncManager.getJobStats());
                        this.this$0.mLogger.log("Shutting down.");
                        return;
                    case 2:
                        this.this$0.updateRunningAccounts(new SyncStorageEngine.EndPoint(null, null, getSendingUserId()));
                        return;
                    case 3:
                        if ("android.intent.action.TIME_SET".equals(intent.getAction())) {
                            SyncStorageEngine syncStorageEngine5 = this.this$0.mSyncStorageEngine;
                            if (syncStorageEngine5.mIsClockValid) {
                                return;
                            }
                            syncStorageEngine5.mIsClockValid = true;
                            Slog.w("SyncManager", "Clock is valid now.");
                            return;
                        }
                        return;
                    default:
                        String action = intent.getAction();
                        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        if (intExtra == -10000) {
                            return;
                        }
                        if ("android.intent.action.USER_REMOVED".equals(action)) {
                            SyncManager syncManager3 = this.this$0;
                            syncManager3.mLogger.log("onUserRemoved: u", Integer.valueOf(intExtra));
                            syncManager3.updateRunningAccounts(null);
                            syncManager3.mSyncStorageEngine.removeStaleAccounts(null, intExtra);
                            Iterator it = ((ArrayList) syncManager3.getAllPendingSyncs()).iterator();
                            while (it.hasNext()) {
                                SyncOperation syncOperation = (SyncOperation) it.next();
                                if (syncOperation.target.userId == intExtra) {
                                    syncManager3.cancelJob(syncOperation, "user removed u" + intExtra);
                                }
                            }
                            return;
                        }
                        if (!"android.intent.action.USER_UNLOCKED".equals(action)) {
                            if ("android.intent.action.USER_STOPPED".equals(action)) {
                                SyncManager syncManager4 = this.this$0;
                                syncManager4.updateRunningAccounts(null);
                                syncManager4.cancelActiveSync(new SyncStorageEngine.EndPoint(null, null, intExtra), null, "onUserStopped");
                                return;
                            }
                            return;
                        }
                        SyncManager syncManager5 = this.this$0;
                        syncManager5.getClass();
                        AccountManagerService singleton = AccountManagerService.getSingleton();
                        singleton.validateAccountsInternal(singleton.getUserAccounts(intExtra), true);
                        syncManager5.mSyncAdapters.invalidateCache(intExtra);
                        syncManager5.updateRunningAccounts(new SyncStorageEngine.EndPoint(null, null, intExtra));
                        for (Account account : AccountManagerService.getSingleton().getAccounts(intExtra, syncManager5.mContext.getOpPackageName())) {
                            syncManager5.scheduleSync(account, intExtra, -8, null, null, -1, 0, Process.myUid(), -3, null);
                        }
                        return;
                }
            }
        };
        this.mUnlockedUsers = new SparseBooleanArray();
        synchronized (SyncManager.class) {
            try {
                if (sInstance == null) {
                    sInstance = this;
                } else {
                    Slog.wtf("SyncManager", "SyncManager instantiated multiple times");
                }
            } finally {
            }
        }
        this.mContext = context;
        this.mLogger = SyncLogger.getInstance();
        Looper looper = BackgroundThread.get().getLooper();
        if (SyncStorageEngine.sSyncStorageEngine == null) {
            SyncStorageEngine.sSyncStorageEngine = new SyncStorageEngine(context, Environment.getDataDirectory(), looper);
        }
        if (SyncStorageEngine.sSyncStorageEngine == null) {
            throw new IllegalStateException("not initialized");
        }
        SyncStorageEngine syncStorageEngine = SyncStorageEngine.sSyncStorageEngine;
        this.mSyncStorageEngine = syncStorageEngine;
        AnonymousClass7 anonymousClass7 = new AnonymousClass7();
        if (syncStorageEngine.mSyncRequestListener == null) {
            syncStorageEngine.mSyncRequestListener = anonymousClass7;
        }
        AnonymousClass7 anonymousClass72 = new AnonymousClass7();
        if (SyncStorageEngine.mPeriodicSyncAddedListener == null) {
            SyncStorageEngine.mPeriodicSyncAddedListener = anonymousClass72;
        }
        AnonymousClass7 anonymousClass73 = new AnonymousClass7();
        if (syncStorageEngine.mAuthorityRemovedListener == null) {
            syncStorageEngine.mAuthorityRemovedListener = anonymousClass73;
        }
        SyncAdaptersCache syncAdaptersCache = new SyncAdaptersCache(context);
        this.mSyncAdapters = syncAdaptersCache;
        HandlerThread handlerThread = new HandlerThread("SyncManager", 10);
        handlerThread.start();
        SyncHandler syncHandler = new SyncHandler(handlerThread.getLooper());
        this.mSyncHandler = syncHandler;
        syncAdaptersCache.setListener(new RegisteredServicesCacheListener() { // from class: com.android.server.content.SyncManager.10
            public final void onServiceChanged(Object obj, int i7, boolean z2) {
                SyncAdapterType syncAdapterType = (SyncAdapterType) obj;
                if (z2) {
                    return;
                }
                SyncManager.this.scheduleSync(null, -1, -3, syncAdapterType.authority, null, -2, 0, Process.myUid(), -1, null);
            }
        }, syncHandler);
        this.mConstants = new SyncManagerConstants(context);
        this.mAppCloningDeviceConfigHelper = AppCloningDeviceConfigHelper.getInstance(context);
        context.registerReceiver(broadcastReceiver2, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        IntentFilter intentFilter = new IntentFilter("android.intent.action.ACTION_SHUTDOWN");
        intentFilter.setPriority(1000);
        context.registerReceiver(broadcastReceiver3, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        intentFilter2.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter2.addAction("android.intent.action.USER_STOPPED");
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(broadcastReceiver5, userHandle, intentFilter2, null, null);
        new PackageMonitorImpl().register(context, (Looper) null, userHandle, false);
        context.registerReceiver(broadcastReceiver4, new IntentFilter("android.intent.action.TIME_SET"));
        if (z) {
            this.mNotificationMgr = null;
        } else {
            this.mNotificationMgr = (NotificationManager) context.getSystemService("notification");
        }
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        this.mPowerManager = powerManager;
        UserManager userManager = (UserManager) context.getSystemService("user");
        this.mUserManager = userManager;
        this.mAccountManager = (AccountManager) context.getSystemService("account");
        AccountManagerInternal accountManagerInternal = getAccountManagerInternal();
        this.mAccountManagerInternal = accountManagerInternal;
        this.mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mAmi = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        accountManagerInternal.addOnAppPermissionChangeListener(new AccountManagerInternal.OnAppPermissionChangeListener() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda2
            public final void onAppPermissionChanged(Account account, int i7) {
                SyncManager syncManager = SyncManager.this;
                if (syncManager.mAccountManagerInternal.hasAccountAccess(account, i7)) {
                    syncManager.scheduleSync(account, UserHandle.getUserId(i7), -2, null, null, 3, 0, Process.myUid(), -2, null);
                }
            }
        });
        this.mBatteryStats = IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats"));
        this.mSyncManagerWakeLock = powerManager.newWakeLock(1, "SyncLoopWakeLock");
        this.mSyncManagerWakeLock.setReferenceCounted(false);
        this.mProvisioned = isDeviceProvisioned();
        if (!this.mProvisioned) {
            final ContentResolver contentResolver = context.getContentResolver();
            ContentObserver contentObserver = new ContentObserver() { // from class: com.android.server.content.SyncManager.11
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(null);
                }

                @Override // android.database.ContentObserver
                public final void onChange(boolean z2) {
                    SyncManager.this.mProvisioned |= SyncManager.this.isDeviceProvisioned();
                    if (SyncManager.this.mProvisioned) {
                        contentResolver.unregisterContentObserver(this);
                    }
                }
            };
            synchronized (syncHandler) {
                try {
                    contentResolver.registerContentObserver(Settings.Global.getUriFor("device_provisioned"), false, contentObserver);
                    this.mProvisioned |= isDeviceProvisioned();
                    if (this.mProvisioned) {
                        contentResolver.unregisterContentObserver(contentObserver);
                    }
                } finally {
                }
            }
        }
        if (!z) {
            context.registerReceiverAsUser(broadcastReceiver, userHandle, new IntentFilter("android.accounts.LOGIN_ACCOUNTS_CHANGED"), null, null);
        }
        if (syncStorageEngine.mGrantSyncAdaptersAccountAccess) {
            List aliveUsers = userManager.getAliveUsers();
            int size = aliveUsers.size();
            int i7 = 0;
            while (i7 < size) {
                UserHandle userHandle2 = ((UserInfo) aliveUsers.get(i7)).getUserHandle();
                int identifier = userHandle2.getIdentifier();
                for (RegisteredServicesCache.ServiceInfo serviceInfo : this.mSyncAdapters.getAllServices(identifier)) {
                    String packageName = serviceInfo.componentName.getPackageName();
                    Account[] accountsByTypeAsUser = this.mAccountManager.getAccountsByTypeAsUser(((SyncAdapterType) serviceInfo.type).accountType, userHandle2);
                    int length = accountsByTypeAsUser.length;
                    int i8 = i;
                    while (i8 < length) {
                        Account account = accountsByTypeAsUser[i8];
                        if (canAccessAccount(account, packageName, identifier)) {
                            list = aliveUsers;
                        } else {
                            list = aliveUsers;
                            this.mAccountManager.updateAppPermission(account, "com.android.AccountManager.ACCOUNT_ACCESS_TOKEN_TYPE", serviceInfo.uid, true);
                        }
                        i8++;
                        aliveUsers = list;
                        i = 0;
                    }
                }
                i7++;
                i = 0;
            }
        }
        this.mLogger.log("Sync manager initialized: " + Build.FINGERPRINT);
    }

    public static void dumpDayStatistic(PrintWriter printWriter, SyncStorageEngine.DayStats dayStats) {
        printWriter.print("Success (");
        printWriter.print(dayStats.successCount);
        if (dayStats.successCount > 0) {
            printWriter.print(" for ");
            dumpTimeSec(printWriter, dayStats.successTime);
            printWriter.print(" avg=");
            dumpTimeSec(printWriter, dayStats.successTime / dayStats.successCount);
        }
        printWriter.print(") Failure (");
        printWriter.print(dayStats.failureCount);
        if (dayStats.failureCount > 0) {
            printWriter.print(" for ");
            dumpTimeSec(printWriter, dayStats.failureTime);
            printWriter.print(" avg=");
            dumpTimeSec(printWriter, dayStats.failureTime / dayStats.failureCount);
        }
        printWriter.println(")");
    }

    public static void dumpTimeSec(PrintWriter printWriter, long j) {
        printWriter.print(j / 1000);
        printWriter.print('.');
        printWriter.print((j / 100) % 10);
        printWriter.print('s');
    }

    public static void formatDurationHMS(StringBuilder sb, long j) {
        boolean z;
        long j2 = j / 1000;
        if (j2 < 0) {
            sb.append('-');
            j2 = -j2;
        }
        long j3 = j2 % 60;
        long j4 = j2 / 60;
        long j5 = j4 % 60;
        long j6 = j4 / 60;
        long j7 = j6 % 24;
        long j8 = j6 / 24;
        if (j8 > 0) {
            sb.append(j8);
            sb.append('d');
            z = true;
        } else {
            z = false;
        }
        if (printTwoDigitNumber(sb, j3, 's', printTwoDigitNumber(sb, j5, 'm', printTwoDigitNumber(sb, j7, 'h', z)))) {
            return;
        }
        sb.append("0s");
    }

    public static String formatTime(long j) {
        return j == 0 ? "N/A" : TimeMigrationUtils.formatMillisWithFixedFormat(j);
    }

    public static Intent getAdapterBindIntent(Context context, ComponentName componentName, int i) {
        Intent intent = new Intent();
        intent.setAction("android.content.SyncAdapter");
        intent.setComponent(componentName);
        intent.putExtra("android.intent.extra.client_label", 17043193);
        intent.putExtra("android.intent.extra.client_intent", PendingIntent.getActivityAsUser(context, 0, new Intent("android.settings.SYNC_SETTINGS"), 67108864, null, UserHandle.of(i)));
        return intent;
    }

    public static SyncManager getInstance() {
        SyncManager syncManager;
        synchronized (SyncManager.class) {
            try {
                if (sInstance == null) {
                    Slog.wtf("SyncManager", "sInstance == null");
                }
                syncManager = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return syncManager;
    }

    public static String getJobStats() {
        JobSchedulerInternal jobSchedulerInternal = (JobSchedulerInternal) LocalServices.getService(JobSchedulerInternal.class);
        StringBuilder sb = new StringBuilder("JobStats: ");
        sb.append(jobSchedulerInternal == null ? "(JobSchedulerInternal==null)" : jobSchedulerInternal.getPersistStats().toString());
        return sb.toString();
    }

    public static boolean printTwoDigitNumber(StringBuilder sb, long j, char c, boolean z) {
        if (!z && j == 0) {
            return false;
        }
        if (z && j < 10) {
            sb.append('0');
        }
        sb.append(j);
        sb.append(c);
        return true;
    }

    public static boolean readyToSync(int i) {
        boolean z;
        boolean z2;
        SyncManager syncManager = getInstance();
        if (syncManager == null) {
            return false;
        }
        synchronized (SyncJobService.sLock) {
            z = SyncJobService.sInstance != null;
        }
        if (!z || !syncManager.mProvisioned) {
            return false;
        }
        synchronized (syncManager.mUnlockedUsers) {
            z2 = syncManager.mUnlockedUsers.get(i);
        }
        return z2;
    }

    public static boolean syncExtrasEquals(Bundle bundle, Bundle bundle2, boolean z) {
        if (bundle == bundle2) {
            return true;
        }
        if (z && bundle.size() != bundle2.size()) {
            return false;
        }
        Bundle bundle3 = bundle.size() > bundle2.size() ? bundle : bundle2;
        if (bundle.size() > bundle2.size()) {
            bundle = bundle2;
        }
        for (String str : bundle3.keySet()) {
            if (z || str == null || (!str.equals("expedited") && !str.equals("schedule_as_expedited_job") && !str.equals("ignore_settings") && !str.equals("ignore_backoff") && !str.equals("do_not_retry") && !str.equals("force") && !str.equals("upload") && !str.equals("deletions_override") && !str.equals("discard_deletions") && !str.equals("expected_upload") && !str.equals("expected_download") && !str.equals("sync_priority") && !str.equals("allow_metered") && !str.equals("initialize"))) {
                if (!bundle.containsKey(str) || !Objects.equals(bundle3.get(str), bundle.get(str))) {
                    return false;
                }
            }
        }
        return true;
    }

    public final boolean canAccessAccount(Account account, String str, int i) {
        if (this.mAccountManager.hasAccountAccess(account, str, UserHandle.getUserHandleForUid(i))) {
            return true;
        }
        try {
            this.mContext.getPackageManager().getApplicationInfoAsUser(str, 1048576, UserHandle.getUserId(i));
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final void cancelActiveSync(SyncStorageEngine.EndPoint endPoint, Bundle bundle, String str) {
        if (Log.isLoggable("SyncManager", 2)) {
            Slog.v("SyncManager", "sending MESSAGE_CANCEL");
        }
        this.mLogger.log("sendCancelSyncsMessage() ep=", endPoint, " why=", str);
        SyncHandler syncHandler = this.mSyncHandler;
        Message obtainMessage = syncHandler.obtainMessage();
        obtainMessage.what = 6;
        obtainMessage.setData(bundle);
        obtainMessage.obj = endPoint;
        syncHandler.sendMessage(obtainMessage);
    }

    public final void cancelJob(SyncOperation syncOperation, String str) {
        if (syncOperation == null) {
            Slog.wtf("SyncManager", "Null sync operation detected.");
            return;
        }
        if (syncOperation.isPeriodic) {
            this.mLogger.log("Removing periodic sync ", syncOperation, " for ", str);
        }
        verifyJobScheduler();
        this.mJobScheduler.cancel(syncOperation.jobId);
    }

    public final void cancelScheduledSyncOperation(SyncStorageEngine.EndPoint endPoint, Bundle bundle) {
        Iterator it = ((ArrayList) getAllPendingSyncs()).iterator();
        while (it.hasNext()) {
            SyncOperation syncOperation = (SyncOperation) it.next();
            if (!syncOperation.isPeriodic && syncOperation.target.matchesSpec(endPoint) && syncExtrasEquals(syncOperation.mImmutableExtras, bundle, false)) {
                cancelJob(syncOperation, "cancelScheduledSyncOperation");
            }
        }
        setAuthorityPendingState(endPoint);
        if (this.mSyncStorageEngine.isSyncPending(endPoint)) {
            return;
        }
        this.mSyncStorageEngine.setBackoff(endPoint, -1L, -1L);
    }

    public final void clearScheduledSyncOperations(SyncStorageEngine.EndPoint endPoint) {
        Iterator it = ((ArrayList) getAllPendingSyncs()).iterator();
        while (it.hasNext()) {
            SyncOperation syncOperation = (SyncOperation) it.next();
            if (!syncOperation.isPeriodic) {
                SyncStorageEngine.EndPoint endPoint2 = syncOperation.target;
                if (endPoint2.matchesSpec(endPoint)) {
                    cancelJob(syncOperation, "clearScheduledSyncOperations");
                    this.mSyncStorageEngine.markPending(endPoint2, false);
                }
            }
        }
        this.mSyncStorageEngine.setBackoff(endPoint, -1L, -1L);
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00ad, code lost:
    
        if (r0.equals(r7.type) == false) goto L28;
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00b1 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00b2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int computeSyncable(android.accounts.Account r7, int r8, java.lang.String r9, boolean r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.content.SyncManager.computeSyncable(android.accounts.Account, int, java.lang.String, boolean, boolean):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:120:0x0430  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x045e  */
    /* JADX WARN: Type inference failed for: r9v31, types: [com.android.server.content.SyncManager$$ExternalSyntheticLambda8] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dump(java.io.PrintWriter r33, boolean r34) {
        /*
            Method dump skipped, instructions count: 1778
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.content.SyncManager.dump(java.io.PrintWriter, boolean):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:60:0x037d, code lost:
    
        if (r8.downstreamActivity == 0) goto L68;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dumpSyncHistory(java.io.PrintWriter r30) {
        /*
            Method dump skipped, instructions count: 1275
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.content.SyncManager.dumpSyncHistory(java.io.PrintWriter):void");
    }

    public AccountManagerInternal getAccountManagerInternal() {
        return (AccountManagerInternal) LocalServices.getService(AccountManagerInternal.class);
    }

    public final List getAllPendingSyncs() {
        verifyJobScheduler();
        List<JobInfo> allPendingJobs = this.mJobScheduler.getAllPendingJobs();
        int size = allPendingJobs.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            SyncOperation maybeCreateFromJobExtras = SyncOperation.maybeCreateFromJobExtras(allPendingJobs.get(i).getExtras());
            if (maybeCreateFromJobExtras != null) {
                arrayList.add(maybeCreateFromJobExtras);
            } else {
                Slog.wtf("SyncManager", "Non-sync job inside of SyncManager's namespace");
            }
        }
        return arrayList;
    }

    public final ConnectivityManager getConnectivityManager() {
        ConnectivityManager connectivityManager;
        synchronized (this) {
            try {
                if (this.mConnManagerDoNotUseDirectly == null) {
                    this.mConnManagerDoNotUseDirectly = (ConnectivityManager) this.mContext.getSystemService("connectivity");
                }
                connectivityManager = this.mConnManagerDoNotUseDirectly;
            } catch (Throwable th) {
                throw th;
            }
        }
        return connectivityManager;
    }

    public final List getPeriodicSyncs(SyncStorageEngine.EndPoint endPoint) {
        List allPendingSyncs = getAllPendingSyncs();
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) allPendingSyncs).iterator();
        while (it.hasNext()) {
            SyncOperation syncOperation = (SyncOperation) it.next();
            if (syncOperation.isPeriodic) {
                SyncStorageEngine.EndPoint endPoint2 = syncOperation.target;
                if (endPoint2.matchesSpec(endPoint)) {
                    arrayList.add(new PeriodicSync(endPoint2.account, endPoint2.provider, new Bundle(syncOperation.mImmutableExtras), syncOperation.periodMillis / 1000, syncOperation.flexMillis / 1000));
                }
            }
        }
        return arrayList;
    }

    public final SyncAdapterType[] getSyncAdapterTypes(int i, int i2) {
        Collection<RegisteredServicesCache.ServiceInfo> allServices = this.mSyncAdapters.getAllServices(i2);
        ArrayList arrayList = new ArrayList(allServices.size());
        for (RegisteredServicesCache.ServiceInfo serviceInfo : allServices) {
            String packageName = ((SyncAdapterType) serviceInfo.type).getPackageName();
            if (TextUtils.isEmpty(packageName) || !this.mPackageManagerInternal.filterAppAccess(i, i2, packageName, true)) {
                arrayList.add((SyncAdapterType) serviceInfo.type);
            }
        }
        return (SyncAdapterType[]) arrayList.toArray(new SyncAdapterType[0]);
    }

    public final boolean isDeviceProvisioned() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0;
    }

    public final boolean isPackageStopped(int i, String str) {
        if (!Flags.stayStopped()) {
            return false;
        }
        try {
            return ((PackageManagerService.PackageManagerInternalImpl) this.mPackageManagerInternal).mService.snapshotComputer().isPackageStoppedForUser(str, i);
        } catch (PackageManager.NameNotFoundException unused) {
            StorageManagerService$$ExternalSyntheticOutline0.m("Couldn't determine stopped state for unknown package: ", str, "SyncManager");
            return false;
        }
    }

    public final void migrateSyncJobNamespaceIfNeeded() {
        boolean z = this.mSyncStorageEngine.mIsJobNamespaceMigrated;
        boolean z2 = this.mSyncStorageEngine.mIsJobAttributionFixed;
        if (z && z2) {
            return;
        }
        JobScheduler jobScheduler = (JobScheduler) this.mContext.getSystemService(JobScheduler.class);
        if (!z) {
            List<JobInfo> allPendingJobs = jobScheduler.getAllPendingJobs();
            boolean z3 = true;
            for (int size = allPendingJobs.size() - 1; size >= 0; size--) {
                JobInfo jobInfo = allPendingJobs.get(size);
                SyncOperation maybeCreateFromJobExtras = SyncOperation.maybeCreateFromJobExtras(jobInfo.getExtras());
                if (maybeCreateFromJobExtras != null) {
                    this.mJobScheduler.scheduleAsPackage(jobInfo, maybeCreateFromJobExtras.owningPackage, maybeCreateFromJobExtras.target.userId, maybeCreateFromJobExtras.wakeLockName());
                    jobScheduler.cancel(jobInfo.getId());
                    z3 = false;
                }
            }
            SyncStorageEngine syncStorageEngine = this.mSyncStorageEngine;
            if (syncStorageEngine.mIsJobNamespaceMigrated != z3) {
                syncStorageEngine.mIsJobNamespaceMigrated = z3;
                syncStorageEngine.mHandler.sendEmptyMessageDelayed(1, 600000L);
            }
        }
        List systemScheduledOwnJobs = ((JobSchedulerInternal) LocalServices.getService(JobSchedulerInternal.class)).getSystemScheduledOwnJobs(this.mJobScheduler.getNamespace());
        boolean z4 = true;
        for (int size2 = systemScheduledOwnJobs.size() - 1; size2 >= 0; size2--) {
            JobInfo jobInfo2 = (JobInfo) systemScheduledOwnJobs.get(size2);
            SyncOperation maybeCreateFromJobExtras2 = SyncOperation.maybeCreateFromJobExtras(jobInfo2.getExtras());
            if (maybeCreateFromJobExtras2 != null) {
                this.mJobScheduler.scheduleAsPackage(jobInfo2, maybeCreateFromJobExtras2.owningPackage, maybeCreateFromJobExtras2.target.userId, maybeCreateFromJobExtras2.wakeLockName());
                z4 = false;
            }
        }
        SyncStorageEngine syncStorageEngine2 = this.mSyncStorageEngine;
        if (syncStorageEngine2.mIsJobAttributionFixed == z4) {
            return;
        }
        syncStorageEngine2.mIsJobAttributionFixed = z4;
        syncStorageEngine2.mHandler.sendEmptyMessageDelayed(1, 600000L);
    }

    public final void postScheduleSyncMessage(SyncOperation syncOperation, long j) {
        this.mSyncHandler.obtainMessage(12, new ScheduleSyncMessagePayload(syncOperation, j)).sendToTarget();
    }

    public final void rescheduleSyncs(SyncStorageEngine.EndPoint endPoint, String str) {
        this.mLogger.log("rescheduleSyncs() ep=", endPoint, " why=", str);
        Iterator it = ((ArrayList) getAllPendingSyncs()).iterator();
        int i = 0;
        while (it.hasNext()) {
            SyncOperation syncOperation = (SyncOperation) it.next();
            if (!syncOperation.isPeriodic && syncOperation.target.matchesSpec(endPoint)) {
                i++;
                cancelJob(syncOperation, str);
                postScheduleSyncMessage(syncOperation, 0L);
            }
        }
        if (Log.isLoggable("SyncManager", 2)) {
            Slog.v("SyncManager", "Rescheduled " + i + " syncs for " + endPoint);
        }
    }

    public final void scheduleSync(Account account, int i, int i2, String str, Bundle bundle, int i3, int i4, int i5, int i6, String str2) {
        scheduleSync(account, i, i2, str, bundle, i3, 0L, true, i4, i5, i6, str2);
    }

    public final void scheduleSync(Account account, final int i, final int i2, String str, Bundle bundle, final int i3, final long j, boolean z, final int i4, final int i5, final int i6, final String str2) {
        int i7;
        Bundle bundle2;
        boolean z2;
        char c;
        int i8;
        AccountAndUser[] accountAndUserArr;
        AccountAndUser[] accountAndUserArr2;
        String str3;
        int i9;
        RegisteredServicesCache.ServiceInfo serviceInfo;
        boolean z3;
        SyncManager syncManager;
        AccountAndUser accountAndUser;
        char c2;
        Bundle bundle3;
        boolean z4;
        long j2;
        int i10;
        Bundle bundle4 = bundle == null ? new Bundle() : bundle;
        bundle4.size();
        if (Log.isLoggable("SyncManager", 2)) {
            i7 = 2;
            bundle2 = bundle4;
            this.mLogger.log("scheduleSync: account=", account, " u", Integer.valueOf(i), " authority=", str, " reason=", Integer.valueOf(i2), " extras=", bundle2, " cuid=", Integer.valueOf(i5), " cpid=", Integer.valueOf(i6), " cpkg=", str2, " mdm=", Long.valueOf(j), " ciar=", Boolean.valueOf(z), " sef=", Integer.valueOf(i4));
        } else {
            i7 = 2;
            bundle2 = bundle4;
        }
        SyncManager syncManager2 = this;
        synchronized (syncManager2.mAccountsLock) {
            z2 = true;
            c = 65535;
            try {
                if (account != null) {
                    i8 = i;
                    if (i8 != -1) {
                        accountAndUserArr = new AccountAndUser[]{new AccountAndUser(account, i8)};
                    } else {
                        AccountAndUser[] accountAndUserArr3 = null;
                        for (AccountAndUser accountAndUser2 : syncManager2.mRunningAccounts) {
                            if (account.equals(accountAndUser2.account)) {
                                accountAndUserArr3 = (AccountAndUser[]) ArrayUtils.appendElement(AccountAndUser.class, accountAndUserArr3, accountAndUser2);
                            }
                        }
                        accountAndUserArr2 = accountAndUserArr3;
                    }
                } else {
                    i8 = i;
                    accountAndUserArr = syncManager2.mRunningAccounts;
                }
                accountAndUserArr2 = accountAndUserArr;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (ArrayUtils.isEmpty(accountAndUserArr2)) {
            return;
        }
        Bundle bundle5 = bundle2;
        boolean z5 = bundle5.getBoolean("upload", false);
        boolean z6 = bundle5.getBoolean("force", false);
        if (z6) {
            bundle5.putBoolean("ignore_backoff", true);
            bundle5.putBoolean("ignore_settings", true);
        }
        boolean z7 = bundle5.getBoolean("ignore_settings", false);
        int i11 = 3;
        if (z5) {
            str3 = str;
            i9 = 1;
        } else if (z6) {
            str3 = str;
            i9 = 3;
        } else {
            str3 = str;
            i9 = str3 == null ? i7 : bundle5.containsKey("feed") ? 5 : 0;
        }
        int length = accountAndUserArr2.length;
        int i12 = 0;
        while (i12 < length) {
            AccountAndUser accountAndUser3 = accountAndUserArr2[i12];
            if (i8 < 0 || (i10 = accountAndUser3.userId) < 0 || i8 == i10) {
                HashSet hashSet = new HashSet();
                Iterator it = syncManager2.mSyncAdapters.getAllServices(accountAndUser3.userId).iterator();
                while (it.hasNext()) {
                    hashSet.add(((SyncAdapterType) ((RegisteredServicesCache.ServiceInfo) it.next()).type).authority);
                }
                if (str3 != null) {
                    boolean contains = hashSet.contains(str3);
                    hashSet.clear();
                    if (contains) {
                        hashSet.add(str3);
                    }
                }
                Iterator it2 = hashSet.iterator();
                while (it2.hasNext()) {
                    final String str4 = (String) it2.next();
                    final AccountAndUser accountAndUser4 = accountAndUser3;
                    int i13 = i12;
                    int computeSyncable = computeSyncable(accountAndUser3.account, accountAndUser3.userId, str4, !z, true);
                    if (computeSyncable != 0 && (serviceInfo = syncManager2.mSyncAdapters.getServiceInfo(SyncAdapterType.newKey(str4, accountAndUser4.account.type), accountAndUser4.userId)) != null) {
                        int i14 = serviceInfo.uid;
                        if (computeSyncable == i11) {
                            syncManager2.mLogger.log("scheduleSync: Not scheduling sync operation: isSyncable == SYNCABLE_NO_ACCOUNT_ACCESS");
                            final Bundle bundle6 = new Bundle(bundle5);
                            String packageName = serviceInfo.componentName.getPackageName();
                            try {
                                z3 = syncManager2.mPackageManagerInternal.wasPackageEverLaunched(i8, packageName);
                            } catch (IllegalArgumentException unused) {
                                z3 = false;
                            }
                            if (z3) {
                                syncManager2.mAccountManagerInternal.requestAccountAccess(accountAndUser4.account, packageName, i, new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda4
                                    public final void onResult(Bundle bundle7) {
                                        SyncManager syncManager3 = SyncManager.this;
                                        AccountAndUser accountAndUser5 = accountAndUser4;
                                        int i15 = i;
                                        int i16 = i2;
                                        String str5 = str4;
                                        Bundle bundle8 = bundle6;
                                        int i17 = i3;
                                        long j3 = j;
                                        int i18 = i4;
                                        int i19 = i5;
                                        int i20 = i6;
                                        String str6 = str2;
                                        syncManager3.getClass();
                                        if (bundle7 == null || !bundle7.getBoolean("booleanResult")) {
                                            return;
                                        }
                                        syncManager3.scheduleSync(accountAndUser5.account, i15, i16, str5, bundle8, i17, j3, true, i18, i19, i20, str6);
                                    }
                                }));
                                c = 65535;
                                z2 = true;
                                accountAndUser3 = accountAndUser4;
                                i8 = i;
                                i12 = i13;
                                length = length;
                                bundle5 = bundle5;
                                i11 = i11;
                                accountAndUserArr2 = accountAndUserArr2;
                                syncManager2 = this;
                            }
                        } else {
                            int i15 = length;
                            Bundle bundle7 = bundle5;
                            int i16 = i11;
                            AccountAndUser[] accountAndUserArr4 = accountAndUserArr2;
                            int i17 = i8;
                            boolean allowParallelSyncs = ((SyncAdapterType) serviceInfo.type).allowParallelSyncs();
                            boolean isAlwaysSyncable = ((SyncAdapterType) serviceInfo.type).isAlwaysSyncable();
                            if (z || computeSyncable >= 0 || !isAlwaysSyncable) {
                                syncManager = this;
                                accountAndUser = accountAndUser4;
                            } else {
                                syncManager = this;
                                accountAndUser = accountAndUser4;
                                syncManager.mSyncStorageEngine.setIsSyncable(accountAndUser.account, accountAndUser.userId, str4, 1, i5, i6);
                                computeSyncable = 1;
                            }
                            if ((i3 == -2 || i3 == computeSyncable) && (((SyncAdapterType) serviceInfo.type).supportsUploading() || !z5)) {
                                if (computeSyncable < 0 || z7 || (syncManager.mSyncStorageEngine.getMasterSyncAutomatically(accountAndUser.userId) && syncManager.mSyncStorageEngine.getSyncAutomatically(accountAndUser.account, str4, accountAndUser.userId))) {
                                    syncManager.mSyncStorageEngine.getDelayUntilTime(new SyncStorageEngine.EndPoint(accountAndUser.account, str4, accountAndUser.userId));
                                    String packageName2 = serviceInfo.componentName.getPackageName();
                                    if (computeSyncable != -1) {
                                        c2 = 65535;
                                        bundle3 = bundle7;
                                        z4 = true;
                                        j2 = j;
                                        if (i3 == -2 || i3 == computeSyncable) {
                                            syncManager.mLogger.log("scheduleSync: scheduling sync ", accountAndUser, " ", str4);
                                            syncManager.postScheduleSyncMessage(new SyncOperation(accountAndUser.account, accountAndUser.userId, i14, packageName2, i2, i9, str4, bundle3, allowParallelSyncs, i4), j);
                                            i8 = i;
                                            syncManager2 = syncManager;
                                            accountAndUser3 = accountAndUser;
                                            i12 = i13;
                                            bundle5 = bundle3;
                                            length = i15;
                                            i11 = i16;
                                            accountAndUserArr2 = accountAndUserArr4;
                                            z2 = z4;
                                            c = c2;
                                        } else {
                                            syncManager.mLogger.log("scheduleSync: not handling ", accountAndUser, " ", str4);
                                            i8 = i;
                                            syncManager2 = syncManager;
                                            accountAndUser3 = accountAndUser;
                                            i12 = i13;
                                            bundle5 = bundle3;
                                            length = i15;
                                            i11 = i16;
                                            accountAndUserArr2 = accountAndUserArr4;
                                            z2 = z4;
                                            c = c2;
                                        }
                                    } else if (z) {
                                        Bundle bundle8 = new Bundle(bundle7);
                                        final Context context = syncManager.mContext;
                                        int i18 = accountAndUser.userId;
                                        AccountAndUser accountAndUser5 = accountAndUser;
                                        bundle3 = bundle7;
                                        c2 = 65535;
                                        final OnUnsyncableAccountCheck onUnsyncableAccountCheck = new OnUnsyncableAccountCheck(serviceInfo, new SyncManager$$ExternalSyntheticLambda5(this, accountAndUser, i2, str4, bundle8, i3, j, i4, i5, i6, str2));
                                        if (context.bindServiceAsUser(getAdapterBindIntent(context, serviceInfo.componentName, i18), onUnsyncableAccountCheck, SYNC_ADAPTER_CONNECTION_FLAGS, UserHandle.of(i18))) {
                                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.android.server.content.SyncManager$$ExternalSyntheticLambda11
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    context.unbindService(onUnsyncableAccountCheck);
                                                }
                                            }, 5000L);
                                        } else {
                                            onUnsyncableAccountCheck.onReady();
                                        }
                                        syncManager = this;
                                        accountAndUser = accountAndUser5;
                                        z4 = true;
                                        i8 = i;
                                        syncManager2 = syncManager;
                                        accountAndUser3 = accountAndUser;
                                        i12 = i13;
                                        bundle5 = bundle3;
                                        length = i15;
                                        i11 = i16;
                                        accountAndUserArr2 = accountAndUserArr4;
                                        z2 = z4;
                                        c = c2;
                                    } else {
                                        c2 = 65535;
                                        bundle3 = bundle7;
                                        Bundle bundle9 = new Bundle();
                                        z4 = true;
                                        bundle9.putBoolean("initialize", true);
                                        syncManager = this;
                                        syncManager.mLogger.log("scheduleSync: schedule initialisation sync ", accountAndUser, " ", str4);
                                        j2 = j;
                                        syncManager.postScheduleSyncMessage(new SyncOperation(accountAndUser.account, accountAndUser.userId, i14, packageName2, i2, i9, str4, bundle9, allowParallelSyncs, i4), j2);
                                        i8 = i;
                                        syncManager2 = syncManager;
                                        accountAndUser3 = accountAndUser;
                                        i12 = i13;
                                        bundle5 = bundle3;
                                        length = i15;
                                        i11 = i16;
                                        accountAndUserArr2 = accountAndUserArr4;
                                        z2 = z4;
                                        c = c2;
                                    }
                                } else {
                                    syncManager.mLogger.log("scheduleSync: sync of ", accountAndUser, " ", str4, " is not allowed, dropping request");
                                }
                            }
                            i8 = i17;
                            syncManager2 = syncManager;
                            accountAndUser3 = accountAndUser;
                            i12 = i13;
                            length = i15;
                            bundle5 = bundle7;
                            i11 = i16;
                            accountAndUserArr2 = accountAndUserArr4;
                            c = 65535;
                            z2 = true;
                        }
                    }
                    accountAndUser3 = accountAndUser4;
                    i12 = i13;
                }
            }
            i12++;
            i8 = i;
            str3 = str;
            syncManager2 = syncManager2;
            bundle5 = bundle5;
            length = length;
            i11 = i11;
            accountAndUserArr2 = accountAndUserArr2;
            z2 = z2;
            c = c;
        }
    }

    public final void scheduleSyncOperationH(SyncOperation syncOperation) {
        scheduleSyncOperationH(syncOperation, 0L);
    }

    /* JADX WARN: Removed duplicated region for block: B:74:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x01db  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01e5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void scheduleSyncOperationH(com.android.server.content.SyncOperation r18, long r19) {
        /*
            Method dump skipped, instructions count: 906
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.content.SyncManager.scheduleSyncOperationH(com.android.server.content.SyncOperation, long):void");
    }

    public final void setAuthorityPendingState(SyncStorageEngine.EndPoint endPoint) {
        Iterator it = ((ArrayList) getAllPendingSyncs()).iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            SyncStorageEngine syncStorageEngine = this.mSyncStorageEngine;
            if (!hasNext) {
                syncStorageEngine.markPending(endPoint, false);
                return;
            }
            SyncOperation syncOperation = (SyncOperation) it.next();
            if (!syncOperation.isPeriodic && syncOperation.target.matchesSpec(endPoint)) {
                syncStorageEngine.markPending(endPoint, true);
                return;
            }
        }
    }

    public boolean shouldDisableSyncForUser(UserInfo userInfo, String str) {
        boolean z;
        if (userInfo == null || str == null || !this.mContext.getResources().getBoolean(R.bool.config_enableContextSyncInCall) || !this.mAppCloningDeviceConfigHelper.getEnableAppCloningBuildingBlocks() || !str.equals("com.android.contacts")) {
            return false;
        }
        try {
            z = !UserManager.get(this.mContext).getUserProperties(userInfo.getUserHandle()).getUseParentsContacts();
        } catch (IllegalArgumentException unused) {
            Log.w("SyncManager", "Trying to fetch user properties for non-existing/partial user " + userInfo.getUserHandle());
            z = false;
        }
        return !z;
    }

    public final void updateOrAddPeriodicSync(SyncStorageEngine.EndPoint endPoint, long j, long j2, Bundle bundle) {
        this.mSyncHandler.obtainMessage(13, new UpdatePeriodicSyncMessagePayload(endPoint, j, j2, bundle)).sendToTarget();
    }

    public final void updateRunningAccounts(SyncStorageEngine.EndPoint endPoint) {
        if (Log.isLoggable("SyncManager", 2)) {
            Slog.v("SyncManager", "sending MESSAGE_ACCOUNTS_UPDATED");
        }
        Message obtainMessage = this.mSyncHandler.obtainMessage(9);
        obtainMessage.obj = endPoint;
        obtainMessage.sendToTarget();
    }

    public final synchronized void verifyJobScheduler() {
        int size;
        if (this.mJobScheduler != null) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (Log.isLoggable("SyncManager", 2)) {
                Log.d("SyncManager", "initializing JobScheduler object.");
            }
            this.mJobScheduler = ((JobScheduler) this.mContext.getSystemService(JobScheduler.class)).forNamespace("SyncManager");
            migrateSyncJobNamespaceIfNeeded();
            List<JobInfo> allPendingJobs = this.mJobScheduler.getAllPendingJobs();
            Iterator<JobInfo> it = allPendingJobs.iterator();
            int i = 0;
            int i2 = 0;
            while (it.hasNext()) {
                SyncOperation maybeCreateFromJobExtras = SyncOperation.maybeCreateFromJobExtras(it.next().getExtras());
                if (maybeCreateFromJobExtras == null) {
                    Slog.wtf("SyncManager", "Non-sync job inside of SyncManager namespace");
                } else if (maybeCreateFromJobExtras.isPeriodic) {
                    i++;
                } else {
                    i2++;
                    this.mSyncStorageEngine.markPending(maybeCreateFromJobExtras.target, true);
                }
            }
            String str = "Loaded persisted syncs: " + i + " periodic syncs, " + i2 + " oneshot syncs, " + allPendingJobs.size() + " total system server jobs, " + getJobStats();
            Slog.i("SyncManager", str);
            this.mLogger.log(str);
            this.mSyncHandler.postAtFrontOfQueue(new Runnable() { // from class: com.android.server.content.SyncManager.6
                @Override // java.lang.Runnable
                public final void run() {
                    List allPendingSyncs = SyncManager.this.getAllPendingSyncs();
                    HashSet hashSet = new HashSet();
                    ArrayList arrayList = (ArrayList) allPendingSyncs;
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        SyncOperation syncOperation = (SyncOperation) it2.next();
                        if (!hashSet.contains(syncOperation.key)) {
                            String str2 = syncOperation.key;
                            hashSet.add(str2);
                            Iterator it3 = arrayList.iterator();
                            while (it3.hasNext()) {
                                SyncOperation syncOperation2 = (SyncOperation) it3.next();
                                if (syncOperation != syncOperation2 && str2.equals(syncOperation2.key)) {
                                    SyncManager.this.mLogger.log("Removing duplicate sync: ", syncOperation2);
                                    SyncManager.this.cancelJob(syncOperation2, "cleanupJobs() x=" + syncOperation + " y=" + syncOperation2);
                                }
                            }
                        }
                    }
                }
            });
            if (ENABLE_SUSPICIOUS_CHECK && i == 0) {
                try {
                    SyncStorageEngine syncStorageEngine = this.mSyncStorageEngine;
                    synchronized (syncStorageEngine.mAuthorities) {
                        size = syncStorageEngine.mAuthorities.size();
                    }
                    if (size >= 6) {
                        Slog.wtf("SyncManager", "Device booted with no persisted periodic syncs: " + str);
                    }
                } catch (Throwable unused) {
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
