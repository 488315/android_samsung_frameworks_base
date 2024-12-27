package com.samsung.android.knox.custom;

import android.os.Parcel;
import android.os.Parcelable;

public final class HardKeyReport implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public static final String TAG = "HardKeyReport";
    public int mBlock;
    public int mKeyCode;
    public int mReportType;

    /* renamed from: com.samsung.android.knox.custom.HardKeyReport$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final HardKeyReport createFromParcel(Parcel parcel) {
            return new HardKeyReport(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new HardKeyReport(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final HardKeyReport[] newArray(int i) {
            return new HardKeyReport[i];
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new HardKeyReport[i];
        }
    }

    public HardKeyReport(int i, int i2, int i3) {
        this.mKeyCode = i;
        this.mReportType = i2;
        this.mBlock = i3;
    }

    public HardKeyReport(Parcel parcel) {
        this.mKeyCode = parcel.readInt();
        this.mReportType = parcel.readInt();
        this.mBlock = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final String toString() {
        return "descr:0 mKeyCode:"
                + this.mKeyCode
                + " mReportType:"
                + this.mReportType
                + " mBlock:"
                + this.mBlock;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mKeyCode);
        parcel.writeInt(this.mReportType);
        parcel.writeInt(this.mBlock);
    }
}
