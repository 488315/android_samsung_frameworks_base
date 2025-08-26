package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public class ServiceWithMetadata implements Parcelable {
    public static final Parcelable.Creator<ServiceWithMetadata> CREATOR = new Parcelable.Creator<ServiceWithMetadata>() { // from class: android.os.ServiceWithMetadata.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ServiceWithMetadata createFromParcel(Parcel parcel) {
            ServiceWithMetadata serviceWithMetadata = new ServiceWithMetadata();
            serviceWithMetadata.readFromParcel(parcel);
            return serviceWithMetadata;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ServiceWithMetadata[] newArray(int i) {
            return new ServiceWithMetadata[i];
        }
    };
    public boolean isLazyService = false;
    public IBinder service;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeStrongBinder(this.service);
        parcel.writeBoolean(this.isLazyService);
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
                this.service = parcel.readStrongBinder();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.isLazyService = parcel.readBoolean();
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
}
