package com.samsung.android.smartface;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public final class FaceInfo implements Parcelable {
    public static final Parcelable.Creator<FaceInfo> CREATOR = new Parcelable.Creator<FaceInfo>() { // from class: com.samsung.android.smartface.FaceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceInfo createFromParcel(Parcel parcel) {
            return new FaceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceInfo[] newArray(int i) {
            return new FaceInfo[i];
        }
    };
    public static final int NEED_TO_SLEEP = 0;
    public static final int NEED_TO_STAY = 1;
    public int needToRotate;
    public int needToStay;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FaceInfo() {
    }

    public FaceInfo(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.needToRotate);
        parcel.writeInt(this.needToStay);
    }

    public void readFromParcel(Parcel parcel) {
        this.needToRotate = parcel.readInt();
        this.needToStay = parcel.readInt();
    }
}
