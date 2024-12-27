package com.android.server.appop;

import android.app.AppOpsManager;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.os.Message;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.LongSparseArray;
import android.util.Slog;
import android.util.TimeUtils;
import android.util.Xml;
import com.android.internal.os.AtomicDirectory;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.FgThread;
import com.android.server.IoThread;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.appop.DiscreteRegistry;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.samsung.android.knox.analytics.service.KnoxAnalyticsSystemService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HistoricalRegistry {
    public AppOpsManager.HistoricalOps mCurrentHistoricalOps;
    public volatile DiscreteRegistry mDiscreteRegistry;
    public final Object mInMemoryLock;
    public long mNextPersistDueTimeMillis;
    public long mPendingHistoryOffsetMillis;
    public Persistence mPersistence;
    public static final boolean KEEP_WTF_LOG = Build.IS_DEBUGGABLE;
    public static final String LOG_TAG = "HistoricalRegistry";
    public static final long DEFAULT_SNAPSHOT_INTERVAL_MILLIS = TimeUnit.MINUTES.toMillis(15);
    public final LinkedList mPendingWrites = new LinkedList();
    public final Object mOnDiskLock = new Object();
    public int mMode = 1;
    public long mBaseSnapshotInterval = DEFAULT_SNAPSHOT_INTERVAL_MILLIS;
    public long mIntervalCompressionMultiplier = 10;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Persistence {
        public static final AtomicDirectory sHistoricalAppOpsDir = new AtomicDirectory(new File(new File(Environment.getDataSystemDirectory(), "appops"), "history"));
        public final long mBaseSnapshotInterval;
        public final long mIntervalCompressionMultiplier;

        public Persistence(long j, long j2) {
            this.mBaseSnapshotInterval = j;
            this.mIntervalCompressionMultiplier = j2;
        }

        public static void clearHistoryDLocked$1() {
            sHistoricalAppOpsDir.delete();
        }

        public static Set getHistoricalFileNames(File file) {
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                return Collections.emptySet();
            }
            ArraySet arraySet = new ArraySet(listFiles.length);
            for (File file2 : listFiles) {
                arraySet.add(file2.getName());
            }
            return arraySet;
        }

        public static long getLastPersistTimeMillisDLocked() {
            File file;
            Throwable th;
            AtomicDirectory atomicDirectory;
            File[] listFiles;
            File file2 = null;
            try {
                atomicDirectory = sHistoricalAppOpsDir;
                file = atomicDirectory.startRead();
                try {
                    listFiles = file.listFiles();
                } catch (Throwable th2) {
                    th = th2;
                    HistoricalRegistry.m245$$Nest$smwtf("Error reading historical app ops. Deleting history.", th, file);
                    sHistoricalAppOpsDir.delete();
                    return 0L;
                }
            } catch (Throwable th3) {
                file = null;
                th = th3;
            }
            if (listFiles == null || listFiles.length <= 0) {
                atomicDirectory.finishRead();
                return 0L;
            }
            for (File file3 : listFiles) {
                String name = file3.getName();
                if (name.endsWith(".xml")) {
                    if (file2 != null && name.length() >= file2.getName().length()) {
                    }
                    file2 = file3;
                }
            }
            if (file2 == null) {
                return 0L;
            }
            return file2.lastModified();
        }

        /* JADX WARN: Removed duplicated region for block: B:105:0x023e  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x02f0  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x02f3  */
        /* JADX WARN: Removed duplicated region for block: B:53:0x02ca  */
        /* JADX WARN: Removed duplicated region for block: B:67:0x029c  */
        /* JADX WARN: Removed duplicated region for block: B:87:0x026c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static android.app.AppOpsManager.HistoricalOps readeHistoricalOpsDLocked(com.android.modules.utils.TypedXmlPullParser r35, int r36, java.lang.String r37, java.lang.String r38, java.lang.String[] r39, int r40, long r41, long r43, int r45, long[] r46) {
            /*
                Method dump skipped, instructions count: 791
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.appop.HistoricalRegistry.Persistence.readeHistoricalOpsDLocked(com.android.modules.utils.TypedXmlPullParser, int, java.lang.String, java.lang.String, java.lang.String[], int, long, long, int, long[]):android.app.AppOpsManager$HistoricalOps");
        }

        public static void writeHistoricalOpDLocked(AppOpsManager.HistoricalOps historicalOps, TypedXmlSerializer typedXmlSerializer) {
            String str;
            String str2;
            int i;
            int i2;
            AppOpsManager.HistoricalUidOps historicalUidOps;
            String str3;
            int i3;
            int i4;
            AppOpsManager.HistoricalPackageOps historicalPackageOps;
            String str4;
            int i5;
            AppOpsManager.HistoricalOp historicalOp;
            int i6;
            String str5 = null;
            String str6 = "ops";
            typedXmlSerializer.startTag((String) null, "ops");
            typedXmlSerializer.attributeLong((String) null, "beg", historicalOps.getBeginTimeMillis());
            typedXmlSerializer.attributeLong((String) null, "end", historicalOps.getEndTimeMillis());
            int uidCount = historicalOps.getUidCount();
            int i7 = 0;
            while (i7 < uidCount) {
                AppOpsManager.HistoricalUidOps uidOpsAt = historicalOps.getUidOpsAt(i7);
                String str7 = "uid";
                typedXmlSerializer.startTag(str5, "uid");
                typedXmlSerializer.attributeInt(str5, "na", uidOpsAt.getUid());
                int packageCount = uidOpsAt.getPackageCount();
                int i8 = 0;
                while (i8 < packageCount) {
                    AppOpsManager.HistoricalPackageOps packageOpsAt = uidOpsAt.getPackageOpsAt(i8);
                    String str8 = "pkg";
                    typedXmlSerializer.startTag(str5, "pkg");
                    typedXmlSerializer.attributeInterned(str5, "na", packageOpsAt.getPackageName());
                    int attributedOpsCount = packageOpsAt.getAttributedOpsCount();
                    int i9 = 0;
                    while (i9 < attributedOpsCount) {
                        AppOpsManager.AttributedHistoricalOps attributedOpsAt = packageOpsAt.getAttributedOpsAt(i9);
                        int i10 = uidCount;
                        String str9 = "ftr";
                        typedXmlSerializer.startTag(str5, "ftr");
                        XmlUtils.writeStringAttribute(typedXmlSerializer, "na", attributedOpsAt.getTag());
                        int opCount = attributedOpsAt.getOpCount();
                        int i11 = 0;
                        while (i11 < opCount) {
                            int i12 = opCount;
                            AppOpsManager.HistoricalOp opAt = attributedOpsAt.getOpAt(i11);
                            AppOpsManager.AttributedHistoricalOps attributedHistoricalOps = attributedOpsAt;
                            LongSparseArray collectKeys = opAt.collectKeys();
                            if (collectKeys == null || collectKeys.size() <= 0) {
                                str = str6;
                                str2 = str9;
                                i = i7;
                                i2 = i11;
                                historicalUidOps = uidOpsAt;
                                str3 = str7;
                                i3 = packageCount;
                                i4 = i8;
                                historicalPackageOps = packageOpsAt;
                                str4 = str8;
                                i5 = attributedOpsCount;
                            } else {
                                historicalUidOps = uidOpsAt;
                                i3 = packageCount;
                                typedXmlSerializer.startTag((String) null, "op");
                                historicalPackageOps = packageOpsAt;
                                typedXmlSerializer.attributeInt((String) null, "na", opAt.getOpCode());
                                int size = collectKeys.size();
                                int i13 = 0;
                                while (i13 < size) {
                                    String str10 = str7;
                                    int i14 = size;
                                    long keyAt = collectKeys.keyAt(i13);
                                    LongSparseArray longSparseArray = collectKeys;
                                    int extractUidStateFromKey = AppOpsManager.extractUidStateFromKey(keyAt);
                                    int i15 = attributedOpsCount;
                                    int extractFlagsFromKey = AppOpsManager.extractFlagsFromKey(keyAt);
                                    String str11 = str6;
                                    String str12 = str9;
                                    long accessCount = opAt.getAccessCount(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                                    int i16 = i7;
                                    int i17 = i11;
                                    long rejectCount = opAt.getRejectCount(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                                    String str13 = str8;
                                    long accessDuration = opAt.getAccessDuration(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                                    if (accessCount > 0 || rejectCount > 0 || accessDuration > 0) {
                                        historicalOp = opAt;
                                        i6 = i8;
                                        typedXmlSerializer.startTag((String) null, KnoxAnalyticsSystemService.STATUS_PARAMETER_NAME);
                                        typedXmlSerializer.attributeLong((String) null, "na", keyAt);
                                        if (accessCount > 0) {
                                            typedXmlSerializer.attributeLong((String) null, "ac", accessCount);
                                        }
                                        if (rejectCount > 0) {
                                            typedXmlSerializer.attributeLong((String) null, "rc", rejectCount);
                                        }
                                        if (accessDuration > 0) {
                                            typedXmlSerializer.attributeLong((String) null, "du", accessDuration);
                                        }
                                        typedXmlSerializer.endTag((String) null, KnoxAnalyticsSystemService.STATUS_PARAMETER_NAME);
                                    } else {
                                        historicalOp = opAt;
                                        i6 = i8;
                                    }
                                    i13++;
                                    str7 = str10;
                                    size = i14;
                                    collectKeys = longSparseArray;
                                    attributedOpsCount = i15;
                                    str6 = str11;
                                    str9 = str12;
                                    i7 = i16;
                                    i11 = i17;
                                    str8 = str13;
                                    opAt = historicalOp;
                                    i8 = i6;
                                }
                                str = str6;
                                str2 = str9;
                                i = i7;
                                i2 = i11;
                                str3 = str7;
                                i4 = i8;
                                str4 = str8;
                                i5 = attributedOpsCount;
                                typedXmlSerializer.endTag((String) null, "op");
                            }
                            i11 = i2 + 1;
                            opCount = i12;
                            attributedOpsAt = attributedHistoricalOps;
                            uidOpsAt = historicalUidOps;
                            packageCount = i3;
                            packageOpsAt = historicalPackageOps;
                            str7 = str3;
                            attributedOpsCount = i5;
                            str6 = str;
                            str9 = str2;
                            i7 = i;
                            str8 = str4;
                            i8 = i4;
                        }
                        typedXmlSerializer.endTag((String) null, str9);
                        i9++;
                        str5 = null;
                        uidCount = i10;
                        uidOpsAt = uidOpsAt;
                        packageCount = packageCount;
                        packageOpsAt = packageOpsAt;
                        str7 = str7;
                        attributedOpsCount = attributedOpsCount;
                        str6 = str6;
                        i7 = i7;
                        str8 = str8;
                        i8 = i8;
                    }
                    int i18 = i8;
                    String str14 = str5;
                    typedXmlSerializer.endTag(str14, str8);
                    uidCount = uidCount;
                    uidOpsAt = uidOpsAt;
                    packageCount = packageCount;
                    str7 = str7;
                    str6 = str6;
                    i7 = i7;
                    i8 = i18 + 1;
                    str5 = str14;
                }
                String str15 = str5;
                typedXmlSerializer.endTag(str15, str7);
                i7++;
                str5 = str15;
                uidCount = uidCount;
                str6 = str6;
            }
            typedXmlSerializer.endTag(str5, str6);
        }

        public final LinkedList collectHistoricalOpsBaseDLocked(int i, String str, String str2, String[] strArr, int i2, long j, long j2, int i3) {
            File file;
            AtomicDirectory atomicDirectory;
            File startRead;
            try {
                atomicDirectory = sHistoricalAppOpsDir;
                startRead = atomicDirectory.startRead();
            } catch (Throwable th) {
                th = th;
                file = null;
            }
            try {
                LinkedList collectHistoricalOpsRecursiveDLocked = collectHistoricalOpsRecursiveDLocked(startRead, i, str, str2, strArr, i2, j, j2, i3, new long[]{0}, 0, getHistoricalFileNames(startRead));
                atomicDirectory.finishRead();
                return collectHistoricalOpsRecursiveDLocked;
            } catch (Throwable th2) {
                th = th2;
                file = startRead;
                HistoricalRegistry.m245$$Nest$smwtf("Error reading historical app ops. Deleting history.", th, file);
                sHistoricalAppOpsDir.delete();
                return null;
            }
        }

        public final LinkedList collectHistoricalOpsRecursiveDLocked(File file, int i, String str, String str2, String[] strArr, int i2, long j, long j2, int i3, long[] jArr, int i4, Set set) {
            double d = this.mIntervalCompressionMultiplier;
            long pow = (long) Math.pow(d, i4);
            long j3 = this.mBaseSnapshotInterval;
            long j4 = pow * j3;
            int i5 = i4 + 1;
            long pow2 = j3 * ((long) Math.pow(d, i5));
            long max = Math.max(j - j4, 0L);
            long j5 = j2 - j4;
            List readHistoricalOpsLocked = readHistoricalOpsLocked(file, j4, pow2, i, str, str2, strArr, i2, max, j5, i3, jArr, i4, set);
            if (readHistoricalOpsLocked != null && readHistoricalOpsLocked.isEmpty()) {
                return null;
            }
            LinkedList collectHistoricalOpsRecursiveDLocked = collectHistoricalOpsRecursiveDLocked(file, i, str, str2, strArr, i2, max, j5, i3, jArr, i5, set);
            if (collectHistoricalOpsRecursiveDLocked != null) {
                int size = collectHistoricalOpsRecursiveDLocked.size();
                for (int i6 = 0; i6 < size; i6++) {
                    ((AppOpsManager.HistoricalOps) collectHistoricalOpsRecursiveDLocked.get(i6)).offsetBeginAndEndTime(pow2);
                }
            }
            if (readHistoricalOpsLocked != null) {
                if (collectHistoricalOpsRecursiveDLocked == null) {
                    collectHistoricalOpsRecursiveDLocked = new LinkedList();
                }
                for (int size2 = readHistoricalOpsLocked.size() - 1; size2 >= 0; size2--) {
                    collectHistoricalOpsRecursiveDLocked.offerFirst((AppOpsManager.HistoricalOps) readHistoricalOpsLocked.get(size2));
                }
            }
            return collectHistoricalOpsRecursiveDLocked;
        }

        public final File generateFile(File file, int i) {
            long j = 0;
            for (int i2 = 0; i2 < i + 1; i2++) {
                j = (long) (Math.pow(this.mIntervalCompressionMultiplier, i2) + j);
            }
            return new File(file, Long.toString(j * this.mBaseSnapshotInterval) + ".xml");
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x00ee  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x00fa  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x0104 A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handlePersistHistoricalOpsRecursiveDLocked(java.io.File r29, java.io.File r30, java.util.List r31, java.util.Set r32, int r33) {
            /*
                Method dump skipped, instructions count: 559
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.appop.HistoricalRegistry.Persistence.handlePersistHistoricalOpsRecursiveDLocked(java.io.File, java.io.File, java.util.List, java.util.Set, int):void");
        }

        public final void persistHistoricalOpsDLocked(List list) {
            try {
                AtomicDirectory atomicDirectory = sHistoricalAppOpsDir;
                File startWrite = atomicDirectory.startWrite();
                File backupDirectory = atomicDirectory.getBackupDirectory();
                handlePersistHistoricalOpsRecursiveDLocked(startWrite, backupDirectory, list, getHistoricalFileNames(backupDirectory), 0);
                atomicDirectory.finishWrite();
            } catch (Throwable th) {
                HistoricalRegistry.m245$$Nest$smwtf("Failed to write historical app ops, restoring backup", th, null);
                sHistoricalAppOpsDir.failWrite();
            }
        }

        public final List readHistoricalOpsLocked(File file, long j, long j2, int i, String str, String str2, String[] strArr, int i2, long j3, long j4, int i3, long[] jArr, int i4, Set set) {
            int i5;
            File generateFile = generateFile(file, i4);
            if (set != null) {
                set.remove(generateFile.getName());
            }
            if (j3 >= j4 || j4 < j) {
                return Collections.emptyList();
            }
            if (j3 >= ((j2 - j) / this.mIntervalCompressionMultiplier) + j2 + (jArr != null ? jArr[0] : 0L) || !generateFile.exists()) {
                if (set == null || set.isEmpty()) {
                    return Collections.emptyList();
                }
                return null;
            }
            try {
                FileInputStream fileInputStream = new FileInputStream(generateFile);
                try {
                    TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
                    XmlUtils.beginDocument(resolvePullParser, "history");
                    if (resolvePullParser.getAttributeInt((String) null, "ver") < 2) {
                        throw new IllegalStateException("Dropping unsupported history version 1 for file:" + generateFile);
                    }
                    long attributeLong = resolvePullParser.getAttributeLong((String) null, "ov", 0L);
                    int depth = resolvePullParser.getDepth();
                    ArrayList arrayList = null;
                    while (XmlUtils.nextElementWithin(resolvePullParser, depth)) {
                        if ("ops".equals(resolvePullParser.getName())) {
                            i5 = depth;
                            AppOpsManager.HistoricalOps readeHistoricalOpsDLocked = readeHistoricalOpsDLocked(resolvePullParser, i, str, str2, strArr, i2, j3, j4, i3, jArr);
                            if (readeHistoricalOpsDLocked != null) {
                                if (readeHistoricalOpsDLocked.isEmpty()) {
                                    XmlUtils.skipCurrentTag(resolvePullParser);
                                } else {
                                    ArrayList arrayList2 = arrayList == null ? new ArrayList() : arrayList;
                                    arrayList2.add(readeHistoricalOpsDLocked);
                                    arrayList = arrayList2;
                                }
                            }
                        } else {
                            i5 = depth;
                        }
                        depth = i5;
                    }
                    if (jArr != null) {
                        jArr[0] = jArr[0] + attributeLong;
                    }
                    fileInputStream.close();
                    return arrayList;
                } finally {
                }
            } catch (FileNotFoundException unused) {
                Slog.i("HistoricalRegistry$Persistence", "No history file: " + generateFile.getName());
                return Collections.emptyList();
            }
        }

        public final List readHistoryDLocked() {
            LinkedList collectHistoricalOpsBaseDLocked = collectHistoricalOpsBaseDLocked(-1, null, null, null, 0, 0L, Long.MAX_VALUE, 31);
            if (collectHistoricalOpsBaseDLocked != null) {
                int size = collectHistoricalOpsBaseDLocked.size();
                for (int i = 0; i < size; i++) {
                    ((AppOpsManager.HistoricalOps) collectHistoricalOpsBaseDLocked.get(i)).offsetBeginAndEndTime(this.mBaseSnapshotInterval);
                }
            }
            return collectHistoricalOpsBaseDLocked;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StringDumpVisitor implements AppOpsManager.HistoricalOpsVisitor {
        public final String mAttributionPrefix;
        public final String mEntryPrefix;
        public final int mFilter;
        public final int mFilterOp;
        public final String mFilterPackage;
        public final int mFilterUid;
        public final String mOpsPrefix;
        public final String mPackagePrefix;
        public final String mUidPrefix;
        public final String mUidStatePrefix;
        public final PrintWriter mWriter;
        public final long mNow = System.currentTimeMillis();
        public final SimpleDateFormat mDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        public final Date mDate = new Date();

        public StringDumpVisitor(int i, int i2, int i3, PrintWriter printWriter, String str, String str2) {
            String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "  ");
            this.mOpsPrefix = m$1;
            String m$12 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m$1, "  ");
            this.mUidPrefix = m$12;
            String m$13 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m$12, "  ");
            this.mPackagePrefix = m$13;
            String m$14 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m$13, "  ");
            this.mAttributionPrefix = m$14;
            String m$15 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m$14, "  ");
            this.mEntryPrefix = m$15;
            this.mUidStatePrefix = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m$15, "  ");
            this.mWriter = printWriter;
            this.mFilterUid = i;
            this.mFilterPackage = str2;
            this.mFilterOp = i2;
            this.mFilter = i3;
        }

        public final void visitHistoricalAttributionOps(AppOpsManager.AttributedHistoricalOps attributedHistoricalOps) {
            if ((this.mFilter & 4) == 0 || Objects.equals(this.mFilterPackage, attributedHistoricalOps.getTag())) {
                this.mWriter.print(this.mAttributionPrefix);
                this.mWriter.print("Attribution ");
                this.mWriter.print(attributedHistoricalOps.getTag());
                this.mWriter.println(":");
            }
        }

        public final void visitHistoricalOp(AppOpsManager.HistoricalOp historicalOp) {
            boolean z;
            if ((this.mFilter & 8) == 0 || this.mFilterOp == historicalOp.getOpCode()) {
                this.mWriter.print(this.mEntryPrefix);
                this.mWriter.print(AppOpsManager.opToName(historicalOp.getOpCode()));
                this.mWriter.println(":");
                LongSparseArray collectKeys = historicalOp.collectKeys();
                int size = collectKeys.size();
                for (int i = 0; i < size; i++) {
                    long keyAt = collectKeys.keyAt(i);
                    int extractUidStateFromKey = AppOpsManager.extractUidStateFromKey(keyAt);
                    int extractFlagsFromKey = AppOpsManager.extractFlagsFromKey(keyAt);
                    long accessCount = historicalOp.getAccessCount(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                    boolean z2 = true;
                    if (accessCount > 0) {
                        this.mWriter.print(this.mUidStatePrefix);
                        this.mWriter.print(AppOpsManager.keyToString(keyAt));
                        this.mWriter.print(" = ");
                        this.mWriter.print("access=");
                        this.mWriter.print(accessCount);
                        z = true;
                    } else {
                        z = false;
                    }
                    long rejectCount = historicalOp.getRejectCount(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                    if (rejectCount > 0) {
                        if (z) {
                            this.mWriter.print(", ");
                        } else {
                            this.mWriter.print(this.mUidStatePrefix);
                            this.mWriter.print(AppOpsManager.keyToString(keyAt));
                            this.mWriter.print(" = ");
                            z = true;
                        }
                        this.mWriter.print("reject=");
                        this.mWriter.print(rejectCount);
                    }
                    long accessDuration = historicalOp.getAccessDuration(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                    if (accessDuration > 0) {
                        if (z) {
                            this.mWriter.print(", ");
                            z2 = z;
                        } else {
                            this.mWriter.print(this.mUidStatePrefix);
                            this.mWriter.print(AppOpsManager.keyToString(keyAt));
                            this.mWriter.print(" = ");
                        }
                        this.mWriter.print("duration=");
                        TimeUtils.formatDuration(accessDuration, this.mWriter);
                        z = z2;
                    }
                    if (z) {
                        this.mWriter.println("");
                    }
                }
            }
        }

        public final void visitHistoricalOps(AppOpsManager.HistoricalOps historicalOps) {
            this.mWriter.println();
            this.mWriter.print(this.mOpsPrefix);
            this.mWriter.println("snapshot:");
            this.mWriter.print(this.mUidPrefix);
            this.mWriter.print("begin = ");
            this.mDate.setTime(historicalOps.getBeginTimeMillis());
            this.mWriter.print(this.mDateFormatter.format(this.mDate));
            this.mWriter.print("  (");
            TimeUtils.formatDuration(historicalOps.getBeginTimeMillis() - this.mNow, this.mWriter);
            this.mWriter.println(")");
            this.mWriter.print(this.mUidPrefix);
            this.mWriter.print("end = ");
            this.mDate.setTime(historicalOps.getEndTimeMillis());
            this.mWriter.print(this.mDateFormatter.format(this.mDate));
            this.mWriter.print("  (");
            TimeUtils.formatDuration(historicalOps.getEndTimeMillis() - this.mNow, this.mWriter);
            this.mWriter.println(")");
        }

        public final void visitHistoricalPackageOps(AppOpsManager.HistoricalPackageOps historicalPackageOps) {
            if ((this.mFilter & 2) == 0 || this.mFilterPackage.equals(historicalPackageOps.getPackageName())) {
                this.mWriter.print(this.mPackagePrefix);
                this.mWriter.print("Package ");
                this.mWriter.print(historicalPackageOps.getPackageName());
                this.mWriter.println(":");
            }
        }

        public final void visitHistoricalUidOps(AppOpsManager.HistoricalUidOps historicalUidOps) {
            if ((this.mFilter & 1) == 0 || this.mFilterUid == historicalUidOps.getUid()) {
                this.mWriter.println();
                this.mWriter.print(this.mUidPrefix);
                this.mWriter.print("Uid ");
                UserHandle.formatUid(this.mWriter, historicalUidOps.getUid());
                this.mWriter.println(":");
            }
        }
    }

    /* renamed from: -$$Nest$smwtf, reason: not valid java name */
    public static void m245$$Nest$smwtf(String str, Throwable th, File file) {
        Slog.wtf(LOG_TAG, str, th);
        if (KEEP_WTF_LOG) {
            try {
                File file2 = new File(new File(Environment.getDataSystemDirectory(), "appops"), "wtf" + TimeUtils.formatForLogging(System.currentTimeMillis()));
                if (file2.createNewFile()) {
                    PrintWriter printWriter = new PrintWriter(file2);
                    try {
                        printWriter.append('\n').append((CharSequence) th.toString());
                        printWriter.append('\n').append((CharSequence) Debug.getCallers(10));
                        if (file != null) {
                            printWriter.append((CharSequence) ("\nfiles: " + Arrays.toString(file.listFiles())));
                        } else {
                            printWriter.append((CharSequence) "\nfiles: none");
                        }
                        printWriter.close();
                    } finally {
                    }
                }
            } catch (IOException unused) {
            }
        }
    }

    public HistoricalRegistry(Object obj) {
        this.mInMemoryLock = obj;
        this.mDiscreteRegistry = new DiscreteRegistry(obj);
    }

    public final void clearHistoricalRegistry() {
        synchronized (this.mOnDiskLock) {
            synchronized (this.mInMemoryLock) {
                if (!isPersistenceInitializedMLocked()) {
                    Slog.d(LOG_TAG, "Interaction before persistence initialized");
                    return;
                }
                clearHistoryOnDiskDLocked();
                this.mNextPersistDueTimeMillis = 0L;
                this.mPendingHistoryOffsetMillis = 0L;
                this.mCurrentHistoricalOps = null;
            }
        }
    }

    public final void clearHistoryOnDiskDLocked() {
        IoThread.getHandler().removeMessages(1);
        synchronized (this.mInMemoryLock) {
            this.mCurrentHistoricalOps = null;
            this.mNextPersistDueTimeMillis = System.currentTimeMillis();
            this.mPendingWrites.clear();
        }
        Persistence.clearHistoryDLocked$1();
    }

    public final void dump(PrintWriter printWriter, int i, String str, int i2, int i3) {
        synchronized (this.mOnDiskLock) {
            synchronized (this.mInMemoryLock) {
                printWriter.println();
                printWriter.print("  ");
                printWriter.print("History:");
                printWriter.print("  mode=");
                printWriter.println(AppOpsManager.historicalModeToString(this.mMode));
                StringDumpVisitor stringDumpVisitor = new StringDumpVisitor(i, i2, i3, printWriter, "    ", str);
                long currentTimeMillis = System.currentTimeMillis();
                AppOpsManager.HistoricalOps updatedPendingHistoricalOpsMLocked = getUpdatedPendingHistoricalOpsMLocked(currentTimeMillis);
                updatedPendingHistoricalOpsMLocked.setBeginAndEndTime(currentTimeMillis - updatedPendingHistoricalOpsMLocked.getEndTimeMillis(), currentTimeMillis - updatedPendingHistoricalOpsMLocked.getBeginTimeMillis());
                updatedPendingHistoricalOpsMLocked.accept(stringDumpVisitor);
                if (!isPersistenceInitializedMLocked()) {
                    Slog.e(LOG_TAG, "Interaction before persistence initialized");
                    return;
                }
                List readHistoryDLocked = this.mPersistence.readHistoryDLocked();
                if (readHistoryDLocked != null) {
                    long j = (this.mNextPersistDueTimeMillis - currentTimeMillis) - this.mBaseSnapshotInterval;
                    int size = readHistoryDLocked.size();
                    for (int i4 = 0; i4 < size; i4++) {
                        AppOpsManager.HistoricalOps historicalOps = (AppOpsManager.HistoricalOps) readHistoryDLocked.get(i4);
                        historicalOps.offsetBeginAndEndTime(j);
                        historicalOps.setBeginAndEndTime(currentTimeMillis - historicalOps.getEndTimeMillis(), currentTimeMillis - historicalOps.getBeginTimeMillis());
                        historicalOps.accept(stringDumpVisitor);
                    }
                } else {
                    printWriter.println("  Empty");
                }
            }
        }
    }

    public final void dumpDiscreteData(PrintWriter printWriter, int i, String str, String str2, int i2, int i3, SimpleDateFormat simpleDateFormat, Date date, int i4) {
        int i5 = i4;
        DiscreteRegistry discreteRegistry = this.mDiscreteRegistry;
        DiscreteRegistry.DiscreteOps allDiscreteOps = discreteRegistry.getAllDiscreteOps();
        DiscreteRegistry.DiscreteOps.m241$$Nest$mfilter(allDiscreteOps, 0L, Instant.now().toEpochMilli(), i2, i, str, i3 == -1 ? null : new String[]{AppOpsManager.opToPublicName(i3)}, str2, 31, new ArrayMap());
        String str3 = "  ";
        printWriter.print("  ");
        printWriter.print("Largest chain id: ");
        printWriter.print(discreteRegistry.mDiscreteOps.mLargestChainId);
        printWriter.println();
        int size = allDiscreteOps.mUids.size();
        int i6 = 0;
        while (i6 < size) {
            printWriter.print(str3);
            printWriter.print("Uid: ");
            printWriter.print(allDiscreteOps.mUids.keyAt(i6));
            printWriter.println();
            DiscreteRegistry.DiscreteUidOps discreteUidOps = (DiscreteRegistry.DiscreteUidOps) allDiscreteOps.mUids.valueAt(i6);
            int size2 = discreteUidOps.mPackages.size();
            int i7 = 0;
            while (i7 < size2) {
                printWriter.print("    ");
                printWriter.print("Package: ");
                printWriter.print((String) discreteUidOps.mPackages.keyAt(i7));
                printWriter.println();
                DiscreteRegistry.DiscretePackageOps discretePackageOps = (DiscreteRegistry.DiscretePackageOps) discreteUidOps.mPackages.valueAt(i7);
                int size3 = discretePackageOps.mPackageOps.size();
                int i8 = 0;
                while (i8 < size3) {
                    printWriter.print("      ");
                    printWriter.print(AppOpsManager.opToName(((Integer) discretePackageOps.mPackageOps.keyAt(i8)).intValue()));
                    printWriter.println();
                    DiscreteRegistry.DiscreteOp discreteOp = (DiscreteRegistry.DiscreteOp) discretePackageOps.mPackageOps.valueAt(i8);
                    int size4 = discreteOp.mAttributedOps.size();
                    int i9 = 0;
                    while (i9 < size4) {
                        printWriter.print("        ");
                        int i10 = size;
                        printWriter.print("Attribution: ");
                        printWriter.print((String) discreteOp.mAttributedOps.keyAt(i9));
                        printWriter.println();
                        List list = (List) discreteOp.mAttributedOps.valueAt(i9);
                        DiscreteRegistry.DiscreteUidOps discreteUidOps2 = discreteUidOps;
                        int size5 = list.size();
                        int i11 = size2;
                        int max = i5 < 1 ? 0 : Math.max(0, size5 - i5);
                        while (max < size5) {
                            DiscreteRegistry.DiscreteOpEvent discreteOpEvent = (DiscreteRegistry.DiscreteOpEvent) list.get(max);
                            List list2 = list;
                            discreteOpEvent.getClass();
                            printWriter.print("        " + str3);
                            printWriter.print("Access [");
                            printWriter.print(AppOpsManager.getUidStateName(discreteOpEvent.mUidState));
                            printWriter.print(PackageManagerShellCommandDataLoader.STDIN_PATH);
                            printWriter.print(AppOpsManager.flagsToString(discreteOpEvent.mOpFlag));
                            printWriter.print("] at ");
                            String str4 = str3;
                            int i12 = size5;
                            long j = discreteOpEvent.mNoteTime;
                            long j2 = DiscreteRegistry.sDiscreteHistoryQuantization;
                            date.setTime((j / j2) * j2);
                            printWriter.print(simpleDateFormat.format(date));
                            long j3 = discreteOpEvent.mNoteDuration;
                            if (j3 != -1) {
                                printWriter.print(" for ");
                                printWriter.print(DiscreteRegistry.m239$$Nest$smdiscretizeDuration(j3));
                                printWriter.print(" milliseconds ");
                            }
                            int i13 = discreteOpEvent.mAttributionFlags;
                            if (i13 != 0) {
                                printWriter.print(" attribution flags=");
                                printWriter.print(i13);
                                printWriter.print(" with chainId=");
                                printWriter.print(discreteOpEvent.mAttributionChainId);
                            }
                            printWriter.println();
                            max++;
                            list = list2;
                            str3 = str4;
                            size5 = i12;
                        }
                        i9++;
                        size = i10;
                        discreteUidOps = discreteUidOps2;
                        size2 = i11;
                        i5 = i4;
                    }
                    i8++;
                    i5 = i4;
                }
                i7++;
                i5 = i4;
            }
            i6++;
            i5 = i4;
        }
    }

    public final AppOpsManager.HistoricalOps getUpdatedPendingHistoricalOpsMLocked(long j) {
        AppOpsManager.HistoricalOps historicalOps = this.mCurrentHistoricalOps;
        if (historicalOps != null) {
            long j2 = this.mNextPersistDueTimeMillis - j;
            long j3 = this.mBaseSnapshotInterval;
            if (j2 > j3) {
                this.mPendingHistoryOffsetMillis = j2 - j3;
            }
            historicalOps.setEndTime(j3 - j2);
            if (j2 > 0) {
                return this.mCurrentHistoricalOps;
            }
            if (this.mCurrentHistoricalOps.isEmpty()) {
                this.mCurrentHistoricalOps.setBeginAndEndTime(0L, 0L);
                this.mNextPersistDueTimeMillis = j + this.mBaseSnapshotInterval;
                return this.mCurrentHistoricalOps;
            }
            this.mCurrentHistoricalOps.offsetBeginAndEndTime(this.mBaseSnapshotInterval);
            AppOpsManager.HistoricalOps historicalOps2 = this.mCurrentHistoricalOps;
            historicalOps2.setBeginTime(historicalOps2.getEndTimeMillis() - this.mBaseSnapshotInterval);
            this.mCurrentHistoricalOps.offsetBeginAndEndTime(Math.abs(j2));
            AppOpsManager.HistoricalOps historicalOps3 = this.mCurrentHistoricalOps;
            Message obtainMessage = PooledLambda.obtainMessage(new HistoricalRegistry$$ExternalSyntheticLambda0(), this);
            obtainMessage.what = 1;
            IoThread.getHandler().sendMessage(obtainMessage);
            this.mPendingWrites.offerFirst(historicalOps3);
        }
        AppOpsManager.HistoricalOps historicalOps4 = new AppOpsManager.HistoricalOps(0L, 0L);
        this.mCurrentHistoricalOps = historicalOps4;
        this.mNextPersistDueTimeMillis = j + this.mBaseSnapshotInterval;
        return historicalOps4;
    }

    public final void incrementOpAccessedCount(int i, int i2, String str, String str2, int i3, int i4, long j, int i5, int i6) {
        synchronized (this.mInMemoryLock) {
            try {
                if (this.mMode == 1) {
                    if (!isPersistenceInitializedMLocked()) {
                        Slog.v(LOG_TAG, "Interaction before persistence initialized");
                    } else {
                        getUpdatedPendingHistoricalOpsMLocked(System.currentTimeMillis()).increaseAccessCount(i, i2, str, str2, i3, i4, 1L);
                        this.mDiscreteRegistry.recordDiscreteAccess(i2, i, i4, i3, i5, i6, j, -1L, str, str2);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isPersistenceInitializedMLocked() {
        return this.mPersistence != null;
    }

    public final void offsetHistory(long j) {
        synchronized (this.mOnDiskLock) {
            synchronized (this.mInMemoryLock) {
                if (!isPersistenceInitializedMLocked()) {
                    Slog.e(LOG_TAG, "Interaction before persistence initialized");
                    return;
                }
                List readHistoryDLocked = this.mPersistence.readHistoryDLocked();
                clearHistoricalRegistry();
                if (readHistoryDLocked != null) {
                    int size = readHistoryDLocked.size();
                    for (int i = 0; i < size; i++) {
                        ((AppOpsManager.HistoricalOps) readHistoryDLocked.get(i)).offsetBeginAndEndTime(j);
                    }
                    if (j < 0) {
                        for (int size2 = readHistoryDLocked.size() - 1; size2 >= 0; size2--) {
                            AppOpsManager.HistoricalOps historicalOps = (AppOpsManager.HistoricalOps) readHistoryDLocked.get(size2);
                            if (historicalOps.getEndTimeMillis() <= this.mBaseSnapshotInterval) {
                                readHistoryDLocked.remove(size2);
                            } else if (historicalOps.getBeginTimeMillis() < this.mBaseSnapshotInterval) {
                                AtomicDirectory atomicDirectory = Persistence.sHistoricalAppOpsDir;
                                historicalOps.spliceFromBeginning((historicalOps.getEndTimeMillis() - this.mBaseSnapshotInterval) / historicalOps.getDurationMillis());
                            }
                        }
                    }
                    this.mPersistence.persistHistoricalOpsDLocked(readHistoryDLocked);
                }
            }
        }
    }

    public final void persistPendingHistory() {
        ArrayList arrayList;
        synchronized (this.mOnDiskLock) {
            synchronized (this.mInMemoryLock) {
                try {
                    arrayList = new ArrayList(this.mPendingWrites);
                    this.mPendingWrites.clear();
                    long j = this.mPendingHistoryOffsetMillis;
                    if (j != 0) {
                        this.mPersistence = new Persistence(this.mBaseSnapshotInterval, this.mIntervalCompressionMultiplier);
                        offsetHistory(j);
                        this.mPendingHistoryOffsetMillis = 0L;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            persistPendingHistory(arrayList);
        }
        this.mDiscreteRegistry.writeAndClearAccessHistory();
    }

    public final void persistPendingHistory(List list) {
        synchronized (this.mOnDiskLock) {
            try {
                IoThread.getHandler().removeMessages(1);
                ArrayList arrayList = (ArrayList) list;
                if (arrayList.isEmpty()) {
                    return;
                }
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    AppOpsManager.HistoricalOps historicalOps = (AppOpsManager.HistoricalOps) arrayList.get(i);
                    if (i > 0) {
                        historicalOps.offsetBeginAndEndTime(((AppOpsManager.HistoricalOps) arrayList.get(i - 1)).getBeginTimeMillis());
                    }
                }
                this.mPersistence.persistHistoricalOpsDLocked(list);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setHistoryParameters(int i, long j, long j2) {
        boolean z;
        synchronized (this.mOnDiskLock) {
            synchronized (this.mInMemoryLock) {
                try {
                    Slog.i(LOG_TAG, "New history parameters: mode:" + AppOpsManager.historicalModeToString(i) + " baseSnapshotInterval:" + j + " intervalCompressionMultiplier:" + j2);
                    boolean z2 = true;
                    if (this.mMode != i) {
                        this.mMode = i;
                        if (i == 0) {
                            clearHistoryOnDiskDLocked();
                        }
                        if (this.mMode == 2) {
                            this.mDiscreteRegistry.mDebugMode = true;
                        }
                    }
                    if (this.mBaseSnapshotInterval != j) {
                        this.mBaseSnapshotInterval = j;
                        z = true;
                    } else {
                        z = false;
                    }
                    if (this.mIntervalCompressionMultiplier != j2) {
                        this.mIntervalCompressionMultiplier = j2;
                    } else {
                        z2 = z;
                    }
                    if (z2) {
                        this.mPersistence = new Persistence(this.mBaseSnapshotInterval, this.mIntervalCompressionMultiplier);
                        offsetHistory(0L);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void systemReady(final ContentResolver contentResolver) {
        final DiscreteRegistry discreteRegistry = this.mDiscreteRegistry;
        discreteRegistry.getClass();
        DeviceConfig.addOnPropertiesChangedListener("privacy", AsyncTask.THREAD_POOL_EXECUTOR, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.appop.DiscreteRegistry$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                DiscreteRegistry.this.setDiscreteHistoryParameters(properties);
            }
        });
        discreteRegistry.setDiscreteHistoryParameters(DeviceConfig.getProperties("privacy", new String[0]));
        contentResolver.registerContentObserver(Settings.Global.getUriFor("appop_history_parameters"), false, new ContentObserver(FgThread.getHandler()) { // from class: com.android.server.appop.HistoricalRegistry.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                HistoricalRegistry.this.updateParametersFromSetting(contentResolver);
            }
        });
        updateParametersFromSetting(contentResolver);
        synchronized (this.mOnDiskLock) {
            synchronized (this.mInMemoryLock) {
                try {
                    if (this.mMode != 0) {
                        if (!isPersistenceInitializedMLocked()) {
                            this.mPersistence = new Persistence(this.mBaseSnapshotInterval, this.mIntervalCompressionMultiplier);
                        }
                        this.mPersistence.getClass();
                        long lastPersistTimeMillisDLocked = Persistence.getLastPersistTimeMillisDLocked();
                        if (lastPersistTimeMillisDLocked > 0) {
                            this.mPendingHistoryOffsetMillis = System.currentTimeMillis() - lastPersistTimeMillisDLocked;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void updateParametersFromSetting(ContentResolver contentResolver) {
        String string = Settings.Global.getString(contentResolver, "appop_history_parameters");
        if (string == null) {
            return;
        }
        String[] split = string.split(",");
        int length = split.length;
        String str = null;
        int i = 0;
        String str2 = null;
        String str3 = null;
        while (true) {
            String str4 = LOG_TAG;
            if (i >= length) {
                if (str != null && str2 != null && str3 != null) {
                    try {
                        setHistoryParameters(AppOpsManager.parseHistoricalMode(str), Long.parseLong(str2), Integer.parseInt(str3));
                        return;
                    } catch (NumberFormatException unused) {
                    }
                }
                PinnerService$$ExternalSyntheticOutline0.m("Bad value forappop_history_parameters=", string, " resetting!", str4);
                return;
            }
            String str5 = split[i];
            String[] split2 = str5.split("=");
            if (split2.length == 2) {
                String trim = split2[0].trim();
                trim.getClass();
                switch (trim) {
                    case "intervalMultiplier":
                        str3 = split2[1].trim();
                        break;
                    case "mode":
                        str = split2[1].trim();
                        break;
                    case "baseIntervalMillis":
                        str2 = split2[1].trim();
                        break;
                    default:
                        Slog.w(str4, "Unknown parameter: ".concat(str5));
                        break;
                }
            }
            i++;
        }
    }
}
