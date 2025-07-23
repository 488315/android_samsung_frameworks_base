package android.hardware.thermal;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class TemperatureThreshold implements Parcelable {
    public static final Parcelable.Creator<TemperatureThreshold> CREATOR = new Parcelable.Creator<TemperatureThreshold>() { // from class: android.hardware.thermal.TemperatureThreshold.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TemperatureThreshold createFromParcel(Parcel parcel) {
            TemperatureThreshold temperatureThreshold = new TemperatureThreshold();
            temperatureThreshold.readFromParcel(parcel);
            return temperatureThreshold;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TemperatureThreshold[] newArray(int i) {
            return new TemperatureThreshold[i];
        }
    };
    public float[] coldThrottlingThresholds;
    public float[] hotThrottlingThresholds;
    public String name;
    public int type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.type);
        parcel.writeString(this.name);
        parcel.writeFloatArray(this.hotThrottlingThresholds);
        parcel.writeFloatArray(this.coldThrottlingThresholds);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.type = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.name = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.hotThrottlingThresholds = parcel.createFloatArray();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.coldThrottlingThresholds = parcel.createFloatArray();
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("name: " + Objects.toString(this.name));
        stringJoiner.add("hotThrottlingThresholds: " + Arrays.toString(this.hotThrottlingThresholds));
        stringJoiner.add("coldThrottlingThresholds: " + Arrays.toString(this.coldThrottlingThresholds));
        return "TemperatureThreshold" + stringJoiner.toString();
    }
}
