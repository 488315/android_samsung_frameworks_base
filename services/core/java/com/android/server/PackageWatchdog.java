package com.android.server;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.VersionedPackage;
import android.net.ConnectivityModuleConnector;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.provider.DeviceConfig;
import android.service.watchdog.ExplicitHealthCheckService;
import android.sysprop.CrashRecoveryProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.EventLog;
import android.util.LongArrayQueue;
import android.util.Slog;
import android.util.Xml;

import com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.Flags;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import libcore.io.IoUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class PackageWatchdog {
    static final int DEFAULT_BOOT_LOOP_TRIGGER_COUNT = 5;
    public static final long DEFAULT_BOOT_LOOP_TRIGGER_WINDOW_MS;
    static final long DEFAULT_DEESCALATION_WINDOW_MS;
    static final long DEFAULT_MITIGATION_WINDOW_MS;
    static final long DEFAULT_OBSERVING_DURATION_MS;
    static final int DEFAULT_TRIGGER_FAILURE_COUNT = 5;
    static final int DEFAULT_TRIGGER_FAILURE_DURATION_MS;
    public static final long NATIVE_CRASH_POLLING_INTERVAL_MILLIS;
    public static PackageWatchdog sPackageWatchdog;
    public final ArrayMap mAllObservers;
    public final BootThreshold mBootThreshold;
    public final ConnectivityModuleConnector mConnectivityModuleConnector;
    public final Context mContext;
    public final ExplicitHealthCheckController mHealthCheckController;
    public boolean mIsPackagesReady;
    public long mLastMitigation;
    public final Object mLock;
    public final Handler mLongTaskHandler;
    public long mNumberOfNativeCrashPollsRemaining;
    public final PackageWatchdog$$ExternalSyntheticLambda3 mOnPropertyChangedListener;
    public final AtomicFile mPolicyFile;
    public Set mRequestedHealthCheckPackages;
    public final PackageWatchdog$$ExternalSyntheticLambda0 mSaveToFile;
    public final Handler mShortTaskHandler;
    public final PackageWatchdog$$ExternalSyntheticLambda0 mSyncRequests;
    public boolean mSyncRequired;
    public final PackageWatchdog$$ExternalSyntheticLambda0 mSyncStateWithScheduledReason;
    public final SystemClock mSystemClock;
    public int mTriggerFailureCount;
    public int mTriggerFailureDurationMs;
    public long mUptimeAtLastStateSync;

    public final class BootThreshold {
        public final long mTriggerWindow;

        public BootThreshold(long j) {
            this.mTriggerWindow = j;
        }

        public static void saveMitigationCountToMetadata() {
            try {
                BufferedWriter bufferedWriter =
                        new BufferedWriter(
                                new FileWriter("/metadata/watchdog/mitigation_count.txt"));
                try {
                    bufferedWriter.write(
                            String.valueOf(
                                    ((Integer)
                                                    CrashRecoveryProperties.bootMitigationCount()
                                                            .orElse(0))
                                            .intValue()));
                    bufferedWriter.close();
                } finally {
                }
            } catch (Exception e) {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        e, "Could not save metadata to file: ", "PackageWatchdog");
            }
        }

        public final boolean incrementAndTest() {
            boolean recoverabilityDetection = Flags.recoverabilityDetection();
            PackageWatchdog packageWatchdog = PackageWatchdog.this;
            if (recoverabilityDetection) {
                File file = new File("/metadata/watchdog/mitigation_count.txt");
                if (file.exists()) {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        ObjectInputStream objectInputStream =
                                new ObjectInputStream(fileInputStream);
                        HashMap hashMap = (HashMap) objectInputStream.readObject();
                        objectInputStream.close();
                        fileInputStream.close();
                        for (int i = 0; i < packageWatchdog.mAllObservers.size(); i++) {
                            ObserverInternal observerInternal =
                                    (ObserverInternal) packageWatchdog.mAllObservers.valueAt(i);
                            if (hashMap.containsKey(observerInternal.name)) {
                                observerInternal.mMitigationCount =
                                        ((Integer) hashMap.get(observerInternal.name)).intValue();
                            }
                        }
                    } catch (Exception e) {
                        PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                                e, "Could not read observer metadata file: ", "PackageWatchdog");
                    }
                }
            } else {
                File file2 = new File("/metadata/watchdog/mitigation_count.txt");
                if (file2.exists()) {
                    try {
                        BufferedReader bufferedReader =
                                new BufferedReader(
                                        new FileReader("/metadata/watchdog/mitigation_count.txt"));
                        try {
                            CrashRecoveryProperties.bootMitigationCount(
                                    Integer.valueOf(Integer.parseInt(bufferedReader.readLine())));
                            file2.delete();
                            bufferedReader.close();
                        } finally {
                        }
                    } catch (Exception e2) {
                        PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                                e2, "Could not read metadata file: ", "PackageWatchdog");
                    }
                }
            }
            long uptimeMillis = packageWatchdog.mSystemClock.uptimeMillis();
            if (uptimeMillis
                            - ((Long) CrashRecoveryProperties.rescueBootStart().orElse(0L))
                                    .longValue()
                    < 0) {
                Slog.e(
                        "PackageWatchdog",
                        "Window was less than zero. Resetting start to current time.");
                long uptimeMillis2 = packageWatchdog.mSystemClock.uptimeMillis();
                if (uptimeMillis < 0) {
                    uptimeMillis2 = 0;
                } else if (uptimeMillis <= uptimeMillis2) {
                    uptimeMillis2 = uptimeMillis;
                }
                CrashRecoveryProperties.rescueBootStart(Long.valueOf(uptimeMillis2));
                long uptimeMillis3 = packageWatchdog.mSystemClock.uptimeMillis();
                if (uptimeMillis < 0) {
                    uptimeMillis3 = 0;
                } else if (uptimeMillis <= uptimeMillis3) {
                    uptimeMillis3 = uptimeMillis;
                }
                CrashRecoveryProperties.bootMitigationStart(Long.valueOf(uptimeMillis3));
            }
            if (uptimeMillis
                            - ((Long) CrashRecoveryProperties.bootMitigationStart().orElse(0L))
                                    .longValue()
                    > PackageWatchdog.DEFAULT_DEESCALATION_WINDOW_MS) {
                long uptimeMillis4 = packageWatchdog.mSystemClock.uptimeMillis();
                if (uptimeMillis < 0) {
                    uptimeMillis4 = 0;
                } else if (uptimeMillis <= uptimeMillis4) {
                    uptimeMillis4 = uptimeMillis;
                }
                CrashRecoveryProperties.bootMitigationStart(Long.valueOf(uptimeMillis4));
                if (Flags.recoverabilityDetection()) {
                    for (int i2 = 0; i2 < packageWatchdog.mAllObservers.size(); i2++) {
                        ((ObserverInternal) packageWatchdog.mAllObservers.valueAt(i2))
                                        .mMitigationCount =
                                0;
                    }
                    packageWatchdog.saveAllObserversBootMitigationCountToMetadata();
                } else {
                    CrashRecoveryProperties.bootMitigationCount(0);
                }
            }
            long longValue =
                    uptimeMillis
                            - ((Long) CrashRecoveryProperties.rescueBootStart().orElse(0L))
                                    .longValue();
            if (longValue >= this.mTriggerWindow) {
                CrashRecoveryProperties.rescueBootCount(1);
                long uptimeMillis5 = packageWatchdog.mSystemClock.uptimeMillis();
                if (uptimeMillis < 0) {
                    uptimeMillis = 0;
                } else if (uptimeMillis > uptimeMillis5) {
                    uptimeMillis = uptimeMillis5;
                }
                CrashRecoveryProperties.rescueBootStart(Long.valueOf(uptimeMillis));
                return false;
            }
            int intValue =
                    ((Integer) CrashRecoveryProperties.rescueBootCount().orElse(0)).intValue() + 1;
            CrashRecoveryProperties.rescueBootCount(Integer.valueOf(intValue));
            SystemProperties.set("sys.rescue_boot_count", Integer.toString(intValue));
            EventLog.writeEvent(2900, 0, Integer.valueOf(intValue), Long.valueOf(longValue));
            if (!Flags.recoverabilityDetection()) {
                return intValue >= 5;
            }
            if (intValue < 5) {
                Iterator it = packageWatchdog.mAllObservers.values().iterator();
                while (it.hasNext()) {
                    if (((ObserverInternal) it.next()).mMitigationCount > 0) {
                        if (intValue <= 1) {
                            return false;
                        }
                    }
                }
                return false;
            }
            return true;
        }
    }

    public final class MonitoredPackage {
        public long mDurationMs;
        public boolean mHasPassedHealthCheck;
        public long mHealthCheckDurationMs;
        public final LongArrayQueue mMitigationCalls;
        public final String mPackageName;
        public final LongArrayQueue mFailureHistory = new LongArrayQueue();
        public int mHealthCheckState = 1;

        public MonitoredPackage(
                String str, long j, long j2, boolean z, LongArrayQueue longArrayQueue) {
            this.mPackageName = str;
            this.mDurationMs = j;
            this.mHealthCheckDurationMs = j2;
            this.mHasPassedHealthCheck = z;
            this.mMitigationCalls = longArrayQueue;
            updateHealthCheckStateLocked();
        }

        public static String toString(int i) {
            return i != 0
                    ? i != 1 ? i != 2 ? i != 3 ? "UNKNOWN" : "FAILED" : "PASSED" : "INACTIVE"
                    : "ACTIVE";
        }

        public final int getMitigationCountLocked() {
            try {
                long uptimeMillis = PackageWatchdog.this.mSystemClock.uptimeMillis();
                while (uptimeMillis - this.mMitigationCalls.peekFirst()
                        > PackageWatchdog.DEFAULT_DEESCALATION_WINDOW_MS) {
                    this.mMitigationCalls.removeFirst();
                }
            } catch (NoSuchElementException unused) {
            }
            return this.mMitigationCalls.size();
        }

        public boolean isEqualTo(MonitoredPackage monitoredPackage) {
            return this.mPackageName.equals(monitoredPackage.mPackageName)
                    && this.mDurationMs == monitoredPackage.mDurationMs
                    && this.mHasPassedHealthCheck == monitoredPackage.mHasPassedHealthCheck
                    && this.mHealthCheckDurationMs == monitoredPackage.mHealthCheckDurationMs
                    && this.mMitigationCalls
                            .toString()
                            .equals(monitoredPackage.mMitigationCalls.toString());
        }

        public final int setHealthCheckActiveLocked(long j) {
            if (j <= 0) {
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                "Cannot set non-positive health check duration ",
                                j,
                                "ms for package ");
                m.append(this.mPackageName);
                m.append(". Using total duration ");
                m.append(this.mDurationMs);
                m.append("ms instead");
                Slog.wtf("PackageWatchdog", m.toString());
                j = this.mDurationMs;
            }
            if (this.mHealthCheckState == 1) {
                this.mHealthCheckDurationMs = j;
            }
            return updateHealthCheckStateLocked();
        }

        public final int updateHealthCheckStateLocked() {
            int i = this.mHealthCheckState;
            if (this.mHasPassedHealthCheck) {
                this.mHealthCheckState = 2;
            } else {
                long j = this.mHealthCheckDurationMs;
                if (j <= 0 || this.mDurationMs <= 0) {
                    this.mHealthCheckState = 3;
                } else if (j == Long.MAX_VALUE) {
                    this.mHealthCheckState = 1;
                } else {
                    this.mHealthCheckState = 0;
                }
            }
            if (i != this.mHealthCheckState) {
                Slog.i(
                        "PackageWatchdog",
                        "Updated health check state for package "
                                + this.mPackageName
                                + ": "
                                + toString(i)
                                + " -> "
                                + toString(this.mHealthCheckState));
            }
            return this.mHealthCheckState;
        }

        public final void writeLocked(TypedXmlSerializer typedXmlSerializer) {
            String str;
            typedXmlSerializer.startTag((String) null, "package");
            typedXmlSerializer.attribute((String) null, "name", this.mPackageName);
            typedXmlSerializer.attributeLong((String) null, "duration", this.mDurationMs);
            typedXmlSerializer.attributeLong(
                    (String) null, "health-check-duration", this.mHealthCheckDurationMs);
            typedXmlSerializer.attributeBoolean(
                    (String) null, "passed-health-check", this.mHasPassedHealthCheck);
            LongArrayQueue longArrayQueue = new LongArrayQueue();
            long uptimeMillis = PackageWatchdog.this.mSystemClock.uptimeMillis();
            for (int i = 0; i < this.mMitigationCalls.size(); i++) {
                longArrayQueue.addLast(this.mMitigationCalls.get(i) - uptimeMillis);
            }
            if (longArrayQueue.size() > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(longArrayQueue.get(0));
                for (int i2 = 1; i2 < longArrayQueue.size(); i2++) {
                    sb.append(",");
                    sb.append(longArrayQueue.get(i2));
                }
                str = sb.toString();
            } else {
                str = "";
            }
            typedXmlSerializer.attribute((String) null, "mitigation-calls", str);
            typedXmlSerializer.endTag((String) null, "package");
        }
    }

    public final class ObserverInternal {
        public int mMitigationCount;
        public final ArrayMap mPackages = new ArrayMap();
        public final String name;
        public PackageHealthObserver registeredObserver;

        public ObserverInternal(int i, String str, List list) {
            this.name = str;
            updatePackagesLocked(list);
            this.mMitigationCount = i;
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static com.android.server.PackageWatchdog.ObserverInternal read(
                com.android.modules.utils.TypedXmlPullParser r9,
                com.android.server.PackageWatchdog r10) {
            /*
                java.lang.String r0 = r9.getName()
                java.lang.String r1 = "observer"
                boolean r0 = r1.equals(r0)
                java.lang.String r1 = "PackageWatchdog"
                r2 = 0
                if (r0 == 0) goto L23
                java.lang.String r0 = "name"
                java.lang.String r0 = r9.getAttributeValue(r2, r0)
                boolean r3 = android.text.TextUtils.isEmpty(r0)
                if (r3 == 0) goto L24
                java.lang.String r9 = "Unable to read observer name"
                android.util.Slog.wtf(r1, r9)
                return r2
            L23:
                r0 = r2
            L24:
                java.util.ArrayList r3 = new java.util.ArrayList
                r3.<init>()
                int r4 = r9.getDepth()
                boolean r5 = com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.Flags.recoverabilityDetection()     // Catch: java.lang.Throwable -> L3b
                if (r5 == 0) goto L42
                java.lang.String r5 = "mitigation-count"
                int r5 = r9.getAttributeInt(r2, r5)     // Catch: java.lang.Throwable -> L3b org.xmlpull.v1.XmlPullParserException -> L3d
                goto L43
            L3b:
                r9 = move-exception
                goto L81
            L3d:
                java.lang.String r5 = "ObserverInternal mitigation count was not present."
                android.util.Slog.i(r1, r5)     // Catch: java.lang.Throwable -> L3b java.lang.Throwable -> L3b
            L42:
                r5 = 0
            L43:
                boolean r6 = com.android.internal.util.XmlUtils.nextElementWithin(r9, r4)     // Catch: java.lang.Throwable -> L3b java.lang.Throwable -> L3b
                if (r6 == 0) goto L74
                java.lang.String r6 = "package"
                java.lang.String r7 = r9.getName()     // Catch: java.lang.Throwable -> L3b java.lang.Throwable -> L3b
                boolean r6 = r6.equals(r7)     // Catch: java.lang.Throwable -> L3b java.lang.Throwable -> L3b
                if (r6 == 0) goto L43
                com.android.server.PackageWatchdog$MonitoredPackage r6 = r10.parseMonitoredPackage(r9)     // Catch: java.lang.Throwable -> L3b java.lang.Throwable -> L3b java.lang.NumberFormatException -> L5e
                r3.add(r6)     // Catch: java.lang.Throwable -> L3b java.lang.Throwable -> L3b java.lang.NumberFormatException -> L5e
                goto L43
            L5e:
                r6 = move-exception
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3b java.lang.Throwable -> L3b
                r7.<init>()     // Catch: java.lang.Throwable -> L3b java.lang.Throwable -> L3b
                java.lang.String r8 = "Skipping package for observer "
                r7.append(r8)     // Catch: java.lang.Throwable -> L3b java.lang.Throwable -> L3b
                r7.append(r0)     // Catch: java.lang.Throwable -> L3b java.lang.Throwable -> L3b
                java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L3b java.lang.Throwable -> L3b
                android.util.Slog.wtf(r1, r7, r6)     // Catch: java.lang.Throwable -> L3b java.lang.Throwable -> L3b
                goto L43
            L74:
                boolean r9 = r3.isEmpty()
                if (r9 == 0) goto L7b
                return r2
            L7b:
                com.android.server.PackageWatchdog$ObserverInternal r9 = new com.android.server.PackageWatchdog$ObserverInternal
                r9.<init>(r5, r0, r3)
                return r9
            L81:
                java.lang.StringBuilder r10 = new java.lang.StringBuilder
                java.lang.String r3 = "Unable to read observer "
                r10.<init>(r3)
                r10.append(r0)
                java.lang.String r10 = r10.toString()
                android.util.Slog.wtf(r1, r10, r9)
                return r2
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.PackageWatchdog.ObserverInternal.read(com.android.modules.utils.TypedXmlPullParser,"
                        + " com.android.server.PackageWatchdog):com.android.server.PackageWatchdog$ObserverInternal");
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("Persistent: " + (this.registeredObserver != null));
            for (String str : this.mPackages.keySet()) {
                MonitoredPackage monitoredPackage = getMonitoredPackage(str);
                indentingPrintWriter.println(str + ": ");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println(
                        "# Failures: " + monitoredPackage.mFailureHistory.size());
                indentingPrintWriter.println(
                        "Monitoring duration remaining: " + monitoredPackage.mDurationMs + "ms");
                indentingPrintWriter.println(
                        "Explicit health check duration: "
                                + monitoredPackage.mHealthCheckDurationMs
                                + "ms");
                indentingPrintWriter.println(
                        "Health check state: "
                                .concat(
                                        MonitoredPackage.toString(
                                                monitoredPackage.mHealthCheckState)));
                indentingPrintWriter.decreaseIndent();
            }
        }

        public final MonitoredPackage getMonitoredPackage(String str) {
            return (MonitoredPackage) this.mPackages.get(str);
        }

        public final boolean onPackageFailureLocked(String str) {
            if (getMonitoredPackage(str) == null) {
                this.registeredObserver.getClass();
                if (this.registeredObserver.mayObservePackage(str)) {
                    PackageWatchdog packageWatchdog = PackageWatchdog.sPackageWatchdog;
                    long j = PackageWatchdog.DEFAULT_OBSERVING_DURATION_MS;
                    packageWatchdog.getClass();
                    MonitoredPackage monitoredPackage =
                            packageWatchdog
                            .new MonitoredPackage(
                                    str, j, Long.MAX_VALUE, false, new LongArrayQueue());
                    this.mPackages.put(monitoredPackage.mPackageName, monitoredPackage);
                }
            }
            MonitoredPackage monitoredPackage2 = getMonitoredPackage(str);
            if (monitoredPackage2 != null) {
                PackageWatchdog packageWatchdog2 = PackageWatchdog.this;
                long uptimeMillis = packageWatchdog2.mSystemClock.uptimeMillis();
                monitoredPackage2.mFailureHistory.addLast(uptimeMillis);
                while (uptimeMillis - monitoredPackage2.mFailureHistory.peekFirst()
                        > packageWatchdog2.mTriggerFailureDurationMs) {
                    monitoredPackage2.mFailureHistory.removeFirst();
                }
                r11 =
                        monitoredPackage2.mFailureHistory.size()
                                >= packageWatchdog2.mTriggerFailureCount;
                if (r11) {
                    monitoredPackage2.mFailureHistory.clear();
                }
            }
            return r11;
        }

        public final void updatePackagesLocked(List list) {
            int i = 0;
            while (true) {
                ArrayList arrayList = (ArrayList) list;
                if (i >= arrayList.size()) {
                    return;
                }
                MonitoredPackage monitoredPackage = (MonitoredPackage) arrayList.get(i);
                MonitoredPackage monitoredPackage2 =
                        getMonitoredPackage(monitoredPackage.mPackageName);
                if (monitoredPackage2 != null) {
                    monitoredPackage2.mDurationMs = monitoredPackage.mDurationMs;
                } else {
                    this.mPackages.put(monitoredPackage.mPackageName, monitoredPackage);
                }
                i++;
            }
        }

        public final void writeLocked(TypedXmlSerializer typedXmlSerializer) {
            try {
                typedXmlSerializer.startTag((String) null, "observer");
                typedXmlSerializer.attribute((String) null, "name", this.name);
                if (Flags.recoverabilityDetection()) {
                    typedXmlSerializer.attributeInt(
                            (String) null, "mitigation-count", this.mMitigationCount);
                }
                for (int i = 0; i < this.mPackages.size(); i++) {
                    ((MonitoredPackage) this.mPackages.valueAt(i)).writeLocked(typedXmlSerializer);
                }
                typedXmlSerializer.endTag((String) null, "observer");
            } catch (IOException e) {
                Slog.w("PackageWatchdog", "Cannot save observer", e);
            }
        }
    }

    public interface PackageHealthObserver {
        boolean execute(VersionedPackage versionedPackage, int i, int i2);

        boolean executeBootLoopMitigation(int i);

        String getName();

        boolean mayObservePackage(String str);

        int onBootLoop(int i);

        int onHealthCheckFailed(VersionedPackage versionedPackage, int i, int i2);
    }

    @FunctionalInterface
    interface SystemClock {
        long uptimeMillis();
    }

    static {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        NATIVE_CRASH_POLLING_INTERVAL_MILLIS = timeUnit.toMillis(30L);
        TimeUnit timeUnit2 = TimeUnit.MINUTES;
        DEFAULT_TRIGGER_FAILURE_DURATION_MS = (int) timeUnit2.toMillis(1L);
        DEFAULT_OBSERVING_DURATION_MS = TimeUnit.DAYS.toMillis(2L);
        DEFAULT_DEESCALATION_WINDOW_MS = TimeUnit.HOURS.toMillis(1L);
        DEFAULT_BOOT_LOOP_TRIGGER_WINDOW_MS = timeUnit2.toMillis(10L);
        DEFAULT_MITIGATION_WINDOW_MS = timeUnit.toMillis(5L);
    }

    public PackageWatchdog(Context context) {
        this(
                context,
                new AtomicFile(
                        new File(
                                new File(Environment.getDataDirectory(), "system"),
                                "package-watchdog.xml")),
                new Handler(Looper.myLooper()),
                BackgroundThread.getHandler(),
                new ExplicitHealthCheckController(context),
                ConnectivityModuleConnector.getInstance(),
                new PackageWatchdog$$ExternalSyntheticLambda4());
    }

    public PackageWatchdog(
            Context context,
            AtomicFile atomicFile,
            Handler handler,
            Handler handler2,
            ExplicitHealthCheckController explicitHealthCheckController,
            ConnectivityModuleConnector connectivityModuleConnector,
            SystemClock systemClock) {
        this.mLock = new Object();
        ArrayMap arrayMap = new ArrayMap();
        this.mAllObservers = arrayMap;
        this.mSyncRequests = new PackageWatchdog$$ExternalSyntheticLambda0(this, 0);
        this.mSyncStateWithScheduledReason = new PackageWatchdog$$ExternalSyntheticLambda0(this, 3);
        this.mSaveToFile = new PackageWatchdog$$ExternalSyntheticLambda0(this, 4);
        this.mOnPropertyChangedListener =
                new DeviceConfig
                        .OnPropertiesChangedListener() { // from class:
                                                         // com.android.server.PackageWatchdog$$ExternalSyntheticLambda3
                    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                        PackageWatchdog packageWatchdog = PackageWatchdog.this;
                        packageWatchdog.getClass();
                        try {
                            packageWatchdog.updateConfigs();
                        } catch (Exception unused) {
                            Slog.w("PackageWatchdog", "Failed to reload device config changes");
                        }
                    }
                };
        this.mRequestedHealthCheckPackages = new ArraySet();
        this.mTriggerFailureDurationMs = DEFAULT_TRIGGER_FAILURE_DURATION_MS;
        this.mTriggerFailureCount = 5;
        this.mSyncRequired = false;
        this.mLastMitigation = -1000000L;
        this.mContext = context;
        this.mPolicyFile = atomicFile;
        this.mShortTaskHandler = handler;
        this.mLongTaskHandler = handler2;
        this.mHealthCheckController = explicitHealthCheckController;
        this.mConnectivityModuleConnector = connectivityModuleConnector;
        this.mSystemClock = systemClock;
        this.mNumberOfNativeCrashPollsRemaining = 10L;
        this.mBootThreshold = new BootThreshold(DEFAULT_BOOT_LOOP_TRIGGER_WINDOW_MS);
        arrayMap.clear();
        FileInputStream fileInputStream = null;
        try {
            try {
                fileInputStream = atomicFile.openRead();
                TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
                XmlUtils.beginDocument(resolvePullParser, "package-watchdog");
                int depth = resolvePullParser.getDepth();
                while (XmlUtils.nextElementWithin(resolvePullParser, depth)) {
                    ObserverInternal read = ObserverInternal.read(resolvePullParser, this);
                    if (read != null) {
                        this.mAllObservers.put(read.name, read);
                    }
                }
            } catch (FileNotFoundException unused) {
            } catch (IOException
                    | ArrayIndexOutOfBoundsException
                    | NumberFormatException
                    | XmlPullParserException e) {
                Slog.wtf("PackageWatchdog", "Unable to read monitored packages, deleting file", e);
                this.mPolicyFile.delete();
            }
            sPackageWatchdog = this;
        } finally {
            IoUtils.closeQuietly(fileInputStream);
        }
    }

    public static PackageWatchdog getInstance(Context context) {
        PackageWatchdog packageWatchdog;
        synchronized (PackageWatchdog.class) {
            try {
                if (sPackageWatchdog == null) {
                    new PackageWatchdog(context);
                }
                packageWatchdog = sPackageWatchdog;
            } catch (Throwable th) {
                throw th;
            }
        }
        return packageWatchdog;
    }

    public final void checkAndMitigateNativeCrashes() {
        this.mNumberOfNativeCrashPollsRemaining--;
        if ("1".equals(SystemProperties.get("sys.init.updatable_crashing"))) {
            onPackageFailure(1, Collections.EMPTY_LIST);
        } else if (this.mNumberOfNativeCrashPollsRemaining > 0) {
            this.mShortTaskHandler.postDelayed(
                    new PackageWatchdog$$ExternalSyntheticLambda0(this, 2),
                    NATIVE_CRASH_POLLING_INTERVAL_MILLIS);
        }
    }

    public final Set getPackagesPendingHealthChecksLocked() {
        ArraySet arraySet = new ArraySet();
        Iterator it = this.mAllObservers.values().iterator();
        while (it.hasNext()) {
            for (MonitoredPackage monitoredPackage :
                    ((ObserverInternal) it.next()).mPackages.values()) {
                String str = monitoredPackage.mPackageName;
                int i = monitoredPackage.mHealthCheckState;
                if (i == 0 || i == 1) {
                    arraySet.add(str);
                }
            }
        }
        return arraySet;
    }

    public long getTriggerFailureCount() {
        long j;
        synchronized (this.mLock) {
            j = this.mTriggerFailureCount;
        }
        return j;
    }

    public long getTriggerFailureDurationMs() {
        long j;
        synchronized (this.mLock) {
            j = this.mTriggerFailureDurationMs;
        }
        return j;
    }

    public final VersionedPackage getVersionedPackage(String str) {
        PackageInfo packageInfo;
        if (this.mContext.getPackageManager() != null && !TextUtils.isEmpty(str)) {
            try {
                PackageManager packageManager = this.mContext.getPackageManager();
                try {
                    packageInfo = packageManager.getPackageInfo(str, 4194304);
                } catch (PackageManager.NameNotFoundException unused) {
                    packageInfo = packageManager.getPackageInfo(str, 1073741824);
                }
                return new VersionedPackage(str, packageInfo.getLongVersionCode());
            } catch (PackageManager.NameNotFoundException unused2) {
            }
        }
        return null;
    }

    public final void maybeExecute(
            PackageHealthObserver packageHealthObserver,
            VersionedPackage versionedPackage,
            int i,
            int i2,
            int i3) {
        if (i2
                < SystemProperties.getInt(
                        "persist.device_config.configuration.major_user_impact_level_threshold",
                        71)) {
            synchronized (this.mLock) {
                this.mLastMitigation = this.mSystemClock.uptimeMillis();
            }
            packageHealthObserver.execute(versionedPackage, i, i3);
        }
    }

    public final void noteBoot() {
        synchronized (this.mLock) {
            try {
                if (this.mBootThreshold.incrementAndTest()) {
                    if (!Flags.recoverabilityDetection()) {
                        BootThreshold bootThreshold = this.mBootThreshold;
                        long uptimeMillis = PackageWatchdog.this.mSystemClock.uptimeMillis();
                        long uptimeMillis2 = PackageWatchdog.this.mSystemClock.uptimeMillis();
                        if (uptimeMillis < 0) {
                            uptimeMillis = 0;
                        } else if (uptimeMillis > uptimeMillis2) {
                            uptimeMillis = uptimeMillis2;
                        }
                        CrashRecoveryProperties.rescueBootStart(Long.valueOf(uptimeMillis));
                        CrashRecoveryProperties.rescueBootCount(0);
                    }
                    this.mBootThreshold.getClass();
                    int intValue =
                            ((Integer) CrashRecoveryProperties.bootMitigationCount().orElse(0))
                                            .intValue()
                                    + 1;
                    PackageHealthObserver packageHealthObserver = null;
                    int i = Integer.MAX_VALUE;
                    ObserverInternal observerInternal = null;
                    for (int i2 = 0; i2 < this.mAllObservers.size(); i2++) {
                        ObserverInternal observerInternal2 =
                                (ObserverInternal) this.mAllObservers.valueAt(i2);
                        PackageHealthObserver packageHealthObserver2 =
                                observerInternal2.registeredObserver;
                        if (packageHealthObserver2 != null) {
                            int onBootLoop =
                                    Flags.recoverabilityDetection()
                                            ? packageHealthObserver2.onBootLoop(
                                                    observerInternal2.mMitigationCount + 1)
                                            : packageHealthObserver2.onBootLoop(intValue);
                            if (onBootLoop != 0 && onBootLoop < i) {
                                observerInternal = observerInternal2;
                                packageHealthObserver = packageHealthObserver2;
                                i = onBootLoop;
                            }
                        }
                    }
                    if (packageHealthObserver != null) {
                        if (Flags.recoverabilityDetection()) {
                            int i3 = observerInternal.mMitigationCount + 1;
                            observerInternal.mMitigationCount = i3;
                            saveAllObserversBootMitigationCountToMetadata();
                            packageHealthObserver.executeBootLoopMitigation(i3);
                        } else {
                            this.mBootThreshold.getClass();
                            CrashRecoveryProperties.bootMitigationCount(Integer.valueOf(intValue));
                            this.mBootThreshold.getClass();
                            BootThreshold.saveMitigationCountToMetadata();
                            packageHealthObserver.executeBootLoopMitigation(intValue);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onPackageFailure(final int i, final List list) {
        if (list == null) {
            Slog.w("PackageWatchdog", "Could not resolve a list of failing packages");
            return;
        }
        synchronized (this.mLock) {
            try {
                long uptimeMillis = this.mSystemClock.uptimeMillis();
                if (Flags.recoverabilityDetection()) {
                    long j = this.mLastMitigation;
                    if (uptimeMillis >= j
                            && uptimeMillis - j
                                    < SystemProperties.getLong(
                                            "persist.device_config.configuration.mitigation_window_ms",
                                            DEFAULT_MITIGATION_WINDOW_MS)) {
                        Slog.i("PackageWatchdog", "Skipping onPackageFailure mitigation");
                        return;
                    }
                }
                this.mLongTaskHandler.post(
                        new Runnable() { // from class:
                                         // com.android.server.PackageWatchdog$$ExternalSyntheticLambda8
                            @Override // java.lang.Runnable
                            public final void run() {
                                int onHealthCheckFailed;
                                int i2;
                                PackageWatchdog packageWatchdog = PackageWatchdog.this;
                                int i3 = i;
                                List list2 = list;
                                synchronized (packageWatchdog.mLock) {
                                    try {
                                        if (packageWatchdog.mAllObservers.isEmpty()) {
                                            return;
                                        }
                                        int i4 = 1;
                                        if (i3 != 1 && i3 != 2) {
                                            int i5 = 0;
                                            while (i5 < list2.size()) {
                                                VersionedPackage versionedPackage =
                                                        (VersionedPackage) list2.get(i5);
                                                PackageWatchdog.PackageHealthObserver
                                                        packageHealthObserver = null;
                                                PackageWatchdog.MonitoredPackage monitoredPackage =
                                                        null;
                                                int i6 = Integer.MAX_VALUE;
                                                for (int i7 = 0;
                                                        i7 < packageWatchdog.mAllObservers.size();
                                                        i7++) {
                                                    PackageWatchdog.ObserverInternal
                                                            observerInternal =
                                                                    (PackageWatchdog
                                                                                    .ObserverInternal)
                                                                            packageWatchdog
                                                                                    .mAllObservers
                                                                                    .valueAt(i7);
                                                    PackageWatchdog.PackageHealthObserver
                                                            packageHealthObserver2 =
                                                                    observerInternal
                                                                            .registeredObserver;
                                                    if (packageHealthObserver2 != null
                                                            && observerInternal
                                                                    .onPackageFailureLocked(
                                                                            versionedPackage
                                                                                    .getPackageName())) {
                                                        PackageWatchdog.MonitoredPackage
                                                                monitoredPackage2 =
                                                                        observerInternal
                                                                                .getMonitoredPackage(
                                                                                        versionedPackage
                                                                                                .getPackageName());
                                                        int onHealthCheckFailed2 =
                                                                packageHealthObserver2
                                                                        .onHealthCheckFailed(
                                                                                versionedPackage,
                                                                                i3,
                                                                                monitoredPackage2
                                                                                                != null
                                                                                        ? monitoredPackage2
                                                                                                        .getMitigationCountLocked()
                                                                                                + i4
                                                                                        : i4);
                                                        if (onHealthCheckFailed2 != 0
                                                                && onHealthCheckFailed2 < i6) {
                                                            monitoredPackage = monitoredPackage2;
                                                            i6 = onHealthCheckFailed2;
                                                            packageHealthObserver =
                                                                    packageHealthObserver2;
                                                        }
                                                    }
                                                }
                                                if (packageHealthObserver != null) {
                                                    if (monitoredPackage != null) {
                                                        monitoredPackage.mMitigationCalls.addLast(
                                                                PackageWatchdog.this.mSystemClock
                                                                        .uptimeMillis());
                                                        i2 =
                                                                monitoredPackage
                                                                        .getMitigationCountLocked();
                                                    } else {
                                                        i2 = 1;
                                                    }
                                                    if (Flags.recoverabilityDetection()) {
                                                        packageWatchdog.maybeExecute(
                                                                packageHealthObserver,
                                                                versionedPackage,
                                                                i3,
                                                                i6,
                                                                i2);
                                                    } else {
                                                        packageHealthObserver.execute(
                                                                versionedPackage, i3, i2);
                                                    }
                                                }
                                                i5++;
                                                i4 = 1;
                                            }
                                        }
                                        VersionedPackage versionedPackage2 =
                                                list2.size() > 0
                                                        ? (VersionedPackage) list2.get(0)
                                                        : null;
                                        Iterator it =
                                                packageWatchdog.mAllObservers.values().iterator();
                                        PackageWatchdog.PackageHealthObserver
                                                packageHealthObserver3 = null;
                                        int i8 = Integer.MAX_VALUE;
                                        while (it.hasNext()) {
                                            PackageWatchdog.PackageHealthObserver
                                                    packageHealthObserver4 =
                                                            ((PackageWatchdog.ObserverInternal)
                                                                            it.next())
                                                                    .registeredObserver;
                                            if (packageHealthObserver4 != null
                                                    && (onHealthCheckFailed =
                                                                    packageHealthObserver4
                                                                            .onHealthCheckFailed(
                                                                                    versionedPackage2,
                                                                                    i3,
                                                                                    1))
                                                            != 0
                                                    && onHealthCheckFailed < i8) {
                                                packageHealthObserver3 = packageHealthObserver4;
                                                i8 = onHealthCheckFailed;
                                            }
                                        }
                                        if (packageHealthObserver3 != null) {
                                            if (Flags.recoverabilityDetection()) {
                                                packageWatchdog.maybeExecute(
                                                        packageHealthObserver3,
                                                        versionedPackage2,
                                                        i3,
                                                        i8,
                                                        1);
                                            } else {
                                                packageHealthObserver3.execute(
                                                        versionedPackage2, i3, 1);
                                            }
                                        }
                                    } finally {
                                    }
                                }
                            }
                        });
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onPackagesReady() {
        synchronized (this.mLock) {
            this.mIsPackagesReady = true;
            ExplicitHealthCheckController explicitHealthCheckController =
                    this.mHealthCheckController;
            final int i = 0;
            Consumer consumer =
                    new Consumer(
                            this) { // from class:
                                    // com.android.server.PackageWatchdog$$ExternalSyntheticLambda9
                        public final /* synthetic */ PackageWatchdog f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            boolean z;
                            boolean z2;
                            int updateHealthCheckStateLocked;
                            int i2 = i;
                            PackageWatchdog packageWatchdog = this.f$0;
                            switch (i2) {
                                case 0:
                                    String str = (String) obj;
                                    packageWatchdog.getClass();
                                    Slog.i(
                                            "PackageWatchdog",
                                            "Health check passed for package: " + str);
                                    synchronized (packageWatchdog.mLock) {
                                        z = false;
                                        for (int i3 = 0;
                                                i3 < packageWatchdog.mAllObservers.size();
                                                i3++) {
                                            try {
                                                PackageWatchdog.MonitoredPackage monitoredPackage =
                                                        ((PackageWatchdog.ObserverInternal)
                                                                        packageWatchdog
                                                                                .mAllObservers
                                                                                .valueAt(i3))
                                                                .getMonitoredPackage(str);
                                                if (monitoredPackage != null) {
                                                    int i4 = monitoredPackage.mHealthCheckState;
                                                    boolean z3 = true;
                                                    if (i4 != 3) {
                                                        monitoredPackage.mHasPassedHealthCheck =
                                                                true;
                                                    }
                                                    if (i4
                                                            == monitoredPackage
                                                                    .updateHealthCheckStateLocked()) {
                                                        z3 = false;
                                                    }
                                                    z |= z3;
                                                }
                                            } finally {
                                            }
                                        }
                                    }
                                    if (z) {
                                        packageWatchdog.syncState("health check passed for " + str);
                                        return;
                                    }
                                    return;
                                default:
                                    List<ExplicitHealthCheckService.PackageConfig> list =
                                            (List) obj;
                                    packageWatchdog.getClass();
                                    ArrayMap arrayMap = new ArrayMap();
                                    for (ExplicitHealthCheckService.PackageConfig packageConfig :
                                            list) {
                                        arrayMap.put(
                                                packageConfig.getPackageName(),
                                                Long.valueOf(
                                                        packageConfig
                                                                .getHealthCheckTimeoutMillis()));
                                    }
                                    synchronized (packageWatchdog.mLock) {
                                        try {
                                            Slog.d(
                                                    "PackageWatchdog",
                                                    "Received supported packages " + list);
                                            Iterator it =
                                                    packageWatchdog
                                                            .mAllObservers
                                                            .values()
                                                            .iterator();
                                            z2 = false;
                                            while (it.hasNext()) {
                                                for (PackageWatchdog.MonitoredPackage
                                                        monitoredPackage2 :
                                                                ((PackageWatchdog.ObserverInternal)
                                                                                it.next())
                                                                        .mPackages.values()) {
                                                    String str2 = monitoredPackage2.mPackageName;
                                                    int i5 = monitoredPackage2.mHealthCheckState;
                                                    boolean z4 = true;
                                                    if (arrayMap.containsKey(str2)) {
                                                        updateHealthCheckStateLocked =
                                                                monitoredPackage2
                                                                        .setHealthCheckActiveLocked(
                                                                                ((Long)
                                                                                                arrayMap
                                                                                                        .get(
                                                                                                                str2))
                                                                                        .longValue());
                                                    } else {
                                                        if (monitoredPackage2.mHealthCheckState
                                                                != 3) {
                                                            monitoredPackage2
                                                                            .mHasPassedHealthCheck =
                                                                    true;
                                                        }
                                                        updateHealthCheckStateLocked =
                                                                monitoredPackage2
                                                                        .updateHealthCheckStateLocked();
                                                    }
                                                    if (i5 == updateHealthCheckStateLocked) {
                                                        z4 = false;
                                                    }
                                                    z2 |= z4;
                                                }
                                            }
                                        } finally {
                                        }
                                    }
                                    if (z2) {
                                        packageWatchdog.syncState(
                                                "updated health check supported packages " + list);
                                        return;
                                    }
                                    return;
                            }
                        }
                    };
            final int i2 = 1;
            Consumer consumer2 =
                    new Consumer(
                            this) { // from class:
                                    // com.android.server.PackageWatchdog$$ExternalSyntheticLambda9
                        public final /* synthetic */ PackageWatchdog f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            boolean z;
                            boolean z2;
                            int updateHealthCheckStateLocked;
                            int i22 = i2;
                            PackageWatchdog packageWatchdog = this.f$0;
                            switch (i22) {
                                case 0:
                                    String str = (String) obj;
                                    packageWatchdog.getClass();
                                    Slog.i(
                                            "PackageWatchdog",
                                            "Health check passed for package: " + str);
                                    synchronized (packageWatchdog.mLock) {
                                        z = false;
                                        for (int i3 = 0;
                                                i3 < packageWatchdog.mAllObservers.size();
                                                i3++) {
                                            try {
                                                PackageWatchdog.MonitoredPackage monitoredPackage =
                                                        ((PackageWatchdog.ObserverInternal)
                                                                        packageWatchdog
                                                                                .mAllObservers
                                                                                .valueAt(i3))
                                                                .getMonitoredPackage(str);
                                                if (monitoredPackage != null) {
                                                    int i4 = monitoredPackage.mHealthCheckState;
                                                    boolean z3 = true;
                                                    if (i4 != 3) {
                                                        monitoredPackage.mHasPassedHealthCheck =
                                                                true;
                                                    }
                                                    if (i4
                                                            == monitoredPackage
                                                                    .updateHealthCheckStateLocked()) {
                                                        z3 = false;
                                                    }
                                                    z |= z3;
                                                }
                                            } finally {
                                            }
                                        }
                                    }
                                    if (z) {
                                        packageWatchdog.syncState("health check passed for " + str);
                                        return;
                                    }
                                    return;
                                default:
                                    List<ExplicitHealthCheckService.PackageConfig> list =
                                            (List) obj;
                                    packageWatchdog.getClass();
                                    ArrayMap arrayMap = new ArrayMap();
                                    for (ExplicitHealthCheckService.PackageConfig packageConfig :
                                            list) {
                                        arrayMap.put(
                                                packageConfig.getPackageName(),
                                                Long.valueOf(
                                                        packageConfig
                                                                .getHealthCheckTimeoutMillis()));
                                    }
                                    synchronized (packageWatchdog.mLock) {
                                        try {
                                            Slog.d(
                                                    "PackageWatchdog",
                                                    "Received supported packages " + list);
                                            Iterator it =
                                                    packageWatchdog
                                                            .mAllObservers
                                                            .values()
                                                            .iterator();
                                            z2 = false;
                                            while (it.hasNext()) {
                                                for (PackageWatchdog.MonitoredPackage
                                                        monitoredPackage2 :
                                                                ((PackageWatchdog.ObserverInternal)
                                                                                it.next())
                                                                        .mPackages.values()) {
                                                    String str2 = monitoredPackage2.mPackageName;
                                                    int i5 = monitoredPackage2.mHealthCheckState;
                                                    boolean z4 = true;
                                                    if (arrayMap.containsKey(str2)) {
                                                        updateHealthCheckStateLocked =
                                                                monitoredPackage2
                                                                        .setHealthCheckActiveLocked(
                                                                                ((Long)
                                                                                                arrayMap
                                                                                                        .get(
                                                                                                                str2))
                                                                                        .longValue());
                                                    } else {
                                                        if (monitoredPackage2.mHealthCheckState
                                                                != 3) {
                                                            monitoredPackage2
                                                                            .mHasPassedHealthCheck =
                                                                    true;
                                                        }
                                                        updateHealthCheckStateLocked =
                                                                monitoredPackage2
                                                                        .updateHealthCheckStateLocked();
                                                    }
                                                    if (i5 == updateHealthCheckStateLocked) {
                                                        z4 = false;
                                                    }
                                                    z2 |= z4;
                                                }
                                            }
                                        } finally {
                                        }
                                    }
                                    if (z2) {
                                        packageWatchdog.syncState(
                                                "updated health check supported packages " + list);
                                        return;
                                    }
                                    return;
                            }
                        }
                    };
            PackageWatchdog$$ExternalSyntheticLambda0 packageWatchdog$$ExternalSyntheticLambda0 =
                    new PackageWatchdog$$ExternalSyntheticLambda0(this, 1);
            synchronized (explicitHealthCheckController.mLock) {
                try {
                    if (explicitHealthCheckController.mPassedConsumer == null) {
                        if (explicitHealthCheckController.mSupportedConsumer == null) {
                            if (explicitHealthCheckController.mNotifySyncRunnable != null) {}
                            explicitHealthCheckController.mPassedConsumer = consumer;
                            explicitHealthCheckController.mSupportedConsumer = consumer2;
                            explicitHealthCheckController.mNotifySyncRunnable =
                                    packageWatchdog$$ExternalSyntheticLambda0;
                        }
                    }
                    Slog.wtf(
                            "ExplicitHealthCheckController",
                            "Resetting health check controller callbacks");
                    explicitHealthCheckController.mPassedConsumer = consumer;
                    explicitHealthCheckController.mSupportedConsumer = consumer2;
                    explicitHealthCheckController.mNotifySyncRunnable =
                            packageWatchdog$$ExternalSyntheticLambda0;
                } catch (Throwable th) {
                    throw th;
                }
            }
            DeviceConfig.addOnPropertiesChangedListener(
                    "rollback", this.mContext.getMainExecutor(), this.mOnPropertyChangedListener);
            updateConfigs();
            this.mConnectivityModuleConnector.registerHealthListener(
                    new ConnectivityModuleConnector
                            .ConnectivityModuleHealthListener() { // from class:
                                                                  // com.android.server.PackageWatchdog$$ExternalSyntheticLambda13
                        @Override // android.net.ConnectivityModuleConnector.ConnectivityModuleHealthListener
                        public final void onNetworkStackFailure(String str) {
                            PackageWatchdog packageWatchdog = PackageWatchdog.this;
                            VersionedPackage versionedPackage =
                                    packageWatchdog.getVersionedPackage(str);
                            if (versionedPackage == null) {
                                Slog.wtf(
                                        "PackageWatchdog",
                                        "NetworkStack failed but could not find its package");
                            } else {
                                packageWatchdog.onPackageFailure(
                                        2, Collections.singletonList(versionedPackage));
                            }
                        }
                    });
        }
    }

    public final MonitoredPackage parseMonitoredPackage(TypedXmlPullParser typedXmlPullParser) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
        long attributeLong = typedXmlPullParser.getAttributeLong((String) null, "duration");
        long attributeLong2 =
                typedXmlPullParser.getAttributeLong((String) null, "health-check-duration");
        boolean attributeBoolean =
                typedXmlPullParser.getAttributeBoolean((String) null, "passed-health-check");
        String attributeValue2 =
                typedXmlPullParser.getAttributeValue((String) null, "mitigation-calls");
        LongArrayQueue longArrayQueue = new LongArrayQueue();
        if (!TextUtils.isEmpty(attributeValue2)) {
            for (String str : attributeValue2.split(",")) {
                longArrayQueue.addLast(Long.parseLong(str));
            }
        }
        return new MonitoredPackage(
                attributeValue, attributeLong, attributeLong2, attributeBoolean, longArrayQueue);
    }

    public final void pruneObserversLocked() {
        int updateHealthCheckStateLocked;
        long uptimeMillis =
                this.mUptimeAtLastStateSync == 0
                        ? 0L
                        : this.mSystemClock.uptimeMillis() - this.mUptimeAtLastStateSync;
        if (uptimeMillis <= 0) {
            Slog.i(
                    "PackageWatchdog",
                    "Not pruning observers, elapsed time: " + uptimeMillis + "ms");
            return;
        }
        Iterator it = this.mAllObservers.values().iterator();
        while (it.hasNext()) {
            final ObserverInternal observerInternal = (ObserverInternal) it.next();
            observerInternal.getClass();
            final ArraySet arraySet = new ArraySet();
            Iterator it2 = observerInternal.mPackages.values().iterator();
            while (it2.hasNext()) {
                MonitoredPackage monitoredPackage = (MonitoredPackage) it2.next();
                int i = monitoredPackage.mHealthCheckState;
                String str = monitoredPackage.mPackageName;
                if (uptimeMillis <= 0) {
                    HeimdAllFsService$$ExternalSyntheticOutline0.m(
                            "Cannot handle non-positive elapsed time for package ",
                            str,
                            "PackageWatchdog");
                    updateHealthCheckStateLocked = monitoredPackage.mHealthCheckState;
                } else {
                    monitoredPackage.mDurationMs -= uptimeMillis;
                    if (i == 0) {
                        monitoredPackage.mHealthCheckDurationMs -= uptimeMillis;
                    }
                    updateHealthCheckStateLocked = monitoredPackage.updateHealthCheckStateLocked();
                }
                if (i != 3 && updateHealthCheckStateLocked == 3) {
                    BootReceiver$$ExternalSyntheticOutline0.m58m(
                            "Package ", str, " failed health check", "PackageWatchdog");
                    arraySet.add(monitoredPackage);
                }
                if (monitoredPackage.mDurationMs <= 0) {
                    it2.remove();
                }
            }
            if (!arraySet.isEmpty()) {
                this.mLongTaskHandler.post(
                        new Runnable() { // from class:
                            // com.android.server.PackageWatchdog$$ExternalSyntheticLambda6
                            @Override // java.lang.Runnable
                            public final void run() {
                                PackageWatchdog packageWatchdog = PackageWatchdog.this;
                                PackageWatchdog.ObserverInternal observerInternal2 =
                                        observerInternal;
                                Set set = arraySet;
                                synchronized (packageWatchdog.mLock) {
                                    try {
                                        PackageWatchdog.PackageHealthObserver
                                                packageHealthObserver =
                                                        observerInternal2.registeredObserver;
                                        if (packageHealthObserver != null) {
                                            Iterator it3 = set.iterator();
                                            while (it3.hasNext()) {
                                                VersionedPackage versionedPackage =
                                                        packageWatchdog.getVersionedPackage(
                                                                ((PackageWatchdog.MonitoredPackage)
                                                                                it3.next())
                                                                        .mPackageName);
                                                if (versionedPackage != null) {
                                                    Slog.i(
                                                            "PackageWatchdog",
                                                            "Explicit health check failed for"
                                                                + " package "
                                                                    + versionedPackage);
                                                    packageHealthObserver.execute(
                                                            versionedPackage, 2, 1);
                                                }
                                            }
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        });
            }
            if (observerInternal.mPackages.isEmpty()
                    && observerInternal.registeredObserver == null) {
                Slog.i(
                        "PackageWatchdog",
                        "Discarding observer " + observerInternal.name + ". All packages expired");
                it.remove();
            }
        }
    }

    public final void registerHealthObserver(PackageHealthObserver packageHealthObserver) {
        synchronized (this.mLock) {
            try {
                ObserverInternal observerInternal =
                        (ObserverInternal) this.mAllObservers.get(packageHealthObserver.getName());
                if (observerInternal != null) {
                    observerInternal.registeredObserver = packageHealthObserver;
                } else {
                    ObserverInternal observerInternal2 =
                            new ObserverInternal(
                                    0, packageHealthObserver.getName(), new ArrayList());
                    observerInternal2.registeredObserver = packageHealthObserver;
                    this.mAllObservers.put(packageHealthObserver.getName(), observerInternal2);
                    syncState("added new observer");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void registerObserverInternal(ObserverInternal observerInternal) {
        this.mAllObservers.put(observerInternal.name, observerInternal);
    }

    public void removePropertyChangedListener() {
        DeviceConfig.removeOnPropertiesChangedListener(this.mOnPropertyChangedListener);
    }

    public final void saveAllObserversBootMitigationCountToMetadata() {
        HashMap hashMap = new HashMap();
        for (int i = 0; i < this.mAllObservers.size(); i++) {
            ObserverInternal observerInternal = (ObserverInternal) this.mAllObservers.valueAt(i);
            hashMap.put(observerInternal.name, Integer.valueOf(observerInternal.mMitigationCount));
        }
        try {
            FileOutputStream fileOutputStream =
                    new FileOutputStream(new File("/metadata/watchdog/mitigation_count.txt"));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(hashMap);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                    e, "Could not save observers metadata to file: ", "PackageWatchdog");
        }
    }

    public final boolean saveToFile() {
        Slog.i("PackageWatchdog", "Saving observer state to file");
        synchronized (this.mLock) {
            try {
                FileOutputStream startWrite = this.mPolicyFile.startWrite();
                try {
                    try {
                        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                        resolveSerializer.startDocument((String) null, Boolean.TRUE);
                        resolveSerializer.startTag((String) null, "package-watchdog");
                        resolveSerializer.attributeInt((String) null, "version", 1);
                        for (int i = 0; i < this.mAllObservers.size(); i++) {
                            ((ObserverInternal) this.mAllObservers.valueAt(i))
                                    .writeLocked(resolveSerializer);
                        }
                        resolveSerializer.endTag((String) null, "package-watchdog");
                        resolveSerializer.endDocument();
                        this.mPolicyFile.finishWrite(startWrite);
                        IoUtils.closeQuietly(startWrite);
                    } catch (Throwable th) {
                        IoUtils.closeQuietly(startWrite);
                        throw th;
                    }
                } catch (IOException e) {
                    Slog.w(
                            "PackageWatchdog",
                            "Failed to save monitored packages, restoring backup",
                            e);
                    this.mPolicyFile.failWrite(startWrite);
                    IoUtils.closeQuietly(startWrite);
                    return false;
                }
            } catch (IOException e2) {
                Slog.w("PackageWatchdog", "Cannot update monitored packages", e2);
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x003f, code lost:

       if (r13 > 0) goto L20;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void scheduleNextSyncStateLocked() {
        /*
            r15 = this;
            r0 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r2 = 0
            r4 = r0
            r3 = r2
        L8:
            android.util.ArrayMap r6 = r15.mAllObservers
            int r6 = r6.size()
            r7 = 0
            if (r3 >= r6) goto L50
            android.util.ArrayMap r6 = r15.mAllObservers
            java.lang.Object r6 = r6.valueAt(r3)
            com.android.server.PackageWatchdog$ObserverInternal r6 = (com.android.server.PackageWatchdog.ObserverInternal) r6
            android.util.ArrayMap r6 = r6.mPackages
            r9 = r2
        L1d:
            int r10 = r6.size()
            if (r9 >= r10) goto L4d
            java.lang.Object r10 = r6.valueAt(r9)
            com.android.server.PackageWatchdog$MonitoredPackage r10 = (com.android.server.PackageWatchdog.MonitoredPackage) r10
            long r11 = r10.mDurationMs
            int r13 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
            if (r13 <= 0) goto L30
            goto L31
        L30:
            r11 = r0
        L31:
            int r13 = r10.mHealthCheckState
            if (r13 == 0) goto L3b
            r14 = 1
            if (r13 != r14) goto L39
            goto L3b
        L39:
            r13 = r0
            goto L41
        L3b:
            long r13 = r10.mHealthCheckDurationMs
            int r10 = (r13 > r7 ? 1 : (r13 == r7 ? 0 : -1))
            if (r10 <= 0) goto L39
        L41:
            long r10 = java.lang.Math.min(r11, r13)
            int r12 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r12 >= 0) goto L4a
            r4 = r10
        L4a:
            int r9 = r9 + 1
            goto L1d
        L4d:
            int r3 = r3 + 1
            goto L8
        L50:
            android.os.Handler r2 = r15.mShortTaskHandler
            com.android.server.PackageWatchdog$$ExternalSyntheticLambda0 r3 = r15.mSyncStateWithScheduledReason
            r2.removeCallbacks(r3)
            int r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r0 != 0) goto L65
            java.lang.String r0 = "PackageWatchdog"
            java.lang.String r1 = "Cancelling state sync, nothing to sync"
            android.util.Slog.i(r0, r1)
            r15.mUptimeAtLastStateSync = r7
            goto L70
        L65:
            com.android.server.PackageWatchdog$SystemClock r0 = r15.mSystemClock
            long r0 = r0.uptimeMillis()
            r15.mUptimeAtLastStateSync = r0
            r2.postDelayed(r3, r4)
        L70:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.PackageWatchdog.scheduleNextSyncStateLocked():void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x002d, code lost:

       r7 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x003e, code lost:

       throw r7;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setExplicitHealthCheckEnabled(boolean r8) {
        /*
            r7 = this;
            java.lang.String r0 = "health check state "
            java.lang.Object r1 = r7.mLock
            monitor-enter(r1)
            com.android.server.ExplicitHealthCheckController r2 = r7.mHealthCheckController     // Catch: java.lang.Throwable -> L2d
            java.lang.String r3 = "Explicit health checks "
            java.lang.Object r4 = r2.mLock     // Catch: java.lang.Throwable -> L2d
            monitor-enter(r4)     // Catch: java.lang.Throwable -> L2d
            java.lang.String r5 = "ExplicitHealthCheckController"
            if (r8 == 0) goto L17
            java.lang.String r6 = "enabled."
            goto L1a
        L15:
            r7 = move-exception
            goto L3b
        L17:
            java.lang.String r6 = "disabled."
        L1a:
            java.lang.String r3 = r3.concat(r6)     // Catch: java.lang.Throwable -> L15
            android.util.Slog.i(r5, r3)     // Catch: java.lang.Throwable -> L15
            r2.mEnabled = r8     // Catch: java.lang.Throwable -> L15
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L15
            r2 = 1
            r7.mSyncRequired = r2     // Catch: java.lang.Throwable -> L2d
            if (r8 == 0) goto L2f
            java.lang.String r8 = "enabled"
            goto L32
        L2d:
            r7 = move-exception
            goto L3d
        L2f:
            java.lang.String r8 = "disabled"
        L32:
            java.lang.String r8 = r0.concat(r8)     // Catch: java.lang.Throwable -> L2d
            r7.syncState(r8)     // Catch: java.lang.Throwable -> L2d
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L2d
            return
        L3b:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L15
            throw r7     // Catch: java.lang.Throwable -> L2d
        L3d:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L2d
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.PackageWatchdog.setExplicitHealthCheckEnabled(boolean):void");
    }

    public final void startObservingHealth(
            final PackageHealthObserver packageHealthObserver, final List list, long j) {
        long j2 = j;
        ArrayList arrayList = (ArrayList) list;
        if (arrayList.isEmpty()) {
            Slog.wtf(
                    "PackageWatchdog",
                    "No packages to observe, ".concat(packageHealthObserver.getName()));
            return;
        }
        if (j2 < 1) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "Invalid duration ", j2, "ms for observer ");
            m.append(packageHealthObserver.getName());
            m.append(". Not observing packages ");
            m.append(list);
            Slog.wtf("PackageWatchdog", m.toString());
            j2 = DEFAULT_OBSERVING_DURATION_MS;
        }
        long j3 = j2;
        final ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList2.add(
                    new MonitoredPackage(
                            (String) arrayList.get(i),
                            j3,
                            Long.MAX_VALUE,
                            false,
                            new LongArrayQueue()));
        }
        if (arrayList2.isEmpty()) {
            return;
        }
        this.mLongTaskHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.PackageWatchdog$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        PackageWatchdog packageWatchdog = PackageWatchdog.this;
                        PackageWatchdog.PackageHealthObserver packageHealthObserver2 =
                                packageHealthObserver;
                        List list2 = list;
                        List list3 = arrayList2;
                        packageWatchdog.syncState("observing new packages");
                        synchronized (packageWatchdog.mLock) {
                            try {
                                PackageWatchdog.ObserverInternal observerInternal =
                                        (PackageWatchdog.ObserverInternal)
                                                packageWatchdog.mAllObservers.get(
                                                        packageHealthObserver2.getName());
                                if (observerInternal == null) {
                                    Slog.d(
                                            "PackageWatchdog",
                                            packageHealthObserver2.getName()
                                                    + " started monitoring health of packages "
                                                    + list2);
                                    packageWatchdog.mAllObservers.put(
                                            packageHealthObserver2.getName(),
                                            new PackageWatchdog.ObserverInternal(
                                                    0, packageHealthObserver2.getName(), list3));
                                } else {
                                    Slog.d(
                                            "PackageWatchdog",
                                            packageHealthObserver2.getName()
                                                    + " added the following packages to monitor "
                                                    + list2);
                                    observerInternal.updatePackagesLocked(list3);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        packageWatchdog.registerHealthObserver(packageHealthObserver2);
                        packageWatchdog.syncState("updated observers");
                    }
                });
    }

    public final void syncState(String str) {
        synchronized (this.mLock) {
            Slog.i("PackageWatchdog", "Syncing state, reason: " + str);
            pruneObserversLocked();
            PackageWatchdog$$ExternalSyntheticLambda0 packageWatchdog$$ExternalSyntheticLambda0 =
                    this.mSaveToFile;
            Handler handler = this.mLongTaskHandler;
            if (!handler.hasCallbacks(packageWatchdog$$ExternalSyntheticLambda0)) {
                handler.post(this.mSaveToFile);
            }
            Handler handler2 = this.mShortTaskHandler;
            PackageWatchdog$$ExternalSyntheticLambda0 packageWatchdog$$ExternalSyntheticLambda02 =
                    this.mSyncRequests;
            handler2.removeCallbacks(packageWatchdog$$ExternalSyntheticLambda02);
            handler2.post(packageWatchdog$$ExternalSyntheticLambda02);
            scheduleNextSyncStateLocked();
        }
    }

    public void updateConfigs() {
        synchronized (this.mLock) {
            try {
                int i = DeviceConfig.getInt("rollback", "watchdog_trigger_failure_count", 5);
                this.mTriggerFailureCount = i;
                if (i <= 0) {
                    this.mTriggerFailureCount = 5;
                }
                int i2 = DEFAULT_TRIGGER_FAILURE_DURATION_MS;
                int i3 =
                        DeviceConfig.getInt(
                                "rollback", "watchdog_trigger_failure_duration_millis", i2);
                this.mTriggerFailureDurationMs = i3;
                if (i3 <= 0) {
                    this.mTriggerFailureDurationMs = i2;
                }
                setExplicitHealthCheckEnabled(
                        DeviceConfig.getBoolean(
                                "rollback", "watchdog_explicit_health_check_enabled", true));
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
