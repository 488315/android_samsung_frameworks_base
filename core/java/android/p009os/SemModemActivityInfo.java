package android.p009os;

import java.util.Arrays;

/* loaded from: classes3.dex */
public class SemModemActivityInfo implements Parcelable {
  public static final Parcelable.Creator<SemModemActivityInfo> CREATOR =
      new Parcelable.Creator<
          SemModemActivityInfo>() { // from class: android.os.SemModemActivityInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemModemActivityInfo createFromParcel(Parcel in) {
          return new SemModemActivityInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemModemActivityInfo[] newArray(int size) {
          return new SemModemActivityInfo[size];
        }
      };
  public static final int TX_POWER_LEVELS = 5;
  private int mIdleTimeMs;
  private int mSleepTimeMs;
  MobileActivity mNr = new MobileActivity(5);
  MobileActivity mLc = new MobileActivity(5);

  public SemModemActivityInfo(
      int sleepTimeMs, int idleTimeMs, MobileActivity nr, MobileActivity lc) {
    this.mSleepTimeMs = sleepTimeMs;
    this.mIdleTimeMs = idleTimeMs;
    if (nr != null) {
      System.arraycopy(
          nr.getTxTimeMillis(),
          0,
          this.mNr.getTxTimeMillis(),
          0,
          Math.min(nr.getTxTimeLength(), 5));
      this.mNr.setRxTimeMillis(nr.getRxTimeMillis());
      this.mNr.setTxBytes(nr.getTxBytes());
      this.mNr.setRxBytes(nr.getRxBytes());
    }
    if (lc != null) {
      System.arraycopy(
          lc.getTxTimeMillis(),
          0,
          this.mLc.getTxTimeMillis(),
          0,
          Math.min(lc.getTxTimeLength(), 5));
      this.mLc.setRxTimeMillis(lc.getRxTimeMillis());
      this.mLc.setTxBytes(lc.getTxBytes());
      this.mLc.setRxBytes(lc.getRxBytes());
    }
  }

