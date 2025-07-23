package android.os.incremental;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class StorageHealthCheckParams implements Parcelable {
    public static final Parcelable.Creator<StorageHealthCheckParams> CREATOR = new Parcelable.Creator<StorageHealthCheckParams>() { // from class: android.os.incremental.StorageHealthCheckParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StorageHealthCheckParams createFromParcel(Parcel parcel) {
            StorageHealthCheckParams storageHealthCheckParams = new StorageHealthCheckParams();
            storageHealthCheckParams.readFromParcel(parcel);
            return storageHealthCheckParams;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StorageHealthCheckParams[] newArray(int i) {
            return new StorageHealthCheckParams[i];
        }
    };
    public int blockedTimeoutMs = 0;
    public int unhealthyTimeoutMs = 0;
    public int unhealthyMonitoringMs = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.blockedTimeoutMs);
        parcel.writeInt(this.unhealthyTimeoutMs);
        parcel.writeInt(this.unhealthyMonitoringMs);
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
                this.blockedTimeoutMs = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.unhealthyTimeoutMs = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.unhealthyMonitoringMs = parcel.readInt();
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
