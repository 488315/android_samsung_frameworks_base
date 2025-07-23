package android.hardware.input;

import android.annotation.SystemApi;
import android.hardware.input.VirtualInputDeviceConfig;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes2.dex */
public final class VirtualMouseConfig extends VirtualInputDeviceConfig implements Parcelable {
    public static final Parcelable.Creator<VirtualMouseConfig> CREATOR = new Parcelable.Creator<VirtualMouseConfig>() { // from class: android.hardware.input.VirtualMouseConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualMouseConfig createFromParcel(Parcel parcel) {
            return new VirtualMouseConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualMouseConfig[] newArray(int i) {
            return new VirtualMouseConfig[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VirtualMouseConfig(Builder builder) {
        super(builder);
    }

    private VirtualMouseConfig(Parcel parcel) {
        super(parcel);
    }

    @Override // android.hardware.input.VirtualInputDeviceConfig, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    public static final class Builder extends VirtualInputDeviceConfig.Builder<Builder> {
        public VirtualMouseConfig build() {
            return new VirtualMouseConfig(this);
        }
    }
}
