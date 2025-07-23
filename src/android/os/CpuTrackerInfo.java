package android.os;

import android.os.Parcelable;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class CpuTrackerInfo implements Parcelable {
    public static final Parcelable.Creator<CpuTrackerInfo> CREATOR = new Parcelable.Creator<CpuTrackerInfo>() { // from class: android.os.CpuTrackerInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CpuTrackerInfo createFromParcel(Parcel parcel) {
            return new CpuTrackerInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CpuTrackerInfo[] newArray(int i) {
            return new CpuTrackerInfo[i];
        }
    };
    public long baseSampleTime;
    public ArrayList<CcuStatsInfo> curCcuStatsList;
    public ArrayList<CpuStatsInfo> curCpuStatsList;
    public long lastSampleTime;
    public long totalCpuTime;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CpuTrackerInfo() {
    }

    public CpuTrackerInfo(Parcel parcel) {
        this.baseSampleTime = parcel.readLong();
        this.lastSampleTime = parcel.readLong();
        this.totalCpuTime = parcel.readLong();
        this.curCpuStatsList = parcel.createTypedArrayList(CpuStatsInfo.CREATOR);
        this.curCcuStatsList = parcel.createTypedArrayList(CcuStatsInfo.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.baseSampleTime);
        parcel.writeLong(this.lastSampleTime);
        parcel.writeLong(this.totalCpuTime);
        parcel.writeTypedList(this.curCpuStatsList);
        parcel.writeTypedList(this.curCcuStatsList);
    }
}
