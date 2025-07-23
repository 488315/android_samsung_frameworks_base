package android.hardware.display;

import android.hardware.Camera;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PowerManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public final class BrightnessInfo implements Parcelable {
    public static final int BRIGHTNESS_MAX_REASON_NONE = 0;
    public static final int BRIGHTNESS_MAX_REASON_POWER_IC = 2;
    public static final int BRIGHTNESS_MAX_REASON_THERMAL = 1;
    public static final int BRIGHTNESS_MAX_REASON_WEAR_BEDTIME_MODE = 3;
    public static final Parcelable.Creator<BrightnessInfo> CREATOR = new Parcelable.Creator<BrightnessInfo>() { // from class: android.hardware.display.BrightnessInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BrightnessInfo createFromParcel(Parcel parcel) {
            return new BrightnessInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BrightnessInfo[] newArray(int i) {
            return new BrightnessInfo[i];
        }
    };
    public static final int HIGH_BRIGHTNESS_MODE_HDR = 2;
    public static final int HIGH_BRIGHTNESS_MODE_OFF = 0;
    public static final int HIGH_BRIGHTNESS_MODE_SUNLIGHT = 1;
    public final float adjustedBrightness;
    public final float brightness;
    public final int brightnessMaxReason;
    public final float brightnessMaximum;
    public final float brightnessMinimum;
    public final int highBrightnessMode;
    public final float highBrightnessTransitionPoint;
    public boolean isAnimating;
    public final boolean isBrightnessOverrideByWindow;
    public String screenBrightnessOverridePackageByWindow;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BrightnessMaxReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HighBrightnessMode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BrightnessInfo(float f, float f2, float f3, int i, float f4, int i2) {
        this(f, f, f2, f3, i, f4, i2, false);
    }

    public BrightnessInfo(float f, float f2, float f3, float f4, int i, float f5, int i2, boolean z) {
        this.brightness = f;
        this.adjustedBrightness = f2;
        this.brightnessMinimum = f3;
        this.brightnessMaximum = f4;
        this.highBrightnessMode = i;
        this.highBrightnessTransitionPoint = f5;
        this.brightnessMaxReason = i2;
        this.isBrightnessOverrideByWindow = z;
    }

    public BrightnessInfo(float f, float f2, float f3, float f4, int i, float f5, int i2, boolean z, String str, boolean z2) {
        this.brightness = f;
        this.adjustedBrightness = f2;
        this.brightnessMinimum = f3;
        this.brightnessMaximum = f4;
        this.highBrightnessMode = i;
        this.highBrightnessTransitionPoint = f5;
        this.brightnessMaxReason = i2;
        this.isBrightnessOverrideByWindow = z;
        this.screenBrightnessOverridePackageByWindow = str;
        this.isAnimating = z2;
    }

    public static String hbmToString(int i) {
        if (i == 0) {
            return "off";
        }
        if (i == 1) {
            return "sunlight";
        }
        if (i == 2) {
            return Camera.Parameters.SCENE_MODE_HDR;
        }
        return "invalid";
    }

    public static String briMaxReasonToString(int i) {
        if (i == 0) {
            return "none";
        }
        if (i == 1) {
            return PowerManager.SHUTDOWN_THERMAL_STATE;
        }
        if (i == 2) {
            return "power IC";
        }
        if (i == 3) {
            return "wear bedtime";
        }
        return "invalid";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.brightness);
        parcel.writeFloat(this.adjustedBrightness);
        parcel.writeFloat(this.brightnessMinimum);
        parcel.writeFloat(this.brightnessMaximum);
        parcel.writeInt(this.highBrightnessMode);
        parcel.writeFloat(this.highBrightnessTransitionPoint);
        parcel.writeInt(this.brightnessMaxReason);
        parcel.writeBoolean(this.isBrightnessOverrideByWindow);
        parcel.writeString(this.screenBrightnessOverridePackageByWindow);
        parcel.writeBoolean(this.isAnimating);
    }

    private BrightnessInfo(Parcel parcel) {
        this.brightness = parcel.readFloat();
        this.adjustedBrightness = parcel.readFloat();
        this.brightnessMinimum = parcel.readFloat();
        this.brightnessMaximum = parcel.readFloat();
        this.highBrightnessMode = parcel.readInt();
        this.highBrightnessTransitionPoint = parcel.readFloat();
        this.brightnessMaxReason = parcel.readInt();
        this.isBrightnessOverrideByWindow = parcel.readBoolean();
        this.screenBrightnessOverridePackageByWindow = parcel.readString();
        this.isAnimating = parcel.readBoolean();
    }
}
