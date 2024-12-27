package android.os;

import android.hardware.scontext.SContextConstants;

import java.util.Arrays;

public class SemUidPowerInfo implements Parcelable {
    public static final Parcelable.Creator<SemUidPowerInfo> CREATOR =
            new Parcelable.Creator<SemUidPowerInfo>() { // from class: android.os.SemUidPowerInfo.1
                @Override // android.os.Parcelable.Creator
                public SemUidPowerInfo createFromParcel(Parcel in) {
                    return new SemUidPowerInfo(in);
                }

                @Override // android.os.Parcelable.Creator
                public SemUidPowerInfo[] newArray(int size) {
                    return new SemUidPowerInfo[size];
                }
            };
    public long actualGpsTime;
    public long audioTime;
    public long bgTime;
    public long btData;
    public int btScan;
    public long cameraRunTime;
    public long cpuTime;
    public final long[] displayTopActivityTime;
    public long fgTime;
    public long gpsTime;
    public long killCount;
    public long mobileActive;
    public long mobileData;
    public long mobilePackets;
    public long networkWakeup;
    public double power;
    public double screenPower;
    public long screenTime;
    public boolean shouldHide;
    public double smearedPower;
    public long spkLevel;
    public long spkTime;
    public long syncTime;
    public int uid;
    public long wakelockTime;
    public int wakeupAlarm;
    public long wifiData;
    public long wifiPackets;

    public SemUidPowerInfo(int uid) {
        this.uid = uid;
        this.screenPower = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.smearedPower = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.power = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.shouldHide = false;
        this.cpuTime = 0L;
        this.wakelockTime = 0L;
        this.mobileActive = 0L;
        this.mobileData = 0L;
        this.mobilePackets = 0L;
        this.wifiPackets = 0L;
        this.wifiData = 0L;
        this.wakeupAlarm = 0;
        this.btScan = 0;
        this.btData = 0L;
        this.gpsTime = 0L;
        this.actualGpsTime = 0L;
        this.cameraRunTime = 0L;
        this.killCount = 0L;
        this.screenTime = 0L;
        this.fgTime = 0L;
        this.bgTime = 0L;
        this.spkTime = 0L;
        this.spkLevel = 0L;
        this.audioTime = 0L;
        this.networkWakeup = 0L;
        this.syncTime = 0L;
        this.displayTopActivityTime = new long[getNumDisplays()];
        Arrays.fill(this.displayTopActivityTime, 0L);
    }

    public String toString() {
        return String.format(
                "power %f cpu %d wake %d mActive %d mPkt %d wPkt %d walarm %d",
                Double.valueOf(this.power),
                Long.valueOf(this.cpuTime),
                Long.valueOf(this.wakelockTime),
                Long.valueOf(this.mobileActive),
                Long.valueOf(this.mobilePackets),
                Long.valueOf(this.wifiPackets),
                Integer.valueOf(this.wakeupAlarm));
    }

    public void update(SemUidPowerInfo info) {
        this.screenPower = info.screenPower;
        this.smearedPower = info.smearedPower;
        this.power = info.power;
        this.shouldHide = info.shouldHide;
        this.cpuTime = info.cpuTime;
        this.wakelockTime = info.wakelockTime;
        this.mobileActive = info.mobileActive;
        this.mobileData = info.mobileData;
        this.mobilePackets = info.mobilePackets;
        this.wifiPackets = info.wifiPackets;
        this.wifiData = info.wifiData;
        this.wakeupAlarm = info.wakeupAlarm;
        this.btScan = info.btScan;
        this.btData = info.btData;
        this.gpsTime = info.gpsTime;
        this.actualGpsTime = info.actualGpsTime;
        this.cameraRunTime = info.cameraRunTime;
        this.killCount = info.killCount;
        this.screenTime = info.screenTime;
        this.fgTime = info.fgTime;
        this.bgTime = info.bgTime;
        this.spkTime = info.spkTime;
        this.spkLevel = info.spkLevel;
        this.audioTime = info.audioTime;
        this.networkWakeup = info.networkWakeup;
        this.syncTime = info.syncTime;
        for (int i = 0; i < this.displayTopActivityTime.length; i++) {
            this.displayTopActivityTime[i] = info.displayTopActivityTime[i];
        }
    }

