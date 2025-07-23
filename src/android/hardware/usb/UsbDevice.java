package android.hardware.usb;

import android.app.ActivityThread;
import android.hardware.usb.IUsbSerialReader;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.util.Preconditions;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.Objects;

/* loaded from: classes2.dex */
public class UsbDevice implements Parcelable {
    public static final Parcelable.Creator<UsbDevice> CREATOR = new Parcelable.Creator<UsbDevice>() { // from class: android.hardware.usb.UsbDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsbDevice createFromParcel(Parcel parcel) {
            boolean z;
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            int readInt5 = parcel.readInt();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            String readString4 = parcel.readString();
            IUsbSerialReader asInterface = IUsbSerialReader.Stub.asInterface(parcel.readStrongBinder());
            UsbConfiguration[] usbConfigurationArr = (UsbConfiguration[]) parcel.readParcelableArray(UsbConfiguration.class.getClassLoader(), UsbConfiguration.class);
            boolean z2 = false;
            boolean z3 = parcel.readInt() == 1;
            if (parcel.readInt() == 1) {
                z = false;
                z2 = true;
            } else {
                z = false;
            }
            return new UsbDevice(readString, readInt, readInt2, readInt3, readInt4, readInt5, readString2, readString3, readString4, usbConfigurationArr, asInterface, z3, z2, parcel.readInt() == 1 ? true : z, parcel.readInt() == 1, parcel.readInt() == 1);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsbDevice[] newArray(int i) {
            return new UsbDevice[i];
        }
    };
    private static final boolean DEBUG = false;
    private static final String TAG = "UsbDevice";
    private final int mClass;
    private final UsbConfiguration[] mConfigurations;
    private final boolean mHasAudioCapture;
    private final boolean mHasAudioPlayback;
    private final boolean mHasMidi;
    private final boolean mHasVideoCapture;
    private final boolean mHasVideoPlayback;
    private UsbInterface[] mInterfaces;
    private final String mManufacturerName;
    private final String mName;
    private final int mProductId;
    private final String mProductName;
    private final int mProtocol;
    private final IUsbSerialReader mSerialNumberReader;
    private final int mSubclass;
    private final int mVendorId;
    private final String mVersion;

    private static native int native_get_device_id(String str);

    private static native String native_get_device_name(int i);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private UsbDevice(String str, int i, int i2, int i3, int i4, int i5, String str2, String str3, String str4, UsbConfiguration[] usbConfigurationArr, IUsbSerialReader iUsbSerialReader, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.mName = (String) Objects.requireNonNull(str);
        this.mVendorId = i;
        this.mProductId = i2;
        this.mClass = i3;
        this.mSubclass = i4;
        this.mProtocol = i5;
        this.mManufacturerName = str2;
        this.mProductName = str3;
        this.mVersion = (String) Preconditions.checkStringNotEmpty(str4);
        this.mConfigurations = (UsbConfiguration[]) Preconditions.checkArrayElementsNotNull(usbConfigurationArr, "configurations");
        IUsbSerialReader iUsbSerialReader2 = (IUsbSerialReader) Objects.requireNonNull(iUsbSerialReader);
        this.mSerialNumberReader = iUsbSerialReader2;
        this.mHasAudioPlayback = z;
        this.mHasAudioCapture = z2;
        this.mHasMidi = z3;
        this.mHasVideoPlayback = z4;
        this.mHasVideoCapture = z5;
        if (ActivityThread.isSystem()) {
            Preconditions.checkArgument(iUsbSerialReader2 instanceof IUsbSerialReader.Stub);
        }
    }

    public String getDeviceName() {
        return this.mName;
    }

    public String getManufacturerName() {
        return this.mManufacturerName;
    }

    public String getProductName() {
        return this.mProductName;
    }

    public String getVersion() {
        return this.mVersion;
    }

