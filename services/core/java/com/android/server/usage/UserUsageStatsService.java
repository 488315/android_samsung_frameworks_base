package com.android.server.usage;

import android.app.usage.ConfigurationStats;
import android.app.usage.EventList;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.res.Configuration;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.os.Debug;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.text.format.DateUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.LongSparseArray;
import android.util.Slog;
import android.util.SparseArrayMap;
import android.util.TimeUtils;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.ProfileService$1$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UserUsageStatsService {
    public static final AnonymousClass1 sConfigStatsCombiner;
    public static final AnonymousClass1 sEventStatsCombiner;
    public static final AnonymousClass1 sUsageStatsCombiner;
    public final Context mContext;
    public final UsageStatsDatabase mDatabase;
    public long mDumpInitEndTime;
    public long mDumpInitLastTimeSaved;
    public String mLastBackgroundedPackage;
    public final StatsUpdatedListener mListener;
    public final String mLogPrefix;
    public final int mUserId;
    public static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat sLoggingFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static final long[] INTERVAL_LENGTH = {
        BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS,
        604800000,
        2592000000L,
        31536000000L
    };
    public boolean mStatsChanged = false;
    public final HashMap track = new HashMap();
    public final List mBufferLogs = new ArrayList();
    public final SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public final SparseArrayMap mCachedEarlyEvents = new SparseArrayMap();
    public final UnixCalendar mDailyExpiryDate = new UnixCalendar();
    public final IntervalStats[] mCurrentStats = new IntervalStats[4];
    public long mRealTimeSnapshot = SystemClock.elapsedRealtime();
    public long mSystemTimeSnapshot = System.currentTimeMillis();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CachedEarlyEvents {
        public long eventTime;
        public List events;
        public long searchBeginTime;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface StatsUpdatedListener {}

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.usage.UserUsageStatsService$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.usage.UserUsageStatsService$1] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.server.usage.UserUsageStatsService$1] */
    static {
        final int i = 0;
        sUsageStatsCombiner =
                new UsageStatsDatabase
                        .StatCombiner() { // from class:
                                          // com.android.server.usage.UserUsageStatsService.1
                    @Override // com.android.server.usage.UsageStatsDatabase.StatCombiner
                    public final boolean combine(
                            IntervalStats intervalStats, boolean z, List list) {
                        switch (i) {
                            case 0:
                                if (z) {
                                    int size = intervalStats.packageStats.size();
                                    for (int i2 = 0; i2 < size; i2++) {
                                        list.add(
                                                new UsageStats(
                                                        (UsageStats)
                                                                intervalStats.packageStats.valueAt(
                                                                        i2)));
                                    }
                                    break;
                                } else {
                                    list.addAll(intervalStats.packageStats.values());
                                    break;
                                }
                            case 1:
                                if (z) {
                                    int size2 = intervalStats.configurations.size();
                                    for (int i3 = 0; i3 < size2; i3++) {
                                        list.add(
                                                new ConfigurationStats(
                                                        (ConfigurationStats)
                                                                intervalStats.configurations
                                                                        .valueAt(i3)));
                                    }
                                    break;
                                } else {
                                    list.addAll(intervalStats.configurations.values());
                                    break;
                                }
                            default:
                                intervalStats.interactiveTracker.addToEventStats(
                                        list, 15, intervalStats.beginTime, intervalStats.endTime);
                                intervalStats.nonInteractiveTracker.addToEventStats(
                                        list, 16, intervalStats.beginTime, intervalStats.endTime);
                                intervalStats.keyguardShownTracker.addToEventStats(
                                        list, 17, intervalStats.beginTime, intervalStats.endTime);
                                intervalStats.keyguardHiddenTracker.addToEventStats(
                                        list, 18, intervalStats.beginTime, intervalStats.endTime);
                                break;
                        }
                        return true;
                    }
                };
        final int i2 = 1;
        sConfigStatsCombiner =
                new UsageStatsDatabase
                        .StatCombiner() { // from class:
                                          // com.android.server.usage.UserUsageStatsService.1
                    @Override // com.android.server.usage.UsageStatsDatabase.StatCombiner
                    public final boolean combine(
                            IntervalStats intervalStats, boolean z, List list) {
                        switch (i2) {
                            case 0:
                                if (z) {
                                    int size = intervalStats.packageStats.size();
                                    for (int i22 = 0; i22 < size; i22++) {
                                        list.add(
                                                new UsageStats(
                                                        (UsageStats)
                                                                intervalStats.packageStats.valueAt(
                                                                        i22)));
                                    }
                                    break;
                                } else {
                                    list.addAll(intervalStats.packageStats.values());
                                    break;
                                }
                            case 1:
                                if (z) {
                                    int size2 = intervalStats.configurations.size();
                                    for (int i3 = 0; i3 < size2; i3++) {
                                        list.add(
                                                new ConfigurationStats(
                                                        (ConfigurationStats)
                                                                intervalStats.configurations
                                                                        .valueAt(i3)));
                                    }
                                    break;
                                } else {
                                    list.addAll(intervalStats.configurations.values());
                                    break;
                                }
                            default:
                                intervalStats.interactiveTracker.addToEventStats(
                                        list, 15, intervalStats.beginTime, intervalStats.endTime);
                                intervalStats.nonInteractiveTracker.addToEventStats(
                                        list, 16, intervalStats.beginTime, intervalStats.endTime);
                                intervalStats.keyguardShownTracker.addToEventStats(
                                        list, 17, intervalStats.beginTime, intervalStats.endTime);
                                intervalStats.keyguardHiddenTracker.addToEventStats(
                                        list, 18, intervalStats.beginTime, intervalStats.endTime);
                                break;
                        }
                        return true;
                    }
                };
        final int i3 = 2;
        sEventStatsCombiner =
                new UsageStatsDatabase
                        .StatCombiner() { // from class:
                                          // com.android.server.usage.UserUsageStatsService.1
                    @Override // com.android.server.usage.UsageStatsDatabase.StatCombiner
                    public final boolean combine(
                            IntervalStats intervalStats, boolean z, List list) {
                        switch (i3) {
                            case 0:
                                if (z) {
                                    int size = intervalStats.packageStats.size();
                                    for (int i22 = 0; i22 < size; i22++) {
                                        list.add(
                                                new UsageStats(
                                                        (UsageStats)
                                                                intervalStats.packageStats.valueAt(
                                                                        i22)));
                                    }
                                    break;
                                } else {
                                    list.addAll(intervalStats.packageStats.values());
                                    break;
                                }
                            case 1:
                                if (z) {
                                    int size2 = intervalStats.configurations.size();
                                    for (int i32 = 0; i32 < size2; i32++) {
                                        list.add(
                                                new ConfigurationStats(
                                                        (ConfigurationStats)
                                                                intervalStats.configurations
                                                                        .valueAt(i32)));
                                    }
                                    break;
                                } else {
                                    list.addAll(intervalStats.configurations.values());
                                    break;
                                }
                            default:
                                intervalStats.interactiveTracker.addToEventStats(
                                        list, 15, intervalStats.beginTime, intervalStats.endTime);
                                intervalStats.nonInteractiveTracker.addToEventStats(
                                        list, 16, intervalStats.beginTime, intervalStats.endTime);
                                intervalStats.keyguardShownTracker.addToEventStats(
                                        list, 17, intervalStats.beginTime, intervalStats.endTime);
                                intervalStats.keyguardHiddenTracker.addToEventStats(
                                        list, 18, intervalStats.beginTime, intervalStats.endTime);
                                break;
                        }
                        return true;
                    }
                };
    }

    public UserUsageStatsService(
            Context context, int i, File file, StatsUpdatedListener statsUpdatedListener) {
        this.mContext = context;
        this.mDatabase = new UsageStatsDatabase(file, 5);
        this.mListener = statsUpdatedListener;
        this.mLogPrefix = "User[" + Integer.toString(i) + "] ";
        this.mUserId = i;
    }

    public static String eventToString(int i) {
        switch (i) {
            case 0:
                return "NONE";
            case 1:
                return "ACTIVITY_RESUMED";
            case 2:
                return "ACTIVITY_PAUSED";
            case 3:
                return "END_OF_DAY";
            case 4:
                return "CONTINUE_PREVIOUS_DAY";
            case 5:
                return "CONFIGURATION_CHANGE";
            case 6:
                return "SYSTEM_INTERACTION";
            case 7:
                return "USER_INTERACTION";
            case 8:
                return "SHORTCUT_INVOCATION";
            case 9:
                return "CHOOSER_ACTION";
            case 10:
                return "NOTIFICATION_SEEN";
            case 11:
                return "STANDBY_BUCKET_CHANGED";
            case 12:
                return "NOTIFICATION_INTERRUPTION";
            case 13:
                return "SLICE_PINNED_PRIV";
            case 14:
                return "SLICE_PINNED";
            case 15:
                return "SCREEN_INTERACTIVE";
            case 16:
                return "SCREEN_NON_INTERACTIVE";
            case 17:
                return "KEYGUARD_SHOWN";
            case 18:
                return "KEYGUARD_HIDDEN";
            case 19:
                return "FOREGROUND_SERVICE_START";
            case 20:
                return "FOREGROUND_SERVICE_STOP";
            case 21:
                return "CONTINUING_FOREGROUND_SERVICE";
            case 22:
                return "ROLLOVER_FOREGROUND_SERVICE";
            case 23:
                return "ACTIVITY_STOPPED";
            case 24:
            case 25:
            default:
                return VibrationParam$1$$ExternalSyntheticOutline0.m(i, "UNKNOWN_TYPE_");
            case 26:
                return "DEVICE_SHUTDOWN";
            case 27:
                return "DEVICE_STARTUP";
            case 28:
                return "USER_UNLOCKED";
            case 29:
                return "USER_STOPPED";
            case 30:
                return "LOCUS_ID_SET";
            case 31:
                return "APP_COMPONENT_USED";
        }
    }

    public static String formatDateTime(long j, boolean z) {
        if (!z) {
            return Long.toString(j);
        }
        return "\"" + sDateFormat.format(Long.valueOf(j)) + "\"";
    }

    public static String formatElapsedTime(long j, boolean z) {
        if (!z) {
            return Long.toString(j);
        }
        return "\"" + DateUtils.formatElapsedTime(j / 1000) + "\"";
    }

    public static String intervalToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? "?" : "yearly" : "monthly" : "weekly" : "daily";
    }

    public static void printEvent(
            IndentingPrintWriter indentingPrintWriter, UsageEvents.Event event, boolean z) {
        PersistableBundle persistableBundle;
        indentingPrintWriter.printPair("time", formatDateTime(event.mTimeStamp, z));
        indentingPrintWriter.printPair("type", eventToString(event.mEventType));
        indentingPrintWriter.printPair("package", event.mPackage);
        String str = event.mClass;
        if (str != null) {
            indentingPrintWriter.printPair("class", str);
        }
        Configuration configuration = event.mConfiguration;
        if (configuration != null) {
            indentingPrintWriter.printPair(
                    "config", Configuration.resourceQualifierString(configuration));
        }
        String str2 = event.mShortcutId;
        if (str2 != null) {
            indentingPrintWriter.printPair("shortcutId", str2);
        }
        int i = event.mEventType;
        if (i == 11) {
            indentingPrintWriter.printPair(
                    "standbyBucket", Integer.valueOf(event.getAppStandbyBucket()));
            indentingPrintWriter.printPair(
                    "reason", UsageStatsManager.reasonToString(event.getStandbyReason()));
        } else if (i == 1 || i == 2 || i == 23) {
            indentingPrintWriter.printPair("instanceId", Integer.valueOf(event.getInstanceId()));
        }
        if (event.getTaskRootPackageName() != null) {
            indentingPrintWriter.printPair("taskRootPackage", event.getTaskRootPackageName());
        }
        if (event.getTaskRootClassName() != null) {
            indentingPrintWriter.printPair("taskRootClass", event.getTaskRootClassName());
        }
        String str3 = event.mNotificationChannelId;
        if (str3 != null) {
            indentingPrintWriter.printPair("channelId", str3);
        }
        if (event.mEventType == 7 && (persistableBundle = event.mExtras) != null) {
            indentingPrintWriter.print(persistableBundle.toString());
        }
        indentingPrintWriter.printHexPair("flags", event.mFlags);
        indentingPrintWriter.println();
    }

    public static void printEventAggregation(
            IndentingPrintWriter indentingPrintWriter,
            String str,
            IntervalStats.EventTracker eventTracker,
            boolean z) {
        if (eventTracker.count == 0 && eventTracker.duration == 0) {
            return;
        }
        indentingPrintWriter.print(str);
        indentingPrintWriter.print(": ");
        indentingPrintWriter.print(eventTracker.count);
        indentingPrintWriter.print("x for ");
        indentingPrintWriter.print(formatElapsedTime(eventTracker.duration, z));
        if (eventTracker.curStartTime != 0) {
            indentingPrintWriter.print(" (now running, started at ");
            formatDateTime(eventTracker.curStartTime, z);
            indentingPrintWriter.print(")");
        }
        indentingPrintWriter.println();
    }

    public static boolean validRange(long j, long j2, long j3) {
        return j2 <= j && j2 < j3;
    }

    public final void addBufferLog(String str) {
        synchronized (this.mBufferLogs) {
            try {
                ((ArrayList) this.mBufferLogs)
                        .add(this.mDateFormat.format(new Date(System.currentTimeMillis())) + str);
                if (((ArrayList) this.mBufferLogs).size() > 20) {
                    ((ArrayList) this.mBufferLogs).remove(0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final long checkAndGetTimeLocked() {
        final long j;
        int i;
        long j2;
        final long currentTimeMillis = System.currentTimeMillis();
        if (!UsageStatsService.ENABLE_TIME_CHANGE_CORRECTION) {
            return currentTimeMillis;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j3 = (elapsedRealtime - this.mRealTimeSnapshot) + this.mSystemTimeSnapshot;
        long j4 = currentTimeMillis - j3;
        if (Math.abs(j4) > 2000) {
            Slog.i(
                    "UsageStatsService",
                    this.mLogPrefix + "Time changed in by " + (j4 / 1000) + " seconds");
            this.mCachedEarlyEvents.clear();
            persistActiveStats();
            UsageStatsDatabase usageStatsDatabase = this.mDatabase;
            synchronized (usageStatsDatabase.mLock) {
                StringBuilder sb = new StringBuilder();
                sb.append("Time changed by ");
                TimeUtils.formatDuration(j4, sb);
                sb.append(".");
                LongSparseArray[] longSparseArrayArr = usageStatsDatabase.mSortedStatFiles;
                int length = longSparseArrayArr.length;
                int i2 = 0;
                int i3 = 0;
                int i4 = 0;
                while (i4 < length) {
                    LongSparseArray longSparseArray = longSparseArrayArr[i4];
                    LongSparseArray[] longSparseArrayArr2 = longSparseArrayArr;
                    int size = longSparseArray.size();
                    int i5 = length;
                    int i6 = 0;
                    while (i6 < size) {
                        AtomicFile atomicFile = (AtomicFile) longSparseArray.valueAt(i6);
                        long keyAt = longSparseArray.keyAt(i6) + j4;
                        if (keyAt < 0) {
                            i3++;
                            atomicFile.delete();
                            i = size;
                            j2 = j3;
                        } else {
                            try {
                                atomicFile.openRead().close();
                            } catch (IOException unused) {
                            }
                            i = size;
                            String l = Long.toString(keyAt);
                            int i7 = i3;
                            j2 = j3;
                            if (atomicFile.getBaseFile().getName().endsWith("-c")) {
                                l = l + "-c";
                            }
                            i2++;
                            atomicFile
                                    .getBaseFile()
                                    .renameTo(
                                            new File(atomicFile.getBaseFile().getParentFile(), l));
                            i3 = i7;
                        }
                        i6++;
                        size = i;
                        j3 = j2;
                    }
                    longSparseArray.clear();
                    i4++;
                    longSparseArrayArr = longSparseArrayArr2;
                    length = i5;
                    i3 = i3;
                    j3 = j3;
                }
                j = j3;
                sb.append(" files deleted: ");
                sb.append(i3);
                sb.append(" files moved: ");
                sb.append(i2);
                Slog.i("UsageStatsDatabase", sb.toString());
                usageStatsDatabase.indexFilesLocked();
            }
            loadActiveStats(currentTimeMillis);
            Slog.w("UsageStatsService", "onTimeChanged_ diff=" + j4);
            this.track.forEach(
                    new BiConsumer() { // from class:
                                       // com.android.server.usage.UserUsageStatsService$$ExternalSyntheticLambda3
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            UserUsageStatsService userUsageStatsService =
                                    UserUsageStatsService.this;
                            final long j5 = currentTimeMillis;
                            final long j6 = j;
                            userUsageStatsService.getClass();
                            final ArrayList arrayList = new ArrayList();
                            ((ArrayList) obj2)
                                    .forEach(
                                            new Consumer() { // from class:
                                                             // com.android.server.usage.UserUsageStatsService$$ExternalSyntheticLambda4
                                                @Override // java.util.function.Consumer
                                                public final void accept(Object obj3) {
                                                    ArrayList arrayList2 = arrayList;
                                                    long j7 = j5;
                                                    arrayList2.add(
                                                            Long.valueOf(
                                                                    (((Long) obj3).longValue() + j7)
                                                                            - j6));
                                                }
                                            });
                            userUsageStatsService.track.put((String) obj, arrayList);
                        }
                    });
            this.mRealTimeSnapshot = elapsedRealtime;
            this.mSystemTimeSnapshot = currentTimeMillis;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.mLogPrefix);
            sb2.append("Time changed. actualSystemTime:");
            sb2.append(currentTimeMillis);
            BootReceiver$$ExternalSyntheticOutline0.m(
                    sb2, " expectedSystemTime:", j, " actualRealtime:");
            sb2.append(elapsedRealtime);
            addBufferLog(sb2.toString());
        }
        return currentTimeMillis;
    }

    public final void checkin(IndentingPrintWriter indentingPrintWriter) {
        int i;
        UsageStatsDatabase usageStatsDatabase = this.mDatabase;
        synchronized (usageStatsDatabase.mLock) {
            try {
                LongSparseArray longSparseArray = usageStatsDatabase.mSortedStatFiles[0];
                int size = longSparseArray.size();
                int i2 = -1;
                int i3 = 0;
                while (true) {
                    i = size - 1;
                    if (i3 >= i) {
                        break;
                    }
                    if (((AtomicFile) longSparseArray.valueAt(i3))
                            .getBaseFile()
                            .getPath()
                            .endsWith("-c")) {
                        i2 = i3;
                    }
                    i3++;
                }
                int i4 = i2 + 1;
                if (i4 == i) {
                    return;
                }
                for (int i5 = i4; i5 < i; i5++) {
                    IntervalStats intervalStats = new IntervalStats();
                    usageStatsDatabase.readLocked(
                            (AtomicFile) longSparseArray.valueAt(i5), intervalStats, false);
                    printIntervalStats(indentingPrintWriter, intervalStats, false, false, null);
                }
                while (i4 < i) {
                    AtomicFile atomicFile = (AtomicFile) longSparseArray.valueAt(i4);
                    File file = new File(atomicFile.getBaseFile().getPath() + "-c");
                    if (!atomicFile.getBaseFile().renameTo(file)) {
                        Slog.e(
                                "UsageStatsDatabase",
                                "Failed to mark file "
                                        + atomicFile.getBaseFile().getPath()
                                        + " as checked-in");
                        return;
                    }
                    longSparseArray.setValueAt(i4, new AtomicFile(file));
                    i4++;
                }
            } catch (Exception e) {
                Slog.e("UsageStatsDatabase", "Failed to check-in", e);
            } finally {
            }
        }
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter, final List list, boolean z) {
        boolean z2 = !z;
        final long currentTimeMillis = System.currentTimeMillis();
        final long j =
                ((-1) * BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS)
                        + currentTimeMillis;
        List queryStats =
                queryStats(
                        0,
                        j,
                        currentTimeMillis,
                        new UsageStatsDatabase
                                .StatCombiner() { // from class:
                                                  // com.android.server.usage.UserUsageStatsService.6
                            @Override // com.android.server.usage.UsageStatsDatabase.StatCombiner
                            public final boolean combine(
                                    IntervalStats intervalStats, boolean z3, List list2) {
                                int size = intervalStats.events.size();
                                for (int firstIndexOnOrAfter =
                                                intervalStats.events.firstIndexOnOrAfter(j);
                                        firstIndexOnOrAfter < size;
                                        firstIndexOnOrAfter++) {
                                    if (intervalStats.events.get(firstIndexOnOrAfter).mTimeStamp
                                            >= currentTimeMillis) {
                                        return false;
                                    }
                                    UsageEvents.Event event =
                                            intervalStats.events.get(firstIndexOnOrAfter);
                                    if (CollectionUtils.isEmpty(list)
                                            || list.contains(event.mPackage)) {
                                        list2.add(event);
                                    }
                                }
                                return true;
                            }
                        },
                        false);
        indentingPrintWriter.print("Last 24 hour events (");
        if (z2) {
            indentingPrintWriter.printPair(
                    "timeRange",
                    "\""
                            + DateUtils.formatDateRange(this.mContext, j, currentTimeMillis, 131093)
                            + "\"");
        } else {
            indentingPrintWriter.printPair("beginTime", Long.valueOf(j));
            indentingPrintWriter.printPair("endTime", Long.valueOf(currentTimeMillis));
        }
        indentingPrintWriter.println(")");
        if (queryStats != null) {
            indentingPrintWriter.increaseIndent();
            Iterator it = queryStats.iterator();
            while (it.hasNext()) {
                printEvent(indentingPrintWriter, (UsageEvents.Event) it.next(), z2);
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.printPair(
                "mDumpInitLastTimeSaved", formatDateTime(this.mDumpInitLastTimeSaved, z2));
        indentingPrintWriter.printPair(
                "mDumpInitEndTime", formatDateTime(this.mDumpInitEndTime, z2));
        indentingPrintWriter.println();
        indentingPrintWriter.println(" UsageStats RollOver history :");
        synchronized (this.mBufferLogs) {
            try {
                Iterator it2 = ((ArrayList) this.mBufferLogs).iterator();
                while (it2.hasNext()) {
                    indentingPrintWriter.println("    " + ((String) it2.next()));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        for (int i = 0; i < this.mCurrentStats.length; i++) {
            indentingPrintWriter.print("In-memory ");
            indentingPrintWriter.print(intervalToString(i));
            indentingPrintWriter.println(" stats");
            printIntervalStats(indentingPrintWriter, this.mCurrentStats[i], z2, true, list);
        }
        if (CollectionUtils.isEmpty(list)) {
            this.mDatabase.dump(indentingPrintWriter, z);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final void dumpFile(IndentingPrintWriter indentingPrintWriter, String[] strArr) {
        boolean z;
        int i = 3;
        UsageStatsDatabase usageStatsDatabase = this.mDatabase;
        if (strArr == null || strArr.length == 0) {
            int length = usageStatsDatabase.mSortedStatFiles.length;
            for (int i2 = 0; i2 < length; i2++) {
                indentingPrintWriter.println("interval=".concat(intervalToString(i2)));
                indentingPrintWriter.increaseIndent();
                dumpFileDetailsForInterval(indentingPrintWriter, i2);
                indentingPrintWriter.decreaseIndent();
            }
            return;
        }
        try {
            String lowerCase = strArr[0].toLowerCase();
            lowerCase.getClass();
            switch (lowerCase.hashCode()) {
                case -791707519:
                    if (lowerCase.equals("weekly")) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                case -734561654:
                    if (lowerCase.equals("yearly")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case 95346201:
                    if (lowerCase.equals("daily")) {
                        z = 2;
                        break;
                    }
                    z = -1;
                    break;
                case 1236635661:
                    if (lowerCase.equals("monthly")) {
                        z = 3;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    i = 1;
                    break;
                case true:
                    break;
                case true:
                    i = 0;
                    break;
                case true:
                    i = 2;
                    break;
                default:
                    i = -1;
                    break;
            }
            if (i == -1) {
                i = Integer.valueOf(strArr[0]).intValue();
            }
            if (i < 0 || i >= usageStatsDatabase.mSortedStatFiles.length) {
                indentingPrintWriter.println("the specified interval does not exist.");
                return;
            }
            if (strArr.length == 1) {
                dumpFileDetailsForInterval(indentingPrintWriter, i);
                return;
            }
            try {
                IntervalStats readIntervalStatsForFile =
                        usageStatsDatabase.readIntervalStatsForFile(
                                i, Long.valueOf(strArr[1]).longValue());
                if (readIntervalStatsForFile == null) {
                    indentingPrintWriter.println("the specified filename does not exist.");
                } else {
                    dumpFileDetails(
                            indentingPrintWriter,
                            readIntervalStatsForFile,
                            Long.valueOf(strArr[1]).longValue());
                }
            } catch (NumberFormatException unused) {
                indentingPrintWriter.println("invalid filename specified.");
            }
        } catch (NumberFormatException unused2) {
            indentingPrintWriter.println("invalid interval specified.");
        }
    }

    public final void dumpFileDetails(
            IndentingPrintWriter indentingPrintWriter, IntervalStats intervalStats, long j) {
        indentingPrintWriter.println("file=" + j);
        indentingPrintWriter.increaseIndent();
        printIntervalStats(indentingPrintWriter, intervalStats, false, false, null);
        indentingPrintWriter.decreaseIndent();
    }

    public final void dumpFileDetailsForInterval(IndentingPrintWriter indentingPrintWriter, int i) {
        UsageStatsDatabase usageStatsDatabase = this.mDatabase;
        LongSparseArray longSparseArray = usageStatsDatabase.mSortedStatFiles[i];
        int size = longSparseArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            long keyAt = longSparseArray.keyAt(i2);
            dumpFileDetails(
                    indentingPrintWriter,
                    usageStatsDatabase.readIntervalStatsForFile(i, keyAt),
                    keyAt);
            indentingPrintWriter.println();
        }
    }

    public final void init(long j, HashMap hashMap, boolean z) {
        IntervalStats[] intervalStatsArr;
        this.mDatabase.readMappingsLocked();
        if (this.mUserId != 0 && z) {
            updatePackageMappingsLocked(hashMap);
        }
        UsageStatsDatabase usageStatsDatabase = this.mDatabase;
        synchronized (usageStatsDatabase.mLock) {
            try {
                for (File file : usageStatsDatabase.mIntervalDirs) {
                    file.mkdirs();
                    if (!file.exists()) {
                        throw new IllegalStateException(
                                "Failed to create directory " + file.getAbsolutePath());
                    }
                }
                usageStatsDatabase.checkVersionAndBuildLocked();
                if (usageStatsDatabase.mUpgradePerformed) {
                    usageStatsDatabase.prune(j, false);
                } else {
                    usageStatsDatabase.indexFilesLocked();
                }
                for (LongSparseArray longSparseArray : usageStatsDatabase.mSortedStatFiles) {
                    int firstIndexOnOrAfter = longSparseArray.firstIndexOnOrAfter(j);
                    if (firstIndexOnOrAfter >= 0) {
                        int size = longSparseArray.size();
                        for (int i = firstIndexOnOrAfter; i < size; i++) {
                            ((AtomicFile) longSparseArray.valueAt(i)).delete();
                        }
                        while (firstIndexOnOrAfter < size) {
                            longSparseArray.removeAt(firstIndexOnOrAfter);
                            firstIndexOnOrAfter++;
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        UsageStatsDatabase usageStatsDatabase2 = this.mDatabase;
        if (usageStatsDatabase2.mUpgradePerformed) {
            usageStatsDatabase2.prunePackagesDataOnUpgrade(hashMap);
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            intervalStatsArr = this.mCurrentStats;
            if (i2 >= intervalStatsArr.length) {
                break;
            }
            intervalStatsArr[i2] = this.mDatabase.getLatestUsageStats(i2);
            if (this.mCurrentStats[i2] == null) {
                i3++;
            }
            i2++;
        }
        if (i3 > 0) {
            if (i3 != intervalStatsArr.length) {
                ProfileService$1$$ExternalSyntheticOutline0.m(
                        new StringBuilder(),
                        this.mLogPrefix,
                        "Some stats have no latest available",
                        "UsageStatsService");
            }
            loadActiveStats(j);
        } else {
            updateRolloverDeadline();
        }
        IntervalStats intervalStats = this.mCurrentStats[0];
        if (intervalStats != null) {
            UsageEvents.Event event =
                    new UsageEvents.Event(
                            26, Math.max(intervalStats.lastTimeSaved, intervalStats.endTime));
            this.mDumpInitLastTimeSaved = intervalStats.lastTimeSaved;
            this.mDumpInitEndTime = intervalStats.endTime;
            Slog.d(
                    "UsageStatsService",
                    this.mLogPrefix
                            + eventToString(event.getEventType())
                            + ", "
                            + TimeUtils.formatForLogging(event.mTimeStamp)
                            + ", "
                            + TimeUtils.formatForLogging(intervalStats.lastTimeSaved));
            event.mPackage = "android";
            intervalStats.addEvent(event);
            UsageEvents.Event event2 = new UsageEvents.Event(27, System.currentTimeMillis());
            event2.mPackage = "android";
            intervalStats.addEvent(event2);
        }
        if (this.mDatabase.mNewUpdate) {
            ((UsageStatsService) this.mListener)
                    .mAppStandby.initializeDefaultsForSystemApps(this.mUserId);
        }
    }

    public final void loadActiveStats(long j) {
        int i = 0;
        while (true) {
            IntervalStats[] intervalStatsArr = this.mCurrentStats;
            if (i >= intervalStatsArr.length) {
                this.mStatsChanged = false;
                updateRolloverDeadline();
                ((UsageStatsService) this.mListener).mAppStandby.postOneTimeCheckIdleStates();
                return;
            }
            IntervalStats latestUsageStats = this.mDatabase.getLatestUsageStats(i);
            if (latestUsageStats == null || j >= latestUsageStats.beginTime + INTERVAL_LENGTH[i]) {
                intervalStatsArr[i] = new IntervalStats();
                IntervalStats intervalStats = intervalStatsArr[i];
                intervalStats.beginTime = j;
                intervalStats.endTime = 1 + j;
            } else {
                intervalStatsArr[i] = latestUsageStats;
            }
            i++;
        }
    }

    public final void notifyStatsChanged() {
        if (this.mStatsChanged) {
            return;
        }
        this.mStatsChanged = true;
        ((UsageStatsService) this.mListener).mHandler.sendEmptyMessageDelayed(1, 1200000L);
    }

    public final int onPackageRemoved(long j, String str) {
        int removePackage;
        for (int numMaps = this.mCachedEarlyEvents.numMaps() - 1; numMaps >= 0; numMaps--) {
            this.mCachedEarlyEvents.delete(this.mCachedEarlyEvents.keyAt(numMaps), str);
        }
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                "onPackageRemoved ", str, "UsageStatsService");
        this.track.remove(str);
        UsageStatsDatabase usageStatsDatabase = this.mDatabase;
        synchronized (usageStatsDatabase.mLock) {
            removePackage = usageStatsDatabase.mPackagesTokenData.removePackage(j, str);
            try {
                usageStatsDatabase.writeMappingsLocked();
            } catch (Exception unused) {
                Slog.w(
                        "UsageStatsDatabase",
                        "Unable to update package mappings on disk after removing token "
                                + removePackage);
            }
        }
        return removePackage;
    }

    public final void persistActiveStats() {
        UsageStatsDatabase usageStatsDatabase = this.mDatabase;
        if (this.mStatsChanged) {
            StringBuilder sb = new StringBuilder();
            String str = this.mLogPrefix;
            BootReceiver$$ExternalSyntheticOutline0.m59m(
                    sb, str, "Flushing usage stats to disk", "UsageStatsService");
            try {
                int i = usageStatsDatabase.mCurrentVersion;
                IntervalStats[] intervalStatsArr = this.mCurrentStats;
                if (i >= 5) {
                    for (IntervalStats intervalStats : intervalStatsArr) {
                        intervalStats.obfuscateData(usageStatsDatabase.mPackagesTokenData);
                    }
                }
                usageStatsDatabase.writeMappingsLocked();
                for (int i2 = 0; i2 < intervalStatsArr.length; i2++) {
                    usageStatsDatabase.putUsageStats(i2, intervalStatsArr[i2]);
                }
                this.mStatsChanged = false;
            } catch (IOException e) {
                Slog.e("UsageStatsService", str + "Failed to persist active stats", e);
            }
        }
    }

    public final void printIntervalStats(
            IndentingPrintWriter indentingPrintWriter,
            IntervalStats intervalStats,
            boolean z,
            boolean z2,
            List list) {
        int i;
        int i2;
        Iterator it;
        if (z) {
            indentingPrintWriter.printPair(
                    "timeRange",
                    "\""
                            + DateUtils.formatDateRange(
                                    this.mContext,
                                    intervalStats.beginTime,
                                    intervalStats.endTime,
                                    131093)
                            + "\"");
        } else {
            indentingPrintWriter.printPair("beginTime", Long.valueOf(intervalStats.beginTime));
            indentingPrintWriter.printPair("endTime", Long.valueOf(intervalStats.endTime));
        }
        indentingPrintWriter.println();
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("packages");
        indentingPrintWriter.increaseIndent();
        ArrayMap arrayMap = intervalStats.packageStats;
        int size = arrayMap.size();
        while (i < size) {
            UsageStats usageStats = (UsageStats) arrayMap.valueAt(i);
            if (!CollectionUtils.isEmpty(list)) {
                i = ((ArrayList) list).contains(usageStats.mPackageName) ? 0 : i + 1;
            }
            indentingPrintWriter.printPair("package", usageStats.mPackageName);
            indentingPrintWriter.printPair(
                    "totalTimeUsed", formatElapsedTime(usageStats.mTotalTimeInForeground, z));
            indentingPrintWriter.printPair(
                    "lastTimeUsed", formatDateTime(usageStats.mLastTimeUsed, z));
            indentingPrintWriter.printPair(
                    "totalTimeVisible", formatElapsedTime(usageStats.mTotalTimeVisible, z));
            indentingPrintWriter.printPair(
                    "lastTimeVisible", formatDateTime(usageStats.mLastTimeVisible, z));
            indentingPrintWriter.printPair(
                    "lastTimeComponentUsed", formatDateTime(usageStats.mLastTimeComponentUsed, z));
            indentingPrintWriter.printPair(
                    "totalTimeFS",
                    formatElapsedTime(usageStats.mTotalTimeForegroundServiceUsed, z));
            indentingPrintWriter.printPair(
                    "lastTimeFS", formatDateTime(usageStats.mLastTimeForegroundServiceUsed, z));
            indentingPrintWriter.printPair(
                    "appLaunchCount", Integer.valueOf(usageStats.mAppLaunchCount));
            indentingPrintWriter.println();
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.println("ChooserCounts");
        indentingPrintWriter.increaseIndent();
        Iterator it2 = arrayMap.values().iterator();
        while (it2.hasNext()) {
            UsageStats usageStats2 = (UsageStats) it2.next();
            if (!CollectionUtils.isEmpty(list)) {
                if (!((ArrayList) list).contains(usageStats2.mPackageName)) {}
            }
            indentingPrintWriter.printPair("package", usageStats2.mPackageName);
            ArrayMap arrayMap2 = usageStats2.mChooserCounts;
            if (arrayMap2 != null) {
                int size2 = arrayMap2.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    String str = (String) usageStats2.mChooserCounts.keyAt(i3);
                    ArrayMap arrayMap3 = (ArrayMap) usageStats2.mChooserCounts.valueAt(i3);
                    int size3 = arrayMap3.size();
                    int i4 = 0;
                    while (i4 < size3) {
                        String str2 = (String) arrayMap3.keyAt(i4);
                        int intValue = ((Integer) arrayMap3.valueAt(i4)).intValue();
                        if (intValue != 0) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(str);
                            it = it2;
                            sb.append(":");
                            sb.append(str2);
                            sb.append(" is ");
                            sb.append(Integer.toString(intValue));
                            indentingPrintWriter.printPair("ChooserCounts", sb.toString());
                            indentingPrintWriter.println();
                        } else {
                            it = it2;
                        }
                        i4++;
                        it2 = it;
                    }
                }
            }
            indentingPrintWriter.println();
            it2 = it2;
        }
        indentingPrintWriter.decreaseIndent();
        if (CollectionUtils.isEmpty(list)) {
            indentingPrintWriter.println("configurations");
            indentingPrintWriter.increaseIndent();
            ArrayMap arrayMap4 = intervalStats.configurations;
            int size4 = arrayMap4.size();
            for (int i5 = 0; i5 < size4; i5++) {
                ConfigurationStats configurationStats = (ConfigurationStats) arrayMap4.valueAt(i5);
                indentingPrintWriter.printPair(
                        "config",
                        Configuration.resourceQualifierString(configurationStats.mConfiguration));
                indentingPrintWriter.printPair(
                        "totalTime", formatElapsedTime(configurationStats.mTotalTimeActive, z));
                indentingPrintWriter.printPair(
                        "lastTime", formatDateTime(configurationStats.mLastTimeActive, z));
                indentingPrintWriter.printPair(
                        "count", Integer.valueOf(configurationStats.mActivationCount));
                indentingPrintWriter.println();
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("event aggregations");
            indentingPrintWriter.increaseIndent();
            printEventAggregation(
                    indentingPrintWriter,
                    "screen-interactive",
                    intervalStats.interactiveTracker,
                    z);
            printEventAggregation(
                    indentingPrintWriter,
                    "screen-non-interactive",
                    intervalStats.nonInteractiveTracker,
                    z);
            printEventAggregation(
                    indentingPrintWriter, "keyguard-shown", intervalStats.keyguardShownTracker, z);
            printEventAggregation(
                    indentingPrintWriter,
                    "keyguard-hidden",
                    intervalStats.keyguardHiddenTracker,
                    z);
            indentingPrintWriter.decreaseIndent();
        }
        if (!z2) {
            indentingPrintWriter.println("events");
            indentingPrintWriter.increaseIndent();
            EventList eventList = intervalStats.events;
            int size5 = eventList != null ? eventList.size() : 0;
            while (i2 < size5) {
                UsageEvents.Event event = eventList.get(i2);
                if (!CollectionUtils.isEmpty(list)) {
                    i2 = ((ArrayList) list).contains(event.mPackage) ? 0 : i2 + 1;
                }
                printEvent(indentingPrintWriter, event, z);
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
    }

    public final boolean pruneUninstalledPackagesData() {
        UsageStatsDatabase usageStatsDatabase = this.mDatabase;
        synchronized (usageStatsDatabase.mLock) {
            int i = 0;
            while (true) {
                File[] fileArr = usageStatsDatabase.mIntervalDirs;
                if (i < fileArr.length) {
                    File[] listFiles = fileArr[i].listFiles();
                    if (listFiles != null) {
                        for (int i2 = 0; i2 < listFiles.length; i2++) {
                            try {
                                IntervalStats intervalStats = new IntervalStats();
                                AtomicFile atomicFile = new AtomicFile(listFiles[i2]);
                                if (UsageStatsDatabase.readLocked(
                                        atomicFile,
                                        intervalStats,
                                        usageStatsDatabase.mCurrentVersion,
                                        usageStatsDatabase.mPackagesTokenData,
                                        false)) {
                                    UsageStatsDatabase.writeLocked(
                                            atomicFile,
                                            intervalStats,
                                            usageStatsDatabase.mCurrentVersion,
                                            usageStatsDatabase.mPackagesTokenData);
                                }
                            } catch (Exception unused) {
                                Slog.e(
                                        "UsageStatsDatabase",
                                        "Failed to prune data from: " + listFiles[i2].toString());
                                return false;
                            }
                        }
                    }
                    i++;
                } else {
                    try {
                        usageStatsDatabase.writeMappingsLocked();
                    } catch (IOException unused2) {
                        Slog.e(
                                "UsageStatsDatabase",
                                "Failed to write package mappings after pruning data.");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public final UsageEvents queryEarliestAppEvents(final long j, final long j2) {
        if (!validRange(checkAndGetTimeLocked(), j, j2)) {
            return null;
        }
        final ArraySet arraySet = new ArraySet();
        final ArraySet arraySet2 = new ArraySet();
        List queryStats =
                queryStats(
                        0,
                        j,
                        j2,
                        new UsageStatsDatabase
                                .StatCombiner() { // from class:
                                                  // com.android.server.usage.UserUsageStatsService$$ExternalSyntheticLambda1
                            public final /* synthetic */ int f$4 = 1;

                            @Override // com.android.server.usage.UsageStatsDatabase.StatCombiner
                            public final boolean combine(
                                    IntervalStats intervalStats, boolean z, List list) {
                                ArraySet arraySet3 = arraySet2;
                                ArraySet arraySet4 = arraySet;
                                int size = intervalStats.events.size();
                                for (int firstIndexOnOrAfter =
                                                intervalStats.events.firstIndexOnOrAfter(j);
                                        firstIndexOnOrAfter < size;
                                        firstIndexOnOrAfter++) {
                                    UsageEvents.Event event =
                                            intervalStats.events.get(firstIndexOnOrAfter);
                                    if (event.getTimeStamp() >= j2) {
                                        return false;
                                    }
                                    if (event.getPackageName() != null
                                            && !arraySet3.contains(event.getPackageName())) {
                                        boolean add = arraySet4.add(event.getPackageName());
                                        if (event.getEventType() == this.f$4) {
                                            list.add(event);
                                            arraySet3.add(event.getPackageName());
                                        } else if (add) {
                                            list.add(event);
                                        }
                                    }
                                }
                                return true;
                            }
                        },
                        false);
        if (queryStats == null || queryStats.isEmpty()) {
            return null;
        }
        String[] strArr = (String[]) arraySet.toArray(new String[arraySet.size()]);
        Arrays.sort(strArr);
        return new UsageEvents(queryStats, strArr, false);
    }

    public final UsageEvents queryEarliestEventsForPackage(
            long j, final long j2, final String str) {
        long j3;
        CachedEarlyEvents cachedEarlyEvents;
        List queryStats;
        long checkAndGetTimeLocked = checkAndGetTimeLocked();
        if (!validRange(checkAndGetTimeLocked, j, j2)) {
            return null;
        }
        CachedEarlyEvents cachedEarlyEvents2 =
                (CachedEarlyEvents) this.mCachedEarlyEvents.get(1, str);
        if (cachedEarlyEvents2 == null) {
            cachedEarlyEvents2 = new CachedEarlyEvents();
            cachedEarlyEvents2.searchBeginTime = j;
            this.mCachedEarlyEvents.add(1, str, cachedEarlyEvents2);
        } else {
            if (cachedEarlyEvents2.searchBeginTime <= j && j <= cachedEarlyEvents2.eventTime) {
                List list = cachedEarlyEvents2.events;
                int size = list == null ? 0 : list.size();
                if (size == 0
                        || ((UsageEvents.Event) cachedEarlyEvents2.events.get(size - 1))
                                        .getEventType()
                                != 1) {
                    long j4 = cachedEarlyEvents2.eventTime;
                    if (j4 < j2) {
                        cachedEarlyEvents = cachedEarlyEvents2;
                        j3 = Math.min(checkAndGetTimeLocked, j4);
                        final long j5 = j3;
                        queryStats =
                                queryStats(
                                        0,
                                        j5,
                                        j2,
                                        new UsageStatsDatabase
                                                .StatCombiner() { // from class:
                                                                  // com.android.server.usage.UserUsageStatsService$$ExternalSyntheticLambda2
                                            public final /* synthetic */ int f$3 = 1;

                                            @Override // com.android.server.usage.UsageStatsDatabase.StatCombiner
                                            public final boolean combine(
                                                    IntervalStats intervalStats,
                                                    boolean z,
                                                    List list2) {
                                                int size2 = intervalStats.events.size();
                                                for (int firstIndexOnOrAfter =
                                                                intervalStats.events
                                                                        .firstIndexOnOrAfter(j5);
                                                        firstIndexOnOrAfter < size2;
                                                        firstIndexOnOrAfter++) {
                                                    UsageEvents.Event event =
                                                            intervalStats.events.get(
                                                                    firstIndexOnOrAfter);
                                                    if (event.getTimeStamp() >= j2) {
                                                        return false;
                                                    }
                                                    if (str.equals(event.getPackageName())) {
                                                        if (event.getEventType() == this.f$3) {
                                                            list2.add(event);
                                                            return false;
                                                        }
                                                        if (list2.size() == 0) {
                                                            list2.add(event);
                                                        }
                                                    }
                                                }
                                                return true;
                                            }
                                        },
                                        false);
                        if (queryStats != null || queryStats.isEmpty()) {
                            cachedEarlyEvents.eventTime = Math.min(checkAndGetTimeLocked, j2);
                            cachedEarlyEvents.events = null;
                            return null;
                        }
                        cachedEarlyEvents.eventTime =
                                ((UsageEvents.Event) queryStats.get(queryStats.size() - 1))
                                        .getTimeStamp();
                        cachedEarlyEvents.events = queryStats;
                        return new UsageEvents(queryStats, new String[] {str}, false);
                    }
                }
                if (cachedEarlyEvents2.eventTime > j2 || cachedEarlyEvents2.events == null) {
                    return null;
                }
                return new UsageEvents(cachedEarlyEvents2.events, new String[] {str}, false);
            }
            cachedEarlyEvents2.searchBeginTime = j;
        }
        j3 = j;
        cachedEarlyEvents = cachedEarlyEvents2;
        final long j52 = j3;
        queryStats =
                queryStats(
                        0,
                        j52,
                        j2,
                        new UsageStatsDatabase
                                .StatCombiner() { // from class:
                                                  // com.android.server.usage.UserUsageStatsService$$ExternalSyntheticLambda2
                            public final /* synthetic */ int f$3 = 1;

                            @Override // com.android.server.usage.UsageStatsDatabase.StatCombiner
                            public final boolean combine(
                                    IntervalStats intervalStats, boolean z, List list2) {
                                int size2 = intervalStats.events.size();
                                for (int firstIndexOnOrAfter =
                                                intervalStats.events.firstIndexOnOrAfter(j52);
                                        firstIndexOnOrAfter < size2;
                                        firstIndexOnOrAfter++) {
                                    UsageEvents.Event event =
                                            intervalStats.events.get(firstIndexOnOrAfter);
                                    if (event.getTimeStamp() >= j2) {
                                        return false;
                                    }
                                    if (str.equals(event.getPackageName())) {
                                        if (event.getEventType() == this.f$3) {
                                            list2.add(event);
                                            return false;
                                        }
                                        if (list2.size() == 0) {
                                            list2.add(event);
                                        }
                                    }
                                }
                                return true;
                            }
                        },
                        false);
        if (queryStats != null) {}
        cachedEarlyEvents.eventTime = Math.min(checkAndGetTimeLocked, j2);
        cachedEarlyEvents.events = null;
        return null;
    }

    public final UsageEvents queryEvents(
            final long j, final long j2, final int i, int[] iArr, final ArraySet arraySet) {
        if (!validRange(checkAndGetTimeLocked(), j, j2)) {
            return null;
        }
        final boolean isEmpty = ArrayUtils.isEmpty(iArr);
        final boolean z = arraySet == null || arraySet.isEmpty();
        final boolean[] zArr = new boolean[32];
        if (!isEmpty) {
            for (int i2 : iArr) {
                if (i2 < 0 || i2 > 31) {
                    throw new IllegalArgumentException(
                            VibrationParam$1$$ExternalSyntheticOutline0.m(
                                    i2, "invalid event type: "));
                }
                zArr[i2] = true;
            }
        }
        final ArraySet arraySet2 = new ArraySet();
        List queryStats =
                queryStats(
                        0,
                        j,
                        j2,
                        new UsageStatsDatabase
                                .StatCombiner() { // from class:
                                                  // com.android.server.usage.UserUsageStatsService.4
                            @Override // com.android.server.usage.UsageStatsDatabase.StatCombiner
                            public final boolean combine(
                                    IntervalStats intervalStats, boolean z2, List list) {
                                int size = intervalStats.events.size();
                                for (int firstIndexOnOrAfter =
                                                intervalStats.events.firstIndexOnOrAfter(j);
                                        firstIndexOnOrAfter < size;
                                        firstIndexOnOrAfter++) {
                                    UsageEvents.Event event =
                                            intervalStats.events.get(firstIndexOnOrAfter);
                                    if (event.mTimeStamp >= j2) {
                                        return false;
                                    }
                                    int i3 = event.mEventType;
                                    if (isEmpty || zArr[i3]) {
                                        int i4 = i;
                                        if ((i3 != 8 || (i4 & 2) != 2)
                                                && (i3 != 30 || (i4 & 8) != 8)) {
                                            if ((i3 == 10 || i3 == 12) && (i4 & 4) == 4) {
                                                event = event.getObfuscatedNotificationEvent();
                                            }
                                            if ((i4 & 1) == 1) {
                                                event = event.getObfuscatedIfInstantApp();
                                            }
                                            if (z || arraySet.contains(event.mPackage)) {
                                                String str = event.mPackage;
                                                if (str != null) {
                                                    arraySet2.add(str);
                                                }
                                                String str2 = event.mClass;
                                                if (str2 != null) {
                                                    arraySet2.add(str2);
                                                }
                                                String str3 = event.mTaskRootPackage;
                                                if (str3 != null) {
                                                    arraySet2.add(str3);
                                                }
                                                String str4 = event.mTaskRootClass;
                                                if (str4 != null) {
                                                    arraySet2.add(str4);
                                                }
                                                list.add(event);
                                            }
                                        }
                                    }
                                }
                                return true;
                            }
                        },
                        false);
        if (queryStats == null || queryStats.isEmpty()) {
            return null;
        }
        String[] strArr = (String[]) arraySet2.toArray(new String[arraySet2.size()]);
        Arrays.sort(strArr);
        return new UsageEvents(queryStats, strArr, true);
    }

    public final UsageEvents queryEventsForPackage(
            final long j, final long j2, final String str, final boolean z) {
        if (!validRange(checkAndGetTimeLocked(), j, j2)) {
            return null;
        }
        final ArraySet arraySet = new ArraySet();
        arraySet.add(str);
        List queryStats =
                queryStats(
                        0,
                        j,
                        j2,
                        new UsageStatsDatabase
                                .StatCombiner() { // from class:
                                                  // com.android.server.usage.UserUsageStatsService$$ExternalSyntheticLambda0
                            @Override // com.android.server.usage.UsageStatsDatabase.StatCombiner
                            public final boolean combine(
                                    IntervalStats intervalStats, boolean z2, List list) {
                                String str2;
                                String str3;
                                ArraySet arraySet2 = arraySet;
                                int size = intervalStats.events.size();
                                for (int firstIndexOnOrAfter =
                                                intervalStats.events.firstIndexOnOrAfter(j);
                                        firstIndexOnOrAfter < size;
                                        firstIndexOnOrAfter++) {
                                    UsageEvents.Event event =
                                            intervalStats.events.get(firstIndexOnOrAfter);
                                    if (event.mTimeStamp >= j2) {
                                        return false;
                                    }
                                    if (str.equals(event.mPackage)) {
                                        String str4 = event.mClass;
                                        if (str4 != null) {
                                            arraySet2.add(str4);
                                        }
                                        boolean z3 = z;
                                        if (z3 && (str3 = event.mTaskRootPackage) != null) {
                                            arraySet2.add(str3);
                                        }
                                        if (z3 && (str2 = event.mTaskRootClass) != null) {
                                            arraySet2.add(str2);
                                        }
                                        list.add(event);
                                    }
                                }
                                return true;
                            }
                        },
                        false);
        if (queryStats == null || queryStats.isEmpty()) {
            return null;
        }
        String[] strArr = (String[]) arraySet.toArray(new String[arraySet.size()]);
        Arrays.sort(strArr);
        return new UsageEvents(queryStats, strArr, z);
    }

    public final List queryStats(
            int i, long j, long j2, UsageStatsDatabase.StatCombiner statCombiner, boolean z) {
        int i2;
        int i3 = i;
        if (i3 == 4) {
            UsageStatsDatabase usageStatsDatabase = this.mDatabase;
            synchronized (usageStatsDatabase.mLock) {
                try {
                    i2 = -1;
                    long j3 = Long.MAX_VALUE;
                    for (int length = usageStatsDatabase.mSortedStatFiles.length - 1;
                            length >= 0;
                            length--) {
                        int lastIndexOnOrBefore =
                                usageStatsDatabase.mSortedStatFiles[length].lastIndexOnOrBefore(j);
                        int size = usageStatsDatabase.mSortedStatFiles[length].size();
                        if (lastIndexOnOrBefore >= 0 && lastIndexOnOrBefore < size) {
                            long abs =
                                    Math.abs(
                                            usageStatsDatabase.mSortedStatFiles[length].keyAt(
                                                            lastIndexOnOrBefore)
                                                    - j);
                            if (abs < j3) {
                                i2 = length;
                                j3 = abs;
                            }
                        }
                    }
                } finally {
                }
            }
            i3 = i2 < 0 ? 0 : i2;
        }
        ArrayList arrayList = null;
        if (i3 >= 0) {
            IntervalStats[] intervalStatsArr = this.mCurrentStats;
            if (i3 < intervalStatsArr.length) {
                IntervalStats intervalStats = intervalStatsArr[i3];
                if (j >= intervalStats.endTime) {
                    return null;
                }
                long min = Math.min(intervalStats.beginTime, j2);
                UsageStatsDatabase usageStatsDatabase2 = this.mDatabase;
                if (i3 < 0) {
                    usageStatsDatabase2.getClass();
                } else if (i3 < usageStatsDatabase2.mIntervalDirs.length) {
                    if (min > j) {
                        synchronized (usageStatsDatabase2.mLock) {
                            try {
                                LongSparseArray longSparseArray =
                                        usageStatsDatabase2.mSortedStatFiles[i3];
                                int lastIndexOnOrBefore2 = longSparseArray.lastIndexOnOrBefore(min);
                                if (lastIndexOnOrBefore2 >= 0) {
                                    if (longSparseArray.keyAt(lastIndexOnOrBefore2) != min
                                            || lastIndexOnOrBefore2 - 1 >= 0) {
                                        int lastIndexOnOrBefore3 =
                                                longSparseArray.lastIndexOnOrBefore(j);
                                        if (lastIndexOnOrBefore3 < 0) {
                                            lastIndexOnOrBefore3 = 0;
                                        }
                                        ArrayList arrayList2 = new ArrayList();
                                        for (int i4 = lastIndexOnOrBefore3;
                                                i4 <= lastIndexOnOrBefore2;
                                                i4++) {
                                            AtomicFile atomicFile =
                                                    (AtomicFile) longSparseArray.valueAt(i4);
                                            IntervalStats intervalStats2 = new IntervalStats();
                                            try {
                                                usageStatsDatabase2.readLocked(
                                                        atomicFile, intervalStats2, z);
                                                if (j < intervalStats2.endTime) {
                                                    try {
                                                        if (!statCombiner.combine(
                                                                intervalStats2,
                                                                false,
                                                                arrayList2)) {
                                                            break;
                                                        }
                                                    } catch (Exception e) {
                                                        e = e;
                                                        Slog.e(
                                                                "UsageStatsDatabase",
                                                                "Failed to read usage stats file",
                                                                e);
                                                    }
                                                }
                                            } catch (Exception e2) {
                                                e = e2;
                                            }
                                        }
                                        arrayList = arrayList2;
                                    }
                                }
                            } finally {
                            }
                        }
                    }
                    if (j < intervalStats.endTime && j2 > intervalStats.beginTime) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        PackagesTokenData packagesTokenData = this.mDatabase.mPackagesTokenData;
                        if (!packagesTokenData.removedPackagesMap.isEmpty()) {
                            ArrayMap arrayMap = packagesTokenData.removedPackagesMap;
                            int size2 = arrayMap.size();
                            for (int i5 = 0; i5 < size2; i5++) {
                                String str = (String) arrayMap.keyAt(i5);
                                UsageStats usageStats =
                                        (UsageStats) intervalStats.packageStats.get(str);
                                if (usageStats != null
                                        && usageStats.mEndTimeStamp
                                                < ((Long) arrayMap.valueAt(i5)).longValue()) {
                                    intervalStats.packageStats.remove(str);
                                }
                            }
                            for (int size3 = intervalStats.events.size() - 1; size3 >= 0; size3--) {
                                UsageEvents.Event event = intervalStats.events.get(size3);
                                Long l = (Long) arrayMap.get(event.mPackage);
                                if (l != null && l.longValue() > event.mTimeStamp) {
                                    intervalStats.events.remove(size3);
                                }
                            }
                        }
                        statCombiner.combine(intervalStats, true, arrayList);
                    }
                    return arrayList;
                }
                throw new IllegalArgumentException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(i3, "Bad interval type "));
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:116:0x035e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void reportEvent(android.app.usage.UsageEvents.Event r33) {
        /*
            Method dump skipped, instructions count: 1034
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.usage.UserUsageStatsService.reportEvent(android.app.usage.UsageEvents$Event):void");
    }

    public final boolean updatePackageMappingsLocked(HashMap hashMap) {
        if (ArrayUtils.isEmpty(hashMap)) {
            return true;
        }
        long currentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        UsageStatsDatabase usageStatsDatabase = this.mDatabase;
        for (int size = usageStatsDatabase.mPackagesTokenData.packagesToTokensMap.size() - 1;
                size >= 0;
                size--) {
            String str =
                    (String) usageStatsDatabase.mPackagesTokenData.packagesToTokensMap.keyAt(size);
            if (!hashMap.containsKey(str)) {
                arrayList.add(str);
            }
        }
        if (arrayList.isEmpty()) {
            return true;
        }
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            usageStatsDatabase.mPackagesTokenData.removePackage(
                    currentTimeMillis, (String) arrayList.get(size2));
        }
        try {
            usageStatsDatabase.writeMappingsLocked();
            return true;
        } catch (Exception unused) {
            Slog.w(
                    "UsageStatsService",
                    "Unable to write updated package mappings file on service initialization.");
            return false;
        }
    }

    public final void updateRolloverDeadline() {
        long j = this.mCurrentStats[0].beginTime;
        UnixCalendar unixCalendar = this.mDailyExpiryDate;
        unixCalendar.mTime =
                (1 * BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS) + j;
        StringBuilder sb = new StringBuilder();
        sb.append(this.mLogPrefix);
        sb.append("Rollover scheduled @ ");
        sb.append(sDateFormat.format(Long.valueOf(unixCalendar.mTime)));
        sb.append("(");
        String m = AudioConfig$$ExternalSyntheticOutline0.m(sb, unixCalendar.mTime, ")");
        StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(m, " ");
        m2.append(Debug.getCallers(6));
        addBufferLog(m2.toString());
        Slog.i("UsageStatsService", m);
    }
}
