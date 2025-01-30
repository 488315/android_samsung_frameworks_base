package android.security.keymaster;

import android.p009os.Parcel;

/* loaded from: classes3.dex */
class KeymasterBlobArgument extends KeymasterArgument {
    public final byte[] blob;

    public KeymasterBlobArgument(int tag, byte[] blob) {
        super(tag);
        switch (KeymasterDefs.getTagType(tag)) {
            case Integer.MIN_VALUE:
            case -1879048192:
                this.blob = blob;
                return;
            default:
                throw new IllegalArgumentException("Bad blob tag " + tag);
        }
    }

    public KeymasterBlobArgument(int tag, Parcel in) {
        super(tag);
        this.blob = in.createByteArray();
    }

    @Override // android.security.keymaster.KeymasterArgument
    public void writeValue(Parcel out) {
        out.writeByteArray(this.blob);
    }
}
