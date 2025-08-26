package android.security.identity;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class GetEntriesResultParcel implements Parcelable {
    public static final Parcelable.Creator<GetEntriesResultParcel> CREATOR = new Parcelable.Creator<GetEntriesResultParcel>() { // from class: android.security.identity.GetEntriesResultParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetEntriesResultParcel createFromParcel(Parcel parcel) {
            GetEntriesResultParcel getEntriesResultParcel = new GetEntriesResultParcel();
            getEntriesResultParcel.readFromParcel(parcel);
            return getEntriesResultParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetEntriesResultParcel[] newArray(int i) {
            return new GetEntriesResultParcel[i];
        }
    };
    public byte[] deviceNameSpaces;
    public byte[] mac;
    public ResultNamespaceParcel[] resultNamespaces;
    public byte[] signature;
    public byte[] staticAuthenticationData;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.resultNamespaces, i);
        parcel.writeByteArray(this.deviceNameSpaces);
        parcel.writeByteArray(this.mac);
        parcel.writeByteArray(this.staticAuthenticationData);
        parcel.writeByteArray(this.signature);
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
                this.resultNamespaces = (ResultNamespaceParcel[]) parcel.createTypedArray(ResultNamespaceParcel.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.deviceNameSpaces = parcel.createByteArray();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.mac = parcel.createByteArray();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.staticAuthenticationData = parcel.createByteArray();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.signature = parcel.createByteArray();
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
        return describeContents(this.resultNamespaces);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
