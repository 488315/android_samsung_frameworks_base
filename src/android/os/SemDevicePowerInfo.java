package android.os;

import android.hardware.scontext.SContextConstants;
import android.os.Parcelable;
import android.telephony.CellSignalStrength;
import java.lang.reflect.Array;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class SemDevicePowerInfo implements Parcelable {
    public static final Parcelable.Creator<SemDevicePowerInfo> CREATOR = new Parcelable.Creator<SemDevicePowerInfo>() { // from class: android.os.SemDevicePowerInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemDevicePowerInfo createFromParcel(Parcel parcel) {
            return new SemDevicePowerInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemDevicePowerInfo[] newArray(int i) {
            return new SemDevicePowerInfo[i];
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
    public long[][] mKernelCpuSpeedTime;
    public long[][] mScreenOffKernelCpuSpeedTime;
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
    public int screenDozeDischarge;
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
    public int silentOnScreenOffDischarge;
    public long silentOnScreenOffTime;
    public int silentOnScreenOnDischarge;
    public long silentOnScreenOnTime;
    public long spkCallLevel;
    public long spkCallTime;
    public long spkMediaLevel;
    public long spkMediaTime;
    public long subAodTime;
    public long subHrrAlwaysTime;
    public long[] subScreenAutoBrightnessTime;
    public long[] subScreenBrightnessTime;
    public int subScreenDozeDischarge;
    public long subScreenHighBrightnessTime;
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemDevicePowerInfo() {
        this.screenBrightnessTime = new long[5];
        this.screenAutoBrightnessTime = new long[5];
        this.subScreenBrightnessTime = new long[5];
        this.subScreenAutoBrightnessTime = new long[5];
        this.signalStrengthTime = new long[CellSignalStrength.getNumSignalStrengthLevels()];
        reset();
    }

    public SemDevicePowerInfo(int[][] iArr) {
        this();
        this.mKernelCpuSpeedTime = new long[iArr.length][];
        for (int i = 0; i < iArr.length; i++) {
            long[][] jArr = this.mKernelCpuSpeedTime;
            long[] jArr2 = new long[iArr[i].length];
            jArr[i] = jArr2;
            Arrays.fill(jArr2, 0L);
        }
        this.mScreenOffKernelCpuSpeedTime = new long[iArr.length][];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            long[][] jArr3 = this.mScreenOffKernelCpuSpeedTime;
            long[] jArr4 = new long[iArr[i2].length];
            jArr3[i2] = jArr4;
            Arrays.fill(jArr4, 0L);
        }
    }

    public SemDevicePowerInfo(double d) {
        this.screenBrightnessTime = new long[5];
        this.screenAutoBrightnessTime = new long[5];
        this.subScreenBrightnessTime = new long[5];
        this.subScreenAutoBrightnessTime = new long[5];
        this.signalStrengthTime = new long[CellSignalStrength.getNumSignalStrengthLevels()];
        reset();
        this.totalPower = d;
    }

    public SemDevicePowerInfo(double d, int[][] iArr) {
        this(d);
        this.mKernelCpuSpeedTime = new long[iArr.length][];
        for (int i = 0; i < iArr.length; i++) {
            long[][] jArr = this.mKernelCpuSpeedTime;
            long[] jArr2 = new long[iArr[i].length];
            jArr[i] = jArr2;
            Arrays.fill(jArr2, 0L);
        }
        this.mScreenOffKernelCpuSpeedTime = new long[iArr.length][];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            long[][] jArr3 = this.mScreenOffKernelCpuSpeedTime;
            long[] jArr4 = new long[iArr[i2].length];
            jArr3[i2] = jArr4;
            Arrays.fill(jArr4, 0L);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(256);
        sb.append("power = ");
        sb.append(this.totalPower);
        sb.append(", screenPower = ");
        sb.append(this.screenPower);
        sb.append(", idlePower = ");
        sb.append(this.idlePower);
        sb.append(", percent = ");
        sb.append(this.batteryPerc);
        sb.append(", screenOnTime = ");
        sb.append(this.screenOnTime);
        sb.append(", screenOffTime = ");
        sb.append(this.screenOffTime);
        sb.append(", uptime = ");
        sb.append(this.screenOffUptime);
        sb.append(", screenOffUptime = ");
        sb.append(this.screenOffUptime);
        sb.append(", AOD = ");
        sb.append(this.aodTime);
        sb.append(", powerSave = ");
        sb.append(this.psmTime);
        sb.append(", wakelock time = ");
        sb.append(this.pwlTime);
        return sb.toString();
    }

    public void update(SemDevicePowerInfo semDevicePowerInfo) {
        this.totalPower = semDevicePowerInfo.totalPower;
        this.screenPower = semDevicePowerInfo.screenPower;
        this.aodPower = semDevicePowerInfo.aodPower;
        this.phonePower = semDevicePowerInfo.phonePower;
        this.wifiPower = semDevicePowerInfo.wifiPower;
        this.idlePower = semDevicePowerInfo.idlePower;
        this.idleTime = semDevicePowerInfo.idleTime;
        this.radioPower = semDevicePowerInfo.radioPower;
        this.batteryPerc = semDevicePowerInfo.batteryPerc;
        this.screenOnCount = semDevicePowerInfo.screenOnCount;
        this.screenOnTime = semDevicePowerInfo.screenOnTime;
        this.subScreenOnTime = semDevicePowerInfo.subScreenOnTime;
        this.screenOffTime = semDevicePowerInfo.screenOffTime;
        this.screenOnDischarge = semDevicePowerInfo.screenOnDischarge;
        this.screenDozeDischarge = semDevicePowerInfo.screenDozeDischarge;
        this.screenOffDischarge = semDevicePowerInfo.screenOffDischarge;
        this.subScreenOnDischarge = semDevicePowerInfo.subScreenOnDischarge;
        this.subScreenDozeDischarge = semDevicePowerInfo.subScreenDozeDischarge;
        this.screenOnCoulombCounter = semDevicePowerInfo.screenOnCoulombCounter;
        this.screenOffCoulombCounter = semDevicePowerInfo.screenOffCoulombCounter;
        this.silentOnScreenOnTime = semDevicePowerInfo.silentOnScreenOnTime;
        this.silentOnScreenOffTime = semDevicePowerInfo.silentOnScreenOffTime;
        this.silentOnScreenOnDischarge = semDevicePowerInfo.silentOnScreenOnDischarge;
        this.silentOnScreenOffDischarge = semDevicePowerInfo.silentOnScreenOffDischarge;
        this.uptime = semDevicePowerInfo.uptime;
        this.screenOffUptime = semDevicePowerInfo.screenOffUptime;
        this.phoneOnTime = semDevicePowerInfo.phoneOnTime;
        this.aodTime = semDevicePowerInfo.aodTime;
        this.subAodTime = semDevicePowerInfo.subAodTime;
        this.psmTime = semDevicePowerInfo.psmTime;
        this.pwlTime = semDevicePowerInfo.pwlTime;
        for (int i = 0; i < 5; i++) {
            this.screenBrightnessTime[i] = semDevicePowerInfo.screenBrightnessTime[i];
            this.screenAutoBrightnessTime[i] = semDevicePowerInfo.screenAutoBrightnessTime[i];
            this.subScreenBrightnessTime[i] = semDevicePowerInfo.subScreenBrightnessTime[i];
            this.subScreenAutoBrightnessTime[i] = semDevicePowerInfo.subScreenAutoBrightnessTime[i];
        }
        this.screenHighBrightnessTime = semDevicePowerInfo.screenHighBrightnessTime;
        this.subScreenHighBrightnessTime = semDevicePowerInfo.subScreenHighBrightnessTime;
        for (int i2 = 0; i2 < CellSignalStrength.getNumSignalStrengthLevels(); i2++) {
            this.signalStrengthTime[i2] = semDevicePowerInfo.signalStrengthTime[i2];
        }
        this.mobileTotalBytes = semDevicePowerInfo.mobileTotalBytes;
        this.mobileTotalPackets = semDevicePowerInfo.mobileTotalPackets;
        this.mobileActiveTime = semDevicePowerInfo.mobileActiveTime;
        this.mobileActiveTime5G = semDevicePowerInfo.mobileActiveTime5G;
        this.mobileActiveCount = semDevicePowerInfo.mobileActiveCount;
        this.wifiTotalBytes = semDevicePowerInfo.wifiTotalBytes;
        this.wifiTotalPackets = semDevicePowerInfo.wifiTotalPackets;
        this.wifiOnTime = semDevicePowerInfo.wifiOnTime;
        this.wifiScanTime = semDevicePowerInfo.wifiScanTime;
        this.wifiScanCount = semDevicePowerInfo.wifiScanCount;
        this.btTotalBytes = semDevicePowerInfo.btTotalBytes;
        this.btOnTime = semDevicePowerInfo.btOnTime;
        this.btScanTime = semDevicePowerInfo.btScanTime;
        this.btScanCount = semDevicePowerInfo.btScanCount;
        this.gpsTime = semDevicePowerInfo.gpsTime;
        this.actualGpsTime = semDevicePowerInfo.actualGpsTime;
        this.screenOnGpsTime = semDevicePowerInfo.screenOnGpsTime;
        this.powershareTime = semDevicePowerInfo.powershareTime;
        this.powersharePower = semDevicePowerInfo.powersharePower;
        this.spkCallTime = semDevicePowerInfo.spkCallTime;
        this.spkCallLevel = semDevicePowerInfo.spkCallLevel;
        this.spkMediaTime = semDevicePowerInfo.spkMediaTime;
        this.spkMediaLevel = semDevicePowerInfo.spkMediaLevel;
        this.hrrAlwaysTime = semDevicePowerInfo.hrrAlwaysTime;
        this.subHrrAlwaysTime = semDevicePowerInfo.subHrrAlwaysTime;
        this.cpSleepTime = semDevicePowerInfo.cpSleepTime;
        this.cpIdleTime = semDevicePowerInfo.cpIdleTime;
        this.nrTxTime = semDevicePowerInfo.nrTxTime;
        this.nrTxLevel = semDevicePowerInfo.nrTxLevel;
        this.nrRxTime = semDevicePowerInfo.nrRxTime;
        this.nrTxByte = semDevicePowerInfo.nrTxByte;
        this.nrRxByte = semDevicePowerInfo.nrRxByte;
        this.lcTxTime = semDevicePowerInfo.lcTxTime;
        this.lcTxLevel = semDevicePowerInfo.lcTxLevel;
        this.lcRxTime = semDevicePowerInfo.lcRxTime;
        this.lcTxByte = semDevicePowerInfo.lcTxByte;
        this.lcRxByte = semDevicePowerInfo.lcRxByte;
        long[][] jArr = this.mKernelCpuSpeedTime;
        if (jArr == null || jArr.length != semDevicePowerInfo.mKernelCpuSpeedTime.length) {
            this.mKernelCpuSpeedTime = new long[semDevicePowerInfo.mKernelCpuSpeedTime.length][];
            int i3 = 0;
            while (true) {
                long[][] jArr2 = semDevicePowerInfo.mKernelCpuSpeedTime;
                if (i3 >= jArr2.length) {
                    break;
                }
                this.mKernelCpuSpeedTime[i3] = new long[jArr2[i3].length];
                i3++;
            }
        }
        int i4 = 0;
        while (true) {
            long[][] jArr3 = semDevicePowerInfo.mKernelCpuSpeedTime;
            if (i4 >= jArr3.length) {
                break;
            }
            long[][] jArr4 = this.mKernelCpuSpeedTime;
            long[] jArr5 = jArr4[i4];
            if (jArr5 == null || jArr5.length != jArr3[i4].length) {
                jArr4[i4] = new long[jArr3[i4].length];
            }
            long[] jArr6 = jArr3[i4];
            long[] jArr7 = jArr4[i4];
            System.arraycopy(jArr6, 0, jArr7, 0, jArr7.length);
            i4++;
        }
        long[][] jArr8 = this.mScreenOffKernelCpuSpeedTime;
        if (jArr8 == null || jArr8.length != semDevicePowerInfo.mScreenOffKernelCpuSpeedTime.length) {
            this.mScreenOffKernelCpuSpeedTime = new long[semDevicePowerInfo.mScreenOffKernelCpuSpeedTime.length][];
            int i5 = 0;
            while (true) {
                long[][] jArr9 = semDevicePowerInfo.mScreenOffKernelCpuSpeedTime;
                if (i5 >= jArr9.length) {
                    break;
                }
                this.mScreenOffKernelCpuSpeedTime[i5] = new long[jArr9[i5].length];
                i5++;
            }
        }
        int i6 = 0;
        while (true) {
            long[][] jArr10 = semDevicePowerInfo.mScreenOffKernelCpuSpeedTime;
            if (i6 >= jArr10.length) {
                return;
            }
            long[][] jArr11 = this.mScreenOffKernelCpuSpeedTime;
            long[] jArr12 = jArr11[i6];
            if (jArr12 == null || jArr12.length != jArr10[i6].length) {
                jArr11[i6] = new long[jArr10[i6].length];
            }
            long[] jArr13 = jArr10[i6];
            long[] jArr14 = jArr11[i6];
            System.arraycopy(jArr13, 0, jArr14, 0, jArr14.length);
            i6++;
        }
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
        this.screenDozeDischarge = 0;
        this.screenOffDischarge = 0;
        this.subScreenOnDischarge = 0;
        this.subScreenDozeDischarge = 0;
        this.screenOnCoulombCounter = 0;
        this.screenOffCoulombCounter = 0;
        this.silentOnScreenOnTime = 0L;
        this.silentOnScreenOffTime = 0L;
        this.silentOnScreenOnDischarge = 0;
        this.silentOnScreenOffDischarge = 0;
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
        this.mKernelCpuSpeedTime = (long[][]) Array.newInstance((Class<?>) Long.TYPE, 0, 0);
        this.mScreenOffKernelCpuSpeedTime = (long[][]) Array.newInstance((Class<?>) Long.TYPE, 0, 0);
    }

    public void addDelta(SemDevicePowerInfo semDevicePowerInfo) {
        long j;
        this.totalPower += semDevicePowerInfo.totalPower;
        this.screenPower += semDevicePowerInfo.screenPower;
        this.aodPower += semDevicePowerInfo.aodPower;
        this.phonePower += semDevicePowerInfo.phonePower;
        this.wifiPower += semDevicePowerInfo.wifiPower;
        this.idlePower += semDevicePowerInfo.idlePower;
        this.idleTime += semDevicePowerInfo.idleTime;
        this.radioPower += semDevicePowerInfo.radioPower;
        this.batteryPerc += semDevicePowerInfo.batteryPerc;
        this.screenOnCount += semDevicePowerInfo.screenOnCount;
        this.screenOnTime += semDevicePowerInfo.screenOnTime;
        this.subScreenOnTime += semDevicePowerInfo.subScreenOnTime;
        this.screenOffTime += semDevicePowerInfo.screenOffTime;
        this.screenOnDischarge += semDevicePowerInfo.screenOnDischarge;
        this.screenDozeDischarge += semDevicePowerInfo.screenDozeDischarge;
        this.screenOffDischarge += semDevicePowerInfo.screenOffDischarge;
        this.subScreenOnDischarge += semDevicePowerInfo.subScreenOnDischarge;
        this.subScreenDozeDischarge += semDevicePowerInfo.subScreenDozeDischarge;
        this.screenOnCoulombCounter += semDevicePowerInfo.screenOnCoulombCounter;
        this.screenOffCoulombCounter += semDevicePowerInfo.screenOffCoulombCounter;
        this.silentOnScreenOnTime += semDevicePowerInfo.silentOnScreenOnTime;
        this.silentOnScreenOffTime += semDevicePowerInfo.silentOnScreenOffTime;
        this.silentOnScreenOnDischarge += semDevicePowerInfo.silentOnScreenOnDischarge;
        this.silentOnScreenOffDischarge += semDevicePowerInfo.silentOnScreenOffDischarge;
        this.uptime += semDevicePowerInfo.uptime;
        this.screenOffUptime += semDevicePowerInfo.screenOffUptime;
        this.phoneOnTime += semDevicePowerInfo.phoneOnTime;
        this.aodTime += semDevicePowerInfo.aodTime;
        this.subAodTime += semDevicePowerInfo.subAodTime;
        this.psmTime += semDevicePowerInfo.psmTime;
        this.pwlTime += semDevicePowerInfo.pwlTime;
        for (int i = 0; i < 5; i++) {
            long[] jArr = this.screenBrightnessTime;
            jArr[i] = jArr[i] + semDevicePowerInfo.screenBrightnessTime[i];
            long[] jArr2 = this.screenAutoBrightnessTime;
            jArr2[i] = jArr2[i] + semDevicePowerInfo.screenAutoBrightnessTime[i];
            long[] jArr3 = this.subScreenBrightnessTime;
            jArr3[i] = jArr3[i] + semDevicePowerInfo.subScreenBrightnessTime[i];
            long[] jArr4 = this.subScreenAutoBrightnessTime;
            jArr4[i] = jArr4[i] + semDevicePowerInfo.subScreenAutoBrightnessTime[i];
        }
        this.screenHighBrightnessTime += semDevicePowerInfo.screenHighBrightnessTime;
        this.subScreenHighBrightnessTime += semDevicePowerInfo.subScreenHighBrightnessTime;
        for (int i2 = 0; i2 < CellSignalStrength.getNumSignalStrengthLevels(); i2++) {
            long[] jArr5 = this.signalStrengthTime;
            jArr5[i2] = jArr5[i2] + semDevicePowerInfo.signalStrengthTime[i2];
        }
        this.mobileTotalBytes += semDevicePowerInfo.mobileTotalBytes;
        this.mobileTotalPackets += semDevicePowerInfo.mobileTotalPackets;
        this.mobileActiveTime += semDevicePowerInfo.mobileActiveTime;
        this.mobileActiveTime5G += semDevicePowerInfo.mobileActiveTime5G;
        this.mobileActiveCount += semDevicePowerInfo.mobileActiveCount;
        this.wifiTotalBytes += semDevicePowerInfo.wifiTotalBytes;
        this.wifiTotalPackets += semDevicePowerInfo.wifiTotalPackets;
        this.wifiOnTime += semDevicePowerInfo.wifiOnTime;
        this.wifiScanTime += semDevicePowerInfo.wifiScanTime;
        this.wifiScanCount += semDevicePowerInfo.wifiScanCount;
        this.btTotalBytes += semDevicePowerInfo.btTotalBytes;
        this.btOnTime += semDevicePowerInfo.btOnTime;
        this.btScanTime += semDevicePowerInfo.btScanTime;
        this.btScanCount += semDevicePowerInfo.btScanCount;
        this.gpsTime += semDevicePowerInfo.gpsTime;
        this.actualGpsTime += semDevicePowerInfo.actualGpsTime;
        this.screenOnGpsTime += semDevicePowerInfo.screenOnGpsTime;
        this.powershareTime += semDevicePowerInfo.powershareTime;
        this.powersharePower += semDevicePowerInfo.powersharePower;
        this.spkCallTime += semDevicePowerInfo.spkCallTime;
        this.spkCallLevel += semDevicePowerInfo.spkCallLevel;
        this.spkMediaTime += semDevicePowerInfo.spkMediaTime;
        this.spkMediaLevel += semDevicePowerInfo.spkMediaLevel;
        this.hrrAlwaysTime += semDevicePowerInfo.hrrAlwaysTime;
        this.subHrrAlwaysTime += semDevicePowerInfo.subHrrAlwaysTime;
        this.cpSleepTime += semDevicePowerInfo.cpSleepTime;
        this.cpIdleTime += semDevicePowerInfo.cpIdleTime;
        long j2 = this.nrTxTime + semDevicePowerInfo.nrTxTime;
        this.nrTxTime = j2;
        long j3 = semDevicePowerInfo.nrTxTime;
        if (j2 + j3 != 0) {
            j = 0;
            this.nrTxLevel = ((this.nrTxLevel * j2) + (semDevicePowerInfo.nrTxLevel * j3)) / (j2 + j3);
        } else {
            j = 0;
            this.nrTxLevel = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }
        this.nrRxTime += semDevicePowerInfo.nrRxTime;
        this.nrTxByte += semDevicePowerInfo.nrTxByte;
        this.nrRxByte += semDevicePowerInfo.nrRxByte;
        long j4 = this.lcTxTime + semDevicePowerInfo.lcTxTime;
        this.lcTxTime = j4;
        long j5 = semDevicePowerInfo.lcTxTime;
        if (j4 + j5 != j) {
            this.lcTxLevel = ((this.lcTxLevel * j4) + (semDevicePowerInfo.lcTxLevel * j5)) / (j4 + j5);
        } else {
            this.lcTxLevel = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }
        this.lcRxTime += semDevicePowerInfo.lcRxTime;
        this.lcTxByte += semDevicePowerInfo.lcTxByte;
        this.lcRxByte += semDevicePowerInfo.lcRxByte;
        long[][] jArr6 = this.mKernelCpuSpeedTime;
        if (jArr6 != null && jArr6.length == semDevicePowerInfo.mKernelCpuSpeedTime.length) {
            int i3 = 0;
            while (true) {
                long[][] jArr7 = semDevicePowerInfo.mKernelCpuSpeedTime;
                if (i3 >= jArr7.length) {
                    break;
                }
                long[][] jArr8 = this.mKernelCpuSpeedTime;
                long[] jArr9 = jArr8[i3];
                if (jArr9 == null || jArr9.length != jArr7[i3].length) {
                    long[] jArr10 = new long[jArr7[i3].length];
                    jArr8[i3] = jArr10;
                    System.arraycopy(jArr7[i3], 0, jArr10, 0, jArr10.length);
                } else {
                    int i4 = 0;
                    while (true) {
                        long[] jArr11 = semDevicePowerInfo.mKernelCpuSpeedTime[i3];
                        if (i4 < jArr11.length) {
                            long[] jArr12 = this.mKernelCpuSpeedTime[i3];
                            jArr12[i4] = jArr12[i4] + jArr11[i4];
                            i4++;
                        }
                    }
                }
                i3++;
            }
        } else {
            this.mKernelCpuSpeedTime = new long[semDevicePowerInfo.mKernelCpuSpeedTime.length][];
            int i5 = 0;
            while (true) {
                long[][] jArr13 = semDevicePowerInfo.mKernelCpuSpeedTime;
                if (i5 >= jArr13.length) {
                    break;
                }
                long[][] jArr14 = this.mKernelCpuSpeedTime;
                long[] jArr15 = new long[jArr13[i5].length];
                jArr14[i5] = jArr15;
                System.arraycopy(jArr13[i5], 0, jArr15, 0, jArr15.length);
                i5++;
            }
        }
        long[][] jArr16 = this.mScreenOffKernelCpuSpeedTime;
        if (jArr16 == null || jArr16.length != semDevicePowerInfo.mScreenOffKernelCpuSpeedTime.length) {
            this.mScreenOffKernelCpuSpeedTime = new long[semDevicePowerInfo.mScreenOffKernelCpuSpeedTime.length][];
            int i6 = 0;
            while (true) {
                long[][] jArr17 = semDevicePowerInfo.mScreenOffKernelCpuSpeedTime;
                if (i6 >= jArr17.length) {
                    return;
                }
                long[][] jArr18 = this.mScreenOffKernelCpuSpeedTime;
                long[] jArr19 = new long[jArr17[i6].length];
                jArr18[i6] = jArr19;
                System.arraycopy(jArr17[i6], 0, jArr19, 0, jArr19.length);
                i6++;
            }
        } else {
            int i7 = 0;
            while (true) {
                long[][] jArr20 = semDevicePowerInfo.mScreenOffKernelCpuSpeedTime;
                if (i7 >= jArr20.length) {
                    return;
                }
                long[][] jArr21 = this.mScreenOffKernelCpuSpeedTime;
                long[] jArr22 = jArr21[i7];
                if (jArr22 == null || jArr22.length != jArr20[i7].length) {
                    long[] jArr23 = new long[jArr20[i7].length];
                    jArr21[i7] = jArr23;
                    System.arraycopy(jArr20[i7], 0, jArr23, 0, jArr23.length);
                } else {
                    int i8 = 0;
                    while (true) {
                        long[] jArr24 = semDevicePowerInfo.mScreenOffKernelCpuSpeedTime[i7];
                        if (i8 < jArr24.length) {
                            long[] jArr25 = this.mScreenOffKernelCpuSpeedTime[i7];
                            jArr25[i8] = jArr25[i8] + jArr24[i8];
                            i8++;
                        }
                    }
                }
                i7++;
            }
        }
    }

    protected SemDevicePowerInfo(Parcel parcel) {
        this.screenBrightnessTime = new long[5];
        this.screenAutoBrightnessTime = new long[5];
        this.subScreenBrightnessTime = new long[5];
        this.subScreenAutoBrightnessTime = new long[5];
        this.signalStrengthTime = new long[CellSignalStrength.getNumSignalStrengthLevels()];
        this.totalPower = parcel.readDouble();
        this.screenPower = parcel.readDouble();
        this.aodPower = parcel.readDouble();
        this.phonePower = parcel.readDouble();
        this.wifiPower = parcel.readDouble();
        this.idlePower = parcel.readDouble();
        this.idleTime = parcel.readLong();
        this.radioPower = parcel.readDouble();
        this.batteryPerc = parcel.readInt();
        this.screenOnCount = parcel.readInt();
        this.screenOnTime = parcel.readLong();
        this.subScreenOnTime = parcel.readLong();
        this.screenOffTime = parcel.readLong();
        this.screenOnDischarge = parcel.readInt();
        this.screenDozeDischarge = parcel.readInt();
        this.screenOffDischarge = parcel.readInt();
        this.subScreenOnDischarge = parcel.readInt();
        this.subScreenDozeDischarge = parcel.readInt();
        this.screenOnCoulombCounter = parcel.readInt();
        this.screenOffCoulombCounter = parcel.readInt();
        this.silentOnScreenOnTime = parcel.readLong();
        this.silentOnScreenOffTime = parcel.readLong();
        this.silentOnScreenOnDischarge = parcel.readInt();
        this.silentOnScreenOffDischarge = parcel.readInt();
        this.uptime = parcel.readLong();
        this.screenOffUptime = parcel.readLong();
        this.phoneOnTime = parcel.readLong();
        this.aodTime = parcel.readLong();
        this.subAodTime = parcel.readLong();
        this.psmTime = parcel.readLong();
        this.pwlTime = parcel.readLong();
        for (int i = 0; i < 5; i++) {
            this.screenBrightnessTime[i] = parcel.readLong();
        }
        for (int i2 = 0; i2 < 5; i2++) {
            this.screenAutoBrightnessTime[i2] = parcel.readLong();
        }
        this.screenHighBrightnessTime = parcel.readLong();
        for (int i3 = 0; i3 < 5; i3++) {
            this.subScreenBrightnessTime[i3] = parcel.readLong();
        }
        for (int i4 = 0; i4 < 5; i4++) {
            this.subScreenAutoBrightnessTime[i4] = parcel.readLong();
        }
        this.subScreenHighBrightnessTime = parcel.readLong();
        for (int i5 = 0; i5 < CellSignalStrength.getNumSignalStrengthLevels(); i5++) {
            this.signalStrengthTime[i5] = parcel.readLong();
        }
        this.mobileTotalBytes = parcel.readLong();
        this.mobileTotalPackets = parcel.readLong();
        this.mobileActiveTime = parcel.readLong();
        this.mobileActiveTime5G = parcel.readLong();
        this.mobileActiveCount = parcel.readInt();
        this.wifiTotalBytes = parcel.readLong();
        this.wifiTotalPackets = parcel.readLong();
        this.wifiOnTime = parcel.readLong();
        this.wifiScanTime = parcel.readLong();
        this.wifiScanCount = parcel.readInt();
        this.btTotalBytes = parcel.readLong();
        this.btOnTime = parcel.readLong();
        this.btScanTime = parcel.readLong();
        this.btScanCount = parcel.readInt();
        this.gpsTime = parcel.readLong();
        this.actualGpsTime = parcel.readLong();
        this.screenOnGpsTime = parcel.readLong();
        this.powershareTime = parcel.readLong();
        this.powersharePower = parcel.readLong();
        this.spkCallTime = parcel.readLong();
        this.spkCallLevel = parcel.readLong();
        this.spkMediaTime = parcel.readLong();
        this.spkMediaLevel = parcel.readLong();
        this.hrrAlwaysTime = parcel.readLong();
        this.cpSleepTime = parcel.readLong();
        this.cpIdleTime = parcel.readLong();
        this.nrTxTime = parcel.readLong();
        this.nrTxLevel = parcel.readDouble();
        this.nrRxTime = parcel.readLong();
        this.nrTxByte = parcel.readLong();
        this.nrRxByte = parcel.readLong();
        this.lcTxTime = parcel.readLong();
        this.lcTxLevel = parcel.readDouble();
        this.lcRxTime = parcel.readLong();
        this.lcTxByte = parcel.readLong();
        this.lcRxByte = parcel.readLong();
        int readInt = parcel.readInt();
        if (readInt == 0) {
            this.mKernelCpuSpeedTime = (long[][]) Array.newInstance((Class<?>) Long.TYPE, 0, 0);
        } else {
            this.mKernelCpuSpeedTime = new long[readInt][];
            for (int i6 = 0; i6 < readInt; i6++) {
                int readInt2 = parcel.readInt();
                if (readInt2 == 0) {
                    this.mKernelCpuSpeedTime[i6] = new long[0];
                } else {
                    this.mKernelCpuSpeedTime[i6] = new long[readInt2];
                    for (int i7 = 0; i7 < readInt2; i7++) {
                        this.mKernelCpuSpeedTime[i6][i7] = parcel.readLong();
                    }
                }
            }
        }
        int readInt3 = parcel.readInt();
        if (readInt3 == 0) {
            this.mScreenOffKernelCpuSpeedTime = (long[][]) Array.newInstance((Class<?>) Long.TYPE, 0, 0);
            return;
        }
        this.mScreenOffKernelCpuSpeedTime = new long[readInt3][];
        for (int i8 = 0; i8 < readInt3; i8++) {
            int readInt4 = parcel.readInt();
            if (readInt4 == 0) {
                this.mScreenOffKernelCpuSpeedTime[i8] = new long[0];
            } else {
                this.mScreenOffKernelCpuSpeedTime[i8] = new long[readInt4];
                for (int i9 = 0; i9 < readInt4; i9++) {
                    this.mScreenOffKernelCpuSpeedTime[i8][i9] = parcel.readLong();
                }
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.totalPower);
        parcel.writeDouble(this.screenPower);
        parcel.writeDouble(this.aodPower);
        parcel.writeDouble(this.phonePower);
        parcel.writeDouble(this.wifiPower);
        parcel.writeDouble(this.idlePower);
        parcel.writeLong(this.idleTime);
        parcel.writeDouble(this.radioPower);
        parcel.writeInt(this.batteryPerc);
        parcel.writeInt(this.screenOnCount);
        parcel.writeLong(this.screenOnTime);
        parcel.writeLong(this.subScreenOnTime);
        parcel.writeLong(this.screenOffTime);
        parcel.writeInt(this.screenOnDischarge);
        parcel.writeInt(this.screenDozeDischarge);
        parcel.writeInt(this.screenOffDischarge);
        parcel.writeInt(this.subScreenOnDischarge);
        parcel.writeInt(this.subScreenDozeDischarge);
        parcel.writeInt(this.screenOnCoulombCounter);
        parcel.writeInt(this.screenOffCoulombCounter);
        parcel.writeLong(this.silentOnScreenOnTime);
        parcel.writeLong(this.silentOnScreenOffTime);
        parcel.writeInt(this.silentOnScreenOnDischarge);
        parcel.writeInt(this.silentOnScreenOffDischarge);
        parcel.writeLong(this.uptime);
        parcel.writeLong(this.screenOffUptime);
        parcel.writeLong(this.phoneOnTime);
        parcel.writeLong(this.aodTime);
        parcel.writeLong(this.subAodTime);
        parcel.writeLong(this.psmTime);
        parcel.writeLong(this.pwlTime);
        for (int i2 = 0; i2 < 5; i2++) {
            parcel.writeLong(this.screenBrightnessTime[i2]);
        }
        for (int i3 = 0; i3 < 5; i3++) {
            parcel.writeLong(this.screenAutoBrightnessTime[i3]);
        }
        parcel.writeLong(this.screenHighBrightnessTime);
        for (int i4 = 0; i4 < 5; i4++) {
            parcel.writeLong(this.subScreenBrightnessTime[i4]);
        }
        for (int i5 = 0; i5 < 5; i5++) {
            parcel.writeLong(this.subScreenAutoBrightnessTime[i5]);
        }
        parcel.writeLong(this.subScreenHighBrightnessTime);
        for (int i6 = 0; i6 < CellSignalStrength.getNumSignalStrengthLevels(); i6++) {
            parcel.writeLong(this.signalStrengthTime[i6]);
        }
        parcel.writeLong(this.mobileTotalBytes);
        parcel.writeLong(this.mobileTotalPackets);
        parcel.writeLong(this.mobileActiveTime);
        parcel.writeLong(this.mobileActiveTime5G);
        parcel.writeInt(this.mobileActiveCount);
        parcel.writeLong(this.wifiTotalBytes);
        parcel.writeLong(this.wifiTotalPackets);
        parcel.writeLong(this.wifiOnTime);
        parcel.writeLong(this.wifiScanTime);
        parcel.writeInt(this.wifiScanCount);
        parcel.writeLong(this.btTotalBytes);
        parcel.writeLong(this.btOnTime);
        parcel.writeLong(this.btScanTime);
        parcel.writeInt(this.btScanCount);
        parcel.writeLong(this.gpsTime);
        parcel.writeLong(this.actualGpsTime);
        parcel.writeLong(this.screenOnGpsTime);
        parcel.writeLong(this.powershareTime);
        parcel.writeLong(this.powersharePower);
        parcel.writeLong(this.spkCallTime);
        parcel.writeLong(this.spkCallLevel);
        parcel.writeLong(this.spkMediaTime);
        parcel.writeLong(this.spkMediaLevel);
        parcel.writeLong(this.hrrAlwaysTime);
        parcel.writeLong(this.cpSleepTime);
        parcel.writeLong(this.cpIdleTime);
        parcel.writeLong(this.nrTxTime);
        parcel.writeDouble(this.nrTxLevel);
        parcel.writeLong(this.nrRxTime);
        parcel.writeLong(this.nrTxByte);
        parcel.writeLong(this.nrRxByte);
        parcel.writeLong(this.lcTxTime);
        parcel.writeDouble(this.lcTxLevel);
        parcel.writeLong(this.lcRxTime);
        parcel.writeLong(this.lcTxByte);
        parcel.writeLong(this.lcRxByte);
        long[][] jArr = this.mKernelCpuSpeedTime;
        if (jArr == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(jArr.length);
            int i7 = 0;
            while (true) {
                long[][] jArr2 = this.mKernelCpuSpeedTime;
                if (i7 >= jArr2.length) {
                    break;
                }
                long[] jArr3 = jArr2[i7];
                if (jArr3 == null) {
                    parcel.writeInt(0);
                } else {
                    parcel.writeInt(jArr3.length);
                    int i8 = 0;
                    while (true) {
                        long[] jArr4 = this.mKernelCpuSpeedTime[i7];
                        if (i8 < jArr4.length) {
                            parcel.writeLong(jArr4[i8]);
                            i8++;
                        }
                    }
                }
                i7++;
            }
        }
        long[][] jArr5 = this.mScreenOffKernelCpuSpeedTime;
        if (jArr5 == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(jArr5.length);
        int i9 = 0;
        while (true) {
            long[][] jArr6 = this.mScreenOffKernelCpuSpeedTime;
            if (i9 >= jArr6.length) {
                return;
            }
            long[] jArr7 = jArr6[i9];
            if (jArr7 == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(jArr7.length);
                int i10 = 0;
                while (true) {
                    long[] jArr8 = this.mScreenOffKernelCpuSpeedTime[i9];
                    if (i10 < jArr8.length) {
                        parcel.writeLong(jArr8[i10]);
                        i10++;
                    }
                }
            }
            i9++;
        }
    }
}
