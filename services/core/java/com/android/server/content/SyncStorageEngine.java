package com.android.server.content;

import android.accounts.Account;
import android.accounts.AccountAndUser;
import android.accounts.AccountManager;
import android.app.backup.BackupManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ISyncStatusObserver;
import android.content.PeriodicSync;
import android.content.SyncInfo;
import android.content.SyncRequest;
import android.content.SyncStatusInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.EventLog;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.IntPair;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;

import com.samsung.android.knox.SemPersonaManager;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SyncStorageEngine {
    static final long MILLIS_IN_4WEEKS = 2419200000L;
    public static final String[] SOURCES = {"OTHER", "LOCAL", "POLL", "USER", "PERIODIC", "FEED"};
    public static SyncManager.AnonymousClass7 mPeriodicSyncAddedListener;
    public static final HashMap sAuthorityRenames;
    public static volatile SyncStorageEngine sSyncStorageEngine;
    public final AtomicFile mAccountInfoFile;
    public SyncManager.AnonymousClass7 mAuthorityRemovedListener;
    public final Calendar mCal;
    public final Context mContext;
    final DayStats[] mDayStats;
    public final boolean mDefaultMasterSyncAutomatically;
    public final boolean mGrantSyncAdaptersAccountAccess;
    public final MyHandler mHandler;
    public volatile boolean mIsClockValid;
    public volatile boolean mIsJobAttributionFixed;
    public volatile boolean mIsJobNamespaceMigrated;
    public final SyncLogger mLogger;
    public final SparseArray mMasterSyncAutomatically;
    public int mNextAuthorityId;
    public int mNextHistoryId;
    public final PackageManagerInternal mPackageManagerInternal;
    public final AtomicFile mStatisticsFile;
    public final AtomicFile mStatusFile;
    public final File mSyncDir;
    public final int mSyncRandomOffset;
    public SyncManager.AnonymousClass7 mSyncRequestListener;
    public int mYear;
    public int mYearInDays;
    final SparseArray mAuthorities = new SparseArray();
    public final HashMap mAccounts = new HashMap();
    public final SparseArray mCurrentSyncs = new SparseArray();
    final SparseArray mSyncStatus = new SparseArray();
    public final ArrayList mSyncHistory = new ArrayList();
    public final RemoteCallbackList mChangeListeners = new RemoteCallbackList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AccountAuthorityValidator {
        public final AccountManager mAccountManager;
        public final PackageManager mPackageManager;
        public final SparseArray mAccountsCache = new SparseArray();
        public final SparseArray mProvidersPerUserCache = new SparseArray();

        public AccountAuthorityValidator(Context context) {
            this.mAccountManager = (AccountManager) context.getSystemService(AccountManager.class);
            this.mPackageManager = context.getPackageManager();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AccountInfo {
        public final AccountAndUser accountAndUser;
        public final HashMap authorities = new HashMap();

        public AccountInfo(AccountAndUser accountAndUser) {
            this.accountAndUser = accountAndUser;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AuthorityInfo {
        public long backoffDelay;
        public long backoffTime;
        public long delayUntil;
        public boolean enabled;
        public final int ident;
        public final ArrayList periodicSyncs;
        public int syncable;
        public final EndPoint target;

        public AuthorityInfo(int i, EndPoint endPoint) {
            this.target = endPoint;
            this.ident = i;
            this.enabled = false;
            this.periodicSyncs = new ArrayList();
            this.syncable = -1;
            this.backoffTime = -1L;
            this.backoffDelay = -1L;
            SyncManager.AnonymousClass7 anonymousClass7 =
                    SyncStorageEngine.mPeriodicSyncAddedListener;
            if (anonymousClass7 != null) {
                Bundle bundle = new Bundle();
                SyncManager.this.updateOrAddPeriodicSync(
                        endPoint,
                        86400L,
                        SyncStorageEngine.calculateDefaultFlexTime(86400L),
                        bundle);
            }
        }

        public AuthorityInfo(AuthorityInfo authorityInfo) {
            this.target = authorityInfo.target;
            this.ident = authorityInfo.ident;
            this.enabled = authorityInfo.enabled;
            this.syncable = authorityInfo.syncable;
            this.backoffTime = authorityInfo.backoffTime;
            this.backoffDelay = authorityInfo.backoffDelay;
            this.delayUntil = authorityInfo.delayUntil;
            this.periodicSyncs = new ArrayList();
            Iterator it = authorityInfo.periodicSyncs.iterator();
            while (it.hasNext()) {
                this.periodicSyncs.add(new PeriodicSync((PeriodicSync) it.next()));
            }
        }

        public final String toString() {
            return this.target
                    + ", enabled="
                    + this.enabled
                    + ", syncable="
                    + this.syncable
                    + ", backoff="
                    + this.backoffTime
                    + ", delay="
                    + this.delayUntil;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DayStats {
        public final int day;
        public int failureCount;
        public long failureTime;
        public int successCount;
        public long successTime;

        public DayStats(int i) {
            this.day = i;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EndPoint {
        public static final EndPoint USER_ALL_PROVIDER_ALL_ACCOUNTS_ALL =
                new EndPoint(null, null, -1);
        public final Account account;
        public final String provider;
        public final int userId;

        public EndPoint(Account account, String str, int i) {
            this.account = account;
            this.provider = str;
            this.userId = i;
        }

        public final boolean matchesSpec(EndPoint endPoint) {
            int i = endPoint.userId;
            int i2 = this.userId;
            if (i2 != i && i2 != -1 && i != -1) {
                return false;
            }
            Account account = endPoint.account;
            boolean equals = account == null ? true : this.account.equals(account);
            String str = endPoint.provider;
            return equals && (str == null ? true : this.provider.equals(str));
        }

        public final String toSafeString() {
            StringBuilder sb = new StringBuilder();
            Account account = this.account;
            sb.append(account == null ? "ALL ACCS" : account.toSafeString());
            sb.append("/");
            String str = this.provider;
            if (str == null) {
                str = "ALL PDRS";
            }
            sb.append(str);
            sb.append(":u" + this.userId);
            return sb.toString();
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            Account account = this.account;
            sb.append(account == null ? "ALL ACCS" : account.name);
            sb.append("/");
            String str = this.provider;
            if (str == null) {
                str = "ALL PDRS";
            }
            sb.append(str);
            sb.append(":u" + this.userId);
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                synchronized (SyncStorageEngine.this.mAuthorities) {
                    SyncStorageEngine.this.writeStatusLocked();
                }
            } else if (i == 2) {
                synchronized (SyncStorageEngine.this.mAuthorities) {
                    SyncStorageEngine.this.writeStatisticsLocked();
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SyncHistoryItem {
        public int authorityId;
        public long downstreamActivity;
        public long elapsedTime;
        public int event;
        public long eventTime;
        public Bundle extras;
        public int historyId;
        public String mesg;
        public int reason;
        public int source;
        public int syncExemptionFlag;
        public long upstreamActivity;
    }

    static {
        HashMap hashMap = new HashMap();
        sAuthorityRenames = hashMap;
        hashMap.put("contacts", "com.android.contacts");
        hashMap.put("calendar", "com.android.calendar");
        sSyncStorageEngine = null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:182:0x02dc, code lost:

       if (r10 == null) goto L132;
    */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x02de, code lost:

       r10.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x02f0, code lost:

       if (r10 == null) goto L132;
    */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0333, code lost:

       android.util.Slog.w("SyncManager", "Unknown stats token: " + r4);
    */
    /* JADX WARN: Removed duplicated region for block: B:180:0x02c9 A[Catch: all -> 0x02cd, TRY_ENTER, TryCatch #16 {all -> 0x02cd, blocks: (B:180:0x02c9, B:184:0x02d0, B:187:0x02e4), top: B:5:0x00e9 }] */
    /* JADX WARN: Removed duplicated region for block: B:184:0x02d0 A[Catch: all -> 0x02cd, TRY_LEAVE, TryCatch #16 {all -> 0x02cd, blocks: (B:180:0x02c9, B:184:0x02d0, B:187:0x02e4), top: B:5:0x00e9 }] */
    /* JADX WARN: Removed duplicated region for block: B:195:0x0448 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x03d4  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x043c A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x03a7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SyncStorageEngine(android.content.Context r17, java.io.File r18, android.os.Looper r19) {
        /*
            Method dump skipped, instructions count: 1100
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.content.SyncStorageEngine.<init>(android.content.Context,"
                    + " java.io.File, android.os.Looper):void");
    }

    public static long calculateDefaultFlexTime(long j) {
        if (j < 5) {
            return 0L;
        }
        if (j < 86400) {
            return (long) (j * 0.04d);
        }
        return 3456L;
    }

    public static void parseExtra(TypedXmlPullParser typedXmlPullParser, Bundle bundle) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
        String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "type");
        try {
            if ("long".equals(attributeValue2)) {
                bundle.putLong(
                        attributeValue,
                        typedXmlPullParser.getAttributeLong((String) null, "value1"));
            } else if ("integer".equals(attributeValue2)) {
                bundle.putInt(
                        attributeValue,
                        typedXmlPullParser.getAttributeInt((String) null, "value1"));
            } else if ("double".equals(attributeValue2)) {
                bundle.putDouble(
                        attributeValue,
                        typedXmlPullParser.getAttributeDouble((String) null, "value1"));
            } else if ("float".equals(attributeValue2)) {
                bundle.putFloat(
                        attributeValue,
                        typedXmlPullParser.getAttributeFloat((String) null, "value1"));
            } else if ("boolean".equals(attributeValue2)) {
                bundle.putBoolean(
                        attributeValue,
                        typedXmlPullParser.getAttributeBoolean((String) null, "value1"));
            } else if ("string".equals(attributeValue2)) {
                bundle.putString(
                        attributeValue,
                        typedXmlPullParser.getAttributeValue((String) null, "value1"));
            } else if ("account".equals(attributeValue2)) {
                bundle.putParcelable(
                        attributeValue,
                        new Account(
                                typedXmlPullParser.getAttributeValue((String) null, "value1"),
                                typedXmlPullParser.getAttributeValue((String) null, "value2")));
            }
        } catch (XmlPullParserException e) {
            Slog.e("SyncManager", "error parsing bundle value", e);
        }
    }

    public static PeriodicSync parsePeriodicSync(
            TypedXmlPullParser typedXmlPullParser, AuthorityInfo authorityInfo) {
        long j;
        Bundle bundle = new Bundle();
        try {
            long attributeLong = typedXmlPullParser.getAttributeLong((String) null, "period");
            try {
                j = typedXmlPullParser.getAttributeLong((String) null, "flex");
            } catch (XmlPullParserException e) {
                long calculateDefaultFlexTime = calculateDefaultFlexTime(attributeLong);
                Slog.e(
                        "SyncManager",
                        "Error formatting value parsed for periodic sync flex, using default: "
                                + calculateDefaultFlexTime,
                        e);
                j = calculateDefaultFlexTime;
            }
            EndPoint endPoint = authorityInfo.target;
            PeriodicSync periodicSync =
                    new PeriodicSync(endPoint.account, endPoint.provider, bundle, attributeLong, j);
            authorityInfo.periodicSyncs.add(periodicSync);
            return periodicSync;
        } catch (XmlPullParserException e2) {
            Slog.e("SyncManager", "error parsing the period of a periodic sync", e2);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x0080, code lost:

       return;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void readSyncStatusStatsLocked(
            android.util.proto.ProtoInputStream r2, android.content.SyncStatusInfo.Stats r3) {
        /*
        L0:
            int r0 = r2.nextField()
            switch(r0) {
                case -1: goto L80;
                case 0: goto L7;
                case 1: goto L74;
                case 2: goto L68;
                case 3: goto L5c;
                case 4: goto L50;
                case 5: goto L44;
                case 6: goto L38;
                case 7: goto L2c;
                case 8: goto L20;
                case 9: goto L14;
                case 10: goto L8;
                default: goto L7;
            }
        L7:
            goto L0
        L8:
            r0 = 1120986464266(0x1050000000a, double:5.53840901447E-312)
            int r0 = r2.readInt(r0)
            r3.numSourceFeed = r0
            goto L0
        L14:
            r0 = 1120986464265(0x10500000009, double:5.538409014464E-312)
            int r0 = r2.readInt(r0)
            r3.numSourcePeriodic = r0
            goto L0
        L20:
            r0 = 1120986464264(0x10500000008, double:5.53840901446E-312)
            int r0 = r2.readInt(r0)
            r3.numSourceUser = r0
            goto L0
        L2c:
            r0 = 1120986464263(0x10500000007, double:5.538409014454E-312)
            int r0 = r2.readInt(r0)
            r3.numSourcePoll = r0
            goto L0
        L38:
            r0 = 1120986464262(0x10500000006, double:5.53840901445E-312)
            int r0 = r2.readInt(r0)
            r3.numSourceLocal = r0
            goto L0
        L44:
            r0 = 1120986464261(0x10500000005, double:5.538409014444E-312)
            int r0 = r2.readInt(r0)
            r3.numSourceOther = r0
            goto L0
        L50:
            r0 = 1120986464260(0x10500000004, double:5.53840901444E-312)
            int r0 = r2.readInt(r0)
            r3.numCancels = r0
            goto L0
        L5c:
            r0 = 1120986464259(0x10500000003, double:5.538409014434E-312)
            int r0 = r2.readInt(r0)
            r3.numFailures = r0
            goto L0
        L68:
            r0 = 1120986464258(0x10500000002, double:5.53840901443E-312)
            int r0 = r2.readInt(r0)
            r3.numSyncs = r0
            goto L0
        L74:
            r0 = 1112396529665(0x10300000001, double:5.495969098605E-312)
            long r0 = r2.readLong(r0)
            r3.totalElapsedTime = r0
            goto L0
        L80:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.content.SyncStorageEngine.readSyncStatusStatsLocked(android.util.proto.ProtoInputStream,"
                    + " android.content.SyncStatusInfo$Stats):void");
    }

    public static void writeStatusStatsLocked(
            ProtoOutputStream protoOutputStream, SyncStatusInfo.Stats stats) {
        protoOutputStream.write(1112396529665L, stats.totalElapsedTime);
        protoOutputStream.write(1120986464258L, stats.numSyncs);
        protoOutputStream.write(1120986464259L, stats.numFailures);
        protoOutputStream.write(1120986464260L, stats.numCancels);
        protoOutputStream.write(1120986464261L, stats.numSourceOther);
        protoOutputStream.write(1120986464262L, stats.numSourceLocal);
        protoOutputStream.write(1120986464263L, stats.numSourcePoll);
        protoOutputStream.write(1120986464264L, stats.numSourceUser);
        protoOutputStream.write(1120986464265L, stats.numSourcePeriodic);
        protoOutputStream.write(1120986464266L, stats.numSourceFeed);
    }

    public final AuthorityInfo getAuthority(int i) {
        AuthorityInfo authorityInfo;
        synchronized (this.mAuthorities) {
            authorityInfo = (AuthorityInfo) this.mAuthorities.get(i);
        }
        return authorityInfo;
    }

    public final AuthorityInfo getAuthorityLocked(EndPoint endPoint, String str) {
        AccountAndUser accountAndUser = new AccountAndUser(endPoint.account, endPoint.userId);
        AccountInfo accountInfo = (AccountInfo) this.mAccounts.get(accountAndUser);
        if (accountInfo == null) {
            if (Log.isLoggable("SyncManager", 2)) {
                Slog.v("SyncManager", str + ": unknown account " + accountAndUser);
            }
            return null;
        }
        HashMap hashMap = accountInfo.authorities;
        String str2 = endPoint.provider;
        AuthorityInfo authorityInfo = (AuthorityInfo) hashMap.get(str2);
        if (authorityInfo != null) {
            return authorityInfo;
        }
        if (Log.isLoggable("SyncManager", 2)) {
            Slog.v("SyncManager", str + ": unknown provider " + str2);
        }
        return null;
    }

    public final Pair getBackoff(EndPoint endPoint) {
        synchronized (this.mAuthorities) {
            try {
                AuthorityInfo authorityLocked = getAuthorityLocked(endPoint, "getBackoff");
                if (authorityLocked == null) {
                    return null;
                }
                return Pair.create(
                        Long.valueOf(authorityLocked.backoffTime),
                        Long.valueOf(authorityLocked.backoffDelay));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getCurrentDayLocked() {
        this.mCal.setTimeInMillis(System.currentTimeMillis());
        int i = this.mCal.get(6);
        if (this.mYear != this.mCal.get(1)) {
            this.mYear = this.mCal.get(1);
            this.mCal.clear();
            this.mCal.set(1, this.mYear);
            this.mYearInDays =
                    (int)
                            (this.mCal.getTimeInMillis()
                                    / BackupManagerConstants
                                            .DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
        }
        return i + this.mYearInDays;
    }

    public final List getCurrentSyncsCopy(int i, boolean z) {
        ArrayList arrayList;
        synchronized (this.mAuthorities) {
            try {
                ArrayList<SyncInfo> arrayList2 = (ArrayList) this.mCurrentSyncs.get(i);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList();
                    this.mCurrentSyncs.put(i, arrayList2);
                }
                arrayList = new ArrayList();
                for (SyncInfo syncInfo : arrayList2) {
                    arrayList.add(
                            !z
                                    ? SyncInfo.createAccountRedacted(
                                            syncInfo.authorityId,
                                            syncInfo.authority,
                                            syncInfo.startTime)
                                    : new SyncInfo(syncInfo));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final long getDelayUntilTime(EndPoint endPoint) {
        synchronized (this.mAuthorities) {
            try {
                AuthorityInfo authorityLocked = getAuthorityLocked(endPoint, "getDelayUntil");
                if (authorityLocked == null) {
                    return 0L;
                }
                return authorityLocked.delayUntil;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean getMasterSyncAutomatically(int i) {
        synchronized (this.mAuthorities) {
            try {
                Boolean bool = (Boolean) this.mMasterSyncAutomatically.get(i);
                Log.d("SyncManager", "userId - " + i + " auto - " + bool);
                if (SemPersonaManager.isSecureFolderId(i)) {
                    return bool == null ? false : bool.booleanValue();
                }
                return bool == null ? this.mDefaultMasterSyncAutomatically : bool.booleanValue();
            } finally {
            }
        }
    }

    public final AuthorityInfo getOrCreateAuthorityLocked(EndPoint endPoint, int i, boolean z) {
        AccountAndUser accountAndUser = new AccountAndUser(endPoint.account, endPoint.userId);
        AccountInfo accountInfo = (AccountInfo) this.mAccounts.get(accountAndUser);
        if (accountInfo == null) {
            accountInfo = new AccountInfo(accountAndUser);
            this.mAccounts.put(accountAndUser, accountInfo);
        }
        HashMap hashMap = accountInfo.authorities;
        String str = endPoint.provider;
        AuthorityInfo authorityInfo = (AuthorityInfo) hashMap.get(str);
        if (authorityInfo == null) {
            if (i < 0) {
                i = this.mNextAuthorityId;
                this.mNextAuthorityId = i + 1;
                z = true;
            }
            if (Log.isLoggable("SyncManager", 2)) {
                Slog.v("SyncManager", "created a new AuthorityInfo for " + endPoint);
            }
            authorityInfo = new AuthorityInfo(i, endPoint);
            this.mAuthorities.put(i, authorityInfo);
            if (z) {
                writeAccountInfoLocked();
            }
            accountInfo.authorities.put(str, authorityInfo);
        }
        return authorityInfo;
    }

    public final SyncStatusInfo getOrCreateSyncStatusLocked(int i) {
        SyncStatusInfo syncStatusInfo = (SyncStatusInfo) this.mSyncStatus.get(i);
        if (syncStatusInfo != null) {
            return syncStatusInfo;
        }
        SyncStatusInfo syncStatusInfo2 = new SyncStatusInfo(i);
        this.mSyncStatus.put(i, syncStatusInfo2);
        return syncStatusInfo2;
    }

    public final SyncStatusInfo getStatusByAuthority(EndPoint endPoint) {
        if (endPoint.account == null || endPoint.provider == null) {
            return null;
        }
        synchronized (this.mAuthorities) {
            try {
                int size = this.mSyncStatus.size();
                for (int i = 0; i < size; i++) {
                    SyncStatusInfo syncStatusInfo = (SyncStatusInfo) this.mSyncStatus.valueAt(i);
                    AuthorityInfo authorityInfo =
                            (AuthorityInfo) this.mAuthorities.get(syncStatusInfo.authorityId);
                    if (authorityInfo != null && authorityInfo.target.matchesSpec(endPoint)) {
                        return syncStatusInfo;
                    }
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0042, code lost:

       r6 = true;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean getSyncAutomatically(
            android.accounts.Account r9, java.lang.String r10, int r11) {
        /*
            r8 = this;
            android.util.SparseArray r0 = r8.mAuthorities
            monitor-enter(r0)
            r1 = 0
            r2 = 1
            if (r9 == 0) goto L1f
            com.android.server.content.SyncStorageEngine$EndPoint r3 = new com.android.server.content.SyncStorageEngine$EndPoint     // Catch: java.lang.Throwable -> L1b
            r3.<init>(r9, r10, r11)     // Catch: java.lang.Throwable -> L1b
            java.lang.String r9 = "getSyncAutomatically"
            com.android.server.content.SyncStorageEngine$AuthorityInfo r8 = r8.getAuthorityLocked(r3, r9)     // Catch: java.lang.Throwable -> L1b
            if (r8 == 0) goto L1d
            boolean r8 = r8.enabled     // Catch: java.lang.Throwable -> L1b
            if (r8 == 0) goto L1d
            r1 = r2
            goto L1d
        L1b:
            r8 = move-exception
            goto L60
        L1d:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L1b
            return r1
        L1f:
            android.util.SparseArray r3 = r8.mAuthorities     // Catch: java.lang.Throwable -> L1b
            int r3 = r3.size()     // Catch: java.lang.Throwable -> L1b
        L25:
            if (r3 <= 0) goto L5e
            int r3 = r3 + (-1)
            android.util.SparseArray r4 = r8.mAuthorities     // Catch: java.lang.Throwable -> L1b
            java.lang.Object r4 = r4.valueAt(r3)     // Catch: java.lang.Throwable -> L1b
            com.android.server.content.SyncStorageEngine$AuthorityInfo r4 = (com.android.server.content.SyncStorageEngine.AuthorityInfo) r4     // Catch: java.lang.Throwable -> L1b
            com.android.server.content.SyncStorageEngine$EndPoint r5 = r4.target     // Catch: java.lang.Throwable -> L1b
            r5.getClass()     // Catch: java.lang.Throwable -> L1b
            int r6 = r5.userId     // Catch: java.lang.Throwable -> L1b
            if (r6 == r11) goto L40
            r7 = -1
            if (r6 == r7) goto L40
            if (r11 == r7) goto L40
            goto L25
        L40:
            if (r9 != 0) goto L44
            r6 = r2
            goto L4a
        L44:
            android.accounts.Account r6 = r5.account     // Catch: java.lang.Throwable -> L1b
            boolean r6 = r6.equals(r9)     // Catch: java.lang.Throwable -> L1b
        L4a:
            if (r10 != 0) goto L4e
            r5 = r2
            goto L54
        L4e:
            java.lang.String r5 = r5.provider     // Catch: java.lang.Throwable -> L1b
            boolean r5 = r5.equals(r10)     // Catch: java.lang.Throwable -> L1b
        L54:
            if (r6 == 0) goto L25
            if (r5 == 0) goto L25
            boolean r4 = r4.enabled     // Catch: java.lang.Throwable -> L1b
            if (r4 == 0) goto L25
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L1b
            return r2
        L5e:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L1b
            return r1
        L60:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L1b
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.content.SyncStorageEngine.getSyncAutomatically(android.accounts.Account,"
                    + " java.lang.String, int):boolean");
    }

    public final boolean isSyncActive(EndPoint endPoint) {
        ArrayList arrayList;
        synchronized (this.mAuthorities) {
            try {
                int i = endPoint.userId;
                synchronized (this.mAuthorities) {
                    arrayList = (ArrayList) this.mCurrentSyncs.get(i);
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        this.mCurrentSyncs.put(i, arrayList);
                    }
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    AuthorityInfo authority = getAuthority(((SyncInfo) it.next()).authorityId);
                    if (authority != null && authority.target.matchesSpec(endPoint)) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isSyncPending(EndPoint endPoint) {
        synchronized (this.mAuthorities) {
            try {
                int size = this.mSyncStatus.size();
                for (int i = 0; i < size; i++) {
                    SyncStatusInfo syncStatusInfo = (SyncStatusInfo) this.mSyncStatus.valueAt(i);
                    AuthorityInfo authorityInfo =
                            (AuthorityInfo) this.mAuthorities.get(syncStatusInfo.authorityId);
                    if (authorityInfo != null
                            && authorityInfo.target.matchesSpec(endPoint)
                            && syncStatusInfo.pending) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void markPending(EndPoint endPoint, boolean z) {
        synchronized (this.mAuthorities) {
            getOrCreateSyncStatusLocked(getOrCreateAuthorityLocked(endPoint, -1, true).ident)
                            .pending =
                    z;
        }
        reportChange(2, endPoint);
    }

    public final AuthorityInfo parseAuthority(
            TypedXmlPullParser typedXmlPullParser,
            int i,
            AccountAuthorityValidator accountAuthorityValidator) {
        int i2;
        int i3;
        String str;
        int parseInt;
        AuthorityInfo authorityInfo = null;
        try {
            i2 = typedXmlPullParser.getAttributeInt((String) null, "id");
        } catch (XmlPullParserException e) {
            Slog.e("SyncManager", "error parsing the id of the authority", e);
            i2 = -1;
        }
        if (i2 >= 0) {
            String attributeValue =
                    typedXmlPullParser.getAttributeValue((String) null, "authority");
            boolean attributeBoolean =
                    typedXmlPullParser.getAttributeBoolean((String) null, "enabled", true);
            String attributeValue2 =
                    typedXmlPullParser.getAttributeValue((String) null, "syncable");
            String attributeValue3 = typedXmlPullParser.getAttributeValue((String) null, "account");
            String attributeValue4 = typedXmlPullParser.getAttributeValue((String) null, "type");
            int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "user", 0);
            String attributeValue5 = typedXmlPullParser.getAttributeValue((String) null, "package");
            String attributeValue6 = typedXmlPullParser.getAttributeValue((String) null, "class");
            if (attributeValue4 == null && attributeValue5 == null) {
                attributeValue2 = String.valueOf(-1);
                attributeValue4 = "com.google";
            }
            authorityInfo = (AuthorityInfo) this.mAuthorities.get(i2);
            if (Log.isLoggable("SyncManagerFile", 2)) {
                str = "SyncManager";
                i3 = i2;
                StringBuilder m =
                        InitialConfiguration$$ExternalSyntheticOutline0.m(
                                "Adding authority: account=",
                                attributeValue3,
                                " accountType=",
                                attributeValue4,
                                " auth=");
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        m, attributeValue, " package=", attributeValue5, " class=");
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        attributeInt, attributeValue6, " user=", " enabled=", m);
                m.append(attributeBoolean);
                m.append(" syncable=");
                m.append(attributeValue2);
                Slog.v("SyncManagerFile", m.toString());
            } else {
                i3 = i2;
                str = "SyncManager";
            }
            if (authorityInfo == null) {
                if (Log.isLoggable("SyncManagerFile", 2)) {
                    Slog.v("SyncManagerFile", "Creating authority entry");
                }
                if (attributeValue3 != null && attributeValue != null) {
                    Account account = new Account(attributeValue3, attributeValue4);
                    EndPoint endPoint = new EndPoint(account, attributeValue, attributeInt);
                    Account[] accountArr =
                            (Account[]) accountAuthorityValidator.mAccountsCache.get(attributeInt);
                    if (accountArr == null) {
                        accountArr =
                                accountAuthorityValidator.mAccountManager.getAccountsAsUser(
                                        attributeInt);
                        accountAuthorityValidator.mAccountsCache.put(attributeInt, accountArr);
                    }
                    if (ArrayUtils.contains(accountArr, account)) {
                        ArrayMap arrayMap =
                                (ArrayMap)
                                        accountAuthorityValidator.mProvidersPerUserCache.get(
                                                attributeInt);
                        if (arrayMap == null) {
                            arrayMap = new ArrayMap();
                            accountAuthorityValidator.mProvidersPerUserCache.put(
                                    attributeInt, arrayMap);
                        }
                        if (!arrayMap.containsKey(attributeValue)) {
                            arrayMap.put(
                                    attributeValue,
                                    Boolean.valueOf(
                                            accountAuthorityValidator.mPackageManager
                                                            .resolveContentProviderAsUser(
                                                                    attributeValue,
                                                                    786432,
                                                                    attributeInt)
                                                    != null));
                        }
                        if (((Boolean) arrayMap.get(attributeValue)).booleanValue()) {
                            AuthorityInfo orCreateAuthorityLocked =
                                    getOrCreateAuthorityLocked(endPoint, i3, false);
                            if (i > 0) {
                                orCreateAuthorityLocked.periodicSyncs.clear();
                            }
                            authorityInfo = orCreateAuthorityLocked;
                        }
                    }
                    EventLog.writeEvent(
                            1397638484,
                            "35028827",
                            -1,
                            "account:"
                                    + account
                                    + " provider:"
                                    + attributeValue
                                    + " user:"
                                    + attributeInt);
                }
            }
            if (authorityInfo != null) {
                authorityInfo.enabled = attributeBoolean;
                if (attributeValue2 == null) {
                    parseInt = -1;
                } else {
                    try {
                        parseInt = Integer.parseInt(attributeValue2);
                    } catch (NumberFormatException unused) {
                        if ("unknown".equals(attributeValue2)) {
                            authorityInfo.syncable = -1;
                        } else {
                            authorityInfo.syncable = Boolean.parseBoolean(attributeValue2) ? 1 : 0;
                        }
                    }
                }
                authorityInfo.syncable = parseInt;
            } else {
                StringBuilder sb = new StringBuilder("Failure adding authority: auth=");
                sb.append(attributeValue);
                sb.append(" enabled=");
                sb.append(attributeBoolean);
                sb.append(" syncable=");
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, attributeValue2, str);
            }
        }
        return authorityInfo;
    }

    public final void parseListenForTickles(TypedXmlPullParser typedXmlPullParser) {
        int i;
        try {
            i = typedXmlPullParser.getAttributeInt((String) null, "user");
        } catch (XmlPullParserException e) {
            Slog.e("SyncManager", "error parsing the user for listen-for-tickles", e);
            i = 0;
        }
        this.mMasterSyncAutomatically.put(
                i,
                Boolean.valueOf(
                        typedXmlPullParser.getAttributeBoolean((String) null, "enabled", true)));
    }

    public final void readDayStatsLocked(InputStream inputStream) {
        ProtoInputStream protoInputStream = new ProtoInputStream(inputStream);
        int i = 0;
        while (true) {
            int nextField = protoInputStream.nextField();
            if (nextField == -1) {
                return;
            }
            if (nextField == 1) {
                long start = protoInputStream.start(2246267895809L);
                DayStats dayStats =
                        protoInputStream.nextField(1120986464257L)
                                ? new DayStats(protoInputStream.readInt(1120986464257L))
                                : new DayStats(0);
                while (true) {
                    int nextField2 = protoInputStream.nextField();
                    if (nextField2 == -1) {
                        break;
                    }
                    if (nextField2 == 1) {
                        Slog.w(
                                "SyncManager",
                                "Failed to read the day via fast-path; some data might not have"
                                    + " been read.");
                        DayStats dayStats2 = new DayStats(protoInputStream.readInt(1120986464257L));
                        dayStats2.successCount = dayStats.successCount;
                        dayStats2.successTime = dayStats.successTime;
                        dayStats2.failureCount = dayStats.failureCount;
                        dayStats2.failureTime = dayStats.failureTime;
                        dayStats = dayStats2;
                    } else if (nextField2 == 2) {
                        dayStats.successCount = protoInputStream.readInt(1120986464258L);
                    } else if (nextField2 == 3) {
                        dayStats.successTime = protoInputStream.readLong(1112396529667L);
                    } else if (nextField2 == 4) {
                        dayStats.failureCount = protoInputStream.readInt(1120986464260L);
                    } else if (nextField2 == 5) {
                        dayStats.failureTime = protoInputStream.readLong(1112396529669L);
                    }
                }
                protoInputStream.end(start);
                DayStats[] dayStatsArr = this.mDayStats;
                dayStatsArr[i] = dayStats;
                i++;
                if (i == dayStatsArr.length) {
                    return;
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final void readStatusInfoLocked(InputStream inputStream) {
        ProtoInputStream protoInputStream = new ProtoInputStream(inputStream);
        while (true) {
            int nextField = protoInputStream.nextField();
            int i = -1;
            if (nextField == -1) {
                return;
            }
            int i2 = 1;
            if (nextField == 1) {
                long start = protoInputStream.start(2246267895809L);
                SyncStatusInfo syncStatusInfo =
                        protoInputStream.nextField(1120986464258L)
                                ? new SyncStatusInfo(protoInputStream.readInt(1120986464258L))
                                : new SyncStatusInfo(0);
                ArrayList arrayList = new ArrayList();
                int i3 = 0;
                int i4 = 0;
                while (true) {
                    int nextField2 = protoInputStream.nextField();
                    if (nextField2 != i) {
                        switch (nextField2) {
                            case 2:
                                Slog.w(
                                        "SyncManager",
                                        "Failed to read the authority id via fast-path; some data"
                                            + " might not have been read.");
                                syncStatusInfo =
                                        new SyncStatusInfo(
                                                protoInputStream.readInt(1120986464258L),
                                                syncStatusInfo);
                                break;
                            case 3:
                                syncStatusInfo.lastSuccessTime =
                                        protoInputStream.readLong(1112396529667L);
                                break;
                            case 4:
                                syncStatusInfo.lastSuccessSource =
                                        protoInputStream.readInt(1120986464260L);
                                break;
                            case 5:
                                syncStatusInfo.lastFailureTime =
                                        protoInputStream.readLong(1112396529669L);
                                break;
                            case 6:
                                syncStatusInfo.lastFailureSource =
                                        protoInputStream.readInt(1120986464262L);
                                break;
                            case 7:
                                syncStatusInfo.lastFailureMesg =
                                        protoInputStream.readString(1138166333447L);
                                break;
                            case 8:
                                syncStatusInfo.initialFailureTime =
                                        protoInputStream.readLong(1112396529672L);
                                break;
                            case 9:
                                syncStatusInfo.pending =
                                        protoInputStream.readBoolean(1133871366153L);
                                break;
                            case 10:
                                syncStatusInfo.initialize =
                                        protoInputStream.readBoolean(1133871366154L);
                                break;
                            case 11:
                                syncStatusInfo.addPeriodicSyncTime(
                                        protoInputStream.readLong(2211908157451L));
                                break;
                            case 12:
                                long start2 = protoInputStream.start(2246267895820L);
                                long j = 0;
                                String str = null;
                                while (true) {
                                    int nextField3 = protoInputStream.nextField();
                                    if (nextField3 == i) {
                                        Pair pair =
                                                str == null ? null : new Pair(Long.valueOf(j), str);
                                        if (pair != null) {
                                            arrayList.add(pair);
                                        }
                                        protoInputStream.end(start2);
                                        break;
                                    } else {
                                        if (nextField3 == i2) {
                                            j = protoInputStream.readLong(1112396529665L);
                                        } else if (nextField3 == 2) {
                                            str = protoInputStream.readString(1138166333442L);
                                        }
                                        i = -1;
                                        i2 = 1;
                                    }
                                }
                            case 13:
                                syncStatusInfo.lastTodayResetTime =
                                        protoInputStream.readLong(1112396529677L);
                                break;
                            case 14:
                                long start3 = protoInputStream.start(1146756268046L);
                                readSyncStatusStatsLocked(
                                        protoInputStream, syncStatusInfo.totalStats);
                                protoInputStream.end(start3);
                                break;
                            case 15:
                                long start4 = protoInputStream.start(1146756268047L);
                                readSyncStatusStatsLocked(
                                        protoInputStream, syncStatusInfo.todayStats);
                                protoInputStream.end(start4);
                                break;
                            case 16:
                                long start5 = protoInputStream.start(1146756268048L);
                                readSyncStatusStatsLocked(
                                        protoInputStream, syncStatusInfo.yesterdayStats);
                                protoInputStream.end(start5);
                                break;
                            case 17:
                                long readLong = protoInputStream.readLong(2211908157457L);
                                long[] jArr = syncStatusInfo.perSourceLastSuccessTimes;
                                if (i3 == jArr.length) {
                                    Slog.w(
                                            "SyncManager",
                                            "Attempted to read more per source last success times"
                                                + " than expected; data might be corrupted.");
                                } else {
                                    jArr[i3] = readLong;
                                    i3++;
                                }
                                break;
                            case 18:
                                long readLong2 = protoInputStream.readLong(2211908157458L);
                                long[] jArr2 = syncStatusInfo.perSourceLastFailureTimes;
                                if (i4 == jArr2.length) {
                                    Slog.w(
                                            "SyncManager",
                                            "Attempted to read more per source last failure times"
                                                + " than expected; data might be corrupted.");
                                } else {
                                    jArr2[i4] = readLong2;
                                    i4++;
                                }
                                break;
                        }
                        i = -1;
                        i2 = 1;
                    } else {
                        syncStatusInfo.populateLastEventsInformation(arrayList);
                        protoInputStream.end(start);
                        if (this.mAuthorities.indexOfKey(syncStatusInfo.authorityId) >= 0) {
                            syncStatusInfo.pending = false;
                            this.mSyncStatus.put(syncStatusInfo.authorityId, syncStatusInfo);
                        }
                    }
                }
            } else if (nextField == 2) {
                this.mIsJobNamespaceMigrated = protoInputStream.readBoolean(1133871366146L);
            } else if (nextField == 3) {
                this.mIsJobAttributionFixed = protoInputStream.readBoolean(1133871366147L);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0059, code lost:

       android.util.Slog.w("SyncManager", "Unknown status token: " + r1);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void readStatusLocked() {
        /*
            r7 = this;
            java.io.File r0 = new java.io.File
            java.io.File r1 = r7.mSyncDir
            java.lang.String r2 = "status.bin"
            r0.<init>(r1, r2)
            boolean r1 = r0.exists()
            java.lang.String r2 = "SyncManager"
            if (r1 == 0) goto L76
            android.util.AtomicFile r1 = r7.mStatusFile
            boolean r1 = r1.exists()
            if (r1 != 0) goto L76
            android.util.AtomicFile r1 = new android.util.AtomicFile     // Catch: java.io.IOException -> L6e
            r1.<init>(r0)     // Catch: java.io.IOException -> L6e
            byte[] r1 = r1.readFully()     // Catch: java.io.IOException -> L6e
            android.os.Parcel r3 = android.os.Parcel.obtain()     // Catch: java.io.IOException -> L6e
            int r4 = r1.length     // Catch: java.io.IOException -> L6e
            r5 = 0
            r3.unmarshall(r1, r5, r4)     // Catch: java.io.IOException -> L6e
            r3.setDataPosition(r5)     // Catch: java.io.IOException -> L6e
        L2f:
            int r1 = r3.readInt()     // Catch: java.io.IOException -> L6e
            if (r1 == 0) goto L73
            r4 = 100
            if (r1 != r4) goto L59
            android.content.SyncStatusInfo r1 = new android.content.SyncStatusInfo     // Catch: java.lang.Exception -> L52
            r1.<init>(r3)     // Catch: java.lang.Exception -> L52
            android.util.SparseArray r4 = r7.mAuthorities     // Catch: java.lang.Exception -> L52
            int r6 = r1.authorityId     // Catch: java.lang.Exception -> L52
            int r4 = r4.indexOfKey(r6)     // Catch: java.lang.Exception -> L52
            if (r4 < 0) goto L2f
            r1.pending = r5     // Catch: java.lang.Exception -> L52
            android.util.SparseArray r4 = r7.mSyncStatus     // Catch: java.lang.Exception -> L52
            int r6 = r1.authorityId     // Catch: java.lang.Exception -> L52
            r4.put(r6, r1)     // Catch: java.lang.Exception -> L52
            goto L2f
        L52:
            r1 = move-exception
            java.lang.String r4 = "Unable to parse some sync status."
            android.util.Slog.e(r2, r4, r1)     // Catch: java.io.IOException -> L6e
            goto L2f
        L59:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.io.IOException -> L6e
            r3.<init>()     // Catch: java.io.IOException -> L6e
            java.lang.String r4 = "Unknown status token: "
            r3.append(r4)     // Catch: java.io.IOException -> L6e
            r3.append(r1)     // Catch: java.io.IOException -> L6e
            java.lang.String r1 = r3.toString()     // Catch: java.io.IOException -> L6e
            android.util.Slog.w(r2, r1)     // Catch: java.io.IOException -> L6e
            goto L73
        L6e:
            java.lang.String r1 = "No initial status"
            android.util.Slog.i(r2, r1)
        L73:
            r7.writeStatusLocked()
        L76:
            boolean r1 = r0.exists()
            if (r1 == 0) goto L87
            android.util.AtomicFile r1 = r7.mStatusFile
            boolean r1 = r1.exists()
            if (r1 == 0) goto L87
            r0.delete()
        L87:
            android.util.AtomicFile r0 = r7.mStatusFile
            boolean r0 = r0.exists()
            if (r0 != 0) goto L90
            return
        L90:
            android.util.AtomicFile r0 = r7.mStatusFile     // Catch: java.lang.Exception -> L9f
            java.io.FileInputStream r0 = r0.openRead()     // Catch: java.lang.Exception -> L9f
            r7.readStatusInfoLocked(r0)     // Catch: java.lang.Throwable -> La1
            if (r0 == 0) goto Lb2
            r0.close()     // Catch: java.lang.Exception -> L9f
            goto Lb2
        L9f:
            r7 = move-exception
            goto Lad
        La1:
            r7 = move-exception
            if (r0 == 0) goto Lac
            r0.close()     // Catch: java.lang.Throwable -> La8
            goto Lac
        La8:
            r0 = move-exception
            r7.addSuppressed(r0)     // Catch: java.lang.Exception -> L9f
        Lac:
            throw r7     // Catch: java.lang.Exception -> L9f
        Lad:
            java.lang.String r0 = "Unable to read status info file."
            android.util.Slog.e(r2, r0, r7)
        Lb2:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.content.SyncStorageEngine.readStatusLocked():void");
    }

    public final void removeAuthorityLocked(Account account, String str, int i, boolean z) {
        AuthorityInfo authorityInfo;
        AccountInfo accountInfo = (AccountInfo) this.mAccounts.get(new AccountAndUser(account, i));
        if (accountInfo == null
                || (authorityInfo = (AuthorityInfo) accountInfo.authorities.remove(str)) == null) {
            return;
        }
        SyncManager.AnonymousClass7 anonymousClass7 = this.mAuthorityRemovedListener;
        if (anonymousClass7 != null) {
            anonymousClass7.onAuthorityRemoved(authorityInfo.target);
        }
        this.mAuthorities.remove(authorityInfo.ident);
        if (z) {
            writeAccountInfoLocked();
        }
    }

    public final void removeStaleAccounts(Account[] accountArr, int i) {
        synchronized (this.mAuthorities) {
            try {
                if (Log.isLoggable("SyncManager", 2)) {
                    Slog.v("SyncManager", "Updating for new accounts...");
                }
                SparseArray sparseArray = new SparseArray();
                Iterator it = this.mAccounts.values().iterator();
                while (it.hasNext()) {
                    AccountInfo accountInfo = (AccountInfo) it.next();
                    AccountAndUser accountAndUser = accountInfo.accountAndUser;
                    if (accountAndUser.userId == i
                            && (accountArr == null
                                    || !ArrayUtils.contains(accountArr, accountAndUser.account))) {
                        if (Log.isLoggable("SyncManager", 2)) {
                            Slog.v("SyncManager", "Account removed: " + accountInfo.accountAndUser);
                        }
                        for (AuthorityInfo authorityInfo : accountInfo.authorities.values()) {
                            sparseArray.put(authorityInfo.ident, authorityInfo);
                        }
                        it.remove();
                    }
                }
                int size = sparseArray.size();
                if (size > 0) {
                    while (size > 0) {
                        size--;
                        int keyAt = sparseArray.keyAt(size);
                        AuthorityInfo authorityInfo2 = (AuthorityInfo) sparseArray.valueAt(size);
                        SyncManager.AnonymousClass7 anonymousClass7 =
                                this.mAuthorityRemovedListener;
                        if (anonymousClass7 != null) {
                            anonymousClass7.onAuthorityRemoved(authorityInfo2.target);
                        }
                        this.mAuthorities.remove(keyAt);
                        int size2 = this.mSyncStatus.size();
                        while (size2 > 0) {
                            size2--;
                            if (this.mSyncStatus.keyAt(size2) == keyAt) {
                                SparseArray sparseArray2 = this.mSyncStatus;
                                sparseArray2.remove(sparseArray2.keyAt(size2));
                            }
                        }
                        int size3 = this.mSyncHistory.size();
                        while (size3 > 0) {
                            size3--;
                            if (((SyncHistoryItem) this.mSyncHistory.get(size3)).authorityId
                                    == keyAt) {
                                this.mSyncHistory.remove(size3);
                            }
                        }
                    }
                    writeAccountInfoLocked();
                    writeStatusLocked();
                    writeStatisticsLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void reportChange(int i, int i2, String str) {
        ArrayList arrayList;
        synchronized (this.mAuthorities) {
            try {
                int beginBroadcast = this.mChangeListeners.beginBroadcast();
                arrayList = null;
                while (beginBroadcast > 0) {
                    beginBroadcast--;
                    long longValue =
                            ((Long) this.mChangeListeners.getBroadcastCookie(beginBroadcast))
                                    .longValue();
                    int first = IntPair.first(longValue);
                    int userId = UserHandle.getUserId(first);
                    if ((IntPair.second(longValue) & i) != 0
                            && i2 == userId
                            && (str == null
                                    || !this.mPackageManagerInternal.filterAppAccess(
                                            first, i2, str, true))) {
                        if (arrayList == null) {
                            arrayList = new ArrayList(beginBroadcast);
                        }
                        arrayList.add(this.mChangeListeners.getBroadcastItem(beginBroadcast));
                    }
                }
                this.mChangeListeners.finishBroadcast();
            } catch (Throwable th) {
                throw th;
            }
        }
        if (Log.isLoggable("SyncManager", 2)) {
            Slog.v("SyncManager", "reportChange " + i + " to: " + arrayList);
        }
        if (arrayList != null) {
            int size = arrayList.size();
            while (size > 0) {
                size--;
                try {
                    ((ISyncStatusObserver) arrayList.get(size)).onStatusChanged(i);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public final void reportChange(int i, EndPoint endPoint) {
        String str;
        Account account = endPoint.account;
        int i2 = endPoint.userId;
        reportChange(
                i,
                i2,
                (account == null || (str = endPoint.provider) == null)
                        ? null
                        : ContentResolver.getSyncAdapterPackageAsUser(account.type, str, i2));
    }

    public final void resetTodayStats(boolean z) {
        if (z) {
            Log.w("SyncManager", "Force resetting today stats.");
        }
        synchronized (this.mAuthorities) {
            try {
                int size = this.mSyncStatus.size();
                for (int i = 0; i < size; i++) {
                    ((SyncStatusInfo) this.mSyncStatus.valueAt(i))
                            .maybeResetTodayStats(this.mIsClockValid, z);
                }
                writeStatusLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setBackoff(EndPoint endPoint, long j, long j2) {
        boolean backoffLocked;
        if (Log.isLoggable("SyncManager", 2)) {
            Slog.v(
                    "SyncManager",
                    "setBackoff: " + endPoint + " -> nextSyncTime " + j + ", nextDelay " + j2);
        }
        synchronized (this.mAuthorities) {
            try {
                Account account = endPoint.account;
                if (account != null && endPoint.provider != null) {
                    AuthorityInfo orCreateAuthorityLocked =
                            getOrCreateAuthorityLocked(endPoint, -1, true);
                    if (orCreateAuthorityLocked.backoffTime == j
                            && orCreateAuthorityLocked.backoffDelay == j2) {
                        backoffLocked = false;
                    } else {
                        orCreateAuthorityLocked.backoffTime = j;
                        orCreateAuthorityLocked.backoffDelay = j2;
                        backoffLocked = true;
                    }
                }
                backoffLocked =
                        setBackoffLocked(account, endPoint.userId, endPoint.provider, j, j2);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (backoffLocked) {
            reportChange(1, endPoint);
        }
    }

    public final boolean setBackoffLocked(Account account, int i, String str, long j, long j2) {
        boolean z = false;
        for (AccountInfo accountInfo : this.mAccounts.values()) {
            if (account == null
                    || account.equals(accountInfo.accountAndUser.account)
                    || i == accountInfo.accountAndUser.userId) {
                for (AuthorityInfo authorityInfo : accountInfo.authorities.values()) {
                    if (str == null || str.equals(authorityInfo.target.provider)) {
                        if (authorityInfo.backoffTime != j || authorityInfo.backoffDelay != j2) {
                            authorityInfo.backoffTime = j;
                            authorityInfo.backoffDelay = j2;
                            z = true;
                        }
                    }
                }
            }
        }
        return z;
    }

    public final void setIsSyncable(Account account, int i, String str, int i2, int i3, int i4) {
        SyncManager.AnonymousClass7 anonymousClass7;
        EndPoint endPoint = new EndPoint(account, str, i);
        this.mLogger.log(
                "Set syncable ",
                endPoint.toSafeString(),
                " value=",
                Integer.toString(i2),
                " cuid=",
                Integer.valueOf(i3),
                " cpid=",
                Integer.valueOf(i4));
        synchronized (this.mAuthorities) {
            int i5 = -1;
            try {
                AuthorityInfo orCreateAuthorityLocked =
                        getOrCreateAuthorityLocked(endPoint, -1, false);
                if (i2 >= -1) {
                    i5 = i2;
                }
                if (Log.isLoggable("SyncManager", 2)) {
                    Slog.d(
                            "SyncManager",
                            "setIsSyncable: " + orCreateAuthorityLocked.toString() + " -> " + i5);
                }
                if (orCreateAuthorityLocked.syncable == i5) {
                    if (Log.isLoggable("SyncManager", 2)) {
                        Slog.d(
                                "SyncManager",
                                "setIsSyncable: already set to " + i5 + ", doing nothing");
                    }
                    return;
                }
                orCreateAuthorityLocked.syncable = i5;
                writeAccountInfoLocked();
                if (i5 == 1) {
                    Bundle bundle = new Bundle();
                    if (Process.myUid() != 1000
                            || (anonymousClass7 = this.mSyncRequestListener) == null) {
                        SyncRequest.Builder extras =
                                new SyncRequest.Builder().syncOnce().setExtras(bundle);
                        EndPoint endPoint2 = orCreateAuthorityLocked.target;
                        extras.setSyncAdapter(endPoint2.account, endPoint2.provider);
                        ContentResolver.requestSync(extras.build());
                    } else {
                        EndPoint endPoint3 = orCreateAuthorityLocked.target;
                        SyncManager.this.scheduleSync(
                                endPoint3.account,
                                endPoint3.userId,
                                -5,
                                endPoint3.provider,
                                bundle,
                                -2,
                                0,
                                i3,
                                i4,
                                null);
                    }
                }
                reportChange(1, endPoint);
            } finally {
            }
        }
    }

    public final void setMasterSyncAutomatically(int i, int i2, int i3, int i4, boolean z) {
        SyncManager.AnonymousClass7 anonymousClass7;
        this.mLogger.log(
                "Set master enabled=",
                Boolean.valueOf(z),
                " user=",
                Integer.valueOf(i),
                " cuid=",
                Integer.valueOf(i3),
                " cpid=",
                Integer.valueOf(i4));
        synchronized (this.mAuthorities) {
            try {
                Boolean bool = (Boolean) this.mMasterSyncAutomatically.get(i);
                if (bool == null || !bool.equals(Boolean.valueOf(z))) {
                    this.mMasterSyncAutomatically.put(i, Boolean.valueOf(z));
                    writeAccountInfoLocked();
                    if (z) {
                        Bundle bundle = new Bundle();
                        if (Process.myUid() != 1000
                                || (anonymousClass7 = this.mSyncRequestListener) == null) {
                            ContentResolver.requestSync(null, null, bundle);
                        } else {
                            SyncManager.this.scheduleSync(
                                    null, i, -7, null, bundle, -2, i2, i3, i4, null);
                        }
                    }
                    reportChange(1, i, null);
                    this.mContext.sendBroadcast(ContentResolver.ACTION_SYNC_CONN_STATUS_CHANGED);
                    BackupManager.dataChanged("android");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setSyncAutomatically(
            Account account, int i, String str, boolean z, int i2, int i3, int i4) {
        SyncManager.AnonymousClass7 anonymousClass7;
        if (Log.isLoggable("SyncManager", 2)) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    "SyncManager",
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            i, "setSyncAutomatically:  provider ", str, ", user ", " -> "),
                    z);
        }
        this.mLogger.log(
                "Set sync auto account=",
                account.toSafeString(),
                " user=",
                Integer.valueOf(i),
                " authority=",
                str,
                " value=",
                Boolean.toString(z),
                " cuid=",
                Integer.valueOf(i3),
                " cpid=",
                Integer.valueOf(i4));
        synchronized (this.mAuthorities) {
            try {
                AuthorityInfo orCreateAuthorityLocked =
                        getOrCreateAuthorityLocked(new EndPoint(account, str, i), -1, false);
                if (orCreateAuthorityLocked.enabled == z) {
                    if (Log.isLoggable("SyncManager", 2)) {
                        Slog.d(
                                "SyncManager",
                                "setSyncAutomatically: already set to " + z + ", doing nothing");
                    }
                    return;
                }
                if (z && orCreateAuthorityLocked.syncable == 2) {
                    orCreateAuthorityLocked.syncable = -1;
                }
                orCreateAuthorityLocked.enabled = z;
                writeAccountInfoLocked();
                if (z) {
                    Bundle bundle = new Bundle();
                    if (Process.myUid() != 1000
                            || (anonymousClass7 = this.mSyncRequestListener) == null) {
                        ContentResolver.requestSync(account, str, bundle);
                    } else {
                        SyncManager.this.scheduleSync(
                                account, i, -6, str, bundle, -2, i2, i3, i4, null);
                    }
                }
                reportChange(1, orCreateAuthorityLocked.target);
                BackupManager.dataChanged("android");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void writeAccountInfoLocked() {
        FileOutputStream startWrite;
        if (Log.isLoggable("SyncManagerFile", 2)) {
            Slog.v("SyncManagerFile", "Writing new " + this.mAccountInfoFile.getBaseFile());
        }
        FileOutputStream fileOutputStream = null;
        try {
            startWrite = this.mAccountInfoFile.startWrite();
        } catch (IOException e) {
            e = e;
        }
        try {
            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
            resolveSerializer.startDocument((String) null, Boolean.TRUE);
            resolveSerializer.setFeature(
                    "http://xmlpull.org/v1/doc/features.html#indent-output", true);
            resolveSerializer.startTag((String) null, "accounts");
            resolveSerializer.attributeInt((String) null, "version", 3);
            resolveSerializer.attributeInt((String) null, "nextAuthorityId", this.mNextAuthorityId);
            resolveSerializer.attributeInt(
                    (String) null, "offsetInSeconds", this.mSyncRandomOffset);
            int size = this.mMasterSyncAutomatically.size();
            for (int i = 0; i < size; i++) {
                int keyAt = this.mMasterSyncAutomatically.keyAt(i);
                Boolean bool = (Boolean) this.mMasterSyncAutomatically.valueAt(i);
                resolveSerializer.startTag((String) null, "listenForTickles");
                resolveSerializer.attributeInt((String) null, "user", keyAt);
                resolveSerializer.attributeBoolean((String) null, "enabled", bool.booleanValue());
                resolveSerializer.endTag((String) null, "listenForTickles");
            }
            int size2 = this.mAuthorities.size();
            for (int i2 = 0; i2 < size2; i2++) {
                AuthorityInfo authorityInfo = (AuthorityInfo) this.mAuthorities.valueAt(i2);
                EndPoint endPoint = authorityInfo.target;
                resolveSerializer.startTag((String) null, "authority");
                resolveSerializer.attributeInt((String) null, "id", authorityInfo.ident);
                resolveSerializer.attributeInt((String) null, "user", endPoint.userId);
                resolveSerializer.attributeBoolean((String) null, "enabled", authorityInfo.enabled);
                resolveSerializer.attribute((String) null, "account", endPoint.account.name);
                resolveSerializer.attribute((String) null, "type", endPoint.account.type);
                resolveSerializer.attribute((String) null, "authority", endPoint.provider);
                resolveSerializer.attributeInt((String) null, "syncable", authorityInfo.syncable);
                resolveSerializer.endTag((String) null, "authority");
            }
            resolveSerializer.endTag((String) null, "accounts");
            resolveSerializer.endDocument();
            this.mAccountInfoFile.finishWrite(startWrite);
        } catch (IOException e2) {
            e = e2;
            fileOutputStream = startWrite;
            Slog.w("SyncManager", "Error writing accounts", e);
            if (fileOutputStream != null) {
                this.mAccountInfoFile.failWrite(fileOutputStream);
            }
        }
    }

    public final void writeDayStatsLocked(OutputStream outputStream) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(outputStream);
        int length = this.mDayStats.length;
        for (int i = 0; i < length; i++) {
            DayStats dayStats = this.mDayStats[i];
            if (dayStats == null) {
                break;
            }
            long start = protoOutputStream.start(2246267895809L);
            protoOutputStream.write(1120986464257L, dayStats.day);
            protoOutputStream.write(1120986464258L, dayStats.successCount);
            protoOutputStream.write(1112396529667L, dayStats.successTime);
            protoOutputStream.write(1120986464260L, dayStats.failureCount);
            protoOutputStream.write(1112396529669L, dayStats.failureTime);
            protoOutputStream.end(start);
        }
        protoOutputStream.flush();
    }

    public void writeStatisticsLocked() {
        FileOutputStream fileOutputStream;
        Throwable th;
        Throwable e;
        if (Log.isLoggable("SyncManagerFile", 2)) {
            Slog.v("SyncManager", "Writing new " + this.mStatisticsFile.getBaseFile());
        }
        this.mHandler.removeMessages(2);
        try {
            fileOutputStream = this.mStatisticsFile.startWrite();
        } catch (IOException | IllegalArgumentException e2) {
            fileOutputStream = null;
            e = e2;
        } catch (Throwable th2) {
            fileOutputStream = null;
            th = th2;
            this.mStatisticsFile.failWrite(fileOutputStream);
            throw th;
        }
        try {
            try {
                writeDayStatsLocked(fileOutputStream);
                this.mStatisticsFile.finishWrite(fileOutputStream);
                this.mStatisticsFile.failWrite(null);
            } catch (Throwable th3) {
                th = th3;
                this.mStatisticsFile.failWrite(fileOutputStream);
                throw th;
            }
        } catch (IOException | IllegalArgumentException e3) {
            e = e3;
            Slog.e("SyncManager", "Unable to write day stats to proto.", e);
            this.mStatisticsFile.failWrite(fileOutputStream);
        }
    }

    public final void writeStatusInfoLocked(OutputStream outputStream) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(outputStream);
        int size = this.mSyncStatus.size();
        for (int i = 0; i < size; i++) {
            SyncStatusInfo syncStatusInfo = (SyncStatusInfo) this.mSyncStatus.valueAt(i);
            long start = protoOutputStream.start(2246267895809L);
            protoOutputStream.write(1120986464258L, syncStatusInfo.authorityId);
            protoOutputStream.write(1112396529667L, syncStatusInfo.lastSuccessTime);
            protoOutputStream.write(1120986464260L, syncStatusInfo.lastSuccessSource);
            protoOutputStream.write(1112396529669L, syncStatusInfo.lastFailureTime);
            protoOutputStream.write(1120986464262L, syncStatusInfo.lastFailureSource);
            protoOutputStream.write(1138166333447L, syncStatusInfo.lastFailureMesg);
            protoOutputStream.write(1112396529672L, syncStatusInfo.initialFailureTime);
            protoOutputStream.write(1133871366153L, syncStatusInfo.pending);
            protoOutputStream.write(1133871366154L, syncStatusInfo.initialize);
            int periodicSyncTimesSize = syncStatusInfo.getPeriodicSyncTimesSize();
            for (int i2 = 0; i2 < periodicSyncTimesSize; i2++) {
                protoOutputStream.write(2211908157451L, syncStatusInfo.getPeriodicSyncTime(i2));
            }
            int eventCount = syncStatusInfo.getEventCount();
            for (int i3 = 0; i3 < eventCount; i3++) {
                long start2 = protoOutputStream.start(2246267895820L);
                protoOutputStream.write(1112396529665L, syncStatusInfo.getEventTime(i3));
                protoOutputStream.write(1138166333442L, syncStatusInfo.getEvent(i3));
                protoOutputStream.end(start2);
            }
            protoOutputStream.write(1112396529677L, syncStatusInfo.lastTodayResetTime);
            long start3 = protoOutputStream.start(1146756268046L);
            writeStatusStatsLocked(protoOutputStream, syncStatusInfo.totalStats);
            protoOutputStream.end(start3);
            long start4 = protoOutputStream.start(1146756268047L);
            writeStatusStatsLocked(protoOutputStream, syncStatusInfo.todayStats);
            protoOutputStream.end(start4);
            long start5 = protoOutputStream.start(1146756268048L);
            writeStatusStatsLocked(protoOutputStream, syncStatusInfo.yesterdayStats);
            protoOutputStream.end(start5);
            int length = syncStatusInfo.perSourceLastSuccessTimes.length;
            for (int i4 = 0; i4 < length; i4++) {
                protoOutputStream.write(
                        2211908157457L, syncStatusInfo.perSourceLastSuccessTimes[i4]);
            }
            int length2 = syncStatusInfo.perSourceLastFailureTimes.length;
            for (int i5 = 0; i5 < length2; i5++) {
                protoOutputStream.write(
                        2211908157458L, syncStatusInfo.perSourceLastFailureTimes[i5]);
            }
            protoOutputStream.end(start);
        }
        protoOutputStream.write(1133871366146L, this.mIsJobNamespaceMigrated);
        protoOutputStream.write(1133871366147L, this.mIsJobAttributionFixed);
        protoOutputStream.flush();
    }

    public void writeStatusLocked() {
        FileOutputStream fileOutputStream;
        Throwable th;
        Throwable e;
        if (Log.isLoggable("SyncManagerFile", 2)) {
            Slog.v("SyncManagerFile", "Writing new " + this.mStatusFile.getBaseFile());
        }
        this.mHandler.removeMessages(1);
        try {
            fileOutputStream = this.mStatusFile.startWrite();
        } catch (IOException | IllegalArgumentException e2) {
            fileOutputStream = null;
            e = e2;
        } catch (Throwable th2) {
            fileOutputStream = null;
            th = th2;
            this.mStatusFile.failWrite(fileOutputStream);
            throw th;
        }
        try {
            try {
                writeStatusInfoLocked(fileOutputStream);
                this.mStatusFile.finishWrite(fileOutputStream);
                this.mStatusFile.failWrite(null);
            } catch (Throwable th3) {
                th = th3;
                this.mStatusFile.failWrite(fileOutputStream);
                throw th;
            }
        } catch (IOException | IllegalArgumentException e3) {
            e = e3;
            Slog.e("SyncManager", "Unable to write sync status to proto.", e);
            this.mStatusFile.failWrite(fileOutputStream);
        }
    }
}
