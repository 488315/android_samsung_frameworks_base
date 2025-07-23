package android.hardware.input;

import android.hardware.input.VirtualInputDeviceConfig;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;

/* loaded from: classes2.dex */
abstract class VirtualTouchDeviceConfig extends VirtualInputDeviceConfig {
    private final int mHeight;
    private final int mWidth;

    VirtualTouchDeviceConfig(Builder<? extends Builder<?>> builder) {
        super(builder);
        this.mWidth = ((Builder) builder).mWidth;
        this.mHeight = ((Builder) builder).mHeight;
    }

    VirtualTouchDeviceConfig(Parcel parcel) {
        super(parcel);
        this.mWidth = parcel.readInt();
        this.mHeight = parcel.readInt();
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // android.hardware.input.VirtualInputDeviceConfig, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
    }

    @Override // android.hardware.input.VirtualInputDeviceConfig
    String additionalFieldsToString() {
        return " width=" + this.mWidth + " height=" + this.mHeight;
    }

    static abstract class Builder<T extends Builder<T>> extends VirtualInputDeviceConfig.Builder<T> {
        private final int mHeight;
        private final int mWidth;

        Builder(int i, int i2) {
            if (i2 <= 0 || i <= 0) {
                throw new IllegalArgumentException("Cannot create a virtual touch-based device, dimensions must be positive. Got: (" + i2 + ", " + i + NavigationBarInflaterView.KEY_CODE_END);
            }
            this.mHeight = i2;
            this.mWidth = i;
        }
    }
}