    public void reset() {
        this.screenPower = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.smearedPower = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.power = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.shouldHide = false;
        this.cpuTime = 0L;
        this.wakelockTime = 0L;
        this.mobileActive = 0L;
        this.mobileData = 0L;
        this.mobilePackets = 0L;
        this.wifiPackets = 0L;
        this.wifiData = 0L;
        this.wakeupAlarm = 0;
        this.btScan = 0;
        this.btData = 0L;
        this.gpsTime = 0L;
        this.actualGpsTime = 0L;
        this.cameraRunTime = 0L;
        this.killCount = 0L;
        this.screenTime = 0L;
        this.fgTime = 0L;
        this.bgTime = 0L;
        this.spkTime = 0L;
        this.spkLevel = 0L;
        this.audioTime = 0L;
        this.networkWakeup = 0L;
        this.syncTime = 0L;
        Arrays.fill(this.displayTopActivityTime, 0L);
    }

    public void addDelta(SemUidPowerInfo delta) {
        this.screenPower += delta.screenPower;
        this.smearedPower += delta.smearedPower;
        this.power += delta.power;
        this.cpuTime += delta.cpuTime;
        this.wakelockTime += delta.wakelockTime;
        this.mobileActive += delta.mobileActive;
        this.mobileData += delta.mobileData;
        this.mobilePackets += delta.mobilePackets;
        this.wifiPackets += delta.wifiPackets;
        this.wifiData += delta.wifiData;
        this.wakeupAlarm += delta.wakeupAlarm;
        this.btScan += delta.btScan;
        this.btData += delta.btData;
        this.gpsTime += delta.gpsTime;
        this.actualGpsTime += delta.actualGpsTime;
        this.cameraRunTime += delta.cameraRunTime;
        this.killCount += delta.killCount;
        this.screenTime += delta.screenTime;
        this.fgTime += delta.fgTime;
        this.bgTime += delta.bgTime;
        this.spkTime += delta.spkTime;
        this.spkLevel += delta.spkLevel;
        this.audioTime += delta.audioTime;
        this.networkWakeup += delta.networkWakeup;
        this.syncTime += delta.syncTime;
        for (int i = 0; i < this.displayTopActivityTime.length; i++) {
            long[] jArr = this.displayTopActivityTime;
            jArr[i] = jArr[i] + delta.displayTopActivityTime[i];
        }
    }

    protected SemUidPowerInfo(Parcel in) {
        this.uid = in.readInt();
        this.screenPower = in.readDouble();
        this.smearedPower = in.readDouble();
        this.power = in.readDouble();
        this.shouldHide = in.readBoolean();
        this.cpuTime = in.readLong();
        this.wakelockTime = in.readLong();
        this.mobileActive = in.readLong();
        this.mobileData = in.readLong();
        this.mobilePackets = in.readLong();
        this.wifiPackets = in.readLong();
        this.wifiData = in.readLong();
        this.wakeupAlarm = in.readInt();
        this.btScan = in.readInt();
        this.btData = in.readLong();
        this.gpsTime = in.readLong();
        this.actualGpsTime = in.readLong();
        this.cameraRunTime = in.readLong();
        this.killCount = in.readLong();
        this.screenTime = in.readLong();
        this.fgTime = in.readLong();
        this.bgTime = in.readLong();
        this.spkTime = in.readLong();
        this.spkLevel = in.readLong();
        this.audioTime = in.readLong();
        this.networkWakeup = in.readLong();
        this.syncTime = in.readLong();
        this.displayTopActivityTime = new long[getNumDisplays()];
        for (int i = 0; i < this.displayTopActivityTime.length; i++) {
            this.displayTopActivityTime[i] = in.readLong();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.uid);
        dest.writeDouble(this.screenPower);
        dest.writeDouble(this.smearedPower);
        dest.writeDouble(this.power);
        dest.writeBoolean(this.shouldHide);
        dest.writeLong(this.cpuTime);
        dest.writeLong(this.wakelockTime);
        dest.writeLong(this.mobileActive);
        dest.writeLong(this.mobileData);
        dest.writeLong(this.mobilePackets);
        dest.writeLong(this.wifiPackets);
        dest.writeLong(this.wifiData);
        dest.writeInt(this.wakeupAlarm);
        dest.writeInt(this.btScan);
        dest.writeLong(this.btData);
        dest.writeLong(this.gpsTime);
        dest.writeLong(this.actualGpsTime);
        dest.writeLong(this.cameraRunTime);
        dest.writeLong(this.killCount);
        dest.writeLong(this.screenTime);
        dest.writeLong(this.fgTime);
        dest.writeLong(this.bgTime);
        dest.writeLong(this.spkTime);
        dest.writeLong(this.spkLevel);
        dest.writeLong(this.audioTime);
        dest.writeLong(this.networkWakeup);
        dest.writeLong(this.syncTime);
        for (int i = 0; i < this.displayTopActivityTime.length; i++) {
            dest.writeLong(this.displayTopActivityTime[i]);
        }
    }

    private int getNumDisplays() {
        return 1;
    }
}
