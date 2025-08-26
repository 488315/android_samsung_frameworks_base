package com.samsung.android.knox.deviceinfo;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class SimChangeInfo implements Parcelable {
    public static final Parcelable.Creator<SimChangeInfo> CREATOR = new Parcelable.Creator<SimChangeInfo>() { // from class: com.samsung.android.knox.deviceinfo.SimChangeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SimChangeInfo createFromParcel(Parcel parcel) {
            return new SimChangeInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SimChangeInfo[] newArray(int i) {
            return new SimChangeInfo[i];
        }
    };
    public static final int SIM_CHANGED = 2;
    public static final int SIM_INSERTED = 3;
    public static final int SIM_REMOVED = 1;
    public int changeOperation;
    public long changeTime;
    public SimInfo currentSimInfo;
    public SimInfo previousSimInfo;

    public SimChangeInfo() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.changeTime = parcel.readLong();
        this.changeOperation = parcel.readInt();
        this.previousSimInfo = new SimInfo(parcel);
        this.currentSimInfo = new SimInfo(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.changeTime);
        parcel.writeInt(this.changeOperation);
        this.previousSimInfo.writeToParcel(parcel, i);
        this.currentSimInfo.writeToParcel(parcel, i);
    }

    public SimChangeInfo(Parcel parcel) {
        readFromParcel(parcel);
    }
}
