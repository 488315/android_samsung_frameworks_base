package com.android.server.content;

import android.accounts.Account;
import android.app.ActivityManagerInternal;
import android.app.usage.UsageStatsManagerInternal;
import android.content.pm.PackageManager;
import android.content.pm.UserPackage;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Slog;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.content.SyncStorageEngine;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SyncOperation {
    public static final String[] REASON_NAMES = {"DataSettingsChanged", "AccountsUpdated", "ServiceChanged", "Periodic", "IsSyncable", "AutoSync", "MasterSyncAuto", "UserStart"};
    public final boolean allowParallelSyncs;
    public long expectedRuntime;
    public final long flexMillis;
    public final boolean isPeriodic;
    public int jobId;
    public final String key;
    public volatile Bundle mImmutableExtras;
    public final String owningPackage;
    public final int owningUid;
    public final long periodMillis;
    public final int reason;
    public int retries;
    public boolean scheduleEjAsRegularJob;
    public final int sourcePeriodicId;
    public int syncExemptionFlag;
    public final int syncSource;
    public final SyncStorageEngine.EndPoint target;
    public String wakeLockName;

    public SyncOperation(Account account, int i, int i2, String str, int i3, int i4, String str2, Bundle bundle, boolean z, int i5) {
        this(new SyncStorageEngine.EndPoint(account, str2, i), i2, str, i3, i4, bundle, z, false, -1, 0L, 0L, i5);
    }

    public SyncOperation(SyncStorageEngine.EndPoint endPoint, int i, String str, int i2, int i3, Bundle bundle, boolean z, boolean z2, int i4, long j, long j2, int i5) {
        this.target = endPoint;
        this.owningUid = i;
        this.owningPackage = str;
        this.reason = i2;
        this.syncSource = i3;
        this.mImmutableExtras = new Bundle(bundle);
        this.allowParallelSyncs = z;
        this.isPeriodic = z2;
        this.sourcePeriodicId = i4;
        this.periodMillis = j;
        this.flexMillis = j2;
        this.jobId = -1;
        Bundle bundle2 = this.mImmutableExtras;
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m("provider: ");
        m.append(endPoint.provider);
        m.append(" account {name=" + endPoint.account.name + ", user=" + endPoint.userId + ", type=" + endPoint.account.type + "}");
        m.append(" isPeriodic: ");
        m.append(z2);
        m.append(" period: ");
        m.append(j);
        BootReceiver$$ExternalSyntheticOutline0.m(m, " flex: ", j2, " extras: ");
        extrasToStringBuilder(bundle2, m);
        this.key = m.toString();
        this.syncExemptionFlag = i5;
    }

    public static void extrasToStringBuilder(Bundle bundle, StringBuilder sb) {
        if (bundle == null) {
            sb.append("null");
            return;
        }
        sb.append("[");
        for (String str : bundle.keySet()) {
            sb.append(str);
            sb.append("=");
            sb.append(bundle.get(str));
            sb.append(" ");
        }
        sb.append("]");
    }

    public static SyncOperation maybeCreateFromJobExtras(PersistableBundle persistableBundle) {
        Iterator<String> it;
        if (persistableBundle == null || !persistableBundle.getBoolean("SyncManagerJob", false)) {
            return null;
        }
        String string = persistableBundle.getString("accountName");
        String string2 = persistableBundle.getString("accountType");
        String string3 = persistableBundle.getString("provider");
        int i = persistableBundle.getInt("userId", Integer.MAX_VALUE);
        int i2 = persistableBundle.getInt("owningUid");
        String string4 = persistableBundle.getString("owningPackage");
        int i3 = persistableBundle.getInt("reason", Integer.MAX_VALUE);
        int i4 = persistableBundle.getInt("source", Integer.MAX_VALUE);
        boolean z = persistableBundle.getBoolean("allowParallelSyncs", false);
        boolean z2 = persistableBundle.getBoolean("isPeriodic", false);
        int i5 = persistableBundle.getInt("sourcePeriodicId", -1);
        long j = persistableBundle.getLong("periodMillis");
        long j2 = persistableBundle.getLong("flexMillis");
        int i6 = persistableBundle.getInt("syncExemptionFlag", 0);
        Bundle bundle = new Bundle();
        PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle("syncExtras");
        if (persistableBundle2 != null) {
            bundle.putAll(persistableBundle2);
        }
        Iterator<String> it2 = persistableBundle.keySet().iterator();
        while (it2.hasNext()) {
            String next = it2.next();
            if (next == null || !next.startsWith("ACCOUNT:")) {
                it = it2;
            } else {
                String substring = next.substring(8);
                PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(next);
                it = it2;
                bundle.putParcelable(substring, new Account(persistableBundle3.getString("accountName"), persistableBundle3.getString("accountType")));
            }
            it2 = it;
        }
        SyncOperation syncOperation = new SyncOperation(new SyncStorageEngine.EndPoint(new Account(string, string2), string3, i), i2, string4, i3, i4, bundle, z, z2, i5, j, j2, i6);
        syncOperation.jobId = persistableBundle.getInt("jobId");
        syncOperation.expectedRuntime = persistableBundle.getLong("expectedRuntime");
        syncOperation.retries = persistableBundle.getInt("retries");
        syncOperation.scheduleEjAsRegularJob = persistableBundle.getBoolean("ejDowngradedToRegular");
        return syncOperation;
    }

    public static String reasonToString(PackageManager packageManager, int i) {
        if (i < 0) {
            int i2 = (-i) - 1;
            return i2 >= 8 ? String.valueOf(i) : REASON_NAMES[i2];
        }
        if (packageManager == null) {
            return String.valueOf(i);
        }
        String[] packagesForUid = packageManager.getPackagesForUid(i);
        if (packagesForUid != null && packagesForUid.length == 1) {
            return packagesForUid[0];
        }
        String nameForUid = packageManager.getNameForUid(i);
        return nameForUid != null ? nameForUid : String.valueOf(i);
    }

    public final SyncOperation createOneTimeSyncOperation() {
        if (this.isPeriodic) {
            return new SyncOperation(this.target, this.owningUid, this.owningPackage, this.reason, this.syncSource, this.mImmutableExtras, this.allowParallelSyncs, false, this.jobId, this.periodMillis, this.flexMillis, 0);
        }
        return null;
    }

    public final String dump(PackageManager packageManager, boolean z, SyncAdapterStateFetcher syncAdapterStateFetcher, boolean z2) {
        int appStandbyBucket;
        Bundle bundle = this.mImmutableExtras;
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m("JobId=");
        m.append(this.jobId);
        m.append(" ");
        m.append(z2 ? "***" : this.target.account.name);
        m.append("/");
        m.append(this.target.account.type);
        m.append(" u");
        m.append(this.target.userId);
        m.append(" [");
        m.append(this.target.provider);
        m.append("] ");
        m.append(SyncStorageEngine.SOURCES[this.syncSource]);
        if (this.expectedRuntime != 0) {
            m.append(" ExpectedIn=");
            SyncManager.formatDurationHMS(m, this.expectedRuntime - SystemClock.elapsedRealtime());
        }
        if (bundle.getBoolean("expedited", false)) {
            m.append(" EXPEDITED");
        }
        if (bundle.getBoolean("schedule_as_expedited_job", false)) {
            m.append(" EXPEDITED-JOB");
            if (this.scheduleEjAsRegularJob) {
                m.append("(scheduled-as-regular)");
            }
        }
        int i = this.syncExemptionFlag;
        if (i != 0) {
            if (i == 1) {
                m.append(" STANDBY-EXEMPTED");
            } else if (i != 2) {
                m.append(" ExemptionFlag=" + this.syncExemptionFlag);
            } else {
                m.append(" STANDBY-EXEMPTED(TOP)");
            }
        }
        m.append(" Reason=");
        m.append(reasonToString(packageManager, this.reason));
        if (this.isPeriodic) {
            m.append(" (period=");
            SyncManager.formatDurationHMS(m, this.periodMillis);
            m.append(" flex=");
            SyncManager.formatDurationHMS(m, this.flexMillis);
            m.append(")");
        }
        if (this.retries > 0) {
            m.append(" Retries=");
            m.append(this.retries);
        }
        if (!z) {
            m.append(" Owner={");
            UserHandle.formatUid(m, this.owningUid);
            m.append(" ");
            m.append(this.owningPackage);
            if (syncAdapterStateFetcher != null) {
                m.append(" [");
                int userId = UserHandle.getUserId(this.owningUid);
                String str = this.owningPackage;
                UserPackage of = UserPackage.of(userId, str);
                Integer num = (Integer) syncAdapterStateFetcher.mBucketCache.get(of);
                if (num != null) {
                    appStandbyBucket = num.intValue();
                } else {
                    UsageStatsManagerInternal usageStatsManagerInternal = (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class);
                    if (usageStatsManagerInternal == null) {
                        appStandbyBucket = -1;
                    } else {
                        appStandbyBucket = usageStatsManagerInternal.getAppStandbyBucket(userId, str, SystemClock.elapsedRealtime());
                        syncAdapterStateFetcher.mBucketCache.put(of, Integer.valueOf(appStandbyBucket));
                    }
                }
                m.append(appStandbyBucket);
                m.append("]");
                int i2 = this.owningUid;
                ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
                if (activityManagerInternal != null ? activityManagerInternal.isUidActive(i2) : false) {
                    m.append(" [ACTIVE]");
                }
            }
            m.append("}");
            if (!bundle.keySet().isEmpty()) {
                m.append(" ");
                extrasToStringBuilder(bundle, m);
            }
        }
        return m.toString();
    }

    public final int getJobBias() {
        if (this.mImmutableExtras.getBoolean("initialize", false)) {
            return 20;
        }
        return this.mImmutableExtras.getBoolean("expedited", false) ? 10 : 0;
    }

    public final PersistableBundle toJobInfoExtras() {
        PersistableBundle persistableBundle = new PersistableBundle();
        PersistableBundle persistableBundle2 = new PersistableBundle();
        Bundle bundle = this.mImmutableExtras;
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj instanceof Account) {
                Account account = (Account) obj;
                PersistableBundle persistableBundle3 = new PersistableBundle();
                persistableBundle3.putString("accountName", account.name);
                persistableBundle3.putString("accountType", account.type);
                persistableBundle.putPersistableBundle("ACCOUNT:" + str, persistableBundle3);
            } else if (obj instanceof Long) {
                persistableBundle2.putLong(str, ((Long) obj).longValue());
            } else if (obj instanceof Integer) {
                persistableBundle2.putInt(str, ((Integer) obj).intValue());
            } else if (obj instanceof Boolean) {
                persistableBundle2.putBoolean(str, ((Boolean) obj).booleanValue());
            } else if (obj instanceof Float) {
                persistableBundle2.putDouble(str, ((Float) obj).floatValue());
            } else if (obj instanceof Double) {
                persistableBundle2.putDouble(str, ((Double) obj).doubleValue());
            } else if (obj instanceof String) {
                persistableBundle2.putString(str, (String) obj);
            } else if (obj == null) {
                persistableBundle2.putString(str, null);
            } else {
                Slog.e("SyncManager", "Unknown extra type.");
            }
        }
        persistableBundle.putPersistableBundle("syncExtras", persistableBundle2);
        persistableBundle.putBoolean("SyncManagerJob", true);
        persistableBundle.putString("provider", this.target.provider);
        persistableBundle.putString("accountName", this.target.account.name);
        persistableBundle.putString("accountType", this.target.account.type);
        persistableBundle.putInt("userId", this.target.userId);
        persistableBundle.putInt("owningUid", this.owningUid);
        persistableBundle.putString("owningPackage", this.owningPackage);
        persistableBundle.putInt("reason", this.reason);
        persistableBundle.putInt("source", this.syncSource);
        persistableBundle.putBoolean("allowParallelSyncs", this.allowParallelSyncs);
        persistableBundle.putInt("jobId", this.jobId);
        persistableBundle.putBoolean("isPeriodic", this.isPeriodic);
        persistableBundle.putInt("sourcePeriodicId", this.sourcePeriodicId);
        persistableBundle.putLong("periodMillis", this.periodMillis);
        persistableBundle.putLong("flexMillis", this.flexMillis);
        persistableBundle.putLong("expectedRuntime", this.expectedRuntime);
        persistableBundle.putInt("retries", this.retries);
        persistableBundle.putInt("syncExemptionFlag", this.syncExemptionFlag);
        persistableBundle.putBoolean("ejDowngradedToRegular", this.scheduleEjAsRegularJob);
        return persistableBundle;
    }

    public final String toString() {
        return dump(null, true, null, false);
    }

    public final String wakeLockName() {
        String str = this.wakeLockName;
        if (str != null) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        SyncStorageEngine.EndPoint endPoint = this.target;
        sb.append(endPoint.provider);
        sb.append("/");
        sb.append(endPoint.account.type);
        String sb2 = sb.toString();
        this.wakeLockName = sb2;
        return sb2;
    }
}
