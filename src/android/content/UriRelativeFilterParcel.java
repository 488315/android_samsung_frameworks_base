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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.uriPart);
        parcel.writeInt(this.patternType);
        parcel.writeString(this.filter);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.uriPart = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.patternType = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.filter = parcel.readString();
                        if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }
}
