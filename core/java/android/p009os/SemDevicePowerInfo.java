package android.p009os;

import android.hardware.scontext.SContextConstants;
import android.p009os.Parcelable;
import android.telephony.CellSignalStrength;

/* loaded from: classes3.dex */
public class SemDevicePowerInfo implements Parcelable {
    public static final Parcelable.Creator<SemDevicePowerInfo> CREATOR = new Parcelable.Creator<SemDevicePowerInfo>() { // from class: android.os.SemDevicePowerInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemDevicePowerInfo createFromParcel(Parcel in) {
            return new SemDevicePowerInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemDevicePowerInfo[] newArray(int size) {
            return new SemDevicePowerInfo[size];
        }
    };
    public long actualGpsTime;
    public double aodPower;
    public long aodTime;
    public int batteryPerc;
    public long btOnTime;
    public int btScanCount;
    public long btScanTime;
    public long btTotalBytes;
    public long cpIdleTime;
    public long cpSleepTime;
    public long gpsTime;
    public long hrrAlwaysTime;
    public double idlePower;
    public long idleTime;
    public long lcRxByte;
    public long lcRxTime;
    public long lcTxByte;
    public double lcTxLevel;
    public long lcTxTime;
    public int mobileActiveCount;
    public long mobileActiveTime;
    public long mobileActiveTime5G;
    public long mobileTotalBytes;
    public long mobileTotalPackets;
    public long nrRxByte;
    public long nrRxTime;
    public long nrTxByte;
    public double nrTxLevel;
    public long nrTxTime;
    public long phoneOnTime;
    public double phonePower;
    public long powersharePower;
    public long powershareTime;
    public long psmTime;
    public long pwlTime;
    public double radioPower;
    public long[] screenAutoBrightnessTime;
    public long[] screenBrightnessTime;
    public long screenHighBrightnessTime;
    public int screenOffCoulombCounter;
    public int screenOffDischarge;
    public long screenOffTime;
    public long screenOffUptime;
    public int screenOnCoulombCounter;
    public int screenOnCount;
    public int screenOnDischarge;
    public long screenOnGpsTime;
    public long screenOnTime;
    public double screenPower;
    public long[] signalStrengthTime;
    public long spkCallLevel;
    public long spkCallTime;
    public long spkMediaLevel;
    public long spkMediaTime;
    public long subAodTime;
    public long subHrrAlwaysTime;
    public long[] subScreenAutoBrightnessTime;
    public long[] subScreenBrightnessTime;
    public long subScreenHighBrightnessTime;
    public int subScreenOffDischarge;
    public int subScreenOnDischarge;
    public long subScreenOnTime;
    public double totalPower;
    public long uptime;
    public long wifiOnTime;
    public double wifiPower;
    public int wifiScanCount;
    public long wifiScanTime;
    public long wifiTotalBytes;
    public long wifiTotalPackets;

    public SemDevicePowerInfo() {
        this.screenBrightnessTime = new long[5];
        this.screenAutoBrightnessTime = new long[5];
        this.subScreenBrightnessTime = new long[5];
        this.subScreenAutoBrightnessTime = new long[5];
        this.signalStrengthTime = new long[CellSignalStrength.getNumSignalStrengthLevels()];
        reset();
    }

    public SemDevicePowerInfo(double totalPower) {
        this.screenBrightnessTime = new long[5];
        this.screenAutoBrightnessTime = new long[5];
        this.subScreenBrightnessTime = new long[5];
        this.subScreenAutoBrightnessTime = new long[5];
        this.signalStrengthTime = new long[CellSignalStrength.getNumSignalStrengthLevels()];
        reset();
        this.totalPower = totalPower;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(256);
        sb.append("power = ").append(this.totalPower);
        sb.append(", screenPower = ").append(this.screenPower);
        sb.append(", idlePower = ").append(this.idlePower);
        sb.append(", percent = ").append(this.batteryPerc);
        sb.append(", screenOnTime = ").append(this.screenOnTime);
        sb.append(", screenOffTime = ").append(this.screenOffTime);
        sb.append(", uptime = ").append(this.screenOffUptime);
        sb.append(", screenOffUptime = ").append(this.screenOffUptime);
        sb.append(", AOD = ").append(this.aodTime);
        sb.append(", powerSave = ").append(this.psmTime);
        sb.append(", wakelock time = ").append(this.pwlTime);
        return sb.toString();
    }

