package android.hardware.input;

import android.annotation.SystemApi;
import android.hardware.input.VirtualInputDeviceConfig;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes2.dex */
public final class VirtualRotaryEncoderConfig extends VirtualInputDeviceConfig implements Parcelable {
    public static final Parcelable.Creator<VirtualRotaryEncoderConfig> CREATOR = new Parcelable.Creator<VirtualRotaryEncoderConfig>() { // from class: android.hardware.input.VirtualRotaryEncoderConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualRotaryEncoderConfig createFromParcel(Parcel parcel) {
            return new VirtualRotaryEncoderConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualRotaryEncoderConfig[] newArray(int i) {
            return new VirtualRotaryEncoderConfig[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VirtualRotaryEncoderConfig(Builder builder) {
        super(builder);
    }

    private VirtualRotaryEncoderConfig(Parcel parcel) {
        super(parcel);
    }

    @Override // android.hardware.input.VirtualInputDeviceConfig, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    public static final class Builder extends VirtualInputDeviceConfig.Builder<Builder> {
        public VirtualRotaryEncoderConfig build() {
            return new VirtualRotaryEncoderConfig(this);
        }
    }
}
