package com.android.server.usage;

import android.app.usage.UsageStatsManager;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseLongArray;
import android.util.TimeUtils;
import android.util.Xml;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.CollectionUtils;
import com.android.internal.util.jobs.FastXmlSerializer;
import com.android.internal.util.jobs.XmlUtils;

import libcore.io.IoUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppIdleHistory {
    static final String APP_IDLE_FILENAME = "app_idle_stats.xml";
    public long mElapsedDuration;
    public long mElapsedSnapshot;
    public final SparseArray mIdleHistory = new SparseArray();
    public boolean mScreenOn;
    public long mScreenOnDuration;
    public long mScreenOnSnapshot;
    public final File mStorageDir;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppUsageHistory {
        public SparseLongArray bucketExpiryTimesMs;
        public int bucketingReason;
        public int currentBucket;
        public int lastInformedBucket;
        public long lastJobRunTime;
        public int lastPredictedBucket = -1;
        public long lastPredictedTime;
        public long lastRestrictAttemptElapsedTime;
        public int lastRestrictReason;
        public long lastUsedByUserElapsedTime;
        public long lastUsedElapsedTime;
        public long lastUsedScreenTime;
        public long nextEstimatedLaunchTime;
    }

    public AppIdleHistory(File file, long j) {
        this.mElapsedSnapshot = j;
        this.mScreenOnSnapshot = j;
        this.mStorageDir = file;
        File screenOnTimeFile = getScreenOnTimeFile();
        if (!screenOnTimeFile.exists()) {
            writeScreenOnTime();
            return;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(screenOnTimeFile));
            this.mScreenOnDuration = Long.parseLong(bufferedReader.readLine());
            this.mElapsedDuration = Long.parseLong(bufferedReader.readLine());
            bufferedReader.close();
        } catch (IOException | NumberFormatException unused) {
        }
    }

    public static AppUsageHistory getPackageHistory(ArrayMap arrayMap, String str, boolean z) {
        AppUsageHistory appUsageHistory = (AppUsageHistory) arrayMap.get(str);
        if (appUsageHistory != null || !z) {
            return appUsageHistory;
        }
        AppUsageHistory appUsageHistory2 = new AppUsageHistory();
        appUsageHistory2.lastUsedByUserElapsedTime = -2147483648L;
        appUsageHistory2.lastUsedElapsedTime = -2147483648L;
        appUsageHistory2.lastUsedScreenTime = -2147483648L;
        appUsageHistory2.lastPredictedTime = -2147483648L;
        appUsageHistory2.currentBucket = 50;
        appUsageHistory2.bucketingReason = 256;
        appUsageHistory2.lastInformedBucket = -1;
        appUsageHistory2.lastJobRunTime = Long.MIN_VALUE;
        arrayMap.put(str, appUsageHistory2);
        return appUsageHistory2;
    }

    public static void insertBucketExpiryTime(AppUsageHistory appUsageHistory, int i, long j) {
        if (j == 0) {
            return;
        }
        if (appUsageHistory.bucketExpiryTimesMs == null) {
            appUsageHistory.bucketExpiryTimesMs = new SparseLongArray();
        }
        appUsageHistory.bucketExpiryTimesMs.put(i, j);
    }

    public static void printLastActionElapsedTime(
            IndentingPrintWriter indentingPrintWriter, long j, long j2) {
        if (j2 < 0) {
            indentingPrintWriter.print("<uninitialized>");
        } else {
            TimeUtils.formatDuration(j - j2, indentingPrintWriter);
        }
    }

    public static void readBucketExpiryTimes(
            XmlPullParser xmlPullParser, AppUsageHistory appUsageHistory) {
        int depth = xmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            if ("item".equals(xmlPullParser.getName())) {
                String attributeValue = xmlPullParser.getAttributeValue(null, "bucket");
                int parseInt = attributeValue == null ? -1 : Integer.parseInt(attributeValue);
                if (parseInt == -1) {
                    Slog.e("AppIdleHistory", "Error reading the buckets expiry times");
                } else {
                    String attributeValue2 = xmlPullParser.getAttributeValue(null, "expiry");
                    insertBucketExpiryTime(
                            appUsageHistory,
                            parseInt,
                            attributeValue2 == null ? 0L : Long.parseLong(attributeValue2));
                }
            }
        }
    }

    public final void dumpUsers(IndentingPrintWriter indentingPrintWriter, int[] iArr, List list) {
        int i;
        int i2;
        ArrayMap arrayMap;
        int i3;
        int i4;
        String str;
        int[] iArr2 = iArr;
        int length = iArr2.length;
        int i5 = 0;
        while (i5 < length) {
            indentingPrintWriter.println();
            int i6 = iArr2[i5];
            indentingPrintWriter.print("User ");
            indentingPrintWriter.print(i6);
            indentingPrintWriter.println(" App Standby States:");
            indentingPrintWriter.increaseIndent();
            ArrayMap arrayMap2 = (ArrayMap) this.mIdleHistory.get(i6);
            long currentTimeMillis = System.currentTimeMillis();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long elapsedTime = getElapsedTime(elapsedRealtime);
            if (arrayMap2 == null) {
                i = length;
            } else {
                int size = arrayMap2.size();
                int i7 = 0;
                while (i7 < size) {
                    String str2 = (String) arrayMap2.keyAt(i7);
                    AppUsageHistory appUsageHistory = (AppUsageHistory) arrayMap2.valueAt(i7);
                    if (CollectionUtils.isEmpty(list)) {
                        i2 = length;
                    } else {
                        i2 = length;
                        if (!list.contains(str2)) {
                            arrayMap = arrayMap2;
                            i4 = size;
                            i3 = i7;
                            i7 = i3 + 1;
                            length = i2;
                            arrayMap2 = arrayMap;
                            size = i4;
                        }
                    }
                    arrayMap = arrayMap2;
                    indentingPrintWriter.print("package=" + str2);
                    indentingPrintWriter.print(" u=" + i6);
                    indentingPrintWriter.print(
                            " bucket="
                                    + appUsageHistory.currentBucket
                                    + " reason="
                                    + UsageStatsManager.reasonToString(
                                            appUsageHistory.bucketingReason));
                    indentingPrintWriter.print(" used=");
                    int i8 = size;
                    i3 = i7;
                    printLastActionElapsedTime(
                            indentingPrintWriter, elapsedTime, appUsageHistory.lastUsedElapsedTime);
                    indentingPrintWriter.print(" usedByUser=");
                    printLastActionElapsedTime(
                            indentingPrintWriter,
                            elapsedTime,
                            appUsageHistory.lastUsedByUserElapsedTime);
                    indentingPrintWriter.print(" usedScr=");
                    printLastActionElapsedTime(
                            indentingPrintWriter, elapsedTime, appUsageHistory.lastUsedScreenTime);
                    indentingPrintWriter.print(" lastPred=");
                    printLastActionElapsedTime(
                            indentingPrintWriter, elapsedTime, appUsageHistory.lastPredictedTime);
                    indentingPrintWriter.print(" expiryTimes=");
                    SparseLongArray sparseLongArray = appUsageHistory.bucketExpiryTimesMs;
                    if (sparseLongArray == null || sparseLongArray.size() == 0) {
                        i4 = i8;
                        str = str2;
                        indentingPrintWriter.print("<none>");
                    } else {
                        indentingPrintWriter.print("(");
                        int size2 = appUsageHistory.bucketExpiryTimesMs.size();
                        int i9 = 0;
                        while (i9 < size2) {
                            int i10 = i8;
                            int keyAt = appUsageHistory.bucketExpiryTimesMs.keyAt(i9);
                            int i11 = size2;
                            long valueAt = appUsageHistory.bucketExpiryTimesMs.valueAt(i9);
                            if (i9 != 0) {
                                indentingPrintWriter.print(",");
                            }
                            indentingPrintWriter.print(keyAt + ":");
                            TimeUtils.formatDuration(elapsedTime - valueAt, indentingPrintWriter);
                            i9++;
                            str2 = str2;
                            i8 = i10;
                            size2 = i11;
                        }
                        i4 = i8;
                        str = str2;
                        indentingPrintWriter.print(")");
                    }
                    indentingPrintWriter.print(" lastJob=");
                    TimeUtils.formatDuration(
                            elapsedTime - appUsageHistory.lastJobRunTime, indentingPrintWriter);
                    indentingPrintWriter.print(
                            " lastInformedBucket=" + appUsageHistory.lastInformedBucket);
                    if (appUsageHistory.lastRestrictAttemptElapsedTime > 0) {
                        indentingPrintWriter.print(" lastRestrictAttempt=");
                        TimeUtils.formatDuration(
                                elapsedTime - appUsageHistory.lastRestrictAttemptElapsedTime,
                                indentingPrintWriter);
                        indentingPrintWriter.print(
                                " lastRestrictReason="
                                        + UsageStatsManager.reasonToString(
                                                appUsageHistory.lastRestrictReason));
                    }
                    if (appUsageHistory.nextEstimatedLaunchTime > 0) {
                        indentingPrintWriter.print(" nextEstimatedLaunchTime=");
                        TimeUtils.formatDuration(
                                appUsageHistory.nextEstimatedLaunchTime - currentTimeMillis,
                                indentingPrintWriter);
                    }
                    indentingPrintWriter.print(
                            " idle=".concat(isIdle(i6, str, elapsedRealtime) ? "y" : "n"));
                    indentingPrintWriter.println();
                    i7 = i3 + 1;
                    length = i2;
                    arrayMap2 = arrayMap;
                    size = i4;
                }
                i = length;
                indentingPrintWriter.println();
                indentingPrintWriter.print("totalElapsedTime=");
                TimeUtils.formatDuration(getElapsedTime(elapsedRealtime), indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.print("totalScreenOnTime=");
                long j = this.mScreenOnDuration;
                if (this.mScreenOn) {
                    j += elapsedRealtime - this.mScreenOnSnapshot;
                }
                TimeUtils.formatDuration(j, indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
            }
            i5++;
            iArr2 = iArr;
            length = i;
        }
    }

    public final AppUsageHistory getAppUsageHistory(int i, String str, long j) {
        return getPackageHistory(getUserHistory(i), str, true);
    }

    public long getBucketExpiryTimeMs(String str, int i, int i2, long j) {
        SparseLongArray sparseLongArray;
        AppUsageHistory packageHistory = getPackageHistory(getUserHistory(i), str, false);
        if (packageHistory == null
                || (sparseLongArray = packageHistory.bucketExpiryTimesMs) == null) {
            return 0L;
        }
        return sparseLongArray.get(i2, 0L);
    }

    public final long getElapsedTime(long j) {
        return (j - this.mElapsedSnapshot) + this.mElapsedDuration;
    }

    public File getScreenOnTimeFile() {
        return new File(this.mStorageDir, "screen_on_time");
    }

    public File getUserFile(int i) {
        return new File(
                new File(new File(this.mStorageDir, "users"), Integer.toString(i)),
                APP_IDLE_FILENAME);
    }

    public final ArrayMap getUserHistory(int i) {
        FileInputStream openRead;
        int next;
        int i2;
        int i3;
        ArrayMap arrayMap = (ArrayMap) this.mIdleHistory.get(i);
        if (arrayMap == null) {
            arrayMap = new ArrayMap();
            this.mIdleHistory.put(i, arrayMap);
            FileInputStream fileInputStream = null;
            try {
                try {
                    openRead = new AtomicFile(getUserFile(i)).openRead();
                } catch (FileNotFoundException unused) {
                } catch (IOException | NumberFormatException | XmlPullParserException e) {
                    e = e;
                }
                try {
                    try {
                        XmlPullParser newPullParser = Xml.newPullParser();
                        newPullParser.setInput(openRead, StandardCharsets.UTF_8.name());
                        do {
                            next = newPullParser.next();
                            i2 = 1;
                            i3 = 2;
                            if (next == 2) {
                                break;
                            }
                        } while (next != 1);
                        if (next != 2) {
                            Slog.e("AppIdleHistory", "Unable to read app idle file for user " + i);
                        } else if (newPullParser.getName().equals("packages")) {
                            String attributeValue =
                                    newPullParser.getAttributeValue(null, "version");
                            int parseInt =
                                    attributeValue == null ? 0 : Integer.parseInt(attributeValue);
                            while (true) {
                                int next2 = newPullParser.next();
                                if (next2 == i2) {
                                    break;
                                }
                                if (next2 == i3) {
                                    if (newPullParser.getName().equals("package")) {
                                        String attributeValue2 =
                                                newPullParser.getAttributeValue(null, "name");
                                        AppUsageHistory appUsageHistory = new AppUsageHistory();
                                        long parseLong =
                                                Long.parseLong(
                                                        newPullParser.getAttributeValue(
                                                                null, "elapsedIdleTime"));
                                        appUsageHistory.lastUsedElapsedTime = parseLong;
                                        String attributeValue3 =
                                                newPullParser.getAttributeValue(
                                                        null, "lastUsedByUserElapsedTime");
                                        if (attributeValue3 != null) {
                                            parseLong = Long.parseLong(attributeValue3);
                                        }
                                        appUsageHistory.lastUsedByUserElapsedTime = parseLong;
                                        appUsageHistory.lastUsedScreenTime =
                                                Long.parseLong(
                                                        newPullParser.getAttributeValue(
                                                                null, "screenIdleTime"));
                                        String attributeValue4 =
                                                newPullParser.getAttributeValue(
                                                        null, "lastPredictedTime");
                                        appUsageHistory.lastPredictedTime =
                                                attributeValue4 == null
                                                        ? 0L
                                                        : Long.parseLong(attributeValue4);
                                        String attributeValue5 =
                                                newPullParser.getAttributeValue(
                                                        null, "appLimitBucket");
                                        appUsageHistory.currentBucket =
                                                attributeValue5 == null
                                                        ? 10
                                                        : Integer.parseInt(attributeValue5);
                                        String attributeValue6 =
                                                newPullParser.getAttributeValue(
                                                        null, "bucketReason");
                                        String attributeValue7 =
                                                newPullParser.getAttributeValue(
                                                        null, "lastJobRunTime");
                                        appUsageHistory.lastJobRunTime =
                                                attributeValue7 == null
                                                        ? Long.MIN_VALUE
                                                        : Long.parseLong(attributeValue7);
                                        appUsageHistory.bucketingReason = 256;
                                        if (attributeValue6 != null) {
                                            try {
                                                appUsageHistory.bucketingReason =
                                                        Integer.parseInt(attributeValue6, 16);
                                            } catch (NumberFormatException e2) {
                                                Slog.wtf(
                                                        "AppIdleHistory",
                                                        "Unable to read bucketing reason",
                                                        e2);
                                            }
                                        }
                                        String attributeValue8 =
                                                newPullParser.getAttributeValue(
                                                        null, "lastRestrictionAttemptElapsedTime");
                                        appUsageHistory.lastRestrictAttemptElapsedTime =
                                                attributeValue8 == null
                                                        ? 0L
                                                        : Long.parseLong(attributeValue8);
                                        String attributeValue9 =
                                                newPullParser.getAttributeValue(
                                                        null, "lastRestrictionAttemptReason");
                                        if (attributeValue9 != null) {
                                            try {
                                                appUsageHistory.lastRestrictReason =
                                                        Integer.parseInt(attributeValue9, 16);
                                            } catch (NumberFormatException e3) {
                                                Slog.wtf(
                                                        "AppIdleHistory",
                                                        "Unable to read last restrict reason",
                                                        e3);
                                            }
                                        }
                                        String attributeValue10 =
                                                newPullParser.getAttributeValue(
                                                        null, "nextEstimatedAppLaunchTime");
                                        appUsageHistory.nextEstimatedLaunchTime =
                                                attributeValue10 == null
                                                        ? 0L
                                                        : Long.parseLong(attributeValue10);
                                        Flags.avoidIdleCheck();
                                        appUsageHistory.lastInformedBucket = -1;
                                        arrayMap.put(attributeValue2, appUsageHistory);
                                        if (parseInt >= 1) {
                                            int depth = newPullParser.getDepth();
                                            while (XmlUtils.nextElementWithin(
                                                    newPullParser, depth)) {
                                                if ("expiryTimes".equals(newPullParser.getName())) {
                                                    readBucketExpiryTimes(
                                                            newPullParser, appUsageHistory);
                                                }
                                            }
                                        } else {
                                            String attributeValue11 =
                                                    newPullParser.getAttributeValue(
                                                            null, "activeTimeoutTime");
                                            long parseLong2 =
                                                    attributeValue11 == null
                                                            ? 0L
                                                            : Long.parseLong(attributeValue11);
                                            String attributeValue12 =
                                                    newPullParser.getAttributeValue(
                                                            null, "workingSetTimeoutTime");
                                            long parseLong3 =
                                                    attributeValue12 == null
                                                            ? 0L
                                                            : Long.parseLong(attributeValue12);
                                            if (parseLong2 != 0 || parseLong3 != 0) {
                                                insertBucketExpiryTime(
                                                        appUsageHistory, 10, parseLong2);
                                                insertBucketExpiryTime(
                                                        appUsageHistory, 20, parseLong3);
                                            }
                                        }
                                    }
                                    i2 = 1;
                                    i3 = 2;
                                }
                            }
                        }
                        IoUtils.closeQuietly(openRead);
                    } catch (FileNotFoundException unused2) {
                        fileInputStream = openRead;
                        Slog.d("AppIdleHistory", "App idle file for user " + i + " does not exist");
                        IoUtils.closeQuietly(fileInputStream);
                        return arrayMap;
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = openRead;
                        IoUtils.closeQuietly(fileInputStream);
                        throw th;
                    }
                } catch (IOException | NumberFormatException | XmlPullParserException e4) {
                    e = e4;
                    fileInputStream = openRead;
                    Slog.e("AppIdleHistory", "Unable to read app idle file for user " + i, e);
                    IoUtils.closeQuietly(fileInputStream);
                    return arrayMap;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        return arrayMap;
    }

    public final boolean isIdle(int i, String str, long j) {
        return getPackageHistory(getUserHistory(i), str, true).currentBucket >= 40;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void reportUsage(
            com.android.server.usage.AppIdleHistory.AppUsageHistory r16,
            java.lang.String r17,
            int r18,
            int r19,
            int r20,
            long r21,
            long r23) {
        /*
            r15 = this;
            r0 = r15
            r1 = r16
            r2 = r19
            r3 = r21
            r5 = r23
            r7 = 768(0x300, float:1.076E-42)
            r8 = r20
            r8 = r8 | r7
            r9 = 65280(0xff00, float:9.1477E-41)
            r10 = r8 & r9
            r11 = 1
            r12 = 0
            if (r10 != r7) goto L20
            r7 = r8 & 255(0xff, float:3.57E-43)
            r10 = 3
            if (r7 == r10) goto L1f
            r10 = 4
            if (r7 != r10) goto L20
        L1f:
            r12 = r11
        L20:
            int r7 = r1.currentBucket
            r10 = 45
            if (r7 != r10) goto L32
            if (r12 != 0) goto L32
            int r7 = r1.bucketingReason
            r13 = r7 & r9
            r14 = 512(0x200, float:7.175E-43)
            if (r13 == r14) goto L32
            r8 = r7
            goto L77
        L32:
            int r7 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r7 <= 0) goto L76
            long r5 = r15.getElapsedTime(r5)
            android.util.SparseLongArray r7 = r1.bucketExpiryTimesMs
            if (r7 != 0) goto L45
            android.util.SparseLongArray r7 = new android.util.SparseLongArray
            r7.<init>()
            r1.bucketExpiryTimesMs = r7
        L45:
            android.util.SparseLongArray r7 = r1.bucketExpiryTimesMs
            long r13 = r7.get(r2)
            android.util.SparseLongArray r7 = r1.bucketExpiryTimesMs
            long r5 = java.lang.Math.max(r5, r13)
            r7.put(r2, r5)
            long r5 = r15.getElapsedTime(r3)
            android.util.SparseLongArray r7 = r1.bucketExpiryTimesMs
            if (r7 != 0) goto L5d
            goto L76
        L5d:
            int r7 = r7.size()
            int r7 = r7 - r11
        L62:
            if (r7 < 0) goto L76
            android.util.SparseLongArray r10 = r1.bucketExpiryTimesMs
            long r10 = r10.valueAt(r7)
            int r10 = (r10 > r5 ? 1 : (r10 == r5 ? 0 : -1))
            if (r10 >= 0) goto L73
            android.util.SparseLongArray r10 = r1.bucketExpiryTimesMs
            r10.removeAt(r7)
        L73:
            int r7 = r7 + (-1)
            goto L62
        L76:
            r10 = r2
        L77:
            r5 = 0
            int r2 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r2 == 0) goto L97
            long r5 = r0.mElapsedDuration
            long r13 = r0.mElapsedSnapshot
            long r13 = r3 - r13
            long r13 = r13 + r5
            r1.lastUsedElapsedTime = r13
            if (r12 == 0) goto L8a
            r1.lastUsedByUserElapsedTime = r13
        L8a:
            long r5 = r0.mScreenOnDuration
            boolean r2 = r0.mScreenOn
            if (r2 == 0) goto L95
            long r11 = r0.mScreenOnSnapshot
            long r2 = r3 - r11
            long r5 = r5 + r2
        L95:
            r1.lastUsedScreenTime = r5
        L97:
            int r0 = r1.currentBucket
            if (r0 < r10) goto Lb6
            if (r0 <= r10) goto Lb4
            r1.currentBucket = r10
            r0 = r8 & r9
            r2 = r8 & 255(0xff, float:3.57E-43)
            r3 = 258(0x102, float:3.62E-43)
            r19 = r3
            r20 = r17
            r21 = r18
            r22 = r10
            r23 = r0
            r24 = r2
            com.android.internal.util.FrameworkStatsLog.write(r19, r20, r21, r22, r23, r24)
        Lb4:
            r1.bucketingReason = r8
        Lb6:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.usage.AppIdleHistory.reportUsage(com.android.server.usage.AppIdleHistory$AppUsageHistory,"
                    + " java.lang.String, int, int, int, long, long):void");
    }

    public final void setAppStandbyBucket(int i, int i2, int i3, long j, String str, boolean z) {
        SparseLongArray sparseLongArray;
        AppUsageHistory packageHistory = getPackageHistory(getUserHistory(i), str, true);
        boolean z2 = packageHistory.currentBucket != i2;
        packageHistory.currentBucket = i2;
        packageHistory.bucketingReason = i3;
        long elapsedTime = getElapsedTime(j);
        int i4 = i3 & 65280;
        if (i4 == 1280) {
            packageHistory.lastPredictedTime = elapsedTime;
            packageHistory.lastPredictedBucket = i2;
        }
        if (z && (sparseLongArray = packageHistory.bucketExpiryTimesMs) != null) {
            sparseLongArray.clear();
        }
        if (z2) {
            FrameworkStatsLog.write(
                    258,
                    str,
                    i,
                    i2,
                    i4,
                    i3 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
        }
    }

    public final void updateDisplay(long j, boolean z) {
        if (z == this.mScreenOn) {
            return;
        }
        this.mScreenOn = z;
        if (z) {
            this.mScreenOnSnapshot = j;
            return;
        }
        this.mScreenOnDuration = (j - this.mScreenOnSnapshot) + this.mScreenOnDuration;
        this.mElapsedDuration = (j - this.mElapsedSnapshot) + this.mElapsedDuration;
        this.mElapsedSnapshot = j;
    }

    public final void writeAppIdleTimes(int i, long j) {
        AtomicFile atomicFile;
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2;
        ArrayMap arrayMap;
        FastXmlSerializer fastXmlSerializer;
        long j2;
        int i2;
        AtomicFile atomicFile2 = new AtomicFile(getUserFile(i));
        FileOutputStream fileOutputStream3 = null;
        String str = null;
        try {
            FileOutputStream startWrite = atomicFile2.startWrite();
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(startWrite);
                FastXmlSerializer fastXmlSerializer2 = new FastXmlSerializer();
                fastXmlSerializer2.setOutput(bufferedOutputStream, StandardCharsets.UTF_8.name());
                fastXmlSerializer2.startDocument(null, Boolean.TRUE);
                fastXmlSerializer2.setFeature(
                        "http://xmlpull.org/v1/doc/features.html#indent-output", true);
                fastXmlSerializer2.startTag(null, "packages");
                fastXmlSerializer2.attribute(null, "version", String.valueOf(1));
                long elapsedTime = getElapsedTime(j);
                ArrayMap userHistory = getUserHistory(i);
                int size = userHistory.size();
                int i3 = 0;
                while (i3 < size) {
                    try {
                        String str2 = (String) userHistory.keyAt(i3);
                        if (str2 == null) {
                            try {
                                Slog.w(
                                        "AppIdleHistory",
                                        "Skipping App Idle write for unexpected null package");
                                fileOutputStream2 = startWrite;
                                arrayMap = userHistory;
                                fastXmlSerializer = fastXmlSerializer2;
                                j2 = elapsedTime;
                                atomicFile = atomicFile2;
                            } catch (Exception e) {
                                e = e;
                                atomicFile = atomicFile2;
                                fileOutputStream3 = startWrite;
                                atomicFile.failWrite(fileOutputStream3);
                                Slog.e(
                                        "AppIdleHistory",
                                        "Error writing app idle file for user " + i,
                                        e);
                            }
                        } else {
                            AppUsageHistory appUsageHistory =
                                    (AppUsageHistory) userHistory.valueAt(i3);
                            fastXmlSerializer2.startTag(str, "package");
                            arrayMap = userHistory;
                            fastXmlSerializer2.attribute(str, "name", str2);
                            fileOutputStream2 = startWrite;
                            try {
                                fastXmlSerializer2.attribute(
                                        null,
                                        "elapsedIdleTime",
                                        Long.toString(appUsageHistory.lastUsedElapsedTime));
                                fastXmlSerializer2.attribute(
                                        null,
                                        "lastUsedByUserElapsedTime",
                                        Long.toString(appUsageHistory.lastUsedByUserElapsedTime));
                                fastXmlSerializer = fastXmlSerializer2;
                                fastXmlSerializer.attribute(
                                        null,
                                        "screenIdleTime",
                                        Long.toString(appUsageHistory.lastUsedScreenTime));
                                j2 = elapsedTime;
                                fastXmlSerializer.attribute(
                                        null,
                                        "lastPredictedTime",
                                        Long.toString(appUsageHistory.lastPredictedTime));
                                fastXmlSerializer.attribute(
                                        null,
                                        "appLimitBucket",
                                        Integer.toString(appUsageHistory.currentBucket));
                                fastXmlSerializer.attribute(
                                        null,
                                        "bucketReason",
                                        Integer.toHexString(appUsageHistory.bucketingReason));
                                long j3 = appUsageHistory.lastJobRunTime;
                                if (j3 != Long.MIN_VALUE) {
                                    fastXmlSerializer.attribute(
                                            null, "lastJobRunTime", Long.toString(j3));
                                }
                                long j4 = appUsageHistory.lastRestrictAttemptElapsedTime;
                                if (j4 > 0) {
                                    fastXmlSerializer.attribute(
                                            null,
                                            "lastRestrictionAttemptElapsedTime",
                                            Long.toString(j4));
                                }
                                fastXmlSerializer.attribute(
                                        null,
                                        "lastRestrictionAttemptReason",
                                        Integer.toHexString(appUsageHistory.lastRestrictReason));
                                atomicFile = atomicFile2;
                            } catch (Exception e2) {
                                e = e2;
                                atomicFile = atomicFile2;
                            }
                            try {
                                long j5 = appUsageHistory.nextEstimatedLaunchTime;
                                if (j5 > 0) {
                                    fastXmlSerializer.attribute(
                                            null, "nextEstimatedAppLaunchTime", Long.toString(j5));
                                }
                                if (appUsageHistory.bucketExpiryTimesMs != null) {
                                    fastXmlSerializer.startTag(null, "expiryTimes");
                                    int size2 = appUsageHistory.bucketExpiryTimesMs.size();
                                    int i4 = 0;
                                    while (i4 < size2) {
                                        long valueAt =
                                                appUsageHistory.bucketExpiryTimesMs.valueAt(i4);
                                        if (valueAt < j2) {
                                            i2 = size2;
                                        } else {
                                            int keyAt =
                                                    appUsageHistory.bucketExpiryTimesMs.keyAt(i4);
                                            fastXmlSerializer.startTag(null, "item");
                                            i2 = size2;
                                            fastXmlSerializer.attribute(
                                                    null, "bucket", String.valueOf(keyAt));
                                            fastXmlSerializer.attribute(
                                                    null, "expiry", String.valueOf(valueAt));
                                            fastXmlSerializer.endTag(null, "item");
                                        }
                                        i4++;
                                        size2 = i2;
                                    }
                                    fastXmlSerializer.endTag(null, "expiryTimes");
                                }
                                fastXmlSerializer.endTag(null, "package");
                            } catch (Exception e3) {
                                e = e3;
                                fileOutputStream3 = fileOutputStream2;
                                atomicFile.failWrite(fileOutputStream3);
                                Slog.e(
                                        "AppIdleHistory",
                                        "Error writing app idle file for user " + i,
                                        e);
                            }
                        }
                        i3++;
                        fastXmlSerializer2 = fastXmlSerializer;
                        atomicFile2 = atomicFile;
                        startWrite = fileOutputStream2;
                        elapsedTime = j2;
                        str = null;
                        userHistory = arrayMap;
                    } catch (Exception e4) {
                        e = e4;
                        atomicFile = atomicFile2;
                        fileOutputStream2 = startWrite;
                    }
                }
                atomicFile = atomicFile2;
                FileOutputStream fileOutputStream4 = startWrite;
                FastXmlSerializer fastXmlSerializer3 = fastXmlSerializer2;
                try {
                    fastXmlSerializer3.endTag(str, "packages");
                    fastXmlSerializer3.endDocument();
                    fileOutputStream = fileOutputStream4;
                } catch (Exception e5) {
                    e = e5;
                    fileOutputStream = fileOutputStream4;
                }
            } catch (Exception e6) {
                e = e6;
                atomicFile = atomicFile2;
                fileOutputStream = startWrite;
            }
        } catch (Exception e7) {
            e = e7;
            atomicFile = atomicFile2;
        }
        try {
            atomicFile.finishWrite(fileOutputStream);
        } catch (Exception e8) {
            e = e8;
            fileOutputStream3 = fileOutputStream;
            atomicFile.failWrite(fileOutputStream3);
            Slog.e("AppIdleHistory", "Error writing app idle file for user " + i, e);
        }
    }

    public final void writeScreenOnTime() {
        FileOutputStream fileOutputStream;
        AtomicFile atomicFile = new AtomicFile(getScreenOnTimeFile());
        try {
            fileOutputStream = atomicFile.startWrite();
            try {
                fileOutputStream.write(
                        (Long.toString(this.mScreenOnDuration)
                                        + "\n"
                                        + Long.toString(this.mElapsedDuration)
                                        + "\n")
                                .getBytes());
                atomicFile.finishWrite(fileOutputStream);
            } catch (IOException unused) {
                atomicFile.failWrite(fileOutputStream);
            }
        } catch (IOException unused2) {
            fileOutputStream = null;
        }
    }
}
