package com.android.server.usage;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.content.res.Configuration;
import android.os.Build;
import android.os.PersistableBundle;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.LongSparseArray;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import libcore.io.IoUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UsageStatsDatabase {
    public static final int BACKUP_VERSION = 4;
    static final int[] MAX_FILES_PER_INTERVAL_TYPE = {100, 50, 12, 10};
    public static final int SELECTION_LOG_RETENTION_LEN = SystemProperties.getInt("ro.usagestats.chooser.retention", 14);
    public final File mBackupsDir;
    public int mCurrentVersion;
    public final File[] mIntervalDirs;
    public boolean mNewUpdate;
    public final File mPackageMappingsFile;
    public final File mUpdateBreadcrumb;
    public boolean mUpgradePerformed;
    public final File mVersionFile;
    public final Object mLock = new Object();
    public final PackagesTokenData mPackagesTokenData = new PackagesTokenData();
    final LongSparseArray[] mSortedStatFiles = new LongSparseArray[4];
    public final UnixCalendar mCal = new UnixCalendar();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.usage.UsageStatsDatabase$1, reason: invalid class name */
    public final class AnonymousClass1 implements FilenameFilter {
        @Override // java.io.FilenameFilter
        public final boolean accept(File file, String str) {
            return !str.endsWith(".bak");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface StatCombiner {
        boolean combine(IntervalStats intervalStats, boolean z, List list);
    }

    public UsageStatsDatabase(File file, int i) {
        this.mIntervalDirs = new File[]{new File(file, "daily"), new File(file, "weekly"), new File(file, "monthly"), new File(file, "yearly")};
        this.mCurrentVersion = i;
        this.mVersionFile = new File(file, "version");
        this.mBackupsDir = new File(file, "backups");
        this.mUpdateBreadcrumb = new File(file, "breadcrumb");
        this.mPackageMappingsFile = new File(file, "mappings");
    }

    public static void calculatePackagesUsedWithinTimeframe(IntervalStats intervalStats, Set set, long j) {
        for (UsageStats usageStats : intervalStats.packageStats.values()) {
            if (usageStats.getLastTimePackageUsed() > j) {
                ((ArraySet) set).add(usageStats.mPackageName);
            }
        }
    }

    public static void deleteDirectory(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File file2 : listFiles) {
                if (file2.isDirectory()) {
                    deleteDirectory(file2);
                } else {
                    file2.delete();
                }
            }
        }
        file.delete();
    }

    public static IntervalStats mergeStats(IntervalStats intervalStats, IntervalStats intervalStats2) {
        if (intervalStats2 == null) {
            return intervalStats;
        }
        intervalStats.activeConfiguration = intervalStats2.activeConfiguration;
        intervalStats.configurations.putAll(intervalStats2.configurations);
        intervalStats.events.clear();
        intervalStats.events.merge(intervalStats2.events);
        return intervalStats;
    }

    public static long parseBeginTime(File file) {
        String name = file.getName();
        for (int i = 0; i < name.length(); i++) {
            char charAt = name.charAt(i);
            if (charAt < '0' || charAt > '9') {
                name = name.substring(0, i);
                break;
            }
        }
        try {
            return Long.parseLong(name);
        } catch (NumberFormatException e) {
            throw new IOException(e);
        }
    }

    public static void pruneFilesOlderThan(File file, long j) {
        long j2;
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File file2 : listFiles) {
                try {
                    j2 = parseBeginTime(file2);
                } catch (IOException unused) {
                    j2 = 0;
                }
                if (j2 < j) {
                    new AtomicFile(file2).delete();
                }
            }
        }
    }

    public static boolean pruneStats(HashMap hashMap, IntervalStats intervalStats) {
        boolean z = false;
        for (int size = intervalStats.packageStats.size() - 1; size >= 0; size--) {
            UsageStats usageStats = (UsageStats) intervalStats.packageStats.valueAt(size);
            Long l = (Long) hashMap.get(usageStats.mPackageName);
            if (l == null || l.longValue() > usageStats.mEndTimeStamp) {
                intervalStats.packageStats.removeAt(size);
                z = true;
            }
        }
        if (z) {
            intervalStats.packageStatsObfuscated.clear();
        }
        for (int size2 = intervalStats.events.size() - 1; size2 >= 0; size2--) {
            UsageEvents.Event event = intervalStats.events.get(size2);
            Long l2 = (Long) hashMap.get(event.mPackage);
            if (l2 == null || l2.longValue() > event.mTimeStamp) {
                intervalStats.events.remove(size2);
                z = true;
            }
        }
        return z;
    }

    public static boolean readLocked(AtomicFile atomicFile, IntervalStats intervalStats, int i, PackagesTokenData packagesTokenData, boolean z) {
        try {
            FileInputStream openRead = atomicFile.openRead();
            try {
                intervalStats.beginTime = parseBeginTime(atomicFile.getBaseFile());
                boolean readLocked = readLocked(openRead, intervalStats, i, packagesTokenData, z);
                intervalStats.lastTimeSaved = atomicFile.getLastModifiedTime();
                return readLocked;
            } finally {
                try {
                    openRead.close();
                } catch (IOException unused) {
                }
            }
        } catch (FileNotFoundException e) {
            Slog.e("UsageStatsDatabase", "UsageStatsDatabase", e);
            throw e;
        }
    }

    public static boolean readLocked(InputStream inputStream, IntervalStats intervalStats, int i, PackagesTokenData packagesTokenData, boolean z) {
        boolean z2;
        int i2;
        int i3;
        int i4;
        int i5 = 0;
        boolean z3 = true;
        if (i == 1 || i == 2 || i == 3) {
            z2 = false;
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "Reading UsageStats as XML; database version: ", "UsageStatsDatabase");
            try {
                UsageStatsXml.read(inputStream, intervalStats);
            } catch (Exception e) {
                Slog.e("UsageStatsDatabase", "Unable to read interval stats from XML", e);
            }
        } else if (i == 4) {
            z2 = false;
            try {
                UsageStatsProto.read(inputStream, intervalStats);
            } catch (Exception e2) {
                Slog.e("UsageStatsDatabase", "Unable to read interval stats from proto.", e2);
            }
        } else {
            if (i != 5) {
                throw new RuntimeException("Unhandled UsageStatsDatabase version: " + Integer.toString(i) + " on read.");
            }
            try {
                UsageStatsProtoV2.read(inputStream, intervalStats, z);
            } catch (Exception e3) {
                Slog.e("UsageStatsDatabase", "Unable to read interval stats from proto.", e3);
            }
            ArraySet arraySet = new ArraySet();
            int size = intervalStats.packageStatsObfuscated.size();
            int i6 = 0;
            boolean z4 = false;
            while (true) {
                if (i6 >= size) {
                    break;
                }
                int keyAt = intervalStats.packageStatsObfuscated.keyAt(i6);
                UsageStats usageStats = (UsageStats) intervalStats.packageStatsObfuscated.valueAt(i6);
                ArrayList arrayList = (ArrayList) packagesTokenData.tokensToPackagesMap.get(keyAt);
                String str = arrayList != null ? (String) arrayList.get(i5) : null;
                usageStats.mPackageName = str;
                if (str == null) {
                    arraySet.add(Integer.valueOf(keyAt));
                    z4 = z3;
                    i2 = size;
                } else {
                    int size2 = usageStats.mChooserCountsObfuscated.size();
                    int i7 = i5;
                    while (i7 < size2) {
                        ArrayMap arrayMap = new ArrayMap();
                        String string = packagesTokenData.getString(keyAt, usageStats.mChooserCountsObfuscated.keyAt(i7));
                        if (string == null) {
                            i3 = size;
                        } else {
                            SparseIntArray sparseIntArray = (SparseIntArray) usageStats.mChooserCountsObfuscated.valueAt(i7);
                            int size3 = sparseIntArray.size();
                            while (i5 < size3) {
                                String string2 = packagesTokenData.getString(keyAt, sparseIntArray.keyAt(i5));
                                if (string2 == null) {
                                    i4 = size;
                                } else {
                                    i4 = size;
                                    arrayMap.put(string2, Integer.valueOf(sparseIntArray.valueAt(i5)));
                                }
                                i5++;
                                size = i4;
                            }
                            i3 = size;
                            usageStats.mChooserCounts.put(string, arrayMap);
                        }
                        i7++;
                        size = i3;
                        i5 = 0;
                    }
                    i2 = size;
                    intervalStats.packageStats.put(usageStats.mPackageName, usageStats);
                }
                i6++;
                size = i2;
                i5 = 0;
                z3 = true;
            }
            if (z4) {
                Slog.d("IntervalStats", "Unable to parse usage stats packages: " + Arrays.toString(arraySet.toArray()));
            }
            ArraySet arraySet2 = new ArraySet();
            boolean z5 = false;
            for (int size4 = intervalStats.events.size() - 1; size4 >= 0; size4--) {
                UsageEvents.Event event = intervalStats.events.get(size4);
                int i8 = event.mPackageToken;
                ArrayList arrayList2 = (ArrayList) packagesTokenData.tokensToPackagesMap.get(i8);
                String str2 = arrayList2 == null ? null : (String) arrayList2.get(0);
                event.mPackage = str2;
                if (str2 == null) {
                    arraySet2.add(Integer.valueOf(i8));
                    intervalStats.events.remove(size4);
                    z5 = true;
                } else {
                    int i9 = event.mClassToken;
                    if (i9 != -1) {
                        event.mClass = packagesTokenData.getString(i8, i9);
                    }
                    int i10 = event.mTaskRootPackageToken;
                    if (i10 != -1) {
                        event.mTaskRootPackage = packagesTokenData.getString(i8, i10);
                    }
                    int i11 = event.mTaskRootClassToken;
                    if (i11 != -1) {
                        event.mTaskRootClass = packagesTokenData.getString(i8, i11);
                    }
                    int i12 = event.mEventType;
                    if (i12 != 5) {
                        if (i12 == 12) {
                            String string3 = packagesTokenData.getString(i8, event.mNotificationChannelIdToken);
                            event.mNotificationChannelId = string3;
                            if (string3 == null) {
                                Slog.v("IntervalStats", "Unable to parse notification channel " + event.mNotificationChannelIdToken + " for package " + i8);
                                intervalStats.events.remove(size4);
                                z5 = true;
                            }
                        } else if (i12 == 30) {
                            String string4 = packagesTokenData.getString(i8, event.mLocusIdToken);
                            event.mLocusId = string4;
                            if (string4 == null) {
                                Slog.v("IntervalStats", "Unable to parse locus " + event.mLocusIdToken + " for package " + i8);
                                intervalStats.events.remove(size4);
                                z5 = true;
                            }
                        } else if (i12 == 7) {
                            UsageEvents.Event.UserInteractionEventExtrasToken userInteractionEventExtrasToken = event.mUserInteractionExtrasToken;
                            if (userInteractionEventExtrasToken != null) {
                                String string5 = packagesTokenData.getString(i8, userInteractionEventExtrasToken.mCategoryToken);
                                String string6 = packagesTokenData.getString(i8, event.mUserInteractionExtrasToken.mActionToken);
                                if (TextUtils.isEmpty(string5) || TextUtils.isEmpty(string6)) {
                                    intervalStats.events.remove(size4);
                                    z5 = true;
                                } else {
                                    PersistableBundle persistableBundle = new PersistableBundle();
                                    event.mExtras = persistableBundle;
                                    persistableBundle.putString("android.app.usage.extra.EVENT_CATEGORY", string5);
                                    event.mExtras.putString("android.app.usage.extra.EVENT_ACTION", string6);
                                    event.mUserInteractionExtrasToken = null;
                                }
                            }
                        } else if (i12 == 8) {
                            String string7 = packagesTokenData.getString(i8, event.mShortcutIdToken);
                            event.mShortcutId = string7;
                            if (string7 == null) {
                                Slog.v("IntervalStats", "Unable to parse shortcut " + event.mShortcutIdToken + " for package " + i8);
                                intervalStats.events.remove(size4);
                                z5 = true;
                            }
                        }
                    } else if (event.mConfiguration == null) {
                        event.mConfiguration = new Configuration();
                    }
                }
            }
            z2 = false;
            if (z5) {
                Slog.d("IntervalStats", "Unable to parse event packages: " + Arrays.toString(arraySet2.toArray()));
            }
            if (z4 || z5) {
                return true;
            }
        }
        return z2;
    }

    public static void writeLocked(AtomicFile atomicFile, IntervalStats intervalStats, int i, PackagesTokenData packagesTokenData) {
        FileOutputStream startWrite = atomicFile.startWrite();
        try {
            writeLocked(startWrite, intervalStats, i, packagesTokenData);
            atomicFile.finishWrite(startWrite);
            atomicFile.failWrite(null);
        } catch (Exception unused) {
            atomicFile.failWrite(startWrite);
        } catch (Throwable th) {
            atomicFile.failWrite(startWrite);
            throw th;
        }
    }

    public static void writeLocked(OutputStream outputStream, IntervalStats intervalStats, int i, PackagesTokenData packagesTokenData) {
        if (i == 1 || i == 2 || i == 3) {
            Slog.wtf("UsageStatsDatabase", "Attempting to write UsageStats as XML with version " + i);
            return;
        }
        if (i == 4) {
            try {
                UsageStatsProto.write(outputStream, intervalStats);
                return;
            } catch (Exception e) {
                Slog.e("UsageStatsDatabase", "Unable to write interval stats to proto.", e);
                throw e;
            }
        }
        if (i != 5) {
            throw new RuntimeException("Unhandled UsageStatsDatabase version: " + Integer.toString(i) + " on write.");
        }
        intervalStats.obfuscateData(packagesTokenData);
        try {
            UsageStatsProtoV2.write(outputStream, intervalStats);
        } catch (Exception e2) {
            Slog.e("UsageStatsDatabase", "Unable to write interval stats to proto.", e2);
            throw e2;
        }
    }

    public Set applyRestoredPayload(String str, byte[] bArr) {
        DataInputStream dataInputStream;
        int readInt;
        synchronized (this.mLock) {
            try {
                if (!"usage_stats".equals(str)) {
                    return Collections.EMPTY_SET;
                }
                IntervalStats latestUsageStats = getLatestUsageStats(0);
                IntervalStats latestUsageStats2 = getLatestUsageStats(1);
                IntervalStats latestUsageStats3 = getLatestUsageStats(2);
                IntervalStats latestUsageStats4 = getLatestUsageStats(3);
                ArraySet arraySet = new ArraySet();
                try {
                    try {
                        dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
                        readInt = dataInputStream.readInt();
                    } catch (IOException e) {
                        Slog.d("UsageStatsDatabase", "Failed to read data from input stream", e);
                    }
                    if (readInt >= 1 && readInt <= 4) {
                        int i = 0;
                        while (true) {
                            File[] fileArr = this.mIntervalDirs;
                            if (i >= fileArr.length) {
                                break;
                            }
                            for (File file : fileArr[i].listFiles()) {
                                deleteDirectory(file);
                            }
                            i++;
                        }
                        IntervalStats intervalStats = latestUsageStats3;
                        long currentTimeMillis = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(90L);
                        int readInt2 = dataInputStream.readInt();
                        for (int i2 = 0; i2 < readInt2; i2++) {
                            int readInt3 = dataInputStream.readInt();
                            byte[] bArr2 = new byte[readInt3];
                            dataInputStream.read(bArr2, 0, readInt3);
                            IntervalStats deserializeIntervalStats = deserializeIntervalStats(readInt, bArr2);
                            calculatePackagesUsedWithinTimeframe(deserializeIntervalStats, arraySet, currentTimeMillis);
                            arraySet.addAll(deserializeIntervalStats.packageStats.keySet());
                            mergeStats(deserializeIntervalStats, latestUsageStats);
                            putUsageStats(0, deserializeIntervalStats);
                        }
                        int readInt4 = dataInputStream.readInt();
                        for (int i3 = 0; i3 < readInt4; i3++) {
                            int readInt5 = dataInputStream.readInt();
                            byte[] bArr3 = new byte[readInt5];
                            dataInputStream.read(bArr3, 0, readInt5);
                            IntervalStats deserializeIntervalStats2 = deserializeIntervalStats(readInt, bArr3);
                            calculatePackagesUsedWithinTimeframe(deserializeIntervalStats2, arraySet, currentTimeMillis);
                            mergeStats(deserializeIntervalStats2, latestUsageStats2);
                            putUsageStats(1, deserializeIntervalStats2);
                        }
                        int readInt6 = dataInputStream.readInt();
                        int i4 = 0;
                        while (i4 < readInt6) {
                            int readInt7 = dataInputStream.readInt();
                            byte[] bArr4 = new byte[readInt7];
                            dataInputStream.read(bArr4, 0, readInt7);
                            IntervalStats deserializeIntervalStats3 = deserializeIntervalStats(readInt, bArr4);
                            calculatePackagesUsedWithinTimeframe(deserializeIntervalStats3, arraySet, currentTimeMillis);
                            IntervalStats intervalStats2 = intervalStats;
                            mergeStats(deserializeIntervalStats3, intervalStats2);
                            putUsageStats(2, deserializeIntervalStats3);
                            i4++;
                            intervalStats = intervalStats2;
                        }
                        int readInt8 = dataInputStream.readInt();
                        for (int i5 = 0; i5 < readInt8; i5++) {
                            int readInt9 = dataInputStream.readInt();
                            byte[] bArr5 = new byte[readInt9];
                            dataInputStream.read(bArr5, 0, readInt9);
                            IntervalStats deserializeIntervalStats4 = deserializeIntervalStats(readInt, bArr5);
                            calculatePackagesUsedWithinTimeframe(deserializeIntervalStats4, arraySet, currentTimeMillis);
                            mergeStats(deserializeIntervalStats4, latestUsageStats4);
                            putUsageStats(3, deserializeIntervalStats4);
                        }
                        indexFilesLocked();
                        return arraySet;
                    }
                    return arraySet;
                } finally {
                    indexFilesLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v3 */
    /* JADX WARN: Type inference failed for: r13v4, types: [int] */
    /* JADX WARN: Type inference failed for: r13v6 */
    public final void checkVersionAndBuildLocked() {
        int i;
        int i2;
        boolean z;
        StringBuilder sb = new StringBuilder();
        sb.append(Build.VERSION.RELEASE + ";" + Build.VERSION.CODENAME + ";" + Build.VERSION.INCREMENTAL);
        StringBuilder sb2 = new StringBuilder(";");
        sb2.append(SystemProperties.get("ro.csc.sales_code", ""));
        sb2.append(";");
        sb2.append(SystemProperties.get("ril.official_cscver", ""));
        sb.append(sb2.toString());
        String sb3 = sb.toString();
        this.mNewUpdate = true;
        boolean z2 = false;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.mVersionFile));
            try {
                int parseInt = Integer.parseInt(bufferedReader.readLine());
                if (sb3.equals(bufferedReader.readLine())) {
                    this.mNewUpdate = false;
                }
                bufferedReader.close();
                i = parseInt;
            } finally {
            }
        } catch (IOException | NumberFormatException unused) {
            i = 0;
        }
        if (i != this.mCurrentVersion) {
            SystemServiceManager$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m(i, "Upgrading from version ", " to "), this.mCurrentVersion, "UsageStatsDatabase");
            if (this.mUpdateBreadcrumb.exists()) {
                Slog.i("UsageStatsDatabase", "Version upgrade breadcrumb found on disk! Continuing version upgrade");
            } else {
                try {
                    doUpgradeLocked(i);
                } catch (Exception e) {
                    StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Failed to upgrade from version ", " to ");
                    m.append(this.mCurrentVersion);
                    Slog.e("UsageStatsDatabase", m.toString(), e);
                    this.mCurrentVersion = i;
                    return;
                }
            }
        }
        if (this.mUpdateBreadcrumb.exists()) {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader(this.mUpdateBreadcrumb));
                try {
                    long parseLong = Long.parseLong(bufferedReader2.readLine());
                    int parseInt2 = Integer.parseInt(bufferedReader2.readLine());
                    bufferedReader2.close();
                    if (this.mCurrentVersion >= 4) {
                        PackagesTokenData packagesTokenData = this.mPackagesTokenData;
                        if (parseInt2 <= 3) {
                            HeapdumpWatcher$$ExternalSyntheticOutline0.m(new StringBuilder("Reading UsageStats as XML; current database version: "), this.mCurrentVersion, "UsageStatsDatabase");
                        }
                        File file = new File(this.mBackupsDir, Long.toString(parseLong));
                        int i3 = 5;
                        if (parseInt2 >= 5) {
                            readMappingsLocked();
                        }
                        int i4 = 0;
                        while (i4 < this.mIntervalDirs.length) {
                            File[] listFiles = new File(file, this.mIntervalDirs[i4].getName()).listFiles();
                            if (listFiles != null) {
                                for (?? r13 = z2; r13 < listFiles.length; r13++) {
                                    try {
                                        IntervalStats intervalStats = new IntervalStats();
                                        readLocked(new AtomicFile(listFiles[r13]), intervalStats, parseInt2, packagesTokenData, z2);
                                        if (this.mCurrentVersion >= i3) {
                                            intervalStats.obfuscateData(packagesTokenData);
                                        }
                                        i2 = i4;
                                        try {
                                            writeLocked(new AtomicFile(new File(this.mIntervalDirs[i4], Long.toString(intervalStats.beginTime))), intervalStats, this.mCurrentVersion, packagesTokenData);
                                        } catch (Exception unused2) {
                                            Slog.e("UsageStatsDatabase", "Failed to upgrade backup file : " + listFiles[r13].toString());
                                            i4 = i2;
                                            z2 = false;
                                            i3 = 5;
                                        }
                                    } catch (Exception unused3) {
                                        i2 = i4;
                                    }
                                    i4 = i2;
                                    z2 = false;
                                    i3 = 5;
                                }
                            }
                            i4++;
                            z2 = false;
                            i3 = 5;
                        }
                        if (this.mCurrentVersion >= 5) {
                            try {
                                writeMappingsLocked();
                            } catch (IOException unused4) {
                                Slog.e("UsageStatsDatabase", "Failed to write the tokens mappings file.");
                            }
                        }
                    } else {
                        Slog.wtf("UsageStatsDatabase", "Attempting to upgrade to an unsupported version: " + this.mCurrentVersion);
                    }
                } finally {
                }
            } catch (IOException | NumberFormatException e2) {
                Slog.e("UsageStatsDatabase", "Failed read version upgrade breadcrumb");
                throw new RuntimeException(e2);
            }
        }
        if (i != this.mCurrentVersion || this.mNewUpdate) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.mVersionFile));
                try {
                    bufferedWriter.write(Integer.toString(this.mCurrentVersion));
                    bufferedWriter.write("\n");
                    bufferedWriter.write(sb3);
                    bufferedWriter.write("\n");
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } finally {
                }
            } catch (IOException e3) {
                Slog.e("UsageStatsDatabase", "Failed to write new version");
                throw new RuntimeException(e3);
            }
        }
        if (this.mUpdateBreadcrumb.exists()) {
            this.mUpdateBreadcrumb.delete();
            z = true;
            this.mUpgradePerformed = true;
        } else {
            z = true;
        }
        if (this.mBackupsDir.exists()) {
            this.mUpgradePerformed = z;
            deleteDirectory(this.mBackupsDir);
        }
    }

    public final IntervalStats deserializeIntervalStats(int i, byte[] bArr) {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        IntervalStats intervalStats = new IntervalStats();
        try {
            intervalStats.beginTime = dataInputStream.readLong();
            readLocked((InputStream) dataInputStream, intervalStats, i, this.mPackagesTokenData, false);
            return intervalStats;
        } catch (Exception e) {
            Slog.d("UsageStatsDatabase", "DeSerializing IntervalStats Failed", e);
            return null;
        }
    }

    public final void doUpgradeLocked(int i) {
        if (i < 2) {
            Slog.i("UsageStatsDatabase", "Deleting all usage stats files");
            int i2 = 0;
            while (true) {
                File[] fileArr = this.mIntervalDirs;
                if (i2 >= fileArr.length) {
                    return;
                }
                File[] listFiles = fileArr[i2].listFiles();
                if (listFiles != null) {
                    for (File file : listFiles) {
                        file.delete();
                    }
                }
                i2++;
            }
        } else {
            long currentTimeMillis = System.currentTimeMillis();
            File file2 = new File(this.mBackupsDir, Long.toString(currentTimeMillis));
            file2.mkdirs();
            if (!file2.exists()) {
                throw new IllegalStateException("Failed to create backup directory " + file2.getAbsolutePath());
            }
            try {
                Files.copy(this.mVersionFile.toPath(), new File(file2, this.mVersionFile.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                for (int i3 = 0; i3 < this.mIntervalDirs.length; i3++) {
                    File file3 = new File(file2, this.mIntervalDirs[i3].getName());
                    file3.mkdir();
                    if (!file3.exists()) {
                        throw new IllegalStateException("Failed to create interval backup directory " + file3.getAbsolutePath());
                    }
                    File[] listFiles2 = this.mIntervalDirs[i3].listFiles();
                    if (listFiles2 != null) {
                        for (int i4 = 0; i4 < listFiles2.length; i4++) {
                            try {
                                Files.move(listFiles2[i4].toPath(), new File(file3, listFiles2[i4].getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException e) {
                                Slog.e("UsageStatsDatabase", "Failed to back up file : " + listFiles2[i4].toString());
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
                BufferedWriter bufferedWriter = null;
                try {
                    try {
                        BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(this.mUpdateBreadcrumb));
                        try {
                            bufferedWriter2.write(Long.toString(currentTimeMillis));
                            bufferedWriter2.write("\n");
                            bufferedWriter2.write(Integer.toString(i));
                            bufferedWriter2.write("\n");
                            bufferedWriter2.flush();
                            IoUtils.closeQuietly(bufferedWriter2);
                        } catch (IOException e2) {
                            e = e2;
                            bufferedWriter = bufferedWriter2;
                            Slog.e("UsageStatsDatabase", "Failed to write new version upgrade breadcrumb");
                            throw new RuntimeException(e);
                        } catch (Throwable th) {
                            th = th;
                            bufferedWriter = bufferedWriter2;
                            IoUtils.closeQuietly(bufferedWriter);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (IOException e3) {
                    e = e3;
                }
            } catch (IOException e4) {
                Slog.e("UsageStatsDatabase", "Failed to back up version file : " + this.mVersionFile.toString());
                throw new RuntimeException(e4);
            }
        }
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter, boolean z) {
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println();
                indentingPrintWriter.println("UsageStatsDatabase:");
                indentingPrintWriter.increaseIndent();
                dumpMappings(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("Database Summary:");
                indentingPrintWriter.increaseIndent();
                int i = 0;
                while (true) {
                    LongSparseArray[] longSparseArrayArr = this.mSortedStatFiles;
                    if (i < longSparseArrayArr.length) {
                        LongSparseArray longSparseArray = longSparseArrayArr[i];
                        int size = longSparseArray.size();
                        indentingPrintWriter.print(UserUsageStatsService.intervalToString(i));
                        indentingPrintWriter.print(" stats files: ");
                        indentingPrintWriter.print(size);
                        indentingPrintWriter.println(", sorted list of files:");
                        indentingPrintWriter.increaseIndent();
                        for (int i2 = 0; i2 < size; i2++) {
                            long keyAt = longSparseArray.keyAt(i2);
                            if (z) {
                                indentingPrintWriter.print(Long.toString(keyAt));
                            } else {
                                indentingPrintWriter.printPair(Long.toString(keyAt), UserUsageStatsService.formatDateTime(keyAt, true));
                            }
                            indentingPrintWriter.print(" size:" + ((AtomicFile) longSparseArray.valueAt(i2)).getBaseFile().length() + "byte");
                            indentingPrintWriter.println();
                        }
                        indentingPrintWriter.decreaseIndent();
                        i++;
                    } else {
                        indentingPrintWriter.decreaseIndent();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dumpMappings(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println("Obfuscated Packages Mappings:");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("Counter: " + this.mPackagesTokenData.counter);
                indentingPrintWriter.println("Tokens Map Size: " + this.mPackagesTokenData.tokensToPackagesMap.size());
                if (!this.mPackagesTokenData.removedPackageTokens.isEmpty()) {
                    indentingPrintWriter.println("Removed Package Tokens: " + Arrays.toString(this.mPackagesTokenData.removedPackageTokens.toArray()));
                }
                for (int i = 0; i < this.mPackagesTokenData.tokensToPackagesMap.size(); i++) {
                    indentingPrintWriter.println("Token " + this.mPackagesTokenData.tokensToPackagesMap.keyAt(i) + ": [" + String.join(", ", (Iterable<? extends CharSequence>) this.mPackagesTokenData.tokensToPackagesMap.valueAt(i)) + "]");
                }
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void forceIndexFiles() {
        synchronized (this.mLock) {
            indexFilesLocked();
        }
    }

    public byte[] getBackupPayload(String str, int i) {
        byte[] byteArray;
        if (i >= 1 && i <= 3) {
            Slog.wtf("UsageStatsDatabase", "Attempting to backup UsageStats as XML with version " + i);
            return null;
        }
        if (i < 1 || i > 4) {
            Slog.wtf("UsageStatsDatabase", "Attempting to backup UsageStats with an unknown version: " + i);
            return null;
        }
        synchronized (this.mLock) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                if ("usage_stats".equals(str)) {
                    prune(System.currentTimeMillis(), false);
                    DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                    try {
                        dataOutputStream.writeInt(i);
                        dataOutputStream.writeInt(this.mSortedStatFiles[0].size());
                        for (int i2 = 0; i2 < this.mSortedStatFiles[0].size(); i2++) {
                            writeIntervalStatsToStream(dataOutputStream, (AtomicFile) this.mSortedStatFiles[0].valueAt(i2), i);
                        }
                        dataOutputStream.writeInt(this.mSortedStatFiles[1].size());
                        for (int i3 = 0; i3 < this.mSortedStatFiles[1].size(); i3++) {
                            writeIntervalStatsToStream(dataOutputStream, (AtomicFile) this.mSortedStatFiles[1].valueAt(i3), i);
                        }
                        dataOutputStream.writeInt(this.mSortedStatFiles[2].size());
                        for (int i4 = 0; i4 < this.mSortedStatFiles[2].size(); i4++) {
                            writeIntervalStatsToStream(dataOutputStream, (AtomicFile) this.mSortedStatFiles[2].valueAt(i4), i);
                        }
                        dataOutputStream.writeInt(this.mSortedStatFiles[3].size());
                        for (int i5 = 0; i5 < this.mSortedStatFiles[3].size(); i5++) {
                            writeIntervalStatsToStream(dataOutputStream, (AtomicFile) this.mSortedStatFiles[3].valueAt(i5), i);
                        }
                    } catch (IOException e) {
                        Slog.d("UsageStatsDatabase", "Failed to write data to output stream", e);
                        byteArrayOutputStream.reset();
                    }
                }
                byteArray = byteArrayOutputStream.toByteArray();
            } catch (Throwable th) {
                throw th;
            }
        }
        return byteArray;
    }

    public final IntervalStats getLatestUsageStats(int i) {
        synchronized (this.mLock) {
            if (i >= 0) {
                if (i < this.mIntervalDirs.length) {
                    int size = this.mSortedStatFiles[i].size();
                    if (size == 0) {
                        return null;
                    }
                    try {
                        AtomicFile atomicFile = (AtomicFile) this.mSortedStatFiles[i].valueAt(size - 1);
                        IntervalStats intervalStats = new IntervalStats();
                        readLocked(atomicFile, intervalStats, false);
                        return intervalStats;
                    } catch (Exception e) {
                        Slog.e("UsageStatsDatabase", "Failed to read usage stats file", e);
                        return null;
                    }
                }
            }
            throw new IllegalArgumentException("Bad interval type " + i);
        }
    }

    public final void indexFilesLocked() {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        int i = 0;
        while (true) {
            LongSparseArray[] longSparseArrayArr = this.mSortedStatFiles;
            if (i >= longSparseArrayArr.length) {
                return;
            }
            LongSparseArray longSparseArray = longSparseArrayArr[i];
            if (longSparseArray == null) {
                longSparseArrayArr[i] = new LongSparseArray();
            } else {
                longSparseArray.clear();
            }
            File[] listFiles = this.mIntervalDirs[i].listFiles(anonymousClass1);
            if (listFiles != null) {
                for (File file : listFiles) {
                    AtomicFile atomicFile = new AtomicFile(file);
                    try {
                        this.mSortedStatFiles[i].put(parseBeginTime(atomicFile.getBaseFile()), atomicFile);
                    } catch (IOException e) {
                        Slog.e("UsageStatsDatabase", "failed to index file: " + file, e);
                    }
                }
                int size = this.mSortedStatFiles[i].size() - MAX_FILES_PER_INTERVAL_TYPE[i];
                if (size > 0) {
                    for (int i2 = 0; i2 < size; i2++) {
                        ((AtomicFile) this.mSortedStatFiles[i].valueAt(0)).delete();
                        this.mSortedStatFiles[i].removeAt(0);
                    }
                    ASKSManagerService$$ExternalSyntheticOutline0.m(size, i, "Deleted ", " stat files for interval ", "UsageStatsDatabase");
                }
            }
            i++;
        }
    }

    public final void prune(long j, boolean z) {
        synchronized (this.mLock) {
            try {
                UnixCalendar unixCalendar = this.mCal;
                unixCalendar.mTime = j;
                if (!z) {
                    unixCalendar.mTime = ((-2) * 31536000000L) + j;
                }
                pruneFilesOlderThan(this.mIntervalDirs[3], unixCalendar.mTime);
                UnixCalendar unixCalendar2 = this.mCal;
                unixCalendar2.mTime = j;
                if (!z) {
                    unixCalendar2.mTime = ((-6) * 2592000000L) + j;
                }
                pruneFilesOlderThan(this.mIntervalDirs[2], unixCalendar2.mTime);
                UnixCalendar unixCalendar3 = this.mCal;
                unixCalendar3.mTime = j;
                if (!z) {
                    unixCalendar3.mTime = ((-4) * 604800000) + j;
                }
                pruneFilesOlderThan(this.mIntervalDirs[1], unixCalendar3.mTime);
                UnixCalendar unixCalendar4 = this.mCal;
                unixCalendar4.mTime = j;
                if (!z) {
                    unixCalendar4.mTime = ((-10) * BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS) + j;
                }
                int i = 0;
                pruneFilesOlderThan(this.mIntervalDirs[0], unixCalendar4.mTime);
                UnixCalendar unixCalendar5 = this.mCal;
                unixCalendar5.mTime = j;
                if (!z) {
                    unixCalendar5.mTime = ((-SELECTION_LOG_RETENTION_LEN) * BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS) + j;
                }
                while (true) {
                    File[] fileArr = this.mIntervalDirs;
                    if (i < fileArr.length) {
                        pruneChooserCountsOlderThan(fileArr[i], this.mCal.mTime);
                        i++;
                    } else {
                        indexFilesLocked();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void pruneChooserCountsOlderThan(File file, long j) {
        long j2;
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File file2 : listFiles) {
                try {
                    j2 = parseBeginTime(file2);
                } catch (IOException unused) {
                    j2 = 0;
                }
                if (j2 < j) {
                    try {
                        AtomicFile atomicFile = new AtomicFile(file2);
                        IntervalStats intervalStats = new IntervalStats();
                        readLocked(atomicFile, intervalStats, false);
                        int size = intervalStats.packageStats.size();
                        for (int i = 0; i < size; i++) {
                            ArrayMap arrayMap = ((UsageStats) intervalStats.packageStats.valueAt(i)).mChooserCounts;
                            if (arrayMap != null) {
                                arrayMap.clear();
                            }
                        }
                        writeLocked(atomicFile, intervalStats);
                    } catch (Exception e) {
                        Slog.e("UsageStatsDatabase", "Failed to delete chooser counts from usage stats file", e);
                    }
                }
            }
        }
    }

    public final void prunePackagesDataOnUpgrade(HashMap hashMap) {
        if (ArrayUtils.isEmpty(hashMap)) {
            return;
        }
        synchronized (this.mLock) {
            int i = 0;
            while (true) {
                File[] fileArr = this.mIntervalDirs;
                if (i < fileArr.length) {
                    File[] listFiles = fileArr[i].listFiles();
                    if (listFiles != null) {
                        for (int i2 = 0; i2 < listFiles.length; i2++) {
                            try {
                                IntervalStats intervalStats = new IntervalStats();
                                AtomicFile atomicFile = new AtomicFile(listFiles[i2]);
                                readLocked(atomicFile, intervalStats, this.mCurrentVersion, this.mPackagesTokenData, false);
                                if (pruneStats(hashMap, intervalStats)) {
                                    writeLocked(atomicFile, intervalStats, this.mCurrentVersion, this.mPackagesTokenData);
                                }
                            } catch (Exception unused) {
                                Slog.e("UsageStatsDatabase", "Failed to prune data from: " + listFiles[i2].toString());
                            }
                        }
                    }
                    i++;
                }
            }
        }
    }

    public final void putUsageStats(int i, IntervalStats intervalStats) {
        if (intervalStats == null) {
            return;
        }
        synchronized (this.mLock) {
            if (i >= 0) {
                try {
                    if (i < this.mIntervalDirs.length) {
                        AtomicFile atomicFile = (AtomicFile) this.mSortedStatFiles[i].get(intervalStats.beginTime);
                        if (atomicFile == null) {
                            atomicFile = new AtomicFile(new File(this.mIntervalDirs[i], Long.toString(intervalStats.beginTime)));
                            this.mSortedStatFiles[i].put(intervalStats.beginTime, atomicFile);
                        }
                        writeLocked(atomicFile, intervalStats);
                        intervalStats.lastTimeSaved = atomicFile.getLastModifiedTime();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            throw new IllegalArgumentException("Bad interval type " + i);
        }
    }

    public final IntervalStats readIntervalStatsForFile(int i, long j) {
        IntervalStats intervalStats;
        synchronized (this.mLock) {
            try {
                intervalStats = new IntervalStats();
                try {
                    readLocked((AtomicFile) this.mSortedStatFiles[i].get(j, null), intervalStats, false);
                } catch (Exception unused) {
                    return null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return intervalStats;
    }

    public final void readLocked(AtomicFile atomicFile, IntervalStats intervalStats, boolean z) {
        if (this.mCurrentVersion <= 3) {
            Slog.wtf("UsageStatsDatabase", "Reading UsageStats as XML; current database version: " + this.mCurrentVersion);
        }
        readLocked(atomicFile, intervalStats, this.mCurrentVersion, this.mPackagesTokenData, z);
    }

    public final void readMappingsLocked() {
        PackagesTokenData packagesTokenData = this.mPackagesTokenData;
        if (this.mPackageMappingsFile.exists()) {
            try {
                FileInputStream openRead = new AtomicFile(this.mPackageMappingsFile).openRead();
                try {
                    UsageStatsProtoV2.readObfuscatedData(openRead, packagesTokenData);
                    if (openRead != null) {
                        openRead.close();
                    }
                    SparseArray sparseArray = packagesTokenData.tokensToPackagesMap;
                    int size = sparseArray.size();
                    for (int i = 0; i < size; i++) {
                        int keyAt = sparseArray.keyAt(i);
                        ArrayList arrayList = (ArrayList) sparseArray.valueAt(i);
                        ArrayMap arrayMap = new ArrayMap();
                        int size2 = arrayList.size();
                        arrayMap.put((String) arrayList.get(0), Integer.valueOf(keyAt));
                        for (int i2 = 1; i2 < size2; i2++) {
                            arrayMap.put((String) arrayList.get(i2), Integer.valueOf(i2));
                        }
                        packagesTokenData.packagesToTokensMap.put((String) arrayList.get(0), arrayMap);
                    }
                } finally {
                }
            } catch (Exception e) {
                Slog.e("UsageStatsDatabase", "Failed to read the obfuscated packages mapping file.", e);
            }
        }
    }

    public final void writeIntervalStatsToStream(DataOutputStream dataOutputStream, AtomicFile atomicFile, int i) {
        IntervalStats intervalStats = new IntervalStats();
        try {
            readLocked(atomicFile, intervalStats, false);
            intervalStats.activeConfiguration = null;
            intervalStats.configurations.clear();
            intervalStats.events.clear();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream2.writeLong(intervalStats.beginTime);
                writeLocked(dataOutputStream2, intervalStats, i, this.mPackagesTokenData);
            } catch (Exception e) {
                Slog.d("UsageStatsDatabase", "Serializing IntervalStats Failed", e);
                byteArrayOutputStream.reset();
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            dataOutputStream.writeInt(byteArray.length);
            dataOutputStream.write(byteArray);
        } catch (IOException e2) {
            Slog.e("UsageStatsDatabase", "Failed to read usage stats file", e2);
            dataOutputStream.writeInt(0);
        }
    }

    public final void writeLocked(AtomicFile atomicFile, IntervalStats intervalStats) {
        int i = this.mCurrentVersion;
        if (i > 3) {
            writeLocked(atomicFile, intervalStats, i, this.mPackagesTokenData);
            return;
        }
        Slog.wtf("UsageStatsDatabase", "Attempting to write UsageStats as XML with version " + this.mCurrentVersion);
    }

    public final void writeMappingsLocked() {
        AtomicFile atomicFile = new AtomicFile(this.mPackageMappingsFile);
        FileOutputStream startWrite = atomicFile.startWrite();
        try {
            try {
                UsageStatsProtoV2.writeObfuscatedData(startWrite, this.mPackagesTokenData);
                atomicFile.finishWrite(startWrite);
                atomicFile.failWrite(null);
            } catch (Exception e) {
                Slog.e("UsageStatsDatabase", "Unable to write obfuscated data to proto.", e);
                atomicFile.failWrite(startWrite);
            }
        } catch (Throwable th) {
            atomicFile.failWrite(startWrite);
            throw th;
        }
    }
}