    public String getSerialNumber() {
        try {
            return this.mSerialNumberReader.getSerial(ActivityThread.currentPackageName());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public int getDeviceId() {
        return getDeviceId(this.mName);
    }

    public int getVendorId() {
        return this.mVendorId;
    }

    public int getProductId() {
        return this.mProductId;
    }

    public int getDeviceClass() {
        return this.mClass;
    }

    public int getDeviceSubclass() {
        return this.mSubclass;
    }

    public int getDeviceProtocol() {
        return this.mProtocol;
    }

    public int getConfigurationCount() {
        return this.mConfigurations.length;
    }

    public boolean getHasAudioPlayback() {
        return this.mHasAudioPlayback;
    }

    public boolean getHasAudioCapture() {
        return this.mHasAudioCapture;
    }

    public boolean getHasMidi() {
        return this.mHasMidi;
    }

    public boolean getHasVideoPlayback() {
        return this.mHasVideoPlayback;
    }

    public boolean getHasVideoCapture() {
        return this.mHasVideoCapture;
    }

    public UsbConfiguration getConfiguration(int i) {
        return this.mConfigurations[i];
    }

    private UsbInterface[] getInterfaceList() {
        if (this.mInterfaces == null) {
            int length = this.mConfigurations.length;
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                i += this.mConfigurations[i2].getInterfaceCount();
            }
            this.mInterfaces = new UsbInterface[i];
            int i3 = 0;
            for (int i4 = 0; i4 < length; i4++) {
                UsbConfiguration usbConfiguration = this.mConfigurations[i4];
                int interfaceCount = usbConfiguration.getInterfaceCount();
                int i5 = 0;
                while (i5 < interfaceCount) {
                    this.mInterfaces[i3] = usbConfiguration.getInterface(i5);
                    i5++;
                    i3++;
                }
            }
        }
        return this.mInterfaces;
    }

    public int getInterfaceCount() {
        return getInterfaceList().length;
    }

    public UsbInterface getInterface(int i) {
        return getInterfaceList()[i];
    }

    public boolean equals(Object obj) {
        if (obj instanceof UsbDevice) {
            return ((UsbDevice) obj).mName.equals(this.mName);
        }
        if (obj instanceof String) {
            return ((String) obj).equals(this.mName);
        }
        return false;
    }

    public int hashCode() {
        return this.mName.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("UsbDevice[mName=" + this.mName + ",mVendorId=" + this.mVendorId + ",mProductId=" + this.mProductId + ",mClass=" + this.mClass + ",mSubclass=" + this.mSubclass + ",mProtocol=" + this.mProtocol + ",mManufacturerName=" + this.mManufacturerName + ",mProductName=" + this.mProductName + ",mVersion=" + this.mVersion + ",mSerialNumberReader=" + this.mSerialNumberReader + ", mHasAudioPlayback=" + this.mHasAudioPlayback + ", mHasAudioCapture=" + this.mHasAudioCapture + ", mHasMidi=" + this.mHasMidi + ", mHasVideoCapture=" + this.mHasVideoCapture + ", mHasVideoPlayback=" + this.mHasVideoPlayback + ", mConfigurations=[");
        for (int i = 0; i < this.mConfigurations.length; i++) {
            sb.append(ShaderAssembler.NEWLINE);
            sb.append(this.mConfigurations[i].toString());
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeInt(this.mVendorId);
        parcel.writeInt(this.mProductId);
        parcel.writeInt(this.mClass);
        parcel.writeInt(this.mSubclass);
        parcel.writeInt(this.mProtocol);
        parcel.writeString(this.mManufacturerName);
        parcel.writeString(this.mProductName);
        parcel.writeString(this.mVersion);
        parcel.writeStrongBinder(this.mSerialNumberReader.asBinder());
        parcel.writeParcelableArray(this.mConfigurations, 0);
        parcel.writeInt(this.mHasAudioPlayback ? 1 : 0);
        parcel.writeInt(this.mHasAudioCapture ? 1 : 0);
        parcel.writeInt(this.mHasMidi ? 1 : 0);
        parcel.writeInt(this.mHasVideoPlayback ? 1 : 0);
        parcel.writeInt(this.mHasVideoCapture ? 1 : 0);
    }

    public static int getDeviceId(String str) {
        return native_get_device_id(str);
    }

    public static String getDeviceName(int i) {
        return native_get_device_name(i);
    }

    public static class Builder {
        private final int mClass;
        private final UsbConfiguration[] mConfigurations;
        private final boolean mHasAudioCapture;
        private final boolean mHasAudioPlayback;
        private final boolean mHasMidi;
        private final boolean mHasVideoCapture;
        private final boolean mHasVideoPlayback;
        private final String mManufacturerName;
        private final String mName;
        private final int mProductId;
        private final String mProductName;
        private final int mProtocol;
        private final int mSubclass;
        private final int mVendorId;
        private final String mVersion;
        public final String serialNumber;

        public Builder(String str, int i, int i2, int i3, int i4, int i5, String str2, String str3, String str4, UsbConfiguration[] usbConfigurationArr, String str5, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
            this.mName = (String) Objects.requireNonNull(str);
            this.mVendorId = i;
            this.mProductId = i2;
            this.mClass = i3;
            this.mSubclass = i4;
            this.mProtocol = i5;
            this.mManufacturerName = str2;
            this.mProductName = str3;
            this.mVersion = (String) Preconditions.checkStringNotEmpty(str4);
            this.mConfigurations = usbConfigurationArr;
            this.serialNumber = str5;
            this.mHasAudioPlayback = z;
            this.mHasAudioCapture = z2;
            this.mHasMidi = z3;
            this.mHasVideoPlayback = z4;
            this.mHasVideoCapture = z5;
        }

        public UsbDevice build(IUsbSerialReader iUsbSerialReader) {
            return new UsbDevice(this.mName, this.mVendorId, this.mProductId, this.mClass, this.mSubclass, this.mProtocol, this.mManufacturerName, this.mProductName, this.mVersion, this.mConfigurations, iUsbSerialReader, this.mHasAudioPlayback, this.mHasAudioCapture, this.mHasMidi, this.mHasVideoPlayback, this.mHasVideoCapture);
        }
    }
}
