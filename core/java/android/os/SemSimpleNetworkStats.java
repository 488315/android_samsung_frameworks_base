package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class SemSimpleNetworkStats implements Parcelable {
    private long mRxBytes;
    private long mTxBytes;
    private int mUid;
    private static final String TAG = SemSimpleNetworkStats.class.getSimpleName();
    public static final Parcelable.Creator<SemSimpleNetworkStats> CREATOR = new Parcelable.Creator<SemSimpleNetworkStats>() { // from class: android.os.SemSimpleNetworkStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSimpleNetworkStats createFromParcel(Parcel in) {
            return new SemSimpleNetworkStats(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSimpleNetworkStats[] newArray(int size) {
            return new SemSimpleNetworkStats[size];
        }
    };

    public SemSimpleNetworkStats() {
        initialize();
    }

    public SemSimpleNetworkStats(int uid) {
        initialize();
        this.mUid = uid;
    }

    public SemSimpleNetworkStats(int uid, long txBytes, long rxBytes) {
        this.mUid = uid;
        this.mTxBytes = txBytes;
        this.mRxBytes = rxBytes;
    }

    public SemSimpleNetworkStats(Parcel in) {
        initialize();
        readFromParcel(in);
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
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mUid);
        out.writeLong(this.mTxBytes);
        out.writeLong(this.mRxBytes);
    }

    public void readFromParcel(Parcel in) {
        this.mUid = in.readInt();
        this.mTxBytes = in.readLong();
        this.mRxBytes = in.readLong();
    }

    public void reset() {
        initialize();
    }

    public void add(SemSimpleNetworkStats item) {
        if (this.mUid == item.getUid()) {
            this.mTxBytes += item.getTxBytes();
            this.mRxBytes += item.getRxBytes();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private void initialize() {
        this.mUid = -1;
        this.mTxBytes = 0L;
        this.mRxBytes = 0L;
    }
}
