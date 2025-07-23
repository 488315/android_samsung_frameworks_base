package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public class CpuStatsInfo implements Parcelable {
    public static final Parcelable.Creator<CpuStatsInfo> CREATOR = new Parcelable.Creator<CpuStatsInfo>() { // from class: android.os.CpuStatsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CpuStatsInfo createFromParcel(Parcel parcel) {
            return new CpuStatsInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CpuStatsInfo[] newArray(int i) {
            return new CpuStatsInfo[i];
        }
    };
    public String name;
    public int pid;
    public long rel_stime;
    public long rel_utime;
    public int tid;
    public int uid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CpuStatsInfo() {
    }

    public CpuStatsInfo(Parcel parcel) {
        this.name = parcel.readString();
        this.pid = parcel.readInt();
        this.uid = parcel.readInt();
        this.tid = parcel.readInt();
        this.rel_utime = parcel.readLong();
        this.rel_stime = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeInt(this.pid);
        parcel.writeInt(this.uid);
        parcel.writeInt(this.tid);
        parcel.writeLong(this.rel_utime);
        parcel.writeLong(this.rel_stime);
    }
}
