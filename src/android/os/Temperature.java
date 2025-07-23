package android.os;

import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class Temperature implements Parcelable {
    public static final Parcelable.Creator<Temperature> CREATOR = new Parcelable.Creator<Temperature>() { // from class: android.os.Temperature.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Temperature createFromParcel(Parcel parcel) {
            return new Temperature(parcel.readFloat(), parcel.readInt(), parcel.readString(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Temperature[] newArray(int i) {
            return new Temperature[i];
        }
    };
    public static final int THROTTLING_CRITICAL = 4;
    public static final int THROTTLING_EMERGENCY = 5;
    public static final int THROTTLING_LIGHT = 1;
    public static final int THROTTLING_MODERATE = 2;
    public static final int THROTTLING_NONE = 0;
    public static final int THROTTLING_SEVERE = 3;
    public static final int THROTTLING_SHUTDOWN = 6;
    public static final int TYPE_AMBIENT = 18;
    public static final int TYPE_BATTERY = 2;
    public static final int TYPE_BCL_CURRENT = 7;
    public static final int TYPE_BCL_PERCENTAGE = 8;
    public static final int TYPE_BCL_VOLTAGE = 6;
    public static final int TYPE_CAMERA = 15;
    public static final int TYPE_CPU = 0;
    public static final int TYPE_DISPLAY = 11;
    public static final int TYPE_FLASHLIGHT = 16;
    public static final int TYPE_GPU = 1;
    public static final int TYPE_MODEM = 12;
    public static final int TYPE_NPU = 9;
    public static final int TYPE_POGO = 19;
    public static final int TYPE_POWER_AMPLIFIER = 5;
    public static final int TYPE_SKIN = 3;
    public static final int TYPE_SOC = 13;
    public static final int TYPE_SPEAKER = 17;
    public static final int TYPE_TPU = 10;
    public static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_USB_PORT = 4;
    public static final int TYPE_WIFI = 14;
    private final String mName;
    private final int mStatus;
    private final int mType;
    private final float mValue;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ThrottlingStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public static boolean isValidStatus(int i) {
        return i >= 0 && i <= 6;
    }

    public static boolean isValidType(int i) {
        return i >= -1 && i <= 19;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Temperature(float f, int i, String str, int i2) {
        Preconditions.checkArgument(isValidType(i), "Invalid Type");
        Preconditions.checkArgument(isValidStatus(i2), "Invalid Status");
        this.mValue = f;
        this.mType = i;
        this.mName = (String) Preconditions.checkStringNotEmpty(str);
        this.mStatus = i2;
    }

    public float getValue() {
        return this.mValue;
    }

    public int getType() {
        return this.mType;
    }

    public String getName() {
        return this.mName;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public String toString() {
        return "Temperature{mValue=" + this.mValue + ", mType=" + this.mType + ", mName=" + this.mName + ", mStatus=" + this.mStatus + "}";
    }

    public int hashCode() {
        return (((((this.mName.hashCode() * 31) + Float.hashCode(this.mValue)) * 31) + this.mType) * 31) + this.mStatus;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Temperature)) {
            return false;
        }
        Temperature temperature = (Temperature) obj;
        return temperature.mValue == this.mValue && temperature.mType == this.mType && temperature.mName.equals(this.mName) && temperature.mStatus == this.mStatus;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.mValue);
        parcel.writeInt(this.mType);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mStatus);
    }
}
