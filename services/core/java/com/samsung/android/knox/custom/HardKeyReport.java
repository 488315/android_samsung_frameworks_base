package com.samsung.android.knox.custom;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class HardKeyReport implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.samsung.android.knox.custom.HardKeyReport.1
        @Override // android.os.Parcelable.Creator
        public HardKeyReport createFromParcel(Parcel parcel) {
            return new HardKeyReport(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public HardKeyReport[] newArray(int i) {
            return new HardKeyReport[i];
        }
    };
    public static final String TAG = "HardKeyReport";
    public int mBlock;
    public int mKeyCode;
    public int mReportType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public HardKeyReport(int i, int i2, int i3) {
        this.mKeyCode = i;
        this.mReportType = i2;
        this.mBlock = i3;
    }

    public String toString() {
        return "descr:" + describeContents() + " mKeyCode:" + this.mKeyCode + " mReportType:" + this.mReportType + " mBlock:" + this.mBlock;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mKeyCode);
        parcel.writeInt(this.mReportType);
        parcel.writeInt(this.mBlock);
    }

    public HardKeyReport(Parcel parcel) {
        this.mKeyCode = parcel.readInt();
        this.mReportType = parcel.readInt();
        this.mBlock = parcel.readInt();
    }
}
