package android.hardware.location;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class GeofenceHardwareRequestParcelable implements Parcelable {
    public static final Parcelable.Creator<GeofenceHardwareRequestParcelable> CREATOR = new Parcelable.Creator<GeofenceHardwareRequestParcelable>() { // from class: android.hardware.location.GeofenceHardwareRequestParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GeofenceHardwareRequestParcelable createFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            if (i != 0) {
                throw new BadParcelableException("Invalid Geofence type: " + i);
            }
            GeofenceHardwareRequest geofenceHardwareRequestCreateCircularGeofence = GeofenceHardwareRequest.createCircularGeofence(parcel.readDouble(), parcel.readDouble(), parcel.readDouble());
            geofenceHardwareRequestCreateCircularGeofence.setLastTransition(parcel.readInt());
            geofenceHardwareRequestCreateCircularGeofence.setMonitorTransitions(parcel.readInt());
            geofenceHardwareRequestCreateCircularGeofence.setUnknownTimer(parcel.readInt());
            geofenceHardwareRequestCreateCircularGeofence.setNotificationResponsiveness(parcel.readInt());
            geofenceHardwareRequestCreateCircularGeofence.setSourceTechnologies(parcel.readInt());
            return new GeofenceHardwareRequestParcelable(parcel.readInt(), geofenceHardwareRequestCreateCircularGeofence);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GeofenceHardwareRequestParcelable[] newArray(int i) {
            return new GeofenceHardwareRequestParcelable[i];
        }
    };
    private int mId;
    private GeofenceHardwareRequest mRequest;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public GeofenceHardwareRequestParcelable(int i, GeofenceHardwareRequest geofenceHardwareRequest) {
        this.mId = i;
        this.mRequest = geofenceHardwareRequest;
    }

    public int getId() {
        return this.mId;
    }

    public double getLatitude() {
        return this.mRequest.getLatitude();
    }

    public double getLongitude() {
        return this.mRequest.getLongitude();
    }

    public double getRadius() {
        return this.mRequest.getRadius();
    }

    public int getMonitorTransitions() {
        return this.mRequest.getMonitorTransitions();
    }

    public int getUnknownTimer() {
        return this.mRequest.getUnknownTimer();
    }

    public int getNotificationResponsiveness() {
        return this.mRequest.getNotificationResponsiveness();
    }

    public int getLastTransition() {
        return this.mRequest.getLastTransition();
    }

    int getType() {
        return this.mRequest.getType();
    }

    int getSourceTechnologies() {
        return this.mRequest.getSourceTechnologies();
    }

    public String toString() {
        return "id=" + this.mId + ", type=" + this.mRequest.getType() + ", latitude=" + this.mRequest.getLatitude() + ", longitude=" + this.mRequest.getLongitude() + ", radius=" + this.mRequest.getRadius() + ", lastTransition=" + this.mRequest.getLastTransition() + ", unknownTimer=" + this.mRequest.getUnknownTimer() + ", monitorTransitions=" + this.mRequest.getMonitorTransitions() + ", notificationResponsiveness=" + this.mRequest.getNotificationResponsiveness() + ", sourceTechnologies=" + this.mRequest.getSourceTechnologies();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getType());
        parcel.writeDouble(getLatitude());
        parcel.writeDouble(getLongitude());
        parcel.writeDouble(getRadius());
        parcel.writeInt(getLastTransition());
        parcel.writeInt(getMonitorTransitions());
        parcel.writeInt(getUnknownTimer());
        parcel.writeInt(getNotificationResponsiveness());
        parcel.writeInt(getSourceTechnologies());
        parcel.writeInt(getId());
    }
}
