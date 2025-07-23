package android.hardware.usb;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class UsbEndpoint implements Parcelable {
    public static final Parcelable.Creator<UsbEndpoint> CREATOR = new Parcelable.Creator<UsbEndpoint>() { // from class: android.hardware.usb.UsbEndpoint.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsbEndpoint createFromParcel(Parcel parcel) {
            return new UsbEndpoint(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsbEndpoint[] newArray(int i) {
            return new UsbEndpoint[i];
        }
    };
    private final int mAddress;
    private final int mAttributes;
    private final int mInterval;
    private final int mMaxPacketSize;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UsbEndpoint(int i, int i2, int i3, int i4) {
        this.mAddress = i;
        this.mAttributes = i2;
        this.mMaxPacketSize = i3;
        this.mInterval = i4;
    }

    public int getAddress() {
        return this.mAddress;
    }

    public int getEndpointNumber() {
        return this.mAddress & 15;
    }

    public int getDirection() {
        return this.mAddress & 128;
    }

    public int getAttributes() {
        return this.mAttributes;
    }

    public int getType() {
        return this.mAttributes & 3;
    }

    public int getMaxPacketSize() {
        return this.mMaxPacketSize;
    }

    public int getInterval() {
        return this.mInterval;
    }

    public String toString() {
        return "UsbEndpoint[mAddress=" + this.mAddress + ",mAttributes=" + this.mAttributes + ",mMaxPacketSize=" + this.mMaxPacketSize + ",mInterval=" + this.mInterval + NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mAddress);
        parcel.writeInt(this.mAttributes);
        parcel.writeInt(this.mMaxPacketSize);
        parcel.writeInt(this.mInterval);
    }
}
