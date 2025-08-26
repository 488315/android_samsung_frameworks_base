package android.content;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class UriRelativeFilterGroupParcel implements Parcelable {
    public static final Parcelable.Creator<UriRelativeFilterGroupParcel> CREATOR = new Parcelable.Creator<UriRelativeFilterGroupParcel>() { // from class: android.content.UriRelativeFilterGroupParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UriRelativeFilterGroupParcel createFromParcel(Parcel parcel) {
            UriRelativeFilterGroupParcel uriRelativeFilterGroupParcel = new UriRelativeFilterGroupParcel();
            uriRelativeFilterGroupParcel.readFromParcel(parcel);
            return uriRelativeFilterGroupParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UriRelativeFilterGroupParcel[] newArray(int i) {
            return new UriRelativeFilterGroupParcel[i];
        }
    };
    public int action = 0;
    public List<UriRelativeFilterParcel> filters;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.action);
        parcel.writeTypedList(this.filters, i);
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
                this.action = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.filters = parcel.createTypedArrayList(UriRelativeFilterParcel.CREATOR);
                    if (iDataPosition > Integer.MAX_VALUE - i) {
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.filters);
    }

    private int describeContents(Object obj) {
        int iDescribeContents = 0;
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Collection) {
            Iterator it = ((Collection) obj).iterator();
            while (it.hasNext()) {
                iDescribeContents |= describeContents(it.next());
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
