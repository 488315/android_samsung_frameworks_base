package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class ForegroundAppEnergyInfo implements Parcelable {
    public static final Parcelable.Creator<ForegroundAppEnergyInfo> CREATOR = new Parcelable.Creator<ForegroundAppEnergyInfo>() { // from class: android.os.ForegroundAppEnergyInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ForegroundAppEnergyInfo createFromParcel(Parcel parcel) {
            return new ForegroundAppEnergyInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ForegroundAppEnergyInfo[] newArray(int i) {
            return new ForegroundAppEnergyInfo[i];
        }
    };
    private static final String TAG = "ForegroundAppEnergyInfo";
    private int mBatteryDischargeUah;
    private int mDisplayPowerUah;
    private long mDuration;
    private long mEndTime;
    private long mStartTime;
    private int mUid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ForegroundAppEnergyInfo() {
        initialize();
    }

    public ForegroundAppEnergyInfo(Parcel parcel) {
        initialize();
        readFromParcel(parcel);
    }

    public ForegroundAppEnergyInfo(int i, long j, long j2, long j3, int i2, int i3) {
        this.mUid = i;
        this.mStartTime = j;
        this.mEndTime = j2;
        this.mDuration = j3;
        this.mDisplayPowerUah = i2;
        this.mBatteryDischargeUah = i3;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mUid);
        parcel.writeLong(this.mStartTime);
        parcel.writeLong(this.mEndTime);
        parcel.writeLong(this.mDuration);
        parcel.writeInt(this.mDisplayPowerUah);
        parcel.writeInt(this.mBatteryDischargeUah);
    }

    public void readFromParcel(Parcel parcel) {
        this.mUid = parcel.readInt();
        this.mStartTime = parcel.readLong();
        this.mEndTime = parcel.readLong();
        this.mDuration = parcel.readLong();
        this.mDisplayPowerUah = parcel.readInt();
        this.mBatteryDischargeUah = parcel.readInt();
    }

    public void setUid(int i) {
        this.mUid = i;
    }

    public int getUid() {
        return this.mUid;
    }

    public long getStartTime() {
        return this.mStartTime;
    }

    public long getEndTime() {
        return this.mEndTime;
    }

    public void startTimer(long j) {
        this.mStartTime = j;
        this.mEndTime = -1L;
    }

    public void stopTimer(long j) {
        long j2;
        if (isTimerRunning()) {
            this.mEndTime = j;
            j2 = j - this.mStartTime;
        } else {
            j2 = 0;
        }
        this.mDuration += j2;
    }

    public boolean isTimerRunning() {
        return this.mStartTime != -1 && this.mEndTime == -1;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public void setDisplayPower(int i) {
        this.mDisplayPowerUah = i;
    }

    public int getDisplayPower() {
        return this.mDisplayPowerUah;
    }

    public void setBatteryDischarged(int i) {
        this.mBatteryDischargeUah = i;
    }

    public int getBatteryDischarged() {
        return this.mBatteryDischargeUah;
    }

    public void reset() {
        initialize();
    }

    private void initialize() {
        this.mUid = -1;
        this.mEndTime = -1L;
        this.mStartTime = -1L;
        this.mDuration = 0L;
        this.mDisplayPowerUah = 0;
        this.mBatteryDischargeUah = 0;
    }
}
