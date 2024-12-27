package com.android.server.powerstats;

import android.hardware.power.stats.Channel;
import android.hardware.power.stats.EnergyConsumer;
import android.hardware.power.stats.EnergyConsumerResult;
import android.hardware.power.stats.EnergyMeasurement;
import android.hardware.power.stats.PowerEntity;
import android.hardware.power.stats.StateResidencyResult;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PowerStatsLogger extends Handler {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final File mDataStoragePath;
    public final boolean mDeleteMeterDataOnBoot;
    public final boolean mDeleteModelDataOnBoot;
    public final boolean mDeleteResidencyDataOnBoot;
    public final PowerStatsHALWrapper$IPowerStatsHALWrapper mPowerStatsHALWrapper;
    public final PowerStatsDataStorage mPowerStatsMeterStorage;
    public final PowerStatsDataStorage mPowerStatsModelStorage;
    public final PowerStatsDataStorage mPowerStatsResidencyStorage;
    public final long mStartWallTime;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.powerstats.PowerStatsLogger$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ ProtoOutputStream val$pos;

        public /* synthetic */ AnonymousClass1(int i, ProtoOutputStream protoOutputStream) {
            this.$r8$classId = i;
            this.val$pos = protoOutputStream;
        }
    }

    public PowerStatsLogger(Looper looper, File file, PowerStatsHALWrapper$IPowerStatsHALWrapper powerStatsHALWrapper$IPowerStatsHALWrapper) {
        super(looper);
        this.mStartWallTime = System.currentTimeMillis() - SystemClock.elapsedRealtime();
        this.mPowerStatsHALWrapper = powerStatsHALWrapper$IPowerStatsHALWrapper;
        this.mDataStoragePath = file;
        PowerStatsDataStorage powerStatsDataStorage = new PowerStatsDataStorage(file, "log.powerstats.meter.0");
        this.mPowerStatsMeterStorage = powerStatsDataStorage;
        PowerStatsDataStorage powerStatsDataStorage2 = new PowerStatsDataStorage(file, "log.powerstats.model.0");
        this.mPowerStatsModelStorage = powerStatsDataStorage2;
        PowerStatsDataStorage powerStatsDataStorage3 = new PowerStatsDataStorage(file, "log.powerstats.residency.0");
        this.mPowerStatsResidencyStorage = powerStatsDataStorage3;
        Channel[] energyMeterInfo = powerStatsHALWrapper$IPowerStatsHALWrapper.getEnergyMeterInfo();
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        ProtoStreamUtils$ChannelUtils.packProtoMessage(energyMeterInfo, protoOutputStream);
        byte[] bytes = protoOutputStream.getBytes();
        boolean dataChanged = dataChanged("meterCache", bytes);
        this.mDeleteMeterDataOnBoot = dataChanged;
        if (dataChanged) {
            powerStatsDataStorage.deleteLogs();
            updateCacheFile("meterCache", bytes);
        }
        EnergyConsumer[] energyConsumerInfo = powerStatsHALWrapper$IPowerStatsHALWrapper.getEnergyConsumerInfo();
        ProtoOutputStream protoOutputStream2 = new ProtoOutputStream();
        ProtoStreamUtils$ChannelUtils.packProtoMessage(energyConsumerInfo, protoOutputStream2);
        byte[] bytes2 = protoOutputStream2.getBytes();
        boolean dataChanged2 = dataChanged("modelCache", bytes2);
        this.mDeleteModelDataOnBoot = dataChanged2;
        if (dataChanged2) {
            powerStatsDataStorage2.deleteLogs();
            updateCacheFile("modelCache", bytes2);
        }
        PowerEntity[] powerEntityInfo = powerStatsHALWrapper$IPowerStatsHALWrapper.getPowerEntityInfo();
        ProtoOutputStream protoOutputStream3 = new ProtoOutputStream();
        ProtoStreamUtils$ChannelUtils.packProtoMessage(powerEntityInfo, protoOutputStream3);
        byte[] bytes3 = protoOutputStream3.getBytes();
        boolean dataChanged3 = dataChanged("residencyCache", bytes3);
        this.mDeleteResidencyDataOnBoot = dataChanged3;
        if (dataChanged3) {
            powerStatsDataStorage3.deleteLogs();
            updateCacheFile("residencyCache", bytes3);
        }
    }

    public final boolean dataChanged(String str, byte[] bArr) {
        if (!this.mDataStoragePath.exists() && !this.mDataStoragePath.mkdirs()) {
            return false;
        }
        File file = new File(this.mDataStoragePath, str);
        if (!file.exists()) {
            return true;
        }
        try {
            new FileInputStream(file.getPath()).read(new byte[(int) file.length()]);
        } catch (IOException e) {
            Slog.e("PowerStatsLogger", "Failed to read cached data from file", e);
        }
        return !Arrays.equals(r3, bArr);
    }

    public long getStartWallTime() {
        return this.mStartWallTime;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        int i = message.what;
        long j = this.mStartWallTime;
        PowerStatsHALWrapper$IPowerStatsHALWrapper powerStatsHALWrapper$IPowerStatsHALWrapper = this.mPowerStatsHALWrapper;
        if (i == 0) {
            StateResidencyResult[] stateResidency = powerStatsHALWrapper$IPowerStatsHALWrapper.getStateResidency(new int[0]);
            if (stateResidency != null) {
                for (int i2 = 0; i2 < stateResidency.length; i2++) {
                    int length = stateResidency[i2].stateResidencyData.length;
                    for (int i3 = 0; i3 < length; i3++) {
                        stateResidency[i2].stateResidencyData[i3].lastEntryTimestampMs += j;
                    }
                }
            }
            ProtoOutputStream protoOutputStream = new ProtoOutputStream();
            ProtoStreamUtils$ChannelUtils.packProtoMessage(stateResidency, protoOutputStream);
            this.mPowerStatsResidencyStorage.write(protoOutputStream.getBytes());
            return;
        }
        PowerStatsDataStorage powerStatsDataStorage = this.mPowerStatsModelStorage;
        if (i == 1) {
            EnergyConsumerResult[] energyConsumed = powerStatsHALWrapper$IPowerStatsHALWrapper.getEnergyConsumed(new int[0]);
            if (energyConsumed != null) {
                for (EnergyConsumerResult energyConsumerResult : energyConsumed) {
                    energyConsumerResult.timestampMs += j;
                }
            }
            ProtoOutputStream protoOutputStream2 = new ProtoOutputStream();
            ProtoStreamUtils$ChannelUtils.packProtoMessage(energyConsumed, protoOutputStream2, true);
            powerStatsDataStorage.write(protoOutputStream2.getBytes());
            return;
        }
        if (i != 2) {
            return;
        }
        EnergyMeasurement[] readEnergyMeter = powerStatsHALWrapper$IPowerStatsHALWrapper.readEnergyMeter(new int[0]);
        if (readEnergyMeter != null) {
            for (EnergyMeasurement energyMeasurement : readEnergyMeter) {
                energyMeasurement.timestampMs += j;
            }
        }
        ProtoOutputStream protoOutputStream3 = new ProtoOutputStream();
        ProtoStreamUtils$ChannelUtils.packProtoMessage(readEnergyMeter, protoOutputStream3);
        this.mPowerStatsMeterStorage.write(protoOutputStream3.getBytes());
        EnergyConsumerResult[] energyConsumed2 = powerStatsHALWrapper$IPowerStatsHALWrapper.getEnergyConsumed(new int[0]);
        if (energyConsumed2 != null) {
            for (EnergyConsumerResult energyConsumerResult2 : energyConsumed2) {
                energyConsumerResult2.timestampMs += j;
            }
        }
        ProtoOutputStream protoOutputStream4 = new ProtoOutputStream();
        ProtoStreamUtils$ChannelUtils.packProtoMessage(energyConsumed2, protoOutputStream4, false);
        powerStatsDataStorage.write(protoOutputStream4.getBytes());
    }

    public final void updateCacheFile(String str, byte[] bArr) {
        try {
            AtomicFile atomicFile = new AtomicFile(new File(this.mDataStoragePath, str));
            FileOutputStream startWrite = atomicFile.startWrite();
            startWrite.write(bArr);
            atomicFile.finishWrite(startWrite);
        } catch (IOException e) {
            Slog.e("PowerStatsLogger", "Failed to write current data to cached file", e);
        }
    }
}
