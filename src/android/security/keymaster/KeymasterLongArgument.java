package android.security.keymaster;

import android.os.Parcel;

/* loaded from: classes3.dex */
class KeymasterLongArgument extends KeymasterArgument {
    public final long value;

    public KeymasterLongArgument(int i, long j) {
        super(i);
        int tagType = KeymasterDefs.getTagType(i);
        if (tagType != -1610612736 && tagType != 1342177280) {
            throw new IllegalArgumentException("Bad long tag " + i);
        }
        this.value = j;
    }

    public KeymasterLongArgument(int i, Parcel parcel) {
        super(i);
        this.value = parcel.readLong();
    }

    @Override // android.security.keymaster.KeymasterArgument
    public void writeValue(Parcel parcel) {
        parcel.writeLong(this.value);
    }
}
