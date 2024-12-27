package android.security.keymaster;

import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;

/* loaded from: classes3.dex */
abstract class KeymasterArgument implements Parcelable {
    public static final Parcelable.Creator<KeymasterArgument> CREATOR =
            new Parcelable.Creator<
                    KeymasterArgument>() { // from class:
                                           // android.security.keymaster.KeymasterArgument.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public KeymasterArgument createFromParcel(Parcel in) {
                    int pos = in.dataPosition();
                    int tag = in.readInt();
                    switch (KeymasterDefs.getTagType(tag)) {
                        case Integer.MIN_VALUE:
                        case -1879048192:
                            return new KeymasterBlobArgument(tag, in);
                        case -1610612736:
                        case 1342177280:
                            return new KeymasterLongArgument(tag, in);
                        case 268435456:
                        case 536870912:
                        case 805306368:
                        case 1073741824:
                            return new KeymasterIntArgument(tag, in);
                        case 1610612736:
                            return new KeymasterDateArgument(tag, in);
                        case 1879048192:
                            return new KeymasterBooleanArgument(tag, in);
                        default:
                            throw new ParcelFormatException("Bad tag: " + tag + " at " + pos);
                    }
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public KeymasterArgument[] newArray(int size) {
                    return new KeymasterArgument[size];
                }
            };
    public final int tag;

    public abstract void writeValue(Parcel parcel);

    protected KeymasterArgument(int tag) {
        this.tag = tag;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.tag);
        writeValue(out);
    }
}
