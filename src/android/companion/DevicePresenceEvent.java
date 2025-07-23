package android.companion;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes.dex */
public final class DevicePresenceEvent implements Parcelable {
    public static final Parcelable.Creator<DevicePresenceEvent> CREATOR = new Parcelable.Creator<DevicePresenceEvent>() { // from class: android.companion.DevicePresenceEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DevicePresenceEvent[] newArray(int i) {
            return new DevicePresenceEvent[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DevicePresenceEvent createFromParcel(Parcel parcel) {
            return new DevicePresenceEvent(parcel);
        }
    };
    public static final int EVENT_BLE_APPEARED = 0;
    public static final int EVENT_BLE_DISAPPEARED = 1;
    public static final int EVENT_BT_CONNECTED = 2;
    public static final int EVENT_BT_DISCONNECTED = 3;
    public static final int EVENT_SELF_MANAGED_APPEARED = 4;
    public static final int EVENT_SELF_MANAGED_DISAPPEARED = 5;
    public static final int NO_ASSOCIATION = -1;
    private static final int PARCEL_UUID_NOT_NULL = 1;
    private static final int PARCEL_UUID_NULL = 0;
    private final int mAssociationId;
    private final int mEvent;
    private final ParcelUuid mUuid;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Event {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DevicePresenceEvent(int i, int i2, ParcelUuid parcelUuid) {
        this.mAssociationId = i;
        this.mEvent = i2;
        this.mUuid = parcelUuid;
    }

    public int getAssociationId() {
        return this.mAssociationId;
    }

    public int getEvent() {
        return this.mEvent;
    }

    public ParcelUuid getUuid() {
        return this.mUuid;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mAssociationId);
        parcel.writeInt(this.mEvent);
        if (this.mUuid == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mUuid.writeToParcel(parcel, i);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DevicePresenceEvent) {
            DevicePresenceEvent devicePresenceEvent = (DevicePresenceEvent) obj;
            if (Objects.equals(this.mUuid, devicePresenceEvent.mUuid) && this.mAssociationId == devicePresenceEvent.mAssociationId && this.mEvent == devicePresenceEvent.mEvent) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "ObservingDevicePresenceResult { Association Id= " + this.mAssociationId + ",ParcelUuid= " + this.mUuid + ",Event= " + this.mEvent + "}";
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mAssociationId), Integer.valueOf(this.mEvent), this.mUuid);
    }

    private DevicePresenceEvent(Parcel parcel) {
        this.mAssociationId = parcel.readInt();
        this.mEvent = parcel.readInt();
        if (parcel.readInt() == 0) {
            this.mUuid = null;
        } else {
            this.mUuid = ParcelUuid.CREATOR.createFromParcel(parcel);
        }
    }
}
