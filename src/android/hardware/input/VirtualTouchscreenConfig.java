package android.hardware.input;

import android.annotation.SystemApi;
import android.hardware.input.VirtualTouchDeviceConfig;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes2.dex */
public final class VirtualTouchscreenConfig extends VirtualTouchDeviceConfig implements Parcelable {
    public static final Parcelable.Creator<VirtualTouchscreenConfig> CREATOR = new Parcelable.Creator<VirtualTouchscreenConfig>() { // from class: android.hardware.input.VirtualTouchscreenConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualTouchscreenConfig createFromParcel(Parcel parcel) {
            return new VirtualTouchscreenConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualTouchscreenConfig[] newArray(int i) {
            return new VirtualTouchscreenConfig[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.hardware.input.VirtualTouchDeviceConfig
    public /* bridge */ /* synthetic */ int getHeight() {
        return super.getHeight();
    }

    @Override // android.hardware.input.VirtualTouchDeviceConfig
    public /* bridge */ /* synthetic */ int getWidth() {
        return super.getWidth();
    }

    private VirtualTouchscreenConfig(Builder builder) {
        super(builder);
    }

    private VirtualTouchscreenConfig(Parcel parcel) {
        super(parcel);
    }

    @Override // android.hardware.input.VirtualTouchDeviceConfig, android.hardware.input.VirtualInputDeviceConfig, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    public static final class Builder extends VirtualTouchDeviceConfig.Builder<Builder> {
        public Builder(int i, int i2) {
            super(i, i2);
        }

        public VirtualTouchscreenConfig build() {
            return new VirtualTouchscreenConfig(this);
        }
    }
}
