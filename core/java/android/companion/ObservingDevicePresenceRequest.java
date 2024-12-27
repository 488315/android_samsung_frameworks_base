package android.companion;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.provider.OneTimeUseBuilder;

import java.util.Objects;

/* loaded from: classes.dex */
public final class ObservingDevicePresenceRequest implements Parcelable {
    public static final Parcelable.Creator<ObservingDevicePresenceRequest> CREATOR =
            new Parcelable.Creator<ObservingDevicePresenceRequest>() { // from class:
                // android.companion.ObservingDevicePresenceRequest.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ObservingDevicePresenceRequest[] newArray(int size) {
                    return new ObservingDevicePresenceRequest[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ObservingDevicePresenceRequest createFromParcel(Parcel in) {
                    return new ObservingDevicePresenceRequest(in);
                }
            };
    private static final int PARCEL_UUID_NOT_NULL = 1;
    private static final int PARCEL_UUID_NULL = 0;
    private final int mAssociationId;
    private final ParcelUuid mUuid;

    private ObservingDevicePresenceRequest(int associationId, ParcelUuid uuid) {
        this.mAssociationId = associationId;
        this.mUuid = uuid;
    }

    private ObservingDevicePresenceRequest(Parcel in) {
        this.mAssociationId = in.readInt();
        if (in.readInt() == 0) {
            this.mUuid = null;
        } else {
            this.mUuid = ParcelUuid.CREATOR.createFromParcel(in);
        }
    }

    public int getAssociationId() {
        return this.mAssociationId;
    }

    public ParcelUuid getUuid() {
        return this.mUuid;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mAssociationId);
        if (this.mUuid == null) {
            dest.writeInt(0);
        } else {
            dest.writeInt(1);
            this.mUuid.writeToParcel(dest, flags);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "ObservingDevicePresenceRequest { Association Id= "
                + this.mAssociationId
                + ",ParcelUuid= "
                + this.mUuid
                + "}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObservingDevicePresenceRequest)) {
            return false;
        }
        ObservingDevicePresenceRequest that = (ObservingDevicePresenceRequest) o;
        return Objects.equals(this.mUuid, that.mUuid) && this.mAssociationId == that.mAssociationId;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mAssociationId), this.mUuid);
    }

    public static final class Builder extends OneTimeUseBuilder<ObservingDevicePresenceRequest> {
        private int mAssociationId = -1;
        private ParcelUuid mUuid;

        public Builder setAssociationId(int associationId) {
            checkNotUsed();
            this.mAssociationId = associationId;
            return this;
        }

        public Builder setUuid(ParcelUuid uuid) {
            checkNotUsed();
            this.mUuid = uuid;
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.provider.OneTimeUseBuilder
        public ObservingDevicePresenceRequest build() {
            markUsed();
            if (this.mUuid != null && this.mAssociationId != -1) {
                throw new IllegalStateException(
                        "Cannot observe device presence based on both ParcelUuid and association"
                                + " ID. Choose one or the other.");
            }
            if (this.mUuid == null && this.mAssociationId <= 0) {
                throw new IllegalStateException(
                        "Must provide either a ParcelUuid or a valid association ID to observe"
                                + " device presence.");
            }
            return new ObservingDevicePresenceRequest(this.mAssociationId, this.mUuid);
        }
    }
}
