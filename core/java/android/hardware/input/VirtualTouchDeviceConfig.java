package android.hardware.input;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;

abstract class VirtualTouchDeviceConfig extends VirtualInputDeviceConfig {
    private final int mHeight;
    private final int mWidth;

    VirtualTouchDeviceConfig(Builder<? extends Builder<?>> builder) {
        super(builder);
        this.mWidth = ((Builder) builder).mWidth;
        this.mHeight = ((Builder) builder).mHeight;
    }

    VirtualTouchDeviceConfig(Parcel in) {
        super(in);
        this.mWidth = in.readInt();
        this.mHeight = in.readInt();
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // android.hardware.input.VirtualInputDeviceConfig, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mWidth);
        dest.writeInt(this.mHeight);
    }

    @Override // android.hardware.input.VirtualInputDeviceConfig
    String additionalFieldsToString() {
        return " width=" + this.mWidth + " height=" + this.mHeight;
    }

    abstract static class Builder<T extends Builder<T>>
            extends VirtualInputDeviceConfig.Builder<T> {
        private final int mHeight;
        private final int mWidth;

        Builder(int touchDeviceWidth, int touchDeviceHeight) {
            if (touchDeviceHeight <= 0 || touchDeviceWidth <= 0) {
                throw new IllegalArgumentException(
                        "Cannot create a virtual touch-based device, dimensions must be positive."
                                + " Got: ("
                                + touchDeviceHeight
                                + ", "
                                + touchDeviceWidth
                                + NavigationBarInflaterView.KEY_CODE_END);
            }
            this.mHeight = touchDeviceHeight;
            this.mWidth = touchDeviceWidth;
        }
    }
}
