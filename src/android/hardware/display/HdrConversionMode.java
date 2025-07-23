package android.hardware.display;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.Display;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public final class HdrConversionMode implements Parcelable {
    public static final Parcelable.Creator<HdrConversionMode> CREATOR = new Parcelable.Creator<HdrConversionMode>() { // from class: android.hardware.display.HdrConversionMode.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HdrConversionMode createFromParcel(Parcel parcel) {
            return new HdrConversionMode(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HdrConversionMode[] newArray(int i) {
            return new HdrConversionMode[i];
        }
    };
    public static final int HDR_CONVERSION_FORCE = 3;
    public static final int HDR_CONVERSION_PASSTHROUGH = 1;
    public static final int HDR_CONVERSION_SYSTEM = 2;
    public static final int HDR_CONVERSION_UNSUPPORTED = 0;
    private final int mConversionMode;
    private int mPreferredHdrOutputType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConversionMode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return 0;
    }

    public HdrConversionMode(int i, int i2) {
        if ((i == 1 || i == 0) && i2 != -1) {
            throw new IllegalArgumentException("preferredHdrOutputType must not be set if the conversion mode is " + hdrConversionModeString(i));
        }
        this.mConversionMode = i;
        this.mPreferredHdrOutputType = i2;
    }

    public HdrConversionMode(int i) {
        this.mConversionMode = i;
        this.mPreferredHdrOutputType = -1;
    }

    private HdrConversionMode(Parcel parcel) {
        this(parcel.readInt(), parcel.readInt());
    }

    public int getConversionMode() {
        return this.mConversionMode;
    }

    public int getPreferredHdrOutputType() {
        return this.mPreferredHdrOutputType;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mConversionMode);
        parcel.writeInt(this.mPreferredHdrOutputType);
    }

    public boolean equals(Object obj) {
        return (obj instanceof HdrConversionMode) && equals((HdrConversionMode) obj);
    }

    public String toString() {
        return "HdrConversionMode{ConversionMode=" + hdrConversionModeString(getConversionMode()) + ", PreferredHdrOutputType=" + Display.HdrCapabilities.hdrTypeToString(getPreferredHdrOutputType()) + "}";
    }

    private boolean equals(HdrConversionMode hdrConversionMode) {
        return hdrConversionMode != null && this.mConversionMode == hdrConversionMode.getConversionMode() && this.mPreferredHdrOutputType == hdrConversionMode.getPreferredHdrOutputType();
    }

    private static String hdrConversionModeString(int i) {
        if (i == 1) {
            return "HDR_CONVERSION_PASSTHROUGH";
        }
        if (i == 2) {
            return "HDR_CONVERSION_SYSTEM";
        }
        if (i == 3) {
            return "HDR_CONVERSION_FORCE";
        }
        return "HDR_CONVERSION_UNSUPPORTED";
    }
}
