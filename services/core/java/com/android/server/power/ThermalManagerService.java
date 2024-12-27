package com.android.server.power;

import android.app.StatsManager;
import android.content.Context;
import android.hardware.thermal.IThermal;
import android.hardware.thermal.IThermalChangedCallback;
import android.hardware.thermal.TemperatureThreshold;
import android.hardware.thermal.V1_0.IThermal;
import android.hardware.thermal.V1_0.ThermalStatus;
import android.hardware.thermal.V1_1.IThermalCallback;
import android.hardware.thermal.V2_0.CoolingDevice;
import android.hardware.thermal.V2_0.IThermal;
import android.hardware.thermal.V2_0.IThermalChangedCallback;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.IHwBinder;
import android.os.IThermalEventListener;
import android.os.IThermalService;
import android.os.IThermalStatusListener;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.os.Temperature;
import android.util.ArrayMap;
import android.util.EventLog;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.SystemService;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class ThermalManagerService extends SystemService {
    public final Context mContext;
    public final AtomicBoolean mHalReady;
    public ThermalHalWrapper mHalWrapper;
    public boolean mIsStatusOverride;
    public final Object mLock;
    final IThermalService.Stub mService;
    public int mStatus;
    public final ArrayMap mTemperatureMap;
    final TemperatureWatcher mTemperatureWatcher;
    public final RemoteCallbackList mThermalEventListeners;
    public final RemoteCallbackList mThermalStatusListeners;

    class TemperatureWatcher {
        public final Handler mHandler = BackgroundThread.getHandler();
        final ArrayMap mSamples = new ArrayMap();
        ArrayMap mSevereThresholds = new ArrayMap();
        public final float[] mHeadroomThresholds = new float[7];
        public long mLastForecastCallTimeMillis = 0;
        long mInactivityThresholdMillis = 10000;

        class Sample {
            public final float temperature;
            public final long time;

            public Sample(long j, float f) {
                this.time = j;
                this.temperature = f;
            }
        }

        public TemperatureWatcher() {}

        public static float normalizeTemperature(float f, float f2) {
            float f3 = f2 - 30.0f;
            return f <= f3 ? FullScreenMagnificationGestureHandler.MAX_SCALE : (f - f3) / 30.0f;
        }

        public Sample createSampleForTesting(long j, float f) {
            return new Sample(j, f);
        }

        public final float getForecast(int i) {
            float normalizeTemperature;
            synchronized (this.mSamples) {
                try {
                    this.mLastForecastCallTimeMillis = SystemClock.elapsedRealtime();
                    if (this.mSamples.isEmpty()) {
                        updateTemperature();
                    }
                    if (this.mSamples.isEmpty()) {
                        android.util.Slog.e(
                                "ThermalManagerService", "No temperature samples found");
                        FrameworkStatsLog.write(
                                FrameworkStatsLog.THERMAL_HEADROOM_CALLED,
                                Binder.getCallingUid(),
                                5,
                                Float.NaN,
                                i);
                        return Float.NaN;
                    }
                    if (this.mSevereThresholds.isEmpty()) {
                        android.util.Slog.e(
                                "ThermalManagerService", "No temperature thresholds found");
                        FrameworkStatsLog.write(
                                FrameworkStatsLog.THERMAL_HEADROOM_CALLED,
                                Binder.getCallingUid(),
                                6,
                                Float.NaN,
                                i);
                        return Float.NaN;
                    }
                    int i2 = 0;
                    float f = Float.NaN;
                    for (Map.Entry entry : this.mSamples.entrySet()) {
                        String str = (String) entry.getKey();
                        ArrayList arrayList = (ArrayList) entry.getValue();
                        Float f2 = (Float) this.mSevereThresholds.get(str);
                        if (f2 == null) {
                            i2++;
                            android.util.Slog.e(
                                    "ThermalManagerService", "No threshold found for " + str);
                        } else {
                            float f3 = ((Sample) arrayList.getLast()).temperature;
                            if (arrayList.size() < 3) {
                                normalizeTemperature = normalizeTemperature(f3, f2.floatValue());
                                if (!Float.isNaN(f) && normalizeTemperature <= f) {}
                                f = normalizeTemperature;
                            } else {
                                normalizeTemperature =
                                        normalizeTemperature(
                                                (getSlopeOf(arrayList) * i * 1000.0f) + f3,
                                                f2.floatValue());
                                if (!Float.isNaN(f) && normalizeTemperature <= f) {}
                                f = normalizeTemperature;
                            }
                        }
                    }
                    if (i2 == this.mSamples.size()) {
                        FrameworkStatsLog.write(
                                FrameworkStatsLog.THERMAL_HEADROOM_CALLED,
                                Binder.getCallingUid(),
                                6,
                                Float.NaN,
                                i);
                    } else {
                        FrameworkStatsLog.write(
                                FrameworkStatsLog.THERMAL_HEADROOM_CALLED,
                                Binder.getCallingUid(),
                                1,
                                f,
                                i);
                    }
                    return f;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public float getSlopeOf(List list) {
            long j = 0;
            float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
            float f2 = 0.0f;
            long j2 = 0;
            for (int i = 0; i < list.size(); i++) {
                Sample sample = (Sample) list.get(i);
                j2 += sample.time;
                f2 += sample.temperature;
            }
            long size = j2 / list.size();
            float size2 = f2 / list.size();
            for (int i2 = 0; i2 < list.size(); i2++) {
                Sample sample2 = (Sample) list.get(i2);
                long j3 = sample2.time - size;
                j += j3 * j3;
                f += j3 * (sample2.temperature - size2);
            }
            return f / j;
        }

        public final void updateHeadroomThreshold(int i, float f, float f2) {
            if (Float.isNaN(f)) {
                return;
            }
            synchronized (this.mSamples) {
                try {
                    if (i == 3) {
                        this.mHeadroomThresholds[i] = 1.0f;
                        return;
                    }
                    float normalizeTemperature = normalizeTemperature(f, f2);
                    if (Float.isNaN(this.mHeadroomThresholds[i])) {
                        this.mHeadroomThresholds[i] = normalizeTemperature;
                    } else {
                        float[] fArr = this.mHeadroomThresholds;
                        fArr[i] = Math.min(fArr[i], normalizeTemperature);
                    }
                } finally {
                }
            }
        }

        public final void updateTemperature() {
            synchronized (this.mSamples) {
                try {
                    if (SystemClock.elapsedRealtime() - this.mLastForecastCallTimeMillis
                            >= this.mInactivityThresholdMillis) {
                        this.mSamples.clear();
                        return;
                    }
                    this.mHandler.postDelayed(
                            new Runnable() { // from class:
                                             // com.android.server.power.ThermalManagerService$TemperatureWatcher$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ThermalManagerService.TemperatureWatcher.this
                                            .updateTemperature();
                                }
                            },
                            1000L);
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    List currentTemperatures =
                            ThermalManagerService.this.mHalWrapper.getCurrentTemperatures(3, true);
                    int i = 0;
                    while (true) {
                        ArrayList arrayList = (ArrayList) currentTemperatures;
                        if (i >= arrayList.size()) {
                            return;
                        }
                        Temperature temperature = (Temperature) arrayList.get(i);
                        if (!Float.isNaN(temperature.getValue())) {
                            ArrayList arrayList2 =
                                    (ArrayList)
                                            this.mSamples.computeIfAbsent(
                                                    temperature.getName(),
                                                    new ThermalManagerService$TemperatureWatcher$$ExternalSyntheticLambda1());
                            if (arrayList2.size() == 30) {
                                arrayList2.removeFirst();
                            }
                            arrayList2.add(new Sample(elapsedRealtime, temperature.getValue()));
                        }
                        i++;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void updateThresholds() {
            synchronized (this.mSamples) {
                try {
                    List temperatureThresholds =
                            ThermalManagerService.this.mHalWrapper.getTemperatureThresholds(
                                    3, true);
                    if (Flags.allowThermalHeadroomThresholds()) {
                        Arrays.fill(this.mHeadroomThresholds, Float.NaN);
                    }
                    for (int i = 0; i < temperatureThresholds.size(); i++) {
                        TemperatureThreshold temperatureThreshold =
                                (TemperatureThreshold) temperatureThresholds.get(i);
                        float[] fArr = temperatureThreshold.hotThrottlingThresholds;
                        if (fArr.length > 3) {
                            float f = fArr[3];
                            if (!Float.isNaN(f)) {
                                this.mSevereThresholds.put(
                                        temperatureThreshold.name, Float.valueOf(f));
                                if (Flags.allowThermalHeadroomThresholds()) {
                                    for (int i2 = 1; i2 <= 6; i2++) {
                                        if (i2 != 3) {
                                            float[] fArr2 =
                                                    temperatureThreshold.hotThrottlingThresholds;
                                            if (fArr2.length > i2) {
                                                updateHeadroomThreshold(i2, fArr2[i2], f);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class ThermalHal10Wrapper extends ThermalHalWrapper {
        public IThermal mThermalHal10;

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        public final boolean connectToHal() {
            boolean z;
            synchronized (this.mHalLock) {
                z = true;
                try {
                    IThermal service = IThermal.getService(true);
                    this.mThermalHal10 = service;
                    service.linkToDeath(new ThermalHalWrapper.DeathRecipient(), 5612L);
                    android.util.Slog.i(
                            "ThermalManagerService$ThermalHalWrapper",
                            "Thermal HAL 1.0 service connected, no thermal call back will be called"
                                + " due to legacy API.");
                } catch (RemoteException | NoSuchElementException unused) {
                    android.util.Slog.e(
                            "ThermalManagerService$ThermalHalWrapper",
                            "Thermal HAL 1.0 service not connected.");
                    this.mThermalHal10 = null;
                }
                if (this.mThermalHal10 == null) {
                    z = false;
                }
            }
            return z;
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        public final void dump(PrintWriter printWriter) {
            synchronized (this.mHalLock) {
                try {
                    printWriter.print("\t");
                    printWriter.println(
                            "ThermalHAL 1.0 connected: "
                                    .concat(this.mThermalHal10 != null ? "yes" : "no"));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        public final List getCurrentCoolingDevices(int i, boolean z) {
            synchronized (this.mHalLock) {
                ArrayList arrayList = new ArrayList();
                IThermal iThermal = this.mThermalHal10;
                if (iThermal == null) {
                    return arrayList;
                }
                try {
                    iThermal.getCoolingDevices(
                            new ThermalManagerService$ThermalHal10Wrapper$$ExternalSyntheticLambda0(
                                    arrayList, i, z, 0));
                } catch (RemoteException e) {
                    android.util.Slog.e(
                            "ThermalManagerService$ThermalHalWrapper",
                            "Couldn't getCurrentCoolingDevices, reconnecting...",
                            e);
                    connectToHal();
                }
                return arrayList;
            }
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        public final List getCurrentTemperatures(int i, boolean z) {
            synchronized (this.mHalLock) {
                ArrayList arrayList = new ArrayList();
                IThermal iThermal = this.mThermalHal10;
                if (iThermal == null) {
                    return arrayList;
                }
                try {
                    iThermal.getTemperatures(
                            new ThermalManagerService$ThermalHal10Wrapper$$ExternalSyntheticLambda1(
                                    arrayList, i, z, 0));
                } catch (RemoteException e) {
                    android.util.Slog.e(
                            "ThermalManagerService$ThermalHalWrapper",
                            "Couldn't getCurrentTemperatures, reconnecting...",
                            e);
                    connectToHal();
                }
                return arrayList;
            }
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        public final List getTemperatureThresholds(int i, boolean z) {
            return new ArrayList();
        }
    }

    public final class ThermalHal11Wrapper extends ThermalHalWrapper {
        public final /* synthetic */ int $r8$classId;
        public final Object mThermalCallback11;
        public Object mThermalHal11;

        public ThermalHal11Wrapper(
                ThermalManagerService$$ExternalSyntheticLambda0
                        thermalManagerService$$ExternalSyntheticLambda0,
                int i) {
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.mThermalHal11 = null;
                    this.mThermalCallback11 =
                            new IThermalChangedCallback
                                    .Stub() { // from class:
                                              // com.android.server.power.ThermalManagerService$ThermalHal20Wrapper$1
                                public final void notifyThrottling(
                                        android.hardware.thermal.V2_0.Temperature temperature) {
                                    Temperature temperature2 =
                                            new Temperature(
                                                    temperature.value,
                                                    temperature.type,
                                                    temperature.name,
                                                    temperature.throttlingStatus);
                                    long clearCallingIdentity = Binder.clearCallingIdentity();
                                    try {
                                        ThermalManagerService.ThermalHal11Wrapper.this.mCallback
                                                .onValues(temperature2);
                                    } finally {
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                    }
                                }
                            };
                    this.mCallback = thermalManagerService$$ExternalSyntheticLambda0;
                    break;
                default:
                    this.mThermalHal11 = null;
                    this.mThermalCallback11 =
                            new IThermalCallback
                                    .Stub() { // from class:
                                              // com.android.server.power.ThermalManagerService.ThermalHal11Wrapper.1
                                public final void notifyThrottling(
                                        boolean z,
                                        android.hardware.thermal.V1_0.Temperature temperature) {
                                    Temperature temperature2 =
                                            new Temperature(
                                                    temperature.currentValue,
                                                    temperature.type,
                                                    temperature.name,
                                                    z ? 3 : 0);
                                    long clearCallingIdentity = Binder.clearCallingIdentity();
                                    try {
                                        ThermalHal11Wrapper.this.mCallback.onValues(temperature2);
                                    } finally {
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                    }
                                }
                            };
                    this.mCallback = thermalManagerService$$ExternalSyntheticLambda0;
                    break;
            }
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        public final boolean connectToHal() {
            boolean z;
            boolean z2;
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this.mHalLock) {
                        z = true;
                        try {
                            android.hardware.thermal.V1_1.IThermal service =
                                    android.hardware.thermal.V1_1.IThermal.getService(true);
                            this.mThermalHal11 = service;
                            service.linkToDeath(new ThermalHalWrapper.DeathRecipient(), 5612L);
                            ((android.hardware.thermal.V1_1.IThermal) this.mThermalHal11)
                                    .registerThermalCallback(
                                            (AnonymousClass1) this.mThermalCallback11);
                            android.util.Slog.i(
                                    "ThermalManagerService$ThermalHalWrapper",
                                    "Thermal HAL 1.1 service connected, limited thermal functions"
                                        + " due to legacy API.");
                        } catch (RemoteException | NoSuchElementException unused) {
                            android.util.Slog.e(
                                    "ThermalManagerService$ThermalHalWrapper",
                                    "Thermal HAL 1.1 service not connected.");
                            this.mThermalHal11 = null;
                        }
                        if (((android.hardware.thermal.V1_1.IThermal) this.mThermalHal11) == null) {
                            z = false;
                        }
                    }
                    return z;
                default:
                    synchronized (this.mHalLock) {
                        z2 = true;
                        try {
                            android.hardware.thermal.V2_0.IThermal service2 =
                                    android.hardware.thermal.V2_0.IThermal.getService(true);
                            this.mThermalHal11 = service2;
                            service2.linkToDeath(new ThermalHalWrapper.DeathRecipient(), 5612L);
                            ((android.hardware.thermal.V2_0.IThermal) this.mThermalHal11)
                                    .registerThermalChangedCallback(
                                            (ThermalManagerService$ThermalHal20Wrapper$1)
                                                    this.mThermalCallback11,
                                            false,
                                            0);
                            android.util.Slog.i(
                                    "ThermalManagerService$ThermalHalWrapper",
                                    "Thermal HAL 2.0 service connected.");
                        } catch (RemoteException | NoSuchElementException unused2) {
                            android.util.Slog.e(
                                    "ThermalManagerService$ThermalHalWrapper",
                                    "Thermal HAL 2.0 service not connected.");
                            this.mThermalHal11 = null;
                        }
                        if (((android.hardware.thermal.V2_0.IThermal) this.mThermalHal11) == null) {
                            z2 = false;
                        }
                    }
                    return z2;
            }
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        public final void dump(PrintWriter printWriter) {
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this.mHalLock) {
                        try {
                            printWriter.print("\t");
                            printWriter.println(
                                    "ThermalHAL 1.1 connected: "
                                            .concat(
                                                    ((android.hardware.thermal.V1_1.IThermal)
                                                                            this.mThermalHal11)
                                                                    != null
                                                            ? "yes"
                                                            : "no"));
                        } finally {
                        }
                    }
                    return;
                default:
                    synchronized (this.mHalLock) {
                        try {
                            printWriter.print("\t");
                            printWriter.println(
                                    "ThermalHAL 2.0 connected: "
                                            .concat(
                                                    ((android.hardware.thermal.V2_0.IThermal)
                                                                            this.mThermalHal11)
                                                                    != null
                                                            ? "yes"
                                                            : "no"));
                        } finally {
                        }
                    }
                    return;
            }
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        public final List getCurrentCoolingDevices(int i, boolean z) {
            ArrayList arrayList;
            final ArrayList arrayList2;
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this.mHalLock) {
                        arrayList = new ArrayList();
                        android.hardware.thermal.V1_1.IThermal iThermal =
                                (android.hardware.thermal.V1_1.IThermal) this.mThermalHal11;
                        if (iThermal != null) {
                            try {
                                iThermal.getCoolingDevices(
                                        new ThermalManagerService$ThermalHal10Wrapper$$ExternalSyntheticLambda0(
                                                arrayList, i, z, 1));
                            } catch (RemoteException e) {
                                android.util.Slog.e(
                                        "ThermalManagerService$ThermalHalWrapper",
                                        "Couldn't getCurrentCoolingDevices, reconnecting...",
                                        e);
                                connectToHal();
                            }
                        }
                    }
                    return arrayList;
                default:
                    synchronized (this.mHalLock) {
                        arrayList2 = new ArrayList();
                        android.hardware.thermal.V2_0.IThermal iThermal2 =
                                (android.hardware.thermal.V2_0.IThermal) this.mThermalHal11;
                        if (iThermal2 != null) {
                            try {
                                iThermal2.getCurrentCoolingDevices(
                                        z,
                                        i,
                                        new IThermal
                                                .getCurrentCoolingDevicesCallback() { // from class:
                                            // com.android.server.power.ThermalManagerService$ThermalHal20Wrapper$$ExternalSyntheticLambda0
                                            public final void onValues(
                                                    ThermalStatus thermalStatus,
                                                    ArrayList arrayList3) {
                                                List list = arrayList2;
                                                if (thermalStatus.code != 0) {
                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                            .m$1(
                                                                    new StringBuilder(
                                                                            "Couldn't get cooling"
                                                                                + " device because"
                                                                                + " of HAL error:"
                                                                                + " "),
                                                                    thermalStatus.debugMessage,
                                                                    "ThermalManagerService$ThermalHalWrapper");
                                                    return;
                                                }
                                                Iterator it = arrayList3.iterator();
                                                while (it.hasNext()) {
                                                    CoolingDevice coolingDevice =
                                                            (CoolingDevice) it.next();
                                                    list.add(
                                                            new android.os.CoolingDevice(
                                                                    coolingDevice.value,
                                                                    coolingDevice.type,
                                                                    coolingDevice.name));
                                                }
                                            }
                                        });
                            } catch (RemoteException e2) {
                                android.util.Slog.e(
                                        "ThermalManagerService$ThermalHalWrapper",
                                        "Couldn't getCurrentCoolingDevices, reconnecting...",
                                        e2);
                                connectToHal();
                            }
                        }
                    }
                    return arrayList2;
            }
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        public final List getCurrentTemperatures(int i, boolean z) {
            ArrayList arrayList;
            final ArrayList arrayList2;
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this.mHalLock) {
                        arrayList = new ArrayList();
                        android.hardware.thermal.V1_1.IThermal iThermal =
                                (android.hardware.thermal.V1_1.IThermal) this.mThermalHal11;
                        if (iThermal != null) {
                            try {
                                iThermal.getTemperatures(
                                        new ThermalManagerService$ThermalHal10Wrapper$$ExternalSyntheticLambda1(
                                                arrayList, i, z, 1));
                            } catch (RemoteException e) {
                                android.util.Slog.e(
                                        "ThermalManagerService$ThermalHalWrapper",
                                        "Couldn't getCurrentTemperatures, reconnecting...",
                                        e);
                                connectToHal();
                            }
                        }
                    }
                    return arrayList;
                default:
                    synchronized (this.mHalLock) {
                        arrayList2 = new ArrayList();
                        android.hardware.thermal.V2_0.IThermal iThermal2 =
                                (android.hardware.thermal.V2_0.IThermal) this.mThermalHal11;
                        if (iThermal2 != null) {
                            try {
                                iThermal2.getCurrentTemperatures(
                                        z,
                                        i,
                                        new IThermal
                                                .getCurrentTemperaturesCallback() { // from class:
                                            // com.android.server.power.ThermalManagerService$ThermalHal20Wrapper$$ExternalSyntheticLambda2
                                            public final void onValues(
                                                    ThermalStatus thermalStatus,
                                                    ArrayList arrayList3) {
                                                List list = arrayList2;
                                                if (thermalStatus.code != 0) {
                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                            .m$1(
                                                                    new StringBuilder(
                                                                            "Couldn't get"
                                                                                + " temperatures"
                                                                                + " because of HAL"
                                                                                + " error: "),
                                                                    thermalStatus.debugMessage,
                                                                    "ThermalManagerService$ThermalHalWrapper");
                                                    return;
                                                }
                                                Iterator it = arrayList3.iterator();
                                                while (it.hasNext()) {
                                                    android.hardware.thermal.V2_0.Temperature
                                                            temperature =
                                                                    (android.hardware.thermal.V2_0
                                                                                    .Temperature)
                                                                            it.next();
                                                    if (!Temperature.isValidStatus(
                                                            temperature.throttlingStatus)) {
                                                        android.util.Slog.e(
                                                                "ThermalManagerService$ThermalHalWrapper",
                                                                "Invalid status data from HAL");
                                                        temperature.throttlingStatus = 0;
                                                    }
                                                    list.add(
                                                            new Temperature(
                                                                    temperature.value,
                                                                    temperature.type,
                                                                    temperature.name,
                                                                    temperature.throttlingStatus));
                                                }
                                            }
                                        });
                            } catch (RemoteException e2) {
                                android.util.Slog.e(
                                        "ThermalManagerService$ThermalHalWrapper",
                                        "Couldn't getCurrentTemperatures, reconnecting...",
                                        e2);
                                connectToHal();
                            }
                        }
                    }
                    return arrayList2;
            }
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        public final List getTemperatureThresholds(int i, boolean z) {
            final ArrayList arrayList;
            switch (this.$r8$classId) {
                case 0:
                    return new ArrayList();
                default:
                    synchronized (this.mHalLock) {
                        arrayList = new ArrayList();
                        android.hardware.thermal.V2_0.IThermal iThermal =
                                (android.hardware.thermal.V2_0.IThermal) this.mThermalHal11;
                        if (iThermal != null) {
                            try {
                                iThermal.getTemperatureThresholds(
                                        z,
                                        i,
                                        new IThermal
                                                .getTemperatureThresholdsCallback() { // from class:
                                            // com.android.server.power.ThermalManagerService$ThermalHal20Wrapper$$ExternalSyntheticLambda1
                                            public final void onValues(
                                                    ThermalStatus thermalStatus,
                                                    ArrayList arrayList2) {
                                                final ThermalManagerService.ThermalHal11Wrapper
                                                        thermalHal11Wrapper =
                                                                ThermalManagerService
                                                                        .ThermalHal11Wrapper.this;
                                                List list = arrayList;
                                                thermalHal11Wrapper.getClass();
                                                if (thermalStatus.code == 0) {
                                                    list.addAll(
                                                            (Collection)
                                                                    arrayList2.stream()
                                                                            .map(
                                                                                    new Function() { // from class: com.android.server.power.ThermalManagerService$ThermalHal20Wrapper$$ExternalSyntheticLambda3
                                                                                        @Override // java.util.function.Function
                                                                                        public final
                                                                                        Object
                                                                                                apply(
                                                                                                        Object
                                                                                                                obj) {
                                                                                            android
                                                                                                            .hardware
                                                                                                            .thermal
                                                                                                            .V2_0
                                                                                                            .TemperatureThreshold
                                                                                                    temperatureThreshold =
                                                                                                            (android
                                                                                                                            .hardware
                                                                                                                            .thermal
                                                                                                                            .V2_0
                                                                                                                            .TemperatureThreshold)
                                                                                                                    obj;
                                                                                            ThermalManagerService
                                                                                                    .ThermalHal11Wrapper
                                                                                                    .this
                                                                                                    .getClass();
                                                                                            TemperatureThreshold
                                                                                                    temperatureThreshold2 =
                                                                                                            new TemperatureThreshold();
                                                                                            temperatureThreshold2
                                                                                                            .name =
                                                                                                    temperatureThreshold
                                                                                                            .name;
                                                                                            temperatureThreshold2
                                                                                                            .type =
                                                                                                    temperatureThreshold
                                                                                                            .type;
                                                                                            temperatureThreshold2
                                                                                                            .coldThrottlingThresholds =
                                                                                                    temperatureThreshold
                                                                                                            .coldThrottlingThresholds;
                                                                                            temperatureThreshold2
                                                                                                            .hotThrottlingThresholds =
                                                                                                    temperatureThreshold
                                                                                                            .hotThrottlingThresholds;
                                                                                            return temperatureThreshold2;
                                                                                        }
                                                                                    })
                                                                            .collect(
                                                                                    Collectors
                                                                                            .toList()));
                                                } else {
                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                            .m$1(
                                                                    new StringBuilder(
                                                                            "Couldn't get"
                                                                                + " temperature"
                                                                                + " thresholds"
                                                                                + " because of HAL"
                                                                                + " error: "),
                                                                    thermalStatus.debugMessage,
                                                                    "ThermalManagerService$ThermalHalWrapper");
                                                }
                                            }
                                        });
                            } catch (RemoteException e) {
                                android.util.Slog.e(
                                        "ThermalManagerService$ThermalHalWrapper",
                                        "Couldn't getTemperatureThresholds, reconnecting...",
                                        e);
                            }
                        }
                    }
                    return arrayList;
            }
        }
    }

    class ThermalHalAidlWrapper extends ThermalHalWrapper implements IBinder.DeathRecipient {
        public android.hardware.thermal.IThermal mInstance = null;
        public final AnonymousClass1 mThermalChangedCallback =
                new IThermalChangedCallback
                        .Stub() { // from class:
                                  // com.android.server.power.ThermalManagerService.ThermalHalAidlWrapper.1
                    public final String getInterfaceHash() {
                        return "2f49c78011338b42b43d5d0e250d9b520850cc1f";
                    }

                    public final int getInterfaceVersion() {
                        return 2;
                    }

                    public final void notifyThrottling(
                            android.hardware.thermal.Temperature temperature) {
                        Temperature temperature2 =
                                new Temperature(
                                        temperature.value,
                                        temperature.type,
                                        temperature.name,
                                        temperature.throttlingStatus);
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            ThermalHalAidlWrapper.this.mCallback.onValues(temperature2);
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                };

        /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.power.ThermalManagerService$ThermalHalAidlWrapper$1] */
        public ThermalHalAidlWrapper(
                ThermalManagerService$$ExternalSyntheticLambda0
                        thermalManagerService$$ExternalSyntheticLambda0) {
            this.mCallback = thermalManagerService$$ExternalSyntheticLambda0;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final synchronized void binderDied() {
            android.util.Slog.w(
                    "ThermalManagerService$ThermalHalWrapper",
                    "Thermal AIDL HAL died, reconnecting...");
            connectToHal();
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        public final boolean connectToHal() {
            synchronized (this.mHalLock) {
                initProxyAndRegisterCallback(
                        Binder.allowBlocking(
                                ServiceManager.waitForDeclaredService(
                                        android.hardware.thermal.IThermal.DESCRIPTOR
                                                + "/default")));
            }
            return this.mInstance != null;
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        public final void dump(PrintWriter printWriter) {
            synchronized (this.mHalLock) {
                try {
                    printWriter.print("\t");
                    printWriter.println(
                            "ThermalHAL AIDL 2  connected: "
                                    .concat(this.mInstance != null ? "yes" : "no"));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        public final List getCurrentCoolingDevices(int i, boolean z) {
            android.hardware.thermal.CoolingDevice[] coolingDevicesWithType;
            synchronized (this.mHalLock) {
                ArrayList arrayList = new ArrayList();
                android.hardware.thermal.IThermal iThermal = this.mInstance;
                if (iThermal == null) {
                    return arrayList;
                }
                try {
                    try {
                        coolingDevicesWithType =
                                z
                                        ? iThermal.getCoolingDevicesWithType(i)
                                        : iThermal.getCoolingDevices();
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        android.util.Slog.e(
                                "ThermalManagerService$ThermalHalWrapper",
                                "Couldn't getCurrentCoolingDevices due to invalid status",
                                e);
                    }
                } catch (RemoteException e2) {
                    android.util.Slog.e(
                            "ThermalManagerService$ThermalHalWrapper",
                            "Couldn't getCurrentCoolingDevices, reconnecting",
                            e2);
                    connectToHal();
                }
                if (coolingDevicesWithType == null) {
                    return arrayList;
                }
                for (android.hardware.thermal.CoolingDevice coolingDevice :
                        coolingDevicesWithType) {
                    if (!android.os.CoolingDevice.isValidType(coolingDevice.type)) {
                        android.util.Slog.e(
                                "ThermalManagerService$ThermalHalWrapper",
                                "Invalid cooling device type "
                                        + coolingDevice.type
                                        + " from AIDL HAL");
                    } else if (!z || coolingDevice.type == i) {
                        arrayList.add(
                                new android.os.CoolingDevice(
                                        coolingDevice.value,
                                        coolingDevice.type,
                                        coolingDevice.name));
                    }
                }
                return arrayList;
            }
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        public final List getCurrentTemperatures(int i, boolean z) {
            android.hardware.thermal.Temperature[] temperaturesWithType;
            synchronized (this.mHalLock) {
                ArrayList arrayList = new ArrayList();
                android.hardware.thermal.IThermal iThermal = this.mInstance;
                if (iThermal == null) {
                    return arrayList;
                }
                try {
                    try {
                        temperaturesWithType =
                                z
                                        ? iThermal.getTemperaturesWithType(i)
                                        : iThermal.getTemperatures();
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        android.util.Slog.e(
                                "ThermalManagerService$ThermalHalWrapper",
                                "Couldn't getCurrentCoolingDevices due to invalid status",
                                e);
                    }
                } catch (RemoteException e2) {
                    android.util.Slog.e(
                            "ThermalManagerService$ThermalHalWrapper",
                            "Couldn't getCurrentTemperatures, reconnecting",
                            e2);
                    connectToHal();
                }
                if (temperaturesWithType == null) {
                    return arrayList;
                }
                for (android.hardware.thermal.Temperature temperature : temperaturesWithType) {
                    if (!Temperature.isValidStatus(temperature.throttlingStatus)) {
                        android.util.Slog.e(
                                "ThermalManagerService$ThermalHalWrapper",
                                "Invalid temperature status "
                                        + temperature.throttlingStatus
                                        + " received from AIDL HAL");
                        temperature.throttlingStatus = 0;
                    }
                    if (!z || temperature.type == i) {
                        arrayList.add(
                                new Temperature(
                                        temperature.value,
                                        temperature.type,
                                        temperature.name,
                                        temperature.throttlingStatus));
                    }
                }
                return arrayList;
            }
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        public final List getTemperatureThresholds(final int i, boolean z) {
            synchronized (this.mHalLock) {
                ArrayList arrayList = new ArrayList();
                android.hardware.thermal.IThermal iThermal = this.mInstance;
                if (iThermal == null) {
                    return arrayList;
                }
                try {
                    try {
                        TemperatureThreshold[] temperatureThresholdsWithType =
                                z
                                        ? iThermal.getTemperatureThresholdsWithType(i)
                                        : iThermal.getTemperatureThresholds();
                        if (temperatureThresholdsWithType == null) {
                            return arrayList;
                        }
                        if (z) {
                            return (List)
                                    Arrays.stream(temperatureThresholdsWithType)
                                            .filter(
                                                    new Predicate() { // from class:
                                                                      // com.android.server.power.ThermalManagerService$ThermalHalAidlWrapper$$ExternalSyntheticLambda0
                                                        @Override // java.util.function.Predicate
                                                        public final boolean test(Object obj) {
                                                            return ((TemperatureThreshold) obj).type
                                                                    == i;
                                                        }
                                                    })
                                            .collect(Collectors.toList());
                        }
                        return Arrays.asList(temperatureThresholdsWithType);
                    } catch (RemoteException e) {
                        android.util.Slog.e(
                                "ThermalManagerService$ThermalHalWrapper",
                                "Couldn't getTemperatureThresholds, reconnecting...",
                                e);
                        connectToHal();
                        return arrayList;
                    }
                } catch (IllegalArgumentException | IllegalStateException e2) {
                    android.util.Slog.e(
                            "ThermalManagerService$ThermalHalWrapper",
                            "Couldn't getTemperatureThresholds due to invalid status",
                            e2);
                    return arrayList;
                }
            }
        }

        public void initProxyAndRegisterCallback(IBinder iBinder) {
            synchronized (this.mHalLock) {
                if (iBinder != null) {
                    this.mInstance = IThermal.Stub.asInterface(iBinder);
                    try {
                        iBinder.linkToDeath(this, 0);
                    } catch (RemoteException e) {
                        android.util.Slog.e(
                                "ThermalManagerService$ThermalHalWrapper",
                                "Unable to connect IThermal AIDL instance",
                                e);
                        connectToHal();
                    }
                    if (this.mInstance != null) {
                        try {
                            android.util.Slog.i(
                                    "ThermalManagerService$ThermalHalWrapper",
                                    "Thermal HAL AIDL service connected with version "
                                            + this.mInstance.getInterfaceVersion());
                            registerThermalChangedCallback();
                        } catch (RemoteException e2) {
                            android.util.Slog.e(
                                    "ThermalManagerService$ThermalHalWrapper",
                                    "Unable to read interface version from Thermal HAL",
                                    e2);
                            connectToHal();
                        }
                    }
                }
            }
        }

        public void registerThermalChangedCallback() {
            try {
                this.mInstance.registerThermalChangedCallback(this.mThermalChangedCallback);
            } catch (RemoteException e) {
                android.util.Slog.e(
                        "ThermalManagerService$ThermalHalWrapper",
                        "Unable to connect IThermal AIDL instance",
                        e);
                connectToHal();
            } catch (IllegalArgumentException | IllegalStateException e2) {
                android.util.Slog.e(
                        "ThermalManagerService$ThermalHalWrapper",
                        "Couldn't registerThermalChangedCallback due to invalid status",
                        e2);
            }
        }
    }

    public abstract class ThermalHalWrapper {
        public TemperatureChangedCallback mCallback;
        public final Object mHalLock = new Object();

        public final class DeathRecipient implements IHwBinder.DeathRecipient {
            public DeathRecipient() {}

            public final void serviceDied(long j) {
                if (j == 5612) {
                    android.util.Slog.e(
                            "ThermalManagerService$ThermalHalWrapper",
                            "Thermal HAL service died cookie: " + j);
                    synchronized (ThermalHalWrapper.this.mHalLock) {
                        ThermalHalWrapper.this.connectToHal();
                        ThermalHalWrapper thermalHalWrapper = ThermalHalWrapper.this;
                        synchronized (thermalHalWrapper.mHalLock) {
                            try {
                                ArrayList arrayList =
                                        (ArrayList)
                                                thermalHalWrapper.getCurrentTemperatures(0, false);
                                int size = arrayList.size();
                                for (int i = 0; i < size; i++) {
                                    thermalHalWrapper.mCallback.onValues(
                                            (Temperature) arrayList.get(i));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }
            }
        }

        public interface TemperatureChangedCallback {
            void onValues(Temperature temperature);
        }

        public abstract boolean connectToHal();

        public abstract void dump(PrintWriter printWriter);

        public abstract List getCurrentCoolingDevices(int i, boolean z);

        public abstract List getCurrentTemperatures(int i, boolean z);

        public abstract List getTemperatureThresholds(int i, boolean z);

        public void setCallback(TemperatureChangedCallback temperatureChangedCallback) {
            this.mCallback = temperatureChangedCallback;
        }
    }

    public final class ThermalShellCommand extends ShellCommand {
        public ThermalShellCommand() {}

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
        java.lang.NullPointerException
         */
        /* JADX WARN: Removed duplicated region for block: B:100:0x02bc A[Catch: all -> 0x0112, RuntimeException -> 0x02c1, TRY_LEAVE, TryCatch #4 {RuntimeException -> 0x02c1, blocks: (B:98:0x02b6, B:100:0x02bc), top: B:97:0x02b6, outer: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:105:0x02c3  */
        /* JADX WARN: Removed duplicated region for block: B:108:0x024e A[Catch: all -> 0x0112, TryCatch #0 {all -> 0x0112, blocks: (B:56:0x00d1, B:57:0x0101, B:60:0x01f8, B:61:0x01fb, B:84:0x023e, B:85:0x024a, B:88:0x0298, B:89:0x029b, B:96:0x02b2, B:98:0x02b6, B:100:0x02bc, B:101:0x02c5, B:107:0x02d3, B:108:0x024e, B:111:0x0259, B:114:0x0263, B:117:0x026e, B:120:0x0278, B:123:0x0282, B:126:0x028d, B:129:0x0106, B:132:0x0115, B:135:0x0121, B:138:0x012d, B:141:0x0139, B:144:0x0145, B:147:0x0150, B:150:0x015b, B:153:0x0167, B:156:0x0173, B:159:0x017f, B:162:0x018b, B:165:0x0197, B:168:0x01a2, B:171:0x01ad, B:174:0x01b8, B:177:0x01c2, B:180:0x01cc, B:183:0x01d7, B:186:0x01e2, B:189:0x01ed), top: B:55:0x00d1, inners: #4 }] */
        /* JADX WARN: Removed duplicated region for block: B:111:0x0259 A[Catch: all -> 0x0112, TryCatch #0 {all -> 0x0112, blocks: (B:56:0x00d1, B:57:0x0101, B:60:0x01f8, B:61:0x01fb, B:84:0x023e, B:85:0x024a, B:88:0x0298, B:89:0x029b, B:96:0x02b2, B:98:0x02b6, B:100:0x02bc, B:101:0x02c5, B:107:0x02d3, B:108:0x024e, B:111:0x0259, B:114:0x0263, B:117:0x026e, B:120:0x0278, B:123:0x0282, B:126:0x028d, B:129:0x0106, B:132:0x0115, B:135:0x0121, B:138:0x012d, B:141:0x0139, B:144:0x0145, B:147:0x0150, B:150:0x015b, B:153:0x0167, B:156:0x0173, B:159:0x017f, B:162:0x018b, B:165:0x0197, B:168:0x01a2, B:171:0x01ad, B:174:0x01b8, B:177:0x01c2, B:180:0x01cc, B:183:0x01d7, B:186:0x01e2, B:189:0x01ed), top: B:55:0x00d1, inners: #4 }] */
        /* JADX WARN: Removed duplicated region for block: B:114:0x0263 A[Catch: all -> 0x0112, TryCatch #0 {all -> 0x0112, blocks: (B:56:0x00d1, B:57:0x0101, B:60:0x01f8, B:61:0x01fb, B:84:0x023e, B:85:0x024a, B:88:0x0298, B:89:0x029b, B:96:0x02b2, B:98:0x02b6, B:100:0x02bc, B:101:0x02c5, B:107:0x02d3, B:108:0x024e, B:111:0x0259, B:114:0x0263, B:117:0x026e, B:120:0x0278, B:123:0x0282, B:126:0x028d, B:129:0x0106, B:132:0x0115, B:135:0x0121, B:138:0x012d, B:141:0x0139, B:144:0x0145, B:147:0x0150, B:150:0x015b, B:153:0x0167, B:156:0x0173, B:159:0x017f, B:162:0x018b, B:165:0x0197, B:168:0x01a2, B:171:0x01ad, B:174:0x01b8, B:177:0x01c2, B:180:0x01cc, B:183:0x01d7, B:186:0x01e2, B:189:0x01ed), top: B:55:0x00d1, inners: #4 }] */
        /* JADX WARN: Removed duplicated region for block: B:117:0x026e A[Catch: all -> 0x0112, TryCatch #0 {all -> 0x0112, blocks: (B:56:0x00d1, B:57:0x0101, B:60:0x01f8, B:61:0x01fb, B:84:0x023e, B:85:0x024a, B:88:0x0298, B:89:0x029b, B:96:0x02b2, B:98:0x02b6, B:100:0x02bc, B:101:0x02c5, B:107:0x02d3, B:108:0x024e, B:111:0x0259, B:114:0x0263, B:117:0x026e, B:120:0x0278, B:123:0x0282, B:126:0x028d, B:129:0x0106, B:132:0x0115, B:135:0x0121, B:138:0x012d, B:141:0x0139, B:144:0x0145, B:147:0x0150, B:150:0x015b, B:153:0x0167, B:156:0x0173, B:159:0x017f, B:162:0x018b, B:165:0x0197, B:168:0x01a2, B:171:0x01ad, B:174:0x01b8, B:177:0x01c2, B:180:0x01cc, B:183:0x01d7, B:186:0x01e2, B:189:0x01ed), top: B:55:0x00d1, inners: #4 }] */
        /* JADX WARN: Removed duplicated region for block: B:120:0x0278 A[Catch: all -> 0x0112, TryCatch #0 {all -> 0x0112, blocks: (B:56:0x00d1, B:57:0x0101, B:60:0x01f8, B:61:0x01fb, B:84:0x023e, B:85:0x024a, B:88:0x0298, B:89:0x029b, B:96:0x02b2, B:98:0x02b6, B:100:0x02bc, B:101:0x02c5, B:107:0x02d3, B:108:0x024e, B:111:0x0259, B:114:0x0263, B:117:0x026e, B:120:0x0278, B:123:0x0282, B:126:0x028d, B:129:0x0106, B:132:0x0115, B:135:0x0121, B:138:0x012d, B:141:0x0139, B:144:0x0145, B:147:0x0150, B:150:0x015b, B:153:0x0167, B:156:0x0173, B:159:0x017f, B:162:0x018b, B:165:0x0197, B:168:0x01a2, B:171:0x01ad, B:174:0x01b8, B:177:0x01c2, B:180:0x01cc, B:183:0x01d7, B:186:0x01e2, B:189:0x01ed), top: B:55:0x00d1, inners: #4 }] */
        /* JADX WARN: Removed duplicated region for block: B:123:0x0282 A[Catch: all -> 0x0112, TryCatch #0 {all -> 0x0112, blocks: (B:56:0x00d1, B:57:0x0101, B:60:0x01f8, B:61:0x01fb, B:84:0x023e, B:85:0x024a, B:88:0x0298, B:89:0x029b, B:96:0x02b2, B:98:0x02b6, B:100:0x02bc, B:101:0x02c5, B:107:0x02d3, B:108:0x024e, B:111:0x0259, B:114:0x0263, B:117:0x026e, B:120:0x0278, B:123:0x0282, B:126:0x028d, B:129:0x0106, B:132:0x0115, B:135:0x0121, B:138:0x012d, B:141:0x0139, B:144:0x0145, B:147:0x0150, B:150:0x015b, B:153:0x0167, B:156:0x0173, B:159:0x017f, B:162:0x018b, B:165:0x0197, B:168:0x01a2, B:171:0x01ad, B:174:0x01b8, B:177:0x01c2, B:180:0x01cc, B:183:0x01d7, B:186:0x01e2, B:189:0x01ed), top: B:55:0x00d1, inners: #4 }] */
        /* JADX WARN: Removed duplicated region for block: B:126:0x028d A[Catch: all -> 0x0112, TryCatch #0 {all -> 0x0112, blocks: (B:56:0x00d1, B:57:0x0101, B:60:0x01f8, B:61:0x01fb, B:84:0x023e, B:85:0x024a, B:88:0x0298, B:89:0x029b, B:96:0x02b2, B:98:0x02b6, B:100:0x02bc, B:101:0x02c5, B:107:0x02d3, B:108:0x024e, B:111:0x0259, B:114:0x0263, B:117:0x026e, B:120:0x0278, B:123:0x0282, B:126:0x028d, B:129:0x0106, B:132:0x0115, B:135:0x0121, B:138:0x012d, B:141:0x0139, B:144:0x0145, B:147:0x0150, B:150:0x015b, B:153:0x0167, B:156:0x0173, B:159:0x017f, B:162:0x018b, B:165:0x0197, B:168:0x01a2, B:171:0x01ad, B:174:0x01b8, B:177:0x01c2, B:180:0x01cc, B:183:0x01d7, B:186:0x01e2, B:189:0x01ed), top: B:55:0x00d1, inners: #4 }] */
        /* JADX WARN: Removed duplicated region for block: B:86:0x024d  */
        /* JADX WARN: Removed duplicated region for block: B:89:0x029b A[Catch: all -> 0x0112, TryCatch #0 {all -> 0x0112, blocks: (B:56:0x00d1, B:57:0x0101, B:60:0x01f8, B:61:0x01fb, B:84:0x023e, B:85:0x024a, B:88:0x0298, B:89:0x029b, B:96:0x02b2, B:98:0x02b6, B:100:0x02bc, B:101:0x02c5, B:107:0x02d3, B:108:0x024e, B:111:0x0259, B:114:0x0263, B:117:0x026e, B:120:0x0278, B:123:0x0282, B:126:0x028d, B:129:0x0106, B:132:0x0115, B:135:0x0121, B:138:0x012d, B:141:0x0139, B:144:0x0145, B:147:0x0150, B:150:0x015b, B:153:0x0167, B:156:0x0173, B:159:0x017f, B:162:0x018b, B:165:0x0197, B:168:0x01a2, B:171:0x01ad, B:174:0x01b8, B:177:0x01c2, B:180:0x01cc, B:183:0x01d7, B:186:0x01e2, B:189:0x01ed), top: B:55:0x00d1, inners: #4 }] */
        /* JADX WARN: Removed duplicated region for block: B:90:0x02a4  */
        /* JADX WARN: Removed duplicated region for block: B:91:0x02a7  */
        /* JADX WARN: Removed duplicated region for block: B:92:0x02aa  */
        /* JADX WARN: Removed duplicated region for block: B:93:0x02ad  */
        /* JADX WARN: Removed duplicated region for block: B:94:0x02af  */
        /* JADX WARN: Removed duplicated region for block: B:95:0x02b1  */
        /* JADX WARN: Removed duplicated region for block: B:96:0x02b2 A[Catch: all -> 0x0112, PHI: r1
        0x02b2: PHI (r1v5 int) = (r1v0 int), (r1v7 int), (r1v8 int), (r1v9 int), (r1v10 int), (r1v11 int), (r1v12 int) binds: [B:88:0x0298, B:95:0x02b1, B:94:0x02af, B:93:0x02ad, B:92:0x02aa, B:91:0x02a7, B:90:0x02a4] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #0 {all -> 0x0112, blocks: (B:56:0x00d1, B:57:0x0101, B:60:0x01f8, B:61:0x01fb, B:84:0x023e, B:85:0x024a, B:88:0x0298, B:89:0x029b, B:96:0x02b2, B:98:0x02b6, B:100:0x02bc, B:101:0x02c5, B:107:0x02d3, B:108:0x024e, B:111:0x0259, B:114:0x0263, B:117:0x026e, B:120:0x0278, B:123:0x0282, B:126:0x028d, B:129:0x0106, B:132:0x0115, B:135:0x0121, B:138:0x012d, B:141:0x0139, B:144:0x0145, B:147:0x0150, B:150:0x015b, B:153:0x0167, B:156:0x0173, B:159:0x017f, B:162:0x018b, B:165:0x0197, B:168:0x01a2, B:171:0x01ad, B:174:0x01b8, B:177:0x01c2, B:180:0x01cc, B:183:0x01d7, B:186:0x01e2, B:189:0x01ed), top: B:55:0x00d1, inners: #4 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int onCommand(java.lang.String r32) {
            /*
                Method dump skipped, instructions count: 1072
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.power.ThermalManagerService.ThermalShellCommand.onCommand(java.lang.String):int");
        }

        public final void onHelp() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("Thermal service (thermalservice) commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Print this help text.");
            outPrintWriter.println("");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "  inject-temperature TYPE STATUS NAME [VALUE]",
                    "    injects a new temperature sample for the specified device.",
                    "    type and status strings follow the names in android.os.Temperature.",
                    "  override-status STATUS");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "    sets and locks the thermal status of the device to STATUS.",
                    "    status code is defined in android.os.Temperature.",
                    "  reset",
                    "    unlocks the thermal status of the device.");
            outPrintWriter.println("  headroom FORECAST_SECONDS");
            outPrintWriter.println(
                    "    gets the thermal headroom forecast in specified seconds, from [0,60].");
            outPrintWriter.println();
        }
    }

    /* renamed from: -$$Nest$mpostEventListenerCurrentTemperatures, reason: not valid java name */
    public static void m826$$Nest$mpostEventListenerCurrentTemperatures(
            ThermalManagerService thermalManagerService,
            IThermalEventListener iThermalEventListener,
            Integer num) {
        synchronized (thermalManagerService.mLock) {
            try {
                int size = thermalManagerService.mTemperatureMap.size();
                for (int i = 0; i < size; i++) {
                    postEventListener(
                            (Temperature) thermalManagerService.mTemperatureMap.valueAt(i),
                            iThermalEventListener,
                            num);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public ThermalManagerService(Context context) {
        this(context, null);
    }

    public ThermalManagerService(Context context, ThermalHalWrapper thermalHalWrapper) {
        super(context);
        this.mLock = new Object();
        this.mThermalEventListeners = new RemoteCallbackList();
        this.mThermalStatusListeners = new RemoteCallbackList();
        this.mTemperatureMap = new ArrayMap();
        this.mHalReady = new AtomicBoolean();
        this.mTemperatureWatcher = new TemperatureWatcher();
        this.mService =
                new IThermalService
                        .Stub() { // from class: com.android.server.power.ThermalManagerService.1
                    public final void dump(
                            FileDescriptor fileDescriptor,
                            PrintWriter printWriter,
                            String[] strArr) {
                        ThermalManagerService.this.dumpInternal(
                                fileDescriptor, printWriter, strArr);
                    }

                    public final android.os.CoolingDevice[] getCurrentCoolingDevices() {
                        ThermalManagerService.this
                                .getContext()
                                .enforceCallingOrSelfPermission(
                                        "android.permission.DEVICE_POWER", null);
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            if (!ThermalManagerService.this.mHalReady.get()) {
                                return new android.os.CoolingDevice[0];
                            }
                            ArrayList arrayList =
                                    (ArrayList)
                                            ThermalManagerService.this.mHalWrapper
                                                    .getCurrentCoolingDevices(0, false);
                            return (android.os.CoolingDevice[])
                                    arrayList.toArray(
                                            new android.os.CoolingDevice[arrayList.size()]);
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }

                    public final android.os.CoolingDevice[] getCurrentCoolingDevicesWithType(
                            int i) {
                        ThermalManagerService.this
                                .getContext()
                                .enforceCallingOrSelfPermission(
                                        "android.permission.DEVICE_POWER", null);
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            if (!ThermalManagerService.this.mHalReady.get()) {
                                return new android.os.CoolingDevice[0];
                            }
                            ArrayList arrayList =
                                    (ArrayList)
                                            ThermalManagerService.this.mHalWrapper
                                                    .getCurrentCoolingDevices(i, true);
                            return (android.os.CoolingDevice[])
                                    arrayList.toArray(
                                            new android.os.CoolingDevice[arrayList.size()]);
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }

                    public final Temperature[] getCurrentTemperatures() {
                        ThermalManagerService.this
                                .getContext()
                                .enforceCallingOrSelfPermission(
                                        "android.permission.DEVICE_POWER", null);
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            if (!ThermalManagerService.this.mHalReady.get()) {
                                return new Temperature[0];
                            }
                            ArrayList arrayList =
                                    (ArrayList)
                                            ThermalManagerService.this.mHalWrapper
                                                    .getCurrentTemperatures(0, false);
                            return (Temperature[])
                                    arrayList.toArray(new Temperature[arrayList.size()]);
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }

                    public final Temperature[] getCurrentTemperaturesWithType(int i) {
                        ThermalManagerService.this
                                .getContext()
                                .enforceCallingOrSelfPermission(
                                        "android.permission.DEVICE_POWER", null);
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            if (!ThermalManagerService.this.mHalReady.get()) {
                                return new Temperature[0];
                            }
                            ArrayList arrayList =
                                    (ArrayList)
                                            ThermalManagerService.this.mHalWrapper
                                                    .getCurrentTemperatures(i, true);
                            return (Temperature[])
                                    arrayList.toArray(new Temperature[arrayList.size()]);
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }

                    public final int getCurrentThermalStatus() {
                        int i;
                        synchronized (ThermalManagerService.this.mLock) {
                            try {
                                long clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    int callingUid = Binder.getCallingUid();
                                    int i2 = 2;
                                    int i3 = ThermalManagerService.this.mHalReady.get() ? 1 : 2;
                                    switch (ThermalManagerService.this.mStatus) {
                                        case 1:
                                            i2 = 1;
                                            break;
                                        case 2:
                                            break;
                                        case 3:
                                            i2 = 3;
                                            break;
                                        case 4:
                                            i2 = 4;
                                            break;
                                        case 5:
                                            i2 = 5;
                                            break;
                                        case 6:
                                            i2 = 6;
                                            break;
                                        default:
                                            i2 = 0;
                                            break;
                                    }
                                    FrameworkStatsLog.write(
                                            FrameworkStatsLog.THERMAL_STATUS_CALLED,
                                            callingUid,
                                            i3,
                                            i2);
                                    i = ThermalManagerService.this.mStatus;
                                } finally {
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        return i;
                    }

                    public final float getThermalHeadroom(int i) {
                        if (!ThermalManagerService.this.mHalReady.get()) {
                            FrameworkStatsLog.write(
                                    FrameworkStatsLog.THERMAL_HEADROOM_CALLED,
                                    IThermalService.Stub.getCallingUid(),
                                    2,
                                    Float.NaN,
                                    i);
                            return Float.NaN;
                        }
                        if (i >= 0 && i <= 60) {
                            return ThermalManagerService.this.mTemperatureWatcher.getForecast(i);
                        }
                        FrameworkStatsLog.write(
                                FrameworkStatsLog.THERMAL_HEADROOM_CALLED,
                                IThermalService.Stub.getCallingUid(),
                                4,
                                Float.NaN,
                                i);
                        return Float.NaN;
                    }

                    public final float[] getThermalHeadroomThresholds() {
                        float[] copyOf;
                        if (!ThermalManagerService.this.mHalReady.get()) {
                            FrameworkStatsLog.write(
                                    FrameworkStatsLog.THERMAL_HEADROOM_THRESHOLDS_CALLED,
                                    Binder.getCallingUid(),
                                    2);
                            throw new IllegalStateException(
                                    "Thermal HAL connection is not initialized");
                        }
                        if (!Flags.allowThermalHeadroomThresholds()) {
                            FrameworkStatsLog.write(
                                    FrameworkStatsLog.THERMAL_HEADROOM_THRESHOLDS_CALLED,
                                    Binder.getCallingUid(),
                                    3);
                            throw new UnsupportedOperationException(
                                    "Thermal headroom thresholds not enabled");
                        }
                        synchronized (ThermalManagerService.this.mTemperatureWatcher.mSamples) {
                            FrameworkStatsLog.write(
                                    FrameworkStatsLog.THERMAL_HEADROOM_THRESHOLDS_CALLED,
                                    Binder.getCallingUid(),
                                    1);
                            float[] fArr =
                                    ThermalManagerService.this
                                            .mTemperatureWatcher
                                            .mHeadroomThresholds;
                            copyOf = Arrays.copyOf(fArr, fArr.length);
                        }
                        return copyOf;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    public final void onShellCommand(
                            FileDescriptor fileDescriptor,
                            FileDescriptor fileDescriptor2,
                            FileDescriptor fileDescriptor3,
                            String[] strArr,
                            ShellCallback shellCallback,
                            ResultReceiver resultReceiver) {
                        int callingUid = Binder.getCallingUid();
                        if (callingUid == 2000 || callingUid == 0) {
                            ThermalManagerService.this.new ThermalShellCommand()
                                    .exec(
                                            this,
                                            fileDescriptor,
                                            fileDescriptor2,
                                            fileDescriptor3,
                                            strArr,
                                            shellCallback,
                                            resultReceiver);
                        } else {
                            android.util.Slog.w(
                                    "ThermalManagerService",
                                    "Only shell is allowed to call thermalservice shell commands");
                        }
                    }

                    public final boolean registerThermalEventListener(
                            IThermalEventListener iThermalEventListener) {
                        ThermalManagerService.this
                                .getContext()
                                .enforceCallingOrSelfPermission(
                                        "android.permission.DEVICE_POWER", null);
                        synchronized (ThermalManagerService.this.mLock) {
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                if (!ThermalManagerService.this.mThermalEventListeners.register(
                                        iThermalEventListener, null)) {
                                    return false;
                                }
                                ThermalManagerService
                                        .m826$$Nest$mpostEventListenerCurrentTemperatures(
                                                ThermalManagerService.this,
                                                iThermalEventListener,
                                                null);
                                return true;
                            } finally {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                    }

                    public final boolean registerThermalEventListenerWithType(
                            IThermalEventListener iThermalEventListener, int i) {
                        ThermalManagerService.this
                                .getContext()
                                .enforceCallingOrSelfPermission(
                                        "android.permission.DEVICE_POWER", null);
                        synchronized (ThermalManagerService.this.mLock) {
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                if (!ThermalManagerService.this.mThermalEventListeners.register(
                                        iThermalEventListener, new Integer(i))) {
                                    return false;
                                }
                                ThermalManagerService
                                        .m826$$Nest$mpostEventListenerCurrentTemperatures(
                                                ThermalManagerService.this,
                                                iThermalEventListener,
                                                new Integer(i));
                                return true;
                            } finally {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                    }

                    public final boolean registerThermalStatusListener(
                            IThermalStatusListener iThermalStatusListener) {
                        synchronized (ThermalManagerService.this.mLock) {
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                if (!ThermalManagerService.this.mThermalStatusListeners.register(
                                        iThermalStatusListener)) {
                                    return false;
                                }
                                ThermalManagerService thermalManagerService =
                                        ThermalManagerService.this;
                                thermalManagerService.getClass();
                                if (!FgThread.getHandler()
                                        .post(
                                                new ThermalManagerService$$ExternalSyntheticLambda2(
                                                        1,
                                                        thermalManagerService,
                                                        iThermalStatusListener))) {
                                    android.util.Slog.e(
                                            "ThermalManagerService",
                                            "Thermal callback failed to queue");
                                }
                                return true;
                            } finally {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                    }

                    public final boolean unregisterThermalEventListener(
                            IThermalEventListener iThermalEventListener) {
                        boolean unregister;
                        ThermalManagerService.this
                                .getContext()
                                .enforceCallingOrSelfPermission(
                                        "android.permission.DEVICE_POWER", null);
                        synchronized (ThermalManagerService.this.mLock) {
                            try {
                                long clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    unregister =
                                            ThermalManagerService.this.mThermalEventListeners
                                                    .unregister(iThermalEventListener);
                                } finally {
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        return unregister;
                    }

                    public final boolean unregisterThermalStatusListener(
                            IThermalStatusListener iThermalStatusListener) {
                        boolean unregister;
                        synchronized (ThermalManagerService.this.mLock) {
                            try {
                                long clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    unregister =
                                            ThermalManagerService.this.mThermalStatusListeners
                                                    .unregister(iThermalStatusListener);
                                } finally {
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        return unregister;
                    }
                };
        this.mContext = context;
        this.mHalWrapper = thermalHalWrapper;
        if (thermalHalWrapper != null) {
            thermalHalWrapper.setCallback(
                    new ThermalManagerService$$ExternalSyntheticLambda0(this));
        }
        this.mStatus = 0;
    }

    public static void dumpItemsLocked(PrintWriter printWriter, Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            printWriter.println("\t" + it.next().toString());
        }
    }

    public static void dumpTemperatureThresholds(PrintWriter printWriter, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            TemperatureThreshold temperatureThreshold = (TemperatureThreshold) it.next();
            printWriter.println(
                    "\tTemperatureThreshold{mType="
                            + temperatureThreshold.type
                            + ", mName="
                            + temperatureThreshold.name
                            + ", mHotThrottlingThresholds="
                            + Arrays.toString(temperatureThreshold.hotThrottlingThresholds)
                            + ", mColdThrottlingThresholds="
                            + Arrays.toString(temperatureThreshold.coldThrottlingThresholds)
                            + "}");
        }
    }

    public static void postEventListener(
            Temperature temperature, IThermalEventListener iThermalEventListener, Integer num) {
        if ((num == null || num.intValue() == temperature.getType())
                && !FgThread.getHandler()
                        .post(
                                new ThermalManagerService$$ExternalSyntheticLambda2(
                                        0, iThermalEventListener, temperature))) {
            android.util.Slog.e("ThermalManagerService", "Thermal callback failed to queue");
        }
    }

    public void dumpInternal(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(getContext(), "ThermalManagerService", printWriter)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (this.mLock) {
                    try {
                        printWriter.println("IsStatusOverride: " + this.mIsStatusOverride);
                        printWriter.println("ThermalEventListeners:");
                        this.mThermalEventListeners.dump(printWriter, "\t");
                        printWriter.println("ThermalStatusListeners:");
                        this.mThermalStatusListeners.dump(printWriter, "\t");
                        printWriter.println("Thermal Status: " + this.mStatus);
                        printWriter.println("Cached temperatures:");
                        dumpItemsLocked(printWriter, this.mTemperatureMap.values());
                        printWriter.println("HAL Ready: " + this.mHalReady.get());
                        if (this.mHalReady.get()) {
                            printWriter.println("HAL connection:");
                            this.mHalWrapper.dump(printWriter);
                            printWriter.println("Current temperatures from HAL:");
                            dumpItemsLocked(
                                    printWriter, this.mHalWrapper.getCurrentTemperatures(0, false));
                            printWriter.println("Current cooling devices from HAL:");
                            dumpItemsLocked(
                                    printWriter,
                                    this.mHalWrapper.getCurrentCoolingDevices(0, false));
                            printWriter.println("Temperature static thresholds from HAL:");
                            dumpTemperatureThresholds(
                                    printWriter,
                                    this.mHalWrapper.getTemperatureThresholds(0, false));
                        }
                    } finally {
                    }
                }
                if (Flags.allowThermalHeadroomThresholds()) {
                    synchronized (this.mTemperatureWatcher.mSamples) {
                        printWriter.println("Temperature headroom thresholds:");
                        printWriter.println(
                                Arrays.toString(this.mTemperatureWatcher.mHeadroomThresholds));
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void notifyEventListenersLocked(Temperature temperature) {
        int beginBroadcast = this.mThermalEventListeners.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                postEventListener(
                        temperature,
                        this.mThermalEventListeners.getBroadcastItem(i),
                        (Integer) this.mThermalEventListeners.getBroadcastCookie(i));
            } catch (Throwable th) {
                this.mThermalEventListeners.finishBroadcast();
                throw th;
            }
        }
        this.mThermalEventListeners.finishBroadcast();
        EventLog.writeEvent(
                2737,
                temperature.getName(),
                Integer.valueOf(temperature.getType()),
                Float.valueOf(temperature.getValue()),
                Integer.valueOf(temperature.getStatus()),
                Integer.valueOf(this.mStatus));
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        StatsManager statsManager;
        if (i == 550) {
            synchronized (this.mLock) {
                try {
                    boolean z = this.mHalWrapper != null;
                    if (!z) {
                        ThermalHalAidlWrapper thermalHalAidlWrapper =
                                new ThermalHalAidlWrapper(
                                        new ThermalManagerService$$ExternalSyntheticLambda0(this));
                        this.mHalWrapper = thermalHalAidlWrapper;
                        z = thermalHalAidlWrapper.connectToHal();
                    }
                    if (!z) {
                        ThermalHal11Wrapper thermalHal11Wrapper =
                                new ThermalHal11Wrapper(
                                        new ThermalManagerService$$ExternalSyntheticLambda0(this),
                                        1);
                        this.mHalWrapper = thermalHal11Wrapper;
                        z = thermalHal11Wrapper.connectToHal();
                    }
                    if (!z) {
                        ThermalHal11Wrapper thermalHal11Wrapper2 =
                                new ThermalHal11Wrapper(
                                        new ThermalManagerService$$ExternalSyntheticLambda0(this),
                                        0);
                        this.mHalWrapper = thermalHal11Wrapper2;
                        z = thermalHal11Wrapper2.connectToHal();
                    }
                    if (!z) {
                        ThermalManagerService$$ExternalSyntheticLambda0
                                thermalManagerService$$ExternalSyntheticLambda0 =
                                        new ThermalManagerService$$ExternalSyntheticLambda0(this);
                        ThermalHal10Wrapper thermalHal10Wrapper = new ThermalHal10Wrapper();
                        thermalHal10Wrapper.mThermalHal10 = null;
                        thermalHal10Wrapper.mCallback =
                                thermalManagerService$$ExternalSyntheticLambda0;
                        this.mHalWrapper = thermalHal10Wrapper;
                        z = thermalHal10Wrapper.connectToHal();
                    }
                    if (z) {
                        ArrayList arrayList =
                                (ArrayList) this.mHalWrapper.getCurrentTemperatures(0, false);
                        int size = arrayList.size();
                        if (size == 0) {
                            android.util.Slog.w(
                                    "ThermalManagerService",
                                    "Thermal HAL reported invalid data, abort connection");
                        }
                        for (int i2 = 0; i2 < size; i2++) {
                            onTemperatureChanged((Temperature) arrayList.get(i2), false);
                        }
                        onTemperatureMapChangedLocked();
                        this.mTemperatureWatcher.updateThresholds();
                        this.mHalReady.set(true);
                    } else {
                        android.util.Slog.w(
                                "ThermalManagerService", "No Thermal HAL service on this device");
                    }
                } finally {
                }
            }
        }
        if (i != 1000
                || (statsManager =
                                (StatsManager) this.mContext.getSystemService(StatsManager.class))
                        == null) {
            return;
        }
        statsManager.setPullAtomCallback(
                FrameworkStatsLog.THERMAL_HEADROOM_THRESHOLDS,
                (StatsManager.PullAtomMetadata) null,
                ConcurrentUtils.DIRECT_EXECUTOR,
                new StatsManager
                        .StatsPullAtomCallback() { // from class:
                                                   // com.android.server.power.ThermalManagerService$$ExternalSyntheticLambda1
                    public final int onPullAtom(int i3, List list) {
                        float[] copyOf;
                        ThermalManagerService thermalManagerService = ThermalManagerService.this;
                        if (i3 != 10201) {
                            thermalManagerService.getClass();
                            return 0;
                        }
                        synchronized (thermalManagerService.mTemperatureWatcher.mSamples) {
                            float[] fArr =
                                    thermalManagerService.mTemperatureWatcher.mHeadroomThresholds;
                            copyOf = Arrays.copyOf(fArr, fArr.length);
                        }
                        list.add(
                                FrameworkStatsLog.buildStatsEvent(
                                        FrameworkStatsLog.THERMAL_HEADROOM_THRESHOLDS, copyOf));
                        return 0;
                    }
                });
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("thermalservice", this.mService);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0023 A[Catch: all -> 0x001c, TryCatch #0 {all -> 0x001c, blocks: (B:4:0x0003, B:6:0x0011, B:10:0x0023, B:11:0x0026, B:15:0x001e), top: B:3:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onTemperatureChanged(android.os.Temperature r4, boolean r5) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.mLock
            monitor-enter(r0)
            android.util.ArrayMap r1 = r3.mTemperatureMap     // Catch: java.lang.Throwable -> L1c
            java.lang.String r2 = r4.getName()     // Catch: java.lang.Throwable -> L1c
            java.lang.Object r1 = r1.put(r2, r4)     // Catch: java.lang.Throwable -> L1c
            android.os.Temperature r1 = (android.os.Temperature) r1     // Catch: java.lang.Throwable -> L1c
            if (r1 == 0) goto L1e
            int r1 = r1.getStatus()     // Catch: java.lang.Throwable -> L1c
            int r2 = r4.getStatus()     // Catch: java.lang.Throwable -> L1c
            if (r1 == r2) goto L21
            goto L1e
        L1c:
            r3 = move-exception
            goto L28
        L1e:
            r3.notifyEventListenersLocked(r4)     // Catch: java.lang.Throwable -> L1c
        L21:
            if (r5 == 0) goto L26
            r3.onTemperatureMapChangedLocked()     // Catch: java.lang.Throwable -> L1c
        L26:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L1c
            return
        L28:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L1c
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.ThermalManagerService.onTemperatureChanged(android.os.Temperature,"
                    + " boolean):void");
    }

    public final void onTemperatureMapChangedLocked() {
        int size = this.mTemperatureMap.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            Temperature temperature = (Temperature) this.mTemperatureMap.valueAt(i2);
            if ((temperature.getType() == 3 || temperature.getType() == 2)
                    && temperature.getStatus() >= i) {
                i = temperature.getStatus();
            }
        }
        if (this.mIsStatusOverride) {
            return;
        }
        setStatusLocked(i);
    }

    public final void setStatusLocked(int i) {
        if (BatteryService$$ExternalSyntheticOutline0.m45m(
                        "/data/data/com.sec.android.sdhms/sdhms_cooldown_disable")
                || i == this.mStatus) {
            return;
        }
        this.mStatus = i;
        int beginBroadcast = this.mThermalStatusListeners.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                if (!FgThread.getHandler()
                        .post(
                                new ThermalManagerService$$ExternalSyntheticLambda2(
                                        1,
                                        this,
                                        this.mThermalStatusListeners.getBroadcastItem(i2)))) {
                    android.util.Slog.e(
                            "ThermalManagerService", "Thermal callback failed to queue");
                }
            } finally {
                this.mThermalStatusListeners.finishBroadcast();
            }
        }
    }
}
