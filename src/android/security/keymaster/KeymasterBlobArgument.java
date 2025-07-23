package android.security.keymaster;

import android.os.Parcel;

/* loaded from: classes3.dex */
class KeymasterBlobArgument extends KeymasterArgument {
    public final byte[] blob;

    public KeymasterBlobArgument(int i, byte[] bArr) {
        super(i);
        int tagType = KeymasterDefs.getTagType(i);
        if (tagType != Integer.MIN_VALUE && tagType != -1879048192) {
            throw new IllegalArgumentException("Bad blob tag " + i);
        }
        this.blob = bArr;
    }

    public KeymasterBlobArgument(int i, Parcel parcel) {
        super(i);
        this.blob = parcel.createByteArray();
    }

    @Override // android.security.keymaster.KeymasterArgument
    public void writeValue(Parcel parcel) {
        parcel.writeByteArray(this.blob);
    }
}
