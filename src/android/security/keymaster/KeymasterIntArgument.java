package android.security.keymaster;

import android.os.Parcel;

/* loaded from: classes3.dex */
class KeymasterIntArgument extends KeymasterArgument {
    public final int value;

    public KeymasterIntArgument(int i, int i2) {
        super(i);
        int tagType = KeymasterDefs.getTagType(i);
        if (tagType != 268435456 && tagType != 536870912 && tagType != 805306368 && tagType != 1073741824) {
            throw new IllegalArgumentException("Bad int tag " + i);
        }
        this.value = i2;
    }

    public KeymasterIntArgument(int i, Parcel parcel) {
        super(i);
        this.value = parcel.readInt();
    }

    @Override // android.security.keymaster.KeymasterArgument
    public void writeValue(Parcel parcel) {
        parcel.writeInt(this.value);
    }
}
