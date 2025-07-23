package com.samsung.android.knox.util;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemCertByte implements Parcelable {

    @Deprecated(forRemoval = true, since = "16.0")
    public static final Parcelable.Creator<SemCertByte> CREATOR = new Parcelable.Creator<SemCertByte>() { // from class: com.samsung.android.knox.util.SemCertByte.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemCertByte[] newArray(int i) {
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemCertByte createFromParcel(Parcel parcel) {
            return new SemCertByte(parcel);
        }
    };
    public byte[] caCertBytes;
    public int caSize;
    public byte[] certBytes;
    public int certsize;

    @Override // android.os.Parcelable
    @Deprecated(forRemoval = true, since = "16.0")
    public int describeContents() {
        return 0;
    }

    public SemCertByte() {
    }

    public SemCertByte(Parcel parcel) {
        int readInt = parcel.readInt();
        this.certsize = readInt;
        byte[] bArr = new byte[readInt];
        this.certBytes = bArr;
        readByteArray(parcel, bArr);
        int readInt2 = parcel.readInt();
        this.caSize = readInt2;
        byte[] bArr2 = new byte[readInt2];
        this.caCertBytes = bArr2;
        readByteArray(parcel, bArr2);
    }

    @Override // android.os.Parcelable
    @Deprecated(forRemoval = true, since = "16.0")
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.certsize);
        parcel.writeByteArray(this.certBytes);
        parcel.writeInt(this.caSize);
        parcel.writeByteArray(this.caCertBytes);
    }

    private final void readByteArray(Parcel parcel, byte[] bArr) {
        byte[] createByteArray = parcel.createByteArray();
        if (createByteArray.length == bArr.length) {
            System.arraycopy(createByteArray, 0, bArr, 0, createByteArray.length);
            return;
        }
        throw new RuntimeException("bad array lengths");
    }
}
