package android.hardware.usb;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;

/* loaded from: classes2.dex */
public class UsbInterface implements Parcelable {
    public static final Parcelable.Creator<UsbInterface> CREATOR = new Parcelable.Creator<UsbInterface>() { // from class: android.hardware.usb.UsbInterface.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsbInterface createFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            String readString = parcel.readString();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            int readInt5 = parcel.readInt();
            Parcelable[] readParcelableArray = parcel.readParcelableArray(UsbEndpoint.class.getClassLoader());
            UsbInterface usbInterface = new UsbInterface(readInt, readInt2, readString, readInt3, readInt4, readInt5);
            usbInterface.setEndpoints(readParcelableArray);
            return usbInterface;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsbInterface[] newArray(int i) {
            return new UsbInterface[i];
        }
    };
    private final int mAlternateSetting;
    private final int mClass;
    private Parcelable[] mEndpoints;
    private final int mId;
    private final String mName;
    private final int mProtocol;
    private final int mSubclass;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UsbInterface(int i, int i2, String str, int i3, int i4, int i5) {
        this.mId = i;
        this.mAlternateSetting = i2;
        this.mName = str;
        this.mClass = i3;
        this.mSubclass = i4;
        this.mProtocol = i5;
    }

    public int getId() {
        return this.mId;
    }

    public int getAlternateSetting() {
        return this.mAlternateSetting;
    }

    public String getName() {
        return this.mName;
    }

    public int getInterfaceClass() {
        return this.mClass;
    }

    public int getInterfaceSubclass() {
        return this.mSubclass;
    }

    public int getInterfaceProtocol() {
        return this.mProtocol;
    }

    public int getEndpointCount() {
        return this.mEndpoints.length;
    }

    public UsbEndpoint getEndpoint(int i) {
        return (UsbEndpoint) this.mEndpoints[i];
    }

    public void setEndpoints(Parcelable[] parcelableArr) {
        this.mEndpoints = (Parcelable[]) Preconditions.checkArrayElementsNotNull(parcelableArr, "endpoints");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("UsbInterface[mId=" + this.mId + ",mAlternateSetting=" + this.mAlternateSetting + ",mName=" + this.mName + ",mClass=" + this.mClass + ",mSubclass=" + this.mSubclass + ",mProtocol=" + this.mProtocol + ",mEndpoints=[");
        for (int i = 0; i < this.mEndpoints.length; i++) {
            sb.append(ShaderAssembler.NEWLINE);
            sb.append(this.mEndpoints[i].toString());
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mAlternateSetting);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mClass);
        parcel.writeInt(this.mSubclass);
        parcel.writeInt(this.mProtocol);
        parcel.writeParcelableArray(this.mEndpoints, 0);
    }
}
