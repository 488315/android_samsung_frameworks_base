package android.hardware.input;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public abstract class VirtualInputDeviceConfig {
    private static final int DEVICE_NAME_MAX_LENGTH = 80;
    private final int mAssociatedDisplayId;
    private final String mInputDeviceName;
    private final int mProductId;
    private final int mVendorId;

    protected VirtualInputDeviceConfig(Builder<? extends Builder<?>> builder) {
        this.mVendorId = ((Builder) builder).mVendorId;
        this.mProductId = ((Builder) builder).mProductId;
        int i = ((Builder) builder).mAssociatedDisplayId;
        this.mAssociatedDisplayId = i;
        String str = (String) Objects.requireNonNull(((Builder) builder).mInputDeviceName, "Missing device name");
        this.mInputDeviceName = str;
        if (i == -1) {
            throw new IllegalArgumentException("Display association is required for virtual input devices.");
        }
        if (str.getBytes(StandardCharsets.UTF_8).length < 80) {
            return;
        }
        throw new IllegalArgumentException("Input device name exceeds maximum length of 80bytes: " + str);
    }

    protected VirtualInputDeviceConfig(Parcel parcel) {
        this.mVendorId = parcel.readInt();
        this.mProductId = parcel.readInt();
        this.mAssociatedDisplayId = parcel.readInt();
        this.mInputDeviceName = (String) Objects.requireNonNull(parcel.readString8(), "Missing device name");
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mVendorId);
        parcel.writeInt(this.mProductId);
        parcel.writeInt(this.mAssociatedDisplayId);
        parcel.writeString8(this.mInputDeviceName);
    }

    public String toString() {
        return getClass().getName() + "(  name=" + this.mInputDeviceName + " vendorId=" + this.mVendorId + " productId=" + this.mProductId + " associatedDisplayId=" + this.mAssociatedDisplayId + additionalFieldsToString() + NavigationBarInflaterView.KEY_CODE_END;
    }

    String additionalFieldsToString() {
        return "";
    }

    public static abstract class Builder<T extends Builder<T>> {
        private int mAssociatedDisplayId = -1;
        private String mInputDeviceName;
        private int mProductId;
        private int mVendorId;

        T self() {
            return this;
        }

        public T setVendorId(int i) {
            this.mVendorId = i;
            return self();
        }

        public T setProductId(int i) {
            this.mProductId = i;
            return self();
        }

        public T setAssociatedDisplayId(int i) {
            this.mAssociatedDisplayId = i;
            return self();
        }

        public T setInputDeviceName(String str) {
            this.mInputDeviceName = (String) Objects.requireNonNull(str);
            return self();
        }
    }
}