  public String toString() {
    return "SemModemActivityInfo{ SleepTimeMs="
        + this.mSleepTimeMs
        + " IdleTimeMs="
        + this.mIdleTimeMs
        + " mNr="
        + this.mNr.toString()
        + " mLc="
        + this.mLc.toString()
        + " }";
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mSleepTimeMs);
    dest.writeInt(this.mIdleTimeMs);
    dest.writeTypedObject(this.mNr, flags);
    dest.writeTypedObject(this.mLc, flags);
  }

  public SemModemActivityInfo(Parcel in) {
    readFromParcel(in);
  }

  public void readFromParcel(Parcel in) {
    this.mSleepTimeMs = in.readInt();
    this.mIdleTimeMs = in.readInt();
    this.mNr = (MobileActivity) in.readTypedObject(MobileActivity.CREATOR);
    this.mLc = (MobileActivity) in.readTypedObject(MobileActivity.CREATOR);
  }

  public int getSleepTimeMillis() {
    return this.mSleepTimeMs;
  }

  public void setSleepTimeMillis(int sleepTimeMillis) {
    this.mSleepTimeMs = sleepTimeMillis;
  }

  public int getIdleTimeMillis() {
    return this.mIdleTimeMs;
  }

  public void setIdleTimeMillis(int idleTimeMillis) {
    this.mIdleTimeMs = idleTimeMillis;
  }

  public int[] getNrTxTimeMillis() {
    return this.mNr.getTxTimeMillis();
  }

  public void setNrTxTimeMillis(int[] txTimeMs) {
    this.mNr.setTxTimeMillis(txTimeMs);
  }

  public int getNrRxTimeMillis() {
    return this.mNr.getRxTimeMillis();
  }

  public void setNrRxTimeMillis(int rxTimeMs) {
    this.mNr.setRxTimeMillis(rxTimeMs);
  }

  public long getNrTxBytes() {
    return this.mNr.getTxBytes();
  }

  public void setNrTxBytes(long txBytes) {
    this.mNr.setTxBytes(txBytes);
  }

  public long getNrRxBytes() {
    return this.mNr.getRxBytes();
  }

  public void setNrRxBytes(long rxBytes) {
    this.mNr.setRxBytes(rxBytes);
  }

  public int[] getLcTxTimeMillis() {
    return this.mLc.getTxTimeMillis();
  }

  public void setLcTxTimeMillis(int[] txTimeMs) {
    this.mLc.setTxTimeMillis(txTimeMs);
  }

  public int getLcRxTimeMillis() {
    return this.mLc.getRxTimeMillis();
  }

  public void setLcRxTimeMillis(int rxTimeMs) {
    this.mLc.setRxTimeMillis(rxTimeMs);
  }

  public long getLcTxBytes() {
    return this.mLc.getTxBytes();
  }

  public void setLcTxBytes(long txBytes) {
    this.mLc.setTxBytes(txBytes);
  }

  public long getLcRxBytes() {
    return this.mLc.getRxBytes();
  }

  public void setLcRxBytes(long rxBytes) {
    this.mLc.setRxBytes(rxBytes);
  }

  public boolean isValid() {
    for (int txVal : this.mNr.getTxTimeMillis()) {
      if (txVal < 0) {
        return false;
      }
    }
    for (int txVal2 : this.mLc.getTxTimeMillis()) {
      if (txVal2 < 0) {
        return false;
      }
    }
    return getIdleTimeMillis() >= 0
        && getSleepTimeMillis() >= 0
        && this.mNr.getRxTimeMillis() >= 0
        && this.mNr.getTxBytes() >= 0
        && this.mNr.getRxBytes() >= 0
        && this.mLc.getRxTimeMillis() >= 0
        && this.mLc.getTxBytes() >= 0
        && this.mLc.getRxBytes() >= 0
        && !isEmpty();
  }

  private boolean isEmpty() {
    for (int txVal : this.mNr.getTxTimeMillis()) {
      if (txVal != 0) {
        return false;
      }
    }
    for (int txVal2 : this.mLc.getTxTimeMillis()) {
      if (txVal2 != 0) {
        return false;
      }
    }
    return getIdleTimeMillis() == 0
        && getSleepTimeMillis() == 0
        && this.mNr.getRxTimeMillis() == 0
        && this.mNr.getTxBytes() == 0
        && this.mNr.getRxBytes() == 0
        && this.mLc.getRxTimeMillis() == 0
        && this.mLc.getTxBytes() == 0
        && this.mLc.getRxBytes() == 0;
  }

  public static class MobileActivity implements Parcelable {
    public static final Parcelable.Creator<MobileActivity> CREATOR =
        new Parcelable.Creator<
            MobileActivity>() { // from class: android.os.SemModemActivityInfo.MobileActivity.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public MobileActivity createFromParcel(Parcel in) {
            return new MobileActivity(in);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public MobileActivity[] newArray(int size) {
            return new MobileActivity[size];
          }
        };
    private long mRxBytes;
    private int mRxTimeMs;
    private long mTxBytes;
    private int[] mTxTimeMs;

    public MobileActivity(int levels) {
      this.mTxTimeMs = new int[levels];
      this.mRxTimeMs = 0;
      this.mTxBytes = 0L;
      this.mRxBytes = 0L;
    }

    public String toString() {
      return "MobileActivity{ txTimeMs[]="
          + Arrays.toString(this.mTxTimeMs)
          + " rxTimeMs="
          + this.mRxTimeMs
          + " txBytes="
          + this.mTxBytes
          + " rxBytes="
          + this.mRxBytes
          + " }";
    }

    public int getTxTimeLength() {
      return this.mTxTimeMs.length;
    }

    public int[] getTxTimeMillis() {
      return this.mTxTimeMs;
    }

    public void setTxTimeMillis(int[] txTimeMs) {
      this.mTxTimeMs = txTimeMs;
    }

    public int getRxTimeMillis() {
      return this.mRxTimeMs;
    }

    public void setRxTimeMillis(int rxTimeMs) {
      this.mRxTimeMs = rxTimeMs;
    }

    public long getTxBytes() {
      return this.mTxBytes;
    }

    public void setTxBytes(long txBytes) {
      this.mTxBytes = txBytes;
    }

    public long getRxBytes() {
      return this.mRxBytes;
    }

    public void setRxBytes(long rxBytes) {
      this.mRxBytes = rxBytes;
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
      return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.mTxTimeMs.length);
      dest.writeIntArray(this.mTxTimeMs);
      dest.writeInt(this.mRxTimeMs);
      dest.writeLong(this.mTxBytes);
      dest.writeLong(this.mRxBytes);
    }

    public MobileActivity(Parcel in) {
      readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
      int level = in.readInt();
      int[] iArr = new int[level];
      this.mTxTimeMs = iArr;
      in.readIntArray(iArr);
      this.mRxTimeMs = in.readInt();
      this.mTxBytes = in.readLong();
      this.mRxBytes = in.readLong();
    }
  }
}
