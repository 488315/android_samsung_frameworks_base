package com.android.server.power.stats.wakeups;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.IndentingPrintWriter;
import android.util.LongSparseArray;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.TimeUtils;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.IntPair;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CpuWakeupStats {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long WAKEUP_WRITE_DELAY_MS = TimeUnit.SECONDS.toMillis(30);
    final Config mConfig;
    public final Handler mHandler;
    public final IrqDeviceMap mIrqDeviceMap;
    public final WakingActivityHistory mRecentWakingActivity;
    public final SparseIntArray mReusableUidProcStates;
    public final SparseIntArray mUidProcStates;
    final LongSparseArray mWakeupAttribution;
    final LongSparseArray mWakeupEvents;

    public final class Config implements DeviceConfig.OnPropertiesChangedListener {
        public volatile long WAKEUP_MATCHING_WINDOW_MS;
        public volatile long WAKEUP_STATS_RETENTION_MS;
        public volatile long WAKING_ACTIVITY_RETENTION_MS;
        public static final String[] PROPERTY_NAMES = {
            "wakeup_stats_retention_ms", "wakeup_matching_window_ms", "waking_activity_retention_ms"
        };
        public static final long DEFAULT_WAKEUP_STATS_RETENTION_MS = TimeUnit.DAYS.toMillis(3);
        public static final long DEFAULT_WAKEUP_MATCHING_WINDOW_MS = TimeUnit.SECONDS.toMillis(1);
        public static final long DEFAULT_WAKING_ACTIVITY_RETENTION_MS =
                TimeUnit.MINUTES.toMillis(5);

        public final void dump(IndentingPrintWriter indentingPrintWriter) {
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

        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            for (String str : properties.getKeyset()) {
                if (str != null) {
                    switch (str) {
                        case "wakeup_matching_window_ms":
                            this.WAKEUP_MATCHING_WINDOW_MS =
                                    properties.getLong(
                                            "wakeup_matching_window_ms",
                                            DEFAULT_WAKEUP_MATCHING_WINDOW_MS);
                            break;
                        case "wakeup_stats_retention_ms":
                            this.WAKEUP_STATS_RETENTION_MS =
                                    properties.getLong(
                                            "wakeup_stats_retention_ms",
                                            DEFAULT_WAKEUP_STATS_RETENTION_MS);
                            break;
                        case "waking_activity_retention_ms":
                            this.WAKING_ACTIVITY_RETENTION_MS =
                                    properties.getLong(
                                            "waking_activity_retention_ms",
                                            DEFAULT_WAKING_ACTIVITY_RETENTION_MS);
                            break;
                    }
                }
            }
        }
    }

    final class Wakeup {
        public static final Pattern sIrqPattern = Pattern.compile("^(\\-?\\d+)\\s+(\\S+)");
        public IrqDevice[] mDevices;
        public long mElapsedMillis;
        public SparseBooleanArray mResponsibleSubsystems;
        public int mType;
        public long mUptimeMillis;

        public final class IrqDevice {
            public String mDevice;
            public int mLine;

            public final String toString() {
                StringBuilder sb = new StringBuilder("IrqDevice{mLine=");
                sb.append(this.mLine);
                sb.append(", mDevice='");
                return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.mDevice, "'}");
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public static Wakeup parseWakeup(String str, long j, long j2, IrqDeviceMap irqDeviceMap) {
            int i;
            char c;
            int i2;
            int i3 = 2;
            int i4 = 0;
            String[] split = str.split(":");
            if (ArrayUtils.isEmpty(split) || split[0].startsWith("Abort")) {
                return null;
            }
            IrqDevice[] irqDeviceArr = new IrqDevice[split.length];
            SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
            int length = split.length;
            int i5 = 0;
            int i6 = 0;
            int i7 = 1;
            while (i5 < length) {
                String str2 = split[i5];
                Matcher matcher = sIrqPattern.matcher(str2.trim());
                if (matcher.find()) {
                    try {
                        int parseInt = Integer.parseInt(matcher.group(1));
                        String group = matcher.group(i3);
                        if (parseInt < 0) {
                            i7 = i3;
                        }
                        int i8 = i6 + 1;
                        IrqDevice irqDevice = new IrqDevice();
                        irqDevice.mLine = parseInt;
                        irqDevice.mDevice = group;
                        irqDeviceArr[i6] = irqDevice;
                        List list = (List) irqDeviceMap.mSubsystemsForDevice.get(group);
                        if (list != null) {
                            int i9 = i4;
                            i = i9;
                            while (i9 < list.size()) {
                                String str3 = (String) list.get(i9);
                                int i10 = CpuWakeupStats.$r8$clinit;
                                str3.getClass();
                                switch (str3.hashCode()) {
                                    case -1822081062:
                                        if (str3.equals("Sensor")) {
                                            c = 0;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case -1102294721:
                                        if (str3.equals("Cellular_data")) {
                                            c = 1;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case -424380824:
                                        if (str3.equals("Sound_trigger")) {
                                            c = 2;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 2695989:
                                        if (str3.equals("Wifi")) {
                                            c = 3;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 63343153:
                                        if (str3.equals("Alarm")) {
                                            c = 4;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    default:
                                        c = 65535;
                                        break;
                                }
                                switch (c) {
                                    case 0:
                                        i2 = 4;
                                        break;
                                    case 1:
                                        i2 = 5;
                                        break;
                                    case 2:
                                        i2 = 3;
                                        break;
                                    case 3:
                                        i2 = 2;
                                        break;
                                    case 4:
                                        i2 = 1;
                                        break;
                                    default:
                                        i2 = -1;
                                        break;
                                }
                                if (i2 != -1) {
                                    sparseBooleanArray.put(i2, true);
                                    i = 1;
                                }
                                i9++;
                            }
                        } else {
                            i = 0;
                        }
                        if (i == 0) {
                            sparseBooleanArray.put(-1, true);
                        }
                        i6 = i8;
                    } catch (NumberFormatException e) {
                        Slog.e(
                                "CpuWakeupStats.Wakeup",
                                "Exception while parsing device names from part: ".concat(str2),
                                e);
                    }
                }
                i5++;
                i3 = 2;
                i4 = 0;
            }
            if (i6 == 0) {
                return null;
            }
            if (sparseBooleanArray.size() == 1 && sparseBooleanArray.get(-1, false)) {
                return null;
            }
            IrqDevice[] irqDeviceArr2 = (IrqDevice[]) Arrays.copyOf(irqDeviceArr, i6);
            Wakeup wakeup = new Wakeup();
            wakeup.mType = i7;
            wakeup.mDevices = irqDeviceArr2;
            wakeup.mElapsedMillis = j;
            wakeup.mUptimeMillis = j2;
            wakeup.mResponsibleSubsystems = sparseBooleanArray;
            return wakeup;
        }

        public final String toString() {
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
    }

    final class WakingActivityHistory {
        public final LongSupplier mRetentionSupplier;
        final SparseArray mWakingActivity = new SparseArray();

        public WakingActivityHistory(
                CpuWakeupStats$$ExternalSyntheticLambda0 cpuWakeupStats$$ExternalSyntheticLambda0) {
            this.mRetentionSupplier = cpuWakeupStats$$ExternalSyntheticLambda0;
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter, long j) {
            indentingPrintWriter.println("Recent waking activity:");
            indentingPrintWriter.increaseIndent();
            for (int i = 0; i < this.mWakingActivity.size(); i++) {
                indentingPrintWriter.println(
                        "Subsystem "
                                + CpuWakeupStats.subsystemToString(this.mWakingActivity.keyAt(i))
                                + ":");
                LongSparseArray longSparseArray = (LongSparseArray) this.mWakingActivity.valueAt(i);
                if (longSparseArray != null) {
                    indentingPrintWriter.increaseIndent();
                    for (int size = longSparseArray.size() - 1; size >= 0; size--) {
                        TimeUtils.formatDuration(
                                longSparseArray.keyAt(size), j, indentingPrintWriter);
                        SparseIntArray sparseIntArray =
                                (SparseIntArray) longSparseArray.valueAt(size);
                        if (sparseIntArray == null) {
                            indentingPrintWriter.println();
                        } else {
                            indentingPrintWriter.print(": ");
                            for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
                                UserHandle.formatUid(
                                        indentingPrintWriter, sparseIntArray.keyAt(i2));
                                indentingPrintWriter.print(
                                        " ["
                                                + ActivityManager.procStateToString(
                                                        sparseIntArray.valueAt(i2)));
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

        public final void recordActivity(int i, long j, SparseIntArray sparseIntArray) {
            if (sparseIntArray == null) {
                return;
            }
            LongSparseArray longSparseArray = (LongSparseArray) this.mWakingActivity.get(i);
            if (longSparseArray == null) {
                longSparseArray = new LongSparseArray();
                this.mWakingActivity.put(i, longSparseArray);
            }
            SparseIntArray sparseIntArray2 = (SparseIntArray) longSparseArray.get(j);
            if (sparseIntArray2 == null) {
                longSparseArray.put(j, sparseIntArray.clone());
            } else {
                for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
                    int keyAt = sparseIntArray.keyAt(i2);
                    if (sparseIntArray2.indexOfKey(keyAt) < 0) {
                        sparseIntArray2.put(keyAt, sparseIntArray.valueAt(i2));
                    }
                }
            }
            for (int lastIndexOnOrBefore =
                            longSparseArray.lastIndexOnOrBefore(
                                    j - this.mRetentionSupplier.getAsLong());
                    lastIndexOnOrBefore >= 0;
                    lastIndexOnOrBefore--) {
                longSparseArray.removeAt(lastIndexOnOrBefore);
            }
        }

        public final SparseIntArray removeBetween(int i, long j, long j2) {
            SparseIntArray sparseIntArray = new SparseIntArray();
            LongSparseArray longSparseArray = (LongSparseArray) this.mWakingActivity.get(i);
            if (longSparseArray != null) {
                int firstIndexOnOrAfter = longSparseArray.firstIndexOnOrAfter(j);
                int lastIndexOnOrBefore = longSparseArray.lastIndexOnOrBefore(j2);
                for (int i2 = lastIndexOnOrBefore; i2 >= firstIndexOnOrAfter; i2--) {
                    SparseIntArray sparseIntArray2 = (SparseIntArray) longSparseArray.valueAt(i2);
                    for (int i3 = 0; i3 < sparseIntArray2.size(); i3++) {
                        sparseIntArray.put(sparseIntArray2.keyAt(i3), sparseIntArray2.valueAt(i3));
                    }
                }
                while (lastIndexOnOrBefore >= firstIndexOnOrAfter) {
                    longSparseArray.removeAt(lastIndexOnOrBefore);
                    lastIndexOnOrBefore--;
                }
            }
            if (sparseIntArray.size() > 0) {
                return sparseIntArray;
            }
            return null;
        }
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.server.power.stats.wakeups.CpuWakeupStats$$ExternalSyntheticLambda0] */
    public CpuWakeupStats(Context context, Handler handler) {
        IrqDeviceMap irqDeviceMap;
        Config config = new Config();
        config.WAKEUP_STATS_RETENTION_MS = Config.DEFAULT_WAKEUP_STATS_RETENTION_MS;
        config.WAKEUP_MATCHING_WINDOW_MS = Config.DEFAULT_WAKEUP_MATCHING_WINDOW_MS;
        config.WAKING_ACTIVITY_RETENTION_MS = Config.DEFAULT_WAKING_ACTIVITY_RETENTION_MS;
        this.mConfig = config;
        this.mWakeupEvents = new LongSparseArray();
        this.mWakeupAttribution = new LongSparseArray();
        this.mUidProcStates = new SparseIntArray();
        this.mReusableUidProcStates = new SparseIntArray(4);
        LongSparseArray longSparseArray = IrqDeviceMap.sInstanceMap;
        synchronized (IrqDeviceMap.class) {
            try {
                LongSparseArray longSparseArray2 = IrqDeviceMap.sInstanceMap;
                long j = R.xml.password_kbd_qwerty_shifted;
                int indexOfKey = longSparseArray2.indexOfKey(j);
                if (indexOfKey >= 0) {
                    irqDeviceMap = (IrqDeviceMap) longSparseArray2.valueAt(indexOfKey);
                } else {
                    IrqDeviceMap irqDeviceMap2 =
                            new IrqDeviceMap(
                                    context.getResources()
                                            .getXml(R.xml.password_kbd_qwerty_shifted));
                    synchronized (IrqDeviceMap.class) {
                        longSparseArray2.put(j, irqDeviceMap2);
                    }
                    irqDeviceMap = irqDeviceMap2;
                }
            } finally {
            }
        }
        this.mIrqDeviceMap = irqDeviceMap;
        this.mRecentWakingActivity =
                new WakingActivityHistory(
                        new LongSupplier() { // from class:
                                             // com.android.server.power.stats.wakeups.CpuWakeupStats$$ExternalSyntheticLambda0
                            @Override // java.util.function.LongSupplier
                            public final long getAsLong() {
                                return CpuWakeupStats.this.mConfig.WAKING_ACTIVITY_RETENTION_MS;
                            }
                        });
        this.mHandler = handler;
    }

    public static String subsystemToString(int i) {
        return i != -1
                ? i != 1
                        ? i != 2
                                ? i != 3
                                        ? i != 4 ? i != 5 ? "N/A" : "Cellular_data" : "Sensor"
                                        : "Sound_trigger"
                                : "Wifi"
                        : "Alarm"
                : "Unknown";
    }

    public final synchronized void attemptAttributionFor(Wakeup wakeup) {
        try {
            SparseBooleanArray sparseBooleanArray = wakeup.mResponsibleSubsystems;
            SparseArray sparseArray =
                    (SparseArray) this.mWakeupAttribution.get(wakeup.mElapsedMillis);
            if (sparseArray == null) {
                sparseArray = new SparseArray();
                this.mWakeupAttribution.put(wakeup.mElapsedMillis, sparseArray);
            }
            long j = this.mConfig.WAKEUP_MATCHING_WINDOW_MS;
            for (int i = 0; i < sparseBooleanArray.size(); i++) {
                int keyAt = sparseBooleanArray.keyAt(i);
                long j2 = wakeup.mElapsedMillis;
                sparseArray.put(
                        keyAt, this.mRecentWakingActivity.removeBetween(keyAt, j2 - j, j2 + j));
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized boolean attemptAttributionWith(
            int i, long j, SparseIntArray sparseIntArray) {
        try {
            long j2 = this.mConfig.WAKEUP_MATCHING_WINDOW_MS;
            int firstIndexOnOrAfter = this.mWakeupEvents.firstIndexOnOrAfter(j - j2);
            int lastIndexOnOrBefore = this.mWakeupEvents.lastIndexOnOrBefore(j + j2);
            while (true) {
                if (firstIndexOnOrAfter > lastIndexOnOrBefore) {
                    return false;
                }
                Wakeup wakeup = (Wakeup) this.mWakeupEvents.valueAt(firstIndexOnOrAfter);
                if (wakeup.mResponsibleSubsystems.get(i)) {
                    SparseArray sparseArray =
                            (SparseArray) this.mWakeupAttribution.get(wakeup.mElapsedMillis);
                    if (sparseArray == null) {
                        sparseArray = new SparseArray();
                        this.mWakeupAttribution.put(wakeup.mElapsedMillis, sparseArray);
                    }
                    SparseIntArray sparseIntArray2 = (SparseIntArray) sparseArray.get(i);
                    if (sparseIntArray2 == null) {
                        sparseArray.put(i, sparseIntArray.clone());
                    } else {
                        for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
                            sparseIntArray2.put(
                                    sparseIntArray.keyAt(i2), sparseIntArray.valueAt(i2));
                        }
                    }
                    return true;
                }
                firstIndexOnOrAfter++;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void dump(IndentingPrintWriter indentingPrintWriter, long j) {
        try {
            indentingPrintWriter.println("CPU wakeup stats:");
            indentingPrintWriter.increaseIndent();
            this.mConfig.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            this.mIrqDeviceMap.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            this.mRecentWakingActivity.dump(indentingPrintWriter, j);
            indentingPrintWriter.println();
            indentingPrintWriter.println(
                    "Current proc-state map (" + this.mUidProcStates.size() + "):");
            indentingPrintWriter.increaseIndent();
            for (int i = 0; i < this.mUidProcStates.size(); i++) {
                if (i > 0) {
                    indentingPrintWriter.print(", ");
                }
                UserHandle.formatUid(indentingPrintWriter, this.mUidProcStates.keyAt(i));
                indentingPrintWriter.print(
                        ":" + ActivityManager.procStateToString(this.mUidProcStates.valueAt(i)));
            }
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            SparseLongArray sparseLongArray = new SparseLongArray();
            indentingPrintWriter.println("Wakeup events:");
            indentingPrintWriter.increaseIndent();
            for (int size = this.mWakeupEvents.size() - 1; size >= 0; size--) {
                TimeUtils.formatDuration(this.mWakeupEvents.keyAt(size), j, indentingPrintWriter);
                indentingPrintWriter.println(":");
                indentingPrintWriter.increaseIndent();
                Wakeup wakeup = (Wakeup) this.mWakeupEvents.valueAt(size);
                indentingPrintWriter.println(wakeup);
                indentingPrintWriter.print("Attribution: ");
                SparseArray sparseArray =
                        (SparseArray) this.mWakeupAttribution.get(wakeup.mElapsedMillis);
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
                                UserHandle.formatUid(
                                        indentingPrintWriter, sparseIntArray.keyAt(i3));
                                indentingPrintWriter.print(
                                        " "
                                                + ActivityManager.procStateToString(
                                                        sparseIntArray.valueAt(i3)));
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
                indentingPrintWriter.print(
                        "Subsystem " + subsystemToString(sparseLongArray.keyAt(i4)));
                indentingPrintWriter.print(": ");
                long valueAt = sparseLongArray.valueAt(i4);
                indentingPrintWriter.println(
                        IntPair.first(valueAt) + "/" + IntPair.second(valueAt));
            }
            indentingPrintWriter.println("Total: " + this.mWakeupEvents.size());
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void noteWakeupTimeAndReason(long j, long j2, String str) {
        try {
            final Wakeup parseWakeup = Wakeup.parseWakeup(str, j, j2, this.mIrqDeviceMap);
            if (parseWakeup == null) {
                return;
            }
            this.mWakeupEvents.put(j, parseWakeup);
            attemptAttributionFor(parseWakeup);
            long j3 = j - this.mConfig.WAKEUP_STATS_RETENTION_MS;
            for (int lastIndexOnOrBefore = this.mWakeupEvents.lastIndexOnOrBefore(j3);
                    lastIndexOnOrBefore >= 0;
                    lastIndexOnOrBefore--) {
                this.mWakeupEvents.removeAt(lastIndexOnOrBefore);
            }
            for (int lastIndexOnOrBefore2 = this.mWakeupAttribution.lastIndexOnOrBefore(j3);
                    lastIndexOnOrBefore2 >= 0;
                    lastIndexOnOrBefore2--) {
                this.mWakeupAttribution.removeAt(lastIndexOnOrBefore2);
            }
            this.mHandler.postDelayed(
                    new Runnable() { // from class:
                        // com.android.server.power.stats.wakeups.CpuWakeupStats$$ExternalSyntheticLambda1
                        /* JADX WARN: Removed duplicated region for block: B:30:0x00a9  */
                        /* JADX WARN: Removed duplicated region for block: B:33:0x00b2  */
                        /* JADX WARN: Removed duplicated region for block: B:43:0x00d4  */
                        /* JADX WARN: Removed duplicated region for block: B:48:0x0104 A[SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:51:0x00c3  */
                        /* JADX WARN: Removed duplicated region for block: B:53:0x00af  */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final void run() {
                            /*
                                Method dump skipped, instructions count: 282
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.server.power.stats.wakeups.CpuWakeupStats$$ExternalSyntheticLambda1.run():void");
                        }
                    },
                    WAKEUP_WRITE_DELAY_MS);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void noteWakingActivity(long j, int[] iArr, int i) {
        if (iArr == null) {
            return;
        }
        try {
            this.mReusableUidProcStates.clear();
            for (int i2 : iArr) {
                this.mReusableUidProcStates.put(i2, this.mUidProcStates.get(i2, -1));
            }
            if (!attemptAttributionWith(i, j, this.mReusableUidProcStates)) {
                this.mRecentWakingActivity.recordActivity(i, j, this.mReusableUidProcStates);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void systemServicesReady() {
        Config config = this.mConfig;
        HandlerExecutor handlerExecutor = new HandlerExecutor(this.mHandler);
        config.getClass();
        DeviceConfig.addOnPropertiesChangedListener("battery_stats", handlerExecutor, config);
        config.onPropertiesChanged(
                DeviceConfig.getProperties("battery_stats", Config.PROPERTY_NAMES));
    }
}
