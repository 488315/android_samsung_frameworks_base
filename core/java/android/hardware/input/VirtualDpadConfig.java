package android.hardware.input;

import android.annotation.SystemApi;
import android.hardware.input.VirtualInputDeviceConfig;
import android.p009os.Parcel;
import android.p009os.Parcelable;

@SystemApi
/* loaded from: classes2.dex */
public final class VirtualDpadConfig extends VirtualInputDeviceConfig implements Parcelable {
    public static final Parcelable.Creator<VirtualDpadConfig> CREATOR = new Parcelable.Creator<VirtualDpadConfig>() { // from class: android.hardware.input.VirtualDpadConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualDpadConfig createFromParcel(Parcel in) {
            return new VirtualDpadConfig(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
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

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.hardware.input.VirtualInputDeviceConfig, android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public static final class Builder extends VirtualInputDeviceConfig.Builder<Builder> {
        public VirtualDpadConfig build() {
            return new VirtualDpadConfig(this);
        }
    }
}
