package android.hardware.biometrics;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class ComponentInfoInternal implements Parcelable {
    public static final Parcelable.Creator<ComponentInfoInternal> CREATOR = new Parcelable.Creator<ComponentInfoInternal>() { // from class: android.hardware.biometrics.ComponentInfoInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ComponentInfoInternal createFromParcel(Parcel parcel) {
            return new ComponentInfoInternal(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ComponentInfoInternal[] newArray(int i) {
            return new ComponentInfoInternal[i];
        }
    };
    public final String componentId;
    public final String firmwareVersion;
    public final String hardwareVersion;
    public final String serialNumber;
    public final String softwareVersion;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static ComponentInfoInternal from(ComponentInfoInternal componentInfoInternal) {
        return new ComponentInfoInternal(componentInfoInternal.componentId, componentInfoInternal.hardwareVersion, componentInfoInternal.firmwareVersion, componentInfoInternal.serialNumber, componentInfoInternal.softwareVersion);
    }

    public ComponentInfoInternal(String str, String str2, String str3, String str4, String str5) {
        this.componentId = str;
        this.hardwareVersion = str2;
        this.firmwareVersion = str3;
        this.serialNumber = str4;
        this.softwareVersion = str5;
    }

    protected ComponentInfoInternal(Parcel parcel) {
        this.componentId = parcel.readString();
        this.hardwareVersion = parcel.readString();
        this.firmwareVersion = parcel.readString();
        this.serialNumber = parcel.readString();
        this.softwareVersion = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.componentId);
        parcel.writeString(this.hardwareVersion);
        parcel.writeString(this.firmwareVersion);
        parcel.writeString(this.serialNumber);
        parcel.writeString(this.softwareVersion);
    }

    public String toString() {
        return "ComponentId: " + this.componentId + ", HardwareVersion: " + this.hardwareVersion + ", FirmwareVersion: " + this.firmwareVersion + ", SerialNumber " + this.serialNumber + ", SoftwareVersion: " + this.softwareVersion;
    }
}
