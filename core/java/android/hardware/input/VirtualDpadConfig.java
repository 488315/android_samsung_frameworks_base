package android.hardware.input;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class VirtualDpadConfig extends VirtualInputDeviceConfig implements Parcelable {
    public static final Parcelable.Creator<VirtualDpadConfig> CREATOR =
            new Parcelable.Creator<
                    VirtualDpadConfig>() { // from class: android.hardware.input.VirtualDpadConfig.1
                @Override // android.os.Parcelable.Creator
                public VirtualDpadConfig createFromParcel(Parcel in) {
                    return new VirtualDpadConfig(in);
                }

                @Override // android.os.Parcelable.Creator
                public VirtualDpadConfig[] newArray(int size) {
                    return new VirtualDpadConfig[size];
                }
            };

    private VirtualDpadConfig(Builder builder) {
        super(builder);
    }

    private VirtualDpadConfig(Parcel in) {
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
        public VirtualDpadConfig build() {
            return new VirtualDpadConfig(this);
        }
    }
}
