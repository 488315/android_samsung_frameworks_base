package android.hardware.input;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@SystemApi
public abstract class VirtualInputDeviceConfig {
    private static final int DEVICE_NAME_MAX_LENGTH = 80;
    private final int mAssociatedDisplayId;
    private final String mInputDeviceName;
    private final int mProductId;
    private final int mVendorId;

    protected VirtualInputDeviceConfig(Builder<? extends Builder<?>> builder) {
        this.mVendorId = ((Builder) builder).mVendorId;
        this.mProductId = ((Builder) builder).mProductId;
        this.mAssociatedDisplayId = ((Builder) builder).mAssociatedDisplayId;
        this.mInputDeviceName =
                (String) Objects.requireNonNull(((Builder) builder).mInputDeviceName);
        if (this.mAssociatedDisplayId == -1) {
            throw new IllegalArgumentException(
                    "Display association is required for virtual input devices.");
        }
        if (this.mInputDeviceName.getBytes(StandardCharsets.UTF_8).length >= 80) {
            throw new IllegalArgumentException(
                    "Input device name exceeds maximum length of 80bytes: "
                            + this.mInputDeviceName);
        }
    }

    protected VirtualInputDeviceConfig(Parcel in) {
        this.mVendorId = in.readInt();
        this.mProductId = in.readInt();
        this.mAssociatedDisplayId = in.readInt();
        this.mInputDeviceName = (String) Objects.requireNonNull(in.readString8());
    }

    public int getVendorId() {
        return this.mVendorId;
    }

    public int getProductId() {
        return this.mProductId;
    }

    public int getAssociatedDisplayId() {
        return this.mAssociatedDisplayId;
    }

    public String getInputDeviceName() {
        return this.mInputDeviceName;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mVendorId);
        dest.writeInt(this.mProductId);
        dest.writeInt(this.mAssociatedDisplayId);
        dest.writeString8(this.mInputDeviceName);
    }

    public String toString() {
        return getClass().getName()
                + "(  name="
                + this.mInputDeviceName
                + " vendorId="
                + this.mVendorId
                + " productId="
                + this.mProductId
                + " associatedDisplayId="
                + this.mAssociatedDisplayId
                + additionalFieldsToString()
                + NavigationBarInflaterView.KEY_CODE_END;
    }

    String additionalFieldsToString() {
        return "";
    }

    public abstract static class Builder<T extends Builder<T>> {
        private int mAssociatedDisplayId = -1;
        private String mInputDeviceName;
        private int mProductId;
        private int mVendorId;

        public T setVendorId(int vendorId) {
            this.mVendorId = vendorId;
            return self();
        }

        public T setProductId(int productId) {
            this.mProductId = productId;
            return self();
        }

        public T setAssociatedDisplayId(int displayId) {
            this.mAssociatedDisplayId = displayId;
            return self();
        }

        public T setInputDeviceName(String deviceName) {
            this.mInputDeviceName = (String) Objects.requireNonNull(deviceName);
            return self();
        }

        T self() {
            return this;
        }
    }
}
