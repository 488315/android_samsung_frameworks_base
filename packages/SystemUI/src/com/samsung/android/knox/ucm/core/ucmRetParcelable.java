package com.samsung.android.knox.ucm.core;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;

/* loaded from: classes4.dex */
public class ucmRetParcelable implements Parcelable {
    public static final Parcelable.Creator<ucmRetParcelable> CREATOR = new Parcelable.Creator<ucmRetParcelable>() { // from class: com.samsung.android.knox.ucm.core.ucmRetParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ucmRetParcelable createFromParcel(Parcel parcel) {
            return new ucmRetParcelable(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ucmRetParcelable[] newArray(int i) {
            return new ucmRetParcelable[i];
        }
    };
    public byte[] mData;
    public int mDataLength;
    public int mResult;

    public /* synthetic */ ucmRetParcelable(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.mResult = parcel.readInt();
        this.mDataLength = parcel.readInt();
        RecyclerView$$ExternalSyntheticOutline0.m(this.mDataLength, "ucmRetParcelable", new StringBuilder("UCMERRORTESTING: @ucmRetParcelable readFromParcel: dateLength = "));
        int i = this.mDataLength;
        if (i > 0) {
            byte[] bArr = new byte[i];
            this.mData = bArr;
            parcel.readByteArray(bArr);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResult);
        parcel.writeInt(this.mDataLength);
        if (this.mDataLength > 0) {
            parcel.writeByteArray(this.mData);
        }
    }

    public ucmRetParcelable(int i, byte[] bArr) {
        this.mResult = i;
        this.mData = bArr;
        if (bArr != null) {
            this.mDataLength = bArr.length;
        } else {
            this.mDataLength = 0;
        }
    }

    private ucmRetParcelable(Parcel parcel) {
        readFromParcel(parcel);
    }
}
