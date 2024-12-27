package com.android.server.usage;

import android.app.usage.ConfigurationStats;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.content.res.Configuration;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class UsageStatsProto {
    public static void loadChooserCounts(ProtoInputStream protoInputStream, UsageStats usageStats) {
        ArrayMap arrayMap;
        String str;
        if (usageStats.mChooserCounts == null) {
            usageStats.mChooserCounts = new ArrayMap();
        }
        String str2 = null;
        if (protoInputStream.nextField(1138166333441L)) {
            str = protoInputStream.readString(1138166333441L);
            arrayMap = (ArrayMap) usageStats.mChooserCounts.get(str);
            if (arrayMap == null) {
                arrayMap = new ArrayMap();
                usageStats.mChooserCounts.put(str, arrayMap);
            }
        } else {
            arrayMap = new ArrayMap();
            str = null;
        }
        while (true) {
            int nextField = protoInputStream.nextField();
            if (nextField == -1) {
                break;
            }
            if (nextField == 1) {
                str = protoInputStream.readString(1138166333441L);
                usageStats.mChooserCounts.put(str, arrayMap);
            } else if (nextField == 3) {
                long start = protoInputStream.start(2246267895811L);
                int i = 0;
                while (true) {
                    int nextField2 = protoInputStream.nextField();
                    if (nextField2 == -1) {
                        break;
                    }
                    if (nextField2 == 1) {
                        str2 = protoInputStream.readString(1138166333441L);
                    } else if (nextField2 == 3) {
                        i = protoInputStream.readInt(1120986464259L);
                    }
                }
                if (str2 == null) {
                    arrayMap.put("", Integer.valueOf(i));
                } else {
                    arrayMap.put(str2, Integer.valueOf(i));
                }
                protoInputStream.end(start);
            }
        }
        if (str == null) {
            usageStats.mChooserCounts.put("", arrayMap);
        }
    }

    public static void loadConfigStats(
            ProtoInputStream protoInputStream, IntervalStats intervalStats) {
        ConfigurationStats configurationStats;
        long start = protoInputStream.start(2246267895829L);
        Configuration configuration = new Configuration();
        boolean z = false;
        if (protoInputStream.nextField(1146756268033L)) {
            configuration.readFromProto(protoInputStream, 1146756268033L);
            configurationStats = intervalStats.getOrCreateConfigurationStats(configuration);
        } else {
            configurationStats = new ConfigurationStats();
        }
        while (true) {
            int nextField = protoInputStream.nextField();
            if (nextField == -1) {
                break;
            }
            if (nextField == 1) {
                configuration.readFromProto(protoInputStream, 1146756268033L);
                ConfigurationStats orCreateConfigurationStats =
                        intervalStats.getOrCreateConfigurationStats(configuration);
                orCreateConfigurationStats.mLastTimeActive = configurationStats.mLastTimeActive;
                orCreateConfigurationStats.mTotalTimeActive = configurationStats.mTotalTimeActive;
                orCreateConfigurationStats.mActivationCount = configurationStats.mActivationCount;
                configurationStats = orCreateConfigurationStats;
            } else if (nextField == 2) {
                configurationStats.mLastTimeActive =
                        protoInputStream.readLong(1112396529666L) + intervalStats.beginTime;
            } else if (nextField == 3) {
                configurationStats.mTotalTimeActive = protoInputStream.readLong(1112396529667L);
            } else if (nextField == 4) {
                configurationStats.mActivationCount = protoInputStream.readInt(1120986464260L);
            } else if (nextField == 5) {
                z = protoInputStream.readBoolean(1133871366149L);
            }
        }
        if (z) {
            intervalStats.activeConfiguration = configurationStats.mConfiguration;
        }
        protoInputStream.end(start);
    }

    public static void loadCountAndTime(
            ProtoInputStream protoInputStream, long j, IntervalStats.EventTracker eventTracker) {
        try {
            long start = protoInputStream.start(j);
            while (true) {
                int nextField = protoInputStream.nextField();
                if (nextField == -1) {
                    protoInputStream.end(start);
                    return;
                } else if (nextField == 1) {
                    eventTracker.count = protoInputStream.readInt(1120986464257L);
                } else if (nextField == 2) {
                    eventTracker.duration = protoInputStream.readLong(1112396529666L);
                }
            }
        } catch (IOException e) {
            Slog.e("UsageStatsProto", "Unable to read event tracker " + j, e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x013b, code lost:

       r9 = r2.mEventType;
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x013e, code lost:

       if (r9 == 5) goto L40;
    */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0144, code lost:

       if (r9 == 8) goto L37;
    */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0148, code lost:

       if (r9 == 12) goto L34;
    */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x014c, code lost:

       if (r9 == 30) goto L31;
    */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0151, code lost:

       if (r2.mLocusId != null) goto L43;
    */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0153, code lost:

       r2.mLocusId = "";
    */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x016f, code lost:

       r7.end(r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0174, code lost:

       if (r2.mPackage == null) goto L47;
    */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0176, code lost:

       r8.events.insert(r2);
    */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x017b, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0184, code lost:

       throw new java.net.ProtocolException("no package field present");
    */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0158, code lost:

       if (r2.mNotificationChannelId != null) goto L43;
    */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x015a, code lost:

       r2.mNotificationChannelId = "";
    */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x015f, code lost:

       if (r2.mShortcutId != null) goto L43;
    */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0161, code lost:

       r2.mShortcutId = "";
    */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0166, code lost:

       if (r2.mConfiguration != null) goto L43;
    */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0168, code lost:

       r2.mConfiguration = new android.content.res.Configuration();
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void loadEvent(
            android.util.proto.ProtoInputStream r7,
            com.android.server.usage.IntervalStats r8,
            java.util.List r9) {
        /*
            Method dump skipped, instructions count: 432
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.usage.UsageStatsProto.loadEvent(android.util.proto.ProtoInputStream,"
                    + " com.android.server.usage.IntervalStats, java.util.List):void");
    }

    public static void loadUsageStats(
            ProtoInputStream protoInputStream, IntervalStats intervalStats, List list) {
        UsageStats orCreateUsageStats;
        long start = protoInputStream.start(2246267895828L);
        UsageStats orCreateUsageStats2 =
                protoInputStream.nextField(1120986464258L)
                        ? intervalStats.getOrCreateUsageStats(
                                (String) list.get(protoInputStream.readInt(1120986464258L) - 1))
                        : protoInputStream.nextField(1138166333441L)
                                ? intervalStats.getOrCreateUsageStats(
                                        protoInputStream.readString(1138166333441L))
                                : new UsageStats();
        while (protoInputStream.nextField() != -1) {
            switch (protoInputStream.getFieldNumber()) {
                case 1:
                    orCreateUsageStats =
                            intervalStats.getOrCreateUsageStats(
                                    protoInputStream.readString(1138166333441L));
                    orCreateUsageStats.mLastTimeUsed = orCreateUsageStats2.mLastTimeUsed;
                    orCreateUsageStats.mTotalTimeInForeground =
                            orCreateUsageStats2.mTotalTimeInForeground;
                    orCreateUsageStats.mLastEvent = orCreateUsageStats2.mLastEvent;
                    orCreateUsageStats.mAppLaunchCount = orCreateUsageStats2.mAppLaunchCount;
                    break;
                case 2:
                    orCreateUsageStats =
                            intervalStats.getOrCreateUsageStats(
                                    (String)
                                            list.get(protoInputStream.readInt(1120986464258L) - 1));
                    orCreateUsageStats.mLastTimeUsed = orCreateUsageStats2.mLastTimeUsed;
                    orCreateUsageStats.mTotalTimeInForeground =
                            orCreateUsageStats2.mTotalTimeInForeground;
                    orCreateUsageStats.mLastEvent = orCreateUsageStats2.mLastEvent;
                    orCreateUsageStats.mAppLaunchCount = orCreateUsageStats2.mAppLaunchCount;
                    break;
                case 3:
                    orCreateUsageStats2.mLastTimeUsed =
                            protoInputStream.readLong(1112396529667L) + intervalStats.beginTime;
                    continue;
                case 4:
                    orCreateUsageStats2.mTotalTimeInForeground =
                            protoInputStream.readLong(1112396529668L);
                    continue;
                case 5:
                    orCreateUsageStats2.mLastEvent = protoInputStream.readInt(1120986464261L);
                    continue;
                case 6:
                    orCreateUsageStats2.mAppLaunchCount = protoInputStream.readInt(1120986464262L);
                    continue;
                case 7:
                    try {
                        long start2 = protoInputStream.start(2246267895815L);
                        loadChooserCounts(protoInputStream, orCreateUsageStats2);
                        protoInputStream.end(start2);
                        continue;
                    } catch (IOException e) {
                        Slog.e(
                                "UsageStatsProto",
                                "Unable to read chooser counts for "
                                        + orCreateUsageStats2.mPackageName,
                                e);
                        break;
                    }
                case 8:
                    orCreateUsageStats2.mLastTimeForegroundServiceUsed =
                            protoInputStream.readLong(1112396529672L) + intervalStats.beginTime;
                    continue;
                case 9:
                    orCreateUsageStats2.mTotalTimeForegroundServiceUsed =
                            protoInputStream.readLong(1112396529673L);
                    continue;
                case 10:
                    orCreateUsageStats2.mLastTimeVisible =
                            protoInputStream.readLong(1112396529674L) + intervalStats.beginTime;
                    continue;
                case 11:
                    orCreateUsageStats2.mTotalTimeVisible =
                            protoInputStream.readLong(1112396529675L);
                    continue;
                case 12:
                    orCreateUsageStats2.mLastTimeComponentUsed =
                            protoInputStream.readLong(1112396529676L) + intervalStats.beginTime;
                    continue;
            }
            orCreateUsageStats2 = orCreateUsageStats;
        }
        protoInputStream.end(start);
    }

    public static void read(InputStream inputStream, IntervalStats intervalStats) {
        ArrayList arrayList;
        IOException e;
        ProtoInputStream protoInputStream = new ProtoInputStream(inputStream);
        intervalStats.packageStats.clear();
        intervalStats.configurations.clear();
        ArrayList arrayList2 = null;
        intervalStats.activeConfiguration = null;
        intervalStats.events.clear();
        while (true) {
            int nextField = protoInputStream.nextField();
            if (nextField == -1) {
                if (intervalStats.majorVersion >= 1) {
                    return;
                }
                intervalStats.majorVersion = 1;
                return;
            }
            if (nextField == 1) {
                intervalStats.endTime =
                        protoInputStream.readLong(1112396529665L) + intervalStats.beginTime;
            } else if (nextField == 2) {
                try {
                    long start = protoInputStream.start(1146756268034L);
                    arrayList =
                            protoInputStream.nextField(1120986464257L)
                                    ? new ArrayList(protoInputStream.readInt(1120986464257L))
                                    : new ArrayList();
                    while (protoInputStream.nextField() != -1) {
                        if (protoInputStream.getFieldNumber() == 2) {
                            arrayList.add(protoInputStream.readString(2237677961218L));
                        }
                    }
                    protoInputStream.end(start);
                    try {
                        intervalStats.mStringCache.addAll(arrayList);
                    } catch (IOException e2) {
                        e = e2;
                        Slog.e("UsageStatsProto", "Unable to read string pool from proto.", e);
                        arrayList2 = arrayList;
                    }
                } catch (IOException e3) {
                    arrayList = arrayList2;
                    e = e3;
                }
                arrayList2 = arrayList;
            } else if (nextField == 3) {
                intervalStats.majorVersion = protoInputStream.readInt(1120986464259L);
            } else if (nextField != 4) {
                switch (nextField) {
                    case 10:
                        loadCountAndTime(
                                protoInputStream, 1146756268042L, intervalStats.interactiveTracker);
                        break;
                    case 11:
                        loadCountAndTime(
                                protoInputStream,
                                1146756268043L,
                                intervalStats.nonInteractiveTracker);
                        break;
                    case 12:
                        loadCountAndTime(
                                protoInputStream,
                                1146756268044L,
                                intervalStats.keyguardShownTracker);
                        break;
                    case 13:
                        loadCountAndTime(
                                protoInputStream,
                                1146756268045L,
                                intervalStats.keyguardHiddenTracker);
                        break;
                    default:
                        switch (nextField) {
                            case 20:
                                try {
                                    loadUsageStats(protoInputStream, intervalStats, arrayList2);
                                    break;
                                } catch (IOException e4) {
                                    Slog.e(
                                            "UsageStatsProto",
                                            "Unable to read some usage stats from proto.",
                                            e4);
                                    break;
                                }
                            case 21:
                                try {
                                    loadConfigStats(protoInputStream, intervalStats);
                                    break;
                                } catch (IOException e5) {
                                    Slog.e(
                                            "UsageStatsProto",
                                            "Unable to read some configuration stats from proto.",
                                            e5);
                                    break;
                                }
                            case 22:
                                try {
                                    loadEvent(protoInputStream, intervalStats, arrayList2);
                                    break;
                                } catch (IOException e6) {
                                    Slog.e(
                                            "UsageStatsProto",
                                            "Unable to read some events from proto.",
                                            e6);
                                    break;
                                }
                        }
                }
            } else {
                intervalStats.minorVersion = protoInputStream.readInt(1120986464260L);
            }
        }
    }

    public static void write(OutputStream outputStream, IntervalStats intervalStats) {
        long j;
        int i;
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(outputStream);
        long j2 = intervalStats.endTime;
        long j3 = intervalStats.beginTime;
        int i2 = UsageStatsProtoV2.$r8$clinit;
        long j4 = j2 - j3;
        if (j4 == 0) {
            j4++;
        }
        protoOutputStream.write(1112396529665L, j4);
        protoOutputStream.write(1120986464259L, intervalStats.majorVersion);
        long j5 = 1120986464260L;
        protoOutputStream.write(1120986464260L, intervalStats.minorVersion);
        try {
            long start = protoOutputStream.start(1146756268034L);
            int size = intervalStats.mStringCache.size();
            protoOutputStream.write(1120986464257L, size);
            for (int i3 = 0; i3 < size; i3++) {
                protoOutputStream.write(
                        2237677961218L, (String) intervalStats.mStringCache.valueAt(i3));
            }
            protoOutputStream.end(start);
        } catch (IllegalArgumentException e) {
            Slog.e("UsageStatsProto", "Unable to write string pool to proto.", e);
        }
        try {
            IntervalStats.EventTracker eventTracker = intervalStats.interactiveTracker;
            writeCountAndTime(
                    eventTracker.count, 1146756268042L, eventTracker.duration, protoOutputStream);
            IntervalStats.EventTracker eventTracker2 = intervalStats.nonInteractiveTracker;
            writeCountAndTime(
                    eventTracker2.count, 1146756268043L, eventTracker2.duration, protoOutputStream);
            IntervalStats.EventTracker eventTracker3 = intervalStats.keyguardShownTracker;
            writeCountAndTime(
                    eventTracker3.count, 1146756268044L, eventTracker3.duration, protoOutputStream);
            IntervalStats.EventTracker eventTracker4 = intervalStats.keyguardHiddenTracker;
            writeCountAndTime(
                    eventTracker4.count, 1146756268045L, eventTracker4.duration, protoOutputStream);
        } catch (IllegalArgumentException e2) {
            Slog.e("UsageStatsProto", "Unable to write some interval stats trackers to proto.", e2);
        }
        int size2 = intervalStats.packageStats.size();
        for (int i4 = 0; i4 < size2; i4++) {
            try {
                writeUsageStats(
                        protoOutputStream,
                        intervalStats,
                        (UsageStats) intervalStats.packageStats.valueAt(i4));
            } catch (IllegalArgumentException e3) {
                Slog.e("UsageStatsProto", "Unable to write some usage stats to proto.", e3);
            }
        }
        int size3 = intervalStats.configurations.size();
        int i5 = 0;
        while (i5 < size3) {
            boolean equals =
                    intervalStats.activeConfiguration.equals(
                            (Configuration) intervalStats.configurations.keyAt(i5));
            try {
                ConfigurationStats configurationStats =
                        (ConfigurationStats) intervalStats.configurations.valueAt(i5);
                long start2 = protoOutputStream.start(2246267895829L);
                configurationStats.mConfiguration.dumpDebug(protoOutputStream, 1146756268033L);
                i = size3;
                try {
                    try {
                        UsageStatsProtoV2.writeOffsetTimestamp(
                                protoOutputStream,
                                1112396529666L,
                                configurationStats.mLastTimeActive,
                                intervalStats.beginTime);
                        protoOutputStream.write(
                                1112396529667L, configurationStats.mTotalTimeActive);
                        j = 1120986464260L;
                        try {
                            protoOutputStream.write(
                                    1120986464260L, configurationStats.mActivationCount);
                            protoOutputStream.write(1133871366149L, equals);
                            protoOutputStream.end(start2);
                        } catch (IllegalArgumentException e4) {
                            e = e4;
                            Slog.e(
                                    "UsageStatsProto",
                                    "Unable to write some configuration stats to proto.",
                                    e);
                            i5++;
                            j5 = j;
                            size3 = i;
                        }
                    } catch (IllegalArgumentException e5) {
                        e = e5;
                        j = 1120986464260L;
                    }
                } catch (IllegalArgumentException e6) {
                    e = e6;
                    j = j5;
                }
            } catch (IllegalArgumentException e7) {
                e = e7;
                j = j5;
                i = size3;
            }
            i5++;
            j5 = j;
            size3 = i;
        }
        int size4 = intervalStats.events.size();
        for (int i6 = 0; i6 < size4; i6++) {
            try {
                writeEvent(protoOutputStream, intervalStats, intervalStats.events.get(i6));
            } catch (IllegalArgumentException e8) {
                Slog.e("UsageStatsProto", "Unable to write some events to proto.", e8);
            }
        }
        protoOutputStream.flush();
    }

    public static void writeChooserCounts(
            ProtoOutputStream protoOutputStream, UsageStats usageStats) {
        ArrayMap arrayMap;
        if (usageStats == null
                || (arrayMap = usageStats.mChooserCounts) == null
                || arrayMap.keySet().isEmpty()) {
            return;
        }
        int size = usageStats.mChooserCounts.size();
        for (int i = 0; i < size; i++) {
            String str = (String) usageStats.mChooserCounts.keyAt(i);
            ArrayMap arrayMap2 = (ArrayMap) usageStats.mChooserCounts.valueAt(i);
            if (str != null && arrayMap2 != null && !arrayMap2.isEmpty()) {
                long start = protoOutputStream.start(2246267895815L);
                long j = 1138166333441L;
                protoOutputStream.write(1138166333441L, str);
                int size2 = arrayMap2.size();
                int i2 = 0;
                while (i2 < size2) {
                    String str2 = (String) arrayMap2.keyAt(i2);
                    int intValue = ((Integer) arrayMap2.valueAt(i2)).intValue();
                    if (intValue > 0) {
                        long start2 = protoOutputStream.start(2246267895811L);
                        protoOutputStream.write(j, str2);
                        protoOutputStream.write(1120986464259L, intValue);
                        protoOutputStream.end(start2);
                    }
                    i2++;
                    j = 1138166333441L;
                }
                protoOutputStream.end(start);
            }
        }
    }

    public static void writeCountAndTime(
            int i, long j, long j2, ProtoOutputStream protoOutputStream) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, i);
        protoOutputStream.write(1112396529666L, j2);
        protoOutputStream.end(start);
    }

    public static void writeEvent(
            ProtoOutputStream protoOutputStream,
            IntervalStats intervalStats,
            UsageEvents.Event event) {
        int indexOf;
        String str;
        int indexOf2;
        int indexOf3;
        long start = protoOutputStream.start(2246267895830L);
        int indexOf4 = intervalStats.mStringCache.indexOf(event.mPackage);
        if (indexOf4 >= 0) {
            protoOutputStream.write(1120986464258L, indexOf4 + 1);
        } else {
            protoOutputStream.write(1138166333441L, event.mPackage);
        }
        String str2 = event.mClass;
        if (str2 != null) {
            int indexOf5 = intervalStats.mStringCache.indexOf(str2);
            if (indexOf5 >= 0) {
                protoOutputStream.write(1120986464260L, indexOf5 + 1);
            } else {
                protoOutputStream.write(1138166333443L, event.mClass);
            }
        }
        UsageStatsProtoV2.writeOffsetTimestamp(
                protoOutputStream, 1112396529669L, event.mTimeStamp, intervalStats.beginTime);
        protoOutputStream.write(1120986464262L, event.mFlags);
        protoOutputStream.write(1120986464263L, event.mEventType);
        protoOutputStream.write(1120986464270L, event.mInstanceId);
        String str3 = event.mTaskRootPackage;
        if (str3 != null && (indexOf3 = intervalStats.mStringCache.indexOf(str3)) >= 0) {
            protoOutputStream.write(1120986464271L, indexOf3 + 1);
        }
        String str4 = event.mTaskRootClass;
        if (str4 != null && (indexOf2 = intervalStats.mStringCache.indexOf(str4)) >= 0) {
            protoOutputStream.write(1120986464272L, indexOf2 + 1);
        }
        int i = event.mEventType;
        if (i == 5) {
            Configuration configuration = event.mConfiguration;
            if (configuration != null) {
                configuration.dumpDebug(protoOutputStream, 1146756268040L);
            }
        } else if (i == 8) {
            String str5 = event.mShortcutId;
            if (str5 != null) {
                protoOutputStream.write(1138166333449L, str5);
            }
        } else if (i == 30) {
            String str6 = event.mLocusId;
            if (str6 != null && (indexOf = intervalStats.mStringCache.indexOf(str6)) >= 0) {
                protoOutputStream.write(1120986464273L, indexOf + 1);
            }
        } else if (i == 11) {
            int i2 = event.mBucketAndReason;
            if (i2 != 0) {
                protoOutputStream.write(1120986464267L, i2);
            }
        } else if (i == 12 && (str = event.mNotificationChannelId) != null) {
            int indexOf6 = intervalStats.mStringCache.indexOf(str);
            if (indexOf6 >= 0) {
                protoOutputStream.write(1120986464269L, indexOf6 + 1);
            } else {
                protoOutputStream.write(1138166333452L, event.mNotificationChannelId);
            }
        }
        protoOutputStream.end(start);
    }

    public static void writeUsageStats(
            ProtoOutputStream protoOutputStream,
            IntervalStats intervalStats,
            UsageStats usageStats) {
        long start = protoOutputStream.start(2246267895828L);
        int indexOf = intervalStats.mStringCache.indexOf(usageStats.mPackageName);
        if (indexOf >= 0) {
            protoOutputStream.write(1120986464258L, indexOf + 1);
        } else {
            protoOutputStream.write(1138166333441L, usageStats.mPackageName);
        }
        UsageStatsProtoV2.writeOffsetTimestamp(
                protoOutputStream,
                1112396529667L,
                usageStats.mLastTimeUsed,
                intervalStats.beginTime);
        protoOutputStream.write(1112396529668L, usageStats.mTotalTimeInForeground);
        protoOutputStream.write(1120986464261L, usageStats.mLastEvent);
        UsageStatsProtoV2.writeOffsetTimestamp(
                protoOutputStream,
                1112396529672L,
                usageStats.mLastTimeForegroundServiceUsed,
                intervalStats.beginTime);
        protoOutputStream.write(1112396529673L, usageStats.mTotalTimeForegroundServiceUsed);
        UsageStatsProtoV2.writeOffsetTimestamp(
                protoOutputStream,
                1112396529674L,
                usageStats.mLastTimeVisible,
                intervalStats.beginTime);
        protoOutputStream.write(1112396529675L, usageStats.mTotalTimeVisible);
        UsageStatsProtoV2.writeOffsetTimestamp(
                protoOutputStream,
                1112396529676L,
                usageStats.mLastTimeComponentUsed,
                intervalStats.beginTime);
        protoOutputStream.write(1120986464262L, usageStats.mAppLaunchCount);
        try {
            writeChooserCounts(protoOutputStream, usageStats);
        } catch (IllegalArgumentException e) {
            Slog.e(
                    "UsageStatsProto",
                    "Unable to write chooser counts for " + usageStats.mPackageName,
                    e);
        }
        protoOutputStream.end(start);
    }
}
