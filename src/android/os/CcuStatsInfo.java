package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public class CcuStatsInfo implements Parcelable {
    public static final Parcelable.Creator<CcuStatsInfo> CREATOR = new Parcelable.Creator<CcuStatsInfo>() { // from class: android.os.CcuStatsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CcuStatsInfo createFromParcel(Parcel parcel) {
            return new CcuStatsInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CcuStatsInfo[] newArray(int i) {
            return new CcuStatsInfo[i];
        }
    };
    public long[] cpuFreqTimeMs;
    public int uid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CcuStatsInfo() {
    }

    public CcuStatsInfo(Parcel parcel) {
        this.uid = parcel.readInt();
        this.cpuFreqTimeMs = parcel.createLongArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.uid);
        parcel.writeLongArray(this.cpuFreqTimeMs);
    }
}
