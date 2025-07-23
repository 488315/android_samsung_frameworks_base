package com.samsung.android.knox.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class BluetoothSecureModeConfig implements Parcelable {
    public static final Parcelable.Creator<BluetoothSecureModeConfig> CREATOR = new Parcelable.Creator<BluetoothSecureModeConfig>() { // from class: com.samsung.android.knox.bluetooth.BluetoothSecureModeConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothSecureModeConfig createFromParcel(Parcel parcel) {
            return new BluetoothSecureModeConfig(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothSecureModeConfig[] newArray(int i) {
            return new BluetoothSecureModeConfig[i];
        }
    };
    public boolean a2dpEnable;
    public boolean ftpEnable;
    public boolean gattEnable;
    public boolean hdpEnable;
    public boolean hfpEnable;
    public boolean hidEnable;
    public boolean mapEnable;
    public boolean oppEnable;
    public boolean pairingMode;
    public boolean panEnable;
    public boolean pbapEnable;
    public boolean sapEnable;
    public boolean scanMode;
    public boolean whitelistEnable;

    public /* synthetic */ BluetoothSecureModeConfig(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        if (parcel == null) {
            return;
        }
        this.scanMode = parcel.readInt() != 0;
        this.pairingMode = parcel.readInt() != 0;
        this.hfpEnable = parcel.readInt() != 0;
        this.a2dpEnable = parcel.readInt() != 0;
        this.hidEnable = parcel.readInt() != 0;
        this.hdpEnable = parcel.readInt() != 0;
        this.panEnable = parcel.readInt() != 0;
        this.oppEnable = parcel.readInt() != 0;
        this.pbapEnable = parcel.readInt() != 0;
        this.mapEnable = parcel.readInt() != 0;
        this.ftpEnable = parcel.readInt() != 0;
        this.sapEnable = parcel.readInt() != 0;
        this.whitelistEnable = parcel.readInt() != 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            return;
        }
        parcel.writeInt(this.scanMode ? 1 : 0);
        parcel.writeInt(this.pairingMode ? 1 : 0);
        parcel.writeInt(this.hfpEnable ? 1 : 0);
        parcel.writeInt(this.a2dpEnable ? 1 : 0);
        parcel.writeInt(this.hidEnable ? 1 : 0);
        parcel.writeInt(this.hdpEnable ? 1 : 0);
        parcel.writeInt(this.panEnable ? 1 : 0);
        parcel.writeInt(this.oppEnable ? 1 : 0);
        parcel.writeInt(this.pbapEnable ? 1 : 0);
        parcel.writeInt(this.mapEnable ? 1 : 0);
        parcel.writeInt(this.ftpEnable ? 1 : 0);
        parcel.writeInt(this.sapEnable ? 1 : 0);
        parcel.writeInt(this.whitelistEnable ? 1 : 0);
    }

    private BluetoothSecureModeConfig(Parcel parcel) {
        readFromParcel(parcel);
    }

    public BluetoothSecureModeConfig() {
    }
}
