package android.hardware.input;

import android.annotation.SystemApi;
import android.hardware.input.VirtualInputDeviceConfig;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes2.dex */
public final class VirtualDpadConfig extends VirtualInputDeviceConfig implements Parcelable {
    public static final Parcelable.Creator<VirtualDpadConfig> CREATOR = new Parcelable.Creator<VirtualDpadConfig>() { // from class: android.hardware.input.VirtualDpadConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualDpadConfig createFromParcel(Parcel parcel) {
            return new VirtualDpadConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualDpadConfig[] newArray(int i) {
            return new VirtualDpadConfig[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VirtualDpadConfig(Builder builder) {
        super(builder);
    }

    private VirtualDpadConfig(Parcel parcel) {
        super(parcel);
    }

    @Override // android.hardware.input.VirtualInputDeviceConfig, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    public static final class Builder extends VirtualInputDeviceConfig.Builder<Builder> {
        public VirtualDpadConfig build() {
            return new VirtualDpadConfig(this);
        }
    }
}
