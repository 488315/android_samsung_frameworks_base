package android.hardware.location;

import android.annotation.SystemApi;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes2.dex */
public class GeofenceHardwareMonitorEvent implements Parcelable {
    public static final Parcelable.Creator<GeofenceHardwareMonitorEvent> CREATOR = new Parcelable.Creator<GeofenceHardwareMonitorEvent>() { // from class: android.hardware.location.GeofenceHardwareMonitorEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GeofenceHardwareMonitorEvent createFromParcel(Parcel parcel) {
            return new GeofenceHardwareMonitorEvent(parcel.readInt(), parcel.readInt(), parcel.readInt(), (Location) parcel.readParcelable(GeofenceHardwareMonitorEvent.class.getClassLoader(), Location.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GeofenceHardwareMonitorEvent[] newArray(int i) {
            return new GeofenceHardwareMonitorEvent[i];
        }
    };
    private final Location mLocation;
    private final int mMonitoringStatus;
    private final int mMonitoringType;
    private final int mSourceTechnologies;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public GeofenceHardwareMonitorEvent(int i, int i2, int i3, Location location) {
        this.mMonitoringType = i;
        this.mMonitoringStatus = i2;
        this.mSourceTechnologies = i3;
        this.mLocation = location;
    }

    public int getMonitoringType() {
        return this.mMonitoringType;
    }

    public int getMonitoringStatus() {
        return this.mMonitoringStatus;
    }

    public int getSourceTechnologies() {
        return this.mSourceTechnologies;
    }

    public Location getLocation() {
        return this.mLocation;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mMonitoringType);
        parcel.writeInt(this.mMonitoringStatus);
        parcel.writeInt(this.mSourceTechnologies);
        parcel.writeParcelable(this.mLocation, i);
    }

    public String toString() {
        return String.format("GeofenceHardwareMonitorEvent: type=%d, status=%d, sources=%d, location=%s", Integer.valueOf(this.mMonitoringType), Integer.valueOf(this.mMonitoringStatus), Integer.valueOf(this.mSourceTechnologies), this.mLocation);
    }
}
