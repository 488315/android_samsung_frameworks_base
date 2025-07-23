package android.os;

import android.hardware.scontext.SContextConstants;
import android.os.Parcelable;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class SemUidPowerInfo implements Parcelable {
    public static final Parcelable.Creator<SemUidPowerInfo> CREATOR = new Parcelable.Creator<SemUidPowerInfo>() { // from class: android.os.SemUidPowerInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemUidPowerInfo createFromParcel(Parcel parcel) {
            return new SemUidPowerInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemUidPowerInfo[] newArray(int i) {
            return new SemUidPowerInfo[i];
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
    public double smearedPower;
    public long spkLevel;
    public long spkTime;
    public long syncTime;
    public int uid;
    public long wakelockTime;
    public int wakeupAlarm;
    public long wifiData;
    public long wifiPackets;

    private int getNumDisplays() {
        return 1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemUidPowerInfo(int i) {
        this.uid = i;
        this.screenPower = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.smearedPower = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.power = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
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
        long[] jArr = new long[getNumDisplays()];
        this.displayTopActivityTime = jArr;
        Arrays.fill(jArr, 0L);
    }

    public String toString() {
        return String.format("power %f cpu %d wake %d mActive %d mPkt %d wPkt %d walarm %d", Double.valueOf(this.power), Long.valueOf(this.cpuTime), Long.valueOf(this.wakelockTime), Long.valueOf(this.mobileActive), Long.valueOf(this.mobilePackets), Long.valueOf(this.wifiPackets), Integer.valueOf(this.wakeupAlarm));
    }

    public void update(SemUidPowerInfo semUidPowerInfo) {
        this.screenPower = semUidPowerInfo.screenPower;
        this.smearedPower = semUidPowerInfo.smearedPower;
        this.power = semUidPowerInfo.power;
        this.cpuTime = semUidPowerInfo.cpuTime;
        this.wakelockTime = semUidPowerInfo.wakelockTime;
        this.mobileActive = semUidPowerInfo.mobileActive;
        this.mobileData = semUidPowerInfo.mobileData;
        this.mobilePackets = semUidPowerInfo.mobilePackets;
        this.wifiPackets = semUidPowerInfo.wifiPackets;
        this.wifiData = semUidPowerInfo.wifiData;
        this.wakeupAlarm = semUidPowerInfo.wakeupAlarm;
        this.btScan = semUidPowerInfo.btScan;
        this.btData = semUidPowerInfo.btData;
        this.gpsTime = semUidPowerInfo.gpsTime;
        this.actualGpsTime = semUidPowerInfo.actualGpsTime;
        this.cameraRunTime = semUidPowerInfo.cameraRunTime;
        this.killCount = semUidPowerInfo.killCount;
        this.screenTime = semUidPowerInfo.screenTime;
        this.fgTime = semUidPowerInfo.fgTime;
        this.bgTime = semUidPowerInfo.bgTime;
        this.spkTime = semUidPowerInfo.spkTime;
        this.spkLevel = semUidPowerInfo.spkLevel;
        this.audioTime = semUidPowerInfo.audioTime;
        this.networkWakeup = semUidPowerInfo.networkWakeup;
        this.syncTime = semUidPowerInfo.syncTime;
        int i = 0;
        while (true) {
            long[] jArr = this.displayTopActivityTime;
            if (i >= jArr.length) {
                return;
            }
            jArr[i] = semUidPowerInfo.displayTopActivityTime[i];
            i++;
        }
    }

    public void reset() {
        this.screenPower = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.smearedPower = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.power = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
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

    public void addDelta(SemUidPowerInfo semUidPowerInfo) {
        this.screenPower += semUidPowerInfo.screenPower;
        this.smearedPower += semUidPowerInfo.smearedPower;
        this.power += semUidPowerInfo.power;
        this.cpuTime += semUidPowerInfo.cpuTime;
        this.wakelockTime += semUidPowerInfo.wakelockTime;
        this.mobileActive += semUidPowerInfo.mobileActive;
        this.mobileData += semUidPowerInfo.mobileData;
        this.mobilePackets += semUidPowerInfo.mobilePackets;
        this.wifiPackets += semUidPowerInfo.wifiPackets;
        this.wifiData += semUidPowerInfo.wifiData;
        this.wakeupAlarm += semUidPowerInfo.wakeupAlarm;
        this.btScan += semUidPowerInfo.btScan;
        this.btData += semUidPowerInfo.btData;
        this.gpsTime += semUidPowerInfo.gpsTime;
        this.actualGpsTime += semUidPowerInfo.actualGpsTime;
        this.cameraRunTime += semUidPowerInfo.cameraRunTime;
        this.killCount += semUidPowerInfo.killCount;
        this.screenTime += semUidPowerInfo.screenTime;
        this.fgTime += semUidPowerInfo.fgTime;
        this.bgTime += semUidPowerInfo.bgTime;
        this.spkTime += semUidPowerInfo.spkTime;
        this.spkLevel += semUidPowerInfo.spkLevel;
        this.audioTime += semUidPowerInfo.audioTime;
        this.networkWakeup += semUidPowerInfo.networkWakeup;
        this.syncTime += semUidPowerInfo.syncTime;
        int i = 0;
        while (true) {
            long[] jArr = this.displayTopActivityTime;
            if (i >= jArr.length) {
                return;
            }
            jArr[i] = jArr[i] + semUidPowerInfo.displayTopActivityTime[i];
            i++;
        }
    }

    protected SemUidPowerInfo(Parcel parcel) {
        this.uid = parcel.readInt();
        this.screenPower = parcel.readDouble();
        this.smearedPower = parcel.readDouble();
        this.power = parcel.readDouble();
        this.cpuTime = parcel.readLong();
        this.wakelockTime = parcel.readLong();
        this.mobileActive = parcel.readLong();
        this.mobileData = parcel.readLong();
        this.mobilePackets = parcel.readLong();
        this.wifiPackets = parcel.readLong();
        this.wifiData = parcel.readLong();
        this.wakeupAlarm = parcel.readInt();
        this.btScan = parcel.readInt();
        this.btData = parcel.readLong();
        this.gpsTime = parcel.readLong();
        this.actualGpsTime = parcel.readLong();
        this.cameraRunTime = parcel.readLong();
        this.killCount = parcel.readLong();
        this.screenTime = parcel.readLong();
        this.fgTime = parcel.readLong();
        this.bgTime = parcel.readLong();
        this.spkTime = parcel.readLong();
        this.spkLevel = parcel.readLong();
        this.audioTime = parcel.readLong();
        this.networkWakeup = parcel.readLong();
        this.syncTime = parcel.readLong();
        this.displayTopActivityTime = new long[getNumDisplays()];
        int i = 0;
        while (true) {
            long[] jArr = this.displayTopActivityTime;
            if (i >= jArr.length) {
                return;
            }
            jArr[i] = parcel.readLong();
            i++;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.uid);
        parcel.writeDouble(this.screenPower);
        parcel.writeDouble(this.smearedPower);
        parcel.writeDouble(this.power);
        parcel.writeLong(this.cpuTime);
        parcel.writeLong(this.wakelockTime);
        parcel.writeLong(this.mobileActive);
        parcel.writeLong(this.mobileData);
        parcel.writeLong(this.mobilePackets);
        parcel.writeLong(this.wifiPackets);
        parcel.writeLong(this.wifiData);
        parcel.writeInt(this.wakeupAlarm);
        parcel.writeInt(this.btScan);
        parcel.writeLong(this.btData);
        parcel.writeLong(this.gpsTime);
        parcel.writeLong(this.actualGpsTime);
        parcel.writeLong(this.cameraRunTime);
        parcel.writeLong(this.killCount);
        parcel.writeLong(this.screenTime);
        parcel.writeLong(this.fgTime);
        parcel.writeLong(this.bgTime);
        parcel.writeLong(this.spkTime);
        parcel.writeLong(this.spkLevel);
        parcel.writeLong(this.audioTime);
        parcel.writeLong(this.networkWakeup);
        parcel.writeLong(this.syncTime);
        int i2 = 0;
        while (true) {
            long[] jArr = this.displayTopActivityTime;
            if (i2 >= jArr.length) {
                return;
            }
            parcel.writeLong(jArr[i2]);
            i2++;
        }
    }
}
