package com.android.server.power.stats.wakeups;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.TimeSparseArray;
import android.util.TimeUtils;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IntPair;
import com.android.internal.util.jobs.XmlUtils;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public class CpuWakeupStats {
  public static final long WAKEUP_WRITE_DELAY_MS = TimeUnit.SECONDS.toMillis(30);
  public final Handler mHandler;
  public final IrqDeviceMap mIrqDeviceMap;
  final Config mConfig = new Config();
  final TimeSparseArray mWakeupEvents = new TimeSparseArray();
  final TimeSparseArray mWakeupAttribution = new TimeSparseArray();
  public final SparseIntArray mUidProcStates = new SparseIntArray();
  public final SparseIntArray mReusableUidProcStates = new SparseIntArray(4);
  public final WakingActivityHistory mRecentWakingActivity =
      new WakingActivityHistory(
          new LongSupplier() { // from class:
                               // com.android.server.power.stats.wakeups.CpuWakeupStats$$ExternalSyntheticLambda0
            @Override // java.util.function.LongSupplier
            public final long getAsLong() {
              long lambda$new$0;
              lambda$new$0 = CpuWakeupStats.this.lambda$new$0();
              return lambda$new$0;
            }
          });

  public static int subsystemToStatsReason(int i) {
    int i2 = 1;
    if (i != 1) {
      i2 = 2;
      if (i != 2) {
        i2 = 3;
        if (i != 3) {
          i2 = 4;
          if (i != 4) {
            i2 = 5;
            if (i != 5) {
              return 0;
            }
          }
        }
      }
    }
    return i2;
  }

  public static String subsystemToString(int i) {
    return i != -1
        ? i != 1
            ? i != 2
                ? i != 3 ? i != 4 ? i != 5 ? "N/A" : "Cellular_data" : "Sensor" : "Sound_trigger"
                : "Wifi"
            : "Alarm"
        : "Unknown";
  }

  public static int typeToStatsType(int i) {
    int i2 = 1;
    if (i != 1) {
      i2 = 2;
      if (i != 2) {
        return 0;
      }
    }
    return i2;
  }

  public CpuWakeupStats(Context context, int i, Handler handler) {
    this.mIrqDeviceMap = IrqDeviceMap.getInstance(context, i);
    this.mHandler = handler;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ long lambda$new$0() {
    return this.mConfig.WAKING_ACTIVITY_RETENTION_MS;
  }

  public synchronized void systemServicesReady() {
    this.mConfig.register(new HandlerExecutor(this.mHandler));
  }

  /* JADX WARN: Removed duplicated region for block: B:30:0x00b9  */
  /* JADX WARN: Removed duplicated region for block: B:35:0x00e9 A[SYNTHETIC] */
  /* renamed from: logWakeupAttribution, reason: merged with bridge method [inline-methods] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final synchronized void lambda$noteWakeupTimeAndReason$1(Wakeup wakeup) {
    int[] iArr;
    int[] iArr2;
    if (ArrayUtils.isEmpty(wakeup.mDevices)) {
      FrameworkStatsLog.write(
          FrameworkStatsLog.KERNEL_WAKEUP_ATTRIBUTED,
          0,
          0,
          (int[]) null,
          wakeup.mElapsedMillis,
          (int[]) null);
      Trace.instantForTrack(131072L, "wakeup_attribution", wakeup.mElapsedMillis + " --");
      return;
    }
    SparseArray sparseArray = (SparseArray) this.mWakeupAttribution.get(wakeup.mElapsedMillis);
    if (sparseArray == null) {
      Slog.wtf("CpuWakeupStats", "Unexpected null attribution found for " + wakeup);
      return;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < sparseArray.size(); i++) {
      int keyAt = sparseArray.keyAt(i);
      SparseIntArray sparseIntArray = (SparseIntArray) sparseArray.valueAt(i);
      if (sparseIntArray != null && sparseIntArray.size() != 0) {
        int size = sparseIntArray.size();
        iArr = new int[size];
        int[] iArr3 = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
          iArr[i2] = sparseIntArray.keyAt(i2);
          iArr3[i2] = ActivityManager.processStateAmToProto(sparseIntArray.valueAt(i2));
        }
        iArr2 = iArr3;
        FrameworkStatsLog.write(
            FrameworkStatsLog.KERNEL_WAKEUP_ATTRIBUTED,
            typeToStatsType(wakeup.mType),
            subsystemToStatsReason(keyAt),
            iArr,
            wakeup.mElapsedMillis,
            iArr2);
        if (Trace.isTagEnabled(131072L)) {
          if (i == 0) {
            sb.append(wakeup.mElapsedMillis + " ");
          }
          sb.append(subsystemToString(keyAt));
          sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
          sb.append(Arrays.toString(iArr));
          sb.append(" ");
        }
      }
      iArr = new int[0];
      iArr2 = iArr;
      FrameworkStatsLog.write(
          FrameworkStatsLog.KERNEL_WAKEUP_ATTRIBUTED,
          typeToStatsType(wakeup.mType),
          subsystemToStatsReason(keyAt),
          iArr,
          wakeup.mElapsedMillis,
          iArr2);
      if (Trace.isTagEnabled(131072L)) {}
    }
    Trace.instantForTrack(131072L, "wakeup_attribution", sb.toString().trim());
  }

  public synchronized void onUidRemoved(int i) {
    this.mUidProcStates.delete(i);
  }

  public synchronized void noteUidProcessState(int i, int i2) {
    this.mUidProcStates.put(i, i2);
  }

  public synchronized void noteWakeupTimeAndReason(long j, long j2, String str) {
    final Wakeup parseWakeup = Wakeup.parseWakeup(str, j, j2, this.mIrqDeviceMap);
    if (parseWakeup == null) {
      return;
    }
    this.mWakeupEvents.put(j, parseWakeup);
    attemptAttributionFor(parseWakeup);
    long j3 = j - this.mConfig.WAKEUP_STATS_RETENTION_MS;
    for (int closestIndexOnOrBefore = this.mWakeupEvents.closestIndexOnOrBefore(j3);
        closestIndexOnOrBefore >= 0;
        closestIndexOnOrBefore--) {
      this.mWakeupEvents.removeAt(closestIndexOnOrBefore);
    }
    for (int closestIndexOnOrBefore2 = this.mWakeupAttribution.closestIndexOnOrBefore(j3);
        closestIndexOnOrBefore2 >= 0;
        closestIndexOnOrBefore2--) {
      this.mWakeupAttribution.removeAt(closestIndexOnOrBefore2);
    }
    this.mHandler.postDelayed(
        new Runnable() { // from class:
                         // com.android.server.power.stats.wakeups.CpuWakeupStats$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            CpuWakeupStats.this.lambda$noteWakeupTimeAndReason$1(parseWakeup);
          }
        },
        WAKEUP_WRITE_DELAY_MS);
  }

  public synchronized void noteWakingActivity(int i, long j, int... iArr) {
    if (iArr == null) {
      return;
    }
    this.mReusableUidProcStates.clear();
    for (int i2 : iArr) {
      this.mReusableUidProcStates.put(i2, this.mUidProcStates.get(i2, -1));
    }
    if (!attemptAttributionWith(i, j, this.mReusableUidProcStates)) {
      this.mRecentWakingActivity.recordActivity(i, j, this.mReusableUidProcStates);
    }
  }

  public final synchronized void attemptAttributionFor(Wakeup wakeup) {
    SparseBooleanArray sparseBooleanArray = wakeup.mResponsibleSubsystems;
    SparseArray sparseArray = (SparseArray) this.mWakeupAttribution.get(wakeup.mElapsedMillis);
    if (sparseArray == null) {
      sparseArray = new SparseArray();
      this.mWakeupAttribution.put(wakeup.mElapsedMillis, sparseArray);
    }
    long j = this.mConfig.WAKEUP_MATCHING_WINDOW_MS;
    for (int i = 0; i < sparseBooleanArray.size(); i++) {
      int keyAt = sparseBooleanArray.keyAt(i);
      long j2 = wakeup.mElapsedMillis;
      sparseArray.put(keyAt, this.mRecentWakingActivity.removeBetween(keyAt, j2 - j, j2 + j));
    }
  }

  public final synchronized boolean attemptAttributionWith(
      int i, long j, SparseIntArray sparseIntArray) {
    long j2 = this.mConfig.WAKEUP_MATCHING_WINDOW_MS;
    int closestIndexOnOrAfter = this.mWakeupEvents.closestIndexOnOrAfter(j - j2);
    int closestIndexOnOrBefore = this.mWakeupEvents.closestIndexOnOrBefore(j + j2);
    while (true) {
      if (closestIndexOnOrAfter > closestIndexOnOrBefore) {
        return false;
      }
      Wakeup wakeup = (Wakeup) this.mWakeupEvents.valueAt(closestIndexOnOrAfter);
      if (wakeup.mResponsibleSubsystems.get(i)) {
        SparseArray sparseArray = (SparseArray) this.mWakeupAttribution.get(wakeup.mElapsedMillis);
        if (sparseArray == null) {
          sparseArray = new SparseArray();
          this.mWakeupAttribution.put(wakeup.mElapsedMillis, sparseArray);
        }
        SparseIntArray sparseIntArray2 = (SparseIntArray) sparseArray.get(i);
        if (sparseIntArray2 == null) {
          sparseArray.put(i, sparseIntArray.clone());
        } else {
          for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
            sparseIntArray2.put(sparseIntArray.keyAt(i2), sparseIntArray.valueAt(i2));
          }
        }
        return true;
      }
      closestIndexOnOrAfter++;
    }
  }

  public synchronized void dump(IndentingPrintWriter indentingPrintWriter, long j) {
    indentingPrintWriter.println("CPU wakeup stats:");
    indentingPrintWriter.increaseIndent();
    this.mConfig.dump(indentingPrintWriter);
    indentingPrintWriter.println();
    this.mIrqDeviceMap.dump(indentingPrintWriter);
    indentingPrintWriter.println();
    this.mRecentWakingActivity.dump(indentingPrintWriter, j);
    indentingPrintWriter.println();
    indentingPrintWriter.println("Current proc-state map (" + this.mUidProcStates.size() + "):");
    indentingPrintWriter.increaseIndent();
    for (int i = 0; i < this.mUidProcStates.size(); i++) {
      if (i > 0) {
        indentingPrintWriter.print(", ");
      }
      UserHandle.formatUid(indentingPrintWriter, this.mUidProcStates.keyAt(i));
      indentingPrintWriter.print(
          XmlUtils.STRING_ARRAY_SEPARATOR
              + ActivityManager.procStateToString(this.mUidProcStates.valueAt(i)));
    }
    indentingPrintWriter.println();
    indentingPrintWriter.decreaseIndent();
    indentingPrintWriter.println();
    SparseLongArray sparseLongArray = new SparseLongArray();
    indentingPrintWriter.println("Wakeup events:");
    indentingPrintWriter.increaseIndent();
    for (int size = this.mWakeupEvents.size() - 1; size >= 0; size--) {
      TimeUtils.formatDuration(this.mWakeupEvents.keyAt(size), j, indentingPrintWriter);
      indentingPrintWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
      indentingPrintWriter.increaseIndent();
      Wakeup wakeup = (Wakeup) this.mWakeupEvents.valueAt(size);
      indentingPrintWriter.println(wakeup);
      indentingPrintWriter.print("Attribution: ");
      SparseArray sparseArray = (SparseArray) this.mWakeupAttribution.get(wakeup.mElapsedMillis);
      if (sparseArray == null) {
        indentingPrintWriter.println("N/A");
      } else {
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
          if (i2 > 0) {
            indentingPrintWriter.print(", ");
          }
          long j2 = sparseLongArray.get(sparseArray.keyAt(i2), IntPair.of(0, 0));
          int first = IntPair.first(j2);
          int second = IntPair.second(j2) + 1;
          indentingPrintWriter.print(subsystemToString(sparseArray.keyAt(i2)));
          indentingPrintWriter.print(" [");
          SparseIntArray sparseIntArray = (SparseIntArray) sparseArray.valueAt(i2);
          if (sparseIntArray != null) {
            for (int i3 = 0; i3 < sparseIntArray.size(); i3++) {
              if (i3 > 0) {
                indentingPrintWriter.print(", ");
              }
              UserHandle.formatUid(indentingPrintWriter, sparseIntArray.keyAt(i3));
              indentingPrintWriter.print(
                  " " + ActivityManager.procStateToString(sparseIntArray.valueAt(i3)));
            }
            first++;
          }
          indentingPrintWriter.print("]");
          sparseLongArray.put(sparseArray.keyAt(i2), IntPair.of(first, second));
        }
        indentingPrintWriter.println();
      }
      indentingPrintWriter.decreaseIndent();
    }
    indentingPrintWriter.decreaseIndent();
    indentingPrintWriter.println("Attribution stats:");
    indentingPrintWriter.increaseIndent();
    for (int i4 = 0; i4 < sparseLongArray.size(); i4++) {
      indentingPrintWriter.print("Subsystem " + subsystemToString(sparseLongArray.keyAt(i4)));
      indentingPrintWriter.print(": ");
      long valueAt = sparseLongArray.valueAt(i4);
      indentingPrintWriter.println(IntPair.first(valueAt) + "/" + IntPair.second(valueAt));
    }
    indentingPrintWriter.println("Total: " + this.mWakeupEvents.size());
    indentingPrintWriter.decreaseIndent();
    indentingPrintWriter.decreaseIndent();
    indentingPrintWriter.println();
  }

  final class WakingActivityHistory {
    public LongSupplier mRetentionSupplier;
    final SparseArray mWakingActivity = new SparseArray();

    public WakingActivityHistory(LongSupplier longSupplier) {
      this.mRetentionSupplier = longSupplier;
    }

    public void recordActivity(int i, long j, SparseIntArray sparseIntArray) {
      if (sparseIntArray == null) {
        return;
      }
      TimeSparseArray timeSparseArray = (TimeSparseArray) this.mWakingActivity.get(i);
      if (timeSparseArray == null) {
        timeSparseArray = new TimeSparseArray();
        this.mWakingActivity.put(i, timeSparseArray);
      }
      SparseIntArray sparseIntArray2 = (SparseIntArray) timeSparseArray.get(j);
      if (sparseIntArray2 == null) {
        timeSparseArray.put(j, sparseIntArray.clone());
      } else {
        for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
          int keyAt = sparseIntArray.keyAt(i2);
          if (sparseIntArray2.indexOfKey(keyAt) < 0) {
            sparseIntArray2.put(keyAt, sparseIntArray.valueAt(i2));
          }
        }
      }
      for (int closestIndexOnOrBefore =
              timeSparseArray.closestIndexOnOrBefore(j - this.mRetentionSupplier.getAsLong());
          closestIndexOnOrBefore >= 0;
          closestIndexOnOrBefore--) {
        timeSparseArray.removeAt(closestIndexOnOrBefore);
      }
    }

    public SparseIntArray removeBetween(int i, long j, long j2) {
      SparseIntArray sparseIntArray = new SparseIntArray();
      TimeSparseArray timeSparseArray = (TimeSparseArray) this.mWakingActivity.get(i);
      if (timeSparseArray != null) {
        int closestIndexOnOrAfter = timeSparseArray.closestIndexOnOrAfter(j);
        int closestIndexOnOrBefore = timeSparseArray.closestIndexOnOrBefore(j2);
        for (int i2 = closestIndexOnOrBefore; i2 >= closestIndexOnOrAfter; i2--) {
          SparseIntArray sparseIntArray2 = (SparseIntArray) timeSparseArray.valueAt(i2);
          for (int i3 = 0; i3 < sparseIntArray2.size(); i3++) {
            sparseIntArray.put(sparseIntArray2.keyAt(i3), sparseIntArray2.valueAt(i3));
          }
        }
        while (closestIndexOnOrBefore >= closestIndexOnOrAfter) {
          timeSparseArray.removeAt(closestIndexOnOrBefore);
          closestIndexOnOrBefore--;
        }
      }
      if (sparseIntArray.size() > 0) {
        return sparseIntArray;
      }
      return null;
    }

    public void dump(IndentingPrintWriter indentingPrintWriter, long j) {
      indentingPrintWriter.println("Recent waking activity:");
      indentingPrintWriter.increaseIndent();
      for (int i = 0; i < this.mWakingActivity.size(); i++) {
        indentingPrintWriter.println(
            "Subsystem "
                + CpuWakeupStats.subsystemToString(this.mWakingActivity.keyAt(i))
                + XmlUtils.STRING_ARRAY_SEPARATOR);
        TimeSparseArray timeSparseArray = (TimeSparseArray) this.mWakingActivity.valueAt(i);
        if (timeSparseArray != null) {
          indentingPrintWriter.increaseIndent();
          for (int size = timeSparseArray.size() - 1; size >= 0; size--) {
            TimeUtils.formatDuration(timeSparseArray.keyAt(size), j, indentingPrintWriter);
            SparseIntArray sparseIntArray = (SparseIntArray) timeSparseArray.valueAt(size);
            if (sparseIntArray == null) {
              indentingPrintWriter.println();
            } else {
              indentingPrintWriter.print(": ");
              for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
                UserHandle.formatUid(indentingPrintWriter, sparseIntArray.keyAt(i2));
                indentingPrintWriter.print(
                    " [" + ActivityManager.procStateToString(sparseIntArray.valueAt(i2)));
                indentingPrintWriter.print("], ");
              }
              indentingPrintWriter.println();
            }
          }
          indentingPrintWriter.decreaseIndent();
        }
      }
      indentingPrintWriter.decreaseIndent();
    }
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  public static int stringToKnownSubsystem(String str) {
    boolean z;
    str.hashCode();
    switch (str.hashCode()) {
      case -1822081062:
        if (str.equals("Sensor")) {
          z = false;
          break;
        }
        z = -1;
        break;
      case -1102294721:
        if (str.equals("Cellular_data")) {
          z = true;
          break;
        }
        z = -1;
        break;
      case -424380824:
        if (str.equals("Sound_trigger")) {
          z = 2;
          break;
        }
        z = -1;
        break;
      case 2695989:
        if (str.equals("Wifi")) {
          z = 3;
          break;
        }
        z = -1;
        break;
      case 63343153:
        if (str.equals("Alarm")) {
          z = 4;
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
        return 4;
      case true:
        return 5;
      case true:
        return 3;
      case true:
        return 2;
      case true:
        return 1;
      default:
        return -1;
    }
  }

  final class Wakeup {
    public static final Pattern sIrqPattern = Pattern.compile("^(\\-?\\d+)\\s+(\\S+)");
    public IrqDevice[] mDevices;
    public long mElapsedMillis;
    public SparseBooleanArray mResponsibleSubsystems;
    public int mType;
    public long mUptimeMillis;

    public Wakeup(
        int i, IrqDevice[] irqDeviceArr, long j, long j2, SparseBooleanArray sparseBooleanArray) {
      this.mType = i;
      this.mDevices = irqDeviceArr;
      this.mElapsedMillis = j;
      this.mUptimeMillis = j2;
      this.mResponsibleSubsystems = sparseBooleanArray;
    }

    public static Wakeup parseWakeup(String str, long j, long j2, IrqDeviceMap irqDeviceMap) {
      boolean z;
      String[] split = str.split(XmlUtils.STRING_ARRAY_SEPARATOR);
      if (ArrayUtils.isEmpty(split) || split[0].startsWith("Abort")) {
        return null;
      }
      IrqDevice[] irqDeviceArr = new IrqDevice[split.length];
      SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
      int i = 0;
      int i2 = 1;
      for (String str2 : split) {
        Matcher matcher = sIrqPattern.matcher(str2.trim());
        if (matcher.find()) {
          try {
            int parseInt = Integer.parseInt(matcher.group(1));
            String group = matcher.group(2);
            if (parseInt < 0) {
              i2 = 2;
            }
            int i3 = i + 1;
            irqDeviceArr[i] = new IrqDevice(parseInt, group);
            List subsystemsForDevice = irqDeviceMap.getSubsystemsForDevice(group);
            if (subsystemsForDevice != null) {
              z = false;
              for (int i4 = 0; i4 < subsystemsForDevice.size(); i4++) {
                int stringToKnownSubsystem =
                    CpuWakeupStats.stringToKnownSubsystem((String) subsystemsForDevice.get(i4));
                if (stringToKnownSubsystem != -1) {
                  sparseBooleanArray.put(stringToKnownSubsystem, true);
                  z = true;
                }
              }
            } else {
              z = false;
            }
            if (!z) {
              sparseBooleanArray.put(-1, true);
            }
            i = i3;
          } catch (NumberFormatException e) {
            Slog.e(
                "CpuWakeupStats.Wakeup",
                "Exception while parsing device names from part: " + str2,
                e);
          }
        }
      }
      if (i == 0) {
        return null;
      }
      if (sparseBooleanArray.size() == 1 && sparseBooleanArray.get(-1, false)) {
        return null;
      }
      return new Wakeup(
          i2, (IrqDevice[]) Arrays.copyOf(irqDeviceArr, i), j, j2, sparseBooleanArray);
    }

    public String toString() {
      return "Wakeup{mType="
          + this.mType
          + ", mElapsedMillis="
          + this.mElapsedMillis
          + ", mUptimeMillis="
          + this.mUptimeMillis
          + ", mDevices="
          + Arrays.toString(this.mDevices)
          + ", mResponsibleSubsystems="
          + this.mResponsibleSubsystems
          + '}';
    }

    public final class IrqDevice {
      public String mDevice;
      public int mLine;

      public IrqDevice(int i, String str) {
        this.mLine = i;
        this.mDevice = str;
      }

      public String toString() {
        return "IrqDevice{mLine=" + this.mLine + ", mDevice='" + this.mDevice + "'}";
      }
    }
  }

  public final class Config implements DeviceConfig.OnPropertiesChangedListener {
    public static final String[] PROPERTY_NAMES = {
      "wakeup_stats_retention_ms", "wakeup_matching_window_ms", "waking_activity_retention_ms"
    };
    public static final long DEFAULT_WAKEUP_STATS_RETENTION_MS = TimeUnit.DAYS.toMillis(3);
    public static final long DEFAULT_WAKEUP_MATCHING_WINDOW_MS = TimeUnit.SECONDS.toMillis(1);
    public static final long DEFAULT_WAKING_ACTIVITY_RETENTION_MS = TimeUnit.MINUTES.toMillis(5);
    public volatile long WAKEUP_STATS_RETENTION_MS = DEFAULT_WAKEUP_STATS_RETENTION_MS;
    public volatile long WAKEUP_MATCHING_WINDOW_MS = DEFAULT_WAKEUP_MATCHING_WINDOW_MS;
    public volatile long WAKING_ACTIVITY_RETENTION_MS = DEFAULT_WAKING_ACTIVITY_RETENTION_MS;

    public void register(Executor executor) {
      DeviceConfig.addOnPropertiesChangedListener("battery_stats", executor, this);
      onPropertiesChanged(DeviceConfig.getProperties("battery_stats", PROPERTY_NAMES));
    }

    public void onPropertiesChanged(DeviceConfig.Properties properties) {
      for (String str : properties.getKeyset()) {
        if (str != null) {
          switch (str) {
            case "wakeup_matching_window_ms":
              this.WAKEUP_MATCHING_WINDOW_MS =
                  properties.getLong(
                      "wakeup_matching_window_ms", DEFAULT_WAKEUP_MATCHING_WINDOW_MS);
              break;
            case "wakeup_stats_retention_ms":
              this.WAKEUP_STATS_RETENTION_MS =
                  properties.getLong(
                      "wakeup_stats_retention_ms", DEFAULT_WAKEUP_STATS_RETENTION_MS);
              break;
            case "waking_activity_retention_ms":
              this.WAKING_ACTIVITY_RETENTION_MS =
                  properties.getLong(
                      "waking_activity_retention_ms", DEFAULT_WAKING_ACTIVITY_RETENTION_MS);
              break;
          }
        }
      }
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
      indentingPrintWriter.println("Config:");
      indentingPrintWriter.increaseIndent();
      indentingPrintWriter.print("wakeup_stats_retention_ms");
      indentingPrintWriter.print("=");
      TimeUtils.formatDuration(this.WAKEUP_STATS_RETENTION_MS, indentingPrintWriter);
      indentingPrintWriter.println();
      indentingPrintWriter.print("wakeup_matching_window_ms");
      indentingPrintWriter.print("=");
      TimeUtils.formatDuration(this.WAKEUP_MATCHING_WINDOW_MS, indentingPrintWriter);
      indentingPrintWriter.println();
      indentingPrintWriter.print("waking_activity_retention_ms");
      indentingPrintWriter.print("=");
      TimeUtils.formatDuration(this.WAKING_ACTIVITY_RETENTION_MS, indentingPrintWriter);
      indentingPrintWriter.println();
      indentingPrintWriter.decreaseIndent();
    }
  }
}
