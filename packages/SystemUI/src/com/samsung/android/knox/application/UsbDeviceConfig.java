package com.samsung.android.knox.application;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class UsbDeviceConfig implements Parcelable {
    public static final Parcelable.Creator<UsbDeviceConfig> CREATOR = new Parcelable.Creator<UsbDeviceConfig>() { // from class: com.samsung.android.knox.application.UsbDeviceConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsbDeviceConfig createFromParcel(Parcel parcel) {
            return new UsbDeviceConfig(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsbDeviceConfig[] newArray(int i) {
            return new UsbDeviceConfig[i];
        }
    };
    public int productId;
    public int vendorId;

    public /* synthetic */ UsbDeviceConfig(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        UsbDeviceConfig usbDeviceConfig;
        int i;
        int i2;
        return obj != null && (obj instanceof UsbDeviceConfig) && (i = (usbDeviceConfig = (UsbDeviceConfig) obj).vendorId) > 0 && (i2 = usbDeviceConfig.productId) > 0 && this.vendorId == i && this.productId == i2;
    }

    public void readFromParcel(Parcel parcel) {
        this.vendorId = parcel.readInt();
        this.productId = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.vendorId);
        parcel.writeInt(this.productId);
    }

    public UsbDeviceConfig() {
    }

    public UsbDeviceConfig(int i, int i2) {
        this.vendorId = i;
        this.productId = i2;
    }

    private UsbDeviceConfig(Parcel parcel) {
        readFromParcel(parcel);
    }
}
