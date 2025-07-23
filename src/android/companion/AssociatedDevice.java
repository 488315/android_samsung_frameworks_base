package android.companion;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class AssociatedDevice implements Parcelable {
    private static final int BLUETOOTH_LE = 1;
    private static final int CLASSIC_BLUETOOTH = 0;
    public static final Parcelable.Creator<AssociatedDevice> CREATOR = new Parcelable.Creator<AssociatedDevice>() { // from class: android.companion.AssociatedDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssociatedDevice[] newArray(int i) {
            return new AssociatedDevice[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssociatedDevice createFromParcel(Parcel parcel) {
            return new AssociatedDevice(parcel);
        }
    };
    private static final int WIFI = 2;
    private final Parcelable mDevice;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AssociatedDevice(Parcelable parcelable) {
        this.mDevice = parcelable;
    }

    private AssociatedDevice(Parcel parcel) {
        this.mDevice = getDeviceCreator(parcel.readInt()).createFromParcel(parcel);
    }

    public BluetoothDevice getBluetoothDevice() {
        Parcelable parcelable = this.mDevice;
        if (parcelable instanceof BluetoothDevice) {
            return (BluetoothDevice) parcelable;
        }
        return null;
    }

    public ScanResult getBleDevice() {
        Parcelable parcelable = this.mDevice;
        if (parcelable instanceof ScanResult) {
            return (ScanResult) parcelable;
        }
        return null;
    }

    public android.net.wifi.ScanResult getWifiDevice() {
        Parcelable parcelable = this.mDevice;
        if (parcelable instanceof android.net.wifi.ScanResult) {
            return (android.net.wifi.ScanResult) parcelable;
        }
        return null;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getDeviceType());
        this.mDevice.writeToParcel(parcel, i);
    }

    private int getDeviceType() {
        Parcelable parcelable = this.mDevice;
        if (parcelable instanceof BluetoothDevice) {
            return 0;
        }
        if (parcelable instanceof ScanResult) {
            return 1;
        }
        if (parcelable instanceof android.net.wifi.ScanResult) {
            return 2;
        }
        throw new UnsupportedOperationException("Unsupported device type.");
    }

    private static Parcelable.Creator<? extends Parcelable> getDeviceCreator(int i) {
        if (i == 0) {
            return BluetoothDevice.CREATOR;
        }
        if (i == 1) {
            return ScanResult.CREATOR;
        }
        if (i == 2) {
            return android.net.wifi.ScanResult.CREATOR;
        }
        throw new UnsupportedOperationException("Unsupported device type.");
    }

    public String toString() {
        return "AssociatedDevice { device = " + this.mDevice + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AssociatedDevice associatedDevice = (AssociatedDevice) obj;
        if (getDeviceType() != associatedDevice.getDeviceType()) {
            return false;
        }
        Parcelable parcelable = this.mDevice;
        if ((parcelable instanceof ScanResult) || (parcelable instanceof android.net.wifi.ScanResult)) {
            return parcelable.toString().equals(associatedDevice.mDevice.toString());
        }
        return Objects.equals(parcelable, associatedDevice.mDevice);
    }

    public int hashCode() {
        return Objects.hash(this.mDevice);
    }
}
