package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class CpuUsageInfo implements Parcelable {
    public static final Parcelable.Creator<CpuUsageInfo> CREATOR = new Parcelable.Creator<CpuUsageInfo>() { // from class: android.os.CpuUsageInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CpuUsageInfo createFromParcel(Parcel parcel) {
            return new CpuUsageInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CpuUsageInfo[] newArray(int i) {
            return new CpuUsageInfo[i];
        }
    };
    private long mActive;
    private long mTotal;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CpuUsageInfo(long j, long j2) {
        this.mActive = j;
        this.mTotal = j2;
    }

    private CpuUsageInfo(Parcel parcel) {
        readFromParcel(parcel);
    }

    public long getActive() {
        return this.mActive;
    }

    public long getTotal() {
        return this.mTotal;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mActive);
        parcel.writeLong(this.mTotal);
    }

    private void readFromParcel(Parcel parcel) {
        this.mActive = parcel.readLong();
        this.mTotal = parcel.readLong();
    }
}
