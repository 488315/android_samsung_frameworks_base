package android.content;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class UriRelativeFilterParcel implements Parcelable {
    public static final Parcelable.Creator<UriRelativeFilterParcel> CREATOR = new Parcelable.Creator<UriRelativeFilterParcel>() { // from class: android.content.UriRelativeFilterParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UriRelativeFilterParcel createFromParcel(Parcel parcel) {
            UriRelativeFilterParcel uriRelativeFilterParcel = new UriRelativeFilterParcel();
            uriRelativeFilterParcel.readFromParcel(parcel);
            return uriRelativeFilterParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UriRelativeFilterParcel[] newArray(int i) {
            return new UriRelativeFilterParcel[i];
        }
    };
    public String filter;
    public int uriPart = 0;
    public int patternType = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.uriPart);
        parcel.writeInt(this.patternType);
        parcel.writeString(this.filter);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.uriPart = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.patternType = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.filter = parcel.readString();
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }
}
