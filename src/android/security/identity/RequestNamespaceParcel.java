package android.security.identity;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class RequestNamespaceParcel implements Parcelable {
    public static final Parcelable.Creator<RequestNamespaceParcel> CREATOR = new Parcelable.Creator<RequestNamespaceParcel>() { // from class: android.security.identity.RequestNamespaceParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RequestNamespaceParcel createFromParcel(Parcel parcel) {
            RequestNamespaceParcel requestNamespaceParcel = new RequestNamespaceParcel();
            requestNamespaceParcel.readFromParcel(parcel);
            return requestNamespaceParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RequestNamespaceParcel[] newArray(int i) {
            return new RequestNamespaceParcel[i];
        }
    };
    public RequestEntryParcel[] entries;
    public String namespaceName;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.namespaceName);
        parcel.writeTypedArray(this.entries, i);
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
                this.namespaceName = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.entries = (RequestEntryParcel[]) parcel.createTypedArray(RequestEntryParcel.CREATOR);
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.entries);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
