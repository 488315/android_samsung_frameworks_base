package android.security.keymaster;

import android.os.Parcel;

/* loaded from: classes3.dex */
class KeymasterBooleanArgument extends KeymasterArgument {
    public final boolean value;

    @Override // android.security.keymaster.KeymasterArgument
    public void writeValue(Parcel parcel) {
    }

    public KeymasterBooleanArgument(int i) {
        super(i);
        this.value = true;
        if (KeymasterDefs.getTagType(i) == 1879048192) {
            return;
        }
        throw new IllegalArgumentException("Bad bool tag " + i);
    }

    public KeymasterBooleanArgument(int i, Parcel parcel) {
        super(i);
        this.value = true;
    }
}
