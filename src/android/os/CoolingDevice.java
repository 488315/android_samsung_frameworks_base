package android.os;

import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class CoolingDevice implements Parcelable {
    public static final Parcelable.Creator<CoolingDevice> CREATOR = new Parcelable.Creator<CoolingDevice>() { // from class: android.os.CoolingDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CoolingDevice createFromParcel(Parcel parcel) {
            return new CoolingDevice(parcel.readLong(), parcel.readInt(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CoolingDevice[] newArray(int i) {
            return new CoolingDevice[i];
        }
    };
    public static final int TYPE_BATTERY = 1;
    public static final int TYPE_CAMERA = 12;
    public static final int TYPE_COMPONENT = 6;
    public static final int TYPE_CPU = 2;
    public static final int TYPE_DISPLAY = 9;
    public static final int TYPE_FAN = 0;
    public static final int TYPE_FLASHLIGHT = 13;
    public static final int TYPE_GPU = 3;
    public static final int TYPE_MODEM = 4;
    public static final int TYPE_NPU = 5;
    public static final int TYPE_POWER_AMPLIFIER = 8;
    public static final int TYPE_SPEAKER = 10;
    public static final int TYPE_TPU = 7;
    public static final int TYPE_USB_PORT = 14;
    public static final int TYPE_WIFI = 11;
    private final String mName;
    private final int mType;
    private final long mValue;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public static boolean isValidType(int i) {
        return i >= 0 && i <= 14;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CoolingDevice(long j, int i, String str) {
        Preconditions.checkArgument(isValidType(i), "Invalid Type");
        this.mValue = j;
        this.mType = i;
        this.mName = (String) Preconditions.checkStringNotEmpty(str);
    }

    public long getValue() {
        return this.mValue;
    }

    public int getType() {
        return this.mType;
    }

    public String getName() {
        return this.mName;
    }

    public String toString() {
        return "CoolingDevice{mValue=" + this.mValue + ", mType=" + this.mType + ", mName=" + this.mName + "}";
    }

    public int hashCode() {
        return (((this.mName.hashCode() * 31) + Long.hashCode(this.mValue)) * 31) + this.mType;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CoolingDevice)) {
            return false;
        }
        CoolingDevice coolingDevice = (CoolingDevice) obj;
        return coolingDevice.mValue == this.mValue && coolingDevice.mType == this.mType && coolingDevice.mName.equals(this.mName);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mValue);
        parcel.writeInt(this.mType);
        parcel.writeString(this.mName);
    }
}
