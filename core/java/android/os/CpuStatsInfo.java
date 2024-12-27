package android.os;


public class CpuStatsInfo implements Parcelable {
    public static final Parcelable.Creator<CpuStatsInfo> CREATOR =
            new Parcelable.Creator<CpuStatsInfo>() { // from class: android.os.CpuStatsInfo.1
                @Override // android.os.Parcelable.Creator
                public CpuStatsInfo createFromParcel(Parcel in) {
                    return new CpuStatsInfo(in);
                }

                @Override // android.os.Parcelable.Creator
                public CpuStatsInfo[] newArray(int size) {
                    return new CpuStatsInfo[size];
                }
            };
    public String name;
    public int pid;
    public long rel_stime;
    public long rel_utime;
    public int tid;
    public int uid;

    public CpuStatsInfo() {}

    public CpuStatsInfo(Parcel in) {
        this.name = in.readString();
        this.pid = in.readInt();
        this.uid = in.readInt();
        this.tid = in.readInt();
        this.rel_utime = in.readLong();
        this.rel_stime = in.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.pid);
        dest.writeInt(this.uid);
        dest.writeInt(this.tid);
        dest.writeLong(this.rel_utime);
        dest.writeLong(this.rel_stime);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
