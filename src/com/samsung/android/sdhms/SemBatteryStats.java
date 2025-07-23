package com.samsung.android.sdhms;

import android.hardware.scontext.SContextConstants;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class SemBatteryStats implements Parcelable {
    public static final Parcelable.Creator<SemBatteryStats> CREATOR = new Parcelable.Creator<SemBatteryStats>() { // from class: com.samsung.android.sdhms.SemBatteryStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemBatteryStats createFromParcel(Parcel parcel) {
            return new SemBatteryStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemBatteryStats[] newArray(int i) {
            return new SemBatteryStats[i];
        }
    };
    private List<AppDetailUsage> appDetailList;
    private long beginTime;
    private long endTime;
    private long highBrightnessTime;
    private long highRefreshRateTime;
    private int screenOffDischarge;
    private long screenOffTime;
    private int screenOnCount;
    private int screenOnDischarge;
    private long screenOnTime;
    private double screenPowerUsage;
    private List<SysDetailUsage> sysDetailList;
    private double totalPowerUsage;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemBatteryStats(long j, long j2, double d, double d2, long j3, long j4, int i, int i2, List<AppDetailUsage> list, List<SysDetailUsage> list2) {
        this.beginTime = j;
        this.endTime = j2;
        this.totalPowerUsage = d;
        this.screenPowerUsage = d2;
        this.screenOnTime = j3;
        this.screenOffTime = j4;
        this.screenOnDischarge = i;
        this.screenOffDischarge = i2;
        this.screenOnCount = 0;
        this.highBrightnessTime = 0L;
        this.highRefreshRateTime = 0L;
        this.appDetailList = new ArrayList();
        if (list != null) {
            Iterator<AppDetailUsage> it = list.iterator();
            while (it.hasNext()) {
                this.appDetailList.add(new AppDetailUsage(it.next()));
            }
        }
        this.sysDetailList = new ArrayList();
        if (list2 != null) {
            Iterator<SysDetailUsage> it2 = list2.iterator();
            while (it2.hasNext()) {
                this.sysDetailList.add(new SysDetailUsage(it2.next()));
            }
        }
    }

    public SemBatteryStats(long j, long j2, double d, double d2, long j3, long j4, int i, int i2, int i3, long j5, long j6, List<AppDetailUsage> list, List<SysDetailUsage> list2) {
        this(j, j2, d, d2, j3, j4, i, i2, list, list2);
        this.screenOnCount = i3;
        this.highBrightnessTime = j5;
        this.highRefreshRateTime = j6;
    }

    public SemBatteryStats(long j, long j2) {
        this.beginTime = j;
        this.endTime = j2;
        this.totalPowerUsage = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.screenPowerUsage = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.screenOnTime = 0L;
        this.screenOffTime = 0L;
        this.screenOnDischarge = 0;
        this.screenOffDischarge = 0;
        this.screenOnCount = 0;
        this.highBrightnessTime = 0L;
        this.highRefreshRateTime = 0L;
        this.appDetailList = new ArrayList();
        this.sysDetailList = new ArrayList();
    }

    public long getStartTimestamp() {
        return this.beginTime;
    }

    public long getEndTimestamp() {
        return this.endTime;
    }

    public double getTotalPowerUsage() {
        return this.totalPowerUsage;
    }

    public double getScreenPowerUsage() {
        return this.screenPowerUsage;
    }

    public long getScreenOnTime() {
        return this.screenOnTime;
    }

    public long getScreenOffTime() {
        return this.screenOffTime;
    }

    public int getScreenOnDischarge() {
        return this.screenOnDischarge;
    }

    public int getScreenOffDischarge() {
        return this.screenOffDischarge;
    }

    public int getScreenOnCount() {
        return this.screenOnCount;
    }

    public long getHighBrightnessTime() {
        return this.highBrightnessTime;
    }

    public long getHighRefreshRateTime() {
        return this.highRefreshRateTime;
    }

    public List<AppDetailUsage> getAppDetailUsages() {
        return this.appDetailList;
    }

    public List<SysDetailUsage> getSysDetailUsages() {
        return this.sysDetailList;
    }

    public static final class Builder {
        private List<AppDetailUsage> appDetailList;
        private long beginTime;
        private long endTime;
        private long highBrightnessTime;
        private long highRefreshRateTime;
        private int screenOffDischarge;
        private long screenOffTime;
        private int screenOnCount;
        private int screenOnDischarge;
        private long screenOnTime;
        private double screenPowerUsage;
        private List<SysDetailUsage> sysDetailList;
        private double totalPowerUsage;

        public Builder beginTime(long j) {
            this.beginTime = j;
            return this;
        }

        public Builder endTime(long j) {
            this.endTime = j;
            return this;
        }

        public Builder totalPowerUsage(double d) {
            this.totalPowerUsage = d;
            return this;
        }

        public Builder screenPowerUsage(double d) {
            this.screenPowerUsage = d;
            return this;
        }

        public Builder screenOnTime(long j) {
            this.screenOnTime = j;
            return this;
        }

        public Builder screenOffTime(long j) {
            this.screenOffTime = j;
            return this;
        }

        public Builder screenOnDischarge(int i) {
            this.screenOnDischarge = i;
            return this;
        }

        public Builder screenOffDischarge(int i) {
            this.screenOffDischarge = i;
            return this;
        }

        public Builder screenOnCount(int i) {
            this.screenOnCount = i;
            return this;
        }

        public Builder highBrightnessTime(long j) {
            this.highBrightnessTime = j;
            return this;
        }

        public Builder highRefreshRateTime(long j) {
            this.highRefreshRateTime = j;
            return this;
        }

        public Builder appDetailList(List<AppDetailUsage> list) {
            if (this.appDetailList == null) {
                this.appDetailList = new ArrayList();
            }
            if (list == null) {
                return this;
            }
            this.appDetailList.addAll(list);
            return this;
        }

        public Builder sysDetailList(List<SysDetailUsage> list) {
            if (this.sysDetailList == null) {
                this.sysDetailList = new ArrayList();
            }
            if (list == null) {
                return this;
            }
            this.sysDetailList.addAll(list);
            return this;
        }

        public SemBatteryStats build() {
            return new SemBatteryStats(this);
        }
    }

    public SemBatteryStats(Builder builder) {
        this.beginTime = builder.beginTime;
        this.endTime = builder.endTime;
        this.totalPowerUsage = builder.totalPowerUsage;
        this.screenPowerUsage = builder.screenPowerUsage;
        this.screenOnTime = builder.screenOnTime;
        this.screenOffTime = builder.screenOffTime;
        this.screenOnDischarge = builder.screenOnDischarge;
        this.screenOffDischarge = builder.screenOffDischarge;
        this.screenOnCount = builder.screenOnCount;
        this.highBrightnessTime = builder.highBrightnessTime;
        this.highRefreshRateTime = builder.highRefreshRateTime;
        this.appDetailList = builder.appDetailList;
        this.sysDetailList = builder.sysDetailList;
    }

    public void calculateDeviceUsageDelta(SemBatteryStats semBatteryStats) {
        this.totalPowerUsage = Math.max(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, this.totalPowerUsage - semBatteryStats.getTotalPowerUsage());
        this.screenPowerUsage = Math.max(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, this.screenPowerUsage - semBatteryStats.getScreenPowerUsage());
        this.screenOnTime = Math.max(0L, this.screenOnTime - semBatteryStats.getScreenOnTime());
        this.screenOffTime = Math.max(0L, this.screenOffTime - semBatteryStats.getScreenOffTime());
        this.screenOnDischarge = Math.max(0, this.screenOnDischarge - semBatteryStats.getScreenOnDischarge());
        this.screenOffDischarge = Math.max(0, this.screenOffDischarge - semBatteryStats.getScreenOffDischarge());
        this.screenOnCount = Math.max(0, this.screenOnCount - semBatteryStats.screenOnCount);
        this.highBrightnessTime = Math.max(0L, this.highBrightnessTime - semBatteryStats.highBrightnessTime);
        this.highRefreshRateTime = Math.max(0L, this.highRefreshRateTime - semBatteryStats.highRefreshRateTime);
    }

    public void shiftTimestamp(long j) {
        this.beginTime += j;
        this.endTime += j;
    }

    public void updateAppUsage(List<AppDetailUsage> list) {
        if (list == null) {
            return;
        }
        if (this.appDetailList == null) {
            this.appDetailList = new ArrayList();
        }
        this.appDetailList.clear();
        this.appDetailList.addAll(list);
    }

    public void updateSysUsage(List<SysDetailUsage> list) {
        if (list == null) {
            return;
        }
        if (this.sysDetailList == null) {
            this.sysDetailList = new ArrayList();
        }
        this.sysDetailList.clear();
        this.sysDetailList.addAll(list);
    }

    protected SemBatteryStats(Parcel parcel) {
        this.beginTime = parcel.readLong();
        this.endTime = parcel.readLong();
        this.totalPowerUsage = parcel.readDouble();
        this.screenPowerUsage = parcel.readDouble();
        this.screenOnTime = parcel.readLong();
        this.screenOffTime = parcel.readLong();
        this.screenOnDischarge = parcel.readInt();
        this.screenOffDischarge = parcel.readInt();
        this.screenOnCount = parcel.readInt();
        this.highBrightnessTime = parcel.readLong();
        this.highRefreshRateTime = parcel.readLong();
        this.appDetailList = parcel.createTypedArrayList(AppDetailUsage.CREATOR);
        this.sysDetailList = parcel.createTypedArrayList(SysDetailUsage.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.beginTime);
        parcel.writeLong(this.endTime);
        parcel.writeDouble(this.totalPowerUsage);
        parcel.writeDouble(this.screenPowerUsage);
        parcel.writeLong(this.screenOnTime);
        parcel.writeLong(this.screenOffTime);
        parcel.writeInt(this.screenOnDischarge);
        parcel.writeInt(this.screenOffDischarge);
        parcel.writeInt(this.screenOnCount);
        parcel.writeLong(this.highBrightnessTime);
        parcel.writeLong(this.highRefreshRateTime);
        parcel.writeTypedList(this.appDetailList);
        parcel.writeTypedList(this.sysDetailList);
    }

    public static class AppDetailUsage implements Parcelable {
        public static final Parcelable.Creator<AppDetailUsage> CREATOR = new Parcelable.Creator<AppDetailUsage>() { // from class: com.samsung.android.sdhms.SemBatteryStats.AppDetailUsage.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AppDetailUsage createFromParcel(Parcel parcel) {
                return new AppDetailUsage(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AppDetailUsage[] newArray(int i) {
                return new AppDetailUsage[i];
            }
        };
        private long audioTime;
        private long bgTime;
        private int bluetoothScanCount;
        private long cpuTime;
        private long fgTime;
        private long gpsTime;
        private long mobilePackets;
        private long mobileRadioActiveTime;
        private double power;
        private double screenPower;
        private final int uid;
        private long wakelockTime;
        private int walarm;
        private long wifiPackets;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public AppDetailUsage(int i) {
            this.uid = i;
            this.power = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            this.screenPower = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            this.fgTime = 0L;
            this.bgTime = 0L;
            this.cpuTime = 0L;
            this.wakelockTime = 0L;
            this.mobilePackets = 0L;
            this.wifiPackets = 0L;
            this.walarm = 0;
            this.gpsTime = 0L;
            this.audioTime = 0L;
            this.mobileRadioActiveTime = 0L;
            this.bluetoothScanCount = 0;
        }

        public AppDetailUsage(AppDetailUsage appDetailUsage) {
            this.uid = appDetailUsage.getUid();
            this.power = appDetailUsage.getPowerUsage();
            this.screenPower = appDetailUsage.getScreenPowerUsage();
            this.fgTime = appDetailUsage.getForegroundTime();
            this.bgTime = appDetailUsage.getBackgroundTime();
            this.cpuTime = appDetailUsage.getCpuTime();
            this.wakelockTime = appDetailUsage.getWakelockTime();
            this.mobilePackets = appDetailUsage.getMobileDataUsage();
            this.wifiPackets = appDetailUsage.getWifiDataUsage();
            this.walarm = appDetailUsage.getWakeAlarmCount();
            this.gpsTime = appDetailUsage.getGpsTime();
            this.audioTime = appDetailUsage.getAudioTime();
            this.mobileRadioActiveTime = appDetailUsage.getMobileRadioActiveTime();
            this.bluetoothScanCount = appDetailUsage.getBluetoothScanCount();
        }

        public int getUid() {
            return this.uid;
        }

        public double getPowerUsage() {
            return this.power;
        }

        public double getScreenPowerUsage() {
            return this.screenPower;
        }

        public long getForegroundTime() {
            return this.fgTime;
        }

        public long getBackgroundTime() {
            return this.bgTime;
        }

        public long getCpuTime() {
            return this.cpuTime;
        }

        public long getWakelockTime() {
            return this.wakelockTime;
        }

        public long getMobileDataUsage() {
            return this.mobilePackets;
        }

        public long getWifiDataUsage() {
            return this.wifiPackets;
        }

        public int getWakeAlarmCount() {
            return this.walarm;
        }

        public long getGpsTime() {
            return this.gpsTime;
        }

        public long getAudioTime() {
            return this.audioTime;
        }

        public long getMobileRadioActiveTime() {
            return this.mobileRadioActiveTime;
        }

        public int getBluetoothScanCount() {
            return this.bluetoothScanCount;
        }

        public static final class Builder {
            private long audioTime;
            private long bgTime;
            private int bluetoothScanCount;
            private long cpuTime;
            private long fgTime;
            private long gpsTime;
            private long mobilePackets;
            private long mobileRadioActiveTime;
            private double power;
            private double screenPower;
            private int uid;
            private long wakelockTime;
            private int walarm;
            private long wifiPackets;

            public Builder uid(int i) {
                this.uid = i;
                return this;
            }

            public Builder power(double d) {
                this.power = d;
                return this;
            }

            public Builder screenPower(double d) {
                this.screenPower = d;
                return this;
            }

            public Builder fgTime(long j) {
                this.fgTime = j;
                return this;
            }

            public Builder bgTime(long j) {
                this.bgTime = j;
                return this;
            }

            public Builder cpuTime(long j) {
                this.cpuTime = j;
                return this;
            }

            public Builder wakelockTime(long j) {
                this.wakelockTime = j;
                return this;
            }

            public Builder mobilePackets(long j) {
                this.mobilePackets = j;
                return this;
            }

            public Builder wifiPackets(long j) {
                this.wifiPackets = j;
                return this;
            }

            public Builder walarm(int i) {
                this.walarm = i;
                return this;
            }

            public Builder gpsTime(long j) {
                this.gpsTime = j;
                return this;
            }

            public Builder audioTime(long j) {
                this.audioTime = j;
                return this;
            }

            public Builder mobileRadioActiveTime(long j) {
                this.mobileRadioActiveTime = j;
                return this;
            }

            public Builder bluetoothScanCount(int i) {
                this.bluetoothScanCount = i;
                return this;
            }

            public AppDetailUsage build() {
                return new AppDetailUsage(this);
            }
        }

        public AppDetailUsage(Builder builder) {
            this.uid = builder.uid;
            this.power = builder.power;
            this.screenPower = builder.screenPower;
            this.fgTime = builder.fgTime;
            this.bgTime = builder.bgTime;
            this.cpuTime = builder.cpuTime;
            this.wakelockTime = builder.wakelockTime;
            this.mobilePackets = builder.mobilePackets;
            this.wifiPackets = builder.wifiPackets;
            this.walarm = builder.walarm;
            this.gpsTime = builder.gpsTime;
            this.audioTime = builder.audioTime;
            this.mobileRadioActiveTime = builder.mobileRadioActiveTime;
            this.bluetoothScanCount = builder.bluetoothScanCount;
        }

        public void addAppUsage(AppDetailUsage appDetailUsage) {
            if (this.uid != appDetailUsage.getUid()) {
                return;
            }
            this.power += appDetailUsage.getPowerUsage();
            this.screenPower += appDetailUsage.getScreenPowerUsage();
            this.fgTime += appDetailUsage.getForegroundTime();
            this.bgTime += appDetailUsage.getBackgroundTime();
            this.cpuTime += appDetailUsage.getCpuTime();
            this.wakelockTime += appDetailUsage.getWakelockTime();
            this.mobilePackets += appDetailUsage.getMobileDataUsage();
            this.wifiPackets += appDetailUsage.getWifiDataUsage();
            this.walarm += appDetailUsage.getWakeAlarmCount();
            this.gpsTime += appDetailUsage.getGpsTime();
            this.audioTime += appDetailUsage.getAudioTime();
            this.mobileRadioActiveTime += appDetailUsage.getMobileRadioActiveTime();
            this.bluetoothScanCount += appDetailUsage.getBluetoothScanCount();
        }

        public void calculateDelta(AppDetailUsage appDetailUsage) {
            if (this.uid != appDetailUsage.getUid()) {
                return;
            }
            this.power = Math.max(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, this.power - appDetailUsage.getPowerUsage());
            this.screenPower = Math.max(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, this.screenPower - appDetailUsage.getScreenPowerUsage());
            this.fgTime = Math.max(0L, this.fgTime - appDetailUsage.getForegroundTime());
            this.bgTime = Math.max(0L, this.bgTime - appDetailUsage.getBackgroundTime());
            this.cpuTime = Math.max(0L, this.cpuTime - appDetailUsage.getCpuTime());
            this.wakelockTime = Math.max(0L, this.wakelockTime - appDetailUsage.getWakelockTime());
            this.mobilePackets = Math.max(0L, this.mobilePackets - appDetailUsage.getMobileDataUsage());
            this.wifiPackets = Math.max(0L, this.wifiPackets - appDetailUsage.getWifiDataUsage());
            this.walarm = Math.max(0, this.walarm - appDetailUsage.getWakeAlarmCount());
            this.gpsTime = Math.max(0L, this.gpsTime - appDetailUsage.getGpsTime());
            this.audioTime = Math.max(0L, this.audioTime - appDetailUsage.getAudioTime());
            this.mobileRadioActiveTime = Math.max(0L, this.mobileRadioActiveTime - appDetailUsage.getMobileRadioActiveTime());
            this.bluetoothScanCount = Math.max(0, this.bluetoothScanCount - appDetailUsage.getBluetoothScanCount());
        }

        protected AppDetailUsage(Parcel parcel) {
            this.uid = parcel.readInt();
            this.power = parcel.readDouble();
            this.screenPower = parcel.readDouble();
            this.fgTime = parcel.readLong();
            this.bgTime = parcel.readLong();
            this.cpuTime = parcel.readLong();
            this.wakelockTime = parcel.readLong();
            this.mobilePackets = parcel.readLong();
            this.wifiPackets = parcel.readLong();
            this.walarm = parcel.readInt();
            this.gpsTime = parcel.readLong();
            this.audioTime = parcel.readLong();
            this.mobileRadioActiveTime = parcel.readLong();
            this.bluetoothScanCount = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.uid);
            parcel.writeDouble(this.power);
            parcel.writeDouble(this.screenPower);
            parcel.writeLong(this.fgTime);
            parcel.writeLong(this.bgTime);
            parcel.writeLong(this.cpuTime);
            parcel.writeLong(this.wakelockTime);
            parcel.writeLong(this.mobilePackets);
            parcel.writeLong(this.wifiPackets);
            parcel.writeInt(this.walarm);
            parcel.writeLong(this.gpsTime);
            parcel.writeLong(this.audioTime);
            parcel.writeLong(this.mobileRadioActiveTime);
            parcel.writeInt(this.bluetoothScanCount);
        }
    }

    public static class SysDetailUsage implements Parcelable {
        public static final Parcelable.Creator<SysDetailUsage> CREATOR = new Parcelable.Creator<SysDetailUsage>() { // from class: com.samsung.android.sdhms.SemBatteryStats.SysDetailUsage.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SysDetailUsage createFromParcel(Parcel parcel) {
                return new SysDetailUsage(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SysDetailUsage[] newArray(int i) {
                return new SysDetailUsage[i];
            }
        };
        private final int drainType;
        private double power;
        private long usedTime;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public SysDetailUsage(int i) {
            this.drainType = i;
            this.power = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            this.usedTime = 0L;
        }

        public SysDetailUsage(SysDetailUsage sysDetailUsage) {
            this.drainType = sysDetailUsage.getDrainType();
            this.power = sysDetailUsage.getPowerUsage();
            this.usedTime = sysDetailUsage.getUsedTime();
        }

        public int getDrainType() {
            return this.drainType;
        }

        public double getPowerUsage() {
            return this.power;
        }

        public long getUsedTime() {
            return this.usedTime;
        }

        public static final class Builder {
            private int drainType;
            private double power;
            private long usedTime;

            public Builder drainType(int i) {
                this.drainType = i;
                return this;
            }

            public Builder power(double d) {
                this.power = d;
                return this;
            }

            public Builder usedTime(long j) {
                this.usedTime = j;
                return this;
            }

            public SysDetailUsage build() {
                return new SysDetailUsage(this);
            }
        }

        public SysDetailUsage(Builder builder) {
            this.drainType = builder.drainType;
            this.power = builder.power;
            this.usedTime = builder.usedTime;
        }

        public void addSysUsage(SysDetailUsage sysDetailUsage) {
            if (this.drainType != sysDetailUsage.getDrainType()) {
                return;
            }
            this.power += sysDetailUsage.getPowerUsage();
            this.usedTime += sysDetailUsage.getUsedTime();
        }

        public void calculateDelta(SysDetailUsage sysDetailUsage) {
            if (this.drainType != sysDetailUsage.getDrainType()) {
                return;
            }
            this.power = Math.max(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, this.power - sysDetailUsage.getPowerUsage());
            this.usedTime = Math.max(0L, this.usedTime - sysDetailUsage.getUsedTime());
        }

        protected SysDetailUsage(Parcel parcel) {
            this.drainType = parcel.readInt();
            this.power = parcel.readDouble();
            this.usedTime = parcel.readLong();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.drainType);
            parcel.writeDouble(this.power);
            parcel.writeLong(this.usedTime);
        }
    }
}
