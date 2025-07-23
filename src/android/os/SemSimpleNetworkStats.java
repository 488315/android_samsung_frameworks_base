package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class SemSimpleNetworkStats implements Parcelable {
    public static final Parcelable.Creator<SemSimpleNetworkStats> CREATOR = new Parcelable.Creator<SemSimpleNetworkStats>() { // from class: android.os.SemSimpleNetworkStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSimpleNetworkStats createFromParcel(Parcel parcel) {
            return new SemSimpleNetworkStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSimpleNetworkStats[] newArray(int i) {
            return new SemSimpleNetworkStats[i];
        }
    };
    private static final String TAG = "SemSimpleNetworkStats";
    private long mRxBytes;
    private long mTxBytes;
    private int mUid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemSimpleNetworkStats() {
        initialize();
    }

    public SemSimpleNetworkStats(int i) {
        initialize();
        this.mUid = i;
    }

    public SemSimpleNetworkStats(int i, long j, long j2) {
        this.mUid = i;
        this.mTxBytes = j;
        this.mRxBytes = j2;
    }

    public SemSimpleNetworkStats(Parcel parcel) {
        initialize();
        readFromParcel(parcel);
    }

    public int getUid() {
        return this.mUid;
    }

    public long getTxBytes() {
        return this.mTxBytes;
    }

    public long getRxBytes() {
        return this.mRxBytes;
    }

    public long getTxRxBytes() {
        return this.mTxBytes + this.mRxBytes;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mUid);
        parcel.writeLong(this.mTxBytes);
        parcel.writeLong(this.mRxBytes);
    }

    public void readFromParcel(Parcel parcel) {
        this.mUid = parcel.readInt();
        this.mTxBytes = parcel.readLong();
        this.mRxBytes = parcel.readLong();
    }

    public void reset() {
        initialize();
    }

    public void add(SemSimpleNetworkStats semSimpleNetworkStats) {
        if (this.mUid == semSimpleNetworkStats.getUid()) {
            this.mTxBytes += semSimpleNetworkStats.getTxBytes();
            this.mRxBytes += semSimpleNetworkStats.getRxBytes();
        }
    }

    private void initialize() {
        this.mUid = -1;
        this.mTxBytes = 0L;
        this.mRxBytes = 0L;
    }
}
