package android.hardware.input;

import android.annotation.SystemApi;
import android.hardware.input.VirtualInputDeviceConfig;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes2.dex */
public final class VirtualNavigationTouchpadConfig extends VirtualInputDeviceConfig implements Parcelable {
    public static final Parcelable.Creator<VirtualNavigationTouchpadConfig> CREATOR = new Parcelable.Creator<VirtualNavigationTouchpadConfig>() { // from class: android.hardware.input.VirtualNavigationTouchpadConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualNavigationTouchpadConfig createFromParcel(Parcel parcel) {
            return new VirtualNavigationTouchpadConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualNavigationTouchpadConfig[] newArray(int i) {
            return new VirtualNavigationTouchpadConfig[i];
        }
    };
    private final int mHeight;
    private final int mWidth;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VirtualNavigationTouchpadConfig(Builder builder) {
        super(builder);
        this.mHeight = builder.mHeight;
        this.mWidth = builder.mWidth;
    }

    private VirtualNavigationTouchpadConfig(Parcel parcel) {
        super(parcel);
        this.mHeight = parcel.readInt();
        this.mWidth = parcel.readInt();
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getWidth() {
        return this.mWidth;
    }

    @Override // android.hardware.input.VirtualInputDeviceConfig, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mHeight);
        parcel.writeInt(this.mWidth);
    }

    @Override // android.hardware.input.VirtualInputDeviceConfig
    String additionalFieldsToString() {
        return " width=" + this.mWidth + " height=" + this.mHeight;
    }

    public static final class Builder extends VirtualInputDeviceConfig.Builder<Builder> {
        private final int mHeight;
        private final int mWidth;

        public Builder(int i, int i2) {
            if (i2 <= 0 || i <= 0) {
                throw new IllegalArgumentException("Cannot create a virtual navigation touchpad, touchpad dimensions must be positive. Got: (" + i2 + ", " + i + NavigationBarInflaterView.KEY_CODE_END);
            }
            this.mHeight = i2;
            this.mWidth = i;
        }

        public VirtualNavigationTouchpadConfig build() {
            return new VirtualNavigationTouchpadConfig(this);
        }
    }
}
