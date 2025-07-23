package android.security.keymaster;

import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;

/* loaded from: classes3.dex */
abstract class KeymasterArgument implements Parcelable {
    public static final Parcelable.Creator<KeymasterArgument> CREATOR = new Parcelable.Creator<KeymasterArgument>() { // from class: android.security.keymaster.KeymasterArgument.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeymasterArgument createFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            int tagType = KeymasterDefs.getTagType(readInt);
            if (tagType != Integer.MIN_VALUE && tagType != -1879048192) {
                if (tagType != -1610612736) {
                    if (tagType == 268435456 || tagType == 536870912 || tagType == 805306368 || tagType == 1073741824) {
                        return new KeymasterIntArgument(readInt, parcel);
                    }
                    if (tagType != 1342177280) {
                        if (tagType == 1610612736) {
                            return new KeymasterDateArgument(readInt, parcel);
                        }
                        if (tagType == 1879048192) {
                            return new KeymasterBooleanArgument(readInt, parcel);
                        }
                        throw new ParcelFormatException("Bad tag: " + readInt + " at " + dataPosition);
                    }
                }
                return new KeymasterLongArgument(readInt, parcel);
            }
            return new KeymasterBlobArgument(readInt, parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeymasterArgument[] newArray(int i) {
            return new KeymasterArgument[i];
        }
    };
    public final int tag;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public abstract void writeValue(Parcel parcel);

    protected KeymasterArgument(int i) {
        this.tag = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.tag);
        writeValue(parcel);
    }
}
