package android.companion.virtual.sensor;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

@SystemApi
/* loaded from: classes.dex */
public final class VirtualSensorAdditionalInfo implements Parcelable {
    public static final Parcelable.Creator<VirtualSensorAdditionalInfo> CREATOR = new Parcelable.Creator<VirtualSensorAdditionalInfo>() { // from class: android.companion.virtual.sensor.VirtualSensorAdditionalInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualSensorAdditionalInfo createFromParcel(Parcel parcel) {
            return new VirtualSensorAdditionalInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualSensorAdditionalInfo[] newArray(int i) {
            return new VirtualSensorAdditionalInfo[i];
        }
    };
    private final int mType;
    private final List<float[]> mValues;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VirtualSensorAdditionalInfo(int i, List<float[]> list) {
        this.mType = i;
        this.mValues = list;
    }

    private VirtualSensorAdditionalInfo(Parcel parcel) {
        this.mType = parcel.readInt();
        int i = parcel.readInt();
        this.mValues = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            this.mValues.add(parcel.createFloatArray());
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mValues.size());
        for (int i2 = 0; i2 < this.mValues.size(); i2++) {
            parcel.writeFloatArray(this.mValues.get(i2));
        }
    }

    public int getType() {
        return this.mType;
    }

    public List<float[]> getValues() {
        return this.mValues;
    }

    public static final class Builder {
        private static final int TYPE_INTERNAL_TEMPERATURE_PLAYLOAD_SIZE = 1;
        private static final int TYPE_SAMPLING_PLAYLOAD_SIZE = 2;
        private static final int TYPE_SENSOR_PLACEMENT_PAYLOAD_SIZE = 12;
        private static final int TYPE_UNTRACKED_DELAY_PAYLOAD_SIZE = 2;
        private static final int TYPE_VEC3_CALIBRATION_PAYLOAD_SIZE = 12;
        private final int mType;
        private final ArrayList<float[]> mValues = new ArrayList<>();

        public Builder(int i) {
            switch (i) {
                case 65536:
                case 65537:
                case 65538:
                case 65539:
                case 65540:
                    this.mType = i;
                    return;
                default:
                    throw new IllegalArgumentException("Unsupported type " + i);
            }
        }

        public Builder addValues(float[] fArr) {
            if (this.mValues.isEmpty()) {
                switch (this.mType) {
                    case 65536:
                        assertValuesLength(fArr, 2);
                        break;
                    case 65537:
                        assertValuesLength(fArr, 1);
                        break;
                    case 65538:
                        assertValuesLength(fArr, 12);
                        break;
                    case 65539:
                        assertValuesLength(fArr, 12);
                        break;
                    case 65540:
                        assertValuesLength(fArr, 2);
                        break;
                }
            } else if (fArr.length != ((float[]) this.mValues.getFirst()).length) {
                throw new IllegalArgumentException("All payload values must have the same length");
            }
            this.mValues.add(fArr);
            return this;
        }

        private void assertValuesLength(float[] fArr, int i) {
            if (fArr.length == i) {
                return;
            }
            throw new IllegalArgumentException("Payload values must have size " + i + " for type " + this.mType);
        }

        public VirtualSensorAdditionalInfo build() {
            if (this.mValues.isEmpty()) {
                throw new IllegalArgumentException("Payload is required");
            }
            return new VirtualSensorAdditionalInfo(this.mType, this.mValues);
        }
    }
}
