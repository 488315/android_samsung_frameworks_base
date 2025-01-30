package android.os;


/* loaded from: classes3.dex */
public final class ForegroundAppEnergyInfo implements Parcelable {
  private int mBatteryDischargeUah;
  private int mDisplayPowerUah;
  private long mDuration;
  private long mEndTime;
  private long mStartTime;
  private int mUid;
  private static final String TAG = ForegroundAppEnergyInfo.class.getSimpleName();
  public static final Parcelable.Creator<ForegroundAppEnergyInfo> CREATOR =
      new Parcelable.Creator<
          ForegroundAppEnergyInfo>() { // from class: android.os.ForegroundAppEnergyInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ForegroundAppEnergyInfo createFromParcel(Parcel in) {
          return new ForegroundAppEnergyInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ForegroundAppEnergyInfo[] newArray(int size) {
          return new ForegroundAppEnergyInfo[size];
        }
      };

  public ForegroundAppEnergyInfo() {
    initialize();
  }

  public ForegroundAppEnergyInfo(Parcel in) {
    initialize();
    readFromParcel(in);
  }

  public ForegroundAppEnergyInfo(
      int uid,
      long startTime,
      long endTime,
      long duration,
      int displayPowerUah,
      int batteryDischargeUah) {
    this.mUid = uid;
    this.mStartTime = startTime;
    this.mEndTime = endTime;
    this.mDuration = duration;
    this.mDisplayPowerUah = displayPowerUah;
    this.mBatteryDischargeUah = batteryDischargeUah;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel out, int flags) {
    out.writeInt(this.mUid);
    out.writeLong(this.mStartTime);
    out.writeLong(this.mEndTime);
    out.writeLong(this.mDuration);
    out.writeInt(this.mDisplayPowerUah);
    out.writeInt(this.mBatteryDischargeUah);
  }

  public void readFromParcel(Parcel in) {
    this.mUid = in.readInt();
    this.mStartTime = in.readLong();
    this.mEndTime = in.readLong();
    this.mDuration = in.readLong();
    this.mDisplayPowerUah = in.readInt();
    this.mBatteryDischargeUah = in.readInt();
  }

  public void setUid(int n) {
    this.mUid = n;
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

  public void startTimer(long elapsedRealtime) {
    this.mStartTime = elapsedRealtime;
    this.mEndTime = -1L;
  }

  public void stopTimer(long elapsedRealtime) {
    long currentDuration = 0;
    if (isTimerRunning()) {
      this.mEndTime = elapsedRealtime;
      currentDuration = elapsedRealtime - this.mStartTime;
    }
    this.mDuration += currentDuration;
  }

  public boolean isTimerRunning() {
    if (this.mStartTime != -1 && this.mEndTime == -1) {
      return true;
    }
    return false;
  }

  public long getDuration() {
    return this.mDuration;
  }

  public void setDisplayPower(int powerUah) {
    this.mDisplayPowerUah = powerUah;
  }

  public int getDisplayPower() {
    return this.mDisplayPowerUah;
  }

  public void setBatteryDischarged(int dischargedUah) {
    this.mBatteryDischargeUah = dischargedUah;
  }

  public int getBatteryDischarged() {
    return this.mBatteryDischargeUah;
  }

  public void reset() {
    initialize();
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
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
