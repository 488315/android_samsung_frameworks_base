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
        public SemBatteryStats createFromParcel(Parcel in) {
            return new SemBatteryStats(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemBatteryStats[] newArray(int size) {
            return new SemBatteryStats[size];
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

    public SemBatteryStats(long begin, long end, double totalPowerUsage, double screenPowerUsage, long screenOnTime, long screenOffTime, int screenOnDischarge, int screenOffDischarge, List<AppDetailUsage> appDetailList, List<SysDetailUsage> sysDetailList) {
        SemBatteryStats semBatteryStats = this;
        semBatteryStats.beginTime = begin;
        semBatteryStats.endTime = end;
        semBatteryStats.totalPowerUsage = totalPowerUsage;
        semBatteryStats.screenPowerUsage = screenPowerUsage;
        semBatteryStats.screenOnTime = screenOnTime;
        semBatteryStats.screenOffTime = screenOffTime;
        semBatteryStats.screenOnDischarge = screenOnDischarge;
        semBatteryStats.screenOffDischarge = screenOffDischarge;
        semBatteryStats.screenOnCount = 0;
        semBatteryStats.highBrightnessTime = 0L;
        semBatteryStats.highRefreshRateTime = 0L;
        semBatteryStats.appDetailList = new ArrayList();
        if (appDetailList != null) {
            for (Iterator<AppDetailUsage> it = appDetailList.iterator(); it.hasNext(); it = it) {
                AppDetailUsage copy = it.next();
                semBatteryStats.appDetailList.add(new AppDetailUsage(copy));
            }
        }
        semBatteryStats.sysDetailList = new ArrayList();
        if (sysDetailList != null) {
            for (SysDetailUsage copy2 : sysDetailList) {
                semBatteryStats.sysDetailList.add(new SysDetailUsage(copy2));
                semBatteryStats = this;
            }
        }
    }

    public SemBatteryStats(long begin, long end, double totalPowerUsage, double screenPowerUsage, long screenOnTime, long screenOffTime, int screenOnDischarge, int screenOffDischarge, int screenOnCount, long highBrightnessTime, long highRefreshRateTime, List<AppDetailUsage> appDetailList, List<SysDetailUsage> sysDetailList) {
        this(begin, end, totalPowerUsage, screenPowerUsage, screenOnTime, screenOffTime, screenOnDischarge, screenOffDischarge, appDetailList, sysDetailList);
        this.screenOnCount = screenOnCount;
        this.highBrightnessTime = highBrightnessTime;
        this.highRefreshRateTime = highRefreshRateTime;
    }

    public SemBatteryStats(long begin, long end) {
        this.beginTime = begin;
        this.endTime = end;
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

        public Builder beginTime(long value) {
            this.beginTime = value;
            return this;
        }

        public Builder endTime(long value) {
            this.endTime = value;
            return this;
        }

        public Builder totalPowerUsage(double value) {
            this.totalPowerUsage = value;
            return this;
        }

        public Builder screenPowerUsage(double value) {
            this.screenPowerUsage = value;
            return this;
        }

        public Builder screenOnTime(long value) {
            this.screenOnTime = value;
            return this;
        }

        public Builder screenOffTime(long value) {
            this.screenOffTime = value;
            return this;
        }

        public Builder screenOnDischarge(int value) {
            this.screenOnDischarge = value;
            return this;
        }

        public Builder screenOffDischarge(int value) {
            this.screenOffDischarge = value;
            return this;
        }

        public Builder screenOnCount(int value) {
            this.screenOnCount = value;
            return this;
        }

        public Builder highBrightnessTime(long value) {
            this.highBrightnessTime = value;
            return this;
        }

        public Builder highRefreshRateTime(long value) {
            this.highRefreshRateTime = value;
            return this;
        }

        public Builder appDetailList(List<AppDetailUsage> value) {
            if (this.appDetailList == null) {
                this.appDetailList = new ArrayList();
            }
            if (value == null) {
                return this;
            }
            this.appDetailList.addAll(value);
            return this;
        }

        public Builder sysDetailList(List<SysDetailUsage> value) {
            if (this.sysDetailList == null) {
                this.sysDetailList = new ArrayList();
            }
            if (value == null) {
                return this;
            }
            this.sysDetailList.addAll(value);
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

    public void calculateDeviceUsageDelta(SemBatteryStats prev) {
        this.totalPowerUsage = Math.max(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, this.totalPowerUsage - prev.getTotalPowerUsage());
        this.screenPowerUsage = Math.max(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, this.screenPowerUsage - prev.getScreenPowerUsage());
        this.screenOnTime = Math.max(0L, this.screenOnTime - prev.getScreenOnTime());
        this.screenOffTime = Math.max(0L, this.screenOffTime - prev.getScreenOffTime());
        this.screenOnDischarge = Math.max(0, this.screenOnDischarge - prev.getScreenOnDischarge());
        this.screenOffDischarge = Math.max(0, this.screenOffDischarge - prev.getScreenOffDischarge());
        this.screenOnCount = Math.max(0, this.screenOnCount - prev.screenOnCount);
        this.highBrightnessTime = Math.max(0L, this.highBrightnessTime - prev.highBrightnessTime);
        this.highRefreshRateTime = Math.max(0L, this.highRefreshRateTime - prev.highRefreshRateTime);
    }

    public void shiftTimestamp(long time) {
        this.beginTime += time;
        this.endTime += time;
    }

    public void updateAppUsage(List<AppDetailUsage> usageList) {
        if (usageList == null) {
            return;
        }
        if (this.appDetailList == null) {
            this.appDetailList = new ArrayList();
        }
        this.appDetailList.clear();
        this.appDetailList.addAll(usageList);
    }

    public void updateSysUsage(List<SysDetailUsage> usageList) {
        if (usageList == null) {
            return;
        }
        if (this.sysDetailList == null) {
            this.sysDetailList = new ArrayList();
        }
        this.sysDetailList.clear();
        this.sysDetailList.addAll(usageList);
    }

    protected SemBatteryStats(Parcel in) {
        this.beginTime = in.readLong();
        this.endTime = in.readLong();
        this.totalPowerUsage = in.readDouble();
        this.screenPowerUsage = in.readDouble();
        this.screenOnTime = in.readLong();
        this.screenOffTime = in.readLong();
        this.screenOnDischarge = in.readInt();
        this.screenOffDischarge = in.readInt();
        this.screenOnCount = in.readInt();
        this.highBrightnessTime = in.readLong();
        this.highRefreshRateTime = in.readLong();
        this.appDetailList = in.createTypedArrayList(AppDetailUsage.CREATOR);
        this.sysDetailList = in.createTypedArrayList(SysDetailUsage.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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
            public AppDetailUsage createFromParcel(Parcel in) {
                return new AppDetailUsage(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AppDetailUsage[] newArray(int size) {
                return new AppDetailUsage[size];
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

        public AppDetailUsage(int uid) {
            this.uid = uid;
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

        public AppDetailUsage(AppDetailUsage copy) {
            this.uid = copy.getUid();
            this.power = copy.getPowerUsage();
            this.screenPower = copy.getScreenPowerUsage();
            this.fgTime = copy.getForegroundTime();
            this.bgTime = copy.getBackgroundTime();
            this.cpuTime = copy.getCpuTime();
            this.wakelockTime = copy.getWakelockTime();
            this.mobilePackets = copy.getMobileDataUsage();
            this.wifiPackets = copy.getWifiDataUsage();
            this.walarm = copy.getWakeAlarmCount();
            this.gpsTime = copy.getGpsTime();
            this.audioTime = copy.getAudioTime();
            this.mobileRadioActiveTime = copy.getMobileRadioActiveTime();
            this.bluetoothScanCount = copy.getBluetoothScanCount();
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

            public Builder uid(int value) {
                this.uid = value;
                return this;
            }

            public Builder power(double value) {
                this.power = value;
                return this;
            }

            public Builder screenPower(double value) {
                this.screenPower = value;
                return this;
            }

            public Builder fgTime(long value) {
                this.fgTime = value;
                return this;
            }

            public Builder bgTime(long value) {
                this.bgTime = value;
                return this;
            }

            public Builder cpuTime(long value) {
                this.cpuTime = value;
                return this;
            }

            public Builder wakelockTime(long value) {
                this.wakelockTime = value;
                return this;
            }

            public Builder mobilePackets(long value) {
                this.mobilePackets = value;
                return this;
            }

            public Builder wifiPackets(long value) {
                this.wifiPackets = value;
                return this;
            }

            public Builder walarm(int value) {
                this.walarm = value;
                return this;
            }

            public Builder gpsTime(long value) {
                this.gpsTime = value;
                return this;
            }

            public Builder audioTime(long value) {
                this.audioTime = value;
                return this;
            }

            public Builder mobileRadioActiveTime(long value) {
                this.mobileRadioActiveTime = value;
                return this;
            }

            public Builder bluetoothScanCount(int value) {
                this.bluetoothScanCount = value;
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

        public void addAppUsage(AppDetailUsage input) {
            if (this.uid != input.getUid()) {
                return;
            }
            this.power += input.getPowerUsage();
            this.screenPower += input.getScreenPowerUsage();
            this.fgTime += input.getForegroundTime();
            this.bgTime += input.getBackgroundTime();
            this.cpuTime += input.getCpuTime();
            this.wakelockTime += input.getWakelockTime();
            this.mobilePackets += input.getMobileDataUsage();
            this.wifiPackets += input.getWifiDataUsage();
            this.walarm += input.getWakeAlarmCount();
            this.gpsTime += input.getGpsTime();
            this.audioTime += input.getAudioTime();
            this.mobileRadioActiveTime += input.getMobileRadioActiveTime();
            this.bluetoothScanCount += input.getBluetoothScanCount();
        }

        public void calculateDelta(AppDetailUsage prev) {
            if (this.uid != prev.getUid()) {
                return;
            }
            this.power = Math.max(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, this.power - prev.getPowerUsage());
            this.screenPower = Math.max(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, this.screenPower - prev.getScreenPowerUsage());
            this.fgTime = Math.max(0L, this.fgTime - prev.getForegroundTime());
            this.bgTime = Math.max(0L, this.bgTime - prev.getBackgroundTime());
            this.cpuTime = Math.max(0L, this.cpuTime - prev.getCpuTime());
            this.wakelockTime = Math.max(0L, this.wakelockTime - prev.getWakelockTime());
            this.mobilePackets = Math.max(0L, this.mobilePackets - prev.getMobileDataUsage());
            this.wifiPackets = Math.max(0L, this.wifiPackets - prev.getWifiDataUsage());
            this.walarm = Math.max(0, this.walarm - prev.getWakeAlarmCount());
            this.gpsTime = Math.max(0L, this.gpsTime - prev.getGpsTime());
            this.audioTime = Math.max(0L, this.audioTime - prev.getAudioTime());
            this.mobileRadioActiveTime = Math.max(0L, this.mobileRadioActiveTime - prev.getMobileRadioActiveTime());
            this.bluetoothScanCount = Math.max(0, this.bluetoothScanCount - prev.getBluetoothScanCount());
        }

        protected AppDetailUsage(Parcel in) {
            this.uid = in.readInt();
            this.power = in.readDouble();
            this.screenPower = in.readDouble();
            this.fgTime = in.readLong();
            this.bgTime = in.readLong();
            this.cpuTime = in.readLong();
            this.wakelockTime = in.readLong();
            this.mobilePackets = in.readLong();
            this.wifiPackets = in.readLong();
            this.walarm = in.readInt();
            this.gpsTime = in.readLong();
            this.audioTime = in.readLong();
            this.mobileRadioActiveTime = in.readLong();
            this.bluetoothScanCount = in.readInt();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
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
            public SysDetailUsage createFromParcel(Parcel in) {
                return new SysDetailUsage(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SysDetailUsage[] newArray(int size) {
                return new SysDetailUsage[size];
            }
        };
        private final int drainType;
        private double power;
        private long usedTime;

        public SysDetailUsage(int drainType) {
            this.drainType = drainType;
            this.power = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            this.usedTime = 0L;
        }

        public SysDetailUsage(SysDetailUsage copy) {
            this.drainType = copy.getDrainType();
            this.power = copy.getPowerUsage();
            this.usedTime = copy.getUsedTime();
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

            public Builder drainType(int value) {
                this.drainType = value;
                return this;
            }

            public Builder power(double value) {
                this.power = value;
                return this;
            }

            public Builder usedTime(long value) {
                this.usedTime = value;
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

        public void addSysUsage(SysDetailUsage input) {
            if (this.drainType != input.getDrainType()) {
                return;
            }
            this.power += input.getPowerUsage();
            this.usedTime += input.getUsedTime();
        }

        public void calculateDelta(SysDetailUsage prev) {
            if (this.drainType != prev.getDrainType()) {
                return;
            }
            this.power = Math.max(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, this.power - prev.getPowerUsage());
            this.usedTime = Math.max(0L, this.usedTime - prev.getUsedTime());
        }

        protected SysDetailUsage(Parcel in) {
            this.drainType = in.readInt();
            this.power = in.readDouble();
            this.usedTime = in.readLong();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.drainType);
            parcel.writeDouble(this.power);
            parcel.writeLong(this.usedTime);
        }
    }
}
