package android.hardware.input;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class VirtualStylusConfig extends VirtualTouchDeviceConfig implements Parcelable {
    public static final Parcelable.Creator<VirtualStylusConfig> CREATOR =
            new Parcelable.Creator<VirtualStylusConfig>() { // from class:
                // android.hardware.input.VirtualStylusConfig.1
                @Override // android.os.Parcelable.Creator
                public VirtualStylusConfig createFromParcel(Parcel in) {
                    return new VirtualStylusConfig(in);
                }

                @Override // android.os.Parcelable.Creator
                public VirtualStylusConfig[] newArray(int size) {
                    return new VirtualStylusConfig[size];
                }
            };

    @Override // android.hardware.input.VirtualTouchDeviceConfig
    public /* bridge */ /* synthetic */ int getHeight() {
        return super.getHeight();
    }

    @Override // android.hardware.input.VirtualTouchDeviceConfig
    public /* bridge */ /* synthetic */ int getWidth() {
        return super.getWidth();
    }

    private VirtualStylusConfig(Builder builder) {
        super(builder);
    }

    private VirtualStylusConfig(Parcel in) {
        super(in);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.hardware.input.VirtualTouchDeviceConfig,
    // android.hardware.input.VirtualInputDeviceConfig, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public static final class Builder extends VirtualTouchDeviceConfig.Builder<Builder> {
        public Builder(int screenWidth, int screenHeight) {
            super(screenWidth, screenHeight);
        }

        public VirtualStylusConfig build() {
            return new VirtualStylusConfig(this);
        }
    }
}
