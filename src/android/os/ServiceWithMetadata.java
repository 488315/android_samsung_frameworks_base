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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeStrongBinder(this.service);
        parcel.writeBoolean(this.isLazyService);
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
                this.service = parcel.readStrongBinder();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.isLazyService = parcel.readBoolean();
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
}
