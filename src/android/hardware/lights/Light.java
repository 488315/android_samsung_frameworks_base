package android.hardware.lights;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public final class Light implements Parcelable {
    public static final Parcelable.Creator<Light> CREATOR = new Parcelable.Creator<Light>() { // from class: android.hardware.lights.Light.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Light createFromParcel(Parcel parcel) {
            return new Light(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Light[] newArray(int i) {
            return new Light[i];
        }
    };
    public static final int LIGHT_CAPABILITY_BRIGHTNESS = 1;
    public static final int LIGHT_CAPABILITY_COLOR_RGB = 2;

    @Deprecated
    public static final int LIGHT_CAPABILITY_RGB = 0;
    public static final int LIGHT_TYPE_CAMERA = 9;
    public static final int LIGHT_TYPE_INPUT = 10001;
    public static final int LIGHT_TYPE_KEYBOARD_BACKLIGHT = 10003;
    public static final int LIGHT_TYPE_KEYBOARD_MIC_MUTE = 10004;
    public static final int LIGHT_TYPE_KEYBOARD_VOLUME_MUTE = 10005;
    public static final int LIGHT_TYPE_MICROPHONE = 8;
    public static final int LIGHT_TYPE_PLAYER_ID = 10002;
    private final int mCapabilities;
    private final int mId;
    private final String mName;
    private final int mOrdinal;
    private final int[] mPreferredBrightnessLevels;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface LightCapability {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LightType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Light(int i, int i2, int i3) {
        this(i, "Light", i2, i3, 0, null);
    }

    public Light(int i, String str, int i2, int i3, int i4) {
        this(i, str, i2, i3, i4, null);
    }

    public Light(int i, String str, int i2, int i3, int i4, int[] iArr) {
        this.mId = i;
        this.mName = str;
        this.mOrdinal = i2;
        this.mType = i3;
        this.mCapabilities = i4;
        this.mPreferredBrightnessLevels = iArr;
    }

    private Light(Parcel parcel) {
        this.mId = parcel.readInt();
        this.mName = parcel.readString();
        this.mOrdinal = parcel.readInt();
        this.mType = parcel.readInt();
        this.mCapabilities = parcel.readInt();
        this.mPreferredBrightnessLevels = parcel.createIntArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mOrdinal);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mCapabilities);
        parcel.writeIntArray(this.mPreferredBrightnessLevels);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Light) {
            Light light = (Light) obj;
            if (this.mId == light.mId && this.mOrdinal == light.mOrdinal && this.mType == light.mType && this.mCapabilities == light.mCapabilities) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.mId;
    }

    public String toString() {
        return "[Name=" + this.mName + " Id=" + this.mId + " Type=" + this.mType + " Capabilities=" + this.mCapabilities + " Ordinal=" + this.mOrdinal + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public int getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

    public int getOrdinal() {
        return this.mOrdinal;
    }

    public int getType() {
        return this.mType;
    }

    public int getCapabilities() {
        return this.mCapabilities;
    }

    public boolean hasBrightnessControl() {
        return (this.mCapabilities & 1) == 1;
    }

    public boolean hasRgbControl() {
        return (this.mCapabilities & 2) == 2;
    }

    public int[] getPreferredBrightnessLevels() {
        return this.mPreferredBrightnessLevels;
    }
}
