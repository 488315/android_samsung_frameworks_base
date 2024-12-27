package android.hardware.input;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes2.dex */
public final class VirtualTouchscreenConfig extends VirtualTouchDeviceConfig implements Parcelable {
    public static final Parcelable.Creator<VirtualTouchscreenConfig> CREATOR =
            new Parcelable.Creator<VirtualTouchscreenConfig>() { // from class:
                // android.hardware.input.VirtualTouchscreenConfig.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VirtualTouchscreenConfig createFromParcel(Parcel in) {
                    return new VirtualTouchscreenConfig(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VirtualTouchscreenConfig[] newArray(int size) {
                    return new VirtualTouchscreenConfig[size];
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

    private VirtualTouchscreenConfig(Builder builder) {
        super(builder);
    }

    private VirtualTouchscreenConfig(Parcel in) {
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
        public Builder(int touchscreenWidth, int touchscreenHeight) {
            super(touchscreenWidth, touchscreenHeight);
        }

        public VirtualTouchscreenConfig build() {
            return new VirtualTouchscreenConfig(this);
        }
    }
}
