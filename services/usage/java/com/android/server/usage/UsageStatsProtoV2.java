package com.android.server.usage;

import android.app.usage.ConfigurationStats;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.content.res.Configuration;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public abstract class UsageStatsProtoV2 {
  public static final long ONE_HOUR_MS = TimeUnit.HOURS.toMillis(1);

  public static long getOffsetTimestamp(long j, long j2) {
    long j3 = j - j2;
    return j3 == 0 ? j3 + 1 : j3;
  }

  public static UsageStats parseUsageStats(ProtoInputStream protoInputStream, long j) {
    UsageStats usageStats = new UsageStats();
    while (true) {
      int nextField = protoInputStream.nextField();
      if (nextField == -1) {
        return usageStats;
      }
      if (nextField == 1) {
        usageStats.mPackageToken = protoInputStream.readInt(1120986464257L) - 1;
      } else if (nextField == 3) {
        usageStats.mLastTimeUsed = protoInputStream.readLong(1112396529667L) + j;
      } else if (nextField == 4) {
        usageStats.mTotalTimeInForeground = protoInputStream.readLong(1112396529668L);
      } else {
        switch (nextField) {
          case 6:
            usageStats.mAppLaunchCount = protoInputStream.readInt(1120986464262L);
            break;
          case 7:
            try {
              long start = protoInputStream.start(2246267895815L);
              loadChooserCounts(protoInputStream, usageStats);
              protoInputStream.end(start);
              break;
            } catch (IOException unused) {
              Slog.e(
                  "UsageStatsProtoV2",
                  "Unable to read chooser counts for " + usageStats.mPackageToken);
              break;
            }
          case 8:
            usageStats.mLastTimeForegroundServiceUsed =
                protoInputStream.readLong(1112396529672L) + j;
            break;
          case 9:
            usageStats.mTotalTimeForegroundServiceUsed = protoInputStream.readLong(1112396529673L);
            break;
          case 10:
            usageStats.mLastTimeVisible = protoInputStream.readLong(1112396529674L) + j;
            break;
          case 11:
            usageStats.mTotalTimeVisible = protoInputStream.readLong(1112396529675L);
            break;
          case 12:
            usageStats.mLastTimeComponentUsed = protoInputStream.readLong(1112396529676L) + j;
            break;
        }
      }
    }
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
      Slog.e("UsageStatsProtoV2", "Unable to read event tracker " + j, e);
    }
  }

  public static void loadChooserCounts(ProtoInputStream protoInputStream, UsageStats usageStats) {
    SparseIntArray sparseIntArray;
    if (protoInputStream.nextField(1120986464257L)) {
      int readInt = protoInputStream.readInt(1120986464257L) - 1;
      sparseIntArray = (SparseIntArray) usageStats.mChooserCountsObfuscated.get(readInt);
      if (sparseIntArray == null) {
        sparseIntArray = new SparseIntArray();
        usageStats.mChooserCountsObfuscated.put(readInt, sparseIntArray);
      }
    } else {
      sparseIntArray = new SparseIntArray();
    }
    while (true) {
      int nextField = protoInputStream.nextField();
      if (nextField == -1) {
        return;
      }
      if (nextField == 1) {
        usageStats.mChooserCountsObfuscated.put(
            protoInputStream.readInt(1120986464257L) - 1, sparseIntArray);
      } else if (nextField == 2) {
        long start = protoInputStream.start(2246267895810L);
        loadCountsForAction(protoInputStream, sparseIntArray);
        protoInputStream.end(start);
      }
    }
  }

  public static void loadCountsForAction(
      ProtoInputStream protoInputStream, SparseIntArray sparseIntArray) {
    int i = 0;
    int i2 = -1;
    while (true) {
      int nextField = protoInputStream.nextField();
      if (nextField == -1) {
        break;
      }
      if (nextField == 1) {
        i2 = protoInputStream.readInt(1120986464257L) - 1;
      } else if (nextField == 2) {
        i = protoInputStream.readInt(1120986464258L);
      }
    }
    if (i2 != -1) {
      sparseIntArray.put(i2, i);
    }
  }

  public static void loadConfigStats(
      ProtoInputStream protoInputStream, IntervalStats intervalStats) {
    Configuration configuration = new Configuration();
    ConfigurationStats configurationStats = new ConfigurationStats();
    boolean z = false;
    if (protoInputStream.nextField(1146756268033L)) {
      configuration.readFromProto(protoInputStream, 1146756268033L);
      configurationStats = intervalStats.getOrCreateConfigurationStats(configuration);
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
            intervalStats.beginTime + protoInputStream.readLong(1112396529666L);
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
  }

  /* JADX WARN: Code restructure failed: missing block: B:46:0x00c4, code lost:

     if (r0.mPackageToken != (-1)) goto L52;
  */
  /* JADX WARN: Code restructure failed: missing block: B:47:0x00c6, code lost:

     return null;
  */
  /* JADX WARN: Code restructure failed: missing block: B:49:?, code lost:

     return r0;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static UsageEvents.Event parseEvent(ProtoInputStream protoInputStream, long j) {
    UsageEvents.Event event = new UsageEvents.Event();
    while (true) {
      switch (protoInputStream.nextField()) {
        case 1:
          event.mPackageToken = protoInputStream.readInt(1120986464257L) - 1;
          break;
        case 2:
          event.mClassToken = protoInputStream.readInt(1120986464258L) - 1;
          break;
        case 3:
          event.mTimeStamp = protoInputStream.readLong(1112396529667L) + j;
          break;
        case 4:
          event.mFlags = protoInputStream.readInt(1120986464260L);
          break;
        case 5:
          event.mEventType = protoInputStream.readInt(1120986464261L);
          break;
        case 6:
          Configuration configuration = new Configuration();
          event.mConfiguration = configuration;
          configuration.readFromProto(protoInputStream, 1146756268038L);
          break;
        case 7:
          event.mShortcutIdToken = protoInputStream.readInt(1120986464263L) - 1;
          break;
        case 8:
          event.mBucketAndReason = protoInputStream.readInt(1120986464264L);
          break;
        case 9:
          event.mNotificationChannelIdToken = protoInputStream.readInt(1120986464265L) - 1;
          break;
        case 10:
          event.mInstanceId = protoInputStream.readInt(1120986464266L);
          break;
        case 11:
          event.mTaskRootPackageToken = protoInputStream.readInt(1120986464267L) - 1;
          break;
        case 12:
          event.mTaskRootClassToken = protoInputStream.readInt(1120986464268L) - 1;
          break;
        case 13:
          event.mLocusIdToken = protoInputStream.readInt(1120986464269L) - 1;
          break;
      }
    }
  }

  public static void writeOffsetTimestamp(
      ProtoOutputStream protoOutputStream, long j, long j2, long j3) {
    if (j2 > j3 - ONE_HOUR_MS) {
      protoOutputStream.write(j, getOffsetTimestamp(j2, j3));
    }
  }

  public static void writeUsageStats(
      ProtoOutputStream protoOutputStream, long j, UsageStats usageStats) {
    protoOutputStream.write(1120986464257L, usageStats.mPackageToken + 1);
    writeOffsetTimestamp(protoOutputStream, 1112396529667L, usageStats.mLastTimeUsed, j);
    protoOutputStream.write(1112396529668L, usageStats.mTotalTimeInForeground);
    writeOffsetTimestamp(
        protoOutputStream, 1112396529672L, usageStats.mLastTimeForegroundServiceUsed, j);
    protoOutputStream.write(1112396529673L, usageStats.mTotalTimeForegroundServiceUsed);
    writeOffsetTimestamp(protoOutputStream, 1112396529674L, usageStats.mLastTimeVisible, j);
    protoOutputStream.write(1112396529675L, usageStats.mTotalTimeVisible);
    writeOffsetTimestamp(protoOutputStream, 1112396529676L, usageStats.mLastTimeComponentUsed, j);
    protoOutputStream.write(1120986464262L, usageStats.mAppLaunchCount);
    try {
      writeChooserCounts(protoOutputStream, usageStats);
    } catch (IllegalArgumentException e) {
      Slog.e(
          "UsageStatsProtoV2", "Unable to write chooser counts for " + usageStats.mPackageName, e);
    }
  }

  public static void writeCountAndTime(
      ProtoOutputStream protoOutputStream, long j, int i, long j2) {
    long start = protoOutputStream.start(j);
    protoOutputStream.write(1120986464257L, i);
    protoOutputStream.write(1112396529666L, j2);
    protoOutputStream.end(start);
  }

  public static void writeChooserCounts(
      ProtoOutputStream protoOutputStream, UsageStats usageStats) {
    if (usageStats == null || usageStats.mChooserCountsObfuscated.size() == 0) {
      return;
    }
    int size = usageStats.mChooserCountsObfuscated.size();
    for (int i = 0; i < size; i++) {
      int keyAt = usageStats.mChooserCountsObfuscated.keyAt(i);
      SparseIntArray sparseIntArray =
          (SparseIntArray) usageStats.mChooserCountsObfuscated.valueAt(i);
      if (sparseIntArray != null && sparseIntArray.size() != 0) {
        long start = protoOutputStream.start(2246267895815L);
        protoOutputStream.write(1120986464257L, keyAt + 1);
        writeCountsForAction(protoOutputStream, sparseIntArray);
        protoOutputStream.end(start);
      }
    }
  }

  public static void writeCountsForAction(
      ProtoOutputStream protoOutputStream, SparseIntArray sparseIntArray) {
    int size = sparseIntArray.size();
    for (int i = 0; i < size; i++) {
      int keyAt = sparseIntArray.keyAt(i);
      int valueAt = sparseIntArray.valueAt(i);
      if (valueAt > 0) {
        long start = protoOutputStream.start(2246267895810L);
        protoOutputStream.write(1120986464257L, keyAt + 1);
        protoOutputStream.write(1120986464258L, valueAt);
        protoOutputStream.end(start);
      }
    }
  }

  public static void writeConfigStats(
      ProtoOutputStream protoOutputStream,
      long j,
      ConfigurationStats configurationStats,
      boolean z) {
    configurationStats.mConfiguration.dumpDebug(protoOutputStream, 1146756268033L);
    writeOffsetTimestamp(protoOutputStream, 1112396529666L, configurationStats.mLastTimeActive, j);
    protoOutputStream.write(1112396529667L, configurationStats.mTotalTimeActive);
    protoOutputStream.write(1120986464260L, configurationStats.mActivationCount);
    protoOutputStream.write(1133871366149L, z);
  }

  public static void writeEvent(
      ProtoOutputStream protoOutputStream, long j, UsageEvents.Event event) {
    int i;
    protoOutputStream.write(1120986464257L, event.mPackageToken + 1);
    int i2 = event.mClassToken;
    if (i2 != -1) {
      protoOutputStream.write(1120986464258L, i2 + 1);
    }
    writeOffsetTimestamp(protoOutputStream, 1112396529667L, event.mTimeStamp, j);
    protoOutputStream.write(1120986464260L, event.mFlags);
    protoOutputStream.write(1120986464261L, event.mEventType);
    protoOutputStream.write(1120986464266L, event.mInstanceId);
    int i3 = event.mTaskRootPackageToken;
    if (i3 != -1) {
      protoOutputStream.write(1120986464267L, i3 + 1);
    }
    int i4 = event.mTaskRootClassToken;
    if (i4 != -1) {
      protoOutputStream.write(1120986464268L, i4 + 1);
    }
    int i5 = event.mEventType;
    if (i5 == 5) {
      Configuration configuration = event.mConfiguration;
      if (configuration != null) {
        configuration.dumpDebug(protoOutputStream, 1146756268038L);
        return;
      }
      return;
    }
    if (i5 == 8) {
      int i6 = event.mShortcutIdToken;
      if (i6 != -1) {
        protoOutputStream.write(1120986464263L, i6 + 1);
        return;
      }
      return;
    }
    if (i5 == 30) {
      int i7 = event.mLocusIdToken;
      if (i7 != -1) {
        protoOutputStream.write(1120986464269L, i7 + 1);
        return;
      }
      return;
    }
    if (i5 != 11) {
      if (i5 == 12 && (i = event.mNotificationChannelIdToken) != -1) {
        protoOutputStream.write(1120986464265L, i + 1);
        return;
      }
      return;
    }
    int i8 = event.mBucketAndReason;
    if (i8 != 0) {
      protoOutputStream.write(1120986464264L, i8);
    }
  }

  public static void read(InputStream inputStream, IntervalStats intervalStats) {
    ProtoInputStream protoInputStream = new ProtoInputStream(inputStream);
    while (true) {
      int nextField = protoInputStream.nextField();
      if (nextField == -1) {
        int size = intervalStats.packageStatsObfuscated.size();
        for (int i = 0; i < size; i++) {
          UsageStats usageStats = (UsageStats) intervalStats.packageStatsObfuscated.valueAt(i);
          usageStats.mBeginTimeStamp = intervalStats.beginTime;
          usageStats.mEndTimeStamp = intervalStats.endTime;
        }
        return;
      }
      if (nextField != 1) {
        if (nextField != 2) {
          if (nextField == 3) {
            intervalStats.minorVersion = protoInputStream.readInt(1120986464259L);
          } else {
            switch (nextField) {
              case 10:
                loadCountAndTime(
                    protoInputStream, 1146756268042L, intervalStats.interactiveTracker);
                break;
              case 11:
                loadCountAndTime(
                    protoInputStream, 1146756268043L, intervalStats.nonInteractiveTracker);
                break;
              case 12:
                loadCountAndTime(
                    protoInputStream, 1146756268044L, intervalStats.keyguardShownTracker);
                break;
              case 13:
                loadCountAndTime(
                    protoInputStream, 1146756268045L, intervalStats.keyguardHiddenTracker);
                break;
              default:
                switch (nextField) {
                  case 20:
                    try {
                      long start = protoInputStream.start(2246267895828L);
                      UsageStats parseUsageStats =
                          parseUsageStats(protoInputStream, intervalStats.beginTime);
                      protoInputStream.end(start);
                      int i2 = parseUsageStats.mPackageToken;
                      if (i2 != -1) {
                        intervalStats.packageStatsObfuscated.put(i2, parseUsageStats);
                        break;
                      } else {
                        break;
                      }
                    } catch (IOException e) {
                      Slog.e("UsageStatsProtoV2", "Unable to read some usage stats from proto.", e);
                      break;
                    }
                  case 21:
                    try {
                      long start2 = protoInputStream.start(2246267895829L);
                      loadConfigStats(protoInputStream, intervalStats);
                      protoInputStream.end(start2);
                      break;
                    } catch (IOException e2) {
                      Slog.e(
                          "UsageStatsProtoV2",
                          "Unable to read some configuration stats from proto.",
                          e2);
                      break;
                    }
                  case 22:
                    try {
                      long start3 = protoInputStream.start(2246267895830L);
                      UsageEvents.Event parseEvent =
                          parseEvent(protoInputStream, intervalStats.beginTime);
                      protoInputStream.end(start3);
                      if (parseEvent != null) {
                        intervalStats.events.insert(parseEvent);
                        break;
                      } else {
                        break;
                      }
                    } catch (IOException e3) {
                      Slog.e("UsageStatsProtoV2", "Unable to read some events from proto.", e3);
                      break;
                    }
                }
            }
          }
        } else {
          intervalStats.majorVersion = protoInputStream.readInt(1120986464258L);
        }
      } else {
        intervalStats.endTime = intervalStats.beginTime + protoInputStream.readLong(1112396529665L);
      }
    }
  }

  public static void write(OutputStream outputStream, IntervalStats intervalStats) {
    ProtoOutputStream protoOutputStream = new ProtoOutputStream(outputStream);
    protoOutputStream.write(
        1112396529665L, getOffsetTimestamp(intervalStats.endTime, intervalStats.beginTime));
    protoOutputStream.write(1120986464258L, intervalStats.majorVersion);
    protoOutputStream.write(1120986464259L, intervalStats.minorVersion);
    try {
      IntervalStats.EventTracker eventTracker = intervalStats.interactiveTracker;
      writeCountAndTime(
          protoOutputStream, 1146756268042L, eventTracker.count, eventTracker.duration);
      IntervalStats.EventTracker eventTracker2 = intervalStats.nonInteractiveTracker;
      writeCountAndTime(
          protoOutputStream, 1146756268043L, eventTracker2.count, eventTracker2.duration);
      IntervalStats.EventTracker eventTracker3 = intervalStats.keyguardShownTracker;
      writeCountAndTime(
          protoOutputStream, 1146756268044L, eventTracker3.count, eventTracker3.duration);
      IntervalStats.EventTracker eventTracker4 = intervalStats.keyguardHiddenTracker;
      writeCountAndTime(
          protoOutputStream, 1146756268045L, eventTracker4.count, eventTracker4.duration);
    } catch (IllegalArgumentException e) {
      Slog.e("UsageStatsProtoV2", "Unable to write some interval stats trackers to proto.", e);
    }
    int size = intervalStats.packageStatsObfuscated.size();
    for (int i = 0; i < size; i++) {
      try {
        long start = protoOutputStream.start(2246267895828L);
        writeUsageStats(
            protoOutputStream,
            intervalStats.beginTime,
            (UsageStats) intervalStats.packageStatsObfuscated.valueAt(i));
        protoOutputStream.end(start);
      } catch (IllegalArgumentException e2) {
        Slog.e("UsageStatsProtoV2", "Unable to write some usage stats to proto.", e2);
      }
    }
    int size2 = intervalStats.configurations.size();
    for (int i2 = 0; i2 < size2; i2++) {
      boolean equals =
          intervalStats.activeConfiguration.equals(
              (Configuration) intervalStats.configurations.keyAt(i2));
      try {
        long start2 = protoOutputStream.start(2246267895829L);
        writeConfigStats(
            protoOutputStream,
            intervalStats.beginTime,
            (ConfigurationStats) intervalStats.configurations.valueAt(i2),
            equals);
        protoOutputStream.end(start2);
      } catch (IllegalArgumentException e3) {
        Slog.e("UsageStatsProtoV2", "Unable to write some configuration stats to proto.", e3);
      }
    }
    int size3 = intervalStats.events.size();
    for (int i3 = 0; i3 < size3; i3++) {
      try {
        long start3 = protoOutputStream.start(2246267895830L);
        writeEvent(protoOutputStream, intervalStats.beginTime, intervalStats.events.get(i3));
        protoOutputStream.end(start3);
      } catch (IllegalArgumentException e4) {
        Slog.e("UsageStatsProtoV2", "Unable to write some events to proto.", e4);
      }
    }
    protoOutputStream.flush();
  }

  public static void loadPackagesMap(ProtoInputStream protoInputStream, SparseArray sparseArray) {
    ArrayList arrayList = new ArrayList();
    int i = -1;
    while (true) {
      int nextField = protoInputStream.nextField();
      if (nextField == -1) {
        break;
      }
      if (nextField == 1) {
        i = protoInputStream.readInt(1120986464257L) - 1;
      } else if (nextField == 2) {
        arrayList.add(protoInputStream.readString(2237677961218L));
      }
    }
    if (i != -1) {
      sparseArray.put(i, arrayList);
    }
  }

  public static void readObfuscatedData(
      InputStream inputStream, PackagesTokenData packagesTokenData) {
    ProtoInputStream protoInputStream = new ProtoInputStream(inputStream);
    while (true) {
      int nextField = protoInputStream.nextField();
      if (nextField == -1) {
        return;
      }
      if (nextField == 1) {
        packagesTokenData.counter = protoInputStream.readInt(1120986464257L);
      } else if (nextField == 2) {
        long start = protoInputStream.start(2246267895810L);
        loadPackagesMap(protoInputStream, packagesTokenData.tokensToPackagesMap);
        protoInputStream.end(start);
      }
    }
  }

  public static void writeObfuscatedData(
      OutputStream outputStream, PackagesTokenData packagesTokenData) {
    ProtoOutputStream protoOutputStream = new ProtoOutputStream(outputStream);
    protoOutputStream.write(1120986464257L, packagesTokenData.counter);
    int size = packagesTokenData.tokensToPackagesMap.size();
    for (int i = 0; i < size; i++) {
      long start = protoOutputStream.start(2246267895810L);
      protoOutputStream.write(1120986464257L, packagesTokenData.tokensToPackagesMap.keyAt(i) + 1);
      ArrayList arrayList = (ArrayList) packagesTokenData.tokensToPackagesMap.valueAt(i);
      int size2 = arrayList.size();
      for (int i2 = 0; i2 < size2; i2++) {
        protoOutputStream.write(2237677961218L, (String) arrayList.get(i2));
      }
      protoOutputStream.end(start);
    }
    protoOutputStream.flush();
  }

  /* JADX WARN: Code restructure failed: missing block: B:42:0x00a4, code lost:

     r4 = r0.mEventType;
  */
  /* JADX WARN: Code restructure failed: missing block: B:43:0x00a7, code lost:

     if (r4 == 5) goto L31;
  */
  /* JADX WARN: Code restructure failed: missing block: B:45:0x00ad, code lost:

     if (r4 == 8) goto L28;
  */
  /* JADX WARN: Code restructure failed: missing block: B:47:0x00b1, code lost:

     if (r4 == 12) goto L25;
  */
  /* JADX WARN: Code restructure failed: missing block: B:49:0x00b6, code lost:

     if (r0.mNotificationChannelId != null) goto L34;
  */
  /* JADX WARN: Code restructure failed: missing block: B:50:0x00b8, code lost:

     r0.mNotificationChannelId = "";
  */
  /* JADX WARN: Code restructure failed: missing block: B:52:0x00cf, code lost:

     if (r0.mPackage != null) goto L65;
  */
  /* JADX WARN: Code restructure failed: missing block: B:53:0x00d1, code lost:

     return null;
  */
  /* JADX WARN: Code restructure failed: missing block: B:55:?, code lost:

     return r0;
  */
  /* JADX WARN: Code restructure failed: missing block: B:57:0x00bd, code lost:

     if (r0.mShortcutId != null) goto L34;
  */
  /* JADX WARN: Code restructure failed: missing block: B:58:0x00bf, code lost:

     r0.mShortcutId = "";
  */
  /* JADX WARN: Code restructure failed: missing block: B:60:0x00c4, code lost:

     if (r0.mConfiguration != null) goto L34;
  */
  /* JADX WARN: Code restructure failed: missing block: B:61:0x00c6, code lost:

     r0.mConfiguration = new android.content.res.Configuration();
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static UsageEvents.Event parsePendingEvent(ProtoInputStream protoInputStream) {
    UsageEvents.Event event = new UsageEvents.Event();
    while (true) {
      switch (protoInputStream.nextField()) {
        case 1:
          event.mPackage = protoInputStream.readString(1138166333441L);
          break;
        case 2:
          event.mClass = protoInputStream.readString(1138166333442L);
          break;
        case 3:
          event.mTimeStamp = protoInputStream.readLong(1112396529667L);
          break;
        case 4:
          event.mFlags = protoInputStream.readInt(1120986464260L);
          break;
        case 5:
          event.mEventType = protoInputStream.readInt(1120986464261L);
          break;
        case 6:
          Configuration configuration = new Configuration();
          event.mConfiguration = configuration;
          configuration.readFromProto(protoInputStream, 1146756268038L);
          break;
        case 7:
          event.mShortcutId = protoInputStream.readString(1138166333447L);
          break;
        case 8:
          event.mBucketAndReason = protoInputStream.readInt(1120986464264L);
          break;
        case 9:
          event.mNotificationChannelId = protoInputStream.readString(1138166333449L);
          break;
        case 10:
          event.mInstanceId = protoInputStream.readInt(1120986464266L);
          break;
        case 11:
          event.mTaskRootPackage = protoInputStream.readString(1138166333451L);
          break;
        case 12:
          event.mTaskRootClass = protoInputStream.readString(1138166333452L);
          break;
      }
    }
  }

  public static void readPendingEvents(InputStream inputStream, LinkedList linkedList) {
    ProtoInputStream protoInputStream = new ProtoInputStream(inputStream);
    while (true) {
      int nextField = protoInputStream.nextField();
      if (nextField == -1) {
        return;
      }
      if (nextField == 23) {
        try {
          long start = protoInputStream.start(2246267895831L);
          UsageEvents.Event parsePendingEvent = parsePendingEvent(protoInputStream);
          protoInputStream.end(start);
          if (parsePendingEvent != null) {
            linkedList.add(parsePendingEvent);
          }
        } catch (IOException e) {
          Slog.e("UsageStatsProtoV2", "Unable to parse some pending events from proto.", e);
        }
      }
    }
  }

  public static void writePendingEvent(
      ProtoOutputStream protoOutputStream, UsageEvents.Event event) {
    String str;
    protoOutputStream.write(1138166333441L, event.mPackage);
    String str2 = event.mClass;
    if (str2 != null) {
      protoOutputStream.write(1138166333442L, str2);
    }
    protoOutputStream.write(1112396529667L, event.mTimeStamp);
    protoOutputStream.write(1120986464260L, event.mFlags);
    protoOutputStream.write(1120986464261L, event.mEventType);
    protoOutputStream.write(1120986464266L, event.mInstanceId);
    String str3 = event.mTaskRootPackage;
    if (str3 != null) {
      protoOutputStream.write(1138166333451L, str3);
    }
    String str4 = event.mTaskRootClass;
    if (str4 != null) {
      protoOutputStream.write(1138166333452L, str4);
    }
    int i = event.mEventType;
    if (i == 5) {
      Configuration configuration = event.mConfiguration;
      if (configuration != null) {
        configuration.dumpDebug(protoOutputStream, 1146756268038L);
        return;
      }
      return;
    }
    if (i == 8) {
      String str5 = event.mShortcutId;
      if (str5 != null) {
        protoOutputStream.write(1138166333447L, str5);
        return;
      }
      return;
    }
    if (i != 11) {
      if (i == 12 && (str = event.mNotificationChannelId) != null) {
        protoOutputStream.write(1138166333449L, str);
        return;
      }
      return;
    }
    int i2 = event.mBucketAndReason;
    if (i2 != 0) {
      protoOutputStream.write(1120986464264L, i2);
    }
  }

  public static void writePendingEvents(OutputStream outputStream, LinkedList linkedList) {
    ProtoOutputStream protoOutputStream = new ProtoOutputStream(outputStream);
    int size = linkedList.size();
    for (int i = 0; i < size; i++) {
      try {
        long start = protoOutputStream.start(2246267895831L);
        writePendingEvent(protoOutputStream, (UsageEvents.Event) linkedList.get(i));
        protoOutputStream.end(start);
      } catch (IllegalArgumentException e) {
        Slog.e("UsageStatsProtoV2", "Unable to write some pending events to proto.", e);
      }
    }
    protoOutputStream.flush();
  }

  public static Pair parseGlobalComponentUsage(ProtoInputStream protoInputStream) {
    String str = "";
    long j = 0;
    while (true) {
      int nextField = protoInputStream.nextField();
      if (nextField == -1) {
        return new Pair(str, Long.valueOf(j));
      }
      if (nextField == 1) {
        str = protoInputStream.readString(1138166333441L);
      } else if (nextField == 2) {
        j = protoInputStream.readLong(1112396529666L);
      }
    }
  }

  public static void readGlobalComponentUsage(InputStream inputStream, Map map) {
    ProtoInputStream protoInputStream = new ProtoInputStream(inputStream);
    while (true) {
      int nextField = protoInputStream.nextField();
      if (nextField == -1) {
        return;
      }
      if (nextField == 24) {
        try {
          long start = protoInputStream.start(2246267895832L);
          Pair parseGlobalComponentUsage = parseGlobalComponentUsage(protoInputStream);
          protoInputStream.end(start);
          if (!((String) parseGlobalComponentUsage.first).isEmpty()
              && ((Long) parseGlobalComponentUsage.second).longValue() > 0) {
            map.put(
                (String) parseGlobalComponentUsage.first, (Long) parseGlobalComponentUsage.second);
          }
        } catch (IOException e) {
          Slog.e("UsageStatsProtoV2", "Unable to parse some package usage from proto.", e);
        }
      }
    }
  }

  public static void writeGlobalComponentUsage(OutputStream outputStream, Map map) {
    ProtoOutputStream protoOutputStream = new ProtoOutputStream(outputStream);
    Map.Entry[] entryArr = (Map.Entry[]) map.entrySet().toArray();
    int length = entryArr.length;
    for (int i = 0; i < length; i++) {
      if (((Long) entryArr[i].getValue()).longValue() > 0) {
        long start = protoOutputStream.start(2246267895832L);
        protoOutputStream.write(1138166333441L, (String) entryArr[i].getKey());
        protoOutputStream.write(1112396529666L, ((Long) entryArr[i].getValue()).longValue());
        protoOutputStream.end(start);
      }
    }
  }
}
