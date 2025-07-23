package android.hardware.face;

import android.hardware.biometrics.ComponentInfoInternal;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/* loaded from: classes2.dex */
public class FaceSensorPropertiesInternal extends SensorPropertiesInternal {
    public static final Parcelable.Creator<FaceSensorPropertiesInternal> CREATOR = new Parcelable.Creator<FaceSensorPropertiesInternal>() { // from class: android.hardware.face.FaceSensorPropertiesInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceSensorPropertiesInternal createFromParcel(Parcel parcel) {
            return new FaceSensorPropertiesInternal(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceSensorPropertiesInternal[] newArray(int i) {
            return new FaceSensorPropertiesInternal[i];
        }
    };
    public final int sensorType;
    public final boolean supportsFaceDetection;
    public final boolean supportsSelfIllumination;

    @Override // android.hardware.biometrics.SensorPropertiesInternal, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FaceSensorPropertiesInternal(int i, int i2, int i3, List<ComponentInfoInternal> list, int i4, boolean z, boolean z2, boolean z3) {
        super(i, i2, i3, list, false, z3);
        this.sensorType = i4;
        this.supportsFaceDetection = z;
        this.supportsSelfIllumination = z2;
    }

    protected FaceSensorPropertiesInternal(Parcel parcel) {
        super(parcel);
        this.sensorType = parcel.readInt();
        this.supportsFaceDetection = parcel.readBoolean();
        this.supportsSelfIllumination = parcel.readBoolean();
    }

    @Override // android.hardware.biometrics.SensorPropertiesInternal, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.sensorType);
        parcel.writeBoolean(this.supportsFaceDetection);
        parcel.writeBoolean(this.supportsSelfIllumination);
    }

    @Override // android.hardware.biometrics.SensorPropertiesInternal
    public String toString() {
        return "ID: " + this.sensorId + ", Strength: " + this.sensorStrength + ", Type: " + this.sensorType + ", SupportsFaceDetection: " + this.supportsFaceDetection;
    }
}
