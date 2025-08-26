package android.hardware.usb;

import android.app.ActivityThread;
import android.hardware.usb.IUsbSerialReader;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.util.Preconditions;
import java.util.Objects;

/* loaded from: classes2.dex */
public class UsbAccessory implements Parcelable {
    public static final Parcelable.Creator<UsbAccessory> CREATOR = new Parcelable.Creator<UsbAccessory>() { // from class: android.hardware.usb.UsbAccessory.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsbAccessory createFromParcel(Parcel parcel) {
            return new UsbAccessory(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), IUsbSerialReader.Stub.asInterface(parcel.readStrongBinder()));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsbAccessory[] newArray(int i) {
            return new UsbAccessory[i];
        }
    };
    public static final int DESCRIPTION_STRING = 2;
    public static final int MANUFACTURER_STRING = 0;
    public static final int MODEL_STRING = 1;
    public static final int SERIAL_STRING = 5;
    private static final String TAG = "UsbAccessory";
    public static final int URI_STRING = 4;
    public static final int VERSION_STRING = 3;
    private final String mDescription;
    private final String mManufacturer;
    private final String mModel;
    private final IUsbSerialReader mSerialNumberReader;
    private final String mUri;
    private final String mVersion;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UsbAccessory(String str, String str2, String str3, String str4, String str5, IUsbSerialReader iUsbSerialReader) {
        this.mManufacturer = (String) Objects.requireNonNull(str);
        this.mModel = (String) Objects.requireNonNull(str2);
        this.mDescription = str3;
        this.mVersion = str4;
        this.mUri = str5;
        this.mSerialNumberReader = iUsbSerialReader;
        if (ActivityThread.isSystem()) {
            Preconditions.checkArgument(iUsbSerialReader instanceof IUsbSerialReader.Stub);
        }
    }

    @Deprecated
    public UsbAccessory(String str, String str2, String str3, String str4, String str5, final String str6) {
        this(str, str2, str3, str4, str5, new IUsbSerialReader.Stub() { // from class: android.hardware.usb.UsbAccessory.1
            @Override // android.hardware.usb.IUsbSerialReader
            public String getSerial(String str7) {
                return str6;
            }
        });
    }

    public String getManufacturer() {
        return this.mManufacturer;
    }

    public String getModel() {
        return this.mModel;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getVersion() {
        return this.mVersion;
    }

    public String getUri() {
        return this.mUri;
    }

    public String getSerial() {
        try {
            return this.mSerialNumberReader.getSerial(ActivityThread.currentPackageName());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    private static boolean compare(String str, String str2) {
        if (str == null) {
            return str2 == null;
        }
        return str.equals(str2);
    }

    public boolean equals(Object obj) {
        if (obj instanceof UsbAccessory) {
            UsbAccessory usbAccessory = (UsbAccessory) obj;
            if (compare(this.mManufacturer, usbAccessory.getManufacturer()) && compare(this.mModel, usbAccessory.getModel()) && compare(this.mDescription, usbAccessory.getDescription()) && compare(this.mVersion, usbAccessory.getVersion()) && compare(this.mUri, usbAccessory.getUri()) && compare(getSerial(), usbAccessory.getSerial())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int iHashCode = this.mManufacturer.hashCode() ^ this.mModel.hashCode();
        String str = this.mDescription;
        int iHashCode2 = iHashCode ^ (str == null ? 0 : str.hashCode());
        String str2 = this.mVersion;
        int iHashCode3 = iHashCode2 ^ (str2 == null ? 0 : str2.hashCode());
        String str3 = this.mUri;
        return iHashCode3 ^ (str3 != null ? str3.hashCode() : 0);
    }

    public String toString() {
        return "UsbAccessory[mManufacturer=" + this.mManufacturer + ", mModel=" + this.mModel + ", mDescription=" + this.mDescription + ", mVersion=" + this.mVersion + ", mUri=" + this.mUri + ", mSerialNumberReader=" + this.mSerialNumberReader + NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mManufacturer);
        parcel.writeString(this.mModel);
        parcel.writeString(this.mDescription);
        parcel.writeString(this.mVersion);
        parcel.writeString(this.mUri);
        parcel.writeStrongBinder(this.mSerialNumberReader.asBinder());
    }
}
