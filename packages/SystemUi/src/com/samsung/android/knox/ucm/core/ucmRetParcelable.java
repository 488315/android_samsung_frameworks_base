package com.samsung.android.knox.ucm.core;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ucmRetParcelable implements Parcelable {
    public static final Parcelable.Creator<ucmRetParcelable> CREATOR = new Parcelable.Creator<ucmRetParcelable>() { // from class: com.samsung.android.knox.ucm.core.ucmRetParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ucmRetParcelable[] newArray(int i) {
            return new ucmRetParcelable[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ucmRetParcelable createFromParcel(Parcel parcel) {
            return new ucmRetParcelable(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final ucmRetParcelable[] newArray(int i) {
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
    public final int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        this.mResult = parcel.readInt();
        this.mDataLength = parcel.readInt();
        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("UCMERRORTESTING: @ucmRetParcelable readFromParcel: dateLength = "), this.mDataLength, "ucmRetParcelable");
        int i = this.mDataLength;
        if (i > 0) {
            byte[] bArr = new byte[i];
            this.mData = bArr;
            parcel.readByteArray(bArr);
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
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
