package com.samsung.android.knox.util;

import android.os.Parcel;
import android.os.Parcelable;

public class SemCertByte implements Parcelable {

    @Deprecated(forRemoval = true, since = "16.0")
    public static final Parcelable.Creator<SemCertByte> CREATOR =
            new Parcelable.Creator<
                    SemCertByte>() { // from class: com.samsung.android.knox.util.SemCertByte.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemCertByte createFromParcel(Parcel source) {
                    return new SemCertByte(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemCertByte[] newArray(int size) {
                    return null;
                }
            };

    public byte[] caCertBytes;
    public int caSize;
    public byte[] certBytes;
    public int certsize;

    public SemCertByte() {}

    public SemCertByte(Parcel source) {
        this.certsize = source.readInt();
        this.certBytes = new byte[this.certsize];
        readByteArray(source, this.certBytes);
        this.caSize = source.readInt();
        this.caCertBytes = new byte[this.caSize];
        readByteArray(source, this.caCertBytes);
    }

    @Override // android.os.Parcelable
    @Deprecated(forRemoval = true, since = "16.0")
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    @Deprecated(forRemoval = true, since = "16.0")
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.certsize);
        dest.writeByteArray(this.certBytes);
        dest.writeInt(this.caSize);
        dest.writeByteArray(this.caCertBytes);
    }

    private final void readByteArray(Parcel parcel, byte[] val) {
        byte[] ba = parcel.createByteArray();
        if (ba.length == val.length) {
            System.arraycopy(ba, 0, val, 0, ba.length);
            return;
        }
        throw new RuntimeException("bad array lengths");
    }
}
