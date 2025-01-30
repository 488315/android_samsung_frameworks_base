package com.samsung.android.knox.application;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class UsbDeviceConfig implements Parcelable {
    public static final Parcelable.Creator<UsbDeviceConfig> CREATOR = new Parcelable.Creator<UsbDeviceConfig>() { // from class: com.samsung.android.knox.application.UsbDeviceConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final UsbDeviceConfig[] newArray(int i) {
            return new UsbDeviceConfig[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final UsbDeviceConfig createFromParcel(Parcel parcel) {
            return new UsbDeviceConfig(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final UsbDeviceConfig[] newArray(int i) {
            return new UsbDeviceConfig[i];
        }
    };
    public int productId;
    public int vendorId;

    public /* synthetic */ UsbDeviceConfig(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        UsbDeviceConfig usbDeviceConfig;
        int i;
        int i2;
        return obj != null && (obj instanceof UsbDeviceConfig) && (i = (usbDeviceConfig = (UsbDeviceConfig) obj).vendorId) > 0 && (i2 = usbDeviceConfig.productId) > 0 && this.vendorId == i && this.productId == i2;
    }

    public final void readFromParcel(Parcel parcel) {
        this.vendorId = parcel.readInt();
        this.productId = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
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
