package android.os;

import android.os.Parcelable;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class SemModemActivityInfo implements Parcelable {
    public static final Parcelable.Creator<SemModemActivityInfo> CREATOR = new Parcelable.Creator<SemModemActivityInfo>() { // from class: android.os.SemModemActivityInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemModemActivityInfo createFromParcel(Parcel parcel) {
            return new SemModemActivityInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemModemActivityInfo[] newArray(int i) {
            return new SemModemActivityInfo[i];
        }
    };
    public static final int TX_POWER_LEVELS = 5;
    private int mIdleTimeMs;
    private int mSleepTimeMs;
    MobileActivity mNr = new MobileActivity(5);
    MobileActivity mLc = new MobileActivity(5);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemModemActivityInfo(int i, int i2, MobileActivity mobileActivity, MobileActivity mobileActivity2) {
        this.mSleepTimeMs = i;
        this.mIdleTimeMs = i2;
        if (mobileActivity != null) {
            System.arraycopy(mobileActivity.getTxTimeMillis(), 0, this.mNr.getTxTimeMillis(), 0, Math.min(mobileActivity.getTxTimeLength(), 5));
        }
        this.mNr.setRxTimeMillis(mobileActivity.getRxTimeMillis());
        this.mNr.setTxBytes(mobileActivity.getTxBytes());
        this.mNr.setRxBytes(mobileActivity.getRxBytes());
        if (mobileActivity2 != null) {
            System.arraycopy(mobileActivity2.getTxTimeMillis(), 0, this.mLc.getTxTimeMillis(), 0, Math.min(mobileActivity2.getTxTimeLength(), 5));
        }
        this.mLc.setRxTimeMillis(mobileActivity2.getRxTimeMillis());
        this.mLc.setTxBytes(mobileActivity2.getTxBytes());
        this.mLc.setRxBytes(mobileActivity2.getRxBytes());
    }

    public String toString() {
        return "SemModemActivityInfo{ SleepTimeMs=" + this.mSleepTimeMs + " IdleTimeMs=" + this.mIdleTimeMs + " mNr=" + this.mNr.toString() + " mLc=" + this.mLc.toString() + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSleepTimeMs);
        parcel.writeInt(this.mIdleTimeMs);
        parcel.writeTypedObject(this.mNr, i);
        parcel.writeTypedObject(this.mLc, i);
    }

    public SemModemActivityInfo(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.mSleepTimeMs = parcel.readInt();
        this.mIdleTimeMs = parcel.readInt();
        this.mNr = (MobileActivity) parcel.readTypedObject(MobileActivity.CREATOR);
        this.mLc = (MobileActivity) parcel.readTypedObject(MobileActivity.CREATOR);
    }

    public int getSleepTimeMillis() {
        return this.mSleepTimeMs;
    }

    public void setSleepTimeMillis(int i) {
        this.mSleepTimeMs = i;
    }

    public int getIdleTimeMillis() {
        return this.mIdleTimeMs;
    }

    public void setIdleTimeMillis(int i) {
        this.mIdleTimeMs = i;
    }

    public int[] getNrTxTimeMillis() {
        return this.mNr.getTxTimeMillis();
    }

    public void setNrTxTimeMillis(int[] iArr) {
        this.mNr.setTxTimeMillis(iArr);
    }

    public int getNrRxTimeMillis() {
        return this.mNr.getRxTimeMillis();
    }

    public void setNrRxTimeMillis(int i) {
        this.mNr.setRxTimeMillis(i);
    }

    public long getNrTxBytes() {
        return this.mNr.getTxBytes();
    }

    public void setNrTxBytes(long j) {
        this.mNr.setTxBytes(j);
    }

    public long getNrRxBytes() {
        return this.mNr.getRxBytes();
    }

    public void setNrRxBytes(long j) {
        this.mNr.setRxBytes(j);
    }

    public int[] getLcTxTimeMillis() {
        return this.mLc.getTxTimeMillis();
    }

    public void setLcTxTimeMillis(int[] iArr) {
        this.mLc.setTxTimeMillis(iArr);
    }

    public int getLcRxTimeMillis() {
        return this.mLc.getRxTimeMillis();
    }

    public void setLcRxTimeMillis(int i) {
        this.mLc.setRxTimeMillis(i);
    }

    public long getLcTxBytes() {
        return this.mLc.getTxBytes();
    }

    public void setLcTxBytes(long j) {
        this.mLc.setTxBytes(j);
    }

    public long getLcRxBytes() {
        return this.mLc.getRxBytes();
    }

    public void setLcRxBytes(long j) {
        this.mLc.setRxBytes(j);
    }

    public boolean isValid() {
        for (int i : this.mNr.getTxTimeMillis()) {
            if (i < 0) {
                return false;
            }
        }
        for (int i2 : this.mLc.getTxTimeMillis()) {
            if (i2 < 0) {
                return false;
            }
        }
        return getIdleTimeMillis() >= 0 && getSleepTimeMillis() >= 0 && this.mNr.getRxTimeMillis() >= 0 && this.mNr.getTxBytes() >= 0 && this.mNr.getRxBytes() >= 0 && this.mLc.getRxTimeMillis() >= 0 && this.mLc.getTxBytes() >= 0 && this.mLc.getRxBytes() >= 0 && !isEmpty();
    }

    private boolean isEmpty() {
        for (int i : this.mNr.getTxTimeMillis()) {
            if (i != 0) {
                return false;
            }
        }
        for (int i2 : this.mLc.getTxTimeMillis()) {
            if (i2 != 0) {
                return false;
            }
        }
        return getIdleTimeMillis() == 0 && getSleepTimeMillis() == 0 && this.mNr.getRxTimeMillis() == 0 && this.mNr.getTxBytes() == 0 && this.mNr.getRxBytes() == 0 && this.mLc.getRxTimeMillis() == 0 && this.mLc.getTxBytes() == 0 && this.mLc.getRxBytes() == 0;
    }

    public static class MobileActivity implements Parcelable {
        public static final Parcelable.Creator<MobileActivity> CREATOR = new Parcelable.Creator<MobileActivity>() { // from class: android.os.SemModemActivityInfo.MobileActivity.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public MobileActivity createFromParcel(Parcel parcel) {
                return new MobileActivity(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public MobileActivity[] newArray(int i) {
                return new MobileActivity[i];
            }
        };
        private long mRxBytes;
        private int mRxTimeMs;
        private long mTxBytes;
        private int[] mTxTimeMs;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public MobileActivity(int i) {
            this.mTxTimeMs = new int[i];
            this.mRxTimeMs = 0;
            this.mTxBytes = 0L;
            this.mRxBytes = 0L;
        }

        public String toString() {
            return "MobileActivity{ txTimeMs[]=" + Arrays.toString(this.mTxTimeMs) + " rxTimeMs=" + this.mRxTimeMs + " txBytes=" + this.mTxBytes + " rxBytes=" + this.mRxBytes + " }";
        }

        public int getTxTimeLength() {
            return this.mTxTimeMs.length;
        }

        public int[] getTxTimeMillis() {
            return this.mTxTimeMs;
        }

        public void setTxTimeMillis(int[] iArr) {
            this.mTxTimeMs = iArr;
        }

        public int getRxTimeMillis() {
            return this.mRxTimeMs;
        }

        public void setRxTimeMillis(int i) {
            this.mRxTimeMs = i;
        }

        public long getTxBytes() {
            return this.mTxBytes;
        }

        public void setTxBytes(long j) {
            this.mTxBytes = j;
        }

        public long getRxBytes() {
            return this.mRxBytes;
        }

        public void setRxBytes(long j) {
            this.mRxBytes = j;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mTxTimeMs.length);
            parcel.writeIntArray(this.mTxTimeMs);
            parcel.writeInt(this.mRxTimeMs);
            parcel.writeLong(this.mTxBytes);
            parcel.writeLong(this.mRxBytes);
        }

        public MobileActivity(Parcel parcel) {
            readFromParcel(parcel);
        }

        public void readFromParcel(Parcel parcel) {
            int[] iArr = new int[parcel.readInt()];
            this.mTxTimeMs = iArr;
            parcel.readIntArray(iArr);
            this.mRxTimeMs = parcel.readInt();
            this.mTxBytes = parcel.readLong();
            this.mRxBytes = parcel.readLong();
        }
    }
}
