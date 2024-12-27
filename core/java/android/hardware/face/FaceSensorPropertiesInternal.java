package android.hardware.face;

import android.hardware.biometrics.ComponentInfoInternal;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/* loaded from: classes2.dex */
public class FaceSensorPropertiesInternal extends SensorPropertiesInternal {
    public static final Parcelable.Creator<FaceSensorPropertiesInternal> CREATOR =
            new Parcelable.Creator<FaceSensorPropertiesInternal>() { // from class:
                // android.hardware.face.FaceSensorPropertiesInternal.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FaceSensorPropertiesInternal createFromParcel(Parcel in) {
                    return new FaceSensorPropertiesInternal(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FaceSensorPropertiesInternal[] newArray(int size) {
                    return new FaceSensorPropertiesInternal[size];
                }
            };
    public final int sensorType;
    public final boolean supportsFaceDetection;
    public final boolean supportsSelfIllumination;

    public FaceSensorPropertiesInternal(
            int sensorId,
            int strength,
            int maxEnrollmentsPerUser,
            List<ComponentInfoInternal> componentInfo,
            int sensorType,
            boolean supportsFaceDetection,
            boolean supportsSelfIllumination,
            boolean resetLockoutRequiresChallenge) {
        super(
                sensorId,
                strength,
                maxEnrollmentsPerUser,
                componentInfo,
                false,
                resetLockoutRequiresChallenge);
        this.sensorType = sensorType;
        this.supportsFaceDetection = supportsFaceDetection;
        this.supportsSelfIllumination = supportsSelfIllumination;
    }

    protected FaceSensorPropertiesInternal(Parcel in) {
        super(in);
        this.sensorType = in.readInt();
        this.supportsFaceDetection = in.readBoolean();
        this.supportsSelfIllumination = in.readBoolean();
    }

    @Override // android.hardware.biometrics.SensorPropertiesInternal, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.hardware.biometrics.SensorPropertiesInternal, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.sensorType);
        dest.writeBoolean(this.supportsFaceDetection);
        dest.writeBoolean(this.supportsSelfIllumination);
    }

    @Override // android.hardware.biometrics.SensorPropertiesInternal
    public String toString() {
        return "ID: "
                + this.sensorId
                + ", Strength: "
                + this.sensorStrength
                + ", Type: "
                + this.sensorType
                + ", SupportsFaceDetection: "
                + this.supportsFaceDetection;
    }
}
