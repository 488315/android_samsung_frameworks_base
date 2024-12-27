package android.hardware.lights;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Light implements Parcelable {
    public static final Parcelable.Creator<Light> CREATOR =
            new Parcelable.Creator<Light>() { // from class: android.hardware.lights.Light.1
                @Override // android.os.Parcelable.Creator
                public Light createFromParcel(Parcel in) {
                    return new Light(in);
                }

                @Override // android.os.Parcelable.Creator
                public Light[] newArray(int size) {
                    return new Light[size];
                }
            };
    public static final int LIGHT_CAPABILITY_BRIGHTNESS = 1;
    public static final int LIGHT_CAPABILITY_COLOR_RGB = 2;

    @Deprecated public static final int LIGHT_CAPABILITY_RGB = 0;
    public static final int LIGHT_TYPE_CAMERA = 9;
    public static final int LIGHT_TYPE_INPUT = 10001;
    public static final int LIGHT_TYPE_KEYBOARD_BACKLIGHT = 10003;
    public static final int LIGHT_TYPE_KEYBOARD_MIC_MUTE = 10004;
    public static final int LIGHT_TYPE_MICROPHONE = 8;
    public static final int LIGHT_TYPE_PLAYER_ID = 10002;
    private final int mCapabilities;
    private final int mId;
    private final String mName;
    private final int mOrdinal;
    private final int[] mPreferredBrightnessLevels;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface LightCapability {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface LightType {}

    public Light(int id, int ordinal, int type) {
        this(id, "Light", ordinal, type, 0, null);
    }

    public Light(int id, String name, int ordinal, int type, int capabilities) {
        this(id, name, ordinal, type, capabilities, null);
    }

    public Light(
            int id,
            String name,
            int ordinal,
            int type,
            int capabilities,
            int[] preferredBrightnessLevels) {
        this.mId = id;
        this.mName = name;
        this.mOrdinal = ordinal;
        this.mType = type;
        this.mCapabilities = capabilities;
        this.mPreferredBrightnessLevels = preferredBrightnessLevels;
    }

    private Light(Parcel in) {
        this.mId = in.readInt();
        this.mName = in.readString();
        this.mOrdinal = in.readInt();
        this.mType = in.readInt();
        this.mCapabilities = in.readInt();
        this.mPreferredBrightnessLevels = in.createIntArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mName);
        dest.writeInt(this.mOrdinal);
        dest.writeInt(this.mType);
        dest.writeInt(this.mCapabilities);
        dest.writeIntArray(this.mPreferredBrightnessLevels);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Light)) {
            return false;
        }
        Light light = (Light) obj;
        return this.mId == light.mId
                && this.mOrdinal == light.mOrdinal
                && this.mType == light.mType
                && this.mCapabilities == light.mCapabilities;
    }

    public int hashCode() {
        return this.mId;
    }

    public String toString() {
        return "[Name="
                + this.mName
                + " Id="
                + this.mId
                + " Type="
                + this.mType
                + " Capabilities="
                + this.mCapabilities
                + " Ordinal="
                + this.mOrdinal
                + NavigationBarInflaterView.SIZE_MOD_END;
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
