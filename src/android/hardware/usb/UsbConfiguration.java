package android.hardware.usb;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;

/* loaded from: classes2.dex */
public class UsbConfiguration implements Parcelable {
    private static final int ATTR_REMOTE_WAKEUP = 32;
    private static final int ATTR_SELF_POWERED = 64;
    public static final Parcelable.Creator<UsbConfiguration> CREATOR = new Parcelable.Creator<UsbConfiguration>() { // from class: android.hardware.usb.UsbConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsbConfiguration createFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            String string = parcel.readString();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            Parcelable[] parcelableArr = (Parcelable[]) parcel.readParcelableArray(UsbInterface.class.getClassLoader(), UsbInterface.class);
            UsbConfiguration usbConfiguration = new UsbConfiguration(i, string, i2, i3);
            usbConfiguration.setInterfaces(parcelableArr);
            return usbConfiguration;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsbConfiguration[] newArray(int i) {
            return new UsbConfiguration[i];
        }
    };
    private final int mAttributes;
    private final int mId;
    private Parcelable[] mInterfaces;
    private final int mMaxPower;
    private final String mName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UsbConfiguration(int i, String str, int i2, int i3) {
        this.mId = i;
        this.mName = str;
        this.mAttributes = i2;
        this.mMaxPower = i3;
    }

    public int getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

    public boolean isSelfPowered() {
        return (this.mAttributes & 64) != 0;
    }

    public boolean isRemoteWakeup() {
        return (this.mAttributes & 32) != 0;
    }

    public int getAttributes() {
        return this.mAttributes;
    }

    public int getMaxPower() {
        return this.mMaxPower * 2;
    }

    public int getInterfaceCount() {
        return this.mInterfaces.length;
    }

    public UsbInterface getInterface(int i) {
        return (UsbInterface) this.mInterfaces[i];
    }

    public void setInterfaces(Parcelable[] parcelableArr) {
        this.mInterfaces = (Parcelable[]) Preconditions.checkArrayElementsNotNull(parcelableArr, "interfaces");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("UsbConfiguration[mId=" + this.mId + ",mName=" + this.mName + ",mAttributes=" + this.mAttributes + ",mMaxPower=" + this.mMaxPower + ",mInterfaces=[");
        for (int i = 0; i < this.mInterfaces.length; i++) {
            sb.append(ShaderAssembler.NEWLINE);
            sb.append(this.mInterfaces[i].toString());
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mAttributes);
        parcel.writeInt(this.mMaxPower);
        parcel.writeParcelableArray(this.mInterfaces, 0);
    }
}