    public void update(SemDevicePowerInfo info) {
        this.totalPower = info.totalPower;
        this.screenPower = info.screenPower;
        this.aodPower = info.aodPower;
        this.phonePower = info.phonePower;
        this.wifiPower = info.wifiPower;
        this.idlePower = info.idlePower;
        this.idleTime = info.idleTime;
        this.radioPower = info.radioPower;
        this.batteryPerc = info.batteryPerc;
        this.screenOnCount = info.screenOnCount;
        this.screenOnTime = info.screenOnTime;
        this.subScreenOnTime = info.subScreenOnTime;
        this.screenOffTime = info.screenOffTime;
        this.screenOnDischarge = info.screenOnDischarge;
        this.screenOffDischarge = info.screenOffDischarge;
        this.subScreenOnDischarge = info.subScreenOnDischarge;
        this.subScreenOffDischarge = info.subScreenOffDischarge;
        this.screenOnCoulombCounter = info.screenOnCoulombCounter;
        this.screenOffCoulombCounter = info.screenOffCoulombCounter;
        this.uptime = info.uptime;
        this.screenOffUptime = info.screenOffUptime;
        this.phoneOnTime = info.phoneOnTime;
        this.aodTime = info.aodTime;
        this.subAodTime = info.subAodTime;
        this.psmTime = info.psmTime;
        this.pwlTime = info.pwlTime;
        for (int i = 0; i < 5; i++) {
            this.screenBrightnessTime[i] = info.screenBrightnessTime[i];
            this.screenAutoBrightnessTime[i] = info.screenAutoBrightnessTime[i];
            this.subScreenBrightnessTime[i] = info.subScreenBrightnessTime[i];
            this.subScreenAutoBrightnessTime[i] = info.subScreenAutoBrightnessTime[i];
        }
        this.screenHighBrightnessTime = info.screenHighBrightnessTime;
        this.subScreenHighBrightnessTime = info.subScreenHighBrightnessTime;
        for (int i2 = 0; i2 < CellSignalStrength.getNumSignalStrengthLevels(); i2++) {
            this.signalStrengthTime[i2] = info.signalStrengthTime[i2];
        }
        this.mobileTotalBytes = info.mobileTotalBytes;
        this.mobileTotalPackets = info.mobileTotalPackets;
        this.mobileActiveTime = info.mobileActiveTime;
        this.mobileActiveTime5G = info.mobileActiveTime5G;
        this.mobileActiveCount = info.mobileActiveCount;
        this.wifiTotalBytes = info.wifiTotalBytes;
        this.wifiTotalPackets = info.wifiTotalPackets;
        this.wifiOnTime = info.wifiOnTime;
        this.wifiScanTime = info.wifiScanTime;
        this.wifiScanCount = info.wifiScanCount;
        this.btTotalBytes = info.btTotalBytes;
        this.btOnTime = info.btOnTime;
        this.btScanTime = info.btScanTime;
        this.btScanCount = info.btScanCount;
        this.gpsTime = info.gpsTime;
        this.actualGpsTime = info.actualGpsTime;
        this.screenOnGpsTime = info.screenOnGpsTime;
        this.powershareTime = info.powershareTime;
        this.powersharePower = info.powersharePower;
        this.spkCallTime = info.spkCallTime;
        this.spkCallLevel = info.spkCallLevel;
        this.spkMediaTime = info.spkMediaTime;
        this.spkMediaLevel = info.spkMediaLevel;
        this.hrrAlwaysTime = info.hrrAlwaysTime;
        this.subHrrAlwaysTime = info.subHrrAlwaysTime;
        this.cpSleepTime = info.cpSleepTime;
        this.cpIdleTime = info.cpIdleTime;
        this.nrTxTime = info.nrTxTime;
        this.nrTxLevel = info.nrTxLevel;
        this.nrRxTime = info.nrRxTime;
        this.nrTxByte = info.nrTxByte;
        this.nrRxByte = info.nrRxByte;
        this.lcTxTime = info.lcTxTime;
        this.lcTxLevel = info.lcTxLevel;
        this.lcRxTime = info.lcRxTime;
        this.lcTxByte = info.lcTxByte;
        this.lcRxByte = info.lcRxByte;
    }

