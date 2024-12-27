package android.hardware.input;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class VirtualMouseConfig extends VirtualInputDeviceConfig implements Parcelable {
    public static final Parcelable.Creator<VirtualMouseConfig> CREATOR =
            new Parcelable.Creator<VirtualMouseConfig>() { // from class:
                // android.hardware.input.VirtualMouseConfig.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VirtualMouseConfig createFromParcel(Parcel in) {
                    return new VirtualMouseConfig(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VirtualMouseConfig[] newArray(int size) {
                    return new VirtualMouseConfig[size];
                }
            };

    private VirtualMouseConfig(Builder builder) {
        super(builder);
    }

    private VirtualMouseConfig(Parcel in) {
        super(in);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.hardware.input.VirtualInputDeviceConfig, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public static final class Builder extends VirtualInputDeviceConfig.Builder<Builder> {
        public VirtualMouseConfig build() {
            return new VirtualMouseConfig(this);
        }
    }
}
