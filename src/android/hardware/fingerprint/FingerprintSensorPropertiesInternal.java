package android.hardware.fingerprint;

import android.hardware.biometrics.ComponentInfoInternal;
import android.hardware.biometrics.SensorLocationInternal;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.logging.nano.MetricsProto;
import java.util.List;

/* loaded from: classes2.dex */
public class FingerprintSensorPropertiesInternal extends SensorPropertiesInternal {
    public static final Parcelable.Creator<FingerprintSensorPropertiesInternal> CREATOR = new Parcelable.Creator<FingerprintSensorPropertiesInternal>() { // from class: android.hardware.fingerprint.FingerprintSensorPropertiesInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FingerprintSensorPropertiesInternal createFromParcel(Parcel parcel) {
            return new FingerprintSensorPropertiesInternal(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FingerprintSensorPropertiesInternal[] newArray(int i) {
            return new FingerprintSensorPropertiesInternal[i];
        }
    };
    public final boolean halControlsIllumination;
    private final List<SensorLocationInternal> mSensorLocations;
    public final int sensorType;

    @Override // android.hardware.biometrics.SensorPropertiesInternal, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FingerprintSensorPropertiesInternal(int i, int i2, int i3, List<ComponentInfoInternal> list, int i4, boolean z, boolean z2, List<SensorLocationInternal> list2) {
        super(i, i2, i3, list, z2, false);
        this.sensorType = i4;
        this.halControlsIllumination = z;
        this.mSensorLocations = List.copyOf(list2);
    }

    public FingerprintSensorPropertiesInternal(int i, int i2, int i3, List<ComponentInfoInternal> list, int i4, boolean z) {
        this(i, i2, i3, list, i4, false, z, List.of(new SensorLocationInternal("", 540, MetricsProto.MetricsEvent.FIELD_TEXT_CLASSIFIER_SECOND_ENTITY_TYPE, 130)));
    }

    protected FingerprintSensorPropertiesInternal(Parcel parcel) {
        super(parcel);
        this.sensorType = parcel.readInt();
        this.halControlsIllumination = parcel.readBoolean();
        this.mSensorLocations = parcel.createTypedArrayList(SensorLocationInternal.CREATOR);
    }

    @Override // android.hardware.biometrics.SensorPropertiesInternal, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.sensorType);
        parcel.writeBoolean(this.halControlsIllumination);
        parcel.writeTypedList(this.mSensorLocations);
    }

    public boolean isAnyUdfpsType() {
        int i = this.sensorType;
        return i == 2 || i == 3;
    }

    public boolean isUltrasonicUdfps() {
        return this.sensorType == 2;
    }

    public boolean isOpticalUdfps() {
        return this.sensorType == 3;
    }

    public boolean isAnySidefpsType() {
        return this.sensorType == 4;
    }

    public boolean isOpticalType() {
        return this.sensorType == 3;
    }

    public boolean isUltrasonicType() {
        return this.sensorType == 2;
    }

    public SensorLocationInternal getLocation() {
        SensorLocationInternal location = getLocation("");
        return location != null ? location : SensorLocationInternal.DEFAULT;
    }

    public SensorLocationInternal getLocation(String str) {
        for (SensorLocationInternal sensorLocationInternal : this.mSensorLocations) {
            if (sensorLocationInternal.displayId.equals(str)) {
                return sensorLocationInternal;
            }
        }
        return null;
    }

    public List<SensorLocationInternal> getAllLocations() {
        return this.mSensorLocations;
    }

    @Override // android.hardware.biometrics.SensorPropertiesInternal
    public String toString() {
        return "ID: " + this.sensorId + ", Strength: " + this.sensorStrength + ", Type: " + this.sensorType;
    }
}