    public void reset() {
        this.totalPower = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.screenPower = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.aodPower = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.phonePower = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.wifiPower = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.idlePower = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.idleTime = 0L;
        this.radioPower = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.batteryPerc = 0;
        this.screenOnCount = 0;
        this.screenOnTime = 0L;
        this.subScreenOnTime = 0L;
        this.screenOffTime = 0L;
        this.screenOnDischarge = 0;
        this.screenOffDischarge = 0;
        this.subScreenOnDischarge = 0;
        this.subScreenOffDischarge = 0;
        this.screenOnCoulombCounter = 0;
        this.screenOffCoulombCounter = 0;
        this.uptime = 0L;
        this.screenOffUptime = 0L;
        this.phoneOnTime = 0L;
        this.aodTime = 0L;
        this.subAodTime = 0L;
        this.psmTime = 0L;
        this.pwlTime = 0L;
        for (int i = 0; i < 5; i++) {
            this.screenBrightnessTime[i] = 0;
            this.screenAutoBrightnessTime[i] = 0;
            this.subScreenBrightnessTime[i] = 0;
            this.subScreenAutoBrightnessTime[i] = 0;
        }
        this.screenHighBrightnessTime = 0L;
        this.subScreenHighBrightnessTime = 0L;
        for (int i2 = 0; i2 < CellSignalStrength.getNumSignalStrengthLevels(); i2++) {
            this.signalStrengthTime[i2] = 0;
        }
        this.mobileTotalBytes = 0L;
        this.mobileTotalPackets = 0L;
        this.mobileActiveTime = 0L;
        this.mobileActiveTime5G = 0L;
        this.mobileActiveCount = 0;
        this.wifiTotalBytes = 0L;
        this.wifiTotalPackets = 0L;
        this.wifiOnTime = 0L;
        this.wifiScanTime = 0L;
        this.wifiScanCount = 0;
        this.btTotalBytes = 0L;
        this.btOnTime = 0L;
        this.btScanTime = 0L;
        this.btScanCount = 0;
        this.gpsTime = 0L;
        this.actualGpsTime = 0L;
        this.screenOnGpsTime = 0L;
        this.powershareTime = 0L;
        this.powersharePower = 0L;
        this.spkCallTime = 0L;
        this.spkCallLevel = 0L;
        this.spkMediaTime = 0L;
        this.spkMediaLevel = 0L;
        this.hrrAlwaysTime = 0L;
        this.subHrrAlwaysTime = 0L;
        this.cpSleepTime = 0L;
        this.cpIdleTime = 0L;
        this.nrTxTime = 0L;
        this.nrTxLevel = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.nrRxTime = 0L;
        this.nrTxByte = 0L;
        this.nrRxByte = 0L;
        this.lcTxTime = 0L;
        this.lcTxLevel = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.lcRxTime = 0L;
        this.lcTxByte = 0L;
        this.lcRxByte = 0L;
    }

