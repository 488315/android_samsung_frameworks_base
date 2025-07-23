package android.companion;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.provider.OneTimeUseBuilder;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ObservingDevicePresenceRequest implements Parcelable {
    public static final Parcelable.Creator<ObservingDevicePresenceRequest> CREATOR = new Parcelable.Creator<ObservingDevicePresenceRequest>() { // from class: android.companion.ObservingDevicePresenceRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ObservingDevicePresenceRequest[] newArray(int i) {
            return new ObservingDevicePresenceRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ObservingDevicePresenceRequest createFromParcel(Parcel parcel) {
            return new ObservingDevicePresenceRequest(parcel);
        }
    };
    private static final int PARCEL_UUID_NOT_NULL = 1;
    private static final int PARCEL_UUID_NULL = 0;
    private final int mAssociationId;
    private final ParcelUuid mUuid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ObservingDevicePresenceRequest(int i, ParcelUuid parcelUuid) {
        this.mAssociationId = i;
        this.mUuid = parcelUuid;
    }

    private ObservingDevicePresenceRequest(Parcel parcel) {
        this.mAssociationId = parcel.readInt();
        if (parcel.readInt() == 0) {
            this.mUuid = null;
        } else {
            this.mUuid = ParcelUuid.CREATOR.createFromParcel(parcel);
        }
    }

    public int getAssociationId() {
        return this.mAssociationId;
    }

    public ParcelUuid getUuid() {
        return this.mUuid;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mAssociationId);
        if (this.mUuid == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mUuid.writeToParcel(parcel, i);
        }
    }

    public String toString() {
        return "ObservingDevicePresenceRequest { Association Id= " + this.mAssociationId + ",ParcelUuid= " + this.mUuid + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ObservingDevicePresenceRequest) {
            ObservingDevicePresenceRequest observingDevicePresenceRequest = (ObservingDevicePresenceRequest) obj;
            if (Objects.equals(this.mUuid, observingDevicePresenceRequest.mUuid) && this.mAssociationId == observingDevicePresenceRequest.mAssociationId) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mAssociationId), this.mUuid);
    }

    public static final class Builder extends OneTimeUseBuilder<ObservingDevicePresenceRequest> {
        private int mAssociationId = -1;
        private ParcelUuid mUuid;

        public Builder setAssociationId(int i) {
            checkNotUsed();
            this.mAssociationId = i;
            return this;
        }

        public Builder setUuid(ParcelUuid parcelUuid) {
            checkNotUsed();
            this.mUuid = parcelUuid;
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.provider.OneTimeUseBuilder
        public ObservingDevicePresenceRequest build() {
            markUsed();
            ParcelUuid parcelUuid = this.mUuid;
            if (parcelUuid != null && this.mAssociationId != -1) {
                throw new IllegalStateException("Cannot observe device presence based on both ParcelUuid and association ID. Choose one or the other.");
            }
            if (parcelUuid == null && this.mAssociationId <= 0) {
                throw new IllegalStateException("Must provide either a ParcelUuid or a valid association ID to observe device presence.");
            }
            return new ObservingDevicePresenceRequest(this.mAssociationId, this.mUuid);
        }
    }
}
