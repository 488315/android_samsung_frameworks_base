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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.blockedTimeoutMs);
        parcel.writeInt(this.unhealthyTimeoutMs);
        parcel.writeInt(this.unhealthyMonitoringMs);
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
                this.blockedTimeoutMs = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.unhealthyTimeoutMs = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.unhealthyMonitoringMs = parcel.readInt();
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