    public void addDelta(SemDevicePowerInfo delta) {
        this.totalPower += delta.totalPower;
        this.screenPower += delta.screenPower;
        this.aodPower += delta.aodPower;
        this.phonePower += delta.phonePower;
        this.wifiPower += delta.wifiPower;
        this.idlePower += delta.idlePower;
        this.idleTime += delta.idleTime;
        this.radioPower += delta.radioPower;
        this.batteryPerc += delta.batteryPerc;
        this.screenOnCount += delta.screenOnCount;
        this.screenOnTime += delta.screenOnTime;
        this.subScreenOnTime += delta.subScreenOnTime;
        this.screenOffTime += delta.screenOffTime;
        this.screenOnDischarge += delta.screenOnDischarge;
        this.screenOffDischarge += delta.screenOffDischarge;
        this.subScreenOnDischarge += delta.subScreenOnDischarge;
        this.subScreenOffDischarge += delta.subScreenOffDischarge;
        this.screenOnCoulombCounter += delta.screenOnCoulombCounter;
        this.screenOffCoulombCounter += delta.screenOffCoulombCounter;
        this.uptime += delta.uptime;
        this.screenOffUptime += delta.screenOffUptime;
        this.phoneOnTime += delta.phoneOnTime;
        this.aodTime += delta.aodTime;
        this.subAodTime += delta.subAodTime;
        this.psmTime += delta.psmTime;
        this.pwlTime += delta.pwlTime;
        for (int i = 0; i < 5; i++) {
            long[] jArr = this.screenBrightnessTime;
            jArr[i] = jArr[i] + delta.screenBrightnessTime[i];
            long[] jArr2 = this.screenAutoBrightnessTime;
            jArr2[i] = jArr2[i] + delta.screenAutoBrightnessTime[i];
            long[] jArr3 = this.subScreenBrightnessTime;
            jArr3[i] = jArr3[i] + delta.subScreenBrightnessTime[i];
            long[] jArr4 = this.subScreenAutoBrightnessTime;
            jArr4[i] = jArr4[i] + delta.subScreenAutoBrightnessTime[i];
        }
        this.screenHighBrightnessTime += delta.screenHighBrightnessTime;
        this.subScreenHighBrightnessTime += delta.subScreenHighBrightnessTime;
        for (int i2 = 0; i2 < CellSignalStrength.getNumSignalStrengthLevels(); i2++) {
            long[] jArr5 = this.signalStrengthTime;
            jArr5[i2] = jArr5[i2] + delta.signalStrengthTime[i2];
        }
        this.mobileTotalBytes += delta.mobileTotalBytes;
        this.mobileTotalPackets += delta.mobileTotalPackets;
        this.mobileActiveTime += delta.mobileActiveTime;
        this.mobileActiveTime5G += delta.mobileActiveTime5G;
        this.mobileActiveCount += delta.mobileActiveCount;
        this.wifiTotalBytes += delta.wifiTotalBytes;
        this.wifiTotalPackets += delta.wifiTotalPackets;
        this.wifiOnTime += delta.wifiOnTime;
        this.wifiScanTime += delta.wifiScanTime;
        this.wifiScanCount += delta.wifiScanCount;
        this.btTotalBytes += delta.btTotalBytes;
        this.btOnTime += delta.btOnTime;
        this.btScanTime += delta.btScanTime;
        this.btScanCount += delta.btScanCount;
        this.gpsTime += delta.gpsTime;
        this.actualGpsTime += delta.actualGpsTime;
        this.screenOnGpsTime += delta.screenOnGpsTime;
        this.powershareTime += delta.powershareTime;
        this.powersharePower += delta.powersharePower;
        this.spkCallTime += delta.spkCallTime;
        this.spkCallLevel += delta.spkCallLevel;
        this.spkMediaTime += delta.spkMediaTime;
        this.spkMediaLevel += delta.spkMediaLevel;
        this.hrrAlwaysTime += delta.hrrAlwaysTime;
        this.subHrrAlwaysTime += delta.subHrrAlwaysTime;
        this.cpSleepTime += delta.cpSleepTime;
        this.cpIdleTime += delta.cpIdleTime;
        long j = this.nrTxTime + delta.nrTxTime;
        this.nrTxTime = j;
        long j2 = delta.nrTxTime;
        if (j + j2 != 0) {
            this.nrTxLevel = ((this.nrTxLevel * j) + (delta.nrTxLevel * j2)) / (j + j2);
        } else {
            this.nrTxLevel = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }
        this.nrRxTime += delta.nrRxTime;
        this.nrTxByte += delta.nrTxByte;
        this.nrRxByte += delta.nrRxByte;
        long j3 = this.lcTxTime + delta.lcTxTime;
        this.lcTxTime = j3;
        long j4 = delta.lcTxTime;
        if (j3 + j4 != 0) {
            this.lcTxLevel = ((this.lcTxLevel * j3) + (delta.lcTxLevel * j4)) / (j3 + j4);
        } else {
            this.lcTxLevel = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }
        this.lcRxTime += delta.lcRxTime;
        this.lcTxByte += delta.lcTxByte;
        this.lcRxByte += delta.lcRxByte;
    }

