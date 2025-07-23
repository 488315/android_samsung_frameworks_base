package android.os.connectivity;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.ContextImpl;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.os.PowerProfile;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class WifiActivityEnergyInfo implements Parcelable {
    public static final Parcelable.Creator<WifiActivityEnergyInfo> CREATOR = new Parcelable.Creator<WifiActivityEnergyInfo>() { // from class: android.os.connectivity.WifiActivityEnergyInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WifiActivityEnergyInfo createFromParcel(Parcel parcel) {
            return new WifiActivityEnergyInfo(parcel.readLong(), parcel.readInt(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WifiActivityEnergyInfo[] newArray(int i) {
            return new WifiActivityEnergyInfo[i];
        }
    };
    private static final long DEFERRED_ENERGY_ESTIMATE = -1;
    public static final int STACK_STATE_INVALID = 0;
    public static final int STACK_STATE_STATE_ACTIVE = 1;
    public static final int STACK_STATE_STATE_IDLE = 3;
    public static final int STACK_STATE_STATE_SCANNING = 2;
    private long mControllerEnergyUsedMicroJoules;
    private final long mControllerIdleDurationMillis;
    private final long mControllerRxDurationMillis;
    private final long mControllerScanDurationMillis;
    private final long mControllerTxDurationMillis;
    private final int mStackState;
    private final long mTimeSinceBootMillis;

    @Retention(RetentionPolicy.SOURCE)
    public @interface StackState {
    }

    private static long calculateEnergyMicroJoules$ravenwood(long j, long j2, long j3) {
        return 0L;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WifiActivityEnergyInfo(long j, int i, long j2, long j3, long j4, long j5) {
        this(j, i, j2, j3, j4, j5, -1L);
    }

    private static long calculateEnergyMicroJoules(long j, long j2, long j3) {
        ContextImpl systemContext = ActivityThread.currentActivityThread().getSystemContext();
        if (systemContext == null) {
            return 0L;
        }
        PowerProfile powerProfile = new PowerProfile(systemContext);
        double averagePower = powerProfile.getAveragePower(PowerProfile.POWER_WIFI_CONTROLLER_IDLE);
        return (long) (((j * powerProfile.getAveragePower(PowerProfile.POWER_WIFI_CONTROLLER_TX)) + (j2 * powerProfile.getAveragePower(PowerProfile.POWER_WIFI_CONTROLLER_RX)) + (j3 * averagePower)) * (powerProfile.getAveragePower(PowerProfile.POWER_WIFI_CONTROLLER_OPERATING_VOLTAGE) / 1000.0d));
    }

    public WifiActivityEnergyInfo(long j, int i, long j2, long j3, long j4, long j5, long j6) {
        this.mTimeSinceBootMillis = j;
        this.mStackState = i;
        this.mControllerTxDurationMillis = j2;
        this.mControllerRxDurationMillis = j3;
        this.mControllerScanDurationMillis = j4;
        this.mControllerIdleDurationMillis = j5;
        this.mControllerEnergyUsedMicroJoules = j6;
    }

    public String toString() {
        return "WifiActivityEnergyInfo{ mTimeSinceBootMillis=" + this.mTimeSinceBootMillis + " mStackState=" + this.mStackState + " mControllerTxDurationMillis=" + this.mControllerTxDurationMillis + " mControllerRxDurationMillis=" + this.mControllerRxDurationMillis + " mControllerScanDurationMillis=" + this.mControllerScanDurationMillis + " mControllerIdleDurationMillis=" + this.mControllerIdleDurationMillis + " mControllerEnergyUsedMicroJoules=" + getControllerEnergyUsedMicroJoules() + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mTimeSinceBootMillis);
        parcel.writeInt(this.mStackState);
        parcel.writeLong(this.mControllerTxDurationMillis);
        parcel.writeLong(this.mControllerRxDurationMillis);
        parcel.writeLong(this.mControllerScanDurationMillis);
        parcel.writeLong(this.mControllerIdleDurationMillis);
    }

    public long getTimeSinceBootMillis() {
        return this.mTimeSinceBootMillis;
    }

    public int getStackState() {
        return this.mStackState;
    }

    public long getControllerTxDurationMillis() {
        return this.mControllerTxDurationMillis;
    }

    public long getControllerRxDurationMillis() {
        return this.mControllerRxDurationMillis;
    }

    public long getControllerScanDurationMillis() {
        return this.mControllerScanDurationMillis;
    }

    public long getControllerIdleDurationMillis() {
        return this.mControllerIdleDurationMillis;
    }

    public long getControllerEnergyUsedMicroJoules() {
        if (this.mControllerEnergyUsedMicroJoules == -1) {
            this.mControllerEnergyUsedMicroJoules = calculateEnergyMicroJoules(this.mControllerTxDurationMillis, this.mControllerRxDurationMillis, this.mControllerIdleDurationMillis);
        }
        return this.mControllerEnergyUsedMicroJoules;
    }

    public boolean isValid() {
        return this.mControllerTxDurationMillis >= 0 && this.mControllerRxDurationMillis >= 0 && this.mControllerScanDurationMillis >= 0 && this.mControllerIdleDurationMillis >= 0;
    }
}
