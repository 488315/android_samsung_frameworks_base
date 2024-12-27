package android.os;

import android.os.Parcelable;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class CpuTrackerInfo implements Parcelable {
    public static final Parcelable.Creator<CpuTrackerInfo> CREATOR = new Parcelable.Creator<CpuTrackerInfo>() { // from class: android.os.CpuTrackerInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CpuTrackerInfo createFromParcel(Parcel in) {
            return new CpuTrackerInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CpuTrackerInfo[] newArray(int size) {
            return new CpuTrackerInfo[size];
        }
    };
    public long baseSampleTime;
    public ArrayList<CcuStatsInfo> curCcuStatsList;
    public ArrayList<CpuStatsInfo> curCpuStatsList;
    public long lastSampleTime;
    public long totalCpuTime;

    public CpuTrackerInfo() {
    }

    public CpuTrackerInfo(Parcel in) {
        this.baseSampleTime = in.readLong();
        this.lastSampleTime = in.readLong();
        this.totalCpuTime = in.readLong();
        this.curCpuStatsList = in.createTypedArrayList(CpuStatsInfo.CREATOR);
        this.curCcuStatsList = in.createTypedArrayList(CcuStatsInfo.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.baseSampleTime);
        dest.writeLong(this.lastSampleTime);
        dest.writeLong(this.totalCpuTime);
        dest.writeTypedList(this.curCpuStatsList);
        dest.writeTypedList(this.curCcuStatsList);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