    protected SemDevicePowerInfo(Parcel in) {
        this.screenBrightnessTime = new long[5];
        this.screenAutoBrightnessTime = new long[5];
        this.subScreenBrightnessTime = new long[5];
        this.subScreenAutoBrightnessTime = new long[5];
        this.signalStrengthTime = new long[CellSignalStrength.getNumSignalStrengthLevels()];
        this.totalPower = in.readDouble();
        this.screenPower = in.readDouble();
        this.aodPower = in.readDouble();
        this.phonePower = in.readDouble();
        this.wifiPower = in.readDouble();
        this.idlePower = in.readDouble();
        this.idleTime = in.readLong();
        this.radioPower = in.readDouble();
        this.batteryPerc = in.readInt();
        this.screenOnCount = in.readInt();
        this.screenOnTime = in.readLong();
        this.subScreenOnTime = in.readLong();
        this.screenOffTime = in.readLong();
        this.screenOnDischarge = in.readInt();
        this.screenOffDischarge = in.readInt();
        this.subScreenOnDischarge = in.readInt();
        this.subScreenOffDischarge = in.readInt();
        this.screenOnCoulombCounter = in.readInt();
        this.screenOffCoulombCounter = in.readInt();
        this.uptime = in.readLong();
        this.screenOffUptime = in.readLong();
        this.phoneOnTime = in.readLong();
        this.aodTime = in.readLong();
        this.subAodTime = in.readLong();
        this.psmTime = in.readLong();
        this.pwlTime = in.readLong();
        for (int i = 0; i < 5; i++) {
            this.screenBrightnessTime[i] = in.readLong();
        }
        for (int i2 = 0; i2 < 5; i2++) {
            this.screenAutoBrightnessTime[i2] = in.readLong();
        }
        this.screenHighBrightnessTime = in.readLong();
        for (int i3 = 0; i3 < 5; i3++) {
            this.subScreenBrightnessTime[i3] = in.readLong();
        }
        for (int i4 = 0; i4 < 5; i4++) {
            this.subScreenAutoBrightnessTime[i4] = in.readLong();
        }
        this.subScreenHighBrightnessTime = in.readLong();
        for (int i5 = 0; i5 < CellSignalStrength.getNumSignalStrengthLevels(); i5++) {
            this.signalStrengthTime[i5] = in.readLong();
        }
        this.mobileTotalBytes = in.readLong();
        this.mobileTotalPackets = in.readLong();
        this.mobileActiveTime = in.readLong();
        this.mobileActiveTime5G = in.readLong();
        this.mobileActiveCount = in.readInt();
        this.wifiTotalBytes = in.readLong();
        this.wifiTotalPackets = in.readLong();
        this.wifiOnTime = in.readLong();
        this.wifiScanTime = in.readLong();
        this.wifiScanCount = in.readInt();
        this.btTotalBytes = in.readLong();
        this.btOnTime = in.readLong();
        this.btScanTime = in.readLong();
        this.btScanCount = in.readInt();
        this.gpsTime = in.readLong();
        this.actualGpsTime = in.readLong();
        this.screenOnGpsTime = in.readLong();
        this.powershareTime = in.readLong();
        this.powersharePower = in.readLong();
        this.spkCallTime = in.readLong();
        this.spkCallLevel = in.readLong();
        this.spkMediaTime = in.readLong();
        this.spkMediaLevel = in.readLong();
        this.hrrAlwaysTime = in.readLong();
        this.cpSleepTime = in.readLong();
        this.cpIdleTime = in.readLong();
        this.nrTxTime = in.readLong();
        this.nrTxLevel = in.readDouble();
        this.nrRxTime = in.readLong();
        this.nrTxByte = in.readLong();
        this.nrRxByte = in.readLong();
        this.lcTxTime = in.readLong();
        this.lcTxLevel = in.readDouble();
        this.lcRxTime = in.readLong();
        this.lcTxByte = in.readLong();
        this.lcRxByte = in.readLong();
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.totalPower);
        dest.writeDouble(this.screenPower);
        dest.writeDouble(this.aodPower);
        dest.writeDouble(this.phonePower);
        dest.writeDouble(this.wifiPower);
        dest.writeDouble(this.idlePower);
        dest.writeLong(this.idleTime);
        dest.writeDouble(this.radioPower);
        dest.writeInt(this.batteryPerc);
        dest.writeInt(this.screenOnCount);
        dest.writeLong(this.screenOnTime);
        dest.writeLong(this.subScreenOnTime);
        dest.writeLong(this.screenOffTime);
        dest.writeInt(this.screenOnDischarge);
        dest.writeInt(this.screenOffDischarge);
        dest.writeInt(this.subScreenOnDischarge);
        dest.writeInt(this.subScreenOffDischarge);
        dest.writeInt(this.screenOnCoulombCounter);
        dest.writeInt(this.screenOffCoulombCounter);
        dest.writeLong(this.uptime);
        dest.writeLong(this.screenOffUptime);
        dest.writeLong(this.phoneOnTime);
        dest.writeLong(this.aodTime);
        dest.writeLong(this.subAodTime);
        dest.writeLong(this.psmTime);
        dest.writeLong(this.pwlTime);
        for (int i = 0; i < 5; i++) {
            dest.writeLong(this.screenBrightnessTime[i]);
        }
        for (int i2 = 0; i2 < 5; i2++) {
            dest.writeLong(this.screenAutoBrightnessTime[i2]);
        }
        dest.writeLong(this.screenHighBrightnessTime);
        for (int i3 = 0; i3 < 5; i3++) {
            dest.writeLong(this.subScreenBrightnessTime[i3]);
        }
        for (int i4 = 0; i4 < 5; i4++) {
            dest.writeLong(this.subScreenAutoBrightnessTime[i4]);
        }
        dest.writeLong(this.subScreenHighBrightnessTime);
        for (int i5 = 0; i5 < CellSignalStrength.getNumSignalStrengthLevels(); i5++) {
            dest.writeLong(this.signalStrengthTime[i5]);
        }
        dest.writeLong(this.mobileTotalBytes);
        dest.writeLong(this.mobileTotalPackets);
        dest.writeLong(this.mobileActiveTime);
        dest.writeLong(this.mobileActiveTime5G);
        dest.writeInt(this.mobileActiveCount);
        dest.writeLong(this.wifiTotalBytes);
        dest.writeLong(this.wifiTotalPackets);
        dest.writeLong(this.wifiOnTime);
        dest.writeLong(this.wifiScanTime);
        dest.writeInt(this.wifiScanCount);
        dest.writeLong(this.btTotalBytes);
        dest.writeLong(this.btOnTime);
        dest.writeLong(this.btScanTime);
        dest.writeInt(this.btScanCount);
        dest.writeLong(this.gpsTime);
        dest.writeLong(this.actualGpsTime);
        dest.writeLong(this.screenOnGpsTime);
        dest.writeLong(this.powershareTime);
        dest.writeLong(this.powersharePower);
        dest.writeLong(this.spkCallTime);
        dest.writeLong(this.spkCallLevel);
        dest.writeLong(this.spkMediaTime);
        dest.writeLong(this.spkMediaLevel);
        dest.writeLong(this.hrrAlwaysTime);
        dest.writeLong(this.cpSleepTime);
        dest.writeLong(this.cpIdleTime);
        dest.writeLong(this.nrTxTime);
        dest.writeDouble(this.nrTxLevel);
        dest.writeLong(this.nrRxTime);
        dest.writeLong(this.nrTxByte);
        dest.writeLong(this.nrRxByte);
        dest.writeLong(this.lcTxTime);
        dest.writeDouble(this.lcTxLevel);
        dest.writeLong(this.lcRxTime);
        dest.writeLong(this.lcTxByte);
        dest.writeLong(this.lcRxByte);
    }
}
