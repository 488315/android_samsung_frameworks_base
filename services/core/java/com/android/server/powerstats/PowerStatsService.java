package com.android.server.powerstats;

import android.app.StatsManager;
import android.content.Context;
import android.hardware.power.stats.Channel;
import android.hardware.power.stats.EnergyConsumer;
import android.hardware.power.stats.EnergyConsumerResult;
import android.hardware.power.stats.EnergyMeasurement;
import android.hardware.power.stats.PowerEntity;
import android.hardware.power.stats.State;
import android.hardware.power.stats.StateResidencyResult;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.IPowerStatsService;
import android.os.Looper;
import android.os.PowerMonitor;
import android.os.ResultReceiver;
import android.provider.DeviceConfig;
import android.provider.DeviceConfigInterface;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.os.Clock;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.SystemService;
import com.android.server.powerstats.PowerStatsLogger;
import com.android.server.powerstats.PowerStatsService;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.apache.commons.math.MathException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PowerStatsService extends SystemService {
    public final Clock mClock;
    public final Context mContext;
    public File mDataStoragePath;
    public final DeviceConfigInterface mDeviceConfig;
    public final DeviceConfigListener mDeviceConfigListener;
    public EnergyConsumer[] mEnergyConsumers;
    public Channel[] mEnergyMeters;
    public Handler mHandler;
    public final Injector mInjector;
    public IntervalRandomNoiseGenerator mIntervalRandomNoiseGenerator;
    public boolean mPowerMonitorApiEnabled;
    public PowerMonitorState[] mPowerMonitorStates;
    public volatile PowerMonitor[] mPowerMonitors;
    public LocalService mPowerStatsInternal;
    public PowerStatsLogger mPowerStatsLogger;
    public final AnonymousClass1 mService;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.powerstats.PowerStatsService$1, reason: invalid class name */
    public final class AnonymousClass1 extends IPowerStatsService.Stub {
        public AnonymousClass1() {
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(PowerStatsService.this.mContext, "PowerStatsService", printWriter)) {
                if (PowerStatsService.this.mPowerStatsLogger == null) {
                    Slog.e("PowerStatsService", "PowerStats HAL is not initialized.  No data available.");
                    return;
                }
                if (strArr.length > 0 && "--proto".equals(strArr[0])) {
                    if ("model".equals(strArr[1])) {
                        PowerStatsLogger powerStatsLogger = PowerStatsService.this.mPowerStatsLogger;
                        powerStatsLogger.getClass();
                        ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
                        try {
                            ProtoStreamUtils$ChannelUtils.packProtoMessage(powerStatsLogger.mPowerStatsHALWrapper.getEnergyConsumerInfo(), protoOutputStream);
                            powerStatsLogger.mPowerStatsModelStorage.read(new PowerStatsLogger.AnonymousClass1(1, protoOutputStream));
                        } catch (IOException e) {
                            Slog.e("PowerStatsLogger", "Failed to write energy model info to incident report.", e);
                        }
                        protoOutputStream.flush();
                        return;
                    }
                    if ("meter".equals(strArr[1])) {
                        PowerStatsLogger powerStatsLogger2 = PowerStatsService.this.mPowerStatsLogger;
                        powerStatsLogger2.getClass();
                        ProtoOutputStream protoOutputStream2 = new ProtoOutputStream(fileDescriptor);
                        try {
                            ProtoStreamUtils$ChannelUtils.packProtoMessage(powerStatsLogger2.mPowerStatsHALWrapper.getEnergyMeterInfo(), protoOutputStream2);
                            powerStatsLogger2.mPowerStatsMeterStorage.read(new PowerStatsLogger.AnonymousClass1(0, protoOutputStream2));
                        } catch (IOException e2) {
                            Slog.e("PowerStatsLogger", "Failed to write energy meter info to incident report.", e2);
                        }
                        protoOutputStream2.flush();
                        return;
                    }
                    if ("residency".equals(strArr[1])) {
                        PowerStatsLogger powerStatsLogger3 = PowerStatsService.this.mPowerStatsLogger;
                        powerStatsLogger3.getClass();
                        ProtoOutputStream protoOutputStream3 = new ProtoOutputStream(fileDescriptor);
                        try {
                            ProtoStreamUtils$ChannelUtils.packProtoMessage(powerStatsLogger3.mPowerStatsHALWrapper.getPowerEntityInfo(), protoOutputStream3);
                            powerStatsLogger3.mPowerStatsResidencyStorage.read(new PowerStatsLogger.AnonymousClass1(2, protoOutputStream3));
                        } catch (IOException e3) {
                            Slog.e("PowerStatsLogger", "Failed to write residency data to incident report.", e3);
                        }
                        protoOutputStream3.flush();
                        return;
                    }
                    return;
                }
                IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
                indentingPrintWriter.println("PowerStatsService dumpsys: available PowerEntities");
                PowerEntity[] powerEntityInfo = PowerStatsService.this.getPowerStatsHal().getPowerEntityInfo();
                indentingPrintWriter.increaseIndent();
                if (powerEntityInfo != null) {
                    for (int i = 0; i < powerEntityInfo.length; i++) {
                        indentingPrintWriter.println("PowerEntityId: " + powerEntityInfo[i].id + ", PowerEntityName: " + powerEntityInfo[i].name);
                        if (powerEntityInfo[i].states != null) {
                            for (int i2 = 0; i2 < powerEntityInfo[i].states.length; i2++) {
                                indentingPrintWriter.println("  StateId: " + powerEntityInfo[i].states[i2].id + ", StateName: " + powerEntityInfo[i].states[i2].name);
                            }
                        }
                    }
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("PowerStatsService dumpsys: available Channels");
                Channel[] energyMeterInfo = PowerStatsService.this.getPowerStatsHal().getEnergyMeterInfo();
                indentingPrintWriter.increaseIndent();
                if (energyMeterInfo != null) {
                    for (int i3 = 0; i3 < energyMeterInfo.length; i3++) {
                        indentingPrintWriter.println("ChannelId: " + energyMeterInfo[i3].id + ", ChannelName: " + energyMeterInfo[i3].name + ", ChannelSubsystem: " + energyMeterInfo[i3].subsystem);
                    }
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("PowerStatsService dumpsys: available EnergyConsumers");
                EnergyConsumer[] energyConsumerInfo = PowerStatsService.this.getPowerStatsHal().getEnergyConsumerInfo();
                indentingPrintWriter.increaseIndent();
                if (energyConsumerInfo != null) {
                    for (int i4 = 0; i4 < energyConsumerInfo.length; i4++) {
                        indentingPrintWriter.println("EnergyConsumerId: " + energyConsumerInfo[i4].id + ", Ordinal: " + energyConsumerInfo[i4].ordinal + ", Type: " + ((int) energyConsumerInfo[i4].type) + ", Name: " + energyConsumerInfo[i4].name);
                    }
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("PowerStatsService dumpsys: PowerStatsLogger stats");
                indentingPrintWriter.increaseIndent();
                PowerStatsLogger powerStatsLogger4 = PowerStatsService.this.mPowerStatsLogger;
                powerStatsLogger4.getClass();
                indentingPrintWriter.println("PowerStats Meter Data:");
                indentingPrintWriter.increaseIndent();
                powerStatsLogger4.mPowerStatsMeterStorage.dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("PowerStats Model Data:");
                indentingPrintWriter.increaseIndent();
                powerStatsLogger4.mPowerStatsModelStorage.dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("PowerStats State Residency Data:");
                indentingPrintWriter.increaseIndent();
                powerStatsLogger4.mPowerStatsResidencyStorage.dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.decreaseIndent();
            }
        }

        public final void getPowerMonitorReadings(final int[] iArr, final ResultReceiver resultReceiver) {
            final int callingUid = Binder.getCallingUid();
            PowerStatsService.m850$$Nest$mgetHandler(PowerStatsService.this).post(new Runnable() { // from class: com.android.server.powerstats.PowerStatsService$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    PowerStatsService.AnonymousClass1 anonymousClass1 = PowerStatsService.AnonymousClass1.this;
                    PowerStatsService.this.getPowerMonitorReadingsImpl(iArr, resultReceiver, callingUid);
                }
            });
        }

        public final void getSupportedPowerMonitors(final ResultReceiver resultReceiver) {
            PowerStatsService.m850$$Nest$mgetHandler(PowerStatsService.this).post(new Runnable() { // from class: com.android.server.powerstats.PowerStatsService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PowerStatsService.AnonymousClass1 anonymousClass1 = PowerStatsService.AnonymousClass1.this;
                    PowerStatsService.this.getSupportedPowerMonitorsImpl(resultReceiver);
                }
            });
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeviceConfigListener implements DeviceConfig.OnPropertiesChangedListener {
        public final Executor mExecutor;

        public DeviceConfigListener() {
            this.mExecutor = new HandlerExecutor(PowerStatsService.m850$$Nest$mgetHandler(PowerStatsService.this));
        }

        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            PowerStatsService powerStatsService = PowerStatsService.this;
            boolean z = powerStatsService.mDeviceConfig.getBoolean("battery_stats", "power_monitor_api_enabled", true);
            if (z != powerStatsService.mPowerMonitorApiEnabled) {
                powerStatsService.mPowerMonitorApiEnabled = z;
                powerStatsService.mPowerMonitors = null;
                powerStatsService.mPowerMonitorStates = null;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {
        public PowerStatsHALWrapper$IPowerStatsHALWrapper mPowerStatsHALWrapper;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService {
        public LocalService() {
        }

        public final CompletableFuture getEnergyConsumedAsync(int[] iArr) {
            CompletableFuture completableFuture = new CompletableFuture();
            PowerStatsService.m850$$Nest$mgetHandler(PowerStatsService.this).post(new PowerStatsService$LocalService$$ExternalSyntheticLambda0(this, completableFuture, iArr, 0));
            return completableFuture;
        }

        public final CompletableFuture getStateResidencyAsync(int[] iArr) {
            CompletableFuture completableFuture = new CompletableFuture();
            PowerStatsService.m850$$Nest$mgetHandler(PowerStatsService.this).post(new PowerStatsService$LocalService$$ExternalSyntheticLambda0(this, completableFuture, iArr, 2));
            return completableFuture;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PowerMonitorState {
        public long energyUws = -1;
        public final int id;
        public final PowerMonitor powerMonitor;
        public long prevEnergyUws;
        public long timestampMs;

        public PowerMonitorState(PowerMonitor powerMonitor, int i) {
            this.powerMonitor = powerMonitor;
            this.id = i;
        }
    }

    /* renamed from: -$$Nest$mgetHandler, reason: not valid java name */
    public static Handler m850$$Nest$mgetHandler(PowerStatsService powerStatsService) {
        Handler handler;
        synchronized (powerStatsService) {
            try {
                if (powerStatsService.mHandler == null) {
                    powerStatsService.mHandler = new Handler(powerStatsService.getLooper());
                }
                handler = powerStatsService.mHandler;
            } catch (Throwable th) {
                throw th;
            }
        }
        return handler;
    }

    public PowerStatsService(Context context) {
        this(context, new Injector());
    }

    public PowerStatsService(Context context, Injector injector) {
        super(context);
        this.mDeviceConfigListener = new DeviceConfigListener();
        this.mEnergyConsumers = null;
        this.mEnergyMeters = null;
        this.mService = new AnonymousClass1();
        this.mPowerMonitorApiEnabled = true;
        this.mContext = context;
        this.mInjector = injector;
        injector.getClass();
        this.mClock = Clock.SYSTEM_CLOCK;
        this.mDeviceConfig = DeviceConfigInterface.REAL;
    }

    public static int[] collectIds(PowerMonitorState[] powerMonitorStateArr, int i) {
        int i2 = 0;
        for (PowerMonitorState powerMonitorState : powerMonitorStateArr) {
            if (powerMonitorState.powerMonitor.getType() == i) {
                i2++;
            }
        }
        if (i2 == 0) {
            return null;
        }
        int[] iArr = new int[i2];
        int i3 = 0;
        for (PowerMonitorState powerMonitorState2 : powerMonitorStateArr) {
            if (powerMonitorState2.powerMonitor.getType() == i) {
                iArr[i3] = powerMonitorState2.id;
                i3++;
            }
        }
        return iArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getEnergyConsumerName(android.hardware.power.stats.EnergyConsumer r8, android.hardware.power.stats.EnergyConsumer[] r9) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            byte r1 = r8.type
            switch(r1) {
                case 1: goto L4f;
                case 2: goto L49;
                case 3: goto L43;
                case 4: goto L3d;
                case 5: goto L37;
                case 6: goto L31;
                case 7: goto L2b;
                default: goto La;
            }
        La:
            java.lang.String r1 = r8.name
            if (r1 == 0) goto L20
            boolean r1 = r1.isBlank()
            if (r1 != 0) goto L20
            java.lang.String r1 = r8.name
            java.util.Locale r2 = java.util.Locale.ENGLISH
            java.lang.String r1 = r1.toUpperCase(r2)
            r0.append(r1)
            goto L54
        L20:
            java.lang.String r1 = "CONSUMER_"
            r0.append(r1)
            byte r1 = r8.type
            r0.append(r1)
            goto L54
        L2b:
            java.lang.String r1 = "CAMERA"
            r0.append(r1)
            goto L54
        L31:
            java.lang.String r1 = "WIFI"
            r0.append(r1)
            goto L54
        L37:
            java.lang.String r1 = "MOBILE_RADIO"
            r0.append(r1)
            goto L54
        L3d:
            java.lang.String r1 = "GNSS"
            r0.append(r1)
            goto L54
        L43:
            java.lang.String r1 = "DISPLAY"
            r0.append(r1)
            goto L54
        L49:
            java.lang.String r1 = "CPU"
            r0.append(r1)
            goto L54
        L4f:
            java.lang.String r1 = "BLUETOOTH"
            r0.append(r1)
        L54:
            int r1 = r8.ordinal
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L5c
            r1 = r2
            goto L5d
        L5c:
            r1 = r3
        L5d:
            if (r1 != 0) goto L72
            int r4 = r9.length
        L60:
            if (r3 >= r4) goto L72
            r5 = r9[r3]
            byte r6 = r5.type
            byte r7 = r8.type
            if (r6 != r7) goto L6f
            int r5 = r5.ordinal
            if (r5 == 0) goto L6f
            goto L73
        L6f:
            int r3 = r3 + 1
            goto L60
        L72:
            r2 = r1
        L73:
            if (r2 == 0) goto L7f
            r9 = 47
            r0.append(r9)
            int r8 = r8.ordinal
            r0.append(r8)
        L7f:
            java.lang.String r8 = r0.toString()
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.powerstats.PowerStatsService.getEnergyConsumerName(android.hardware.power.stats.EnergyConsumer, android.hardware.power.stats.EnergyConsumer[]):java.lang.String");
    }

    public final void ensurePowerMonitors() {
        int i;
        if (this.mPowerMonitors != null) {
            return;
        }
        synchronized (this) {
            try {
                if (this.mPowerMonitors != null) {
                    return;
                }
                if (this.mIntervalRandomNoiseGenerator == null) {
                    this.mInjector.getClass();
                    this.mIntervalRandomNoiseGenerator = new IntervalRandomNoiseGenerator();
                }
                if (!this.mPowerMonitorApiEnabled) {
                    this.mPowerMonitors = new PowerMonitor[0];
                    this.mPowerMonitorStates = new PowerMonitorState[0];
                    return;
                }
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                synchronized (this) {
                    try {
                        if (this.mEnergyMeters == null) {
                            this.mEnergyMeters = getPowerStatsHal().getEnergyMeterInfo();
                        }
                        Channel[] channelArr = this.mEnergyMeters;
                        if (channelArr != null) {
                            int length = channelArr.length;
                            int i2 = 0;
                            i = 0;
                            while (i2 < length) {
                                Channel channel = channelArr[i2];
                                int i3 = i + 1;
                                StringBuilder sb = new StringBuilder("[");
                                sb.append(channel.name);
                                sb.append("]:");
                                String str = channel.subsystem;
                                if (str != null) {
                                    sb.append(str);
                                }
                                PowerMonitor powerMonitor = new PowerMonitor(i, 1, sb.toString());
                                arrayList.add(powerMonitor);
                                arrayList2.add(new PowerMonitorState(powerMonitor, channel.id));
                                i2++;
                                i = i3;
                            }
                        } else {
                            i = 0;
                        }
                        EnergyConsumer[] energyConsumerInfo = getEnergyConsumerInfo();
                        if (energyConsumerInfo != null) {
                            int length2 = energyConsumerInfo.length;
                            int i4 = 0;
                            while (i4 < length2) {
                                EnergyConsumer energyConsumer = energyConsumerInfo[i4];
                                PowerMonitor powerMonitor2 = new PowerMonitor(i, 0, getEnergyConsumerName(energyConsumer, energyConsumerInfo));
                                arrayList.add(powerMonitor2);
                                arrayList2.add(new PowerMonitorState(powerMonitor2, energyConsumer.id));
                                i4++;
                                i++;
                            }
                        }
                        this.mPowerMonitors = (PowerMonitor[]) arrayList.toArray(new PowerMonitor[arrayList.size()]);
                        this.mPowerMonitorStates = (PowerMonitorState[]) arrayList2.toArray(new PowerMonitorState[arrayList.size()]);
                    } finally {
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean getDeleteMeterDataOnBoot() {
        return this.mPowerStatsLogger.mDeleteMeterDataOnBoot;
    }

    public boolean getDeleteModelDataOnBoot() {
        return this.mPowerStatsLogger.mDeleteModelDataOnBoot;
    }

    public boolean getDeleteResidencyDataOnBoot() {
        return this.mPowerStatsLogger.mDeleteResidencyDataOnBoot;
    }

    public final EnergyConsumer[] getEnergyConsumerInfo() {
        EnergyConsumer[] energyConsumerArr;
        synchronized (this) {
            try {
                if (this.mEnergyConsumers == null) {
                    this.mEnergyConsumers = getPowerStatsHal().getEnergyConsumerInfo();
                }
                energyConsumerArr = this.mEnergyConsumers;
            } catch (Throwable th) {
                throw th;
            }
        }
        return energyConsumerArr;
    }

    public final Looper getLooper() {
        Looper looper;
        synchronized (this) {
            HandlerThread handlerThread = new HandlerThread("PowerStatsService");
            handlerThread.start();
            looper = handlerThread.getLooper();
        }
        return looper;
    }

    public void getPowerMonitorReadingsImpl(int[] iArr, ResultReceiver resultReceiver, int i) {
        EnergyMeasurement[] readEnergyMeter;
        EnergyConsumerResult[] energyConsumed;
        ensurePowerMonitors();
        int length = iArr.length;
        PowerMonitorState[] powerMonitorStateArr = new PowerMonitorState[length];
        long j = Long.MAX_VALUE;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            int i3 = iArr[i2];
            if (i3 >= 0) {
                PowerMonitorState[] powerMonitorStateArr2 = this.mPowerMonitorStates;
                if (i3 < powerMonitorStateArr2.length) {
                    powerMonitorStateArr[i2] = powerMonitorStateArr2[i3];
                    PowerMonitorState powerMonitorState = powerMonitorStateArr2[i3];
                    if (powerMonitorState != null) {
                        long j2 = powerMonitorState.timestampMs;
                        if (j2 < j) {
                            j = j2;
                        }
                    }
                }
            }
            resultReceiver.send(1, null);
            return;
        }
        if (j == 0 || this.mClock.elapsedRealtime() - j > 30000) {
            int[] collectIds = collectIds(powerMonitorStateArr, 0);
            if (collectIds != null && (energyConsumed = getPowerStatsHal().getEnergyConsumed(collectIds)) != null) {
                for (int i4 = 0; i4 < length; i4++) {
                    PowerMonitorState powerMonitorState2 = powerMonitorStateArr[i4];
                    if (powerMonitorState2.powerMonitor.getType() == 0) {
                        int length2 = energyConsumed.length;
                        int i5 = 0;
                        while (true) {
                            if (i5 < length2) {
                                EnergyConsumerResult energyConsumerResult = energyConsumed[i5];
                                if (energyConsumerResult.id == powerMonitorState2.id) {
                                    powerMonitorState2.prevEnergyUws = powerMonitorState2.energyUws;
                                    powerMonitorState2.energyUws = energyConsumerResult.energyUWs;
                                    powerMonitorState2.timestampMs = energyConsumerResult.timestampMs;
                                    break;
                                }
                                i5++;
                            }
                        }
                    }
                }
            }
            int[] collectIds2 = collectIds(powerMonitorStateArr, 1);
            if (collectIds2 != null && (readEnergyMeter = getPowerStatsHal().readEnergyMeter(collectIds2)) != null) {
                for (int i6 = 0; i6 < length; i6++) {
                    PowerMonitorState powerMonitorState3 = powerMonitorStateArr[i6];
                    if (powerMonitorState3.powerMonitor.getType() == 1) {
                        int length3 = readEnergyMeter.length;
                        int i7 = 0;
                        while (true) {
                            if (i7 < length3) {
                                EnergyMeasurement energyMeasurement = readEnergyMeter[i7];
                                if (energyMeasurement.id == powerMonitorState3.id) {
                                    powerMonitorState3.prevEnergyUws = powerMonitorState3.energyUws;
                                    powerMonitorState3.energyUws = energyMeasurement.energyUWs;
                                    powerMonitorState3.timestampMs = energyMeasurement.timestampMs;
                                    break;
                                }
                                i7++;
                            }
                        }
                    }
                }
            }
            Arrays.fill(this.mIntervalRandomNoiseGenerator.mSamples, -1.0d);
        }
        long[] jArr = new long[length];
        long[] jArr2 = new long[length];
        for (int i8 = 0; i8 < length; i8++) {
            PowerMonitorState powerMonitorState4 = powerMonitorStateArr[i8];
            long j3 = powerMonitorState4.energyUws;
            if (j3 != -1) {
                long j4 = powerMonitorState4.prevEnergyUws;
                if (j4 != -1) {
                    IntervalRandomNoiseGenerator intervalRandomNoiseGenerator = this.mIntervalRandomNoiseGenerator;
                    long max = Math.max(j4, j3 - 10000000);
                    long j5 = powerMonitorState4.energyUws;
                    int i9 = i % 17;
                    double[] dArr = intervalRandomNoiseGenerator.mSamples;
                    double d = dArr[i9];
                    if (d < 0.0d) {
                        try {
                            d = intervalRandomNoiseGenerator.mDistribution.sample();
                            dArr[i9] = d;
                        } catch (MathException e) {
                            throw new IllegalStateException(e);
                        }
                    }
                    jArr[i8] = max + ((long) ((j5 - max) * d));
                    jArr2[i8] = powerMonitorState4.timestampMs;
                }
            }
            jArr[i8] = j3;
            jArr2[i8] = powerMonitorState4.timestampMs;
        }
        Bundle bundle = new Bundle();
        bundle.putLongArray("energy", jArr);
        bundle.putLongArray("timestamps", jArr2);
        resultReceiver.send(0, bundle);
    }

    public final PowerStatsHALWrapper$IPowerStatsHALWrapper getPowerStatsHal() {
        PowerStatsHALWrapper$IPowerStatsHALWrapper powerStatsHALWrapper$IPowerStatsHALWrapper;
        Injector injector = this.mInjector;
        synchronized (injector) {
            try {
                if (injector.mPowerStatsHALWrapper == null) {
                    PowerStatsHALWrapper$IPowerStatsHALWrapper powerStatsHALWrapper$PowerStatsHAL20WrapperImpl = new PowerStatsHALWrapper$PowerStatsHAL20WrapperImpl();
                    PowerStatsHALWrapper$VintfHalCache powerStatsHALWrapper$VintfHalCache = new PowerStatsHALWrapper$VintfHalCache();
                    powerStatsHALWrapper$VintfHalCache.mInstance = null;
                    PowerStatsHALWrapper$PowerStatsHAL20WrapperImpl.sVintfPowerStats = null;
                    if (powerStatsHALWrapper$VintfHalCache.get() == null) {
                        PowerStatsHALWrapper$PowerStatsHAL20WrapperImpl.sVintfPowerStats = null;
                    } else {
                        PowerStatsHALWrapper$PowerStatsHAL20WrapperImpl.sVintfPowerStats = powerStatsHALWrapper$VintfHalCache;
                    }
                    if (!powerStatsHALWrapper$PowerStatsHAL20WrapperImpl.isInitialized()) {
                        powerStatsHALWrapper$PowerStatsHAL20WrapperImpl = new PowerStatsHALWrapper$IPowerStatsHALWrapper() { // from class: com.android.server.powerstats.PowerStatsHALWrapper$PowerStatsHAL10WrapperImpl
                            public final boolean mIsInitialized;

                            {
                                if (nativeInit()) {
                                    this.mIsInitialized = true;
                                } else {
                                    this.mIsInitialized = false;
                                }
                            }

                            private static native Channel[] nativeGetEnergyMeterInfo();

                            private static native PowerEntity[] nativeGetPowerEntityInfo();

                            private static native StateResidencyResult[] nativeGetStateResidency(int[] iArr);

                            private static native boolean nativeInit();

                            private static native EnergyMeasurement[] nativeReadEnergyMeters(int[] iArr);

                            @Override // com.android.server.powerstats.PowerStatsHALWrapper$IPowerStatsHALWrapper
                            public final EnergyConsumerResult[] getEnergyConsumed(int[] iArr) {
                                return new EnergyConsumerResult[0];
                            }

                            @Override // com.android.server.powerstats.PowerStatsHALWrapper$IPowerStatsHALWrapper
                            public final EnergyConsumer[] getEnergyConsumerInfo() {
                                return new EnergyConsumer[0];
                            }

                            @Override // com.android.server.powerstats.PowerStatsHALWrapper$IPowerStatsHALWrapper
                            public final Channel[] getEnergyMeterInfo() {
                                return nativeGetEnergyMeterInfo();
                            }

                            @Override // com.android.server.powerstats.PowerStatsHALWrapper$IPowerStatsHALWrapper
                            public final PowerEntity[] getPowerEntityInfo() {
                                return nativeGetPowerEntityInfo();
                            }

                            @Override // com.android.server.powerstats.PowerStatsHALWrapper$IPowerStatsHALWrapper
                            public final StateResidencyResult[] getStateResidency(int[] iArr) {
                                return nativeGetStateResidency(iArr);
                            }

                            @Override // com.android.server.powerstats.PowerStatsHALWrapper$IPowerStatsHALWrapper
                            public final boolean isInitialized() {
                                return this.mIsInitialized;
                            }

                            @Override // com.android.server.powerstats.PowerStatsHALWrapper$IPowerStatsHALWrapper
                            public final EnergyMeasurement[] readEnergyMeter(int[] iArr) {
                                return nativeReadEnergyMeters(iArr);
                            }
                        };
                    }
                    injector.mPowerStatsHALWrapper = powerStatsHALWrapper$PowerStatsHAL20WrapperImpl;
                }
                powerStatsHALWrapper$IPowerStatsHALWrapper = injector.mPowerStatsHALWrapper;
            } catch (Throwable th) {
                throw th;
            }
        }
        return powerStatsHALWrapper$IPowerStatsHALWrapper;
    }

    public void getSupportedPowerMonitorsImpl(ResultReceiver resultReceiver) {
        ensurePowerMonitors();
        Bundle bundle = new Bundle();
        bundle.putParcelableArray("monitors", this.mPowerMonitors);
        resultReceiver.send(0, bundle);
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        Injector injector = this.mInjector;
        if (i != 500) {
            if (i == 1000) {
                if (!getPowerStatsHal().isInitialized()) {
                    Slog.e("PowerStatsService", "Failed to start PowerStatsService loggers");
                    return;
                }
                injector.getClass();
                this.mDataStoragePath = new File(Environment.getDataSystemDeDirectory(0), "powerstats");
                PowerStatsLogger powerStatsLogger = new PowerStatsLogger(getLooper(), this.mDataStoragePath, getPowerStatsHal());
                this.mPowerStatsLogger = powerStatsLogger;
                new BatteryTrigger(this.mContext, powerStatsLogger);
                new TimerTrigger(this.mContext, this.mPowerStatsLogger);
                return;
            }
            return;
        }
        Context context = this.mContext;
        LocalService localService = this.mPowerStatsInternal;
        injector.getClass();
        StatsPullAtomCallbackImpl statsPullAtomCallbackImpl = new StatsPullAtomCallbackImpl();
        statsPullAtomCallbackImpl.mChannels = new HashMap();
        statsPullAtomCallbackImpl.mEntityNames = new HashMap();
        statsPullAtomCallbackImpl.mStateNames = new HashMap();
        statsPullAtomCallbackImpl.mPowerStatsInternal = localService;
        if (localService == null) {
            Slog.e("StatsPullAtomCallbackImpl", "Failed to start PowerStatsService statsd pullers");
        } else {
            StatsManager statsManager = (StatsManager) context.getSystemService(StatsManager.class);
            Channel[] energyMeterInfo = PowerStatsService.this.getPowerStatsHal().getEnergyMeterInfo();
            if (energyMeterInfo == null || energyMeterInfo.length == 0) {
                Slog.e("StatsPullAtomCallbackImpl", "Failed to init OnDevicePowerMeasurement puller");
            } else {
                for (Channel channel : energyMeterInfo) {
                    ((HashMap) statsPullAtomCallbackImpl.mChannels).put(Integer.valueOf(channel.id), channel);
                }
                statsManager.setPullAtomCallback(FrameworkStatsLog.ON_DEVICE_POWER_MEASUREMENT, (StatsManager.PullAtomMetadata) null, ConcurrentUtils.DIRECT_EXECUTOR, statsPullAtomCallbackImpl);
            }
            PowerEntity[] powerEntityInfo = PowerStatsService.this.getPowerStatsHal().getPowerEntityInfo();
            if (powerEntityInfo == null || powerEntityInfo.length == 0) {
                Slog.e("StatsPullAtomCallbackImpl", "Failed to init SubsystemSleepState puller");
            } else {
                for (PowerEntity powerEntity : powerEntityInfo) {
                    HashMap hashMap = new HashMap();
                    int i2 = 0;
                    while (true) {
                        State[] stateArr = powerEntity.states;
                        if (i2 < stateArr.length) {
                            State state = stateArr[i2];
                            hashMap.put(Integer.valueOf(state.id), state.name);
                            i2++;
                        }
                    }
                    ((HashMap) statsPullAtomCallbackImpl.mEntityNames).put(Integer.valueOf(powerEntity.id), powerEntity.name);
                    ((HashMap) statsPullAtomCallbackImpl.mStateNames).put(Integer.valueOf(powerEntity.id), hashMap);
                }
                statsManager.setPullAtomCallback(FrameworkStatsLog.SUBSYSTEM_SLEEP_STATE, (StatsManager.PullAtomMetadata) null, ConcurrentUtils.DIRECT_EXECUTOR, statsPullAtomCallbackImpl);
            }
        }
        DeviceConfigListener deviceConfigListener = this.mDeviceConfigListener;
        PowerStatsService.this.mDeviceConfig.addOnPropertiesChangedListener("battery_stats", deviceConfigListener.mExecutor, deviceConfigListener);
        boolean z = this.mDeviceConfig.getBoolean("battery_stats", "power_monitor_api_enabled", true);
        if (z != this.mPowerMonitorApiEnabled) {
            this.mPowerMonitorApiEnabled = z;
            this.mPowerMonitors = null;
            this.mPowerMonitorStates = null;
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        if (getPowerStatsHal().isInitialized()) {
            LocalService localService = new LocalService();
            this.mPowerStatsInternal = localService;
            publishLocalService(LocalService.class, localService);
        }
        publishBinderService("powerstats", this.mService);
    }
}
