package android.companion;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes.dex */
public final class DevicePresenceEvent implements Parcelable {
    public static final Parcelable.Creator<DevicePresenceEvent> CREATOR =
            new Parcelable.Creator<
                    DevicePresenceEvent>() { // from class: android.companion.DevicePresenceEvent.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public DevicePresenceEvent[] newArray(int size) {
                    return new DevicePresenceEvent[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public DevicePresenceEvent createFromParcel(Parcel in) {
                    return new DevicePresenceEvent(in);
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
    public @interface Event {}

    public DevicePresenceEvent(int associationId, int event, ParcelUuid uuid) {
        this.mAssociationId = associationId;
        this.mEvent = event;
        this.mUuid = uuid;
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mAssociationId);
        dest.writeInt(this.mEvent);
        if (this.mUuid == null) {
            dest.writeInt(0);
        } else {
            dest.writeInt(1);
            this.mUuid.writeToParcel(dest, flags);
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DevicePresenceEvent)) {
            return false;
        }
        DevicePresenceEvent that = (DevicePresenceEvent) o;
        return Objects.equals(this.mUuid, that.mUuid)
                && this.mAssociationId == that.mAssociationId
                && this.mEvent == that.mEvent;
    }

    public String toString() {
        return "ObservingDevicePresenceResult { Association Id= "
                + this.mAssociationId
                + ",ParcelUuid= "
                + this.mUuid
                + ",Event= "
                + this.mEvent
                + "}";
    }

    public int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mAssociationId), Integer.valueOf(this.mEvent), this.mUuid);
    }

    private DevicePresenceEvent(Parcel in) {
        this.mAssociationId = in.readInt();
        this.mEvent = in.readInt();
        if (in.readInt() == 0) {
            this.mUuid = null;
        } else {
            this.mUuid = ParcelUuid.CREATOR.createFromParcel(in);
        }
    }
}
